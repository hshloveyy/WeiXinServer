<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />

		<title>厦门信达业务辅助系统</title>
		<script type="text/javascript" src="<%= request.getContextPath() %>/js/lib/ExtModalWindowUtil.js"></script>

		<style type="text/css">
		html, body {
			font:normal 12px verdana;
			margin:0;
			padding:0;
			border:0 none;
			height:100%;
			background-color: #d2e0f2;
		}
		.window 
		{
   			background-image:url(images/fam/application_go.png);
    	}
    	.but_one
    	{
    		background-image:url(images/fam/rss_load.gif) !important;
    	}
    	.but_two
    	{
    		background-image:url(images/fam/rss_delete.gif) !important;
    	}
    	.user
    	{
    		background:url(images/fam/user.png) no-repeat 1px 2px;
    	}
    	.pass
    	{
    		background:url(images/fam/icon_padlock.png) no-repeat 1px 2px;
    	},
    	.img
    	{
    		background:url(images/fam/logout.gif) no-repeat 1px 2px;
    	}
    	.user,.pass,.img
    	{
			background-color:#FFFFFF;
			padding-left:20px;
			font-weight:bold;
			color:#000000;
		}
		</style>
	</head>
	<body>
		<div id="div_loginForm" style="margin-top:0">
		</div>
		<form action="" name="falgform">
		<input type="hidden" name="userid" id="userid" value=""></input>
		</form>
	</body>
</html>
<script>
Ext.onReady(function(){	
	Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    Ext.BLANK_IMAGE_URL = 'images/fam/s.gif';

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
	        fieldLabel: '原密码',
	        name: 'oldpass',
        	id: 'oldpass',
	        allowBlank:false,
	        blankText:'原密码不能为空!',
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
	        name: 'again',
	        id:'again',
	        allowBlank:false,
        	anchor: '90%'
	    }
	    ]
	});	
	
	var win = new Ext.Window({
        title: '修改初始密码',
        width: 260,
        height:190,
        layout: 'fit',
        iconCls:'window',
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
            handler:function(){
            	var old = Ext.getCmp('newpass').getValue();
            	var again = Ext.getCmp('again').getValue();
            	if(old!=null && again!=null && old==again){
                	if(again.length<5){
                    	Ext.Msg.alert('提示','密码不能少于5位');
                    	return;
                	}	
                	var req = /[A-Za-z]+/;
                	var req1 = /[0-9]+/;
                	if(req.exec(again)==null || req1.exec(again)==null){
                		Ext.Msg.alert('提示','密码必须为数字和字母组成');
                		return;
                    }
    			if(simple.form.isValid()){
    				simple.getForm().submit({
					url:'orgController.spr?action=updateUserPassword&userName=${userName}&'+Form.serialize('modiPass'),
					method:'get',
					success:function(form,action){
							Ext.Msg.alert('提示','修改成功');
							Ext.getCmp('lg').enable();
						},
					waitMsg: '正在处理中...',
					failure:function(form,action){
							Ext.Msg.alert('提示',"修改失败");
						}
	    			});
    			}	
            	}else
            		Ext.Msg.alert('提示','密码不一致');
            }
        },{
            text:'登录',
            id:'lg',
            disabled:true,
            handler:function(){
        		window.location.href='loginController.spr?action=lgAgainMP';
            }
        },{
            text: '退出',
            iconCls:'but_two',
            handler:function(){
        		window.location.href='loginController.spr?action=loginOut';
          }
        }]
    });
    win.show();

    function loginUserPassword()
    {
    	if (simple.form.isValid())
		{
            simple.form.submit(
            {
      			url:'loginController.spr?action=loginUserNewPsd&userName=${userName}&password='+Ext.getCmp('again').getValue(),
      			waitTitle:'提示',
      			method: 'POST',
      			waitMsg:'正在登录验证,请稍候...',
      			success:function(form,action)
      			{
      				var loginResult = action.result.success;
      				var message = action.result.message;
      				var deptcount = action.result.deptcount;
      				var userid = action.result.userid;
          			if (deptcount == '1')
              			window.location.href='loginController.spr?action=mainPage';
              		else{
              			document.falgform.userid.value = userid;
              			win.close();
              			deptwin.show();
              			deptds.proxy= new Ext.data.HttpProxy({url: 'loginController.spr?action=queryLoginDept&userid='+userid});
						deptds.load();
              		}
      			},
      			failure: function(form,action) 
      			{
      				var loginResult = action.result.success;
      				var message = action.result.message;
					//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
				}
      		});
		}
    }
  
    //选择所属部门窗体
    var RecordDef = Ext.data.Record.create([    
       	{name: 'deptId'},
       	{name: 'deptName'}
   	]);
        	
  	var deptds=new Ext.data.Store({    
      	//设定读取的地址
      	proxy: new Ext.data.HttpProxy({url: ''}),    
      	//设定读取的格式    
      	reader: new Ext.data.JsonReader({
          	root:'root',
          	totalProperty:'totalProperty'
      	}, RecordDef),    
      	remoteSort: true   
 	});
  	
    var deptCombo=new Ext.form.ComboBox({
       selectOnFocus:true,
       valueField:'deptId',
       hiddenName:'groupCombo',
       displayField:'deptName',
       fieldLabel: '所属部门',
       blankText:'请选择一个登录部门',
       emptyText:'请选择一个登录部门',
       editable:false,
       anchor:'90%',
       forceSelection:true,
       triggerAction:'all',
       allowBlank:false,
       store:deptds,
       typeAhead: true
  	});
  	
  	
  	var deptsimple = new Ext.form.FormPanel({
    	baseCls: 'x-plain',
    	frame:true,
	    labelWidth: 70,
	    height:120,
	    bodyStyle:'padding:5px 5px 0',
	    autoTabs:true,
	    activeTab:0,                    
	    deferredRender:false,
	    border:false,
	    items: [deptCombo]
	});
	
	var deptwin = new Ext.Window({
        title: '所属部门情况',
        width: 300,
        height:140,
        layout: 'fit',
        iconCls:'window',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        maximizable:false,
		closeAction:'close',
		closable:false,
		collapsible:false,
        items: deptsimple,
        buttons: [
        {
            text: '确认',
            iconCls:'but_one',
            handler:function()
            {
            	loginDept();
            }	
        },
        {
            text: '取消',
            iconCls:'but_two',
            handler:function(){deptsimple.form.reset();}
        }],
        keys:[{
			key:Ext.EventObject.ENTER,  
			fn:loginDept
        }]
    });
    
    function loginDept()
    {
    	deptsimple.form.submit({
			url:'loginController.spr?action=mainPageByDeptId&userid='+document.falgform.userid.value+'&deptid='+deptCombo.getValue(),
            success:function(form,action)
      		{
      			window.location.href='loginController.spr?action=mainPage';
      		},
      		failure: function(form,action) 
      		{
      		}
		});
    }
});
</script>

