/**
 * Author(s):java业务平台代码生成工具 Date: 2010年06月30日 11点22分11秒 Copyright Notice:版权声明
 * 福建讯盟软件有限公司, 版权所有 违者必究
 * 
 * @(#) Description: <功能>主对象客户退款(CustomerRefundment)查看页面JS文件
 */
/**
 * 打印
 */
function  _printCustomerRefundment(){
    window.open(contextPath +'/xdss3/customerDrawback/customerRefundmentController.spr?action=_print&refundmentId='+document.getElementById('CustomerRefundment.refundmentid').value,'_blank','location=no,resizable=yes');
}

/*
 * 合同详细信息查看
 */ 
function viewContractInfo(contNo){
	var contUrl = contextPath + '/contractController.spr?action=viewSaleContract&contractno=' + contNo;
	if(contNo.trim()!=''){
		_getMainFrame().ExtModalWindowUtil.show('合同信息', contUrl, '', '', {width:700,height:600});
	}
}
/*
 * 立项详细信息查看
 */
function viewProjectInfo(projNo){
	var projUrl = contextPath + '/projectController.spr?action=modify&from=view&projectNo=' + projNo;
	if(projNo.trim()!=''){
		_getMainFrame().ExtModalWindowUtil.show('立项信息', projUrl, '', '', {width:700,height:600});
	}
}

/**
 * 模拟凭证
 */
function _previewCustomerRefundment()
{
	var param = Form.serialize('mainForm');
	param = param + "&"+ Form.serialize('settleSubjectForm');
	param = param + "&"+ Form.serialize('fundFlowForm');
	param = param + "" + getAllCustomerDbBankItemGridData();
	param = param + "" + getAllCustomerRefundItemGridData();
	// alert(param);
	new AjaxEngine(contextPath + '/xdss3/customerDrawback/customerRefundmentController.spr?action=_preview',
			{
				method		: "post",
				parameters	: param,
				onComplete	: callBackHandle1
			});
}

// 2010-10-13 HJJ 修改显示预览凭证参数传递错误
function callBackHandle1(transport)
{
	var promptMessagebox = new MessageBoxUtil();
	promptMessagebox.Close();
	if (transport.responseText)
	{
		var voucherid = transport.responseText;
		var andFlag = voucherid.indexOf("&");
//		if (andFlag == -1)
//		{
//			_getMainFrame().maintabs.addPanel('模拟凭证', '', contextPath + '/xdss3/voucher/voucherController.spr?action=_edit&voucherid=' + voucherid);
//		}
//		else
//		{
//			_getMainFrame().maintabs.addPanel('模拟凭证', '', contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&voucherid=' + voucherid);
//		}
		Ext.get('currentWorkflowTask').mask();
		Ext.get('centercontent').mask();
		Ext.get(Ext.getCmp("tabs").getActiveTab().getItemId()).mask();
		Ext.getCmp("tabs").on('tabchange',function(t,p){
			Ext.get(p.getItemId()).mask();
		});
		_getMainFrame().maintabs.addPanel('模拟凭证','', contextPath+'/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.getDom('CustomerRefundment.refundmentid').value,closeVoucherCallBack,true);
	}
}

/**
 * 关闭凭证窗体回调动作
 * */
function closeVoucherCallBack(flag){
	Ext.get('currentWorkflowTask').unmask();
	Ext.get('centercontent').unmask();
	Ext.get(Ext.getCmp("tabs").getActiveTab().getItemId()).unmask();
	
	Ext.getCmp("tabs").on('tabchange',function(t,p){
		Ext.get(p.getItemId()).unmask();
	});
}

function _submitForReassignCustomerRefundment()
{
	if (_presubmitProcessCustomerRefundment())
	{
		var param = Form.serialize('mainForm');
		param = param + "&"+ Form.serialize('settleSubjectForm');
		param = param + "&"+ Form.serialize('fundFlowForm');
		param = param + "&" + getAllCustomerDbBankItemGridData();
		
		param = param + "&" + getAllCustomerRefundItemGridData();
		param = param + "&" + Form.serialize('workflowForm') + "&type=view";
		
		new AjaxEngine(contextPath + '/xdss3/customerDrawback/customerRefundmentController.spr?action=_submitForReassign',
				{
					method		: "post",
					parameters	: param,
					onComplete	: callBackHandle,
					callback	: _submitCallBackHandle
				});
	}
	_postsubmitProcessCustomerRefundment();
}

/**
 * 客户退款行项目编辑操作
 */
function _preeditCustomerDbBankItem(id, url)
{
	return true;
}

/**
 * 客户退款行项目编辑操作
 */
function _posteditCustomerDbBankItem(id, url)
{
	
}

/**
 * 客户退款行项目查看操作
 */
function _previewCustomerDbBankItem(id, url)
{
	return true;
}

/**
 * 客户退款行项目查看操作
 */
function _postviewCustomerDbBankItem(id, url)
{
	
}

/**
 * 保存
 */
function _presaveOrUpdateCustomerRefundment()
{
	return true;
}

/**
 * 保存
 */
function _postsaveOrUpdateCustomerRefundment()
{
	
}

/**
 * 取消
 */
function _precancelCustomerRefundment()
{
	return false;
}

/**
 * 取消
 */
function _postcancelCustomerRefundment()
{
	new AjaxEngine(contextPath + '/xdss3/customerDrawback/customerRefundmentController.spr?action=_cancel&refundmentid=' + refundmentid,
			{
				method		: "post",
				parameters	: '',
				onComplete	: callBackHandle,
				callback	: cancelCustomerRefundmentCallBack
			});
	
}

function cancelCustomerRefundmentCallBack()
{
	if (_getMainFrame().ExtModalWindowUtil.getActiveExtWin())
	{
		_getMainFrame().ExtModalWindowUtil.close();
	}
	else
	{
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	}
}

/**
 * 提交
 */
function _presubmitProcessCustomerRefundment(id)
{
	return true;
}

/**
 * 提交
 */
function _postsubmitProcessCustomerRefundment(id)
{
	
}

/**
 * 查看信用额度
 * @return
 */
function _viewCredit(){
	if (CustomerRefundItem_store.getCount() <= 0){
		_getMainFrame().showInfo('无退款行项!');
		return false;
	}
	
	if(div_customer_sh_sh.getValue()==''){
        _getMainFrame().showInfo("请选择一个客户！");
        return;
	}

	var customer = div_customer_sh_sh.getValue();
	var projectId = CustomerRefundItem_store.getAt(0).get('project_no');
	var cleargoodsamount = CustomerRefundItem_store.getAt(0).get('cleargoodsamount'); // 放货
	var clearprepayvalue = CustomerRefundItem_store.getAt(0).get('clearprepayvalue'); // 代垫
	var clearvendorvalue = CustomerRefundItem_store.getAt(0).get('clearvendorvalue'); // 供应商
	var conn = Ext.lib.Ajax.getConnectionObject().conn;
	var url = '';
	if (cleargoodsamount != 0) {
		url = contextPath+'/xdss3/payment/importPaymentController.spr?action=checkCredit&providerid=a&projectno='+ projectId +
				'&value='+ round(cleargoodsamount,2)
	} else if (clearprepayvalue != 0) {
		url = contextPath+'/xdss3/payment/importPaymentController.spr?action=checkCredit&providerid=a&projectno='+ projectId +
				'&value='+ round(clearprepayvalue,2)
	} else if (clearvendorvalue != 0) {
		url = contextPath+'/xdss3/payment/importPaymentController.spr?action=checkCredit&providerid=a&projectno='+ projectId +
				'&value='+ round(clearvendorvalue,2)
	}
	if (url != '' ) {
		conn.open("POST", url, false);
		conn.send(null);
		var jsonData = Ext.util.JSON.decode(conn.responseText);
		var andFlag = jsonData.result.split("&");
		if (andFlag[0] == 'false' && andFlag[1] == 'false'){
			_getMainFrame().showInfo('该立项没有授信!');
		}else{
			_getMainFrame().showInfo(andFlag[0]);
		}
	} else {
			_getMainFrame().showInfo('该立项没有授信!');
	}
}