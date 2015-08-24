/**
 * Author(s):java业务平台代码生成工具 Date: 2010年07月09日 10点26分57秒 Copyright Notice:版权声明
 * 福建讯盟软件有限公司, 版权所有 违者必究
 * 
 * @(#) Description: <功能>主对象供应商单清帐(SupplierSinglClear)查看页面JS文件
 */

/**
 * 取消
 */
function _precancelSupplierSinglClear()
{
	return true;
}

/**
 * 取消
 */
function _postcancelSupplierSinglClear()
{
	
}

/**
 * 提交
 */
function _presubmitProcessSupplierSinglClear(id)
{
	return true;
}

/**
 * 提交
 */
function _postsubmitProcessSupplierSinglClear(id)
{
	
}

/**
 * 查询
 */
function _queryUnClearSupplierSinglClear()
{
	// 方法执行体
	if (div_supplier_sh_sh.getValue() == '')
	{
		_getMainFrame().showInfo("必须先选择[供应商]！");
		return;
	}
	
	if (div_subject_sh_sh.getValue() == '')
	{
		_getMainFrame().showInfo("必须先选择[会计科目]！");
		return;
	}
	if (div_currencytext_sh_sh.getValue() == '')
	{
		_getMainFrame().showInfo("必须先选择[货币文本]！");
		return;
	}
	
	if (div_companyno_sh_sh.getValue() == '')
	{
		_getMainFrame().showInfo("必须先选择[公司代码]！");
		return;
	}
	
	if (div_depid_sh_sh.getValue() == '')
	{
		_getMainFrame().showInfo("必须先选择[业务范围]！");
		return;
	}
	
	Ext.Ajax.request(
			{
				url		: contextPath + '/xdss3/singleClear/supplierSinglClearController.spr?action=_queryUnClear',
				params	:
				{
					supplier	: div_supplier_sh_sh.getValue(),
					subject		: div_subject_sh_sh.getValue(),
					currencytext : div_currencytext_sh_sh.getValue(),
					companyno : div_companyno_sh_sh.getValue(),
					depid : div_depid_sh_sh.getValue()
				},
				success	: function(xhr)
				{
					if (xhr.responseText)
					{
						var jsonData = Ext.util.JSON.decode(xhr.responseText);
						
						UnClearSupplieBill_grid.getStore().removeAll();
						UnClearPayment_grid.getStore().removeAll();
						
						for (var j = 0; j < jsonData.data.length; j++)
						{
							var p = new Ext.data.Record(jsonData.data[j]);
							UnClearSupplieBill_grid.stopEditing();
							UnClearSupplieBill_grid.getStore().insert(0, p);
							UnClearSupplieBill_grid.startEditing(0, 0);
						}
						// UnClearSupplieBill_grid.getStore().commitChanges();
						
						for (var j = 0; j < jsonData.data2.length; j++)
						{
							var p = new Ext.data.Record(jsonData.data2[j]);
							UnClearPayment_grid.stopEditing();
							UnClearPayment_grid.getStore().insert(0, p);
							UnClearPayment_grid.startEditing(0, 0);
						}
						// UnClearPayment_grid.getStore().commitChanges();
					}
				},
				scope	: this
			});
}

/**
*自动删除为0的行项目
**/
function removeGrid(){
	for (var i = UnClearSupplieBill_grid.getStore().getCount()-1; i >=0 ; i--)
	{
		var ds = UnClearSupplieBill_grid.getStore();
		var rec = ds.getAt(i);
		var clearmount = rec.get('clearamount');
		var receivableamount = rec.get('receivableamount');
		if((clearmount=='0' || clearmount=='') && receivableamount !='0'){
			ds.remove(rec);
		}		
	}
	
	for (var i = UnClearPayment_grid.getStore().getCount()-1; i >=0 ; i--)
	{
		var ds = UnClearPayment_grid.getStore();
		var rec = ds.getAt(i);
		var nowclearamount = rec.get('nowclearamount');
		var paymentamount = rec.get('paymentamount');
		if( (nowclearamount=='0' || nowclearamount=='' ) && paymentamount !='0'){
			ds.remove(rec);
		}
	}
	
}

/**
*校验grid
**/
function _checkGrid(){
	for (var i = 0; i < UnClearSupplieBill_grid.getStore().getCount(); i++)
	{
		var rec = UnClearSupplieBill_grid.getStore().getAt(i);
		var clearmount = rec.get('clearamount');
		if(clearmount=='0' || clearmount==''){
			return "消帐金额不能为空，或为0";
		}		
	}
	
	for (var i = 0; i < UnClearPayment_grid.getStore().getCount(); i++)
	{
		var rec = UnClearPayment_grid.getStore().getAt(i);
		var nowclearamount = rec.get('nowclearamount');
		if(nowclearamount=='0' || nowclearamount==''){
			return "本次已抵消金额不能为空，或为0";
		}
	}
	return "";
}

/**
 * 校验字段值
 */
function _checkFilds()
{
	if (div_supplier_sh_sh.getValue() == '')
	{
		return "必须先选择[供应商]！";
	}
	
	if (div_subject_sh_sh.getValue() == '')
	{
		return "必须先选择[会计科目]！";
	}
	if (div_currencytext_sh_sh.getValue() == '')
	{
		
		return "必须先选择[货币文本]！";
	}
	
	if (div_companyno_sh_sh.getValue() == '')
	{
		
		return "必须先选择[公司代码]！";
	}
	
	if (div_depid_sh_sh.getValue() == '')
	{
		
		return "必须先选择[业务范围]！";
	}
	
	
	if(document.getElementById("SupplierSinglClear.text").value == ''){
		return "必须先填写[文本]！";
	}
	if(document.getElementById("SupplierSinglClear.accountdate").value == ''){
		return "必须先填写[记账日期]！";
	}
	if(document.getElementById("SupplierSinglClear.voucherdate").value == ''){
		return "必须先填写[凭证日期]！";
	}
	var sumclearamount = 0;
	var sumadjustamount = 0;
	var sumnowclearamount = 0;
	for (var i = 0; i < UnClearSupplieBill_grid.getStore().getCount(); i++)
	{
		var rec = UnClearSupplieBill_grid.getStore().getAt(i);
		sumclearamount += parseFloat(rec.get('clearamount'));
		sumadjustamount += parseFloat(rec.get('adjustamount'));
	}
	
	for (var i = 0; i < UnClearPayment_grid.getStore().getCount(); i++)
	{
		var rec = UnClearPayment_grid.getStore().getAt(i);
		sumnowclearamount += parseFloat(rec.get('nowclearamount'));
	}
	if(isNaN(sumclearamount)){
		return "清帐金额的值不能为空，请输入清帐金额！";
	}
	if(isNaN(sumnowclearamount)){
		return "本次抵消付款的值不能为空，请输入清帐金额！";
	}
	if(isNaN(sumadjustamount)){
		return "调整差额的值不能为空，请输入清帐金额！";
	}
	if (sumclearamount == 0)
	{
		return "清帐金额的合计值不能等于0，请输入清帐金额！";
	}
	var totAmount = 0;
	var famount1 = 0;
	var famount2 = 0;
	var famount3 = 0;
	var samount1 = 0;
	var samount2 = 0;
	var samount3 = 0;
	var samount4 = 0;
	var fdebtcredit1 = ""
	var fdebtcredit2 = "";
	var fdebtcredit3 = "";
	var sdebtcredit1 = ""
	var sdebtcredit2 = ""
	var sdebtcredit3 = ""
	var sdebtcredit4 = ""
	
    if ($('FundFlow.amount1').value)
    {
        famount1 = parseFloat($('FundFlow.amount1').value);
        if (famount1 < 0)
        {
            return "纯资金往来[金额1]必需输入正数！";
        }
        fdebtcredit1 = $('FundFlow.debtcredit1').value;
        if (!fdebtcredit1 && famount1>0)
        {
            return "必须选择纯资金往来[借贷1]！";
        }
    }
    if ($('FundFlow.amount2').value)
    {
        famount2 = parseFloat($('FundFlow.amount2').value);
        if (famount2 < 0)
        {
            return "纯资金往来[金额2]必需输入正数！";
        }
        fdebtcredit2 = $('FundFlow.debtcredit2').value;
        if (!fdebtcredit2  && famount2>0)
        {
            return "必须选择纯资金往来[借贷2]！";
        }
    }
    if ($('FundFlow.amount3').value)
    {
        famount3 = parseFloat($('FundFlow.amount3').value);
        if (famount3 < 0)
        {
            return "纯资金往来[金额1]必需输入正数！";
        }
        fdebtcredit3 = $('FundFlow.debtcredit3').value;
        if (!fdebtcredit3  && famount3>0)
        {
            return "必须选择纯资金往来[借贷3]！";
        }
    }
    
    if ($('SettleSubject.amount1').value)
    {
        samount1 = parseFloat($('SettleSubject.amount1').value);
        if (samount1 < 0)
        {
            return "结算科目[金额1]必需输入正数！";
        }
        sdebtcredit1 = $('SettleSubject.debtcredit1').value;
        if (!sdebtcredit1  && samount1>0)
        {
            return "必须选择结算科目[借贷1]！";
        }
    }
    if ($('SettleSubject.amount2').value)
    {
        samount2 = parseFloat($('SettleSubject.amount2').value);
        if (samount2 < 0)
        {
            return "结算科目[金额2]必需输入正数！";
        }
        sdebtcredit2 = $('SettleSubject.debtcredit2').value;
        if (!sdebtcredit2  && samount2>0)
        {
            return "必须选择结算科目[借贷2]！";
        }
    }
    if ($('SettleSubject.amount3').value)
    {
        samount3 = parseFloat($('SettleSubject.amount3').value);
        if (samount3 < 0)
        {
            return "结算科目[金额3]必需输入正数！";
        }
        sdebtcredit3 = $('SettleSubject.debtcredit3').value;
        if (!sdebtcredit3  && samount3>0)
        {
            return "必须选择结算科目[借贷3]！";
        }
    }
    if ($('SettleSubject.amount4').value)
    {
        samount4 = parseFloat($('SettleSubject.amount4').value);
        if (samount4 < 0)
        {
            return "结算科目[金额4]必需输入正数！";
        }
        sdebtcredit4 = $('SettleSubject.debtcredit4').value;
        if (!sdebtcredit4  && samount4>0)
        {
            return "必须选择结算科目[借贷4]！";
        }
    }
	
		//H是代，S是借
	if (fdebtcredit1 == "H")
		totAmount = totAmount + famount1;
	else if (fdebtcredit1 == "S")
		totAmount = totAmount - famount1;
	
	if (fdebtcredit2 == "H")
		totAmount = totAmount + famount2;
	else if (fdebtcredit2 == "S")
		totAmount = totAmount - famount2;
	
	if (fdebtcredit3 == "H")
		totAmount = totAmount + famount3;
	else if (fdebtcredit3 == "S")
		totAmount = totAmount - famount3;
	
	if (sdebtcredit1 == "H")
		totAmount = totAmount + samount1;
	else if (sdebtcredit1 == "S")
		totAmount = totAmount - samount1;
	if (sdebtcredit2 == "H")
		totAmount = totAmount + samount2;
	else if (sdebtcredit2 == "S")
		totAmount = totAmount - samount2;
	if (sdebtcredit3 == "H")
		totAmount = totAmount + samount3;
	else if (sdebtcredit3 == "S")
		totAmount = totAmount - samount3;
	if (sdebtcredit4 == "H")
		totAmount = totAmount + samount4;
	else if (sdebtcredit4 == "S")
		totAmount = totAmount - samount4;
	//totAmount = famount1 + famount2 + famount3 + samount1 + samount2 + samount3 + samount4;
    
	if (round((sumnowclearamount + sumadjustamount),2) != round(sumclearamount,2))
	{
		return "本次抵消付款的总和(" + round(sumnowclearamount,2) + ")加上调整差额的合计值(" + round(sumadjustamount,2) + ")必须等于清帐金额的合计值(" + round(sumclearamount,2) + ")！";
	}
	else if (round(totAmount,2) != round(sumadjustamount,2))
	{
		return "结算科目金额总计+纯资金往来金额总计的和(" + round(totAmount,2) + ")必须等于 调整差额的合计值(" + round(sumadjustamount,2) + ")！";
	}
	else
	{
		return "";
	}
}

/**
 * 确认清帐
 */
function _submitClearSupplierSinglClear()
{
	var msg = _checkFilds();
	if (msg != "")
	{
		showInfo(msg);
		return;
	}
	var msg2 = _checkGrid();
	if (msg2 != "")
	{
		showInfo(msg2);
		return;
	}
	var param = Form.serialize('mainForm');
	param = param + "" + getAllUnClearSupplieBillGridData();
	param = param + "" + getAllUnClearPaymentGridData();
	param = param + "&" + Form.serialize('fundFlowForm');
	param = param + "&" + Form.serialize('settleSubjectForm');
	
	new AjaxEngine(contextPath + '/xdss3/singleClear/supplierSinglClearController.spr?action=_submitClear',
			{
				method		: "post",
				parameters	: param,
				onComplete	: callBackHandle,
				callback	: customCallBackHandle
			});
	
}

/**
 * 作废
 */
function _blankOutSupplierSinglClear()
{
	var param = Form.serialize('mainForm');
	param = param + "" + getAllUnClearSupplieBillGridData();
	param = param + "" + getAllUnClearPaymentGridData();
	param = param + "&" + Form.serialize('fundFlowForm');
	param = param + "&" + Form.serialize('settleSubjectForm');
	
	new AjaxEngine(contextPath + '/xdss3/singleClear/supplierSinglClearController.spr?action=_blankOut',
			{
				method		: "post",
				parameters	: param,
				onComplete	: callBackHandle,
				callback	: blankOutCallBackHandle
			});
}

/**
 * 确认清帐操作成功后的回调动作
 */
function blankOutCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var suppliersclearid = result.suppliersclearid;
	document.getElementById("SupplierSinglClear.businessstate").value = result.businessstate;
	
	Ext.getCmp("_saveOrUpdate").setDisabled(true);
	Ext.getCmp("_queryUnClear").setDisabled(true);
	Ext.getCmp("_autoAssign").setDisabled(true);
	Ext.getCmp("_clearAssign").setDisabled(true);
	Ext.getCmp("_submitClear").setDisabled(true);
	Ext.getCmp("_blankOut").setDisabled(true);
}

/**
 * 自动分配
 */
function _autoAssignSupplierSinglClear()
{
	// 方法执行体
	if (div_supplier_sh_sh.getValue() == '')
	{
		_getMainFrame().showInfo("必须先选择[供应商]！");
		return;
	}
	
	if (div_subject_sh_sh.getValue() == '')
	{
		_getMainFrame().showInfo("必须先选择[会计科目]！");
		return;
	}
	if (div_currencytext_sh_sh.getValue() == '')
	{
		_getMainFrame().showInfo("必须先选择[货币文本]！");
		return;
	}
	
	if (div_companyno_sh_sh.getValue() == '')
	{
		_getMainFrame().showInfo("必须先选择[公司代码]！");
		return;
	}
	
	if (div_depid_sh_sh.getValue() == '')
	{
		_getMainFrame().showInfo("必须先选择[业务范围]！");
		return;
	}
	
	
	Ext.Ajax.request(
			{
				url		: contextPath + '/xdss3/singleClear/supplierSinglClearController.spr?action=_autoAssign',
				params	:
				{
					supplier	: div_supplier_sh_sh.getValue(),
					subject		: div_subject_sh_sh.getValue(),
					currencytext : div_currencytext_sh_sh.getValue(),
					companyno : div_companyno_sh_sh.getValue(),
					depid : div_depid_sh_sh.getValue()
				},
				success	: function(xhr)
				{
					if (xhr.responseText)
					{
						var jsonData = Ext.util.JSON.decode(xhr.responseText);
						UnClearSupplieBill_grid.getStore.removeAll();
						UnClearPayment_grid.getStore.removeAll();
						for (var j = 0; j < jsonData.data.length; j++)
						{
							var p = new Ext.data.Record(jsonData.data[j]);
							UnClearSupplieBill_grid.stopEditing();
							UnClearSupplieBill_grid.getStore().insert(0, p);
							UnClearSupplieBill_grid.startEditing(0, 0);
						}
						
						for (var j = 0; j < jsonData.data2.length; j++)
						{
							var p = new Ext.data.Record(jsonData.data2[j]);
							UnClearPayment_grid.stopEditing();
							UnClearPayment_grid.getStore().insert(0, p);
							UnClearPayment_grid.startEditing(0, 0);
						}
					}
				},
				scope	: this
			});
	
}

/**
 * 清除分配
 */
function _clearAssignSupplierSinglClear()
{
	// 方法执行体
	for (var i = 0; i < UnClearSupplieBill_grid.getStore().getCount(); i++)
	{
		var rec = UnClearSupplieBill_grid.getStore().getAt(i);
		rec.set('clearamount', 0);
		rec.set('adjustamount', 0);
	}
	
	for (var i = 0; i < UnClearPayment_grid.getStore().getCount(); i++)
	{
		var rec = UnClearPayment_grid.getStore().getAt(i);
		rec.set('nowclearamount', 0);
	}
}

/**
 * 保存
 */
function _presaveOrUpdateSupplierSinglClear()
{
	return false;
}

/**
 * 保存
 */
function _postsaveOrUpdateSupplierSinglClear()
{
	var msg = _checkFilds();
	if (msg != "")
	{
		showInfo(msg);
		return;
	}
	removeGrid();
	
	var param = Form.serialize('mainForm');
	param = param + "" + getAllUnClearSupplieBillGridData();
	param = param + "" + getAllUnClearPaymentGridData();
	// alert(param);
	param = param + "&" + Form.serialize('fundFlowForm');
	param = param + "&" + Form.serialize('settleSubjectForm');
	
	new AjaxEngine(contextPath + '/xdss3/singleClear/supplierSinglClearController.spr?action=_saveOrUpdate',
			{
				method		: "post",
				parameters	: param,
				onComplete	: callBackHandle,
				callback	: customCallBackHandle
			});
	
}

Ext.onReady(function()
		{
			//先把自动分配给隐藏，以后再做这个功能
			Ext.getCmp("_autoAssign").setVisible(false);
			Ext.getCmp("_clearAssign").setVisible(false);
			Ext.getCmp("_submitClear").hide();
			Ext.getCmp("_voucherPreview").hide();
			Ext.getCmp("_queryUnClear").hide();
			var businessState = $('SupplierSinglClear.businessstate').value;
			
			if (businessState == "4")
			{
				Ext.getCmp("_blankOut").setDisabled(false);
				//Ext.getCmp("_voucherPreview").setDisabled(false);
				// Ext.getCmp("_blankOut").setVisible(true);
				// Ext.getCmp("_saveOrUpdate").setDisabled(true);
				Ext.getCmp("_queryUnClear").setDisabled(true);
				Ext.getCmp("_autoAssign").setDisabled(true);
				Ext.getCmp("_clearAssign").setDisabled(true);
				//Ext.getCmp("_submitClear").setDisabled(true);
			}
			else if (businessState == "9")
			{
				Ext.getCmp("_blankOut").setDisabled(true);
				//Ext.getCmp("_voucherPreview").setDisabled(false);
				// Ext.getCmp("_saveOrUpdate").setDisabled(true);
				Ext.getCmp("_queryUnClear").setDisabled(true);
				Ext.getCmp("_autoAssign").setDisabled(true);
				Ext.getCmp("_clearAssign").setDisabled(true);
				//Ext.getCmp("_submitClear").setDisabled(true);
				Ext.getCmp("_showVoucher").hide();
			}
			else
			{
				Ext.getCmp("_blankOut").setDisabled(true);
				//Ext.getCmp("_voucherPreview").setDisabled(false);
				// Ext.getCmp("_blankOut").setVisible(false);
				Ext.getCmp("_showVoucher").hide();
			}
			Ext.getCmp("_blankOut").hide();
			/**
			 * if (businessState) { // alert(businessState); if (businessState ==
			 * "3") { Ext.getCmp("_blankOut").setDisabled(false);
			 * Ext.getCmp("_voucherPreview").setDisabled(false); //
			 * Ext.getCmp("_blankOut").setVisible(true); } else {
			 * Ext.getCmp("_blankOut").setDisabled(true);
			 * Ext.getCmp("_voucherPreview").setDisabled(true); //
			 * Ext.getCmp("_blankOut").setVisible(false); } } else {
			 * Ext.getCmp("_blankOut").setDisabled(true);
			 * Ext.getCmp("_voucherPreview").setDisabled(true); //
			 * Ext.getCmp("_blankOut").setVisible(false); }
			 */
			div_supplier_sh_sh.defaultCondition = " LIFNR in(select LIFNR from YVENDORTITLE where ISCLEARED='0' )";
			
			
			
		});

/**
 * 模拟凭证
 */
function _voucherPreviewSupplierSinglClear()
{
//	var sumadjustamount = 0;
//	for (var i = 0; i < UnClearSupplieBill_grid.getStore().getCount(); i++)
//	{
//		var rec = UnClearSupplieBill_grid.getStore().getAt(i);
//		sumadjustamount += parseFloat(rec.get('adjustamount'));
//	}
//	
//	if (sumadjustamount == 0)
//	{
//		showInfo("调差金额总计为0，不需要生成凭证！");
//		return;
//	}
	var msg = _checkFilds();
	if (msg != "")
	{
		showInfo(msg);
		return;
	}
	var param = Form.serialize('mainForm');
	param = param + "" + getAllUnClearSupplieBillGridData();
	param = param + "" + getAllUnClearPaymentGridData();
	param = param + "&" + Form.serialize('fundFlowForm');
	param = param + "&" + Form.serialize('settleSubjectForm');
	
	new AjaxEngine(contextPath + '/xdss3/singleClear/supplierSinglClearController.spr?action=_voucherPreview',
			{
				method		: "post",
				parameters	: param,
				onComplete	: callBackHandle,
				callback	: voucherPreviewCallBackHandle
			});
}

/**
 * 凭证预览回调函数。
 * 
 * @param {}
 *            transport
 */
function voucherPreviewCallBackHandle(transport)
{
    if (transport.responseText)
    {
        var responseUtil = new AjaxResponseUtils(transport.responseText);
        var result = responseUtil.getCustomField("coustom");
        if(result==null || result ==""){
        	_getMainFrame().showInfo("没有凭证预览可生成");        
        }else{
	        _getMainFrame().maintabs.addPanel('供应商单清凭证预览', '', contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid=' + result);
        }
    }
}


function _showVoucherSupplierSinglClear(){
	_getMainFrame().maintabs.addPanel('凭证查看', '', contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.get('SupplierSinglClear.suppliersclearid').dom.value);
}