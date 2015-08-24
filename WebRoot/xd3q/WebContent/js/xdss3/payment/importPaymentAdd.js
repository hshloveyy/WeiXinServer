/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年05月27日 19点36分16秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象进口付款(ImportPayment)增加页面用户可编程扩展JS文件
 */

Ext.onReady(function(){
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-16
	 * 隐藏所有页签的"增加行"、"删除行"按钮
	 */
	/******************************************************************************/
	var toolbar01 = ImportPaymentItem_grid.getTopToolbar();
	toolbar01.items.get('ImportPaymentItemaddRow').hide();			// 隐藏"增加行"按钮
	toolbar01.items.get('ImportPaymentItemdeleteRow').hide();		// 隐藏"删除行"按钮
	var toolbar02 = ImportPaymentCbill_grid.getTopToolbar();
	toolbar02.items.get('ImportPaymentCbilladdRow').hide();			// 隐藏"增加行"按钮
	toolbar02.items.get('ImportPaymentCbilldeleteRow').hide();		// 隐藏"删除行"按钮
	var toolbar03 = ImportPayBankItem_grid.getTopToolbar();
	toolbar03.items.get('ImportPayBankItemaddRow').hide();			// 隐藏"增加行"按钮
	toolbar03.items.get('ImportPayBankItemdeleteRow').hide();		// 隐藏"删除行"按钮
	var toolbar04 = ImportDocuBankItem_grid.getTopToolbar();
	toolbar04.items.get('ImportDocuBankItemaddRow').hide();			// 隐藏"增加行"按钮
	toolbar04.items.get('ImportDocuBankItemdeleteRow').hide();		// 隐藏"删除行"按钮
	var toolbar05 = ImportRelatPayment_grid.getTopToolbar();
	toolbar05.items.get('ImportRelatPaymentaddRow').hide();			// 隐藏"增加行"按钮
	toolbar05.items.get('ImportRelatPaymentdeleteRow').hide();		// 隐藏"删除行"按钮
	var toolbar06 = BillPayReBankItem_grid.getTopToolbar();
	toolbar06.items.get('BillPayReBankItemaddRow').hide();			// 隐藏"增加行"按钮
	toolbar06.items.get('BillPayReBankItemdeleteRow').hide();		// 隐藏"删除行"按钮
	/******************************************************************************/
	
	/**
	 * @创建作者：yanghancai
	 * @创建日期：2010-10-20
	 * 根据当前流程步骤隐藏相关按钮
	 */
	var taskName=Ext.getDom("workflowCurrentTaskName").value;
	if (taskName.substring(0,6) == '业务部重分配'){
		Ext.getCmp("_bookofaccount").hide();
		Ext.getCmp("_simulate").hide();
		Ext.getCmp("_submitProcess").hide();
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-08
	 * 申请界面时，直接将押汇日、押汇利率、押汇汇率、押汇到期付款日等设为不可填
	 * （备注：等虎哥修改完日期控制后恢复以下三条注释）
	 */
	/***********************************************************************/
//	calendar_ImportPayment_documentarydate.setEditable(false);		// 押汇日（日期型）
	Ext.getDom('ImportPayment.doctaryinterest').readOnly = true;	// 押汇利率
	Ext.getDom('ImportPayment.documentaryrate').readOnly = true;	// 押汇汇率
//	calendar_ImportPayment_documentarypaydt.setEditable(false);		// 押汇到期付款日（日期型）
	calendar_ImportPayment_payrealdate.setEditable(false);			// 实际付款日期
	Ext.getDom('ImportPayment.redocaryamount').readOnly = true;		// 还押汇金额
	Ext.getDom('ImportPayment.redocaryrate').readOnly = true;		// 还押汇汇率
	/***********************************************************************/
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-01
	 * 在财务界面时，自动计算"付款金额分配"与"付款银行"页签下的分配金额与付款金额
	 * ------------------------------ 修改记录 ------------------------------
	 */
	/***********************************************************************/
	/*
	 * 付款金额分配页签
	 */
	ImportPaymentItem_grid.on('afteredit', calcAssignamount, ImportPaymentItem_grid);
	function calcAssignamount(e){
		var cRecord = e.record;
		var cField  = e.field;
		var settleRate = Ext.getDom('ImportPayment.closeexchangerat').value;		// 结算汇率
		if(cField == 'assignamount' && Utils.isNumber(settleRate)){									
			var assignAmount = cRecord.get(cField);									// 分配金额
			cRecord.set('assignamount2', round((assignAmount * settleRate),2));		// 分配金额（本位币）
			cRecord.set('prepayamount',cRecord.get('assignamount'));
		}
	}
	
	/*
	 * 付款银行页签
	 */
	ImportPayBankItem_grid.on('afteredit', calcPayAmount, ImportPayBankItem_grid);
	function calcPayAmount(e){
		var cRecord = e.record;
		var cField  = e.field;
		var payRate = Ext.getDom('ImportPayment.exchangerate').value;				// 付款汇率
		if(cField == 'payamount' && Utils.isNumber(payRate)){					
			var payAmount = cRecord.get(cField);									// 付款金额
			cRecord.set('payamount2', round((payAmount * payRate),2));			// 付款金额(本位币)
		}
	}
	
	/*
	 * 将付款金额分配的合同号、立项号与到单号渲染成链接形式，并在点击时弹出出详情查看窗口xx
	 */
	var contIndex = ImportPaymentItem_grid.getColumnModel().findColumnIndex('contract_no');
	var projIndex = ImportPaymentItem_grid.getColumnModel().findColumnIndex('project_no');
	var pickIndex = ImportPaymentItem_grid.getColumnModel().findColumnIndex('pick_list_no_text');
	var relaIndex = ImportRelatPayment_grid.getColumnModel().findColumnIndex('relatedno');
	ImportPaymentItem_grid.getColumnModel().setRenderer(contIndex,function(contNo){
		return '<a href="#" onclick="viewContractInfo(\''+contNo+'\');"><u style="border-bottom:1px;">'+contNo+'</u></a>';
	});
	ImportPaymentItem_grid.getColumnModel().setRenderer(projIndex,function(projNo){
		return '<a href="#" onclick="viewProjectInfo(\''+projNo+'\');"><u style="border-bottom:1px;">'+projNo+'</u></a>';
	});
	ImportPaymentItem_grid.getColumnModel().setRenderer(pickIndex,function(pickNo, metadata, record){
		if(record.data.pick_list_no.trim()==''){
			return '';
		}else{
			return '<a href="#" onclick="viewPickListInfo(\''+record.data.pick_list_no+'\');"><u style="border-bottom:1px;">'+pickNo+'</u></a>';
		}
	});
	ImportRelatPayment_grid.getColumnModel().setRenderer(relaIndex,function(relaNo){
		return '<a href="#" onclick="viewRelatedInfo(\''+relaNo+'\');"><u style="border-bottom:1px;">'+relaNo+'</u></a>';
	});
	/***********************************************************************/
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-06
	 * 付款金额分配页签在申请页面中，如果是非预付款时只需要显示"增加到单"及"取消"按钮；
	 * 如果预付款，显示"增加合同"、"增加立项"、"取消"按钮
	 * ------------------------------ 修改记录 ------------------------------
	 * 邱杰烜 2010-09-27 若付款类型为"押汇"，将部门信息带到押汇业务范围里
	 */
	/***********************************************************************/
	var ipItemToolbar = ImportPaymentItem_grid.getTopToolbar();
	if(dict_div_paymenttype_dict.getValue()!='14'){				// 若为预付款
		ipItemToolbar.items.get('ImportPaymentItem._addProject').hide();	// 隐藏"增加立项"按钮
		ipItemToolbar.items.get('ImportPaymentItem._addPurchase').hide();	// 隐藏"增加合同"按钮
	}else{
		ipItemToolbar.items.get('ImportPaymentItem._addPlick').hide();		// 隐藏"增加到单"按钮
	}
	/*
	 * 若付款类型为"押汇"，将部门信息带到押汇业务范围里
	 */
	dict_div_pay_type_dict.on('select',function(){
		if(dict_div_pay_type_dict.getValue()=='2'){		// 若付款类型为"押汇"
			var param = 'deptid=' + div_dept_id_sh_sh.getValue();
			new AjaxEngine(contextPath + '/xdss3/payment/importPaymentController.spr?action=getRedocarybcByDeptID', {
				method:"post", 
				parameters:param, 
				onComplete:function(transport){
				var promptMessagebox = new MessageBoxUtil();
				promptMessagebox.Close();
				var responseUtil = new AjaxResponseUtils(transport.responseText);
				var returnParam = responseUtil.getCustomField("coustom");
				var redocarybc = returnParam.redocarybc;	// 获取押汇业务范围
				div_redocarybc_sh_sh.setValue(redocarybc);	// 设置押汇业务范围
			}, 
			callback:''});
		}else{
			div_redocarybc_sh_sh.setValue('');				// 清空押汇业务范围
		}
	});
	/***********************************************************************/

	div_supplier_sh_sh.on("change",function(){
		// 若为预付款才去取银行
		if(dict_div_paymenttype_dict.getValue()=='14'){
			var supplierid = div_supplier_sh_sh.getValue();
			Ext.Ajax.request({
				url : contextPath + '/xdss3/payment/importPaymentController.spr?action=getSupplierByID&supplierid=' + supplierid,
				success : function(xhr){
				if(xhr.responseText){
					var jsonData = Ext.util.JSON.decode(xhr.responseText);
					document.getElementById('ImportPayment.collectbankid').value = jsonData.collectbankid;
					document.getElementById('ImportPayment.collectbankacc').value = jsonData.collectbankacc;
				}
			},
			scope : this
			});
		}
	});

	dict_div_paymenttype_dict.readOnly = true;
	
	dict_div_isrepresentpay_dict.on("change",function(){

		if (dict_div_isrepresentpay_dict.getValue() == '1'){
			var maintab = Ext.getCmp("tabs");
			maintab.getItem('importPaymentCbillTab').disable();
	
			Ext.getCmp('_autoassign').disable();
			Ext.getCmp('_clearassign').disable();
		}else{
			var maintab = Ext.getCmp("tabs");
			maintab.getItem('importPaymentCbillTab').enable();
	
			Ext.getCmp('_autoassign').enable();
			Ext.getCmp('_clearassign').enable();
		}
		
	});

	dict_div_isoverrepay_dict.on("change",function(){
		if (dict_div_isoverrepay_dict.getValue() == '1'){
			dict_div_pay_type_dict.setValue('2');
			//dict_div_pay_type_dict.disable();
		}else{
			dict_div_pay_type_dict.clearValue();
			dict_div_pay_type_dict.enable();
		}
	});
	
	mainInit();
	
	div_supplier_sh_sh.callBack=function(data){
		//supplierHelpCallBack(data);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-15
	 * 加入申请金额失去焦点且付款方式为CNY时触发的功能
	 * --------------------------------- 修改记录 ----------------------------------
	 * 邱杰烜 2010-11-02 改成change事件
	 * 
	 */
	/******************************************************************************/
	var index = 0;
	div_currency_sh_sh.on('change',function(e,n,o){
		if(index != 0){
			div_factcurrency_sh_sh.setValue(this.getValue());
			if(this.getValue()=='CNY'){
				Ext.getDom('ImportPayment.factamount').value = Ext.getDom('ImportPayment.applyamount').value;
			}
		}else{
			++index;
		}
	});
	// 申请金额失去焦点且付款方式为CNY时触发
	Ext.getDom('ImportPayment.applyamount').onblur = function(){
		if(div_currency_sh_sh.getValue()=='CNY'){
			// 将申请金额填入实际付款金额
			Ext.getDom('ImportPayment.factamount').value = this.value;
		}
	}
	/******************************************************************************/
	
	var ImportPayment_exchangerate = document.getElementById("ImportPayment.exchangerate");
	
	ImportPayment_exchangerate.onblur = function exchangerate(){    
		for (var j = 0;j<ImportPayBankItem_store.getCount();j++){
			var record = ImportPayBankItem_grid.getStore().getAt(j);
			
			var exchangerate = 0;
			if (Utils.isEmpty(document.getElementById('ImportPayment.exchangerate').value) == true){
    			exchangerate = 0;
            }else{
            	exchangerate = document.getElementById('ImportPayment.exchangerate').value;
            }
			 
			var payamount2 = ImportPayBankItem_store.getAt(j).get('payamount');
			if (Utils.isNumber(ImportPayBankItem_store.getAt(j).get('payamount')) == false){
				payamount2 = 0;
            }else{
            	payamount2 = ImportPayBankItem_store.getAt(j).get('payamount');
            }
			
            var mulValue = parseFloat(payamount2) * parseFloat(exchangerate);
			record.set('payamount2',round(mulValue,2));
		}
	}
	
	var ImportPayment_doctaryrealrate = document.getElementById("ImportPayment.doctaryrealrate");
	
	ImportPayment_doctaryrealrate.onblur = function doctaryrealrate(){    
		for (var j = 0;j<ImportDocuBankItem_store.getCount();j++){
			var record = ImportDocuBankItem_grid.getStore().getAt(j);
			
			var exchangerate = 0;
			if (Utils.isEmpty(document.getElementById('ImportPayment.doctaryrealrate').value) == true){
    			exchangerate = 0;
            }else{
            	exchangerate = document.getElementById('ImportPayment.doctaryrealrate').value;
            }
			 
			var payamount2 = 0;
			if (Utils.isNumber(ImportDocuBankItem_store.getAt(j).get('docuarypayamount')) == false){
				payamount2 = 0;
            }else{
            	payamount2 = ImportDocuBankItem_store.getAt(j).get('docuarypayamount');
            }
			
			var mulValue = parseFloat(payamount2) * parseFloat(exchangerate);
			record.set('docuarypayamoun2',round(mulValue,2));
		}
	}

	var ImportPayment_closeexchangerat = document.getElementById("ImportPayment.closeexchangerat");
	
	ImportPayment_closeexchangerat.onblur = function closeexchangerat(){    
		for (var j = 0;j<ImportPaymentItem_store.getCount();j++){
			var record = ImportPaymentItem_grid.getStore().getAt(j);
			
			var exchangerate = 0;
			if (Utils.isEmpty(document.getElementById('ImportPayment.closeexchangerat').value) == true){
    			exchangerate = 0;
            }else{
            	exchangerate = document.getElementById('ImportPayment.closeexchangerat').value;
            }

			var payamount2 = 0;
			if (Utils.isNumber(ImportPaymentItem_store.getAt(j).get('assignamount')) == false){
				payamount2 = 0;
            }else{
            	payamount2 = ImportPaymentItem_store.getAt(j).get('assignamount');
            }
			
			var mulValue = parseFloat(payamount2) * parseFloat(exchangerate);
			record.set('assignamount2',round(mulValue,2));
			
		}
	}
	
	/**
	 * @修改作者：邱杰烜
	 * @修改日期：2010-08-31
	 * 根据流程状态隐藏相应的菜单
	 */
	/***********************************************************************/
	Ext.getCmp("tabs").hideTabStripItem('importPayBankItemTab');	// 隐藏"付款银行"页签
	Ext.getCmp("tabs").hideTabStripItem('billPayReBankItemTab');
	/***********************************************************************/
});

/*
 * 合同详细信息查看
 */ 
function viewContractInfo(contNo){
	var contUrl = contextPath + '/contractController.spr?action=viewPurchaseContract&contractno=' + contNo;
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
/*
 * 到单详细信息查看xx
 */
function viewPickListInfo(pickNo){
	var pickUrl = contextPath + '/pickListInfoController.spr?action=viewPickListInfo&pickListId=' + pickNo;
	if(pickNo.trim()!=''){
		_getMainFrame().ExtModalWindowUtil.show('到单信息', pickUrl, '', '', {width:700,height:600});
	}
}
/*
 * 相关单据信息查看 
 */
function viewRelatedInfo(relaNo){
	var relaUrl = contextPath + '/xdss3/payment/importPaymentController.spr?action=viewRelatedInfo&relatedNo=' + relaNo;
	if(relaNo.trim()!=''){
		_getMainFrame().maintabs.addPanel('原内贸付款单信息','',relaUrl);
		   
	}
}

/**
 * 初始化界面信息
 */
function mainInit(){
	dict_div_paymenttype_dict.setEditable(false);
	var pt = document.getElementById('ImportPayment.paymenttype');
	if(pt.value == '14'){
		Ext.getCmp("tabs").hideTabStripItem('importPaymentCbillTab');
		Ext.getCmp("_autoassign").hide();
		Ext.getCmp("_clearassign").hide();
		Ext.getCmp("_submitForReassign").hide();
	}
	
	/**
	 * @创建人：陈非
	 * @修改日期：2010-09-08
	 * 进口付款保存时控制点：
	 * TT、DP、即期信用证只能选择押汇、一般付款；
	 * 远期信用证与DA只能选择承兑；
	 * 进口预付款只能选择一般付款。
	 */
	var store = dict_div_pay_type_dict.getStore();
	store.removeAll(false);
	var pt = document.getElementById('ImportPayment.paymenttype');
	var TopicRecord = Ext.data.Record.create([{name: 'id',mapping:'text'}]); 
	if(pt.value == '01' || pt.value == '02' || pt.value == '03'  || pt.value == '15' ){
		var myNewRecord1 = new TopicRecord({id: '3',text: '一般付款'}); 
		var myNewRecord2 = new TopicRecord({id: '2',text: '押汇'}); 
		store.add(myNewRecord1);
		store.add(myNewRecord2);
	}
	
	if(pt.value == '04' || pt.value == '05' || pt.value == '16' ){
		var myNewRecord1 = new TopicRecord({id: '1',text: '承兑'}); 
		store.add(myNewRecord1);
	}
	if(pt.value == '14'){
		var myNewRecord1 = new TopicRecord({id: '3',text: '一般付款'}); 
		store.add(myNewRecord1);
	}
	if(pt.value == '15'  ||  pt.value == '16'){
		Ext.getDom('ImportPayment.closeexchangerat').value = "1";
		Ext.getDom('ImportPayment.exchangerate').value = "1";
		Ext.getDom('ImportPayment.documentaryrate').value = "1";
		Ext.getDom('ImportPayment.redocaryrate').value = "1";
		Ext.getDom('ImportPayment.closeexchangerat').readOnly = true;
		Ext.getDom('ImportPayment.exchangerate').readOnly = true;
		Ext.getDom('ImportPayment.documentaryrate').readOnly = true;
		Ext.getDom('ImportPayment.redocaryrate').readOnly = true;
		dict_div_isrepresentpay_dict.readOnly = true;
		dict_div_isoverrepay_dict.readOnly = true;
		
	}
	if (strRoleType == "1"){
		
		var maintab = Ext.getCmp("tabs");

		maintab.hideTabStripItem('importDocuBankItemTab');
		//maintab.hideTabStripItem('importSettSubjTab');
		//maintab.hideTabStripItem('importFundFlowTab');
		maintab.hideTabStripItem('importRelatPaymentTab');

		Ext.getCmp("_bookofaccount").hide();
		Ext.getCmp("_simulate").hide();
		Ext.getCmp("_submitForReassign").hide();
		
		var ImportPaymentItem_grid_cm = ImportPaymentItem_grid.getColumnModel();
		ImportPaymentItem_grid_cm.setHidden(ImportPaymentItem_grid_cm.findColumnIndex('assignamount2'),true);
		//ImportPaymentItem_grid_cm.setHidden(ImportPaymentItem_grid_cm.findColumnIndex('prepayamount'),true);
		
		var ImportPayBankItem_grid_cm = ImportPayBankItem_grid.getColumnModel();
		ImportPayBankItem_grid_cm.setHidden(ImportPayBankItem_grid_cm.findColumnIndex('cashflowitem'),true);
		ImportPayBankItem_grid_cm.setHidden(ImportPayBankItem_grid_cm.findColumnIndex('payamount2'),true);
		
		
		document.getElementById('ImportPayment.factamount').readOnly="readOnly";
		div_factcurrency_sh_sh.readOnly = "true";
		document.getElementById('ImportPayment.exchangerate').readOnly="readOnly";
		calendar_ImportPayment_voucherdate.readOnly = "true";
		calendar_ImportPayment_accountdate.readOnly = "true";
	}
}

function _predeletesImportPaymentItem(){
	if (ImportPaymentItem_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
			title:Txt.cue,
		    msg: Txt.importPaymentItem_Deletes_ConfirmOperation,
			buttons: {yes:Txt.ok, no:Txt.cancel},
			icon: Ext.MessageBox.QUESTION,
			fn:function(buttonid){
			    if (buttonid == 'yes'){
				var records = ImportPaymentItem_grid.selModel.getSelections();
				for (var i=0;i<records.length;i++){
					
					/*手工修改*/
					for (var j=0;j<ImportPaymentCbill_store.getCount();j++){
						if (records[i].get('contract_no') == ImportPaymentCbill_store.getAt(j).get('contract_no')){
							var record = ImportPaymentCbill_grid.getStore().getAt(j);
							
							ImportPaymentCbill_store.remove(record);
							
							j--;
						}
					}
					/*手工修改*/
					
					ImportPaymentItem_grid.getStore().remove(records[i]);
				}
			}
			}
		});
	}else{
		_getMainFrame().showInfo(Txt.importPaymentItem_Deletes_SelectToOperation);
	}
	
	return false;
}

function _postdeletesImportPaymentItem(){
	
}

function _predeletesImportPaymentCbill(){
	return true;
}

function _postdeletesImportPaymentCbill(){
	
}

function _predeletesImportPayBankItem(){
	return true;
}

function _postdeletesImportPayBankItem(){
	
}

function _predeletesImportDocuBankItem(){
	return true;
}

function _postdeletesImportDocuBankItem(){
	
}

/**
 * 判断合同发配项信息
 * @return
 */
function _chectItemInfo(){
	//判断是否有选择到单
	var pickCount = 0;
	var isAssingItemValue = false;
	var isAssignBillValue = false;
	var showText = '';
	for (var j = 0;j<ImportPaymentItem_store.getCount();j++){
		if (Utils.isEmpty(ImportPaymentItem_store.getAt(j).get('pick_list_no')) == false){
			pickCount = pickCount + 1;
		}
		
		if (Utils.isNumber(ImportPaymentItem_store.getAt(j).get('assignamount')) == false){
			isAssingItemValue = true;
			
			if (Utils.isEmpty(ImportPaymentItem_store.getAt(j).get('contract_no')) == false){
				showText = '合同号为:' + ImportPaymentItem_store.getAt(j).get('contract_no') + '的分配金额为空!';
			}else if (Utils.isEmpty(ImportPaymentItem_store.getAt(j).get('project_no')) == false){
				showText = '立项号为:' + ImportPaymentItem_store.getAt(j).get('project_no') + '的分配金额为空!';
			}
			break;
		}                                                               
	}
	
	if (isAssingItemValue == true){
		_getMainFrame().showInfo(showText);
		return false;
	}
	
	var pt = document.getElementById('ImportPayment.paymenttype');
	if (pickCount == 0 && !pt.value == 14){
		_getMainFrame().showInfo("进口付款清帐必需选择一个到单！");
		return false;
	}else if (pickCount > 1){
		_getMainFrame().showInfo("进口付款清帐必需选择一个并且只能有一个到单！");
		return false;
	}
	
	return true;
}

/**
 * 判断发票上的信息
 * @return
 */
function _checkBillInfo(){
	//如果不是纯代理付款的才要判断此项金额
	if (dict_div_isrepresentpay_dict.getValue() != '1'){
		var isAssignBillValue = false;
		var showText = '';
		var isDisaffinityCurrency = false;
		for (var k = 0;k<ImportPaymentCbill_store.getCount();k++){
			var firstCurrency = ImportPaymentCbill_store.getAt(0).get('currency');
			
			if (ImportPaymentCbill_store.getAt(k).get('clearamount2') == '' || ImportPaymentCbill_store.getAt(k).get('clearamount2') == '0'){
				isAssignBillValue = true;
				showText = '发票号为:' + ImportPaymentCbill_store.getAt(k).get('billno') + '的分配金额为空或为零!';
				break;
			}
			
			if (firstCurrency != ImportPaymentCbill_store.getAt(k).get('currency')){
				isDisaffinityCurrency = true;
				showText = '一次的付款中不能有不同币别的发票信息!';
			}
		}
	
		if (isAssignBillValue == true){
			_getMainFrame().showInfo(showText);
			return false;
		}
		
		if (isDisaffinityCurrency == true){
			_getMainFrame().showInfo(showText);
			return false;
		}
	}
	
	return true;
}


/**
 * 检查付款银行的信息
 * @return
 */
function _checkBankInfo(){
	var isAssignBankValue = false;
	var showText = '';
	var assignBankValue = 0;
	for (var k = 0;k<ImportPayBankItem_store.getCount();k++){
		if (ImportPayBankItem_store.getAt(k).get('payamount') == '' || ImportPayBankItem_store.getAt(k).get('payamount') == '0'){
			isAssignBankValue = true;
			showText = '付款银行为:' + ImportPayBankItem_store.getAt(k).get('paybankname') + '的分配金额为空或为零!';
			break;
		}else{
			assignBankValue = parseFloat(assignBankValue) + parseFloat(ImportPayBankItem_store.getAt(k).get('payamount'));
		}
	}
	
	if (isAssignBankValue == true){
		_getMainFrame().showInfo(showText);
		return false;
	}
	
	// 邱杰烜 2010-09-11 猪宝要求先屏蔽该验证
	if (ImportPayBankItem_store.getCount() > 0){
		if (round(assignBankValue,2) != round((Ext.getDom('ImportPayment.applyamount').value),2)){
			showText = '实际申请金额为：'+ round(Ext.getDom('ImportPayment.applyamount').value,2) +'\n银行分配金额为：'+ round(assignBankValue,2) + '两个金额不一致，是否继续？' ;
//			_getMainFrame().showInfo(showText);
//			return false;
			if(confirm(showText)){
				return true;
			}else{
				return false;
			}
		}
	}
	
	return true;
}

/**
 * 检查押汇付款银行的信息
 * @return
 */
function _checkDocBankInfo(){
	var isAssignBankValue = false;
	var showText = '';
	var assignBankValue = 0;
	for (var k = 0;k<ImportDocuBankItem_store.getCount();k++){
		if (ImportDocuBankItem_store.getAt(k).get('docuarypayamount') == '' || ImportDocuBankItem_store.getAt(k).get('docuarypayamount') == '0'){
			isAssignBankValue = true;
			showText = '付款银行为:' + ImportDocuBankItem_store.getAt(k).get('docuarybankname') + '的分配金额为空或为零!';
			break;
		}else{
			assignBankValue = parseFloat(assignBankValue) + parseFloat(ImportDocuBankItem_store.getAt(k).get('docuarypayamount'));
		}
	}
	if (isAssignBankValue == true){
		_getMainFrame().showInfo(showText);
		return false;
	}
	return true;
}

/**
 * @创建作者：邱杰烜
 * @创建日期：2010-09-15
 * 检查未收款-在批金额是否小于清帐金额
 */
function _checkClearAmount(){
	var paymentCbills = ImportPaymentCbill_grid.getStore();
	for(var j=0;j<paymentCbills.getCount();j++){
		var unpaidamount = parseFloat(paymentCbills.getAt(j).get('unpaidamount'));	// 未付款
		var onroadamount = parseFloat(paymentCbills.getAt(j).get('onroadamount'));	// 在批金额
		var clearamount2 = parseFloat(paymentCbills.getAt(j).get('clearamount2'));	// 清账金额
		var difference = round((unpaidamount - onroadamount - clearamount2),2);				// 未付款 - 在批金额 - 清账金额（差值）
		if(difference < 0){
			_getMainFrame().showInfo('未付款 - 在批金额不能小于清帐金额！');
			return false;
		}
	}
	return true;
}

/**
 * 实际的保存动作
 * @return
 */
function _factSave(){
	if(isCreateCopy){
		document.getElementById("ImportPayment.paymentid").value = "";
	}
	
	var param = Form.serialize('mainForm');	
    
    
	if(isCreateCopy)
	{
		param = param + ""+ getAllImportPaymentItemGridData();
	}
	else
	{
		param = param + ""+ getImportPaymentItemGridData(); 
	}
	
     
	if(isCreateCopy)
	{
		param = param + ""+ getAllImportPaymentCbillGridData();
	}
	else
	{
		param = param + ""+ getImportPaymentCbillGridData(); 
	}
		        	           
     
	if(isCreateCopy)
	{
		param = param + ""+ getAllImportPayBankItemGridData();
	}
	else
	{
		param = param + ""+ getImportPayBankItemGridData(); 
	}
		        	           
     
	if(isCreateCopy)
	{
		param = param + ""+ getAllImportDocuBankItemGridData();
	}
	else
	{
		param = param + ""+ getImportDocuBankItemGridData(); 
	}
		        	           
	param = param + ""+ getBillPayReBankItemGridData();
	//param = param + "&" + Form.serialize('importSettSubjForm');		
       

	//param = param + "&" + Form.serialize('importFundFlowForm');		
       
     
	if(isCreateCopy)
	{
		param = param + ""+ getAllImportRelatPaymentGridData();
	}
	else
	{
		param = param + ""+ getImportRelatPaymentGridData(); 
	}
	
	new AjaxEngine(contextPath + '/xdss3/payment/importPaymentController.spr?action=_saveOrUpdate', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
}


/**
 * 处理预付款金额字段
 * @return
 */
function _dealPrepayamount(){
	var allItemAssignValue = 0;
	for (var j = 0;j<ImportPaymentItem_store.getCount();j++){
		var ItemAssignValue = ImportPaymentItem_store.getAt(j).get('assignamount');
		allItemAssignValue = parseFloat(allItemAssignValue) + parseFloat(ItemAssignValue);
		var BillAssignValue = 0;
		
		if (Utils.isEmpty(ImportPaymentItem_store.getAt(j).get('contract_no')) == false){
			for (var k = 0;k<ImportPaymentCbill_store.getCount();k++){
				if (Utils.isEmpty(ImportPaymentItem_store.getAt(j).get('contract_no')) == false &&
					Utils.isEmpty(ImportPaymentCbill_store.getAt(k).get('contract_no')) == false &&
					ImportPaymentItem_store.getAt(j).get('contract_no') == ImportPaymentCbill_store.getAt(k).get('contract_no')){
					BillAssignValue = parseFloat(BillAssignValue) + parseFloat(ImportPaymentCbill_store.getAt(k).get('clearamount2'));
				}
			}
		}else if (Utils.isEmpty(ImportPaymentItem_store.getAt(j).get('project_no')) == false){
			for (var k = 0;k<ImportPaymentCbill_store.getCount();k++){
				if (Utils.isEmpty(ImportPaymentItem_store.getAt(j).get('contract_no')) == true &&
					Utils.isEmpty(ImportPaymentCbill_store.getAt(k).get('contract_no')) == true &&
					ImportPaymentItem_store.getAt(j).get('project_no') == ImportPaymentCbill_store.getAt(k).get('project_no')){
					BillAssignValue = parseFloat(BillAssignValue) + parseFloat(ImportPaymentCbill_store.getAt(k).get('clearamount2'));
				}
			}
		}
		
		var record = ImportPaymentItem_grid.getStore().getAt(j);
		//record.set('prepayamount',parseFloat(ItemAssignValue)-parseFloat(BillAssignValue));
		//record.set('goodsamount',parseFloat(ItemAssignValue));
	}
	
	//判断申请金额和实际公配金额是不是一致
	var redocAmount = Ext.getDom('ImportPayment.redocaryamount').value;
	if(Utils.isEmpty(redocAmount) || redocAmount=='0'){		// 在"还押汇金额"为0或为空的时候才检查分配金额
		if (parseFloat(allItemAssignValue) == parseFloat(document.getElementById('ImportPayment.applyamount').value)){
			_factSave();
			return true;
		}else{
			var showText = '申请金额为：'+document.getElementById('ImportPayment.applyamount').value + '\n实际分配金额为：' + allItemAssignValue+'\n两个金额不一致，是否继续？';
//			_getMainFrame().Ext.MessageBox.show({
//				title:'系统提示',
//				msg: showText,
//				buttons: {yes:Txt.ok, no:Txt.cancel},
//				icon: Ext.MessageBox.QUESTION,
//				fn:function(buttonid){
//					if (buttonid == 'yes'){
//						_factSave();
//					}else{
//						return false;
//					}
//				}
//			});
			if(confirm(showText)){
				_factSave();
			}else{
				return false;
			}
		}
	}else{
		_factSave();
	}
}

/**
 * 检查客户信用额度
 * @return
 */
function _checkCredit(){
	if(div_supplier_sh_sh.getValue()==''){
        _getMainFrame().showInfo("请选择一个供应商！");
        return false;
	}
	if (ImportPaymentItem_store.getCount() <= 0){
		_getMainFrame().showInfo('请选择一个立项信息!');
		return false;
	}
	
	var providerId = div_supplier_sh_sh.getValue();
	var projectId = ImportPaymentItem_store.getAt(0).get('project_no');
	var value = parseFloat(document.getElementById('ImportPayment.applyamount').value) * parseFloat(document.getElementById('ImportPayment.closeexchangerat').value);
		

	var conn = Ext.lib.Ajax.getConnectionObject().conn; 
	conn.open("POST", contextPath+'/xdss3/payment/importPaymentController.spr?action=checkCredit&providerid='+ providerId + 
			'&projectno='+ projectId +
			'&value='+ round(value,2),false);
	conn.send(null);
	var jsonData = Ext.util.JSON.decode(conn.responseText);

	if (jsonData.result == ''){
		return true;
	}else{
		var andFlag = jsonData.result.split("&");
		if (andFlag[1] == 'true'){
			return true;
		}else{
			if (andFlag[0] == 'false' && andFlag[1] == 'false'){
				return true;
			}else{
				_getMainFrame().showInfo('额度超出不能保存!'+andFlag[0]);
				return false;
			}
		}
	}
	
	return true;
}

/**
 * @修改作者：邱杰烜
 * @修改日期：2010-08-31
 * 结算汇率为必填
 */
/*************************************************************/
function _checkCloseExchangeRate(){

	var cer = Ext.getDom('ImportPayment.closeexchangerat').value;
	if(Utils.isEmpty(cer)){
		_getMainFrame().showInfo("[结算汇率]不能为空！");
		return false;
	}else if(!Utils.isNumber(cer)){
		_getMainFrame().showInfo("[结算汇率]必须为数字型！");
		return false;
	}else if(cer <= 0){
		_getMainFrame().showInfo("[结算汇率]不能为0！");
		return false;
	}
	return true;
}
/*************************************************************/


/**
 * 保存的检查
 * @return
 */
function _checkSave(){
	//统一判断合同上的信息
	if (_chectItemInfo() == false){
		return false;
	}
	//统一判断发票上的信息
	if (_checkBillInfo() == false){
		return false;
	}
	
	//检查付款很行信息
	if (_checkBankInfo() == false){
		return false;
	}
	
	//检查押汇行信息
	if (_checkDocBankInfo() == false){
		return false;
	}
	
	// 检查结算汇率
	if(_checkCloseExchangeRate() == false){
		return false;
	}
	
	//start yanghancai  2010-09-15
	// 如果是否纯代理付款选择为是，则纯代理付款客户为必填
	if(dict_div_isrepresentpay_dict.getValue().trim() == '1' && div_representpaycust_sh_sh.getValue()==''){
		_getMainFrame().showInfo("[纯代理付款客户]不能为空！");
		return false;
	}
	//end yanghancai  2010-09-15	
	
	// 检查未收款-在批金额是否小于清帐金额
	if(!_checkClearAmount()){
		return false;
	}
	
	return true;
}

/**
 * 保存 
 * --------------------------------- 修改记录 ----------------------------------
 * 邱杰烜 2010-09-14 加入计算每个付款金额分配页签下的预付金额的功能
 */
function _presaveOrUpdateImportPayment(){
	/*
	 * 清除金额分配、清票、银行页签下金额为0的数据
	 */
	_clearAllZeroAmount();
	
	if (_checkSave() == false){
		return false;
	}
	
	//处理信用额度
	if (_checkCredit() == false){
		return false;
	}
	
	//检查清账金额与分配金额是否相等
	var msg = checkAmount();
	if(msg!=""){
		 _getMainFrame().showInfo(msg);
		return false;
	}
	
	//处理预付款字段
	_dealPrepayamount();
	
	var size = ImportPaymentCbill_grid.getStore().getCount();
	if(size !=0){
		var param = Form.serialize('mainForm');	  
		
		param = param + ""+ getAllImportPaymentItemGridData();         	          
	 	param = param + ""+ getAllImportPaymentCbillGridData(); 
	//	param = param +  "&"+ Form.serialize('settleSubjectForm'); 
 //		param = param +  "&"+ Form.serialize('fundFlowForm');		
		
		//ajax同步调用
		var url = contextPath + '/xdss3/payment/importPaymentController.spr?action=checkClearData&';	
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
	}
	
	return false ;
}

/**
 * 保存 
 */
function _postsaveOrUpdateImportPayment()
{
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

}
          
/**
 * 提交
 * --------------------------------- 修改记录 ----------------------------------
 * 邱杰烜 2010-09-14 加入计算每个付款金额分配页签下的预付金额的功能
 */
function _presubmitProcessImportPayment(){
	/*
	 * 清除金额分配、清票、银行页签下金额为0的数据
	 */
	_clearAllZeroAmount();
	
	if (ImportPaymentItem_store.getCount() <= 0){//付款金额页签里如果没有数据不允许提交
		_getMainFrame().showInfo('请分配金额!');
		return false;
	}
	/**
	 * 计算每个付款金额分配页签下的预付金额
	 */
	_calcPrepayAmount();
	
	//检查清账金额与分配金额是否相等
	var msg = checkAmount();
	if(msg!=""){
		 _getMainFrame().showInfo(msg);
		return false;
	}
	
	var size = ImportPaymentCbill_grid.getStore().getCount();
	if(size !=0){
		var param = Form.serialize('mainForm');	  
		
		param = param + ""+ getAllImportPaymentItemGridData();         	          
	 	param = param + ""+ getAllImportPaymentCbillGridData(); 
	//	param = param +  "&"+ Form.serialize('settleSubjectForm'); 
 //		param = param +  "&"+ Form.serialize('fundFlowForm');		
		
		//ajax同步调用
		var url = contextPath + '/xdss3/payment/importPaymentController.spr?action=checkClearData&';	
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
	}
	return true;
}

/**
 * 提交
 */
function _postsubmitProcessImportPayment(){}

function winPurchaseCallBack(jsonArrayData){
	//var contractList = "";
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j<ImportPaymentItem_store.getCount();j++){
			if (ImportPaymentItem_store.getAt(j).get('contract_no') == jsonArrayData[i].CONTRACT_NO){
				isExists = true;
				break;
			}
		}

		if (isExists == false){
			//contractList = contractList + jsonArrayData[i].CONTRACT_NO + "|";
			var projectRecvFields = new ImportPaymentItem_fields({
				client:'',
				paymentitemid:'',
				paymentno:'',
				billisclear:'',
				paymentid:'',
				contract_no:'',
				old_contract_no:'',
				pick_list_no:'',
				project_no:'',
				project_no_text:'',
				contractamount:'',
				assignamount:'',
				prepayamount:'',
				assignamount2:'',
				goodsamount:'',
				ymat_group:''
			});
			
			ImportPaymentItem_grid.stopEditing();
			ImportPaymentItem_grid.getStore().insert(0, projectRecvFields);
			ImportPaymentItem_grid.startEditing(0, 0);
			var record = ImportPaymentItem_grid.getStore().getAt(0);
			
			record.set('client','');
			record.set('paymentitemid','');
			record.set('paymentno','');
			record.set('billisclear','0');
			record.set('paymentid','');
			record.set('contract_no',jsonArrayData[i].CONTRACT_NO);
			record.set('old_contract_no',jsonArrayData[i].EKKO_UNSEZ);
			record.set('pick_list_no','');
			record.set('project_no',jsonArrayData[i].PROJECT_NO);
			record.set('project_no_text',jsonArrayData[i].PROJECT_NAME);
			record.set('contractamount',jsonArrayData[i].TOTAL);
			record.set('assignamount','0');
			record.set('prepayamount','0');
			record.set('assignamount2','0');
			record.set('goodsamount','0');
			record.set('ymat_group',jsonArrayData[i].WGBEZ);
		}
	}
	
//	if (contractList != ''){
//		_getContractOrProjectBillInfo(contractList,'');
//	}
}

var searchPurchaseHelpWin = new Ext.SearchHelpWindow({
	shlpName : 'YHCONTRAPURCHASE',
	callBack : winPurchaseCallBack
});

/**
 * 增加采购合同
 * @return
 */
function _addPurchaseImportPaymentItem(){
	if(div_supplier_sh_sh.getValue()==''){
        _getMainFrame().showInfo("必须先选择[供应商]！");
        return;
	}
	searchPurchaseHelpWin.defaultCondition = "EKKO_LIFNR='" + div_supplier_sh_sh.getValue() + "' and PROJECT_STATE in('3','8')";
	searchPurchaseHelpWin.show();
}

function winPickCallBack(jsonArrayData){
	//var contractList = '';
	var plick_list_no = '';
	for(var i = 0;i < jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j < ImportPaymentItem_store.getCount();j++){
			// 邱杰烜 2010-09-25 将原本存PICK_LIST_NO字段改成存PICK_LIST_ID
			if (ImportPaymentItem_store.getAt(j).get('pick_list_no') == jsonArrayData[i].PICK_LIST_ID){
				isExists = true;
				break;
			}
		}
		
		if (isExists == false){
			//contractList = contractList + jsonArrayData[i].CONTRACT_NO + "|";
			var projectRecvFields = new ImportPaymentItem_fields({
				client:'',
				paymentitemid:'',
				paymentno:'',
				billisclear:'',
				paymentid:'',
				contract_no:'',
				old_contract_no:'',
				pick_list_no:'',
				pick_list_no_text:'',
				project_no:'',
				project_no_text:'',
				contractamount:'',
				assignamount:'',
				prepayamount:'',
				assignamount2:'',
				goodsamount:'',
				ymat_group:''
			});
			
			ImportPaymentItem_grid.stopEditing();
			ImportPaymentItem_grid.getStore().insert(0, projectRecvFields);
			ImportPaymentItem_grid.startEditing(0, 0);
			var record = ImportPaymentItem_grid.getStore().getAt(0);
			
			record.set('client','');
			record.set('paymentitemid','');
			record.set('paymentno','');
			record.set('billisclear','0');
			record.set('paymentid','');
			record.set('contract_no',jsonArrayData[i].CONTRACT_NO);
			record.set('old_contract_no',jsonArrayData[i].EKKO_UNSEZ);
			record.set('pick_list_no',jsonArrayData[i].PICK_LIST_ID);	// 邱杰烜 2010-09-25 将原本存PICK_LIST_NO字段改成存PICK_LIST_ID
			record.set('pick_list_no_text',jsonArrayData[i].PICK_LIST_NO);
			record.set('project_no',jsonArrayData[i].PROJECT_NO);
			record.set('project_no_text',jsonArrayData[i].PROJECT_NAME);
			record.set('contractamount','');
			record.set('assignamount','0');
			record.set('prepayamount','0');
			record.set('assignamount2','0');
			record.set('goodsamount','0');
			record.set('ymat_group',jsonArrayData[i].WGBEZ);
		}
	}
	plick_list_no = ImportPaymentItem_grid.getStore().getAt(0).get('pick_list_no');

	//从进口到单表T_PICK_LIST_INFO中将进口到单的部分信息带出来：分配金额、申请金额（一般是到单金额 ）；币别；预计到货日；（即期/承兑到期）付款日；核销单号；实际付款金额；实际币别
	if(plick_list_no != ''){
		var conn = Ext.lib.Ajax.getConnectionObject().conn; 
		conn.open("POST", contextPath + '/xdss3/payment/importPaymentController.spr?action=getPlickListInfoByno&plicklistid=' + plick_list_no,false);
		conn.send(null);
		//alert(conn.responseText);
		var jsonData = Ext.util.JSON.decode(conn.responseText);
		//alert(jsonData.totalCurrency + "|" + jsonData.currencyId + "|" + jsonData.arrivalDate + "|" + jsonData.payDate + "|" + jsonData.writeListNo + '|' + jsonData.currencyId);
		document.getElementById('ImportPayment.applyamount').value = jsonData.totalCurrency;
		//document.getElementById('ImportPayment.currency').value = jsonData.currencyId;
		div_currency_sh_sh.setValue(jsonData.currencyId);
		div_factcurrency_sh_sh.setValue(jsonData.currencyId);
		document.getElementById('ImportPayment.arrivegoodsdate').value = jsonData.arrivalDate;
		document.getElementById('ImportPayment.paydate').value = jsonData.payDate;
		document.getElementById('ImportPayment.writelistno').value = jsonData.writeListNo;
		document.getElementById('ImportPayment.factamount').value = jsonData.totalCurrency;
		//document.getElementById('ImportPayment.factcurrency').value = jsonData.currencyId;
		//div_currency2_sh_sh.setValue(jsonData.currencyId);   //取消从到单中带出“押汇币别”
		var settleRate = Ext.getDom('ImportPayment.closeexchangerat').value; // 结算汇率
		if(dict_div_paymenttype_dict.getValue()!='14'){
			Ext.getDom('ImportPayment.collectbankid').value = jsonData.bankName;
		}
		ImportPaymentItem_grid.getStore().getAt(0).set('assignamount',jsonData.totalCurrency);
		ImportPaymentItem_grid.getStore().getAt(0).set('assignamount2',round((jsonData.totalCurrency * settleRate),2));
		ImportPaymentItem_grid.getStore().getAt(0).set('prepayamount',jsonData.totalCurrency);
		ImportPaymentItem_grid.recalculation();
	}
}

//function customCallBackHandle(transport)
//{
//	alert(transport.responseText);
//	var responseUtil = new AjaxResponseUtils(transport.responseText);
//	var result = responseUtil.getCustomField("coustom");
//	document.getElementById('ImportPayment.applyamount').value = result.totalCurrency;
//	document.getElementById('ImportPayment.currency').value = result.currencyId;
//	document.getElementById('ImportPayment.arrivegoodsdate').value = result.arrivalDate;
//	document.getElementById('ImportPayment.paydate').value = result.payDate;
//	document.getElementById('ImportPayment.writelistno').value = result.writeListNo;
//	document.getElementById('ImportPayment.factcurrency').value = result.currencyId;
//}

var searchPickHelpWin = new Ext.SearchHelpWindow({
	shlpName : 'YHPAYPICKLISTINFO',
	callBack : winPickCallBack
});

/**
 * 选定供应商的回调函数
 */
function winSupplierCallBack(jsonArrayData){
}

/**
 * 增加到单
 * @return
 */
function _addPlickImportPaymentItem(){
	if(div_supplier_sh_sh.getValue()==''){
        _getMainFrame().showInfo("必须先选择[供应商]！");
        return;
	}
	//searchPickHelpWin.defaultCondition = "PROVIDER_ID='" + div_supplier_sh_sh.getValue() + "'";
	searchPickHelpWin.defaultCondition = "DEPT_ID='" + div_dept_id_sh_sh.getValue() + "' AND EKKO_LIFNR='"+div_supplier_sh_sh.getValue()+"' " + "and PROJECT_STATE!='9' AND IS_AVAILABLE='1' " + "and (trim(paymentid) is null or (businessstate='-1' and pick_list_id not in ( select pick_list_no from ypaymentitem left join ypayment on ypaymentitem.paymentid=ypayment.paymentid where businessstate<>'-1')))";
	searchPickHelpWin.show();
}

function winProjectCallBack(jsonArrayData){
	//var projectList = '';
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j<ImportPaymentItem_store.getCount();j++){
			if (ImportPaymentItem_store.getAt(j).get('project_no') == jsonArrayData[i].PROJECT_NO &&
				ImportPaymentItem_store.getAt(j).get('contract_no').trim()==''){
				isExists = true;
				break;
			}
		}
		
		if (isExists == false){
		//	projectList = projectList + jsonArrayData[i].PROJECT_NO + '|';
			var projectRecvFields = new ImportPaymentItem_fields({
				client:'',
				paymentitemid:'',
				paymentno:'',
				billisclear:'',
				paymentid:'',
				contract_no:'',
				old_contract_no:'',
				pick_list_no:'',
				project_no:'',
				project_no_text:'',
				contractamount:'',
				assignamount:'',
				prepayamount:'',
				assignamount2:'',
				goodsamount:'',
				ymat_group:''
			});
			
			ImportPaymentItem_grid.stopEditing();
			ImportPaymentItem_grid.getStore().insert(0, projectRecvFields);
			ImportPaymentItem_grid.startEditing(0, 0);
			var record = ImportPaymentItem_grid.getStore().getAt(0);
			
			record.set('client','');
			record.set('paymentitemid','');
			record.set('paymentno','');
			record.set('billisclear','0');
			record.set('paymentid','');
			record.set('contract_no','');
			record.set('old_contract_no','');
			record.set('pick_list_no','');
			record.set('project_no',jsonArrayData[i].PROJECT_NO);
			record.set('project_no_text',jsonArrayData[i].PROJECT_NAME);
			record.set('contractamount','');
			record.set('assignamount','0');
			record.set('prepayamount','0');
			record.set('assignamount2','0');
			record.set('goodsamount','0');
			record.set('ymat_group',jsonArrayData[i].WGBEZ);
		}
	}
//	if ( projectList != ''){
//		_getContractOrProjectBillInfo('',projectList);
//	}
}

var searchProjectHelpWin = new Ext.SearchHelpWindow({
	shlpName : 'YHPROJECTINFO',
	callBack : winProjectCallBack
});

/**
 * 增加立项
 * @return
 */
function _addProjectImportPaymentItem(){
	searchProjectHelpWin.defaultCondition = "DEPT_ID='" + div_dept_id_sh_sh.getValue() + "' " + "and IS_AVAILABLE='1' and PROJECT_STATE in('3','8')";
	searchProjectHelpWin.show();
}

function winPageCallBack(jsonArrayData){
	//var contractList = '';
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j<ImportPaymentItem_store.getCount();j++){
			if (ImportPaymentItem_store.getAt(j).get('contract_no') == jsonArrayData[i].CONTRACT_NO){
				isExists = true;
				break;
			}
		}
		
		if (isExists == false){
			//contractList = contractList + jsonArrayData[i].CONTRACT_NO + "|";
			var projectRecvFields = new ImportPaymentItem_fields({
				client:'',
				paymentitemid:'',
				paymentno:'',
				billisclear:'',
				paymentid:'',
				contract_no:'',
				old_contract_no:'',
				pick_list_no:'',
				project_no:'',
				project_no_text:'',
				contractamount:'',
				assignamount:'',
				prepayamount:'',
				assignamount2:'',
				goodsamount:''
			});
			
			ImportPaymentItem_grid.stopEditing();
			ImportPaymentItem_grid.getStore().insert(0, projectRecvFields);
			ImportPaymentItem_grid.startEditing(0, 0);
			var record = ImportPaymentItem_grid.getStore().getAt(0);
			
			record.set('client','');
			record.set('paymentitemid','');
			record.set('paymentno','');
			record.set('billisclear','0');
			record.set('paymentid','');
			record.set('contract_no',jsonArrayData[i].CONTRACT_NO);
			record.set('old_contract_no',jsonArrayData[i].EKKO_UNSEZ);
			record.set('pick_list_no','');
			record.set('project_no',jsonArrayData[i].PROJECT_NO);
			record.set('project_no_text',jsonArrayData[i].PROJECT_NAME);
			record.set('contractamount',jsonArrayData[i].TOTAL);
			record.set('assignamount','0');
			record.set('prepayamount','0');
			record.set('assignamount2','0');
			record.set('goodsamount','0');
		}
	}
//	if (contractList != ''){
//		_getContractOrProjectBillInfo(contractList,'');
//	}
}

var searchPageHelpWin = new Ext.SearchHelpWindow({
	shlpName : 'YHCONTRAPURCHASE',
	callBack : winPageCallBack
});

/**
 * 增加外部纸质合同号
 * @return
 */
function _addPageContractImportPaymentItem(){
	if(div_supplier_sh_sh.getValue()==''){
        _getMainFrame().showInfo("必须先选择[供应商]！");
        return;
	}
	
	searchPageHelpWin.defaultCondition = "EKKO_LIFNR='" + div_supplier_sh_sh.getValue() + "'";
	searchPageHelpWin.show();
}

function winBillClearItemPayCallBack(jsonArrayData){
	var billnoList = '';
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j<ImportPaymentCbill_store.getCount();j++){
			if (ImportPaymentCbill_store.getAt(j).get('billno') == jsonArrayData[i].INVOICE){
				isExists = true;
				break;
			}
		}
		
		if (isExists == false){
			billnoList = billnoList + jsonArrayData[i].INVOICE + "|";
		}
	}
	
	if (billnoList != ''){
		Ext.Ajax.request({
	        url : contextPath+'/xdss3/payment/importPaymentController.spr?action=_addBillInfo&billnolist='+billnoList ,
	        success : function(xhr){
	            if(xhr.responseText){
	               var jsonData = Ext.util.JSON.decode(xhr.responseText);

	               for(var j=0;j<jsonData.data.length;j++){
	                   var data = jsonData.data[j];
	                   
	                   var p = new Ext.data.Record(data);
	                   ImportPaymentCbill_grid.stopEditing();
	                   ImportPaymentCbill_grid.getStore().insert(0, p);
	                   ImportPaymentCbill_grid.startEditing(0, 0);
	                   var record = ImportPaymentCbill_grid.getStore().getAt(0);
	                   record.set('clearamount2','0');
	               }
	           }
	        },
	        scope : this
	    });
	}
}

var searchBillClearItemPayHelpWin = new Ext.SearchHelpWindow({
    shlpName : 'YHUNCLEARBILL2',
    callBack : winBillClearItemPayCallBack
});

/**
 * 增加发票
 * @return
 */
function _addbillImportPaymentCbill(){
	if(div_supplier_sh_sh.getValue()==''){
        _getMainFrame().showInfo("必须先选择[供应商]！");
        return;
	}
	
	var contracttext = "";
	for (var j = 0;j<ImportPaymentItem_store.getCount();j++){
		contracttext = contracttext + "'" +ImportPaymentItem_store.getAt(j).get('contract_no') + "'";
		
		if (j < ImportPaymentItem_store.getCount()-1){
			contracttext = contracttext + ","
		}
	}
	
	var projecttext = "";
	for (var j = 0;j<ImportPaymentItem_store.getCount();j++){
		if (Utils.isEmpty(ImportPaymentItem_store.getAt(j).get('contract_no')) == true &&
			Utils.isEmpty(ImportPaymentItem_store.getAt(j).get('project_no')) == true){
			projecttext = projecttext + "'" +ImportPaymentItem_store.getAt(j).get('project_no') + "'";
			
			if (j < ImportPaymentItem_store.getCount()-1){
				projecttext = projecttext + ","
			}
		}
	}
	
	if (contracttext != "" && projecttext != ""){
		searchBillClearItemPayHelpWin.defaultCondition = "LIFNR='" + div_supplier_sh_sh.getValue() + "'" +
		" AND (VERKF IN ("+contracttext+") OR LIXIANG IN ("+projecttext+"))" + " and ISCLEARED='0'";
	}else{
		if (contracttext != "" && projecttext == ""){
			searchBillClearItemPayHelpWin.defaultCondition = "LIFNR='" + div_supplier_sh_sh.getValue() + "'" +
			" AND VERKF IN ("+contracttext+")" + " and ISCLEARED='0'";
		}else{
			if (contracttext == "" && projecttext != ""){
				searchBillClearItemPayHelpWin.defaultCondition = "LIFNR='" + div_supplier_sh_sh.getValue() + "'" +
				" AND LIXIANG IN ("+projecttext+")" + " and ISCLEARED='0'";
			}else{
				searchBillClearItemPayHelpWin.defaultCondition = "LIFNR='" + div_supplier_sh_sh.getValue() + "'" + " and ISCLEARED='0'";
			}
		}
	}
	searchBillClearItemPayHelpWin.show();
}

function winBankCallBack(jsonArrayData){
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j<ImportPayBankItem_store.getCount();j++){
			if (ImportPayBankItem_store.getAt(j).get('paybankaccount') == jsonArrayData[i].BANK_ACCOUNT){
				isExists = true;
				break;
			}
		}
		
		if (isExists == false){
			var bankRecvFields = new ImportPayBankItem_fields({
				paybankname:'',
				paybankaccount:'',
				payamount:'',
				cashflowitem:'',
				payamount2:'',
				paybankitemid:'',
				paybanksubject:'',
				paybankid:'',
				client:'',
				paymentid:''
			});
			
			ImportPayBankItem_grid.stopEditing();
			ImportPayBankItem_grid.getStore().insert(0, bankRecvFields);
			ImportPayBankItem_grid.startEditing(0, 0);
			var record = ImportPayBankItem_grid.getStore().getAt(0);
			
			record.set('paybankname',jsonArrayData[i].BANK_NAME);
			record.set('paybankaccount',jsonArrayData[i].BANK_ACCOUNT);
			record.set('payamount','0');
			
			if (dict_div_isrepresentpay_dict.getValue() == '1'){
				record.set('cashflowitem','101');
				record.set('cashflowitem_text','销售商品、提供劳务收到的现金');
			}else{
				record.set('cashflowitem','151');
				record.set('cashflowitem_text','购买商品、接受劳务支付的现金');
			}
			record.set('payamount2','0');
			record.set('paybankitemid','');
			record.set('paybanksubject',jsonArrayData[i].BANK_HKONT);
			record.set('paybankid',jsonArrayData[i].BANK_ID);
			record.set('client','');
			record.set('paymentid','');
		}
	}
}

var searchBankHelpWin = new Ext.SearchHelpWindow({
	shlpName : 'YHBANKACCOUNT',
	callBack : winBankCallBack
});

/**
 * 增加银行
 * @return
 */
function _addBankImportPayBankItem(){
	//searchBankHelpWin.defaultCondition = "BANK_ACCOUNT<>' ' and Isusing='1' ";
    searchBankHelpWin.defaultCondition = "BANK_ACCOUNT<>' ' and Isusing='1' and bukrs=(select company_code from t_sys_dept where dept_id='"+div_dept_id_sh_sh.getValue()+"') ";

	searchBankHelpWin.show();
}


function winDocuBankCallBack(jsonArrayData){
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j<ImportDocuBankItem_store.getCount();j++){
			if (ImportDocuBankItem_store.getAt(j).get('docuarybankacco') == jsonArrayData[i].BANK_ACCOUNT){
				isExists = true;
				break;
			}
		}
		if (isExists == false){
			var bankRecvFields = new ImportPayBankItem_fields({
				client:'',
				docuaryitemid:'',
				paymentid:'',
				docuarybankid:'',
				cashflowitem:'',
				docuarybanksubj:'',
				docuarybanksubj_text:'',
				docuarybankname:'',
				docuarybankacco:'',
				docuarypayamount:'',
				docuarypayamoun2:''
			});
			
			ImportDocuBankItem_grid.stopEditing();
			ImportDocuBankItem_grid.getStore().insert(0, bankRecvFields);
			ImportDocuBankItem_grid.startEditing(0, 0);
			var record = ImportDocuBankItem_grid.getStore().getAt(0);
			
			record.set('docuarybankname',jsonArrayData[i].BANK_NAME);
			record.set('docuarybankacco',jsonArrayData[i].BANK_ACCOUNT);
			record.set('docuarypayamount','0');
			record.set('cashflowitem','302');
			record.set('cashflowitem_text','借款所收到的现金');
			record.set('docuarypayamoun2','0');
			record.set('docuaryitemid','');
			record.set('docuarybankid',jsonArrayData[i].BANK_ID);
			record.set('client','');
			record.set('paymentid','');
		}
	}
}

var searchDocuBankHelpWin = new Ext.SearchHelpWindow({
	shlpName : 'YHBANKACCOUNT',
	callBack : winDocuBankCallBack
});


/**
 * 增加押汇银行
 * @return
 */
function _adddocubankImportDocuBankItem(){
	searchDocuBankHelpWin.show();
}

/**
 * 得到合同或立项的发票信息
 * @param strContractList
 * @param strProjectList
 * @return
 */
function _getContractOrProjectBillInfo(strContractList,strProjectList){
	Ext.Ajax.request({
        url : contextPath+'/xdss3/payment/importPaymentController.spr?action=_autoassign&contractList='+strContractList + '&projectList=' + strProjectList,
        success : function(xhr){
            if(xhr.responseText){
               var jsonData = Ext.util.JSON.decode(xhr.responseText);
               for(var j=0;j<jsonData.data.length;j++){
                   var data = jsonData.data[j];
                   var p = new Ext.data.Record(data);
                   ImportPaymentCbill_grid.stopEditing();
                   ImportPaymentCbill_grid.getStore().insert(0, p);
                   ImportPaymentCbill_grid.startEditing(0, 0);
                   var record = ImportPaymentCbill_grid.getStore().getAt(0);
                   record.set('clearamount2','');
               }
           }
        },
        scope : this
    });
}

/**
 * 自动分配
 * --------------------------------- 修改记录 ----------------------------------
 * 邱杰烜 2010-09-10 重新改造自动分配功能（金额分配计算部分）
 */
function _autoassignImportPayment(){
	// 若付款清票页签下有数据，则不到数据库里带出该合同下的所有清票，直接给那些数据填写金额
	if(ImportPaymentCbill_store.getCount() > 0){
		_autoAssignAmount();
		_getMainFrame().showInfo("自动分配成功！");
	// 否则根据付款分配页签下的所有合同与立项号去获取对应的所有发票， 然后再计算分配金额
	}else{
		_listPaymentCbills();
	}
	
}

/**
 * @修改作者：邱杰烜
 * @修改日期：2010-09-10
 * 根据付款分配页签下的所有合同与立项号去获取对应的所有清票
 */
function _listPaymentCbills(){
	var projectList = '';
	var contractList = '';
	for(var j = 0;j < ImportPaymentItem_store.getCount();j++){
		var record = ImportPaymentItem_store.getAt(j);
		if(record.get('contract_no').trim() != ''){
			contractList += record.get('contract_no') + '|';
		}else if(record.get('project_no').trim() != ''){
			projectList += record.get('project_no') + '|';
		}
	}
	Ext.Ajax.request({
		url : contextPath+'/xdss3/payment/importPaymentController.spr?action=_autoassign&contractList=' + 
		      contractList + '&projectList=' + projectList + '&lifnr=' + div_supplier_sh_sh.getValue(),
		success : function(xhr){
		if(xhr.responseText){
			var jsonData = Ext.util.JSON.decode(xhr.responseText);
			//alert(jsonData);
			for(var j=0;j<jsonData.data.length;j++){
				var data = jsonData.data[j];
				
				var isExists = false;
				for (var k = 0;k<ImportPaymentCbill_store.getCount();k++){
					if (ImportPaymentCbill_store.getAt(k).get('billid') == data.billid){
						isExists = true;
						break;
					}
				}
				
				if (isExists == false){
					var p = new Ext.data.Record(data);
					ImportPaymentCbill_grid.stopEditing();
					ImportPaymentCbill_grid.getStore().insert(0, p);
					ImportPaymentCbill_grid.startEditing(0, 0);
					
					var record = ImportPaymentCbill_grid.getStore().getAt(0);
					record.set('clearamount2','');
				}
			}
			// 自动计算并分配金额
			_autoAssignAmount();
			_getMainFrame().showInfo("自动分配成功！");
		}
	},
	scope : this
	});
}

/**
 * @创建作者：邱杰烜
 * @创建日期：2010-09-10
 * 自动计算并分配金额
 */
function _autoAssignAmount(){
	var paymentItems = ImportPaymentItem_grid.getStore();
	var paymentCbills = ImportPaymentCbill_grid.getStore();
	var assignAmount = 0;
	// 设置付款金额分配中的数据按"合同号"降序排序
	paymentItems.sort('contract_no','DESC');
	// 设置付款清票中的数据按"记账日期"升序排序
//	paymentCbills.sort('accountdate','ASC');
	// 将"清账金额"清零
	for(var k=0;k<paymentCbills.getCount();k++){
		paymentCbills.getAt(k).set('clearamount2', 0);
	}
	// 分别取到付款金额分配页签中每条数据（排序后），然后再根据每条的合同号/立项号去处理对应的清票
	for(var i=0;i<paymentItems.getCount();i++){
		// 先将"预付金额"清零
		paymentItems.getAt(i).set('prepayamount',0);
		var cNo = paymentItems.getAt(i).get('contract_no');
		var pNo = paymentItems.getAt(i).get('project_no');
		// 取得当前记录的值（分配金额 ）
		assignAmount =  parseFloat(paymentItems.getAt(i).get('assignamount'));
		if(!Utils.isEmpty(cNo)){	// 若为合同号
			for(var j=0;j<paymentCbills.getCount();j++){
				if(paymentCbills.getAt(j).get('contract_no')==cNo){		// 且该合同号与付款清票的合同号相同
					var unpaidamount = parseFloat(paymentCbills.getAt(j).get('unpaidamount'));	// 未付款
					var onroadamount = parseFloat(paymentCbills.getAt(j).get('onroadamount'));	// 在批金额
					var clearamount2 = parseFloat(paymentCbills.getAt(j).get('clearamount2'));	// 清账金额
					var difference = unpaidamount - onroadamount - clearamount2;		// 未付款 - 在批金额 - 清账金额（差值）
					// 若差值大于零且当前分配金额大于零
					if(difference>0 && assignAmount>0){
						if(assignAmount > difference){
							paymentCbills.getAt(j).set('clearamount2', round((difference + clearamount2),2));	// 设置清账金额
							assignAmount -= difference;
						}else{
							paymentCbills.getAt(j).set('clearamount2', round((assignAmount + clearamount2),2));	// 设置清账金额
							assignAmount = 0; 
							break;
						}
					}
				}
			}
		}else{						// 若为立项号
			for(var j=0;j<paymentCbills.getCount();j++){
				if(paymentCbills.getAt(j).get('project_no')==pNo){		// 且该合同号与付款清票的合同号相同
					var unpaidamount = parseFloat(paymentCbills.getAt(j).get('unpaidamount'));	// 未付款
					var onroadamount = parseFloat(paymentCbills.getAt(j).get('onroadamount'));	// 在批金额
					var clearamount2 = parseFloat(paymentCbills.getAt(j).get('clearamount2'));	// 清账金额
					var difference = unpaidamount - onroadamount - clearamount2;		// 未付款 - 在批金额 - 清账金额（差值）
					// 若差值大于零且当前分配金额大于零
					if(difference>0 && assignAmount>0){
						if(assignAmount > difference){
							paymentCbills.getAt(j).set('clearamount2', round((difference + clearamount2),2));	// 设置清账金额
							assignAmount -= difference;
						}else{
							paymentCbills.getAt(j).set('clearamount2', round((assignAmount + clearamount2),2));	// 设置清账金额
							assignAmount = 0;
							break;
						}
					}
				}
			}
		}
		paymentItems.getAt(i).set('prepayamount', round(assignAmount,2));
	}
	ImportPaymentCbill_grid.recalculation();
}

/**
 * @创建作者：邱杰烜
 * @创建日期：2010-09-14
 * 计算每个付款金额分配页签下的预付金额（阉割版的自动分配）
 */
function _calcPrepayAmount(){
	var paymentItems = ImportPaymentItem_grid.getStore();
	var paymentCbills = ImportPaymentCbill_grid.getStore();
	var assignAmount = 0;
	// 设置付款金额分配中的数据按"合同号"降序排序
	paymentItems.sort('contract_no','DESC');
	// 设置付款清票中的数据按"记账日期"升序排序
	paymentCbills.sort('accountdate','ASC');
	// 若清帐金额为0时，直接把该条纪录删除
	for(var i=0;i<paymentCbills.getCount();i++){
		if(paymentCbills.getAt(i).get('clearamount2')==0){	
			paymentCbills.removeAt(i);
		}
	}
	// 分别取到付款金额分配页签中每条数据，然后再根据每条的合同号/立项号去处理对应的清票
	for(var i=0;i<paymentItems.getCount();i++){
		// 先将"预付金额"清零
		paymentItems.getAt(i).set('prepayamount',0);
		var cNo = paymentItems.getAt(i).get('contract_no');
		var pNo = paymentItems.getAt(i).get('project_no');
		// 取得当前记录的值（分配金额 ）
		assignAmount = parseFloat(paymentItems.getAt(i).get('assignamount'));
		if(!Utils.isEmpty(cNo)){	// 若为合同号
			for(var j=0;j<paymentCbills.getCount();j++){
				if(paymentCbills.getAt(j).get('contract_no')==cNo){		// 且该合同号与付款清票的合同号相同
					var clearamount2 = parseFloat(paymentCbills.getAt(j).get('clearamount2'));	// 清账金额
					assignAmount -= clearamount2;
				}
			}
		}else{						// 若为立项号
			var flag = true;
			for(var k=0; k<i; k++){
				var pNo2 = paymentItems.getAt(k).get('project_no');
				var ppa = paymentItems.getAt(k).get('prepayamount');
				if(pNo2==pNo && ppa!='0'){
					flag = false;
				}else if(pNo2==pNo && (ppa=='0' || ppa=='')){
					assignAmount += parseFloat(paymentItems.getAt(k).get('assignamount'));
				}
			}
			if(flag){
				for(var j=0;j<paymentCbills.getCount();j++){
					if(paymentCbills.getAt(j).get('project_no')==pNo){		// 且该合同号与付款清票的合同号相同
						var clearamount2 = parseFloat(paymentCbills.getAt(j).get('clearamount2'));		// 清账金额
						assignAmount -= clearamount2;
					}
				}
			}
		}
		if(assignAmount < 0){
			assignAmount = 0;
		}
		paymentItems.getAt(i).set('prepayamount', round(assignAmount,2));
	}
	ImportPaymentCbill_grid.recalculation();
}

/**
 * 清除发票行项目的分配金额
 * @return
 */
function _clearBillValue(){
	for (var k = 0;k<ImportPaymentCbill_store.getCount();k++){
		var record = ImportPaymentCbill_store.getAt(k);
		record.set('clearamount2','0');
	}
	for(var i=0; i<ImportPaymentItem_store.getCount(); i++){
		var record = ImportPaymentItem_store.getAt(i);
		record.set('prepayamount','0');
	}
}

/**
 * 清除分配
 * @return
 */
function _clearassignImportPayment(){
	_getMainFrame().Ext.MessageBox.show({
		title:'系统提示',
	    msg: '您选择了【清除分配】操作，是否确定继续该操作？',
		buttons: {yes:Txt.ok, no:Txt.cancel},
		icon: Ext.MessageBox.QUESTION,
		fn:function(buttonid){
		    if (buttonid == 'yes'){
		    	_clearBillValue();
		    	_getMainFrame().showInfo("清除分配成功！");
		    	ImportPaymentCbill_grid.recalculation();
		    }
		}
	});
}

/**
 * @创建作者：邱杰烜
 * @创建日期：2010-10-08
 * 清除金额分配、清票、银行页签下金额为0的数据
 */
function _clearAllZeroAmount(){
	/*
	 * 清空付款金额分配中"分配金额"为0的数据
	 */
	for(var i=ImportPaymentItem_store.getCount()-1;i>=0;i--){
		var amount = ImportPaymentItem_store.getAt(i).get('assignamount');
		if(amount==0 || amount==''){
			ImportPaymentItem_store.removeAt(i);
		}
	}
	/*
	 * 清空付款清票中"清账金额"为0的数据
	 */
	for(var i=ImportPaymentCbill_store.getCount()-1;i>=0;i--){
		var amount = ImportPaymentCbill_store.getAt(i).get('clearamount2');
		if(amount==0 || amount==''){
			ImportPaymentCbill_store.removeAt(i);
		}
	}
	/*
	 * 清空付款银行中"付款金额"为0的数据
	 */
	for(var i=ImportPayBankItem_store.getCount()-1;i>=0;i--){
		var amount = ImportPayBankItem_store.getAt(i).get('payamount');
		if(amount==0 || amount==''){
			ImportPayBankItem_store.removeAt(i);
		}
	}
}

/**
 * 查看信用额度
 * @return
 */
function _viewCreditImportPayment(){
	if (ImportPaymentItem_store.getCount() <= 0){
		_getMainFrame().showInfo('请分配金额!');
		
		return false;
	}
	
	if(div_supplier_sh_sh.getValue()==''){
        _getMainFrame().showInfo("请选择一个供应商！");
        return;
	}

	var providerId = div_supplier_sh_sh.getValue();
	var projectId = ImportPaymentItem_store.getAt(0).get('project_no');
	var value = parseFloat(document.getElementById('ImportPayment.applyamount').value) * parseFloat(document.getElementById('ImportPayment.closeexchangerat').value);
	var conn = Ext.lib.Ajax.getConnectionObject().conn; 
	conn.open("POST", contextPath+'/xdss3/payment/importPaymentController.spr?action=checkCredit&providerid='+ providerId + 
			'&projectno='+ projectId +
			'&value='+ round(value,2), false);
	conn.send(null);
	var jsonData = Ext.util.JSON.decode(conn.responseText);
	var andFlag = jsonData.result.split("&");
	if (andFlag[0] == 'false' && andFlag[1] == 'false'){
		_getMainFrame().showInfo('该立项没有授信!');
	}else{
		_getMainFrame().showInfo(andFlag[0]);
	}
}

/**
 * 模拟凭证
 * @return
 */
function _simulateImportPayment(){}

/**
 * 现金日记帐
 * @return
 */
function _bookofaccountImportPayment(){}

/**
 * 同步所选供应商未清数据
 * @param sjson
 * @return
 */
function supplierHelpCallBack(sjson){
	Ext.Ajax.request({
        url : contextPath+'/xdss3/payment/importPaymentController.spr?action=_synchronizeUnclearVendor&privadeid=' + div_supplier_sh_sh.getValue(),
        success : function(xhr){
        },
        scope : this
    });
}

/**
 * 重分配提交
 * @return
 */
function _submitForReassignImportPayment(){
	if(isCreateCopy){
		document.getElementById("ImportPayment.paymentid").value = "";
	}
	if(_presubmitProcessImportPayment()){
		var param = Form.serialize('mainForm');	
		param = param + "&" + getAllImportPaymentItemGridData();
		param = param + "&" + getAllImportPaymentCbillGridData();
		param = param + "&" + getAllImportPayBankItemGridData();
		param = param + "&" + getAllImportDocuBankItemGridData();
		param = param + "&" + getAllBillPayReBankItemGridData();
		//param = param + "&" + Form.serialize('importSettSubjForm');		
		//param = param + "&" + Form.serialize('importFundFlowForm');		
		param = param + "&" + getAllImportRelatPaymentGridData();
		        		param = param + "&"+ Form.serialize('workflowForm');
		new AjaxEngine(contextPath +'/xdss3/payment/importPaymentController.spr?action=_submitForReassign', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	  }
	  _postsubmitProcessImportPayment();
}

/***********************************************************************/

/**
 * @修改作者：yanghancai
 * @修改日期：2010-09-27
 * 为申请金额添加触发事件
 */
/***********************************************************************/
Ext.getDom('ImportPayment.applyamount').onchange = function(){
	var applyamount = Ext.getDom('ImportPayment.applyamount').value;
	Ext.getDom('ImportPayment.factamount').value = applyamount;			// 设置实际付款金额
}

/**
*检查清账金额是否跟收款行项目的金额一致
**/
function checkAmount(){
	var sumclearamount = 0;
	var sumitemamount = 0;
	
	for(var k = 0;k<ImportPaymentCbill_store.getCount();k++){
		var record = ImportPaymentCbill_store.getAt(k);		
		sumclearamount += parseFloat(record.get('clearamount2'));
	}
	for(var i=0; i<ImportPaymentItem_store.getCount(); i++){
		var record = ImportPaymentItem_store.getAt(i);
		sumitemamount += (parseFloat(record.get('assignamount'))-parseFloat(record.get('prepayamount')));
		
		if(parseFloat(record.get('prepayamount'))<0){
				return "分配金额为负数时，预付款不能为负数";
		}
	}
	
	
	if(round(sumclearamount,2) !=round(sumitemamount,2) ){
		return "清账金额合计"+round(sumclearamount,2)+"必须等于分配金额合计-保证金金额合计-预收款金额合计" + round(sumitemamount,2);
	}
	return "";
}


/**
 *  增加还押汇银行
 */
function _addReBankBillPayReBankItem()
{

	
}

function _deletesBillPayReBankItem()
{
	
}
