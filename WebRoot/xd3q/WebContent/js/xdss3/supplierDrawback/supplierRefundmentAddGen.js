/**
 * Author(s):java业务平台代码生成工具
 * Date: 2010年07月02日 11点05分23秒
 * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 * @(#)
 * Description:  
 * <功能>主对象供应商退款(SupplierRefundment)增加页面JS文件
 */

/**
 *供应商退款行项目
 *批量删除
 */
function _deletesSupplierDbBankItem() {
	_predeletesSupplierDbBankItem();
}

/**
 *供应商退款行项目
 *批量删除
 */
function _deletesSupplierRefundItem() {
	_predeletesSupplierRefundItem();
}

/**
 * 保存 
 */
function _saveOrUpdateSupplierRefundment() {
	if (isCreateCopy) {
		document.getElementById("SupplierRefundment.refundmentid").value = "";
	}
	if (_presaveOrUpdateSupplierRefundment()) {
		var param = Form.serialize('mainForm');
		param = param + "&"+ Form.serialize('settleSubjectForm');
		param = param + "&"+ Form.serialize('fundFlowForm');
		if (isCreateCopy) {
			param = param + "" + getAllSupplierDbBankItemGridData();
		} else {
			param = param + "" + getSupplierDbBankItemGridData();
		}

		if (isCreateCopy) {
			param = param + "" + getAllSupplierRefundItemGridData();
		} else {
			param = param + "" + getSupplierRefundItemGridData();
		}
		new AjaxEngine(
				contextPath + '/xdss3/supplierDrawback/supplierRefundmentController.spr?action=_saveOrUpdate',
				{
					method : "post",
					parameters : param,
					onComplete : callBackHandle,
					callback : customCallBackHandle
				});
	}

	_postsaveOrUpdateSupplierRefundment();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport) {
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var refundmentid = result.refundmentid;
	document.getElementById("SupplierRefundment.refundmentid").value = refundmentid;

	document.getElementById("SupplierRefundment.createtime").value = result.createtime;
	document.getElementById("SupplierRefundment.creator_text").value = result.creator_text;
	document.getElementById("SupplierRefundment.creator").value = result.creator;
	document.getElementById("SupplierRefundment.lastmodifytime").value = result.lastmodifytime;
	isCreateCopy = false;
	reload_SupplierDbBankItem_grid("defaultCondition=YREFUNDBANKITEM.REFUNDMENTID='"
			+ refundmentid + "'");
	reload_SupplierRefundItem_grid("defaultCondition=YREFUNDMENTITEM.REFUNDMENTID='"
			+ refundmentid + "'");

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
function _submitProcessSupplierRefundment() {
	if (_presubmitProcessSupplierRefundment()) {
		var param = Form.serialize('mainForm');
		param = param + "&"+ Form.serialize('settleSubjectForm');
		param = param + "&"+ Form.serialize('fundFlowForm');
		param = param + "&" + getAllSupplierDbBankItemGridData();
		param = param + "&" + getAllSupplierRefundItemGridData();
		param = param + "&" + Form.serialize('workflowForm');

		new AjaxEngine(
				contextPath + '/xdss3/supplierDrawback/supplierRefundmentController.spr?action=_submitProcess',
				{
					method : "post",
					parameters : param,
					onComplete : callBackHandle,
					callback : _submitCallBackHandle
				});
	}
	_postsubmitProcessSupplierRefundment();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle() {
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

/**
 *供应商退款行项目 批量删除
 */
function _predeletesSupplierRefundItem(){
	if (SupplierRefundItem_grid.selModel.hasSelection() > 0){
		var records = SupplierRefundItem_grid.selModel.getSelections();		
		for(var i=0;i<records.length;i++){
			SupplierRefundItem_grid.getStore().remove(records[i]);
		}			
	}else{
		_getMainFrame().showInfo(Txt.supplierRefundItem_Deletes_SelectToOperation);
	}
	return true;
}

/**
 *供应商退款行项目 批量删除
 */
function _postdeletesSupplierRefundItem(){}

/**
 *供应商退款行项目 批量删除
 */
function _predeletesSupplierDbBankItem(){
	if (SupplierDbBankItem_grid.selModel.hasSelection() > 0){
		var records = SupplierDbBankItem_grid.selModel.getSelections();		
		for(var i=0;i<records.length;i++){
			SupplierDbBankItem_grid.getStore().remove(records[i]);
		}			
	}else{
		_getMainFrame().showInfo(Txt.supplierDbBankItem_Deletes_SelectToOperation);
	}
	return true;
}

/**
 *供应商退款行项目 批量删除
 */
function _postdeletesSupplierDbBankItem(){}

/**
 * 取消
 */
function _precancel(){
	return true;
}

/**
 * 取消
 */
function _postcancel(){}
