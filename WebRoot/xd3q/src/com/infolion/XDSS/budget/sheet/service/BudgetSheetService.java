/*
 * @(#)SpreadSheetService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：刘俊杰
 *  时　间：2010-2-8
 *  描　述：创建
 */

package com.infolion.XDSS.budget.sheet.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.XDSS.budget.maindata.BudgetClass.domain.BudgetClass;
import com.infolion.XDSS.budget.maindata.BudgetClass.service.BudgetClassService;
import com.infolion.XDSS.budget.maindata.BudgetOrgTemp.service.BudgetOrgTempService;
import com.infolion.XDSS.budget.maindata.BudgetOrganization.service.BudgetOrganizationService;
import com.infolion.XDSS.budget.maindata.BudgetTemplate.domain.BudgetTemplate;
import com.infolion.XDSS.budget.maindata.BudgetTemplateItem.domain.BudgetTemplateItem;
import com.infolion.XDSS.budget.sheet.BudgetSheetFDConverter;
import com.infolion.XDSS.budget.sheet.dao.BudgetSheetDao;
import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.UserSuperintend;
import com.infolion.platform.basicApp.authManage.service.UserSuperintendService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.domain.GridColumn;
import com.infolion.platform.bo.domain.Property;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.core.cache.core.SysCachePool;
import com.infolion.platform.dpframework.core.cache.event.ItemReflashedEvent;
import com.infolion.platform.dpframework.core.cache.event.ItemReflashedListener;
import com.infolion.platform.dpframework.core.domain.BaseObject;
import com.infolion.platform.dpframework.core.event.BOEvent;
import com.infolion.platform.dpframework.core.event.BOEventListenerAdapter;
import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.core.service.SheetService;
import com.infolion.platform.dpframework.language.LanguageService;
import com.infolion.platform.dpframework.outsideInterface.Constants;
import com.infolion.platform.dpframework.outsideInterface.OutsideInterfaceUtils;
import com.infolion.platform.dpframework.uicomponent.tree.TreeNodeFilter;
import com.infolion.platform.dpframework.uicomponent.tree.domain.Tree;
import com.infolion.platform.dpframework.uicomponent.tree.domain.TreeNode;
import com.infolion.platform.dpframework.uicomponent.tree.service.TreeService;
import com.infolion.platform.dpframework.util.AssertUtil;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.spreadsheet.SpreadsheetException;
import com.infolion.platform.spreadsheet.Utils;
import com.infolion.platform.spreadsheet.ViewDefBuilder;
import com.infolion.platform.spreadsheet.dataConverters.FlexDataConverter;
import com.infolion.platform.spreadsheet.flex.FlexData;
import com.infolion.platform.spreadsheet.flex.ViewDef;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.workflow.engine.domain.ExtendTransition;
import com.infolion.platform.workflow.engine.domain.TaskInstanceContext;
import com.infolion.platform.workflow.engine.web.TaskHistoryGrid;

/**
 * 
 * <pre>
 * 预算表格服务类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 刘俊杰
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class BudgetSheetService extends BaseService
{

	// 子对象关联模板预算项的属性名
	public static final String BUD_TEM_ITEM_ID = "budtemitemid";
	// 主对象关联预算分类的属性名
	public static final String BUD_CLASS_ID = "budclassid";
	// 主对象关联预算组织的属性名
	public static final String BUD_ORG_ID = "budorgid";

	/**
	 * FLEX应用类型：编辑
	 */
	public static final String APP_EDIT = "app_edit";
	/**
	 * FLEX应用类型：查看
	 */
	public static final String APP_VIEW = "app_view";

	// 缓存预算分类ID与对应SheetService的映射关系，以提高getSheetService方法的效率
	private Map<String, SheetService<BaseObject>> sheetServiceMap = new HashMap<String, SheetService<BaseObject>>();
	// 缓存预算分类ID与对应业务对象的映射关系，以提高getBusinessObjectByBudclassid方法的效率
	private Map<String, BusinessObject> businessObjectCache = new HashMap<String, BusinessObject>();

	private BudgetClassService budgetClassService;

	@Autowired
	private BudgetSheetDao budgetSheetDao;

	@Autowired
	private TreeService treeService;

	@Autowired
	private BudgetOrgTempService budgetOrgTempService;

	@Autowired
	private UserSuperintendService userSuperintendService;

	@Autowired
	public void setBudgetClassService(BudgetClassService budgetClassService)
	{
		this.budgetClassService = budgetClassService;
		// 向BudgetClassService注册一个监听器，当BudgetClass更新时刷新相应的缓存
		this.budgetClassService.addBoEventListener(new BOEventListenerAdapter<BudgetClass>()
		{

			@Override
			public void boUpdated(BOEvent<BudgetClass> event)
			{
				reflashBudClassCache(event.getTarget().getBudclassid());
			}
		});
	}

	@Autowired
	public void setSysCachePool(SysCachePool sysCachePool)
	{
		// 向系统缓存池添加缓存刷新事件监听器
		sysCachePool.addBOReflashedListener(new ItemReflashedListener()
		{

			public void onItemReflashed(ItemReflashedEvent event)
			{
				for (Object obj : event.getItems().values())
				{
					BusinessObject businessObject = (BusinessObject) obj;
					for (Iterator<Map.Entry<String, BusinessObject>> it = businessObjectCache.entrySet().iterator(); it.hasNext();)
					{
						Map.Entry<String, BusinessObject> ent = it.next();
						if (ent.getValue().getBoName().equals(businessObject.getBoName()))
						{
							it.remove();
							break;
						}
					}
				}

			}
		});
	}

	/**
	 * 取得分配给指定用户的所有预算组织根节点的ID
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> getUserBudOrgIds(String userId)
	{
		List<String> nodeIds = this.userSuperintendService.getByUserAndType(userId, UserSuperintend.MANA_OBJ_TYPE_BUDORG);
		Set<String> nodeSet = new HashSet<String>(nodeIds);
		List<String> budOrgIds = new ArrayList<String>();
		Tree tree = BudgetOrganizationService.createBudOrgTree();
		for (String nodeId : nodeSet)
		{
			String parentNodeId = this.treeService.getParentNodeId(tree, nodeId);
			if (parentNodeId == null || !nodeSet.contains(parentNodeId))
			{
				budOrgIds.add(nodeId);
			}
		}
		return budOrgIds;
	}

	private BudgetClass getBudgetClass(String budclassid)
	{
		// TODO 该方法被调用的频率很高，可考虑缓存
		BudgetClass budClass = this.budgetClassService._get(budclassid);
		return budClass;
	}

	/**
	 * 提交流程
	 * 
	 * @param flexData
	 * @param budclassid
	 * @param workflowTaskId
	 * @param workflowLeaveTransitionName
	 * @param workflowExamine
	 */
	public void sumitProcess(FlexData flexData, String budclassid, String workflowTaskId, String workflowLeaveTransitionName, String workflowExamine)
	{
		try
		{
			BaseObject boInst = getAndBindBoInst(flexData, budclassid);

			boInst.setWorkflowTaskId(workflowTaskId);
			boInst.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
			boInst.setWorkflowExamine(workflowExamine);

			SheetService<BaseObject> service = getSheetService(budclassid);
			service._submitProcess(boInst, getBusinessObjectByBudclassid(budclassid));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SpreadsheetException(e);
		}
	}

	/**
	 * 汇总提交流程
	 * 
	 * @param businessId
	 * @param budclassid
	 * @param workflowTaskId
	 * @param workflowLeaveTransitionName
	 * @param workflowExamine
	 */
	public void summarySumitProcess(String businessId, String budclassid, String workflowTaskId, String workflowLeaveTransitionName, String workflowExamine)
	{
		try
		{
			SheetService<BaseObject> service = getSheetService(budclassid);
			service._summarySumitProcess(businessId, workflowTaskId, workflowLeaveTransitionName, workflowExamine);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SpreadsheetException(e);
		}
	}

	/**
	 * 取得指定的节点
	 * 
	 * @param budclassid
	 * @param budorgId
	 * @param getSubNodes
	 *            是否取得所有子节点
	 * @return
	 */
	public TreeNode getTreeNode(String budclassid, String budorgId, boolean getSubNodes)
	{
		try
		{
			Tree tree = createTreeAsParam(budclassid);
			TreeNode treeNode = this.treeService.getTreeNode(tree, budorgId);
			if (treeNode != null && getSubNodes)
			{
				tree.setNeedAutoLoad(true);
				treeNode.setChildrenNodes(this.treeService.getTreeNodes(tree, treeNode));
			}
			return treeNode;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SpreadsheetException(e);
		}
	}

	/**
	 * 取得指定的节点列表
	 * 
	 * @param budclassid
	 * @param budorgIdList
	 * @param getSubNodes
	 *            是否取得所有子节点
	 * @return
	 */
	public List<TreeNode> getTreeNodes(String budclassid, String[] budorgIdList, boolean getSubNodes)
	{
		List<TreeNode> list = new ArrayList<TreeNode>();
		if (budorgIdList != null)
		{
			for (String budorgId : budorgIdList)
			{
				list.add(getTreeNode(budclassid, budorgId, getSubNodes));
			}
		}
		return list;
	}

	/**
	 * 取得指定节点的所有子节点
	 * 
	 * @param budclassid
	 * @param budorgId
	 * @param recursive
	 *            是否递归执行
	 * @return
	 */
	public List<TreeNode> getSubTreeNode(String budclassid, String budorgId, boolean recursive)
	{
		try
		{
			Tree tree = createTreeAsParam(budclassid);
			tree.setNeedAutoLoad(recursive);
			TreeNode node = new TreeNode();
			node.setNodeId(budorgId);
			return this.treeService.getTreeNodes(tree, node);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SpreadsheetException(e);
		}
	}

	// 构造供查询节点的Tree
	private Tree createTreeAsParam(final String budclassid)
	{
		Tree _budorgTree = BudgetOrganizationService.createBudOrgTree();
		final String userid = UserContextHolder.getLocalUserContext().getUserContext().getUser().getUserId();
		TreeNodeFilter treeNodeFilter = new TreeNodeFilter()
		{
			// 取得树节点的过滤条件
			public boolean available(Tree tree, TreeNode treeNode)
			{
				boolean vlb;
				vlb = budgetOrgTempService.isHaveBudClass(budclassid, treeNode.getNodeId());
				if (vlb)
				{
					vlb = userSuperintendService.userAvailable(userid, UserSuperintend.MANA_OBJ_TYPE_BUDORG, treeNode.getNodeId());
				}
				return vlb;
			}
		};
		_budorgTree.setTreeNodeFilter(treeNodeFilter);
		return _budorgTree;
	}

	/**
	 * 取得版本列表
	 * 
	 * @param budclassid
	 * @param boInstId
	 * @return
	 */
	public List<String[]> getVersionList(String budclassid, String boInstId)
	{
		try
		{
			SheetService<BaseObject> service = getSheetService(budclassid);
			return service._getVersionList(boInstId);
		}
		catch (Exception e)
		{
			log.trace("BudgetSheetService exception:", e);
			throw new SpreadsheetException(e);
		}
	}

	/**
	 * 根据预算组织取得与指定业务对象实例相对应的业务对象实例的Flex数据
	 * 
	 * @param budclassid
	 *            预算分类ID
	 * @param budorgid
	 *            预算组织ID
	 * @param boInstId
	 *            参考业务对象实例ID
	 * @return
	 */
	public FlexData getDataByBudorgid(String budclassid, String budorgid, String boInstId)
	{
		try
		{
			AssertUtil.notNullBlank(budclassid, "预算分类ID");
			AssertUtil.notNullBlank(budorgid, "预算组织ID");

			SheetService<BaseObject> service = getSheetService(budclassid);

			Object boInst = service._getByBudorgid(budorgid, boInstId);
			// 取得Flex数据转换器
			FlexDataConverter fdCon = createConverter(budclassid, false);
			// 将业务对象实例转换成FlexData
			return fdCon.toFlexData(boInst);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SpreadsheetException(e);
		}
	}

	/**
	 * 取得汇总数据
	 * 
	 * @param budclassid
	 *            预算分类
	 * @param sumBudOrgId
	 *            要汇总的预算组织
	 * @param budOrgIds
	 *            要汇总的预算组织下的所有叶子节点
	 * @param busId
	 *            参考业务实例
	 * @return
	 */
	public FlexData getSummaryData(String budclassid, String sumBudOrgId, String[] budOrgIds, String busId)
	{
		try
		{
			AssertUtil.notNullBlank(budclassid, "预算分类ID");

			BudgetClass budgetClass = getBudgetClass(budclassid);

			SheetService<BaseObject> service = getSheetService(budclassid);

			Object boInst = service._summary(sumBudOrgId, budOrgIds, budgetClass, busId);
			// 取得Flex数据转换器
			FlexDataConverter fdCon = createConverter(budclassid, true);
			// 将业务对象实例转换成FlexData
			return fdCon.toFlexData(boInst);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SpreadsheetException(e);
		}

	}

	/**
	 * 取得供Flex使用的指定任务实例ID的数据
	 * 
	 * @param taskInstanceId
	 * @return
	 */
	public Object getTaskData(String taskInstanceId)
	{
		try
		{
			TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskInstanceId);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("taskName", taskInstanceContext.getTaskName());
			data.put("currentActor", taskInstanceContext.getCurrentActor());
			ArrayList<String> tranList = new ArrayList<String>();
			for (ExtendTransition et : taskInstanceContext.getTransitions())
			{
				tranList.add(et.getTransitionName());
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dataProvider", tranList);
			data.put("workflowLeaveTransitionName", map);
			data.put("currentTime", DateUtils.getCurrTimeStr(DateUtils.HYPHEN_DISPLAY_DATE));
			data.put("workflowExamine", null);
			return data;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SpreadsheetException(e);
		}
	}

	public Object getTaskHistoryData(String boInstId)
	{
		List<Map<String, Object>> resultList = this.budgetSheetDao.getTaskHistory(boInstId, TaskHistoryGrid.PROCESS_COLUMN);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("columns", TaskHistoryGrid.PROCESS_COLUMN);
		obj.put("titles", TaskHistoryGrid.PROCESS_COLUMN_TITLE);
		obj.put("dataProvider", resultList);
		return obj;
	}

	/**
	 * 更新FlexData<br>
	 * 将FlexData转换成业务对象实例，再持久化到数据库
	 * 
	 * @param flexData
	 *            Flex数据
	 * @param budclassid
	 *            预算分类ID
	 */
	public void updateFlexData(FlexData flexData, String budclassid)
	{
		try
		{
			SheetService<BaseObject> service = getSheetService(budclassid);
			BaseObject boInst = getAndBindBoInst(flexData, budclassid);

			// 将业务对象实例持久化到数据库
			service._update(boInst);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SpreadsheetException(e);
		}
	}

	/**
	 * 根据budclassid取得相对应的SheetService
	 * 
	 * @param budclassid
	 * @return
	 */
	protected SheetService<BaseObject> getSheetService(String budclassid)
	{
		SheetService<BaseObject> service = sheetServiceMap.get(budclassid);
		if (service == null)
		{
			BudgetClass budClass = getBudgetClass(budclassid);
			BusinessObject businessObject = getBusinessObjectByBudclassid(budclassid);

			service = Utils.getSheetService(businessObject);
			sheetServiceMap.put(budclassid, service);
		}
		return service;
	}

	protected BusinessObject getBusinessObjectByBudclassid(String budclassid)
	{
		BusinessObject businessObject = businessObjectCache.get(budclassid);
		if (businessObject == null)
		{
			BudgetClass budClass = getBudgetClass(budclassid);
			businessObject = BusinessObjectService.getBusinessObjectByBoId(budClass.getBoid());
			businessObjectCache.put(budclassid, businessObject);
		}
		return businessObject;
	}

	/**
	 * 更新指定预算分类相关的缓存
	 * 
	 * @param budclassid
	 */
	public void reflashBudClassCache(String budclassid)
	{
		this.sheetServiceMap.remove(budclassid);
		this.businessObjectCache.remove(budclassid);
	}

	/**
	 * 取得业务实例并将FlexData中的数据填入业务实例，并返回此业务实例
	 * 
	 * @param flexData
	 * @param budclassid
	 * @return
	 */
	protected BaseObject getAndBindBoInst(FlexData flexData, String budclassid)
	{
		SheetService<BaseObject> service = getSheetService(budclassid);
		BaseObject boInst = service._get(flexData.getBusId());

		// 取得Flex数据转换器
		FlexDataConverter fdCon = createConverter(budclassid, false);

		fdCon.bindBoInst(flexData, boInst);
		return boInst;
	}

	/**
	 * 根据预算分类ID和业务ID取得FlexData<br>
	 * 过程：取得业务对象实例，转换成FlexData并返回
	 * 
	 * @param budclassid
	 * @param boInstId
	 * @return
	 */
	public FlexData getData(String budclassid, String boInstId)
	{
		try
		{
			// 取得业务对象实例
			BudgetClass budgetClass = getBudgetClass(budclassid);
			String boName = SysCachePoolUtils.getBoNameById(budgetClass.getBoid());
			// 取得业务对象实例
			Object boInst = BusinessObjectService.getBoInstance(boName, boInstId);

			// 取得Flex数据转换器
			FlexDataConverter fdCon = createConverter(budclassid, false);
			// 将业务对象实例转换成FlexData
			return fdCon.toFlexData(boInst);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SpreadsheetException(e);
		}
	}

	private Map summarySumPros(String[] boInstIds, String budclassid)
	{
		BudgetClass budgetClass = getBudgetClass(budclassid);
		BusinessObject businessObject = getBusinessObjectByBudclassid(budclassid);
		Set set = new HashSet();
		for (BudgetTemplate budgetTemplate : budgetClass.getBudgetTemplate())
		{
			// 要汇总的子业务对象
			BusinessObject subBusinessObject = BusinessObjectService.getBusinessObjectByBoId(budgetTemplate.getBoid());
			// 取得所有需要进行SUM操作的子对象属性
			Set<Property> sumProperties = new HashSet<Property>();
			for (GridColumn gridColumn : subBusinessObject.getGridColumns())
			{
				if (StringUtils.isNotBlank(gridColumn.getIsSum()))
				{
					sumProperties.add(gridColumn.getProperty());
				}
			}

			// 子对象表中关联模板预算项的字段名
			String budTempItemColumnName = subBusinessObject.getPropertyByName(BUD_TEM_ITEM_ID).getColumnName();
			// 子对象对应的表名
			String subTableName = subBusinessObject.getTableName();
			// 子对象关联主对象的字段名（主对象的主键字段名就是子对象关联主对象的字段名）
			String foreignKeyColumnName = businessObject.getPrimaryKeyProperty().getColumnName();
			// 汇总每一个预算项
			for (BudgetTemplateItem budgetTemplateItem : budgetTemplate.getBudgetTemplateItem())
			{
				Map<String, Object> map = this.budgetSheetDao.summary(foreignKeyColumnName, boInstIds, budgetTemplateItem.getBudtemitemid(), budTempItemColumnName, sumProperties.toArray(new Property[sumProperties.size()]), subTableName);
				Object obj = OutsideInterfaceUtils.getBoClassName(subBusinessObject);
				Utils.setPros(obj, map);
				set.add(obj);
			}

		}

		for (Property property : businessObject.getProperties())
		{
			// 要汇总的子对象名
			String subBoName = property.getSubBoName();
			if (!Constants.PRO_TYPE_BO.equals(property.getPropertyType()) && StringUtils.isNullBlank(subBoName))
				continue;
		}
		return null;
	}

	/**
	 * 取得Flex视图定义
	 * 
	 * @param budclassid
	 *            预算分类ID
	 * @param isSummary
	 *            是否汇总数据
	 * @return
	 */
	public ViewDef getViewDef(String budclassid, boolean isSummary)
	{
		try
		{
			// 取得预算分类
			BudgetClass budgetClass = getBudgetClass(budclassid);

			ViewDef viewdef;
			String langIso = LanguageService.getCurrentLangIso();
			// 是否要解析数据
			boolean buildViewDef = false;
			File excelFile = null;
			BusinessObject businessObject = null;
			// 根据是否汇总取得不同的业务对象ID
			String boId = isSummary ? budgetClass.getSumboid() : budgetClass.getBoid();
			if (EasyApplicationContextUtils.getSysParamAsBoolean("checkTempFile"))
			{
				businessObject = BusinessObjectService.getBusinessObjectByBoId(boId);
				excelFile = getExcelFile(businessObject);

				long lastModify = this.budgetSheetDao.getLastModify(budgetClass.getBudclassid(), boId, langIso);
				// 如果Excel文件的最后修改时间与上次不相符，重新解析
				if (lastModify == -1 || excelFile.exists() && lastModify != excelFile.lastModified())
				{
					buildViewDef = true;
				}
			}
			if (buildViewDef)
			{
				Map<String, Object> context = new HashMap<String, Object>();
				context.put("budgetClass", budgetClass);
				InputStream inputStream;
				try
				{
					inputStream = new FileInputStream(excelFile);
					log.debug("读取Excel模板文件：" + excelFile);
				}
				catch (FileNotFoundException e1)
				{
					throw new SpreadsheetException("找不到指定的Excel模板文件：" + excelFile);
				}
				viewdef = ViewDefBuilder.buildViewDef(inputStream, businessObject, context, LanguageService.getCurrentLangCode());
				validate(viewdef, budgetClass);
				// 将新解析的FlexUI数据写回数据库
				this.budgetSheetDao.saveOrUpdateViewDef(budclassid, boId, viewdef, langIso, excelFile.lastModified());
			}
			else
			{
				viewdef = this.budgetSheetDao.getViewDef(budgetClass.getBudclassid(), boId, langIso);
				if (viewdef == null)
				{
					throw new SpreadsheetException("找不到预算分类[" + budgetClass.getBudclassname() + "]对应的Flex定义数据！");
				}
			}
			return viewdef;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SpreadsheetException(e);
		}
	}

	/**
	 * 根据业务对象取得对应的Excel模板文件
	 * 
	 * @param businessObject
	 * @return
	 */
	public File getExcelFile(BusinessObject businessObject)
	{
		String excelTempPath = EasyApplicationContextUtils.getSysParamAsString("excelTemplates");
		File excelTemplatesFile;
		if (StringUtils.isNotBlank(excelTempPath))
		{
			excelTemplatesFile = new File(excelTempPath);
		}
		else
		{
			// 取得WEB-INF/ExcelTemplates下的模板文件
			File classFile = new File(BudgetSheetService.class.getResource("/").getFile());
			excelTemplatesFile = new File(classFile.getParentFile(), "ExcelTemplates");
		}
		String path = businessObject.getAppModel() + "/" + businessObject.getJavaPath();
		String fileName = businessObject.getBoName() + "_" + LanguageService.getCurrentLangIso() + ".xls";
		File file = new File(excelTemplatesFile, path + "/" + fileName);
		return file;
	}

	// 验证模板与预算项配置是否一致
	private void validate(ViewDef viewDef, BudgetClass budgetClass)
	{
		// TODO 未实现
	}

	// 创建SpreadSheet.swf启动参数
	private String _createFlexPara(String contextPath, String appType, String businessId, String budclassid, String endpoint, String destination, boolean hasTree, boolean hasProcess, String workflowTaskId)
	{
		JSONObject jsonObj = new JSONObject();
		jsonObj.element("isWebApp", true);
		jsonObj.element("contextPath", contextPath);
		jsonObj.element("applicationType", appType);
		jsonObj.element("budclassid", budclassid);
		jsonObj.element("boInstId", businessId);
		jsonObj.element("endpoint", endpoint);
		jsonObj.element("destination", destination);
		jsonObj.element("hasTree", hasTree);
		jsonObj.element("hasProcess", hasProcess);
		jsonObj.element("workflowTaskId", workflowTaskId);
		return jsonObj.toString();
	}

	// 创建SpreadSheet.swf启动参数的JSONObject
	public JSONObject createFlexParaJsonObj(HttpServletRequest request, String appType)
	{
		JSONObject jsonObj = new JSONObject();
		jsonObj.element("isWebApp", true);
		jsonObj.element("contextPath", request.getContextPath());
		jsonObj.element("applicationType", appType);
		jsonObj.element("endpoint", request.getContextPath() + "/messagebroker/amf");
		jsonObj.element("destination", "budgetSheetService");
		return jsonObj;
	}

	/**
	 * 根据预算分类ID构造Flex数据转换器
	 * 
	 * @param budclassid
	 * @param isSummary
	 *            是否是汇总
	 * @return
	 */
	public FlexDataConverter createConverter(String budclassid, boolean isSummary)
	{

		BudgetSheetFDConverter fdCon = new BudgetSheetFDConverter();
		fdCon.setViewDef(getViewDef(budclassid, isSummary));
		fdCon.setBudgetClass(getBudgetClass(budclassid));
		return fdCon;
	}

	/**
	 * 取得与指定预算组织关联的业务实例ID
	 * 
	 * @param businessObject
	 *            要查询的业务对象
	 * @param budClassId
	 *            关联的预算分类
	 * @param budOrgIds
	 *            所有关联的预算组织ID组成的数组
	 * @param whereSql
	 *            其它查询
	 * @return
	 */
	public String[] getBusIdByBudOrgId(BusinessObject businessObject, String budClassId, String[] budOrgIds, String whereSql)
	{
		String tableName = businessObject.getTableName();

		String idColumnName = businessObject.getPrimaryKeyProperty().getColumnName();
		Property budOrgProperty = businessObject.getPropertyByName(BUD_ORG_ID);
		if (budOrgProperty == null)
		{
			throw new SpreadsheetException("业务对象[" + businessObject.getBoName() + "]找不到关联预算组织的属性[" + BUD_ORG_ID + "]");
		}
		String budOrgColumnName = budOrgProperty.getColumnName();
		Property budClassProperty = businessObject.getPropertyByName(BUD_CLASS_ID);
		if (budClassProperty == null)
		{
			throw new SpreadsheetException("业务对象[" + businessObject.getBoName() + "]找不到关联预算分类的属性[" + BUD_CLASS_ID + "]");
		}
		String budClassColumnName = budClassProperty.getColumnName();
		if (budOrgIds == null || budOrgIds.length <= 0)
		{
			throw new SpreadsheetException("预算组织ID列表不能为空");
		}
		return this.budgetSheetDao.getBusIdsByOrdId(tableName, idColumnName, budOrgColumnName, budOrgIds, budClassColumnName, budClassId, whereSql);
	}

	public Map<String, Object> summary(String foreignKeyColumnName, String[] busIds, String budtemitemid, String budTempItemColumnName, final Property[] sumProperties, String tableName)
	{
		return this.budgetSheetDao.summary(foreignKeyColumnName, busIds, budtemitemid, budTempItemColumnName, sumProperties, tableName);
	}

}
