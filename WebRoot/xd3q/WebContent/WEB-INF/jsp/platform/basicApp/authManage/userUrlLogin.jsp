<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
</head>
<body onload="login()">
<form action="">
	<input type="hidden" name="passWord" id="passWord" value="${passWord}" class="width185">
	<input type="hidden" name="client" id="client" value="${client}" class="width185">
	<input type="text" name="userName" id="userName" value="${userName}" class="width185">
	<input type="text" name="lang" id="lang" value="${lang}" class="width185">
</form>
</body>
<script type="text/javascript">
var modifyPassWordWin ;

Ext.onReady(function(){
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
		        fieldLabel: '输入新密码',
		        name: 'newpassword',
		        id:'newpassword',
		        allowBlank:true,
		        anchor: '90%'
		    },
		    {
		    	fieldLabel: '请再次输入密码',
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
	     		Ext.Msg.alert('提示','请输入新密码!');
	     		return;
	     	}
	     	if(!again)
	     	{
	     		Ext.Msg.alert('提示','请输入确认密码!');
	     		return;
	     	}
	     	if(old!=null && again!=null && old==again){
	         	if(again.length<6){
	             	Ext.Msg.alert('提示','密码长度不够(最短长度：6个字符)!');
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
	     		Ext.Msg.alert('提示','两次输入的密码不一致!');
	     	}
	     }
     
	 modifyPassWordWin = new Ext.Window({
	        title:'修改初始密码',
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
	            text: '修改',
	            iconCls:'but_one',
	            handler:modifyPassWord
	        },{
	            text: '退出',
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
	Ext.Ajax.request({
		waitTitle:'提示',
  		method: 'POST',
  		progress:true,
  		wait:true,
  		waitConfig:{interval:8000},
  		waitMsg:'正在登录验证，请稍候...',
		url: '<%=request.getContextPath()%>/authManage/userLoginController.spr?action=login',
		params:'&userName=${userName}&passWord=${passWord}&client=${client}&lang=${lang}',
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
								//if(resp.sameUserExist)
								//	 existUserMsg();
								//else
							    	 window.location.href='<%=request.getContextPath()%>/index/indexMainController.spr';
						}
					});
				// window.location.href='<%=request.getContextPath()%>/index/indexMainController.spr';
				}
			 }
			 else
		     {
				 Ext.Msg.alert('系统登录失败',responseArray.message);
			 }
		},
		faliue: function(response, options){
			Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
		}

	});
}

</script>
</html>