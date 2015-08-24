package com.infolion.xdss3.ageAnalysis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.xdss3.collect.domain.Collect;


@Repository
public class UnclearDetailedJdbcDao extends BaseJdbcDao {
	
	public void delAllByDate(String date){
		String sql="delete from yunclearDetailed where analysisdate='"+ date +"'";
		this.getJdbcTemplate().execute(sql);
	}
	public void delByUsername(String username){
		String sql="delete from yunclearDetailed where username='"+ username +"'";
		this.getJdbcTemplate().execute(sql);
	}
	/**
	 * 
	  * @param date 日期格式为yyyymmdd  必填
     * @param companyno 公司代码
     * @param depid 业务范围   
     * @param customerNo 供应商NO  
     * @param subjectcode 总账科目代码  必填
	 */
	public void delByCus(String date,String companyno,String depid,String customerNo,String subjectcode){
		String sql="delete from yunclearDetailed where analysisdate='"+ date +"' and subjectcode  like '"+ subjectcode+ "' ";
		if(!StringUtils.isNullBlank(companyno)){
			sql = sql + " and companyno ='"+ companyno+ "' ";
		}
		if(!StringUtils.isNullBlank(depid)){
			sql = sql + " and deptId  ='"+ depid+ "' ";
		}
//		if(!StringUtils.isNullBlank(customerNo)){
			sql = sql + " and customerno ='"+ customerNo+ "' ";
//		}
		this.getJdbcTemplate().execute(sql);
	}
	
	public String queryProjectNameByNo(String projectNo){
		String sql = "select project_name from t_project_info where project_no=?";
		List<Map<String,Object>> list =  this.getJdbcTemplate().queryForList(sql,new Object[]{projectNo});
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			return (String)map.get("project_name");
		}else
			return null;
		
	}
	
	public String queryYmatgroupByNo(String projectNo){
		String sql = "select b.wgbez from t_project_info a,ymatgroup b where a.ymat_group = b.matkl and  a.project_no=?";
		List<Map<String,Object>> list =  this.getJdbcTemplate().queryForList(sql,new Object[]{projectNo});
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			return (String)map.get("wgbez");
		}else
			return null;
		
	}	
	
	public String queryYmatgroupByNo2(String projectNo){
		String sql = "select a.ymat_group from t_project_info a where  a.project_no=?";
		List<Map<String,Object>> list =  this.getJdbcTemplate().queryForList(sql,new Object[]{projectNo});
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			return (String)map.get("ymat_group");
		}else
			return null;
		
	}	
	
	public TContractSalesInfo queryContractSalesByNo(String contractNo){
		String sql = "select * from t_contract_sales_info where contract_no='"+ contractNo + "'" ;
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		TContractSalesInfo tContractSalesInfo = new TContractSalesInfo();
		if(rowList.size()>0){
			
//			ExBeanUtils.setBeanValueFromMap(tContractSalesInfo, rowList.get(0));
			if(null != rowList.get(0).get("SAP_ORDER_NO")){
				tContractSalesInfo.setSapOrderNo(rowList.get(0).get("SAP_ORDER_NO").toString());
			}
			if(null != rowList.get(0).get("TRADE_TYPE")){
				tContractSalesInfo.setTradeType(rowList.get(0).get("TRADE_TYPE").toString());
			}
			if(null != rowList.get(0).get("YMAT_GROUP")){
				tContractSalesInfo.setYmatGroup(rowList.get(0).get("YMAT_GROUP").toString());
			}
			if(null != rowList.get(0).get("VBKD_IHREZ")){
				tContractSalesInfo.setVbkdIhrez(rowList.get(0).get("VBKD_IHREZ").toString());
			}
			if(null != rowList.get(0).get("VBAK_VTWEG")){
				tContractSalesInfo.setVbakVtweg(rowList.get(0).get("VBAK_VTWEG").toString());
			}
			if(null != rowList.get(0).get("VBAK_AUART")){
				tContractSalesInfo.setVbakAuart(rowList.get(0).get("VBAK_AUART").toString());
			}
			if(null != rowList.get(0).get("CONTRACT_NO")){
				tContractSalesInfo.setContractNo(rowList.get(0).get("CONTRACT_NO").toString());
			}
			return tContractSalesInfo;
		}
		return tContractSalesInfo;
	}
	public TContractSalesInfo queryContractSalesBySapNo(String sap_order_no){
		String sql = "select * from t_contract_sales_info where sap_order_no='"+ sap_order_no + "'" ;
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		TContractSalesInfo tContractSalesInfo = new TContractSalesInfo();
		if(rowList.size()>0){			
//			ExBeanUtils.setBeanValueFromMap(tContractSalesInfo, rowList.get(0));
			if(null != rowList.get(0).get("SAP_ORDER_NO")){
				tContractSalesInfo.setSapOrderNo(rowList.get(0).get("SAP_ORDER_NO").toString());
			}
			if(null != rowList.get(0).get("TRADE_TYPE")){
				tContractSalesInfo.setTradeType(rowList.get(0).get("TRADE_TYPE").toString());
			}
			if(null != rowList.get(0).get("YMAT_GROUP")){
				tContractSalesInfo.setYmatGroup(rowList.get(0).get("YMAT_GROUP").toString());
			}
			if(null != rowList.get(0).get("VBKD_IHREZ")){
				tContractSalesInfo.setVbkdIhrez(rowList.get(0).get("VBKD_IHREZ").toString());
			}
			if(null != rowList.get(0).get("VBAK_VTWEG")){
				tContractSalesInfo.setVbakVtweg(rowList.get(0).get("VBAK_VTWEG").toString());
			}
			if(null != rowList.get(0).get("VBAK_AUART")){
				tContractSalesInfo.setVbakAuart(rowList.get(0).get("VBAK_AUART").toString());
			}
			if(null != rowList.get(0).get("CONTRACT_NO")){
				tContractSalesInfo.setContractNo(rowList.get(0).get("CONTRACT_NO").toString());
			}
			return tContractSalesInfo;
		}
		return tContractSalesInfo;
	}
	
	public TContractPurchaseInfo queryContractPurchaseByNo(String contractNo){
		String sql = "select * from t_contract_purchase_info where contract_no='"+ contractNo + "'" ;
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		TContractPurchaseInfo tContractPurchaseInfo = new TContractPurchaseInfo();
		if(rowList.size()>0){
			
//			ExBeanUtils.setBeanValueFromMap(tContractPurchaseInfo, rowList.get(0));
			if(null != rowList.get(0).get("SAP_ORDER_NO")){
				tContractPurchaseInfo.setSapOrderNo(rowList.get(0).get("SAP_ORDER_NO").toString());
			}
			if(null != rowList.get(0).get("TRADE_TYPE")){
				tContractPurchaseInfo.setTradeType(rowList.get(0).get("TRADE_TYPE").toString());
			}
			if(null != rowList.get(0).get("YMAT_GROUP")){
				tContractPurchaseInfo.setYmatGroup(rowList.get(0).get("YMAT_GROUP").toString());
			}
			if(null != rowList.get(0).get("EKKO_IHREZ")){
				tContractPurchaseInfo.setEkkoIhrez(rowList.get(0).get("EKKO_IHREZ").toString());
			}
			if(null != rowList.get(0).get("EKKO_BSART")){
				tContractPurchaseInfo.setEkkoBsart(rowList.get(0).get("EKKO_BSART").toString());
			}
			if(null != rowList.get(0).get("CONTRACT_NO")){
				tContractPurchaseInfo.setContractNo(rowList.get(0).get("CONTRACT_NO").toString());
			}
			return tContractPurchaseInfo;
		}else{
			return tContractPurchaseInfo;
		}
	}
	public TContractPurchaseInfo queryContractPurchaseBySapNo(String sap_order_no){
		String sql = "select * from t_contract_purchase_info where sap_order_no='"+ sap_order_no + "'" ;
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		TContractPurchaseInfo tContractPurchaseInfo = new TContractPurchaseInfo();
		if(rowList.size()>0){
			
//			ExBeanUtils.setBeanValueFromMap(tContractPurchaseInfo, rowList.get(0));
			if(null != rowList.get(0).get("SAP_ORDER_NO")){
				tContractPurchaseInfo.setSapOrderNo(rowList.get(0).get("SAP_ORDER_NO").toString());
			}
			if(null != rowList.get(0).get("TRADE_TYPE")){
				tContractPurchaseInfo.setTradeType(rowList.get(0).get("TRADE_TYPE").toString());
			}
			if(null != rowList.get(0).get("YMAT_GROUP")){
				tContractPurchaseInfo.setYmatGroup(rowList.get(0).get("YMAT_GROUP").toString());
			}
			if(null != rowList.get(0).get("EKKO_IHREZ")){
				tContractPurchaseInfo.setEkkoIhrez(rowList.get(0).get("EKKO_IHREZ").toString());
			}
			if(null != rowList.get(0).get("EKKO_BSART")){
				tContractPurchaseInfo.setEkkoBsart(rowList.get(0).get("EKKO_BSART").toString());
			}
			if(null != rowList.get(0).get("CONTRACT_NO")){
				tContractPurchaseInfo.setContractNo(rowList.get(0).get("CONTRACT_NO").toString());
			}
			return tContractPurchaseInfo;
		}else{
			return tContractPurchaseInfo;
		}
	}
	
	public String getMatgroup(String id){
		if(StringUtils.isNullBlank(id))return null;
		String sql = "select a.wgbez from ymatgroup a where a.matkl = ?";
		List<Map<String,Object>> list =  this.getJdbcTemplate().queryForList(sql,new Object[]{id});
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			return (String)map.get("wgbez");
		}else
			return null;
	}
}
