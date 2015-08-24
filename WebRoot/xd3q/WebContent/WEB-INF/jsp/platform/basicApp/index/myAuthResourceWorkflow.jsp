<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>我的授权</title>
</head>
<body>
<div id="div_workflow"></div>
</body>
</html>

<fisc:grid divId="div_workflow" pageSize="20" tableSqlClass="myAuthResourceWorkflowTableSql"
 handlerClass="com.infolion.platform.basicApp.index.web.grid.MyAuthResourceWorkflowGrid"
 whereSql="" needCheckBox="true" needAutoLoad="true" ></fisc:grid>

<script type="text/javascript" defer="defer">
// tableName="(select t.*,'N' as isAuthRemove from YV_myAuthResourceWorkFlow t where t.Nodeid in (${strNodeId}) and t.nodeid not in (select a.resourceid as nodeid from YUSERAUTHMOVE a where a.resourcetype = '4' and a.fromuserId = '${userId}' and a.mandt = '${client}') union select  t.*,'Y' as isAuthRemove from YV_myAuthResourceWorkFlow t where t.Nodeid in (${strNodeId}) and t.nodeid  in (select a.resourceid as nodeid from YUSERAUTHMOVE a where a.resourcetype = '4' and a.fromuserId = '${userId}' and a.mandt = '${client}')) tt"
//全局路径 height="466" width="762" height="390" width="1043"
var context = '<%= request.getContextPath() %>';
Ext.onReady(function(){
    var viewport = new Ext.Viewport({
        layout:'border', 
        items:div_workflow_grid
    });
    viewport.doLayout();  
});

 	
/**
* 回调函数 -权限转移给 
*/
function authRemoveCallBack()
{   
	div_workflow_grid.getStore().reload();

	window.parent.alreadyRemoveColumnTree.body.mask('Loading', 'x-mask-loading');
	window.parent.alreadyRemoveColumnTree.root.reload();
	window.parent.alreadyRemoveColumnTree.root.collapse(true, false);
     setTimeout(function(){
    	 window.parent.alreadyRemoveColumnTree.body.unmask();
     }, 1000); 
}


/**
* 流程-权限转移给
*/
function _workflowAuthRemove()
{
	if (div_workflow_grid.selModel.hasSelection() > 0){
		var records = div_workflow_grid.selModel.getSelections();
		var uuIds ="";
		var bpdefids = "";
		var strmsg = "";
		for (var i=0;i<records.length;i++){
			var isAuthRemove = records[i].data.isAuthRemove;
    		//判断是否已经转移过
			if (isAuthRemove == 'Y'){
				strmsg = strmsg + records[i].data.nodeName + "," ;			
			}
			uuIds = uuIds + records[i].data.nodeid + ",";
			bpdefids = bpdefids + records[i].data.bpdefid + ",";
		}
		if(strmsg != "")
		{
			_getMainFrame().showInfo('流程节点:' + strmsg + " 已经转移过不能再次转移!");
			return;	
		}
		//alert("流程节点ID：" + uuIds + ",流程ID:"  + bpdefids);
	   operType = "workflow";
	   _getMainFrame().ExtModalWindowUtil.show('权限转移给',
				    context + '/index/indexMainController.spr?action=authRemove&operType=workflow&uuIds='+uuIds + '&bpdefids=' + bpdefids,
					'',
					authRemoveCallBack,
					{width:480,height:330});
	}else{
		_getMainFrame().showInfo('请选择要转移的流程信息！');
	}
	
}

</script> 
