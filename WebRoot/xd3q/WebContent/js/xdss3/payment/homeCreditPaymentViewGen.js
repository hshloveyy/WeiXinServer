/**
  * Author(s):java业务平台代码生成工具
  * Date: 2013年11月19日 15点22分21秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象国内信用证(HomeCreditPayment)查看页面JS文件
 */
          
   
    

    
    

    
    

    
    

    
  

        
    
    
    
    
    
    
    
    
    
    
    
    
    
    

          
   
    

    
  

        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

          
   
    

    
  

        
    
    
    
    
    
    
    
    
    

          
   
    

    
  

        
    
    
    
    
    
    
    
    
    

          
   

    
          
   
    
    
    
  

    
          
        
    
    
    
    
    
    
    

          
   
    

    
  

        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

          
   

        
    
    
    
    
    
    
    

  

          
/**
 * 保存 
 */
function _saveOrUpdateHomeCreditPayment()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("HomeCreditPayment.paymentid").value = id;	
}
          

/**
 * 取消
 */
function _cancelHomeCreditPayment()
{
  if(_precancelHomeCreditPayment()){
	new AjaxEngine(contextPath + '/xdss3/payment/homeCreditPaymentController.spr?action=_cancel&paymentid='+paymentid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelHomeCreditPaymentCallBack});
   }
   _postcancelHomeCreditPayment();
}

function _cancelHomeCreditPaymentCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          

/**
 * 提交
 */
function _submitProcessHomeCreditPayment(id)
{
	if(_presubmitProcessHomeCreditPayment()){
		var param = Form.serialize('mainForm');	
	            
         
		param = param + "&" + getAllHomeCreditPayItemGridData();
		        	            
         
		param = param + "&" + getAllHomeCreditPayCbillGridData();
		        	            
         
		param = param + "&" + getAllHomeCreditBankItemGridData();
		        	            
         
		param = param + "&" + getAllHomeCreditDocuBankGridData();
		        	            
 
		param = param  + "&" +  Form.serialize('settleSubjectForm');		
            
 
		param = param  + "&" +  Form.serialize('fundFlowForm');		
            
		param = param + ""+ getAllattachementAttachementGridData();   
		        	            
         
		param = param + "&" + getAllHomeCreditRebankGridData();
		        	            
         
		param = param + "&" + getAllHomeCreditRelatPayGridData();
		        			param = param + "&"+ Form.serialize('workflowForm')  + "&type=view";
	
		new AjaxEngine(contextPath +'/xdss3/payment/homeCreditPaymentController.spr?action=_submitProcess', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	}
	_postsubmitProcessHomeCreditPayment();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
          
          
