/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年04月19日 11点09分35秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象采购订单(PurchaseOrder)管理页面JS文件
 */

/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
 
    var certificateDateMax = $('certificateDate_maxValue').value;
    var certificateDateMin = $('certificateDate_minValue').value;
    if(certificateDateMax && certificateDateMin)
    {
	    if(certificateDateMax<certificateDateMin)
	    {
           _getMainFrame().showInfo(Txt.certificateDate_EndDateShouldLargerStartDate);
	       return;
	    }
    }
    
	reload_PurchaseOrder_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_PurchaseOrder_grid("");
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
	   var aaa="1";
	   para = para + "&aaa=" + aaa;
	   var ccc="2";
	   para = para + "&ccc=" + ccc;
	   var ddd="a";
	   para = para + "&ddd=" + ddd;
	   //开始取得从_manage网址上带过来的参数，并传递给控制器_create方法。
	   para = para + "&aa=" + aa;
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.purchaseOrder_Create,PurchaseOrder_grid,contextPath + '/sample/orderManage/purchaseOrder/purchaseOrderController.spr?action=_create'+ para);
    }
    _postcreate();
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _copyCreate()
{
if(_precopyCreate()){
	if (PurchaseOrder_grid.selModel.hasSelection() > 0){
		var records = PurchaseOrder_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo(Txt.purchaseOrder_CopyCreate_AllowOnlyOneItemForOperation);
			return;
		}
		var purchaseOrderId = records[0].json.purchaseOrderId;
		_getMainFrame().maintabs.addPanel(Txt.purchaseOrder_CopyCreate,PurchaseOrder_grid,contextPath + '/sample/orderManage/purchaseOrder/purchaseOrderController.spr?action=_copyCreate&purchaseOrderId='+purchaseOrderId);
	}else{
		_getMainFrame().showInfo(Txt.purchaseOrder_CopyCreate_SelectToOperation);
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
	if (PurchaseOrder_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
				msg: Txt.purchaseOrder_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
					if (result == 'yes'){
						var records = PurchaseOrder_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.purchaseOrderId + '|';
						}
		
						var param = '&purchaseOrderIds='+ids;
						new AjaxEngine(contextPath + '/sample/orderManage/purchaseOrder/purchaseOrderController.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});
	}else{
		_getMainFrame().showInfo(Txt.purchaseOrder_Deletes_SelectToOperation);
	}
   }
  _postcreate();
}
