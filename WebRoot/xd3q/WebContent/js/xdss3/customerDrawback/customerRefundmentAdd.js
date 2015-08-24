/**
 * Author(s):java业务平台代码生成工具
 * Date: 2010年06月30日 11点22分10秒
 * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 * @(#)
 * Description:  
 * <功能>主对象客户退款(CustomerRefundment)增加页面用户可编程扩展JS文件
 */
 
 /**
 * 当前流程状态 在onReady方法中赋值
 */          
var ps = "";

Ext.onReady(function(){
	var maintab = Ext.getCmp("tabs");
	maintab.hideTabStripItem('settleSubjectTab');
	maintab.hideTabStripItem('fundFlowTab');
	ps = Ext.getDom('CustomerRefundment.processstate').value;
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
			Ext.getDom('CustomerRefundment.exchangerate').value = 1;
		}else{
			Ext.getDom('CustomerRefundment.exchangerate').value = '';
		}
	});
	/*
	 * 汇率失去焦点的时候计算退款银行行项的金额
	 */
	Ext.getDom('CustomerRefundment.exchangerate').onblur = function(){
		var rate = parseFloat(Ext.getDom('CustomerRefundment.exchangerate').value);
		if(Utils.isNumber(rate)){
			var store = CustomerDbBankItem_store;
			for(var i=0;i<store.getCount();i++){
				// 计算"退款金额（本位币）"
				store.getAt(i).set('refundamount2', round((rate * store.getAt(i).get('refundamount')),2));
			}
			var itemStore = CustomerRefundItem_store; // 根据汇率计算退款行项中立项、合同 "退款金额（本位币）"
			for(var i=0;i<itemStore.getCount();i++){
				itemStore.getAt(i).set('refundmentvalue', round((rate * itemStore.getAt(i).get('pefundmentamount')),2));
			}
			CustomerDbBankItem_grid.recalculation();
		}
	}
	/*
	 * 设置客户退款行项目编辑后触发
	 */
	CustomerRefundItem_grid.on("afterEdit", afterEditForRefundItem, CustomerRefundItem_grid);
	function afterEditForRefundItem(e) {
		var record = e.record;							// 被编辑的记录
		var exchangerate = record.get("exchangerate"); 	// 收款单对应汇率
		// 若修改的字段为"实际退款金额"、"汇率"
		if (e.field == 'pefundmentamount' || e.field == 'exchangerate') {
			if(record.get("collectitemid").trim() != ''){
				// 将"退款金额"自动带到"清原币金额"
				record.set('refundmentamount', round(record.get("pefundmentamount"),2));
			}
			// 退款金额(本位币) = 实际退款金额 * 汇率
			record.set('refundmentvalue',  round((exchangerate * record.get("pefundmentamount")),2));
			var unassignamount = record.get("unassignamount");			// 可退金额
			var refundmentamount = record.get("refundmentamount");		// 清原币金额
			var boId = div_customer_sh_sh.getValue();					// 客户编号	
			projectnos = record.get('project_no');						// 当前记录的项目号
			var param = 'boid=' + boId + '&projectnos=' + projectnos;
			new AjaxEngine(contextPath + '/xdss3/collect/collectController.spr?action=checkProjCreditType', {
				method:"post", 
				parameters:param, 
				onComplete:function(transport){
					var promptMessagebox = new MessageBoxUtil();
					promptMessagebox.Close();
					var responseUtil = new AjaxResponseUtils(transport.responseText);
					var returnParam = responseUtil.getCustomField("coustom");
					var creditType = returnParam.credittypes;	// 授信类型
					if(creditType.indexOf('无',0)!= -1){			// 无授信
					}else if(creditType.indexOf('放货',0)!= -1){		// 客户放货
						record.set('cleargoodsamount', round((exchangerate * record.get("pefundmentamount")),2));
					}else if(creditType.indexOf('代垫',0)!= -1){		// 客户代垫
						record.set('clearprepayvalue', round((exchangerate * record.get("pefundmentamount")),2));
					}else if(creditType=='3'){		// 客户放货+代垫
						record.set('cleargoodsamount', round((exchangerate * record.get("pefundmentamount")),2));
						record.set('clearprepayvalue', round((exchangerate * record.get("pefundmentamount")),2));
					}else if(creditType.indexOf('供应商授信',0)!= -1){		// 供应商授信
						record.set('clearvendorvalue', round((exchangerate * record.get("pefundmentamount")),2));
					}
				}, 
				callback:''});
		}
		CustomerRefundItem_grid.recalculation();
	}
	/*
	 * 设置客户退款银行编辑后触发
	 */
    CustomerDbBankItem_grid.on("afterEdit", afterEditForBankItem, CustomerDbBankItem_grid);
    function afterEditForBankItem(e) {
    	var record = e.record;// 被编辑的记录
    	// 修改退款金额
    	var exchangerate = parseFloat(Ext.getDom('CustomerRefundment.exchangerate').value);
    	if (e.field == 'refundamount' && Utils.isNumber(exchangerate)) {
    		record.set('refundamount2', round((exchangerate * record.get("refundamount")),2));
    	}
    	CustomerDbBankItem_grid.recalculation();
    }
    
    /*
     * 同步指定客户的未清据
     */
	div_customer_sh_sh.callBack = function(data){
		Ext.Ajax.request({
	    	url : contextPath+'/xdss3/collect/collectController.spr?action=syncUnclearCustomer',
	        params : {customer:data.KUNNR},
	        success : function(xhr){
	        },
	        scope : this
	    });
	};
	
	/*
	 * 将付款金额分配的合同号与立项号渲染成链接形式，并在点击时弹出出详情查看窗口
	 */
	var contIndex = CustomerRefundItem_grid.getColumnModel().findColumnIndex('contract_no');
	var projIndex = CustomerRefundItem_grid.getColumnModel().findColumnIndex('project_no');
	CustomerRefundItem_grid.getColumnModel().setRenderer(contIndex,function(contNo){
		return '<a href="#" onclick="viewContractInfo(\''+contNo+'\');"><u style="border-bottom:1px;">'+contNo+'</u></a>';
	});
	CustomerRefundItem_grid.getColumnModel().setRenderer(projIndex,function(projNo){
		return '<a href="#" onclick="viewProjectInfo(\''+projNo+'\');"><u style="border-bottom:1px;">'+projNo+'</u></a>';
	});
	
	
});


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
 * 1.客户退款行项目增加
 */
function _addCustomerRefundItem() {
	if (div_customer_sh_sh.getValue() == '') {
		_getMainFrame().showInfo('请先选择客户！');
		return;
	}
	searchCustomerRefundItemWin.defaultCondition = "(ISCLEAR=' ' OR ISCLEAR='0') AND (BUSINESSSTATE = '3' OR (COLLECTTYPE IN ('11', '12') AND EXISTS(SELECT 'x' FROM YVOUCHERITEM VI WHERE VI.BUSINESSITEMID = COLLECTITEMID)) ) AND DEPT_ID='"+div_dept_id_sh_sh.getValue()+"' AND CUSTOMER = '" + div_customer_sh_sh.getValue() + "'";
	searchCustomerRefundItemWin.show();
}

/**
 * 1.客户退款行项目搜索帮助
 */
var searchCustomerRefundItemWin = new Ext.SearchHelpWindow({
	shlpName : 'YHCUSTOMERDRAWBACK',
	callBack : winRefundItemAddCallBack
});

/**
 * 1.客户退款行项目回调函数
 */
function winRefundItemAddCallBack(jsonArrayData) {
	var ischange = false;
	var itemids = '';
	var varcollectType = '';
	for ( var i = 0; i < jsonArrayData.length; i++) {
		var rec = {
			collectitemid 	: jsonArrayData[i].COLLECTITEMID,
			collectno 		: jsonArrayData[i].COLLECTNO,
			contract_no 	: jsonArrayData[i].CONTRACT_NO,
			project_no 		: jsonArrayData[i].PROJECT_NO,
			assignamount 	: jsonArrayData[i].ASSIGNAMOUNT,
			currency 		: jsonArrayData[i].BILLCURRENCY,
			refundcurrency 	: jsonArrayData[i].REFUNDCURRENCY,
			collectid 		: jsonArrayData[i].COLLECTID,
			collectType 	: jsonArrayData[i].TRADE_TYPE
		};
		var p = new Ext.data.Record(rec);
		var num = CustomerRefundItem_grid.getStore().find('collectitemid',jsonArrayData[i].COLLECTITEMID);
		if (num == -1) {
			ischange = true;
			itemids += p.data.collectitemid + ",";
		}
	}
	if (ischange) {
		if (itemids != '') {
			Ext.Ajax.request( {
						url : 'customerRefundmentController.spr?action=_getRefundmentItemGridData',
						params : {itemids : itemids},
						success : function(xhr) {
							if (xhr.responseText) {
								var jsonData = Ext.util.JSON.decode(xhr.responseText);
								for ( var j = 0; j < jsonData.data.length; j++) {
									var p = new Ext.data.Record(jsonData.data[j]);
									CustomerRefundItem_grid.stopEditing();
									CustomerRefundItem_grid.getStore().insert(0, p);
									CustomerRefundItem_grid.startEditing(0, 0);
								}
							}
						},
						scope : this
					});
		}
	}
}

/**
 * 2.客户退款合同增加
 */
function _addContCustomerRefundItem(){
	if (div_customer_sh_sh.getValue() == '') {
		_getMainFrame().showInfo('请先选择客户！');
		return;
	}
	var store = CustomerRefundItem_store;
	var nos = "' ',";
	for(var i=0;i<store.getCount();i++){
		// 若本条数据的收款号为空，则将本条数据的合同号过滤掉
		if(store.getAt(i).get('collectitemid').trim()=='' && store.getAt(i).get('contract_no').trim()!=''){
			nos += "'" + store.getAt(i).get('contract_no') + "',";
		}
	}
	nos = nos.substring(0,nos.length-1);
	// 过滤已经选择的合同
	searchCustomerContractWin.defaultCondition = "KUAGV_KUNNR='"+div_customer_sh_sh.getValue()+"'" + " and DEPT_ID='" + div_dept_id_sh_sh.getValue() + "' AND CONTRACT_NO NOT IN (" + nos + ")";
	searchCustomerContractWin.show();
}

/**
 * 2.客户退款合同搜索帮助
 */
var searchCustomerContractWin = new Ext.SearchHelpWindow( {
	shlpName : 'YHCONTRASALES',
	callBack : winCustomerContractCallBack
});

/**
 * 2.客户退款合同回调函数
 */
function winCustomerContractCallBack(jsonArrayData){

	var contractnos = '';				// 合同号
	var projectnos = '';				// 立项号
	var rate = parseFloat(Ext.getDom('CustomerRefundment.exchangerate').value); // 抬头汇率
	if (!rate) {
		rate=0;
	}
	var contAmountArray = new Array();	// 合同金额数组
	for(var i=0;i<jsonArrayData.length;i++){
		contractnos += jsonArrayData[i].CONTRACT_NO + ",";
		projectnos += jsonArrayData[i].VBAK_BNAME + ",";
		contAmountArray.push(jsonArrayData[i].ORDER_TOTAL);
	}
	if(projectnos != ''){
		var boId = div_customer_sh_sh.getValue();		
		projectnos = projectnos.substring(0, projectnos.length-1);
		contractnos = contractnos.substring(0,contractnos.length-1);
		var param = 'boid=' + boId + '&projectnos=' + projectnos;
		new AjaxEngine(contextPath + '/xdss3/collect/collectController.spr?action=checkProjCreditType', {
			method:"post", 
			parameters:param, 
			onComplete:function(transport){
				var promptMessagebox = new MessageBoxUtil();
				promptMessagebox.Close();
				var responseUtil = new AjaxResponseUtils(transport.responseText);
				var returnParam = responseUtil.getCustomField("coustom");
				var creditTypes = returnParam.credittypes.split(',');	// 授信类型
				var contractArray = contractnos.split(',');				// 合同编号
				var projectArray= projectnos.split(',');				// 项目编号
				for(var i=0;i<creditTypes.length;i++){
//					var creditType = '';
//					if(creditTypes[i]=='0'){
//						creditType = '无';
//					}else if(creditTypes[i]=='1'){
//						creditType = '客户放货';
//					}else if(creditTypes[i]=='2'){
//						creditType = '客户代垫';
//					}else if(creditTypes[i]=='3'){
//						creditType = '客户放货+代垫';
//					}else if(creditTypes[i]=='4'){
//						creditType = '供应商授信';
//					}
					var p = new Ext.data.Record({
						collectitemid:'',
						contract_no:contractArray[i],
						project_no:projectArray[i],
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
					CustomerRefundItem_grid.stopEditing();
					CustomerRefundItem_grid.getStore().insert(0, p);
					CustomerRefundItem_grid.startEditing(0, 0);
				}
			}, 
			callback:''});
	}
	return;
}


/**
 * 3.客户退款立项增加
 */
function _addProjCustomerRefundItem(){
	var store = CustomerRefundItem_store;
	var nos = "' ',";
	for(var i=0;i<store.getCount();i++){
		// 若本条数据的合同号为空，则将本条数据的项目号过滤掉
		if(store.getAt(i).get('collectitemid').trim()=='' && store.getAt(i).get('contract_no').trim()==''){
			nos += "'" + store.getAt(i).get('project_no') + "',";
		}
	}
	nos = nos.substring(0,nos.length-1);
	// 过滤已经选择的项目
	searchProjectInfoWin.defaultCondition = "DEPT_ID='" + div_dept_id_sh_sh.getValue() + "' " + "and PROJECT_STATE!='9' AND PROJECT_NO NOT IN (" + nos + ")";
	searchProjectInfoWin.show();
}

/**
 * 3.客户退款立项搜索帮助
 */
var searchProjectInfoWin = new Ext.SearchHelpWindow({
    shlpName : 'YHXDPROJECTINFO',
    callBack : winProjectInfoCallBack
});

/**
 * 3.客户退款立项回调函数
 */
function winProjectInfoCallBack(jsonArrayData){
	var projectnos = '';
	var rate = parseFloat(Ext.getDom('CustomerRefundment.exchangerate').value); // 抬头汇率
	if (!rate) {
		rate=0;
	}
	for(var i=0;i<jsonArrayData.length;i++){
		projectnos += jsonArrayData[i].PROJECT_NO + ",";
	}
	if(projectnos!=''){
		projectnos = projectnos.substring(0, projectnos.length-1);
		var projectArray= projectnos.split(',');		// 项目编号
		for(var i=0;i<projectArray.length;i++){
			var p = new Ext.data.Record({
				collectitemid:'',
				contract_no:'',
				project_no:projectArray[i],
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
			CustomerRefundItem_grid.stopEditing();
			CustomerRefundItem_grid.getStore().insert(0, p);
			CustomerRefundItem_grid.startEditing(0, 0);
		}
	}
}

/**
 * 4.客户退款银行行项目增加
 */
function _addCustomerDbBankItem() {
	searchCustomerDbBankItemWin.show();
}

/**
 * 4.客户退款银行行项目搜索帮助
 */
var searchCustomerDbBankItemWin = new Ext.SearchHelpWindow( {
	shlpName : 'YHBANKACCOUNT',
	callBack : winBankItemAddCallBack
});

/**
 * 客户退款银行行项目回调函数
 */
function winBankItemAddCallBack(jsonArrayData) {
	var ischange = false;
	var itemids = '';
	for ( var i = 0; i < jsonArrayData.length; i++) {
		var rec = {
			refundbankid 	: jsonArrayData[i].BANK_ID,
			refundbankname 	: jsonArrayData[i].BANK_NAME,
			refundbankacount: jsonArrayData[i].BANK_ACCOUNT,
			refundamount 	: jsonArrayData[i].REFUNDAMOUNT,
			refundamount2 	: jsonArrayData[i].REFUNDAMOUNT2,
			cashflowitem 	: jsonArrayData[i].CASHFLOWITEM
		};
		var p = new Ext.data.Record(rec);
		var num = CustomerDbBankItem_grid.getStore().find('refundbankid',jsonArrayData[i].BANK_ID);
		//if (num == -1) {
			ischange = true;
			itemids += p.data.refundbankid + ",";
		//}
	}
//	if (ischange) {
		if (itemids != '') {
			Ext.Ajax.request( {
						url : 'customerRefundmentController.spr?action=_getBankItemGridData',
						params : {itemids : itemids},
						success : function(xhr) {
							if (xhr.responseText) {
								var jsonData = Ext.util.JSON.decode(xhr.responseText);
								for ( var j = 0; j < jsonData.data.length; j++) {
									var p = new Ext.data.Record(jsonData.data[j]);
									p.set("cashflowitem",div_cashflowitem_sh_sh.getValue());
									CustomerDbBankItem_grid.stopEditing();
									CustomerDbBankItem_grid.getStore().insert(0, p);
									CustomerDbBankItem_grid.startEditing(0, 0);
								}
							}
						},
						scope : this
					});
		}
//	}
}

/**
 * 保存
 */
function _presaveOrUpdateCustomerRefundment() {
	if(!_commonVerification()){
		return false;
	}
	return true;
}

/**
 * 保存
 */
function _postsaveOrUpdateCustomerRefundment() {}

/**
 * 提交
 */
function _presubmitProcessCustomerRefundment() {
	if(!_commonVerification()){
		return false;
	}
	return true;
}

/**
 * 提交
 */
function _postsubmitProcessCustomerRefundment() {}

/**
 * 重分配提交
 */
function _submitForReassignCustomerRefundment(){
	if(_presubmitProcessCustomerRefundment()){
		var param = Form.serialize('mainForm');	
		param = param + "&"+ Form.serialize('settleSubjectForm');
		param = param + "&"+ Form.serialize('fundFlowForm');
		param = param + "&" + getAllCustomerDbBankItemGridData();
		param = param + "&" + getAllCustomerRefundItemGridData();
		param = param + "&" + Form.serialize('workflowForm');
		new AjaxEngine(
				contextPath + '/xdss3/customerDrawback/customerRefundmentController.spr?action=_submitForReassign',
				{
					method : "post",
					parameters : param,
					onComplete : callBackHandle,
					callback : _submitCallBackHandle
				});
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
	for(var i=0;i<CustomerRefundItem_store.getCount();i++){
		refuAmont += parseFloat(CustomerRefundItem_store.getAt(i).get('refundmentamount'));		
	}
	for(var i=0;i<CustomerDbBankItem_store.getCount();i++){
		bankAmout += parseFloat(CustomerDbBankItem_store.getAt(i).get('refundamount'));			
	}
	if(round(refuAmont,2) != round(bankAmout,2)){
		_getMainFrame().showInfo('退款行项的总退款金额必须与退款银行的总退款金额相等！');
		return false;
	}
	*/
	
	/*
	 * 检查退款行项目 是否填写
	 */
	 if (CustomerRefundItem_store.getCount() < 1) {
	 	_getMainFrame().showInfo('请添加 [客户退款行项目] 记录！');
	 	return false;
	 }
	 
	/*
	 * 2.检查退款行项的汇率是否为空
	 * 6.检查实际退款金额不能大于退款金额
	 */
	for(var i=0;i<CustomerRefundItem_store.getCount();i++){
		var rate = CustomerRefundItem_store.getAt(i).get('exchangerate');
		if(rate=='0' || rate==' ' || rate==''){
			_getMainFrame().showInfo('退款行项中存在汇率为0或为空的数据，请检查并填写汇率！');
			return false;
		}
		var unassignamount = CustomerRefundItem_store.getAt(i).get('unassignamount');
		var pefundmentamount = CustomerRefundItem_store.getAt(i).get('pefundmentamount');
		var collectid = CustomerRefundItem_store.getAt(i).get('collectid');
		if ( parseFloat(pefundmentamount) > parseFloat(unassignamount) && collectid != '') {
			_getMainFrame().showInfo('[实际退款金额]不能大于[可退金额]！');
			return false;
		}
		if (parseFloat(unassignamount)<=0 && collectid != '') {
			_getMainFrame().showInfo('退款行项中存在[可退金额]为0或为空的数据，请检查删除！');
			return false;
		}
		if (parseFloat(pefundmentamount) <=0 && collectid != '') {
			_getMainFrame().showInfo('退款行项中存在[实际退款金额]为0或为空的数据，请检查删除！');
			return false;
		}
	}
	
	/*
	 * 3.检查合同、立项币别
	 */
	var refundcurrency = Ext.getDom('CustomerRefundment.refundcurrency').value; //抬头－退款币别
	var store = CustomerRefundItem_store;
	for(var i=0;i<store.getCount();i++){
		// 合同、立项两类不做提示
		if ( (store.getAt(i).get('collectitemid')!='' && store.getAt(i).get('collectitemid')!=' ') && (store.getAt(i).get('currency') != refundcurrency) ){
			if ( ! window.confirm('[退款币别]与[原收款单币别]不一致，请确认[原收款单币别]是否正确!') ){
				return false;
			}
		}
	}
	
	/*
	 * 4.检查财务节点抬头汇率是否必填
	 */
	if(ps == '财务会计审核并做账'){
		var rate = Ext.getDom('CustomerRefundment.exchangerate').value
		if(rate=='0' || rate==' ' || rate==''){
			_getMainFrame().showInfo('退款抬头中存在汇率为0或为空的数据，请检查并填写汇率！');
			return false;
		}
	}
	
	/*
	 * 5.检查收款银行、收款银行帐号是否为空
	 */
	var collectbankname = Ext.getDom('CustomerRefundment.collectbankname').value
	if( collectbankname.trim() == '' ){
		_getMainFrame().showInfo('[收款银行]不能为空！');
		return false;
	}
	var collectbankacc = Ext.getDom('CustomerRefundment.collectbankacc').value
	if( collectbankacc.trim() == '' ){
		_getMainFrame().showInfo('[收款银行帐号]不能为空！');
		return false;
	}
	
	return true;
}