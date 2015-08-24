package com.infolion.sapss.project.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.mapping.IdGenerator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.console.workflow.dto.TaskHisDto;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.Constants;
import com.infolion.sapss.common.dto.CommonChangeDto;
import com.infolion.sapss.project.domain.TApplyCustomerCredit;
import com.infolion.sapss.project.domain.TApplyProviderCredit;
import com.infolion.sapss.project.domain.TProjectInfo;
import com.infolion.sapss.project.dto.ProjectPrintDto;
import com.infolion.sapss.project.web.ProjectDetailVO;
import com.infolion.xdss3.ceditValueControl.domain.CustomerCreditConf;
import com.infolion.xdss3.ceditValueControl.domain.CustomerCreditProj;
import com.infolion.xdss3.ceditValueControl.domain.ProviderCreditConf;
/**
 * 
 * <pre></pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 陈喜平
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Repository
public class ProjectInfoJdbcDao extends BaseDao{
//	要取利息的总账科目
	 static String controlaccount = "'1121000001', '1241010002', '2201010001', '2202010002', '2202010003',  '1122010001' ," +
	 		" '1122010002' ,  '1122010003'  , '1122010004' ,  '1124000001', '1241010001' ,  '1401000001' ,  '1405000001' ," +
	 		"  '1405000002'  , '1405000003', '1405000004' ,  '1406000001' ";
	/**
	 * 删除一条记录
	 * @param projectId
	 * @return
	 */
	public int delete(String projectId){
		String sql="update t_project_info set is_available='0' where project_id=? and project_state in ('1')";
		return this.getJdbcTemplate().update(sql, new Object[]{projectId});
	}
	/**
	 * 返回立项最大的编码
	 * @return
	 */
	public Map generateProjectNo(String deptId){
		String sql = "select * from t_project_info where dept_id=? order by creator_time desc";
		List list = this.getJdbcTemplate().queryForList(sql,new Object[]{deptId});
		if(list==null || list.size()==0)
			return Collections.EMPTY_MAP;
		else
			return  (Map)list.get(0);
	}
	/**
	 * 保存核算页中t_project_info的信息
	 * @param projectId
	 */
	public int saveDetailInfo(ProjectDetailVO vo){
		StringBuffer sb = new StringBuffer();
		sb.append("update t_project_info set");
		sb.append(" class_name = ?,");	
		sb.append(" ymat_group = ?,");	
		sb.append(" spec = ?,");			
		sb.append(" sum = ?,");
		sb.append(" no = ?,");
		sb.append(" shipment_Port = ?,");			
		sb.append(" destination_Port = ?,");			
		sb.append(" shipment_Date = ?,");			
		sb.append(" currency = ?,");			
		sb.append(" exchange_rate = ?,");		
		sb.append(" place_of_production = ?,");
		sb.append(" mask = ?");			
		sb.append(" where project_id=?");
		
		System.out.println(sb.toString());
		return this.getJdbcTemplate().update(sb.toString(), new Object[]{vo.getClassName(),vo.getYmatGroup(),
									vo.getSpec(),vo.getSum(),vo.getNo(),vo.getShipmentPort(),
									vo.getDestinationPort(),vo.getShipmentDate(),vo.getCurrency(),
									vo.getExchangeRate(),vo.getPlaceOfProduction(),vo.getMask(),vo.getProjectId()});
	}
	/**
	 * 更新项目状态
	 * @param projectInfo
	 * @return
	 */
	public int workflowUpdateState(String businessRecordId,String examineResult){
		String sql = "update t_project_info set project_state=? , approved_time=? where project_id=?";
		String result="";
		if("pass".equals(examineResult))
			result="3";
		else
			result="7";
		return this.getJdbcTemplate().update(sql, new Object[]{result,DateUtils.getCurrTime(DateUtils.DB_STORE_DATE),businessRecordId});
	}
	/**
	 * 提交流程审批后更新状态
	 * @param projectId
	 */
	public int submitUpdateState(String projectId,String state){
		String sql = "update t_project_info set project_state=? where project_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{state,projectId});
	}
	
	/**
	 * 立项申请通过时,把客户授信和供应商的授信转移到授信表中
	 * @param projectId
	 */
	public void pigeonholeCredit(String projectId){
		UserContextHolder context = UserContextHolder.getLocalUserContext();
		UserContext userContext = context.getUserContext();
		
		//得到立项申请客户授信表中本立项的客户授信信息
		String strSql = "select * from t_apply_customer_credit a where a.project_id = '"+projectId+"' and a.iscreditcust = '1';";
		final TApplyCustomerCredit tApplyCustomerCredit = new TApplyCustomerCredit();
		getJdbcTemplate().query(strSql, new Object[]{projectId},
				new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				tApplyCustomerCredit.setCreditType(rs.getString("CREDITTYPE"));
				tApplyCustomerCredit.setPrepayVlaue(rs.getDouble("PREPAYVALUE"));
				tApplyCustomerCredit.setSendValue(rs.getDouble("SENDVALUE"));
				tApplyCustomerCredit.setStartingTime(rs.getString("STARTINGTIME"));
				tApplyCustomerCredit.setEndTime(rs.getString("ENDTIME"));
				tApplyCustomerCredit.setProjectId(rs.getString("PROJECT_ID"));
            }
        });
		
		//得到本立项信息
		final TProjectInfo tProjectInfo = new TProjectInfo();
		strSql = "select a.provider_id,a.customer_id from t_project_info a where a.project_id = '" +projectId+ "'";
		getJdbcTemplate().query(strSql, new Object[]{projectId},
				new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				tProjectInfo.setProviderId(rs.getString("provider_id"));
				tProjectInfo.setCustomerId(rs.getString("customer_id"));
            }
        });
		
		//判断是不是客户授信,如果不是就判断是不是供应商授信
		if (tApplyCustomerCredit.getProjectId() != null && !"".equals(tApplyCustomerCredit.getProjectId())){
			//查询本次立项的客户是否在客户授信表中
			strSql = "select a.* " +
					  "from ycustcredconf a " +
					 "where a.customerid = " +
					       "(select a.customer_id " +
					          "from t_project_info a " +
					         "where a.project_id = '"+projectId+"')";
			
			final CustomerCreditConf customerCreditConf = new CustomerCreditConf();
			getJdbcTemplate().query(strSql, new Object[]{projectId},
					new RowCallbackHandler(){
				public void processRow(ResultSet rs) throws SQLException {
					customerCreditConf.setCustomerid(rs.getString("CUSTOMERID"));
					customerCreditConf.setConfigid(rs.getString("CONFIGID"));
	            }
	        });
			
			//判断客户是否已在授信列表中
			if (customerCreditConf.getCustomerid() != null && !"".equals(customerCreditConf.getCustomerid())){
				String strUpdateConf = "update ycustcredconf a set a.prepayvalue = a.prepayvalue + ? ,a.sendvalue = a.sendvalue + ? where a.configid = ?";
				this.getJdbcTemplate().update(strUpdateConf, new Object[]{
						tApplyCustomerCredit.getPrepayVlaue(),
						tApplyCustomerCredit.getSendValue(),
						customerCreditConf.getConfigid()
				});
				
				//客户已在客户授信表中,把立项信息插入客户授信立项表中
				String strInsertProject = "insert into ycustcredproj( " +
						"MANDT, " +
						"CONFIGPROJECTID, " +
						"CONFIGID, " +
						"PROJECTID, " +
						"CREATOR, " +
						"CREATETIME, " +
						"LASTMODIFYER, " +
						"LASTMODIFYTIME, "+ 
						"OTHERPREPAYVALUE) " +
						"values( " +
						"?,?,?,?,?,to_char(sysdate,'yyyymmdd'),?,to_char(sysdate,'yyyymmdd'),0.0)";
				
				
				this.getJdbcTemplate().update(strInsertProject, new Object[]{
						userContext.getClient(),
						CodeGenerator.getUUID(),
						customerCreditConf.getConfigid(),
						projectId,
						userContext.getUser().getUserId(),
						userContext.getUser().getUserId()
				});
			}else{
				//客户不在客户授信表中,需要把客户增加到客户授信表中,还要把立项插入客户立项授信表
				customerCreditConf.setClient(userContext.getClient());
				customerCreditConf.setConfigid(CodeGenerator.getUUID());
				customerCreditConf.setCustomerid(tProjectInfo.getCustomerId());
				customerCreditConf.setPrepayvalue(BigDecimal.valueOf(tApplyCustomerCredit.getPrepayVlaue()));
				customerCreditConf.setSendvalue(BigDecimal.valueOf(tApplyCustomerCredit.getSendValue()));
				customerCreditConf.setStartingtime(tApplyCustomerCredit.getStartingTime());
				customerCreditConf.setEndtime(tApplyCustomerCredit.getEndTime());
				customerCreditConf.setCredittype(tApplyCustomerCredit.getCreditType());
				
				String strInsertConf = "insert into YCUSTCREDCONF " +
								"( " +
								  "MANDT, " +
								  "CONFIGID, " +
								  "PREPAYVALUE, " +
								  "SENDVALUE, " +
								  "STARTINGTIME, " +
								  "ENDTIME, " +
								  "CREATOR, " +
								  "CREATETIME, " +
								  "LASTMODIFYER, " +
								  "LASTMODIFYTIME, " +
								  "CUSTOMERID, " +
								  "CREDITTYPE " +
								") " +
								"values " +
								"(?,?,?,?,?,?,to_char(sysdate,'yyyymmdd'),?,to_char(sysdate,'yyyymmdd'),?,?)";
				this.getJdbcTemplate().update(strInsertConf, new Object[]{
						userContext.getClient(),
						customerCreditConf.getConfigid(),
						customerCreditConf.getPrepayvalue(),
						customerCreditConf.getSendvalue(),
						customerCreditConf.getStartingtime(),
						customerCreditConf.getEndtime(),
						userContext.getUser().getUserId(),
						userContext.getUser().getUserId(),
						customerCreditConf.getCustomerid(),
						tApplyCustomerCredit.getCreditType()
				});
				
				String strInsertProject = "insert into ycustcredproj( " +
						"MANDT, " +
						"CONFIGPROJECTID, " +
						"CONFIGID, " +
						"PROJECTID, " +
						"CREATOR, " +
						"CREATETIME, " +
						"LASTMODIFYER, " +
						"LASTMODIFYTIME, "+ 
						"OTHERPREPAYVALUE) " +
						"values( " +
						"?,?,?,?,?,to_char(sysdate,'yyyymmdd'),?,to_char(sysdate,'yyyymmdd'),0.0)";
				
				
				this.getJdbcTemplate().update(strInsertProject, new Object[]{
						userContext.getClient(),
						CodeGenerator.getUUID(),
						customerCreditConf.getConfigid(),
						projectId,
						userContext.getUser().getUserId(),
						userContext.getUser().getUserId()
				});
			}
			
		}else{
			strSql = "select * from t_apply_provider_credit a where a.project_id = '"+projectId+"' and a.iscreditprovider = '1'";
			final TApplyProviderCredit tApplyProviderCredit = new TApplyProviderCredit();
			getJdbcTemplate().query(strSql, new Object[]{projectId},
					new RowCallbackHandler(){
				public void processRow(ResultSet rs) throws SQLException {
					tApplyProviderCredit.setProjectId(rs.getString("PROJECT_ID"));
					tApplyProviderCredit.setProviderTotalValue(rs.getDouble("TOTALVALUE"));
					tApplyProviderCredit.setProviderStartingTime(rs.getString("STARTINGTIME"));
					tApplyProviderCredit.setProviderEndTime(rs.getString("ENDTIME"));
					tApplyProviderCredit.setCreditType(rs.getString("CREDITTYPE"));
	            }
	        });
			
			//判断是否供应商授信
			if (tApplyProviderCredit.getProjectId() != null && !"".equals(tApplyProviderCredit.getProjectId())){
				//判断立项所选的供应商是否在已经授信
				strSql = "select a.* " +
						  "from yprovcredconf a " +
						 "where a.providerid = " +
						       "(select a.provider_id " +
						          "from t_project_info a " +
						         "where a.project_id = '"+projectId+"')";
				
				final ProviderCreditConf providerCreditConf = new ProviderCreditConf();
				getJdbcTemplate().query(strSql, new Object[]{projectId},
						new RowCallbackHandler(){
					public void processRow(ResultSet rs) throws SQLException {
						providerCreditConf.setProviderid(rs.getString("PROVIDERID"));
						providerCreditConf.setConfigid(rs.getString("CONFIGID"));
		            }
		        });
				
				//判断供应商是否已在授信列表中
				if (providerCreditConf.getProviderid() != null && !"".equals(providerCreditConf.getProviderid())){
					String strUpdateProvConf = "update yprovcredconf a set a.totalvalue = a.totalvalue + ? where a.configid = ?";
					this.getJdbcTemplate().update(strUpdateProvConf, new Object[]{
							tApplyProviderCredit.getProviderTotalValue(),
							providerCreditConf.getConfigid()
					});
					
					String strProviderInsert = "insert into table YPROVCREDPROJ " +
							"( MANDT, " +
							  "CONFIGPROJECTID, " +
							  "CONFIGID, " +
							  "PROJECTID, " +
							  "CREATOR, " +
							  "CREATETIME, " +
							  "LASTMODIFYER, " +
							  "LASTMODIFYTIME, " +
							  "OTHERPREPAYVALUE) " +
							"values(?,?,?,?,?,to_char(sysdate,'yyyymmdd'),?,to_char(sysdate,'yyyymmdd'),0.0)";
					
					this.getJdbcTemplate().update(strProviderInsert, new Object[]{
							userContext.getClient(),
							CodeGenerator.getUUID(),
							providerCreditConf.getConfigid(),
							projectId,
							userContext.getUser().getUserId(),
							userContext.getUser().getUserId()
					});
				}else{
					//客户不在客户授信表中,需要把客户增加到客户授信表中,还要把立项插入客户立项授信表
					providerCreditConf.setClient(userContext.getClient());
					providerCreditConf.setConfigid(CodeGenerator.getUUID());
					providerCreditConf.setProviderid(tProjectInfo.getProviderId());
					providerCreditConf.setTotalvalue(BigDecimal.valueOf(tApplyProviderCredit.getProviderTotalValue()));
					providerCreditConf.setStartingtime(tApplyProviderCredit.getProviderStartingTime());
					providerCreditConf.setEndtime(tApplyProviderCredit.getProviderEndTime());
					providerCreditConf.setCredittype(tApplyProviderCredit.getCreditType());
					
					String strInsertProviderConf = "insert into YPROVCREDCONF( " +
								  "MANDT, " +
								  "CONFIGID, " +
								  "PROVIDERID, " +
								  "TOTALVALUE, " +
								  "STARTINGTIME, " +
								  "ENDTIME, " +
								  "CREATOR, " +
								  "CREATETIME, " +
								  "LASTMODIFYER, " +
								  "LASTMODIFYTIME, " +
								  "CREDITTYPE) " +
								"values(?,?,?,?,?,?,?,to_char(sysdate,'yyyymmdd'),?,to_char(sysdate,'yyyymmdd'),?)";
					
					this.getJdbcTemplate().update(strInsertProviderConf, new Object[]{
							userContext.getClient(),
							providerCreditConf.getConfigid(),
							providerCreditConf.getProviderid(),
							providerCreditConf.getTotalvalue(),
							providerCreditConf.getStartingtime(),
							providerCreditConf.getEndtime(),
							userContext.getUser().getUserId(),
							userContext.getUser().getUserId(),
							providerCreditConf.getCredittype()
					});
					
					String strProviderInsert = "insert into table YPROVCREDPROJ " +
							"( MANDT, " +
							  "CONFIGPROJECTID, " +
							  "CONFIGID, " +
							  "PROJECTID, " +
							  "CREATOR, " +
							  "CREATETIME, " +
							  "LASTMODIFYER, " +
							  "LASTMODIFYTIME, " +
							  "OTHERPREPAYVALUE) " +
							"values(?,?,?,?,?,to_char(sysdate,'yyyymmdd'),?,to_char(sysdate,'yyyymmdd'),0.0)";
					
					this.getJdbcTemplate().update(strProviderInsert, new Object[]{
							userContext.getClient(),
							CodeGenerator.getUUID(),
							providerCreditConf.getConfigid(),
							projectId,
							userContext.getUser().getUserId(),
							userContext.getUser().getUserId()
					});
				}
			}
		}
	}
	
	/**
	 * 立项变更状态更新
	 * @param businessRecordId
	 * @param examineResult
	 */
	public int workflowChangeUpdateState(String businessRecordId,String examineResult) {
		String sql = "update t_project_info set project_state=? where project_id=?";
		String result="";
		if("pass".equals(examineResult))
			result="8";
		else
			result="3";
		return this.getJdbcTemplate().update(sql, new Object[]{result,businessRecordId});
	}
	
	/**
	 * 更新立项主信息
	 * @param info
	 * @return
	 */
	public int updateProjectMainInfo(TProjectInfo info){
		String sql = "update t_project_info set project_name=?,available_data_end=?," +
				"proider_credit_grade=?,provider_address=?,provider_balance_type=?,provider_explain=?," +
				"provider_link_man=?,provider_id=?,provider_pay_type=?,provider_tel=?," +
				"customer_address=?,customer_balance_type=?,customer_credit_grade=?,customer_explain=?," +
				"customer_id=?,customer_link_man=?,customer_pay_type=?,customer_tel=?,old_project_no=?,is_credit=?,credit_description=?,cmd=? where project_id=?";
		
		return this.getJdbcTemplate().update(sql,new Object[]{
				info.getProjectName(),info.getAvailableDataEnd(),
				info.getProiderCreditGrade(),info.getProviderAddress(),	info.getProviderBalanceType(),info.getProviderExplain(),
				info.getProviderLinkMan(),info.getProviderId(),	info.getProviderPayType(),info.getProviderTel(),
				info.getCustomerAddress(),info.getCustomerBalanceType(),info.getCustomerCreditGrade(),info.getCustomerExplain(),
				info.getCustomerId(),info.getCustomerLinkMan(),info.getCustomerPayType(),info.getCustomerTel(),info.getOldProjectNo(),info.getIsCredit(),info.getCreditDescription(),
				info.getCmd(),info.getProjectId()});
	}
	
	public void insertProjectMemo(){
		String sql =  "select p.project_id,p.creator from t_project_info p where p.project_id not in" +
				"(select a.project_id from t_project_accounting a where currency='OTHER' and a.accounting_item=?)";
		List list = getJdbcTemplate().queryForList(sql, new Object[]{"6"});
		for(Iterator<Map> it = list.iterator();it.hasNext();){
			Map map = it.next();
			String id = CodeGenerator.getUUID();
			String insertSql = "insert into t_project_accounting (ID,PROJECT_ID,ACCOUNTING_ITEM_ID," +
					"ACCOUNTING_ITEM,CURRENCY,ACCOUNTING_VALUE,CREATOR_TIME,CREATOR) VALUES('" +id+
					"','"+map.get("PROJECT_ID")+"','6','6','OTHER',' ','20090306240000','"+map.get("CREATOR")+"')";
			getJdbcTemplate().update(insertSql);
		}
	}
	/**
	 * 取得立项核算项的值
	 * @param projectId
	 * @param currency
	 * @param item
	 * @return
	 */
	public String getProjectAccountValue(String projectId, String currency,	String item) {
		String sql = "select accounting_value from t_project_accounting where project_id=? and currency=? and accounting_item=?";
		List<Map<String,Object>> list =  this.getJdbcTemplate().queryForList(sql,new Object[]{projectId,currency,item});
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			return (String)map.get("accounting_value");
		}else
			return "";
	
	}
	public String queryProjectIdByNo(String projectNo){
		String sql = "select project_id from t_project_info where project_no=?";
		List<Map<String,Object>> list =  this.getJdbcTemplate().queryForList(sql,new Object[]{projectNo});
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			return (String)map.get("project_id");
		}else
			return null;
		
	}
	/**
	 * 通过立项ID查询销售订单号(已处理前导0)
	 * @param projectNo
	 * @return
	 */
	public List<Map<String,Object>> querySelSapNoByProjectId(String projectId){
	    String sql = "select CASE WHEN LENGTH(s.sap_order_no)=10 THEN SUBSTR(s.sap_order_no,3,10) END sap_order_no from T_CONTRACT_SALES_INFO s where PROJECT_ID=?  and trim(sap_order_no) is not null";
	    return  this.getJdbcTemplate().queryForList(sql,new Object[]{projectId});
	}
	
	/**
	 * 通过立项ID查询采购订单号
	 * @param projectNo
	 * @return
	 */
	public List<Map<String,Object>> queryPurSapNoByProjectId(String projectId){
	    String sql = "select SAP_ORDER_NO from T_CONTRACT_PURCHASE_INFO where PROJECT_ID=? and trim(sap_order_no) is not null";
	    return  this.getJdbcTemplate().queryForList(sql,new Object[]{projectId});
	}
	/**
	 * 通过合同组ID查询销售订单号(已处理前导0)
	 * @param projectNo
	 * @return
	 */
	public List<Map<String,Object>> querySelSapNoBycontractGroupId(String contractGroupId){
	    String sql = "select CASE WHEN LENGTH(s.sap_order_no)=10 THEN SUBSTR(s.sap_order_no,3,10) END sap_order_no from T_CONTRACT_SALES_INFO s where contract_group_id=?  and trim(sap_order_no) is not null";
	    return  this.getJdbcTemplate().queryForList(sql,new Object[]{contractGroupId});
	}
	
	/**
	 * 通过合同组ID查询采购订单号
	 * @param projectNo
	 * @return
	 */
	public List<Map<String,Object>> queryPurSapNoBycontractGroupId(String contractGroupId){
	    String sql = "select SAP_ORDER_NO from T_CONTRACT_PURCHASE_INFO where contract_group_id=? and trim(sap_order_no) is not null";
	    return  this.getJdbcTemplate().queryForList(sql,new Object[]{contractGroupId});
	}
	
	/**
	 * 查询某天利率
	 * @param projectNo
	 * @return
	 */
	public String queryRateByDay (String endData){
	    String sql = "SELECT nvl(sum(RATE),0) FROM YRATE WHERE STARTDATE - 1 < '"+endData+"' AND '"+endData+"' < ENDDATE + 1";
	    return  (String)this.getJdbcTemplate().queryForObject(sql, String.class);
	}
	/**
	 * 查询利率期间
	 * @param 
	 * @return
	 */
	public List<Map<String,Object>> queryRateByDay (String strDate,String endDate){
//		select r.startdate,r.enddate,r.rate from YRATE r where  ( r.startdate <= '20110701' and r.enddate >='20110701' )
//		or (r.startdate <= '20131231' and r.enddate >= '20131231')
//		or (r.startdate > '20110701' and r.enddate < '20131231')
	    String sql = "select r.startdate,r.enddate,r.rate from YRATE r where  ( r.startdate <= ? and r.enddate >=? )" +
	    		" or (r.startdate <= ? and r.enddate >= ?) or (r.startdate > ? and r.enddate < ?)";
	    return  this.getJdbcTemplate().queryForList(sql,new Object[]{strDate,strDate,endDate,endDate,strDate,endDate});
	}
	
	public List<Map<String,Object>> queryReceByProjectId(String projectId){
	    String sql = 
	            " SELECT SUM(TOTAL) TOTAL," +
	    		"       SUM(QUANTITY) QUANTITY," +
	    		"        SUM(TOTAL) / SUM(QUANTITY) PRICE" +
	    		"   FROM T_RECEIPT_MATERIAL TT" +
	    		"  WHERE TT.RECEIPT_ID IN" +
	    		"        (SELECT T.RECEIPT_ID FROM T_RECEIPT_INFO T, T_PROJECT_INFO T2 WHERE T.PROJECT_NO = T2.PROJECT_NO AND T2.PROJECT_ID =? )";
	    return  this.getJdbcTemplate().queryForList(sql,new Object[]{projectId});
	}
	
	public List<Map<String,Object>> queryReceByContractGroupid(String contractGroupid){
	    String sql = 
	            " SELECT SUM(TOTAL) TOTAL," +
	    		"       SUM(QUANTITY) QUANTITY," +
	    		"        SUM(TOTAL) / SUM(QUANTITY) PRICE" +
	    		"   FROM T_RECEIPT_MATERIAL TT" +
	    		"  WHERE TT.RECEIPT_ID IN" +
	    		"        (SELECT T.RECEIPT_ID FROM T_RECEIPT_INFO T, t_contract_group T2 WHERE T.Contract_Group_No = T2.Contract_Group_No AND T2.CONTRACT_GROUP_ID =? )";
	    return  this.getJdbcTemplate().queryForList(sql,new Object[]{contractGroupid});
	}
	
	private ProjectPrintDto queryProjectPrintDto(String projectId){
		String sql = "select  project_Id,org_Name,nuder_Taker,project_No,old_Project_No,project_Name,apply_Time,available_Data_End,customer_Link_Man," +
				"customer_Pay_Type,customer_Balance_Type,provider_Link_Man,provider_Pay_Type,provider_Balance_Type from t_project_info where project_id=?" ;
		final ProjectPrintDto dto = new ProjectPrintDto();
		getJdbcTemplate().query(sql, new Object[]{projectId},
				new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				dto.setProjectId(rs.getString("project_Id"));
				dto.setOrgName(rs.getString("org_Name"));
				dto.setNuderTaker(rs.getString("nuder_Taker"));//承办人
				dto.setProjectNo(rs.getString("project_No"));
				dto.setOldProjectNo(rs.getString("old_Project_No"));//原项目编码
				dto.setProjectName(rs.getString("project_Name"));
				dto.setApplyTime(rs.getString("apply_Time"));//申报时间
				dto.setAvailableDataEnd(rs.getString("available_Data_End"));
				dto.setCustomerLinkMan(rs.getString("customer_Link_Man"));//客户名称
				dto.setCustomerPayType(rs.getString("customer_Pay_Type"));//客户付款方式
				dto.setCustomerBalanceType(rs.getString("customer_Balance_Type"));//客户结算方式
				dto.setProviderLinkMan(rs.getString("provider_Link_Man"));//供应商名称
				dto.setProviderPayType(rs.getString("provider_Pay_Type"));//供应商付款方式
				dto.setProviderBalanceType(rs.getString("provider_Balance_Type"));//供应商结算方式
            }
        });
		dto.setTasks(queryTaskHistList(projectId));
		return dto;
	}
	
	private List<TaskHisDto> queryTaskHistList(String businessRecorderId){
		final List<TaskHisDto> list = new ArrayList<TaskHisDto>();
		String strSql = "select TASK_NAME,EXAMINE_DEPT_NAME,EXAMINE_PERSON,EXAMINE_RESULT,EXAMINE,TASK_CREATE_TIME,TASK_END_TIME" +
                        " from (SELECT * fROM t_sys_wf_task_history union SELECT * FROM t_sys_wf_task_history_O) where business_record_id=? order by TASK_CREATE_TIME asc";
		
		getJdbcTemplate().query(strSql,new Object[]{businessRecorderId}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				TaskHisDto dto = new TaskHisDto();
			    dto.setTaskName(rs.getString("TASK_NAME"));
			    dto.setExamine(rs.getString("EXAMINE"));
			    dto.setExamineDeptName(rs.getString("EXAMINE_DEPT_NAME"));
			    dto.setExaminePerson(rs.getString("EXAMINE_PERSON"));
			    dto.setExamineResult(rs.getString("EXAMINE_RESULT"));
			    dto.setTaskCreateTime(rs.getString("TASK_CREATE_TIME"));
			    dto.setTaskEndTime(rs.getString("TASK_END_TIME"));
				list.add(dto);
			}});

		return list;
	}
	
	public List<ProjectPrintDto> queryProjects4Print(String[] projectIds){
		List<ProjectPrintDto> list = new ArrayList<ProjectPrintDto>();
		for(String projectId:projectIds)
			list.add(queryProjectPrintDto(projectId));
		
		return list;
	}
	
	public List<CommonChangeDto> queryChangeDtos(String projectId){
		final List<CommonChangeDto> list = new ArrayList<CommonChangeDto>();
		String sql = "select CHANGE_ID, CHANGE_DESC,CHANGE_TIME,CHANGE_NO from T_PROJECT_CHANGE where project_id=? and is_available='1' order by create_time";
		getJdbcTemplate().query(sql,new Object[]{projectId}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				CommonChangeDto dto = new CommonChangeDto();
				dto.setChangeId(rs.getString("CHANGE_ID"));
			    dto.setChangeDesc(rs.getString("change_desc"));
			    dto.setChangeTime(rs.getString("change_time"));
			    dto.setChangeNo(rs.getString("change_no"));
			    dto.setTasklist(queryTaskHistList(dto.getChangeId()));
				list.add(dto);
		}});
		return list;
	}
	
	/**
	 * 得到客户的信用额度
	 * @param strProjectId
	 * @return
	 */
	public TApplyCustomerCredit getCustomerCredit(String strProjectId){
		final TApplyCustomerCredit tApplyCustomerCredit = new TApplyCustomerCredit();
		
		String strSql = "select * from t_apply_customer_credit a where a.project_id = ?";
		
		getJdbcTemplate().query(strSql,new Object[]{strProjectId}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				tApplyCustomerCredit.setApplyId(rs.getString("APPLYID"));
				tApplyCustomerCredit.setProjectId(rs.getString("PROJECT_ID"));
				tApplyCustomerCredit.setPrepayVlaue(rs.getDouble("PREPAYVALUE"));
				tApplyCustomerCredit.setSendValue(rs.getDouble("SENDVALUE"));
				tApplyCustomerCredit.setStartingTime(rs.getString("STARTINGTIME"));
				tApplyCustomerCredit.setEndTime(rs.getString("ENDTIME"));
				tApplyCustomerCredit.setIsCreditCust(rs.getString("ISCREDITCUST"));
				tApplyCustomerCredit.setCreditType(rs.getString("CREDITTYPE"));
		}});
		
		return tApplyCustomerCredit;
	}
	
	/**
	 * 得到供应商信用额度
	 */
	public TApplyProviderCredit getProviderCredit(String strProjectId){
		final TApplyProviderCredit tApplyProviderCredit = new TApplyProviderCredit();
		
		String strSql = "select * from t_apply_provider_credit a where a.project_id = ?";
		
		getJdbcTemplate().query(strSql,new Object[]{strProjectId}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				tApplyProviderCredit.setProviderApplyId(rs.getString("APPLYID"));
				tApplyProviderCredit.setProjectId(rs.getString("PROJECT_ID"));
				tApplyProviderCredit.setProviderTotalValue(rs.getDouble("TOTALVALUE"));
				tApplyProviderCredit.setProviderStartingTime(rs.getString("STARTINGTIME"));
				tApplyProviderCredit.setProviderEndTime(rs.getString("ENDTIME"));
				tApplyProviderCredit.setIsCreditProvider(rs.getString("ISCREDITPROVIDER"));
				tApplyProviderCredit.setCreditType(rs.getString("CREDITTYPE"));
		}});
		
		return tApplyProviderCredit;
	}
	
	/**立项净利润转为CNY,香港和新加坡公司先转本币再转CNY**/
	public String getClearProfit4CNY(String projectId,String deptId,Double rate){
		String local = getProjectAccountValue(projectId, "RMB", "31");
		String foreign  = getProjectAccountValue(projectId, "US", "31"); 
		//香港处理
		if(Constants.DEPT_ID_HK.contains(deptId)){
			/**计算港币对人民币汇率按提交时间*/
			String ratesql = "select rate from bm_currency where ID = 'HKD'";
			Double hkdToCnyRate = (Double)getJdbcTemplate().queryForObject(ratesql, Double.class);			
			if(local!=null&&!"".equals(local)&&Double.parseDouble(local)>0) {
				Double cny = hkdToCnyRate*Double.parseDouble(local);
				return cny.toString();
			}
			else if(foreign!=null&&!"".equals(foreign)&&Double.parseDouble(foreign)>0){
				Double cny = hkdToCnyRate*Double.parseDouble(foreign)*rate;
				return cny.toString();
			}
		}
		//新加坡处理
		else if(Constants.DEPT_ID_SG.contains(deptId)){
			/**计算港币对人民币汇率按提交时间*/
			String ratesql = "select rate from bm_currency where ID = 'SGD'";
			Double hkdToCnyRate = (Double)getJdbcTemplate().queryForObject(ratesql, Double.class);			
			if(local!=null&&!"".equals(local)&&Double.parseDouble(local)>0) {
				Double cny = hkdToCnyRate*Double.parseDouble(local);
				return cny.toString();
			}
			else if(foreign!=null&&!"".equals(foreign)&&Double.parseDouble(foreign)>0){
				Double cny = hkdToCnyRate*Double.parseDouble(foreign)*rate;
				return cny.toString();
			}
		}
		//
		else {
			if(local!=null&&!"".equals(local)&&Double.parseDouble(local)>0) {
				return local.toString();
			}
			else if(foreign!=null&&!"".equals(foreign)&&Double.parseDouble(foreign)>0){
				Double cny = Double.parseDouble(foreign)*rate;
				return cny.toString();
			}
		}	
		return "0";
	}
	/**
	 * 更新立项信息为有效状态
	 */
	public void fileProject(String projectId){
		String sql = "update t_project_info set KEEP_FLAG=1  where project_Id=?";
		this.getJdbcTemplate().update(sql, new Object[]{projectId});		
	}

	
//  取 得外围的利息
//  应收票据 在收付款中都有，收票据，付票据。应收票据（1121000001）
//  应付票据 在收付款中都有，收票据，付票据 应付票据（2201010001）
//  应付账款 在付款，供应商退款中都有 应付账款（2202010002-3）
//  内部往来-贷 -1241010002	
	public BigDecimal getHKLXByProject(String projectId, String strDate, String endData){
		String sql1="select vi.debitcredit,sum(vi.amount2) as amount2	  from yvoucheritem vi, yvoucher v " +
		  " where vi.voucherid = v.voucherid    and trim(v.voucherno) is not null    and v.processstate <> '-1'" +
		  "   and  v.checkdate BETWEEN ? and ?   " +
		  " and ( vi.controlaccount in  ("+ controlaccount +")  or vi.controlaccount like '200%' )"  +
		  "   and  vi.businessitemid in (  select pi.paymentitemid   from ypaymentitem pi  where pi.project_no in  ( " +
		  " select pri.project_no from t_project_info pri where pri.project_id in  (?))   )" +
		  "		   group by vi.debitcredit ";
		
		String sql2 ="select vi.debitcredit,sum(vi.amount2) as amount2	  from yvoucheritem vi, yvoucher v " +
		  " where vi.voucherid = v.voucherid    and trim(v.voucherno) is not null    and v.processstate <> '-1'" +
		  "   and  v.checkdate BETWEEN ? and ?   " +
		  " and ( vi.controlaccount in  ("+ controlaccount +")  or vi.controlaccount like '200%' )"  +
		 " and  vi.businessitemid in (    select rfi.refundmentitemid   from yrefundmentitem rfi	  where rfi.project_no in (" +
		 "select pri.project_no from t_project_info pri where pri.project_id in  (?))   )    " +
		 "  group by vi.debitcredit";
		
		String sql3 ="select vi.debitcredit,sum(vi.amount2) as amount2	  from yvoucheritem vi, yvoucher v " +
		  " where vi.voucherid = v.voucherid    and trim(v.voucherno) is not null    and v.processstate <> '-1'" +
		  "   and  v.checkdate BETWEEN ? and ?   " +
		  " and ( vi.controlaccount in  ("+ controlaccount +")  or vi.controlaccount like '200%' )"  +
		  " and  vi.businessitemid in ( select ci.collectitemid    from ycollectitem ci    where ci.project_no in  (" +
		  " select pri.project_no from t_project_info pri where pri.project_id in (?))	   )" +
		  "  group by vi.debitcredit";
		
		List list = this.getJdbcTemplate().queryForList(sql1,new Object[]{strDate,endData,projectId});
		List list2 = this.getJdbcTemplate().queryForList(sql2,new Object[]{strDate,endData,projectId});
		List list3 = this.getJdbcTemplate().queryForList(sql3,new Object[]{strDate,endData,projectId});
		list.addAll(list2);
		list.addAll(list3);
		BigDecimal amount = new BigDecimal("0");
		if(list!=null && list.size()>0){			
			for(int i=0;i<list.size();i++){
				String debitcredit = (String)((Map)list.get(i)).get("debitcredit");
				String amount2 = (String)((Map)list.get(i)).get("amount2");
				BigDecimal amount3 = new BigDecimal(amount2);
//				S-H
				if("H".equals(debitcredit)){
					amount3 = new BigDecimal("0").subtract( amount3);
				}
				amount = amount.add(amount3);
			}		
		}
		return amount;	
    }
	
	public BigDecimal getHKLXByContractGroup(String contractGroupId, String strDate, String endData){
		String sql1="select vi.debitcredit,sum(vi.amount2) as amount2	  from yvoucheritem vi, yvoucher v " +
		  " where vi.voucherid = v.voucherid    and trim(v.voucherno) is not null    and v.processstate <> '-1'" +
		  "   and  v.checkdate BETWEEN ? and ?   " +
		  " and ( vi.controlaccount in  ("+ controlaccount +")  or vi.controlaccount like '200%' )"  +
		  "   and  vi.businessitemid in (  select pi.paymentitemid   from ypaymentitem pi  where pi.contract_no in  ( " +
		  " select cpi.contract_no  from t_contract_purchase_info cpi    where cpi.contract_group_id in  (?))   )" +
		  "		   group by vi.debitcredit ";
		
		String sql2 ="select vi.debitcredit,sum(vi.amount2) as amount2	  from yvoucheritem vi, yvoucher v " +
		  " where vi.voucherid = v.voucherid    and trim(v.voucherno) is not null    and v.processstate <> '-1'" +
		  "   and  v.checkdate BETWEEN ? and ?   " +
		  " and ( vi.controlaccount in  ("+ controlaccount +")  or vi.controlaccount like '200%' )"  +
		 " and  vi.businessitemid in (    select rfi.refundmentitemid   from yrefundmentitem rfi	  where rfi.contract_no in (" +
		 "select cpi.contract_no  from t_contract_purchase_info cpi   where cpi.contract_group_id in  (?))   )    " +
		 "  group by vi.debitcredit";
		
		String sql3 ="select vi.debitcredit,sum(vi.amount2) as amount2	  from yvoucheritem vi, yvoucher v " +
		  " where vi.voucherid = v.voucherid    and trim(v.voucherno) is not null    and v.processstate <> '-1'" +
		  "   and  v.checkdate BETWEEN ? and ?   " +
		  " and ( vi.controlaccount in  ("+ controlaccount +")  or vi.controlaccount like '200%' )"  +
		  " and  vi.businessitemid in ( select ci.collectitemid    from ycollectitem ci    where ci.contract_no in  (" +
		  " select csi.contract_no   from t_contract_sales_info csi    where csi.contract_group_id in (?))	   )" +
		  "  group by vi.debitcredit";
		
		List list = this.getJdbcTemplate().queryForList(sql1,new Object[]{strDate,endData,contractGroupId});
		List list2 = this.getJdbcTemplate().queryForList(sql2,new Object[]{strDate,endData,contractGroupId});
		List list3 = this.getJdbcTemplate().queryForList(sql3,new Object[]{strDate,endData,contractGroupId});
		list.addAll(list2);
		list.addAll(list3);
		BigDecimal amount = new BigDecimal("0");
		if(list!=null && list.size()>0){			
			for(int i=0;i<list.size();i++){
				String debitcredit = (String)((Map)list.get(i)).get("debitcredit");
				String amount2 = (String)((Map)list.get(i)).get("amount2");
				BigDecimal amount3 = new BigDecimal(amount2);
				if("H".equals(debitcredit)){
					amount3 = new BigDecimal("0").subtract( amount3);
				}
				amount = amount.add(amount3);
			}		
		}
		return amount;		
    	
    }
	
	public List getHKLXByContractGroup2(String contractGroupId, String strDate, String endData){
		String sql1="select vi.debitcredit,vi.amount2,v.checkdate	  from yvoucheritem vi, yvoucher v " +
		  " where vi.voucherid = v.voucherid    and trim(v.voucherno) is not null    and v.processstate <> '-1'" +
		  "   and  v.checkdate BETWEEN ? and ?   " +
		  " and ( vi.controlaccount in  ("+ controlaccount +")  or vi.controlaccount like '200%' )"  +
		  "   and  vi.businessitemid in (  select pi.paymentitemid   from ypaymentitem pi,ypayment p  where pi.paymentid=p.paymentid and p.paymenttype !='07' and pi.contract_no in  ( " +
		  " select cpi.contract_no  from t_contract_purchase_info cpi    where cpi.contract_group_id in  (?))   )" +
		  "		   ";
		
		String sql2 ="select vi.debitcredit,vi.amount2,v.checkdate	  from yvoucheritem vi, yvoucher v " +
		  " where vi.voucherid = v.voucherid    and trim(v.voucherno) is not null    and v.processstate <> '-1'" +
		  "   and  v.checkdate BETWEEN ? and ?   " +
		  " and ( vi.controlaccount in  ("+ controlaccount +")  or vi.controlaccount like '200%' )"  +
		 " and  vi.businessitemid in (    select rfi.refundmentitemid   from yrefundmentitem rfi	  where rfi.contract_no in (" +
		 "select cpi.contract_no  from t_contract_purchase_info cpi   where cpi.contract_group_id in  (?))  " +
		 " or rfi.contract_no in (select csi.contract_no from t_contract_sales_info csi  where csi.contract_group_id in ('"+contractGroupId+"'))" +
		 " )    " ;
		
		String sql3 ="select vi.debitcredit,vi.amount2,v.checkdate	  from yvoucheritem vi, yvoucher v " +
		  " where vi.voucherid = v.voucherid    and trim(v.voucherno) is not null    and v.processstate <> '-1'" +
		  "   and  v.checkdate BETWEEN ? and ?   " +
		  " and ( vi.controlaccount in  ("+ controlaccount +")  or vi.controlaccount like '200%' )"  +
		  " and  vi.businessitemid in ( select ci.collectitemid    from ycollectitem ci ,ycollect c   where ci.collectid=c.collectid and c.collecttype !='07' and  ci.contract_no in  (" +
		  " select csi.contract_no   from t_contract_sales_info csi    where csi.contract_group_id in (?))	   )" +
		  "  ";
		
		List list = this.getJdbcTemplate().queryForList(sql1,new Object[]{strDate,endData,contractGroupId});
		List list2 = this.getJdbcTemplate().queryForList(sql2,new Object[]{strDate,endData,contractGroupId});
		List list3 = this.getJdbcTemplate().queryForList(sql3,new Object[]{strDate,endData,contractGroupId});
		list.addAll(list2);
		list.addAll(list3);		
		return list;		
    	
    }

//  取 得外围的利息
//  应收票据 在收付款中都有，收票据，付票据。应收票据（1121000001）
//  应付票据 在收付款中都有，收票据，付票据 应付票据（2201010001）
//  应付账款 在付款，供应商退款中都有 应付账款（2202010002-3）
//  内部往来-贷 -1241010002	
	public List getHKLXByProject2(String projectId, String strDate, String endData){
		String sql1="select vi.debitcredit,vi.amount2,v.checkdate	  from yvoucheritem vi, yvoucher v " +
		  " where vi.voucherid = v.voucherid    and trim(v.voucherno) is not null    and v.processstate <> '-1'" +
		  "   and  v.checkdate BETWEEN ? and ?   " +
		  " and ( vi.controlaccount in  ("+ controlaccount +")  or vi.controlaccount like '200%' )"  +
		  "   and  vi.businessitemid in (  select pi.paymentitemid   from ypaymentitem pi  ,ypayment p  where pi.paymentid=p.paymentid and p.paymenttype !='07' and pi.project_no in  ( " +
		  " select pri.project_no from t_project_info pri where pri.project_id in  (?))   )" +
		  "		   group by vi.debitcredit ";
		
		String sql2 ="select vi.debitcredit,vi.amount2,v.checkdate	  from yvoucheritem vi, yvoucher v " +
		  " where vi.voucherid = v.voucherid    and trim(v.voucherno) is not null    and v.processstate <> '-1'" +
		  "   and  v.checkdate BETWEEN ? and ?   " +
		  " and ( vi.controlaccount in  ("+ controlaccount +")  or vi.controlaccount like '200%' )"  +
		 " and  vi.businessitemid in (    select rfi.refundmentitemid   from yrefundmentitem rfi	  where rfi.project_no in (" +
		 "select pri.project_no from t_project_info pri where pri.project_id in  (?))   )    " +
		 "  group by vi.debitcredit";
		
		String sql3 ="select vi.debitcredit,vi.amount2,v.checkdate	  from yvoucheritem vi, yvoucher v " +
		  " where vi.voucherid = v.voucherid    and trim(v.voucherno) is not null    and v.processstate <> '-1'" +
		  "   and  v.checkdate BETWEEN ? and ?   " +
		  " and ( vi.controlaccount in  ("+ controlaccount +")  or vi.controlaccount like '200%' )"  +
		  " and  vi.businessitemid in ( select ci.collectitemitemid    from ycollectitem ci ,ycollect c   where ci.collectid=c.collectid and c.collecttype !='07' and ci.project_no in  (" +
		  " select pri.project_no from t_project_info pri where pri.project_id in (?))	   )" +
		  "  group by vi.debitcredit";
		
		List list = this.getJdbcTemplate().queryForList(sql1,new Object[]{strDate,endData,projectId});
		List list2 = this.getJdbcTemplate().queryForList(sql2,new Object[]{strDate,endData,projectId});
		List list3 = this.getJdbcTemplate().queryForList(sql3,new Object[]{strDate,endData,projectId});
		list.addAll(list2);
		list.addAll(list3);
		
		return list;	
    }
	
	/**
	 * 更新立项信息为有效状态
	 */
	public void closeProject(String projectId){
		String sql = "update t_project_info set project_state=9  where project_Id=?";
		this.getJdbcTemplate().update(sql, new Object[]{projectId});		
	}

}
