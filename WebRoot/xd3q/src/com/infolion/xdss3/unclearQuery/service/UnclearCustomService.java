package com.infolion.xdss3.unclearQuery.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.util.BeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.collectitem.service.CollectItemService;
import com.infolion.xdss3.masterData.dao.CustomerTitleJdbcDao;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.masterData.service.UnclearCustomerService;
import com.infolion.xdss3.singleClear.dao.CustomSingleClearJdbcDao;
import com.infolion.xdss3.unclearQuery.domain.UnclearCustomer;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;

@Service
public class UnclearCustomService extends BaseService{
	@Autowired
	private CustomerTitleJdbcDao customerTitleJdbcDao;

	/**
	 * @param customerTitleJdbcDao
	 *            the customerTitleJdbcDao to set
	 */
	public void setCustomerTitleJdbcDao(CustomerTitleJdbcDao customerTitleJdbcDao)
	{
		this.customerTitleJdbcDao = customerTitleJdbcDao;
	}
	
	@Autowired
	private UnclearCustomerService unclearCustomerService;

	/**
	 * @param unclearCustomerService
	 *            the unclearCustomerService to set
	 */
	public void setUnclearCustomerService(UnclearCustomerService unclearCustomerService)
	{
		this.unclearCustomerService = unclearCustomerService;
	}
	
	@Autowired
	private CollectItemService collectItemService;

	/**
	 * @param collectItemService
	 *            the collectItemService to set
	 */
	public void setCollectItemService(CollectItemService collectItemService)
	{
		this.collectItemService = collectItemService;
	}
	
	@Autowired
	private VoucherItemJdbcDao voucherItemJdbcDao;

	
	public void setVoucherItemJdbcDao(VoucherItemJdbcDao voucherItemJdbcDao) {
		this.voucherItemJdbcDao = voucherItemJdbcDao;
	}
	
	@Autowired
	private CustomSingleClearJdbcDao customSingleClearJdbcDao;

	/**
	 * @param customSingleClearJdbcDao
	 *            the customSingleClearJdbcDao to set
	 */
	public void setCustomSingleClearJdbcDao(CustomSingleClearJdbcDao customSingleClearJdbcDao)
	{
		this.customSingleClearJdbcDao = customSingleClearJdbcDao;
	}
	
	public List<UnclearCustomer> getUnclearCustomer(String bukrs,String gsber,String kunnr,String waers,String saknr,String augdt_to,String augdt_from){
		List<CustomerTitle> customerTitles =customerTitleJdbcDao.getCustomerTitle(bukrs, gsber, kunnr, waers, saknr, augdt_to, augdt_from);
		List<UnclearCustomer> unclearCustomers =new ArrayList<UnclearCustomer>();
		for(CustomerTitle customerTitle :customerTitles){
			UnclearCustomer unclearCustomer =new UnclearCustomer();
			try {
				BeanUtils.copyProperties(unclearCustomer,customerTitle);
				BigDecimal rat = new BigDecimal(1);
				if(unclearCustomer.getDmbtr().compareTo(BigDecimal.valueOf(0)) !=0){
					rat = unclearCustomer.getBwbje().divide(unclearCustomer.getDmbtr(),5,BigDecimal.ROUND_HALF_EVEN);
				}
				
				unclearCustomer.setRat(rat);
				BigDecimal receivedamount = new BigDecimal("0");
				// 会计年度
				String fiYear = customerTitle.getGjahr();
				// 财务凭证号
				String voucherNo = customerTitle.getBelnr();
				// 公司代码
				String companyCode = customerTitle.getBukrs();
				String rowNumber = customerTitle.getBuzei();
				if("0".equals(customerTitle.getIscleared())){
					if("S".equals(customerTitle.getShkzg())){
						// 开票发票已经审批完的，发票已收款
						receivedamount = this.unclearCustomerService.getSumClearAmount(customerTitle.getInvoice().trim(), BusinessState.ALL_COLLECT_PAIDUP);
					}else if("H".equals(customerTitle.getShkzg())){
						String businessItemId = this.voucherItemJdbcDao.getBusinessId(voucherNo, companyCode, fiYear, rowNumber);
						if (StringUtils.isNullBlank(businessItemId)){
							receivedamount = this.customSingleClearJdbcDao.getSumClearAmount(customerTitle.getCustomertitleid(), BusinessState.ALL_COLLECT_PAIDUP);
						}else{
							receivedamount = this.collectItemService.getSumClearAmount(businessItemId.trim(), BusinessState.ALL_COLLECT_PAIDUP);
						}
					}
					unclearCustomer.setUnAmount(unclearCustomer.getDmbtr().subtract(receivedamount));
					unclearCustomer.setUnbwbje(unclearCustomer.getUnAmount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				unclearCustomers.add(unclearCustomer);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return unclearCustomers;
	}
}
