<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年12月04日 10点48分24秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象SAP采购凭证(PurchasingDoc)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SAP采购凭证增加页面</title>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/purchase/purchasingDocAdd.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/purchase/purchasingDocAddGen.js"></script>
</head>
<body>
<fisc:grid divId="div_purchasingDocItem" boName="PurchasingDocItem" needCheckBox="true" editable="true" defaultCondition=" EKPO.EBELN='${purchasingDoc.ebeln}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid><div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 11(调试用)序号:3;formRowNo:1 ;rowNo: 001;columnNo: 1;1X -->
		<td align="right"  width="15%" >采购凭证号：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="PurchasingDoc.ebeln" name="PurchasingDoc.ebeln" value="${purchasingDoc.ebeln}" <fisc:authentication sourceName="PurchasingDoc.ebeln" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 11(调试用)序号:4;formRowNo:1 ;rowNo: 001;columnNo: 2;1X -->
		<td align="right"  width="15%" >采购凭证的状态：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="PurchasingDoc.statu" name="PurchasingDoc.statu" value="${purchasingDoc.statu}" <fisc:authentication sourceName="PurchasingDoc.statu" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 11(调试用)序号:5;formRowNo:2 ;rowNo: 002;columnNo: 1;1X -->
		<td align="right"  width="15%" >记录的创建日期：</td>
		<td  width="30%" >
			<input type="text" id="PurchasingDoc.aedat" name="PurchasingDoc.aedat" value="">
				<fisc:calendar applyTo="PurchasingDoc.aedat"  divId="" fieldName="" defaultValue="${purchasingDoc.aedat}"></fisc:calendar>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 11(调试用)序号:6;formRowNo:2 ;rowNo: 002;columnNo: 2;1X -->
		<td align="right"  width="15%" >最后审批人：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="PurchasingDoc.lastappname" name="PurchasingDoc.lastappname" value="${purchasingDoc.lastappname}" <fisc:authentication sourceName="PurchasingDoc.lastappname" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 11(调试用)序号:7;formRowNo:3 ;rowNo: 003;columnNo: 1;1X -->
		<td align="right"  width="15%" >公司代码：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="PurchasingDoc.bukrs" name="PurchasingDoc.bukrs" value="${purchasingDoc.bukrs}" <fisc:authentication sourceName="PurchasingDoc.bukrs" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 11(调试用)序号:8;formRowNo:3 ;rowNo: 003;columnNo: 2;1X -->
		<td align="right"  width="15%" >最后审批时间：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="PurchasingDoc.lastapptime" name="PurchasingDoc.lastapptime" value="${purchasingDoc.lastapptime}" <fisc:authentication sourceName="PurchasingDoc.lastapptime" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 11(调试用)序号:9;formRowNo:4 ;rowNo: 004;columnNo: 1;1X -->
		<td align="right"  width="15%" >付款条件代码：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="PurchasingDoc.zterm" name="PurchasingDoc.zterm" value="${purchasingDoc.zterm}" <fisc:authentication sourceName="PurchasingDoc.zterm" taskId=""/> >
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="PurchasingDoc.client" name="PurchasingDoc.client" value="${purchasingDoc.client}">
	<input type="hidden" id="PurchasingDoc.waers" name="PurchasingDoc.waers" value="${purchasingDoc.waers}">
	<input type="hidden" id="PurchasingDoc.processstate" name="PurchasingDoc.processstate" value="${purchasingDoc.processstate}">
</table>
</form>
</div>

	          
		        	         
						<div id="div_purchasingDocItem" ></div>
                     
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
				            		title: 'SAP采购凭证信息',
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
						            		title: 'SAP采购凭证行项目',
						            		layout:'fit',
						            		id:'purchasingDocItemTab',
						            		contentEl:'div_purchasingDocItem'
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
