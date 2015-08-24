/**
 * Author(s):java业务平台代码生成工具
 * Date: 2010年07月02日 11点05分24秒
 * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 * @(#)
 * Description:  
 * <功能>主对象供应商退款(SupplierRefundment)编辑页面JS文件
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
	if (_presaveOrUpdateSupplierRefundment()) {
		var param = Form.serialize('mainForm');
		param = param + "&"+ Form.serialize('settleSubjectForm');
		param = param + "&"+ Form.serialize('fundFlowForm');
		param = param + "" + getSupplierDbBankItemGridData();
		param = param + "" + getSupplierRefundItemGridData();
		//alert(param);
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
	//var id = responseUtil.getCustomField('coustom');
	var refundmentid = result.refundmentid;
	//document.getElementById("SupplierRefundment.refundmentid").value = id;
	document.getElementById("SupplierRefundment.createtime").value = result.createtime;
	document.getElementById("SupplierRefundment.creator_text").value = result.creator_text;
	document.getElementById("SupplierRefundment.creator").value = result.creator;
	document.getElementById("SupplierRefundment.lastmodifytime").value = result.lastmodifytime;
	reload_SupplierDbBankItem_grid("defaultCondition=YREFUNDBANKITEM.REFUNDMENTID='"
			+ refundmentid + "'");
	reload_SupplierRefundItem_grid("defaultCondition=YREFUNDMENTITEM.REFUNDMENTID='"
			+ refundmentid + "'");
}

/**
 * 取消
 */
function _cancelSupplierRefundment() {
	if (_precancelSupplierRefundment()) {
		new AjaxEngine(
				contextPath
						+ '/xdss3/supplierDrawback/supplierRefundmentController.spr?action=_cancel&refundmentid='
						+ refundmentid, {
					method : "post",
					parameters : '',
					onComplete : callBackHandle,
					callback : _cancelSupplierRefundmentCallBack
				});
	}
	_postcancelSupplierRefundment();
}

function _cancelSupplierRefundmentCallBack() {
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

var voucherFlag = false; // 是否成功确认凭证
/**
 * 提交
 */
function _submitProcessSupplierRefundment(flag) {
	if (_presubmitProcessSupplierRefundment()) {
		voucherFlag = flag;
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
				    	_getMainFrame().maintabs.addPanel2('凭证查看', contextPath+'/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.get('supplierRefundment.refundmentid').dom.value);
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
