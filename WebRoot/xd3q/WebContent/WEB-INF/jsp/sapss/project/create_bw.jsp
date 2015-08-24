<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>立项申请页面</title>
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
var from = '<%=request.getAttribute("popfrom")%>';
var strProjectId = '${projectId}';

var tempTab={};
Ext.onReady(function(){
	var projectNo = '${main.projectNo}';
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	var tabs = new Ext.TabPanel({
		id:'mainTabPanel',
        renderTo: document.body,
        autoWidth:true,
        activeTab: 0,
        loadMask:true,
        frame:true,
        //defaults:{autoHeight: true},
        items:[
//            {contentEl:'main', title: '项目名称及客户信息'},
            {contentEl:'detail',id:'detailEl',title: '立项测算表',disabled:true,autoScroll:true}
//            {contentEl:'rule',id:'fileEl', title: '立项附件',disabled:true,listeners:{activate:handlerActivate}},
//            {contentEl:'history',id:'historyEl', title: '审批历史记录',disabled:true},
//            {
//				id:'alterEl',
//				title:'立项变更',
//				disabled:true,
//				html:'<iframe src="changeProjectController.spr?action=toProjectChangeTab&projectId=${projectId}&from=${from}&projectNo=' + projectNo +'" width="770" height="430" id="alter" ></iframe>'
//             }
        ]
    });

	/*tabs.on('tabchange', function(tabs, tab){
		if (tab.id == 'custcreditEl'){
			strProjectId = Ext.getDom('projectId').value;
			var custUrl = 'projectController.spr?action=applyCustomerCreditView&from=' + from+ '&projectId=' + strProjectId;
			tab.setSrc(custUrl);
			
		}

		if (tab.id == 'providercreditEl'){
			strProjectId = Ext.getDom('projectId').value;

			var providerUrl = 'projectController.spr?action=applyProviderCreditView&from='+from+'&projectId='+ strProjectId;
			tab.setSrc(providerUrl);
		}
	});*/
    
    var applyTime = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 100,
		applyTo:'shipmentDate'
   	});
    var availableDataEnd = new Ext.form.DateField({
   		format:'Y-m-d',
   		typeAhead: true,  
   		minValue:'2009-3-1',
   		disabledDates:["../../2009"],
		width: 100,
		allowBlank:false,
		applyTo:'availableDataEnd'
   	});

   	
    tempTab = tabs;
    /*
    setTimeout(function(){
        Ext.get('loading').remove();
        Ext.get('loading-mask').fadeOut({remove:true});
    }, 1000);
    */
    //提交按键默认不能用
    Ext.getDom('submitfr').disabled=true;
    //如果是修改窗口
    if(from=='modify'){
    	tabs.getItem('detailEl').enable();
//    	tabs.getItem('fileEl').enable();

    	/*********************fuyy*************************/
    	//tabs.getItem('custcreditEl').enable();
    	//tabs.getItem('providercreditEl').enable();
    	/*********************fuyy*************************/
    	
    	Ext.getDom('submitfr').disabled=true;
    	Ext.getDom('clearDetailfr').disabled=true;
    	Ext.getDom('projectId').value='<%=request.getAttribute("projectId")%>';
    	Ext.getDom('recordhdinputname').value='<%=request.getAttribute("projectId")%>';
    }
    //如果是提交审批窗口
    else if(from=='workflow'){
    	tabs.getItem('detailEl').enable();
//    	tabs.getItem('fileEl').enable();

    	/*********************fuyy*************************/
    	//tabs.getItem('custcreditEl').enable();
    	//tabs.getItem('providercreditEl').enable();
    	/*********************fuyy*************************/
    	
    	Ext.getDom('submitfr').disabled=false;
    	Ext.getDom('savefr').disabled=true;
    	Ext.getDom('clearDetailfr').disabled=true;
    	Ext.getDom('projectId').value='<%=request.getAttribute("projectId")%>';
    	Ext.getDom('recordhdinputname').value='<%=request.getAttribute("projectId")%>';
    	Ext.getDom('saveDetailfr').disabled=true;
    	
    }
    //如果是查看窗口
    else if(from=='view'){
    	tabs.getItem('detailEl').enable();
//    	tabs.getItem('fileEl').enable();
//    	tabs.getItem('historyEl').enable();
//    	tabs.getItem('alterEl').enable();
    	Ext.getDom('submitfr').disabled=true;
    	Ext.getDom('savefr').disabled=true;
    	Ext.getDom('clearDetailfr').disabled=true;
    	Ext.getDom('saveDetailfr').disabled=true;

    	/*********************fuyy*************************/
		//tabs.getItem('custcreditEl').enable();
    	//tabs.getItem('providercreditEl').enable();
    	/*********************fuyy*************************/
    	
    	Ext.getDom('projectId').value='<%=request.getAttribute("projectId")%>';
    	Ext.getDom('recordhdinputname').value='<%=request.getAttribute("projectId")%>';
    }
    //流程中可修改窗口
    else if(from=='iframe'){
     	tabs.getItem('detailEl').enable();
//    	tabs.getItem('fileEl').enable();

    	/*********************fuyy*************************/
    	//tabs.getItem('custcreditEl').enable();
    	//tabs.getItem('providercreditEl').enable();
    	/*********************fuyy*************************/
    	
    	
    	Ext.getDom('projectId').value='<%=request.getAttribute("projectId")%>';
    	Ext.getDom('recordhdinputname').value='<%=request.getAttribute("projectId")%>';
    	Ext.getDom('clearDetailfr').disabled=true;
    	Ext.getDom('closefr').disabled=true;
    }
//    var currency_combo = new Ext.form.ComboBox({
//        typeAhead: true,
//        triggerAction: 'all',
//        transform:'currency',
//        allowBlank:false,
//        width:50,
//        blankText:'...',
//        forceSelection:true
//     });
//    currency_combo.on('change',function(c,v1,v2){
//		$('bibei').innerHTML=c.getValue();
//     });
//    currency_combo.setValue('${main.currency}'); 
//    $('bibei').innerHTML='${main.currency}';	
});//end of Ext.onReady    
function handlerActivate(tab){
	rule_ns_ds.load({params:{start:0,limit:5,recordId:Ext.getDom('recordhdinputname').value}});
}
var saveFlag = false;
function saveMainForm(btn){
	btn.disabled=true;
	var detail=tempTab.getItem('detailEl');
//	var file=tempTab.getItem('fileEl');
	if((${main.tradeType}=='6'||${main.tradeType}=='5')&&$('customerLinkMan').value==''){
		showMsg("代理业务必须填写客户方！");
		btn.disabled=false;
		return false ;
	}
	/*******************fuyy************************/
	//var cust = tempTab.getItem('custcreditEl');
	//var provider = tempTab.getItem('providercreditEl');
	/*******************fuyy************************/
	
	var ajax = Ext.Ajax.request({
		url: 'projectController.spr?action=saveProjectInfo&pid='+Ext.getDom('projectId').value,
		params:Form.serialize('mainForm'),
		success: function(response, options){
			var txt = Ext.util.JSON.decode(response.responseText);
			var json =txt['infolion-json'];
			if(json!=null && json.message!=null){
					top.Ext.Msg.show({
   						title:'警告',
   						closable:false,
   						msg:json.message,
   						buttons:{yes:'关闭'},
   						fn:Ext.emptyFn,
   						icon:Ext.MessageBox.WARNING
					});
					btn.disabled=false;
			}else{
				detail.enable();
//				file.enable();

				/*******************fuyy************************/
				//cust.enable();
				//provider.enable();
				/*******************fuyy************************/
				
				saveFlag = true;
				Ext.getDom('projectId').value=txt.projectId;
				Ext.getDom('recordhdinputname').value=txt.projectId;
				Ext.Msg.alert('提示','保存成功');
				btn.disabled=false;
				if(txt.projectNo!=null && txt.projectNo!='undefined' && txt.projectNo!='' )
					Ext.getDom('projectNo').value=txt.projectNo;
			}
		},
		waitMsg:'处理中...',
		failure:function(resp,o){
			Ext.Msg.alert('Error',o.result.msg);
		}
	});
}
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
function saveDetailCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	var msg = responseUtil1.getMessage();
	showMsg(msg);
	showMsg(customField.returnMessage);
}


function saveDetailFORM(btn){
	btn.disabled=true;
	if(Ext.getDom('ymatGroup').value==''){
		showMsg("请选择物料组！");
		btn.disabled=false;
		return false ;
	}

	Ext.Ajax.request({
		url: 'projectController.spr?action=saveDetail&projectId='+Ext.getDom('projectId').value,
		params:Form.serialize('detailForm'),
		success: function(response, options){
			var txt = Ext.util.JSON.decode(response.responseText);
			var json = txt['infolion-json'];
			if(json!=null&&json.message!=null&&json.message!='操作成功'){
					top.Ext.Msg.show({
   						title:'警告',
   						closable:false,
   						msg:json.message,
   						buttons:{yes:'关闭'},
   						fn:Ext.emptyFn,
   						icon:Ext.MessageBox.WARNING
					});
					btn.disabled=false;
			}else{
				showMsg('保存成功');
				btn.disabled=false;
			}
		}
	});
}

function callBackHandle(){
}
function showFindSupplier(){
	top.ExtModalWindowUtil.show('查找供应商','masterQueryController.spr?action=toFindSupplier','',supplierCallback,{width:755,height:410});
}
function showFindCustomer(){
	top.ExtModalWindowUtil.show('查找客户','masterQueryController.spr?action=toFindCustomer','',customerCallback,{width:755,height:410});
}
function showFindMaterial(){
	top.ExtModalWindowUtil.show('查找物料','masterQueryController.spr?action=toFindMaterial','',materialCallback,{width:755,height:450});
}

function customerCallback(){
	var cb = top.ExtModalWindowUtil.getReturnValue();
	if(cb==''){
	    Ext.getDom('customerLinkMan').value='';
		Ext.getDom('customerAddress').value='';
		Ext.getDom('customerTel').value='';
		Ext.getDom('customerCreditGrade').value='';
		Ext.getDom('customerId').value='';
	}
	else {
		Ext.getDom('customerLinkMan').value=cb.NAME1;
		Ext.getDom('customerAddress').value=cb.STRAS;
		Ext.getDom('customerTel').value=cb.TELF1;
		Ext.getDom('customerCreditGrade').value=cb.NAME1;
		Ext.getDom('customerId').value=cb.KUNNR;
	}
}
function supplierCallback(){
	var cb = top.ExtModalWindowUtil.getReturnValue();
	if(cb==''){
		Ext.getDom('providerLinkMan').value='';
		Ext.getDom('providerAddress').value='';
		Ext.getDom('providerTel').value='';
		Ext.getDom('proiderCreditGrade').value='';
		Ext.getDom('providerId').value='';
	}
	else {
		Ext.getDom('providerLinkMan').value=cb.NAME1;
		Ext.getDom('providerAddress').value=cb.STRAS;
		Ext.getDom('providerTel').value=cb.TELF1;
		Ext.getDom('proiderCreditGrade').value=cb.NAME1;
		Ext.getDom('providerId').value=cb.LIFNR;
	}
}
function materialCallback(){
	var cb = top.ExtModalWindowUtil.getReturnValue();
	Ext.getDom('className').value=cb.MAKTX;
}

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

function submitWorkflow(){
	var sum = Ext.getDom('sum').value;
	
	if(sum ==null || sum==''){
		showMsg('核算项金额为空，请检查！');
		return;
	}
	Ext.Msg.show({
   		title:'提示',
   		msg: '确定提交流程审批',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			if(buttonId=='yes'){
				//handlerActivate();
				//setTimeout(function(){
				// if(rule_ns_ds.getTotalCount()>0){
				 /*
					Ext.Ajax.request({
						url: 'projectController.spr?action=submitProjectInfo&doWorkflow=mainForm',
						params:Form.serialize('mainForm'),
						success: function(response, options){
									var txt = Ext.util.JSON.decode(response.responseText);
									if(txt.success){
										Ext.Msg.alert('提示',txt.msg);
										top.ExtModalWindowUtil.markUpdated();
										top.ExtModalWindowUtil.close();
									}else{
										if(txt['infolion-json']!=null)
											Ext.Msg.alert('提示',txt['infolion-json']['message']);	
										else	
											Ext.Msg.alert('提示',txt.msg);	
									}	
								}
					});
				*/
					new AjaxEngine('projectController.spr?action=submitProjectInfo&doWorkflow=mainForm',
					 {method:"post", parameters: Form.serialize('mainForm'), onComplete: customCallBackHandle});
					
				//}else{
				//	Ext.Msg.alert('提示','请上传附件');
				//}
				//},500);   			
   			}
   		},
   		icon: Ext.MessageBox.QUESTION
	});
}

function savePassFORM(btn){
  btn.disabled=true;
  var projectId = Ext.getDom('projectId').value;
	Ext.Msg.show({
   		title:'提示',
   		msg: '请确认，该立项信息是否正确无误，确定后，该项目信息将变为有效项目的信息！',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			if(buttonId=='yes'){
					Ext.Ajax.request({
					url: 'projectController.spr?action=saveToPass&projectId='+Ext.getDom('projectId').value,
					//params:Form.serialize('detailForm'),
					success: function(response, options){
						var txt = Ext.util.JSON.decode(response.responseText);
						var json = txt['infolion-json'];
						if(json!=null&&json.message!=null&&json.message!='操作成功'){
								top.Ext.Msg.show({
				  						title:'警告',
				  						closable:false,
				  						msg:json.message,
				  						buttons:{yes:'关闭'},
				  						fn:Ext.emptyFn,
				  						icon:Ext.MessageBox.WARNING
								});
								btn.disabled=false;
						}else{
							showMsg('操作成功');
							btn.disabled=false;
						}
					}
				});
   			}
   		},
   		icon: Ext.MessageBox.QUESTION
	});
}

function customCallBackHandle(transport){
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

function submitMainForm(){
/*
		new AjaxEngine('projectController.spr?action=submitProjectInfo&doWorkflow=mainForm', 
			{method:"post", parameters: Form.serialize('mainForm'), onComplete: callBackHandle});
*/
		handlerActivate();
		setTimeout(function(){
		if(rule_ns_ds.getTotalCount()>0){
		Ext.Ajax.request({
			url: 'projectController.spr?action=submitProjectInfo&doWorkflow=mainForm',
			params:Form.serialize('mainForm'),
			success: function(response, options){
						var txt = Ext.util.JSON.decode(response.responseText);
						if(txt.success){
							Ext.Msg.alert('提示',txt.msg);
							setTimeout(function(){
								_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
								top.ExtModalWindowUtil.markUpdated();
								top.ExtModalWindowUtil.close();
					    	}, 1000);
						}else{
							if(txt['infolion-json']!=null)
								Ext.Msg.alert('提示',txt['infolion-json']['message']);	
							else	
								Ext.Msg.alert('提示',txt.msg);	
						}	
					}
		});
		}else{
			Ext.Msg.alert('提示','请上传附件');
		}
		},500);
}
</script>
</head>
<body>
<div id='progressBar'></div>
<div id="main" class="x-hide-display">
<form id="mainForm">
<table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
  <tr >
    <td width="755" height="196">
    <table width="755" align="center" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
      <tr>
        <td colspan="2"><div align="center"  >承 办 单 位</div></td>
        <td width="403" align="left"><input name="orgName" type="text" size="55" tabindex="1" value="${main.orgName}" readonly="readonly"/></td>
      </tr>
      <tr>
        <td colspan="2"><div align="center"  >承   办   人</div></td>
        <td>
        	<input name="nuderTaker" type="text" size="55" tabindex="2" value="${main.nuderTaker}" readonly="readonly"/>
        	<input type="hidden" name="deptId" value="${main.deptId}"/>
        	<input name="oldProjectNo" type="hidden"  value="${main.oldProjectNo}"/>
        </td>
        
      </tr>
      <tr>
        <td colspan="2"><div align="center">项 目 编 号</div></td>
        <td><input name="projectNo" type="text" size="55" tabindex="3" readonly="readonly" value="${main.projectNo}"/></td>
      </tr>
      <tr>
        <td colspan="2"><div align="center" ><font color="red">*</font>项 目 名 称</div></td>
        <td><input name="projectName" type="text" size="55" tabindex="4" value="${main.projectName}"/><font color='red'>请填写品名</font></td>
      </tr>
      <tr>
        <td colspan="2"><div align="center">申 报 日 期</div></td>
        <td><input name="applyTime" type="text" size="55" tabindex="5" value="${main.applyTime}" readonly="readonly"/></td>
      </tr>
      <tr>
        <td colspan="2"><div align="center"><font color="red">*</font>终 止 时 间</div></td>
        <td><input name="availableDataEnd" type="text" size="55" tabindex="5" value="${main.availableDataEnd}" readonly="readonly"/></td>
      </tr>
       <tr>
        <td colspan="2"><div align="center"  >是 否 授 信</div></td>
        <td>
        	<select id="isCredit" name="isCredit" tabindex="27">
				<option value="">请选择</option>
				<option value="Y"
					<c:if test="${main.isCredit=='Y'}"> selected </c:if>>是</option>
				<option value="N"
					<c:if test="${main.isCredit=='N'}"> selected </c:if>>否</option>
			</select>
        </td>
      </tr>
       <tr>
        <td colspan="2"><div align="center"  >授 信 描 述</div></td>
        <td><textarea name="creditDescription" cols="57" rows="2" tabindex="3" style="overflow-y:visible;word-break:break-all;"  >${main.creditDescription}</textarea></td>
      </tr>
      <tr>
        <td width="13" rowspan="4" align="center" valign="middle"  >买 方 / 委托方</td>
        <td width="166" height="25"><div align="center"  >客 户 名 称</div></td>
        <td><input name="customerLinkMan" value="${main.customerLinkMan}" type="text" size="52" readonly="readonly" tabindex="6"/><input type="button" class="btn" value="..." onclick="showFindCustomer();"/></td>
      </tr>
      <tr>
        <td><div align="center"  >付 款 方 式</div></td>
        <td>
        	<textarea name="customerPayType" cols="57" rows="2" tabindex="12" style="overflow-y:visible;word-break:break-all;" >${main.customerPayType}</textarea>
        </td>
      </tr>
      <tr>
        <td><div align="center"  >结 算 方 式</div></td>
        <td>
        	<textarea name="customerBalanceType" cols="57" rows="2" tabindex="12" style="overflow-y:visible;word-break:break-all;" >${main.customerBalanceType}</textarea>
        </td>
      </tr>
      <tr>
        <td><div align="center"  >说       明</div></td>
        <td>
        	<textarea name="customerExplain" cols="57" rows="2" tabindex="12" style="overflow-y:visible;word-break:break-all;" >${main.customerExplain}</textarea>
        </td>
      </tr>
      <tr>
        <td width="13" rowspan="4"  >卖 方 / 委托方</td>
        <td width="166" height="25"><div align="center"  >供 应 商 名称</div></td>
        <td><input name="providerLinkMan" value="${main.providerLinkMan}" type="text" size="52" readonly="readonly" tabindex="10"/><input type="button" class="btn" value="..." onclick="showFindSupplier()"/></td>
      </tr>
      <tr>
        <td><div align="center"  >付 款 方 式</div></td>
        <td>
        	<textarea name="providerPayType" cols="57" rows="2" tabindex="12" style="overflow-y:visible;word-break:break-all;" >${main.providerPayType}</textarea>
        </td>
      </tr>
      <tr>
        <td><div align="center"  >结 算 方 式</div></td>
        <td>
        	<textarea name="providerBalanceType" cols="57" rows="2" tabindex="12" style="overflow-y:visible;word-break:break-all;" >${main.providerBalanceType}</textarea>
        </td>
      </tr>
      <tr>
        <td><div align="center"  >说       明</div></td>
        <td>
        	<textarea name="providerExplain" cols="57" rows="2" tabindex="12" style="overflow-y:visible;word-break:break-all;" >${main.providerExplain}</textarea>
        </td>
      </tr>
      
    </table>
    </td>
  </tr>
  <tr >
  <td  width="755" align="center">
  	<table width="750">
  		<tr>
  			<td width="49%" align="right">
  			    <div style="display: none">
  			    <input type="button" value="保 存" onclick="saveMainForm(this)" class="btn" id="savefr" />
  			    </div>
  			</td>
  			<td width="1%" >
  				<input type="hidden" name="projectId" />
  				<input type="hidden" name="tradeType" value="${main.tradeType}"/>
  				<input type="hidden" name="projectState" value="${main.projectState}"/>
  				<input type="hidden" name="providerId" value="${main.providerId}"/>
  				<input type="hidden" name="providerAddress" value="${main.providerAddress}"/>
  				<input type="hidden" name="providerTel" value="${main.providerTel}"/>
  				<input type="hidden" name="proiderCreditGrade" value="${main.proiderCreditGrade}"/>
  				<input type="hidden" name="customerAddress" value="${main.customerAddress}"/>
  				<input type="hidden" name="customerTel" value="${main.customerTel}"/>
  				<input type="hidden" name="customerCreditGrade" value="${main.customerCreditGrade}"/>
  				<input type="hidden" name="customerId" value="${main.customerId}"/>
  				<input type="hidden" name="underTakerId" value="${main.underTakerId}"/>
  				<input type="hidden" name="sum" value="${main.sum}"/>
  				<input type="hidden" name="currency" value="${main.currency}"/>
  			</td>
  			 <td  width="50%" align="left">
  			 	<div style="display: none">
  			    	<input type="button" value="提 交" onclick="submitWorkflow()" class="btn" id="submitfr"/>
    				<input type="button" value="关闭窗口" onclick="closeForm()" class="btn" id="closefr"/>
    			        </div>
  			</td>
  		</tr>
  	</table>
 </td>
	</tr>
</table>
<input type="hidden" name="taskId" value="${taskId}">
</form>
</div>
<div id="detail" class="x-hide-display">
<form id="detailForm">
<input type="hidden" name="tradeType" value="${main.tradeType}"/>
<c:if test="${main.tradeType=='7'||main.tradeType=='10'}">
<c:if test="${main.deptId=='65A90F40-CF91-4F4F-B5B0-43DC62C273D9'}">
<%@ include file="costEvaluation/hKhomeTrade_bw.jsp"%>
</c:if>
<c:if test="${main.deptId!='65A90F40-CF91-4F4F-B5B0-43DC62C273D9'}">
<%@ include file="costEvaluation/homeTrade_bw.jsp"%>
</c:if>
</c:if>
<c:if test="${main.tradeType=='1'||main.tradeType=='3'||main.tradeType=='8'||main.tradeType=='9'||main.tradeType=='11'||main.tradeType=='12'}">
<%@ include file="costEvaluation/import_bw.jsp"%>
</c:if>
<c:if test="${main.tradeType=='2'||main.tradeType=='4'}">
<%@ include file="costEvaluation/export_bw.jsp"%>
</c:if>
<c:if test="${main.tradeType=='5'||main.tradeType=='6'}">
<%@ include file="costEvaluation/proxy_bw.jsp"%>
</c:if>
<table width="756">
<tr>
	<td><div align="center">
	<div style="display: none">
	  <input type="button" value="保存" onclick="saveDetailFORM(this)" id="saveDetailfr"/>
	  <input type="button" value="清空" onclick="resetForm(this.form)" id="clearDetailfr"/>
        </div>
    </div></td>
</tr>
</table>
</form>
</div>

<div id="rule" class="x-hide-display" ></div>
<div id="history" class="x-hide-display" ></div>
</body>
</html>
<fiscxd:workflow-taskHistory divId="history" businessRecordId="${projectId}" width="750"></fiscxd:workflow-taskHistory>
<fiscxd:fileUpload divId="rule" erasable="${revisable}"  increasable="${revisable}"
 resourceId="2222" resourceName="33333" recordIdHiddenInputName="recordhdinputname"></fiscxd:fileUpload>
