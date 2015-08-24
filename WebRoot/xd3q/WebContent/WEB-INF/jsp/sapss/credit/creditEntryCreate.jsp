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
	background-image: url(<%= request.getContextPath() %>/images/fam/add.gif ) !important;
}

.delete {
	background-image: url(<%= request.getContextPath() %>/images/fam/delete.gif ) !important;
}

.update {
	background-image: url(<%= request.getContextPath() %>/images/fam/refresh.gif ) !important;
}

.find {
	background-image: url(<%= request.getContextPath() %>/images/fam/find.png ) !important;
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
	            	disabled:${saveDisabled},
	            	handler:function(){
	            		var materialOrg=dict_div_ymatGroup.getActualValue();			
						if(materialOrg==""){
							top.ExtModalWindowUtil.alert('提示','请选择物料组！');
							return;
						}
	    				var amount =Ext.getDom("amount").value; 
	    			    if(Utils.isEmpty(amount)==false && Utils.isFloatValue(amount) == false  )
	    			    {
	    			    	top.ExtModalWindowUtil.alert('错误','[金额]必须为数字！');
		    			    return;
	    			    }
	    			    if(amount<=0)
	    			    {
	    			    	top.ExtModalWindowUtil.alert('错误','[金额]必须大于0！');
		    			    return;
	    			    }
	    				var rate =Ext.getDom("rate").value;
	    			    if(Utils.isEmpty(rate)==false && Utils.isFloatValue(rate) == false)
	    			    {
	    			    	top.ExtModalWindowUtil.alert('错误','[汇率]必须为数字！');
		    			    return;
	    			    }
	    			    if(Utils.isEmpty(Ext.getDom("availDate").value))
	    			    {
	    			    	top.ExtModalWindowUtil.alert('错误','期限必须填写！');
		    			    return;
	    			    }
	    			    
	            		var param1 = Form.serialize('mainForm');
						var param = param1 + "&action=save";
						new AjaxEngine('creditEntryController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});
					}

	            },{
	            	text:'保存信用证状态',
	            	hidden: ${!CHANGE_CREDIT_STATUS},
	            	handler:saveCreditStatus
	            },
	            {
	            	text:'提交',
	            	id:'btn_Submit',
	            	name:'btn_Submit',
	            	hidden:${submitHidden},
	            	handler:submitWorkflow
	            },
	            {
	            	text:'关闭',
	            	id:'btn_close',
	            	name:'btn_close',
	            	handler:closeForm
	            }]
		       }]
	});

   //开证日期
   	var createDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'createDate',
	    name:'createDate',
		width: 130,
	    readOnly:true,
		applyTo:'createDate'
   	});
   	
   	//保证金日期 "bailDate"
    var bailDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'bailDate',
	    name:'bailDate',
		width: 130,
	    readOnly:true,
		applyTo:'bailDate'
   	});	
   	
    //validDate
   	var validDate = new Ext.form.DateField({
	  	format:'Y-m-d',
		id:'validDate',
	    name:'validDate',
		width: 130,
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
      
     //如果是执行修改操作 modify workflow iframe
    if(operate=='iframe')
    {
        Ext.getDom('btn_Save').style.display = "none";
    	Ext.getDom('btn_Submit').style.display = "none";
    	Ext.getDom('btn_close').style.display = "none";
    }
    
    if(Ext.getDom('btn_Save').disabled)  //但无改证权限时候，则不能选择合同信息。
    {
       Ext.getDom('btnselectContractInfo').disabled = "true";
      // Ext.getDom('btnselectContractInfo').style.display = "none";
    }
    if(${CHANGE_CREDIT_STATUS}){
		Ext.getDom('lbCREDIT_STATE').style.display = "";
  		Ext.getDom('CREDIT_STATE').style.display = "";
  		$('CREDIT_STATE').value='${main.creditState}';
//  		Ext.getDom('btn_Submit').style.display = "none";
    }else{
		Ext.getDom('lbCREDIT_STATE').style.display = "none";
  		Ext.getDom('CREDIT_STATE').style.display = "none";
    }
    
});//end of Ext.onReady   

//保存信用证状态
var saveCreditStatus=function(){
	var parm='?action=saveCreditStatus&creditId=${creditId}&creditStatus='+$('CREDIT_STATE').value;
	new AjaxEngine('creditEntryController.spr',
			 {method:"post", parameters:parm, onComplete:callBackHandle});
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
  
  Ext.getDom("creditid").value = result.creditid;
  Ext.getDom('recordhdinputname').value=result.creditid;
                                        
  var fileEl = Ext.getCmp("fileEl");
  fileEl.enable();
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
 				//_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
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
	'creditEntryController.spr?action=selectContractInfo&tradeType=' + ${tradeType} +'&deptid=&orderState=3,5,7,8,9&contractType=P&contractNo='+ contractNo,
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
		//_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	},1000);	
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
		<td width="10%" align="right"><font color="red">*</font>合同号：</td>
		<td width="15%" align="left"><input name="contractNo"
			type="text" size="12" tabindex="2" value="${main.contractNo}" readonly="readonly" />
			 <input type="button" value="..." id="btnselectContractInfo" name="btnselectContractInfo" onclick="selectContractInfo()" tabindex="1">
			<input name="contractPurchaseId" type="hidden"/>
			<input name="contractId" type="hidden" value="${main.contractId}" />
			<a href="#" onclick="Utils.showPurcharseContract('${main.contractId}')">查看</a>
		</td>
		<td width="10%" align="right"><nobr>SAP订单号：</nobr></td>
		<td width="15%" align="left"><input name="sapOrderNo"
			type="text" size="15" tabindex="2" value="${main.sapOrderNo}"
			readonly="readonly" /></td>
		<td width="10%" align="right">立项号：</td>
		<td width="15%" align="left"><input name="projectNo"
			type="text" size="15" tabindex="2" value="${main.projectNo}"
			readonly="readonly" /></td>
		<td width="10%" align="right"><nobr>立项名称：</nobr></td>
		<td width="15%" align="left"><input
			name="projectName" type="text" size="15" 
			value="${main.projectName}" readonly="readonly" /></td>
	</tr>
	<tr>
	    <td width="11%" align="right">申请人：</td>
		<td width="22%" align="left">
			<input name="request" type="text" size="15"  value="${main.request}" readonly="readonly"/>
		</td>
		<td width="11%" align="right">信用证号：</td>
		<td width="22%" align="left"><input name="creditNo" type="text"
			size="15" tabindex="2" value="${main.creditNo}" title="信用证号要等最后才输入！"/></td>
		<td width="11%" align="right"><nobr>开证日期：</nobr></td>
		<td width="22%" align="left">
		<input name="createDate" type="text" size="15" tabindex="3"
			value="${main.createDate}" readonly="readonly" /></td>
		<td width="11%" align="right">开证行：</td>
		<td width="22%" align="left"><div id="div_createBank"></div></td>

	</tr>
	<tr>
	    
		<td width="11%" align="right"><nobr>国别/地区：</nobr></td>
		<td width="22%" align="left">
			<input name="country" type="text" size="15" tabindex="5" value="${main.country}" />
		</td>
		<td width="11%" align="right">付款方式：</td>
		<td width="22%" align="left">
			<input name="paymentType" type="text" size="15" tabindex="6" value="${main.paymentType}" />
		</td>
		<td width="11%" align="right">受益人：</td>
		<td width="22%" align="left" >
			<input name="benefit" type="text" size="15" tabindex="7" value="${main.benefit}" />
		</td>
		<td width="11%" align="right"><label id="lbCREDIT_STATE">信用证状态：</label></td>
		<td width="22%" align="left" >
            <select id="CREDIT_STATE" name="CREDIT_STATE">
				<option value="">请选择</option>
				<option value="5">备用</option>
				<option value="7">撤销</option>
				<option value="8">关闭</option>
				<option value="11">改证通过</option>
			</select>
		</td>
		
	</tr>

	<tr>
		<td width="11%" align="right">币别：</td>
		<td width="22%" align="left">
		 	<div id="div_currency" id="div_currency"></div>
		</td>
		<td width="11%" align="right">金额：</td>
		<td width="22%" align="left">
			<input name="amount" type="text" size="15" tabindex="8" value="${main.amount}" />
		</td>
		<td width="11%" align="right" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>汇率：</td>
		<td width="22%" align="left" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>
		 	<input name="rate" type="text" size="15" tabindex="9" value="${main.rate}" />
		</td>
		<td width="11%" align="right" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>核销单号：</td>
		<td width="22%" align="left" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>
		<input name="writeOffSingleNo" type="text" size="15" tabindex="10" value="${main.writeOffSingleNo}" />
		</td>
		
	</tr>
	<tr>
		<td width="11%" align="right">货物品名：</td>
		<td width="22%" align="left">
			<input name="goods" type="text" size="15" tabindex="11" value="${main.goods}" />
		</td>
		<td width="11%" align="right">规格：</td>
		<td width="22%" align="left">
			<input name="specification" type="text" size="15" tabindex="12" value="${main.specification}" />
		</td>
		<td width="11%" align="right" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>唛头：</td>
		<td width="22%" align="left" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>
		<input name="mark" type="text" size="15" tabindex="13" value="${main.mark}" /></td>
		 <td width="11%" align="right">期限：</td>
	      <td width="22%" align="left">
			 <input name=availDate type="text" size="15" tabindex="31" value="${main.availDate}" />
	      </td>  
	</tr>
	<tr>
		<td width="11%" align="right">发票：</td>
		<td width="22%" align="left">
			<input name="invoice" type="text" size="15" tabindex="14" value="${main.invoice}" />
		</td>
		<td width="11%" align="right"><c:if test="${purTradeType=='1'}">提单：</c:if><c:if test="${purTradeType=='2'}">运输单据或货物收据：</c:if></td>
		<td width="22%" align="left">
			<input name="billOfLading" type="text" size="15" tabindex="15" value="${main.billOfLading}" />
		</td>
		<td width="11%" align="right">保险单：</td>
		<td width="22%" align="left">
			<input name="billOfInsurance" type="text" size="15" tabindex="16" value="${main.billOfInsurance}" />
		</td>
		<td width="11%" align="right"><nobr>品质(分析证)：</nobr></td>
		<td width="22%" align="left">
			<input name="billOfQuality" type="text" size="15" tabindex="17" value="${main.billOfQuality}" />
		</td>
	</tr>
	<tr>
		<td width="11%" align="right"><nobr>受益人证明：</nobr></td>
		<td width="22%" align="left">
			<input name="benefitCertification" type="text" size="15" tabindex="18" value="${main.benefitCertification}" />
		</td>
		<td width="11%" align="right" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>产地证：</td>
		<td width="22%" align="left" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>
			<input name="certificateOfOrigin" type="text" size="15" tabindex="19" value="${main.certificateOfOrigin}" />
		</td>
		<td width="11%" align="right">装箱单：</td>
		<td width="22%" align="left">
			<input name="packingSlip" type="text" size="15" tabindex="20" value="${main.packingSlip}" />
		</td>
		<td width="11%" align="right" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>起运电：</td>
		<td width="22%" align="left" <c:if test="${purTradeType=='2'}">style='visibility: hidden'</c:if>>
			<input name="dispatchElectric" type="text" size="15" tabindex="21" value="${main.dispatchElectric}" />
		</td>
	</tr>
	<tr>
	    <td width="11%" align="right" valign="top">其他单据：</td>
		<td width="22%" align="left" colspan="7">
		    <textarea cols="96" rows="5" name="otherDocuments" tabindex="22">${main.otherDocuments}</textarea>
		</td>

	</tr>
	<tr>
		<td width="11%" align="right"><c:if test="${purTradeType=='1'}">装运港：</c:if><c:if test="${purTradeType=='2'}">装运地：</c:if></td>
		<td width="22%" align="left">
			<input name="portOfShipment" type="text" size="15" tabindex="23" value="${main.portOfShipment}" />
		</td>
		<td width="11%" align="right"><c:if test="${purTradeType=='1'}">目的港：</c:if><c:if test="${purTradeType=='2'}">目的地：</c:if></td>
		<td width="22%" align="left">
			<input name="portOfDestination"	type="text" size="15" tabindex="24" value="${main.portOfDestination}" />
		</td>
		<td width="11%" align="right">装期：</td>
		<td width="22%" align="left">
			<input name="loadingPeriod" type="text" size="15" tabindex="25" value="${main.loadingPeriod}" />
		</td>
		<td width="11%" align="right">有效期：</td>
        <td width="22%" align="left">
            <input name="validDate" type="text" size="15" tabindex="26" value="${main.validDate}" readonly="readonly" />
        </td>
		
	</tr>
	<tr>
        <td width="11%" align="right"><c:if test="${purTradeType=='1'}">可否转船：</c:if><c:if test="${purTradeType=='2'}">可否转运：</c:if></td>
		<td width="22%" align="left">
			<select id="transShipment" name="transShipment" tabindex="27">
				<option value="">请选择</option>
				<option value="Y"
					<c:if test="${main.transShipment=='Y'}"> selected </c:if>>是</option>
				<option value="N"
					<c:if test="${main.transShipment=='N'}"> selected </c:if>>否</option>
			</select>
		</td>
		<td width="11%" align="right">可否分批：</td>
		<td width="22%" align="left">
			<select id="canBatches" name="canBatches" tabindex="28">
				<option value="">请选择</option>
				<option value="Y"<c:if test="${main.canBatches=='Y'}"> selected </c:if>>是</option>
				<option value="N"<c:if test="${main.canBatches=='N'}"> selected </c:if>>否</option>
			</select>
		</td>
		<td width="11%" align="right">是否预保：</td>
		<td width="22%" align="left">
			<select id="preSecurity" name="preSecurity" tabindex="29">
				<option value="">请选择</option>
				<option value="Y"
					<c:if test="${main.preSecurity=='Y'}"> selected </c:if>>是</option>
				<option value="N"
					<c:if test="${main.preSecurity=='N'}"> selected </c:if>>否</option>
			</select>
		</td>
		<td width="11%" align="right">到期地点：</td>
		<td width="22%" align="left">
			<input name="place" type="text" size="15" tabindex="30" value="${main.place}" />
	   </td>
	   
	</tr>
	<tr>
		<td width="11%" align="right" valign="top">特别条款：</td>
		<td width="88%" align="left" colspan="7">
			<textarea cols="96" rows="8" id="specialConditions" name="specialConditions" tabindex="31">${main.specialConditions}</textarea>
		</td>
	</tr>
	<tr>
		<td width="11%" align="right">收保证金：</td>
		<td width="22%" align="left">
			<input type="text" size="15"  name="creditInfo" value="${main.creditInfo}" tabindex="32"/>
		</td>
	    <td width="11%" align="right"><nobr>保证金日期：</nobr></td>
		<td width="22%" align="left" >
			<input name="bailDate" type="text" size="15" tabindex="33" value="${main.bailDate}" readonly="readonly" />
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
<input name="tradeType" type="hidden" size="15" value="${main.tradeType}" /> 
<input name="createOrRec" type="hidden" size="15" value="${main.createOrRec}" /> 
<input name="deptId" type="hidden" size="15" value="${main.deptId}" /> 
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
 resourceId="CREDITENTRY" resourceName="CREDITENTRY" recordId="${creditId}" recordIdHiddenInputName="recordhdinputname"></fiscxd:fileUpload>
<fiscxd:workflow-taskHistory divId="history" businessRecordId="${creditId}" width="750"></fiscxd:workflow-taskHistory>
<fiscxd:dictionary divId="div_currency" fieldName="currency" dictionaryName="bm_currency" width="126" selectedValue="${main.currency}" disable="false" needDisplayCode="true">
</fiscxd:dictionary>
<fiscxd:dictionary divId="div_createBank" fieldName="createBank" dictionaryName="BM_BANK_INFO" width="150" selectedValue="${main.createBank}" disable="false"></fiscxd:dictionary>

<fiscxd:dictionary divId="div_ymatGroup" fieldName="ymatGroup" dictionaryName="YMATGROUP" width="150" selectedValue="${main.ymatGroup}" disable="false" ></fiscxd:dictionary>
