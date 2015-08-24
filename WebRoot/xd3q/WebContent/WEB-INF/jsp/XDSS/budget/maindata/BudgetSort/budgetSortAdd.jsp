<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月04日 13点22分32秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象预算分类(BudgetSort)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetSort/budgetSortAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetSort/budgetSortAddGen.js"></script>
</head>
<body>
<fisc:grid divId="div_budgetTemplate" boName="BudgetTemplate" needCheckBox="true" editable="true" defaultCondition=" YBUDTEM.BUDSORTID='${budgetSort.budsortid}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid><div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 12(调试用)序号:4;formRowNo:1 ;rowNo: 01;columnNo: 1;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.budsortname}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="BudgetSort.budsortname" name="BudgetSort.budsortname" value="${budgetSort.budsortname}" <fisc:authentication sourceName="BudgetSort.budsortname" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 12(调试用)序号:5;formRowNo:1 ;rowNo: 01;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.budsortdesc}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="BudgetSort.budsortdesc" name="BudgetSort.budsortdesc" value="${budgetSort.budsortdesc}" <fisc:authentication sourceName="BudgetSort.budsortdesc" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 12(调试用)序号:6;formRowNo:2 ;rowNo: 02;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.boid}：</td>
		<td  width="30%" >
			<div id="div_boid_sh"></div>
			<fisc:searchHelp divId="div_boid_sh" boName="BudgetSort" boProperty="boid" value="${budgetSort.boid}"></fisc:searchHelp>
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 12(调试用)序号:8;formRowNo:3 ;rowNo: 03;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.creator}：</td>
		<td  width="30%" >
			<fisc:user boProperty="creator" boName="BudgetSort" userId="${budgetSort.creator}"></fisc:user>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 12(调试用)序号:9;formRowNo:3 ;rowNo: 03;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.createtime}：</td>
		<td   width="40%" >
		    <input type="text" class="inputText" id="BudgetSort.createtime" name="BudgetSort.createtime" value="${budgetSort.createtime}"  readonly="readonly" <fisc:authentication sourceName="BudgetSort.createtime" taskId=""/>>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 12(调试用)序号:10;formRowNo:4 ;rowNo: 04;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.lastmodifyer}：</td>
		<td  width="30%" >
			<fisc:user boProperty="lastmodifyer" boName="BudgetSort" userId="${budgetSort.lastmodifyer}"></fisc:user>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 12(调试用)序号:11;formRowNo:4 ;rowNo: 04;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.lastmodifytime}：</td>
		<td   width="40%" >
		    <input type="text" class="inputText" id="BudgetSort.lastmodifytime" name="BudgetSort.lastmodifytime" value="${budgetSort.lastmodifytime}"  readonly="readonly" <fisc:authentication sourceName="BudgetSort.lastmodifytime" taskId=""/>>
		</td>
	</tr>

	<input type="hidden" id="BudgetSort.client" name="BudgetSort.client" value="${budgetSort.client}">
	<input type="hidden" id="BudgetSort.budsortid" name="BudgetSort.budsortid" value="${budgetSort.budsortid}">
	<input type="hidden" id="BudgetSort.budupsortid" name="BudgetSort.budupsortid" value="${budgetSort.budupsortid}">
	<input type="hidden" id="BudgetSort.ischange" name="BudgetSort.ischange" value="${budgetSort.ischange}">
</table>
</form>
</div>

	          
		        	         
						<div id="div_budgetTemplate" ></div>
                     
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

//页面文本
var Txt={
	//预算分类
	budgetSort:'${vt.boText}',
	          
	//预算模版
	budgetTemplate:'${budgetTemplateVt.boText}',
	//boText复制创建
	budgetSort_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	budgetSort_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	budgetSort_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
	// '提交成功！'
	submitSuccess:'${vt.submitSuccess}',
	// '操作成功！'
	operateSuccess:'${vt.operateSuccess}',
	// 提示
	cue:'${vt.sCue}',
	// 确定
	ok:'${vt.sOk}',
	// 取消
	cancel:'${vt.sCancel}'
};

/**
 * EXT 布局
 */
//Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:"border",
		border:false,
		items:[{
					region:'center',
					layout:'border',
					border:false,
					items:[{
							region:'north',
							layout:'fit',
							height:26,
							border:false,
							contentEl:'div_toolbar'
						},{
							region:'center',
							layout: 'anchor',
				            height:600,
				            border:false,
				            autoScroll: true,
				            items:[{
				            		title: '${vt.boTextInfo}',
				            		layout:'fit',
				            		autoScroll: true,
				            		contentEl:'div_center'
						}
						,{
									xtype:'tabpanel',
					            	height:310,
					            	id:'tabs',
					            	name:'tabs',
					            	defaults:{bodyStyle:"background-color:#DFE8F6"},
					            	autoScroll: true,
									activeTab:0,
						            items:[
				          						                {
						            		title: '${budgetTemplateVt.boText}',
						            		layout:'fit',
						            		id:'budgetTemplateTab',
						            		contentEl:'div_budgetTemplate'
						            	}
				          
						]
						}]
						}]
				}
				 ]
	});
	var toolbars = new Ext.Toolbar({
			items:[
					'-','->','-',
					          
          
          
          
					{id:'_save',text:'${vt.mSaveOrUpdate}',handler:_save,iconCls:'icon-table-save'},'-',
          
					{id:'_cancel',text:'${vt.mCancel}',handler:_cancel,iconCls:'icon-undo'},'-',
          
					' '],  
			renderTo:"div_toolbar"
	});
Ext.onReady(function(){
    var tabsSize = 1;
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < 1 ; i++){
		   tabs.setActiveTab(1-1-i);
		}
	}
 });
//});
</script>
