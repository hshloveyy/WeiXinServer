/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年12月17日 17点07分26秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象出口押汇(BillPurchased)查看页面JS文件
 */

var ps = "";
var enableBank = true;
/**
 * 初始化界面信息
 */
Ext.onReady(function(){
	var maintab = Ext.getCmp("tabs");
	ps = Ext.getDom('BillPurchased.processstate').value;
	// 隐藏所有页签的"增加行"、"删除行"按钮
	// 非资金部节点隐藏现金日记帐
	if ((ps != '资金部出纳确认押汇收款') && (ps != '资金部出纳确认还押汇')) {
			Ext.getCmp('_cashJournal').hide();
	}
	// 是否还押汇节点
	var eeFlag = (ps=='业务填写相关信息' || ps=='资金部出纳确认押汇收款' || ps=='财务会计确认押汇过账'); // 押汇节点
	var reFlag = (ps=='业务填写还押汇信息' || ps=='资金部出纳确认还押汇' || ps=='财务会计确认还押汇并做帐'); // 还押汇节点
	
	
	if (eeFlag) {

		BillPurBankItem_grid.getStore().on('load', function(e, recs, o) {
//					if (recs.length > 0) {
//						crToolbar.items.get('BillPurBankItem._addBank').hide();
//						crToolbar.items.get('BillPurBankItem._deletes').hide();
//					}
				});
	} else {
//		Ext.getDom('BillPurchased.text').readOnly=true; // 抬头文本(还押汇用途)
		calendar_BillPurchased_voucherdate.setEditable(false); // 押汇凭证日期
		calendar_BillPurchased_accountdate.setEditable(false); // 押汇记帐日期
	}
	
	if (reFlag) {
	} else {
		// 控制界面不可用
		Ext.getDom('BillPurchased.retext').readOnly=true; // 抬头文本(还押汇用途)
		calendar_BillPurchased_revoucherdate.setEditable(false); // 还押汇凭证日期
		calendar_BillPurchased_reaccountdate.setEditable(false); // 还押汇记帐日期
	}
	if (!eeFlag && !reFlag) {
		// 非押汇节点、非还押汇节点屏蔽 押汇银行、还押汇银行。
//		maintab.hideTabStripItem('billPurBankItemTab');
//		maintab.hideTabStripItem('billPurReBankItemTab');
	}
	Ext.getCmp('_voucherPreview').hide();
	if ((ps == '财务会计确认还押汇并做帐') || (ps == '财务会计确认押汇过账')) {
		Ext.getDom('workflowLeaveTransitionName').onchange = function() {
			var ns = this.value; // 下一步操作状态
			if (ns == '确认') {
				Ext.getCmp('_submitProcess').setDisabled(true); // 禁用提交
				Ext.getCmp('_voucherPreview').setDisabled(false); // 启用凭证按钮
			} else {
				Ext.getCmp('_submitProcess').setDisabled(false); // 启用提交
				Ext.getCmp('_voucherPreview').setDisabled(true); // 禁用凭证按钮
			}
		}
		if (Ext.getDom('workflowLeaveTransitionName').value = '确认') {
			Ext.getCmp('_submitProcess').setDisabled(true); // 禁用提交
			Ext.getCmp('_voucherPreview').setDisabled(false); // 启用凭证按钮
		}
	} else {
		Ext.getCmp('_voucherPreview').hide();
		maintab.hideTabStripItem('settleSubjectTab');
		maintab.hideTabStripItem('fundFlowTab');
	}
	
	// 押汇汇率改变事件 1
	var billpurrate = Ext.getDom('BillPurchased.billpurrate');
	if (billpurrate) {
		billpurrate.onchange = function(){
			for(var i=0;i<BillPurBankItem_grid.getStore().getCount();i++){
				var newValue = round( billpurrate.value * parseFloat(BillPurBankItem_grid.getStore().getAt(i).get('realmoney')), 2 );
				BillPurBankItem_grid.getStore().getAt(i).set('realmoney1', newValue);
			}
		}
	}
	
	// 押汇汇率改变事件 2  押汇银行行项
	BillPurBankItem_grid.on("afterEdit", afterEditForBillPurBankItem, BillPurBankItem_grid);
	function afterEditForBillPurBankItem (e) {
		var record = e.record;// 被编辑的记录
    	var exchangerate = parseFloat(Ext.getDom('BillPurchased.billpurrate').value);
    	if (e.field == 'realmoney' && Utils.isNumber(exchangerate)) {
    		record.set('realmoney1', round((exchangerate * record.get("realmoney")),2));
    	}
		BillPurBankItem_grid.recalculation();
	}
	
	// 押汇银行行项，存有ID时则不能修改
	BillPurBankItem_grid.on('cellclick', function(grid, rowIndex, columnIndex, e) {
		var record = grid.getStore().getAt(rowIndex);
		var bankitemid = record.get('bankitemid');
		if (bankitemid) {
			var url = contextPath
					+ '/xdss3/billpurchased/billPurchasedController.spr?action=_hasCashJournal'
					+ '&BUSINESSID='
					+ Ext.getDom('BillPurchased.billpurid').value;
			var conn = Ext.lib.Ajax.getConnectionObject().conn;
			conn.open("post", url, false);
			conn.send(null);
			if (conn.responseText == 'true') {
				Ext.getDom('BillPurchased.remark').focus();
				_getMainFrame().showInfo('不能修改[押汇银行]行项！');
				return false;
			}

		}
	});
	
	// 还押汇银行行项
	BillPurReBankItem_grid.on("afterEdit", afterEditForBillPurReBankItem, BillPurReBankItem_grid);
	function afterEditForBillPurReBankItem (e) {
		var record = e.record;// 被编辑的记录
    	var exchangerate = parseFloat(record.get("rebillpurrate"));
    	if ( (e.field == 'realmoney' || e.field == 'rebillpurrate' )&& Utils.isNumber(exchangerate)) {
    		record.set('realmoney2', round((exchangerate * record.get("realmoney")),2));
    	}
		BillPurReBankItem_grid.recalculation();
	}
	
	// 还押汇银行行项，存有ID时则不能修改
	BillPurReBankItem_grid.on('rowclick', function(grid, rowIndex, e) {
				var record = grid.getStore().getAt(rowIndex);
				var bankitemid = record.get('bankitemid');
				if (bankitemid) {
					Ext.getDom('BillPurchased.remark').focus();
					_getMainFrame().showInfo('不能修改该[还押汇银行]行项！');
					return false;
				}
			});

	
});
/**
 * 通用验证
 * */
function commonValidate(){
	var ns = Ext.getDom('workflowLeaveTransitionName').value;
	// 回退操作不验证
	if (ns == '退回修改' || ns == '退回出纳' || ns == '退回业务修改相关信息'
			|| ns == '退回业务修改还押汇信息') {
		return true;
	}
	if (BillPurBillItem_grid.getStore().getCount()<1){
		_getMainFrame().showInfo('请添加[发票]行项！');
			return false;
	}
	for(var i=0;i<BillPurBankItem_grid.getStore().getCount();i++){
		if (ps == '财务会计确认押汇过账') {
		var banksubj = BillPurBankItem_grid.getStore().getAt(i).get('banksubj');
		if (banksubj=='' || banksubj == ' ') {
			_getMainFrame().showInfo('[押汇银行－押汇银行科目]存在为空数据，请添加！');
			return false;
			}
		}
		var currency = BillPurBankItem_grid.getStore().getAt(i).get('currency');
		if (currency=='' || currency == ' ') {
			_getMainFrame().showInfo('[押汇银行－押汇币别]存在为空数据，请添加！');
			return false;
		}
	}
	for(var i=0;i<BillPurReBankItem_grid.getStore().getCount();i++){
		var rebillpurrate = BillPurReBankItem_grid.getStore().getAt(i).get('rebillpurrate');
		if (rebillpurrate=='' || rebillpurrate == ' ') {
			_getMainFrame().showInfo('[还押汇银行－还押汇汇率]存在为空数据，请添加！');
			return false;
		}
		var currency = BillPurReBankItem_grid.getStore().getAt(i).get('currency');
		if (currency=='' || currency == ' ') {
			_getMainFrame().showInfo('[还押汇银行－还押汇币别]存在为空数据，请添加！');
			return false;
		}
	}
	
	if (ps == '业务填写相关信息') {
		if (BillPurBankItem_grid.getStore().getCount() < 1) {
			_getMainFrame().showInfo('请添加[押汇银行]行项！');
			return false;
		}
		var billpurrate = Ext.getDom('BillPurchased.billpurrate').value;
		if (billpurrate=='' || billpurrate==0) {
			_getMainFrame().showInfo('押汇汇率不能为空或者为0！');
			return false;;
		}
		var maturity = Ext.getDom("BillPurchased.maturity").value;
		if (maturity=='') {
			_getMainFrame().showInfo('请添加[押汇到期日]！');
			return false;;
		}
		var payrealdate = Ext.getDom("BillPurchased.payrealdate").value;
		if (payrealdate=='') {
			_getMainFrame().showInfo('请添加[押汇收款日]！');
			return false;;
		}
	}
	
	if (ps == '业务填写还押汇信息') {
		
		if (BillPurReBankItem_grid.getStore().getCount() < 1) {
			_getMainFrame().showInfo('请添加[还押汇银行]行项！');
			return false;
		}
	}
	
	if (ps == '财务会计确认还押汇并做帐') {
		if (Ext.getDom("BillPurchased.revoucherdate").value == '' ) {
			_getMainFrame().showInfo('请添加[还押汇凭证日期]行项！');
			return false;
		} else if (Ext.getDom("BillPurchased.reaccountdate").value == ''){
			_getMainFrame().showInfo('请添加[还押汇记帐日期]行项！');
			return false;
		} else if (Ext.getDom("BillPurchased.retext").value == ''
				|| Ext.getDom("BillPurchased.retext").value == ' ') {
			_getMainFrame().showInfo('请添加[抬头文本(还押汇用途)]行项！');
			return false;
		}
	}
	
	if (ps == '财务会计确认押汇过账') {
		if (Ext.getDom("BillPurchased.voucherdate").value == '' ) {
			_getMainFrame().showInfo('请添加[押汇凭证日期]行项！');
			return false;
		} else if (Ext.getDom("BillPurchased.accountdate").value == ''){
			_getMainFrame().showInfo('请添加[押汇记帐日期]行项！');
			return false;
		} else if (Ext.getDom("BillPurchased.text").value == ''
				|| Ext.getDom("BillPurchased.text").value == ' ') {
			_getMainFrame().showInfo('请添加[抬头文本(押汇用途)]行项！');
			return false;
		}
		
	}
		
	return true;
}
 
/**
 * 发票搜索窗体
 */
var searchBillWin = new Ext.SearchHelpWindow( {
	shlpName : 'YHEXPORTBILL',
	callBack : winBillCallBack
});

/**
 *  增加发票
 */
function _addBillBillPurBillItem() {
	searchBillWin.defaultCondition = "DEPT_ID='"+div_dept_id_sh_sh.getValue()+"'";
	searchBillWin.show();
}

/**
 *  添加发票的回调
 */
function winBillCallBack(jsonArrayData) {
	for ( var i = 0; i < jsonArrayData.length; i++) {
		var num = BillPurBillItem_grid.getStore().find('bill_no',jsonArrayData[i].INV_NO);
		if(num==-1){
			var lcdpdano = jsonArrayData[i].LCDPDA_NO;
			if (lcdpdano=='null') {
				lcdpdano = ' ';
			}
			var billType = jsonArrayData[i].BILL_TYPE;
			if (BillPurBillItem_grid.getStore().getCount()>0) {
				if (BillPurBillItem_grid.getStore().getAt(0).get('billtype') != billType){
					_getMainFrame().showInfo('请添加相同票据类型发票！');
					continue;
				}
				if (BillPurBillItem_grid.getStore().getAt(0).get('billtype') != "TT") {
					_getMainFrame().showInfo('非 TT 类型仅能添加一行！');
					return;
				}
			} else if (div_currency_sh_sh.getValue()==''){
				 div_currency_sh_sh.setValue(jsonArrayData[i].CURRENCY);
			}
			var p = new Ext.data.Record( {
				bill_no : jsonArrayData[i].INV_NO,
				amount : jsonArrayData[i].TOTAL,
				examdate : jsonArrayData[i].EXAM_DATE,
				billtype : jsonArrayData[i].BILL_TYPE,
				lcdpdano : lcdpdano,
				write_no : jsonArrayData[i].WRITE_NO,
				contract_no : jsonArrayData[i].CONTRACT_NO,
				bankid : jsonArrayData[i].BANK,
				bankname : jsonArrayData[i].BANK,
				shouldincomedate : jsonArrayData[i].SHOULDINCOMEDATE
			});
			BillPurBillItem_grid.stopEditing();
			BillPurBillItem_grid.getStore().insert(0, p);
			BillPurBillItem_grid.startEditing(0, 0);
		}
	}
	BillPurBillItem_grid.recalculation(); //重计算总计
	var applyamount = Ext.getDom("BillPurchased.applyamount");
	if (applyamount) {
		applyamount.value = 0 ;
		for(var i=0;i<BillPurBillItem_grid.getStore().getCount();i++){
			applyamount.value = round(parseFloat(applyamount.value) + parseFloat(BillPurBillItem_grid.getStore().getAt(i).get('amount')), 2);
		}
	}
	
}




/**
 *  模拟凭证
 */
function _voucherPreviewBillPurchased()
{
	if ( !commonValidate() ) return;
	
	var param = Form.serialize('mainForm');
	param = param + "&"+ Form.serialize('settleSubjectForm');
	param = param + "&"+ Form.serialize('fundFlowForm');
	param = param + "" + getBillPurBillItemGridData();
	param = param + "" + getBillPurBankItemGridData();
	param = param + "" + getBillPurReBankItemGridData();
	param = param + "" + getBillPurReBankTwoGridData();
	// alert(param);
	new AjaxEngine(contextPath + '/xdss3/billpurchased/billPurchasedController.spr?action=_voucherPreview',
			{
				method		: "post",
				parameters	: param,
				onComplete	: voucherCallBackHandle
			});
}

function voucherCallBackHandle(transport)
{
	BillPurBankItem_grid.getStore().reload();
	BillPurBankItem_grid.getStore().commitChanges();
	BillPurBillItem_grid.getStore().reload();
	BillPurBillItem_grid.getStore().commitChanges();
	BillPurReBankItem_grid.getStore().reload();
	BillPurReBankItem_grid.getStore().commitChanges();
	
	var promptMessagebox = new MessageBoxUtil();
	promptMessagebox.Close();
	if (transport.responseText)
	{
		var voucherid = transport.responseText;
		var andFlag = voucherid.indexOf("&");
		Ext.get('currentWorkflowTask').mask();
		Ext.get('centercontent').mask();
		Ext.get(Ext.getCmp("tabs").getActiveTab().getItemId()).mask();
		Ext.getCmp("tabs").on('tabchange',function(t,p){
			Ext.get(p.getItemId()).mask();
		});
		_getMainFrame().maintabs.addPanel('模拟凭证','', contextPath+'/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.getDom('BillPurchased.billpurid').value,closeVoucherCallBack,true);
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
	if (flag)
		_submitProcessBillPurchased(flag);
}
    

    
/**
 *  删除
 */
function _deleteBillPurBillItem()
{
   //方法执行体
    
}

    
    
/**
 * 押汇银行搜索窗体
 */
var searchBankInfoWin = new Ext.SearchHelpWindow({
	    shlpName : 'YHBANKACCOUNT',
	    callBack : winBankInfoCallBack
});
    
/**
 *  增加押汇银行
 */
function _addBankBillPurBankItem()
{
	if ( BillPurBillItem_grid.getStore().getCount() < 1 ){
		_getMainFrame().showInfo('请先添加[发票]行项！');
		return;
	}
	
	if (BillPurBankItem_grid.getStore().getCount()>0) {
		_getMainFrame().showInfo('仅能添加一条[押汇银行]行项！');
		return;
	}
   //方法执行体
	searchBankInfoWin.defaultCondition = "BANK_ACCOUNT<>' '  and Isusing='1'  ";
	searchBankInfoWin.show();
}
    
/**
 * 增加押汇银行的回调
*/
function winBankInfoCallBack(jsonArrayData)
{
	var tCurrency = Ext.getDom("BillPurchased.currency");
	var tCurrency_text = Ext.getDom("BillPurchased.currency_text");
	var tApplyamount = Ext.getDom("BillPurchased.applyamount");
	var billpurrate = Ext.getDom("BillPurchased.billpurrate");
	if (billpurrate && ! isNaN(billpurrate.value)) {
		var tRealmoney1 = round(billpurrate.value * tApplyamount.value, 2);
	}
	var bill_no = '';
	if ( BillPurBillItem_grid.getStore().getAt(0) ) {
		bill_no = BillPurBillItem_grid.getStore().getAt(0).get('bill_no');
	}	
	for(var i=0;i<jsonArrayData.length;i++){
		var num = BillPurBankItem_grid.getStore().find('bankacc',jsonArrayData[i].BANK_ACCOUNT);
		if(num==-1){
			var supplieramount = 0;
			var param = "bill_no="+bill_no;
			new AjaxEngine(contextPath + '/xdss3/billpurchased/billPurchasedController.spr?action=checkProjCreditType', {
			method:"post", 
			parameters:param, 
			onComplete:function(transport){
				var promptMessagebox = new MessageBoxUtil();
				promptMessagebox.Close();
				var responseUtil = new AjaxResponseUtils(transport.responseText);
				var returnParam = responseUtil.getCustomField("coustom");
				var creditTypes = returnParam.credittypes.split(',');	// 授信类型
				for(var i=0;i<creditTypes.length;i++){
					if(creditTypes[i].indexOf('供应商授信') != -1){
					//if(creditTypes[i]=='4'){
						supplieramount = tRealmoney1;
					}
					var p = new Ext.data.Record({
						bankacc:jsonArrayData[i].BANK_ACCOUNT,
						bankacc_text:jsonArrayData[i].BANK_NAME,
						bankname : jsonArrayData[i].BANK_NAME,
						currency:tCurrency.value,
						currency_text:tCurrency_text.value,
						realmoney:tApplyamount.value,
						realmoney1:tRealmoney1,
						supplieramount:supplieramount,
						cashflowitem:'302',
						cashflowitem_text:'借款所收到的现金',
						banksubj:''
						//banksubj_text:jsonArrayData[i].TXT20
					});
					BillPurBankItem_grid.stopEditing();
					BillPurBankItem_grid.getStore().insert(0, p);
				    BillPurBankItem_grid.startEditing(0, 0);
				}
			}, 
			callback:''});
		} //end if num==-1
	} // end for
}
    
/**
 *  增加还押汇银行
 */
function _addReBankBillPurReBankItem()
{
	if (BillPurBankItem_grid.getStore().getCount() < 1) {
		_getMainFrame().showInfo('请先添加[押汇银行]行项！');
		return;
	}
	//实际押汇金额
	var totalReadlMoney = 0;
	for(var i=0;i<BillPurBankItem_grid.getStore().getCount();i++){
		totalReadlMoney = parseFloat(totalReadlMoney) + parseFloat(BillPurBankItem_grid.getStore().getAt(i).get('realmoney'));
	}
	
	var realmoney = 0;
	for(var i=0;i<BillPurReBankItem_grid.getStore().getCount();i++){
		realmoney = parseFloat(realmoney) + parseFloat(BillPurReBankItem_grid.getStore().getAt(i).get('realmoney'));
	}
	realmoney = round(totalReadlMoney - realmoney, 2);
	if ( realmoney < 0){
		realmoney = 0;
	}
	var billpurrate = Ext.getDom("BillPurchased.billpurrate");
	var rebillpurrate = 0;
	if (BillPurReBankItem_grid.getStore().getCount() > 0 ) {
		rebillpurrate = BillPurReBankItem_grid.getStore().getAt(0).get('rebillpurrate');
	} else {
		rebillpurrate = billpurrate.value;
	}
	var realmoney2 = round(billpurrate.value*realmoney, 2);
	var supplieramount = BillPurBankItem_grid.getStore().getAt(0).get('supplieramount');
	var tCurrency = BillPurBankItem_grid.getStore().getAt(0).get('currency');
	var tCurrency_text = BillPurBankItem_grid.getStore().getAt(0).get('currency_text');
	var bankacc = BillPurBankItem_grid.getStore().getAt(0).get('bankacc');
	var bankacc_text = BillPurBankItem_grid.getStore().getAt(0).get('bankacc_text');
	var maturity = Ext.getDom("BillPurchased.maturity");
	
	var p = new Ext.data.Record({
		bankacc:bankacc,
		bankacc_text:bankacc_text,
		bankname : bankacc_text,
		currency:tCurrency,
		currency_text:tCurrency_text,
		realmoney:realmoney,
		realmoney2:realmoney2,
		rebillpurrate:rebillpurrate,
		supplieramount:supplieramount,
		cashflowitem:'351',
		cashflowitem_text:'偿还债务所支付的现金',
		rematurity:maturity.value
	});
	BillPurReBankItem_grid.stopEditing();
	BillPurReBankItem_grid.getStore().insert(0, p);
	BillPurReBankItem_grid.startEditing(0, 0);
	
	BillPurReBankItem_grid.recalculation(); //重计算总计
}

/**
 *  删除
 */
function _deleteBillPurBankItem()
{
   //方法执行体
 
}

    

    
/**
 *  删除
 */
function _deleteBillPurReBankItem()
{
   //方法执行体
 
}

    

/**
 * 删除押汇银行项
*/
function _predeletesBillPurBankItem()  {
	if (BillPurBankItem_grid.selModel.hasSelection() > 0) {
		var records = BillPurBankItem_grid.selModel.getSelections();
		for (var i = 0; i < records.length; i++) {
			var bankitemid = records[i].get('bankitemid');
			if (bankitemid) {
				var url = contextPath
						+ '/xdss3/billpurchased/billPurchasedController.spr?action=_hasCashJournal'
						+ '&BUSINESSID='
						+ Ext.getDom('BillPurchased.billpurid').value;
				var conn = Ext.lib.Ajax.getConnectionObject().conn;
				conn.open("post", url, false);
				conn.send(null);
				if (conn.responseText == 'true') {
					_getMainFrame().showInfo('不能删除记帐过的 [押汇银行] 行项！');
					return false;
				}
			} // end if
		} // end for
	}
	return true;
}

/**
 * 删除押汇银行项
*/
function _postdeletesBillPurBankItem()  {
	return true;
}
    
    
/**
 * 删除还押汇银行项
*/
function _predeletesBillPurReBankItem() {
	// 还押汇银行行项，存有ID时则不能修改
	if (BillPurReBankItem_grid.selModel.hasSelection() > 0) {
		var records = BillPurReBankItem_grid.selModel.getSelections();
		for (var i = 0; i < records.length; i++) {
			var bankitemid = records[i].get('bankitemid');
			if (bankitemid) {
				_getMainFrame().showInfo('不能删除保存过的 [还押汇银行] 行项！');
				return false;
			}
		}
	}
	return true;
}
    
/**
 * 删除还押汇银行项
*/
function _postdeletesBillPurReBankItem()  {
	return true;
}
    
/**
 *出口押汇行项目
 *批量删除
 */
function _predeletesBillPurBillItem()
{
	return true;
}

/**
 *出口押汇行项目
 *批量删除
 */
function _postdeletesBillPurBillItem()
{
	return true;
}
    
/**
 *提交流程
 */
function _presubmitProcessBillPurchased()
{
	return (commonValidate());
}


/**
 *提交流程
 */
function _postsubmitProcessBillPurchased()
{
	return true;
}
    
    
    
    
    
    
  

          
          

function _precopyCreateBillPurchased()
{
	return true ;
}

function _postcopyCreateBillPurchased()
{

}

          

/**
 * 删除出口押汇
 */
function _predeleteBillPurchased()
{
	return true ;
}

/**
 * 删除出口押汇
 */
function _postdeleteBillPurchased()
{
	return true;
}
          

/**
 * 创建按钮调用方法 
 * 新增出口押汇
 */
function _precreateBillPurchased()
{
	return true ;
}

/**
 * 创建按钮调用方法 
 * 新增出口押汇
 */
function _postcreateBillPurchased()
{

}
          
/**
 * 保存 
 */
function _presaveOrUpdateBillPurchased()
{
	return commonValidate() ;
}

/**
 * 保存 
 */
function _postsaveOrUpdateBillPurchased()
{
	return true;
}

          
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
	return true;
}
          
/**
 * 取消
 */
function _precancelBillPurchased()
{
	return true ;
}

/**
 * 取消
 */
function _postcancelBillPurchased()
{
	return true;
}
          
          
/**
 *  现金日记账记帐
 */
function _cashJournalBillPurchased(){
	var param = Form.serialize('mainForm');
	param = param + "&"+ Form.serialize('settleSubjectForm');
	param = param + "&"+ Form.serialize('fundFlowForm');
	param = param + "" + getBillPurBillItemGridData();
	param = param + "" + getBillPurBankItemGridData();
	param = param + "" + getBillPurReBankItemGridData();
	param = param + "" + getBillPurReBankTwoGridData();
	new AjaxEngine(
			contextPath + '/xdss3/billpurchased/billPurchasedController.spr?action=_cashJournal',
			{
				method : "post",
				parameters : param,
				onComplete : _cashJournalCallBackHandle
			});

}
 /**
  * 现金日记账回调函数
  */
function _cashJournalCallBackHandle(transport){
	var promptMessagebox = new MessageBoxUtil();
	promptMessagebox.Close();
	
	BillPurBankItem_grid.getStore().reload();
	BillPurBankItem_grid.getStore().commitChanges();
	BillPurBillItem_grid.getStore().reload();
	BillPurBillItem_grid.getStore().commitChanges();
	BillPurReBankItem_grid.getStore().reload();
	BillPurReBankItem_grid.getStore().commitChanges();
	
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
//	Ext.getDom("BillPurchased.createtime").value = result.createtime;
//	Ext.getDom("BillPurchased.creator_text").value = result.creator_text;
//	Ext.getDom("BillPurchased.creator").value = result.creator;
//	Ext.getDom("BillPurchased.lastmodifytime").value = result.lastmodifytime;
	_getMainFrame().maintabs.addPanel('现金日记账','', result.cashJournalURl);
}