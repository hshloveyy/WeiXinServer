/*
 * @(#)VoucherController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月30日 06点58分32秒
 *  描　述：创建
 */
package com.infolion.xdss3.voucher.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.outsideInterface.OutsidePersistenceService;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucherGen.web.VoucherControllerGen;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;
import com.infolion.xdss3.voucheritem.service.VoucherItemService;

/**
 * <pre>
 * 凭证预览(Voucher)控制器类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@BDPController(parent = "baseMultiActionController")
public class VoucherController extends VoucherControllerGen
{
	
	@Autowired
	protected VoucherItemService voucherItemService;
	
	public void setVoucherItemService(VoucherItemService voucherItemService)
	{
		this.voucherItemService = voucherItemService;
	}

	public void genVoucher(HttpServletRequest request, HttpServletResponse response)
	{
		// 绑定主对象值
		Voucher voucher = (Voucher) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), Voucher.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<VoucherItem> voucheritemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { voucher }, VoucherItem.class, null);
		Set<VoucherItem> deletedVoucherItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { voucher }, VoucherItem.class, null);
		
		Iterator<VoucherItem> VoucherItemit = voucheritemmodifyItems.iterator();		
		while(VoucherItemit.hasNext()){
			VoucherItem voucherItem = VoucherItemit.next();
			voucherItem.setAmount(voucherItem.getAmount().abs());
			voucherItem.setAmount2(voucherItem.getAmount2().abs());
			String rowNubmer = voucherItem.getRownumber();
//			补前导0，因为SAP会排序，导致外围行项目和SAP行项目不一致
			rowNubmer = StringUtils.leftPad(rowNubmer, 3, '0');
			voucherItem.setRownumber(rowNubmer);
		}
		voucher.setVoucherItem(voucheritemmodifyItems);
		this.voucherService._saveOrUpdate(voucher, deletedVoucherItemSet, getBusinessObject());

		OutsidePersistenceService.execute(voucher, "_submitProcess");
		
		JSONObject jo = new JSONObject();
		jo.put("voucherno", voucher.getVoucherno());
		this.operateSuccessfullyWithString(response,jo.toString());
	}
	
	public void genVoucherList(HttpServletRequest request, HttpServletResponse response)
	{
		String voucherids = request.getParameter("voucherids");
		String[] voucherid = voucherids.split(",");
		String businessId = "";
		List clearVoucherList = new ArrayList();
		List voucherList = new ArrayList();
		Boolean needClear = false;
		for (int i=0;i<voucherid.length;i++){
			Voucher voucher = this.voucherService._get(voucherid[i]);
			businessId = voucher.getBusinessid();
			//判断是否是清帐凭证 则暂时先不执行 
			if(("A").equals(voucher.getBstat())){
				clearVoucherList.add(voucher);
				needClear = true;
				continue;
			}else{
				voucherList.add(voucher);
			}
			if(StringUtils.isNullBlank(voucher.getVoucherno())) {
			    OutsidePersistenceService.execute(voucher, "_submitProcess");
			}
		}
		//对清帐凭证进行处理
		if(needClear){
			for(int i=0;i<clearVoucherList.size();i++){
				Voucher clearVoucher = (Voucher)clearVoucherList.get(i);
				Set<VoucherItem> voucherItems = clearVoucher.getVoucherItem();
				for (Iterator<VoucherItem> iter = voucherItems.iterator(); iter.hasNext();)
				{
					VoucherItem voucherItem = (VoucherItem) iter.next();
					if(StringUtils.isNullBlank(voucherItem.getVoucherno())&&!StringUtils.isNullBlank(voucherItem.getBusvoucherid())){
						Voucher voucher = this.voucherService.getVoucherById(voucherItem.getBusvoucherid());
						voucherItem.setVoucherno(voucher.getVoucherno());
					}
				}
				this.voucherService._saveOrUpdate(clearVoucher, null, getBusinessObject());
				
				if(StringUtils.isNullBlank(clearVoucher.getVoucherno())) OutsidePersistenceService.execute(clearVoucher, "_submitProcess");
			}
		}
		//处理凭证里的款、票状态
		this.voucherService.dealVoucher(voucherList);
		//处理清帐凭证里的款、票状态
		this.voucherService.dealClearVoucher(clearVoucherList);
		
		boolean isVoucherGen = this.voucherService.isVoucherGen(businessId);
		if (isVoucherGen) {
//		    this.voucherService.updateOldTitle(businessId);
		    this.voucherService.updateRelatedItem(businessId);
		}
		
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print("{'isVoucherGen':"+isVoucherGen+",'businessId':'"+businessId+"'}");
			System.out.println("{'isVoucherGen':'"+isVoucherGen+"','businessId':'"+businessId+"'}");
		} catch (IOException e) {
			logger.error("输出json失败," + e.getMessage(), e.getCause());
		}
	}
	
	/**
	 * 管理  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _manage(HttpServletRequest request, HttpServletResponse response)
	{ 
		String businessid = request.getParameter("businessid");
		String businesstype = request.getParameter("businesstype");
		request.setAttribute("businessid", businessid);
		request.setAttribute("businesstype", businesstype);
		request.setAttribute("vt", getBusinessObject().getViewText());
		return new ModelAndView("xdss3/voucher/voucherManage");
	}
	
	/**
	 * 编辑  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _confirm(HttpServletRequest request, HttpServletResponse response)
	{
		Voucher voucher = new Voucher();
		String id = request.getParameter("voucherid");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;
			
		if (StringUtils.isNullBlank(id))
		{	
			voucher = this.voucherService._getForEdit(id);        
		}else
        {
           voucher = this.voucherService._getForEdit(id);
        }
		//获取SAP账户
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String SAPAccount = this.voucherService.getSAPAccount(userContext.getUser().getUserName(), voucher.getGsber());
		voucher.setImporter(SAPAccount);
		voucher.setPreparer(SAPAccount);
		this.voucherService._saveOrUpdate(voucher, null, getBusinessObject());
		
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000305");
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);	
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);	
		request.setAttribute("vt", getBusinessObject().getViewText());
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		request.setAttribute("voucher", voucher);
		return new ModelAndView("xdss3/voucher/voucherConfirm");
	}
    
	/**
	 * 确认 
	 *   
	 * @param request
	 * @param response
	 */
	public void confirmVoucher(HttpServletRequest request, HttpServletResponse response)
	{
		// 绑定主对象值
		Voucher voucher = (Voucher) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), Voucher.class, true, request.getMethod(), true);
		// 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<VoucherItem> voucheritemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { voucher }, VoucherItem.class, null);
		Set<VoucherItem> deletedVoucherItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { voucher }, VoucherItem.class, null);
		voucher.setVoucherItem(voucheritemmodifyItems);
		voucher.setIsconfirm("Y");
		Iterator<VoucherItem> VoucherItemit = voucheritemmodifyItems.iterator();		
		while(VoucherItemit.hasNext()){
			VoucherItem voucherItem = VoucherItemit.next();			
			String rowNubmer = voucherItem.getRownumber();
//			补前导0，因为SAP会排序，导致外围行项目和SAP行项目不一致
			rowNubmer = StringUtils.leftPad(rowNubmer, 3, '0');
			voucherItem.setRownumber(rowNubmer);
		}
		this.voucherService._saveOrUpdate(voucher, deletedVoucherItemSet, getBusinessObject());

		this.operateSuccessfullyHiddenInfo(response);
	}
	
	/**
	 * 管理查询  
	 *   
	 * @param request
	 * @param response
	 */
	public ModelAndView _manageSearch(HttpServletRequest request, HttpServletResponse response)
	{ 
		String businessid = request.getParameter("businessid");
		String businesstype = request.getParameter("businesstype");
		request.setAttribute("businessid", businessid);
		request.setAttribute("businesstype", businesstype);
		com.infolion.platform.console.sys.context.UserContext xdssUserContext = com.infolion.platform.console.sys.context.UserContextHolder
		.getLocalUserContext().getUserContext();
		request.setAttribute("companycode", xdssUserContext.getSysDept().getCompanyCode());
		request.setAttribute("vt", getBusinessObject().getViewText());
		return new ModelAndView("xdss3/voucher/voucherManageSearch");
	}
	/**
	 * 该操作功能用于SAP系统数据丢失从外围重新生成，覆盖原来生成的凭证，
	 * @param request
	 * @param response
	 */
	public void genVoucher2(HttpServletRequest request, HttpServletResponse response)
	{
		com.infolion.platform.console.sys.context.UserContext xdssUserContext = com.infolion.platform.console.sys.context.UserContextHolder
		.getLocalUserContext().getUserContext();
		String user=xdssUserContext.getSysUser().getUserName();
		if(!"linhongkun".equals(user)){
			response.setContentType("text/html;charset=UTF-8");
			try {
				response.getWriter().print("{'error':'生成失败，请查看SAP凭证日志'}");
				System.out.println("{'error':'生成失败，请查看SAP凭证日志'}");
			} catch (IOException e) {
				logger.error("输出json失败," + e.getMessage(), e.getCause());
			}
			return;
		}
		String importdate_to = request.getParameter("importdate.fieldValue");
		String importdate_from = request.getParameter("importdate.fieldValue_from");
//		因为没有时间戳，只能取最大的凭证号
		String bigvoucherno = request.getParameter("voucherno.fieldValue");
		importdate_to = importdate_to.replace("-", "");
		importdate_from = importdate_from.replace("-", "");
		List<Voucher> vouchers = this.voucherService.getHasVoucherNoByImportdate(importdate_to, importdate_from);
		String businessId=genVoucher2(vouchers);
//		取票据的清账凭证
		vouchers = this.voucherService.getHasVoucherNoByImportdate2(importdate_to, importdate_from);
		businessId=genVoucher2(vouchers);
		
		if(StringUtils.isNotBlank(businessId)){
			boolean isVoucherGen = this.voucherService.isVoucherGen(businessId);
			try {
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print("{'isVoucherGen':"+isVoucherGen+",'businessId':'"+businessId+"'}");
				System.out.println("{'isVoucherGen':'"+isVoucherGen+"','businessId':'"+businessId+"'}");
			} catch (IOException e) {
				logger.error("输出json失败," + e.getMessage(), e.getCause());
			}
		}else{
			response.setContentType("text/html;charset=UTF-8");
			try {
				response.getWriter().print("{'error':'生成失败，请查看SAP凭证日志'}");
				System.out.println("{'error':'生成失败，请查看SAP凭证日志'}");
			} catch (IOException e) {
				logger.error("输出json失败," + e.getMessage(), e.getCause());
			}
			
		}
	}
	/**
	 * 
	 * @param vouchers
	 * @param bigvoucherno 最大凭证号
	 * @return
	 */
	public String genVoucher2(List<Voucher> vouchers) {
		String bussinessid2 ="";
//		2100公司 
		int bigno =  Integer.parseInt("0100006839");
		int bigno2 =  Integer.parseInt("1900000011");
		int bigno3 =  Integer.parseInt("1500000001");
		
//		bigno =  Integer.parseInt("0100033120");
//		bigno2 =  Integer.parseInt("1900000157");
//		bigno3 =  Integer.parseInt("1500000808");
		
		for (Voucher voucher:vouchers){
			//先判断是否已经有生成过了
			boolean isgen2 = this.voucherService.isgen(voucher.getVoucherid());
			String oldvoucherNo = voucher.getVoucherno();	
			int oldno =Integer.parseInt(oldvoucherNo);
			String no2 =oldvoucherNo.substring(0,2);
//			判断凭证号大小取最大凭证号
			boolean isbig =false;
			if("2400".equals(voucher.getCompanycode())){
				bigno =  Integer.parseInt("0100000787");
				bigno2 =  Integer.parseInt("1900000001");
				bigno3 =  Integer.parseInt("1500000001");
			}else if("2700".equals(voucher.getCompanycode())){
				bigno =  Integer.parseInt("0100000486");
				bigno2 =  Integer.parseInt("1900000001");
				bigno3 =  Integer.parseInt("1500000001");
			}else if("2200".equals(voucher.getCompanycode())){
				bigno =  Integer.parseInt("0100000151");
				bigno2 =  Integer.parseInt("1900000001");
				bigno3 =  Integer.parseInt("1500000001");
			}else if("2800".equals(voucher.getCompanycode())){
				bigno =  Integer.parseInt("0100000001");
				bigno2 =  Integer.parseInt("1900000001");
				bigno3 =  Integer.parseInt("1500000001");
			}else if("2900".equals(voucher.getCompanycode())){
				bigno =  Integer.parseInt("0100000206");
				bigno2 =  Integer.parseInt("1900000001");
				bigno3 =  Integer.parseInt("1500000001");
			}else if("3000".equals(voucher.getCompanycode())){
				bigno =  Integer.parseInt("0100000001");
				bigno2 =  Integer.parseInt("1900000001");
				bigno3 =  Integer.parseInt("1500000001");
			}else if("3100".equals(voucher.getCompanycode())){
				bigno =  Integer.parseInt("0100000001");
				bigno2 =  Integer.parseInt("1900000001");
				bigno3 =  Integer.parseInt("1500000001");
			}else if("2600".equals(voucher.getCompanycode())){
				bigno =  Integer.parseInt("0100000001");
				bigno2 =  Integer.parseInt("1900000001");
				bigno3 =  Integer.parseInt("1500000001");
			}else if("2100".equals(voucher.getCompanycode())){
				 bigno =  Integer.parseInt("0100006839");
				 bigno2 =  Integer.parseInt("1900000011");
				 bigno3 =  Integer.parseInt("1500000001");
			}
			if("01".equals(no2)){
				if(bigno < oldno)isbig=true;
			}else if("19".equals(no2)){
				if(bigno2 < oldno)isbig=true;
			}else if("15".equals(no2)){
				if(bigno3 < oldno)isbig=true;
			}else{
				isbig=true;
			}
//			if("0100033120".equals(oldvoucherNo) || "1500000809".equals(oldvoucherNo)){
//				isbig=true;
//			}else{
//				isbig=false;
//			}
			
			if(!isgen2 && isbig){				
/**			没有处理数据先不用，有处理数据再打开	
//				如果是清票据的清账凭证，要修改对应行项目的凭证号
				if("R".equals(voucher.getFlag()) || "P".equals(voucher.getFlag())){
					for(VoucherItem vi:voucher.getVoucherItem()){
						Voucher v=this.voucherService._get(vi.getBusvoucherid());
						if(null !=v && StringUtils.isNotBlank(v.getVoucherno())){
							vi.setVoucherno(v.getVoucherno());
						}
					}					
				}
				voucher.setVoucherno(" ");
				OutsidePersistenceService.execute(voucher, "_submitProcess");
				String newvoucherNo = voucher.getVoucherno();
				String voucherid = voucher.getVoucherid();
				String bussinessid = voucher.getBusinessid();
				String bussinesstype = voucher.getBusinesstype();
				String companyCode = voucher.getCompanycode();
				String fiYear = voucher.getFiyear();
				SimpleDateFormat  dateFormat=new SimpleDateFormat ("yyyyMMdd hh:mm:ss");
				String createtime =dateFormat.format(new Date());
				String flag =voucher.getFlag();
				String agums = voucher.getAgums();
				String logtext = "生成凭证成功";
				String isgen ="1";
				if(StringUtils.isBlank(newvoucherNo)){
					logtext = "生成凭证失败";		
					bussinessid2 ="";
					isgen="0";
				}else{
					bussinessid2 = bussinessid;
					this.voucherService._saveOrUpdate(voucher, null, getBusinessObject());
				}
				
				this.voucherService.insertSapVoucherLog(voucherid, bussinessid, bussinesstype, oldvoucherNo, newvoucherNo, companyCode, fiYear, createtime, flag, agums, logtext,isgen);
			**/
			}
			
		}
		return bussinessid2;
	}
}