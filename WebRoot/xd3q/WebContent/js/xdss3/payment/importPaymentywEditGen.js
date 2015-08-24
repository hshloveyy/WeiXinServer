/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年08月23日 02点16分36秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象进口付款(ImportPayment)编辑页面JS文件
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

var voucherFlag = false;       
          
/**
 * 提交
 * ------------------------------ 修改记录 ------------------------------
 * 邱杰烜 2010-09-20 在资金部节点提交时，给予现金日记账是否已经登记的提示
 */
function _submitProcessImportPayment(flag){
	if(_presubmitProcessImportPayment()){
		voucherFlag = flag;
		var param = Form.serialize('mainForm');	
		param = param + "&" + getAllImportPaymentItemGridData();
		param = param + "&" + getAllImportPaymentCbillGridData();
		param = param + "&" + getAllImportPayBankItemGridData();
		param = param + "&" + getAllImportDocuBankItemGridData();
		param = param + "&" + getAllBillPayReBankItemGridData();
		param = param + "&" + getAllImportRelatPaymentGridData();
		param = param + "&"+ Form.serialize('workflowForm');
		/*
		 * 在资金部节点提交时，给予现金日记账提示（stateFlag为2代表资金部，在importPaymentEdit.js里赋值）
		 */
		if(stateFlag==2){
			Ext.MessageBox.confirm(Txt.cue, '请确认是否已经登记现金日记账？', function(result){
				if(result=='yes'){
					new AjaxEngine(contextPath +'/xdss3/payment/importPaymentController.spr?action=_submitProcess', 
							{method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
				}
			});
		}else{		// 不为资金部节点则不给提示
			new AjaxEngine(contextPath +'/xdss3/payment/importPaymentController.spr?action=_submitProcess', 
					{method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
		}
	}
	_postsubmitProcessImportPayment();
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
			var paymentid = Ext.get('ImportPayment.paymentid').dom.value;	
			Ext.Ajax.request({
	    				url : contextPath+'/xdss3/payment/importPaymentController.spr?action=updateVendorTitle',
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
				    	_getMainFrame().maintabs.addPanel2('凭证查看', contextPath+'/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.get('ImportPayment.paymentid').dom.value);
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
          
          
          
/**
 * 保存 
 */
function _saveOrUpdateImportPayment()
{
if(_presaveOrUpdateImportPayment()){
	var param = Form.serialize('mainForm');	
	           
         
		        		param = param + ""+ getImportPaymentItemGridData();
		        	           
         
		        		param = param + ""+ getImportPaymentCbillGridData();
		        	           
         
		        		param = param + ""+ getImportPayBankItemGridData();
		        	           
         
		        		param = param + ""+ getImportDocuBankItemGridData();
		        	           
 						param = param + ""+ getBillPayReBankItemGridData();
		        		param = param + ""+ getImportRelatPaymentGridData();
		        	    //alert(param);
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
	//var id = responseUtil.getCustomField('coustom');
	var paymentid=result.paymentid;
	//document.getElementById("ImportPayment.paymentid").value = id;
	document.getElementById("ImportPayment.createtime").value = result.createtime;
	document.getElementById("ImportPayment.creator_text").value = result.creator_text;
	document.getElementById("ImportPayment.creator").value = result.creator;
	document.getElementById("ImportPayment.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("ImportPayment.lastmodifytime").value = result.lastmodifytime;
	reload_ImportPaymentItem_grid("defaultCondition=YPAYMENTITEM.PAYMENTID='"+ paymentid +"'");
	reload_ImportPaymentCbill_grid("defaultCondition=YPAYMENTCBILL.PAYMENTID='"+ paymentid +"'");
	reload_ImportPayBankItem_grid("defaultCondition=YPAYBANKITEM.PAYMENTID='"+ paymentid +"'");
	reload_ImportDocuBankItem_grid("defaultCondition=YDOUCARYBANKITEM.PAYMENTID='"+ paymentid +"'");
	reload_ImportRelatPayment_grid("defaultCondition=YPAYMENTRELATED.PAYMENTID='"+ paymentid +"'");
	reload_BillPayReBankItem_grid("defaultCondition=YBILLPAYMENTBANK.PAYMENTID='"+ paymentid +"'");
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
          
          
