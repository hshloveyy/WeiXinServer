/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年08月02日 13点55分27秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象内贸付款(HomePayment)增加页面JS文件
 */

             
    
    
    
    
    
    
/**
 *内贸付款行项目
 *批量删除
 */
function _deletesHomePaymentItem()
{
	if(_predeletesHomePaymentItem()){
		if (HomePaymentItem_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.homePaymentItem_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = HomePaymentItem_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						HomePaymentItem_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.homePaymentItem_Deletes_SelectToOperation);
		}
	}
	_postdeletesHomePaymentItem();
}
    

    
  

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
             

    

    
/**
 *内贸付款行项目
 *批量删除
 */
function _deletesHomePaymentCbill()
{
	if(_predeletesHomePaymentCbill()){
		if (HomePaymentCbill_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.homePaymentCbill_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = HomePaymentCbill_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						HomePaymentCbill_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.homePaymentCbill_Deletes_SelectToOperation);
		}
	}
	_postdeletesHomePaymentCbill();
}
    

    
  

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
             

    

    
/**
 *内贸付款行项目
 *批量删除
 */
function _deletesHomePayBankItem()
{
	if(_predeletesHomePayBankItem()){
		if (HomePayBankItem_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.homePayBankItem_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = HomePayBankItem_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						HomePayBankItem_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.homePayBankItem_Deletes_SelectToOperation);
		}
	}
	_postdeletesHomePayBankItem();
}
    

    
  

    
    
    
    
    
    
    
    
    
    
    
             

    

    
/**
 *内贸付款行项目
 *批量删除
 */
function _deletesHomeDocuBankItem()
{
	if(_predeletesHomeDocuBankItem()){
		if (HomeDocuBankItem_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.homeDocuBankItem_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = HomeDocuBankItem_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						HomeDocuBankItem_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.homeDocuBankItem_Deletes_SelectToOperation);
		}
	}
	_postdeletesHomeDocuBankItem();
}


    
    
  
    
    
             
  
    
    
             
    
    
  
    
    
             
  
    
    
  

          

          

          
/**
 * 保存 
 */
function _saveOrUpdateHomePayment()
{					 
    if(isCreateCopy){
		document.getElementById("HomePayment.paymentid").value = "";
	}
	if(_presaveOrUpdateHomePayment()){
		var param = Form.serialize('mainForm');	
		           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllHomePaymentItemGridData();
		}
		else
		{
			param = param + ""+ getHomePaymentItemGridData(); 
		}
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllHomePaymentCbillGridData();
		}
		else
		{
			param = param + ""+ getHomePaymentCbillGridData(); 
		}
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllHomePayBankItemGridData();
		}
		else
		{
			param = param + ""+ getHomePayBankItemGridData(); 
		}
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllHomeDocuBankItemGridData();
		}
		else
		{
			param = param + ""+ getHomeDocuBankItemGridData(); 
		}
			        	           
 
		//param = param + "&" + Form.serialize('homeSettSubjForm');		
           
 
		//param = param + "&" + Form.serialize('homeFundFlowForm');		
           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllHomePaymentRelatGridData();
		}
		else
		{
			param = param + ""+ getHomePaymentRelatGridData(); 
		}
			        		    new AjaxEngine(contextPath + '/xdss3/payment/homePaymentController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdateHomePayment();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var paymentid=result.paymentid;
	document.getElementById("HomePayment.paymentid").value = paymentid;
	
	document.getElementById("HomePayment.paymentno").value = result.paymentno;
	document.getElementById("HomePayment.creator_text").value = result.creator_text;
	document.getElementById("HomePayment.creator").value = result.creator;
	document.getElementById("HomePayment.createtime").value = result.createtime;
	document.getElementById("HomePayment.lastmodifytime").value = result.lastmodifytime;
	document.getElementById("HomePayment.lastmodifyer").value = result.lastmodifyer;
	isCreateCopy = false;	
	reload_HomePaymentItem_grid("defaultCondition=YPAYMENTITEM.PAYMENTID='"+ paymentid +"'");
	reload_HomePaymentCbill_grid("defaultCondition=YPAYMENTCBILL.PAYMENTID='"+ paymentid +"'");
	reload_HomePayBankItem_grid("defaultCondition=YPAYBANKITEM.PAYMENTID='"+ paymentid +"'");
	reload_HomeDocuBankItem_grid("defaultCondition=YDOUCARYBANKITEM.PAYMENTID='"+ paymentid +"'");
	//document.getElementById("HomeSettSubj.settlesubjectid").value=result.settlesubjectid;
	//document.getElementById("HomeFundFlow.fundflowid").value=result.fundflowid;
	reload_HomePaymentRelat_grid("defaultCondition=YPAYMENTRELATED.PAYMENTID='"+ paymentid +"'");

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
          

/**
 * 提交
 */
function _submitProcessHomePayment()
{
if(_presubmitProcessHomePayment()){
	var param = Form.serialize('mainForm');	
          
         
	param = param + "&" + getAllHomePaymentItemGridData();
	        	          
         
	param = param + "&" + getAllHomePaymentCbillGridData();
	        	          
         
	param = param + "&" + getAllHomePayBankItemGridData();
	        	          
         
	param = param + "&" + getAllHomeDocuBankItemGridData();
	        	          
 
	//param = param + "&" + Form.serialize('homeSettSubjForm');		
          
 
	//param = param + "&" + Form.serialize('homeFundFlowForm');		
          
         
	param = param + "&" + getAllHomePaymentRelatGridData();
	        		param = param + "&"+ Form.serialize('workflowForm');
	
	new AjaxEngine(contextPath +'/xdss3/payment/homePaymentController.spr?action=_submitProcess', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
  }
  _postsubmitProcessHomePayment();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

          

          

          

          

