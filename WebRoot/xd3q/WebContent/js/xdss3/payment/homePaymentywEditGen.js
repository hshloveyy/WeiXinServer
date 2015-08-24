/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年08月02日 13点55分29秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象内贸付款(HomePayment)编辑页面JS文件
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
if(_presaveOrUpdateHomePayment()){
	var param = Form.serialize('mainForm');	
	           
         
		        		param = param + ""+ getHomePaymentItemGridData();
		        	           
         
		        		param = param + ""+ getHomePaymentCbillGridData();
		        	           
         
		        		param = param + ""+ getHomePayBankItemGridData();
		        	           
         
		        		param = param + ""+ getHomeDocuBankItemGridData();
		        	           
         
		        		param = param + ""+ getHomePaymentRelatGridData();
		        	    //alert(param);
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
	//var id = responseUtil.getCustomField('coustom');
	var paymentid=result.paymentid;
	//document.getElementById("HomePayment.paymentid").value = id;
	document.getElementById("HomePayment.creator_text").value = result.creator_text;
	document.getElementById("HomePayment.creator").value = result.creator;
	document.getElementById("HomePayment.createtime").value = result.createtime;
	document.getElementById("HomePayment.lastmodifytime").value = result.lastmodifytime;
	document.getElementById("HomePayment.lastmodifyer").value = result.lastmodifyer;
	reload_HomePaymentItem_grid("defaultCondition=YPAYMENTITEM.PAYMENTID='"+ paymentid +"'");
	reload_HomePaymentCbill_grid("defaultCondition=YPAYMENTCBILL.PAYMENTID='"+ paymentid +"'");
	reload_HomePayBankItem_grid("defaultCondition=YPAYBANKITEM.PAYMENTID='"+ paymentid +"'");
	reload_HomeDocuBankItem_grid("defaultCondition=YDOUCARYBANKITEM.PAYMENTID='"+ paymentid +"'");
	reload_HomePaymentRelat_grid("defaultCondition=YPAYMENTRELATED.PAYMENTID='"+ paymentid +"'");
}
          
/**
 * 取消
 */
function _cancelHomePayment()
{
  if(_precancelHomePayment()){
	new AjaxEngine(contextPath + '/xdss3/payment/homePaymentController.spr?action=_cancel&paymentid='+paymentid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelHomePaymentCallBack});
   }
   _postcancelHomePayment();
}
function _cancelHomePaymentCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
var voucherFlag = false;    
/**
 * 提交
 * ------------------------------ 修改记录 ------------------------------
 * 邱杰烜 2010-09-20 在资金部节点提交时，给予现金日记账是否已经登记的提示
 */
function _submitProcessHomePayment(flag){
if(_presubmitProcessHomePayment()){
	voucherFlag = flag;
	var param = Form.serialize('mainForm');	
	param = param + "&" + getAllHomePaymentItemGridData();
	param = param + "&" + getAllHomePaymentCbillGridData();
	param = param + "&" + getAllHomePayBankItemGridData();
	param = param + "&" + getAllHomeDocuBankItemGridData();
	param = param + "&" + getAllHomePaymentRelatGridData();
	param = param + "&"+ Form.serialize('workflowForm');
	/*
	 * 在资金部节点(非现金)提交时，给予现金日记账提示（stateFlag为2代表资金部，在homePaymentEdit.js里赋值）
	 * pt：现金(08)、背书(09)
	 */
	if(stateFlag==2 && pt!='08' && pt!='09'){
		Ext.MessageBox.confirm(Txt.cue, '请确认是否已经登记现金日记账？', function(result){
			if(result=='yes'){
				new AjaxEngine(contextPath +'/xdss3/payment/homePaymentController.spr?action=_submitProcess', 
						   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
			}
		});
	}else{		// 不为资金部节点则不给提示
		new AjaxEngine(contextPath +'/xdss3/payment/homePaymentController.spr?action=_submitProcess', 
				   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	}
  }
  _postsubmitProcessHomePayment();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle(transport)
{
	 var responseUtil = new AjaxResponseUtils(transport.responseText);
	 var msgType = responseUtil.getMessageType();
	 if (msgType == 'info')
	 {
		if(voucherFlag==true){
			var suppli =div_supplier_sh_sh.getValue();		
			var paymentid = Ext.get('HomePayment.paymentid').dom.value;	
			Ext.Ajax.request({
	    				url : contextPath+'/xdss3/payment/homePaymentController.spr?action=updateVendorTitle',
	        			params : {supplier:suppli,paymentid:paymentid},
	        			success : function(xhr){
							if(xhr.responseText){				        	
							}
			        	},
			        	scope : this
			    	});
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.submitSuccess+'是否需要查看凭证？',
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
				    	_getMainFrame().maintabs.addPanel2('凭证查看', contextPath+'/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.get('HomePayment.paymentid').dom.value);
					}else{
						_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
					}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.submitSuccess);
			_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		}
	 }
	 if(msgType == 'info_hidden'){
	 	var result = responseUtil.getCustomField("coustom");
	 	_getMainFrame().showInfo(result.info);
	 }
}
          
          
          
          
