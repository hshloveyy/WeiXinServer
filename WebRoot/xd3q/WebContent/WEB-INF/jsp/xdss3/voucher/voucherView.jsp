<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年07月27日 07点47分01秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象凭证预览(Voucher)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/voucher/voucherEditGen.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/voucher/voucherEdit.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
</head>
<body>
<fisc:grid divId="div_voucherItem" boName="VoucherItem" needCheckBox="true" editable="true" defaultCondition=" YVOUCHERITEM.VOUCHERID='${voucher.voucherid}'" orderBySql="rownumber" pageSize="10000" title="借贷" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${voucher.voucherid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.voucherno" nodeId="${workflowNodeDefId}"/> >${vt.property.voucherno}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Voucher.voucherno" name="Voucher.voucherno" value="${voucher.voucherno}"   <fisc:authentication sourceName="Voucher.voucherno" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.vouchertype" nodeId="${workflowNodeDefId}"/> >${vt.property.vouchertype}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Voucher.vouchertype" name="Voucher.vouchertype" value="${voucher.vouchertype}"   <fisc:authentication sourceName="Voucher.vouchertype" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.vouchertext" nodeId="${workflowNodeDefId}"/> >${vt.property.vouchertext}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Voucher.vouchertext" name="Voucher.vouchertext" value="${voucher.vouchertext}"   <fisc:authentication sourceName="Voucher.vouchertext" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.voucherclass" nodeId="${workflowNodeDefId}"/> >${vt.property.voucherclass}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Voucher.voucherclass" name="Voucher.voucherclass" value="${voucher.voucherclass}"   <fisc:authentication sourceName="Voucher.voucherclass" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.checkdate" nodeId="${workflowNodeDefId}"/> >${vt.property.checkdate}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Voucher.checkdate" name="Voucher.checkdate" value="${voucher.checkdate}"   <fisc:authentication sourceName="Voucher.checkdate" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.voucherdate" nodeId="${workflowNodeDefId}"/> >${vt.property.voucherdate}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Voucher.voucherdate" name="Voucher.voucherdate" value="${voucher.voucherdate}"   <fisc:authentication sourceName="Voucher.voucherdate" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.companycode" nodeId="${workflowNodeDefId}"/> >${vt.property.companycode}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Voucher.companycode" name="Voucher.companycode" value="${voucher.companycode}"   <fisc:authentication sourceName="Voucher.companycode" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.currency" nodeId="${workflowNodeDefId}"/> >${vt.property.currency}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Voucher.currency" name="Voucher.currency" value="${voucher.currency}"   <fisc:authentication sourceName="Voucher.currency" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.exchangerate" nodeId="${workflowNodeDefId}"/> >${vt.property.exchangerate}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Voucher.exchangerate" name="Voucher.exchangerate" value="${voucher.exchangerate}"   <fisc:authentication sourceName="Voucher.exchangerate" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.fiyear" nodeId="${workflowNodeDefId}"/> >${vt.property.fiyear}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Voucher.fiyear" name="Voucher.fiyear" value="${voucher.fiyear}"   <fisc:authentication sourceName="Voucher.fiyear" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.fiperiod" nodeId="${workflowNodeDefId}"/> >${vt.property.fiperiod}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Voucher.fiperiod" name="Voucher.fiperiod" value="${voucher.fiperiod}"   <fisc:authentication sourceName="Voucher.fiperiod" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.referencecode" nodeId="${workflowNodeDefId}"/> >${vt.property.referencecode}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Voucher.referencecode" name="Voucher.referencecode" value="${voucher.referencecode}"   <fisc:authentication sourceName="Voucher.referencecode" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.importdate" nodeId="${workflowNodeDefId}"/> >${vt.property.importdate}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Voucher.importdate" name="Voucher.importdate" value="${voucher.importdate}"   <fisc:authentication sourceName="Voucher.importdate" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.importer" nodeId="${workflowNodeDefId}"/> >${vt.property.importer}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Voucher.importer" name="Voucher.importer" value="${voucher.importer}"   <fisc:authentication sourceName="Voucher.importer" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.preparer" nodeId="${workflowNodeDefId}"/> >${vt.property.preparer}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Voucher.preparer" name="Voucher.preparer" value="${voucher.preparer}"   <fisc:authentication sourceName="Voucher.preparer" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.offsetvoucherno" nodeId="${workflowNodeDefId}"/> >${vt.property.offsetvoucherno}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Voucher.offsetvoucherno" name="Voucher.offsetvoucherno" value="${voucher.offsetvoucherno}"   <fisc:authentication sourceName="Voucher.offsetvoucherno" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.offyear" nodeId="${workflowNodeDefId}"/> >${vt.property.offyear}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Voucher.offyear" name="Voucher.offyear" value="${voucher.offyear}"   <fisc:authentication sourceName="Voucher.offyear" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.iflag" nodeId="${workflowNodeDefId}"/> >${vt.property.iflag}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Voucher.iflag" name="Voucher.iflag" value="${voucher.iflag}"   <fisc:authentication sourceName="Voucher.iflag" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.eflag" nodeId="${workflowNodeDefId}"/> >${vt.property.eflag}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Voucher.eflag" name="Voucher.eflag" value="${voucher.eflag}"   <fisc:authentication sourceName="Voucher.eflag" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.bstat" nodeId="${workflowNodeDefId}"/> >${vt.property.bstat}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Voucher.bstat" name="Voucher.bstat" value="${voucher.bstat}"   <fisc:authentication sourceName="Voucher.bstat" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.konto" nodeId="${workflowNodeDefId}"/> >${vt.property.konto}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Voucher.konto" name="Voucher.konto" value="${voucher.konto}"   <fisc:authentication sourceName="Voucher.konto" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.valut" nodeId="${workflowNodeDefId}"/> >${vt.property.valut}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Voucher.valut" name="Voucher.valut" value="${voucher.valut}"   <fisc:authentication sourceName="Voucher.valut" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.agkon" nodeId="${workflowNodeDefId}"/> >${vt.property.agkon}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Voucher.agkon" name="Voucher.agkon" value="${voucher.agkon}"   <fisc:authentication sourceName="Voucher.agkon" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.agkoa" nodeId="${workflowNodeDefId}"/> >${vt.property.agkoa}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Voucher.agkoa" name="Voucher.agkoa" value="${voucher.agkoa}"   <fisc:authentication sourceName="Voucher.agkoa" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.agums" nodeId="${workflowNodeDefId}"/> >${vt.property.agums}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Voucher.agums" name="Voucher.agums" value="${voucher.agums}"   <fisc:authentication sourceName="Voucher.agums" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.gsber" nodeId="${workflowNodeDefId}"/> >${vt.property.gsber}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Voucher.gsber" name="Voucher.gsber" value="${voucher.gsber}"   <fisc:authentication sourceName="Voucher.gsber" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.rstgr" nodeId="${workflowNodeDefId}"/> >${vt.property.rstgr}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Voucher.rstgr" name="Voucher.rstgr" value="${voucher.rstgr}"   <fisc:authentication sourceName="Voucher.rstgr" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.kostl" nodeId="${workflowNodeDefId}"/> >${vt.property.kostl}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Voucher.kostl" name="Voucher.kostl" value="${voucher.kostl}"   <fisc:authentication sourceName="Voucher.kostl" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.flag" nodeId="${workflowNodeDefId}"/> >${vt.property.flag}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Voucher.flag" name="Voucher.flag" value="${voucher.flag}"   <fisc:authentication sourceName="Voucher.flag" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.pay" nodeId="${workflowNodeDefId}"/> >${vt.property.pay}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Voucher.pay" name="Voucher.pay" value="${voucher.pay}"   <fisc:authentication sourceName="Voucher.pay" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="Voucher.receive" nodeId="${workflowNodeDefId}"/> >${vt.property.receive}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Voucher.receive" name="Voucher.receive" value="${voucher.receive}"   <fisc:authentication sourceName="Voucher.receive" nodeId="${workflowNodeDefId}"/>>
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="Voucher.client" name="Voucher.client" value="${voucher.client}">
	<input type="hidden" id="Voucher.voucherid" name="Voucher.voucherid" value="${voucher.voucherid}">
	<input type="hidden" id="Voucher.businessid" name="Voucher.businessid" value="${voucher.businessid}">
	<input type="hidden" id="Voucher.businesstype" name="Voucher.businesstype" value="${voucher.businesstype}">
	<input type="hidden" id="Voucher.processstate" name="Voucher.processstate" value="${voucher.processstate}">
	<input type="hidden" id="Voucher.isconfirm" name="Voucher.isconfirm" value="${voucher.isconfirm}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_voucherItem"></div>
<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '${voucher.processstate}';
//当前对象主键ID
var voucherid = '${voucher.voucherid}';	

//页面文本
var Txt={
	//凭证预览
	voucher:'${vt.boText}',
	          
	//凭证预览明细
	voucherItem:'${voucherItemVt.boText}',
	//boText复制创建
	voucher_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	voucher_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	voucher_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
							layout:'anchor',
				            height:600,
				            border:false,
				            autoScroll:true,
				            items:[
				               {
				            	id:'currentWorkflowTask',
					            title: '${vt.processApproveInfo}',
					            border:false,
					            layout:'fit',
					            contentEl:'div_top_north'
					          },
				              {
				            		title:'${vt.boTextInfo}',
				            		layout:'fit',
				            		autoScroll: true,
				            		border:false,
				            		//height:610,
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
						            		title:'${voucherItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_voucherItem'
						            	}
						    ]}
						   ]}
						,{
							id:'historyWorkflowTask',
							region:'south',
							title:'${vt.processTrackInfo}',
		            		layout:'anchor',
			            	collapsible: true,
			            	collapsed:true,
			            	border:false,
			            	autoScroll: true,
							height:200,
							contentEl:'div_top_south'
						}			
]
				}
                 ]
	});

	var toolbars = new Ext.Toolbar({
			items:[
					'-',
'->','-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelVoucher,iconCls:'icon-undo'},'-',
' '
],
			renderTo:'div_toolbar'
	});

	var isTask = '${workflowTaskId}';
	if(!isTask || isTask==''){
		Ext.getCmp('currentWorkflowTask').hide();
		//如果已经提交，不允许再提交
		if(isSubmited && isSubmited!='' && isSubmited!=' '){
		}
		else{
			Ext.getCmp('historyWorkflowTask').hide();
		}
		viewport.doLayout();
	}else{
	}
//});
Ext.onReady(function(){
    var tabsSize = 1;
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < 1 ; i++){
		   tabs.setActiveTab(1-1-i);
		}
	}
	
	var cm = VoucherItem_grid.getColumnModel();
	cm.setRenderer(cm.findColumnIndex('amount'),function(v,m,r){
		if(r.get('debitcredit')=='H'){
			return v+'-';
		}else{
			return v;
		}
	});
	
	cm.setRenderer(cm.findColumnIndex('amount2'),function(v,m,r){
		if(r.get('debitcredit')=='H'){
			return v+'-';
		}else{
			return v;
		}
	});
	
	VoucherItem_grid.getStore().on('load',function(e,recs,o){
		var amountH = 0;
		var amountS = 0;
		var amountH2 = 0;
		var amountS2 = 0;
		for(var i=0;i<recs.length;i++){
			var r=recs[i];
			if(r.get('debitcredit')=='H'){
				amountH += parseFloat(r.get('amount'));
				amountH2 += parseFloat(r.get('amount2'));
			}else{
				amountS += parseFloat(r.get('amount'));
				amountS2 += parseFloat(r.get('amount2'));
			}
			//判断是否是需要业务范围，2100在模拟凭证后的凭证行项目不用业务范围，如果以后要加公司，如果跟2100一样就加，不一样就不用
			if('2100' == Ext.get('Voucher.companycode').dom.value){
				
			}else{
				r.set('gsber','');
			}
		}
		VoucherItem_grid.setTitle('原币  借：'+ round(amountS,2)+'   贷：'+Math.abs(round(amountH,2)) +'-      本位币  借：'+ round(amountS2,2)+'   贷：'+Math.abs(round(amountH2,2)) +'-');
	});
 });
</script>

