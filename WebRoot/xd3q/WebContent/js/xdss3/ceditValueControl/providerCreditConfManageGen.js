/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年05月28日 09点10分42秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象供应商信用额度配置(ProviderCreditConf)管理页面JS文件
 */

/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
	reload_ProviderCreditConf_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_ProviderCreditConf_grid("");
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
 * 新增供应商信用额度配置
 */
function _create()
{
	if(_precreate()){
	   var para = "";
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.providerCreditConf_Create,ProviderCreditConf_grid,contextPath + '/xdss3/ceditValueControl/providerCreditConfController.spr?action=_create'+ para);
    }
    _postcreate();
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _copyCreate()
{
if(_precopyCreate()){
	if (ProviderCreditConf_grid.selModel.hasSelection() > 0){
		var records = ProviderCreditConf_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo(Txt.providerCreditConf_CopyCreate_AllowOnlyOneItemForOperation);
			return;
		}
		var configid = records[0].json.configid;
		_getMainFrame().maintabs.addPanel(Txt.providerCreditConf_CopyCreate,ProviderCreditConf_grid,contextPath + '/xdss3/ceditValueControl/providerCreditConfController.spr?action=_copyCreate&configid='+configid);
	}else{
		_getMainFrame().showInfo(Txt.providerCreditConf_CopyCreate_SelectToOperation);
	}	
  }
  _postcopyCreate();
}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除供应商信用额度配置
 */
function _deletes()
{
if(_precreate()){
	if (ProviderCreditConf_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
				msg: Txt.providerCreditConf_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
					if (result == 'yes'){
						var records = ProviderCreditConf_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.configid + '|';
						}
		
						var param = '&providerCreditConfIds='+ids;
						new AjaxEngine(contextPath + '/xdss3/ceditValueControl/providerCreditConfController.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});
	}else{
		_getMainFrame().showInfo(Txt.providerCreditConf_Deletes_SelectToOperation);
	}
   }
  _postcreate();
}
