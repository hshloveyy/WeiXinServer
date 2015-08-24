<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>个人参数设置</title>
</head>

<body>
<div id="div_toolbar"></div>

<div id="div_top" name="div_top" class="search">
<form id="topFrom" action="" name="topFrom">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%"></td>
		<td colspan="3" width="85%"><br></td>
	</tr>
	<tr>
		<td align="right" width="15%">用户名：</td>
		<td width="30%">
		<input class="inputText"  type="text" name="userName"
			value="${userName}" readonly="readonly"> <input type="hidden"
			name="userId" value="${userId}" /></td>
		<td align="right" width="15%">皮肤：</td>
		<td width="40%">
		<input class="inputText"  type="text" name="skinType"
			value="${personalInf.skinType}" /> <input type="hidden"
			name="defaultLang" value="${personalInf.defaultLang}" /> <input
			type="hidden" name="personalinfId"
			value="${personalInf.personalinfId}" /></td>
	</tr>
	<tr>
		<td align="right" width="15%">启动开始事务：</td>
		<td colspan="1" width="30%">
		<input class="inputText" type="text" name="startTcode"
			value="${personalInf.startTcode}" /></td>
		<td align="right" width="15%">启动菜单：</td>
		<td colspan="1" width="40%">
		<div id="div_startMenu" ></div>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"></td>
		<td  width="85%" colspan="3"><br></td>
	</tr>
</table>
</form>
</div>


<div id="div_personal" name="div_personal" class="search">
<form id="tpersonalFrom" action="" name="tpersonalFrom">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%"></td>
		<td width="85%" colspan="3"><BR></td>
	</tr>
	<tr>
		<td align="right" width="15%">姓名：</td>
		<td width="30%">
			<input class="inputText" type="hidden" name="personnelId" value="${userPersonnel.personnelId}" /> 
			<input class="inputText" type="hidden" name="userPersonnelId" value="${userPersonnel.userPersonnelId}" />
			<input class="inputText" type="text" name="realName" value="${userPersonnel.realName}" /></td>
		<td align="right" width="15%">性别：</td>
		<td width="40%">
		<div id="div_sex"></div>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%">身份证：</td>
		<td width="30%">
			<input class="inputText"  type="text" name="cardId"
			value="${userPersonnel.cardId}" /></td>
		<td align="right" width="15%">电话：</td>
		<td width="40%">
			<input class="inputText" type="text" name="phone"
			value="${userPersonnel.phone}" /></td>
	</tr>
	<tr>
		<td align="right" width="15%">职位：</td>
		<td width="30%">
			<input class="inputText" type="text" name="place" value="${userPersonnel.place}" /></td>
		<td align="right" width="15%">职务：</td>
		<td width="40%">
			<input class="inputText" type="text" name="job" value="${userPersonnel.job}" /></td>
	</tr>
	<tr>
		<td align="right" width="15%">邮件地址：</td>
		<td colspan="3" width="85%" >
			<input class="inputText" type="text" name="email"
			value="${userPersonnel.email}" /></td>
	</tr>
	<tr>
		<td align="right" width="15%" valign="top">地址：</td>
		<td colspan="3" width="85%">
		<textarea cols="49" rows="2" id="address"
			name="address" tabindex="31">${userPersonnel.address}</textarea>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"></td>
		<td colspan="3" width="85%"><BR></td>
	</tr>
</table>
</form>
</div>



<div id="div_password" name="div_password" class="search">
<form id="passwordFrom" action="" name="passwordFrom">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%"></td>
		<td colspan="3" width="85%"><BR></td>
	</tr>
	<tr>
		<td align="right" width="15%">原密码：</td>
		<td colspan="3" width="85%">
			<input class="inputText"  type="password" name="oldpassword" /></td>
	</tr>
	<tr>
		<td align="right" width="15%">新密码：</td>
		<td colspan="3" width="85%">
			<input class="inputText"  type="password" name="newpassword" /></td>
	</tr>
	<tr>
		<td align="right" width="15%">确认新密码：</td>
		<td colspan="3" width="85%">
			<input class="inputText"  type="password" name="validatenewpassword" />
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"></td>
		<td colspan="3" width="85%"><BR></td>
	</tr>
	<tr>
		<td align="right" width="15%"></td>
		<td colspan="3" width="85%"><BR></td>
	</tr>
	<tr>
		<td align="right" width="15%"></td>
		<td colspan="3" width="85%"><BR></td>
	</tr>
	<tr>
		<td align="right" width="15%"></td>
		<td colspan="3" width="85%"><BR></td>
	</tr>
	<tr>
		<td align="right" width="15%"></td>
		<td colspan="3" width="85%"><BR></td>
	</tr>
</table>
</form>
</div>


<div id="div_east"></div>
<div id="div_west"></div>

</body>
</html>
<script type="text/javascript" defer="defer">
/*,
	buttons:[{
    	text:'保存',
    	id:'btn_Save',
    	name:'btn_Save',
    	handler:_save
    },
    {
    	text:'关闭',
    	id:'btn_close',
    	name:'btn_close',
    	handler:closeForm
    }]
 */
//关闭窗体
function _close(){
	Ext.Msg.show({
   		title:'提示',
   		msg: '是否退出并关闭窗口？',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
 			if(buttonId=='yes'){
				_getMainFrame().ExtModalWindowUtil.markUpdated();
				_getMainFrame().ExtModalWindowUtil.close();
			}
   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.QUESTION
	});
}

/**
* 取消
*/
function _cancel()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

//保存
function _save(){
	var param = Form.serialize('topFrom') + "&" + Form.serialize('tpersonalFrom') + "&" + Form.serialize('passwordFrom');
	param += "&action=_savePersonalInf";
	new AjaxEngine('<%=request.getContextPath()%>/index/indexMainController.spr', 
		   {method:"post", parameters: param, onComplete: callBackHandle});
}

//保存回调函数
function customCallBackHandle(transport)
{
  var responseUtil = new AjaxResponseUtils(transport.responseText);
  var msg = responseUtil.getMessage();
  var msgType = responseUtil.getMessageType();
  //if(msgType!="error"){ _getMainFrame().ExtModalWindowUtil.close(); _getMainFrame().showInfo(msg);}
 
}

var toolbars = new Ext.Toolbar({
	items:[
			'-','->','-',
			{id:'_save',text:'保存',handler:_save, iconCls:'ICON-TABLE-SAVE'},'-',
			{id:'_cancel',text:'取消',handler:_cancel,iconCls:'icon-undo'},
			'-',{text:' '}
			],
	renderTo:"div_toolbar"
});


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
	   				            		title:'个人参数信息',
	   				            		layout:'fit',
	   				            		autoScroll: true,
	   				            		border:false,
	   				            		//height:535,
	   				            		contentEl:'div_top'
	   						}
	   						,{
	   									xtype:'tabpanel',
	   					            	//height:310,
	   					            	defaults:{bodyStyle:"background-color:#DFE8F6"},
	   					            	autoScroll: true,
	   									activeTab:0,
	   						            items:[{
				   					            	title:'个人信息',
				   					            	id:'personal',
				   									name:'personal',
				   									autoScroll:true,
				   									border:false,
				   									autoHeight:true,
				   					            	layout:'fit',
				   					            	bodyStyle:"background-color:#DFE8F6",
				   					            	contentEl: 'div_personal'
	   				            				},
	   				            				{
				   					            	title:'口令',
				   					            	id:'password',
				   									name:'password',
				   									border:false,
				   									autoScroll:true,
				   									autoHeight:true,
				   									bodyStyle:"background-color:#DFE8F6",
				   					            	layout:'fit',
				   					            	contentEl: 'div_password'
	   				               				}]
					            	}]
					            	}]
					            	}]
	   	});
 
</script>
<fisc:dictionary dictionaryName="YDSEX" divId="div_sex" hiddenName="sex" value="${userPersonnel.sex}" isNeedAuth="false"></fisc:dictionary>
<fisc:dictionary dictionaryName="YDSTARTMENU" divId="div_startMenu" hiddenName="startMenu" value="${personalInf.startMenu}" isNeedAuth="false"></fisc:dictionary>
