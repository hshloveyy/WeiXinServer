/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月02日 09点27分04秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象客户退款(CustomerRefundment)查看页面JS文件
 */
          
   
    

    
    

    
  

        
    
    
    
    
    
    
    
    
    
    

          
   
    

    
    

    
  

        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

  

          
          
/**
 * 保存 
 */
function _saveOrUpdateCustomerRefundment()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("CustomerRefundment.refundmentid").value = id;	
}
          

/**
 * 取消
 */
function _cancelCustomerRefundment()
{
  if(_precancelCustomerRefundment()){
	new AjaxEngine(contextPath + '/xdss3/customerDrawback/customerRefundmentController.spr?action=_cancel&refundmentid='+refundmentid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelCustomerRefundmentCallBack});
   }
   _postcancelCustomerRefundment();
}

function _cancelCustomerRefundmentCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          

/**
 * 提交
 */
function _submitProcessCustomerRefundment(id)
{
	if(_presubmitProcessCustomerRefundment()){
		var param = Form.serialize('mainForm');	
		param = param + "&"+ Form.serialize('settleSubjectForm');
		param = param + "&"+ Form.serialize('fundFlowForm');
		param = param + "&" + getAllCustomerDbBankItemGridData();
		param = param + "&" + getAllCustomerRefundItemGridData();
		param = param + "&"+ getAllattachementAttachementGridData();   
		        			param = param + "&"+ Form.serialize('workflowForm')  + "&type=view";
	
		new AjaxEngine(contextPath +'/xdss3/customerDrawback/customerRefundmentController.spr?action=_submitProcess', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	}
	_postsubmitProcessCustomerRefundment();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	var businessid = document.getElementById("CustomerRefundment.refundmentid").value;
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
	  _submitProcessCustomerRefundment();
	}else {
	  _getMainFrame().showInfo(Txt.submitSuccess);
	  _getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	}

}