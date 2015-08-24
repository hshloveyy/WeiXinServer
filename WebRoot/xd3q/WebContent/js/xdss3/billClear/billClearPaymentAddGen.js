/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年09月17日 08点27分44秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象票清付款(BillClearPayment)增加页面JS文件
 */

             
    
    
/**
 *票清付款行项目
 *批量删除
 */
function _deletesBillClearItemPay()
{
    if(_predeletesBillClearItemPay())
    {
        
    }
    _postdeletesBillClearItemPay();
}

    
    
  
    
    
             
    
    
/**
 *票清付款行项目
 *批量删除
 */
function _deletesBillInPayment()
{
   if(_predeletesBillInPayment())
    {
        
    }
    _postdeletesBillInPayment();
}

    
    
  
    
    
             
    
    
    
  
    
    
             
  
    
    
  

          

          
/**
 * 保存 
 */
function _saveOrUpdateBillClearPayment()
{					 
    if(isCreateCopy){
		document.getElementById("BillClearPayment.billclearid").value = "";
	}
	if(_presaveOrUpdateBillClearPayment()){
		var param = Form.serialize('mainForm');	
		           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllBillClearItemPayGridData();
		}
		else
		{
			param = param + ""+ getBillClearItemPayGridData(); 
		}
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllBillInPaymentGridData();
		}
		else
		{
			param = param + ""+ getBillInPaymentGridData(); 
		}
			        	           
 
		param = param + "&" + Form.serialize('fundFlowForm');		
           
 
		param = param + "&" + Form.serialize('settleSubjectForm');		
	    new AjaxEngine(contextPath + '/xdss3/billClear/billClearPaymentController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdateBillClearPayment();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var billclearid=result.billclearid;
	document.getElementById("BillClearPayment.billclearid").value = billclearid;
	
	document.getElementById("BillClearPayment.billclearno").value = result.billclearno;
	document.getElementById("BillClearPayment.createtime").value = result.createtime;
	document.getElementById("BillClearPayment.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("BillClearPayment.lastmodifytime").value = result.lastmodifytime;
	document.getElementById("BillClearPayment.creator").value = result.creator;
	isCreateCopy = false;	
	reload_BillClearItemPay_grid("defaultCondition=YBILLCLEARITEM.BILLCLEARID='"+ billclearid +"'");
	reload_BillInPayment_grid("defaultCondition=YBILLINPAYMENT.BILLCLEARID='"+ billclearid +"'");
	document.getElementById("FundFlow.fundflowid").value=result.fundflowid;
	document.getElementById("SettleSubject.settlesubjectid").value=result.settlesubjectid;

    if(Ext.getCmp("_submitProcess").disabled){
    	Ext.getCmp("_submitProcess").setDisabled(false);
    }
    if (Ext.getCmp("_submitForReassign").disabled) {
		Ext.getCmp("_submitForReassign").setDisabled(false);
	}
}

          

/**
 * 提交
 */
function _submitProcessBillClearPayment()
{
if(_presubmitProcessBillClearPayment()){
	var param = Form.serialize('mainForm');	
          
         
//	param = param + "&" + getAllBillClearItemPayGridData();
	        	          
         
//	param = param + "&" + getAllBillInPaymentGridData();
	        	          
 	param = param + ""+ getBillClearItemPayGridData();
		        	           
         
	param = param + ""+ getBillInPaymentGridData();
	param = param + "&" + Form.serialize('fundFlowForm');		
          
 
	param = param + "&" + Form.serialize('settleSubjectForm');		
	param = param + "&"+ Form.serialize('workflowForm');
	
	new AjaxEngine(contextPath +'/xdss3/billClear/billClearPaymentController.spr?action=_submitProcess', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
  }
  _postsubmitProcessBillClearPayment();
}

/**
 * 提交后的回调动作
 */
 /**
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
**/
          
/**
 * 取消
 */
function _cancel()
{
	if(_precancel()){
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	 }
	 _postcancel();
}
          

          

          

