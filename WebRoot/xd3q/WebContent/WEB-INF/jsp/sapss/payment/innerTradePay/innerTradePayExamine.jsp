<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ page import="com.infolion.sapss.payment.PaymentContants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>内贸货款信息表</title>
<style type="text/css">
.add {
	background-image: url("<%= request.getContextPath() %>/images/fam/add.gif") !important;
}

.delete {
	background-image: url("<%= request.getContextPath() %>/images/fam/delete.gif") !important;
}

.update {
	background-image: url("<%= request.getContextPath() %>/images/fam/refresh.gif" ) !important;
}

.find {
	background-image: url("<%= request.getContextPath() %>/images/fam/find.png") !important;
}

  
</style>
<script type="text/javascript">
//操作类别  
var payMethod = '${main.payMethod}';
//贸易类型
var payType = '${main.payType}';

var strTitle = '';
if (payType == '1') {
	switch(payMethod){
		case '1':
			strTitle = '国内信用证';
			break;
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '9':
			strTitle = '现金/背书/转账/电汇/网银/银行即期汇票';
			break;
		case '7':
			strTitle = '银行/商业承兑汇票';
			break;
	}
}else if(payType == '2'){
	if(payMethod=='2')
		strTitle = '期货保证金';
	else
		strTitle = '非货款';
}else if(payType == '3'){
	strTitle = '进口预付款';
}
document.title = strTitle+'相关信息';
//付款合同相关信息
var innerPayContractInfogrid; 
var innerPayContractInfods;
function openContractWin(){
	var records = innerPayContractInfogrid.selModel.getSelections();
	var contractType= records[0].data.CONTRACT_TYPE;
	var contractNo= records[0].data.CONTRACT_NO;
	var contractName= records[0].data.CONTRACT_NAME;
	var state= records[0].data.ORDER_STATE_D_ORDER_STATE;
	
	if (records[0].data.CONTRACT_TYPE.trim() == '采购合同'){
		top.ExtModalWindowUtil.show(contractType+'-'+contractNo+'-'+contractName+'-'+state,
			'contractController.spr?action=ArchPurchaseInfoView&businessRecordId='+records[0].data.CONTRACT_PURCHASE_ID,
			'',
			'',
			{width:800,height:550});
	}

	if (records[0].data.CONTRACT_TYPE.trim() == '销售合同'){
		top.ExtModalWindowUtil.show(contractType+'-'+contractNo+'-'+contractName+'-'+state,
		'contractController.spr?action=addSaleContractView&contractid='+records[0].data.CONTRACT_PURCHASE_ID,
		'',
		'',
		{width:800,height:600});
	}
}
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	
   	var innerPayContractInfoPlant = Ext.data.Record.create([
   	    {name:'CONTRACT_TYPE'},	//CONTRACT_TYPE
		{name:'CONTRACT_NO'},				//合同号
		{name:'APPROVED_TIME'},				//合同时间
		{name:'CONTRACT_PURCHASE_ID'},		//采购合同ID
		{name:'SAP_ORDER_NO'},				//SAP订单号
		{name:'TOTAL'},						//金额
		{name:'PAPER_NO'},						//金额
		{name:'CMD'},						//备注
		{name:'ORDER_STATE_D_ORDER_STATE'},	//审批状态
		{name:'CONTRACT_INFO'}				//详情
	]);

	innerPayContractInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'innerTradePayController.spr?action=queryPaymentContract&paymentId='
						+Ext.getDom('paymentId').value+'&payType=${payType}'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},innerPayContractInfoPlant)
    });
    innerPayContractInfods.load();
    /*var innerPayContractInfobbar = new Ext.PagingToolbar({
        pageSize: 100,
        store:innerPayContractInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });*/
    var innerPayContractInfosm = new Ext.grid.CheckboxSelectionModel();
	var innerPayContractInfocm = new Ext.grid.ColumnModel([
	new Ext.grid.RowNumberer(),
	innerPayContractInfosm,
	    {
			header: '合同类型',
			width: 100,
			sortable: true,
			dataIndex: 'CONTRACT_TYPE'
		},
		{
			header: '合同编号',
			width: 100,
			sortable: true,
			dataIndex: 'CONTRACT_NO'
		},
		{
			header: '合同时间',
			width: 100,
			sortable: true,
			dataIndex: 'APPROVED_TIME'
		},
		{
			header: '采购合同ID',
			width: 100,
			sortable: true,
			dataIndex: 'CONTRACT_PURCHASE_ID'
		},
		{
			header: 'SAP订单号',
			width: 100,
			sortable: true,
			dataIndex: 'SAP_ORDER_NO'
		},
		{
			header: '合同金额',
			width: 100,
			sortable: true,
			dataIndex: 'TOTAL'
		},
		{
			header: '纸质合同号',
			width: 100,
			sortable: true,
			dataIndex: 'PAPER_NO'
		},
		{
			header: '备注',
			width: 100,
			sortable: true,
			dataIndex: 'CMD'
		},
		{
			header: '合同状态',
			width: 100,
			sortable: true,
			dataIndex: 'ORDER_STATE_D_ORDER_STATE'
		},
		{
			header: '合同详情',
			width: 100,
			sortable: true,
			dataIndex: 'CONTRACT_INFO',
			renderer:contractOper
		}
	]);
	
	
	function contractOper(value, p, record){
    	return String.format('<a href="#" onclick="openContractWin()">{0}</a>',value);
    }
    //付款合同相关信息
	innerPayContractInfogrid = new Ext.grid.EditorGridPanel({
    	id:'innerPayContractInfogrid',
        ds: innerPayContractInfods,
        cm: innerPayContractInfocm,
        sm: innerPayContractInfosm,
        border:false,
        loadMask:true,
       // hidden:${!isRelateProject},
        autoScroll:true,
        region:'center',
        el: 'div_innerPayContractInfogrid',
        autowidth:true,
		height:152,
        clicksToEdit:1,
        layout:'fit'
    });
    
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
				region:'north',
				collapsible: true,
				height:110,
				border:false,
				contentEl: 'div_headForm'
			  },
			  {
				region:"center",
				layout:'fit',
				buttonAlign:'center',
				border:false,
				items:[{
					region:'center',
					xtype:'tabpanel',
					id:'infotype',
					name:'infotype',
					plain:true,
		            //height:190,
		            activeTab: 0,
		            items:[{
		            	title:strTitle+'相关信息',
		            	contentEl: '',
		            	id:'creditEntryInfo',
						name:'creditEntryInfo',
						autoScroll:true,
						//disabled:${!isRelateProject},
		            	layout:'fit',
		            	items:[innerPayContractInfogrid]
		            	},{contentEl:'rule',
			               id:'fileEl', 
			               title: '附件信息',
			               autoScroll:'true',
			               listeners:{activate:handlerActivate}
			            },{
			            	title:'审批历史记录',
			            	contentEl: 'div_history',
			            	id:'historyinfo',
							name:'historyinfo',
							autoScroll:true,
							disabled:${!saveDisabled},
			            	layout:'fit'
	            			}]
			       }]
		       },
		       {
		       	    border:false,
				    region:'south',
				    height:210,
					collapsible: true,
					autoScroll:true,
					contentEl: 'div_main'
			  }]
	});
    <c:if test="${main.payType!=2}">
	//maturityDate,货款需要显示到期日
	var maturityDate = new Ext.form.DateField({
		format:'Y-m-d',
		id:'maturityDate',
		name:'maturityDate',
		width: 160,
		disabled:${maturityDateStatus},
		renderTo:'maturityDateDiv',
		value:'${main.maturityDate}'
	});
	</c:if>

	//payRealTime
	var payRealTime = new Ext.form.DateField({
		format:'Y-m-d',
		id:'payRealTime',
		name:'payRealTime',
		width: 160,
		readOnly:true,
		disabled:${payRealTimeStatus},
		renderTo:'payRealTimeDiv',
		value:'${main.payRealTime}'
	});
	
	if (Ext.getDom('creatorTime').value == '') {
		var dt = new Date();
		Ext.getDom('creatorTime').value = dt.format('Y-m-d');
	}
	setPreSecurity();
	setDeposit();
});//end of Ext.onReady


var needCheckDeposit;
function setDeposit(){
	try {
		var preSecurity = document.getElementsByName('preSecurity');
		var deposit = document.getElementById('deposit');
		var amount = document.getElementById('amount');
		if (preSecurity[1].checked) {
			deposit.disabled = false;
			deposit.style.borderColor = "#A0A0A0";
			deposit.style.borderWidth = "1px";
			deposit.onchange = function(){
				judgeFloat(deposit, '保证金');
			};
			amount.style.color = "black";
			needCheckDeposit = 1;
		}
		else {
			deposit.disabled = true;
			deposit.style.borderColor = "#FFFFFF";
			deposit.style.borderWidth = "0px";
			deposit.onclick = "";
			deposit.value = "";
			amount.style.color = "#FFFFFF";
			needCheckDeposit = 0;
		}
	}catch(e){
		
	}
}

function setPreSecurity(){
	try{
		if('${main.preSecurity}'=='1'){
			document.getElementById('preSecurity0').checked = true;
		}else if('${main.preSecurity}'=='0'){
			document.getElementById('preSecurity1').checked = true;
		}else{
			document.getElementById('preSecurity0').checked = false;
			document.getElementById('preSecurity1').checked = false;
		}
	}catch(e){
	}
}
//金额检查
function judgeFloat(obj,name){
	var v = obj.value;
	if(!Utils.isFloatValue(v)){
		showMsg(name+'必需为数字且大于 0');
		obj.focus();
		return false;
	}
	return true;
}

function examineModify(){
	if(needCheckDeposit&&!judgeFloat(Ext.getDom('deposit'),'保证金')){
		return false;
	}
}
function handlerActivate(tab){
	rule_ns_ds.load({params:{start:0,limit:5,recordId:Ext.getDom('recordhdinputname').value}});
}
</script>
</head>
<body>
<div id="div_headForm">
	<form id="headForm" action="" name="headForm">
		<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
			<tr>
				<td width="12%" align="right">申请时间：</td>
				<td width="21%" align="left">
					<!--div id="applyTimeDiv"></div-->
					<input type="text" name="applyTime" id="applyTime" value="${main.applyTime}" style="color: #909090;" readonly="readonly">
				</td>
				<td width="11%" align="right"><nobr>是否网上电子口岸支付：</nobr></td>
				<td width="22%" align="left">
				    <select name="isNetPay" id="isNetPay">
				      <option value="">请选择</option>
				      <option value="0">否</option>
				      <option value="1">是</option>
				    </select>
				    <script type="text/javascript">
				    $('isNetPay').value='${main.isNetPay}';
				    </script>
					<input name="financeNo" id="financeNo" type="hidden" value="${main.financeNo}"/>
				</td>
				<td width="12%" align="right">申请部门：</td>
				<td width="21%" >
					<input type="text" id="deptName" name="deptName" value="${main.deptName}" style="color: #909090;" readonly="readonly">
					<input type="hidden" id="deptId" name="deptId" value="${main.deptId}">
				</td>
			</tr>
			<tr>
				<td width="12%" align="right">申请人：</td>
				<td width="21%" align="left">
					<input type="text" id="creatorName" name="creatorName" value="${main.creatorName}" style="color: #909090;" readonly="readonly"></input>
					<input type="hidden" id="creator" name="creator" value="${main.creator}">
				</td>
				<td width="12%" align="right">付款形式：</td>
				<td width="21%" align="left">
					<div id="payMethodDiv"></div>
				</td>
				<td width="12%" align="right">${isTeller}付款银行</td>
				<td width="21%" align="left">
					<input name="payBank" id="payBank" type="text" size="20" tabindex="1" value="${main.payBank}" ${isNotTeller} maxlength="100"/>
				</td>
			</tr>
			<tr>
				<td width="12%" align="right">${isTeller}付款帐号</td>
				<td width="21%" align="left">
					<input name="payAccount" id="payAccount" type="text" size="20" tabindex="1" value="${main.payAccount}" ${isNotTeller} maxlength="100"/>
				</td>
				<c:if test="${main.payType!=2}">
				<td width="12%" align="right">${maturityDateCheck}到期日期</td>
				<td width="21%" align="left">
					<div id="maturityDateDiv"></div>
				</td>
				</c:if>
				<c:if test="${main.payType==2}">
				<td width="11%" align="right"><nobr>是否存代理代收代付：</nobr></td>
				<td width="22%" align="left">
				    <select name="isPayForAnother" id="isPayForAnother">
				      <option value="">请选择</option>
				      <option value="1">是</option>
				      <option value="0">否</option>
				    </select>
				    <script type="text/javascript">
				    $('isPayForAnother').value='${main.isPayForAnother}';
				    </script>
				 </td>
				</c:if>
				<td width="11%" align="right">立项号：</td>
				<td width="22%" align="left">
					<input name="projectNo" id="projectNo" type="text" size="8" tabindex="1" value="${main.projectNo}" readonly="readonly"/>
					
					<input type="hidden" value="${main.projectId }" name="projectId"/>
					<a href="#" onclick="viewProjectForm()">查看</a>
				</td>
			
			</tr>
			<tr>
				<td width="11%" align="right">品名：</td>
				<td width="22%" align="left">
					<input type="text" name="goodsName" id="goodsName" value="${main.goodsName}" size="18" readonly="readonly">
				</td>
				<td width="11%" align="right">数量：</td>
				<td width="22%" align="left">
					<input name="quantity" id="quantity" type="text" size="18" tabindex="1" value="${main.quantity}" readonly="readonly"/>
				</td>
				<td width="11%" align="right" colspan="2"></td>
			</tr>
			
			<script>
			function viewProjectForm(){
			        var projectId = Ext.getDom("projectId").value
			        if(projectId=='') {
			           showMsg('请选择立项信息！');
			           return ;
			        }
					top.ExtModalWindowUtil.show('查看立项申请','projectController.spr?action=modify&from=view&projectId='+projectId,'','',{width:800,height:500});
		    }
		    </script>
		</table>
	</form>
</div> <!-- //end div_headForm -->
<div id="div_innerPayContractInfogrid"></div>
<div id="div_history" class="x-hide-display"></div>
<div id="div_main" class="x-hide-display">
	<form id="mainForm" action="" name="mainForm">
		<input type="hidden" name="paymentId" id="paymentId" value="${main.paymentId}">
		<input type="hidden" name="creatorTime" id="creatorTime" value="${main.creatorTime}">
		<input type="hidden" name="payType" value="${main.payType}">
		<input type="hidden" name="tradeType" value="7">
		<input type="hidden" name="currency" value="${main.currency}">
		<input type="hidden" name="payTime" value="${main.payTime}">
		<input type="hidden" name="approvedTime" value="${main.approvedTime}">
		<input type="hidden" name="isAvailable" value="${main.isAvailable}">
		<input type="hidden" name="examineState" value="${main.examineState}">
${isNotCreateBank}
		
		<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
		
			<tr>
				<td width="11%" align="right">申请付款金额：</td>
				<td width="22%" align="left" colspan="1">
				    <%=PaymentContants.htmlInputMic((String)request.getAttribute("payValue"),".00","payValue") %>
					
					${main.currency}
				</td>
				<td width="11%" align="right">币别：</td>
				<td width="22%" align="left" colspan="3">
					<input name="openAccountBank" id="openAccountBank" type="text" size="20" tabindex="1" value="${main.currency}" style="color: #909090;" readonly="readonly" maxlength="100"/>
				</td>
			</tr>
		    <tr>
				<td width="12%" align="right" valign="middle">汇率：<font color="red">▲</font></td>
				<td width="21%" align="left" colspan="1">
					<input name="exchangeRate" id="exchangeRate" type="text" size="20" tabindex="1" value="${empty main.exchangeRate ?1.0:main.exchangeRate}" maxlength="20"/>
				</td>
				<td width="12%" align="right">仓储|货运|保险|报关协议：</td>
				<td width="21%" align="left" colspan="3">
					<input name="protocolNo" id="protocolNo" type="text" size="20" tabindex="1" value="${main.protocolNo}" readonly="readonly" />
					<input id="selectProtocolBtn" type="button" value="..." onclick="selectProtocolInfo()"/>
					<input type="hidden" value="${main.protocolId }" name="protocolId"/>
					<a href="#" onclick="viewProtocolForm()">查看</a>
					<script>
			        function viewProtocolForm(){
			        var protocolId = Ext.getDom("protocolId").value
			        if(protocolId=='') {
			           showMsg('请选择仓储协议信息！');
			           return ;
			        }
					top.ExtModalWindowUtil.show('查看仓储协议申请','/storageProtocolController.spr?action=view&infoId='+protocolId,'','',{width:800,height:500});
		    }
		    </script>
				</td>
			</tr>
			<tr>
				<td width="11%" align="right" valign="top">付款用途：</td>
				<td width="22%" align="left" colspan="5">
					<textarea cols="50" rows="2" id="payUse" name="payUse" style="color: #909090;" readonly="readonly">${main.payUse}</textarea>
				</td>
			</tr>
${isCreateBank}
			<tr>
				<td width="11%" align="right">收款单位：</td>
				<td width="22%" align="left">
					<input name="recBank" id="recBank" type="text" size="20" tabindex="1" value="${main.recBank}" style="color: #909090;" readonly="readonly" maxlength="100"/>
				    <input type="hidden" id="unitNo" name="unitNo" value="${main.unitNo}"/>
      	            <input type="hidden" id="unitType" name="unitType" value="${main.unitType}"/>
				</td>
				<td width="11%" align="right">收款单位开户银行：</td>
				<td width="22%" align="left" colspan="3">
					<input name="openAccountBank" id="openAccountBank" type="text" size="20" tabindex="1" value="${main.openAccountBank}" style="color: #909090;" readonly="readonly" maxlength="100"/>
				</td>
			</tr>
			<tr>
				<td width="11%" align="right">收款单位账户：</td>
				<td width="22%" align="left" colspan="5">
					<input name="openAccountNo" id="openAccountNo" type="text" size="60" tabindex="1" value="${main.openAccountNo}" style="color: #909090;" readonly="readonly" maxlength="100"/>
				</td>
			</tr>
			<tr>
				<td width="11%" align="right">${isTeller}付款人：</td>
				<td width="22%" align="left">
					<input name="payer" id="payer" type="text" size="20" tabindex="1" value="${main.payer}" ${isNotTeller} maxlength="50"/>
				</td>
				<td width="11%" align="right">${isTeller}付款日：</td>
				<td width="22%" align="left" colspan="3">
					<div id="payRealTimeDiv"></div>
				</td>
			</tr>
			<tr>
				<td width="11%" align="right"  valign="top">备注：</td>
				<td width="22%" align="left" colspan="5">
					<textarea cols="50" rows="2" id="cmd" name="cmd" style="color: #909090;" readonly="readonly">${main.cmd}</textarea>
				</td>
			</tr>
		</table>
	</form>
</div> <!-- end div_main -->
<div id="rule" class="x-hide-display" ></div>
<fiscxd:fileUpload divId="rule" recordId="${main.paymentId}" erasable="${!saveDisabled}"  increasable="${!saveDisabled}" 
 resourceId="2222" resourceName="33333" recordIdHiddenInputName="recordhdinputname"></fiscxd:fileUpload>
</body>
</html>
<fiscxd:workflow-taskHistory divId="div_history" businessRecordId="${main.paymentId}" width="750"></fiscxd:workflow-taskHistory>
<% 
if("1".equals(request.getParameter("payType"))){ %>
<fiscxd:dictionary width="130" divId="payMethodDiv" fieldName="payMethod" dictionaryName="BM_INNER_PAY_METHOD" selectedValue="${main.payMethod}" disable="true"></fiscxd:dictionary>
<%} else if("2".equals(request.getParameter("payType"))){%>
<fiscxd:dictionary divId="payMethodDiv" fieldName="payMethod" dictionaryName="BM_INNER_PAY_METHOD_UN" selectedValue="${main.payMethod}" disable="true"></fiscxd:dictionary>
<%} else if("3".equals(request.getParameter("payType"))){%>
<fiscxd:dictionary divId="payMethodDiv" fieldName="payMethod" dictionaryName="BM_INNER_PAY_METHOD_IMPORT" selectedValue="${main.payMethod}" disable="true"></fiscxd:dictionary>
<%}%>