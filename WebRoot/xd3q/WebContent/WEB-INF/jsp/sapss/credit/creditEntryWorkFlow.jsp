<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信用证开证审查登记表</title>
<style type="text/css">
.add {
	background-image: url(<%=request.getContextPath()%>/ images/ fam/
		add.gif ) !important;
}

.delete {
	background-image: url(<%=request.getContextPath()%>/ images/ fam/
		delete.gif ) !important;
}

.update {
	background-image: url(<%=request.getContextPath()%>/ images/ fam/
		refresh.gif ) !important;
}

.find {
	background-image: url(<%=request.getContextPath()%>/ images/ fam/
		find.png ) !important;
}
</style>
<script type="text/javascript">
//操作类别  
var operate = '${operate}';
//贸易类型
var tradeType = '${tradeType}';
////是否可以提交
//var submitHidden = '${submitHidden}'; 
////是否可以保存
//var saveDisabled = '${saveDisabled}';
var fileDisable = (operate=="" ||operate==null) ? true:false;
//工作流当前节点名称标识码
var taskType = '${taskType}';
 
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/images/fam/s.gif';
   	
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			collapsible: true,
			height:0,
			contentEl: 'div_basrForm'
		},{
			region:"center",
			layout:'fit',
			buttonAlign:'center',
			items:[{
				region:'center',
				xtype:'tabpanel',
				id:'infotype',
				name:'infotype',
				plain:true,
	            height:300,
	            autoScroll:'true',
	            activeTab: 0,
	            items:[{
	            	title:'信用证开证审查登记表',
	            	contentEl: 'div_main',
	            	id:'creditEntryInfo',
					name:'creditEntryInfo',
					autoScroll:'true',
	            	layout:'fit'
	            	},
	            	{
	                title: '信用证附件',
	                contentEl:'rule',
	                id:'fileEl', 
	                disabled:fileDisable,
	                listeners:{activate:handlerActivate}
	                }]
		       }]
		       }]
	});

   //开证日期
   	var createDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'createDate',
	    name:'createDate',
		width: 160,
		disabled:true,
	    readOnly:true,
		applyTo:'createDate'
   	});
    /* 	
   //付款日期
   var paymentDate = new Ext.form.DateField({
	  	format:'Y-m-d',
		id:'paymentDate',
	    name:'paymentDate',
		width: 160,
		disabled:true,
	    readOnly:true,
		applyTo:'paymentDate'
   	});
   	*/
    //validDate
   	var validDate = new Ext.form.DateField({
	  	format:'Y-m-d',
		id:'validDate',
	    name:'validDate',
		width: 160,
		disabled:false,
	    readOnly:true,
		applyTo:'validDate'
   	});

  //装期
   	var loadingPeriod = new Ext.form.DateField({
	  	format:'Ymd',
		id:'loadingPeriod',
	    name:'loadingPeriod',
		width: 130,
	    readOnly:true,
		applyTo:'loadingPeriod'
   	});
   	
   	//保证金日期 "bailDate"
    var bailDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'bailDate',
	    name:'bailDate',
		width: 160,
		disabled:false,
	    readOnly:true,
		applyTo:'bailDate'
   	});	
   	
    Ext.getDom('recordhdinputname').value='<%=request.getAttribute("creditId")%>';
    Ext.getDom('btnselectContractInfo').disabled = "true";

    //根据当前节点的名称进行控件各控件的可读可写状态
    if(taskType=="1") //资金部填写开征行
    {
      //Ext.getDom("createBank").readOnly = false;
      dict_div_createBank.disable(false);
    }
    else if(taskType=="5") //填写开证申请表及预保
    {
        Ext.getDom("btnselectContractInfo").disabled= false;
        Ext.getDom("request").readOnly = false;
        Ext.getDom("country").readOnly = false;
        Ext.getDom("paymentType").readOnly = false;
        Ext.getDom("benefit").readOnly = false;
        Ext.getDom("amount").readOnly = false;
        Ext.getDom("rate").readOnly = false;
        Ext.getDom("goods").readOnly = false;
        Ext.getDom("specification").readOnly = false;
        Ext.getDom("mark").readOnly = false;
        Ext.getDom("invoice").readOnly = false;
        Ext.getDom("billOfLading").readOnly = false;
        Ext.getDom("billOfInsurance").readOnly = false;
        Ext.getDom("billOfQuality").readOnly = false;
        Ext.getDom("benefitCertification").readOnly = false;
        Ext.getDom("certificateOfOrigin").readOnly = false;
        Ext.getDom("packingSlip").readOnly = false;
        Ext.getDom("dispatchElectric").readOnly = false;
        Ext.getDom("packingSlip").readOnly = false;
        Ext.getDom("otherDocuments").readOnly = false;
        Ext.getDom("portOfShipment").readOnly = false;
        Ext.getDom("portOfDestination").readOnly = false;
        Ext.getDom("loadingPeriod").readOnly = false;
        Ext.getDom("place").readOnly = false;
        Ext.getDom("availDate").readOnly = false;
        //Ext.getDom("createBank").readOnly = false;
         
        dict_div_createBank.disable(false);
        var validDate1=Ext.getCmp("validDate");
        validDate1.setDisabled(false);
        
        Ext.getDom("specialConditions").readOnly = false;
        Ext.getDom("specialConditions").readOnly = false;

        Ext.getDom("transShipment").disabled= false;
        Ext.getDom("canBatches").disabled= false;
        Ext.getDom("preSecurity").disabled= false;
        
        Ext.getDom("creditInfo").readOnly = false;
        var bailDate1=Ext.getCmp("bailDate");
        bailDate1.setDisabled(false);
        
        dict_div_currency.disable(false);
    }
    else if(taskType=="2") //综合二部填写核销单
    {
      Ext.getDom("writeOffSingleNo").readOnly = false;
    }
    else if(taskType=="3") //外贸回填信用证号和开证日期 creditNo createDate
    {
      Ext.getDom("creditNo").readOnly = false;
      //Ext.getDom("createDate").disabled = false;
      var createDate1=Ext.getCmp("createDate");
      createDate1.setDisabled(false);
    }
    else if(taskType=="4") //财务人员审核
    {
      Ext.getDom("creditInfo").readOnly = false;
      var bailDate1=Ext.getCmp("bailDate");
      bailDate1.setDisabled(false);
    }
    
});//end of Ext.onReady   



//新增保存
function customCallBackHandle(transport)
{
  var responseUtil = new AjaxResponseUtils(transport.responseText);
  var result = responseUtil.getCustomField("coustom");
  
  Ext.getDom("creditid").value = result.creditid; //msgType[creditID]
  Ext.getDom('recordhdinputname').value=result.creditid;
  //alert(Ext.getDom("creditid").value);
  var msg = responseUtil.getMessage();
  var promptMessagebox = new MessageBoxUtil();
  promptMessagebox.show('info',msg);
  
}

//提示窗口
function showMsg(msg){
	top.Ext.Msg.show({
			title:'提示',
			closable:false,
			msg:msg,
			buttons:{yes:'关闭'},
			fn:Ext.emptyFn,
			icon:Ext.MessageBox.INFO
	});
}

//提交工作流审批
function submitWorkflow(){

	Ext.Msg.show({
   		title:'提示',
   		msg: '是否确定提交流程审批 ',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			if(buttonId=='yes'){
   			
				new AjaxEngine('creditEntryController.spr?action=submitWorkflow&doWorkflow=mainForm',
					 {method:"post", parameters: Form.serialize('mainForm'), onComplete: submitWorkflowCallBackHandle});
			
   			}
   		},
   		icon: Ext.MessageBox.QUESTION
	});
}	

//关闭窗体
function closeForm(){
	Ext.Msg.show({
   		title:'提示',
   		msg: '是否退出并关闭窗口',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
 			if(buttonId=='yes'){
 				_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
				top.ExtModalWindowUtil.markUpdated();
				top.ExtModalWindowUtil.close();
			}
   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.QUESTION
	});
}

//清空窗口
function resetForm(form){
	Ext.Msg.show({
   		title:'提示',
   		msg: '是否清空窗口',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			if(buttonId=='yes'){
   				form.reset()
   			}
   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.QUESTION
	});
}

//合同选择窗体
function selectContractInfo()
{
    var contractNo=  document.mainForm.contractNo.value;
    //alert(contractNo);
    //orderState=3 为审核通过的采购订单合同
 	top.ExtModalWindowUtil.show('查询所属登陆员工部门的合同信息',
	'creditEntryController.spr?action=selectContractInfo&tradeType=' + ${tradeType} +'&deptid=&orderState=3&contractType=P&contractNo='+ contractNo,
	'',
	selectContractInfoCallBack,
	{width:880,height:500});	
}

//合同选择窗体 回调函数。
function selectContractInfoCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    var contractNo = "";
    var sapOrderNo = "";
    var projectNo = "";
    var projectName = "";
    var contractPurchaseId =""; //合同ID CONTRACT_PURCHASE_ID 
     var ymatGroup = "";//物料组
    if(returnvalue.length >0)
    {
        for(i=0;i<=returnvalue.length -1;i++)
        {
          if(returnvalue[i].data.CONTRACT_NO != null && returnvalue[i].data.CONTRACT_NO != "")
          {
         	 contractNo = contractNo + returnvalue[i].data.CONTRACT_NO + ",";
          }
          else
          {
         	 contractNo = contractNo + "" + ",";
          }
          //采购合同ID
          if(returnvalue[i].data.CONTRACT_PURCHASE_ID != null && returnvalue[i].data.CONTRACT_PURCHASE_ID != "")
          {
         	 contractPurchaseId = contractPurchaseId + returnvalue[i].data.CONTRACT_PURCHASE_ID + ",";
          }
          else
          {
         	 contractPurchaseId = contractPurchaseId + "" + ",";
          }
          
          if(returnvalue[i].data.SAP_ORDER_NO != null && returnvalue[i].data.SAP_ORDER_NO != "")
          {
         	sapOrderNo = sapOrderNo + returnvalue[i].data.SAP_ORDER_NO + ",";
          }
          else if(returnvalue.length==1)
          {
            sapOrderNo = "";
          }
          else
          {
             sapOrderNo = sapOrderNo + "" + ",";
          }
          
          if(returnvalue[i].data.PROJECT_NO != null && returnvalue[i].data.PROJECT_NO != "")
          {
         	 projectNo = projectNo + returnvalue[i].data.PROJECT_NO + "," ;
          }
          else if(returnvalue.length==1)
          {
            projectNo = "";
          }
          else
          {
             projectNo = projectNo + "" + "," ;
          }
          
          if(returnvalue[i].data.PROJECT_NAME != null && returnvalue[i].data.PROJECT_NAME != "")
          {
         	 	projectName = projectName + returnvalue[i].data.PROJECT_NAME+ ",";
          }
          else if(returnvalue.length==1)
          {
            projectName = "";
          }
          else
          {
             projectName = projectName + "" + ",";
          }
          if(returnvalue[i].data.YMAT_GROUP != null && returnvalue[i].data.YMAT_GROUP != "")
          {
         	 ymatGroup = ymatGroup + returnvalue[i].data.YMAT_GROUP + ",";
          }
          else
          {
         	 ymatGroup = ymatGroup + "" + ",";
          }
        }
        
        if(contractPurchaseId.length>0)
          contractPurchaseId = contractPurchaseId.substr(0,contractPurchaseId.length-1);
        if(contractNo.length>0)
          contractNo = contractNo.substr(0,contractNo.length-1);
        if(sapOrderNo.length>0)
          sapOrderNo = sapOrderNo.substr(0,sapOrderNo.length-1);
        if(projectNo.length>0)
          projectNo = projectNo.substr(0,projectNo.length-1);
        if(projectName.length>0)
          projectName = projectName.substr(0,projectName.length-1); 
         if(ymatGroup.length>0)
          ymatGroup = ymatGroup.substr(0,ymatGroup.length-1);   
    }
	dict_div_ymatGroup.setComboValue(ymatGroup);
    document.mainForm.contractPurchaseId.value = contractPurchaseId;
	document.mainForm.contractId.value = contractPurchaseId;
    document.mainForm.contractNo.value = contractNo;
	document.mainForm.sapOrderNo.value = sapOrderNo;
 	document.mainForm.projectNo.value = projectNo;
	document.mainForm.projectName.value = projectName;
}

//提交审批流程回调函数
function submitWorkflowCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
//	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	showMsg(customField.returnMessage);
	setTimeout(function(){
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	},1000);	
}

function handlerActivate(tab){
	rule_ns_ds.load({params:{start:0,limit:5,recordId:Ext.getDom('recordhdinputname').value}});
} 
function viewPurchaseForm(){
	        var contractPurchaseId = Ext.getDom("contractId").value
	        if(contractPurchaseId=='') {
	           showMsg('请选择采购合同信息！');
	           return ;
	        }
	        top.ExtModalWindowUtil.show('采购合同信息','contractController.spr?action=ArchPurchaseInfoView&businessRecordId='+contractPurchaseId,'','',{width:900,height:550});
       }
</script>
</head>
<body>
<div id='div_progressBar'></div>
<div id="div_basrForm">
<div id="div_main" class="x-hide-display">
<form id="mainForm" action="" name="mainForm">
<table width="90%" border="1" cellpadding="4" cellspacing="1"
	bordercolor="#6699FF" class="datatable">
	<tr>
		<td width="10%" align="right"><font color="red">*</font>合同号：</td>
		<td width="15%" align="left"><nobr><input name="contractNo" type="text"
			size="10" tabindex="2" value="${main.contractNo}" readonly="readonly" />
		<input type="button" value="..." id="btnselectContractInfo"
			name="btnselectContractInfo" onclick="selectContractInfo()" >
		<input name="contractPurchaseId" type="hidden" />
		<input name="contractId" type="hidden"  value="${main.contractId}"/>
		<a href="#" onclick="viewPurchaseForm()">查看</a></nobr>
		</td>
		<td width="10%" align="right"><nobr>SAP订单号：</nobr></td>
		<td width="15%" align="left"><input name="sapOrderNo" type="text"
			size="15" tabindex="2" value="${main.sapOrderNo}" readonly="readonly" /></td>
		<td width="10%" align="right">立项号：</td>
		<td width="15%" align="left"><input name="projectNo" type="text"
			size="15" tabindex="2" value="${main.projectNo}" readonly="readonly" /></td>
		<td width="10%" align="right"><nobr>立项名称：</nobr></td>
		<td width="15%" align="left"><input name="projectName"
			type="text" size="15" tabindex="2" value="${main.projectName}"
			readonly="readonly" /></td>
	</tr>
	<tr>
		<td width="11%" align="right">申请人：</td>
		<td width="22%" align="left"><input name="request" type="text"
			size="15" tabindex="1" value="${main.request}" readonly="readonly" />
		</td>
		<td width="11%" align="right">信用证号：</td>
		<td width="22%" align="left"><input name="creditNo" type="text"
			size="15" tabindex="1" value="${main.creditNo}" title="信用证号要等最后才输入！" readonly="readonly" /></td>
		<td width="11%" align="right"><nobr>开证日期：</nobr></td>
		<td width="22%" align="left"><input name="createDate" type="text"
			size="15" tabindex="1" value="${main.createDate}" readonly="readonly" /></td>
		<td width="11%" align="right">开证行：</td>
		<td width="22%" align="left"><div id="div_createBank"></div></td>

	</tr>
	<tr>

		<td width="11%" align="right"><nobr>国别/地区：</nobr></td>
		<td width="22%" align="left"><input name="country" type="text"
			size="15" tabindex="1" value="${main.country}" readonly="readonly" /></td>
		<td width="11%" align="right">付款方式：</td>
		<td width="22%" align="left"><input name="paymentType"
			type="text" size="15" tabindex="1" value="${main.paymentType}"  readonly="readonly" /></td>
		<td width="11%" align="right">受益人：</td>
		<td width="22%" align="left" colspan="3"><input name="benefit"
			type="text" size="53" tabindex="2" value="${main.benefit}" readonly="readonly" /></td>
	</tr>

	<tr>
		<td width="11%" align="right">币别：</td>
		<td width="22%" align="left">
		<div id="div_currency" id="div_currency"></div>
		</td>
		<td width="11%" align="right">金额：</td>
		<td width="22%" align="left">
		   <input name="amount" type="text" size="15" tabindex="1" value="${main.amount}" readonly="readonly" />
		</td>
		<td width="11%" align="right" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>汇率：</td>
		<td width="22%" align="left" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>
		   <input name="rate" type="text" size="15" tabindex="1" value="${main.rate}" />
		</td>
		<td width="11%" align="right" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>核销单号：</td>
		<td width="22%" align="left" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>
		  <input name="writeOffSingleNo" type="text" size="15" tabindex="1" value="${main.writeOffSingleNo}" readonly="readonly" />
		</td>

	</tr>
	<tr>

		<td width="11%" align="right">货物品名：</td>
		<td width="22%" align="left"><input name="goods" type="text"
			size="15" tabindex="1" value="${main.goods}" readonly="readonly" /></td>
		<td width="11%" align="right">规格：</td>
		<td width="22%" align="left"><input name="specification"
			type="text" size="15" tabindex="1" value="${main.specification}" readonly="readonly" />
		</td>
		<td width="11%" align="right" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>唛头：</td>
		<td width="22%" align="left" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>
		  <input name="mark" type="text" size="15" tabindex="1" value="${main.mark}" readonly="readonly" />
		</td>
		 <td width="11%" align="right">期限：</td>
	      <td width="22%" align="left">
			 <input name=availDate type="text" size="15" tabindex="31" value="${main.availDate}" readonly="readonly" />
	      </td>  
	</tr>
	<tr>
		<td width="11%" align="right">发票：</td>
		<td width="22%" align="left"><input name="invoice" type="text"
			size="15" tabindex="1" value="${main.invoice}" readonly="readonly" /></td>
		<td width="11%" align="right"><c:if test="${purTradeType=='1'}">提单：</c:if><c:if test="${purTradeType=='2'}">运输单据或货物收据：</c:if></td>
		<td width="22%" align="left"><input name="billOfLading"
			type="text" size="15" tabindex="1" value="${main.billOfLading}" readonly="readonly" /></td>
		<td width="11%" align="right">保险单：</td>
		<td width="22%" align="left"><input name="billOfInsurance"
			type="text" size="15" tabindex="1" value="${main.billOfInsurance}" readonly="readonly" />
		</td>
		<td width="11%" align="right"><nobr>品质(分析证)：</nobr></td>
		<td width="22%" align="left"><input name="billOfQuality"
			type="text" size="15" tabindex="1" value="${main.billOfQuality}" readonly="readonly" />
		</td>
	</tr>
	<tr>
		<td width="11%" align="right"><nobr>受益人证明：</nobr></td>
		<td width="22%" align="left"><input name="benefitCertification"
			type="text" size="15" tabindex="1"
			value="${main.benefitCertification}" readonly="readonly" /></td>
		<td width="11%" align="right" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>产地证:</td>
		<td width="22%" align="left" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>
		  <input name="certificateOfOrigin"	type="text" size="15" tabindex="1" value="${main.certificateOfOrigin}" readonly="readonly" /></td>
		<td width="11%" align="right">装箱单：</td>
		<td width="22%" align="left"><input name="packingSlip"
			type="text" size="15" tabindex="1" value="${main.packingSlip}" readonly="readonly" /></td>
		<td width="11%" align="right" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>起运电：</td>
		<td width="22%" align="left" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>
		   <input name="dispatchElectric" type="text" size="15" tabindex="1" value="${main.dispatchElectric}" readonly="readonly" />
		</td>
	</tr>
	<tr>
		<td width="11%" align="right" valign="top">其他单据:</td>
		<td width="22%" align="left" colspan="7"><textarea cols="96"
			rows="5" name="otherDocuments" readonly="readonly" >${main.otherDocuments}</textarea></td>

	</tr>
	<tr>
		<td width="11%" align="right"><c:if test="${purTradeType=='1'}">装运港：</c:if><c:if test="${purTradeType=='2'}">装运地：</c:if></td>
		<td width="22%" align="left"><input name="portOfShipment"
			type="text" size="15" tabindex="1" value="${main.portOfShipment}"  readonly="readonly" />
		</td>
		<td width="11%" align="right"><c:if test="${purTradeType=='1'}">目的港：</c:if><c:if test="${purTradeType=='2'}">目的地：</c:if></td>
		<td width="22%" align="left"><input name="portOfDestination"
			type="text" size="15" tabindex="1" value="${main.portOfDestination}" readonly="readonly" />
		</td>
		<td width="11%" align="right">装期：</td>
		<td width="22%" align="left"><input name="loadingPeriod"
			type="text" size="15" tabindex="1" value="${main.loadingPeriod}" readonly="readonly" />
		</td>
		<td width="11%" align="right">有效期：</td>
		<td width="22%" align="left"><input name="validDate" type="text"
			size="15" tabindex="1" value="${main.validDate}" readonly="readonly" />
		</td>

	</tr>
	<tr>
		<td width="11%" align="right"><c:if test="${purTradeType=='1'}">可否转船：</c:if><c:if test="${purTradeType=='2'}">可否转运：</c:if></td>
		<td width="22%" align="left"><select id="transShipment"
			name="transShipment">
			<option value="">请选择</option>
			<option value="Y"
				<c:if test="${main.transShipment=='Y'}"> selected </c:if>>是</option>
			<option value="N"
				<c:if test="${main.transShipment=='N'}"> selected </c:if>>否</option>
		</select></td>
		<td width="11%" align="right">可否分批：</td>
		<td width="22%" align="left"><select id="canBatches"
			name="canBatches">
			<option value="">请选择</option>
			<option value="Y"
				<c:if test="${main.canBatches=='Y'}"> selected </c:if>>是</option>
			<option value="N"
				<c:if test="${main.canBatches=='N'}"> selected </c:if>>否</option>
		</select></td>
		<td width="11%" align="right">是否预保：</td>
		<td width="22%" align="left"><select id="preSecurity"
			name="preSecurity">
			<option value="">请选择</option>
			<option value="Y"
				<c:if test="${main.preSecurity=='Y'}"> selected </c:if>>是</option>
			<option value="N"
				<c:if test="${main.preSecurity=='N'}"> selected </c:if>>否</option>
		</select></td>
		<td width="11%" align="right">到期地点：</td>
		<td width="22%" align="left"><input name="place" type="text"
			size="15" tabindex="1" value="${main.place}" /></td>

	</tr>
	<tr>
		<td width="11%" align="right" valign="top">特别条款：</td>
		<td width="88%" align="left" colspan="7"><textarea cols="96"
			rows="8" id="specialConditions" name="specialConditions" readonly="readonly" >${main.specialConditions}</textarea>
		</td>
	</tr>
	<tr>
		<td width="11%" align="right">收保证金：</td>
		<td width="88%" align="left" >
			<input type="text" size="30"  name="creditInfo" value="${main.creditInfo}" readonly="readonly" />
		</td>
	    <td width="11%" align="right"><nobr>保证金日期：</nobr></td>
		<td width="22%" align="left" >
			<input name="bailDate" type="text" size="15" tabindex="1" value="${main.bailDate}" readonly="readonly" />
		</td> 
		<td align="right" width="11%">	
		<font color="red">*</font>物料组:
		</td>
		<td width="22%" >	
			<div id="div_ymatGroup"></div>
		</td> 
		<td align="right" colspan="4">	</td>	 	
	</tr>
</table>

<div id="div_CreditEntryInfo"></div>
<input name="tradeType" type="hidden" size="20"
	value="${main.tradeType}" /> <input name="createOrRec" type="hidden"
	size="20" value="${main.createOrRec}" /> <input name="deptId"
	type="hidden" size="20" value="${main.deptId}" /> <input
	name="creditId" type="hidden" size="40" value="${creditId}" /> <input
	name="creditState" type="hidden" size="40" value="${main.creditState}" />
<input name="isAvailable" type="hidden" size="40"
	value="${main.isAvailable}" /> <input name="applyTime" type="hidden"
	size="40" value="${main.applyTime}" /> <input name="approvedTime"
	type="hidden" size="40" value="${main.approvedTime}" /> <input
	name="creatorTime" type="hidden" size="40" value="${main.creatorTime}" />
<input name="creator" type="hidden" size="40" value="${main.creator}" />

</form>
</div>
</div>

<div id="rule" class="x-hide-display"></div>
</body>
</html>
<fiscxd:fileUpload divId="rule" erasable="true"
	increasable="true" resourceId="CREDITENTRY"
	resourceName="CREDITENTRY" recordId="${creditId}"
	recordIdHiddenInputName="recordhdinputname"></fiscxd:fileUpload>

<fiscxd:dictionary divId="div_currency" fieldName="currency"
	dictionaryName="bm_currency" width="150"
	selectedValue="${main.currency}" disable="true">
</fiscxd:dictionary>


<fiscxd:dictionary divId="div_ymatGroup" fieldName="ymatGroup" dictionaryName="YMATGROUP" width="150" selectedValue="${main.ymatGroup}" disable="false" ></fiscxd:dictionary>
<fiscxd:dictionary divId="div_createBank" fieldName="createBank" dictionaryName="BM_BANK_INFO" width="150" selectedValue="${main.createBank}" disable="true"></fiscxd:dictionary>
