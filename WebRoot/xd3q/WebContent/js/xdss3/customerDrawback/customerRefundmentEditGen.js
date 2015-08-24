/**
 * Author(s):java业务平台代码生成工具
 * Date: 2010年07月02日 09点27分04秒
 * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 * @(#)
 * Description:  
 * <功能>主对象客户退款(CustomerRefundment)编辑页面JS文件
 */

/**
 * 客户退款行项目 批量删除
 */
function _deletesCustomerDbBankItem() {
	_predeletesCustomerDbBankItem();
}

/**
 * 客户退款行项目 批量删除
 */
function _deletesCustomerRefundItem() {
	_predeletesCustomerRefundItem();
}

/**
 * 保存
 */
function _saveOrUpdateCustomerRefundment() {
	if (_presaveOrUpdateCustomerRefundment()) {
		var param = Form.serialize('mainForm');
		param = param + "&"+ Form.serialize('settleSubjectForm');
		param = param + "&"+ Form.serialize('fundFlowForm');
		param = param + "" + getCustomerDbBankItemGridData();

		param = param + "" + getCustomerRefundItemGridData();
		param = param + "&"+ getattachementAttachementGridData();     
		// alert(param);
		new AjaxEngine(
				contextPath + '/xdss3/customerDrawback/customerRefundmentController.spr?action=_saveOrUpdate',
				{
					method : "post",
					parameters : param,
					onComplete : callBackHandle,
					callback : customCallBackHandle
				});
	}
	_postsaveOrUpdateCustomerRefundment();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport) {
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	// var id = responseUtil.getCustomField('coustom');
	var refundmentid = result.refundmentid;
	// document.getElementById("CustomerRefundment.refundmentid").value = id;
	document.getElementById("CustomerRefundment.createtime").value = result.createtime;
	document.getElementById("CustomerRefundment.creator_text").value = result.creator_text;
	document.getElementById("CustomerRefundment.creator").value = result.creator;
	document.getElementById("CustomerRefundment.lastmodifytime").value = result.lastmodifytime;
	reload_CustomerDbBankItem_grid("defaultCondition=YREFUNDBANKITEM.REFUNDMENTID='"
			+ refundmentid + "'");
	reload_CustomerRefundItem_grid("defaultCondition=YREFUNDMENTITEM.REFUNDMENTID='"
			+ refundmentid + "'");
	reload_attachementAttachement_grid("defaultCondition=YATTACHEMENT.BUSINESSID='"+ refundmentid +"' and YATTACHEMENT.BOPROPERTY='attachement'");
}

/**
 * 取消
 */
function _cancelCustomerRefundment() {
	if (_precancelCustomerRefundment()) {
		new AjaxEngine(
				contextPath
						+ '/xdss3/customerDrawback/customerRefundmentController.spr?action=_cancel&refundmentid='
						+ refundmentid, {
					method : "post",
					parameters : '',
					onComplete : callBackHandle,
					callback : _cancelCustomerRefundmentCallBack
				});
	}
	_postcancelCustomerRefundment();
}

function _cancelCustomerRefundmentCallBack() {
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

var voucherFlag = false;
/**
 * 提交
 */
function _submitProcessCustomerRefundment(flag) {
	voucherFlag =  flag;
	if (_presubmitProcessCustomerRefundment()) {
		var param = Form.serialize('mainForm');
		param = param + "&"+ Form.serialize('settleSubjectForm');
		param = param + "&"+ Form.serialize('fundFlowForm');
		param = param + "&" + getAllCustomerDbBankItemGridData();
		param = param + "&" + getAllCustomerRefundItemGridData();
		param = param + "&"+ getAllattachementAttachementGridData();   
		param = param + "&" + Form.serialize('workflowForm');
		new AjaxEngine(
				contextPath + '/xdss3/customerDrawback/customerRefundmentController.spr?action=_submitProcess',
				{
					method : "post",
					parameters : param,
					onComplete : callBackHandle,
					callback : _submitCallBackHandle
				});
	}
	_postsubmitProcessCustomerRefundment();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle(transport) {
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var msgType = responseUtil.getMessageType();
	if (msgType == 'info')
	{
		if(voucherFlag==true){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.submitSuccess+'是否需要查看凭证？',
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
				    	_getMainFrame().maintabs.addPanel2('凭证查看', contextPath+'/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.get('CustomerRefundment.refundmentid').dom.value);
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
}

/**
 * 客户退款银行行项目 批量删除
 */
function _predeletesCustomerDbBankItem() {
	if (CustomerDbBankItem_grid.selModel.hasSelection() > 0) {
		var records = CustomerDbBankItem_grid.selModel.getSelections();
		for ( var i = 0; i < records.length; i++) {
			CustomerDbBankItem_grid.getStore().remove(records[i]);
		}
	} else {
		_getMainFrame().showInfo(
				Txt.customerDbBankItem_Deletes_SelectToOperation);
	}
	return true;
}

/**
 * 客户退款行项目 批量删除
 */
function _predeletesCustomerRefundItem() {
	if (CustomerRefundItem_grid.selModel.hasSelection() > 0) {
		var records = CustomerRefundItem_grid.selModel.getSelections();
		for ( var i = 0; i < records.length; i++) {
			CustomerRefundItem_grid.getStore().remove(records[i]);
		}
	} else {
		_getMainFrame().showInfo(
				Txt.customerRefundItem_Deletes_SelectToOperation);
	}
	return true;
}

/**
 * 取消
 */
function _precancelCustomerRefundment() {
	return true;
}

/**
 * 取消
 */
function _postcancelCustomerRefundment() {
}