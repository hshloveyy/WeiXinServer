/**
 * Author(s):java业务平台代码生成工具 Date: 2010年06月30日 11点22分36秒 Copyright Notice:版权声明
 * 福建讯盟软件有限公司, 版权所有 违者必究
 * 
 * @(#) Description: <功能>主对象供应商退款(SupplierRefundment)查看页面JS文件
 */

Ext.onReady(function(){
//	Ext.getCmp("_submitForReassign").hide();
});

// 模拟凭证
function _previewSupplierRefundment()
{
	
	var param = Form.serialize('mainForm');
	param = param + "&"+ Form.serialize('settleSubjectForm');
	param = param + "&"+ Form.serialize('fundFlowForm');
	param = param + "" + getAllSupplierDbBankItemGridData();
	param = param + "" + getAllSupplierRefundItemGridData();
	new AjaxEngine(contextPath + '/xdss3/supplierDrawback/supplierRefundmentController.spr?action=_preview',
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
		_getMainFrame().maintabs.addPanel('模拟凭证','', contextPath+'/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.getDom('SupplierRefundment.refundmentid').value,closeVoucherCallBack,true);
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

/**
 * 供应商退款行项目编辑操作
 */
function _preeditSupplierDbBankItem(id, url)
{
	return true;
}

/**
 * 供应商退款行项目编辑操作
 */
function _posteditSupplierDbBankItem(id, url)
{
	
}

/**
 * 供应商退款行项目查看操作
 */
function _previewSupplierDbBankItem(id, url)
{
	return true;
}

/**
 * 供应商退款行项目查看操作
 */
function _postviewSupplierDbBankItem(id, url)
{
	
}

/**
 * 保存
 */
function _presaveOrUpdateSupplierRefundment()
{
	return true;
}

/**
 * 保存
 */
function _postsaveOrUpdateSupplierRefundment()
{
	
}

/**
 * 取消
 */
function _precancelSupplierRefundment()
{
	return false;
}

/**
 * 取消
 */
function _postcancelSupplierRefundment()
{
	new AjaxEngine(contextPath + '/xdss3/supplierDrawback/supplierRefundmentController.spr?action=_cancel&refundmentid=' + refundmentid,
			{
				method		: "post",
				parameters	: '',
				onComplete	: callBackHandle,
				callback	: cancelSupplierRefundmentCallBack
			});
	
}

function cancelSupplierRefundmentCallBack()
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
function _presubmitProcessSupplierRefundment(id)
{
	return true;
}

/**
 * 提交
 */
function _postsubmitProcessSupplierRefundment(id)
{
	
}

/**
 * 重分配提交
 * 
 * @return
 */
function _submitForReassignSupplierRefundment()
{
	if (_presubmitProcessSupplierRefundment())
	{
		var param = Form.serialize('mainForm');
		param = param + "&"+ Form.serialize('settleSubjectForm');
		param = param + "&"+ Form.serialize('fundFlowForm');
		param = param + "&" + getAllSupplierDbBankItemGridData();
		param = param + "&" + getAllSupplierRefundItemGridData();
		param = param + "&" + Form.serialize('workflowForm') + "&type=view";
		
		new AjaxEngine(contextPath + '/xdss3/supplierDrawback/supplierRefundmentController.spr?action=_submitForReassign',
				{
					method		: "post",
					parameters	: param,
					onComplete	: callBackHandle,
					callback	: _submitCallBackHandle
				});
	}
	_postsubmitProcessSupplierRefundment();
}
