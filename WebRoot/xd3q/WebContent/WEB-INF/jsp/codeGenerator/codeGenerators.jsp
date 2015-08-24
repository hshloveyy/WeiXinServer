<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
    input{width:153px;}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>生成代码</title>
</head>

<body>
<div id="div_north">
	<div id="div_toolbar"></div>
</div>

<div id="div_mainFrom"  class="search">
<form id="mainFrom" name="mainFrom" >
<table width="100%" > 
<tr>
	<td align="right" width="15%" valign="top">业务对象:</td>
	<td width="30%" colspan="3">
       <textarea rows="5" cols="79" id="boNames" name="boNames"></textarea>
	</td>
</tr>

<tr>
	<td align="right" width="15%"> 模版方案:</td>
	<td width="35%" colspan="3">
		<div id="div_uiTemplate" ></div>
	</td>
</tr>
<tr>
	<td align="right" width="15%">是否生成子业务对象:</td>
	<td width="35%">
		<input type="checkbox" id="isBuildSubBo" name="isBuildSubBo" checked="checked">
	</td>
	<td align="right" width="15%">是否生成JS文件:</td>
	<td width="35%">
	   <input type="checkbox" id="isBuildJs" name="isBuildJs" checked="checked">
	</td>
</tr>
<tr>
	<td align="right" width="15%">是否外部取数:</td>
	<td width="35%">
	   <input type="checkbox" id="outsidegetdata" name="outsidegetdata" readonly="readonly" disabled="disabled">
	</td>
	<td align="right" width="15%">是否外部持久化:</td>
	<td width="35%">
		<input type="checkbox" id="outsidePersistence" name="outsidePersistence"  readonly="readonly" disabled="disabled">
	</td>
</tr>
<tr>
	<td align="right" valign="top" width="15%">模版:</td>
	<td colspan="3">
	    <div id="div_templateFileGrid" ></div>
	</td>
</tr>
<tr>
    <td colspan="4"><br> </td>
</tr>
</table>
</form>
</div>
<div id="div_east"></div>
<div id="div_west"></div>
</body>
</html>

<script type="text/javascript" defer="defer">
//全局路径
var context = '<%= request.getContextPath() %>';
var addschemaWindows ;

Ext.onReady(function(){

   	//handler {id:'schema',text:'添加界面方案',handler: addschema,iconCls:'icon-item-add'},'-',
	var toolbars = new Ext.Toolbar({
		items:[
				{id:'environment',text:'环境设置',handler: setEnvironment,iconCls:'icon-item-edit'},'-',
				'->',
				{id:'CodeGenerate',text:'代码生成',handler: codeGeneerate,iconCls:'icon-export'},'-'
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
				height:26,
				contentEl:'div_north'
			},{
				region:'center',
	            layout:'fit',
	            autoScroll:true,
	            border:false,
	            contentEl:'div_mainFrom'
			}]
   	   	},{
   	   		region:'east',
			width:0,
			contentEl:'div_east'
   	   	},{
   	   		region:'west',
			width:0,
			contentEl:'div_west'
   	   	}]
   	});
	
}); //End Ext.onReady

/**
 * 环境设置 _getMainFrame()
 */
function setEnvironment()
{
	_getMainFrame().ExtModalWindowUtil.show('环境设置',
    '<%= request.getContextPath() %>/codeGenerator/codeGenerateController.spr?action=generateEnvironment',
    '',
    '',
    {width:380,height:212});
}

/**
 * 添加界面方案
 * @type String
 */
function addschema()
{
     var addschemaFrom =  new Ext.form.FormPanel({
        baseCls:'x-plain',
        frame:true,
        autoHeight:true,
        labelWidth:100,
        name:'addschemaFrom',
        id:'addschemaFrom',
        bodyStyle:'padding: 10px 10px 0 10px;',
        items:[{
            id:'CodeGenerateSchema.schemaName',
            name:'CodeGenerateSchema.schemaName',
            width:140,
            allowBlank:true,
            xtype:'textfield',
            fieldLabel:'方案名称',
            blankText:'[方案名称]不能为空!',
            anchor:'95%'
        },{
            id:'CodeGenerateSchema.schemaDesc',
            name:'CodeGenerateSchema.schemaDesc',
            width:140,
            allowBlank:true,
            xtype:'textfield',
            fieldLabel:'方案描述',
            blankText:'[方案描述]不能为空!',
            anchor:'95%'
        }]
    }); 
     
    addschemaWindows = new Ext.Window({
        title: '环境设置',
        width: 360,
        height:140,
        layout: 'fit',
        iconCls:'icon-window',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        maximizable:false,
        closeAction:'close',
        closable:true,
        collapsible:false,
        items: addschemaFrom,
        buttons: [
        {
            text: '确认',
            handler:function()
            {
                if(addschemaFrom.form.isValid()){
                    var param =addschemaFrom.form.getValues() ;
                    //var cc = Ext.util.JSON.decode(param);
                    new AjaxEngine('<%= request.getContextPath() %>/codeGenerator/codeGenerateController.spr?action=_addschema', 
                    {method:"post", parameters: param, onComplete: callBackHandle,callback:callback});
                }
            }   
        },
        {
            text: '取消',
            handler:function()
            {
                addschemaWindows.close();
            }
        }]
    });
    
    addschemaWindows.show();
}

/**
 * 自定义回调函数
 */
function callback(transport)
{
  var responseUtil = new AjaxResponseUtils(transport.responseText);
  var msg = responseUtil.getMessage();
  var msgType = responseUtil.getMessageType();
  
  if(msgType!="error"){addschemaWindows.close();}
}

/**
 * 代码生成
 * @type String
 */
function codeGeneerate()
{       
    var boNames = $('boNames').value;
    if(!boNames)
    {
    	_getMainFrame().showInfo("[业务对象]，不能为空！请输入要代码生成的业务对象名称，多个业务对象[,]符号隔开！");
        return ;
    }
    if(!dict_div_uiTemplate.getValue())
    {
    	_getMainFrame().showInfo("[模版方案]，不能为空！请选择模版方案！");
        return ;  
    }

    var templateFileIds = "" ;
	var records = div_templateFileGrid_grid.getSelectionModel().getSelections();   
    
	//alert(Ext.util.JSON.decode(records[0].data));
		
    if(records.size()<1)
    {
    	_getMainFrame().showInfo("[模版]，不能为空！请选择模版！");
        return ;  
    }
    
	for(i=0;i<records.size();i++)
    {
		templateFileIds = templateFileIds + records[i].data.templateid+ ","
    }
    //是否生成JS文件
    var buildJs = $('isBuildJs').checked ;
    //是否生成子业务对象
    var buildSubBo = $('isBuildSubBo').checked ; 
    var param = Form.serialize('mainFrom');
    //alert(templateFileIds + ",isBuildJs:" + isBuildJs);
    param = param + "&templateFileIds=" + templateFileIds + "&buildJs=" + buildJs + "&buildSubBo=" + buildSubBo;
    //alert(param);
	new AjaxEngine('<%= request.getContextPath() %>/codeGenerator/codeGenerateController.spr?action=_codeGeneerates', 
	{method:"post", parameters: param, onComplete: callBackHandle,callback:callback});
   
    //alert(div_businessObject_sh.getValue());(select * from ytemplatefile where templateid not like '%JS%')t
}
</script>

<fisc:grid divId="div_templateFileGrid" pageSize="20" tableName=" YTEMPLATEFILE T"
 handlerClass="com.infolion.platform.codeGenerator.web.grid.TemplateFileGrid" defaultCondition="T.TEMPLATEID not in('5JS','6JS','7JS','8JS','9JS','10JS','11JS','5JSB','6JSB','7JSB','8JSB','9JSB','10JSB','11JSB','2B','3B','4B')" whereSql=" and 1=1" needCheckBox="true" needAutoLoad="true" height="365" width="720"></fisc:grid> 
<fisc:dictionary hiddenName="schemaName" dictionaryName="YDUITEMPLATE" divId="div_uiTemplate"  isNeedAuth="false"  value="default"></fisc:dictionary>
