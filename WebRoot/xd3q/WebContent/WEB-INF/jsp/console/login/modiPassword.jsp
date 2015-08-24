<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>
<script type="text/javascript">
Ext.apply(Ext.form.VTypes, {  
  password: function(val, field) {
    if (field.initialPassField) {
      var pwd = Ext.getCmp(field.initialPassField);
      return (val == pwd.getValue());
    }
    return true;
  },
  
  passwordText: '新用户密码和确认密码不一致'
});

function customCallBackHandle(transport){
}

Ext.onReady(function(){	
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
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
	    renderTo:document.body,
	    items: [{
	        fieldLabel: '原密码',
	        name: 'oldpass',
        	id: 'oldpass',
	        allowBlank:false,
	        blankText:'原密码不能为空!初始为1',
	        anchor:'90%'
	    },{
	        fieldLabel: '新密码',
	        name: 'newpass',
	        id:'newpass',
	        allowBlank:false,
	        blankText:'新密码不能为空!',
	        anchor: '90%'
	    },
	    {
	    	fieldLabel: '确认密码',
	        name: 'newpass-cfrm',
	        id:'newpass-cfrm',
	        allowBlank:false,
	        vtype: 'password',
        	initialPassField: 'newpass',
        	anchor: '90%'
	    }
	    ],
	    buttons:[
	    {
	    	text:'确定',
	    	handler:function(){
	    		if(simple.form.isValid()){
	    			simple.getForm().submit({
						url:'orgController.spr?action=updateUserPassword&'+Form.serialize('modiPass'),
						method:'post',
						success:function(form,action){
								Ext.Msg.alert('提示',action.result.success);
							},
						waitMsg: '正在处理中...',
						failure:function(form,action){
								Ext.Msg.alert('提示',action.result.success);
							}
		    		});
	    		}	
	    	}
	    },
	    {
	    	text:'取消',
	    	handler:function(){
	    		simple.form.reset();
	    	}
	    }
	    ]
	});
	
	simple.render();
});
</script>