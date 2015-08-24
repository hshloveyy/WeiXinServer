<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年01月09日 11点03分53秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象采购订单(SAP)(PurchaseInfo)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/purchase/purchaseInfoAdd.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/purchase/purchaseInfoAddGen.js"></script>
</head>
<body>
<fisc:grid divId="div_purchaseRows" boName="PurchaseRows" needCheckBox="true" editable="true" defaultCondition=" YPURCHASEROWS.PURCHASEINFOID='${purchaseInfo.purchaseinfoId}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid><div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:14;formRowNo:1 ;rowNo: 0001;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.contractNo}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="PurchaseInfo.contractNo" name="PurchaseInfo.contractNo" value="${purchaseInfo.contractNo}" <fisc:authentication sourceName="PurchaseInfo.contractNo" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:15;formRowNo:1 ;rowNo: 0001;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.contractName}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="PurchaseInfo.contractName" name="PurchaseInfo.contractName" value="${purchaseInfo.contractName}" <fisc:authentication sourceName="PurchaseInfo.contractName" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:16;formRowNo:2 ;rowNo: 0002;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.invoicingParty}：</td>
		<td  width="30%" >
			<div id="div_invoicingParty_sh"></div>
			<fisc:searchHelp divId="div_invoicingParty_sh" boName="PurchaseInfo" boProperty="invoicingParty" value="${purchaseInfo.invoicingParty}"></fisc:searchHelp>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:17;formRowNo:2 ;rowNo: 0002;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.payer}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="PurchaseInfo.payer" name="PurchaseInfo.payer" value="${purchaseInfo.payer}" <fisc:authentication sourceName="PurchaseInfo.payer" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:18;formRowNo:3 ;rowNo: 0003;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.bsart}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="PurchaseInfo.bsart" name="PurchaseInfo.bsart" value="${purchaseInfo.bsart}" <fisc:authentication sourceName="PurchaseInfo.bsart" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:19;formRowNo:3 ;rowNo: 0003;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.bstyp}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="PurchaseInfo.bstyp" name="PurchaseInfo.bstyp" value="${purchaseInfo.bstyp}" <fisc:authentication sourceName="PurchaseInfo.bstyp" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:20;formRowNo:4 ;rowNo: 0004;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.lifnr}：</td>
		<td  width="30%" >
			<div id="div_lifnr_sh"></div>
			<fisc:searchHelp divId="div_lifnr_sh" boName="PurchaseInfo" boProperty="lifnr" value="${purchaseInfo.lifnr}"></fisc:searchHelp>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:21;formRowNo:4 ;rowNo: 0004;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.lifnrName}：</td>
		<td   width="40%" >
			<div id="div_lifnrName_sh"></div>
			<fisc:searchHelp divId="div_lifnrName_sh" boName="PurchaseInfo" boProperty="lifnrName" value="${purchaseInfo.lifnrName}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:22;formRowNo:5 ;rowNo: 0005;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.bedat}：</td>
		<td  width="30%" >
			<input type="text" id="PurchaseInfo.bedat" name="PurchaseInfo.bedat" value="">
				<fisc:calendar applyTo="PurchaseInfo.bedat"  divId="" fieldName="" defaultValue="${purchaseInfo.bedat}"></fisc:calendar>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:23;formRowNo:5 ;rowNo: 0005;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.ekorg}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="PurchaseInfo.ekorg" name="PurchaseInfo.ekorg" value="${purchaseInfo.ekorg}" <fisc:authentication sourceName="PurchaseInfo.ekorg" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:24;formRowNo:6 ;rowNo: 0006;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.ekgrp}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="PurchaseInfo.ekgrp" name="PurchaseInfo.ekgrp" value="${purchaseInfo.ekgrp}" <fisc:authentication sourceName="PurchaseInfo.ekgrp" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:25;formRowNo:6 ;rowNo: 0006;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.zterm}：</td>
		<td   width="40%" >
			<div id="div_zterm_sh"></div>
			<fisc:searchHelp divId="div_zterm_sh" boName="PurchaseInfo" boProperty="zterm" value="${purchaseInfo.zterm}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:26;formRowNo:7 ;rowNo: 0007;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.inco1}：</td>
		<td  width="30%" >
			<div id="div_inco1_sh"></div>
			<fisc:searchHelp divId="div_inco1_sh" boName="PurchaseInfo" boProperty="inco1" value="${purchaseInfo.inco1}"></fisc:searchHelp>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:27;formRowNo:7 ;rowNo: 0007;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.waers}：</td>
		<td   width="40%" >
			<div id="div_waers_sh"></div>
			<fisc:searchHelp divId="div_waers_sh" boName="PurchaseInfo" boProperty="waers" value="${purchaseInfo.waers}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:28;formRowNo:8 ;rowNo: 0008;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.wkurs}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="PurchaseInfo.wkurs" name="PurchaseInfo.wkurs" value="${purchaseInfo.wkurs}" <fisc:authentication sourceName="PurchaseInfo.wkurs" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:29;formRowNo:8 ;rowNo: 0008;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.ihrez}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="PurchaseInfo.ihrez" name="PurchaseInfo.ihrez" value="${purchaseInfo.ihrez}" <fisc:authentication sourceName="PurchaseInfo.ihrez" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:30;formRowNo:9 ;rowNo: 0009;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.unsez}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="PurchaseInfo.unsez" name="PurchaseInfo.unsez" value="${purchaseInfo.unsez}" <fisc:authentication sourceName="PurchaseInfo.unsez" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:31;formRowNo:9 ;rowNo: 0009;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.telf1}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="PurchaseInfo.telf1" name="PurchaseInfo.telf1" value="${purchaseInfo.telf1}" <fisc:authentication sourceName="PurchaseInfo.telf1" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:32;formRowNo:10 ;rowNo: 0010;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.shipmentPort}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="PurchaseInfo.shipmentPort" name="PurchaseInfo.shipmentPort" value="${purchaseInfo.shipmentPort}" <fisc:authentication sourceName="PurchaseInfo.shipmentPort" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:33;formRowNo:10 ;rowNo: 0010;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.destinationPort}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="PurchaseInfo.destinationPort" name="PurchaseInfo.destinationPort" value="${purchaseInfo.destinationPort}" <fisc:authentication sourceName="PurchaseInfo.destinationPort" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:34;formRowNo:11 ;rowNo: 0011;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.shipmentDate}：</td>
		<td  width="30%" >
			<input type="text" id="PurchaseInfo.shipmentDate" name="PurchaseInfo.shipmentDate" value="">
				<fisc:calendar applyTo="PurchaseInfo.shipmentDate"  divId="" fieldName="" defaultValue="${purchaseInfo.shipmentDate}"></fisc:calendar>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:35;formRowNo:11 ;rowNo: 0011;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.totalAmount}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="PurchaseInfo.totalAmount" name="PurchaseInfo.totalAmount" value="${purchaseInfo.totalAmount}" <fisc:authentication sourceName="PurchaseInfo.totalAmount" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:36;formRowNo:12 ;rowNo: 0012;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.creator}：</td>
		<td  width="30%" >
			<fisc:user boProperty="creator" boName="PurchaseInfo" userId="${purchaseInfo.creator}"></fisc:user>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:37;formRowNo:12 ;rowNo: 0012;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.createTime}：</td>
		<td   width="40%" >
		    <input type="text" class="inputText" id="PurchaseInfo.createTime" name="PurchaseInfo.createTime" value="${purchaseInfo.createTime}"  readonly="readonly" <fisc:authentication sourceName="PurchaseInfo.createTime" taskId=""/>>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:38;formRowNo:13 ;rowNo: 0013;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.lastmodifyTime}：</td>
		<td  width="30%" >
		    <input type="text" class="inputText" id="PurchaseInfo.lastmodifyTime" name="PurchaseInfo.lastmodifyTime" value="${purchaseInfo.lastmodifyTime}"  readonly="readonly" <fisc:authentication sourceName="PurchaseInfo.lastmodifyTime" taskId=""/>>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 40(调试用)序号:39;formRowNo:13 ;rowNo: 0013;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.lastmodifyer}：</td>
		<td   width="40%" >
			<fisc:user boProperty="lastmodifyer" boName="PurchaseInfo" userId="${purchaseInfo.lastmodifyer}"></fisc:user>
		</td>
	</tr>

	<input type="hidden" id="PurchaseInfo.pincr" name="PurchaseInfo.pincr" value="${purchaseInfo.pincr}">
	<input type="hidden" id="PurchaseInfo.oldcontractNo" name="PurchaseInfo.oldcontractNo" value="${purchaseInfo.oldcontractNo}">
	<input type="hidden" id="PurchaseInfo.tradeType" name="PurchaseInfo.tradeType" value="${purchaseInfo.tradeType}">
	<input type="hidden" id="PurchaseInfo.projectName" name="PurchaseInfo.projectName" value="${purchaseInfo.projectName}">
	<input type="hidden" id="PurchaseInfo.payerName" name="PurchaseInfo.payerName" value="${purchaseInfo.payerName}">
	<input type="hidden" id="PurchaseInfo.ztermName" name="PurchaseInfo.ztermName" value="${purchaseInfo.ztermName}">
	<input type="hidden" id="PurchaseInfo.invoicingName" name="PurchaseInfo.invoicingName" value="${purchaseInfo.invoicingName}">
	<input type="hidden" id="PurchaseInfo.inco1Name" name="PurchaseInfo.inco1Name" value="${purchaseInfo.inco1Name}">
	<input type="hidden" id="PurchaseInfo.inco2" name="PurchaseInfo.inco2" value="${purchaseInfo.inco2}">
	<input type="hidden" id="PurchaseInfo.waersName" name="PurchaseInfo.waersName" value="${purchaseInfo.waersName}">
	<input type="hidden" id="PurchaseInfo.purchaseinfoId" name="PurchaseInfo.purchaseinfoId" value="${purchaseInfo.purchaseinfoId}">
	<input type="hidden" id="PurchaseInfo.contractgroupId" name="PurchaseInfo.contractgroupId" value="${purchaseInfo.contractgroupId}">
	<input type="hidden" id="PurchaseInfo.projectId" name="PurchaseInfo.projectId" value="${purchaseInfo.projectId}">
	<input type="hidden" id="PurchaseInfo.processState" name="PurchaseInfo.processState" value="${purchaseInfo.processState}">
</table>
</form>
</div>

	          
		        	         
						<div id="div_purchaseRows" ></div>
                     
</body>
</html>
<script type="text/javascript">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

//页面文本
var Txt={
	//采购订单(SAP)
	purchaseInfo:'${vt.boText}',
	          
	//采购订单行项目信息(SAP)
	purchaseRows:'${purchaseRowsVt.boText}',
	//boText创建
	purchaseRows_Create:'${purchaseRowsVt.boTextCreate}',
	//boText复制创建
	purchaseRows_CopyCreate:'${purchaseRowsVt.boTextCopyCreate}',
	// 进行【采购订单行项目信息(SAP)复制创建】操作时，只允许选择一条记录！
	purchaseRows_CopyCreate_AllowOnlyOneItemForOperation:'${purchaseRowsVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【采购订单行项目信息(SAP)复制创建】操作的记录！
	purchaseRows_CopyCreate_SelectToOperation:'${purchaseRowsVt.copyCreate_SelectToOperate}',
	//boText复制创建
	purchaseInfo_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	purchaseInfo_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	purchaseInfo_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
						            		title: '${purchaseRowsVt.boText}',
						            		layout:'fit',
						            		id:'purchaseRowsTab',
						            		contentEl:'div_purchaseRows'
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
