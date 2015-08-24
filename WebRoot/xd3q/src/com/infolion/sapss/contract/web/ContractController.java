/*
 * @(#)contractController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2009-1-19
 *  描　述：创建
 */

package com.infolion.sapss.contract.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.component.dictionary.DictionaryRow;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.console.workflow.dto.TaskHisDto;
import com.infolion.platform.console.workflow.service.SysWfUtilsService;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.web.BaseMultiActionController;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.workflow.manager.service.ExtProcessDefinitionService;
import com.infolion.sapss.Constants;
import com.infolion.sapss.bapi.domain.TBapiLog;
import com.infolion.sapss.bapi.service.BapiHibernateService;
import com.infolion.sapss.bapi.service.BapiJdbcService;
import com.infolion.sapss.common.dto.CommonChangeDto;
import com.infolion.sapss.contract.domain.TContractAgentMateriel;
import com.infolion.sapss.contract.domain.TContractAgentMtCase;
import com.infolion.sapss.contract.domain.TContractBom;
import com.infolion.sapss.contract.domain.TContractGroup;
import com.infolion.sapss.contract.domain.TContractPuMaterielCase;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.contract.domain.TContractPurchaseMateriel;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.contract.domain.TContractSalesMateriel;
import com.infolion.sapss.contract.domain.TContractSeMaterielCase;
import com.infolion.sapss.contract.domain.TPurchaseChange;
import com.infolion.sapss.contract.domain.TSalesChange;
import com.infolion.sapss.contract.service.ContractHibernateService;
import com.infolion.sapss.contract.service.ContractService;
import com.infolion.sapss.contract.service.PurchaseChangeHibernateService;
import com.infolion.sapss.contract.service.PurchaseContractJdbcService;
import com.infolion.sapss.contract.service.SaleChangeHibernateService;
import com.infolion.sapss.contract.service.SaleContractJdbcService;
import com.infolion.sapss.project.ProjectConstants;
import com.infolion.sapss.project.domain.TProjectAccounting;
import com.infolion.sapss.project.domain.TProjectInfo;
import com.infolion.sapss.project.service.ProjectHibernateService;
import com.infolion.sapss.project.service.ProjectJdbcService;
import com.infolion.sapss.project.web.ProjectDetailVO;

public class ContractController extends BaseMultiActionController {
	@Autowired
	private ExtProcessDefinitionService extProcessDefinitionService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private ContractHibernateService contractHibernateService;
	@Autowired
	private BapiHibernateService bapiHibernateService;
	@Autowired
	private BapiJdbcService bapiJdbcService;
	@Autowired
	private ProjectHibernateService projectHibernateService;
	@Autowired
	private PurchaseContractJdbcService purchaseContractJdbcService;
	@Autowired
	private SaleContractJdbcService saleContractJdbcService;
	@Autowired
	private SaleChangeHibernateService saleChangeHibernateService;
	@Autowired
	private PurchaseChangeHibernateService purchaseChangeHibernateService;
	public void setPurchaseChangeHibernateService(
			PurchaseChangeHibernateService purchaseChangeHibernateService) {
		this.purchaseChangeHibernateService = purchaseChangeHibernateService;
	}
	public void setSaleChangeHibernateService(
			SaleChangeHibernateService saleChangeHibernateService) {
		this.saleChangeHibernateService = saleChangeHibernateService;
	}
	public void setSaleContractJdbcService(
			SaleContractJdbcService saleContractJdbcService) {
		saleContractJdbcService = saleContractJdbcService;
	}
	public void setPurchaseContractJdbcService(
			PurchaseContractJdbcService purchaseContractJdbcService) {
		this.purchaseContractJdbcService = purchaseContractJdbcService;
	}
	public void setBapiJdbcService(BapiJdbcService BapiJdbcService) {
		this.bapiJdbcService = BapiJdbcService;
	}
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}
	public void setContractHibernateService(
			ContractHibernateService contractHibernateService) {
		this.contractHibernateService = contractHibernateService;
	}

	public void setBapiHibernateService(
			BapiHibernateService bapiHibernateService) {
		this.bapiHibernateService = bapiHibernateService;
	}

	public void setProjectHibernateService(
			ProjectHibernateService projectHibernateService) {
		this.projectHibernateService = projectHibernateService;
	}

	@Autowired
	private ProjectJdbcService projectJdbcService;

	public void setProjectJdbcService(ProjectJdbcService projectJdbcService) {
		this.projectJdbcService = projectJdbcService;
	}
	
	/**
	 * 进入指定的合同信息管理界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView contractInfoManageView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//bapiJdbcService.synchronizeAllData();
		String strContractType = request.getParameter("contracttype");
		request.setAttribute("contracttype", strContractType);
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String loginer = userContext.getSysUser().getUserId();
		request.setAttribute("loginer", loginer);
		return new ModelAndView("sapss/contract/contractInfoManage");
	}

	/**
	 * 进入总的合同信息管理界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView ArchContractInfoManageView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String loginer = userContext.getSysUser().getUserId();
		request.setAttribute("loginer", loginer);
		if(userContext.getSysDept().getDeptid().equals(Constants.FILE_DEPT_NAME_ID)){
			request.setAttribute("fileDisable", "false");
		}
		return new ModelAndView(
		"sapss/contract/Archives/ArchcontractInfoManage");
	}

	/**
	 * 进入增加合同组界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView contractGroupManageView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strtradeType = request.getParameter("tradeType");
		request.setAttribute("tradeType", strtradeType);

		return new ModelAndView("sapss/contract/addContractGroup");
	}
	/**
	 * 销售金额反显处理
	 * @param tContractSalesInfo
	 * @return
	 */
	private TContractSalesInfo accessSaleMoney(TContractSalesInfo tContractSalesInfo){
		String total = tContractSalesInfo.getOrderTotal();
		if(total==null)
			total="0";
		BigDecimal bd = new BigDecimal(Double.valueOf(total));
		String onet = tContractSalesInfo.getOrderNet();
		if(onet==null)
			onet="0";
		BigDecimal net = new BigDecimal(onet);
		String otax = tContractSalesInfo.getOrderTaxes();
		if(otax==null)
			otax ="0";
		BigDecimal tt = new BigDecimal(tContractSalesInfo.getOrderTaxes());
//		BigDecimal rate = tContractSalesInfo.getVbkdKurrf();
		BigDecimal rate = new BigDecimal(1);
		if(rate==null || rate.doubleValue()==0.0)
			rate= new BigDecimal(1);
		tContractSalesInfo.setOrderTotal(bd.divide(rate,2,RoundingMode.HALF_UP).toString());
		tContractSalesInfo.setOrderNet(net.divide(rate,2,RoundingMode.HALF_UP).toString());
		tContractSalesInfo.setOrderTaxes(tt.divide(rate,2,RoundingMode.HALF_UP).toString());
		
		return tContractSalesInfo;
	}
	/**
	 * 进入增加销售合同界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView addSaleContractView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String modify = request.getParameter("modify");
		String writeSap = request.getParameter("writeSap");
		String contractType = request.getParameter("contractType");
		
		String submit = "false";

		String strContractId = request.getParameter("contractid");

		TContractSalesInfo tContractSalesInfo = new TContractSalesInfo();

		tContractSalesInfo = contractHibernateService.queryContractSalesInfoById(strContractId);
		
		if((contractType!=null && "8".equals(contractType))|| 
				(tContractSalesInfo!=null&& "8".equals(tContractSalesInfo.getTradeType())))
			request.setAttribute("vbakVtwegDisable", false);//分销渠道--是否可选
		else
			request.setAttribute("vbakVtwegDisable", true);//分销渠道--是否可选

		tContractSalesInfo = accessSaleMoney(tContractSalesInfo);
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		if (modify == null || "".equals(modify)) {
			modify = "false";
			if (("1".equals(tContractSalesInfo.getOrderState()))
					&& userContext.getSysUser().getUserId().equals(
							tContractSalesInfo.getCreator())) {
				modify = "true";
				submit = "true";
			}
		}
		if (writeSap == null || "".equals(writeSap)) {
			writeSap = "true";
		}
		request.setAttribute("writeSap", writeSap);
		request.setAttribute("submit", submit);
		request.setAttribute("sales", tContractSalesInfo);
		TProjectInfo pi=this.projectHibernateService.getProjectInfo(tContractSalesInfo.getProjectId());
		pi.setInterestRate(Constants.INTEREST_RATE);
		request.setAttribute("main",pi);
		request.setAttribute("popfrom", "add");
		Map<String, TProjectAccounting> map = this.contractService.getContractAccounting(strContractId);
		TProjectAccounting ta;
		for (int i = 1; i < map.size() + 1; i++) {
			ta = (TProjectAccounting) map.get(i + "");
			request.setAttribute(ta.getCurrency().toLowerCase()
					+ ta.getAccountingItemId(), ta.getAccountingValue());
		}
		String fileEdit = "1".equals(userContext.getSysDept().getIsFuncDept())?"true":"false";
		request.setAttribute("fileEdit", fileEdit);
		// 取合同组信息
		TContractGroup tContractGroup = contractHibernateService
				.getContractGroup(tContractSalesInfo.getContractGroupId());
		request.setAttribute("contractGroup", tContractGroup);

		Map<String, DictionaryRow> ItemTypeMap = SysCachePoolUtils
				.getDictTableGroup("BM_ITEM_TYPE");
		Collection<DictionaryRow> ItemType = ItemTypeMap.values();
		request.setAttribute("ItemType", ItemType);

		Map<String, DictionaryRow> FactoryMap = SysCachePoolUtils
				.getDictTableGroup("BM_FACTORY");
		Collection<DictionaryRow> Factory = FactoryMap.values();
		request.setAttribute("Factory", Factory);
		// 付款条件
		Map<String, DictionaryRow> PayMentTypeMap = SysCachePoolUtils
				.getDictTableGroup("BM_PAYMENT_TYPE");
		Collection<DictionaryRow> PayMentType = PayMentTypeMap.values();
		request.setAttribute("PayMentType", PayMentType);
		//货币
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);
		request.setAttribute("currency", Currency);
		//销售地区
		Map<String, DictionaryRow> salesDistrict = SysCachePoolUtils.getDictTableGroup("BM_SALES_DISTRICT");
		Collection<DictionaryRow> district = salesDistrict.values();
		request.setAttribute("district", district);


		String strContractGroupName = contractService
				.queryContractGroupInfoByPurchaseId(strContractId);
		request.setAttribute("contractGroupName", strContractGroupName);
		String change = request.getParameter("from");
		request.setAttribute("balaceMsg", getBalanceMsg(tContractSalesInfo.getProjectId(),"sales",tContractSalesInfo.getDeptId()).get("msg"));
		//合同管理页面--变更链接
		if(change!=null && change.equals("change")){
			request.setAttribute("from", "change");
			return new ModelAndView("sapss/contract/Archives/archSaleContract");
		}
		//贸管部允许更改部分信息
		if("MG".equals(userContext.getSysDept().getDeptShortCode())){
			request.setAttribute("mgModify", "true");
		}
		// 可修改
		if ("true".equals(modify)) {
			return new ModelAndView("sapss/contract/addSaleContract");
		} else {
			return new ModelAndView("sapss/contract/Archives/archSaleContract");
		}

	}

	/**
	 * 进入增加采购合同界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView addPurchaseContractView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strContractId = request.getParameter("contractid");
		String modify = request.getParameter("modify");
		String writeSap = "true";
		String contractType = request.getParameter("contractType");
		String submit = "false";
		String fileEdit = "false";

		TContractPurchaseInfo tContractPurchaseInfo = new TContractPurchaseInfo();
		tContractPurchaseInfo = contractHibernateService.queryContractPurchaseInfoById(strContractId);
		String strContractGroupName = contractService.queryContractGroupInfoByPurchaseId(strContractId);
		String strContractGroupNo = contractService.queryContractGroupByPurchaseId(strContractId).getContractGroupNo();

		request.setAttribute("purchase", tContractPurchaseInfo);
		request.setAttribute("contractGroupName", strContractGroupName);
		request.setAttribute("contractGroupNo", strContractGroupNo);

		// 用于控制前台的提交和关闭是否显示出来
		request.setAttribute("show", "false");
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		if (modify == null || "".equals(modify)) {
			modify = "false";
			if (("1".equals(tContractPurchaseInfo.getOrderState()))
					&& userContext.getSysUser().getUserId().equals(
							tContractPurchaseInfo.getCreator())) {
				modify = "true";
				submit = "true";
			}

		}
		// 只有信息中心允许手工执行写入动作
		if ("XXZX".equals(userContext.getSysDept().getDeptShortCode())) {
			writeSap = "false";
		}
		if("1".equals(userContext.getSysDept().getIsFuncDept())){
			fileEdit = "true";
		}
		request.setAttribute("fileEdit", fileEdit);
		request.setAttribute("writeSap", writeSap);
		request.setAttribute("submit", submit);

		Map<String, DictionaryRow> ItemTypeMap = SysCachePoolUtils
				.getDictTableGroup("BM_ITEM_TYPE");
		Collection<DictionaryRow> ItemType = ItemTypeMap.values();
		request.setAttribute("ItemType", ItemType);

		Map<String, DictionaryRow> FactoryMap = SysCachePoolUtils
				.getDictTableGroup("BM_FACTORY");
		Collection<DictionaryRow> Factory = FactoryMap.values();
		request.setAttribute("Factory", Factory);

		Map<String, DictionaryRow> MaterielGroupMap = SysCachePoolUtils
				.getDictTableGroup("BM_MATERIAL_GROUP");
		Collection<DictionaryRow> MaterielGroup = MaterielGroupMap.values();
		request.setAttribute("MaterielGroup", MaterielGroup);

		Map<String, DictionaryRow> WareHouseMap = SysCachePoolUtils
				.getDictTableGroup("BM_WAREHOUSE");
		Collection<DictionaryRow> WareHouse = WareHouseMap.values();
		request.setAttribute("WareHouse", WareHouse);

		Map<String, DictionaryRow> SelasTaxMap = SysCachePoolUtils
				.getDictTableGroup("BM_SALES_TAX");
		Collection<DictionaryRow> SelasTax = SelasTaxMap.values();
		request.setAttribute("SelasTax", SelasTax);

		Map<String, DictionaryRow> UnitMap = SysCachePoolUtils
				.getDictTableGroup("BM_UNIT");
		Collection<DictionaryRow> Unit = UnitMap.values();
		request.setAttribute("Unit", Unit);

		Map<String, DictionaryRow> InvoiceValidMap = SysCachePoolUtils
				.getDictTableGroup("BM_INVOICE_VALID");
		Collection<DictionaryRow> InvoiceValid = InvoiceValidMap.values();
		request.setAttribute("InvoiceValid", InvoiceValid);

		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);
		
		Map<String, DictionaryRow> purchaseGroupMap = SysCachePoolUtils.getDictTableGroup("BM_PURCHASING_GROUP");
		Collection<DictionaryRow> purchase = purchaseGroupMap.values();
		request.setAttribute("purchaseGroup", purchase);
		
        request.setAttribute("balaceMsg", getBalanceMsg(tContractPurchaseInfo.getProjectId(),"purchase",tContractPurchaseInfo.getDeptId()).get("msg"));
		// 如果可修改的动作
		if ("true".equals(modify)) {
			return new ModelAndView("sapss/contract/Apply/addPurchaseContract");
		} else {
			return new ModelAndView(
					"sapss/contract/Archives/ArchPurchaseContract");
		}
	}

	/**
	 * 进入增加销售合同其他信息界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView addSaleCaseView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strSalesRowsId = request.getParameter("salesrowsid");

		request.setAttribute("salesrowsid", strSalesRowsId);

		String strSapRowNo = request.getParameter("saprowno");
		request.setAttribute("saprowno", strSapRowNo);

		Map<String, DictionaryRow> ConditionMap = SysCachePoolUtils
				.getDictTableGroup("BM_CONDITION_TYPE_SE");
		Collection<DictionaryRow> Condition = ConditionMap.values();
		request.setAttribute("Condition", Condition);

		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils
				.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);

		return new ModelAndView("sapss/contract/addSaleCase");
	}

	/**
	 * 进入增加销售合同其他信息界面(只读)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView archSaleCaseView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strSalesRowsId = request.getParameter("salesrowsid");

		request.setAttribute("salesrowsid", strSalesRowsId);

		String strSapRowNo = request.getParameter("saprowno");
		request.setAttribute("saprowno", strSapRowNo);

		Map<String, DictionaryRow> ConditionMap = SysCachePoolUtils
				.getDictTableGroup("BM_CONDITION_TYPE_SE");
		Collection<DictionaryRow> Condition = ConditionMap.values();
		request.setAttribute("Condition", Condition);

		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils
				.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);

		return new ModelAndView("sapss/contract/Archives/archSaleCase");
	}

	/**
	 * 进入增加销售合同代理物料其他信息界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView addAgentCaseView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strSalesRowsId = request.getParameter("salesrowsid");

		request.setAttribute("salesrowsid", strSalesRowsId);

		String strSapRowNo = request.getParameter("saprowno");
		request.setAttribute("saprowno", strSapRowNo);

		Map<String, DictionaryRow> ConditionMap = SysCachePoolUtils
				.getDictTableGroup("BM_CONDITION_TYPE_SE");
		Collection<DictionaryRow> Condition = ConditionMap.values();
		request.setAttribute("Condition", Condition);

		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils
				.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);

		return new ModelAndView("sapss/contract/addAgentCase");
	}

	/**
	 * 进入增加销售合同代理物料其他信息界面(只读)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView archAgentCaseView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strSalesRowsId = request.getParameter("salesrowsid");

		request.setAttribute("salesrowsid", strSalesRowsId);

		String strSapRowNo = request.getParameter("saprowno");
		request.setAttribute("saprowno", strSapRowNo);

		Map<String, DictionaryRow> ConditionMap = SysCachePoolUtils
				.getDictTableGroup("BM_CONDITION_TYPE_SE");
		Collection<DictionaryRow> Condition = ConditionMap.values();
		request.setAttribute("Condition", Condition);

		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils
				.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);

		return new ModelAndView("sapss/contract/Archives/archAgentCase");
	}

	/**
	 * 进入增加采购合同其他信息界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView addPurchaseCaseView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strPurchaseRowsId = request.getParameter("purchaserowsid");
		request.setAttribute("purchaserowsid", strPurchaseRowsId);

		String strSapRowNo = request.getParameter("saprowno");
		request.setAttribute("saprowno", strSapRowNo);

		Map<String, DictionaryRow> ConditionMap = SysCachePoolUtils
				.getDictTableGroup("BM_CONDITION_TYPE_PU");
		Collection<DictionaryRow> Condition = ConditionMap.values();
		request.setAttribute("Condition", Condition);

		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils
				.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);
		

		return new ModelAndView("sapss/contract/Apply/addPurchaseCase");
	}

	/**
	 * 进入增加采购合同其他信息界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView ArchPurchaseCaseView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strPurchaseRowsId = request.getParameter("purchaserowsid");
		request.setAttribute("purchaserowsid", strPurchaseRowsId);

		return new ModelAndView("sapss/contract/Archives/ArchPurchaseCase");
	}

	/**
	 * 进入增加BOM信息界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView addBomInfoView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strPurchaseRowsId = request.getParameter("purchaserowsid");
		request.setAttribute("purchaserowsid", strPurchaseRowsId);
		String strSapRowNo = request.getParameter("saprowno");
		request.setAttribute("saprowno", strSapRowNo);
		String ekpomeinsdunit = request.getParameter("ekpomeinsdunit");
		request.setAttribute("ekpomeinsdunit", ekpomeinsdunit);
		
		Map<String, DictionaryRow> UnitMap = SysCachePoolUtils
				.getDictTableGroup("BM_UNIT");
		Collection<DictionaryRow> Unit = UnitMap.values();
		request.setAttribute("Unit", Unit);

		Map<String, DictionaryRow> FactoryMap = SysCachePoolUtils
				.getDictTableGroup("BM_FACTORY");
		Collection<DictionaryRow> Factory = FactoryMap.values();
		request.setAttribute("Factory", Factory);

		return new ModelAndView("sapss/contract/Apply/addBomInfo");
	}

	/**
	 * 进入增加BOM信息界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView ArchBomInfoView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strPurchaseRowsId = request.getParameter("purchaserowsid");
		request.setAttribute("purchaserowsid", strPurchaseRowsId);
		return new ModelAndView("sapss/contract/Archives/ArchBomInfo");
	}

	/**
	 * 进入增加物料信息查询界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView selectMaterielInfoView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return new ModelAndView("sapss/contract/selectMaterielInfo");
	}

	/**
	 * 根据部门查询该部门的产项信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryProbjectInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

	}

	/**
	 * 进入项目信息查询界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView selectProjrctInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strDeptId = request.getParameter("deptid");
		String projectState = request.getParameter("projectState");
		String tradeType = request.getParameter("tradeType");
		request.setAttribute("deptid", strDeptId);
		request.setAttribute("projectState", projectState);
		request.setAttribute("tradeType", tradeType);
		return new ModelAndView("sapss/contract/selectProjectInfo");
	}

	/**
	 * 增加合同组信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addContractGroup(HttpServletRequest request,
			HttpServletResponse response, TContractGroup tContractGroup)
			throws IOException {
		String projectNo = request.getParameter("projectNo");
		String groupNo = contractService.getContractGroupCount(tContractGroup.getProjectId());
		tContractGroup.setContractGroupNo(projectNo+groupNo);

		// 把需要处理的数据先填入指定的字段，以便以json的格式返回给前台的界面
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		tContractGroup.setContractGroupId(CodeGenerator.getUUID());
		tContractGroup.setDeptId(userContext.getSysDept().getDeptid());
		tContractGroup.setCreateTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		tContractGroup.setCreator(userContext.getSysUser().getUserId());
		contractService.addContractGroup(tContractGroup);
		// 因为查询时返回的是用户的名子，所以这里重新设置
		tContractGroup.setCreator(userContext.getSysUser().getUserName());

		JSONObject jsonObject = JSONObject.fromObject(tContractGroup);
		String jsonText = jsonObject.toString();

		this.operateSuccessfullyWithString(response, jsonText);
	}

	/**
	 * 删组合同组信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deleteContractGroup(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strContractGroupId = request.getParameter("contractgroupid");

		JSONObject jsonObject = new JSONObject();

		int retrunFlag = contractService
				.deleteContractGroup(strContractGroupId);

		switch (retrunFlag) {
		case 1:
			jsonObject.put("returnMessage", "删除成功!");
			break;
		case 2:
			jsonObject.put("returnMessage", "还有下挂采购合同或销售合同不允许删除该合同组!");
			break;
		}

		String jsontext = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 得到该项目下已下挂的合同组数量
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getContractGroupCount(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strProjectId = request.getParameter("projectid");
		String strProjectNo = request.getParameter("projectno");

		String GroupCount = "";
		GroupCount = contractService.getContractGroupCount(strProjectId);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("count", strProjectNo + GroupCount);
		// jsonObject.put("count", strProjectNo + "-" + GroupCount);
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/**
	 * 查询合同组信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void selectContractGroup(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String strDeptId = request.getParameter("deptid");
		String strProjectId = request.getParameter("projectid");
		String strProjectNo = request.getParameter("projectNo");
		String strContractGroupNo = request.getParameter("contractgroupno");
		String strContractNo = request.getParameter("contractNo");
		String strOutContractNo = request.getParameter("outContractNo");
		String strSapOrderNo = request.getParameter("sapOrderNo");
		String strTradeType = request.getParameter("tradetype");
        String projectName = extractFR(request, "projectName");
        String className = extractFR(request, "className");
        String unitName = extractFR(request, "unitName");
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String deptSql = "";
		//是职能部门
		if("1".equals(userContext.getSysDept().getIsFuncDept())){
			//部门不为空
			if(!"".equals(strDeptId)){
				//部门多个
				if (strDeptId.indexOf(",") != -1) {
					String[] dp = strDeptId.split(",");
					strDeptId = "";
					for (int i = 0; i < dp.length; i++) {
						if (i == (dp.length - 1))
							strDeptId = strDeptId + "'" + dp[i] + "'";
						else
							strDeptId = strDeptId + "'" + dp[i] + "',";
					}
				}else
					strDeptId="'"+strDeptId+"'";
				
				deptSql = " and a.dept_id in("+strDeptId+")";
			}
			deptSql +=" and a.dept_id in("+userContext.getGrantedDepartmentsId()+")";
		}else
			deptSql = " and a.dept_id = '"+userContext.getSysDept().getDeptid()+"'";
		
		String strPurchaseSql = "select a.contract_group_id "
				+ "from t_contract_purchase_info a left outer join t_contract_purchase_materiel a1 on a.contract_purchase_id=a1.contract_purchase_id " + "where 1=1"
				+ deptSql  + " and a.is_available = '1'";
		String strSaleSql = " select a.contract_group_id "
				+ " from t_contract_sales_info a left outer join t_contract_sales_materiel a1 on a.contract_sales_id=a1.contract_sales_id " + "where 1=1"
				+ deptSql + " and a.is_available = '1'";
		String strContractGroup = "select a.contract_group_id "
				+ "from t_contract_group a " + "where 1=1"
				+ deptSql + " and a.is_available = '1'";

		if (strProjectId != null && !"".equals(strProjectId)) {
			strPurchaseSql = strPurchaseSql + " and a.project_id ='"
					+ strProjectId + "'";
			strSaleSql = strSaleSql + " and a.project_id ='" + strProjectId
					+ "'";
			strContractGroup = strContractGroup + " and a.project_id ='"
					+ strProjectId + "'";
		}
		
		if (strProjectNo != null && !"".equals(strProjectNo)) {
			strPurchaseSql = strPurchaseSql + " and a.project_No like '%"
					+ strProjectNo + "%'";
			strSaleSql = strSaleSql + " and a.project_No like '%" + strProjectNo
					+ "%'";
			strContractGroup = strContractGroup + " and a.project_No like '%"
					+ strProjectNo + "%'";
		}
		
		if (projectName != null && !"".equals(projectName)) {
			strPurchaseSql = strPurchaseSql + " and a.project_Name like '%"
					+ projectName + "%'";
			strSaleSql = strSaleSql + " and a.project_Name  like '%" + projectName
					+ "%'";
			strContractGroup = strContractGroup + " and a.project_Name  like '%"
					+ projectName + "%'";
		}

		if (strContractNo != null && !"".equals(strContractNo)) {
			strPurchaseSql = strPurchaseSql + " and a.contract_no like'%"
					+ strContractNo + "%'";
			strSaleSql = strSaleSql + " and a.contract_no like'%"
					+ strContractNo + "%'";
		}
		
		if (className != null && !"".equals(className)) {
			strPurchaseSql = strPurchaseSql + " and a1.ekpo_txz01 like'%"
					+ className + "%'";
			strSaleSql = strSaleSql + " and a1.vbap_arktx like'%"
					+ className + "%'";
		}

		if (strOutContractNo != null && !"".equals(strOutContractNo)) {
			strPurchaseSql = strPurchaseSql + " and a.ekko_unsez like'%"
					+ strOutContractNo + "%'";
			strSaleSql = strSaleSql + " and a.vbkd_ihrez like'%"
					+ strOutContractNo + "%'";
		}
		
		if (unitName != null && !"".equals(unitName)) {
			strPurchaseSql = strPurchaseSql + " and a.ekko_Lifnr_Name like'%"
					+ unitName + "%'";
			strSaleSql = strSaleSql + " and a.kuagv_Kunnr_Name like'%"
					+ unitName + "%'";
		}

		if (strSapOrderNo != null && !"".equals(strSapOrderNo)) {
			strPurchaseSql = strPurchaseSql + " and a.sap_order_no like'%"
					+ strSapOrderNo + "%'";
			strSaleSql = strSaleSql + " and a.sap_order_no like'%"
					+ strSapOrderNo + "%'";
		}

		if (strContractGroupNo != null && !"".equals(strContractGroupNo)) {
			strPurchaseSql = strPurchaseSql + " and a.contract_group_no like '%" + strContractGroupNo
			+ "%'";
			strSaleSql = strSaleSql + " and a.contract_group_no like '%" + strContractGroupNo
			+ "%'";
			strContractGroup = strContractGroup
			+ " and a.contract_group_no like '%" + strContractGroupNo
			+ "%'";
		}

		String strCompleSql = "";
		if ((strContractNo != null && !"".equals(strContractNo))
				|| (strSapOrderNo != null && !"".equals(strSapOrderNo))
				|| (className != null && !"".equals(className))
				|| (strOutContractNo != null && !"".equals(strOutContractNo))
				|| (unitName != null && !"".equals(unitName))) {
			strCompleSql = strPurchaseSql + " union all " + strSaleSql;
		} else {
			strCompleSql = strContractGroup + " union all " + strPurchaseSql
					+ " union all " + strSaleSql;
		}

		String strSql = "select a.contract_group_id,a.project_id,a.contract_group_name,a.contract_group_no,a.project_name,a.cmd,a.create_time,c.real_name "
			+ "from t_contract_group a, t_sys_user c "
			+ "where a.is_available = '1' "
			+ "and a.creator = c.user_id "
			+ "and c.deleted = '1'"
			+ "and a.contract_group_id in (" + strCompleSql + ")";

		if (strTradeType != null && !"".equals(strTradeType)) {
			strSql = strSql + " and a.trade_type = " + strTradeType;
		}
		strSql += " order by a.create_time desc";
		String grid_columns = "CONTRACT_GROUP_ID,PROJECT_ID,CONTRACT_GROUP_NAME,CONTRACT_GROUP_NO,PROJECT_NAME,CMD,CREATE_TIME,REAL_NAME";

		String grid_size = request.getParameter("limit");
		request.setAttribute("grid_sql", strSql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size==null?"10":grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}


	/**
	 * 根据条件查询合同信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void selectContractByCondition(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String strDeptId = request.getParameter("deptid");
		String strProjectId = request.getParameter("projectid");
		String strContractGroupNo = request.getParameter("contractgroupno");
		String strContractNo = request.getParameter("contractno");
		String strOutContractNo = request.getParameter("outcontractno");
		String strSapOrderNo = request.getParameter("saporderno");
		String strTradeType = request.getParameter("tradetype");

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		if (strDeptId == null || "".equals(strDeptId)) {
			strDeptId = userContext.getSysDept().getDeptid();
		}

		String strPuSql = "select a.contract_purchase_id contract_id, "
				+ "'采购合同' contract_type, " + "a.contract_no, "
				+ "a.contract_name, " + "a.ekko_unsez out_contract_no, "
				+ "a.apply_time, " + "a.approved_time, " + "a.sap_order_no, "
				+ "a.order_state ORDER_STATE_D_ORDER_STATE, " + "a.creator , "
				+ "'详情' contract_info "
				+ "from t_contract_purchase_info a, t_contract_group b "
				+ "where a.is_available = '1' "
				+ "and a.contract_group_id = b.contract_group_id "
				+ "and b.is_available = '1' " + "and a.dept_id = '" + strDeptId
				+ "' " + "and b.trade_type = '" + strTradeType + "'";

		String strSaSql = "select a.contract_sales_id contract_id, "
				+ "'销售合同' contract_type,  " + "a.contract_no,  "
				+ "a.contract_name,  " + "a.vbkd_ihrez out_contract_no, "
				+ "a.apply_time,  " + "a.approved_time,  "
				+ "a.sap_order_no,  "
				+ "a.order_state ORDER_STATE_D_ORDER_STATE, " + "a.creator , "
				+ "'详情' contract_info  "
				+ "from t_contract_sales_info a,t_contract_group b "
				+ "where a.is_available = '1' "
				+ "and a.contract_group_id = b.contract_group_id "
				+ "and b.is_available = '1'" + "and a.dept_id = '" + strDeptId
				+ "'" + "and b.trade_type = '" + strTradeType + "'";
		if (strProjectId != null && !"".equals(strProjectId)) {
			strPuSql = strPuSql + " and a.project_id ='" + strProjectId + "'";
			strSaSql = strSaSql + " and a.project_id ='" + strProjectId + "'";
		}

		if (strContractNo != null && !"".equals(strContractNo)) {
			strPuSql = strPuSql + " and a.contract_no like'%" + strContractNo
					+ "%'";
			strSaSql = strSaSql + " and a.contract_no like'%" + strContractNo
					+ "%'";
		}

		if (strOutContractNo != null && !"".equals(strOutContractNo)) {
			strPuSql = strPuSql + " and a.ekko_unsez like'%" + strOutContractNo
					+ "%'";
			strSaSql = strSaSql + " and a.vbkd_ihrez like'%" + strOutContractNo
					+ "%'";
		}

		if (strSapOrderNo != null && !"".equals(strSapOrderNo)) {
			strPuSql = strPuSql + " and a.sap_order_no like'%" + strSapOrderNo
					+ "%'";
			strSaSql = strSaSql + " and a.sap_order_no like'%" + strSapOrderNo
					+ "%'";
		}

		if (strContractGroupNo != null && !"".equals(strContractGroupNo)) {
			strPuSql = strPuSql + " and b.contract_group_no like '%"
					+ strContractGroupNo + "%'";
			strSaSql = strSaSql + " and b.contract_group_no like '%"
					+ strContractGroupNo + "%'";
		}

		String strSql = strPuSql + " union all " + strSaSql;

		String grid_columns = "CONTRACT_ID,CONTRACT_TYPE,CONTRACT_NO,CONTRACT_NAME,OUT_CONTRACT_NO,APPLY_TIME,APPROVED_TIME,SAP_ORDER_NO,ORDER_STATE_D_ORDER_STATE,CONTRACT_INFO,CREATOR";

		String grid_size = "10";
		request.setAttribute("grid_sql", strSql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 查找合同
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryProjectInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String strProjectName = extractFR(request, "projectname"); 
		String strProjectNo = request.getParameter("projectno");
		String strDeptId = request.getParameter("deptid");
		String tradeType = request.getParameter("tradeType");

		String strSql = "select a.project_id,a.project_name,a.project_no "
				+ "from t_project_info a " + "where a.is_available = '1'";
		UserContext userContext = UserContextHolder.getLocalUserContext()
		.getUserContext();
		if("1".equals(userContext.getSysDept().getIsFuncDept())){
			
			strSql +=" and a.dept_id in("+userContext.getGrantedDepartmentsId()+")";
		}
		else 
	    	strSql +=" and a.dept_id = '"+userContext.getSysDept().getDeptid()+"'";
		
		//部门不为空
		if(!"".equals(strDeptId)){
			//部门多个
			if (strDeptId.indexOf(",") != -1) {
				String[] dp = strDeptId.split(",");
				strDeptId = "";
				for (int i = 0; i < dp.length; i++) {
					if (i == (dp.length - 1))
						strDeptId = strDeptId + "'" + dp[i] + "'";
					else
						strDeptId = strDeptId + "'" + dp[i] + "',";
				}
			}else
				strDeptId="'"+strDeptId+"'";
			
			strSql = strSql + " and a.dept_id in("+strDeptId+")";
		}
		
		
		if (strProjectName != null && !"".equals(strProjectName)) {
			strSql = strSql + " and a.project_name like '%" + strProjectName
					+ "%'";
		}

		if (strProjectNo != null && !"".equals(strProjectNo)) {
			strSql = strSql + " and a.project_no like '%" + strProjectNo + "%'";
		}

		if (tradeType != null && !"".equals(tradeType)) {
			strSql = strSql + " and a.trade_type = '" + tradeType+"'";
		}
		//立项状态为生效,变更通过
			strSql = strSql + " and a.project_state in('3','8') order by creator_time desc";

		String grid_columns = "PROJECT_ID,PROJECT_NAME,PROJECT_NO";

		String grid_size = request.getParameter("limit");
		grid_size = grid_size==null || "".equals(grid_size)?"10":grid_size;
		request.setAttribute("grid_sql", strSql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 根据组合同组编码查询下挂合同
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryContractInfoByGroupId(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String strGroupId = request.getParameter("groupid");
		String outContractNo = request.getParameter("outContractNo");
		StringBuffer sb = new StringBuffer();
		sb.append("select a.contract_purchase_id contract_id, ")
				.append( "getContract_Type(a.TRADE_TYPE) contract_type,  a.contract_no, ")
				.append( "a.contract_name,  a.ekko_unsez out_contract_no, ")
				.append( "a.TOTAL as total,a.EKKO_WAERS as currency, ")
				.append( "a.apply_time,  a.approved_time,  a.sap_order_no, ")
				.append( "a.order_state ORDER_STATE_D_ORDER_STATE, ")
				.append( "a.order_state,  a.creator,  '详情' contract_info,create_time ,a.Total_quality qualityTotal,a.KEEP_FLAG ")
				.append( "from t_contract_purchase_info a ")
				//.append( "inner join (select sum(b.ekpo_menge) as qualityTotal ,b.contract_purchase_id from t_contract_purchase_materiel b group by b.contract_purchase_id) b on a.contract_purchase_id=b.contract_purchase_id ")
				.append( "where a.contract_group_id = '" ).append( strGroupId ).append( "' ");
				
				if(null != outContractNo && !"".equals(outContractNo.trim()) ){
					sb.append( "and a.ekko_unsez like '%" + outContractNo + "%'");
				}
		
				sb.append( "and a.is_available = '1'  union all ")
				.append( "select a.contract_sales_id contract_id, ")
				.append( "'销售合同' contract_type,  a.contract_no, ")
				.append( "a.contract_name,  a.vbkd_ihrez out_contract_no, ")
				.append( "a.ORDER_TOTAL as total,a.VBAK_WAERK as currency, ")
				.append( "a.apply_time,  a.approved_time,  a.sap_order_no, ")
				.append( "a.order_state ORDER_STATE_D_ORDER_STATE, ")
				.append( "a.order_state,  a.creator,  '详情' contract_info,create_time ,a.Total_quality qualityTotal,a.KEEP_FLAG ")
				.append( "from t_contract_sales_info a ")
				//.append( "inner join (select sum(b.VBAP_ZMENG) as qualityTotal ,b.contract_sales_id from t_contract_sales_materiel b group by b.contract_sales_id) b on a.contract_sales_id=b.contract_sales_id ")
				.append( " where a.contract_group_id = '" ).append( strGroupId ).append( "' ");
				if(null != outContractNo && !"".equals(outContractNo.trim()) ){
					sb.append( "and a.vbkd_ihrez like '%" + outContractNo + "%'");
				}
				sb.append( "and a.is_available = '1'").toString();

		String strSql = "select * from ("+sb.toString()+") order by create_time desc";
		String grid_columns = "CONTRACT_ID,CONTRACT_TYPE,CONTRACT_NO,CONTRACT_NAME,OUT_CONTRACT_NO,TOTAL,CURRENCY,APPLY_TIME,APPROVED_TIME,SAP_ORDER_NO,ORDER_STATE_D_ORDER_STATE,CONTRACT_INFO,ORDER_STATE,CREATOR,QUALITYTOTAL,KEEP_FLAG";

		String grid_size = request.getParameter("limit");
		request.setAttribute("grid_sql", strSql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size==null||"".equals(grid_size)?"10":grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 通过采购合同号得到合同信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryPurchaserByPurchaseId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strPurchaseId = request.getParameter("contractpurchaserid");

		TContractPurchaseInfo tContractPurchaseInfo = new TContractPurchaseInfo();

		tContractPurchaseInfo = contractHibernateService
				.queryContractPurchaseInfoById(strPurchaseId);

		JSONObject jsonObject = new JSONObject();
		jsonObject
				.put("CONTRACT_NAME", tContractPurchaseInfo.getContractName());
		jsonObject.put("OUT_CONTRACT_NO", tContractPurchaseInfo.getEkkoUnsez());
		jsonObject.put("APPLY_TIME", tContractPurchaseInfo.getApplyTime());
		jsonObject.put("ORDER_STATE_D_ORDER_STATE", SysCachePoolUtils
				.getDictDataValue("BM_ORDER_STATE", tContractPurchaseInfo
						.getOrderState()));
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/**
	 * 通过销售合同号得到合同信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void querySalesBySalesId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strContractSalesId = request.getParameter("contractsalesid");

		TContractSalesInfo tContractSalesInfo = new TContractSalesInfo();

		tContractSalesInfo = contractHibernateService.queryContractSalesInfoById(strContractSalesId);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("CONTRACT_NAME", tContractSalesInfo.getContractName());
		jsonObject.put("OUT_CONTRACT_NO", tContractSalesInfo.getContractNo());
		jsonObject.put("APPLY_TIME", tContractSalesInfo.getApplyTime());
		jsonObject.put("ORDER_STATE_D_ORDER_STATE", SysCachePoolUtils
				.getDictDataValue("BM_ORDER_STATE", tContractSalesInfo
						.getOrderState()));
		String jsontext = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/**
	 * 增加采购合同
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 */
	public void addPurchaseContract(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		String strContractGroupId = request.getParameter("contractgroupid");
		String strContractGroupNo = request.getParameter("contractgroupno");
		String strProjectId = request.getParameter("projectid");
		String strProjectName = extractFR(request, "projectname");// request.getParameter("projectname");
		String strContractType = request.getParameter("contracttype");
		String strPurchaserType = request.getParameter("purchasertype");
		TProjectInfo tProjectInfo = contractService
		.queryProjectInfo(strProjectId);
		JSONObject jo = new JSONObject();
		if(!com.infolion.platform.dpframework.util.DateUtils.isDateBefore(tProjectInfo.getAvailableDataEnd()+" 23:59:59")){
			jo.put("msg", "立项时间已过了期效，请变更期效再新增合同！");
			String jsontext = jo.toString();
			response.setCharacterEncoding("GBK");
			response.setContentType("text/html; charset=GBK");
			response.getWriter().print(jsontext);
			return ;
			//throw new BusinessException("立项时间已过了期效，请变更期效再新增合同！");
		}
		TContractPurchaseInfo tContractPurchaseInfo = new TContractPurchaseInfo();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		// 采购合同ID
		tContractPurchaseInfo.setContractPurchaseId(CodeGenerator.getUUID());
		// 合同组ID
		tContractPurchaseInfo.setContractGroupId(strContractGroupId);
		// 项目ID
		tContractPurchaseInfo.setProjectId(strProjectId);
		// 项目名称
		tContractPurchaseInfo.setProjectName(strProjectName);
		// 合同编号
		tContractPurchaseInfo.setContractNo(contractService.getContractNo(
				strContractGroupId, strContractGroupNo, strContractType, "1",
				strPurchaserType.length()>1?strPurchaserType.substring(0, 1):strPurchaserType));
		//默认显示币别:内贸为CNY,外贸为USD
		if("2".equals(strContractType)||"4".equals(strContractType)||"5".equals(strContractType)||"7".equals(strContractType)||"10".equals(strContractType)){
			if(tContractPurchaseInfo.getEkkoWaers()==null||"".equals(tContractPurchaseInfo.getEkkoWaers()))
				tContractPurchaseInfo.setEkkoWaers("CNY");
		}else if("1".equals(strContractType)||"3".equals(strContractType)||"6".equals(strContractType)||"8".equals(strContractType)||"12".equals(strContractType)){
			if(tContractPurchaseInfo.getEkkoWaers()==null||"".equals(tContractPurchaseInfo.getEkkoWaers()))
				tContractPurchaseInfo.setEkkoWaers("USD");
		}

		// 业务类型
		tContractPurchaseInfo.setTradeType(strContractType);
		// 采购凭证类型
		String strEkkoBsart = "";
		switch (Integer.parseInt(strContractType)) {
		case 1:// 外贸合作进口业务
			strEkkoBsart = "Z001";
			break;
		case 2:// 外贸合作出口业务
			strEkkoBsart = "Z002";
			break;
		case 3:// 外贸自营进口业务
			strEkkoBsart = "Z003";
			break;
		case 4:// 外贸自营出口业务
			strEkkoBsart = "Z004";
			break;
		case 5:// 外贸代理出口业务
			strEkkoBsart = " ";
			break;
		case 6:// 外贸代理进口业务
			strEkkoBsart = " ";
			break;
		case 7:// 内贸业务
			strEkkoBsart = "Z005";
			break;
		case 8:// 进料加工业务内销(P:成品采购 I:原材料采购/进口 PI:原材料采购/国内)
			if (strPurchaserType.equals("P"))
				strEkkoBsart = "Z006";
			if (strPurchaserType.equals("I"))
				strEkkoBsart = "Z003";
			if(strPurchaserType.equals("PI"))
				strEkkoBsart = "Z002";
			break;
		case 9:// 外贸自营进口敞口业务
			strEkkoBsart = "Z003";
			break;
		case 10:// 内贸敞口业务
			strEkkoBsart = "Z005";
			break;
		case 11:// 转口业务
			strEkkoBsart = "Z007";
			break;
		case 12:// 进料加工业务外销(P:成品采购 I:原材料采购/进口 PI:原材料采购/国内)
			if (strPurchaserType.equals("P"))
				strEkkoBsart = "Z006";
			if (strPurchaserType.equals("I"))
				strEkkoBsart = "Z003";
			if(strPurchaserType.equals("PI"))
				strEkkoBsart = "Z002";
			break;
		}
		tContractPurchaseInfo.setEkkoBsart(strEkkoBsart);
		// 采购组织
		// userContext.getSysDept().getCgzzcode()
		tContractPurchaseInfo.setEkkoEkorg(userContext.getSysDept()
				.getCgzzcode());
		// 汇率
		Double EkkoWkurs = Double.valueOf(1);
		tContractPurchaseInfo.setEkkoWkurs(EkkoWkurs);
		// 采购合同号
		tContractPurchaseInfo.setEkkoIhrez(tContractPurchaseInfo
				.getContractNo());
		// 装运港
		
		tContractPurchaseInfo.setShipmentPort(tProjectInfo.getShipmentPort());
		// 目的港
		tContractPurchaseInfo.setDestinationPort(tProjectInfo
				.getDestinationPort());
		// 装运期
		//tContractPurchaseInfo.setShipmentDate(tProjectInfo.getShipmentDate());
		// 不含税金额汇总
		tContractPurchaseInfo.setTotalAmount("0");
		// 税金汇总
		tContractPurchaseInfo.setTotalTaxes("0");
		// 含税金额汇总
		tContractPurchaseInfo.setTotalAmountTaxes("0");
		// 运费汇总
		tContractPurchaseInfo.setTotalFreight("0");
		// 关税汇总
		tContractPurchaseInfo.setTotalTariff("0");
		// 消费税汇总
		tContractPurchaseInfo.setTotalCt("0");
		// 进项税汇总
		tContractPurchaseInfo.setTotalIt("0");
		// 总金额
		tContractPurchaseInfo.setTotal("0");
		// 部门编号
		tContractPurchaseInfo.setDeptId(userContext.getSysDept().getDeptid());
		// 是否有效
		tContractPurchaseInfo.setIsAvailable("1");
		// 创建时间
		tContractPurchaseInfo.setCreateTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		// 创建者
		tContractPurchaseInfo.setCreator(userContext.getSysUser().getUserId());
		// 订单状态
		tContractPurchaseInfo.setOrderState("1");
		// tContractPurchaseInfo.validateMe();
		contractHibernateService
				.saveContractPurchaseInfo(tContractPurchaseInfo);

		//JSONObject jo = new JSONObject();
		// jo.fromObject(tContractPurchaseInfo);
		jo.put("CONTRACT_ID", tContractPurchaseInfo.getContractPurchaseId());
		jo.put("CONTRACT_TYPE", "采购合同");
		jo.put("CONTRACT_NO", tContractPurchaseInfo.getContractNo());
		jo.put("CONTRACT_NAME", "");
		jo.put("OUT_CONTRACT_NO", "");
		jo.put("APPLY_TIME", "");
		jo.put("APPROVED_TIME", "");
		jo.put("SAP_ORDER_NO", "");
		jo.put("ORDER_STATE", "新增");
		jo.put("CONTRACT_INFO", "详情");
		jo.put("CREATOR", userContext.getSysUser().getUserId());

		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);
	}

	/**
	 * 修改采购合同信息
	 * 
	 * @param request
	 * @param response
	 * @param tContractPurchaseInfo
	 * @throws IOException
	 */
	public void updatePurchaseContract(HttpServletRequest request,
			HttpServletResponse response,
			TContractPurchaseInfo tContractPurchaseInfo) throws IOException {
		if((!"".equals(tContractPurchaseInfo.getEkkoInco1())&&tContractPurchaseInfo.getEkkoInco1()!=null)&&("".equals(tContractPurchaseInfo.getEkkoInco2())||tContractPurchaseInfo.getEkkoInco2()==null))
			throw new BusinessException("国际贸易条件1为非空，则国际贸易条件2为必填！");
		if ("".equals(tContractPurchaseInfo.getPayType())||tContractPurchaseInfo.getPayType()==null)
			throw new BusinessException("付款方式必填！");
		tContractPurchaseInfo.setEkkoEkgrp(contractService.getSalePurGroup(tContractPurchaseInfo.getYmatGroup(), "purchase"));
		Map<String,String> map = accessPurchaseMoney(tContractPurchaseInfo);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功！");
		jo.put("SumTotal", map.get("total"));
		jo.put("total1", map.get("total1"));
		jo.put("taxTotal", map.get("totalTaxes"));
		jo.put("balanceMSG",map.get("balanceMSG"));
		jo.put("ekkoEkgrp",tContractPurchaseInfo.getEkkoEkgrp());
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
	/**
	 * 处理采购金额
	 * @param info
	 * @return
	 */
	private Map<String,String> accessPurchaseMoney(TContractPurchaseInfo info){
		Map<String,Double> map = purchaseContractJdbcService.sumPurchase(info.getContractPurchaseId()); 
//		double rate = info.getEkkoWkurs();//汇率
		double rate = 1.0;//汇率
		
		Double sum = (Double)map.get("total");//外币总额
		Double tax = (Double)map.get("tax");//外币税总额
		//进口业务不计算税
		if(info.getTradeType().equals("1")||info.getTradeType().equals("3")||info.getTradeType().equals("6")||info.getTradeType().equals("9")){
			tax = 0d;
		}
		BigDecimal sumb = new BigDecimal(sum);
		String totalAmount = sumb.divide(new BigDecimal(1),2,RoundingMode.HALF_UP).toString();
		BigDecimal taxb = new BigDecimal(tax);
		String totalTaxes = taxb.divide(new BigDecimal(1),2,RoundingMode.HALF_UP).toString();
		/***modify by cat****/
		/********根据立项金额控制合同金额****************/
		Map balanceMap = getBalanceMsg(info.getProjectId(),"purchase",info.getDeptId());
		Double contralSum = sum*contractService.getCurrencyRate(info.getEkkoWaers());
		contractService.checkPurcharseBalance(info, contralSum);
		/************************/
		//BigDecimal sumM = new BigDecimal(sum);
		//String total = sumM.multiply(new BigDecimal(rate)).divide(new BigDecimal(1),4,RoundingMode.HALF_UP).toString();//本币总额
		BigDecimal sumM = new BigDecimal(sum+tax);
		String total = sumM.divide(new BigDecimal(1),2,RoundingMode.HALF_UP).toString();
		/*******************/
		info.setTotalAmount(totalAmount);//保存到数据库都为本币数额
		info.setTotal(total);//总金额
		info.setTotalTaxes(totalTaxes);//货币码对应的税额
		info.setTotalQuality(map.get("totalQuality").toString());
		
		contractService.updatePurchaseContract(info);//更新保存记录
		Map<String,String> rtnMap = new HashMap<String,String>();
		rtnMap.put("total",totalAmount);
		rtnMap.put("totalTaxes",totalTaxes);
		rtnMap.put("total1",total);
		rtnMap.put("balanceMSG", (String)balanceMap.get("msg"));
		return rtnMap;
	}
	
/*	private void checkBalance(Map balanceMap,Double contralSum,String tradeType){
		if(tradeType.equals("8")){
			if((Double)balanceMap.get("balanceValue")-contralSum<0)
				throw new BusinessException("进料加工业务，合同累计金额大于立项金额");
		}
		else {
			Double projectSum = (Double)balanceMap.get("projectTotal");
			Double contractTotal = (Double)balanceMap.get("contractTotal");
			if(projectSum*1.2-contractTotal-contralSum<0)
				throw new BusinessException("合同累计金额超过立项总金额的20%");
		}
			
	}*/
	
	private Map<String,Object> getBalanceMsg(String projectID,String orderTypeStr,String dept_id){
	   Map<String,Object> map = new HashMap<String,Object>();
	   Double projectTotal = contractService.getProjectSum(projectID);
	   Double contractTotal ;
	   String currency="CNY";
	   if(Constants.DEPT_ID_HK.contains(dept_id)){
		   currency ="HKD";
	   }
	   if(orderTypeStr.equals("purchase")){
		   contractTotal = contractService.getPurcharseContractSum(projectID, "8,9,6,7,3,5,2");
	   }
	   else 
		   contractTotal = contractService.getSalesContractSum(projectID, "8,9,6,7,3,5,2");
	   Double balanceValue = projectTotal-contractTotal;
	   String msg = "立项金额为："+new BigDecimal(projectTotal).divide(new BigDecimal(1),2,RoundingMode.HALF_UP).toString()+" "+currency+"<br>累计有效合同金额为："+new BigDecimal(contractTotal).divide(new BigDecimal(1),2,RoundingMode.HALF_UP).toString()+" "+currency+"<br>差额为："+new BigDecimal(balanceValue).divide(new BigDecimal(1),2,RoundingMode.HALF_UP).toString()+" "+currency;
	   if(balanceValue<0) msg ="<font color=red>"+msg+"</font>";
	   map.put("projectTotal", projectTotal);
	   map.put("contractTotal", contractTotal);
	   map.put("balanceValue", balanceValue);
	   map.put("msg", msg);
	   return map;
	}
	/**
	 * 删除采购合同信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deletePurchaseContract(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strContractPurchaseId = request
				.getParameter("contractpurchaseid");

		int returnFlag = contractService.deletePurchaseContract(strContractPurchaseId);

		JSONObject jsonObject = new JSONObject();

		if (returnFlag == 1) {
			jsonObject.put("returnMessage", "删除成功！");
		}

		String jsonText = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsonText);
	}

	/**
	 * 增加采购合同物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addPurchaseMaterielInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strContractPurchaseId = request
				.getParameter("contractpurchaseid");
		String strEkpoMatnr = request.getParameter("EkpoMatnr");
		String strEkpoTxz01 = extractFR(request, "EkpoTxz01");
		String strEkpoMeins = extractFR(request, "EkpoMeins");//request.getParameter("EkpoMeins");
		String strEkpoWerks = request.getParameter("EkpoWerks");
		String ekpoBprme = request.getParameter("EkpoBprme"); // 货币

		TContractPurchaseMateriel purchMartrl = new TContractPurchaseMateriel();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();

		// 取的采购合同
		// TContractPurchaseInfo tContractPurchaseInfo =
		// contractHibernateService.getContractPurchaseInfo(strContractPurchaseId);
		// String contractNO = tContractPurchaseInfo.getContractNo();
		// 根据合同号设置项目类别
		/*
		 * if (contractNO.indexOf("P") > 0) purchMartrl.setEkpoPstyp("3"); else
		 * { purchMartrl.setEkpoPstyp(""); }
		 */
		purchMartrl.setEkpoPstyp("");
		// 采购合同行项ID
		purchMartrl.setPurchaseRowsId(CodeGenerator.getUUID());
		// 采购合同ID
		purchMartrl.setContractPurchaseId(strContractPurchaseId);
		// SAP行项目编号(000010格式流水编码)
		purchMartrl.setSapRowNo(contractService.getSapRowNo("1",
				strContractPurchaseId));

		// 物料号
		purchMartrl.setEkpoMatnr(strEkpoMatnr);
		// 物料描述
		purchMartrl.setEkpoTxz01(strEkpoTxz01);
		// 采购订单计量单位
		purchMartrl.setEkpoMeins(strEkpoMeins);
		// 采购订单数量
		Double EkpoMenge = Double.valueOf(0);
		purchMartrl.setEkpoMenge(EkpoMenge);
		// 采购凭证中的净价(以凭证货币计)
		Double EkpoNetpr = Double.valueOf(0);
		purchMartrl.setEkpoNetpr(EkpoNetpr);
		// 价格单位
		purchMartrl.setEkpoBprme(ekpoBprme);
		// purchMartrl.setEkpoPeinh(ekpoBprme);
		// 工厂编号
		purchMartrl.setEkpoWerks(strEkpoWerks);
		// 标识：基于收货的发票验证
		purchMartrl.setEkpoWebre("0");
		// 批次号
		purchMartrl.setFlowNo(" ");
		// 不含税金额汇总
		purchMartrl.setTotalValue("0");
		// 含税单价
		purchMartrl.setPrice("0");
		// 税金
		purchMartrl.setTaxes("0");
		// 含税金额汇总
		purchMartrl.setTotalTaxes("0");
		// 创建时间
		purchMartrl.setCreateTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		// 创建者
		purchMartrl.setCreator(userContext.getSysUser().getUserId());
		//add
		//每价格单位
		purchMartrl.setEkpoPeinh("10000");
		//装运期
		purchMartrl.setEketEindt(request.getParameter("eketEindt"));
		
		contractHibernateService.saveContractPurchaseMaterielInfo(purchMartrl);

		purchMartrl.setEkpoWerks(SysCachePoolUtils.getDictDataValue(
				"BM_FACTORY", purchMartrl.getEkpoWerks()));
		purchMartrl.setEkpoWebre(SysCachePoolUtils.getDictDataValue(
				"BM_INVOICE_VALID", purchMartrl.getEkpoWebre()));

		JSONObject jsonObject = JSONObject.fromObject(purchMartrl);
		String jsonText = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
	}

	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void copyPurchaseMaterielInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strcontractpurcharseMaterielid = request
				.getParameter("contractpurcharseMaterielid");

		TContractPurchaseMateriel srcPurchMartrl = contractHibernateService.getTContractPurcharseMateriel(strcontractpurcharseMaterielid);
		TContractPurchaseMateriel purchMartrl = new TContractPurchaseMateriel();
		BeanUtils.copyProperties(purchMartrl, srcPurchMartrl);
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();



		purchMartrl.setPurchaseRowsId(CodeGenerator.getUUID());

		// SAP行项目编号(000010格式流水编码)
		purchMartrl.setSapRowNo(contractService.getSapRowNo("1",
				srcPurchMartrl.getContractPurchaseId()));

		// 创建时间
		purchMartrl.setCreateTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		// 创建者
		purchMartrl.setCreator(userContext.getSysUser().getUserId());

		contractHibernateService.saveContractPurchaseMaterielInfo(purchMartrl);
	}
	
	/**
	 * 查询采购合同的物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryPurchaseMaterielInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String strContractPurchaseId = request
				.getParameter("contractPurchaseId");

		String strSql = "select a.purchase_Rows_Id     PURCHASE_ROWS_ID, "
				+ "a.contract_Purchase_Id CONTRACT_PURCHASE_ID, "
				+ "a.sap_row_no			SAP_ROW_NO, "
				+ "a.ekpo_Pstyp           EKPO_PSTYP, "
				+ "a.ekpo_Matnr           EKPO_MATNR, "
				+ "a.ekpo_Txz01           EKPO_TXZ01, "
				+ "a.ekpo_Meins           EKPO_MEINS_D_UNIT, "
				+ "a.ekpo_Menge           EKPO_MENGE, "
				+ "a.ekpo_Netpr           EKPO_NETPR, "
				+ "a.ekpo_Peinh           EKPO_PEINH, "
				+ "a.ekpo_Werks           EKPO_WERKS, "
				+ "a.eket_Eindt           EKET_EINDT, "
				+ "a.ekpo_Webre           EKPO_WEBRE_D_INVOICE_VALID, "
				+ "a.ekpo_Mwskz           EKPO_MWSKZ, "
				+ "a.factory_Local        FACTORY_LOCAL_D_WAREHOUSE, "
				+ "a.flow_No              FLOW_NO, "
				+ "a.total_Value          TOTAL_VALUE, "
				+ "a.price                PRICE, "
				+ "a.taxes                TAXES, "
				+ "a.total_Taxes          TOTAL_TAXES, "
				+ "a.materiel_Unit        MATERIEL_UNIT, "
				+ "a.create_Time          CREATE_TIME, "
				+ "a.creator              CREATOR, "
				+ "a.ekpo_Pstyp           EKPO_PSTYP_D_ITEM_TYPE, "
				+ "a.ekpo_Werks			EKPO_WERKS_D_FACTORY, "
				+ "a.materiel_Unit 		MATERIEL_UNIT_D_MATERIAL_GROUP, "
				+ "a.ekpo_Mwskz           EKPO_MWSKZ_D_SALES_TAX,  "
				+ "a.ekpo_Bprme           EKPO_BPRME_D_CURRENCY  "
				+ "from t_contract_purchase_materiel a where a.contract_Purchase_Id = '"
				+ strContractPurchaseId + "' order by a.sap_row_no ";

		String grid_columns = "PURCHASE_ROWS_ID,CONTRACT_PURCHASE_ID,SAP_ROW_NO,EKPO_PSTYP,EKPO_MATNR,EKPO_TXZ01,"
				+ "EKPO_MEINS_D_UNIT,EKPO_MENGE,EKPO_NETPR,EKPO_PEINH,EKPO_WERKS,"
				+ "EKET_EINDT,EKPO_WEBRE_D_INVOICE_VALID,EKPO_MWSKZ,EKPO_MWSKZ_D_SALES_TAX,FACTORY_LOCAL_D_WAREHOUSE,FLOW_NO,"
				+ "TOTAL_VALUE,PRICE,TAXES,TOTAL_TAXES,MATERIEL_UNIT,CREATE_TIME,CREATOR,"
				+ "EKPO_PSTYP_D_ITEM_TYPE,EKPO_WERKS_D_FACTORY,MATERIEL_UNIT_D_MATERIAL_GROUP,EKPO_BPRME_D_CURRENCY";

		String grid_size = "10";
		request.setAttribute("grid_sql", strSql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 删除采购合同物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deletePurchaseMaterielInfo(HttpServletRequest request,
			HttpServletResponse response,TContractPurchaseInfo info) throws IOException {
		String strIdList = request.getParameter("idList");
		JSONObject jsonObject = new JSONObject();
		contractService.deletePurchaseMaterielInfo(strIdList);
		Map<String,String> map = accessPurchaseMoney(info);
		jsonObject.put("SumTotal",map.get("total"));
		jsonObject.put("taxTotal",map.get("totalTaxes"));
		jsonObject.put("returnMessage", "删除成功！");
		String jsonText = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsonText);
	}

	/**
	 * 修改物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void updatePurchaseMaterielInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strPurchaseRowsId = request.getParameter("purchaserowsid");
		String strColName = request.getParameter("colname");
		String strColValue = request.getParameter("colvalue");
		strColValue = extractFR(request, "colvalue");

		contractService.updatePurchaseMaterielInfo(strPurchaseRowsId,
				strColName, strColValue);
	}

	/**
	 * 增加采购合同的BOM信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addBomMaterielInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strPurchaseRowsId = request.getParameter("purchaserowsid");
		String strBomMateriel = request.getParameter("bommateriel");
		String strBomMaterielCmd = request.getParameter("bommaterielcmd");
		strBomMaterielCmd = extractFR(request, "bommaterielcmd");
		String plant = request.getParameter("bomplant");
		String sapRowNo = request.getParameter("saprowno");
		String ekpoMeinsDUnit = request.getParameter("ekpomeinsdunit");
		

		TContractBom tContractBom = new TContractBom();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		//条目单位
		tContractBom.setEntryUom(ekpoMeinsDUnit);
		// BOMID
		tContractBom.setBomId(CodeGenerator.getUUID());
		// 采购合同行项ID
		tContractBom.setPurchaseRowsId(strPurchaseRowsId);
		// 委外原料
		tContractBom.setMateriel(strBomMateriel);
		// 委外原料数量
		BigDecimal bomMaterielNum = BigDecimal.valueOf(0);
		tContractBom.setEntryQuantity("0");
		// 委外物料描述
		tContractBom.setMaterielName(strBomMaterielCmd);
		// 工厂
		tContractBom.setPlant(plant);
		// sap行号
		tContractBom.setSapRowNo(sapRowNo);
		// 备注

		// 创建时间
		tContractBom.setCreateTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		// 创建者
		tContractBom.setCreator(userContext.getSysUser().getUserId());

		contractHibernateService.saveBomMaterielInfo(tContractBom);

		JSONObject jsonObject = JSONObject.fromObject(tContractBom);
		String jsonText = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
	}

	/**
	 * 查询Bom物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryBomInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String strPurchaseRowsId = request.getParameter("purchaserowsid");

		String strSql = "select a.bom_id BOM_ID, "
				+ "a.purchase_rows_id PURCHASE_ROWS_ID, "
				+ "a.item_no ITEM_NO," + "a.sap_row_no SAP_ROW_NO,"
				+ "a.materiel MATERIEL," + "a.materiel_name MATERIEL_NAME, "
				+ "a.entry_quantity ENTRY_QUANTITY, "
				+ "a.entry_uom ENTRY_UOM, " + "a.entry_uom ENTRY_UOM_D_UNIT, "
				+ "a.plant PLANT," + "a.plant PLANT_D_FACTORY," + "a.cmd CMD, "
				+ "a.create_time CREATE_TIME, " + "a.creator CREATOR "
				+ "from t_contract_bom a where a.purchase_rows_id = '"
				+ strPurchaseRowsId + "'";

		String grid_columns = "BOM_ID,PURCHASE_ROWS_ID,ITEM_NO,SAP_ROW_NO,MATERIEL,MATERIEL_NAME,ENTRY_QUANTITY,"
				+ "ENTRY_UOM,ENTRY_UOM_D_UNIT,PLANT,PLANT_D_FACTORY,CMD,CREATE_TIME,CREATOR";

		String grid_size = "10";
		request.setAttribute("grid_sql", strSql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 删除采购合同的BOM信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deletePurchaseBom(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strIdList = request.getParameter("idList");

		JSONObject jsonObject = new JSONObject();

		contractService.deletePurchaseBom(strIdList);

		jsonObject.put("returnMessage", "删除成功！");

		String jsonText = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsonText);
	}

	/**
	 * 修改物料BOM信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void updateBomInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strBomId = request.getParameter("bomid");
		String strColName = request.getParameter("colname");
		String strColValue = request.getParameter("colvalue");
		strColValue = extractFR(request, "colvalue");

		contractService.updateBomInfo(strBomId, strColName, strColValue);
	}

	/**
	 * 增加采购物料其他信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addPurchaseCaseInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strPurchaseRowsId = request.getParameter("purchaserowsid");
		String strSapRowNo = request.getParameter("saprowno");
		TContractPuMaterielCase tContractPuMaterielCase = new TContractPuMaterielCase();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();

		// 其他费用ID
		tContractPuMaterielCase.setMaterielCaseId(CodeGenerator.getUUID());
		// 采购合同行项ID
		tContractPuMaterielCase.setPurchaseRowsId(strPurchaseRowsId);
		// SAP行项目编号(000010格式流水编码)
		tContractPuMaterielCase.setSapRowNo(strSapRowNo);
		// 条件类型
		tContractPuMaterielCase.setKonvKschl(" ");
		// 币别
		tContractPuMaterielCase.setKonvWears(" ");
		// 币别名称
		tContractPuMaterielCase.setKonvWearsName(" ");
		// 价格( 条件金额或百分数 )
		Double KonvKbetr = Double.valueOf(1);
		tContractPuMaterielCase.setKonvKbetr(KonvKbetr);
		// 供应商或债权人的帐号
		tContractPuMaterielCase.setKonvLifnr(" ");
		// 供应商名称
		tContractPuMaterielCase.setKonvLifnrName(" ");
		// 备注
		tContractPuMaterielCase.setCmd(" ");
		// 创建时间
		tContractPuMaterielCase.setCreateTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		// 创建者
		tContractPuMaterielCase.setCreator(userContext.getSysUser()
				.getUserId());

		contractHibernateService
				.saveContractPuMaterielCaseInfo(tContractPuMaterielCase);

		JSONObject jsonObject = JSONObject.fromObject(tContractPuMaterielCase);
		String jsonText = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
	}

	/**
	 * 查询采购合同其他费用信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryPurchaseMaterielCaseInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String strPurchaseRowsId = request.getParameter("purchaserowsid");

		String strSql = "select materiel_Case_Id, " + "purchase_Rows_Id, "
				+ "konv_Kschl, " + "konv_Wears, " + "konv_Kbetr, "
				+ "konv_Lifnr, " + "KONV_LIFNR_NAME, " + "cmd, "+ "rate, "
				+ "create_Time, " + "creator, "
				+ "konv_Kschl KONV_KSCHL_D_CONDITION_TYPE_PU, "
				+ "konv_Wears KONV_WEARS_D_CURRENCY "
				+ "from t_contract_pu_materiel_case a "
				+ "where a.purchase_rows_id =  '" + strPurchaseRowsId + "'";

		String grid_columns = "MATERIEL_CASE_ID,PURCHASE_ROWS_ID,KONV_KSCHL,KONV_KSCHL_D_CONDITION_TYPE_PU,KONV_WEARS,KONV_WEARS_D_CURRENCY,"
				+ "KONV_KBETR,KONV_LIFNR,KONV_LIFNR_NAME,CMD,CREATE_TIME,CREATOR,RATE";

		String grid_size = "10";
		request.setAttribute("grid_sql", strSql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 删除采购合同的其费用信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deletePurchaseMaterielCase(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strIdList = request.getParameter("idList");

		JSONObject jsonObject = new JSONObject();

		contractService.deletePurchaseMaterielCase(strIdList);

		jsonObject.put("returnMessage", "删除成功！");

		String jsonText = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsonText);
	}

	/**
	 * 修改物料其他费用信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void updateMaterielCase(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strMaterielCaseId = request.getParameter("materielcaseid");
		String strColName = request.getParameter("colname");
		String strColValue = request.getParameter("colvalue");
		strColValue = extractFR(request, "colvalue");
		contractService.updateMaterielCase(strMaterielCaseId, strColName,
				strColValue);
	}

	/**
	 * 萃取URL参数
	 * 
	 * @param req
	 * @param parm
	 * @return parm对应的参数值
	 */
	private String extractFR(HttpServletRequest req, String parm) {
		try {
			String wait = java.net.URLDecoder.decode(req.getQueryString(),
					"UTF-8");
			int pos = wait.indexOf(parm) + parm.length() + 1;
			wait = wait.substring(pos);
			pos = wait.indexOf("&");
			if (pos != -1) {
				wait = wait.substring(0, pos);
			}
			return wait;
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
	/**
	 * 销售订单凭证类型定义
	 * @param tContractSalesInfo
	 * @param strContractType
	 */
	private void assemSalesVoucher(TContractSalesInfo tContractSalesInfo ,String strContractType,String salestype){
		if (strContractType.equals("1")) { // 外贸合作进口业务
			tContractSalesInfo.setVbakVtweg("20");
			tContractSalesInfo.setVbakAuart("Z002");
		} else if (strContractType.equals("2")) { // 外贸合作出口业务
			tContractSalesInfo.setVbakVtweg("20");
			tContractSalesInfo.setVbakAuart("Z001");
		} else if (strContractType.equals("3")) { // 外贸自营进口业务
			tContractSalesInfo.setVbakVtweg("10");
			tContractSalesInfo.setVbakAuart("Z002");
		} else if (strContractType.equals("4")) { // 外贸自营出口业务
			tContractSalesInfo.setVbakVtweg("10");
			tContractSalesInfo.setVbakAuart("Z001");
		} else if (strContractType.equals("5")) { // 外贸代理出口业务
			tContractSalesInfo.setVbakVtweg("30");
			tContractSalesInfo.setVbakAuart("Z001"); 
		} else if (strContractType.equals("6")) { // 外贸代理进口业务
			tContractSalesInfo.setVbakVtweg("30");
			tContractSalesInfo.setVbakAuart("Z002");
		} else if (strContractType.equals("7")) { // 内贸业务
			tContractSalesInfo.setVbakVtweg("10");
			tContractSalesInfo.setVbakAuart("Z003");
		} else if (strContractType.equals("8")) { // 进料加工业务内销
			tContractSalesInfo.setVbakVtweg("10");
			tContractSalesInfo.setVbakAuart("Z003");//内贸订单
			/**
			if("E".equals(salestype))
			   tContractSalesInfo.setVbakAuart("Z001");//出口订单
			else if("S".equals(salestype))
			   tContractSalesInfo.setVbakAuart("Z003");//内贸订单
			**/
		}else if (strContractType.equals("9")) { // 外贸自营进口敞口业务
			tContractSalesInfo.setVbakVtweg("10");
			tContractSalesInfo.setVbakAuart("Z002");
		}else if (strContractType.equals("10")) { // 内贸敞口业务
			tContractSalesInfo.setVbakVtweg("10");
			tContractSalesInfo.setVbakAuart("Z003");
		}
		else if (strContractType.equals("11")) { // 转口业务
			tContractSalesInfo.setVbakVtweg("10");
			tContractSalesInfo.setVbakAuart("Z004");
		}
		else if (strContractType.equals("12")) { // 进料加工业务外销
			tContractSalesInfo.setVbakVtweg("20");
			tContractSalesInfo.setVbakAuart("Z001");//出口订单
		}
	}

	/**
	 * 增加销售合同
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addSaleContract(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strContractGroupId = request.getParameter("contractgroupid");
		String strContractGroupNo = request.getParameter("contractgroupno");
		String strProjectId = request.getParameter("projectid");
		String strProjectName = request.getParameter("projectname");
		strProjectName = extractFR(request, "projectname");
		String strContractType = request.getParameter("contracttype");
		String salestype = request.getParameter("salestype");
		// 取立项数据到合同
		TProjectInfo tProjectInfo = projectHibernateService
		.getTProjectInfo(strProjectId);
		JSONObject jo = new JSONObject();
		if(!com.infolion.platform.dpframework.util.DateUtils.isDateBefore(tProjectInfo.getAvailableDataEnd()+" 23:59:59")){
			jo.put("msg", "立项时间已过了期效，请变更期效再新增合同！");
			String jsontext = jo.toString();
			response.setCharacterEncoding("GBK");
			response.setContentType("text/html; charset=GBK");
			response.getWriter().print(jsontext);
			return ;
			//throw new BusinessException("立项时间已过了期效，请变更期效再新增合同！");
		}
		// ////////
		TContractSalesInfo tContractSalesInfo = new TContractSalesInfo();
		
		
		// 装运港
		tContractSalesInfo.setShipmentPort(tProjectInfo.getShipmentPort());
		// 目的港
		tContractSalesInfo
				.setDestinationPort(tProjectInfo.getDestinationPort());
		// 装运期
		//tContractSalesInfo.setShipmentDate(tProjectInfo.getShipmentDate());
		// 产品组
		tContractSalesInfo.setVbakSpart("00");
		//默认显示币别:内贸为CNY,外贸为USD
		if("7".equals(strContractType)||"10".equals(strContractType)||"1".equals(strContractType)||"3".equals(strContractType)||"6".equals(strContractType)||"9".equals(strContractType)){
			if(tContractSalesInfo.getVbakWaerk()==null||"".equals(tContractSalesInfo.getVbakWaerk()))
				tContractSalesInfo.setVbakWaerk("CNY");
		}else if(!"8".equals(strContractType)){
			if(tContractSalesInfo.getVbakWaerk()==null||"".equals(tContractSalesInfo.getVbakWaerk()))
				tContractSalesInfo.setVbakWaerk("USD");
		}
		
		// 初始化分销渠道,凭证类型(vbakVtweg,vbakAuart)
		assemSalesVoucher(tContractSalesInfo,strContractType,salestype);
		// 业务类型
		tContractSalesInfo.setTradeType(strContractType);
		// 采购合同ID
		tContractSalesInfo.setContractSalesId(CodeGenerator.getUUID());
		// 合同组ID
		tContractSalesInfo.setContractGroupId(strContractGroupId);
		// 项目ID
		tContractSalesInfo.setProjectId(strProjectId);
		// 项目名称
		tContractSalesInfo.setProjectName(strProjectName);
		// 合同编号
		tContractSalesInfo.setContractNo(contractService.getContractNo(
				strContractGroupId, strContractGroupNo, strContractType, "2",
				salestype));
		// 会计汇率
		BigDecimal VbkdKurrf = BigDecimal.valueOf(1);
		tContractSalesInfo.setVbkdKurrf(VbkdKurrf);
		// 信达项目号
		//String ProjectNo[] = strContractGroupNo.split("-");
		//tContractSalesInfo.setVbakBname(ProjectNo[0]);
		tContractSalesInfo.setVbakBname(tProjectInfo.getProjectNo());
		// 订单总值
		tContractSalesInfo.setOrderTotal("0");
		// 订单净值
		tContractSalesInfo.setOrderNet("0");
		// 订单销项税金
		tContractSalesInfo.setOrderTaxes("0");
		// 是否有效
		tContractSalesInfo.setIsAvailable("1");
		// 创建时间
		tContractSalesInfo.setCreateTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		// 合同状态(创建状态)
		tContractSalesInfo.setOrderState("1");
		// 取用户相关信息
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		// 部门编号
		tContractSalesInfo.setDeptId(userContext.getSysDept().getDeptid());
		// 创建者
		tContractSalesInfo.setCreator(userContext.getSysUser().getUserId());
		// 销售组织(vbakVkorg)
		tContractSalesInfo.setVbakVkorg(userContext.getSysDept().getXszzcode());
		// 销售部门(vbakVkbur)
		tContractSalesInfo.setVbakVkbur(userContext.getSysDept().getDeptcode());
		contractHibernateService.saveContractSalesInfo(tContractSalesInfo);

		
		jo.put("CONTRACT_ID", tContractSalesInfo.getContractSalesId());
		jo.put("CONTRACT_TYPE", "销售合同");
		jo.put("CONTRACT_NO", tContractSalesInfo.getContractNo());
		jo.put("CONTRACT_NAME", tContractSalesInfo.getContractName());
		jo.put("OUT_CONTRACT_NO", "");
		jo.put("APPLY_TIME", "");
		jo.put("APPROVED_TIME", "");
		jo.put("SAP_ORDER_NO", "");
		jo.put("ORDER_STATE", "新增");
		jo.put("CONTRACT_INFO", "详情");
		jo.put("CREATOR", userContext.getSysUser().getUserId());

		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsontext);

	}

	/**
	 * 删除销售合同
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deleteSalesContract(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strContractSalesId = request.getParameter("contractsalesid");

		JSONObject jsonObject = new JSONObject();

		contractService.deleteSalesContract(strContractSalesId);

		jsonObject.put("returnMessage", "删除成功！");

		String jsonText = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsonText);
	}

	/**
	 * 修改销售合同抬头信息
	 * 
	 * @param request
	 * @param response
	 * @param tContractSalesInfo
	 * @throws IOException
	 */
	public void updateSalesContract(HttpServletRequest request,
			HttpServletResponse response, TContractSalesInfo tContractSalesInfo)
			throws IOException {
		//
		Map<String,String> map = saleContractJdbcService.sumSales(tContractSalesInfo.getContractSalesId(),tContractSalesInfo.getVbkdKurrf()); 

		/****/
		Map balanceMap = getBalanceMsg(tContractSalesInfo.getProjectId(), "sales",tContractSalesInfo.getDeptId());
		Double contralSum = Double.valueOf(map.get("moneyTotal"))*contractService.getCurrencyRate(tContractSalesInfo.getVbakWaerk());
		contractService.checkSalesBalance(tContractSalesInfo, contralSum);
		/****/
		
		tContractSalesInfo.setOrderNet(map.get("netTotal"));
		tContractSalesInfo.setOrderTaxes(map.get("taxTotal"));
		tContractSalesInfo.setOrderTotal(map.get("moneyTotal"));
		tContractSalesInfo.setTotalQuality(map.get("totalQuality"));
		
		if((!"".equals(tContractSalesInfo.getVbkdInco1())&&tContractSalesInfo.getVbkdInco1()!=null)&&("".equals(tContractSalesInfo.getVbkdInco2())||tContractSalesInfo.getVbkdInco2()==null))
			throw new BusinessException("国际贸易条件1为非空，则国际贸易条件2为必填！");
		tContractSalesInfo.setVbakVkgrp(contractService.getSalePurGroup(tContractSalesInfo.getYmatGroup(), "sales"));
		contractHibernateService.saveContractSalesInfo(tContractSalesInfo);
		JSONObject jo = new JSONObject();
		//不转化成本币
//		if(tContractSalesInfo.getVbkdKurrf()!=null && tContractSalesInfo.getVbkdKurrf().doubleValue()>0){
//			BigDecimal nett = new BigDecimal(Double.valueOf(map.get("netTotal")));
//			BigDecimal taxt = new BigDecimal(Double.valueOf(map.get("taxTotal")));
//			BigDecimal mt = new BigDecimal(Double.valueOf(map.get("moneyTotal")));
//			BigDecimal tax = tContractSalesInfo.getVbkdKurrf();
//			jo.put("netTotal", nett.divide(tax,2,RoundingMode.HALF_UP).toString());
//			jo.put("taxTotal", taxt.divide(tax,2,RoundingMode.HALF_UP).toString());
//			jo.put("moneyTotal", mt.divide(tax,2,RoundingMode.HALF_UP).toString());
//		} else {
		
			jo.put("netTotal", map.get("netTotal"));
			jo.put("taxTotal", map.get("taxTotal"));
			jo.put("moneyTotal", map.get("moneyTotal"));
			jo.put("balanceMSG", balanceMap.get("msg"));
			jo.put("vbakVkgrp", tContractSalesInfo.getVbakVkgrp());
//		}
		jo.put("returnMessage", "保存成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 增加销售合同其他费用信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addContractSeMaterielCase(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strSalesRowsId = request.getParameter("salesrowsid");
		String strSapRowNo = request.getParameter("saprowno");

		TContractSeMaterielCase tContractSeMaterielCase = new TContractSeMaterielCase();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();

		// 其他费用ID
		tContractSeMaterielCase.setMaterielCaseId(CodeGenerator.getUUID());
		// 销售合同行项ID
		tContractSeMaterielCase.setSalesRowsId(strSalesRowsId);
		// 金额
		Long KonvKbetr = Long.valueOf(1);
		tContractSeMaterielCase.setKonvKbetr(KonvKbetr);
		// 创建时间
		tContractSeMaterielCase.setCreateTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		// 创建者
		tContractSeMaterielCase.setCreator(userContext.getSysUser()
				.getUserId());
		// SAP通过此字段与物料其他费用关联
		tContractSeMaterielCase.setSapRowNo(strSapRowNo);

		contractHibernateService
				.saveContractSeMaterielCase(tContractSeMaterielCase);

		JSONObject jsonObject = JSONObject.fromObject(tContractSeMaterielCase);
		String jsonText = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
	}

	/**
	 * 增加销售合同代理物料其他费用信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addContractAgentMtCase(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strSalesRowsId = request.getParameter("salesrowsid");

		TContractAgentMtCase tContractAgentMtCase = new TContractAgentMtCase();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();

		// 其他费用ID
		tContractAgentMtCase.setMaterielCaseId(CodeGenerator.getUUID());
		// 销售合同行项ID
		tContractAgentMtCase.setSalesRowsId(strSalesRowsId);
		// 金额
		Long KonvKbetr = Long.valueOf(1);
		tContractAgentMtCase.setKonvKbetr(KonvKbetr);
		// 创建时间
		tContractAgentMtCase.setCreateTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		// 创建者
		tContractAgentMtCase.setCreator(userContext.getSysUser()
				.getUserId());

		contractHibernateService.saveContractAgentMtCase(tContractAgentMtCase);

		JSONObject jsonObject = JSONObject.fromObject(tContractAgentMtCase);
		String jsonText = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
	}

	/**
	 * 查询销售合同的其他费用信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryContractSeMaterielCase(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String strSalesRowsId = request.getParameter("salesrowsid");
		String strSapRowNo = request.getParameter("saprowno");

		String strSql = "select a.materiel_Case_Id MATERIEL_CASE_ID, "
				+ "a.konv_Kschl       KONV_KSCHL, "
				+ "a.konv_Kbetr       KONV_KBETR, "
				+ "a.konv_Wears       KONV_WEARS, "
				+ "a.cmd              CMD, "
				+ "a.konv_Kschl 		KONV_KSCHL_D_CONDITION_TYPE_SE, "
				+ "a.konv_Wears 		KONV_WEARS_D_CURRENCY, a.rate "
				+ "from t_contract_se_materiel_case a "
				+ "where a.sales_rows_id = '" + strSalesRowsId + "'"
				+ " and a.sap_row_no = '" + strSapRowNo + "'";

		String grid_columns = "MATERIEL_CASE_ID,KONV_KSCHL,KONV_KBETR,KONV_WEARS,CMD,"
				+ "KONV_KSCHL_D_CONDITION_TYPE_SE,KONV_WEARS_D_CURRENCY,RATE";

		String grid_size = "10";
		request.setAttribute("grid_sql", strSql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 查询销售合同的代理其他费用信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryContractAgentMtCase(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String strSalesRowsId = request.getParameter("salesrowsid");

		String strSql = "select a.materiel_Case_Id MATERIEL_CASE_ID, "
				+ "a.konv_Kschl       KONV_KSCHL, "
				+ "a.konv_Kbetr       KONV_KBETR, "
				+ "a.konv_Wears       KONV_WEARS, "
				+ "a.cmd              CMD, "
				+ "a.konv_Kschl 		KONV_KSCHL_D_CONDITION_TYPE_SE, "
				+ "a.konv_Wears 		KONV_WEARS_D_CURRENCY,a.rate "
				+ "from t_contract_agent_mt_case a "
				+ "where a.sales_rows_id = '" + strSalesRowsId + "'";

		String grid_columns = "MATERIEL_CASE_ID,KONV_KSCHL,KONV_KBETR,KONV_WEARS,CMD,"
				+ "KONV_KSCHL_D_CONDITION_TYPE_SE,KONV_WEARS_D_CURRENCY,RATE";

		String grid_size = "10";
		request.setAttribute("grid_sql", strSql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 修改销售物料其他费用信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void updateSalesMaterielCase(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strMaterielCaseId = request.getParameter("materielcaseid");
		String strColName = request.getParameter("colname");
		String strColValue = request.getParameter("colvalue");
		strColValue = extractFR(request, "colvalue");

		contractService.updateSalesMaterielCaseInfo(strMaterielCaseId,
				strColName, strColValue);
	}

	/**
	 * 删除销售合同其他费用信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deleteSalesMaterielCase(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strIdList = request.getParameter("idList");

		JSONObject jsonObject = new JSONObject();

		contractService.deleteSalesMaterielCase(strIdList);

		jsonObject.put("returnMessage", "删除成功！");

		String jsonText = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsonText);
	}

	/**
	 * 增加销售合同的物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addSalesMateriel(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strContractSalesId = request.getParameter("contractsalesid");
		String vbapMarnr = request.getParameter("vbapMarnr");
		String vbapArktx = extractFR(request, "vbapArktx");
		String vbapVrkme = request.getParameter("vbapVrkme");
		String vbapWerks = request.getParameter("vbapWerks");
		String payer = request.getParameter("payer");
		String payerName = extractFR(request, "payerName");
		String billToParty = request.getParameter("billToParty");
		String billToPartyName = extractFR(request, "billToPartyName");

		TContractSalesInfo tContractSalesInfo = new TContractSalesInfo();
		tContractSalesInfo = contractHibernateService
				.queryContractSalesInfoById(strContractSalesId);
		TContractSalesMateriel tContractSalesMateriel = new TContractSalesMateriel();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		// 销售合同行项ID
		tContractSalesMateriel.setSalesRowsId(CodeGenerator.getUUID());
		// 销售合同ID
		tContractSalesMateriel.setContractSalesId(strContractSalesId);
		// 项目类别
		tContractSalesMateriel.setVbapPstyv("");
		// 物料号
		tContractSalesMateriel.setVbapMatnr(vbapMarnr);
		// 物料描述(VBAP_ARKTX)
		tContractSalesMateriel.setVbapArktx(vbapArktx);
		// 以销售单位表示的累计订单数量(VBAP_ZMENG)
		BigDecimal vbapZmeng = BigDecimal.valueOf(1);
		tContractSalesMateriel.setVbapZmeng(vbapZmeng);
		// 销售订单的计量单位(VBAP_VRKME)
		tContractSalesMateriel.setVbapVrkme(vbapVrkme);
		// 工厂(VBAP_WERKS)
		if (vbapWerks != null && !"".equals(vbapWerks)) {
			tContractSalesMateriel.setVbapWerks(vbapWerks);
		}
		// 过量交货限度（百分比）(VBAP_UEBTO)
		tContractSalesMateriel.setVbapUebto("0");
		// 交货不足限度(VBAP_UNTTO)
		tContractSalesMateriel.setVbapUntto("0");
		// 销售订单行项目数量(VBAP_KWMENG)
		BigDecimal vbapKwmeng = BigDecimal.valueOf(1);
		tContractSalesMateriel.setVbapKwmeng(vbapKwmeng);
		// 含税单价(KONV_KBETR)
		Double konvKbetr = Double.valueOf(0);
		tContractSalesMateriel.setKonvKbetr(konvKbetr);
		// 付款条件(VBKD_ZTERM)
		if (tContractSalesInfo.getVbkdZterm() != null
				&& !"".equals(tContractSalesInfo.getVbkdZterm())) {
			tContractSalesMateriel.setVbkdZterm(tContractSalesInfo
					.getVbkdZterm());
		}

		// 条件定价单位(VBAP_KPEIN)
		tContractSalesMateriel.setVbapKpein("1");
		// 创建时间
		tContractSalesMateriel.setCreateTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		// 创建者
		tContractSalesMateriel.setCreator(userContext.getSysUser()
				.getUserId());
		// SAP行项目编号(000010格式流水编码)
		tContractSalesMateriel.setSapRowNo(contractService.getSapRowNo("2",
				strContractSalesId));
		// 付款芳,开票方
		tContractSalesMateriel.setPayer(payer);
		tContractSalesMateriel.setPayerName(payerName);
		tContractSalesMateriel.setBillToParty(billToParty);
		tContractSalesMateriel.setBillToPartyName(billToPartyName);
		contractHibernateService
				.saveContractSalesMateriel(tContractSalesMateriel);
		JSONObject jsonObject = JSONObject.fromObject(tContractSalesMateriel);
		String jsonText = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
	}
	
	
	public void copySalesMateriel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strContractSalesMaterielId = request.getParameter("contractsalesMaterielid");
	
		TContractSalesMateriel srcTContractSalesMateriel = contractHibernateService.getTContractSalesMateriel(strContractSalesMaterielId);
		TContractSalesMateriel tContractSalesMateriel = new TContractSalesMateriel();
		BeanUtils.copyProperties(tContractSalesMateriel, srcTContractSalesMateriel);
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		// 销售合同行项ID
		tContractSalesMateriel.setSalesRowsId(CodeGenerator.getUUID());
		
		// 创建时间
		tContractSalesMateriel.setCreateTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		// 创建者
		tContractSalesMateriel.setCreator(userContext.getSysUser()
				.getUserId());
		// SAP行项目编号(000010格式流水编码)
		tContractSalesMateriel.setSapRowNo(contractService.getSapRowNo("2",
				srcTContractSalesMateriel.getContractSalesId()));
		
		contractHibernateService
				.saveContractSalesMateriel(tContractSalesMateriel);
		JSONObject jsonObject = JSONObject.fromObject(tContractSalesMateriel);
		String jsonText = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
	}

	/**
	 * 删除销售合同物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deleteSalesMateriel(HttpServletRequest request,	HttpServletResponse response,TContractSalesInfo info) throws IOException {
		String strIdList = request.getParameter("idList");

		contractService.deleteSalesMateriel(strIdList);

		//重新计算总金额
		Map<String,String> map = saleContractJdbcService.sumSales(info.getContractSalesId(),info.getVbkdKurrf()); 

		info.setOrderNet(map.get("netTotal"));
		info.setOrderTaxes(map.get("taxTotal"));
		info.setOrderTotal(map.get("moneyTotal"));
		
		contractHibernateService.saveContractSalesInfo(info);

		JSONObject jo = new JSONObject();
		jo.put("netTotal", map.get("netTotal"));
		jo.put("taxTotal", map.get("taxTotal"));
		jo.put("moneyTotal", map.get("moneyTotal"));
		
		
		jo.put("returnMessage", "删除成功！");

		String jsonText = jo.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsonText);
	}

	/**
	 * 修改销售物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void updateSalesMateriel(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strSalesRowsId = request.getParameter("salesrowsid");
		String strColName = request.getParameter("colname");
		String strColValue = request.getParameter("colvalue");
		strColValue = extractFR(request, "colvalue");
		contractService.updateSalesMateriel(strSalesRowsId, strColName,
				strColValue);
	}

	/**
	 * 查询销售合同物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void quertSalesMateriel(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String strContractSalesId = request.getParameter("contractsalesid");

		String strSql = "select a.*," + "a.vbap_pstyv VBAP_PSTYV_D_ITEM_TYPE, "
				+ "a.vbap_werks VBAP_WERKS_D_FACTORY, "
				+ "a.vbkd_zterm VBKD_ZTERM_D_PAYMENT_TYPE "
				+ "from t_contract_sales_materiel a "
				+ "where a.contract_sales_id = '" + strContractSalesId + "' order by a.SAP_ROW_NO";
		String grid_columns = "SALES_ROWS_ID,SAP_ROW_NO,VBAP_PSTYV_D_ITEM_TYPE,VBAP_WERKS_D_FACTORY,VBAP_MATNR,VBAP_ARKTX,VBAP_ZMENG,"
				+ "VBAP_VRKME,RV45A_ETDAT,VBAP_WERKS,VBAP_UEBTO,VBAP_UNTTO,"
				+ "VBAP_KWMENG,KONV_KBETR,VBKD_ZTERM_D_PAYMENT_TYPE,VBAP_KPEIN,VBAP_KMEIN,"
				+ "ROW_TOTAL,ROW_NET,ROW_TAXES,ROW_TAXES_RALE,PAYER,PAYER_NAME,BILL_TO_PARTY,BILL_TO_PARTY_NAME";

		String grid_size = "10";
		request.setAttribute("grid_sql", strSql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 增加代理物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addAgentMaterielInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strContractSalesId = request.getParameter("contractsalesid");
		String vbapMarnr = request.getParameter("vbapMarnr");
		String vbapArktx = extractFR(request, "vbapArktx");
		String vbapVrkme = request.getParameter("vbapVrkme");
		String vbapWerks = request.getParameter("vbapWerks");
		String payer = request.getParameter("payer");
		String payerName = extractFR(request, "payerName");
		String billToParty = request.getParameter("billToParty");
		String billToPartyName = extractFR(request, "billToPartyName");
		TContractSalesInfo tContractSalesInfo = new TContractSalesInfo();
		tContractSalesInfo = contractHibernateService
				.queryContractSalesInfoById(strContractSalesId);
		TContractAgentMateriel tContractAgentMateriel = new TContractAgentMateriel();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();

		// 销售合同行项ID
		tContractAgentMateriel.setSalesRowsId(CodeGenerator.getUUID());
		// 销售合同ID
		tContractAgentMateriel.setContractSalesId(strContractSalesId);
		// 项目类别
		tContractAgentMateriel.setVbapPstyv("");
		// 物料号
		tContractAgentMateriel.setVbapMatnr(vbapMarnr);
		// 物料描述(VBAP_ARKTX)
		tContractAgentMateriel.setVbapArktx(vbapArktx);
		// 以销售单位表示的累计订单数量(VBAP_ZMENG)
		BigDecimal vbapZmeng = BigDecimal.valueOf(1);
		tContractAgentMateriel.setVbapZmeng(vbapZmeng);
		// 销售订单的计量单位(VBAP_VRKME)
		tContractAgentMateriel.setVbapVrkme(vbapVrkme);
		// 工厂(VBAP_WERKS)
		if (vbapWerks != null && !"".equals(vbapWerks)) {
			tContractAgentMateriel.setVbapWerks(vbapWerks);
		}
		// 过量交货限度（百分比）(VBAP_UEBTO)
		tContractAgentMateriel.setVbapUebto("0");
		// 交货不足限度(VBAP_UNTTO)
		tContractAgentMateriel.setVbapUntto("0");
		// 销售订单行项目数量(VBAP_KWMENG)
		BigDecimal vbapKwmeng = BigDecimal.valueOf(1);
		tContractAgentMateriel.setVbapKwmeng(vbapKwmeng);
		// 含税单价(KONV_KBETR)
		Double konvKbetr = Double.valueOf(0);
		tContractAgentMateriel.setKonvKbetr(konvKbetr);
		// 付款条件(VBKD_ZTERM)
		if (tContractSalesInfo.getVbkdZterm() != null
				&& !"".equals(tContractSalesInfo.getVbkdZterm())) {
			tContractAgentMateriel.setVbkdZterm(tContractSalesInfo
					.getVbkdZterm());
		}

		// 条件定价单位(VBAP_KPEIN)
		tContractAgentMateriel.setVbapKpein("1");
		// 创建时间
		tContractAgentMateriel.setCreateTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		// 创建者
		tContractAgentMateriel.setCreator(userContext.getSysUser()
				.getUserId());
		// 付款,开票方
		tContractAgentMateriel.setPayer(payer);
		tContractAgentMateriel.setPayerName(payerName);
		tContractAgentMateriel.setBillToParty(billToParty);
		tContractAgentMateriel.setBillToPartyName(billToPartyName);
		contractHibernateService
				.saveContractAgentMaterier(tContractAgentMateriel);
		JSONObject jsonObject = JSONObject.fromObject(tContractAgentMateriel);
		String jsonText = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
	}

	/**
	 * 修改代理物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void updateAgentMateriel(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strSalesRowsId = request.getParameter("salesrowsid");
		String strColName = request.getParameter("colname");
		String strColValue = request.getParameter("colvalue");
		strColValue = extractFR(request, "colvalue");
		contractService.updateAgentMateriel(strSalesRowsId, strColName,
				strColValue);
	}

	/**
	 * 删除代理物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deleteAgentMateriel(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strIdList = request.getParameter("idList");

		JSONObject jsonObject = new JSONObject();

		contractHibernateService.deleteAgentMateriel(strIdList);

		jsonObject.put("returnMessage", "删除成功！");

		String jsonText = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsonText);
	}

	/**
	 * 增加代理物料其他费用信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addAgentMtCase(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strSalesRowsId = request.getParameter("salesrowsid");

		TContractAgentMtCase tContractAgentMtCase = new TContractAgentMtCase();
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();

		// 其他费用ID
		tContractAgentMtCase.setMaterielCaseId(CodeGenerator.getUUID());
		// 销售合同行项ID
		tContractAgentMtCase.setSalesRowsId(strSalesRowsId);
		// 金额
		Long KonvKbetr = Long.valueOf(1);
		tContractAgentMtCase.setKonvKbetr(KonvKbetr);
		// 创建时间
		tContractAgentMtCase.setCreateTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		// 创建者
		tContractAgentMtCase.setCreator(userContext.getSysUser()
				.getUserId());

		contractHibernateService.saveTContractAgentMtCase(tContractAgentMtCase);

		JSONObject jsonObject = JSONObject.fromObject(tContractAgentMtCase);
		String jsonText = jsonObject.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(jsonText);
	}

	/**
	 * 修改代理物料其他费用信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void updateAgentMtCase(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strMaterielCaseId = request.getParameter("materielcaseid");
		String strColName = request.getParameter("colname");
		String strColValue = request.getParameter("colvalue");
		strColValue = extractFR(request, "colvalue");
		contractService.updateAgentMtCaseInfo(strMaterielCaseId, strColName,
				strColValue);

	}

	/**
	 * 删除代理物料其他费用信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deleteAgentMtCase(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strIdList = request.getParameter("idList");

		JSONObject jsonObject = new JSONObject();

		contractHibernateService.deleteAgentMtCase(strIdList);

		jsonObject.put("returnMessage", "删除成功！");

		String jsonText = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsonText);
	}

	/**
	 * 查询代理物料信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void quertAgentMateriel(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String strContractSalesId = request.getParameter("contractsalesid");

		String strSql = "select a.*, "
				+ "a.vbap_pstyv VBAP_PSTYV_D_ITEM_TYPE, "
				+ "a.vbap_werks VBAP_WERKS_D_FACTORY, "
				+ "a.vbkd_zterm VBKD_ZTERM_PAYMENT_TYPE "
				+ "from t_contract_agent_materiel a "
				+ "where a.contract_sales_id = '" + strContractSalesId + "'";

		String grid_columns = "SALES_ROWS_ID,VBAP_PSTYV,VBAP_PSTYV_D_ITEM_TYPE,VBAP_WERKS_D_FACTORY,VBAP_MATNR,VBAP_ARKTX,VBAP_ZMENG,"
				+ "VBAP_VRKME,RV45A_ETDAT,VBAP_WERKS,VBAP_UEBTO,VBAP_UNTTO,"
				+ "VBAP_KWMENG,KONV_KBETR,VBKD_ZTERM,VBAP_KPEIN,VBAP_KMEIN,"
				+ "ROW_TOTAL,ROW_NET,ROW_TAXES,ROW_TAXES_RALE,VBAP_PSTYV_D_ITEM_TYPE,VBKD_ZTERM_PAYMENT_TYPE,"
				+ "PAYER,PAYER_NAME,BILL_TO_PARTY,BILL_TO_PARTY_NAME,AGENT_CURRENCY";

		String grid_size = "10";
		request.setAttribute("grid_sql", strSql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}

	/**
	 * 流程展现
	 * 
	 * @param request
	 * @param resp
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView ArchPurchaseInfoView(HttpServletRequest request,
			HttpServletResponse resp) throws IllegalAccessException,
			InvocationTargetException {
		String strContractId = request.getParameter("businessRecordId");
		//extProcessDefinitionService.copyCreate("BBEB5E62-F6E3-4D05-9DE8-31AB8AB0B0B8", "440CD25D-5EF0-4405-A266-9EA2256CE031");
		if (strContractId != null && !"".equals(strContractId)) {
			TPurchaseChange change = this.purchaseChangeHibernateService.findChange(strContractId);//把strContractId当作变更ID,用来查找变更记录
			if (change != null)
				strContractId = change.getContractPurchaseId();
		}
		String writeToSap = request.getParameter("writeToSap");

		TContractPurchaseInfo tContractPurchaseInfo = new TContractPurchaseInfo();

		tContractPurchaseInfo = contractHibernateService
				.queryContractPurchaseInfoById(strContractId);

		String strContractGroupName = contractService
				.queryContractGroupInfoByPurchaseId(strContractId);
		String strContractGroupNo = contractService.queryContractGroupByPurchaseId(strContractId).getContractGroupNo();
		request.setAttribute("contractGroupNo", strContractGroupNo);

		request.setAttribute("purchase", tContractPurchaseInfo);
		request.setAttribute("contractGroupName", strContractGroupName);
		request.setAttribute("writeToSap", writeToSap);
		String fileEdit = "1".equals(UserContextHolder.getLocalUserContext()
		.getUserContext().getSysDept().getIsFuncDept())?"true":"false";
		request.setAttribute("fileEdit", fileEdit);
		 request.setAttribute("balaceMsg", getBalanceMsg(tContractPurchaseInfo.getProjectId(),"purchase",tContractPurchaseInfo.getDeptId()).get("msg"));
		String from = request.getParameter("from");
		//传递 不经审批写入SAP 状态
		String st = request.getParameter("shortSAP");
		if(st!=null && "true".equals(st))
			request.setAttribute("shortSAP","true");
		//贸管部允许更改部分信息
        UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
		if("MG".equals(context.getSysDept().getDeptShortCode())){
			request.setAttribute("mgModify", "true");
		}
		
		if(from!=null){
			if(from.equals("changeW")){
				request.setAttribute("from", "changeW");
				return new ModelAndView("sapss/contract/Archives/purchaseContractRChange");
			}
			else if(from.equals("changeR")){
				request.setAttribute("from", "changeR");
				return new ModelAndView("sapss/contract/Archives/purchaseContractRChange");
			}
		}

		return new ModelAndView("sapss/contract/Archives/ArchPurchaseContract");

	}

	/**
	 * 流程展现
	 * 
	 * @param request
	 * @param resp
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView archSalesInfoView(HttpServletRequest request,
			HttpServletResponse resp) throws IllegalAccessException,
			InvocationTargetException {
		
		String strContractId = request.getParameter("businessRecordId");
		TSalesChange change= this.saleChangeHibernateService.findChange(strContractId);//根据变更ID查找合同
		if(change!=null)
			strContractId = change.getContractSalesId();
		String writeToSap = request.getParameter("writeToSap");
		TContractSalesInfo tContractSalesInfo = new TContractSalesInfo();
		tContractSalesInfo = contractHibernateService.queryContractSalesInfoById(strContractId);
		tContractSalesInfo = accessSaleMoney(tContractSalesInfo);
		TContractGroup tcg= contractService.getContractGroupName(tContractSalesInfo.getContractGroupId());
        TContractGroup group = contractHibernateService.getContractGroup(tContractSalesInfo.getContractGroupId());
        UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
        //
        request.setAttribute("rateEdit", context.getSysUser().getUserId().equals(tContractSalesInfo.getCreator()));
		request.setAttribute("sales", tContractSalesInfo);
		request.setAttribute("contractGroupName", tcg.getContractGroupName());
		request.setAttribute("contractGroup", group);
		request.setAttribute("writeToSap", writeToSap);
		request.setAttribute("balaceMsg", getBalanceMsg(tContractSalesInfo.getProjectId(),"sales",tContractSalesInfo.getDeptId()).get("msg"));
		String fileEdit = "1".equals(context.getSysDept().getIsFuncDept())?"true":"false";
		request.setAttribute("fileEdit", fileEdit);
//		合同组测算信息zzh201412
		TProjectInfo pi=this.projectHibernateService.getProjectInfo(tContractSalesInfo.getProjectId());
		pi.setInterestRate(Constants.INTEREST_RATE);
		request.setAttribute("main",pi);
		String popfrom = (String) request.getParameter("popfrom");
		request.setAttribute("popfrom", popfrom);
		Map<String, TProjectAccounting> map = this.contractService.getContractAccounting(strContractId);
		TProjectAccounting ta;
		for (int i = 1; i < map.size() + 1; i++) {
			ta = (TProjectAccounting) map.get(i + "");
			request.setAttribute(ta.getCurrency().toLowerCase()
					+ ta.getAccountingItemId(), ta.getAccountingValue());
		}
		
		
		String from = request.getParameter("from");
		//传递 不经审批写入SAP 状态
		String st = request.getParameter("shortSAP");
		if(st!=null && "true".equals(st))
			request.setAttribute("shortSAP","true");
		if(from!=null){
			if(from.equals("changeW")){
				request.setAttribute("from", "changeW");
				return new ModelAndView("sapss/contract/Archives/saleContractRChange");
			}
			else if(from.equals("changeR")){
				request.setAttribute("from", "changeR");
				return new ModelAndView("sapss/contract/Archives/saleContractRChange");
			}
		}
		return new ModelAndView("sapss/contract/Archives/archSaleContract");

	}

	/**
	 * 流程展现
	 * 
	 * @param request
	 * @param resp
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView purchaseExamine(HttpServletRequest request,
			HttpServletResponse resp) throws IllegalAccessException,
			InvocationTargetException {
		String strContractId = request.getParameter("businessRecordId");

		String taskId = request.getParameter("taskId");
		request.setAttribute("taskId", taskId);
		request.setAttribute("businessRecordId", strContractId);

		TContractPurchaseInfo tContractPurchaseInfo = contractHibernateService
				.queryContractPurchaseInfoById(strContractId);
		String tradeType = SysCachePoolUtils.getDictDataValue("BM_BUSINESS_TYPE", tContractPurchaseInfo.getTradeType());
		request.setAttribute("tradeType", tradeType);
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		// 在流程审批过程中如果又回到修改合同界面时就让可以修改内容-->流程中如果合同信息需修改,要一步步退回到发起人(合同创建者)处修改
		// 注: 采购合同流程"合同信息修改"节点,有页面资源且是该合同创建者
		Boolean modify = false;
		String writeSap = "true";
		/*List list = userContext.getGrantedResources();
		for (int i = 0; i < list.size(); i++) {
			SysResource sysResource = (SysResource) list.get(i);
			if ("purchase_update".equals(sysResource.getResourcename())) {
				modify = true;
			}
			// if ("purchase_tosap".equals(sysResource.getResourcename())) {
			// writeSap = "false";
			// }
		}*/
		//创建者能修改：
		if(tContractPurchaseInfo.getCreator().equals(userContext.getSysUser().getUserId())){
			writeSap = "true";
			modify = true;
		}
		// 只有信息中心允许手工执行写入动作
		if ("XXZX".equals(userContext.getSysDept().getDeptShortCode())) {
			writeSap = "false";
			modify = true;
		}
		// 贸管专员允许修改
		if ("MG".equals(userContext.getSysDept().getDeptShortCode())) {
			modify = true;
		}
		// 判断修改,写入SAP权限
		if (modify) {
			request.setAttribute("iframeSrc",
					"contractController.spr?action=addPurchaseContractView&contractid="
							+ strContractId + "&modify=true&writeSap="
							+ writeSap);
		} else {
			request.setAttribute("iframeSrc",
					"contractController.spr?action=ArchPurchaseInfoView&businessRecordId="
							+ strContractId + "&writeSap=" + writeSap);
		}

		return new ModelAndView("sapss/contract/Archives/PurchaseWorkFlow");
	}

	/**
	 * 流程展现(销售合同)
	 * 
	 * @param request
	 * @param resp
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView salesExamine(HttpServletRequest request,
			HttpServletResponse resp) throws IllegalAccessException,
			InvocationTargetException {
		String strContractId = request.getParameter("businessRecordId");

		String taskId = request.getParameter("taskId");
		request.setAttribute("taskId", taskId);
		request.setAttribute("businessRecordId", strContractId);

		TContractSalesInfo tContractSalesInfo = contractHibernateService
				.queryContractSalesInfoById(strContractId);
		String tradeType = SysCachePoolUtils.getDictDataValue("BM_BUSINESS_TYPE", tContractSalesInfo.getTradeType());
		request.setAttribute("tradeType", tradeType);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Boolean modify = false;
		String writeSap = "true";
		/*List list = userContext.getGrantedResources();
		for (int i = 0; i < list.size(); i++) {
			SysResource sysResource = (SysResource) list.get(i);
			if ("sales_update".equals(sysResource.getResourcename())) {
				modify = true;
			}
			// if ("sales_tosap".equals(sysResource.getResourcename())) {
			// writeSap = true;
			// }
		}*/
		//创建者能修改：
		if(tContractSalesInfo.getCreator().equals(userContext.getSysUser().getUserId())){
			writeSap = "true";
			modify = true;
		}
		// 只有信息中心允许手工执行写入动作
		if ("XXZX".equals(userContext.getSysDept().getDeptShortCode())) {
			writeSap = "false";
			modify = true;
		}
		// 贸管专员审核允许修改
		if ("MG".equals(userContext.getSysDept().getDeptShortCode())) {
			modify = true;
		}
		// 有修改权限进入修改页面
		if (modify) {
			request.setAttribute("iframeSrc",
					"contractController.spr?action=addSaleContractView&contractid="
							+ strContractId + "&modify=true&writeSap="
							+ writeSap);
		} else {
			request.setAttribute("iframeSrc",
					"contractController.spr?action=archSalesInfoView&businessRecordId="
							+ strContractId + "&writeSap=" + writeSap);
		}
		// request.setAttribute("iframeSrc",
		// "contractController.spr?action=archSalesInfoView&businessRecordId="+strContractId);

		return new ModelAndView("sapss/contract/Archives/saleWorkFlow");
	}
	/**
	 * 补充审批(销售合同)
	 * 
	 * @param request
	 * @param resp
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView seniorExamine(HttpServletRequest request,
			HttpServletResponse resp) throws IllegalAccessException,
			InvocationTargetException {
		String strContractId = request.getParameter("businessRecordId");

		String taskId = request.getParameter("taskId");
		request.setAttribute("taskId", taskId);
		request.setAttribute("businessRecordId", strContractId);

		TContractSalesInfo tContractSalesInfo = contractHibernateService
				.queryContractSalesInfoById(strContractId);
		String tradeType = SysCachePoolUtils.getDictDataValue("BM_BUSINESS_TYPE", tContractSalesInfo.getTradeType());
		request.setAttribute("tradeType", tradeType);

		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		Boolean modify = false;
		String writeSap = "true";
		/*List list = userContext.getGrantedResources();
		for (int i = 0; i < list.size(); i++) {
			SysResource sysResource = (SysResource) list.get(i);
			if ("sales_update".equals(sysResource.getResourcename())) {
				modify = true;
			}
			// if ("sales_tosap".equals(sysResource.getResourcename())) {
			// writeSap = true;
			// }
		}*/
		//创建者能修改：
		if(tContractSalesInfo.getCreator().equals(userContext.getSysUser().getUserId())){
			writeSap = "true";
			modify = true;
		}
		// 只有信息中心允许手工执行写入动作
		if ("XXZX".equals(userContext.getSysDept().getDeptShortCode())) {
			writeSap = "false";
			modify = true;
		}
		// 有修改权限进入修改页面
		if (modify) {
			request.setAttribute("iframeSrc",
					"contractController.spr?action=addSaleContractView&contractid="
							+ strContractId + "&modify=true&writeSap="
							+ writeSap);
		} else {
			request.setAttribute("iframeSrc",
					"contractController.spr?action=archSalesInfoView&businessRecordId="
							+ strContractId + "&writeSap=" + writeSap);
		}
		// request.setAttribute("iframeSrc",
		// "contractController.spr?action=archSalesInfoView&businessRecordId="+strContractId);
        request.setAttribute("person", request.getParameter("person"));
		return new ModelAndView("sapss/workflow/particular/saleWorkFlow");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void seniorSubmitSalesInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String businessRecordId = request.getParameter("businessRecordId");
		String workflowExamine = request.getParameter("workflowExamine");
		String person = request.getParameter("person");
		
		if(person!=null && "1".equals(person))
			person = "股份公司总经理审批";
		else
			person = "股份公司董事长审批 ";
		
		this.saleContractJdbcService.saveToWorkflow(businessRecordId,workflowExamine,person);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		this.operateSuccessfullyWithString(response, jo.toString());

	}

	/**
	 * 流程提交审批
	 * 
	 * @param request
	 * @param response
	 * @param projectInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public void submitPurchaseInfo(HttpServletRequest request,
			HttpServletResponse response, TContractPurchaseInfo info)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String msg="";
		//try{
		String taskId = request.getParameter("workflowTaskId");
		String contractPuchaseId = request.getParameter("contractPurchaseId");
		// 五部,纸销售组,可以提交 “写入SAP订单”动作
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String taskName = (String) request
				.getParameter("workflowCurrentTaskName");
		String transitionName = info.getWorkflowLeaveTransitionName();
		String group = info.getEkkoEkgrp();
		String deptCode = userContext.getSysDept().getDeptcode();
		/**20140630采购订单不允许直接写入SAP了*/
		if("写入SAP订单".equals(transitionName.trim())){
			throw new BusinessException("不允许直接写入SAP");
		}
		/*外贸代理出口业务5,由业务经理通过提交"直接写入SAP",/20140630取消控制
		if("5".equals(info.getTradeType())&& "部门经理确认".equals(taskName) && "确认提交".equals(transitionName))
			throw new BusinessException("外贸代理出口业务,请选择'写入SAP订单'进行提交");
		*/
		/*不执行//20140630取消控制
		else if(!"5".equals(info.getTradeType()))
			if (null != taskId&& !contractService.checkAllowPurcharseSubmit(taskName, transitionName,group, deptCode, info.getTradeType(),""))
				throw new BusinessException("不是五部内贸纸业务,除此之外的业务活动不允许直接写入SAP订单，请检查!");
		*/		
		//贸管检查
		/*if(!"2".equals(info.getTradeType())&& "贸管经理审核".equals(taskName) && "同意".equals(transitionName))
			throw new BusinessException("该操作只适用于合作出口订单");
		else if("2".equals(info.getTradeType())&&Double.parseDouble(info.getTotal())>2000000&& "贸管经理审核".equals(taskName) && "同意".equals(transitionName)){
			throw new BusinessException("该操作只适用于合作出口订单,且金额小于200万CNY");
		}*/
		//
		info.setContractPurchaseId(contractPuchaseId);
		contractService.submitPurchaseInfo(taskId, info);
		//} catch (Exception e) {
		//	msg = "异常类:" + e.getClass().getName() + "<br>异常信息:"+ e.getMessage();
		//	e.printStackTrace();
		//}
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		JSONObject jo = new JSONObject();
		if ("".equals(msg))
			jo.put("returnMessage", "提交成功！");
		else
			jo.put("returnMessage", msg);
		this.operateSuccessfullyWithString(response, jo.toString());

	}

	/**
	 * 采购合同新增提交审批
	 * 
	 * @param request
	 * @param response
	 * @param projectInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public void submitOperationPurchaseInfo(HttpServletRequest request,
			HttpServletResponse response, TContractPurchaseInfo info)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String taskId = request.getParameter("workflowTaskId");
		String contractPuchaseId = request.getParameter("contractPurchaseId");
		info.setContractPurchaseId(contractPuchaseId);
		// 五部,纸销售组,可以提交 “写入SAP订单”动作
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String taskName = (String) request
				.getParameter("workflowCurrentTaskName");
		String transitionName = info.getWorkflowLeaveTransitionName();
		String group = info.getEkkoEkgrp();
		String deptCode = userContext.getSysDept().getDeptcode();
		if (null != taskId
				&& !contractService.checkAllowPurcharseSubmit(taskName, transitionName,
						group, deptCode, info.getTradeType(),""))
			throw new BusinessException(
					"不是五部内贸纸业务,除此之外的业务活动不允许直接写入SAP订单，请检查!");
		this.accessPurchaseMoney(info);
		StringBuffer sb = new StringBuffer();
		sb.append(userContext.getSysDept().getDeptname());
		sb.append("|");
		sb.append(userContext.getSysUser().getRealName());
		sb.append("|");
		sb.append("合同编号:");
		sb.append(info.getContractNo());
		sb.append("|合同名称:");
		sb.append(info.getContractName());
		sb.append("");
		info.setWorkflowBusinessNote(sb.toString());
		contractService.submitPurchaseInfo(taskId, info);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 保存并提交流程审批(销售合同)
	 * 
	 * @param request
	 * @param response
	 * @param projectInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public void submitAndSaveSalesInfo(HttpServletRequest request,
			HttpServletResponse response, TContractSalesInfo info)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String taskId = request.getParameter("workflowTaskId");
		String contractSalesId = request.getParameter("contractSalesId");
		/****/
		Map<String,String> map = saleContractJdbcService.sumSales(contractSalesId,info.getVbkdKurrf()); 
        Double contralSum = Double.valueOf(map.get("moneyTotal"))*contractService.getCurrencyRate(info.getVbakWaerk());
        contractService.checkSalesBalance(info, contralSum);
		/*****/
        info.setOrderNet(map.get("netTotal"));
        info.setOrderTaxes(map.get("taxTotal"));
        info.setOrderTotal(map.get("moneyTotal"));
        info.setTotalQuality(map.get("totalQuality"));
       
        Map<String, TProjectAccounting> projectAccountingMap = this.contractService.getContractAccounting(contractSalesId);
		if(projectAccountingMap.size()==0 && !"2".equals(info.getTradeType())){
			throw new BusinessException(
			"请填写测算表的信息保存后再提交！");
		}
		
		
		// 五部,纸销售组,可以提交 “写入SAP订单”动作
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String taskName = (String) request
				.getParameter("workflowCurrentTaskName");
		String transitionName = info.getWorkflowLeaveTransitionName();
		String group = info.getVbakVkgrp();
		String deptCode = userContext.getSysDept().getDeptcode();
		if (null != taskId
				&& !contractService.checkAllowSubmit(taskName, transitionName,
						group, deptCode, info.getTradeType(),"salesContract"))
			throw new BusinessException(
					"不是自营出口*、进料加工及五部纸业务之外的业务活动不允许直接写入SAP订单，请检查!");
		info.setOrderState("2");
		String applyTime = (new java.text.SimpleDateFormat(
				"yyyy-MM-dd")).format(new Date());
		info.setApplyTime(applyTime);
		contractHibernateService.saveContractSalesInfo(info);
		info.setContractSalesId(contractSalesId);
		String workflowBusinessNote = userContext.getSysDept().getDeptname()
				+ "|" + userContext.getSysUser().getRealName() + "|";
		workflowBusinessNote = workflowBusinessNote + "合同编号："
				+ info.getContractNo() + "|合同名称:" + info.getContractName();
		info.setWorkflowBusinessNote(workflowBusinessNote);
		contractService.submitSalesInfo(taskId, info);

		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 流程提交审批(销售合同)
	 * 
	 * @param request
	 * @param response
	 * @param projectInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public void submitSalesInfo(HttpServletRequest request,
			HttpServletResponse response, TContractSalesInfo info)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String msg="";
		//try{
		String taskId = request.getParameter("workflowTaskId");
		String contractSalesId = request.getParameter("contractSalesId");
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String taskName = (String) request
				.getParameter("workflowCurrentTaskName");
		String transitionName = info.getWorkflowLeaveTransitionName();
		String group = info.getVbakVkgrp();
		String deptCode = userContext.getSysDept().getDeptcode();
		boolean isOver3OUSD = contractService.getIsOver30WUSD(info.getOrderTotal(), info.getVbakWaerk());
		
		if (null != taskId
				&& !contractService.checkAllowSubmit(taskName, transitionName,
						group, deptCode, info.getTradeType(),"salesContract"))
			throw new BusinessException(
					"不是代理进出口、自营出口*、进料加工外销及五部纸业务之外的业务活动不允许直接写入SAP订单，请检查!");
		if ("写入SAP订单".equals(transitionName.trim())&&isOver3OUSD)
			throw new BusinessException("金额大于30万美金不允许直接写入SAP订单，请检查!");
		info.setContractSalesId(contractSalesId);
		contractService.submitSalesInfo(taskId, info);
		//} catch (Exception e) {
		//	msg = "异常类:" + e.getClass().getName() + "<br>异常信息:"+ e.getMessage();
		//	e.printStackTrace();
		//}
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		JSONObject jo = new JSONObject();
		if ("".equals(msg))
			jo.put("returnMessage", "提交成功！");
		else
			jo.put("returnMessage", msg);
		this.operateSuccessfullyWithString(response, jo.toString());
	}

	/**
	 * 提交写入SAP(销售合同)
	 * 
	 * @param request
	 * @param response
	 * @param projectInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public void submitToSapSalesInfo(HttpServletRequest request,
			HttpServletResponse response)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String contractSalesId = request.getParameter("contractSalesId");
		TContractSalesInfo tContractSalesInfo = contractHibernateService
				.querySales(contractSalesId);
		TBapiLog bapiLog = bapiHibernateService
				.insertSalesOrder(tContractSalesInfo);
		String sapOrderNo = bapiLog.getSapOrderNo();
		JSONObject jo = new JSONObject();
		if (sapOrderNo != null && !"".equals(sapOrderNo)) {
			tContractSalesInfo = contractHibernateService
					.getContractSalesInfo(contractSalesId);
			tContractSalesInfo.setSapOrderNo(sapOrderNo);
			contractHibernateService.saveContractSalesInfo(tContractSalesInfo);
			jo.put("returnMessage", "销售合同写入SAP成功！返回订单号："+sapOrderNo);
		} else {
			String logDetail = bapiHibernateService.getBapiLogText(bapiLog);
			jo.put("returnMessage", "销售合同写入SAP失败！"+logDetail);
		}
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	 * 提交写入SAP(采购合同)
	 * 
	 * @param request
	 * @param response
	 * @param projectInfo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public void submitToSapPurchaserInfo(HttpServletRequest request,
			HttpServletResponse response)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String strContractPurchaseId = request
				.getParameter("contractPurchaseId");
		TContractPurchaseInfo tContractPurchaseInfo = contractHibernateService
				.queryPurchase(strContractPurchaseId);

		TBapiLog bapiLog = bapiHibernateService
				.insertPurcharseOrder(tContractPurchaseInfo);

		String sapOrderNo = bapiLog.getSapOrderNo();
		JSONObject jo = new JSONObject();
		if (sapOrderNo != null && !"".equals(sapOrderNo)) {
			tContractPurchaseInfo = contractHibernateService
					.getContractPurchaseInfo(strContractPurchaseId);
			tContractPurchaseInfo.setSapOrderNo(sapOrderNo);
			contractHibernateService
					.saveContractPurchaseInfo(tContractPurchaseInfo);
			jo.put("returnMessage", "采购合同写入SAP成功！返回订单号："+sapOrderNo);
		} else {
			String logDetail = bapiHibernateService.getBapiLogText(bapiLog);
			jo.put("returnMessage", "采购合同写入SAP失败！"+logDetail);
		}

		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView openSubmitParticularWorkflow(HttpServletRequest request,HttpServletResponse response){
		String bizId = request.getParameter("bizId");
		String particularId = request.getParameter("particularId");
		request.setAttribute("particularId",particularId);
		String view = request.getParameter("view");
		//设置标题
		String title="";
		if(view!=null){
			if(view.indexOf("Sale")!=-1)
				title="销售合同";
			else
				title="采购合同";
		}
		request.setAttribute("title", title);
		String parms="view,"+view;
		request.setAttribute("iframeSrc", "contractController.spr?action="+view+"&businessRecordId="+bizId);
		//判断是否来自待办链接
		if(request.getParameter("fromParticular")==null){
			request.setAttribute("submitUrl", "particularWorkflowController.spr");
			request.setAttribute("parameters", "?action=firstSubmitParticularWorkflow"+
					"&bizId="+bizId+"&parms="+parms+"&controller=contractController&title="+title);
		}
		return new ModelAndView("sapss/workflow/particular/particularWorkflowPage");

	}
	
	public void changeSalesRate(HttpServletRequest request,
			HttpServletResponse response)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String contractSalesId = request.getParameter("contractSalesId");
        String sapOrderNo = request.getParameter("sapOrderNo");
		String rate = request.getParameter("rate");
		JSONObject jo = new JSONObject();
		if (rate==null||"".equals(rate)) {
			jo.put("returnMessage", "汇率为空，请填写汇率");
		}else if(sapOrderNo==null||"".equals(sapOrderNo)){
			jo.put("returnMessage", "订单号为空，不能写入，请确认该订单是否已经导入SAP系统");
		}
		else {
			Map<String,Object> r = contractService.updateContractSalesRate(contractSalesId,sapOrderNo,rate);
			if((Integer)r.get("isSuccess")==0)
		       jo.put("returnMessage", "销售订单汇率写入SAP成功！");
			else 
			   jo.put("returnMessage", "销售订单汇率写入SAP失败！<br>错误日志："+r.get("msg"));
		}
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	
	public ModelAndView addReferenceView(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setAttribute("contractgroupid", request.getParameter("contractgroupid"));
		request.setAttribute("tradetype", request.getParameter("tradetype"));
		request.setAttribute("projectid", request.getParameter("projectid"));
		String sfas = request.getParameter("projectname");		
		request.setAttribute("projectname", sfas);
		request.setAttribute("contractgroupno", request.getParameter("contractgroupno"));
		return new ModelAndView("sapss/contract/referenceCreate");
	}
	
	
	
	public void addReferenceSaleContract(HttpServletRequest request,
			HttpServletResponse response, TContractGroup tContractGroup)
			throws IOException {
		String strContractGroupId = tContractGroup.getContractGroupId();
		String strContractGroupNo = tContractGroup.getContractGroupNo();
		String strProjectId = tContractGroup.getProjectId();
		String strProjectName = tContractGroup.getProjectName();
		
		String strContractType = request.getParameter("tradetype");
		String contractNo = request.getParameter("contractNo");
		TProjectInfo tProjectInfo = contractService
		.queryProjectInfo(strProjectId);
		if(!com.infolion.platform.dpframework.util.DateUtils.isDateBefore(tProjectInfo.getAvailableDataEnd()+" 23:59:59")){
			throw new BusinessException("立项时间已过了期效，请变更期效再新增合同！");
		}
		
		String contractId = saleContractJdbcService.queryContractIdByNo(contractNo);
		if(contractId==null) throw new BusinessException("请录入正确的合同编码！");
		// ////////
		TContractSalesInfo tContractSalesInfo = contractHibernateService.querySales(contractId);
		if(!tContractSalesInfo.getTradeType().equals(strContractType)) throw new BusinessException("请输入相同贸易类型下的合同编码！");
		//客户信息校验
		if(!contractService.isExistCusSupp(tContractSalesInfo.getDeptId(), "sales", tContractSalesInfo.getKuagvKunnr())){
			throw new BusinessException("客户信息已失效，请先维护客户主数据！");
		}
		// 采购合同ID
		tContractSalesInfo.setContractSalesId(CodeGenerator.getUUID());
		
		// 合同编号
		tContractSalesInfo.setContractNo(contractService.getContractNo(
				strContractGroupId, strContractGroupNo, strContractType, "2",
				""));
		// 是否有效
		tContractSalesInfo.setIsAvailable("1");
		tContractSalesInfo.setApplyTime(null);
		tContractSalesInfo.setApprovedTime(null);
		// 创建时间
		tContractSalesInfo.setCreateTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		// 合同组ID
		tContractSalesInfo.setContractGroupId(strContractGroupId);
		// 项目ID
		tContractSalesInfo.setProjectId(strProjectId);
		// 项目名称
		tContractSalesInfo.setProjectName(strProjectName);
		tContractSalesInfo.setSapOrderNo("");
		// 信达项目号
		tContractSalesInfo.setVbakBname(strContractGroupNo.substring(0, 6));
		// 合同状态(创建状态)
		tContractSalesInfo.setOrderState("1");
		// 取用户相关信息
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		// 部门编号
		tContractSalesInfo.setDeptId(userContext.getSysDept().getDeptid());
		// 创建者
		tContractSalesInfo.setCreator(userContext.getSysUser().getUserId());
		// 销售组织(vbakVkorg)
		tContractSalesInfo.setVbakVkorg(userContext.getSysDept().getXszzcode());
		// 销售部门(vbakVkbur)
		tContractSalesInfo.setVbakVkbur(userContext.getSysDept().getDeptcode());
		// 初始化分销渠道,凭证类型(vbakVtweg,vbakAuart)
		//assemSalesVoucher(tContractSalesInfo,strContractType);
		
		// 订单总值
		tContractSalesInfo.setOrderTotal("0");
		// 订单净值
		tContractSalesInfo.setOrderNet("0");
		// 订单销项税金
		tContractSalesInfo.setOrderTaxes("0");
		
		contractHibernateService.saveContractSalesAll(tContractSalesInfo);

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("returnMessage", "创建成功!");
		jsonObject.put("contractId", tContractSalesInfo.getContractSalesId());
		jsonObject.put("contractType","1");
			
		String jsontext = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	public void addReferencePurcharseContract(HttpServletRequest request,
			HttpServletResponse response, TContractGroup tContractGroup)
			throws IOException {
		String strContractGroupId = tContractGroup.getContractGroupId();
		String strContractGroupNo = tContractGroup.getContractGroupNo();
		String strProjectId = tContractGroup.getProjectId();
		String strProjectName = tContractGroup.getProjectName();// request.getParameter("projectname");
		String strContractType = request.getParameter("tradetype");
		String contractNo = request.getParameter("contractNo");
		TProjectInfo tProjectInfo = contractService
		.queryProjectInfo(strProjectId);
		if(!com.infolion.platform.dpframework.util.DateUtils.isDateBefore(tProjectInfo.getAvailableDataEnd()+" 23:59:59")){
			throw new BusinessException("立项时间已过了期效，请变更期效再新增合同！");
		}
		String contractId = purchaseContractJdbcService.queryContractIdByNo(contractNo);
		if(contractId==null) throw new BusinessException("请录入正确的合同编码！");
		String strPurchaserType = "";
		if(strContractType.equals("8")) strPurchaserType=contractNo.substring(9, 10);
		
		TContractPurchaseInfo tContractPurchaseInfo = contractHibernateService.queryPurchase(contractId);
		if(!tContractPurchaseInfo.getTradeType().equals(strContractType)) throw new BusinessException("请填写相同贸易类型下的合同编码！");
		//供应商信息校验
		if(!contractService.isExistCusSupp(tContractPurchaseInfo.getDeptId(), "purchase", tContractPurchaseInfo.getEkkoLifnr())){
			throw new BusinessException("供应商信息已失效，请先维护供应商主数据！");
		}
		
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		// 采购合同ID
		tContractPurchaseInfo.setContractPurchaseId(CodeGenerator.getUUID());
		// 合同组ID
		tContractPurchaseInfo.setContractGroupId(strContractGroupId);
		// 项目ID
		tContractPurchaseInfo.setProjectId(strProjectId);
		// 项目名称
		tContractPurchaseInfo.setProjectName(strProjectName);
		// 合同编号
		tContractPurchaseInfo.setContractNo(contractService.getContractNo(
				strContractGroupId, strContractGroupNo, strContractType, "1",
				strPurchaserType.length()>1?strPurchaserType.substring(0, 1):strPurchaserType));


		
		// 采购组织
		// userContext.getSysDept().getCgzzcode()
		tContractPurchaseInfo.setEkkoEkorg(userContext.getSysDept()
				.getCgzzcode());
		// 采购合同号
		tContractPurchaseInfo.setEkkoIhrez(tContractPurchaseInfo
				.getContractNo());
		// 装运港
		
		tContractPurchaseInfo.setShipmentPort(tProjectInfo.getShipmentPort());
		// 目的港
		tContractPurchaseInfo.setDestinationPort(tProjectInfo
				.getDestinationPort());
		// 装运期
		tContractPurchaseInfo.setShipmentDate(tProjectInfo.getShipmentDate());
		tContractPurchaseInfo.setSapOrderNo("");
		// 部门编号
		tContractPurchaseInfo.setDeptId(userContext.getSysDept().getDeptid());
		// 是否有效
		tContractPurchaseInfo.setIsAvailable("1");
		// 创建时间
		tContractPurchaseInfo.setCreateTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		// 创建者
		tContractPurchaseInfo.setCreator(userContext.getSysUser().getUserId());
		// 订单状态
		tContractPurchaseInfo.setOrderState("1");
		tContractPurchaseInfo.setApplyTime("");
		tContractPurchaseInfo.setApprovedTime("");
		// 不含税金额汇总
		tContractPurchaseInfo.setTotalAmount("0");
		// 税金汇总
		tContractPurchaseInfo.setTotalTaxes("0");
		// 含税金额汇总
		tContractPurchaseInfo.setTotalAmountTaxes("0");
		// 运费汇总
		tContractPurchaseInfo.setTotalFreight("0");
		// 关税汇总
		tContractPurchaseInfo.setTotalTariff("0");
		// 消费税汇总
		tContractPurchaseInfo.setTotalCt("0");
		// 进项税汇总
		tContractPurchaseInfo.setTotalIt("0");
		// 总金额
		tContractPurchaseInfo.setTotal("0");
		// tContractPurchaseInfo.validateMe();
		contractHibernateService
				.saveContractPurchaseInfoAll(tContractPurchaseInfo);

		
		
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("returnMessage", "创建成功!");
		jsonObject.put("contractId", tContractPurchaseInfo.getContractPurchaseId());
		jsonObject.put("contractType","2");	
		String jsontext = jsonObject.toString();

		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	@Autowired
	private SysWfUtilsService sysWfUtilsService;
	public void setSysWfUtilsService(SysWfUtilsService sysWfUtilsService) {
		this.sysWfUtilsService = sysWfUtilsService;
	}
	public ModelAndView dealPrintV2(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String contractId = request.getParameter("contractId");
		TContractSalesInfo info = this.contractHibernateService.querySales(contractId);
		info.setVbkdZtermName(SysCachePoolUtils.getDictDataValue("BM_PAYMENT_TYPE", info.getVbkdZterm()));
		info.setVbkdZlschName(SysCachePoolUtils.getDictDataValue("BM_PAY_TYPE", info.getVbkdZlsch()));
		info.setVbkdBzirkName(SysCachePoolUtils.getDictDataValue("BM_SALES_DISTRICT", info.getVbkdBzirk()));
		
		info.setVbkdInco1Name(SysCachePoolUtils.getDictDataValue("BM_INCOTERM_TYPE", info.getVbkdInco1()));
		List<TaskHisDto> list = sysWfUtilsService.queryTaskHisList(contractId);
		List<CommonChangeDto> changeList = saleContractJdbcService.queryChangeDtos(contractId);
		request.setAttribute("changeList", changeList);
		request.setAttribute("taskHis", list);
		request.setAttribute("sales", info);
		return new ModelAndView("sapss/contract/Archives/printV2");
    }
	
	public ModelAndView dealPrintV1(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String contractId = request.getParameter("contractId");
		TContractPurchaseInfo info = this.contractHibernateService.queryPurchase(contractId);
		info.setPayType(SysCachePoolUtils.getDictDataValue("BM_PAY_TYPE", info.getPayType()));
		info.setEkkoZtermName(SysCachePoolUtils.getDictDataValue("BM_PAYMENT_TYPE", info.getEkkoZterm()));
		info.setEkkoInco1Name(SysCachePoolUtils.getDictDataValue("BM_INCOTERM_TYPE", info.getEkkoInco1()));
		List<TaskHisDto> list = sysWfUtilsService.queryTaskHisList(contractId);
		List<CommonChangeDto> changeList = purchaseContractJdbcService.queryChangeDtos(contractId);
		request.setAttribute("changeList", changeList);
		request.setAttribute("taskHis", list);
		request.setAttribute("purchase", info);
		return new ModelAndView("sapss/contract/Archives/printV1");
    }
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-25
	 * 在收付款界面的金额分配行项双击"销售合同号"时，弹出合同查看窗口
	 */
	public ModelAndView viewSaleContract(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String contractType = request.getParameter("contractType");
		String contractNo = request.getParameter("contractno");
		String strContractId = this.contractService.getContractIdByNo(contractNo, 1);

		TContractSalesInfo tContractSalesInfo = new TContractSalesInfo();

		tContractSalesInfo = contractHibernateService.queryContractSalesInfoById(strContractId);
		
		if((contractType!=null && "8".equals(contractType))|| 
				(tContractSalesInfo!=null&& "8".equals(tContractSalesInfo.getTradeType())))
			request.setAttribute("vbakVtwegDisable", false);//分销渠道--是否可选
		else
			request.setAttribute("vbakVtwegDisable", true);//分销渠道--是否可选

		tContractSalesInfo = accessSaleMoney(tContractSalesInfo);
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		request.setAttribute("writeSap", true);
		request.setAttribute("submit", false);
		request.setAttribute("sales", tContractSalesInfo);
		String fileEdit = "1".equals(userContext.getSysDept().getIsFuncDept())?"true":"false";
		request.setAttribute("fileEdit", fileEdit);
		// 取合同组信息
		TContractGroup tContractGroup = contractHibernateService
				.getContractGroup(tContractSalesInfo.getContractGroupId());
		request.setAttribute("contractGroup", tContractGroup);

		Map<String, DictionaryRow> ItemTypeMap = SysCachePoolUtils
				.getDictTableGroup("BM_ITEM_TYPE");
		Collection<DictionaryRow> ItemType = ItemTypeMap.values();
		request.setAttribute("ItemType", ItemType);

		Map<String, DictionaryRow> FactoryMap = SysCachePoolUtils
				.getDictTableGroup("BM_FACTORY");
		Collection<DictionaryRow> Factory = FactoryMap.values();
		request.setAttribute("Factory", Factory);
		// 付款条件
		Map<String, DictionaryRow> PayMentTypeMap = SysCachePoolUtils
				.getDictTableGroup("BM_PAYMENT_TYPE");
		Collection<DictionaryRow> PayMentType = PayMentTypeMap.values();
		request.setAttribute("PayMentType", PayMentType);
		//货币
		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);
		//销售地区
		Map<String, DictionaryRow> salesDistrict = SysCachePoolUtils.getDictTableGroup("BM_SALES_DISTRICT");
		Collection<DictionaryRow> district = salesDistrict.values();
		request.setAttribute("district", district);


		String strContractGroupName = contractService
				.queryContractGroupInfoByPurchaseId(strContractId);
		request.setAttribute("contractGroupName", strContractGroupName);
		request.setAttribute("balaceMsg", getBalanceMsg(tContractSalesInfo.getProjectId(),"sales",tContractSalesInfo.getDeptId()).get("msg"));
		return new ModelAndView("sapss/contract/Archives/archSaleContract");
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-25
	 * 在收付款界面的金额分配行项双击"采购合同号"时，弹出合同查看窗口
	 */
	public ModelAndView viewPurchaseContract(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		//extProcessDefinitionService.copyCreate('BBEB5E62-F6E3-4D05-9DE8-31AB8AB0B0B8','2B6C1E94-4C0C-43B6-9456-D92A4D41AA4C');
		//extProcessDefinitionService.copyCreate("BBEB5E62-F6E3-4D05-9DE8-31AB8AB0B0B8", "2B6C1E94-4C0C-43B6-9456-D92A4D41AA4C");
		String contractType = request.getParameter("contractType");
		String contractNo = request.getParameter("contractno");
		String strContractId = this.contractService.getContractIdByNo(contractNo, 2);

		TContractPurchaseInfo tContractPurchaseInfo = new TContractPurchaseInfo();
		tContractPurchaseInfo = contractHibernateService.queryContractPurchaseInfoById(strContractId);
		String strContractGroupName = contractService.queryContractGroupInfoByPurchaseId(strContractId);
		String strContractGroupNo = contractService.queryContractGroupByPurchaseId(strContractId).getContractGroupNo();

		request.setAttribute("purchase", tContractPurchaseInfo);
		request.setAttribute("contractGroupName", strContractGroupName);
		request.setAttribute("contractGroupNo", strContractGroupNo);

		// 用于控制前台的提交和关闭是否显示出来
		request.setAttribute("show", "false");
		//UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		request.setAttribute("fileEdit", false);
		request.setAttribute("writeSap", true);
		request.setAttribute("submit", false);

		Map<String, DictionaryRow> ItemTypeMap = SysCachePoolUtils
				.getDictTableGroup("BM_ITEM_TYPE");
		Collection<DictionaryRow> ItemType = ItemTypeMap.values();
		request.setAttribute("ItemType", ItemType);

		Map<String, DictionaryRow> FactoryMap = SysCachePoolUtils
				.getDictTableGroup("BM_FACTORY");
		Collection<DictionaryRow> Factory = FactoryMap.values();
		request.setAttribute("Factory", Factory);

		Map<String, DictionaryRow> MaterielGroupMap = SysCachePoolUtils
				.getDictTableGroup("BM_MATERIAL_GROUP");
		Collection<DictionaryRow> MaterielGroup = MaterielGroupMap.values();
		request.setAttribute("MaterielGroup", MaterielGroup);

		Map<String, DictionaryRow> WareHouseMap = SysCachePoolUtils
				.getDictTableGroup("BM_WAREHOUSE");
		Collection<DictionaryRow> WareHouse = WareHouseMap.values();
		request.setAttribute("WareHouse", WareHouse);

		Map<String, DictionaryRow> SelasTaxMap = SysCachePoolUtils
				.getDictTableGroup("BM_SALES_TAX");
		Collection<DictionaryRow> SelasTax = SelasTaxMap.values();
		request.setAttribute("SelasTax", SelasTax);

		Map<String, DictionaryRow> UnitMap = SysCachePoolUtils
				.getDictTableGroup("BM_UNIT");
		Collection<DictionaryRow> Unit = UnitMap.values();
		request.setAttribute("Unit", Unit);

		Map<String, DictionaryRow> InvoiceValidMap = SysCachePoolUtils
				.getDictTableGroup("BM_INVOICE_VALID");
		Collection<DictionaryRow> InvoiceValid = InvoiceValidMap.values();
		request.setAttribute("InvoiceValid", InvoiceValid);

		Map<String, DictionaryRow> CurrencyMap = SysCachePoolUtils.getDictTableGroup("BM_CURRENCY");
		Collection<DictionaryRow> Currency = CurrencyMap.values();
		request.setAttribute("Currency", Currency);
		
		Map<String, DictionaryRow> purchaseGroupMap = SysCachePoolUtils.getDictTableGroup("BM_PURCHASING_GROUP");
		Collection<DictionaryRow> purchase = purchaseGroupMap.values();
		request.setAttribute("purchaseGroup", purchase);
		
        request.setAttribute("balaceMsg", getBalanceMsg(tContractPurchaseInfo.getProjectId(),"purchase",tContractPurchaseInfo.getDeptId()).get("msg"));
		return new ModelAndView("sapss/contract/Archives/ArchPurchaseContract");
	}
	public ExtProcessDefinitionService getExtProcessDefinitionService() {
		return extProcessDefinitionService;
	}
	public void setExtProcessDefinitionService(
			ExtProcessDefinitionService extProcessDefinitionService) {
		this.extProcessDefinitionService = extProcessDefinitionService;
	}

	public void fileSales(HttpServletRequest request,	HttpServletResponse response) throws IOException {
		String strIdList = request.getParameter("idList");
		contractService.fileContract(strIdList, "sale");
		JSONObject jo = new JSONObject();		
		jo.put("returnMessage", "操作成功！");
		String jsonText = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsonText);
	}


	/**
	 * 保存核算页信息
	 * 
	 * @param request
	 * @param response
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public void saveDetail(HttpServletRequest req, HttpServletResponse resp,
			ProjectDetailVO vo) throws IllegalAccessException,
			InvocationTargetException, IOException, Exception {

		vo.setClassName(vo.getClassName() == null ? " " : vo.getClassName());
		vo.setDestinationPort(vo.getDestinationPort() == null ? " " : vo
				.getDestinationPort());
		vo.setNo(vo.getNo() == null ? Double.valueOf("0") : vo.getNo());
		vo.setShipmentDate(vo.getShipmentDate() == null ? " " : vo
				.getShipmentDate());
		vo.setShipmentPort(vo.getShipmentPort() == null ? " " : vo
				.getShipmentPort());
		vo.setSpec(vo.getSpec() == null ? " " : vo.getSpec());
		if(vo.getExchangeRate()==null||Double.parseDouble(vo.getExchangeRate())<=0)
			throw new BusinessException("请填写正确的汇率!");
//		if ("2".equals(req.getParameter("other5")))
//			vo.setSum(vo.getSum() == null ? "0" : (Double.valueOf(vo.getSum())
//					* Double.valueOf(Constants.properties.getProperty("exchange_usd_to_cny")))
//					+ "");
//		else
//			vo.setSum(vo.getSum() == null ? "0" : vo.getSum());
		
//		this.projectJdbcService.saveDetailInfo(vo);
		List<TProjectAccounting> list = new ArrayList<TProjectAccounting>();
		
		/****/
		dealAssemble(list,req);
        /****/
		int records = this.contractService
				.saveOrUpdateContractAccounting(list);

		resp.setCharacterEncoding("GBK");
		resp.setContentType("text/html; charset=GBK");
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "提交成功！");
		this.operateSuccessfullyWithString(resp, jo.toString());

	}
	
	private void dealAssemble(List<TProjectAccounting> list,HttpServletRequest req){
		String tradeType = req.getParameter("tradeType");
		String contractSalesId = req.getParameter("contractSalesId");
		Map<String,String> validate ;
		//内贸
		if(tradeType.equals("7")||tradeType.equals("10")||tradeType.equals("8")){
		   validate  = ProjectConstants.VALIDATE_HOMETRADE;
		   accountAssemble("other", 4, validate, req, contractSalesId, list);
		   for(Integer i:ProjectConstants.EV_D_TT_HOMETRADE){
			   accountAssemble("rmb", i, validate, req, contractSalesId, list);
		   }
		}
		//进口，进料加工（内销，外销）,转口
		else if(tradeType.equals("1")||tradeType.equals("3")||tradeType.equals("9")||tradeType.equals("11")||tradeType.equals("12")){
			validate  = ProjectConstants.VALIDATE_IMPORT;
			accountAssemble("other", 4, validate, req, contractSalesId, list);
			for(Integer i:ProjectConstants.EV_D_TT_IMPORT){
				   accountAssemble("rmb", i, validate, req, contractSalesId, list);
				   accountAssemble("us", i, validate, req, contractSalesId, list);
		    }
		}
		//出口
		else if(tradeType.equals("2")||tradeType.equals("4")){
			validate = ProjectConstants.VALIDATE_EXPORT;
			accountAssemble("other", 4, validate, req, contractSalesId, list);
			for(Integer i:ProjectConstants.EV_D_TT_EXPORT){
				   accountAssemble("rmb", i, validate, req, contractSalesId, list);
				   accountAssemble("us", i, validate, req, contractSalesId, list);
		    }
			accountAssemble("other", 3, validate, req, contractSalesId, list);
		}
		//代理
		else if(tradeType.equals("5")||tradeType.equals("6")){
			validate  = ProjectConstants.VALIDATE_PROXY;
			accountAssemble("other", 4, validate, req, contractSalesId, list);
			for(Integer i:ProjectConstants.EV_D_TT_PROXY){
				   accountAssemble("rmb", i, validate, req, contractSalesId, list);
				   accountAssemble("us", i, validate, req, contractSalesId, list);
		    }
		}
		else {
			validate = ProjectConstants.PROJECT_VALIDATE.get(tradeType);
			for (int i = 1; i < 32; i++) {
				accountAssemble("rmb", i, validate, req, contractSalesId, list);
			}
			for (int i = 1; i < 32; i++) {
				accountAssemble("us", i, validate, req, contractSalesId, list);
			}
			for (int i = 1; i < 5; i++) {
				accountAssemble("other", i, validate, req, contractSalesId, list);
			}
		}
			
	}
	
	private void accountAssemble(String itemName,int i,Map<String,String> validate,
			HttpServletRequest req,String contractSalesId,List<TProjectAccounting> list){
		String parameter = itemName + i;
		String cash = req.getParameter(parameter);
		if(validate.keySet().contains(parameter)&&(cash==null || "".equals(cash)))
			throw new BusinessException(validate.get(parameter)+",不能为空,请检查!");
		TProjectAccounting ta = new TProjectAccounting();
		ta.setAccountingItem(i + "");
		ta.setAccountingItemId(i);
		ta.setCurrency(itemName.toUpperCase());
		ta.setAccountingValue(cash == null || "".equals(cash) ? "0" : cash);
		ta.setContractSalesId(contractSalesId);
		ta.setProjectId(req.getParameter("projectId"));
		ta.setContractGroupId(req.getParameter("contractGroupId"));
		list.add(ta);
	}
	
	/**
	 * 查询合同组信息2_bw报表用查询报表zzh
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void findContractGroup(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		String strProjectId = request.getParameter("projectId");		
		String strContractGroupNo = request.getParameter("contractgroupno");

		String strSql = "select a.contract_group_id,a.project_id,a.contract_group_name,a.contract_group_no,a.project_name,a.cmd,a.create_time,c.real_name "
			+ "from t_contract_group a, t_sys_user c "
			+ "where a.is_available = '1' "
			+ "and a.creator = c.user_id "
			+ "and c.deleted = '1'"
			+ "and a.project_id='" + strProjectId + "'";
		if(null != strContractGroupNo && !"".equals(strContractGroupNo.trim()) ){
			strSql +=  "and a.contract_group_no like '%" + strContractGroupNo + "%'";
		}
		
		strSql += " order by a.create_time desc";
		String grid_columns = "CONTRACT_GROUP_ID,PROJECT_ID,CONTRACT_GROUP_NAME,CONTRACT_GROUP_NO,PROJECT_NAME,CMD,CREATE_TIME,REAL_NAME";

		String grid_size = request.getParameter("limit");
		request.setAttribute("grid_sql", strSql);
		request.setAttribute("grid_columns", grid_columns);
		request.setAttribute("grid_size", grid_size==null?"10":grid_size);

		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	

	public void filePurchase(HttpServletRequest request,	HttpServletResponse response) throws IOException {
		String strIdList = request.getParameter("idList");
		contractService.fileContract(strIdList, "purchase");
		JSONObject jo = new JSONObject();		
		jo.put("returnMessage", "操作成功！");
		String jsonText = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsonText);
	}
	
	public void mgModifySalesInfo(HttpServletRequest request,
			HttpServletResponse response,TContractSalesInfo tContractSalesInfoT)
			throws IllegalAccessException, InvocationTargetException,
			IOException, Exception {
		String contractSalesId = request.getParameter("contractSalesId");
		TContractSalesInfo tContractSalesInfo = contractHibernateService.getContractSalesInfo(contractSalesId);
		tContractSalesInfo.setMarginRatio(tContractSalesInfoT.getMarginRatio());
		tContractSalesInfo.setLaterDate(tContractSalesInfoT.getLaterDate());
		tContractSalesInfo.setCollectionDate(tContractSalesInfoT.getCollectionDate());
		tContractSalesInfo.setShipmentDate(tContractSalesInfoT.getShipmentDate());
		tContractSalesInfo.setIsPromise(tContractSalesInfoT.getIsPromise());
		
		contractHibernateService.saveContractSalesInfo(tContractSalesInfo);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "操作成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}
	
	public void mgModifyPurchaserInfo(HttpServletRequest request,
			HttpServletResponse response,TContractPurchaseInfo tContractPurchaseInfoT) throws IOException {
		String contractPurchaseId = request.getParameter("contractPurchaseId");
		TContractPurchaseInfo tContractPurchaseInfo = contractHibernateService.getContractPurchaseInfo(contractPurchaseId);
		tContractPurchaseInfo.setCollectionDate(tContractPurchaseInfoT.getCollectionDate());
		tContractPurchaseInfo.setLaterDate(tContractPurchaseInfoT.getLaterDate());
		tContractPurchaseInfo.setShipmentDate(tContractPurchaseInfoT.getShipmentDate());
		contractHibernateService.saveContractPurchaseInfo(tContractPurchaseInfo);
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "保存成功！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");
		this.operateSuccessfullyWithString(response, jsontext);
	}

	/**
	public void fuckDeal(HttpServletRequest request,	HttpServletResponse response) throws IOException {
		List list = contractService.fuckDealQuery();
		for(int i= 0 ;i<list.size();i++){
			Map map = (Map)list.get(i);
			String business_record_id = (String)map.get("business_record_id".toUpperCase());
			String sap_order_no = (String)map.get("sap_order_no".toUpperCase());
			String order_type = (String)map.get("order_type".toUpperCase());
			//
			if("1".equals(order_type)){
				TContractPurchaseInfo tContractPurchaseInfo = contractHibernateService.queryPurchase(business_record_id);
				TBapiLog bapiLog = bapiHibernateService.insertPurcharseOrder(tContractPurchaseInfo);
				String sapOrderNo = bapiLog.getSapOrderNo();
				contractService.fuckDeal(business_record_id, sap_order_no, "1", sapOrderNo);
				
			}else if("2".equals(order_type)){
				TContractSalesInfo tContractSalesInfo = contractHibernateService.querySales(business_record_id);
				TBapiLog bapiLog = bapiHibernateService.insertSalesOrder(tContractSalesInfo);
				String sapOrderNo = bapiLog.getSapOrderNo();
				contractService.fuckDeal(business_record_id, sap_order_no, "2", sapOrderNo);
			}

		}
		JSONObject jo = new JSONObject();
		jo.put("returnMessage", "操作完成共计"+list.size()+"条！");
		String jsontext = jo.toString();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=GBK");

		this.operateSuccessfullyWithString(response, jsontext);

	}**/

}
