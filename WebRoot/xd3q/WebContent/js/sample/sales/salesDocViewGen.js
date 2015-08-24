/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年12月04日 16点13分45秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象SAP销售订单(SalesDoc)查看页面JS文件
 */
          
   
    
  

          

/**
 * 提交
 */
function _submitProcessSalesDoc(id)
{
	if(_presubmitProcessSalesDoc()){
		var param = Form.serialize('mainForm');	
	            
		        	         
		param = param + "&" + getSalesDocItemGridData();
		        			param = param + "&"+ Form.serialize('workflowForm');
	
		new AjaxEngine(contextPath +'/sample/sales/salesDocController.spr?action=_submitProcess', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	}
	_postsubmitProcessSalesDoc();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo('提交成功！             ');
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          

/**
 * 取消
 */
function _cancelSalesDoc()
{
  if(_precancelSalesDoc()){
	new AjaxEngine(contextPath + '/sample/sales/salesDocController.spr?action=_cancel&vbeln='+vbeln, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelSalesDocCallBack});
   }
   _postcancelSalesDoc();
}

function _cancelSalesDocCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
