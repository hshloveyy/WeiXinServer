/**
  * Author(s):java业务平台代码生成工具
  * Date: 2011年11月25日 11点42分19秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象利率(Rate)管理页面JS文件
 */

/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
	reload_Rate_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_Rate_grid("");
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
 * 新增利率
 */
function _create()
{
	if(_precreate()){
	   var para = "";
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.rate_Create,Rate_grid,contextPath + '/xdss3/rate/rateController.spr?action=_create'+ para);
    }
    _postcreate();
}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除利率
 */
function _deletes()
{
if(_precreate()){
	if (Rate_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
				msg: Txt.rate_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
					if (result == 'yes'){
						var records = Rate_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.rateid + '|';
						}
		
						var param = '&rateIds='+ids;
						new AjaxEngine(contextPath + '/xdss3/rate/rateController.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});
	}else{
		_getMainFrame().showInfo(Txt.rate_Deletes_SelectToOperation);
	}
   }
  _postcreate();
}
