/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年12月04日 10点48分26秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象SAP采购凭证(PurchasingDoc)编辑页面JS文件
 */
 
             
    
  

          

/**
 * 提交
 */
function _submitProcessPurchasingDoc()
{
if(_presubmitProcessPurchasingDoc()){
	var param = Form.serialize('mainForm');	
          
	        	         
	param = param + "&" + getPurchasingDocItemGridData();
	        		param = param + "&"+ Form.serialize('workflowForm');
	
	new AjaxEngine(contextPath +'/sample/purchase/purchasingDocController.spr?action=_submitProcess', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
  }
  _postsubmitProcessPurchasingDoc();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo('提交成功！');
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
/**
 * 取消
 */
function _cancelPurchasingDoc()
{
  if(_precancelPurchasingDoc()){
	new AjaxEngine(contextPath + '/sample/purchase/purchasingDocController.spr?action=_cancel&ebeln='+ebeln, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelPurchasingDocCallBack});
   }
   _postcancelPurchasingDoc();
}
function _cancelPurchasingDocCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
