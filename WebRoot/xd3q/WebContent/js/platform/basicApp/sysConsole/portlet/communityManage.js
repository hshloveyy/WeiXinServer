/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年10月16日 09点40分31秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>社区管理(Community)管理页面JS文件
 */


/**
 * 查询
 */
function _search()
{
	var para = Form.serialize('mainForm');
	reload_Community_grid(para);
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_Community_grid("");
}

/**
 * 清空操作
 */
function _resetForm()
{
	document.all.mainForm.reset();
}


/**
 * grid 上的 创建按钮调用方法 
 * 新增社区管理
 */
function _create()
{
   var para = "";
	//增加页签
	_getMainFrame().maintabs.addPanel('社区管理新增',Community_grid,contextPath + '/platform/basicApp/sysConsole/portlet/communityController.spr?action=_create'+ para);
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _copyCreate()
{
	if (Community_grid.selModel.hasSelection() > 0){
		var records = Community_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo('进行【社区管理复制创建】操作时，只允许选择一条记录！');
			return;
		}
		var communityid = records[0].json.communityid;
		_getMainFrame().maintabs.addPanel('复制创建社区管理',Community_grid,contextPath + '/platform/basicApp/sysConsole/portlet/communityController.spr?action=_copyCreate&communityid='+communityid);
	}else{
		_getMainFrame().showInfo('请选择需要进行【社区管理复制创建】操作的记录！');
	}	
}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除社区管理
 */
function _deletes()
{
	if (Community_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '您选择了【社区管理批量删除】操作，是否确定继续该操作？',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
                      if (result == 'yes'){
			                var records = Community_grid.selModel.getSelections();
			                var ids = '';           
			                for(var i=0;i<records.length;i++){
			                    ids = ids + records[i].json.communityid + '|';
			                }
			
			                var param = '&communityIds='+ids;
			                new AjaxEngine(contextPath + '/platform/basicApp/sysConsole/portlet/communityController.spr?action=_deletes', 
			                {method:"post", parameters: param, onComplete: callBackHandle});
			            }
                }
		});
	}else{
		_getMainFrame().showInfo('请选择需要进行【社区管理批量删除】操作的记录！');
	}
}
