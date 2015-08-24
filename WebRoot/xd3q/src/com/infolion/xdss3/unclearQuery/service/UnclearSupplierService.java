package com.infolion.xdss3.unclearQuery.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.util.BeanUtils;
import com.infolion.platform.util.StringUtils;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.masterData.dao.CustomerTitleJdbcDao;
import com.infolion.xdss3.masterData.dao.VendorTitleJdbcDao;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.masterData.domain.VendorTitle;
import com.infolion.xdss3.masterData.service.VendorService;
import com.infolion.xdss3.payment.service.PaymentItemService;
import com.infolion.xdss3.singleClear.dao.SupplierSinglClearJdbcDao;
import com.infolion.xdss3.unclearQuery.domain.UnclearCustomer;
import com.infolion.xdss3.unclearQuery.domain.UnclearSupplier;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;
@Service
public class UnclearSupplierService extends BaseService{
	@Autowired
	private VendorTitleJdbcDao vendorTitleJdbcDao;

	
	public void setVendorTitleJdbcDao(VendorTitleJdbcDao vendorTitleJdbcDao) {
		this.vendorTitleJdbcDao = vendorTitleJdbcDao;
	}
	@Autowired
	private SupplierSinglClearJdbcDao supplierSinglClearJdbcDao;

	/**
	 * @param supplierSinglClearJdbcDao
	 *            the supplierSinglClearJdbcDao to set
	 */
	public void setSupplierSinglClearJdbcDao(SupplierSinglClearJdbcDao supplierSinglClearJdbcDao)
	{
		this.supplierSinglClearJdbcDao = supplierSinglClearJdbcDao;
	}
	@Autowired
	private VoucherItemJdbcDao voucherItemJdbcDao;

	
	public void setVoucherItemJdbcDao(VoucherItemJdbcDao voucherItemJdbcDao) {
		this.voucherItemJdbcDao = voucherItemJdbcDao;
	}
	@Autowired
	private VendorService vendorService;

	/**
	 * @param vendorService
	 *            the vendorService to set
	 */
	public void setVendorService(VendorService vendorService)
	{
		this.vendorService = vendorService;
	}

	@Autowired
	private PaymentItemService paymentItemService;

	/**
	 * @param paymentItemService
	 *            the paymentItemService to set
	 */
	public void setPaymentItemService(PaymentItemService paymentItemService)
	{
		this.paymentItemService = paymentItemService;
	}

	public List<UnclearSupplier> getUnclearSupplier(String bukrs,String gsber,String kunnr,String waers,String saknr,String augdt_to,String augdt_from){
		List<VendorTitle> vendorTitles =vendorTitleJdbcDao.getVendorTitle(bukrs, gsber, kunnr, waers, saknr, augdt_to, augdt_from);
		List<UnclearSupplier> unclearSuppliers =new ArrayList<UnclearSupplier>();
		for(VendorTitle vendorTitle :vendorTitles){
			UnclearSupplier unclearSupplier =new UnclearSupplier();
			try {
				BeanUtils.copyProperties(unclearSupplier,vendorTitle );
				BigDecimal rat = new BigDecimal(1);
				if(unclearSupplier.getDmbtr().compareTo(BigDecimal.valueOf(0)) !=0){
				 rat = unclearSupplier.getBwbje().divide(unclearSupplier.getDmbtr(),5,BigDecimal.ROUND_HALF_EVEN);
				}
				unclearSupplier.setRat(rat);
				String vendorTitleId = vendorTitle.getVendortitleid();
				String rowNumber = vendorTitle.getBuzei();				
				// 会计年度
				String fiYear = vendorTitle.getGjahr();
				// 财务凭证号
				String voucherNo = vendorTitle.getBelnr();
				// 公司代码
				String companyCode = vendorTitle.getBukrs();
				if("0".equals(vendorTitle.getIscleared())){
					BigDecimal offsetamount = new BigDecimal("0");
					if("H".equals(vendorTitle.getShkzg())){
						if(StringUtils.isNullBlank(vendorTitle.getInvoice())){
							offsetamount = this.vendorService.getSumClearAmountByVendorTitleID(vendorTitle.getVendortitleid().trim(), BusinessState.ALL_SUBMITPASS);
						}else{
							offsetamount = this.vendorService.getSumClearAmount(vendorTitle.getInvoice(), BusinessState.ALL_SUBMITPASS);
						}
						
					}else if("S".equals(vendorTitle.getShkzg())){
						String businessItemId = this.voucherItemJdbcDao.getBusinessId(voucherNo, companyCode, fiYear, rowNumber);
						
						if (StringUtils.isNullBlank(businessItemId)){
							// 已清金额
							offsetamount = supplierSinglClearJdbcDao.getSumClearAmount(vendorTitleId.trim(), BusinessState.ALL_SUBMITPASS);// 根据vendorTitleId到YUNCLEARPAYMENT计算已经金额。
						}else{
							offsetamount = this.paymentItemService.getSumClearAmount(businessItemId.trim(), BusinessState.ALL_SUBMITPASS);
						}
					}
					unclearSupplier.setUnAmount(unclearSupplier.getDmbtr().subtract(offsetamount));
					unclearSupplier.setUnbwbje(unclearSupplier.getUnAmount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				unclearSuppliers.add(unclearSupplier);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return unclearSuppliers;
	}
}
