/**
  * Author(s):java业务平台代码生成工具
  * Date: 2013年11月19日 15点22分21秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象国内信用证(HomeCreditPayment)编辑页面JS文件
 */
 
             

    

    

    

    

    

    
/**
 *国内信用证行项目
 *批量删除
 */
function _deletesHomeCreditPayItem()
{
	if(_predeletesHomeCreditPayItem()){
		if (HomeCreditPayItem_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: '您选择了【国内证付款行项目批量删除】操作，是否确定继续该操作？',
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = HomeCreditPayItem_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						HomeCreditPayItem_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo('您选择了【国内证付款行项目批量删除】操作，是否确定继续该操作？');
		}
	}
	_postdeletesHomeCreditPayItem();
}
    

    
  

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
             

    

    
  

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
             

    

    
  

    
    
    
    
    
    
    
    
    
    
    
             
/**
 *国内信用证行项目
 *批量删除
 */
function _deletesHomeCreditDocuBank()
{
	if(_predeletesHomeCreditDocuBank()){
		if (HomeCreditDocuBank_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.homeCreditDocuBank_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = HomeCreditDocuBank_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						HomeCreditDocuBank_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.homeCreditDocuBank_Deletes_SelectToOperation);
		}
	}
	_postdeletesHomeCreditDocuBank();
}
    

    
  

    
    
    
    
    
    
    
    
    
    
    
             

    
             
    
    
    
  

    
              
             

    

    
  

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
             

    
    
    
    
    
    
    
    
    
  

          
/**
 * 保存 
 */
function _saveOrUpdateHomeCreditPayment()
{
if(_presaveOrUpdateHomeCreditPayment()){
	var param = Form.serialize('mainForm');	
	           
         
		        		param = param + ""+ getHomeCreditPayItemGridData();
		        	           
         
		        		param = param + ""+ getHomeCreditPayCbillGridData();
		        	           
         
		        		param = param + ""+ getHomeCreditBankItemGridData();
		        	           
         
		        		param = param + ""+ getHomeCreditDocuBankGridData();
		        	           
 
		        		param = param +  "&"+ Form.serialize('settleSubjectForm');		
           
 
		        		param = param +  "&"+ Form.serialize('fundFlowForm');		
           
						param = param + ""+ getattachementAttachementGridData();     
		        	           
         
		        		param = param + ""+ getHomeCreditRebankGridData();
		        	           
         
		        		param = param + ""+ getHomeCreditRelatPayGridData();
		        	    //alert(param);
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
	//var id = responseUtil.getCustomField('coustom');
	var paymentid=result.paymentid;
	//document.getElementById("HomeCreditPayment.paymentid").value = id;
	document.getElementById("HomeCreditPayment.creator_text").value = result.creator_text;
	document.getElementById("HomeCreditPayment.creator").value = result.creator;
	document.getElementById("HomeCreditPayment.createtime").value = result.createtime;
	document.getElementById("HomeCreditPayment.lastmodifyer_text").value = result.lastmodifyer_text;
	document.getElementById("HomeCreditPayment.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("HomeCreditPayment.lastmodifytime").value = result.lastmodifytime;
	reload_HomeCreditPayItem_grid("defaultCondition=YPAYMENTITEM.PAYMENTID='"+ paymentid +"'");
	reload_HomeCreditPayCbill_grid("defaultCondition=YPAYMENTCBILL.PAYMENTID='"+ paymentid +"'");
	reload_HomeCreditBankItem_grid("defaultCondition=YPAYBANKITEM.PAYMENTID='"+ paymentid +"'");
	reload_HomeCreditDocuBank_grid("defaultCondition=YDOUCARYBANKITEM.PAYMENTID='"+ paymentid +"'");
	reload_attachementAttachement_grid("defaultCondition=YATTACHEMENT.BUSINESSID='"+ paymentid +"' and YATTACHEMENT.BOPROPERTY='attachement'");
	reload_HomeCreditRebank_grid("defaultCondition=YBILLPAYMENTBANK.PAYMENTID='"+ paymentid +"'");
	reload_HomeCreditRelatPay_grid("defaultCondition=YPAYMENTRELATED.PAYMENTID='"+ paymentid +"'");
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
          
          
          
