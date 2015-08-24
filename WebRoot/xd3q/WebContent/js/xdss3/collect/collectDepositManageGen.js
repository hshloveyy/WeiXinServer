/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月19日 04点25分38秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象收款(Collect)管理页面JS文件
 */

/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
	reload_Collect_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_Collect_grid("");
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
 * 新增收款
 */
function _create()
{
	if(_precreate()){
	   var para = "";
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.collect_Create,Collect_grid,contextPath + '/xdss3/collect/collectController.spr?action=_create'+ para);
    }
    _postcreate();
}


/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除收款
 */
function _deletes()
{
if(_precreate()){
	if (Collect_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
				msg: Txt.collect_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
					if (result == 'yes'){
						var records = Collect_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.collectid + '|';
						}
		
						var param = '&collectIds='+ids;
						new AjaxEngine(contextPath + '/xdss3/collect/collectController.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});
	}else{
		_getMainFrame().showInfo(Txt.collect_Deletes_SelectToOperation);
	}
   }
  _postcreate();
}
