<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年12月25日 08点58分59秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象检验信息(Examine)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/TEMP/examine/examineAdd.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/TEMP/examine/examineAddGen.js"></script>
</head>
<body>
<fisc:grid divId="div_examineItem" boName="ExamineItem" needCheckBox="true" editable="true" defaultCondition=" YEXAMINEITEM.VI='${examine.vi}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid>
<fisc:grid divId="div_examineUser" boName="ExamineUser" needCheckBox="true" editable="false" defaultCondition=" YEXAMINEUSER.VI='${examine.vi}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid>
<fisc:attachement businessId="${businessId}" allowDelete="true" divId="div_attachement" boName="Examine" gridPageSize="10" gridHeight="285"></fisc:attachement><div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 6(调试用)序号:2;formRowNo:1 ;rowNo: 1;columnNo: 1;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.outward}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Examine.outward" name="Examine.outward" value="${examine.outward}" <fisc:authentication sourceName="Examine.outward" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 6(调试用)序号:3;formRowNo:1 ;rowNo: 1;columnNo: 2;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.outward1}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Examine.outward1" name="Examine.outward1" value="${examine.outward1}" <fisc:authentication sourceName="Examine.outward1" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 6(调试用)序号:4;formRowNo:2 ;rowNo: 2;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.outward2}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Examine.outward2" name="Examine.outward2" value="${examine.outward2}" <fisc:authentication sourceName="Examine.outward2" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 6(调试用)序号:5;formRowNo:2 ;rowNo: 2;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.outward3}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Examine.outward3" name="Examine.outward3" value="${examine.outward3}" <fisc:authentication sourceName="Examine.outward3" taskId=""/> >
		</td>
	</tr>

	<input type="hidden" id="Examine.vi" name="Examine.vi" value="${examine.vi}">
	<input type="hidden" id="Examine.purchaseOrderId" name="Examine.purchaseOrderId" value="${examine.purchaseOrderId}">
</table>
</form>
</div>

	          
		        	         
						<div id="div_examineItem" ></div>
                               
		        	         
						<div id="div_examineUser" ></div>
                               
<div id="div_attachement" ></div>

<div id="div_examine" class="x-hide-display"> 
<form id="examineForm" name="examineForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
			<tr>
				<td align="right"  width="15%" >${vt.property.outward4}：</td>
				<td  width="30%" >
					<input type="text" class="inputText" id="Examine.outward4" name="Examine.outward4" value="${examine.outward4}"   <fisc:authentication sourceName="Examine.outward4" taskId=""/>>
				</td>
			      <td></td><td></td>
				</tr>
			<tr>
				<td align="right"  width="15%" ><font color="red">★</font>${vt.property.certificatedate}：</td>
				<td  width="30%" >
					<input type="text" id="Examine.certificatedate" name="Examine.certificatedate" value="">
						<fisc:calendar applyTo="Examine.certificatedate"  divId="" fieldName=""  defaultValue="${examine.certificatedate}"></fisc:calendar>
				</td>
			      <td></td><td></td>
				</tr>
	</table>
	</form>
	</div> 
</body>
</html>
<script type="text/javascript">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

//页面文本
var Txt={
	//检验信息
	examine:'${vt.boText}',
	          
	//检查信息明细
	examineItem:'${examineItemVt.boText}',
	//boText创建
	examineItem_Create:'${examineItemVt.boTextCreate}',
	//boText复制创建
	examineItem_CopyCreate:'${examineItemVt.boTextCopyCreate}',
	// 进行【检查信息明细复制创建】操作时，只允许选择一条记录！
	examineItem_CopyCreate_AllowOnlyOneItemForOperation:'${examineItemVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【检查信息明细复制创建】操作的记录！
	examineItem_CopyCreate_SelectToOperation:'${examineItemVt.copyCreate_SelectToOperate}',
          
	//检查参与人员
	examineUser:'${examineUserVt.boText}',
	// 请选择需要进行【检查参与人员批量删除】操作的记录！
	examineUser_Deletes_SelectToOperation:'${examineUserVt.deletes_SelectToOperate}',
	// 您选择了【检查参与人员批量删除】操作，是否确定继续该操作？
	examineUser_Deletes_ConfirmOperation:'${examineUserVt.deletes_ConfirmOperation}',
          
	//boText复制创建
	examine_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	examine_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	examine_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
									    		id:'examineTab',
							            		title: '${vt.boText}',
							            		layout:'fit',
							            		bodyStyle:"background-color:#DFE8F6",
							            		contentEl:'div_examine'
							             },
				          						                {
						            		title: '${examineItemVt.boText}',
						            		layout:'fit',
						            		id:'examineItemTab',
						            		contentEl:'div_examineItem'
						            	},
          						                {
						            		title: '${examineUserVt.boText}',
						            		layout:'fit',
						            		id:'examineUserTab',
						            		contentEl:'div_examineUser'
						            	},
          						                {
						            		title: '${attachementVt.boText}',
						            		layout:'fit',
						            		id:'attachementTab',
						            		contentEl:'div_attachement'
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
          
					{id:'_cancel',text:'${vt.mCancel}',handler:_cancel,iconCls:'icon-undo'},
					' '],  
			renderTo:"div_toolbar"
	});
Ext.onReady(function(){
    var tabsSize = 4;
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < 4 ; i++){
		   tabs.setActiveTab(4-1-i);
		}
	}
 });
//});
</script>
