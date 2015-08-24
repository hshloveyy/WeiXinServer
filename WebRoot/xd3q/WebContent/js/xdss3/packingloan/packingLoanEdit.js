/**
 * Author(s):java业务平台代码生成工具 Date: 2011年05月19日 16点48分20秒 Copyright Notice:版权声明
 * 福建讯盟软件有限公司, 版权所有 违者必究
 * 
 * @(#) Description: <功能>主对象打包贷款(PackingLoan)编辑页面用户可编程扩展JS文件
 * 
 * 业务填写相关信息 资金部出纳确认打包贷款收款 财务会计确认打包贷款过账
 * 
 * 业务填写还打包贷款信息 资金部出纳确认还打包贷款 财务会计确认还打包贷款并做帐 PackingBankItem_grid
 * PackingBankItem_store
 * 
 * PackingReBankItem_grid PackingReBankItem_store
 */

var ps = "";
/**
 * 初始化界面信息
 */
Ext.onReady(function() {
	Ext.getDom('PackingLoan.packing_no').readOnly = true; // 打包贷款单号
	var maintab = Ext.getCmp("tabs");
	ps = Ext.getDom('PackingLoan.processstate').value;
	// 隐藏所有页签的"增加行"、"删除行"按钮
	var crToolbar = PackingBankItem_grid.getTopToolbar();
	crToolbar.items.get('PackingBankItemaddRow').hide();
	crToolbar.items.get('PackingBankItemdeleteRow').hide();
	var ccToolbar = PackingReBankItem_grid.getTopToolbar();
	ccToolbar.items.get('PackingReBankItemaddRow').hide();
	ccToolbar.items.get('PackingReBankItemdeleteRow').hide();
	var twoToolbar = PackingReBankTwo_grid.getTopToolbar();
	twoToolbar.items.get('PackingReBankTwoaddRow').hide();	
	twoToolbar.items.get('PackingReBankTwodeleteRow').hide();	

	// 非资金部节点隐藏现金日记帐
	if ((ps != '资金部出纳确认打包贷款收款') && (ps != '资金部出纳确认还打包贷款')) {
		Ext.getCmp('_cashJournal').hide();
	}

	// 判断打包还与还打包节点
	var eeFlag = (ps == '业务填写相关信息' || ps == '资金部出纳确认打包贷款收款' || ps == '财务会计确认打包贷款过账'); // 打包节点
	var reFlag = (ps == '业务填写还打包贷款信息' || ps == '资金部出纳确认还打包贷款' || ps == '财务会计确认还打包贷款并做帐'); // 还打包节点

	if (eeFlag) {
		// 打包节点不可删除 还打包银行
		ccToolbar.items.get('PackingReBankItem._addReBank').hide();
		ccToolbar.items.get('PackingReBankItem._deletes').hide();
	} else {
		calendar_PackingLoan_voucherdate.setEditable(false); // 打包凭证日期
		calendar_PackingLoan_accountdate.setEditable(false); // 打包记帐日期
		PackingBankItem_grid.on('rowclick', function(grid, rowIndex, e) {
			 Ext.getDom('PackingLoan.remark').focus();
			 _getMainFrame().showInfo('不能修改[打包银行]行项！');
			 return false;
		 });
	}

	if (reFlag) {
		// 还打包节点不可删除 打包银行
		crToolbar.items.get('PackingBankItem._addBank').hide();
		crToolbar.items.get('PackingBankItem._deletes').hide();
		Ext.getDom('PackingLoan.packingreate').readOnly = true; // 打包汇率不可修改
		Ext.getDom('PackingLoan.applyamount').readOnly = true; // 打包申请金额

	} else {
		// 控制界面不可用
		Ext.getDom('PackingLoan.retext').readOnly = true; // 抬头文本(还打包用途)
		calendar_PackingLoan_revoucherdate.setEditable(false); // 还打包凭证日期
		calendar_PackingLoan_reaccountdate.setEditable(false); // 还打包记帐日期
	}
	if (!eeFlag && !reFlag) {
		// 非打包节点、非还打包节点屏蔽 打包银行、还打包银行。
// maintab.hideTabStripItem('packingBankItemTab');
// maintab.hideTabStripItem('packingReBankItemTab');
	}
	 if ((ps == '财务会计确认还打包贷款并做帐') || (ps == '财务会计确认打包贷款过账')) {
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
// maintab.hideTabStripItem('settleSubjectTab');
// maintab.hideTabStripItem('fundFlowTab');
	 }
		
	 // 打包汇率改变事件 2 打包银行行项
	 PackingBankItem_grid.on("afterEdit", afterEditForPackingBankItem,
	 PackingBankItem_grid);
	 function afterEditForPackingBankItem (e) {
		 var record = e.record;// 被编辑的记录
		 var packingreate = Ext.getDom('PackingLoan.packingreate');
		 var bankrate = parseFloat(record.get("bankrate"));
		 if ( (e.field == 'realmoney' || e.field == 'bankrate' || e.field == 'applyamount') && Utils.isNumber(bankrate)) {
			 if ( Utils.isNumber(bankrate * record.get("realmoney")) ) {
				 record.set('realmoney1', round((bankrate * record.get("realmoney")),2));
			 }
			 if ( Utils.isNumber(bankrate * record.get("applyamount")) ) {
				 record.set('applyamount2', round((bankrate * record.get("applyamount")),2));
			 }
			 packingreate.value = bankrate;
		 }
		 PackingBankItem_grid.recalculation();
	 }
		
	 // 打包银行行项，存有ID时则不能修改
	 PackingBankItem_grid.on('rowclick', function(grid, rowIndex, e) {
		 var record = grid.getStore().getAt(rowIndex);
		 var businesstype = record.get('businesstype');
		 if ( ! (businesstype=='' || businesstype==' ') ) {
			 Ext.getDom('PackingLoan.remark').focus();
			 _getMainFrame().showInfo('不能修改[打包银行]行项！');
			 return false;
		 }
	 });
		
	 // 还打包银行行项
	 PackingReBankItem_grid.on("afterEdit",
	 afterEditForPackingReBankItem, PackingReBankItem_grid);
	 function afterEditForPackingReBankItem (e) {
		 var record = e.record;// 被编辑的记录
    	var billpurrate = parseFloat(Ext.getDom('PackingLoan.packingreate').value);
    	if ( (e.field == 'realmoney' || e.field == 'rebillpurrate' || e.field == 'applyamount')&& Utils.isNumber(billpurrate)) {
    		record.set('rebillpurrate', billpurrate);
    		record.set('realmoney2', round((billpurrate * record.get("realmoney")),2));
    		record.set('applyamount2', round((billpurrate * record.get("applyamount")),2));
    	}
		 PackingReBankItem_grid.recalculation();
	 }
		
	 // 还打包银行行项，存有ID时则不能修改
	 PackingReBankItem_grid.on('rowclick', function(grid, rowIndex, e)
	 {
		var record = grid.getStore().getAt(rowIndex);
		var businesstype = record.get('businesstype');
		if (businesstype=='已做账') {
		Ext.getDom('PackingLoan.remark').focus();
			_getMainFrame().showInfo('不能修改该[还打包银行]行项！');
			return false;
		}
	 });

	// 还押汇银行2 汇率自动计算
	PackingReBankTwo_grid.on("afterEdit", afterEditForPackingReBankTwo, PackingReBankTwo_grid);
	function afterEditForPackingReBankTwo (e) {
		var record = e.record;// 被编辑的记录
    	var rebillpurrate3 = parseFloat(record.get("rebillpurrate3"));
    	var applyamount3 = parseFloat(record.get("applyamount3"));
    	var applyamount4 = parseFloat(record.get("applyamount4"));
    	if ( e.field == 'applyamount3' || e.field == 'applyamount4' || e.field == 'rebillpurrate3') {
    		if ( Utils.isNumber(applyamount3 * rebillpurrate3) ) {
    			record.set('applyamount4', round((applyamount3 * rebillpurrate3),2));
    		}
    	}
		PackingReBankTwo_grid.recalculation();
	}
		// 还押汇银行2 改银行限定条件
	PackingReBankTwo_grid.on("beforeEdit", beforeEditForPackingReBankTwo, PackingReBankTwo_grid);
	function beforeEditForPackingReBankTwo (e) {
		var record = e.record;// 被编辑的记录
    	if ( e.field == 'bankacc3' ) {
			var gridlist = PackingReBankTwo_grid.getColumnModel().columns;
			for(var i=0;i<gridlist.length;i++){
				var col = gridlist[i];
				if (col.dataIndex =="bankacc3" )
				{
					col.editor.field.defaultCondition = " BANK_ACCOUNT<>' '  and Isusing='1' ";
				} // end if
			} // end for
    	}
		PackingReBankTwo_grid.recalculation();
		var businesstype = record.get('businesstype');
		if (businesstype=='已做账') {
			Ext.getDom('PackingLoan.remark').focus();
			_getMainFrame().showInfo('不能修改该[还打包银行2]行项！');
			return false;
		}
		
	}
		
	// 抬头汇率变动时，打包银行行项进行变动
	Ext.getDom('PackingLoan.packingreate').onchange = function (){
		var packingreate = parseFloat(Ext.getDom('PackingLoan.packingreate').value);
		for(var i=0;i<PackingBankItem_grid.getStore().getCount();i++){
			var record = PackingBankItem_grid.getStore().getAt(i);
			if ( Utils.isNumber(packingreate * record.get("realmoney")) ) {
				record.set('realmoney1', round((packingreate * record.get("realmoney")),2));
			}
			if ( Utils.isNumber(packingreate * record.get("applyamount")) ) {
				record.set('applyamount2', round((packingreate * record.get("applyamount")),2));
			}
			record.set('bankrate', packingreate);
		}
		PackingBankItem_grid.recalculation();
	};
});

/**
 * 通用验证
 */
function commonValidate(){
	var ns = Ext.getDom('workflowLeaveTransitionName').value;
	// 回退操作不验证
	if (ns == '退回修改' || ns == '退回出纳' || ns == '退回业务修改相关信息'
			|| ns == '退回业务修改还打包信息') {
		return true;
	}
	if ("资金部出纳确认打包贷款收款"==ps || "资金部出纳确认还打包贷款"==ps || "财务会计确认打包贷款过账"==ps) {
		if (PackingBankItem_grid.getStore().getCount() < 1) {
			_getMainFrame().showInfo('[打包银行]存在为空数据，请添加！');
			 return false;
		}
	}
	 for(var i=0;i<PackingBankItem_grid.getStore().getCount();i++){
		 if (ps == '财务会计确认打包贷款过账') {
			 var banksubj = PackingBankItem_grid.getStore().getAt(i).get('banksubj');
			 if (banksubj == '' || banksubj == ' ') {
				 _getMainFrame().showInfo('[打包银行－打包银行科目]存在为空数据，请添加！');
				 return false;
			 }
		 var bankrate = PackingBankItem_grid.getStore().getAt(i).get('bankrate');
			 if (bankrate == '' || bankrate == ' ') {
				 _getMainFrame().showInfo('[打包银行－打包汇率]存在为空数据，请添加！');
				 return false;
			 }
		 }
		 var currency = PackingBankItem_grid.getStore().getAt(i).get('currency');
		 if (currency=='' || currency == ' ') {
		 _getMainFrame().showInfo('[打包银行－打包币别]存在为空数据，请添加！');
		 return false;
		 }
		 var applyamount =
		 PackingBankItem_grid.getStore().getAt(i).get('applyamount');
		 if (applyamount=='' || applyamount == ' ') {
		 _getMainFrame().showInfo('[打包银行－实际打包金额]存在为空数据，请添加！');
		 return false;
		 }
		 var applyamount2 = PackingBankItem_grid.getStore().getAt(i).get('applyamount2');
		 if (applyamount2=='' || applyamount2 == ' ') {
		 _getMainFrame().showInfo('[打包银行－实际打包金额本位币]存在为空数据，请添加！');
		 return false;
		 }
	 }
	 for(var i=0;i<PackingReBankItem_grid.getStore().getCount();i++){
		var bankacc = PackingReBankItem_grid.getStore().getAt(i).get('bankacc');
		if (bankacc=='' || bankacc == ' ') {
			_getMainFrame().showInfo('[还打包银行－还打包银行帐号]存在为空数据，请添加！');
			return false;
		}
		 var rebillpurrate = PackingReBankItem_grid.getStore().getAt(i).get('rebillpurrate');
		 if (rebillpurrate=='' || rebillpurrate == ' ') {
			 _getMainFrame().showInfo('[还打包银行－还打包汇率]存在为空数据，请添加！');
			 return false;
		 }
		 var currency = PackingReBankItem_grid.getStore().getAt(i).get('currency');
		 if (currency=='' || currency == ' ') {
			 _getMainFrame().showInfo('[还打包银行－还打包币别]存在为空数据，请添加！');
			 return false;
		 }
	 }
		
	 if (ps == '业务填写相关信息') {
		 if (PackingBankItem_grid.getStore().getCount() < 1) {
			 _getMainFrame().showInfo('请添加[打包银行]行项！');
			 return false;
		 }
		 var billpurrate = Ext.getDom('PackingLoan.packingreate').value;
		 if (billpurrate=='' || billpurrate==0) {
			 _getMainFrame().showInfo('打包汇率不能为空或者为0！');
			 return false;;
		 }
	 }
	
	 if (ps == '业务填写还打包信息') {
		 if (PackingReBankItem_grid.getStore().getCount() < 1) {
			 _getMainFrame().showInfo('请添加[还打包银行]行项！');
			 return false;
		 }
	 }
		
	 if (ps == '财务会计确认还打包贷款并做帐') {
		 if (Ext.getDom("PackingLoan.revoucherdate").value == '' ) {
			 _getMainFrame().showInfo('请添加[还打包凭证日期]行项！');
			 return false;
		 } else if (Ext.getDom("PackingLoan.reaccountdate").value == ''){
			 _getMainFrame().showInfo('请添加[还打包记帐日期]行项！');
			 return false;
		 } else if (Ext.getDom("PackingLoan.retext").value == '' || Ext.getDom("PackingLoan.retext").value == ' ') {
			 _getMainFrame().showInfo('请添加[抬头文本(还打包用途)]行项！');
			 return false;
		 }
	 }
		
	 if (ps == '财务会计确认打包贷款过账') {
		 if (Ext.getDom("PackingLoan.voucherdate").value == '' ) {
			 _getMainFrame().showInfo('请添加[打包凭证日期]行项！');
			 return false;
		 } else if (Ext.getDom("PackingLoan.accountdate").value == ''){
			 _getMainFrame().showInfo('请添加[打包记帐日期]行项！');
			 return false;
		 } else if (Ext.getDom("PackingLoan.text").value == '' || Ext.getDom("PackingLoan.text").value == ' ') {
			 _getMainFrame().showInfo('请添加[抬头文本(打包用途)]行项！');
			 return false;
		 }
	 }
			
		return true;
}




/**
 * 模拟凭证
 */
function _voucherPreviewPackingLoan()
{
	if ( !commonValidate() ) return;
	
	var param = Form.serialize('mainForm');
	param = param + "&"+ Form.serialize('settleSubjectForm');
	param = param + "&"+ Form.serialize('fundFlowForm');
	param = param + "" + getPackingBankItemGridData();
	param = param + "" + getPackingReBankItemGridData();
	param = param + "" + getPackingReBankTwoGridData();
	// alert(param);
	new AjaxEngine(contextPath + '/xdss3/packingloan/packingLoanController.spr?action=_voucherPreview',
			{
				method		: "post",
				parameters	: param,
				onComplete	: voucherCallBackHandle
			});
}

function voucherCallBackHandle(transport)
{
	PackingBankItem_grid.getStore().reload();
	PackingBankItem_grid.getStore().commitChanges();
	PackingReBankItem_grid.getStore().reload();
	PackingReBankItem_grid.getStore().commitChanges();
	
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
		_getMainFrame().maintabs.addPanel('模拟凭证','', contextPath+'/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.getDom('PackingLoan.packingid').value,closeVoucherCallBack,true);
	}
}

/**
 * 关闭凭证窗体回调动作
 */
function closeVoucherCallBack(flag){
	Ext.get('currentWorkflowTask').unmask();
	Ext.get('centercontent').unmask();
	Ext.get(Ext.getCmp("tabs").getActiveTab().getItemId()).unmask();
	
	Ext.getCmp("tabs").on('tabchange',function(t,p){
		Ext.get(p.getItemId()).unmask();
	});
	if (flag)
		_submitProcessPackingLoan(flag);
}
/**
 * 打包银行搜索窗体
 */
var searchBankInfoWin = new Ext.SearchHelpWindow({
	    shlpName : 'YHBANKACCOUNT',
	    callBack : winBankInfoCallBack
});

/**
 * 增加打包银行
 */
function _addBankPackingBankItem()
{
	if (PackingBankItem_grid.getStore().getCount()>0) {
		_getMainFrame().showInfo('仅能添加一条[打包银行]行项！');
		return;
	}
   // 方法执行体
	searchBankInfoWin.defaultCondition = "BANK_ACCOUNT<>' '";
	searchBankInfoWin.show();
}

/**
 * 增加打包银行的回调
 */
function winBankInfoCallBack(jsonArrayData)
{
	var tCurrency = Ext.getDom("PackingLoan.currency");
	var tCurrency_text = Ext.getDom("PackingLoan.currency_text");
	var tApplyamount = Ext.getDom("PackingLoan.applyamount");
	var packingreate = Ext.getDom("PackingLoan.packingreate");
	if (packingreate && ! isNaN(packingreate.value)) {
		var tRealmoney1 = round(packingreate.value * tApplyamount.value, 2);
	}
	for(var i=0;i<jsonArrayData.length;i++){
		var num = PackingBankItem_grid.getStore().find('bankacc',jsonArrayData[i].BANK_ACCOUNT);
		if(num==-1){
			var supplieramount = 0;
// new AjaxEngine(contextPath +
// '/xdss3/packingloan/packingLoanController.spr?action=checkProjCreditType',
// {
// method:"post",
// parameters:param,
// onComplete:function(transport){
// var promptMessagebox = new MessageBoxUtil();
// promptMessagebox.Close();
// var responseUtil = new AjaxResponseUtils(transport.responseText);
// var returnParam = responseUtil.getCustomField("coustom");
// var creditTypes = returnParam.credittypes.split(','); // 授信类型
// for(var i=0;i<creditTypes.length;i++){
// if(creditTypes[i]=='4'){
// supplieramount = tRealmoney1;
// }
					var p = new Ext.data.Record({
						bankacc:jsonArrayData[i].BANK_ACCOUNT,
						bankacc_text:jsonArrayData[i].BANK_NAME,
						currency:tCurrency.value,
						currency_text:tCurrency_text.value,
						bankrate:packingreate.value,
						applyamount:tApplyamount.value,
						applyamount2:tRealmoney1,
						supplieramount:supplieramount,
						cashflowitem:'302',
						cashflowitem_text:'借款所收到的现金',
						banksubj:'',
						businesstype:''
						// banksubj_text:jsonArrayData[i].TXT20
					});
					PackingBankItem_grid.stopEditing();
					PackingBankItem_grid.getStore().insert(0, p);
				    PackingBankItem_grid.startEditing(0, 0);
// }
// },
// callback:''});
		} // end if num==-1
	} // end for
}

/**
 * 增加还打包银行
 */
function _addReBankPackingReBankItem()
{
	if (PackingBankItem_grid.getStore().getCount() < 1) {
		_getMainFrame().showInfo('请先添加[打包银行]行项！');
		return;
	}
	// 实际打包金额
	var totalReadlMoney = 0;
	for(var i=0;i<PackingBankItem_grid.getStore().getCount();i++){
		totalReadlMoney = parseFloat(totalReadlMoney) + parseFloat(PackingBankItem_grid.getStore().getAt(i).get('applyamount'));
	}
	
	var applyamount = 0;
	for(var i=0;i<PackingReBankItem_grid.getStore().getCount();i++){
		applyamount = parseFloat(applyamount) + parseFloat(PackingReBankItem_grid.getStore().getAt(i).get('applyamount'));
	}
	applyamount = round(totalReadlMoney - applyamount, 2);
	if ( applyamount < 0){
		applyamount = 0;
	}
	var billpurrate = Ext.getDom("PackingLoan.packingreate");
	var rebillpurrate = 0;
	if (PackingReBankItem_grid.getStore().getCount() > 0 ) {
		rebillpurrate = PackingReBankItem_grid.getStore().getAt(0).get('rebillpurrate');
	} else {
		rebillpurrate = billpurrate.value;
	}
	var applyamount2 = round(rebillpurrate*applyamount, 2);
	var supplieramount = PackingBankItem_grid.getStore().getAt(0).get('supplieramount');
	var tCurrency = PackingBankItem_grid.getStore().getAt(0).get('currency');
	var tCurrency_text = PackingBankItem_grid.getStore().getAt(0).get('currency_text');
	var bankacc = PackingBankItem_grid.getStore().getAt(0).get('bankacc');
	var bankacc_text = PackingBankItem_grid.getStore().getAt(0).get('bankacc_text');
	var maturity = Ext.getDom("PackingLoan.dealine");
	
	var p = new Ext.data.Record({
//		bankacc:bankacc,
//		bankacc_text:bankacc_text,
		currency:tCurrency,
		currency_text:tCurrency_text,
		realmoney:0,
		realmoney2:0,
		rebillpurrate:rebillpurrate,
		supplieramount:supplieramount,
		cashflowitem:'351',
		cashflowitem_text:'偿还债务所支付的现金',
		rematurity:maturity.value,
		applyamount:applyamount,
		applyamount2:applyamount2,
		businesstype:''
	});
	PackingReBankItem_grid.stopEditing();
	PackingReBankItem_grid.getStore().insert(0, p);
	PackingReBankItem_grid.startEditing(0, 0);
	
	PackingReBankItem_grid.recalculation(); // 重计算总计
}



/**
 * grid 上的 创建按钮调用方法 新增打包贷款,打包贷款银行
 */
function _precreatePackingBankItem()
{
	return true ;
}

/**
 * grid 上的 创建按钮调用方法 新增打包贷款,打包贷款银行
 */
function _postcreatePackingBankItem()
{

}
   
    

    
/**
 * 打包贷款行项目 复制创建
 */
function _precopyCreatePackingBankItem()
{
	return true ;
}

/**
 * 打包贷款行项目 复制创建
 */
function _postcopyCreatePackingBankItem()
{

}
    

    
/**
 * 打包贷款行项目 批量删除
 */
function _predeletesPackingBankItem()
{
	if (PackingBankItem_grid.selModel.hasSelection() > 0) {
		var records = PackingBankItem_grid.selModel.getSelections();
		for (var i = 0; i < records.length; i++) {
			var businesstype = records[i].get('businesstype');
			if (businesstype=='已做账') {
				Ext.getDom('PackingLoan.remark').focus();
				_getMainFrame().showInfo('不能修改[打包银行]行项！');
				return false;
			} // end if
		} // end for
	}
	return true ;
}

/**
 * 打包贷款行项目 批量删除
 */
function _postdeletesPackingBankItem()
{

}
    

    
  

    
/**
 * 打包贷款行项目编辑操作
 */
function _preeditPackingBankItem(id,url)
{
	return true ;
}

/**
 * 打包贷款行项目编辑操作
 */
function _posteditPackingBankItem(id,url)
{

}

    

/**
 * 打包贷款行项目查看操作
 */
function _previewPackingBankItem(id,url)
{
	return  true ;
}

/**
 * 打包贷款行项目查看操作
 */
function _postviewPackingBankItem(id,url)
{

}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
          
   
/**
 * grid 上的 创建按钮调用方法 新增打包贷款,还打包贷款银行
 */
function _precreatePackingReBankItem()
{
	return true ;
}

/**
 * grid 上的 创建按钮调用方法 新增打包贷款,还打包贷款银行
 */
function _postcreatePackingReBankItem()
{

}
   
    

    
/**
 * 打包贷款行项目 复制创建
 */
function _precopyCreatePackingReBankItem()
{
	return true ;
}

/**
 * 打包贷款行项目 复制创建
 */
function _postcopyCreatePackingReBankItem()
{

}
    

    
/**
 * 打包贷款行项目 批量删除
 */
function _predeletesPackingReBankItem()
{
	// 还打包银行行项，存有ID时则不能修改
	if (PackingReBankItem_grid.selModel.hasSelection() > 0) {
		var records = PackingReBankItem_grid.selModel.getSelections();
		for (var i = 0; i < records.length; i++) {
			var businesstype = records[i].get('businesstype');
			if (businesstype=='已做账') {
				_getMainFrame().showInfo('不能删除保存过的 [还打包银行] 行项！');
				return false;
			}
		}
	}
	return true ;
}

/**
 * 打包贷款行项目 批量删除
 */
function _postdeletesPackingReBankItem()
{

}
    

    
  


/**
 * 打包贷款行项目查看操作
 */
function _previewPackingReBankItem(id,url)
{
	return  true ;
}

/**
 * 打包贷款行项目查看操作
 */
function _postviewPackingReBankItem(id,url)
{

}
    
/**
 * 打包贷款行项目编辑操作
 */
function _preeditPackingReBankItem(id,url)
{
	return true ;
}

/**
 * 打包贷款行项目编辑操作
 */
function _posteditPackingReBankItem(id,url)
{

}

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
          
   
    
          
   
    
    
    
  

    
  

          

function _precopyCreatePackingLoan()
{
	return true ;
}

function _postcopyCreatePackingLoan()
{

}
          

/**
 * 删除打包贷款
 */
function _predeletePackingLoan()
{
	return true ;
}

/**
 * 删除打包贷款
 */
function _postdeletePackingLoan()
{

}
          

/**
 * 创建按钮调用方法 新增打包贷款
 */
function _precreatePackingLoan()
{
	return true ;
}

/**
 * 创建按钮调用方法 新增打包贷款
 */
function _postcreatePackingLoan()
{

}
          
/**
 * 保存
 */
function _presaveOrUpdatePackingLoan()
{
	return commonValidate() ;
}

/**
 * 保存
 */
function _postsaveOrUpdatePackingLoan()
{

}
          
/**
 * 取消
 */
function _precancelPackingLoan()
{
	return true ;
}

/**
 * 取消
 */
function _postcancelPackingLoan()
{

}
          

/**
 * 提交
 */
function _presubmitProcessPackingLoan()
{
	return commonValidate() ;
}

/**
 * 提交
 */
function _postsubmitProcessPackingLoan()
{

}




/**
 * 现金日记账记帐
 */
function _cashJournalPackingLoan(){
	var param = Form.serialize('mainForm');
	param = param + "&"+ Form.serialize('settleSubjectForm');
	param = param + "&"+ Form.serialize('fundFlowForm');
	param = param + "" + getPackingBankItemGridData();
	param = param + "" + getPackingReBankItemGridData();
	param = param + "" + getPackingReBankTwoGridData();
	new AjaxEngine(
			contextPath + '/xdss3/packingloan/packingLoanController.spr?action=_cashJournal',
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
	
	PackingBankItem_grid.getStore().reload();
	PackingBankItem_grid.getStore().commitChanges();
	PackingReBankItem_grid.getStore().reload();
	PackingReBankItem_grid.getStore().commitChanges();
	PackingReBankTwo_grid.getStore().reload();
	PackingReBankTwo_grid.getStore().commitChanges();
	
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	_getMainFrame().maintabs.addPanel('现金日记账','', result.cashJournalURl);
}
