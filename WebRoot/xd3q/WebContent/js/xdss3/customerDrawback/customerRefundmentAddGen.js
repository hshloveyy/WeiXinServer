/**
 * Author(s):java业务平台代码生成工具
 * Date: 2010年07月02日 09点27分03秒
 * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 * @(#)
 * Description:  
 * <功能>主对象客户退款(CustomerRefundment)增加页面JS文件
 */

/**
 *客户退款行项目
 *批量删除
 */
function _deletesCustomerDbBankItem() {
	_predeletesCustomerDbBankItem();
}

/**
 *客户退款行项目
 *批量删除
 */
function _deletesCustomerRefundItem() {
	_predeletesCustomerRefundItem();
}

/**
 * 保存 
 */
function _saveOrUpdateCustomerRefundment() {
	if (isCreateCopy) {
		document.getElementById("CustomerRefundment.refundmentid").value = "";
	}
	if (_presaveOrUpdateCustomerRefundment()) {
		var param = Form.serialize('mainForm');
		param = param + "&"+ Form.serialize('settleSubjectForm');
		param = param + "&"+ Form.serialize('fundFlowForm');
		if (isCreateCopy) {
			param = param + "" + getAllCustomerDbBankItemGridData();
		} else {
			param = param + "" + getCustomerDbBankItemGridData();
		}
		if (isCreateCopy) {
			param = param + "" + getAllCustomerRefundItemGridData();
		} else {
			param = param + "" + getCustomerRefundItemGridData();
		}
		if(isCreateCopy)
		{
			param = param + ""+ getAllattachementAttachementGridData();
		}
		else
		{
			param = param + ""+ getattachementAttachementGridData(); 
		}
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
	var refundmentid = result.refundmentid;
	document.getElementById("CustomerRefundment.refundmentid").value = refundmentid;
	document.getElementById("CustomerRefundment.createtime").value = result.createtime;
	document.getElementById("CustomerRefundment.creator_text").value = result.creator_text;
	document.getElementById("CustomerRefundment.creator").value = result.creator;
	document.getElementById("CustomerRefundment.lastmodifytime").value = result.lastmodifytime;
	isCreateCopy = false;
	reload_CustomerDbBankItem_grid("defaultCondition=YREFUNDBANKITEM.REFUNDMENTID='"
			+ refundmentid + "'");
	reload_CustomerRefundItem_grid("defaultCondition=YREFUNDMENTITEM.REFUNDMENTID='"
			+ refundmentid + "'");
	reload_attachementAttachement_grid("defaultCondition=YATTACHEMENT.BUSINESSID='"+ refundmentid +"' and YATTACHEMENT.BOPROPERTY='attachement'");

	if (Ext.getCmp("_submitProcess").disabled) {
		Ext.getCmp("_submitProcess").setDisabled(false);
	}
	if (Ext.getCmp("_submitForReassign").disabled) {
		Ext.getCmp("_submitForReassign").setDisabled(false);
	}
}

/**
 * 取消
 */
function _cancel() {
	if (_precancel()) {
		_getMainFrame().maintabs
				.remove(_getMainFrame().maintabs.getActiveTab());
	}
	_postcancel();
}

/**
 * 提交
 */
function _submitProcessCustomerRefundment() {
	if (_presubmitProcessCustomerRefundment()) {
		var param = Form.serialize('mainForm');
		param = param + "&"+ Form.serialize('settleSubjectForm');
		param = param + "&"+ Form.serialize('fundFlowForm');
		param = param + "&" + getAllCustomerDbBankItemGridData();
		param = param + "&" + getAllCustomerRefundItemGridData();
		param = param + "&" + getAllattachementAttachementGridData();     
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
function _submitCallBackHandle() {
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
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
		_getMainFrame().showInfo(Txt.customerDbBankItem_Deletes_SelectToOperation);
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
function _precancel() {
	return true;
}

/**
 * 取消
 */
function _postcancel() {}
