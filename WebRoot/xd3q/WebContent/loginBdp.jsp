<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.infolion.platform.dpframework.language.LangConstants"%>
<%@page import="com.infolion.platform.dpframework.language.LanguageService"%>
<%@page import="com.infolion.platform.dpframework.util.StringUtils"%>
<%@page import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants"%>
<%@ include file="/common/commons.jsp"%>
<%
//正在登录验证，请稍候...
String loginWait=LanguageService.getText(LangConstants.SYS_LOGINWAIT);
//集团/公司
String clientText=LanguageService.getText(LangConstants.SYS_CLIENT);
//语言
String langText=LanguageService.getText(LangConstants.SYS_LANG);
//用户名
String userNameText=LanguageService.getText(LangConstants.SYS_USER_NAME);
//密码
String passWordText=LanguageService.getText(LangConstants.SYS_PASSWORD);
//登陆
String loginText=LanguageService.getText(LangConstants.SYS_LOGIN);
//系统登录界面
String loginPageTiele=LanguageService.getText(LangConstants.SYS_LOGINPAGE);
//新密码
String newpasswordText=LanguageService.getText(LangConstants.SYS_NEWPASSWORD);
//修改初始密码
String modifypasswordText=LanguageService.getText(LangConstants.SYS_MODIFYPSTITLE);
//确认密码
String validatenewpasswordText=LanguageService.getText(LangConstants.SYS_VALIDATEPASSWORD);
//修改
String modifyText=LanguageService.getText(LangConstants.SYS_MODIFY);
//退出
String exitText=LanguageService.getText(LangConstants.SYS_EXIT);
String userNameNotBlank=SysMsgHelper.getDPMessage(SysMsgConstants.UserNameNotBlank);
String passWordNotBlank=SysMsgHelper.getDPMessage(SysMsgConstants.PassWordNotBlank);
String clientNotBlank=SysMsgHelper.getDPMessage(SysMsgConstants.ClientNotBlank);

%>
<html>
<head>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>系统登录界面</title>
<link rel="stylesheet" type="text/css"
	href='<%= request.getContextPath() %>/css/login.css'>
<style>
input {
	width: 160px;
}
</style>
<script type="text/javascript">
var modifyPassWordWin ;

Ext.onReady(function(){	

	Ext.Ajax.on('beforerequest', showSpinner, this);
	Ext.Ajax.on('requestcomplete', hiddenSpinner, this);
    Ext.Ajax.on('requestexception', hiddenSpinner, this);

	function showSpinner()
	{
		Ext.MessageBox.show({
	           msg: '<%=loginWait%>',
	           progressText: '<%=waitMsg%>',
	           width:300,
	           wait:true,
	           waitConfig: {interval:1000}
	       });
	}
	
	function hiddenSpinner()
	{
		Ext.MessageBox.hide();
	}

	/**
	 * 自定义回调函数
	 */
	function callback(transport)
	{
	  var responseUtil = new AjaxResponseUtils(transport.responseText);
	  var msg = responseUtil.getMessage();
	  var msgType = responseUtil.getMessageType();
      if(msgType!="error"){ 
           window.location.href='<%=request.getContextPath()%>/index/indexMainController.spr';
      }
	}
	
	 var simple = new Ext.form.FormPanel({
	       	baseCls: 'x-plain',
	    	frame:true,
		    labelWidth: 80,
		    width:200,
		    id: 'modiPass', 
		    name: 'modiPass',
		    bodyStyle:'padding:5px 5px 0',
		    autoTabs:true,
		    activeTab:0,                    
		    deferredRender:false,
		    border:false,
		    defaults: {
	        	inputType: 'password'
	      	},
	      	defaultType: 'textfield',
		    items: [{
	            xtype:'hidden',  
	            inputType: 'hidden',
		        name: 'oldpassword',
	        	id: 'oldpassword'
		        	
		    },
		    {
	            xtype:'hidden',  
	            inputType: 'hidden',
		        name: 'userId',
	        	id: 'userId'
		    }
		    ,{
		        fieldLabel: '<%=newpasswordText%>',
		        name: 'newpassword',
		        id:'newpassword',
		        allowBlank:true,
		        anchor: '90%'
		    },
		    {
		    	fieldLabel: '<%=validatenewpasswordText%>',
		        name: 'validatenewpassword',
		        id:'validatenewpassword',
		        allowBlank:true,
	        	anchor: '90%'
		    }
		    ]
		});	

	 function modifyPassWord(){
     	var old = Ext.getCmp('newpassword').getValue();
     	var again = Ext.getCmp('validatenewpassword').getValue();
     	if(!old)
     	{
     		_getMainFrame().showInfo('请输入新密码！');
     		return;
     	}
     	if(!again)
     	{
     		_getMainFrame().showInfo('请输入确认密码！');
     		return;
     	}
     	if(old!=null && again!=null && old==again){
         	if(again.length<6){
         		_getMainFrame().showInfo('密码长度不够(最短长度：6个字符)！');
             	Ext.getDom('newpassword').value = "";
             	Ext.getDom('validatenewpassword').value = "";
             	return;
         	}	
         	/**
         	var req = /[A-Za-z]+/;
         	var req1 = /[0-9]+/;
         	if(req.exec(again)==null || req1.exec(again)==null){
         		Ext.Msg.alert('提示','密码必须为数字和字母组成');
         		return;
             }
             **/
			if(simple.form.isValid()){
				var param = Form.serialize('modiPass');
				param += "&action=updateUserPassword";
				new AjaxEngine('<%=request.getContextPath()%>/index/indexMainController.spr', 
					   {method:"post", parameters: param, onComplete: callBackHandle,callback:callback});
				}	
     	}
     	else
     	{
         	Ext.getDom('newpassword').value = "";
         	Ext.getDom('validatenewpassword').value = "";
         	_getMainFrame().showInfo('两次输入的密码不一致！');
     	}
     }
     
	 modifyPassWordWin = new Ext.Window({
	        title:'<%=modifypasswordText%>',
	        width: 268,
	        height:150,
	        layout: 'fit',
	        iconCls:'icon-window',
	        plain:true,
	        modal:true,
	        bodyStyle:'padding:5px;',
	        buttonAlign:'center',
	        maximizable:false,
			closeAction:'close',
			closable:false,
			collapsible:false,
	        items: simple,
	        buttons: [
	        {
	            text: '<%=modifyText%>',
	            iconCls:'but_one',
	            handler:modifyPassWord
	        },{
	            text: '<%=exitText%>',
	            iconCls:'but_two',
	            handler:function(){
	        		window.location.href='<%=request.getContextPath()%>/authManage/userLoginController.spr?action=logout';
	          }
	        }]
	        ,
	        keys:[{
				key:Ext.EventObject.ENTER,  
				fn:modifyPassWord
	        }]
	    });
        
});

function login()
{ 
   var userName = Ext.getDom('userName').value;
   var passWord = Ext.getDom('passWord').value;
   var client = Ext.getDom('client').value ;
   
   if(!userName || userName=='')
   {
	  _getMainFrame().showInfo('<%=userNameNotBlank%>');
	  return; 
   }
   if(!passWord || passWord=='')
   {
	   _getMainFrame().showInfo('<%=passWordNotBlank%>');
	   return;
   }  
   if(!client || client=='')
   {
	   _getMainFrame().showInfo('<%=clientNotBlank%>');
	   return;
   }
	   
	Ext.Ajax.request({
		waitTitle:'<%=waitMsg%>',
  		method: 'POST',
  		progress:true,
  		wait:true,
  		waitConfig:{interval:8000},
  		waitMsg:'<%=loginWait%>',
		url: '<%=request.getContextPath()%>/authManage/userLoginController.spr?action=login',
		params:'&userName='+ userName +'&passWord='+ passWord +'&client='+ client +'&languageIso=' + Ext.getDom('languageIso').value,
		success: function(response, options){
		     var responseArray = Ext.util.JSON.decode(response.responseText);

			 if(responseArray.success==true){
				 var userId = responseArray.userId;
   				if(responseArray.isinitialpw=='Y'){
   					modifyPassWordWin.show();
   					Ext.getDom('oldpassword').value = Ext.getDom('passWord').value;
   					Ext.getDom('userId').value = userId;
				}
				else{
					Ext.Ajax.request({
						url: '<%= request.getContextPath() %>/platform/basicApp/sysConsole/userOnline/onlineUserController.spr?action=processExistUser',
						progress:true,
						method: 'POST',
						wait:true,
						success: function(response, options){
							     var resp = Ext.util.JSON.decode(response.responseText);
							     if(resp.sameUserExist)
							    	 existUserMsg();
							     else
							     	window.location.href='<%=request.getContextPath()%>/index/indexMainController.spr';
							     	//window.location.href='<%=request.getContextPath()%>/cognos.jsp';
						}
					});
				}
			 }
			 else
		     {
				 _getMainFrame().showInfo(responseArray.message);
			 }
		},
		faliue: function(response, options){
			_getMainFrame().showInfo('与服务器交互失败，请查看具体提示原因！');
		}

	});
}
function existUserMsg()
{
	var aboutWin = new Ext.Window({
       // title: '用户登陆管理',
        width: 600,
        height:400,
        layout: 'fit',
        closable:false,
        border:false,
        modal:true,
        maximizable:false,  //允许最大化
        resizable: false,  //不可调整大小
        html:'<iframe id="about" name="about" src="<%= request.getContextPath()%>/platform/basicApp/sysConsole/userOnline/onlineUserController.spr?action=showExistUserMsg" frameBorder="0" scrolling="auto" width="100%" height="100%"></iframe>'
    });
	aboutWin.show();
	aboutWin.center();
	aboutWin.focus();
/*	
	 _getMainFrame().ExtModalWindowUtil.show('用户登陆管理',
			    '<%= request.getContextPath()%>/platform/basicApp/sysConsole/userOnline/onlineUserController.spr?action=showExistUserMsg',
				'',
				'',
				{width:600,height:400,closable:false,maximizable:false});
*/	
}

</script>

</head>
<div id="mainbody">
	<body style="overflow: hidden">
	<div id="logincontainer">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="l_bgtop1">&nbsp;</td>
			<td class="l_bgtop2">&nbsp;</td>
		</tr>
		<tr>
			<td class="l_bgmiddle1">&nbsp;</td>
			<td class="l_bgmiddle2" valign="top">
				<table width="310" border="0" align="left" cellpadding="0"
					cellspacing="2">
					<tr>
						<td height="30">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td class="aling_r"><%=clientText%>：</td>
						<td><input type="text" name="client" id="client" value="800"
							class="width185"></td>
					</tr>
					<tr>
						<td class="aling_r"><%=userNameText%>：</td>
						<td><input type="text" name="userName" id="userName"
							value="dpadmin" class="width185"></td>
					</tr>
					<tr>
						<td class="aling_r"><%=passWordText%>：</td>
						<td><input type="password" name="passWord" id="passWord"
							value="ilovedp" class="width185"></td>
					</tr>
					<tr>
						<td class="aling_r"><%=langText%>：</td>
						<td><input type="text" name="languageIso" id="languageIso" value="ZH"
							class="width185"></td>
					</tr>
					<tr>
						<td class="aling_r"></td>
						<td><input type="hidden" name="textfield4" id="textfield4"
							class="width185"></td>
					</tr>
					<tr>
						<td colspan="2" class="aling_c" height="50">
						<div class="login_but"> <input type="submit"
							class="buttonBg_large" value='<%=loginText%>'
							onMouseOver="this.className='buttonBg_large_over'"
							onMouseOut="this.className='buttonBg_large'" style="height: 31px;"
							onclick="login();">&nbsp;&nbsp; <input type="reset"
							class="buttonBg_large" value='<%=cancelText%>'
							onMouseOver="this.className='buttonBg_large_over'"
							onMouseOut="this.className='buttonBg_large'" style="height: 31px;">
						</div>
						</td>
					</tr>
				</table>
	
			</td>
		</tr>
		<tr>
			<td colspan="2" valign="bottom" class="l_bgbottom"></td>
		</tr>
	</table>
	
	</div>
	<div id="footer">版权信息描述</div>
	</body>
</div>
</HTML>
