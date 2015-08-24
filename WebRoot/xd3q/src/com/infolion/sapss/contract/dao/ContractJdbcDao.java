/*
 * @(#)ContractJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2009-2-6
 *  描　述：创建
 */

package com.infolion.sapss.contract.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.Constants;
import com.infolion.sapss.bapi.UpdataSapData;
import com.infolion.sapss.contract.domain.TContractGroup;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.project.domain.TProjectInfo;
import com.infolion.sapss.workflow.ProcessCallBack;

@Repository
public class ContractJdbcDao extends BaseDao {
	
	/**
	 * 增加合同组信息
	 * @param tContractGroup
	 */
	public void addContractGroup(TContractGroup tContractGroup){
		String strSql ="insert into t_contract_group " +
				  "(contract_group_id, " +
				   "project_id, " +
				   "contract_group_name, " +
				   "contract_group_no, " +
				   "project_name, " +
				   "cmd, " +
				   "is_available, " +
				   "dept_id, " +
				   "create_time, " +
				   "creator," +
				   "trade_type) " +
				"values(?,?,?,?,?,?,'1',?,?,?,?)";
		
		
		Object[] params = new Object[]{
				tContractGroup.getContractGroupId(),
				tContractGroup.getProjectId(),
				tContractGroup.getContractGroupName(),
				tContractGroup.getContractGroupNo(),
				tContractGroup.getProjectName(),
				tContractGroup.getCmd(),
				tContractGroup.getDeptId(),
				tContractGroup.getCreateTime(),
				tContractGroup.getCreator(),
				tContractGroup.getTradeType()
		};
		
		getJdbcTemplate().update(strSql,params);
	}
	
	/**
	 * 删除指定的合同组信息
	 * @param strContractGroupId
	 * @return
	 * 1:表示删除成功
	 * ２:表示还有下挂采购合同还是销售合同
	 */
	public int deleteContractGroup(String strContractGroupId){
		int returnFalg = 1;
		String strSelectSql = "select count(z.contract_group_id) " +
						  "from (select a.contract_group_id " +
						          "from t_contract_purchase_info a " +
						         "where a.contract_group_id = ? " +
						           "and a.is_available = '1' " +
						        "union " +
						        "select a.contract_group_id " +
						          "from t_contract_sales_info a " +
						         "where a.contract_group_id = ? " +
						           "and a.is_available = '1') z";
		
		String strUpdateSql = "update t_contract_group " +
							   "set is_available = '0' " +
							 "where contract_group_id = ?";
		
		if (getJdbcTemplate().queryForInt(strSelectSql, new Object[]{strContractGroupId,strContractGroupId}) > 0){
			returnFalg = 2;
		}else{
			getJdbcTemplate().update(strUpdateSql,new Object[]{strContractGroupId});
			
			returnFalg = 1;
		}
		
		return returnFalg;
	}
	
	/**
	 * 根据项目编号得到该项目编号下的合同组个数
	 * @param strProjectId
	 * @return
	 */
	public String getContractGroupCount(String strProjectId){
		final List<String> returnFalg = new ArrayList();
		String strSql = "select lpad(count(a.project_id) + 1,(case when count(a.project_id) > 998 then 4 else 3 end),'0') string_count " +
						  "from t_contract_group a " +
						 "where a.project_id = ?";
		getJdbcTemplate().query(strSql, new Object[]{strProjectId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				returnFalg.add(rs.getString("string_count"));
			}
		});

		return returnFalg.get(0);
	}

	/**
	 * 得到合同编码信息
	 * @param strContractGroupId
	 * @param strContractType
	 * 		1:外贸合作进口业务
			2:外贸合作出口业务
			3:外贸自营进口业务
			4:外贸自营出口业务
			5:外贸代理出口业务
			6:外贸代理进口业务
			7:内贸业务
			8:进料加工业务
			9:自营进口敞口业务
			10：内贸敞口
	 * @param strActionType
	 * 		1:采购
	 * 		2:销售
	 * @return
	 */
	public String getContractNo(
			String strContractGroupId,
			String strContractGroupNo,
			String strContractType,
			String strActionType,
			String strType){
		String strOperType = "";
		String strContractNo ="";
		String strSql = "";
		switch(Integer.parseInt(strContractType)){
		case 1:
			if (Integer.parseInt(strActionType) == 1){
				strOperType = "I";
				strSql ="select count(a.contract_group_id) + 1 " +
						  "from t_contract_purchase_info a " +
						 "where a.contract_group_id = ? " +
						        " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
			}
			if (Integer.parseInt(strActionType) == 2){
				strOperType = "S";
				strSql = "select count(a.contract_group_id) + 1 " +
						  "from t_contract_sales_info a " +
						 "where a.contract_group_id = ? " +
						        " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
			}
			break;
		case 2:
			if (Integer.parseInt(strActionType) == 1){
				strOperType = "P";
				strSql ="select count(a.contract_group_id) + 1 " +
				  "from t_contract_purchase_info a " +
				 "where a.contract_group_id = ? " +
			            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
			}
			if (Integer.parseInt(strActionType) == 2){
				strOperType = "E";
				strSql = "select count(a.contract_group_id) + 1 " +
				  "from t_contract_sales_info a " +
				 "where a.contract_group_id = ? " +
			            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
			}
			break;
		case 3:
			if (Integer.parseInt(strActionType) == 1){
				strOperType = "I";
				strSql ="select count(a.contract_group_id) + 1 " +
				  "from t_contract_purchase_info a " +
				 "where a.contract_group_id = ? " +
		            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";				 
			}
			if (Integer.parseInt(strActionType) == 2){
				strOperType = "S";
				strSql = "select count(a.contract_group_id) + 1 " +
				  "from t_contract_sales_info a " +
				 "where a.contract_group_id = ? " +
		            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
			}
			break;
		case 4:
			if (Integer.parseInt(strActionType) == 1){
				strOperType = "P";
				strSql ="select count(a.contract_group_id) + 1 " +
				  "from t_contract_purchase_info a " +
				 "where a.contract_group_id = ? " +
		            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
			}
			if (Integer.parseInt(strActionType) == 2){
				strOperType = "E";
				strSql = "select count(a.contract_group_id) + 1 " +
				  "from t_contract_sales_info a " +
				 "where a.contract_group_id = ? "+
		            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
			}
			break;
		case 5:
			if (Integer.parseInt(strActionType) == 1){
				strOperType = "P";
				strSql ="select count(a.contract_group_id) + 1 " +
				  "from t_contract_purchase_info a " +
				 "where a.contract_group_id = ? " +
		            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
			}
			if (Integer.parseInt(strActionType) == 2){
				strOperType = "E";
				strSql = "select count(a.contract_group_id) + 1 " +
				  "from t_contract_sales_info a " +
				 "where a.contract_group_id = ? " +
		            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
			}
			break;
		case 6:
			if (Integer.parseInt(strActionType) == 1){
				strOperType = "I";
				strSql ="select count(a.contract_group_id) + 1 " +
				  "from t_contract_purchase_info a " +
				 "where a.contract_group_id = ? " +
		            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
			}
			if (Integer.parseInt(strActionType) == 2){
				strOperType = "S";
				strSql = "select count(a.contract_group_id) + 1 " +
				  "from t_contract_sales_info a " +
				 "where a.contract_group_id = ? " +
		            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
			}
			break;
		case 7:
			if (Integer.parseInt(strActionType) == 1){
				strOperType = "P";
				strSql ="select count(a.contract_group_id) + 1 " +
				  "from t_contract_purchase_info a " +
				 "where a.contract_group_id = ? " +
		            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
			}
			if (Integer.parseInt(strActionType) == 2){
				strOperType = "S";
				strSql = "select count(a.contract_group_id) + 1 " +
				  "from t_contract_sales_info a " +
				 "where a.contract_group_id = ? " +
		            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
			}
			break;
		case 8:
			if (Integer.parseInt(strActionType) == 1){
				strOperType = strType;
				strSql ="select count(a.contract_group_id) + 1 " +
				  "from t_contract_purchase_info a " +
				 "where a.contract_group_id = ? " +
		            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
			}
			if (Integer.parseInt(strActionType) == 2){
				strOperType = strType;
				strSql = "select count(a.contract_group_id) + 1 " +
				  "from t_contract_sales_info a " +
				 "where a.contract_group_id = ? " +
		            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
			}
			break;
		case 9:
			if (Integer.parseInt(strActionType) == 1){
				strOperType = "I";
				strSql ="select count(a.contract_group_id) + 1 " +
				  "from t_contract_purchase_info a " +
				 "where a.contract_group_id = ? " +
		            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";				 
			}
			if (Integer.parseInt(strActionType) == 2){
				strOperType = "S";
				strSql = "select count(a.contract_group_id) + 1 " +
				  "from t_contract_sales_info a " +
				 "where a.contract_group_id = ? " +
		            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
			}
			break;
		 case 10:
			if (Integer.parseInt(strActionType) == 1){
				strOperType = "P";
				strSql ="select count(a.contract_group_id) + 1 " +
				  "from t_contract_purchase_info a " +
				 "where a.contract_group_id = ? " +
		            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
			}
			if (Integer.parseInt(strActionType) == 2){
				strOperType = "S";
				strSql = "select count(a.contract_group_id) + 1 " +
				  "from t_contract_sales_info a " +
				 "where a.contract_group_id = ? " +
		            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
			}
			break;
		 case 11:
				if (Integer.parseInt(strActionType) == 1){
					strOperType = "I";
					strSql ="select count(a.contract_group_id) + 1 " +
					  "from t_contract_purchase_info a " +
					 "where a.contract_group_id = ? " +
			            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";				 
				}
				if (Integer.parseInt(strActionType) == 2){
					strOperType = "E";
					strSql = "select count(a.contract_group_id) + 1 " +
					  "from t_contract_sales_info a " +
					 "where a.contract_group_id = ? "+
			            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
				}
				break;
		 case 12:
				if (Integer.parseInt(strActionType) == 1){
					strOperType = strType;
					strSql ="select count(a.contract_group_id) + 1 " +
					  "from t_contract_purchase_info a " +
					 "where a.contract_group_id = ? " +
			            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
				}
				if (Integer.parseInt(strActionType) == 2){
					strOperType = strType;
					strSql = "select count(a.contract_group_id) + 1 " +
					  "from t_contract_sales_info a " +
					 "where a.contract_group_id = ? " +
			            " and substr(a.contract_no,6,10) like '%"+strOperType+"%' ";
				}
				break;
		}
		
		int iReturnCount = getJdbcTemplate().queryForInt(strSql, new Object[]{strContractGroupId});
		
		strContractNo = strContractGroupNo + strOperType + Integer.toString(iReturnCount);
		
		return strContractNo;
	}
	
	/**
	 * 过合同组ID查询合同组名称
	 * @param strGroupId
	 * @return
	 */
	public String queryContractGroupInfoByPurchaseId(String strGroupId){
		final List<String> returnFalg = new ArrayList();
		String strSql = "select b.contract_group_name " +
					  "from t_contract_purchase_info a,t_contract_group b " +
					 "where a.contract_purchase_id = ? " +
					   "and a.is_available = '1' " +
					   "and a.contract_group_id = b.contract_group_id " +
					   "and b.is_available = '1'";

		getJdbcTemplate().query(strSql, new Object[]{strGroupId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				returnFalg.add(rs.getString("contract_group_name"));
			}
		});
		
		if (returnFalg.size() == 0)
			return "";
		else
			return returnFalg.get(0);
	}
	
	public TContractGroup queryContractGroupByPurchaseId(String strGroupId){
		final TContractGroup group = new TContractGroup();
		String strSql = "select b.contract_group_name,b.contract_group_no " +
					  "from t_contract_purchase_info a,t_contract_group b " +
					 "where a.contract_purchase_id = ? " +
					   "and a.contract_group_id = b.contract_group_id " ;

		getJdbcTemplate().query(strSql, new Object[]{strGroupId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				group.setContractGroupName(rs.getString("contract_group_name"));
				group.setContractGroupNo(rs.getString("contract_group_no"));
			}
		});
		
		return group;
	}
	
	/**
	 * 得到合同的物料行sap编码
	 * @return
	 */
	public String getSapRowNo(String strContractType,String strContractPurchaseId){
		final List<String> returnFalg = new ArrayList();
		String strSql = "";
		
		if (strContractType.equals("1")){
			strSql = "select lpad(nvl(max(a.sap_row_no) + 10,2),6,'0') sap_row_no " +
			  "from t_contract_purchase_materiel a " +
				 "where a.contract_purchase_id = ?";
		}else{
			strSql = "select lpad(nvl(max(a.sap_row_no) + 10,2),6,'0') sap_row_no " +
			  "from t_contract_sales_materiel a " +
				 "where a.contract_sales_id = ?";
		}

		getJdbcTemplate().query(strSql, new Object[]{strContractPurchaseId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				returnFalg.add(rs.getString("sap_row_no"));
			}
		});
		
		if (returnFalg.size() == 0)
			return "";
		else
			return returnFalg.get(0);
	}
	
	/**
	 * 保存采购合同抬头信息
	 * @param tContractPurchaseInfo
	 */
	public int updatePurchaseContract(TContractPurchaseInfo tContractPurchaseInfo){
		if (tContractPurchaseInfo.getEkkoInco1() == null)
		{
			tContractPurchaseInfo.setEkkoInco1("");
		}
		if (tContractPurchaseInfo.getEkkoInco2() == null)
		{
			tContractPurchaseInfo.setEkkoInco2("");
		}
		if (tContractPurchaseInfo.getDestinationPort() == null){
			tContractPurchaseInfo.setDestinationPort("");
		}
		
		
		String strSql = "update t_contract_purchase_info a " +
						   "set a.contract_name = ?, " +
						   "a.ekko_bedat = ?, " +
						   "a.ekko_waers = ?, " +
						   "a.ekko_telf1 = ?, " +
						   "a.ekko_zterm = ?, " +
						   "a.ekko_lifnr = ?, " +
						   "a.ekko_lifnr_name = ?, " +
						   "a.ekko_unsez = ?, " +
						   "a.ekko_inco1 = ?, " +
						   "a.ekko_inco2 = ?, " +
						   "a.ekko_wkurs = ?, " +
						   "a.invoicing_party = ?, " +
						   "a.invoicing_party_name = ?, " +
						   "a.payer = ?, " +
						   "a.payer_name = ?, " +
						   "a.shipment_port = ?, " +
						   "a.destination_port = ?," +
						   "a.shipment_date = ?, " +
						   "a.ekko_ekgrp = ?, " +
						   "a.total = ?, " +
						   "a.total_taxes = ?, " +
						   "a.total_amount = ?, " +
						   "a.mask = ?, " +
						   "a.old_contract_no = ? ," +
						   "a.pay_type = ?, " +
						   "a.collection_date = ?," +
						   "a.later_date = ? ," + 
						   "a.ymat_Group = ? ," + 
						   "a.total_Quality = ? " + 
						 "where a.contract_purchase_id = ?";
		Object[] params = new Object[]{
				tContractPurchaseInfo.getContractName(),
				tContractPurchaseInfo.getEkkoBedat(),
				tContractPurchaseInfo.getEkkoWaers(),
				tContractPurchaseInfo.getEkkoTelf1(),
				tContractPurchaseInfo.getEkkoZterm(),
				tContractPurchaseInfo.getEkkoLifnr(),
				tContractPurchaseInfo.getEkkoLifnrName(),
				tContractPurchaseInfo.getEkkoUnsez(),
				tContractPurchaseInfo.getEkkoInco1(),
				tContractPurchaseInfo.getEkkoInco2(),
				tContractPurchaseInfo.getEkkoWkurs(),
				tContractPurchaseInfo.getInvoicingParty(),
				tContractPurchaseInfo.getInvoicingPartyName(),
				tContractPurchaseInfo.getPayer(),
				tContractPurchaseInfo.getPayerName(),
				tContractPurchaseInfo.getShipmentPort(),
				tContractPurchaseInfo.getDestinationPort(),
				tContractPurchaseInfo.getShipmentDate(),
				tContractPurchaseInfo.getEkkoEkgrp(),
				tContractPurchaseInfo.getTotal(),
				tContractPurchaseInfo.getTotalTaxes(),
				tContractPurchaseInfo.getTotalAmount(),
				tContractPurchaseInfo.getMask(),
				tContractPurchaseInfo.getOldContractNo(),
				tContractPurchaseInfo.getPayType(),
				tContractPurchaseInfo.getCollectionDate(),
				tContractPurchaseInfo.getLaterDate(),
				tContractPurchaseInfo.getYmatGroup(),
				tContractPurchaseInfo.getTotalQuality(),
				tContractPurchaseInfo.getContractPurchaseId()
		};
		return getJdbcTemplate().update(strSql,params);
		
		
		/*String strSql = "update t_contract_purchase_info a " +
		   "set a.contract_name = '"+tContractPurchaseInfo.getContractName()+"', " +
		   "a.ekko_bedat = '"+tContractPurchaseInfo.getEkkoBedat()+"', " +
		   "a.ekko_waers = '"+tContractPurchaseInfo.getEkkoWaers()+"', " +
		   "a.ekko_telf1 = '"+tContractPurchaseInfo.getEkkoTelf1()+"', " +
		   "a.ekko_zterm = '"+tContractPurchaseInfo.getEkkoZterm()+"', " +
		   "a.ekko_lifnr = '"+tContractPurchaseInfo.getEkkoLifnr()+"', " +
		   "a.ekko_lifnr_name = '"+tContractPurchaseInfo.getEkkoLifnrName()+"', " +
		   "a.ekko_unsez = '"+tContractPurchaseInfo.getEkkoUnsez()+"', " +
		   "a.ekko_inco1 = '"+tContractPurchaseInfo.getEkkoInco1()+"', " +
		   "a.ekko_inco2 = '"+tContractPurchaseInfo.getEkkoInco2()+"', " +
		   "a.ekko_wkurs = "+tContractPurchaseInfo.getEkkoWkurs()+", " +
		   "a.invoicing_party = '"+tContractPurchaseInfo.getInvoicingParty()+"', " +
		   "a.invoicing_party_name = '"+tContractPurchaseInfo.getInvoicingPartyName()+"', " +
		   "a.payer = '"+tContractPurchaseInfo.getPayer()+"', " +
		   "a.payer_name = '"+tContractPurchaseInfo.getPayerName()+"', " +
		   "a.shipment_port = '"+tContractPurchaseInfo.getShipmentPort()+"', " +
		   "a.destination_port = '"+tContractPurchaseInfo.getDestinationPort()+"'," +
		   "a.shipment_date = '"+tContractPurchaseInfo.getShipmentDate()+"', " +
		   "a.ekko_ekgrp = '"+tContractPurchaseInfo.getEkkoEkgrp()+"', " +
		   "a.total = '"+tContractPurchaseInfo.getTotal()+"', " +
		   "a.total_taxes = '"+tContractPurchaseInfo.getTotalTaxes()+"', " +
		   "a.total_amount = '"+tContractPurchaseInfo.getTotalAmount()+"', " +
		   "a.mask = '"+tContractPurchaseInfo.getMask()+"', " +
		   "a.old_contract_no = '"+tContractPurchaseInfo.getOldContractNo()+"' ," +
		   "a.pay_type = '"+tContractPurchaseInfo.getPayType()+"', " +
		   "a.collection_date = '"+tContractPurchaseInfo.getCollectionDate()+"'," +
		   "a.later_date = '"+tContractPurchaseInfo.getLaterDate()+"'" + 
		 "where a.contract_purchase_id = '"+tContractPurchaseInfo.getContractPurchaseId()+"'";
		
		System.out.println(strSql);
		return getJdbcTemplate().update(strSql);*/
	}
	
	/**
	 * 修改采购合同的申请时间
	 * @param strContractPurchaseId
	 */
	public void updatePurchaseApplyTime(String strContractPurchaseId){
		String strSql = "update t_contract_purchase_info a " +
			   "set a.apply_time = to_char(sysdate, 'yyyy-mm-dd'), " +
			   "a.order_state = '2' " +
			 "where a.contract_purchase_id = ? and a.apply_time is null";

		getJdbcTemplate().update(strSql,new Object[]{strContractPurchaseId});
	}
	
	/**
	 * 修改销售合同的申请时间
	 * @param strContractPurchaseId
	 */
	public void updateSalesApplyTime(String strContractSalesId){
		String strSql = "update t_contract_purchase_info a " +
			   "set a.apply_time = to_char(sysdate, 'yyyy-mm-dd') " +
			 "where a.contract_purchase_id = ?";

		getJdbcTemplate().update(strSql,new Object[]{strContractSalesId});
	}
	
	
	/**
	 * 修改采购合同的审批通过时间,审批状态
	 * @param strContractPurchaseId
	 * 1:新增未提交
	 * 2:提交审批中
	 * 3:审批通过
	 * 4:审批不通过
	 */
	public void updatePurchaseWorkflowResult(String strContractPurchaseId,
											String strResult,
											String strSapOrderNo){
		String strSql = "update t_contract_purchase_info a " +
			   "set a.approved_time = to_char(sysdate, 'yyyy-mm-dd'), ";
			 
		
		if (ProcessCallBack.EXAMINE_SUCCESSFUL.equals(strResult)){
			strSql = strSql + "  a.order_state = '3'";
			//strSql = strSql + "  a.sap_order_no = '" + strSapOrderNo + "'";
		}
		else if (ProcessCallBack.NO_EXAMINE.equals(strResult)){
			strSql = strSql + " a.order_state = '5'";
			//strSql = strSql + " a.sap_order_no = '" + strSapOrderNo + "'";
		}		
		else
			strSql = strSql + " a.order_state = '4'";
		strSql = strSql +" where a.contract_purchase_id = ?";
		
		getJdbcTemplate().update(strSql,new Object[]{strContractPurchaseId});
	}
	
	/**
	 * 修改销售合同的审批通过时间
	 * @param strContractPurchaseId
	 * 1:新增未提交
	 * 2:提交审批中
	 * 3:审批通过
	 * 4:审批不通过
	 */
	public void updateSalesWorkflowResult(String strContractSalesId,
											String strResult,
											String strSapOrderNo){
		String strSql = "update t_contract_sales_info a " +
			   "set a.approved_time = to_char(sysdate, 'yyyy-mm-dd') ," ;
		
		if (ProcessCallBack.EXAMINE_SUCCESSFUL.equals(strResult)){
			strSql = strSql + "  a.order_state = '3'";
			//strSql = strSql + "  a.sap_order_no = '" + strSapOrderNo + "'";
		}
		else if (ProcessCallBack.NO_EXAMINE.equals(strResult)){
			strSql = strSql + "  a.order_state = '5'";
			//strSql = strSql + "  a.sap_order_no = '" + strSapOrderNo + "'";
		}		
		else
			strSql = strSql + "  a.order_state = '4'";
		strSql = strSql + " where a.contract_sales_id = ?";
		getJdbcTemplate().update(strSql,new Object[]{strContractSalesId});
	}
	
	/**
	 * 修改销售合同的审批通过时间
	 * @param strContractPurchaseId
	 */
	public void updateSalesApprovedTime(String strContractSalesId){
		String strSql = "update t_contract_sales_info a " +
			   "set a.approved_time = to_char(sysdate, 'yyyy-mm-dd') " +
			 "where a.contract_purchase_id = ?";

		getJdbcTemplate().update(strSql,new Object[]{strContractSalesId});
	}	
	
	/**
	 * 删除采购合同信息
	 * @param strContractPurchaseId
	 * @return
	 */
	public int deletePurchaseContract(String strContractPurchaseId){
		int returnFlag = 1;
		String delBomSql = "delete t_contract_bom a " +
					 "where a.purchase_rows_id in " +
					       "(select a.purchase_rows_id " +
					          "from t_contract_purchase_materiel a " +
					         "where a.contract_purchase_id = ?)";

		String delCaseSql = "delete t_contract_pu_materiel_case a " +
						 "where a.purchase_rows_id in " +
						       "(select a.purchase_rows_id " +
						          "from t_contract_purchase_materiel a " +
						         "where a.contract_purchase_id = ?)";
		String delMateriel ="delete t_contract_purchase_materiel a where a.contract_purchase_id = ?";
		
		String delPurchase = "update t_contract_purchase_info a " +
				   "set a.is_available = '0' " +
				 "where a.contract_purchase_id = ?";
		
		getJdbcTemplate().update(delBomSql,new Object[]{strContractPurchaseId});
		
		getJdbcTemplate().update(delCaseSql,new Object[]{strContractPurchaseId});
		
		getJdbcTemplate().update(delMateriel,new Object[]{strContractPurchaseId});
		
		getJdbcTemplate().update(delPurchase,new Object[]{strContractPurchaseId});
		
		return returnFlag;
	}
	
	/**
	 * 删除采购合同物料信息
	 * @param strPurchaseRowsId
	 * @return
	 */
	public void deletePurchaseMaterielInfo(String strPurchaseRowsId){
		String delBomSql = "delete t_contract_bom a " +
		 "where a.purchase_rows_id = ?";

		String delCaseSql = "delete t_contract_pu_materiel_case a " +
			 "where a.purchase_rows_id = ?";
		
		String delMateriel ="delete t_contract_purchase_materiel a where a.purchase_rows_id = ?";
		
		getJdbcTemplate().update(delBomSql,new Object[]{strPurchaseRowsId});
		
		getJdbcTemplate().update(delCaseSql,new Object[]{strPurchaseRowsId});
		
		getJdbcTemplate().update(delMateriel,new Object[]{strPurchaseRowsId});
	}
	
	/**
	 * 删除采购合同的物料合他费用信息
	 * @param strMaterielCaseId
	 * @return
	 */
	public void deletePurchaseMaterielCase(String strMaterielCaseId){
		String delCaseSql = "delete t_contract_pu_materiel_case a where a.materiel_case_id = ?";
		
		getJdbcTemplate().update(delCaseSql,new Object[]{strMaterielCaseId});
	}
	
	/**
	 * 删除采购的BON信息
	 * @param strBomId
	 * @return
	 */
	public void deletePurchaseBom(String strBomId){
		String delBomSql = "delete t_contract_bom a where a.bom_id = ?";
		
		getJdbcTemplate().update(delBomSql,new Object[]{strBomId});
	}
	
	/**
	 * 根据项目编号查询项目信息
	 * @param strProjectId
	 * @return
	 */
	public TProjectInfo queryProjectInfo(String strProjectId){
		final TProjectInfo tProjectInfo = new TProjectInfo();
		
		String strSql = "select a.* " +
				  "from t_project_info a " +
				 "where a.project_id = ? " +
				   "and a.is_available = '1'";
		getJdbcTemplate().query(strSql, new Object[]{strProjectId},new RowCallbackHandler(){
		public void processRow(ResultSet rs) throws SQLException{
			tProjectInfo.setShipmentPort(rs.getString("shipment_port"));
			tProjectInfo.setShipmentDate(rs.getString("shipment_date"));
			tProjectInfo.setDestinationPort(rs.getString("destination_port"));
			tProjectInfo.setAvailableDataEnd(rs.getString("Available_Data_End"));
		}
		});
		
		return tProjectInfo;
	}
	
	/**
	 * 修改采购合同的物料信息的其他费用
	 * @param strMaterielCaseId
	 * @param strColName
	 * @param strColValue
	 */
	public void updateMaterielCase(String strMaterielCaseId,
			String strColName,
			String strColValue){
		String strSql =" update t_contract_pu_materiel_case " +
			"set " + strColName + "='" + strColValue + 
			"' where materiel_case_id = ?";
		
		getJdbcTemplate().update(strSql,new Object[]{strMaterielCaseId});
	}
	
	/**
	 * 修改采购合同的物料BOM信息
	 * @param strMaterielCaseId
	 * @param strColName
	 * @param strColValue
	 */
	public void updateBomInfo(String strBomId,
			String strColName,
			String strColValue){
		String strSql =" update t_contract_bom " +
			"set " + strColName + "='" + strColValue + 
			"' where bom_id = ?";
		
		getJdbcTemplate().update(strSql,new Object[]{strBomId});
	}
	
	/**
	 * 修改采购合同的物料信息
	 * @param strMaterielCaseId
	 * @param strColName
	 * @param strColValue
	 */
	public void updatePurchaseMaterielInfo(String strPurchaseRowsId,
			String strColName,
			String strColValue){
		String strSql =" update t_contract_purchase_materiel " +
			"set " + strColName + "='" + strColValue + 
			"' where purchase_rows_id = ?";
		
		getJdbcTemplate().update(strSql,new Object[]{strPurchaseRowsId});
	}
	
	/**
	 * 修改销售合同的物料其他费用
	 * @param strMaterielCaseId
	 * @param strColName
	 * @param strColValue
	 */
	public void updateSalesMaterielCaseInfo(String strMaterielCaseId,
			String strColName,
			String strColValue){
		String strSql =" update t_contract_se_materiel_case " +
			"set " + strColName + "='" + strColValue + 
			"' where materiel_case_id = ?";
		
		getJdbcTemplate().update(strSql,new Object[]{strMaterielCaseId});
	}
	
	/**
	 * 修改代理物料其他费用
	 * @param strMaterielCaseId
	 * @param strColName
	 * @param strColValue
	 */
	public void updateAgentMtCaseInfo(String strMaterielCaseId,
			String strColName,
			String strColValue){
		String strSql =" update t_contract_agent_mt_case " +
			"set " + strColName + "='" + strColValue + 
			"' where materiel_case_id = ?";
		
		getJdbcTemplate().update(strSql,new Object[]{strMaterielCaseId});
	}	
	
	/**
	 * 删除销售合同的物料合他费用信息
	 * @param strMaterielCaseId
	 * @return
	 */
	public void deleteSalesMaterielCase(String strMaterielCaseId){
		String delCaseSql = "delete t_contract_se_materiel_case a where a.materiel_case_id = ?";
		
		getJdbcTemplate().update(delCaseSql,new Object[]{strMaterielCaseId});
	}
	
	/**
	 * 删除销售合同的物料信息
	 * @param strSalesRowsId
	 */
	public void deleteSalesMateriel(String strSalesRowsId){
		String delCaseSql = "delete t_contract_sales_materiel a where a.sales_rows_id = ?";
		
		getJdbcTemplate().update(delCaseSql,new Object[]{strSalesRowsId});
	}
	
	/**
	 * 修改销售合同的物料信息
	 * @param strMaterielCaseId
	 * @param strColName
	 * @param strColValue
	 */
	public void updateSalesMateriel(String strSalesRowsId,
			String strColName,
			String strColValue){
		String strSql =" update t_contract_sales_materiel " +
			"set " + strColName + "='" + strColValue + 
			"' where sales_rows_id = ?";
		
		getJdbcTemplate().update(strSql,new Object[]{strSalesRowsId});
	}
	
	/**
	 * 修改销售合同的代理物料信息
	 * @param strMaterielCaseId
	 * @param strColName
	 * @param strColValue
	 */
	public void updateAgentMateriel(String strSalesRowsId,
			String strColName,
			String strColValue){
		String strSql =" update t_contract_agent_materiel " +
			"set " + strColName + "='" + strColValue + 
			"' where sales_rows_id = ?";
		
		getJdbcTemplate().update(strSql,new Object[]{strSalesRowsId});
	}	
	
	/**
	 * 删除销售合同信息
	 * @param strContractSalesId
	 */
	public void deleteSalesContract(String strContractSalesId){
		//物料
		String strDelMaterielCase ="delete t_contract_se_materiel_case a " +
				 "where a.sales_rows_id in " +
				       "(select a.sales_rows_id " +
				          "from t_contract_sales_materiel a "+
				         "where a.contract_sales_id = ?)";

		String strDelMateriel = "delete t_contract_sales_materiel a where a.contract_sales_id = ?";
		//代理物料		
		String strDelAgentMaterielCase ="delete t_contract_agent_mt_case a " +
		 "where a.sales_rows_id in " +
		       "(select a.sales_rows_id " +
		          "from t_contract_agent_materiel a "+
		         "where a.contract_sales_id = ?)";
		String strDelAgentMateriel = "delete t_contract_agent_materiel a where a.contract_sales_id = ?";		
		//销售合同
		String strDelContract = "update t_contract_sales_info a set a.is_available = '0' where a.contract_sales_id = ?";
		
		getJdbcTemplate().update(strDelMaterielCase,new Object[]{strContractSalesId});
		getJdbcTemplate().update(strDelMateriel,new Object[]{strContractSalesId});
		getJdbcTemplate().update(strDelAgentMaterielCase,new Object[]{strContractSalesId});
		getJdbcTemplate().update(strDelAgentMateriel,new Object[]{strContractSalesId});
		getJdbcTemplate().update(strDelContract,new Object[]{strContractSalesId});
	}
	
	/**
	 * 修改销售合同信息
	 * @param tContractSalesInfo
	 */
	/*
	public void updateContractSalesInfo(TContractSalesInfo tContractSalesInfo){
		String StrSql ="update t_contract_sales_info a " +
				 "set a.contract_name = ?, " +
				 "a.kuagv_kunnr = ?, " +
				 "a.kuagv_kunnr_name = ?, " +
				 "a.kuwev_kunnr = ?, " +
				 "a.kuwev_kunnr_name = ?, " +
				 "a.kuagv_kunnr2 = ?, " +
				 "a.kuagv_kunnr_name2 = ?, " +
				 "a.kuwev_kunnr2 = ?, " +
				 "a.kuwev_kunnr_name2 = ?, " +
				 "a.vbak_audat = ?, " +
				 "a.vbak_waerk = ?, " +
				 "a.vbkd_inco1 = ?, " +
				 "a.vbkd_inco2 = ?, " +
				 "a.vbkd_zterm = ?, " +
				 "a.vbkd_kurrf = ?, " +
				 "a.vbkd_ihrez = ?, " +
				 "a.vbkd_bstkd_e = ?, " +
				 "a.vbkd_bzirk = ?, " +
				 "a.shipment_port = ?, " +
				 "a.destination_port = ?, " +
				 "a.shipment_date = ?, " +
				 "a.order_total = ?, " +
				 "a.order_net = ?, " +
				 "a.order_taxes = ? " +	
				 "where a.contract_sales_id = ? " +
				   "and a.is_available = '1'";
		
		Object[] params = new Object[]{
				tContractSalesInfo.getContractName(),
				tContractSalesInfo.getKuagvKunnr(),
				tContractSalesInfo.getKuagvKunnrName(),
				tContractSalesInfo.getKuwevKunnr(),
				tContractSalesInfo.getKuwevKunnrName(),
				tContractSalesInfo.getKuagvKunnr2(),
				tContractSalesInfo.getKuagvKunnrName2(),
				tContractSalesInfo.getKuwevKunnr2(),
				tContractSalesInfo.getKuwevKunnrName2(),
				tContractSalesInfo.getVbakAudat(),
				tContractSalesInfo.getVbakWaerk(),
				tContractSalesInfo.getVbkdInco1(),
				tContractSalesInfo.getVbkdInco2(),
				tContractSalesInfo.getVbkdZterm(),
				tContractSalesInfo.getVbkdKurrf(),
				tContractSalesInfo.getVbkdIhrez(),
				tContractSalesInfo.getVbkdBstkdE(),
				tContractSalesInfo.getVbkdBzirk(),
				tContractSalesInfo.getShipmentPort(),
				tContractSalesInfo.getDestinationPort(),
				tContractSalesInfo.getShipmentDate(),
				tContractSalesInfo.getOrderTotal(),
				tContractSalesInfo.getOrderNet(),
				tContractSalesInfo.getOrderTaxes(),
				tContractSalesInfo.getContractSalesId()
		};
		
		getJdbcTemplate().update(StrSql,params);
	}
	*/
	
	/**
	 * 修改销售合同的状态
	 * @param strContractPurchaseId
	 */
	public int updateSalesOrderState(String strContractSalesId, String strOrderState){
		String strSql = "update t_contract_sales_info a " +
			   "set a.order_state = ? " +
			 "where a.contract_sales_id = ?";

		return getJdbcTemplate().update(strSql,new Object[]{strOrderState,strContractSalesId});
	}
	
	
	/**
	 * 修改采购合同的状态
	 * @param strContractPurchaseId
	 */
	public void updatePurchaserOrderState(String strContractPrichaserId, String strOrderState){
		String strSql = "update t_contract_purchase_info a " +
			   "set a.order_state = ? " +
			 "where a.contract_purchase_id = ?";

		getJdbcTemplate().update(strSql,new Object[]{strOrderState, strContractPrichaserId});
	}
	
	/**
	 * 计算采购合同的总金额
	 * @param strContractPurchaserId
	 * @return
	 */
	public String sumTotal(String strContractPurchaserId,Double lWhurs){
		String strResult = "";

		String strSumSql = "select sum(a.row_sum) * ? " +
				  "from (select nvl(a.ekpo_menge,0) * nvl(a.ekpo_netpr,0) * nvl(a.ekpo_peinh,0) row_sum " +
				          "from t_contract_purchase_materiel a " +
				         "where a.contract_purchase_id = ?) a";
		
		Long lSum = getJdbcTemplate().queryForLong(strSumSql, new Object[]{lWhurs,strContractPurchaserId});
		
		strResult = lSum.toString();
		
		return strResult;
	}
	
	/**
	 * 修改采合同中物料信息中的价格单位和抬头中的货币码为一致
	 * @param strContractPurchaserId
	 * @param strPeinh
	 */
	public void updatePurchaserMaterielPeinh(String strContractPurchaserId,String strPeinh){
		String strSql = "update t_contract_purchase_materiel a " +
					   "set a.ekpo_peinh = ? " +
					 "where a.contract_purchase_id = ?";
		
		getJdbcTemplate().update(strSql, new Object[]{strPeinh,strContractPurchaserId});
	}
	
	/**
	 * 判断是否进料加工成品采购
	 * @param strContractPurchaseId
	 * @return
	 */
	public boolean isCompleteMaterielPurchase(String strContractPurchaseId){
		boolean returnResult;
		final List<String> returnFalg = new ArrayList();
		
		final String rtnFlag = "";
		String strSql = "select a.contract_no " +
				  "from t_contract_purchase_info a, t_contract_group b " +
				 "where a.contract_purchase_id = ? " +
				   "and a.is_available = '1' " +
				   "and a.contract_group_id = b.contract_group_id " +
				   "and b.is_available = '1' " +
				   "and b.trade_type = '8'";
		
		getJdbcTemplate().query(strSql, new Object[]{strContractPurchaseId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				returnFalg.add(rs.getString("contract_no"));
			}
		});
		
		if (returnFalg.get(0).indexOf("P") > 0)
			returnResult = true;
		else
			returnResult = false;
	
		return returnResult;
	}
	/**
	 * 取得部门编码
	 * @param deptId
	 * @return
	 */
	public String getSysDeptCode(String deptId){
		final List<String> returnFalg = new ArrayList();
		String deptCode = "";
		String sql = "select t.dept_code from t_sys_dept t where t.dept_id = ?";

		getJdbcTemplate().query(sql, new Object[]{deptId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				returnFalg.add( rs.getString("DEPT_CODE"));
			}
		});
		deptCode = returnFalg.get(0);
		return  deptCode;
	}
	/**
	 * 取得合同组名称
	 * @param contractGroupId
	 * @return
	 */
	public TContractGroup getContractGroupName(String contractGroupId) {
		String sql = "select * from t_contract_group where contract_group_id=?";
		final TContractGroup tcg = new TContractGroup();
		this.getJdbcTemplate().query(sql, new Object[]{contractGroupId},
				new RowCallbackHandler(){
					public void processRow(ResultSet rs) throws SQLException {
						tcg.setContractGroupId(rs.getString("contract_group_id"));
						tcg.setCmd(rs.getString("cmd"));
						tcg.setContractGroupName(rs.getString("contract_group_name"));
						tcg.setContractGroupNo(rs.getString("contract_group_no"));
						tcg.setProjectId(rs.getString("project_id"));
						tcg.setProjectName(rs.getString("project_name"));
					}
			
		});
		return tcg;
	}
	/**
	 * 获取汇率
	 * @param currency
	 * @return
	 */
	public Double getCurrencyRate(String currency){
		final List<Double> list = new ArrayList<Double>();
		getJdbcTemplate().query("SELECT RATE FROM BM_CURRENCY WHERE ID=?", new Object[]{currency},
				new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				list.add(rs.getDouble("rate"));
            }
        });
		return list.get(0);
	}
	
	public boolean getIsOver30WUSD(String orderTotal , String currency){
		if("USD".equals(currency)){
			return Double.parseDouble(orderTotal)>300000d;
		}
		else if("CNY".equals(currency)){
			return Double.parseDouble(orderTotal)>2000000d;
		}
		else {
			return Double.parseDouble(orderTotal)*getCurrencyRate(currency)>2000000d;
		}
	}
	/**
	 * 获取港币汇率
	 * @param currency
	 * @return
	 */
	public Double getCurrencyHKRate(String currency){
		final List<Double> list = new ArrayList<Double>();
		getJdbcTemplate().query("SELECT RATE FROM BM_CURRENCY_HK WHERE ID=?", new Object[]{currency},
				new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				list.add(rs.getDouble("rate"));
            }
        });
		return list.get(0);
	}
	/**
	 * 获取立项下，某合同状态下，的金额累计（CNY）
	 * @param projectID
	 * @param orderStateStr
	 * @return
	 */
	public Double getPurcharseContractSum(String projectID,String orderStateStr){
		Double contractTotal = 0d;
		String contractSQL = "select p.dept_id,p.total,p.ekko_waers from t_contract_purchase_info p where p.order_state in("+orderStateStr+") and p.project_id=? and "
		                     +"(p.trade_type !=8 or (p.trade_type=8 and substr(p.contract_no,10,1)='I')) and p.is_available='1'";
		List list = getJdbcTemplate().queryForList(contractSQL, new Object[]{projectID});
		for(Iterator it = list.iterator();it.hasNext();){
			Map itMap = (Map)it.next();
			if(Constants.DEPT_ID_HK.contains(itMap.get("dept_id"))){
				contractTotal+=(Double.parseDouble((String)itMap.get("total"))*getCurrencyHKRate((String)itMap.get("ekko_waers")));
			}
			else 
			    contractTotal+=(Double.parseDouble((String)itMap.get("total"))*getCurrencyRate((String)itMap.get("ekko_waers")));
		}
		return contractTotal;
	}
	
	public Double getSalesContractSum(String projectID,String orderStateStr){
		Double contractTotal = 0d;
		String contractSQL = "select s.dept_id,s.order_total,s.vbak_waerk from t_contract_sales_info s where s.order_state in("+orderStateStr+") and s.project_id=? and s.is_available='1'";
		                    
		List list = getJdbcTemplate().queryForList(contractSQL, new Object[]{projectID});
		for(Iterator it = list.iterator();it.hasNext();){
			Map itMap = (Map)it.next();
			if(Constants.DEPT_ID_HK.contains(itMap.get("dept_id"))){
				contractTotal+=(Double.parseDouble((String)itMap.get("order_total"))*getCurrencyHKRate((String)itMap.get("vbak_waerk")));
			}
			else 
				contractTotal+=(Double.parseDouble((String)itMap.get("order_total"))*getCurrencyRate((String)itMap.get("vbak_waerk")));
		}
		return contractTotal;
	}
	
	public Double getProjectSum(String projectID){
		final Map map = new HashMap();
		String projectSQL = "select p.dept_id, p.apply_time,p.sum,p.currency from t_project_info p "+
                            "where p.project_id=?";
		this.getJdbcTemplate().query(projectSQL, new Object[]{projectID},
				new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				map.put("sum", rs.getDouble("sum"));
				String currency = rs.getString("currency");
				map.put("apply_time",rs.getString("apply_time"));
				map.put("dept_id", rs.getString("dept_id"));
				if(!"".equals(currency)&&currency!=null) map.put("currency", currency);
				else{
					 map.put("currency", "CNY");
				}
			}
        });
		if(!Constants.DEPT_ID_HK.contains(map.get("dept_id"))){
			String date = ((String)map.get("apply_time")).replaceAll("-", "").substring(0, 8);
			String getCnySql = "SELECT getCNYAmount('"+date+"','"+map.get("currency")+"',"+map.get("sum")+") FROM DUAL";
			Double projectTotal = ((Double)this.getJdbcTemplate().queryForObject(getCnySql, Double.class))*10000;
			return projectTotal;
		}
		//香港统一转为港币
		else {
			String getHKRate = "select rate from bm_currency_hk where id='"+map.get("currency")+"'";
			Double projectTotal = ((Double)this.getJdbcTemplate().queryForObject(getHKRate, Double.class))*(Double)(map.get("sum"))*10000;
			return projectTotal;
		}
	}
	
	public String getContractOrderState(String contractId,String orderTypeStr){
		String sql = null;
		if(orderTypeStr.equals("purchase")){
			sql ="select order_state from  t_contract_purchase_info where contract_purchase_id='"+contractId+"'";
		}
		else if(orderTypeStr.equals("sales")){
			sql ="select order_state from  t_contract_sales_info where contract_sales_id='"+contractId+"'";
		}
		List list = getJdbcTemplate().queryForList(sql);
		return (String)((Map)list.get(0)).get("order_state");
	}
	
	public boolean checkSalesBalance(TContractSalesInfo sales,Double currrencyContractSum){
		Double projectSum = this.getProjectSum(sales.getProjectId());
		Double contractSum=getSalesContractSum(sales.getProjectId(),"8,9,6,7,3,5,2");
		String orderState =getContractOrderState(sales.getContractSalesId(),"sales");
		if("1".equals(orderState)){
			if(sales.getTradeType().equals("8")){
				if(projectSum*1.5-contractSum-currrencyContractSum<0)
					throw new BusinessException("进料加工业务，合同累计金额大于立项金额");
			}
			else {
				if(projectSum*1.5-contractSum-currrencyContractSum<0)
					throw new BusinessException("合同累计金额超过立项总金额的50%");
			}
		}
		else if("2".equals(orderState)) {
			if(sales.getTradeType().equals("8")){
				if(projectSum*1.5-contractSum+Double.parseDouble(sales.getOrderTotal())*getCurrencyRate(sales.getVbakWaerk())-currrencyContractSum<0)
					throw new BusinessException("进料加工业务，合同累计金额大于立项金额");
			}
			else {
				if(projectSum*1.5-contractSum+Double.parseDouble(sales.getOrderTotal())*getCurrencyRate(sales.getVbakWaerk())-currrencyContractSum<0)
					throw new BusinessException("合同累计金额超过立项总金额的50%");
			}
		}
		return true;
	}
	
	
	public boolean checkPurcharseBalance(TContractPurchaseInfo purchar,Double currrencyContractSum){
		/**ini*/
		Double projectSum = this.getProjectSum(purchar.getProjectId());
		Double contractSum=getPurcharseContractSum(purchar.getProjectId(),"8,9,6,7,3,5,2");
		String orderState =getContractOrderState(purchar.getContractPurchaseId(),"purchase");
		if("1".equals(orderState)){
			if(purchar.getTradeType().equals("8")){
				if(projectSum*1.2-contractSum-currrencyContractSum<0)
					throw new BusinessException("进料加工业务，合同累计金额大于立项金额");
			}
			else {
				if(projectSum*1.2-contractSum-currrencyContractSum<0)
					throw new BusinessException("合同累计金额超过立项总金额的20%");
			}
		}
		else if("2".equals(orderState)) {
			if(purchar.getTradeType().equals("8")){
				if(projectSum*1.2-contractSum+Double.parseDouble(purchar.getTotal())*getCurrencyRate(purchar.getEkkoWaers())-currrencyContractSum<0)
					throw new BusinessException("进料加工业务，合同累计金额大于立项金额");
			}
			else {
				if(projectSum*1.2-contractSum+Double.parseDouble(purchar.getTotal())*getCurrencyRate(purchar.getEkkoWaers())-currrencyContractSum<0)
					throw new BusinessException("合同累计金额超过立项总金额的20%");
			}
		}
		return true;
	}
	
	public void copyFile(String contractId,String businessRecordId){
		String sql = "update   t_attachement t set t.attachement_business_id = (select f.attachement_business_id from t_attachement_business f " +
				     "where f.record_id=?) where t.attachement_business_id =(select f.attachement_business_id from t_attachement_business f " +
				     "where f.record_id=?)";
		getJdbcTemplate().update(sql, new Object[]{contractId,businessRecordId});
		
		getJdbcTemplate().update("delete from t_attachement_business f where f.record_id='"+businessRecordId+"'");
	}
	
	public Map<String,Object> updateContractSalesRate(String salesId,String sapOrderNo,String rate){
		Map<String,Object> r =  UpdataSapData.updateSaleContractRate(sapOrderNo,rate);
		String sql = "update t_contract_sales_info set vbkd_Kurrf='"+rate+"' where sap_order_no='"+sapOrderNo+"'";
		if((Integer)r.get("isSuccess")==0)
		      getJdbcTemplate().update(sql);
		return r;
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-25
	 * 根据合同编号反回合同ID（用于“在收付款界面的金额分配行项双击"合同号"时，弹出合同查看窗口”的功能）
	 * type: 1-销售合同    2-采购合同
	 * 
	 */
	public List getContractIdByNo(String contractNo, int type){
		String sql = "";
		if(type==1){	// 1表示销售合同
			sql = "SELECT CONTRACT_SALES_ID FROM T_CONTRACT_SALES_INFO WHERE CONTRACT_NO = '" + contractNo + "'";
		}else{			// 否则表示采购合同
			sql = "SELECT CONTRACT_PURCHASE_ID FROM T_CONTRACT_PURCHASE_INFO WHERE CONTRACT_NO = '" + contractNo + "'";
		}
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	public String getSalePurGroup(String ymgroup,String orderTypeStr){
		String sql = null;
		if(orderTypeStr.equals("purchase")){
			sql ="select purgroup groupNo from BM_YMATGROUP_MAP where ymatgroup='"+ymgroup+"'";
		}
		else if(orderTypeStr.equals("sales")){
			sql ="select SALESGROUP groupNo from BM_YMATGROUP_MAP where ymatgroup='"+ymgroup+"'";
		}
		List list = getJdbcTemplate().queryForList(sql);
		return (String)((Map)list.get(0)).get("groupNo");
	}
	
	public boolean isExistCusSupp(String deptId,String mack,String no){
		String sql = null;
		if(mack.equals("purchase")){
			sql ="select count(*) cnt from ysuppliermaster where suppliers_code='"+no+"' and is_available=1 and exists ("+
                 "select '' from YDEPTBYCUSU where suppliercode='"+no+"' and deptid='"+deptId+"')";
		}
		else if(mack.equals("sales")){
			sql ="select count(*) cnt from ycustomermaster where guest_code='"+no+"' and is_available=1 and exists ("+
            "select '' from YDEPTBYCUSU where guestcode='"+no+"' and deptid='"+deptId+"')";
		}
		return getJdbcTemplate().queryForInt(sql)>0;
	}
	/**
	 * 
	 */
	public void fileSales(String contract_id){
		String sql = "update t_contract_sales_info set KEEP_FLAG=1 where contract_sales_id=?";
		this.getJdbcTemplate().update(sql, new Object[]{contract_id});		
	}
	
	public void filePurchase(String contract_id){
		String sql = "update t_contract_purchase_info set KEEP_FLAG=1 where contract_purchase_id=?";
		this.getJdbcTemplate().update(sql, new Object[]{contract_id});		
	}
	/**
	public List fuckDealQuery(){
		String sql = "select business_record_id,sap_order_no,order_type from t_bapi_log where creator_time>'2015-03-30 16:11:42' and creator_time<'2015-04-06 16:11:42' and business_record_id in ('4DDCABC6-DEF2-489E-9685-4F86BB0A4B30','D6D56F1C-BDA8-45BD-AC8C-94C76A264FD6') and 1=2 order by creator_time";
        //String sql = "select business_record_id,sap_order_no,order_type from t_bapi_log where creator_time>'2015-01-06 16:11:42' and creator_time<'2015-01-07 09:02:42'  order by creator_time";
		return this.getJdbcTemplate().queryForList(sql);
	}
	public void fuckDeal(String busid,String oldorderNO,String type,String newOrderNo){
		String strSql ="insert into SAPWRONT_DEAL_LOG " +
				  "(ID, " +
				   "busid, " +
				   "oldorderNO, " +
				   "type, " +
				   "newOrderNo,creatorTime ) " +
				"values(?,?,?,?,?,?)";
		getJdbcTemplate().update(strSql,new Object[]{CodeGenerator.getUUID(),busid,oldorderNO,type,newOrderNo,DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE)});
	}
	**/
	
}
