<!-- 此JSP功能已经被 extProcessDefMgr.jsp 替代。 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
request.setAttribute("contextPath",request.getContextPath());%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务流程树</title>
</head>
<body>
<div id="treeDiv"></div>
</body>
<script type="text/javascript" defer="defer">
Ext.onReady(function(){
   	
	var addButton = new Ext.Toolbar.Button({
		text:'流程配置复制',
		iconCls:'icon-add',
		handler:function(){
			if(treeDiv_currentNodeId==null||treeDiv_currentNodeId==''){
				_getMainFrame().Ext.MessageBox.show({
					title:'系统提示',
					msg: '请选择“流程配置复制”要用到的流程！',
					buttons: {yes:'确定'},
					icon: Ext.MessageBox.INFO
				});
				//showInfo("请选择复制流程配置要用的流程！");
				return false;
			}
			_getMainFrame().ExtModalWindowUtil.show('请选择要复制配置到哪个流程',
				'${contextPath}/workflow/extProcessDefinitionController.spr?action=view&operationType=copyCreate&extProcessId='+treeDiv_currentNodeId
				+'&parentNodeId='+treeDiv_currentNode.parentNode.id+"&treeName="+escape(escape(treeDiv_currentNode.parentNode.text)),
			'',
			addCallBack,
			{width:320,height:470});
		}
	});

	var addToolbar = new Ext.Toolbar({
		items:[addButton]
	});
      	
   	
	var tabs = new Ext.Panel({
		id:'mainPanel',
        renderTo: document.body,
        autoWidth:false,
        loadMask:true,
        frame:true,
        tbar:addToolbar,
        baseCls:'scarch',
        //defaults:{autoHeight: true},
        items:[
            	{contentEl:'treeDiv',id:'treeDiv1',width:197,height:412}
              ]
    });
});

function addCallBack(){
	
}
</script>
</html>
<fisc:tree tableName="DD07T" sourceClass="boProcessDefinitionTree" height="400"
style="search" width="185" rootValue="YDAPPMODEL" parentColumnName="DOMNAME" titleColumnName="DDTEXT" 
linkUrl="${contextPath}/workflow/extProcessDefinitionController.spr?action=page" target="dateView" 
treeTitle="业务流程树" idColumnName="DOMVALUE_L" divId="treeDiv"></fisc:tree>