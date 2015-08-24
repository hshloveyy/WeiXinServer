/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年12月14日 11点16分15秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象固定资产(Fixedasset)管理页面JS文件
 */


/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
	reload_Fixedasset_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_Fixedasset_grid("");
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
 * 新增固定资产
 */
function _create()
{
	if(_precreate()){
	   var para = "";
		//增加页签
		_getMainFrame().maintabs.addPanel('固定资产新增',Fixedasset_grid,contextPath + '/sample/fixedasset/fixedassetController.spr?action=_create'+ para);
    }
    _postcreate();
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _copyCreate()
{
if(_precopyCreate()){
	if (Fixedasset_grid.selModel.hasSelection() > 0){
		var records = Fixedasset_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo('进行【固定资产复制创建】操作时，只允许选择一条记录！');
			return;
		}
		var anln1 = records[0].json.anln1;
		_getMainFrame().maintabs.addPanel('复制创建固定资产',Fixedasset_grid,contextPath + '/sample/fixedasset/fixedassetController.spr?action=_copyCreate&anln1='+anln1);
	}else{
		_getMainFrame().showInfo('请选择需要进行【固定资产复制创建】操作的记录！');
	}	
  }
  _postcopyCreate();
}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除固定资产
 */
function _deletes()
{
if(_precreate()){
	if (Fixedasset_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '您选择了【固定资产批量删除】操作，是否确定继续该操作？',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
					if (result == 'yes'){
						var records = Fixedasset_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.anln1 + '|';
						}
		
						var param = '&fixedassetIds='+ids;
						new AjaxEngine(contextPath + '/sample/fixedasset/fixedassetController.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});
	}else{
		_getMainFrame().showInfo('请选择需要进行【固定资产批量删除】操作的记录！');
	}
   }
  _postcreate();
}
