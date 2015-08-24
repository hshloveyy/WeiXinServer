<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出货统计单</title>
<style type="text/css">
.add{
	background-image:url(<%=request.getContextPath()%>/images/fam/add.gif) !important;
}
.delete{
	background-image:url(<%=request.getContextPath()%>/images/fam/delete.gif) !important;
}
.update{
	background-image:url(<%=request.getContextPath()%>/images/fam/refresh.gif) !important;
}
.find{
	background-image:url(<%=request.getContextPath()%>/images/fam/find.png) !important;
}
</style>
<script type="text/javascript">

//贸易类型
var tradeType ='${tradeType}';

//贸易名称
var strshipTitle ='';

var Enabled='false';
if ('${submit}'!='true')Enabled='submit';
if ('${save}'!='true')Enabled='save';

//取贸易名称
strshipTitle = Utils.getTradeTypeTitle(tradeType);

var currency_combo;
//document.title = strshipTitle + "货物出仓单" ;


function selectExportApplyWin(){
	if (Enabled=='false')return;	//Wang Yipu 2009.4.9
	top.ExtModalWindowUtil.show('查询所属登陆员工部门的出口货物通知单信息',
	'shipController.spr?action=selectExportApply&tradeType=' + '${tradeType}' +'&examineState=3&deptid=',
	'',
	selectExportApplyCallBack,
	{width:900,height:450});
}

function selectExportApplyCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	Ext.getDom('exportApplyNo').value=returnvalue.NOTICE_NO;
	Ext.getDom('exportApplyId').value=returnvalue.EXPORT_APPLY_ID;
	Ext.getDom('total').value=returnvalue.TOTAL;
	Ext.getDom('writeNo').value=returnvalue.WRITE_NO;
	Ext.getDom('port').value=returnvalue.EXPORT_PORT;
	//Ext.getDom('factoryName').value=returnvalue.KUWEV_KUNNR_NAME;
	Ext.getDom('customeName').value=returnvalue.CUSTOMER;
	Ext.getDom('prsNum').value=returnvalue.TOTAL_QUANTITY;
	Ext.getDom('port').value=returnvalue.EXPORT_PORT;
	Ext.getDom('factoryName').value=returnvalue.SUPPLIER;

	currency_combo.setValue(returnvalue.VBAK_WAERK);
	
}



function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
    top.ExtModalWindowUtil.markUpdated();
	top.ExtModalWindowUtil.close();
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/images/fam/s.gif';

    var Bedat = new Ext.form.DateField({
   		format:'Y-m-d',
		name:"exportDate",
		id:"exportDate",
		width: 140,
		applyTo:'exportDate'
   	});
   	
   	var drawbackDate = new Ext.form.DateField({
   		format:'Y-m-d',
		name:"drawbackDate",
		id:"drawbackDate",
		width: 140,
		applyTo:'drawbackDate'
   	});
   	
   	var writeBackDate = new Ext.form.DateField({
   		format:'Y-m-d',
		name:"writeBackDate",
		id:"writeBackDate",
		width: 140,
		applyTo:'writeBackDate'
   	});
   	
   	var shipInsureDate = new Ext.form.DateField({
   		format:'Y-m-d',
		name:"shipInsureDate",
		id:"shipInsureDate",
		width: 140,
		applyTo:'shipInsureDate'
   	});
   	
   	var insureDate = new Ext.form.DateField({
   		format:'Y-m-d',
		name:"insureDate",
		id:"insureDate",
		width: 140,
		applyTo:'insureDate'
   	});
   	
   	var insurePayDate = new Ext.form.DateField({
   		format:'Y-m-d',
		name:"insurePayDate",
		id:"insurePayDate",
		width: 140,
		applyTo:'insurePayDate'
   	});
   	
   	var receiptDate = new Ext.form.DateField({
   		format:'Y-m-d',
		name:"receiptDate",
		id:"receiptDate",
		width: 140,
		applyTo:'receiptDate'
   	});
   	
    currency_combo = new Ext.form.ComboBox({
        typeAhead: true,
        triggerAction: 'all',
        transform:'currency',
        width:150,
        allowBlank:false,
        blankText:'请输入货币',
        forceSelection:true
     });
     currency_combo.setValue('${main.currency}'); 
     
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			collapsible: true,
			height:350,
			contentEl: 'div_main'
		},{
			region:"center",
			layout:'fit',
			buttonAlign:'center',
			buttons:[{
	            	text:'保存',
	            	hidden:${isShow},
	            	handler:function(){
	            	    
	            		var param1 = Form.serialize('mainForm');
	            		if (param1.indexOf("&exportApplyNo=&")>-1)
            			{
            				Ext.MessageBox.alert('提示', '通知单号必填！');
	            			return;
	            		}
						var param = param1 + "&action=updateExportStat";
						new AjaxEngine('exportController.spr', 
						   {method:"post", parameters: param, onComplete: callBackHandle});
					}

	            },{
            	text:'关闭',
            	handler:function(){
            	    top.ExtModalWindowUtil.markUpdated();
	                top.ExtModalWindowUtil.close();
	            }
            }]
		}]
	});//end fo viewPort

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
	top.ExtModalWindowUtil.markUpdated();
	top.ExtModalWindowUtil.close();
	return;
	
	Ext.Msg.show({
   		title:'提示',
   		msg: '是否退出并关闭窗口',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
 			if(buttonId=='yes'){
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

</script>
</head>
<body>
<div id='div_progressBar'></div>
<div id="div_main" class="x-hide-display">
<form id="mainForm" action="" name="mainForm">
      	<input type="hidden" id = "tradeType" name = "tradeType" value="${tradeType}"/>
      	<input type="hidden" name="exportShipmentStatId" value="${main.exportShipmentStatId }"/> 
		<input type="hidden" name="creatorTime" value="${main.creatorTime }"/> 
		<input type="hidden" name="creator" value="${main.creator }"/> 
		<input type="hidden" name="creatorDept" value="${main.creatorDept }"/>
	 <table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
      <tr>
        <td width="11%" align="right">通知单号：<font color="red">▲</font></td>
        <td width="22%" align="left">
        	 <input type="text" id="exportApplyNo" name="exportApplyNo" value="${main.exportApplyNo}" readonly="readonly" />
        	 <input type="button" value="..." onclick="selectExportApplyWin()"></input>
        	 <input type="hidden" id="exportApplyId" name="exportApplyId" value="${main.exportApplyId}"/>
        </td>
        <td width="11%" align="right">发票号：</td>
        <td width="22%" align="left">
            <input id="invNo" name="invNo"  value="${main.invNo }"/>
        </td>
        <td width="11%" align="right">批次号：</td>
        <td width="22%" align="left">
        	<input id="batchNo" name="batchNo" value="${main.batchNo}"/>
        </td>
      </tr>
      <tr>
        <td align="right">出口日期：</td>
        <td align="left">
        	<input type="text" name="exportDate" value="${main.exportDate }"/>
        </td>
        <td align="right">数量（PRS）：</td>
        <td  align="left">
        	<input id="prsNum" name="prsNum" type="text" value="${main.prsNum}"/>
        </td>
        <td align="right">单位：</td>
        <td align="left">
        	<input id="unit" name="unit" type="text" value="${main.unit}"/>
        </td>
      </tr>
      <tr>
        <td align="right">工厂名称：</td>
        <td  align="left">
        	<input id="factoryName" name="factoryName" type="text" value="${main.factoryName}"/>
        </td>
        <td align="right">金额：</td>
        <td align="left">
        	<input id="total" name="total" type="text" value="${main.total}"/>
        </td>
        <td align="right">币别：</td>
        <td align="left">
        	<div id="div_currency">
			<select name="currency" id="currency">
				<option value="">请选择</option>
				<c:forEach items="${Currency}" var="row">
					<option value="${row.code}">${row.code}-${row.title}</option>
				</c:forEach>
			</select>
			</div>
        </td>
      </tr>
      <tr>
        <td align="right">增值税发票号：</td>
        <td align="left">
        	<input id="addedTaxInvNo" name="addedTaxInvNo" type="text" value="${main.addedTaxInvNo}"/>
        </td>
        <td align="right">发票金额（进项）：</td>
        <td align="left">
        	<input id="addedTaxInvValue" name="addedTaxInvValue" type="text" value="${main.addedTaxInvValue}"/>
        </td>
        <td align="right">税面额：</td>
        <td align="left">
        	<input id="addedTaxValue" name="addedTaxValue" type="text" value="${main.addedTaxValue}"/>
        </td>
      </tr>
      <tr>
        <td align="right">核销单号：</td>
        <td  align="left">
        	<input id="writeNo" name="writeNo" type="text" value="${main.writeNo}"/>
        </td>
        <td align="right">提单号：</td>
        <td align="left">
        	<input id="catchNo" name="catchNo" type="text" value="${main.catchNo}"/>
        </td>
        <td align="right">口岸：</td>
        <td align="left">
        	<input id="port" name="port" type="text" value="${main.port}"/>
        </td>
      </tr>
      <tr>
       <td align="right">客户：</td>
        <td align="left">
        	<input id="customeName" name="customeName" type="text" value="${main.customeName}"/>
        </td>
        <td align="right">申报退税日：</td>
        <td  align="left">
        	<input id="drawbackDate" name="drawbackDate" type="text" value="${main.drawbackDate}" readonly="readonly"/>
        </td>
        <td align="right">报关单号：</td>
        <td align="left">
        	<input id="declarationNo" name="declarationNo" type="text" value="${main.declarationNo}"/>
        </td>
      </tr>
       <tr>
        <td align="right">核销单回单日期：</td>
        <td align="left">
        	<input id="writeBackDate" name="writeBackDate" type="text" value="${main.writeBackDate}"/>
        </td>
        <td align="right">船名：</td>
        <td align="left">
        	<input id="shipName" name="shipName" type="text" value="${main.shipName}"/>
        </td>
        <td align="right">货运保险单号：</td>
        <td  align="left">
        	<input id="shipInsureNo" name="shipInsureNo" type="text" value="${main.shipInsureNo}"/>
        </td>
      </tr>
      <tr>
        <td align="right">是否信保：</td>
        <td align="left">
        	<select name="isInsure" id="isInsure">
        	    <option value="">请选择</option>
        	    <option value="1" ${main.isInsure==1?'selected':'' }>是</option>
        	    <option value="0" ${main.isInsure==0?'selected':'' }>否</option>
        	</select>
        </td>
        <td align="right">信保出运申报日：</td>
        <td align="left">
        	<input id="shipInsureDate" name="shipInsureDate" type="text" value="${main.shipInsureDate}"/>
        </td>
        <td align="right">信保报损日：</td>
        <td align="left">
        	<input id="insureDate" name="insureDate" type="text" value="${main.insureDate}"/>
        </td>
      </tr>
       <tr>
        <td align="right">信保赔付日：</td>
        <td  align="left">
        	<input id="insurePayDate" name="insurePayDate" type="text" value="${main.insurePayDate}"/>
        </td>
        <td align="right">收汇汇率：</td>
        <td align="left">
        	<input id="incomeRate" name="incomeRate" type="text" value="${main.incomeRate}"/>
        </td>
        <td align="right">进仓日：</td>
        <td align="left">
        	<input id="receiptDate" name="receiptDate" type="text" value="${main.receiptDate}"/>
        </td>
      </tr>
      <tr>
        <td align="right">换汇比：</td>
        <td align="left">
        	<input id="rate" name="rate" type="text" value="${main.rate}"/>
        </td>
        <td align="right">货代：</td>
        <td align="left" colspan="3">
        	<input id="huodai" name="huodai" type="text" value="${main.huodai}"/>
        </td>
      </tr>
       <tr>
        <td align="right">备注：</td>
        <td  align="left" colspan="5">
        	<textarea name="mark" rows="3" cols="85">${main.mark }</textarea>
        </td>
      </tr>
	</table>
</form>
</div>
<div id="div_center"></div>
<div id="history" class="x-hide-display" ></div>

</body>
</html>
    
