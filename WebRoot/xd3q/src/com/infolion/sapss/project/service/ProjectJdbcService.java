/*
 * @(#)ProjectJdbcService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-2-10
 *  描　述：创建
 */

package com.infolion.sapss.project.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.JbpmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.bapi.ConnectManager_bw;
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.common.dto.CommonChangeDto;
import com.infolion.sapss.project.dao.ApplyCustomerCreditHibernate;
import com.infolion.sapss.project.dao.ApplyProviderCreditHibernate;
import com.infolion.sapss.project.dao.ProjectAccountingJdbcDao;
import com.infolion.sapss.project.dao.ProjectAccountingTypeJdbcDao;
import com.infolion.sapss.project.dao.ProjectInfoJdbcDao;
import com.infolion.sapss.project.domain.TApplyCustomerCredit;
import com.infolion.sapss.project.domain.TApplyProviderCredit;
import com.infolion.sapss.project.domain.TProjectAccounting;
import com.infolion.sapss.project.domain.TProjectInfo;
import com.infolion.sapss.project.dto.ProjectPrintDto;
import com.infolion.sapss.project.web.ProjectDetailVO;
import com.infolion.sapss.workflow.ProcessCallBack;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
@Service
public class ProjectJdbcService  extends BaseJdbcService implements ProcessCallBack{
    
    private final String ZFIRPTGL4 = "ZFIRPTGL4";
	@Autowired
	private ProjectAccountingJdbcDao projectAccountingJdbcDao;

	public void setProjectAccountingJdbcDao(ProjectAccountingJdbcDao sampleDao) {
		this.projectAccountingJdbcDao = sampleDao;
	}

	@Autowired
	private ProjectAccountingTypeJdbcDao projectAccountingTypeJdbcDao;

	public void setProjectAccountingTypeJdbcDao(ProjectAccountingTypeJdbcDao sampleDao) {
		this.projectAccountingTypeJdbcDao = sampleDao;
	}
	@Autowired
	private ProjectInfoJdbcDao projectInfoJdbcDao;

	public void setProjectInfoJdbcDao(ProjectInfoJdbcDao sampleDao) {
		this.projectInfoJdbcDao = sampleDao;
	}
	
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	@Transactional(readOnly = true)
	public int deleteProjectInfo(String projectId){
		return this.projectInfoJdbcDao.delete(projectId);
	}
	/**
	 * 返回立项的最大编码
	 * @return
	 */
	public Map generateProjectNo(String deptId){
		return this.projectInfoJdbcDao.generateProjectNo(deptId);
	}
	/**
	 * 保存核算页面信息
	 * @param vo
	 * @return
	 */
	@Transactional(readOnly = true)
	public int saveDetailInfo(ProjectDetailVO vo){
		return this.projectInfoJdbcDao.saveDetailInfo(vo);
	}
	
	/**
	 * 保存核算信息
	 * @param list
	 * @return
	 */
	@Transactional(readOnly = true)
	public int saveOrUpdateProjectAccounting(List<TProjectAccounting> list){
		return this.projectAccountingJdbcDao.saveOrUpdateProjectAccounting(list);
	}
	/**
	 * 获取核算信息
	 * @param list
	 * @return
	 */
	public Map<String,TProjectAccounting> getProjectAccounting(String projectId){
		return this.projectAccountingJdbcDao.getProjectAccounting(projectId);
	}
	
	/**
	 * 获取核算信息
	 * @param list
	 * @return
	 */
	public Map<String,TProjectAccounting> getContractGroupAccounting(String contractGroupId){
		//去掉行为25是（合同预计利润率）是文本不能求和
		Map<String,TProjectAccounting> cga =this.projectAccountingJdbcDao.getContractGroupAccounting(contractGroupId);
		//重新计算单价=总金额/数量,2=3/1 28=29/27
		String  total_rmb="0";
		String num_rmb ="0";
		String  total_us ="0";
		String num_us ="0";
		//（合同预计利润率） = 预计利润总额或亏损总额*100/不含税货款
		String rmb_31 = "0";
		String us_31 = "0";
		for(TProjectAccounting pa :cga.values()){
			if(pa.getAccountingItemId()==3 && "RMB".equals(pa.getCurrency()))total_rmb=pa.getAccountingValue();			
			if(pa.getAccountingItemId()==1 && "RMB".equals(pa.getCurrency()))num_rmb =pa.getAccountingValue();
			if(pa.getAccountingItemId()==31 && "RMB".equals(pa.getCurrency()))rmb_31 =pa.getAccountingValue();
			if(pa.getAccountingItemId()==3 && "US".equals(pa.getCurrency()))total_us=pa.getAccountingValue();
			if(pa.getAccountingItemId()==1 && "US".equals(pa.getCurrency()))num_us =pa.getAccountingValue();
			if(pa.getAccountingItemId()==31 && "US".equals(pa.getCurrency()))us_31 =pa.getAccountingValue();
		}
		if(StringUtils.isNotBlank(num_rmb) && !"0".equals(num_rmb)){
			BigDecimal dj_rmb=new BigDecimal(total_rmb).divide(new BigDecimal(num_rmb),3,BigDecimal.ROUND_HALF_EVEN);
			BigDecimal yjln_rmb=new BigDecimal(rmb_31).divide(new BigDecimal(num_rmb),3,BigDecimal.ROUND_HALF_EVEN);
			for(TProjectAccounting pa :cga.values()){
				if(pa.getAccountingItemId()==2 && "RMB".equals(pa.getCurrency()) )pa.setAccountingValue(dj_rmb.toString());	
				if(pa.getAccountingItemId()==25 && "RMB".equals(pa.getCurrency()) )pa.setAccountingValue(yjln_rmb.toString());	
			}
		}
		if( StringUtils.isNotBlank(num_us) && !"0".equals(num_us)){
			BigDecimal dj_us=new BigDecimal(total_us).divide(new BigDecimal(num_us),3,BigDecimal.ROUND_HALF_EVEN);
			BigDecimal yjln_us=new BigDecimal(us_31).divide(new BigDecimal(num_rmb),3,BigDecimal.ROUND_HALF_EVEN);
			for(TProjectAccounting pa :cga.values()){
				if(pa.getAccountingItemId()==2 && "US".equals(pa.getCurrency()) )pa.setAccountingValue(dj_us.toString());
				if(pa.getAccountingItemId()==25 && "US".equals(pa.getCurrency()) )pa.setAccountingValue(yjln_us.toString());
			}
		}		
		
		//取（合同预计利润率）
//		Map<String,TProjectAccounting> cga2 =this.projectAccountingJdbcDao.getContractGroupAccounting(contractGroupId,"25");
//		for(TProjectAccounting pa :cga2.values()){
//			cga.put((cga.size()+1) + "", pa);
//		}
		//取（单位）
		Map<String,TProjectAccounting> cga2 =this.projectAccountingJdbcDao.getContractGroupAccounting(contractGroupId,"4");
		for(TProjectAccounting pa :cga2.values()){
			cga.put((cga.size()+1) + "", pa);
		}
		return cga;
	}
	
	/**
	 * 判断是否是关联单位
	 * @param id
	 * @return
	 */
	public boolean isRelateCompany(String id){
		boolean yes_no = false;
		if(id!=null&& !"".equals(id.trim())){
			for(int pos=0;pos<id.length();pos++){
				if(id.charAt(pos)=='0'){
					continue;
				}else if(id.charAt(pos)=='2'){// || id.charAt(pos)=='1'内部交易不经过证券部
					yes_no = true;
					return yes_no;
				}else
					break;
			}	
		}
		return yes_no;
	}
	/**
	 * 提交流程
	 * @param projectInfo
	 * @return
	 */
	public void submitWorkflow(String taskId,TProjectInfo projectInfo){
		Map<String,String> map = new HashMap<String,String>();
		String currency = projectInfo.getCurrency();
		BigDecimal exchangeRate = projectInfo.getExchangeRate();
		if(exchangeRate==null){
			if("CNY".equals(currency)){
				exchangeRate = new BigDecimal(1.0);
			}else
				exchangeRate = new BigDecimal(7.0);
		}
		String sum = projectInfo.getSum();
		String total = "";
		if(!"USD".equals(currency))
			total = String.valueOf(Double.valueOf(sum).doubleValue()*exchangeRate.intValue());
		else
			total = sum;
		map.put("_workflow_total_value",total);
		map.put("_workflow_currency",currency);
		//判断关联单位:
		boolean customer = isRelateCompany(projectInfo.getCustomerId());
		boolean provider = isRelateCompany(projectInfo.getProviderId());
		String yes_no="no";
		if(customer || provider)
			yes_no="yes";
		map.put("_workflow_is_unit",yes_no);
		map.put("_workflow_dept_code",projectInfo.getDeptId());
		map.put("_is_credit", projectInfo.getIsCredit());
		map.put("_clear_profit_4cny", projectInfoJdbcDao.getClearProfit4CNY(projectInfo.getProjectId(), projectInfo.getDeptId(), exchangeRate.doubleValue()));
		projectInfo.setWorkflowUserDefineTaskVariable(map);
		//
		projectInfo.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("project_v1"));
			if(null==taskId||"".equals(taskId)){
				WorkflowService.createAndSignalProcessInstance(projectInfo, projectInfo.getProjectId());
				this.projectInfoJdbcDao.submitUpdateState(projectInfo.getProjectId(),"2");
			}else{
				WorkflowService.signalProcessInstance(projectInfo, projectInfo.getProjectId(), null);
			}
	}
	/**
	 * 回调更新立项状态
	 */
	public void updateBusinessRecord(String businessRecordId,String examineResult, String sapOrderNo) {
		 this.projectInfoJdbcDao.workflowUpdateState(businessRecordId,examineResult);
	}
	/**
	 * 更新立项主信息
	 * @param info
	 */
	public int updateProjectMainInfo(TProjectInfo info){
		return this.projectInfoJdbcDao.updateProjectMainInfo(info);
	}
	/**
	 * 直接更新立项信息为通过状态
	 */
	public void updateProjectToPass(String projectId){
		 this.projectAccountingJdbcDao.updateProjectToPass(projectId);
	}
	/**
	 * 新增立项中，备注项未添加，现补充
	 */
	public void insertProjectMemo(){
		this.projectInfoJdbcDao.insertProjectMemo();
	}
	/**
	 * 取得立项核算项的值
	 * @param projectId
	 * @param string
	 * @param string2
	 * @return
	 */
	public String getProjectAccountValue(String projectId, String currency,String item) {
		return this.projectInfoJdbcDao.getProjectAccountValue(projectId,currency,item);
	}
	
	public String queryProjectIdByNo(String projectNo){
		return this.projectInfoJdbcDao.queryProjectIdByNo(projectNo);
	}
	
	public List<ProjectPrintDto> queryProjects4Print(String[] projectIds){
		return this.projectInfoJdbcDao.queryProjects4Print(projectIds);
	}
	public List<CommonChangeDto> queryChangeDtos(String projectId){
		return this.projectInfoJdbcDao.queryChangeDtos(projectId);
	}
	
	/**
	 * 得到客户的信用额度
	 * @param strProjectId
	 * @return
	 */
	public TApplyCustomerCredit getCustomerCredit(String strProjectId){
		return this.projectInfoJdbcDao.getCustomerCredit(strProjectId);
	}
	
	/**
	 * 得到供应商信用额度
	 */
	public TApplyProviderCredit getProviderCredit(String strProjectId){
		return this.projectInfoJdbcDao.getProviderCredit(strProjectId);
	}
	
	/**
	 * 取得外围进仓信息
	 * @param ageVouchers
	 */
	public Map<String,String> fetchReceItem(String projectId){
        HashMap<String, String> valueMap = new HashMap<String, String>();
        List<Map<String,Object>> list = this.projectInfoJdbcDao.queryReceByProjectId(projectId);
        for(Map map : list  ){
            this.putValue(valueMap, "rmbsp3", String.valueOf(map.get("TOTAL")));  // 总额
            this.putValue(valueMap, "rmbsp1", String.valueOf(map.get("QUANTITY"))); //数量
            this.putValue(valueMap, "rmbsp2", String.valueOf(map.get("PRICE"))); // 单价
        }
	    return valueMap;
	}
	    
	/**
	 * 取得外围进仓信息
	 * @param ageVouchers
	 */
	public Map<String,String> fetchReceItemByContractGroupid(String contractGroupid){
        HashMap<String, String> valueMap = new HashMap<String, String>();
        List<Map<String,Object>> list = this.projectInfoJdbcDao.queryReceByContractGroupid(contractGroupid);
        for(Map map : list  ){
            this.putValue(valueMap, "rmbsp3", String.valueOf(map.get("TOTAL")));  // 总额
            this.putValue(valueMap, "rmbsp1", String.valueOf(map.get("QUANTITY"))); //数量
            this.putValue(valueMap, "rmbsp2", String.valueOf(map.get("PRICE"))); // 单价
        }
	    return valueMap;
	}
	
	   /**
     * 取得SAP其它立项信息
     * @param ageVouchers
     */
    public Map<String,String> fetchSAPAccountingItem(String projectId,String contractGroupId, String strDate, String endData){
        
        List<Map<String,Object>> sap_sel_list = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> sap_pur_list = new ArrayList<Map<String,Object>>();
        //根据立项查
        if(StringUtils.isNotBlank(projectId) && StringUtils.isNullBlank(contractGroupId) ){
	        sap_sel_list = this.projectInfoJdbcDao.querySelSapNoByProjectId(projectId);
	        sap_pur_list = this.projectInfoJdbcDao.queryPurSapNoByProjectId(projectId);
        }
        //根据合同组查
        if(StringUtils.isNotBlank(contractGroupId) && StringUtils.isNotBlank(projectId) ){
	        sap_sel_list = this.projectInfoJdbcDao.querySelSapNoBycontractGroupId(contractGroupId);
	        sap_pur_list = this.projectInfoJdbcDao.queryPurSapNoBycontractGroupId(contractGroupId);
        }
//        查找单个利率不用
        String rate = this.projectInfoJdbcDao.queryRateByDay(endData);
//        查找利率期间
        List<Map<String,Object>> rates = this.projectInfoJdbcDao.queryRateByDay(strDate,endData);
        
        JCO.Table returnDatas = null;
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        
        ConnectManager_bw manager = ConnectManager_bw.manager;
        manager.getClient();
        JCO.Client client = null;
        JCO.Table t_sv = null;   
        try
        {
            IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate(this.ZFIRPTGL4);
            if (ftemplate != null)
            {
                JCO.Function function = ftemplate.getFunction();
                client = JCO.getClient(manager.poolName);   
                t_sv = function.getTableParameterList().getTable("P_SEL_EBELNS");
                for(Map map : sap_sel_list  ){
                    t_sv.appendRow();
                    t_sv.setValue((String)map.get("SAP_ORDER_NO"), "STR");
                }
                t_sv = function.getTableParameterList().getTable("P_PUR_EBELNS");
                for(Map<String,Object> map : sap_pur_list){
                    t_sv.appendRow();
                    t_sv.setValue((String)map.get("SAP_ORDER_NO"), "STR");
                }      
                t_sv = function.getTableParameterList().getTable("P_RATES");
                for(Map<String,Object> map : rates){
                    t_sv.appendRow();
                    t_sv.setValue((String)map.get("startdate"), "STRDATE");
                    t_sv.setValue((String)map.get("enddate"), "ENDDATA");
//                  取是利率除100，如保存是6.5计算是0.065
                    BigDecimal ra = (BigDecimal)map.get("rate");
                    ra=ra.divide(new BigDecimal("100"),8,BigDecimal.ROUND_HALF_EVEN);
                    t_sv.setValue(ra.toString(), "RATE");
                } 
                JCO.ParameterList input = function.getImportParameterList();
                input.setValue(strDate, "P_STRDATE");
                input.setValue(endData, "P_ENDDATE");
                input.setValue(projectId, "P_PROJECTID");
//                input.setValue(contractGroupId, "P_CONTRACTGROUPID");
                input.setValue(rate, "P_RATE");
                
                client.execute(function);


                returnDatas = function.getTableParameterList().getTable("OUT_ZFIGL4");
                System.out.println(returnDatas.toString());
                list = ExtractSapData.getList(returnDatas);
            }
            else
            {
                log.error("---------------：SAP立项执行情况，目标系统上不存在RFC" + ZFIRPTGL4);
            }
        }
        catch (Exception ex)
        {
            log.error("----------------SAP立项执行情况，数据发生错误" + ex);
        }
        finally
        {
            manager.cleanUp();
        }
        
        BigDecimal lx = new BigDecimal("0");
        try {
			lx =this.getHKLX(projectId, contractGroupId, strDate, endData, rates);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        String hkont = "";
        String dmbtr = "";
        String ussdmbtr = "";
        HashMap<String, String> valueMap = new HashMap<String, String>();
        for (Map<String,String> map : list) {
            hkont = map.get("/BIC/HKONT");
            dmbtr = map.get("/BIC/DMBTR");
            ussdmbtr = map.get("/BIC/USDMBTR");
            
            if ("7001000003".equals(hkont) || "7001000004".equals(hkont)) { // 运杂费、装卸费-h-i
                this.putValue(valueMap, "rmbs8", dmbtr);
                this.putValue(valueMap, "uss8", ussdmbtr);

            }
            if ("7001000001".equals(hkont) || "7001000002".equals(hkont) || "7001000003".equals(hkont) || "7001000004".equals(hkont)) { // 运杂费、装卸费-p
                this.putValue(valueMap, "rmbs8_p", dmbtr);
                this.putValue(valueMap, "uss8_p", ussdmbtr);
            }

            if ("7001000001".equals(hkont) || "7001000004".equals(hkont)) { // 运杂费、装卸费-e
                this.putValue(valueMap, "rmbs8_e", dmbtr);
                this.putValue(valueMap, "uss8_e", ussdmbtr);

            }
            
            if ("7001000005".equals(hkont)) { // 码头费-h-i-e
                this.putValue(valueMap, "rmbs9", dmbtr);
                this.putValue(valueMap, "uss9", ussdmbtr);

            }
            if ("7001000010".equals(hkont)) { // 仓储费-h-i-e
                this.putValue(valueMap, "rmbs10", dmbtr);
                this.putValue(valueMap, "uss10", ussdmbtr);

            }
            if ("7001000009".equals(hkont)) { // 保险费-h
                this.putValue(valueMap, "rmbs11", dmbtr);
                this.putValue(valueMap, "uss11", ussdmbtr);

            }
            if ("7001000051".equals(hkont) || "7001000040".equals(hkont)) { // 间接管理(差旅费、执行费、邮电费)-h
                this.putValue(valueMap, "rmbs12", dmbtr);
                this.putValue(valueMap, "uss12", ussdmbtr);

            }
            if ("7001000030".equals(hkont)) { // 佣金或手续费-h
                this.putValue(valueMap, "rmbs13", dmbtr);
                this.putValue(valueMap, "uss13", ussdmbtr);

            }
            if ("6603010003".equals(hkont)) { // 贴现利息-h-e
                this.putValue(valueMap, "rmbs18", dmbtr);
                this.putValue(valueMap, "uss18", ussdmbtr);

            }
            if ("6603020001".equals(hkont) || "6603020002".equals(hkont) || "6603020003".equals(hkont)
                    || "6603020004".equals(hkont)) { // 银行结算费-h
                this.putValue(valueMap, "rmbs19", dmbtr);
                this.putValue(valueMap, "uss19", ussdmbtr);

            }
            if ("6403010001".equals(hkont) || "6403010002".equals(hkont)) { // 营业税及附加费（5.55%）-h-p
                this.putValue(valueMap, "rmbs22", dmbtr);
                this.putValue(valueMap, "uss22", ussdmbtr);

            }
            if ("2221010001".equals(hkont)) { // 海关关税-i 出口关税-e
                this.putValue(valueMap, "rmbs40", dmbtr);
                this.putValue(valueMap, "uss40", ussdmbtr);

            }
            if ("2221050001".equals(hkont)) { // 海关增值税-i
                this.putValue(valueMap, "rmbs41_i", dmbtr);
                this.putValue(valueMap, "uss41_i", ussdmbtr);

            }
            if ("2221020001".equals(hkont)) { // 海关消费税-i
                this.putValue(valueMap, "rmbs42_i", dmbtr);
                this.putValue(valueMap, "uss42_i", ussdmbtr);

            }
            if ("7001000002".equals(hkont)) { // 进口运费-i
                this.putValue(valueMap, "rmbs4_i", dmbtr);
                this.putValue(valueMap, "uss4_i", ussdmbtr);

            }
            if ("7001000009".equals(hkont)) { // 进口保险费-i
                this.putValue(valueMap, "rmbs5_i", dmbtr);
                this.putValue(valueMap, "uss5_i", ussdmbtr);

            }
            if ("6603010005".equals(hkont) || "6603010006".equals(hkont)) { // 货款利息-i-e
                this.putValue(valueMap, "rmbs17", dmbtr);
                this.putValue(valueMap, "uss17", ussdmbtr);

            }
            if ("6603010002".equals(hkont)) { // 押汇利息-i
                this.putValue(valueMap, "rmbs47_i", dmbtr);
                this.putValue(valueMap, "uss47_i", ussdmbtr);

            }
            if ("6603010003".equals(hkont)) { // 贴现利息-i
                this.putValue(valueMap, "rmbs48", dmbtr);
                this.putValue(valueMap, "uss48", ussdmbtr);

            }
            if ("7001000011".equals(hkont) || "7001000012".equals(hkont) ) { // 报关、报检费等-e
                this.putValue(valueMap, "rmbs11_e", dmbtr);
                this.putValue(valueMap, "uss11__e", ussdmbtr);

            }
            if ("6603010001".equals(hkont) || "6603010002".equals(hkont) ) { // 押汇利息-p
                this.putValue(valueMap, "rmbs43_p", dmbtr);
                this.putValue(valueMap, "uss43_p", ussdmbtr);
            }
            if ("6001010001".equals(hkont) || "6001010002".equals(hkont) ) { // 代理收入（开票金额）-p
                this.putValue(valueMap, "rmbs46_p", dmbtr);
                this.putValue(valueMap, "uss46_p", ussdmbtr);
            }
            if ("2221050004".equals(hkont)) { // 增值税及附加费 （出口征税填此栏）-e
                this.putValue(valueMap, "rmbs21", dmbtr);
                this.putValue(valueMap, "uss21", ussdmbtr);

            }
            if ("6603050101".equals(hkont) || "6603050201".equals(hkont) ||  "6603050301".equals(hkont)  ||  "6603050302".equals(hkont) 
            		 ||  "6603050401".equals(hkont)  ||  "6603050402".equals(hkont)  ||  "6603050501".equals(hkont)  ||  "6603050502".equals(hkont)
            		 ||  "6603050503".equals(hkont)  ||  "6603050504".equals(hkont)  ||  "6603050505".equals(hkont)  ||  "6603050506".equals(hkont)
            		 ||  "6603050601".equals(hkont)  ||  "6603050602".equals(hkont)  ||  "6603050701".equals(hkont)  ||  "6603050702".equals(hkont)            		
            		 ) { // 财务费用里面的其他-关联损汇科目
                this.putValue(valueMap, "rmbs49", dmbtr);
                this.putValue(valueMap, "uss49", ussdmbtr);

            }
            if ("SPCGCB_E".equals(hkont)) { // -i 商品购进成本- 数量
                this.putValue(valueMap, "rmbs1", dmbtr);
                this.putValue(valueMap, "uss1", ussdmbtr);

            }
            if ("SPCGCB_CE".equals(hkont)) { // -i 商品购进成本- 单价
                this.putValue(valueMap, "rmbs2", dmbtr);
                this.putValue(valueMap, "uss2", ussdmbtr);

            }
            if ("SPCGCB_A".equals(hkont)) { // -i 商品购进成本- 税率
                this.putValue(valueMap, "rmbs40", dmbtr);
                this.putValue(valueMap, "uss40", ussdmbtr);

            }
            if ("SPCGCB_C".equals(hkont)) { // -i 商品购进成本- 货款总额
                this.putValue(valueMap, "rmbs3", dmbtr);
                this.putValue(valueMap, "uss3", ussdmbtr);

            }
            if ("SPCGCB_C1".equals(hkont)) { // -i 商品购进成本- 不含税货款
                this.putValue(valueMap, "rmbs41", dmbtr);
                this.putValue(valueMap, "uss41", ussdmbtr);

            }
            if ("SPCGCB_N".equals(hkont)) { // -i 商品购进成本- 进项税金
                this.putValue(valueMap, "rmbs42", dmbtr);
                this.putValue(valueMap, "uss42", ussdmbtr);

            }
            if ("SPSSJ_E".equals(hkont)) { // -i 商品销售价- 数量
                this.putValue(valueMap, "rmbs27", dmbtr);
                this.putValue(valueMap, "uss27", ussdmbtr);

            }
            if ("SPSSJ_CE".equals(hkont)) { // -i 商品销售价- 单价
                this.putValue(valueMap, "rmbs28", dmbtr);
                this.putValue(valueMap, "uss28", ussdmbtr);

            }
            if ("SPSSJ_D".equals(hkont)) { // -i 商品销售价- 税率
                this.putValue(valueMap, "rmbs45", dmbtr);
                this.putValue(valueMap, "uss45", ussdmbtr);

            }
            if ("SPSSJ_B".equals(hkont)) { // -i 商品销售价- 货款总额
                this.putValue(valueMap, "rmbs29", dmbtr);
                this.putValue(valueMap, "uss29", ussdmbtr);

            }
            if ("SPSSJ_C1".equals(hkont)) { // -i 商品销售价- 不含税货款
                this.putValue(valueMap, "rmbs46", dmbtr);
                this.putValue(valueMap, "uss46", ussdmbtr);

            }
            if ("SPSSJ_M".equals(hkont)) { // -i 商品销售价- 销项税金
                this.putValue(valueMap, "rmbs47", dmbtr);
                this.putValue(valueMap, "uss47", ussdmbtr);

            }
            if ("MN".equals(hkont)) { // -i 税收- 增值税及附加费
                this.putValue(valueMap, "rmbs21", dmbtr);
                this.putValue(valueMap, "uss21", ussdmbtr);

            }
            if ("BC".equals(hkont)) { // -i 税收- 印花税
                this.putValue(valueMap, "rmbs23", dmbtr);
                this.putValue(valueMap, "uss23", ussdmbtr);
            }
            if ("HKLX".equals(hkont)) { // -i e h  货款利息
            	if(StringUtils.isNullBlank(dmbtr))dmbtr="0";
            	BigDecimal dm=lx.add(new BigDecimal(dmbtr));
                this.putValue(valueMap, "rmbs17", dm.toString());
                this.putValue(valueMap, "uss17", ussdmbtr);
            }
            valueMap.put(hkont, dmbtr);
        }
        String s2221050004 = (String)valueMap.get("2221050004");
        String s2221050002 = (String)valueMap.get("2221050002");
        if (StringUtils.isNullBlank(s2221050004) || StringUtils.isNullBlank(s2221050002)) {
            Double d = 0.0;
            try {
                d = Double.valueOf(s2221050004) - Double.valueOf(s2221050002) * .111;
            } catch (Exception e) {
            }
            if (d > 0) {
                this.putValue(valueMap, "rmbs21_p", d.toString());
            } else {
                this.putValue(valueMap, "rmbs21_p", "0");
            }
        }
        
        return valueMap;
    }
    
//    取 得外围的利息
//    应收票据 在收付款中都有，收票据，付票据。应收票据（1121000001）
//    应付票据 在收付款中都有，收票据，付票据 应付票据（2201010001）
//    应付账款 在付款，供应商退款中都有 应付账款（2202010002-3）
//    内部往来-贷 -1241010002
    public BigDecimal getHKLX(String projectId,String contractGroupId, String strDate, String endData,String rate) throws ParseException{
    	Calendar cal = Calendar.getInstance();
    	Date	strDate2 = (new SimpleDateFormat("yyyyMMdd")).parse(strDate);
    	Date	endDate2 = (new SimpleDateFormat("yyyyMMdd")).parse(endData);
		cal.setTime(strDate2);
		BigDecimal aunout = new BigDecimal("0");
//		金额小数位数为3位,为避免精度丢失,先把利率乘以100,最后总数再除以100,再除以365天(年利率)
		BigDecimal rate2 = new BigDecimal(rate).multiply(new BigDecimal("100"));	
		while(strDate2.compareTo(endDate2) <=0){
			
			String end = (new SimpleDateFormat("yyyyMMdd")).format(strDate2);
			if(StringUtils.isNotBlank(contractGroupId)){
	    		
				aunout =this.projectInfoJdbcDao.getHKLXByContractGroup(contractGroupId, strDate, end);
	    	}else{
	    		aunout =this.projectInfoJdbcDao.getHKLXByProject(projectId, strDate, end);
	    	}
			aunout = aunout.add(aunout.multiply(rate2));
			cal.add(Calendar.DATE, 1);
			strDate2 =cal.getTime();
		}
//		金额小数位数为3位,为避免精度丢失,先把利率乘以100,最后总数再除以100,再除以360天(年利率)
		BigDecimal lx = aunout.divide(new BigDecimal("36000"), 3,BigDecimal.ROUND_HALF_EVEN);
    	return lx;
    }
    
//  取 得外围的利息
//  应收票据 在收付款中都有，收票据，付票据。应收票据（1121000001）
//  应付票据 在收付款中都有，收票据，付票据 应付票据（2201010001）
//  应付账款 在付款，供应商退款中都有 应付账款（2202010002-3）
//  内部往来-贷 -1241010002
  public BigDecimal getHKLX(String projectId,String contractGroupId, String strDate, String endData,List<Map<String,Object>> rates) throws ParseException{
  	Calendar cal = Calendar.getInstance();
	Date	strDate_2 = (new SimpleDateFormat("yyyyMMdd")).parse(strDate);
  	Date	strDate2 = (new SimpleDateFormat("yyyyMMdd")).parse(strDate);
  	Date	endDate2 = (new SimpleDateFormat("yyyyMMdd")).parse(endData);
		cal.setTime(strDate2);
		BigDecimal aunout = new BigDecimal("0");
//		先取出数据到list 中
		List list = new ArrayList();
		if(StringUtils.isNotBlank(contractGroupId)){
    		
			list =this.projectInfoJdbcDao.getHKLXByContractGroup2(contractGroupId, strDate, endData);
    	}else{
    		list =this.projectInfoJdbcDao.getHKLXByProject2(projectId, strDate, endData);
    	}

		while(strDate2.compareTo(endDate2) <=0){
			BigDecimal ra = new BigDecimal("0");
			for(Map<String,Object> map : rates){        
				String startdate2 =(String)map.get("startdate");
		        String enddate2 = (String)map.get("enddate");
		        
		        Date	strDate3 = (new SimpleDateFormat("yyyyMMdd")).parse(startdate2);
		      	Date	endDate3 = (new SimpleDateFormat("yyyyMMdd")).parse(enddate2);
//		         取是利率已经乘以100
				if(strDate2.compareTo(strDate3) >=0 && strDate2.compareTo(endDate3) <=0){
		           ra = (BigDecimal)map.get("rate");
				}
		      } 
			BigDecimal amount4 = new BigDecimal("0");
			if(list!=null && list.size()>0){				
				for(int i=0;i<list.size();i++){
					String checkdate = (String)((Map)list.get(i)).get("checkdate");
					Date	checkdate2 = (new SimpleDateFormat("yyyyMMdd")).parse(checkdate);
					String debitcredit = (String)((Map)list.get(i)).get("debitcredit");
					BigDecimal amount2 = (BigDecimal)((Map)list.get(i)).get("amount2");
					if(checkdate2.compareTo(strDate_2) >=0 && checkdate2.compareTo(strDate2) <=0){
						
						if("H".equals(debitcredit)){
							amount2 = new BigDecimal("0").subtract( amount2);						}
						amount4 = amount4.add(amount2.multiply(ra));
					}
				}		
			}
			aunout = aunout.add(amount4);
			cal.add(Calendar.DATE, 1);
			strDate2 =cal.getTime();
		}
//		金额小数位数为3位,为避免精度丢失,先把利率乘以100,最后总数再除以100,再除以360天(年利率)
		BigDecimal lx = aunout.divide(new BigDecimal("36000"), 3,BigDecimal.ROUND_HALF_EVEN);
  	return lx;
  }
  
    private static void putValue(HashMap<String, String> map,String key, String value){
        if (map.containsKey(key)) {
            Double d = Double.valueOf(map.get(key).toString()) + Double.valueOf( value );
//            把Double/double型数据由科学计数法转成普通表示
            BigDecimal big = new BigDecimal(d);
            big = big.setScale(4, 2);            
            map.put(key, big.toString());
        } else {
            map.put(key, value);
        }
    }
    
	public void fileProject(String idList){
		String[] toIdList = idList.split("\\|");
		for (int i = 0; i < toIdList.length; i++) {
			projectInfoJdbcDao.fileProject(toIdList[i]);
		}
	}

	
	public static void main(String[] args) throws ParseException
	{
		String strDate ="20150121";
		String endData ="20150202";
		Calendar cal = Calendar.getInstance();
    	Date	strDate2 = (new SimpleDateFormat("yyyyMMdd")).parse(strDate);
    	Date	endDate2 = (new SimpleDateFormat("yyyyMMdd")).parse(endData);
		cal.setTime(strDate2);
		while(strDate2.compareTo(endDate2) <=0){
			
			String end = (new SimpleDateFormat("yyyyMMdd")).format(strDate2);
			System.out.println(end);
			cal.add(Calendar.DATE, 1);
			strDate2 =cal.getTime();
			
		}
    	
		
    	
	}

	
	public void closeProject(String idList){
		String[] toIdList = idList.split("\\|");
		for (int i = 0; i < toIdList.length; i++) {
			projectInfoJdbcDao.closeProject(toIdList[i]);
		}
	}

}
