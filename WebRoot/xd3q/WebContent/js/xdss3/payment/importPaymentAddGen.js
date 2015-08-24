/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年08月23日 02点16分35秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象进口付款(ImportPayment)增加页面JS文件
 */

             
    
    
/**
 *进口付款行项目
 *批量删除
 */
function _deletesImportPaymentItem()
{
	if(_predeletesImportPaymentItem()){
		if (ImportPaymentItem_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.importPaymentItem_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = ImportPaymentItem_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						ImportPaymentItem_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.importPaymentItem_Deletes_SelectToOperation);
		}
	}
	_postdeletesImportPaymentItem();
}

    
    
  
    
    
             
    
    
/**
 *进口付款行项目
 *批量删除
 */
function _deletesImportPaymentCbill()
{
	if(_predeletesImportPaymentCbill()){
		if (ImportPaymentCbill_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.importPaymentCbill_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = ImportPaymentCbill_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						ImportPaymentCbill_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.importPaymentCbill_Deletes_SelectToOperation);
		}
	}
	_postdeletesImportPaymentCbill();
}

    
    
  
    
    
             
    
    
/**
 *进口付款行项目
 *批量删除
 */
function _deletesImportPayBankItem()
{
	if(_predeletesImportPayBankItem()){
		if (ImportPayBankItem_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.importPayBankItem_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = ImportPayBankItem_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						ImportPayBankItem_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.importPayBankItem_Deletes_SelectToOperation);
		}
	}
	_postdeletesImportPayBankItem();
}

    
    
  
    
    
             
    
    
/**
 *进口付款行项目
 *批量删除
 */
function _deletesImportDocuBankItem()
{
	if(_predeletesImportDocuBankItem()){
		if (ImportDocuBankItem_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.importDocuBankItem_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = ImportDocuBankItem_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						ImportDocuBankItem_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.importDocuBankItem_Deletes_SelectToOperation);
		}
	}
	_postdeletesImportDocuBankItem();

}

    
    
  
    
    
             
  
    
    
             
  
    
    
             
  
    
    
  

          

          

          

/**
 * 提交
 */
function _submitProcessImportPayment()
{
if(_presubmitProcessImportPayment()){
	var param = Form.serialize('mainForm');	
          
         
	param = param + "&" + getAllImportPaymentItemGridData();
	        	          
         
	param = param + "&" + getAllImportPaymentCbillGridData();
	        	          
         
	param = param + "&" + getAllImportPayBankItemGridData();
	        	          
         
	param = param + "&" + getAllImportDocuBankItemGridData();
	        	          
 
	//param = param + "&" + Form.serialize('importSettSubjForm');		
          
 
	//param = param + "&" + Form.serialize('importFundFlowForm');		
          
         
	param = param + "&" + getAllImportRelatPaymentGridData();
	        		param = param + "&"+ Form.serialize('workflowForm');
	
	new AjaxEngine(contextPath +'/xdss3/payment/importPaymentController.spr?action=_submitProcess', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
  }
  _postsubmitProcessImportPayment();
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
 * 保存 
 */
function _saveOrUpdateImportPayment()
{					 
    if(isCreateCopy){
		document.getElementById("ImportPayment.paymentid").value = "";
	}
	if(_presaveOrUpdateImportPayment()){
		var param = Form.serialize('mainForm');	
		           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllImportPaymentItemGridData();
		}
		else
		{
			param = param + ""+ getImportPaymentItemGridData(); 
		}
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllImportPaymentCbillGridData();
		}
		else
		{
			param = param + ""+ getImportPaymentCbillGridData(); 
		}
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllImportPayBankItemGridData();
		}
		else
		{
			param = param + ""+ getImportPayBankItemGridData(); 
		}
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllImportDocuBankItemGridData();
		}
		else
		{
			param = param + ""+ getImportDocuBankItemGridData(); 
		}
			        	           
 		param = param + ""+ getBillPayReBankItemGridData();
		//param = param + "&" + Form.serialize('importSettSubjForm');		
           
 
		//param = param + "&" + Form.serialize('importFundFlowForm');		
           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllImportRelatPaymentGridData();
		}
		else
		{
			param = param + ""+ getImportRelatPaymentGridData(); 
		}
			        		    new AjaxEngine(contextPath + '/xdss3/payment/importPaymentController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdateImportPayment();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var paymentid=result.paymentid;
	document.getElementById("ImportPayment.paymentid").value = paymentid;
	
	document.getElementById("ImportPayment.paymentno").value = result.paymentno;
	document.getElementById("ImportPayment.createtime").value = result.createtime;
	document.getElementById("ImportPayment.creator_text").value = result.creator_text;
	document.getElementById("ImportPayment.creator").value = result.creator;
	document.getElementById("ImportPayment.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("ImportPayment.lastmodifytime").value = result.lastmodifytime;
	isCreateCopy = false;	
	reload_ImportPaymentItem_grid("defaultCondition=YPAYMENTITEM.PAYMENTID='"+ paymentid +"'");
	reload_ImportPaymentCbill_grid("defaultCondition=YPAYMENTCBILL.PAYMENTID='"+ paymentid +"'");
	reload_ImportPayBankItem_grid("defaultCondition=YPAYBANKITEM.PAYMENTID='"+ paymentid +"'");
	reload_ImportDocuBankItem_grid("defaultCondition=YDOUCARYBANKITEM.PAYMENTID='"+ paymentid +"'");
	//document.getElementById("ImportSettSubj.settlesubjectid").value=result.settlesubjectid;
	//document.getElementById("ImportFundFlow.fundflowid").value=result.fundflowid;
	reload_ImportRelatPayment_grid("defaultCondition=YPAYMENTRELATED.PAYMENTID='"+ paymentid +"'");

    if(Ext.getCmp("_submitProcess").disabled){
    	Ext.getCmp("_submitProcess").setDisabled(false);}
    
    if(Ext.getCmp("_submitForReassign").disabled){
    	Ext.getCmp("_submitForReassign").setDisabled(false);} 
    	
    if(Ext.getCmp("tabs").getItem('attachementTab').disabled){
    	Ext.getCmp("tabs").getItem('attachementTab').setDisabled(false);
    }
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
          

          

