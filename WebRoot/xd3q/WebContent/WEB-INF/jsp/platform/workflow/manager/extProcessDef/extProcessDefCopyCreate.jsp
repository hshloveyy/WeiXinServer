<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>复制流程配置</title>
</head>
<body>
<div id="treeDiv"></div>
<div id="div_toolbar"></div>
</body>
<script type="text/javascript" defer="defer">
Ext.onReady(function(){
   	
	var addButton = new Ext.Toolbar.Button({
		text:'复制',
		iconCls:'icon-add',
		handler:function(){
			if(treeDiv_currentNodeId==null||treeDiv_currentNodeId==''){
				_getMainFrame().showInfo("请选择要将流程配置复制到哪个流程！");
				return false;
			}
			_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '是否确定要复制流程配置？复制会替换原有的配置。',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
					if (buttonid == 'yes'){
						var param = '?action=copyCreate&extProcessId=${extProcessId}&toExtProcessId='+treeDiv_currentNodeId;
						//alert(param);
						new AjaxEngine('<%=request.getContextPath()%>/workflow/extProcessDefinitionController.spr', 
								{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
			});
		}
	});
	
	function _close(){
		_getMainFrame().ExtModalWindowUtil.markUpdated();
		_getMainFrame().ExtModalWindowUtil.close();
	}
	
	var addToolbar = new Ext.Toolbar({
		items:['-','->','-',addButton,'-',{id:'_close',text:'取消',handler:_close,iconCls:'icon-undo'},'-'],
		renderTo:"div_toolbar",
		align:'center'
	});
      	
  /** 	
	var tabs = new Ext.Panel({
		id:'mainPanel',
        renderTo: document.body,
        autoWidth:false,
        loadMask:true,
        frame:true,
        baseCls:'scarch',
        //tbar:addToolbar,
        //defaults:{autoHeight: true},
        items:[
	           	addToolbar,
            	{contentEl:'treeDiv',id:'treeDiv1',width:300}
              ]
    });
    **/


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
		   				            height:410,
		   				        	border:false,
		   				            autoScroll:true,
		   				            items:[
		   				              {
		   				            		title:'',
		   				            		layout:'fit',
		   				            		autoScroll: true,
		   				            		border:false,
		   				            		bodyStyle:"background-color:#DFE8F6",
		   				            		contentEl:'treeDiv'
		   						}]
						            	}]
						            	}]
		   	});
	 
});

function customCallBackHandle(transport){
	//alert(transport);
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	_getMainFrame().showInfo(customField.returnMessage);
	setTimeout(function(){
		_getMainFrame().ExtModalWindowUtil.markUpdated();
		_getMainFrame().ExtModalWindowUtil.close();},1500);
}
</script>
</html>
<fisc:tree tableName="WF_PROCESSDEF" 
style="scarch" width="290" rootValue="${processDefinitionName}" parentColumnName="processDefinitionName" titleColumnName="${titleColumnName}" 
linkUrl="" target="" treeTitle="${treeName}" idColumnName="extProcessId" divId="treeDiv" height="410" whereSql=" AND EXTPROCESSID NOT IN ('${extProcessId}')"></fisc:tree>