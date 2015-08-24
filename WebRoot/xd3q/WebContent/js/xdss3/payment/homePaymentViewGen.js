/**
 * Author(s):java业务平台代码生成工具 Date: 2010年08月02日 13点55分34秒 Copyright Notice:版权声明
 * 福建讯盟软件有限公司, 版权所有 违者必究
 * 
 * @(#) Description: <功能>主对象内贸付款(HomePayment)查看页面JS文件
 */

/**
 * 保存
 */
function _saveOrUpdateHomePayment()
{
	
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("HomePayment.paymentid").value = id;
}

/**
 * 取消
 */
function _cancelHomePayment()
{
	if (_precancelHomePayment())
	{
		new AjaxEngine(contextPath + '/xdss3/payment/homePaymentController.spr?action=_cancel&paymentid=' + paymentid,
				{
					method		: "post",
					parameters	: '',
					onComplete	: callBackHandle,
					callback	: _cancelHomePaymentCallBack
				});
	}
	_postcancelHomePayment();
}

function _cancelHomePaymentCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

/**
 * 提交
 */
function _submitProcessHomePayment(id)
{
	if (_presubmitProcessHomePayment())
	{
		
		/**
		 * if (Ext.getDom('workflowExamine').value.trim() == ''){
		 * _getMainFrame().showInfo('请输入审批意见！'); return ; }
		 */
		var param = Form.serialize('mainForm');
		
		param = param + "&" + getAllHomePaymentItemGridData();
		
		param = param + "&" + getAllHomePaymentCbillGridData();
		
		param = param + "&" + getAllHomePayBankItemGridData();
		
		param = param + "&" + getAllHomeDocuBankItemGridData();
		
		param = param + "&" + Form.serialize('homeSettSubjForm');
		
		param = param + "&" + Form.serialize('homeFundFlowForm');
		
		param = param + "&" + getAllHomePaymentRelatGridData();
		param = param + "&" + Form.serialize('workflowForm') + "&type=view";
		
		new AjaxEngine(contextPath + '/xdss3/payment/homePaymentController.spr?action=_submitProcess',
				{
					method		: "post",
					parameters	: param,
					onComplete	: callBackHandle,
					callback	: _submitCallBackHandle
				});
	}
	_postsubmitProcessHomePayment();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle(transport)
{   var businessid = document.getElementById("HomePayment.paymentid").value;
    var para = '?action=checkSpecil&businessId='+businessid+'&type=2';
    new AjaxEngine('/showProcessImgController.spr', 
							   {method:"post", parameters: para, onComplete: speckBackHandle});
}
function speckBackHandle(transport)
{   
    var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	if(customField.returnMessage>0){
	  document.getElementById('workflowForm').workflowTaskId.value = customField.returnMessage;
	  _submitProcessHomePayment();
	}else {
		_getMainFrame().showInfo(Txt.submitSuccess);
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	}
}
