/**
 * Author(s):java业务平台代码生成工具 Date: 2010年07月09日 10点26分57秒 Copyright Notice:版权声明
 * 福建讯盟软件有限公司, 版权所有 违者必究
 * 
 * @(#) Description: <功能>主对象供应商单清帐(SupplierSinglClear)增加页面用户可编程扩展JS文件
 */

/**
 * 取消
 */
function _precancel()
{
	return true;
}

/**
 * 取消
 */
function _postcancel()
{
	
}

/**
 * 提交
 */
function _presubmitProcessSupplierSinglClear()
{
	return true;
}

/**
 * 提交
 */
function _postsubmitProcessSupplierSinglClear()
{
	
}

/**
 * 查询
 */
function _queryUnClearSupplierSinglClear()
{
	//同步所选供应商未清数据
	/**
	Ext.Ajax.request({
        url : contextPath+'/xdss3/payment/importPaymentController.spr?action=_synchronizeUnclearVendor&privadeid=' + div_supplier_sh_sh.getValue(),
        success : function(xhr){
        },
        scope : this
    });
    **/
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
	
	var clearid = document.getElementById("SupplierSinglClear.suppliersclearid").value;
	if(clearid==""){
			UnClearSupplieBill_grid.getStore().removeAll();
			UnClearPayment_grid.getStore().removeAll();
			
			var url = contextPath + '/xdss3/singleClear/supplierSinglClearController.spr?action=_queryUnClear';
			var param ={
							supplier	: div_supplier_sh_sh.getValue(),
							subject		: div_subject_sh_sh.getValue(),
							currencytext : div_currencytext_sh_sh.getValue(),
							companyno : div_companyno_sh_sh.getValue(),
							depid : div_depid_sh_sh.getValue(),
							supplierclearid : document.getElementById("SupplierSinglClear.suppliersclearid").value
						};
		    new AjaxEngine(url, {method:"post", parameters: param, onComplete: queryUnClearcallBackHandle});  
	}else{
			var param2 ={supplierclearid : document.getElementById("SupplierSinglClear.suppliersclearid").value};
			new AjaxEngine(contextPath + '/xdss3/singleClear/supplierSinglClearController.spr?action=getVoucherId',
					{
						method		: "post",
						parameters	: param2,
						onComplete	: function(xhr){
										if (xhr.responseText){
											var voucherid=xhr.responseText;
											if(voucherid !='0'){
												_getMainFrame().showInfo("该单清账已生成凭证不能再查询！");
												var promptMessagebox = new MessageBoxUtil();
												promptMessagebox.Close();
												return;
											}		
											UnClearSupplieBill_grid.getStore().removeAll();
											UnClearPayment_grid.getStore().removeAll();
											var url = contextPath + '/xdss3/singleClear/supplierSinglClearController.spr?action=_queryUnClear';
											var param ={
															supplier	: div_supplier_sh_sh.getValue(),
															subject		: div_subject_sh_sh.getValue(),
															currencytext : div_currencytext_sh_sh.getValue(),
															companyno : div_companyno_sh_sh.getValue(),
															depid : div_depid_sh_sh.getValue(),
															supplierclearid : document.getElementById("SupplierSinglClear.suppliersclearid").value
														};
										    new AjaxEngine(url, {method:"post", parameters: param, onComplete: queryUnClearcallBackHandle});  
										}
									},				
						scope : this
					});
	 }
			
}

function queryUnClearcallBackHandle(xhr){
	
	var promptMessagebox = new MessageBoxUtil();
	promptMessagebox.Close();
	
	if (xhr.responseText)
	{
		var jsonData = Ext.util.JSON.decode(xhr.responseText);
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
		showTitle();
		// UnClearPayment_grid.getStore().commitChanges();
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
	
	new AjaxEngine(contextPath + '/xdss3/singleClear/supplierSinglClearController.spr?action=_submitClear2',
			{
				method		: "post",
				parameters	: param,
				onComplete	: callBackHandle,
				callback	: submitClearCallBackHandle
			});
	
}
var voucherFlag = false; 
/**
 * 确认清帐操作成功后的回调动作
 */
function submitClearCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
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
					    	_getMainFrame().maintabs.addPanel('凭证查看', '', contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.get('SupplierSinglClear.suppliersclearid').dom.value);
						}else{
							_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
						}
					}
				});
		}else{
			var suppliersclearid = result.suppliersclearid;
			document.getElementById("SupplierSinglClear.suppliersclearid").value = suppliersclearid;
			
			document.getElementById("SupplierSinglClear.createtime").value = result.createtime;
			document.getElementById("SupplierSinglClear.creator").value = result.creator;
			document.getElementById("SupplierSinglClear.lastmodifytime").value = result.lastmodifytime;
			document.getElementById("SupplierSinglClear.lastmodifyer").value = result.lastmodifyer;
			isCreateCopy = false;
			reload_UnClearSupplieBill_grid("defaultCondition=YUNCLEARSUPPBILL.SUPPLIERSCLEARID='" + suppliersclearid + "'");
			reload_UnClearPayment_grid("defaultCondition=YUNCLEARPAYMENT.SUPPLIERSCLEARID='" + suppliersclearid + "'");
			document.getElementById("FundFlow.fundflowid").value = result.fundflowid;
			document.getElementById("SettleSubject.settlesubjectid").value = result.settlesubjectid;
			document.getElementById("SupplierSinglClear.businessstate").value = result.businessstate;
			
			Ext.getCmp("_saveOrUpdateSupplierSinglClear").setDisabled(true);
			Ext.getCmp("_queryUnClear").setDisabled(true);
			Ext.getCmp("_autoAssign").setDisabled(true);
			Ext.getCmp("_clearAssign").setDisabled(true);
			Ext.getCmp("_submitClear").setDisabled(true);
			Ext.getCmp("_blankOut").setDisabled(false);
			Ext.getCmp("_voucherPreview2").setDisabled(false);
		}
	}
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
	
	Ext.getCmp("_saveOrUpdateSupplierSinglClear").setDisabled(true);
	Ext.getCmp("_queryUnClear").setDisabled(true);
	Ext.getCmp("_autoAssign").setDisabled(true);
	Ext.getCmp("_clearAssign").setDisabled(true);
	Ext.getCmp("_submitClear").setDisabled(true);
	Ext.getCmp("_blankOut").setDisabled(true);
	Ext.getCmp("_voucherPreview").setDisabled(true);
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
	
	
	UnClearSupplieBill_grid.getStore().removeAll();
	UnClearPayment_grid.getStore().removeAll();
	
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
	var clearid = document.getElementById("SupplierSinglClear.suppliersclearid").value;
	if(clearid==""){
		var msg = _checkFilds();
		if (msg != "")
		{
			showInfo(msg);
			return false;
		}
		var msg2=checkGridItem();
		if(msg2 != ""){
			showInfo(msg2);
			return false;
		}
		removeGrid();
		showTitle();
		var param = Form.serialize('mainForm');
		param = param + "" + getAllUnClearSupplieBillGridData();
		param = param + "" + getAllUnClearPaymentGridData();
		param = param + "&" + Form.serialize('fundFlowForm');
		param = param + "&" + Form.serialize('settleSubjectForm');
		
		//ajax同步调用,url字符串后面要加&不然会出错
			var url = contextPath + '/xdss3/singleClear/supplierSinglClearController.spr?action=checkClearData&';
		var conn = Ext.lib.Ajax.getConnectionObject().conn; 		  
		conn.open("post", url,false); 
		conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		conn.send(param); 
		var responseUtil = new AjaxResponseUtils(conn.responseText);
		var result = responseUtil.getCustomField("coustom");		
		if(result.isRight){
			return true;	
		}else{
			showInfo(result.info);	
			return false;	
		}		
		
	
	}else{
		//ajax同步调用
		var url = contextPath + '/xdss3/singleClear/supplierSinglClearController.spr?action=getVoucherId&supplierclearid=' + clearid;	
		var conn = Ext.lib.Ajax.getConnectionObject().conn; 
		conn.open("post", url,false); 
		conn.send(null); 
	//	alert(conn.responseText);
		//没有生成凭证号才能保存
		if(conn.responseText =='0'){
			var msg = _checkFilds();
			if (msg != "")
			{
				showInfo(msg);
				return false;
			}
			var msg2=checkGridItem();
			if(msg2 != ""){
				showInfo(msg2);
				return false;
			}
			removeGrid();
			showTitle();
			
			var param = Form.serialize('mainForm');
			param = param + "" + getAllUnClearSupplieBillGridData();
			param = param + "" + getAllUnClearPaymentGridData();
			param = param + "&" + Form.serialize('fundFlowForm');
			param = param + "&" + Form.serialize('settleSubjectForm');
		
		//ajax同步调用,url字符串后面要加&不然会出错
			var url = contextPath + '/xdss3/singleClear/supplierSinglClearController.spr?action=checkClearData&';
		var conn = Ext.lib.Ajax.getConnectionObject().conn; 		  
		conn.open("post", url,false); 
		conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		conn.send(param); 
		var responseUtil = new AjaxResponseUtils(conn.responseText);
		var result = responseUtil.getCustomField("coustom");		
		if(result.isRight){
			return true;	
		}else{
			showInfo(result.info);	
			return false;	
		}		
		}else{
			_getMainFrame().showInfo("该单清账已生成凭证不能再保存！");
			return false;
		}
	}
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
	var flag = false;
	var flag2 =  false;
	for (var i = 0; i < UnClearSupplieBill_grid.getStore().getCount(); i++)
	{
		var rec = UnClearSupplieBill_grid.getStore().getAt(i);
		var clearmount = rec.get('clearamount');
		var receivableamount = rec.get('receivableamount');
		if((clearmount=='0' || clearmount=='') && receivableamount !='0'){
			return "消帐金额不能为空，或为0";
		}
		var unpaidamount =  rec.get('unpaidamount');
		if(parseFloat(unpaidamount) - parseFloat(clearmount) != 0){
			flag =true;
		}		
	}
	
	for (var i = 0; i < UnClearPayment_grid.getStore().getCount(); i++)
	{
		var rec = UnClearPayment_grid.getStore().getAt(i);
		var nowclearamount = rec.get('nowclearamount');
		var paymentamount = rec.get('paymentamount');
		if( (nowclearamount=='0' || nowclearamount=='' ) && paymentamount !='0'){
			return "本次已抵消金额不能为空，或为0";
		}
		var unoffsetamount = rec.get('unoffsetamount');
		if(parseFloat(nowclearamount) - parseFloat(unoffsetamount) != 0){
			flag2 =true;
		}
	}
	if(flag && flag2){
		return "票与款最少要有一边要全清！";
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
	//未收款金额总和
	var unclearBillAmount = 0;
	//未抵消金额总和
	var unclearAmount = 0;
	//用来判断应收金额是不是为0
	var flagAmount =true;
	var accountdate = document.getElementById("SupplierSinglClear.accountdate").value;
	for (var i = 0; i < UnClearSupplieBill_grid.getStore().getCount(); i++)
	{
		var rec = UnClearSupplieBill_grid.getStore().getAt(i);
		sumclearamount += parseFloat(rec.get('clearamount'));
		sumadjustamount += parseFloat(rec.get('adjustamount'));
		unclearBillAmount += parseFloat(rec.get('unpaidamount'));
		if(rec.get('receivableamount')!='0')flagAmount=false;
			//日期比较大小,0:小于 1：等于 2：大于 3 错误	
		if( DateUtils.dateCompareStr(accountdate,rec.get('accountdate')) ==0)return "清账的过账日期一定要大于或等于借贷方的过账日期";
	}
	
	for (var i = 0; i < UnClearPayment_grid.getStore().getCount(); i++)
	{
		var rec = UnClearPayment_grid.getStore().getAt(i);
		sumnowclearamount += parseFloat(rec.get('nowclearamount'));
		unclearAmount += parseFloat(rec.get('unoffsetamount'));
		if(rec.get('paymentamount')!='0')flagAmount=false;
			//日期比较大小,0:小于 1：等于 2：大于 3 错误	
		if( DateUtils.dateCompareStr(accountdate,rec.get('accountdate')) ==0)return "清账的过账日期一定要大于或等于借贷方的过账日期";
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
		
		if(!flagAmount)return "清帐金额的合计值不能等于0，请输入清帐金额！";
		
	}
	/**
	if(sumadjustamount ==0 && unclearBillAmount != sumclearamount && unclearAmount != sumnowclearamount ){
		return "借贷方最少要一边要全清！";
	}**/
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
		if (!fdebtcredit1 && famount1 > 0)
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
		if (!fdebtcredit2 && famount2 > 0)
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
		if (!fdebtcredit3 && famount3 > 0)
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
		if (!sdebtcredit1 && samount1 > 0)
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
		if (!sdebtcredit2 && samount2 > 0)
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
		if (!sdebtcredit3 && samount3 > 0)
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
		if (!sdebtcredit4 && samount4 > 0)
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
	// totAmount = famount1 + famount2 + famount3 + samount1 + samount2 +
	// samount3 + samount4;
	
	
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
 * 保存
 */
function _postsaveOrUpdateSupplierSinglClear()
{
	
	return true;
	
	/**
	
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
	**/
}

Ext.onReady(function()
		{
			//先把自动分配给隐藏，以后再做这个功能
			Ext.getCmp("_autoAssign").setVisible(false);
			Ext.getCmp("_clearAssign").setVisible(false);
			Ext.getCmp("_submitClear").hide();
			Ext.getCmp("_voucherPreview").hide();
			var businessState = $('SupplierSinglClear.businessstate').value;
			if (businessState)
			{
				// alert(businessState);
				if (businessState == "4")
				{
					Ext.getCmp("_blankOut").setDisabled(false);
					// Ext.getCmp("_blankOut").setVisible(true);
					Ext.getCmp("_saveOrUpdate").setDisabled(true);
					Ext.getCmp("_queryUnClear").setDisabled(true);
					Ext.getCmp("_autoAssign").setDisabled(true);
					Ext.getCmp("_clearAssign").setDisabled(true);
					//Ext.getCmp("_submitClear").setDisabled(true);
					Ext.getCmp("_voucherPreview").setDisabled(false);
				}
				else if (businessState == "9")
				{
					Ext.getCmp("_blankOut").setDisabled(true);
					Ext.getCmp("_saveOrUpdate").setDisabled(true);
					Ext.getCmp("_queryUnClear").setDisabled(true);
					Ext.getCmp("_autoAssign").setDisabled(true);
					Ext.getCmp("_clearAssign").setDisabled(true);
					//Ext.getCmp("_submitClear").setDisabled(true);
					Ext.getCmp("_voucherPreview").setDisabled(false);
				}
				else
				{
					Ext.getCmp("_blankOut").setDisabled(true);
					Ext.getCmp("_voucherPreview").setDisabled(false);
					// Ext.getCmp("_blankOut").setVisible(false);
				}
			}
			else
			{
				Ext.getCmp("_blankOut").setDisabled(true);
				Ext.getCmp("_voucherPreview").setDisabled(false);
				// Ext.getCmp("_blankOut").setVisible(false);
			}
			Ext.getCmp("_blankOut").hide();
			div_supplier_sh_sh.defaultCondition = " LIFNR in(select LIFNR from YVENDORTITLE where ISCLEARED='0' )";
			Ext.getCmp("_queryUnClear").setDisabled(true);
			div_supplier_sh_sh.callBack = function(data){
				//同步所选供应商未清数据
				Ext.Ajax.request({
			        url : contextPath+'/xdss3/payment/importPaymentController.spr?action=_synchronizeUnclearVendor&privadeid=' + div_supplier_sh_sh.getValue(),
			        success : function(xhr){
			        	Ext.getCmp("_queryUnClear").setDisabled(false);
			        },
			        callback:function(x){
				        	Ext.getCmp("_queryUnClear").setDisabled(false);
				        },
			        scope : this
			    });
			}
			
			div_companyno_sh_sh.callBack = function(data){
				//先同步一下当天的冲销凭证

				Ext.Ajax.request({
				    	url : contextPath+'/xdss3/payment/importPaymentController.spr?action=syncWriteoff',
				        params : {bukrs:data.COMPANYID},
				        success : function(xhr){
				        	//Ext.getCmp("_queryUnClear").setDisabled(false);
				        },
				        callback:function(x){
				        	//Ext.getCmp("_queryUnClear").setDisabled(false);
				        },
				        scope : this
				    });
			 
			}
			var unsbToolbar = UnClearSupplieBill_grid.getTopToolbar();
			unsbToolbar.items.get('UnClearSupplieBilladdRow').hide();		// 隐藏"增加行"按钮
			unsbToolbar.items.get('UnClearSupplieBilldeleteRow').hide();	// 隐藏"删除行"按钮
			
			var uncpToolbar = UnClearPayment_grid.getTopToolbar();
			uncpToolbar.items.get('UnClearPaymentaddRow').hide();		// 隐藏"增加行"按钮
			uncpToolbar.items.get('UnClearPaymentdeleteRow').hide();	// 隐藏"删除行"按钮
			
			
			UnClearSupplieBill_grid.on('afteredit',calclear,UnClearSupplieBill_grid);
			UnClearPayment_grid.on('afteredit',calclear,UnClearPayment_grid);
			function calclear(e){
				var cRecord = e.record;
				var cField  = e.field;
				
				if(cField == 'clearamount' && cRecord.get("clearamount")==''){
					cRecord.set("clearamount",0);
				}
				if(cField == 'adjustamount' && cRecord.get("adjustamount")==''){
					cRecord.set("adjustamount",0);
				}
				if(cField == 'nowclearamount' && cRecord.get("nowclearamount")==''){
					cRecord.set("nowclearamount",0);
				}
				showTitle();
			}
			
		});
		
		
function showTitle(){
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
	var m = parseFloat(sumclearamount) - parseFloat(sumnowclearamount);
	UnClearSupplieBill_grid.setTitle('清帐金额：'+ round(sumclearamount,2)+'   本次已抵消金额：'+Math.abs(round(sumnowclearamount,2)) +'      调整金额：'+ round(sumadjustamount,2)+'   差额:' + round(m,2) );
	UnClearPayment_grid.setTitle('清帐金额：'+ round(sumclearamount,2)+'   本次已抵消金额：'+Math.abs(round(sumnowclearamount,2)) +'      调整金额：'+ round(sumadjustamount,2)+'   差额:' + round(m,2) );
	
}

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
	
	var sup_id = document.getElementById("SupplierSinglClear.suppliersclearid").value;
	
	if(sup_id==""){
		showInfo("请先保存后再模拟凭证");
		return ;
	}
	
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
		return false;
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
 * 模拟凭证
 */
function _voucherPreviewSupplierSinglClear2()
{

	
	var sup_id = document.getElementById("SupplierSinglClear.suppliersclearid").value;
	
	if(sup_id==""){
		showInfo("请先保存后再模拟凭证");
		return ;
	}
	
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
		return false;
	}
	var param = Form.serialize('mainForm');
	param = param + "" + getAllUnClearSupplieBillGridData();
	param = param + "" + getAllUnClearPaymentGridData();
	param = param + "&" + Form.serialize('fundFlowForm');
	param = param + "&" + Form.serialize('settleSubjectForm');
	
	new AjaxEngine(contextPath + '/xdss3/singleClear/supplierSinglClearController.spr?action=_voucherPreview2',
			{
				method		: "post",
				parameters	: param,
				onComplete	: callBackHandle,
				callback	: voucherPreviewCallBackHandle2
			});
}

/**
 * 凭证预览回调函数。
 * 
 * @param {}
 *            transport
 */
function voucherPreviewCallBackHandle2(transport)
{
	if (transport.responseText)
	{
		var responseUtil = new AjaxResponseUtils(transport.responseText);
		var result = responseUtil.getCustomField("coustom");
			if(!result.isRight){
				showInfo(result.info);				
			}else{	
				Ext.get('currentWorkflowTask').mask();
				Ext.get('centercontent').mask();
				Ext.get(Ext.getCmp("tabs").getActiveTab().getItemId()).mask();
				
				Ext.getCmp("tabs").on('tabchange',function(t,p){
					Ext.get(p.getItemId()).mask();
				});
	        	var suppliersclearid=result.suppliersclearid;
				document.getElementById("SupplierSinglClear.suppliersclearid").value = suppliersclearid;
		
				_getMainFrame().maintabs.addPanel('供应商单清凭证预览', '', contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid=' + result.businessid,closeVoucherCallBack,true);
			}
	}
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
				
			Ext.get('currentWorkflowTask').mask();
			Ext.get('centercontent').mask();
			Ext.get(Ext.getCmp("tabs").getActiveTab().getItemId()).mask();
			
			Ext.getCmp("tabs").on('tabchange',function(t,p){
				Ext.get(p.getItemId()).mask();
			});
        	var suppliersclearid=result.suppliersclearid;
			document.getElementById("SupplierSinglClear.suppliersclearid").value = suppliersclearid;
	
			_getMainFrame().maintabs.addPanel('供应商单清凭证预览', '', contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid=' + result.businessid,closeVoucherCallBack,true);
		
	}
}

function closeVoucherCallBack(flag){
	Ext.get('currentWorkflowTask').unmask();
	Ext.get('centercontent').unmask();
	Ext.get(Ext.getCmp("tabs").getActiveTab().getItemId()).unmask();
	
	Ext.getCmp("tabs").on('tabchange',function(t,p){
		Ext.get(p.getItemId()).unmask();
	});
	if(flag){
		voucherFlag = flag;
		_submitClearSupplierSinglClear();
		
	}
}

function _addUnClearSupplieBill(){
	
	add('H');
}

function _addUnClearPayment(){
	
	add('S');
}

function _delUnClearSupplieBill(){
	var records = UnClearSupplieBill_grid.getSelectionModel().getSelections();        			
	for(var i=0;i<records.size();i++)
    {
		UnClearSupplieBill_grid.getStore().remove(records[i]);
    }
     showTitle();
}

function _delUnClearPayment(){
	var records = UnClearPayment_grid.getSelectionModel().getSelections();        			
	for(var i=0;i<records.size();i++)
    {
		UnClearPayment_grid.getStore().remove(records[i]);
    }
     showTitle();
}

var searchVendorTitleWin = new Ext.SearchHelpWindow({
	    shlpName : 'YHVENDORTITLE3',
	    callBack : winVendorTitleCallBack
});
var searchVendorTitleWin2 = new Ext.SearchHelpWindow({
	    shlpName : 'YHVENDORTITLE3',
	    callBack : winVendorTitleCallBack2
});

function winVendorTitleCallBack(jsonArrayData){
	var vendortitleids = '';
	for(var i=0;i<jsonArrayData.length;i++){
		var num = UnClearSupplieBill_grid.getStore().find('vendortitleid',jsonArrayData[i].VENDORTITLEID);
		if(num==-1){
			vendortitleids += jsonArrayData[i].VENDORTITLEID + ",";
		}
	}
	if(vendortitleids=="")return;
	vendortitleids = vendortitleids.substring(0,vendortitleids.length-1);
	Ext.Ajax.request({
    	url : contextPath+'/xdss3/singleClear/supplierSinglClearController.spr?action=getUnSingleClearData',
        params : {vendortitleids:vendortitleids,
        		suppliersclearid :document.getElementById("SupplierSinglClear.suppliersclearid").value
        		},
        success : function(xhr){
			if(xhr.responseText){
				
	        	var jsonData = Ext.util.JSON.decode(xhr.responseText);	        	
	        	UnClearSupplieBill_grid.getStore().clearFilter();	        	
	        	for(var j=0;j<jsonData.data.length;j++){	        		
	        		var p = new Ext.data.Record(jsonData.data[j]);
					UnClearSupplieBill_grid.stopEditing();
					UnClearSupplieBill_grid.getStore().insert(0, p);
				    UnClearSupplieBill_grid.startEditing(0, 0);
	        	}
	        	//UnClearSupplieBill_grid.getStore().sort([{field:'contract_no',direction: 'ASC'}, {field:'project_no',direction:'ASC'}], 'ASC');
			}
        },
        scope : this
    });
}

function winVendorTitleCallBack2(jsonArrayData){
	var vendortitleids = '';
	for(var i=0;i<jsonArrayData.length;i++){
		var num = UnClearPayment_grid.getStore().find('vendortitleid',jsonArrayData[i].VENDORTITLEID);
		if(num==-1){
			vendortitleids += jsonArrayData[i].VENDORTITLEID + ",";
		}
	}
	if(vendortitleids=="")return;
	vendortitleids = vendortitleids.substring(0,vendortitleids.length-1);
	Ext.Ajax.request({
    	url : contextPath+'/xdss3/singleClear/supplierSinglClearController.spr?action=getUnSingleClearData',
        params : {vendortitleids:vendortitleids,
        		suppliersclearid :document.getElementById("SupplierSinglClear.suppliersclearid").value
        		},
        success : function(xhr){
			if(xhr.responseText){				
	        	var jsonData = Ext.util.JSON.decode(xhr.responseText);	        	
	        	UnClearPayment_grid.getStore().clearFilter();	        	
	        	for(var j=0;j<jsonData.data.length;j++){	        		
	        		var p = new Ext.data.Record(jsonData.data[j]);
					UnClearPayment_grid.stopEditing();
					UnClearPayment_grid.getStore().insert(0, p);
				    UnClearPayment_grid.startEditing(0, 0);
	        	}
	        	//UnClearPayment_grid.getStore().sort([{field:'contract_no',direction: 'ASC'}, {field:'project_no',direction:'ASC'}], 'ASC');
			}
        },
        scope : this
    });
}


function add(flag){

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

	var supplier = div_supplier_sh_sh.getValue();
	var subject = div_subject_sh_sh.getValue();
	var currencytext = div_currencytext_sh_sh.getValue();
	var depid = div_depid_sh_sh.getValue();
	var companyno = div_companyno_sh_sh.getValue();
	
	
	if(flag == 'H'){
	searchVendorTitleWin.defaultCondition ="SHKZG ='" + flag +  "' and LIFNR ='" + supplier + "' and SAKNR='" + subject + "' and WAERS='" + currencytext + "' and BUKRS='" + companyno + "' and GSBER='" + depid + "' and ISCLEARED='0'";
		searchVendorTitleWin.show();
	}
	
	if(flag == 'S'){
	searchVendorTitleWin2.defaultCondition ="SHKZG ='" + flag +  "' and LIFNR ='" + supplier + "' and SAKNR='" + subject + "' and WAERS='" + currencytext + "' and BUKRS='" + companyno + "' and GSBER='" + depid + "' and ISCLEARED='0'";
		searchVendorTitleWin2.show();
	}
}



function checkGridItem(){
	for (var i = UnClearSupplieBill_grid.getStore().getCount()-1; i >=0 ; i--)
	{
		var ds = UnClearSupplieBill_grid.getStore();
		var rec = ds.getAt(i);
		var clearmount = parseFloat(rec.get('clearamount'));
		var unreceivedamount = parseFloat(rec.get('unreceivedamount'));
		if(clearmount>unreceivedamount){
			return "清账金额不能大于未收款金额";
		}
		if(clearmount<0){
			return "清账金额不能小于0";
		}
	}
	
	for (var i = UnClearPayment_grid.getStore().getCount()-1; i >=0 ; i--)
	{
		var ds = UnClearPayment_grid.getStore();
		var rec = ds.getAt(i);
		var nowclearamount = parseFloat(rec.get('nowclearamount'));
		var unoffsetamount = parseFloat(rec.get('unoffsetamount'));
		if(nowclearamount>unoffsetamount){
			return "未抵消金额不能大于未清金额";
		}
		if(nowclearamount<0){
			return "未抵消金额不能小于0";
		}
	}
	return "";
}
 

function _resetUnClearSupplieBill(){	
	var records = UnClearSupplieBill_grid.getSelectionModel().getSelections();        			
	for(var i=0;i<records.size();i++)
    {
		records[i].set("clearamount",0);
		records[i].set("adjustamount",0);
    }
     showTitle();
}

function _resetUnClearPayment(){
	
	var records = UnClearPayment_grid.getSelectionModel().getSelections();        			
	for(var i=0;i<records.size();i++)
    {
		records[i].set("nowclearamount",0);
    }
     showTitle();
}

function _assignSetUnClearSupplieBill(){	
	var records = UnClearSupplieBill_grid.getSelectionModel().getSelections();        			
	for(var i=0;i<records.size();i++)
    {
		records[i].set("clearamount",records[i].get("unpaidamount"));
    }
     showTitle();
}

function _assignSetUnClearPayment(){
	
	var records = UnClearPayment_grid.getSelectionModel().getSelections();        			
	for(var i=0;i<records.size();i++)
    {
		records[i].set("nowclearamount",records[i].get("unoffsetamount"));
    }
     showTitle();
}


function _updateCT(){
	if (div_supplier_sh_sh.getValue() == '')
	{
		_getMainFrame().showInfo("必须先选择[供应商]！");
		return;
	}
	Ext.getCmp("_updateCT").setDisabled(true);
	Ext.Ajax.request({
				    	url : contextPath+'/xdss3/payment/importPaymentController.spr?action=_updateCT',
				        params : {privadeid:div_supplier_sh_sh.getValue()},
				        success : function(xhr){
				        	Ext.getCmp("_updateCT").setDisabled(true);
				        },
				        callback:function(x){
				        	Ext.getCmp("_updateCT").setDisabled(true);
				        },
				        scope : this
				    });
}
 