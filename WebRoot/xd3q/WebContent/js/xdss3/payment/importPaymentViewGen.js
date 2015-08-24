/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年08月23日 02点16分36秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象进口付款(ImportPayment)查看页面JS文件
 */
          
   
    

    
    

    
  

        
    
    
    
    
    
    
    
    
    
    
    
    
    

          
   
    

    
    

    
  

        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

          
   
    

    
    

    
  

        
    
    
    
    
    
    
    
    
    

          
   
    

    
    

    
  

        
    
    
    
    
    
    
    
    
    

          
   

    
          
   

    
          
   

        
    
    
    
    
    
    
    

  

          
          
          

/**
 * 提交
 */
function _submitProcessImportPayment(id)
{   
	if(_presubmitProcessImportPayment()){
		var param = Form.serialize('mainForm');	
         
		param = param + "&" + getAllImportPaymentItemGridData();
		        	            
         
		param = param + "&" + getAllImportPaymentCbillGridData();
		        	            
         
		param = param + "&" + getAllImportPayBankItemGridData();
		        	            
         
		param = param + "&" + getAllImportDocuBankItemGridData();
		        	            
 
		param = param  + "&" +  Form.serialize('importSettSubjForm');		
            
 
		param = param  + "&" +  Form.serialize('importFundFlowForm');		
            
         
		param = param + "&" + getAllImportRelatPaymentGridData();
		        			param = param + "&"+ Form.serialize('workflowForm')  + "&type=view";
		new AjaxEngine(contextPath +'/xdss3/payment/importPaymentController.spr?action=_submitProcess', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	}
	_postsubmitProcessImportPayment();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle(transport)
{
    var businessid = document.getElementById("ImportPayment.paymentid").value;
    var para = '?action=checkSpecil&businessId='+businessid+'&type=2';
    new AjaxEngine('/showProcessImgController.spr', 
							   {method:"post", parameters: para, onComplete: speckBackHandle});
							 
}

function speckBackHandle(transport)
{   
    var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	if(customField.returnMessage>0){
	  document.getElementById('workflowForm').workflowTaskId.value = customField.returnMessage;
	  _submitProcessImportPayment();
	}else {
		_getMainFrame().showInfo(Txt.submitSuccess);
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	}
}          
          
          
/**
 * 保存 
 */
function _saveOrUpdateImportPayment()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("ImportPayment.paymentid").value = id;	
}
          

/**
 * 取消
 */
function _cancelImportPayment()
{
  if(_precancelImportPayment()){
	new AjaxEngine(contextPath + '/xdss3/payment/importPaymentController.spr?action=_cancel&paymentid='+paymentid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelImportPaymentCallBack});
   }
   _postcancelImportPayment();
}

function _cancelImportPaymentCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
          
