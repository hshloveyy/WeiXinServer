/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月06日 10点45分08秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象未明户收款(UnnamerCollect)管理页面JS文件
 */

/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
	reload_UnnamerCollect_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_UnnamerCollect_grid("");
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
 * 新增未明户收款
 */
function _create()
{
	if(_precreate()){
	   var para = "";
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.unnamerCollect_Create,UnnamerCollect_grid,contextPath + '/xdss3/unnameCollect/unnamerCollectController.spr?action=_create'+ para);
    }
    _postcreate();
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _copyCreate()
{
if(_precopyCreate()){
	if (UnnamerCollect_grid.selModel.hasSelection() > 0){
		var records = UnnamerCollect_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo(Txt.unnamerCollect_CopyCreate_AllowOnlyOneItemForOperation);
			return;
		}
		var unnamercollectid = records[0].json.unnamercollectid;
		_getMainFrame().maintabs.addPanel(Txt.unnamerCollect_CopyCreate,UnnamerCollect_grid,contextPath + '/xdss3/unnameCollect/unnamerCollectController.spr?action=_copyCreate&unnamercollectid='+unnamercollectid);
	}else{
		_getMainFrame().showInfo(Txt.unnamerCollect_CopyCreate_SelectToOperation);
	}	
  }
  _postcopyCreate();
}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除未明户收款
 */
function _deletes()
{
if(_precreate()){
	if (UnnamerCollect_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
				msg: Txt.unnamerCollect_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
					if (result == 'yes'){
						var records = UnnamerCollect_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.unnamercollectid + '|';
						}
		
						var param = '&unnamerCollectIds='+ids;
						new AjaxEngine(contextPath + '/xdss3/unnameCollect/unnamerCollectController.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});
	}else{
		_getMainFrame().showInfo(Txt.unnamerCollect_Deletes_SelectToOperation);
	}
   }
  _postcreate();
}
