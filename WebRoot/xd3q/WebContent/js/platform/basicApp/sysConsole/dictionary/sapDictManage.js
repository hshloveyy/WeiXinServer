/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年11月04日 14点42分05秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能> (SapDict)管理页面JS文件
 */


/**
 * 查询
 */
function _search()
{
	var para = Form.serialize('mainForm');
	reload_SapDict_grid(para);
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_SapDict_grid("");
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
 * 新增 
 */
function _create()
{
   var para = "";
	//增加页签
	_getMainFrame().maintabs.addPanel(' 新增',SapDict_grid,contextPath + '/platform/basicApp/sysConsole/dictionary/sapDictController.spr?action=_create'+ para);
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _copyCreate()
{
	if (SapDict_grid.selModel.hasSelection() > 0){
		var records = SapDict_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo('进行【 复制创建】操作时，只允许选择一条记录！');
			return;
		}
		var domName = records[0].json.domName;
		_getMainFrame().maintabs.addPanel('复制创建 ',SapDict_grid,contextPath + '/platform/basicApp/sysConsole/dictionary/sapDictController.spr?action=_copyCreate&domName='+domName);
	}else{
		_getMainFrame().showInfo('请选择需要进行【 复制创建】操作的记录！');
	}	
}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除 
 */
function _deletes()
{
	if (SapDict_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '您选择了【 批量删除】操作，是否确定继续该操作？',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
                   if (result == 'yes'){
		                var records = SapDict_grid.selModel.getSelections();
		                var ids = '';           
		                for(var i=0;i<records.length;i++){
		                    ids = ids + records[i].json.domName + '|';
		                }
		
		                var param = '&sapDictIds='+ids;
		                new AjaxEngine(contextPath + '/platform/basicApp/sysConsole/dictionary/sapDictController.spr?action=_deletes', 
		                {method:"post", parameters: param, onComplete: callBackHandle});
		            }
                }
		});
        
	}else{
		_getMainFrame().showInfo('请选择需要进行【 批量删除】操作的记录！');
	}
}
