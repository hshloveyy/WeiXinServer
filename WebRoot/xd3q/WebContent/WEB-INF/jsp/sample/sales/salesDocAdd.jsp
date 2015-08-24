<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年12月04日 16点13分41秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象SAP销售订单(SalesDoc)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SAP销售订单增加页面</title>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/sales/salesDocAdd.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/sales/salesDocAddGen.js"></script>
</head>
<body>
<fisc:grid divId="div_salesDocItem" boName="SalesDocItem" needCheckBox="true" editable="true" defaultCondition=" YVSOITEM.VBELN='${salesDoc.vbeln}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid><div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 10(调试用)序号:2;formRowNo:1 ;rowNo: 001;columnNo: 1;1X -->
		<td align="right"  width="15%" >销售凭证：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="SalesDoc.vbeln" name="SalesDoc.vbeln" value="${salesDoc.vbeln}" <fisc:authentication sourceName="SalesDoc.vbeln" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 10(调试用)序号:3;formRowNo:1 ;rowNo: 001;columnNo: 2;1X -->
		<td align="right"  width="15%" >审批状态：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="SalesDoc.endorsestate" name="SalesDoc.endorsestate" value="${salesDoc.endorsestate}" <fisc:authentication sourceName="SalesDoc.endorsestate" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 10(调试用)序号:4;formRowNo:2 ;rowNo: 002;columnNo: 1;1X -->
		<td align="right"  width="15%" >销售订单日期：</td>
		<td  width="30%" >
			<input type="text" id="SalesDoc.erdat" name="SalesDoc.erdat" value="">
				<fisc:calendar applyTo="SalesDoc.erdat"  divId="" fieldName="" defaultValue="${salesDoc.erdat}"></fisc:calendar>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 10(调试用)序号:5;formRowNo:2 ;rowNo: 002;columnNo: 2;1X -->
		<td align="right"  width="15%" >最后审批人：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="SalesDoc.lastappname" name="SalesDoc.lastappname" value="${salesDoc.lastappname}" <fisc:authentication sourceName="SalesDoc.lastappname" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 10(调试用)序号:6;formRowNo:3 ;rowNo: 003;columnNo: 1;1X -->
		<td align="right"  width="15%" > 客户：</td>
		<td  width="30%" >
			<div id="div_kunnr_sh"></div>
			<fisc:searchHelp divId="div_kunnr_sh" boName="SalesDoc" boProperty="kunnr" value="${salesDoc.kunnr}"></fisc:searchHelp>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 10(调试用)序号:7;formRowNo:3 ;rowNo: 003;columnNo: 2;1X -->
		<td align="right"  width="15%" >最后审批时间：</td>
		<td   width="40%" >
			<input type="text" id="SalesDoc.lastapptime" name="SalesDoc.lastapptime" value="">
				<fisc:calendar applyTo="SalesDoc.lastapptime"  divId="" fieldName="" defaultValue="${salesDoc.lastapptime}"></fisc:calendar>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 10(调试用)序号:8;formRowNo:4 ;rowNo: 004;columnNo: 1;1X -->
		<td align="right"  width="15%" >付款条件：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="SalesDoc.zterm" name="SalesDoc.zterm" value="${salesDoc.zterm}" <fisc:authentication sourceName="SalesDoc.zterm" taskId=""/> >
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="SalesDoc.client" name="SalesDoc.client" value="${salesDoc.client}">
	<input type="hidden" id="SalesDoc.processstate" name="SalesDoc.processstate" value="${salesDoc.processstate}">
</table>
</form>
</div>

	          
		        	         
						<div id="div_salesDocItem" ></div>
                     
</body>
</html>
<script type="text/javascript">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

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
				            		title: 'SAP销售订单信息',
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
						            		title: 'SAP销售凭证行项目',
						            		layout:'fit',
						            		id:'salesDocItemTab',
						            		contentEl:'div_salesDocItem'
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
					          
          
					{id:'_cancel',text:'取消',handler:_cancel,iconCls:'icon-undo'},
					'-'],  
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
