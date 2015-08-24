<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>我的授权</title>
</head>
<body>
<div id="div_role"></div>
</body>
</html>
 <fisc:grid divId="div_role" pageSize="20" tableName="(select b.* from YV_MYAUTHRESOURCEROLE b  where b.MANDT='${client}' and b.USERID='${userId}') T" 
 tableSqlClass="" handlerClass="com.infolion.platform.basicApp.index.web.grid.MyAuthResourceRoleGrid"
 whereSql=""  needCheckBox="true" needAutoLoad="true" needToolbar="true"></fisc:grid>
 
<script type="text/javascript" defer="defer">
//全局路径  height="466" width="763"  height="390" width="1043" 
var context = '<%= request.getContextPath() %>';

Ext.onReady(function(){

    var viewport = new Ext.Viewport({
        layout:'border',
        items:div_role_grid
    });
    viewport.doLayout();   
});

 	
/**
* 回调函数 -权限转移给 
*/
function authRemoveCallBack()
{   
	div_role_grid.getStore().reload();

	window.parent.alreadyRemoveColumnTree.body.mask('Loading', 'x-mask-loading');
	window.parent.alreadyRemoveColumnTree.root.reload();
	window.parent.alreadyRemoveColumnTree.root.collapse(true, false);
     setTimeout(function(){
    	 window.parent.alreadyRemoveColumnTree.body.unmask();
     }, 1000); 
}

/**
* 角色-权限转移给
*/
function _roleAuthRemove()
{
	if (div_role_grid.selModel.hasSelection() > 0){
		var records = div_role_grid.selModel.getSelections();
		var uuIds ="";
		var strmsg ="";
		for (var i=0;i<records.length;i++){
			var isAuthRemove = records[i].data.isAuthRemove;
			//判断是否已经转移过
			if (isAuthRemove == 'Y'){
				strmsg = strmsg + records[i].data.ROLENAME + ",";	 	
			}
			uuIds = uuIds + records[i].data.ROLEID + ",";
		}
		if(strmsg != "")
		{
			_getMainFrame().showInfo('角色名:' + strmsg + " 已经转移过不能再次转移!");
			return;	
		}
	   operType = "role";
	   _getMainFrame().ExtModalWindowUtil.show('权限转移给',
				    context + '/index/indexMainController.spr?action=authRemove&operType=role&uuIds='+uuIds,
					'',
					authRemoveCallBack,
					{width:480,height:330});
	}else{
	   _getMainFrame().showInfo('请选择要转移的角色信息！');
	}
}


</script> 
