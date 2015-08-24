<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信用证到证审查登记表</title>
<style type="text/css">
.add{
	background-image:url(<%= request.getContextPath()%>/images/fam/add.gif) !important;
}
.delete{
	background-image:url(<%= request.getContextPath()%>/images/fam/delete.gif) !important;
}
.update{
	background-image:url(<%= request.getContextPath()%>/images/fam/refresh.gif) !important;
}
.find{
	background-image:url(<%= request.getContextPath()%>/images/fam/find.png) !important;
}
</style>
<script type="text/javascript">
//操作类别  
var operate = '${operate}';
//贸易类型
var tradeType = '${tradeType}';
//是否可以提交
var submitHidden = '${submitHidden}'; 
//是否可以保存
var saveDisabled = '${saveDisabled}';
var fileDisable = (operate=="" ||operate==null) ? true:false;
var viewHistory = true; 
if(operate =="view" )
{
    viewHistory = false; //Ext.getCmp('historyEl').disabled = false;
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
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
	            	title:'信用证到证审查登记表',
	            	contentEl: 'div_main',
	            	id:'creditArriveInfo',
					name:'creditArriveInfo',
					autoScroll:'true',
	            	layout:'fit'
	               	},
	            	{
	                title: '信用证附件',
	                contentEl:'rule',
	                id:'fileEl', 
	                disabled:fileDisable,
	                listeners:{activate:handlerActivate}
	                },
	                {
	               	 contentEl:'history',
	               	 id:'historyEl', 
	               	 title: '审批历史记录',
	               	 disabled:viewHistory
	                }]
		       }],
				buttons:[{
	            	text:'保存',
	            	id:'btn_Save',
	            	name:'btn_Save',
	            	disabled :${saveDisabled},
	            	handler:function(){
	            		var materialOrg=dict_div_ymatGroup.getActualValue();			
						if(materialOrg==""){
							top.ExtModalWindowUtil.alert('提示','请选择物料组！');
							return;
						}
		            	var creditNo1 = Ext.getDom("creditNo").value;
	    				if (creditNo1 == '' || creditNo1 == null){
	    					top.ExtModalWindowUtil.alert('提示','[信用证号]为必填项！');
	    					return;				
	    				}
	    				var amount =Ext.getDom("amount").value; 
	    			    if(Utils.isEmpty(amount)==false && Utils.isFloatValue(amount) == false  )
	    			    {
	    			    	top.ExtModalWindowUtil.alert('错误','[金额]必须为数字！');
		    			    return;
	    			    }
	    				var rate =Ext.getDom("rate").value;
	    			    if(Utils.isEmpty(rate)==false && Utils.isFloatValue(rate) == false)
	    			    {
	    			    	top.ExtModalWindowUtil.alert('错误','[汇率]必须为数字！');
		    			    return;
	    			    }
	    			    var projectNo =Ext.getDom("projectNo").value;
	    			    if(projectNo == '' || projectNo == null)
	    			    {
	    			    	top.ExtModalWindowUtil.alert('错误','[立项号]为必填项！');
		    			    return;
	    			    }
	    			    var currency =dict_div_currency.getActualValue();
	    			    if(currency == '' || currency == null)
	    			    {
	    			    	top.ExtModalWindowUtil.alert('错误','[币别]为必填项！');
		    			    return;
	    			    }
	    			    var loadingPeriod =Ext.getDom("loadingPeriod").value;
	    			    if(loadingPeriod == '' || loadingPeriod == null)
	    			    {
	    			    	top.ExtModalWindowUtil.alert('错误','[装期]为必填项！');
		    			    return;
	    			    }
	    			    var validDate =Ext.getDom("validDate").value;
	    			    if(validDate == '' || validDate == null)
	    			    {
	    			    	top.ExtModalWindowUtil.alert('错误','[效期]为必填项！');
		    			    return;
	    			    }
	    			    var availDate =Ext.getDom("availDate").value;
	    			    if(availDate == '' || availDate == null)
	    			    {
	    			    	top.ExtModalWindowUtil.alert('错误','[期限]为必填项！');
		    			    return;
	    			    }
	    			    var portOfShipment =Ext.getDom("portOfShipment").value;
	    			    if(portOfShipment == '' || portOfShipment == null)
	    			    {
	    			    	top.ExtModalWindowUtil.alert('错误','[装运港]为必填项！');
		    			    return;
	    			    }
	    			    var portOfDestination =Ext.getDom("portOfDestination").value;
	    			    if(portOfDestination == '' || portOfDestination == null)
	    			    {
	    			    	top.ExtModalWindowUtil.alert('错误','[目的港]为必填项！');
		    			    return;
	    			    }
	    			    var place =Ext.getDom("place").value;
	    			    if(place == '' || place == null)
	    			    {
	    			    	top.ExtModalWindowUtil.alert('错误','[到期地点]为必填项！');
		    			    return;
	    			    }
	    			     var customCreateDate =Ext.getDom("customCreateDate").value;
	    			    if(customCreateDate == '' || customCreateDate == null)
	    			    {
	    			    	top.ExtModalWindowUtil.alert('错误','[开证日期]为必填项！');
		    			    return;
	    			    }
	    			     var creditRecDate =Ext.getDom("creditRecDate").value;
	    			    if(creditRecDate == '' || creditRecDate == null)
	    			    {
	    			    	top.ExtModalWindowUtil.alert('错误','[到证日期]为必填项！');
		    			    return;
	    			    }
	    			    
	            		var param1 = Form.serialize('mainForm');
						var param = param1 + "&action=save&operate=" + operate;
						new AjaxEngine('creditArriveController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});
					}

	            },
	            {
	            	text:'提交',
	            	id:'btn_Submit',
	            	name:'btn_Submit',
	            	hidden: ${submitHidden},
	            	handler:submitWorkflow
	            },
	            {
	            	id:'btn_close',
	            	name:'btn_close',
	            	text:'关闭',
	            	handler:closeForm
	            }]
		       }]
	});

   //到证日期
   	var creditRecDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'creditRecDate',
	    name:'creditRecDate',
		width: 124,
	    readOnly:true,
		applyTo:'creditRecDate'
   	});  
   	 	
   	//客户开证日期
   	var customCreateDate = new Ext.form.DateField({
	  	format:'Y-m-d',
		id:'customCreateDate',
	    name:'customCreateDate',
		width: 124,
	    readOnly:true,
		applyTo:'customCreateDate'
   	});
   	
   	//validDate
   	var validDate = new Ext.form.DateField({
	  	format:'Y-m-d',
		id:'validDate',
	    name:'validDate',
		width: 124,
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
   	
   	if(operate != null || operate !="" )
    {
	   Ext.getDom('recordhdinputname').value='<%=request.getAttribute("creditId")%>';
    }

    Ext.getDom('lbCREDIT_STATE').style.display = "none";
    Ext.getDom('CREDIT_STATE').style.display = "none";
	    
     //如果是执行修改操作
    if(operate=='modify')
    {
    }
    else if(operate=='iframe')
    {
      var taskName = parent.document.getElementById("workflowCurrentTaskName").value;
      if(taskName=="贸管人员更改信用证状态")
      {
      	Ext.getDom('lbCREDIT_STATE').style.display = "";
      	Ext.getDom('CREDIT_STATE').style.display = "";
      }
      
        Ext.getDom('creditNo').readOnly = true;
		Ext.getDom('createBank').readOnly = true;
		Ext.getDom('country').readOnly = true;
		Ext.getDom('benefit').readOnly = true;
		Ext.getDom('request').readOnly = true;
		Ext.getDom('rate').readOnly = true;
		Ext.getDom('amount').readOnly = true;
		Ext.getDom('paymentType').readOnly = true;
		Ext.getDom('goods').readOnly = true;
		Ext.getDom('specification').readOnly = true;
		Ext.getDom('invoice').readOnly = true;
		Ext.getDom('billOfLading').readOnly = true;
		Ext.getDom('mark').readOnly = true;
		Ext.getDom('billOfInsurance').readOnly = true;
		Ext.getDom('billOfQuality').readOnly = true;
		Ext.getDom('benefitCertification').readOnly = true;
		Ext.getDom('certificateOfOrigin').readOnly = true;
		Ext.getDom('packingSlip').readOnly = true;
		Ext.getDom('electricShip').readOnly = true;
		Ext.getDom('loadingPeriod').readOnly = true;
		Ext.getDom('otherDocuments').readOnly = true;
		Ext.getDom('place').readOnly = true;
		Ext.getDom('portOfShipment').readOnly = true;
		Ext.getDom('portOfDestination').readOnly = true;
		Ext.getDom('specialConditions').readOnly = true;
		Ext.getDom('billConditions').readOnly = true;

	    var customCreateDate1=Ext.getCmp("customCreateDate");
	    customCreateDate1.setDisabled(true);
	    var creditRecDate1=Ext.getCmp("creditRecDate");
	    creditRecDate1.setDisabled(true);
	    var validDate1=Ext.getCmp("validDate");
	    validDate1.setDisabled(true);
	    
	    Ext.getDom('canBatches').disabled = "disabled";
	    Ext.getDom('transShipment').disabled = "disabled";
	    Ext.getDom('canBatches').disabled = "disabled";

	    dict_div_currency.disable(true);
	    
        Ext.getDom('btn_Save').style.display = "none";
    	Ext.getDom('btn_Submit').style.display = "none";
    	Ext.getDom('btn_close').style.display = "none";

    	Ext.getDom('btnselectContractInfo').disabled = "true";
    	Ext.getDom('btnselectDept').disabled = "true";
    	Ext.getDom('btnselectProjectInfo').disabled = "true";
    }
    
    if(Ext.getDom('btn_Save').disabled== true)  //但无改证权限时候，则不能选择合同信息。
    {
       Ext.getDom('btnselectContractInfo').disabled = true;
      // Ext.getDom('btnselectContractInfo').style.display = "none";
    }
 
});//end of Ext.onReady   

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
//新增保存
function customCallBackHandle(transport)
{
  var materialOrg=dict_div_ymatGroup.getActualValue();			
  if(materialOrg==""){
	top.ExtModalWindowUtil.alert('提示','请选择物料组！');
	return;
  }
  var responseUtil = new AjaxResponseUtils(transport.responseText);
  var result = responseUtil.getCustomField("coustom");

  Ext.getDom("creditid").value = result.creditid; //msgType[creditID]
  Ext.getDom('recordhdinputname').value=result.creditid;
  
  var fileEl = Ext.getCmp("fileEl");
  //fileE1.setDisabled(false);
  fileEl.enable();
  
  var msg = responseUtil.getMessage();
  var promptMessagebox = new MessageBoxUtil();
  promptMessagebox.show('info',msg);
  
}

//提交工作流审批
function submitWorkflow(){

	Ext.Msg.show({
   		title:'提示',
   		msg: '是否确定提交流程审批 ',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			if(buttonId=='yes'){
   			
				new AjaxEngine('creditArriveController.spr?action=submitWorkflow&doWorkflow=mainForm',
					 {method:"post", parameters: Form.serialize('mainForm'), onComplete: submitWorkflowCallBackHandle});
			
   			}
   		},
   		icon: Ext.MessageBox.QUESTION
	});
}	

//立项选择窗体
function selectProjectInfo(){
	var deptid = Ext.getDom("deptid").value;
	top.ExtModalWindowUtil.show('查询立项信息',
	'creditArriveController.spr?action=selectProjrctInfo&tradeType=' + ${tradeType},
	'',
	selectProjectInfoCallBack,
	{width:800,height:400});
}

//立项选择回调函数
function selectProjectInfoCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	document.mainForm.projectName.value = returnvalue.PROJECT_NAME + "";
	document.mainForm.projectNo.value = returnvalue.PROJECT_NO + "";
	dict_div_ymatGroup.setComboValue(returnvalue.YMAT_GROUP);
}

//合同选择窗体
function selectContractInfo()
{
    var contractNo=  document.mainForm.contractNo.value;
    //orderState=3 为审核通过的采购订单合同
 	top.ExtModalWindowUtil.show('查询合同信息',
	'creditArriveController.spr?action=selectContractInfo&tradeType=' + ${tradeType} +'&deptid=&orderState=3&contractType=P&contractNo='+ contractNo,
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
    var contractSalesId =""; //合同ID CONTRACT_SALES_ID 
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
          
          //alert(returnvalue[i].data.CONTRACT_SALES_ID );
          //销售合同ID
          if(returnvalue[i].data.CONTRACT_SALES_ID != null && returnvalue[i].data.CONTRACT_SALES_ID != "")
          {
         	 contractSalesId = contractSalesId + returnvalue[i].data.CONTRACT_SALES_ID + ",";
          }
          else
          {
         	 contractSalesId = contractSalesId + "" + ",";
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
        
        if(contractSalesId.length>0)
          contractSalesId = contractSalesId.substr(0,contractSalesId.length-1);
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
    document.mainForm.contractSalesId.value = contractSalesId;
	document.mainForm.contractId.value = contractSalesId;
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
		//_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	},1000);	
}


//部门(受益人)选择窗体
function selectDeptInfo()
{
 	top.ExtModalWindowUtil.show('选择受益人',
	'creditArriveController.spr?action=selectDeptInfo',
	'',
	selectDeptInfoCallBack,
	{width:410,height:400});	
}

function selectDeptInfoCallBack()
{	
	 var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	 document.mainForm.benefit.value = returnvalue.DEPT_NAME ;
	 document.mainForm.deptId.value = returnvalue.DEPT_ID ;
}

function handlerActivate(tab){
	rule_ns_ds.load({params:{start:0,limit:5,recordId:Ext.getDom('recordhdinputname').value}});
} 
</script>
</head>
<body>
<div id='div_progressBar'></div>
<div id="div_basrForm">
<div id="div_main" class="x-hide-display">
<form id="mainForm" action="" name="mainForm">
	<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
	    <tr>
			<td width="10%" align="right">合同号：</td>
			<td width="15%" align="left"><input name="contractNo"
				type="text" size="12"  value="${main.contractNo}" readonly="readonly" /> 
				<input type="button" value="..." tabindex="1" id="btnselectContractInfo" name="btnselectContractInfo" onclick="selectContractInfo()">
				<input name="contractSalesId" type="hidden"/>
				<input name="contractId" type="hidden" value="${main.contractId}" />
			</td>
			<td width="10%" align="right"><nobr>SAP订单号：</nobr></td>
			<td width="15%" align="left"><input name="sapOrderNo"
				type="text" size="14" value="${main.sapOrderNo}" readonly="readonly" /></td>
			<td width="10%" align="right">立项号<font color="red">▲</font></td>
			<td width="15%" align="left"><input name="projectNo"
				type="text" size="12" tabindex="3" value="${main.projectNo}" readonly="readonly" />
				<input type="button" value="..." id="btnselectProjectInfo" name="btnselectProjectInfo" onclick="selectProjectInfo()"></td>
			<td width="10%" align="right"><nobr>立项名称：</nobr></td>
			<td width="15%" align="left"><input
				name="projectName" type="text" size="14"  value="${main.projectName}" readonly="readonly" /></td>
		</tr>
      <tr>
      <td width="11%" align="right">审证人：</td>
        <td width="22%" align="left">
			<input name="applyer" type="text" size="14"  value="${main.applyer}" readonly="readonly"/>
        </td>
        <td width="11%" align="right">信用证号<font color="red">▲</font></td>
        <td width="22%" align="left">
        	<input name="creditNo" type="text" size="14" tabindex="2" value="${main.creditNo}" />
        </td>
        <td width="11%" align="right"><nobr>客户开证日期<font color="red">▲</font></nobr></td>
        <td width="22%" align="left">
        	<input name="customCreateDate" type="text" size="14" tabindex="3" value="${main.customCreateDate}" readonly="readonly" />
        </td>
        <td width="11%" align="right">到证日期<font color="red">▲</font></td>
        <td width="22%" align="left">
            <input name="creditRecDate" type="text" size="14" tabindex="4" value="${main.creditRecDate}" readonly="readonly" />
        </td>
        
      </tr>
      <tr>
        <td width="11%" align="right"><nobr>国别/地区：</nobr></td>
        <td width="22%" align="left">
			<input name="country" type="text" size="14" tabindex="5" value="${main.country}" />
        </td>
        <td width="11%" align="right">付款方式：</td>
        <td width="22%" align="left">
			<input name="paymentType" type="text" size="14" tabindex="6" value="${main.paymentType}" />
        </td>
        <td width="11%" align="right">开证行：</td>
		<td width="22%" align="left">
			<input name="createBank" type="text" size="14" tabindex="7" value="${main.createBank}" />
		</td>
		
        <td width="11%" align="right">
        <label id="lbCREDIT_STATE">信用证状态：</label>
        </td>
        <td width="22%" align="left">
            <select id="CREDIT_STATE" name="CREDIT_STATE" tabindex="8">
				<option value="">请选择</option>
				<option value="5">备用</option>
				<option value="7">撤销</option>
				<option value="8">关闭</option>
				<option value="11">改证通过</option>
			</select>
        </td>
      </tr>
     <tr>
     
        <td width="11%" align="right">币别<font color="red">▲</font></td>
        <td width="22%" align="left">
            <div id="div_currency" name="div_currency"></div>
        </td>
        <td width="11%" align="right">金额<font color="red">▲</font></td>
        <td width="22%" align="left">
			<input name="amount" type="text" size="14" tabindex="9" value="${main.amount}" />
        </td>
        <td width="11%" align="right" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>汇率<font color="red">▲</font></td>
        <td width="22%" align="left" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>
            <input name="rate" type="text" size="14" tabindex="10" value="${main.rate}" />
        </td>
        <td width="11%" align="right">受益人<font color="red">▲</font></td>
        <td width="22%" align="left">
			<input name="benefit" type="text" size="14" tabindex="11" value="${main.benefit}" readonly="readonly"/><input type="button" value="..." name="btnselectDept" title="选择部门" onclick="selectDeptInfo()">
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">开证申请人：</td>
        <td width="22%" align="left">
			<input name="request" type="text" size="14" tabindex="12" value="${main.request}"/>
        </td>
        <td width="11%" align="right">货物品名：</td>
        <td width="22%" align="left">
			<input name="goods" type="text" size="14" tabindex="13" value="${main.goods}" />
        </td>
        <td width="11%" align="right" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>规格：</td>
        <td width="22%" align="left" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>
			<input name="specification" type="text" size="14" tabindex="14" value="${main.specification}" />
        </td>
        <td width="11%" align="right" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>唛头：</td>
        <td width="22%" align="left" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>
        	<input name="mark" type="text" size="14" tabindex="15" value="${main.mark}"/>
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">发票：</td>
        <td width="22%" align="left">
			<input name="invoice" type="text" size="14" tabindex="16" value="${main.invoice}" />
        </td>
        <td width="11%" align="right"><c:if test="${saleTradeType=='2'}">运输单据/货物收据：</c:if><c:if test="${saleTradeType=='3'}">提单：</c:if></td>
        <td width="22%" align="left">
			<input name="billOfLading" type="text" size="14" tabindex="17" value="${main.billOfLading}" />
        </td>
        <td width="11%" align="right" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>保险单：</td>
        <td width="22%" align="left" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>
			<input name="billOfInsurance" type="text" size="14" tabindex="18" value="${main.billOfInsurance}" />
        </td>
        <td width="11%" align="right" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>><nobr>品质(分析证)：</nobr></td>
        <td width="22%" align="left" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>
			<input name="billOfQuality" type="text" size="14" tabindex="19" value="${main.billOfQuality}" />
        </td>
      </tr>
      <tr>
        
        <td width="11%" align="right"><nobr>受益人证明：</nobr></td>
        <td width="22%" align="left">
			<input name="benefitCertification" type="text" size="14" tabindex="20" value="${main.benefitCertification}" />
        </td>
        <td width="11%" align="right" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>产地证：</td>
        <td width="22%" align="left" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>
			<input name="certificateOfOrigin" type="text" size="14" tabindex="21" value="${main.certificateOfOrigin}" />
        </td>
        <td width="11%" align="right" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>装箱单：</td>
        <td width="22%" align="left" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>
			<input name="packingSlip" type="text" size="14" tabindex="22" value="${main.packingSlip}" />
        </td>
        <td width="11%" align="right" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>装船电：</td>
        <td width="22%" align="left" <c:if test="${saleTradeType=='2'}">style='visibility: hidden'</c:if>>
			<input name="electricShip" type="text" size="14" tabindex="23" value="${main.electricShip}" />
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">其他单据：</td>
        <td width="22%" align="left" colspan="5">
			<input name="otherDocuments" type="text" size="89" tabindex="24" value="${main.otherDocuments}" />
        </td>
        <td align="right" width="11%">物料组<font color="red">▲</font>
		</td>
		<td width="22%" >	
			<div id="div_ymatGroup"></div>
		</td> 
      </tr>
      <tr>
        <td width="11%" align="right">装期<font color="red">▲</font></td>
        <td width="22%" align="left">
			<input name="loadingPeriod" type="text" size="14" tabindex="25" value="${main.loadingPeriod}" />
        </td>
        <td width="11%" align="right"><c:if test="${saleTradeType=='2'}">装运地</c:if><c:if test="${saleTradeType=='3'}">装运港</c:if><font color="red">▲</font></td>
        <td width="22%" align="left">
			<input name="portOfShipment" type="text" size="14" tabindex="26" value="${main.portOfShipment}" />
        </td>
        <td width="11%" align="right"><c:if test="${saleTradeType=='2'}">目的地</c:if><c:if test="${saleTradeType=='3'}">目的港</c:if><font color="red">▲</font></td>
        <td width="22%" align="left">
			<input name="portOfDestination"	type="text" size="14" tabindex="27" value="${main.portOfDestination}" />
        </td>
        <td width="11%" align="right">有效期<font color="red">▲</font></td>
        <td width="22%" align="left">
            <input name="validDate" type="text" size="14" tabindex="28" value="${main.validDate}" readonly="readonly" />
        </td>
      </tr>
      <tr>
      
      	  
	      <td width="11%" align="right">可否分批：</td>
		<td width="22%" align="left">
			<select id="canBatches" name="canBatches" tabindex="29">
				<option value="">请选择</option>
				<option value="1"
					<c:if test="${main.canBatches=='1'}"> selected </c:if>>是</option>
				<option value="0"
					<c:if test="${main.canBatches=='0'}"> selected </c:if>>否</option>
			</select>
		</td>
	    <td width="11%" align="right"><c:if test="${saleTradeType=='2'}">可否转运</c:if><c:if test="${saleTradeType=='3'}">可否转船</c:if>：</td>
        <td width="22%" align="left">
			<select id="transShipment" name="transShipment" tabindex="30">
				<option value="">请选择</option>
				<option value="1"
					<c:if test="${main.transShipment=='1'}"> selected </c:if>>是</option>
				<option value="0"
					<c:if test="${main.transShipment=='0'}"> selected </c:if>>否</option>
			</select>
        </td>
        <td width="11%" align="right">期限<font color="red">▲</font></td>
	      <td width="22%" align="left">
			 <input name=availDate type="text" size="14" tabindex="31" value="${main.availDate}" />
	      </td>
        <td width="11%" align="right">到期地点<font color="red">▲</font></td>
	      <td width="22%" align="left">
			 <input name="place" type="text" size="14" tabindex="31" value="${main.place}" />
	      </td>
      </tr>
     <tr>
        <td width="11%" align="right" valign="top">特别条款：</td>
        <td width="88%" align="left" colspan="7">
        	<textarea cols="97" rows="10" id="specialConditions" tabindex="32" name="specialConditions">${main.specialConditions}</textarea>
        </td>
     </tr>
    <tr>
		<td width="11%" align="right" valign="top"><nobr>应修改事项：</nobr></td>
		<td width="88%" align="left" colspan="7">
        	<textarea cols="97" rows="3" id="billConditions" name="billConditions" tabindex="33" >${main.billConditions}</textarea>
		</td>
    </tr>

	</table>

<div id="div_CreditArriveInfo"></div>
<input name="tradeType" type="hidden" size="14" value="${main.tradeType}" /> 
<input name="createOrRec" type="hidden" size="14" value="${main.createOrRec}" /> 
<input name="deptId" type="hidden" size="14" value="${main.deptId}" /> 
<input name="creditId" type="hidden" size="40" value="${creditId}" />
<input name="creditState" type="hidden" size="40" value="${main.creditState}" />
<input name="isAvailable" type="hidden" size="40" value="${main.isAvailable}" />
<input name="applyTime" type="hidden" size="40" value="${main.applyTime}" />
<input name="approvedTime" type="hidden" size="40" value="${main.approvedTime}" /> 
<input name="creatorTime" type="hidden" size="40" value="${main.creatorTime}" />
<input name="creator" type="hidden" size="40" value="${main.creator}" />
</form>
</div>
</div>
<div id="rule" class="x-hide-display" ></div>
<div id="history" class="x-hide-display" ></div>
</body>
</html>
<fiscxd:fileUpload divId="rule" erasable="${revisable}"  increasable="${revisable}"
 resourceId="CREDITARRIVE" resourceName="CREDITARRIVE" recordId="${creditId}" recordIdHiddenInputName="recordhdinputname"></fiscxd:fileUpload>
<fiscxd:dictionary divId="div_currency" fieldName="currency" dictionaryName="bm_currency" 
width="123" selectedValue="${main.currency}" disable="false" needDisplayCode="true">
</fiscxd:dictionary>
<fiscxd:workflow-taskHistory divId="history" businessRecordId="${creditId}" width="750"></fiscxd:workflow-taskHistory>    

<fiscxd:dictionary divId="div_ymatGroup" fieldName="ymatGroup" dictionaryName="YMATGROUP" width="150" selectedValue="${main.ymatGroup}" disable="false" ></fiscxd:dictionary>
