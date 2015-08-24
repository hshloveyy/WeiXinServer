/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年03月26日 11点17分54秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象采购订单(OporDoc)管理页面JS文件
 */

/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
	reload_OporDoc_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_OporDoc_grid("");
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
 * 新增采购订单
 */
function _create()
{
	if(_precreate()){
	   var para = "";
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.oporDoc_Create,OporDoc_grid,contextPath + '/sample/b1/opor/oporDocController.spr?action=_create'+ para);
    }
    _postcreate();
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _copyCreate()
{
if(_precopyCreate()){
	if (OporDoc_grid.selModel.hasSelection() > 0){
		var records = OporDoc_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo(Txt.oporDoc_CopyCreate_AllowOnlyOneItemForOperation);
			return;
		}
		var oporDocId = records[0].json.oporDocId;
		_getMainFrame().maintabs.addPanel(Txt.oporDoc_CopyCreate,OporDoc_grid,contextPath + '/sample/b1/opor/oporDocController.spr?action=_copyCreate&oporDocId='+oporDocId);
	}else{
		_getMainFrame().showInfo(Txt.oporDoc_CopyCreate_SelectToOperation);
	}	
  }
  _postcopyCreate();
}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除采购订单
 */
function _deletes()
{
if(_precreate()){
	if (OporDoc_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
				msg: Txt.oporDoc_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
					if (result == 'yes'){
						var records = OporDoc_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.oporDocId + '|';
						}
		
						var param = '&oporDocIds='+ids;
						new AjaxEngine(contextPath + '/sample/b1/opor/oporDocController.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});
	}else{
		_getMainFrame().showInfo(Txt.oporDoc_Deletes_SelectToOperation);
	}
   }
  _postcreate();
}
