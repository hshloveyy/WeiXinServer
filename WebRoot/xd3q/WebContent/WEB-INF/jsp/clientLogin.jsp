<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


</head>
<body>
<script>
var success = ${success};
var deptcount = '${deptcount}';
var userid = '${userid}';
var isInitUser = '${isInitUser}';

Ext.onReady(function(){ 
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/images/fam/s.gif';
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
            url:contextPath+'/loginController.spr?action=mainPageByDeptId&userid=${userid}'+'&deptid='+deptCombo.getValue(),
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
if(success==true&&isInitUser=='1'){
    window.location.href='<%=request.getContextPath()%>/loginController.spr?action=toModifyPsd&userName=${userName}';
}
else if(success==true&&deptcount=='1'){
     //window.location.href='loginController.spr?action=mainPage';
     window.location.href='<%=request.getContextPath()%>/indexMainController.spr';
}
else if(success==true&&deptcount>1){
      deptwin.show();
      deptds.proxy= new Ext.data.HttpProxy({url: contextPath+'/loginController.spr?action=queryLoginDept&userid='+userid});
      deptds.reload();
}
else window.location.href='login.jsp';


});
</script>
</body>
</html>