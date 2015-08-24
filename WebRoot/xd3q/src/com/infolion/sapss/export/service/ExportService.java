package com.infolion.sapss.export.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.bill.domain.TBillApply;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.contract.domain.TContractBom;
import com.infolion.sapss.export.dao.ExportBillExamDHibernateDao;
import com.infolion.sapss.export.dao.ExportJdbcDao;
import com.infolion.sapss.export.dao.ExportShipmentStatHibernateDao;
import com.infolion.sapss.export.dao.ExportBillsHibernateDao;
import com.infolion.sapss.export.domain.TExportBillExamD;
import com.infolion.sapss.export.domain.TExportShipmentStat;
import com.infolion.sapss.export.domain.TExportBills;
import com.infolion.sapss.export.domain.TExportBillExam;
import com.infolion.sapss.export.dao.ExportBillExamHibernateDao;
import com.infolion.sapss.project.dao.ProjectInfoHibernate;
import com.infolion.sapss.project.domain.TProjectInfo;
import com.infolion.sapss.workflow.ProcessCallBack;
import com.infolion.xdss3.collect.dao.CollectHibernateDao;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collect.service.CollectService;
import com.infolion.xdss3.overCollect.domain.OverCollect;
import com.infolion.xdss3.overCollect.dao.OverCollectHibernateDao;
import com.infolion.xdss3.profitLoss.domain.ProfitLoss;

@Service
public class ExportService extends BaseHibernateService {

	@Autowired
	private ExportJdbcDao exportJdbcDao;
	@Autowired
	private ExportShipmentStatHibernateDao exportShipmentStatHibernateDao;
	@Autowired
	private ExportBillsHibernateDao exportBillsHibernateDao;

	@Autowired
	private ExportBillExamHibernateDao exportBillExamHibernateDao;
	@Autowired
	private ExportBillExamDHibernateDao exportBillExamDHibernateDao;
	@Autowired		
	private OverCollectHibernateDao overCollectHibernateDao;
	@Autowired
	private ProjectInfoHibernate projectInfoHibernate;
	@Autowired	
	private CollectHibernateDao collectHibernateDao;
	
	@Autowired	
	private CollectService collectService;

	public void setCollectService(CollectService collectService) {
		this.collectService = collectService;
	}
	public ExportJdbcDao getExportJdbcDao() {
		return exportJdbcDao;
	}

	public void setExportJdbcDao(ExportJdbcDao exportJdbcDao) {
		this.exportJdbcDao = exportJdbcDao;
	}

	public ExportShipmentStatHibernateDao getExportShipmentStatHibernateDao() {
		return exportShipmentStatHibernateDao;
	}

	public void setExportShipmentStatHibernateDao(
			ExportShipmentStatHibernateDao exportShipmentStatHibernateDao) {
		this.exportShipmentStatHibernateDao = exportShipmentStatHibernateDao;
	}

	public void saveExportStatObject(TExportShipmentStat obj) {
		this.exportShipmentStatHibernateDao.saveOrUpdate(obj);
	}

	public void updateExportStatObject(TExportShipmentStat obj) {
		this.exportShipmentStatHibernateDao.update(obj);
	}

	public TExportShipmentStat getExportShipmentStat(String serializableID) {
		return this.exportShipmentStatHibernateDao.get(serializableID);
	}

	// ///////////////出口押汇开始 By Wang Yipu 2009.06.10///////////////
	public String verifyFilds(String taskId, TExportBills tExportBills) {
		String taskName = "";
		String rtn = "";
		if (!StringUtils.isEmpty(taskId)) {
			TaskInstanceContext taskInstanceContext = WorkflowService
					.getTaskInstanceContext(taskId);
			taskName = taskInstanceContext.getTaskName();
		}
		if (taskName.equals("资金部人员办理并填写实收金额及币别")
				|| taskName.equals("贸管审单员办理并填写实收金额及币别")) {
			if (tExportBills.getRealMoney() == null
					|| tExportBills.getRealMoney() == 0) {
				rtn = "请填写实际押汇金额！";
				return rtn;
			}
			exportBillsHibernateDao.saveOrUpdate(tExportBills);
		}
		return rtn;
	}

	public void updateExportBillsBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
		String examineState = "";
		if (ProcessCallBack.EXAMINE_SUCCESSFUL.equals(examineResult)) {
			examineState = "3";
		} else if (ProcessCallBack.NO_EXAMINE.equals(examineResult)) {
			examineState = "5";
		} else {
			examineState = "4";
		}
		TExportBills tExportBills = this.getExportBills(businessRecordId);
		tExportBills.setExamineState(examineState);
		tExportBills.setApprovedTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		this.saveExportBillsObject(tExportBills);
		//更新出口押汇，审单记录
		if (ProcessCallBack.EXAMINE_SUCCESSFUL.equals(examineResult)) {
			TExportBillExam exam = this.getExportBillExam(tExportBills.getExportBillExamId());
			exam.setIsNegotiat("是");
			this.updateExportBillExam(exam);
		}

		System.out.println("examineState" + examineState);
	}

	public void saveExportBillsObject(TExportBills obj) {
		this.exportBillsHibernateDao.saveOrUpdate(obj);
	}

	public void updateExportBillsObject(TExportBills obj) {
		this.exportBillsHibernateDao.update(obj);
	}

	public TExportBills getExportBills(String exportBillsID) {
		return this.exportBillsHibernateDao.get(exportBillsID);
	}

	public TExportBillExam getExportBillExam(String exportBillExamID) {
		return this.exportBillExamHibernateDao.get(exportBillExamID);
	}
	
	public void updateExportBillExam(TExportBillExam obj) {
		exportJdbcDao.assemble(obj);
		this.exportBillExamHibernateDao.saveOrUpdate(obj);
	}
	
	public TExportBillExam copyCreate(TExportBillExam target , String exportBillExamID) {
		TExportBillExam source = getExportBillExam(exportBillExamID);
		BeanUtils.copyProperties(source, target, new String[]{"exportBillExamId","creatorTime","creator","creatorDept"});
		List<TExportBillExamD> list = exportBillExamDHibernateDao.find("from TExportBillExamD a where a.exportBillExamId ='" + exportBillExamID + "'");
		for(TExportBillExamD d : list){
			TExportBillExamD n = new TExportBillExamD();
			BeanUtils.copyProperties(d, n);
			n.setCreatorTime(target.getCreatorTime());
			n.setCreator(n.getCreator());
			n.setExportBillExamId(target.getExportBillExamId());
			n.setExportBillExamDId(CodeGenerator.getUUID());
			exportBillExamDHibernateDao.save(n);
		}
		this.exportBillExamHibernateDao.saveOrUpdate(target);
		return target;
	}
	
	public void updateCollect(TExportBillExam obj,String oldCollectId){
//		Collect collect2 = collectHibernateDao.get(oldCollectId);
		Collect collect2 =this.collectService._get(oldCollectId);
		collect2.setExport_apply_no("");
		collectHibernateDao.saveOrUpdate(collect2);
		
		if(StringUtils.isNotBlank(obj.getCollectId())){
//			Collect collect = collectHibernateDao.get(obj.getCollectId());
			Collect collect =this.collectService._get(obj.getCollectId());
			if(!StringUtils.isNotBlank(obj.getCollectNo())){
				collect.setExport_apply_no("");
			}else{
				collect.setExport_apply_no(obj.getInvNo());
			}
			collectHibernateDao.saveOrUpdate(collect);
		}
	}
	public void updateCollect(TExportBillExam obj){		
//		Collect collect = collectHibernateDao.get(obj.getCollectId());
		Collect collect = collectService._get(obj.getCollectId());
		if(!StringUtils.isNotBlank(obj.getCollectNo())){
			collect.setExport_apply_no("");
		}else{
			collect.setExport_apply_no(obj.getInvNo());
		}
		collectHibernateDao.saveOrUpdate(collect);
	}
	public void delExportApplyNo(TExportBillExam obj){		
//		Collect collect = collectHibernateDao.get(obj.getCollectId());	
		Collect collect = collectService._get(obj.getCollectId());	
		collect.setExport_apply_no("");		
		collectHibernateDao.saveOrUpdate(collect);
	}
	/**
	 * 获得出单发票号 
	 * @param collectId
	 * @return
	 */
	public String getInvNoByCollect(String collectId){
//		Collect collect2 = collectHibernateDao.get(collectId);
		Collect collect2 = collectService._get(collectId);
		return collect2.getExport_apply_no();
	}
	
//	
//	 /* 保存出单审单表
//	 * @param obj
//	 * @param exportCcollectSet
//	 * @return
//	 */
//	public void updateExportBillExam(TExportBillExam obj,Set<ExportCcollect> exportCcollectSet) {
//		exportJdbcDao.assemble(obj);
//		this.exportBillExamHibernateDao.saveOrUpdate(obj);
//		this.SaveExportCcollect(obj, exportCcollectSet);
//	}
//	
//	
//	public void SaveExportCcollect(TExportBillExam obj,Set<ExportCcollect> exportCcollectSet){
//		
//		Iterator<ExportCcollect> items = exportCcollectSet.iterator();
//		while (items.hasNext()){
//			ExportCcollect exportCcollect =  (ExportCcollect) items.next();
//			exportCcollectHibernateDao.save(exportCcollect);
//		}
//	}
//
//	 /* 初始化出单清款表 
//	 * @param exportBillExamID
//	 * @return
//	 */	
//	public Set<ExportCcollect> initExportCcollect(String exportBillExamID){
//		Boolean isExist = false;
//		Set<ExportCcollect> exportCcollect = new HashSet();
//		//找出相同合同的多收款单
//		List<OverCollect> overCollectList1 = overCollectHibernateDao.find("select a from OverCollect a ,TExportBillExamD b , TContractSalesInfo c " +
//				"where a.contract_no = b.contractNo and a.contract_no = c.contractNo  and  b.exportBillExamId ='" + exportBillExamID + "' order by c.createTime");
//		//找出同立项没有合同号的多收款单(先取出立项号)
//		String projectNo = "";
//		List<TProjectInfo> tProjectInfoList =  projectInfoHibernate.find("select a from TProjectInfo a,TContractSalesInfo b, TExportBillExamD c " +
//				"where a.projectId = b.projectId and b.contractNo = c.contractNo and c.exportBillExamId ='" + exportBillExamID + "'");
//		if (tProjectInfoList != null){
//			for (int i=0;i< tProjectInfoList.size();i++){	
//				TProjectInfo tProjectInfo = (TProjectInfo) tProjectInfoList.get(i);
//				projectNo = projectNo + " '" +tProjectInfo.getProjectNo() + "'" + ",";
//			}
//		}
//		List<OverCollect> overCollectList2 = new ArrayList();
//		if (StringUtils.isNotBlank(projectNo)){
//			projectNo = projectNo.substring(0, projectNo.length()-1);
//			projectNo = "(" + projectNo + ")";
//			overCollectList2 = overCollectHibernateDao.find("select a from OverCollect a ,TProjectInfo b  " +
//					"where a.project_no = b.projectNo and a.project_no in " + projectNo + " order by b.createTime");
//		}
//		if (overCollectList2 != null){
//			for (int i=0;i< overCollectList2.size();i++){	
//				OverCollect OverCollect = (OverCollect) overCollectList2.get(i);
//				overCollectList1.add(OverCollect);
//			}
//		}
//		//取已存在的出单清款表 
//		List<ExportCcollect> exportCcollectList = exportCcollectHibernateDao.find("select a from ExportCcollect a where a.exportbillexamid = '" + exportBillExamID + "'");
//		//重新组合出单清款表
//		if (overCollectList1 != null){
//			for (int i=0;i< overCollectList1.size();i++){	
//				OverCollect overCollect = (OverCollect) overCollectList1.get(i);
//				//取对应收款单
//				Collect collect = collectHibernateDao.get(overCollect.getCollect().getCollectid());
//				//判断是否已经存在
//				isExist = false;
//				for(Iterator<ExportCcollect> it = exportCcollectList.iterator();it.hasNext();){
//					ExportCcollect temp = it.next();
//					if (temp.getOvercollectid().equals(overCollect.getOvercollectid())){
//						isExist = true;
//					}
//				}
//				//判断此收款单是否已经清帐,不存在则查询是否已清帐,清帐过的就不显示 
//				if (isExist == false){
//					int cnt =exportJdbcDao.getNotClear(collect.getCollectid());
//					if (cnt == 0)
//						continue;
//				}
//				ExportCcollect newExportCcollect = new ExportCcollect();
//				newExportCcollect.setOvercollectid(overCollect.getOvercollectid());
//				newExportCcollect.setExportbillexamid(exportBillExamID);
//				newExportCcollect.setCollectid(overCollect.getCollect().getCollectid());
//				//it.setProject_id(overCollect.getProject_no());
//				newExportCcollect.setProject_no(overCollect.getProject_no());
//				newExportCcollect.setContract_no(overCollect.getContract_no());
//				newExportCcollect.setCollectno(collect.getCollectno());
//				newExportCcollect.setCollectstate(collect.getCollectstate());
//				newExportCcollect.setSendgoodsdate(collect.getSendgoodsdate());
//				newExportCcollect.setCollectamount(overCollect.getOveramount());
//				newExportCcollect.setCurrency(collect.getCurrency());
//				//取已抵消收款
//				Long clearAmount = exportJdbcDao.getSumClearAmount(overCollect.getOvercollectid(), exportBillExamID);
//				newExportCcollect.setOffsetamount(new BigDecimal(clearAmount.toString()));
//				//在批抵消收款(取CollectCbill表的CLEARAMOUNT的值,收款单未结束，则计入在批抵消收款,收款单已结束，则计入已抵消收款)
//				BigDecimal onroadamount = BigDecimal.valueOf(0);  
//				newExportCcollect.setOnroadamount(onroadamount);  //未处理
//				//本次抵消收款
//				newExportCcollect.setClearamount(BigDecimal.valueOf(0));
//				exportCcollect.add(newExportCcollect);
//			}
//		}
//		//添加已存在的数据
//		if (exportCcollectList != null){
//			for (int i=0;i< exportCcollectList.size();i++){	
//				ExportCcollect oldExportCcollect = (ExportCcollect) exportCcollectList.get(i);
//				isExist = false;
//				for(Iterator<ExportCcollect> it = exportCcollect.iterator();it.hasNext();){
//					ExportCcollect newExportCcollect = it.next();
//					if (oldExportCcollect.getOvercollectid().equals(newExportCcollect.getOvercollectid())){
//						newExportCcollect.setClearamount(oldExportCcollect.getClearamount());
//						isExist = true;
//					}
//				}
//				if (isExist == false)
//					exportCcollect.add(oldExportCcollect);
//			}
//		}
//		//
//		return exportCcollect;
//	}
	
	/**
	 * 保存并提交出口押汇单
	 * 
	 * @param taskId
	 * @param tExportBills
	 */
	public void submitAndSaveExportBills(String taskId,
			TExportBills tExportBills) {
		tExportBills.setWorkflowProcessName(WorkflowUtils
				.chooseWorkflowName("export_negotiating_v1"));	
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		tExportBills.setExamineState("2");
		tExportBills.setApplyTime(DateUtils.getCurrDate(2));
		this.saveExportBillsObject(tExportBills);
		
		String workflowBusinessNote = userContext.getSysDept().getDeptname()
				+ "|" + userContext.getSysUser().getRealName() + "|";
		workflowBusinessNote = workflowBusinessNote + "出口押汇单号："
				+ tExportBills.getExportBillNo();
		tExportBills.setWorkflowBusinessNote(workflowBusinessNote);

		this.submitExportBills(taskId, tExportBills);
	}

	/**
	 * 提交出口押汇单
	 * 
	 * @param taskId
	 * @param tExportBills
	 * @return
	 */
	public void submitExportBills(String taskId, TExportBills tExportBills) {
		Map parameters = new HashMap();
		
		tExportBills.setWorkflowProcessName(WorkflowUtils
				.chooseWorkflowName("export_negotiating_v1"));		
		
		// 判断是否为TT收款
		TExportBillExam tExportBillExam = exportBillExamHibernateDao
				.get(tExportBills.getExportBillExamId());
		String billType = tExportBillExam.getBillType();
		if ("TT".equals(billType.toUpperCase()))
			parameters.put("_isTT", "TT");
		
		parameters.put("billType",billType.toUpperCase());
		
		tExportBills.setWorkflowUserDefineTaskVariable(parameters);

		if (null == taskId || "".equals(taskId)) {
			WorkflowService.createAndSignalProcessInstance(tExportBills,
					tExportBills.getExportBillId());
			tExportBills.setExamineState("2");
			this.saveExportBillsObject(tExportBills);
		} else {
			WorkflowService.signalProcessInstance(tExportBills, tExportBills
					.getExportBillId(), null);
		}
	}

	/**
	 * 提交审批流程
	 * 
	 * @param tExportBills
	 * @param taskId
	 * @return JSONObject
	 */
	public JSONObject submitExportBillsWorkflow(TExportBills tExportBills, String taskId) {
		JSONObject jo = new JSONObject();
		String msg = "";
		try {
			submitExportBills(taskId, tExportBills);
		} catch (Exception e) {
			msg = "异常类:" + e.getClass().getName() + "<br>异常信息:"
					+ e.getMessage();
			e.printStackTrace();
		}

		if ("".equals(msg))
			jo.put("returnMessage", "提交审批成功！  ");
		else
			jo.put("returnMessage", msg);

		return jo;
	}
	// //////////////////////////////////////////////////////////

	public void setExportBillExamDHibernateDao(
			ExportBillExamDHibernateDao exportBillExamDHibernateDao) {
		this.exportBillExamDHibernateDao = exportBillExamDHibernateDao;
	}

	public ExportBillExamDHibernateDao getExportBillExamDHibernateDao() {
		return exportBillExamDHibernateDao;
	}
	
	public void saveExportBillExamD(TExportBillExamD d){
		exportBillExamDHibernateDao.saveOrUpdate(d);
	}
	
	public void deleteExportBillExamD(String id){
		TExportBillExamD d = exportBillExamDHibernateDao.get(id);
		exportBillExamDHibernateDao.remove(d);
	}
	
	public boolean checkIsExitExportApply(String examBillId,String exportApplyId){
		return exportJdbcDao.checkIsExitExportApply(examBillId,exportApplyId);
	}
	
	public boolean isExistInvNo(String billID,String invNo){
		return exportJdbcDao.isExistInvNo(billID,invNo);
	}

}
