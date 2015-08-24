<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>代码生成环境配置</title>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_mainFrom" name="div_mainFrom" class="search">
<form id="mainFrom" action="" name="mainFrom">
<table width="99%" height="100%"  align="center" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td align="right" width="30%">J2EE工程路径：</td>
        <td>
             <input type="text" name="GenerateEnvironment.projectPath" id="GenerateEnvironment.projectPath" value="${env.projectPath}" />
        </td>
    </tr>
    <tr>
        <td align="right" width="30%">工程名：</td>
        <td>
             <input type="text" name="GenerateEnvironment.projectName" id="GenerateEnvironment.projectName" value="${env.projectName}" />
        </td>
    </tr>
    <tr>
        <td align="right" width="30%">web目录名：</td>
        <td>
             <input type="text" name="GenerateEnvironment.webContentName" id="GenerateEnvironment.webContentName" value="${env.webContentName}" />
        </td>
    </tr>
    <tr>
        <td align="right" width="30%">模版路径：</td>
        <td>
             <input type="text" name="GenerateEnvironment.templatePath" id="GenerateEnvironment.templatePath" value="${env.templatePath}" />
        </td>
    </tr>
   <tr>
        <td align="right" width="30%">当前用户：</td>
        <td>
             <input type="text" name="GenerateEnvironment.currentUserName" id="GenerateEnvironment.currentUserName" value="${env.currentUserName}" readonly="readonly"/>
             <input type="hidden" name="GenerateEnvironment.currentUser" id="GenerateEnvironment.currentUser" value="${env.currentUser}" />
             <input type="hidden" name="GenerateEnvironment.envId" id="GenerateEnvironment.envId" value="${env.envId}" />
       </td>
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

Ext.onReady(function(){
	//整体布局
  	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:'center',
			layout:'border',
			border:false,
			buttonAlign:'center',
			items:[{
					region:'north',
					height:0,
					border:false,
					contentEl:'div_toolbar'
				},{
					region:'center',
					border:false,
		            layout:'fit',
		            contentEl:'div_mainFrom'
				}],
		    buttons:[{
		        	text:'确定',
		        	id:'btn_Save',
		        	name:'btn_Save',
		        	minWidth: 20,
		        	handler:function(){
						var param = Form.serialize('mainFrom') ;
						//alert(param);
		                //var cc = Ext.util.JSON.decode(param);
		                new AjaxEngine('<%= request.getContextPath() %>/codeGenerator/codeGenerateController.spr?action=_save', 
		                {method:"post", parameters: param, onComplete: callBackHandle,callback:callback});
	        	    }
	        	
		        },
		        {
		        	text:'取消',
		        	id:'btn_cancel',
		        	minWidth: 20,
		        	name:'btn_cancel',
		        	handler:function(){
		        		_getMainFrame().ExtModalWindowUtil.close();
		        	}
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
   
   
    
    //viewport.doLayout();   
 
}); //End Ext.onReady

/**
 * 自定义回调函数
 */
function callback(transport)
{
  var responseUtil = new AjaxResponseUtils(transport.responseText);
  var msg = responseUtil.getMessage();
  var msgType = responseUtil.getMessageType();
  
  if(msgType!="error"){ _getMainFrame().ExtModalWindowUtil.close(); _getMainFrame().showInfo(msg);}
}
</script>

