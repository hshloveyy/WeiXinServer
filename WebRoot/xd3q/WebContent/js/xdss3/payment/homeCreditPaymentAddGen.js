/**
  * Author(s):java业务平台代码生成工具
  * Date: 2013年11月19日 15点22分21秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象国内信用证(HomeCreditPayment)增加页面JS文件
 */

             
    
    
    
    
    
    
/**
 *国内信用证行项目
 *批量删除
 */
function _deletesHomeCreditPayItem()
{
}

    
    
  
    
    
             
    
    
  
    
    
             
    
    
  
    
    
             
/**
 *国内信用证行项目
 *批量删除
 */
function _deletesHomeCreditDocuBank()
{
}

    
    
  
    
    
             
  
    
    
             
    
    
    
  
    
    
              
    
             
    
    
  
    
    
             
  
    
    
  

          
/**
 * 保存 
 */
function _saveOrUpdateHomeCreditPayment()
{					 
    if(isCreateCopy){
		document.getElementById("HomeCreditPayment.paymentid").value = "";
	}
	if(_presaveOrUpdateHomeCreditPayment()){
		var param = Form.serialize('mainForm');	
		           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllHomeCreditPayItemGridData();
		}
		else
		{
			param = param + ""+ getHomeCreditPayItemGridData(); 
		}
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllHomeCreditPayCbillGridData();
		}
		else
		{
			param = param + ""+ getHomeCreditPayCbillGridData(); 
		}
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllHomeCreditBankItemGridData();
		}
		else
		{
			param = param + ""+ getHomeCreditBankItemGridData(); 
		}
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllHomeCreditDocuBankGridData();
		}
		else
		{
			param = param + ""+ getHomeCreditDocuBankGridData(); 
		}
			        	           
 
		param = param + "&" + Form.serialize('settleSubjectForm');		
           
 
		param = param + "&" + Form.serialize('fundFlowForm');		
           
		if(isCreateCopy)
		{
			param = param + ""+ getAllattachementAttachementGridData();
		}
		else
		{
			param = param + ""+ getattachementAttachementGridData(); 
		}	           
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllHomeCreditRebankGridData();
		}
		else
		{
			param = param + ""+ getHomeCreditRebankGridData(); 
		}
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllHomeCreditRelatPayGridData();
		}
		else
		{
			param = param + ""+ getHomeCreditRelatPayGridData(); 
		}
			        		    new AjaxEngine(contextPath + '/xdss3/payment/homeCreditPaymentController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdateHomeCreditPayment();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var paymentid=result.paymentid;
	document.getElementById("HomeCreditPayment.paymentid").value = paymentid;
	document.getElementById("HomeCreditPayment.paymentno").value = result.paymentno;
//	document.getElementById("HomeCreditPayment.creator_text").value = result.creator_text;
	document.getElementById("HomeCreditPayment.creator").value = result.creator;
	document.getElementById("HomeCreditPayment.createtime").value = result.createtime;
//	document.getElementById("HomeCreditPayment.lastmodifyer_text").value = result.lastmodifyer_text;
	document.getElementById("HomeCreditPayment.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("HomeCreditPayment.lastmodifytime").value = result.lastmodifytime;
	//isCreateCopy = false;	
	reload_HomeCreditPayItem_grid("defaultCondition=YPAYMENTITEM.PAYMENTID='"+ paymentid +"'");
	reload_HomeCreditPayCbill_grid("defaultCondition=YPAYMENTCBILL.PAYMENTID='"+ paymentid +"'");
	reload_HomeCreditBankItem_grid("defaultCondition=YPAYBANKITEM.PAYMENTID='"+ paymentid +"'");
	reload_HomeCreditDocuBank_grid("defaultCondition=YDOUCARYBANKITEM.PAYMENTID='"+ paymentid +"'");
	document.getElementById("SettleSubject.settlesubjectid").value=result.settlesubjectid;
	document.getElementById("FundFlow.fundflowid").value=result.fundflowid;
	reload_attachementAttachement_grid("defaultCondition=YATTACHEMENT.BUSINESSID='"+ paymentid +"' and YATTACHEMENT.BOPROPERTY='attachement'");
	reload_HomeCreditRebank_grid("defaultCondition=YBILLPAYMENTBANK.PAYMENTID='"+ paymentid +"'");
	reload_HomeCreditRelatPay_grid("defaultCondition=YPAYMENTRELATED.PAYMENTID='"+ paymentid +"'");

    if(Ext.getCmp("_submitProcess").disabled){
    	Ext.getCmp("_submitProcess").setDisabled(false);}
}

          
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
          

/**
 * 提交
 */
function _submitProcessHomeCreditPayment()
{
if(_presubmitProcessHomeCreditPayment()){
	var param = Form.serialize('mainForm');	
          
         
	param = param + "&" + getAllHomeCreditPayItemGridData();
	        	          
         
	param = param + "&" + getAllHomeCreditPayCbillGridData();
	        	          
         
	param = param + "&" + getAllHomeCreditBankItemGridData();
	        	          
         
	param = param + "&" + getAllHomeCreditDocuBankGridData();
	        	          
 
	param = param + "&" + Form.serialize('settleSubjectForm');		
          
 
	param = param + "&" + Form.serialize('fundFlowForm');		
          
	param = param + ""+ getAllattachementAttachementGridData();     
	        	          
         
	param = param + "&" + getAllHomeCreditRebankGridData();
	        	          
         
	param = param + "&" + getAllHomeCreditRelatPayGridData();
	        		param = param + "&"+ Form.serialize('workflowForm');
	
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

          

          

          

