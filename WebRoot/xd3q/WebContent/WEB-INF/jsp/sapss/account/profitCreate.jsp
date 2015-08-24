<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ taglib prefix="ffcs" tagdir="/WEB-INF/tags/infolion"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>利润中心申请页面</title>
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
</head>
<body>
<div id='progressBar'></div>
<div id="rule" class="x-hide-display"></div>
<div id="main" class="x-hide-display">
<fiscxd:fileUpload divId="rule" erasable="${revisable}"  increasable="${revisable}" resourceId="profitCenter" resourceName="profitCenter" recordIdHiddenInputName="profitID" recordId="${main.profitID}"></fiscxd:fileUpload>
<form id="mainForm">
<table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
      <tr>
        <td width="20%" align="right">申请单位</td>
        <td width="30%" align="left"><input name="orgName" type="text" size="25" tabindex="1" value="${main.orgName}" readonly="readonly"/><input type="hidden" name="orgID" value="${main.orgID}"/></td>
        <td colspan="2" width="20%" align="right">申请人</td>
        <td width="30%">
        	<input name="creatorName" type="text" size="25" tabindex="2" value="${main.creatorName}" readonly="readonly"/>
        	<input type="hidden" name="creatorID" value="${main.creatorID}"/>
        </td>
      </tr>
	  <tr>
        <td align="right">电话<font color="red">▲</font></td>
        <td>
        	<input name="tel" type="text" size="25" tabindex="2" value="${main.tel}" maxlength="20" />
        </td>
        <td colspan="2" align="right">EMAIL<font color="red">▲</font></td>
        <td>
        	<input name="email" type="text" size="25" tabindex="2" value="${main.email}" maxlength="50"/>
        </td>
      </tr>
	  <tr>
        <td align="right">公司代码<font color="red">▲</font></td>
        <td>
        	<input name="companyCode" type="text" size="25" tabindex="2" value="${main.companyCode}"/>
        </td>
        <td colspan="2" align="right">上层利润中心组<font color="red">▲</font></td>
        <td>
        	<input name="upProfitGroup" type="text" size="25" tabindex="2" value="${main.upProfitGroup}"/>
        </td>
      </tr>
	  <tr>
        <td align="right">利润中心组名称<font color="red">▲</font></td>
        <td>
        	<input name="profitGroupName" type="text" size="25" tabindex="2" value="${main.profitGroupName}"/>
        </td>
        <td colspan="2" align="right">利润中心组编号<font color="red">▲</font></td>
        <td>
        	<input name="profitGroupNO" type="text" size="25" tabindex="2" value="${main.profitGroupNO}" maxlength="20"/>
        </td>
      </tr>
	  <tr>
        <td align="right">利润中心名称<font color="red">▲</font></td>
        <td>
        	<input name="profitCenterName" type="text" size="25" tabindex="2" value="${main.profitCenterName}"/>
        </td>
        <td colspan="2" align="right">利润中心编号<font color="red">▲</font></td>
        <td>
        	<input name="profitCenterNO" type="text" size="25" tabindex="2" value="${main.profitCenterNO}" maxlength="20"/>
        </td>
      </tr>
	  <tr>
        <td align="right">是否改变标准层次结构<font color="red">▲</font></td>
        <td>
        	<select name="isChangeStandard" onChange="changeStandard()">
				<option value="">请选择</option>
				<option value="1" <c:if test="${main.isChangeStandard=='1'}"> selected </c:if> >是</option>
				<option value="0" <c:if test="${main.isChangeStandard=='0'}"> selected </c:if> >否</option>
			</select>
        </td>
		<td colspan="2" align="right">负责人<font color="red">▲</font></td>
        <td>
        	<input name="personInCharge" type="text" size="25" tabindex="2" value="${main.personInCharge}"/>
        </td>
	  </tr>
	  <tr>
        <td align="right">生效起始日</td>
        <td>
        	<input name="effectiveBeginDate" type="text" size="10" tabindex="5" value="${main.effectiveBeginDate}" readonly="readonly"/>
        </td>
        <td colspan="2" align="right">生效截止日</td>
        <td>
        	<input name="effectiveEndDate" type="text" size="10" tabindex="5" value="9999-12-31" readonly="readonly"/>
        </td>
      </tr>
	  <tr>
        <td align="right">描述</td>
        <td colspan="5">
        	<input name="description" type="text" size="25" tabindex="2" value="${main.description}"/>
        </td>
      </tr>
</table>
<input type="hidden" name="taskId" value="${taskId}">
<input type="hidden" name="profitID" value="${main.profitID}">
<input type="hidden" name="applyTime" value="${main.applyTime}" />
<input type="hidden" name="approveState" value="${main.approveState}" />
<input type="hidden" name="createTime" value="${main.createTime}" />
<input type="hidden" name="isAvailable" value="${main.isAvailable}" />
<input type="hidden" name="from" value="${from}">
</form>
</div>

<div id="history" class="x-hide-display" ></div>
</body>
</html>
<script type="text/javascript">
var from = '<%=request.getAttribute("popfrom")%>';
var tempTab={};
Ext.onReady(function(){
    
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	rule_ns_ds.load({params:{start:0, limit:5,recordId:'${profitID}'}});
	var effectiveBeginDate = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 100,
		applyTo:'effectiveBeginDate'
   	});
   /* var effectiveEndDate = new Ext.form.DateField({
   		format:'Y-m-d',
   		typeAhead: true,  
   		minValue:'2009-3-1',
   		disabledDates:["../../2009"],
		width: 100,
		allowBlank:false,
		applyTo:'effectiveEndDate'
   	});*/
	
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			collapsible: true,
			height:210,
			items:[{contentEl:'main'}]
		},{
			region:"center",
			layout:'fit',
			title:'附件',
			height:400,
			items:[{contentEl:'rule'}]
		},{
			region:"south",
			layout:'fit',
			buttonAlign:'center',
			buttons:[{
            	text:'保存',
				hidden: ${!save},
            	handler:function(){
            		var param1 = Form.serialize('mainForm');
					var param = param1 + "&action=save";
					new AjaxEngine('profitCenterController.spr', 
						   {method:"post", parameters: param, onComplete: callBackHandle});
            	}
            },{
            	text:'提交',
            	hidden: ${!submit},
            	handler:function(){
            		var isChangeStandard = Ext.getDom('isChangeStandard').value;
					if (isChangeStandard=='1'&&rule_ns_ds.getTotalCount()<=0){
						top.Ext.Msg.show({
							title:'提示',
							msg: '改变标准层次结构的，没有增加会议纪要附件，不能提交！',
							icon: Ext.MessageBox.QUESTION
						});
					} else{
						var param1 = Form.serialize('mainForm');
						var param = param1 + "&action=submitProfitInfo";
						new AjaxEngine('profitCenterController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});
					}  
            	}
            },{
            	text:'关闭',
            	handler:function(){
            	    top.ExtModalWindowUtil.markUpdated();
	                top.ExtModalWindowUtil.close();
	            }
            }]
		}]
	});
	
});//end of Ext.onReady    

var saveFlag = false;
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

function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
//	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	showMsg(customField.returnMessage);
	setTimeout(function(){
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	},1000);	
}

function submitMainForm(){
	setTimeout(function(){
	Ext.Ajax.request({
		url: 'profitCenterController.spr?action=submitProjectInfo&doWorkflow=mainForm',
		params:Form.serialize('mainForm'),
		success: function(response, options){
					var txt = Ext.util.JSON.decode(response.responseText);
					if(txt.success){
						Ext.Msg.alert('提示',txt.msg);
						setTimeout(function(){
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
	},500);
}
function changeStandard(){
	var isChangeStandard = Ext.getDom('isChangeStandard').value;
	if(isChangeStandard!=''&&isChangeStandard=='1')
	{
		Ext.get('rule').setStyle("display","block");
	}
	else{
		Ext.get('rule').setStyle("display","none");
	}
}

</script>
<script>
changeStandard();

</script>
<fiscxd:workflow-taskHistory divId="history" businessRecordId="${profitID}" width="750"></fiscxd:workflow-taskHistory>