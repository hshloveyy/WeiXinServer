/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年06月13日 18点01分19秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象预警对像配置(AdvanceWarnObject)管理页面JS文件
 */

/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
	reload_AdvanceWarnObject_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_AdvanceWarnObject_grid("");
}

/**
 * 清空操作
 */
function _resetForm()
{
  if(_preresetForm()){
		document.all.mainForm.reset();
	}
	_postresetForm();
}



/**
 * grid 上的 创建按钮调用方法 
 * 新增预警对像配置
 */
function _create()
{
	if(_precreate()){
	   var para = "";
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.advanceWarnObject_Create,AdvanceWarnObject_grid,contextPath + '/xdss3/advanceWarn/advanceWarnObjectController.spr?action=_create'+ para);
    }
    _postcreate();
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _copyCreate()
{
if(_precopyCreate()){
	if (AdvanceWarnObject_grid.selModel.hasSelection() > 0){
		var records = AdvanceWarnObject_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo(Txt.advanceWarnObject_CopyCreate_AllowOnlyOneItemForOperation);
			return;
		}
		var warnid = records[0].json.warnid;
		_getMainFrame().maintabs.addPanel(Txt.advanceWarnObject_CopyCreate,AdvanceWarnObject_grid,contextPath + '/xdss3/advanceWarn/advanceWarnObjectController.spr?action=_copyCreate&warnid='+warnid);
	}else{
		_getMainFrame().showInfo(Txt.advanceWarnObject_CopyCreate_SelectToOperation);
	}	
  }
  _postcopyCreate();
}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除预警对像配置
 */
function _deletes()
{
if(_precreate()){
	if (AdvanceWarnObject_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
				msg: Txt.advanceWarnObject_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
					if (result == 'yes'){
						var records = AdvanceWarnObject_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.warnid + '|';
						}
		
						var param = '&advanceWarnObjectIds='+ids;
						new AjaxEngine(contextPath + '/xdss3/advanceWarn/advanceWarnObjectController.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});
	}else{
		_getMainFrame().showInfo(Txt.advanceWarnObject_Deletes_SelectToOperation);
	}
   }
  _postcreate();
}
