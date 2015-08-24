/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年06月30日 11点22分36秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象供应商退款(SupplierRefundment)增加页面用户可编程扩展JS文件
 */
var ps = "";

Ext.onReady(function(){
	var maintab = Ext.getCmp("tabs");
	maintab.hideTabStripItem('settleSubjectTab');
	maintab.hideTabStripItem('fundFlowTab');
	ps = Ext.getDom('SupplierRefundment.processstate').value;
	// 若不为重分配，将"重分配提交"按钮隐藏；否则将"提交"按钮隐藏
	if(isReassign != 'Y'){
		Ext.getCmp("_submitForReassign").hide();
	}else{
		Ext.getCmp('_submitProcess').hide();
	}
	/*
	 * 退款币别变更时触发汇率的填写事件
	 */
	div_refundcurrency_sh_sh.on('change',function(){
		if(this.getValue()=='CNY'){
			Ext.getDom('SupplierRefundment.exchangerate').value = 1;
		}else{
			Ext.getDom('SupplierRefundment.exchangerate').value = '';
		}
	});
	/*
	 * 汇率失去焦点的时候计算退款银行行项的金额
	 */
	Ext.getDom('SupplierRefundment.exchangerate').onblur = function(){
		var rate = parseFloat(Ext.getDom('SupplierRefundment.exchangerate').value);
		if(Utils.isNumber(rate)){
			var store = SupplierDbBankItem_store;
			for(var i=0;i<store.getCount();i++){
				// 计算"退款金额（本位币）"
				store.getAt(i).set('refundamount2', round((rate * store.getAt(i).get('refundamount')),2));
			}
			var itemStore = SupplierRefundItem_store; // 根据汇率计算退款行项中立项、合同 "退款金额（本位币）"
			for(var i=0;i<itemStore.getCount();i++){
				if(store.getAt(i).get('paymentitemid').trim()=='' && store.getAt(i).get('contract_no').trim()!=''){
					itemStore.getAt(i).set('refundmentvalue', round((rate * itemStore.getAt(i).get('pefundmentamount')),2));
				}
			}
			SupplierDbBankItem_grid.recalculation();
		}
	}
	/*
	 * 设置供应商退款行项目编辑后触发
	 */
	SupplierRefundItem_grid.on("afteredit", afterEditForRefundItem, SupplierRefundItem_grid);
	function afterEditForRefundItem(e) {
		var record = e.record;// 被编辑的记录
		var unassignamount = record.get("unassignamount");
		var refundmentamount = record.get("refundmentamount");
		var pefundmentamount = record.get("pefundmentamount");
		var exchangerate = parseFloat(record.get("exchangerate"));
		if(e.field=='pefundmentamount' || e.field=='exchangerate'){
			if (record.get("paymentitemid") == '' || record.get("paymentitemid") ==' ') {
				exchangerate=1;
				record.set('exchangerate', 1);
			}
			// 清票金额大于未分配金额
			if (round(pefundmentamount,2) > round(unassignamount,2)) {
				_getMainFrame().showInfo("清票金额必须小于等于未分配金额，请重新输入！");
				record.set('refundmentamount', 0);			
			}else{
				if(record.get('paymentitemid').trim()!=''){
					// 将"退款金额"自动带到"清原币金额"
					record.set('refundmentamount', round(record.get("pefundmentamount"),2));
				}
				// 根据汇率计算退款金额本位币
				record.set('refundmentvalue', round((exchangerate * pefundmentamount),2));
			}
		}
		SupplierRefundItem_grid.recalculation();
	};
	/*
	 * 设置供应商退款银行行项目编辑后触发
	 */
    SupplierDbBankItem_grid.on("afteredit", afterEditForBankItem, SupplierDbBankItem_grid);
    function afterEditForBankItem(e) {
    	var record = e.record;// 被编辑的记录
    	// 修改退款金额
    	var exchangerate = parseFloat(Ext.getDom('SupplierRefundment.exchangerate').value);
    	if (e.field == 'refundamount' && Utils.isNumber(exchangerate)) {
    		record.set('refundamount2', round((exchangerate * record.get("refundamount")),2));
    	}
    	SupplierDbBankItem_grid.recalculation();
    };
    
	div_supplier_sh_sh.callBack = function(data){
		//同步所选供应商未清数据
		Ext.Ajax.request({
	        url : contextPath+'/xdss3/supplierDrawback/supplierRefundmentController.spr?action=_synchronizeUnclearVendor&privadeid=' + div_supplier_sh_sh.getValue(),
	        success : function(xhr){
	        },
	        scope : this
	    });
	}
	
});

/**
 *  1.供应商退款行项目增加
 */
function _addSupplierRefundItem(){
	if (div_supplier_sh_sh.getValue() == '') {
		_getMainFrame().showInfo('请先选择供应商！');
		return;
	}
	searchSupplierRefundItemWin.defaultCondition = "BILLISCLEAR='0' AND (BUSINESSSTATE IN ('4', '7') OR (PAYMENTTYPE IN ('06', '07') OR ( pay_type = '2' AND isoverrepay = '0' ) AND EXISTS(SELECT 'x' FROM YVOUCHERITEM VI WHERE VI.BUSINESSITEMID = PAYMENTITEMID))) AND DEPT_ID='"+div_dept_id_sh_sh.getValue()+"' AND SUPPLIER='" + div_supplier_sh_sh.getValue() + "'";
	searchSupplierRefundItemWin.show();
}

/**
 *  1.供应商退款行项目搜索帮助
 */
var searchSupplierRefundItemWin = new Ext.SearchHelpWindow( {
	shlpName :'YHSUPPLIERDRAWBACK',
	callBack :winRefundItemAddCallBack
});

/**
 *  1.供应商退款行项目回调函数
 */
function winRefundItemAddCallBack(jsonArrayData) {
	var ischange = false;
	var itemids = '';
	var _representpaycust = Ext.getDom("SupplierRefundment.representpaycust").value; // 取界面现有客户ID
	var repEmpty = (_representpaycust=='' || _representpaycust== ' ' || _representpaycust==undefined); // 空值为真
	for ( var i = 0; i < jsonArrayData.length; i++) {
		var rec = {
			paymentitemid :jsonArrayData[i].PAYMENTITEMID,
			paymentno :jsonArrayData[i].PAYMENTNO,
			contract_no :jsonArrayData[i].CONTRACT_NO,
			project_no :jsonArrayData[i].PROJECT_NO,
			assignamount :jsonArrayData[i].ASSIGNAMOUNT,
			currency: jsonArrayData[i].CURRENCY,
			refundcurrency: jsonArrayData[i].REFUNDCURRENCY,
			isrepresentpay: jsonArrayData[i].ISREPRESENTPAY,
			representpaycust: jsonArrayData[i].REPRESENTPAYCUST
		};
		var p = new Ext.data.Record(rec);
		var num = SupplierRefundItem_grid.getStore().find('paymentitemid',
				jsonArrayData[i].PAYMENTITEMID);
		_representpaycust = Ext.getDom("SupplierRefundment.representpaycust").value; // 取界面现有客户ID
		if (num == -1) {
			// 加入判断是否同属一纯代理客户付款
			// alert(repEmpty+"<"+_representpaycust+"<l"+jsonArrayData[i].REPRESENTPAYCUST+"<>"+SupplierRefundItem_grid.getStore().getCount());
			if(
				// 原为纯代理，再次添加不相等数据
				 ( !repEmpty && _representpaycust != jsonArrayData[i].REPRESENTPAYCUST)
				// 原不为纯代理，再次添加了纯代理
				 || ( repEmpty && p.data.isrepresentpay=='1'   && SupplierRefundItem_grid.getStore().getCount() > 0 )
			){
				continue;
			}
			div_representpaycust_sh_sh.setValue(jsonArrayData[i].REPRESENTPAYCUST);
			if  (p.data.isrepresentpay=='1') {
				div_cashflowitem_sh_sh.setValue('154');
			} else {
				div_cashflowitem_sh_sh.setValue('151');
			}
			ischange = true;
			itemids += p.data.paymentitemid + ",";
		}
	}
	if (ischange) {
		if (itemids != '') {
			Ext.Ajax.request( {
						url :'supplierRefundmentController.spr?action=_getRefundmentItemGridData',
						params : {itemids :itemids},
						success : function(xhr) {
							if (xhr.responseText) {
								var jsonData = Ext.util.JSON.decode(xhr.responseText);

								for ( var j = 0; j < jsonData.data.length; j++) {
									var p = new Ext.data.Record(jsonData.data[j]);
									SupplierRefundItem_grid.stopEditing();
									SupplierRefundItem_grid.getStore().insert(0, p);
									SupplierRefundItem_grid.startEditing(0, 0);
								}
							}
						},
						scope :this
					});	
		}
	}
}

/**
 *  2.供应商退款合同增加
 */
function _addContSupplierRefundItem(){
	if(div_supplier_sh_sh.getValue()==''){
        _getMainFrame().showInfo("必须先选择[供应商]！");
        return;
	}
	var store = SupplierRefundItem_store;
	var nos = "' ',";
	for(var i=0;i<store.getCount();i++){
		// 若本条数据的付款号为空，则将本条数据的合同号过滤掉
		if(store.getAt(i).get('paymentitemid').trim()=='' && store.getAt(i).get('contract_no').trim()!=''){
			nos += "'" + store.getAt(i).get('contract_no') + "',";
		}
	}
	nos = nos.substring(0,nos.length-1);
	// 过滤已经选择的合同
	searchSupplierContractWin.defaultCondition = "EKKO_LIFNR='" + div_supplier_sh_sh.getValue() + "'" + "  and DEPT_ID='" + div_dept_id_sh_sh.getValue() + "' " + "and PROJECT_STATE!='9' AND CONTRACT_NO NOT IN (" + nos + ")";
	searchSupplierContractWin.show();
}

/**
 *  2.供应商退款合同搜索帮助
 */
var searchSupplierContractWin = new Ext.SearchHelpWindow({
	shlpName : 'YHCONTRAPURCHASE',
	callBack : winSupplierContractCallBack
});

/**
 *  2.供应商退款合同回调函数
 */
function winSupplierContractCallBack(jsonArrayData){
	var rate = parseFloat(Ext.getDom('SupplierRefundment.exchangerate').value);
	if ( ! rate ) {
		rate=0;
	}
	for(var i=0;i<jsonArrayData.length;i++){
		var p = new Ext.data.Record({
			paymentitemid:'',
			contract_no:jsonArrayData[i].CONTRACT_NO,
			project_no:jsonArrayData[i].PROJECT_NO,
			assignamount:0,
			istybond:'N',
			exchangerate:rate,
			unassignamount:0,
			refundmentamount:0,
			pefundmentamount:0,
			refundmentvalue:0,
			cleargoodsamount:0,
			clearprepayvalue:0,
			clearvendorvalue:0
		});
		SupplierRefundItem_grid.stopEditing();
		SupplierRefundItem_grid.getStore().insert(0, p);
		SupplierRefundItem_grid.startEditing(0, 0);
	}
}

/**
 *  3.供应商退款立项增加
 */
function _addProjSupplierRefundItem(){
	var store = SupplierRefundItem_store;
	var nos = "' ',";
	for(var i=0;i<store.getCount();i++){
		// 若本条数据的合同号为空，则将本条数据的项目号过滤掉
		if(store.getAt(i).get('paymentitemid').trim()=='' && store.getAt(i).get('contract_no').trim()==''){
			nos += "'" + store.getAt(i).get('project_no') + "',";
		}
	}
	nos = nos.substring(0,nos.length-1);
	// 过滤已经选择的项目
	searchProjectInfoWin.defaultCondition = "DEPT_ID='" + div_dept_id_sh_sh.getValue() + "' " + "and PROJECT_STATE!='9' AND PROJECT_NO NOT IN (" + nos + ")";
	searchProjectInfoWin.show();
}

/**
 *  3.供应商退款立项搜索帮助
 */
var searchProjectInfoWin = new Ext.SearchHelpWindow({
    shlpName : 'YHPROJECTINFO',
    callBack : winProjectInfoCallBack
});

/**
 *  3.供应商退款立项回调函数
 */
function winProjectInfoCallBack(jsonArrayData){
	var rate = parseFloat(Ext.getDom('SupplierRefundment.exchangerate').value);
	if ( ! rate ) {
		rate=0;
	}
	for(var i=0;i<jsonArrayData.length;i++){
		var p = new Ext.data.Record({
			paymentitemid:'',
			contract_no:'',
			project_no:jsonArrayData[i].PROJECT_NO,
			assignamount:'',
			istybond:'N',
			exchangerate:rate,
			unassignamount:0,
			refundmentamount:0,
			pefundmentamount:0,
			refundmentvalue:0,
			cleargoodsamount:0,
			clearprepayvalue:0,
			clearvendorvalue:0
		});
		SupplierRefundItem_grid.stopEditing();
		SupplierRefundItem_grid.getStore().insert(0, p);
		SupplierRefundItem_grid.startEditing(0, 0);
	}
}

/**
 *  4.供应商退款银行行项目增加
 */
function _addSupplierDbBankItem(){
	searchSupplierDbBankItemWin.show();
}

/**
 * 4.供应商退款银行行项目搜索帮助
 */
var searchSupplierDbBankItemWin = new Ext.SearchHelpWindow( {
	shlpName :'YHBANKACCOUNT',
	callBack :winBankItemAddCallBack
});

/**
 *  4.供应商退款银行行项目回调函数
 */
function winBankItemAddCallBack(jsonArrayData) {
	var ischange = false;
	var itemids = '';
	for ( var i = 0; i < jsonArrayData.length; i++) {
		var rec = {
			refundbankid :jsonArrayData[i].BANK_ID,
			refundbankname :jsonArrayData[i].BANK_NAME,
			refundbankacount :jsonArrayData[i].BANK_ACCOUNT,
			refundamount :jsonArrayData[i].REFUNDAMOUNT,
			refundamount2 :jsonArrayData[i].REFUNDAMOUNT2,
			cashflowitem :jsonArrayData[i].CASHFLOWITEM
		};
		var p = new Ext.data.Record(rec);
		var num = SupplierDbBankItem_grid.getStore().find('refundbankid',jsonArrayData[i].BANK_ID);
		//if (num == -1) {
			ischange = true;
			itemids += p.data.refundbankid + ",";
		//}
	}
//	if (ischange) {
		if (itemids != '') {
			Ext.Ajax.request( {
						url :'supplierRefundmentController.spr?action=_getBankItemGridData',
						params : {itemids :itemids},
						success : function(xhr) {
							if (xhr.responseText) {
								var jsonData = Ext.util.JSON.decode(xhr.responseText);
								for ( var j = 0; j < jsonData.data.length; j++) {
									var p = new Ext.data.Record(jsonData.data[j]);
									p.set("cashflowitem", div_cashflowitem_sh_sh.getValue()); 
									SupplierDbBankItem_grid.stopEditing();
									SupplierDbBankItem_grid.getStore().insert(0, p);
									SupplierDbBankItem_grid.startEditing(0, 0);
								}
							}
						},
						scope :this
					});
		}
//	}
}

/**
 * 保存 
 */
function _presaveOrUpdateSupplierRefundment(){
	if(!_commonVerification()){
		return false;
	}
	return true;
}

/**
 * 保存 
 */
function _postsaveOrUpdateSupplierRefundment(){}
          
/**
 * 提交
 */
function _presubmitProcessSupplierRefundment(){
	if(!_commonVerification()){
		return false;
	}
	return true ;
}

/**
 * 提交
 */
function _postsubmitProcessSupplierRefundment(){}

/**
 * 重分配提交
 * @return
 */
function _submitForReassignSupplierRefundment(){
	if(_presubmitProcessSupplierRefundment()){
		var param = Form.serialize('mainForm');	
		param = param + "&"+ Form.serialize('settleSubjectForm');
		param = param + "&"+ Form.serialize('fundFlowForm');
		param = param + "&" + getAllSupplierDbBankItemGridData();
		param = param + "&" + getAllSupplierRefundItemGridData();
		param = param + "&"+ Form.serialize('workflowForm');
		new AjaxEngine(contextPath +'/xdss3/supplierDrawback/supplierRefundmentController.spr?action=_submitForReassign', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	}
}

/**
 * 保存或提交时通用的验证
 */
function _commonVerification(){
	/*
	 * 1.检查申请退款金额与退款银行中的金额是否相等
	 *
	var refuAmont = 0;		// 退款行项的总退款金额
	var bankAmout = 0;		// 退款银行的总退款金额
	for(var i=0;i<SupplierRefundItem_store.getCount();i++){
		refuAmont += parseFloat(SupplierRefundItem_store.getAt(i).get('refundmentamount'));		
	}
	for(var i=0;i<SupplierDbBankItem_store.getCount();i++){
		bankAmout += parseFloat(SupplierDbBankItem_store.getAt(i).get('refundamount'));			
	}
	if(round(refuAmont,2) != round(bankAmout,2)){
		_getMainFrame().showInfo('退款行项的总退款金额必须与退款银行的总退款金额相等！');
		return false;
	}
	*/
	
	/*
	 * 2.检查退款行项的汇率是否为空
	 * 6.检查实际退款金额不能大于退款金额
	 */
	for(var i=0;i<SupplierRefundItem_store.getCount();i++){
		var rate = SupplierRefundItem_store.getAt(i).get('exchangerate');
		var unassignamount = SupplierRefundItem_store.getAt(i).get('unassignamount');
		var pefundmentamount = SupplierRefundItem_store.getAt(i).get('pefundmentamount');
		var paymentitemid = SupplierRefundItem_store.getAt(i).get('paymentitemid');
		// 添加判断是否添加行立项
		if( (rate=='0' || rate==' ' || rate=='') && paymentitemid.trim()!='' ){
			_getMainFrame().showInfo('退款行项中存在汇率为0或为空的数据，请检查并填写汇率！');
			return false;
		}
		if ( parseFloat(pefundmentamount) > parseFloat(unassignamount) && paymentitemid.trim()!='' ) {
			_getMainFrame().showInfo('[实际退款金额]不能大于[可退金额]！');
			return false;
		}
		if (parseFloat(unassignamount)<1 && paymentitemid.trim()!='' ) {
			_getMainFrame().showInfo('退款行项中存在[可退金额]为0或为空的数据，请检查删除！');
			return false;
		}
		if (parseFloat(pefundmentamount) < 1 && paymentitemid.trim()!='' ) {
			_getMainFrame().showInfo('退款行项中存在[实际退款金额]为0或为空的数据，请检查删除！');
			return false;
		}
	}
	
	/*
	 * 3.检查合同、立项币别
	 */
	var refundcurrency = Ext.getDom('SupplierRefundment.refundcurrency').value; //抬头－退款币别
	var store = SupplierRefundItem_store;
	for(var i=0;i<store.getCount();i++){
		// 合同、立项两类不做提示
		if ( (store.getAt(i).get('paymentitemid')!='' && store.getAt(i).get('paymentitemid')!=' ') && (store.getAt(i).get('currency') != refundcurrency) ){
			if ( ! window.confirm('[退款币别]与[原收款单币别]不一致，请确认[原收款单币别]是否正确!') ){
				return false;
			}
		}
	}
	
	/*
	 * 4.检查财务节点抬头汇率是否必填
	 */
	if(ps == '财务会计审核并做账'){
		var rate = Ext.getDom('SupplierRefundment.exchangerate').value
		if(rate=='0' || rate==' ' || rate==''){
			_getMainFrame().showInfo('退款抬头中存在汇率为0或为空的数据，请检查并填写汇率！');
			return false;
		}
	}
	
	/*
	 * 5.业务发起时就判断　是否添加银行项
	 * */
	if (SupplierDbBankItem_store.getCount() < 1) {
		_getMainFrame().showInfo('请添加[退款银行]');
		return false;
	}
	
	return true;
}

