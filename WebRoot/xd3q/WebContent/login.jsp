<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ page import="com.infolion.platform.util.PropertiesUtil"%>

<html>
    <head>
        <META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <%
        String ipRoute = (String)PropertiesUtil.sysProperties.get("IP_ROUTE");
        boolean isRoute = Boolean.parseBoolean(ipRoute);
		//财务部登录，跳转至81端口
		String ip = request.getRemoteAddr();
		String key = ip.substring(0,ip.lastIndexOf("."));

		if(isRoute&&PropertiesUtil.sysProperties.get(key)!=null){
		%>
        <META HTTP-EQUIV="REFRESH" CONTENT="1; URL=http://<%=PropertiesUtil.sysProperties.get(key)%>">
        <%} %>
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
            background-image:url(<%=request.getContextPath()%>/images/fam/application_go.png);
        }
        .but_one
        {
            background-image:url(<%=request.getContextPath()%>/images/fam/rss_load.gif) !important;
        }
        .but_two
        {
            background-image:url(<%=request.getContextPath()%>/images/fam/rss_delete.gif) !important;
        }
        .user
        {
            background:url(<%=request.getContextPath()%>/images/fam/user.png) no-repeat 1px 2px;
        }
        .pass
        {
            background:url(<%=request.getContextPath()%>/images/fam/icon_padlock.png) no-repeat 1px 2px;
        },
        .img
        {
            background:url(<%=request.getContextPath()%>/images/fam/logout.gif) no-repeat 1px 2px;
        }
        .ckimg
        {
            background:url(<%=request.getContextPath()%>/images/fam/logout.gif) no-repeat 1px 2px;
        }
        .user,.pass,.ckimg,.img
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
    Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/images/fam/s.gif';

    var simple = new Ext.form.FormPanel({
        baseCls: 'x-plain',
        el:'div_loginForm',
        frame:true,
        labelWidth: 60,
        id: 'loginForm', 
        name: 'loginForm',
        bodyStyle:'padding:5px 5px 0',
        autoTabs:true,
        activeTab:0,                    
        deferredRender:false,
        border:false,
        defaultType: 'textfield',
        items: [{
            fieldLabel: '用户名',
            name: 's_user_name',
            id:'s_user_name',
            allowBlank:false,
            //value:'admin',
            blankText:'用户名不能为空!',
            anchor:'90%', 
            cls:'user'
        },{
            fieldLabel: '用户密码',
            name: 's_pass_word',
            inputType: 'password',
            //value:'1',
            allowBlank:false,
            blankText:'用户密码不能为空!',
            anchor: '90%',
            cls:'pass'
        },{   
            cls : 'ckimg',   
            name:'randCode',  
            //value: 'XXX',
            id:'randCode',   
            fieldLabel:'验证码',
            maxLength:4,   
            width:80,
            allowBlank:false,
            blankText : '验证码不能为空'  
        }]
    }); 
    
    var win = new Ext.Window({
        title: '系统登录',
        width: 280,
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
            text: '登录',
            iconCls:'but_one',
            handler:function()
            {   
                loginUserPassword();
            }
        },
        {
            text: '取消',
            iconCls:'but_two',
            handler:function(){
                var bd = Ext.getDom('randCode');
    
                var bd2 = bd.parentNode.lastChild;
                bd.parentNode.removeChild(bd2);
                simple.form.reset();
                bd2 = Ext.get(bd.parentNode);
                var timenow = new Date().getTime(); 

                bd2.createChild({tag: 'img', src: 'image.jsp?d='+ timenow,align:'absbottom'});
                simple.form.reset();
            }
        }],
        keys:[{
            key:Ext.EventObject.ENTER,  
            fn:loginUserPassword
        }]
    });

    win.show();
    var bd = Ext.getDom('randCode');     
    var bd2 = Ext.get(bd.parentNode);
    bd2.createChild({tag: 'img', src: '<%=request.getContextPath()%>/image.jsp',align:'absbottom'});

    function loginUserPassword()
    {
        if (simple.form.isValid())
        {
            simple.form.submit(
            {
                url:'<%=request.getContextPath()%>/loginController.spr?action=loginInByUserName', 
                waitTitle:'提示',
                method: 'POST',
                timeout:50000,
                waitMsg:'正在登录验证,请稍候...',
                success:function(form,action)
                {
                    var loginResult = action.result.success;
                    var message = action.result.message;
                    var deptcount = action.result.deptcount;
                    var userid = action.result.userid;
                    if(action.result.isInitUser=='1'){
                            Ext.Msg.show({
                                    title:'提示',
                                    closable:false,
                                    msg:'初次登录,请修改密码',
                                    buttons:{yes:'确定',no:'取消'},
                                    fn:function(btnId,txt){
                                            if(btnId=='yes'){
                                                window.location.href='<%=request.getContextPath()%>/loginController.spr?action=toModifyPsd&userName='+Ext.getCmp('s_user_name').getValue();
                                            }else{
                                                window.location.href='<%=request.getContextPath()%>/loginController.spr?action=loginOut';
                                            }
                                        },
                                    icon:Ext.MessageBox.INFO
                            });
                        
                    }else{
                    if (deptcount == '1')
                        //window.location.href='loginController.spr?action=mainPage';
                        window.location.href='<%=request.getContextPath()%>/indexMainController.spr';
                    else{
                        document.falgform.userid.value = userid;
                        win.close();
                        deptwin.show();
                        deptds.proxy= new Ext.data.HttpProxy({url: contextPath+'/loginController.spr?action=queryLoginDept&userid='+userid});
                        deptds.reload();
                       }
                    }
                },
                failure: function(form,action) 
                {
                    var loginResult = action.result.success;
                    var message = action.result.message;
                    if(loginResult == false)
                    {
                        Ext.MessageBox.alert('提示', message);
                        var bd = Ext.getDom('randCode');
                        var bd2 = bd.parentNode.lastChild;
                        bd.parentNode.removeChild(bd2);
                        simple.form.reset();
                        bd2 = Ext.get(bd.parentNode);
                        var timenow = new Date().getTime(); 
        
                        bd2.createChild({tag: 'img', src: 'image.jsp?d='+ timenow,align:'absbottom'});
                        simple.form.reset();
                    } else{
                        Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
                        simple.form.reset();
                    }
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
       editable:true,
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
            url:contextPath+'/loginController.spr?action=mainPageByDeptId&userid='+document.falgform.userid.value+'&deptid='+deptCombo.getValue(),
            success:function(form,action)
            {
                window.location.href='<%=request.getContextPath()%>/indexMainController.spr';
                //window.location.href='loginController.spr?action=mainPage';
            },
            failure: function(form,action) 
            {
            }
        });
    }
});
</script>

