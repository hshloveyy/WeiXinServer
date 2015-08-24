/*
 * @(#)BudgetClassService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月11日 10点41分30秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetClass.service;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.core.event.BOEvent;
import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.uicomponent.number.service.NumberService;
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.uicomponent.lock.LockException;
import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.dpframework.core.service.AdvanceService;
import com.infolion.XDSS.budget.maindata.BudgetClass.domain.BudgetClass;
import com.infolion.XDSS.budget.maindata.BudgetClass.service.BudgetClassService;
import com.infolion.XDSS.budget.maindata.BudgetClass.dao.BudgetClassHibernateDao;
import com.infolion.XDSS.budget.maindata.BudgetClassGen.service.BudgetClassServiceGen;

import com.infolion.XDSS.budget.maindata.BudgetTemplate.dao.BudgetTemplateHibernateDao;
import com.infolion.XDSS.budget.maindata.BudgetTemplate.domain.BudgetTemplate;
import com.infolion.XDSS.budget.maindata.BudgetTemplate.service.BudgetTemplateService;
import com.infolion.XDSS.budget.maindata.BudgetTemplateItem.domain.BudgetTemplateItem;
import com.infolion.XDSS.budget.sheet.service.BudgetSheetService;

/**
 * <pre>
 * 预算分类(BudgetClass)服务类
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
@Service
public class BudgetClassService extends BudgetClassServiceGen {
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	@Autowired
	protected BudgetTemplateHibernateDao budgetTemplateHibernateDao;
	
	public void setBudgetTemplateHibernateDao(BudgetTemplateHibernateDao budgetTemplateHibernateDao)
	{
		this.budgetTemplateHibernateDao = budgetTemplateHibernateDao;
	}
	
	/**
	 * 删除节点与及节点下的节点
	 * @param strBudClassId
	 */
	public void deleteNodeAndChildNode(String strBudClassId,BusinessObject businessObject){
		String strSql = "from BudgetClass budgetclass where budgetclass.budupclassid = '" + strBudClassId + "'";
		
		List BudgetClassList = budgetClassHibernateDao.find(strSql);
		
		if (BudgetClassList != null && BudgetClassList.size() > 0)
		{
			for (int i=0;i<BudgetClassList.size();i++){
				BudgetClass budgetClass = (BudgetClass)BudgetClassList.get(i);
				
				deleteNodeAndChildNode(budgetClass.getBudclassid(),businessObject);
			}
			
			if (StringUtils.isNullBlank(strBudClassId))
				// TODO DataElementText
				throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("budclassid"));
			BudgetClass budgetClass = this.budgetClassHibernateDao.load(strBudClassId);
			_delete(budgetClass);
		}else{
			if (StringUtils.isNullBlank(strBudClassId))
				// TODO DataElementText
				throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("budclassid"));
			BudgetClass budgetClass = this.budgetClassHibernateDao.load(strBudClassId);
			_delete(budgetClass);
		}
	}

	/**
	 * 发布一个新的版本
	 * 
	 * @param budgetClass
	 */
	public BudgetClass _promulgate(String strBudClassId, String strBudVersion) {
		BudgetClass budgetClass = this._getDetached(strBudClassId);
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(budgetClass);
		
		//先把原来是激活状态的改为非激活状态
		String strSql = "from BudgetClass budgetclass where budgetclass.budupclassid = '" + 
		budgetClass.getBudupclassid() + "' and budgetclass.active = 'Y'";
		
		List BudgetClassList = budgetClassHibernateDao.find(strSql);
		
		if (BudgetClassList != null && BudgetClassList.size() > 0)
		{
			for (int i=0;i<BudgetClassList.size();i++){
				BudgetClass updateBudgetClass = (BudgetClass)BudgetClassList.get(i);
				updateBudgetClass.setActive("N");
				budgetClassHibernateDao.update(updateBudgetClass);
			}
		}
		//先把原来是激活状态的改为非激活状态
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		
		BudgetClass newBudgetClass = new BudgetClass();
		
		newBudgetClass.setClient(null);
		newBudgetClass.setBudclassid(null);
		newBudgetClass.setBudclassname(budgetClass.getBudclassname());
		newBudgetClass.setBudclassdesc(budgetClass.getBudclassdesc());
		newBudgetClass.setBoid(budgetClass.getBoid());
		newBudgetClass.setSumboid(budgetClass.getSumboid());
		newBudgetClass.setVersion(Integer.valueOf(strBudVersion));
		newBudgetClass.setBudupclassid(budgetClass.getBudupclassid());
		newBudgetClass.setCreator(null);
		newBudgetClass.setCreatetime(null);
		newBudgetClass.setLastmodifyer(null);
		newBudgetClass.setLastmodifytime(null);
		newBudgetClass.setSourcetype("2");
		newBudgetClass.setActive("Y");
		budgetClassHibernateDao.save(newBudgetClass);
		
		Set<BudgetTemplate> budgetTemplateSet = budgetClass.getBudgetTemplate();
		Set<BudgetTemplate> newBudgetTemplateSet = null;
		if (null != budgetTemplateSet)
		{
			newBudgetTemplateSet = new HashSet();
			Iterator<BudgetTemplate> itBudgetTemplate = budgetTemplateSet.iterator();
			while (itBudgetTemplate.hasNext())
			{
				BudgetTemplate budgetTemplate = (BudgetTemplate) itBudgetTemplate.next();
				BudgetTemplate newBudgetTemplate = new BudgetTemplate();
				
				Set<BudgetTemplateItem> budgetTemplateItemSet = budgetTemplate.getBudgetTemplateItem();
				Set<BudgetTemplateItem> newBudgetTemplateItemSet = null;
				if (null != budgetTemplateItemSet){
					newBudgetTemplateItemSet = new HashSet();
					Iterator<BudgetTemplateItem> itBudgetTemplateItem = budgetTemplateItemSet.iterator();
					while (itBudgetTemplateItem.hasNext())
					{
						BudgetTemplateItem budgetTemplateItem = (BudgetTemplateItem) itBudgetTemplateItem.next();
						
						BudgetTemplateItem newBudgetTemplateItem = new BudgetTemplateItem();
						
						newBudgetTemplateItem.setClient(null);
						newBudgetTemplateItem.setBudtemitemid(null);
						newBudgetTemplateItem.setBuditemid(budgetTemplateItem.getBuditemid());
						newBudgetTemplateItem.setSeq(budgetTemplateItem.getSeq());
						newBudgetTemplateItem.setSubject(budgetTemplateItem.getSubject());
						newBudgetTemplateItem.setBudconcycle(budgetTemplateItem.getBudconcycle());
						newBudgetTemplateItem.setStatus(budgetTemplateItem.getStatus());
						newBudgetTemplateItem.setCreatetime(null);
						newBudgetTemplateItem.setCreator(null);
						newBudgetTemplateItem.setLastmodifyer(null);
						newBudgetTemplateItem.setLastmodifytime(null);
						newBudgetTemplateItem.setBudgetTemplate(newBudgetTemplate);
						
						newBudgetTemplateItemSet.add(newBudgetTemplateItem);
					}
				}
				
				newBudgetTemplate.setClient(null);
				newBudgetTemplate.setBudtemid(null);
				newBudgetTemplate.setBudtemname(budgetTemplate.getBudtemname());
				newBudgetTemplate.setBudtemtype(budgetTemplate.getBudtemtype());
				newBudgetTemplate.setBoid(budgetTemplate.getBoid());
				newBudgetTemplate.setBudconcycle(budgetTemplate.getBudconcycle());
				newBudgetTemplate.setCreator(null);
				newBudgetTemplate.setCreatetime(null);
				newBudgetTemplate.setLastmodifyer(null);
				newBudgetTemplate.setLastmodifytime(null);
				//newBudgetTemplate.setBudgetClass(newBudgetClass);
				newBudgetTemplate.setBudgetTemplateItem(newBudgetTemplateItemSet);
				
				BudgetClass middleBudgetClass = new BudgetClass();
				middleBudgetClass.setBudclassid(newBudgetClass.getBudclassid());
				newBudgetTemplate.setBudgetClass(middleBudgetClass);

				
				this.budgetTemplateHibernateDao.save(newBudgetTemplate);
				
				newBudgetTemplateSet.add(newBudgetTemplate);
			}
		}
		newBudgetClass.setBudgetTemplate(newBudgetTemplateSet);
		
		//budgetClassHibernateDao.save(newBudgetClass);

		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(budgetClass);
		
		return newBudgetClass;
	}
	
	/**
	 * 用于激活一个版本
	 * @param strBudClassId
	 * @return
	 */
	public BudgetClass _active(String strBudClassId,BusinessObject businessObject) {
		BudgetClass budgetClass = this._getDetached(strBudClassId);
		
		//先把原来是激活状态的改为非激活状态
		String strSql = "from BudgetClass budgetclass where budgetclass.budupclassid = '" + 
		budgetClass.getBudupclassid() + "' and budgetclass.active = 'Y'";
		
		List BudgetClassList = budgetClassHibernateDao.find(strSql);
		
		if (BudgetClassList != null && BudgetClassList.size() > 0)
		{
			for (int i=0;i<BudgetClassList.size();i++){
				BudgetClass updateBudgetClass = (BudgetClass)BudgetClassList.get(i);
				updateBudgetClass.setActive("N");
				budgetClassHibernateDao.update(updateBudgetClass);
			}
		}
		//先把原来是激活状态的改为非激活状态
		
		//把要激活的版本复制到临时版
		//先把原来的临时版删除，再产生一个临时片和要激活的一样的版本插入
		strSql = "from BudgetClass budgetclass where budgetclass.budupclassid = '" + 
		budgetClass.getBudupclassid() + "' and budgetclass.version = -1";
		
		List zeroVersionList = budgetClassHibernateDao.find(strSql);
		
		if (zeroVersionList != null && zeroVersionList.size() > 0)
		{
			BudgetClass zeroBudgetClass = (BudgetClass)zeroVersionList.get(0);

			_delete(zeroBudgetClass);
		}
		
		BudgetClass newBudgetClass = new BudgetClass();
		
		newBudgetClass.setClient(null);
		newBudgetClass.setBudclassid(null);
		newBudgetClass.setBudclassname(budgetClass.getBudclassname());
		newBudgetClass.setBudclassdesc(budgetClass.getBudclassdesc());
		newBudgetClass.setBoid(budgetClass.getBoid());
		newBudgetClass.setSumboid(budgetClass.getSumboid());
		newBudgetClass.setVersion(-1);
		newBudgetClass.setBudupclassid(budgetClass.getBudupclassid());
		newBudgetClass.setCreator(null);
		newBudgetClass.setCreatetime(null);
		newBudgetClass.setLastmodifyer(null);
		newBudgetClass.setLastmodifytime(null);
		newBudgetClass.setSourcetype("2");
		newBudgetClass.setActive("N");
		
		budgetClassHibernateDao.save(newBudgetClass);

		Set<BudgetTemplate> budgetTemplateSet = budgetClass.getBudgetTemplate();
		Set<BudgetTemplate> newBudgetTemplateSet = null;
		if (null != budgetTemplateSet)
		{
			newBudgetTemplateSet = new HashSet();
			Iterator<BudgetTemplate> itBudgetTemplate = budgetTemplateSet.iterator();
			while (itBudgetTemplate.hasNext())
			{
				BudgetTemplate budgetTemplate = (BudgetTemplate) itBudgetTemplate.next();
				BudgetTemplate newBudgetTemplate = new BudgetTemplate();
				
				Set<BudgetTemplateItem> budgetTemplateItemSet = budgetTemplate.getBudgetTemplateItem();
				Set<BudgetTemplateItem> newBudgetTemplateItemSet = null;
				if (null != budgetTemplateItemSet){
					newBudgetTemplateItemSet = new HashSet();
					Iterator<BudgetTemplateItem> itBudgetTemplateItem = budgetTemplateItemSet.iterator();
					while (itBudgetTemplateItem.hasNext())
					{
						BudgetTemplateItem budgetTemplateItem = (BudgetTemplateItem) itBudgetTemplateItem.next();
						
						BudgetTemplateItem newBudgetTemplateItem = new BudgetTemplateItem();
						
						newBudgetTemplateItem.setClient(null);
						newBudgetTemplateItem.setBudtemitemid(null);
						newBudgetTemplateItem.setBuditemid(budgetTemplateItem.getBuditemid());
						newBudgetTemplateItem.setSeq(budgetTemplateItem.getSeq());
						newBudgetTemplateItem.setSubject(budgetTemplateItem.getSubject());
						newBudgetTemplateItem.setBudconcycle(budgetTemplateItem.getBudconcycle());
						newBudgetTemplateItem.setStatus(budgetTemplateItem.getStatus());
						newBudgetTemplateItem.setCreatetime(null);
						newBudgetTemplateItem.setCreator(null);
						newBudgetTemplateItem.setLastmodifyer(null);
						newBudgetTemplateItem.setLastmodifytime(null);
						newBudgetTemplateItem.setBudgetTemplate(newBudgetTemplate);
						
						newBudgetTemplateItemSet.add(newBudgetTemplateItem);
					}
				}
				
				newBudgetTemplate.setClient(null);
				newBudgetTemplate.setBudtemid(null);
				newBudgetTemplate.setBudtemname(budgetTemplate.getBudtemname());
				newBudgetTemplate.setBudtemtype(budgetTemplate.getBudtemtype());
				newBudgetTemplate.setBoid(budgetTemplate.getBoid());
				newBudgetTemplate.setBudconcycle(budgetTemplate.getBudconcycle());
				newBudgetTemplate.setCreator(null);
				newBudgetTemplate.setCreatetime(null);
				newBudgetTemplate.setLastmodifyer(null);
				newBudgetTemplate.setLastmodifytime(null);
				//newBudgetTemplate.setBudgetClass(newBudgetClass);
				newBudgetTemplate.setBudgetTemplateItem(newBudgetTemplateItemSet);
				
				BudgetClass middleBudgetClass = new BudgetClass();
				middleBudgetClass.setBudclassid(newBudgetClass.getBudclassid());
				newBudgetTemplate.setBudgetClass(middleBudgetClass);
				
				this.budgetTemplateHibernateDao.save(newBudgetTemplate);
				
				newBudgetTemplateSet.add(newBudgetTemplate);
			}
		}
		newBudgetClass.setBudgetTemplate(newBudgetTemplateSet);
		
		//budgetClassHibernateDao.save(newBudgetClass);
		//把要激活的版本复制到临时版
		
		//修改选定的版本为激活状态
		budgetClass.setActive("Y");
		budgetClassHibernateDao.update(budgetClass);
		//修改选定的版本为激活状态
		
		return budgetClass;
	}

	@Override
	public void _update(BudgetClass budgetClass, Set<BudgetTemplate> deletedBudgetTemplateSet, BusinessObject businessObject)
	{
		super._update(budgetClass, deletedBudgetTemplateSet, businessObject);
		fireBoEvent(new BOEvent<BudgetClass>(BOEvent.TYPE_UPDATE, budgetClass));
	}

}