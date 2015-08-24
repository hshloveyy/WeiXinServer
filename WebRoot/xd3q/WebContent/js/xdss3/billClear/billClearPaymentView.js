/**
 * Author(s):java业务平台代码生成工具 Date: 2010年06月23日 12点02分19秒 Copyright Notice:版权声明
 * 福建讯盟软件有限公司, 版权所有 违者必究
 * 
 * @(#) Description: <功能>主对象票清付款(BillClearPayment)查看页面JS文件
 */

/**
 * 模拟凭证
 */
function _voucherPreviewBillClearPayment()
{
	var sumadjustamount = 0;
	for (var i = 0; i < BillClearItemPay_grid.getStore().getCount(); i++)
	{
		var rec = BillClearItemPay_grid.getStore().getAt(i);
		sumadjustamount += parseFloat(rec.get('adjustamount'));
	}
	
	if (sumadjustamount == 0)
	{
		showInfo("调差金额总计为0，不需要生成凭证！");
		return;
	}
	
	var param = Form.serialize('mainForm');
	param = param + "" + getAllBillClearItemPayGridData();
	param = param + "" + getAllBillInPaymentGridData();
	param = param + "&" + Form.serialize('fundFlowForm');
	param = param + "&" + Form.serialize('settleSubjectForm');
	
	new AjaxEngine(contextPath + '/xdss3/billClear/billClearPaymentController.spr?action=_voucherPreview',
			{
				method		: "post",
				parameters	: param,
				onComplete	: callBackHandle,
				callback	: voucherPreviewCallBackHandle
			});
}

function voucherPreviewCallBackHandle(transport)
{	
	if (transport.responseText)
	{
		var responseUtil = new AjaxResponseUtils(transport.responseText);
		var result = responseUtil.getCustomField("coustom");		
		if(result==null || result ==""){
        	_getMainFrame().showInfo("没有凭证预览可生成");        
        }else{
        	_getMainFrame().maintabs.addPanel('票清付款凭证预览', '', contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid=' + result);
		}
	}
}

function _assignAmount(vendortitleids)
{
	if (!vendortitleids)
		return;
	Ext.Ajax.request(
			{
				url		: contextPath + '/xdss3/billClear/billClearPaymentController.spr?action=getBillClearItemData',
				params	:
				{
					supplier		: div_supplier_sh_sh.getValue(),
					vendortitleids	: vendortitleids
				},
				success	: function(xhr)
				{
					if (xhr.responseText)
					{
						var jsonData = Ext.util.JSON.decode(xhr.responseText);
						for (var j = 0; j < jsonData.data.length; j++)
						{
							var num = BillClearItemPay_grid.getStore().find('titleid', jsonData.data[j].titleid);
							if (num == -1)
							{
								var p = new Ext.data.Record(jsonData.data[j]);
								BillClearItemPay_grid.stopEditing();
								BillClearItemPay_grid.getStore().insert(0, p);
								BillClearItemPay_grid.startEditing(0, 0);
							}
							else
							{
								var rec1 = BillClearItemPay_grid.getStore().query('titleid', jsonData.data[j].titleid);
								rec1.first().set('clearbillamount', jsonData.data[j].clearbillamount);
							}
						}
						BillInPayment_grid.getStore().removeAll();
						
						for (var j = 0; j < jsonData.data2.length; j++)
						{
							var p = new Ext.data.Record(jsonData.data2[j]);
							BillInPayment_grid.stopEditing();
							BillInPayment_grid.getStore().insert(0, p);
							BillInPayment_grid.startEditing(0, 0);
						}
					}
				},
				scope	: this
			});
}

/**
 * 自动分配
 */
function _autoAssignBillClearPayment()
{
	var vendortitleids = "";
	for (var i = 0; i < BillClearItemPay_grid.getStore().getCount(); i++)
	{
		var rec = BillClearItemPay_grid.getStore().getAt(i);
		vendortitleids += "'" + rec.data.titleid + "',";
	}
	
	vendortitleids = vendortitleids.substring(0, vendortitleids.length - 1);
	_assignAmount(vendortitleids);
	
}

/**
 * 清除分配
 */
function _clearAssignBillClearPayment()
{
	for (var i = 0; i < BillClearItemPay_grid.getStore().getCount(); i++)
	{
		var rec = BillClearItemPay_grid.getStore().getAt(i);
		rec.set('clearbillamount', 0);
		rec.set('adjustamount', 0);
	}
	
	for (var i = 0; i < BillInPayment_grid.getStore().getCount(); i++)
	{
		var rec = BillInPayment_grid.getStore().getAt(i);
		rec.set('nowclearamount', 0);
	}
}

/**
 * 增加
 */
function _addBillClearItemPay()
{
	if (pre_addBillClearItemPay())
	{
		// 方法执行体
	}
	post_addBillClearItemPay();
}

/**
 * 增加
 */
function pre_addBillClearItemPay()
{
	return true;
}

/**
 * 增加
 */
function post_addBillClearItemPay()
{
	
}

/**
 * 票清付款行项目 grid上的和删除操作
 */
function _predeletesBillClearItemPay()
{
	return true;
}

/**
 * 票清付款行项目 grid上的和删除操作
 */
function _postdeletesBillClearItemPay()
{
	
}

/**
 * 保存
 */
function _presaveOrUpdateBillClearPayment()
{
	return true;
}

/**
 * 保存
 */
function _postsaveOrUpdateBillClearPayment()
{
	
}

/**
 * 提交
 */
function _presubmitProcessBillClearPayment(id)
{
	return false;
}

/**
 * 提交
 */
function _postsubmitProcessBillClearPayment(id)
{
	var msg = _checkFilds();
	if (msg != "")
	{
		showInfo(msg);
		return;
	}
	
	var param = Form.serialize('mainForm');
	param = param + "&" + getAllBillClearItemPayGridData();
	param = param + "&" + getAllBillInPaymentGridData();
	param = param + "&" + Form.serialize('fundFlowForm');
	param = param + "&" + Form.serialize('settleSubjectForm');
	param = param + "&" + Form.serialize('workflowForm');
	
	new AjaxEngine(contextPath + '/xdss3/billClear/billClearPaymentController.spr?action=_submitProcess',
			{
				method		: "post",
				parameters	: param,
				onComplete	: callBackHandle,
				callback	: _submitCallBackHandle
			});
}

/**
 * 取消
 */
function _precancelBillClearPayment()
{
	return true;
}

/**
 * 取消
 */
function _postcancelBillClearPayment()
{
	
}

/**
 * 重分配提交
 * @return
 */
function _submitForReassignBillClearPayment()
{
	if(_presubmitProcessBillClearPayment()){
		var param = Form.serialize('mainForm');	
	          
	         
		param = param + "&" + getAllBillClearItemPayGridData();
		        	          
	         
		param = param + "&" + getAllBillInPaymentGridData();
		        	          
	 
		param = param + "&" + Form.serialize('fundFlowForm');		
	          
	 
		param = param + "&" + Form.serialize('settleSubjectForm');		
		param = param + "&"+ Form.serialize('workflowForm');
		
		new AjaxEngine(contextPath +'/xdss3/billClear/billClearPaymentController.spr?action=_submitForReassign', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	  }
	  _postsubmitProcessBillClearPayment();
}

Ext.onReady(function()
        {
 
   			 Ext.getCmp('_voucherPreview').hide();
			Ext.getCmp('_submitProcess').hide();
			Ext.getCmp('_autoAssign').hide();
			Ext.getCmp('_clearAssign').hide();
			Ext.getCmp('_submitForReassign').hide();
			
            div_supplier_sh_sh.defaultCondition = " LIFNR in(select LIFNR from YVENDORTITLE where ISCLEARED='0' and SHKZG='H')";
//            div_supplier_sh_sh.on('change', function(e, n, o)
//                    {
//                        searchBillClearItemPayHelpWin.defaultCondition = " INVOICE is not null and INVOICE!=' ' and LIFNR='" + n + "'";
//                        
//                    });

	
			 var uncbToolbar = BillClearItemPay_grid.getTopToolbar();
			uncbToolbar.items.get('BillClearItemPayaddRow').hide();		// 隐藏"增加行"按钮
			uncbToolbar.items.get('BillClearItemPaydeleteRow').hide();	// 隐藏"删除行"按钮
			
			var unccToolbar = BillInPayment_grid.getTopToolbar();
			unccToolbar.items.get('BillInPaymentaddRow').hide();		// 隐藏"增加行"按钮
			unccToolbar.items.get('BillInPaymentdeleteRow').hide();	// 隐藏"删除行"按钮
		
            
        });
        
        
        
function _showVoucherBillClearPayment(){

_getMainFrame().maintabs.addPanel2('凭证查看', contextPath+'/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.get('BillClearPayment.billclearid').dom.value);

}