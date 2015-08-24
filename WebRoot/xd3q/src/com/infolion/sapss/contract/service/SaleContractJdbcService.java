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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.sapss.common.dto.CommonChangeDto;
import com.infolion.sapss.contract.dao.SaleContractJdbcDAO;
import com.infolion.sapss.contract.domain.TContractAgentMateriel;
import com.infolion.sapss.contract.domain.TContractSalesMateriel;
import com.infolion.sapss.workflow.ProcessCallBack;
@Service
public class SaleContractJdbcService extends BaseJdbcService implements ProcessCallBack{
	@Autowired
	private SaleContractJdbcDAO saleContractJdbcDAO;


	public void setSaleContractJdbcDAO(SaleContractJdbcDAO saleContractJdbcDAO) {
		this.saleContractJdbcDAO = saleContractJdbcDAO;
	}
	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
		
	}
	/**
	 * 计算销售总金额
	 */
	public Map<String,String> sumSales(String saleId,BigDecimal rate){
		
		double money = 0;
		double tax = 0;
		double net = 0;
		double sm =0;
		double q = 0;
		//
//		double r = rate==null?0:rate.doubleValue();
		double r = 1.0;
		String tradeType = this.saleContractJdbcDAO.getSaleTradeType(saleId);
		
		
		//服务物料
		List<TContractSalesMateriel> list = saleContractJdbcDAO.findMaterial(saleId);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TContractSalesMateriel pm = (TContractSalesMateriel) iterator.next();
			if(pm.getVbapZmeng().compareTo(BigDecimal.ZERO)<=0){
				throw new BusinessException("物料行订单数量有误，请填写正确的数量！");
			}
				
			String otherFee = saleContractJdbcDAO.sumOtherFee(pm.getSalesRowsId());
			//总金额   数量*含税价
			double vbzpZmeng =  pm.getVbapZmeng()==null?0:pm.getVbapZmeng().doubleValue();
			double konvKbetr =  pm.getKonvKbetr()==null?0:pm.getKonvKbetr().doubleValue();
			double vbapKpein =  Double.valueOf(pm.getVbapKpein()==null||pm.getVbapKpein().equals("")?"1":pm.getVbapKpein());
			sm = vbzpZmeng*konvKbetr*r/vbapKpein;
			money = money + sm +Double.valueOf(otherFee);
			//总税额	
			String rtr = pm.getRowTaxesRale();
			rtr = rtr==null || "".equals(rtr)?"0":rtr;
			if("5".equals(tradeType)||"6".equals(tradeType)){
				tax = tax + sm*(Double.valueOf(rtr).doubleValue()/100);
				net = net + sm;
			}
			else {
				tax = tax + sm/(1+Double.valueOf(rtr).doubleValue()/100)*(Double.valueOf(rtr).doubleValue()/100);
				//总净额
				net = net + sm/(1+Double.valueOf(rtr).doubleValue()/100);
			}
			q +=vbzpZmeng;//数量
			
		}
		//代理进出口
		if("5".equals(tradeType)||"6".equals(tradeType)){
			q=0;
			money=0;
			//代理物料--VBAP_ZMENG(订单数量)--KONV_KBETR(含税单价)--VBAP_KPEIN(条件定价单位)
			List<TContractAgentMateriel> list1 = saleContractJdbcDAO.findAgentMaterial(saleId);
			for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
				TContractAgentMateriel am = (TContractAgentMateriel) iterator.next();
				String otherFee = saleContractJdbcDAO.sumAgentOtherFee(am.getSalesRowsId());
				String vbapKpein = am.getVbapKpein();
				vbapKpein = vbapKpein==null || "".equals(vbapKpein)?"1":vbapKpein;
				//总金额
				sm = am.getVbapZmeng().doubleValue()*am.getKonvKbetr()*saleContractJdbcDAO.getCurrencyRate(am.getAgentCurrency())/Double.valueOf(vbapKpein);
				money = money + sm +Double.valueOf(otherFee);
				//总税额
	//			tax = tax + am.getVbapZmeng().doubleValue() * Double.valueOf(am.getRowTaxes());
				//总净额
//				net = net + am.getVbapZmeng().doubleValue() * Double.valueOf(am.getRowNet());
				q+=am.getVbapZmeng().doubleValue();
			}
		}
		Map<String,String> map = new HashMap<String,String>();
		NumberFormat format = new DecimalFormat("#0.000");
		BigDecimal m = new BigDecimal(format.format(money));
		BigDecimal t = new BigDecimal(format.format(tax));
		BigDecimal n = new BigDecimal(format.format(net));
		map.put("moneyTotal",m.divide(new BigDecimal(1), 2,RoundingMode.HALF_UP).toString());
		map.put("taxTotal", t.divide(new BigDecimal(1), 2,RoundingMode.HALF_UP).toString());
		map.put("netTotal", n.divide(new BigDecimal(1), 2,RoundingMode.HALF_UP).toString());
		map.put("totalQuality", (new BigDecimal(format.format(q))).toString());
		return map;
		//根据采购ID找出所有的物料ID
		//根据物料ID找出所有的其他费用
		//计算物料单行费用
		//累加所有物料行费用
	}
	/**
	 * 保存SAP订单号
	 * @param sapOrderNo
	 */
	public void saveSAPOrderNo(String sapOrderNo,String contractId) {
		this.saleContractJdbcDAO.saveSAPOrderNo(sapOrderNo,contractId);
	}
	/**
	 * 
	 * @param businessRecordId
	 * @param workflowExamine
	 */
	public void saveToWorkflow(String businessRecordId, String workflowExamine,String taskName) {
		this.saleContractJdbcDAO.saveToWorkflow(businessRecordId,workflowExamine,taskName);
	}
	
	public String queryContractIdByNo(String contractNo){
		return this.saleContractJdbcDAO.queryContractIdByNo(contractNo);
	}
	
	public List<CommonChangeDto> queryChangeDtos(String contractId){
		return this.saleContractJdbcDAO.queryChangeDtos(contractId);
	}
}
