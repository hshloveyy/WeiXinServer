<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月11日 15点19分14秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象管理费用预算(DeptCharge)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/sample/deptcharge/deptChargeAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/sample/deptcharge/deptChargeAddGen.js"></script>
</head>
<body>
<fisc:grid divId="div_deptCharDetail" boName="DeptCharDetail" needCheckBox="true" editable="true" defaultCondition=" YDEPTCHARDETAIL.DEPTCHARGEID='${deptCharge.deptchargeid}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid><div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right"  width="15%" >${vt.property.ayear}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="DeptCharge.ayear" name="DeptCharge.ayear" value="${deptCharge.ayear}" <fisc:authentication sourceName="DeptCharge.ayear" taskId=""/> >
		</td>
		<td align="right"  width="15%" >${vt.property.budorgname}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="DeptCharge.budorgname" name="DeptCharge.budorgname" value="${deptCharge.budorgname}" <fisc:authentication sourceName="DeptCharge.budorgname" taskId=""/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.unit}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="DeptCharge.unit" name="DeptCharge.unit" value="${deptCharge.unit}" <fisc:authentication sourceName="DeptCharge.unit" taskId=""/> >
		</td>
		<td align="right"  width="15%" >${vt.property.examinestate}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="DeptCharge.examinestate" name="DeptCharge.examinestate" value="${deptCharge.examinestate}" <fisc:authentication sourceName="DeptCharge.examinestate" taskId=""/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.validity}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="DeptCharge.validity" name="DeptCharge.validity" value="${deptCharge.validity}" <fisc:authentication sourceName="DeptCharge.validity" taskId=""/> >
		</td>
		<td align="right"  width="15%" >${vt.property.version}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="DeptCharge.version" name="DeptCharge.version" value="${deptCharge.version}" <fisc:authentication sourceName="DeptCharge.version" taskId=""/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.budclassid}：</td>
		<td  width="30%" >
			<div id="div_budclassid_sh"></div>
			<fisc:searchHelp divId="div_budclassid_sh" boName="DeptCharge" boProperty="budclassid" value="${deptCharge.budclassid}"></fisc:searchHelp>
		</td>
		<td align="right"  width="15%" >${vt.property.orgmemo}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="DeptCharge.orgmemo" name="DeptCharge.orgmemo" value="${deptCharge.orgmemo}" <fisc:authentication sourceName="DeptCharge.orgmemo" taskId=""/> >
		</td>
	</tr>

	<input type="hidden" id="DeptCharge.client" name="DeptCharge.client" value="${deptCharge.client}">
	<input type="hidden" id="DeptCharge.deptchargeid" name="DeptCharge.deptchargeid" value="${deptCharge.deptchargeid}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_deptCharDetail" ></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

//页面文本
var Txt={
	//管理费用预算
	deptCharge:'${vt.boText}',
	          
	//费用明细
	deptCharDetail:'${deptCharDetailVt.boText}',
	//boText复制创建
	deptCharDetail_CopyCreate:'${deptCharDetailVt.boTextCopyCreate}',
	// 进行【费用明细复制创建】操作时，只允许选择一条记录！
	deptCharDetail_CopyCreate_AllowOnlyOneItemForOperation:'${deptCharDetailVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【费用明细复制创建】操作的记录！
	deptCharDetail_CopyCreate_SelectToOperation:'${deptCharDetailVt.copyCreate_SelectToOperate}',
	//boText创建
	deptCharDetail_Create:'${deptCharDetailVt.boTextCreate}',
	//boText复制创建
	deptCharge_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	deptCharge_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	deptCharge_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
						            		title: '${deptCharDetailVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'deptCharDetailTab',
						            		contentEl:'div_deptCharDetail'
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
