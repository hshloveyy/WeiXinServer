<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>我的授权</title>
</head>
<body>
  	<div id="div_method"></div>
</body>
</html>
<fisc:grid divId="div_method" pageSize="20" tableSqlClass="myAuthResourceMethodTableSql" handlerClass="com.infolion.platform.basicApp.index.web.grid.MyAuthResourceMethodGrid"
 whereSql="" needCheckBox="true" needAutoLoad="true" ></fisc:grid>
 
<script type="text/javascript" defer="defer">
//tableName="(select c.authresourceid,c.methodname,c.methoddesc,c.methodtype,'Y' as isAuthRemove from YUSERROLE a, YROLEMETHODRES b, YAUTHRESOURCE c where a.Roleid = b.Roleid  and b.authresourceid = c.authresourceid  and a.mandt = '${client}' and a.userid = '${userId}' and c.authresourceid in(SELECT e.resourceid from YUSERAUTHMOVE e where e.fromuserid = '${userId}' and e.mandt = '${client}'and e.resourcetype = '3') union select c.authresourceid,c.methodname,c.methoddesc,c.methodtype,'N' as isAuthRemove from YUSERROLE a, YROLEMETHODRES b, YAUTHRESOURCE c where a.Roleid = b.Roleid  and b.authresourceid = c.authresourceid  and a.mandt = '${client}' and a.userid = '${userId}' and c.authresourceid not in(SELECT e.resourceid from YUSERAUTHMOVE e where e.fromuserid = '${userId}' and e.mandt = '${client}'and e.resourcetype = '3')) t"
//全局路径  height="466" width="762" height="390" width="1043"
var context = '<%= request.getContextPath() %>';

Ext.onReady(function(){

    var viewport = new Ext.Viewport({
        layout:'border',
        items:div_method_grid
    });
    viewport.doLayout();   
});

 	
/**
* 回调函数 -权限转移给 
*/
function authRemoveCallBack()
{   
	div_method_grid.getStore().reload();

	window.parent.alreadyRemoveColumnTree.body.mask('Loading', 'x-mask-loading');
	window.parent.alreadyRemoveColumnTree.root.reload();
	window.parent.alreadyRemoveColumnTree.root.collapse(true, false);
     setTimeout(function(){
    	 window.parent.alreadyRemoveColumnTree.body.unmask();
     }, 1000); 
}

/**
* 方法-权限转移给
*/       
function _methodAuthRemove() 
{
	if (div_method_grid.selModel.hasSelection() > 0){
		var records = div_method_grid.selModel.getSelections();
		var uuIds ="";
		var strmsg ="";
		for (var i=0;i<records.length;i++){
			var isAuthRemove = records[i].data.isAuthRemove;
			//判断是否已经转移过
			if (isAuthRemove == 'Y'){
				strmsg = strmsg + records[i].data.methodname + ",";	
			}
			uuIds = uuIds + records[i].data.authresourceid + ",";
		}
		if(strmsg != "")
		{
			_getMainFrame().showInfo('方法名:' + strmsg + " 已经转移过不能再次转移!");
			return;	
		}
		
		_getMainFrame().ExtModalWindowUtil.show('权限转移给',
				    context + '/index/indexMainController.spr?action=authRemove&operType=method&uuIds='+uuIds,
					'',
					authRemoveCallBack,
					{width:480,height:330});
	}else{
		_getMainFrame().showInfo('请选择要转移的方法信息！');
	}
}

</script> 
