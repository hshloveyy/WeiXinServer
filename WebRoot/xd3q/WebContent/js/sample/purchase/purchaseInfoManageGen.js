/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年01月09日 11点03分54秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象采购订单(SAP)(PurchaseInfo)管理页面JS文件
 */


/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
	reload_PurchaseInfo_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_PurchaseInfo_grid("");
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
 * 新增采购订单(SAP)
 */
function _create()
{
	if(_precreate()){
	   var para = "";
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.purchaseInfo_Create,PurchaseInfo_grid,contextPath + '/sample/purchase/purchaseInfoController.spr?action=_create'+ para);
    }
    _postcreate();
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _copyCreate()
{
if(_precopyCreate()){
	if (PurchaseInfo_grid.selModel.hasSelection() > 0){
		var records = PurchaseInfo_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo(Txt.purchaseInfo_CopyCreate_AllowOnlyOneItemForOperation);
			return;
		}
		var purchaseinfoId = records[0].json.purchaseinfoId;
		_getMainFrame().maintabs.addPanel(Txt.purchaseInfo_CopyCreate,PurchaseInfo_grid,contextPath + '/sample/purchase/purchaseInfoController.spr?action=_copyCreate&purchaseinfoId='+purchaseinfoId);
	}else{
		_getMainFrame().showInfo(Txt.purchaseInfo_CopyCreate_SelectToOperation);
	}	
  }
  _postcopyCreate();
}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除采购订单(SAP)
 */
function _deletes()
{
if(_precreate()){
	if (PurchaseInfo_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
				msg: Txt.purchaseInfo_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
					if (result == 'yes'){
						var records = PurchaseInfo_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.purchaseinfoId + '|';
						}
		
						var param = '&purchaseInfoIds='+ids;
						new AjaxEngine(contextPath + '/sample/purchase/purchaseInfoController.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});
	}else{
		_getMainFrame().showInfo(Txt.purchaseInfo_Deletes_SelectToOperation);
	}
   }
  _postcreate();
}
