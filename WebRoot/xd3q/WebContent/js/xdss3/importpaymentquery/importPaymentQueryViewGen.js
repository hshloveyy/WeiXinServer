/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年10月01日 04点51分07秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象进口付款查询(ImportPaymentQuery)查看页面JS文件
 */

          
function _copyCreateImportPaymentQuery()
{
	if(_precopyCreateImportPaymentQuery()){
		_getMainFrame().maintabs.addPanel(Txt.importPaymentQuery_CopyCreate,'',contextPath + '/xdss3/importpaymentquery/importPaymentQueryController.spr?action=_copyCreate&paymentid='+paymentid);
	}
	_postcopyCreateImportPaymentQuery();
}

          

/**
 * 删除进口付款查询
 */
function _deleteImportPaymentQuery()
{
if(_predeleteImportPaymentQuery()){
		_getMainFrame().Ext.MessageBox.show({
			title:Txt.cue,
		    msg: Txt.importPaymentQuery_Delete_ConfirmOperation,
			buttons: {yes:Txt.ok, no:Txt.cancel},
			icon: Ext.MessageBox.QUESTION,
			fn:function(result){
			   if (result == 'yes'){
					var param = '&paymentid='+paymentid;
					new AjaxEngine(contextPath + '/xdss3/importpaymentquery/importPaymentQueryController.spr?action=_delete', 
					{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
				}
			}
		});
	 }
 	 _postdeleteImportPaymentQuery();
}

/**
 * 删除成功后的回调动作
 */
function _deleteCallBackHandle()
{
	_getMainFrame().showInfo(Txt.operateSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

          

/**
 * 创建按钮调用方法 
 * 新增进口付款查询
 */
function _createImportPaymentQuery()
{
	if(_precreateImportPaymentQuery()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.importPaymentQuery_Create,'',contextPath + '/xdss3/importpaymentquery/importPaymentQueryController.spr?action=_create');
	}
	_postcreateImportPaymentQuery();
}
          
/**
 * 保存 
 */
function _saveOrUpdateImportPaymentQuery()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("ImportPaymentQuery.paymentid").value = id;	
}
          

/**
 * 取消
 */
function _cancelImportPaymentQuery()
{
  if(_precancelImportPaymentQuery()){
	new AjaxEngine(contextPath + '/xdss3/importpaymentquery/importPaymentQueryController.spr?action=_cancel&paymentid='+paymentid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelImportPaymentQueryCallBack});
   }
   _postcancelImportPaymentQuery();
}

function _cancelImportPaymentQueryCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          

/**
 * 提交
 */
function _submitProcessImportPaymentQuery(id)
{
	if(_presubmitProcessImportPaymentQuery()){
		var param = Form.serialize('mainForm');	
		param = param + "&"+ Form.serialize('workflowForm')  + "&type=view";
	
		new AjaxEngine(contextPath +'/xdss3/importpaymentquery/importPaymentQueryController.spr?action=_submitProcess', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	}
	_postsubmitProcessImportPaymentQuery();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
