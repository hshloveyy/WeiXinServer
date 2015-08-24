/*
 * @(#)PurchaseContractJdbcService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-3-3
 *  描　述：创建
 */

package com.infolion.sapss.contract.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.sapss.common.dto.CommonChangeDto;
import com.infolion.sapss.contract.dao.PurchaseContractJdbcDAO;
import com.infolion.sapss.contract.domain.TContractPurchaseMateriel;
import com.infolion.sapss.workflow.ProcessCallBack;
@Service
public class PurchaseContractJdbcService extends BaseJdbcService implements ProcessCallBack{
	@Autowired
	private PurchaseContractJdbcDAO purchaseContractJdbcDAO;

	public void setPurchaseContractJdbcDAO(
			PurchaseContractJdbcDAO purchaseContractJdbcDAO) {
		this.purchaseContractJdbcDAO = purchaseContractJdbcDAO;
	}

	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
		
	}
	/**
	 * 计算采购总金额
	 */
	public Map sumPurchase(String purchaseId){//nvl(a.ekpo_menge,0) * nvl(a.ekpo_netpr,0) * nvl(a.ekpo_peinh,0)
		List<TContractPurchaseMateriel> list = purchaseContractJdbcDAO.findMaterial(purchaseId);
		Map map = new HashMap();
		double total =0.0;
		double pcsTotal = 0.0;
		double tax =0.0;
		double pcsTax =0.0;
		double p =0;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TContractPurchaseMateriel pm = (TContractPurchaseMateriel) iterator.next();
			total = total+  Double.valueOf(purchaseContractJdbcDAO.sumOtherFee(pm.getPurchaseRowsId()));
			//数量*单价/条件单位
			pcsTotal= (pm.getEkpoMenge()*pm.getEkpoNetpr()/Double.valueOf(pm.getEkpoPeinh()==null || pm.getEkpoPeinh().equals("")?"1":pm.getEkpoPeinh()));
			total = total + pcsTotal;
			String saleTaxCd = SysCachePoolUtils.getDictDataValue("BM_SALES_TAX", pm.getEkpoMwskz());
			//
			pcsTax = pcsTotal*Double.valueOf(parseInt(saleTaxCd))/100;
			tax = tax + pcsTax;
			p+=pm.getEkpoMenge();
		}
		map.put("total",Double.valueOf(total));
		map.put("tax",Double.valueOf(tax));
		map.put("totalQuality",Double.valueOf(p));
		return map;
		//根据采购ID找出所有的物料ID
		//根据物料ID找出所有的其他费用
		//计算物料单行费用
		//累加所有物料行费用
	}
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String parseInt(String str){
		try{
			if(str!=null && !"".equals(str)){
				char[] chs = str.toCharArray();
				int s=0;
				int e = 0;
				boolean isS = true;
				for (int i = 0; i<chs.length; i++) {
					if(isS&&String.valueOf(chs[i]).matches("\\d")){
						s = i;
						isS = false;
					}
					if(String.valueOf(chs[i]).matches("[%％��]")){
						e = i;
					}
				}

				return str.substring(s, e);
			}else
				return "0";
		}
		catch(Exception e){
			return "0";
		}
	}
	/**
	 * 保存SAP订单号
	 * @param sapOrderNo
	 * @param contractPurchaseId
	 */
	public void saveSAPOrderNo(String sapOrderNo, String contractPurchaseId) {
		this.purchaseContractJdbcDAO.saveSAPOrderNo(sapOrderNo,contractPurchaseId);
	}
	
	public String queryContractIdByNo(String contractNo){
		return this.purchaseContractJdbcDAO.queryContractIdByNo(contractNo);
	}
	
	public List<CommonChangeDto> queryChangeDtos(String contractId){
		return this.purchaseContractJdbcDAO.queryChangeDtos(contractId);
	}
}
