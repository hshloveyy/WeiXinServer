<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月26日 10点11分24秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象采购订单(OporDoc)增加页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/b1/opor/oporDocAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/b1/opor/oporDocAddGen.js"></script>
</head>
<body>
<fisc:grid divId="div_oporDocItem" boName="OporDocItem" needCheckBox="true" editable="true" defaultCondition=" YOPORDOCITEM.OPORDOCID='${oporDoc.oporDocId}'" needAutoLoad="true" height="285" nameSapceSupport="true" orderBySql=" YOPORDOCITEM.ITEMCODE "></fisc:grid><div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right"  width="15%" >${vt.property.cardCode}：</td>
		<td  width="30%" >
			<div id="div_cardCode_sh"></div>
			<fisc:searchHelp divId="div_cardCode_sh" boName="OporDoc" boProperty="cardCode" value="${oporDoc.cardCode}"></fisc:searchHelp>
		</td>
		<td align="right"  width="15%" >${vt.property.cardName}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="OporDoc.cardName" name="OporDoc.cardName" value="${oporDoc.cardName}" <fisc:authentication sourceName="OporDoc.cardName" taskId=""/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.taxDate}：</td>
		<td  width="30%" >
			<input type="text" id="OporDoc.taxDate" name="OporDoc.taxDate" value="">
				<fisc:calendar applyTo="OporDoc.taxDate"  divId="" fieldName="" defaultValue="${oporDoc.taxDate}"></fisc:calendar>
		</td>
		<td align="right"  width="15%" >${vt.property.docDate}：</td>
		<td   width="40%" >
			<input type="text" id="OporDoc.docDate" name="OporDoc.docDate" value="">
				<fisc:calendar applyTo="OporDoc.docDate"  divId="" fieldName="" defaultValue="${oporDoc.docDate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.docDueDate}：</td>
		<td  width="30%" >
			<input type="text" id="OporDoc.docDueDate" name="OporDoc.docDueDate" value="">
				<fisc:calendar applyTo="OporDoc.docDueDate"  divId="" fieldName="" defaultValue="${oporDoc.docDueDate}"></fisc:calendar>
		</td>
		<td align="right"  width="15%" >${vt.property.docTotal}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="OporDoc.docTotal" name="OporDoc.docTotal" value="${oporDoc.docTotal}" <fisc:authentication sourceName="OporDoc.docTotal" taskId=""/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.numatCard}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="OporDoc.numatCard" name="OporDoc.numatCard" value="${oporDoc.numatCard}" <fisc:authentication sourceName="OporDoc.numatCard" taskId=""/> >
		</td>
		<td align="right"  width="15%" >${vt.property.comments}：</td>
		<td   width="40%" >
			<div id="div_comments_dict"></div>
			<fisc:dictionary boName="OporDoc" boProperty="comments" dictionaryName="YDOVTG" divId="div_comments_dict" isNeedAuth="false"  value="${oporDoc.comments}"  groupValue="I"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.u_lc}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="OporDoc.u_lc" name="OporDoc.u_lc" value="${oporDoc.u_lc}" <fisc:authentication sourceName="OporDoc.u_lc" taskId=""/> >
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="OporDoc.client" name="OporDoc.client" value="${oporDoc.client}">
	<input type="hidden" id="OporDoc.oporDocId" name="OporDoc.oporDocId" value="${oporDoc.oporDocId}">
	<input type="hidden" id="OporDoc.processstate" name="OporDoc.processstate" value="${oporDoc.processstate}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_oporDocItem" ></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

//页面文本
var Txt={
	//采购订单
	oporDoc:'${vt.boText}',
	          
	//采购订单明细
	oporDocItem:'${oporDocItemVt.boText}',
	//boText创建
	oporDocItem_Create:'${oporDocItemVt.boTextCreate}',
	//boText复制创建
	oporDocItem_CopyCreate:'${oporDocItemVt.boTextCopyCreate}',
	// 进行【采购订单明细复制创建】操作时，只允许选择一条记录！
	oporDocItem_CopyCreate_AllowOnlyOneItemForOperation:'${oporDocItemVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【采购订单明细复制创建】操作的记录！
	oporDocItem_CopyCreate_SelectToOperation:'${oporDocItemVt.copyCreate_SelectToOperate}',
	//boText复制创建
	oporDoc_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	oporDoc_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	oporDoc_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
						            		title: '${oporDocItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'oporDocItemTab',
						            		contentEl:'div_oporDocItem'
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
					          
          
          
          
					{id:'_saveOrUpdateOporDoc',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateOporDoc,iconCls:'icon-table-save'},'-',
          
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
