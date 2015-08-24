/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月02日 11点05分24秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象供应商退款(SupplierRefundment)查看页面JS文件
 */
          
   
    

    
    

    
  

        
    
    
    
    
    
    
    
    
    
    

          
   
    

    
    

    
  

        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

  

          
          
/**
 * 保存 
 */
function _saveOrUpdateSupplierRefundment()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("SupplierRefundment.refundmentid").value = id;	
}
          

/**
 * 取消
 */
function _cancelSupplierRefundment()
{
  if(_precancelSupplierRefundment()){
	new AjaxEngine(contextPath + '/xdss3/supplierDrawback/supplierRefundmentController.spr?action=_cancel&refundmentid='+refundmentid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelSupplierRefundmentCallBack});
   }
   _postcancelSupplierRefundment();
}

function _cancelSupplierRefundmentCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          

/**
 * 提交
 */
function _submitProcessSupplierRefundment(id)
{
	if(_presubmitProcessSupplierRefundment()){
		var param = Form.serialize('mainForm');	
		param = param + "&"+ Form.serialize('settleSubjectForm');
		param = param + "&"+ Form.serialize('fundFlowForm');
		param = param + "&" + getAllSupplierDbBankItemGridData();
		param = param + "&" + getAllSupplierRefundItemGridData();
		        			param = param + "&"+ Form.serialize('workflowForm')  + "&type=view";
	
		new AjaxEngine(contextPath +'/xdss3/supplierDrawback/supplierRefundmentController.spr?action=_submitProcess', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	}
	_postsubmitProcessSupplierRefundment();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
