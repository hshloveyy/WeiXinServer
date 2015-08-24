/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年09月17日 08点27分43秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象票清付款(BillClearPayment)查看页面JS文件
 */
          
   
    

    
    

    
  

        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

          
   
    

    
    

    
  

        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

          
   
    
    
    
  

    
          
   

    
  

          
          
/**
 * 保存 
 */
function _saveOrUpdateBillClearPayment()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("BillClearPayment.billclearid").value = id;	
}
          

/**
 * 提交
 */
function _submitProcessBillClearPayment(id)
{
	if(_presubmitProcessBillClearPayment()){
		var param = Form.serialize('mainForm');	
	            
         
		param = param + "&" + getAllBillClearItemPayGridData();
		        	            
         
		param = param + "&" + getAllBillInPaymentGridData();
		        	            
 
		param = param  + "&" +  Form.serialize('fundFlowForm');		
            
 
		param = param  + "&" +  Form.serialize('settleSubjectForm');		
		param = param + "&"+ Form.serialize('workflowForm')  + "&type=view";
	
		new AjaxEngine(contextPath +'/xdss3/billClear/billClearPaymentController.spr?action=_submitProcess', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	}
	_postsubmitProcessBillClearPayment();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          

/**
 * 取消
 */
function _cancelBillClearPayment()
{
  if(_precancelBillClearPayment()){
	new AjaxEngine(contextPath + '/xdss3/billClear/billClearPaymentController.spr?action=_cancel&billclearid='+billclearid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelBillClearPaymentCallBack});
   }
   _postcancelBillClearPayment();
}

function _cancelBillClearPaymentCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
          
          
