/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月19日 10点44分13秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象内贸付款(HomePayment)增加页面用户可编程扩展JS文件
 */

Ext.onReady(function(){
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-16
	 * 隐藏所有页签的"增加行"、"删除行"按钮
	 */
	/******************************************************************************/
	var toolbar01 = HomePaymentItem_grid.getTopToolbar();
	toolbar01.items.get('HomePaymentItemaddRow').hide();			// 隐藏"增加行"按钮
	toolbar01.items.get('HomePaymentItemdeleteRow').hide();			// 隐藏"删除行"按钮
	var toolbar02 = HomePaymentCbill_grid.getTopToolbar();
	toolbar02.items.get('HomePaymentCbilladdRow').hide();			// 隐藏"增加行"按钮
	toolbar02.items.get('HomePaymentCbilldeleteRow').hide();		// 隐藏"删除行"按钮
	var toolbar03 = HomePayBankItem_grid.getTopToolbar();
	toolbar03.items.get('HomePayBankItemaddRow').hide();			// 隐藏"增加行"按钮
	toolbar03.items.get('HomePayBankItemdeleteRow').hide();			// 隐藏"删除行"按钮
	var toolbar04 = HomeDocuBankItem_grid.getTopToolbar();
	toolbar04.items.get('HomeDocuBankItemaddRow').hide();			// 隐藏"增加行"按钮
	toolbar04.items.get('HomeDocuBankItemdeleteRow').hide();		// 隐藏"删除行"按钮
	var toolbar05 = HomePaymentRelat_grid.getTopToolbar();
	toolbar05.items.get('HomePaymentRelataddRow').hide();			// 隐藏"增加行"按钮
	toolbar05.items.get('HomePaymentRelatdeleteRow').hide();		// 隐藏"删除行"按钮
	var tb = Ext.getCmp('tabs');
	var pt = dict_div_paymenttype_dict.getValue();					// 付款类型
	if(pt=='06' || pt=='07' || pt=='08' || pt=='09'){	// 若付款方式为"信用证(06)、银承(07)、现金(08)、背书(09)"时不能有银行
		tb.hideTabStripItem('homePayBankItemTab');					// 隐藏（页签）内贸付款银行	
	}
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
	div_supplier_sh_sh.on("change",function(){
		//alert(div_supplier_sh_sh.getValue());
		var supplierid = div_supplier_sh_sh.getValue();
		Ext.Ajax.request({
		url : contextPath + '/xdss3/payment/homePaymentController.spr?action=getSupplierByID&supplierid=' + supplierid,
	      success : function(xhr){
	          	if(xhr.responseText){
	          		var responseUtil = new AjaxResponseUtils(xhr.responseText);
	          		var result = responseUtil.getCustomField("coustom");
	          		if(result.collectbankid != undefined){
	          			Ext.getDom('HomePayment.collectbankid').value = result.collectbankid;
	          		}
	          		if(result.collectbankacc != undefined){
	          			Ext.getDom('HomePayment.collectbankacc').value = result.collectbankacc;
	          		}
	             }
	      },
      scope : this
		});
	});
	
	mainInit();
	
	div_supplier_sh_sh.callBack=function(data){
		//supplierHelpCallBack(data);
	}
	
	div_currency_sh_sh.callBack=function(data){
		div_factcurrency_sh_sh.setValue(div_currency_sh_sh.getValue(),function(){
	    });
	};
	
	HomePayBankItem_grid.on("afteredit", homePayBankItemAfterEdit, HomePayBankItem_grid);
	
	function homePayBankItemAfterEdit(obj){
    	var row = obj.record;
        var colName = obj.field;
        var colValue = row.get(colName);
        
        if (colName == 'payamount'){
        	var exchangerate = 0;
            
            if (Utils.isEmpty(document.getElementById('HomePayment.exchangerate').value) == true){
    			exchangerate = 0;
            }else{
            	exchangerate = document.getElementById('HomePayment.exchangerate').value;
            }
            
            var mulValue = parseFloat(colValue) * parseFloat(exchangerate);
        	row.set('payamount2',round(mulValue,2));
        }
	}
	
	var HomePayment_exchangerate = document.getElementById("HomePayment.exchangerate");
	
	HomePayment_exchangerate.onblur = function exchangerate(){    
		for (var j = 0;j<HomePayBankItem_store.getCount();j++){
			var record = HomePayBankItem_grid.getStore().getAt(j);
			
			var exchangerate = 0;
			if (Utils.isEmpty(document.getElementById('HomePayment.exchangerate').value) == true){
    			exchangerate = 0;
            }else{
            	exchangerate = document.getElementById('HomePayment.exchangerate').value;
            }
			 
			var payamount2 = HomePayBankItem_store.getAt(j).get('payamount');
			if (Utils.isNumber(HomePayBankItem_store.getAt(j).get('payamount')) == false){
				payamount2 = 0;
            }else{
            	payamount2 = HomePayBankItem_store.getAt(j).get('payamount');
            }
			
            var mulValue = parseFloat(payamount2) * parseFloat(exchangerate);
			record.set('payamount2',round(mulValue,2));
		}
	}
	
	HomeDocuBankItem_grid.on("afteredit", homeDocuBankItemAfterEdit, HomeDocuBankItem_grid);
	
	function homeDocuBankItemAfterEdit(obj){
		var row = obj.record;
        var colName = obj.field;
        var colValue = row.get(colName);

        if (colName == 'docuarypayamount'){
        	var exchangerate = 0;
            
            if (Utils.isEmpty(document.getElementById('HomePayment.doctaryrealrate').value) == true){
    			exchangerate = 0;
            }else{
            	exchangerate = document.getElementById('HomePayment.doctaryrealrate').value;
            }
            
            var mulValue = parseFloat(colValue) * parseFloat(exchangerate);
        	row.set('docuarypayamoun2',round(mulValue,2));
        }
	}
	
	
	
	var HomePayment_doctaryrealrate = document.getElementById("HomePayment.doctaryrealrate");
	
	HomePayment_doctaryrealrate.onblur = function doctaryrealrate(){    
		for (var j = 0;j<HomeDocuBankItem_store.getCount();j++){
			var record = HomeDocuBankItem_grid.getStore().getAt(j);
			
			var exchangerate = 0;
			if (Utils.isEmpty(document.getElementById('HomePayment.doctaryrealrate').value) == true){
    			exchangerate = 0;
            }else{
            	exchangerate = document.getElementById('HomePayment.doctaryrealrate').value;
            }
			 
			var payamount2 = 0;
			if (Utils.isNumber(HomeDocuBankItem_store.getAt(j).get('docuarypayamount')) == false){
				payamount2 = 0;
            }else{
            	payamount2 = HomeDocuBankItem_store.getAt(j).get('docuarypayamount');
            }
			
			var mulValue = parseFloat(payamount2) * parseFloat(exchangerate);
			record.set('docuarypayamoun2',round(mulValue,2));
		}
	}

	HomePaymentItem_grid.on("afteredit", homePaymentItemAfterEdit, HomePaymentItem_grid);
	
	function homePaymentItemAfterEdit(obj){
		var row = obj.record;
        var colName = obj.field;
        var colValue = row.get(colName);

        if (colName == 'assignamount'){
        	var exchangerate = 0;
            
            if (Utils.isEmpty(document.getElementById('HomePayment.closeexchangerat').value) == true){
    			exchangerate = 0;
            }else{
            	exchangerate = document.getElementById('HomePayment.closeexchangerat').value;
            }
        	
            var mulValue = parseFloat(colValue) * parseFloat(exchangerate);
        	row.set('assignamount2',round(mulValue,2));
        	row.set('prepayamount',row.get('assignamount'));
        }
	}
	
	var HomePayment_closeexchangerat = document.getElementById("HomePayment.closeexchangerat");
	
	HomePayment_closeexchangerat.onblur = function closeexchangerat(){    
		for (var j = 0;j<HomePaymentItem_store.getCount();j++){
			var record = HomePaymentItem_grid.getStore().getAt(j);
			
			var exchangerate = 0;
			if (Utils.isEmpty(document.getElementById('HomePayment.closeexchangerat').value) == true){
    			exchangerate = 0;
            }else{
            	exchangerate = document.getElementById('HomePayment.closeexchangerat').value;
            }

			var payamount2 = 0;
			if (Utils.isNumber(HomePaymentItem_store.getAt(j).get('assignamount')) == false){
				payamount2 = 0;
            }else{
            	payamount2 = HomePaymentItem_store.getAt(j).get('assignamount');
            }
			
			var mulValue = parseFloat(payamount2) * parseFloat(exchangerate);
			record.set('assignamount2',round(mulValue,2));
		}
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-15
	 * 加入申请金额失去焦点且付款方式为CNY时触发的功能
	 * ------------------------------ 修改记录 ------------------------------
	 * 邱杰烜 2010-09-28 将付款金额分配的合同号与立项号渲染成链接形式，并在点击时弹出出详情查看窗口
	 */
	/******************************************************************************/
	div_currency_sh_sh.on('select',function(e,n,o){
		div_factcurrency_sh_sh.setValue(this.getValue());
		if(this.getValue()=='CNY'){
			Ext.getDom('HomePayment.factamount').value = Ext.getDom('HomePayment.applyamount').value;
		}
	});
	// 申请金额失去焦点且付款方式为CNY时触发
	Ext.getDom('HomePayment.applyamount').onblur = function(){
		if(div_currency_sh_sh.getValue()=='CNY'){
			// 将申请金额填入实际付款金额
			Ext.getDom('HomePayment.factamount').value = this.value;
		}
	}
	/*
	 * 将付款金额分配的合同号与立项号渲染成链接形式，并在点击时弹出出详情查看窗口
	 */
	var contIndex = HomePaymentItem_grid.getColumnModel().findColumnIndex('contract_no');
	var projIndex = HomePaymentItem_grid.getColumnModel().findColumnIndex('project_no');
	var relaIndex = HomePaymentRelat_grid.getColumnModel().findColumnIndex('relatedno');
	HomePaymentItem_grid.getColumnModel().setRenderer(contIndex,function(contNo){
		return '<a href="#" onclick="viewContractInfo(\''+contNo+'\');"><u style="border-bottom:1px;">'+contNo+'</u></a>';
	});
	HomePaymentItem_grid.getColumnModel().setRenderer(projIndex,function(projNo){
		return '<a href="#" onclick="viewProjectInfo(\''+projNo+'\');"><u style="border-bottom:1px;">'+projNo+'</u></a>';
	});
	HomePaymentRelat_grid.getColumnModel().setRenderer(relaIndex,function(relaNo){
		return '<a href="#" onclick="viewRelatedInfo(\''+relaNo+'\');"><u style="border-bottom:1px;">'+relaNo+'</u></a>';
	});
	/******************************************************************************/
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
 * 相关单据信息查看 
 */
function viewRelatedInfo(relaNo){
	var relaUrl = contextPath + '/xdss3/payment/homePaymentController.spr?action=viewRelatedInfo&relatedNo=' + relaNo;
	if(relaNo.trim()!=''){
		_getMainFrame().maintabs.addPanel('原内贸付款单信息','',relaUrl);
		   
	}
}

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
 * 初始化界面信息
 */
function mainInit(){
	
	dict_div_paymenttype_dict.setEditable(false);
	dict_div_pay_type_dict.setEditable(false);
	
	var pt = dict_div_paymenttype_dict.getValue();
	if(pt == '09'){
		Ext.getCmp('_bookofaccount').hide();
	}
	
	if (strRoleType == "1"){
		
		var maintab = Ext.getCmp("tabs");
		
		maintab.hideTabStripItem('homeDocuBankItemTab');
		//maintab.hideTabStripItem('homeSettSubjTab');
		//maintab.hideTabStripItem('homeFundFlowTab');
		maintab.hideTabStripItem('homePaymentRelatTab');

		Ext.getCmp("_bookofaccount").hide();
		Ext.getCmp("_simulate").hide();
		Ext.getCmp("_submitForReassign").hide();
		
		var HomePaymentItem_grid_cm = HomePaymentItem_grid.getColumnModel();
		HomePaymentItem_grid_cm.setHidden(HomePaymentItem_grid_cm.findColumnIndex('assignamount2'),true);
		//HomePaymentItem_grid_cm.setHidden(HomePaymentItem_grid_cm.findColumnIndex('prepayamount'),true);
		
		var HomePayBankItem_grid_cm = HomePayBankItem_grid.getColumnModel();
		HomePayBankItem_grid_cm.setHidden(HomePayBankItem_grid_cm.findColumnIndex('cashflowitem'),true);
		HomePayBankItem_grid_cm.setHidden(HomePayBankItem_grid_cm.findColumnIndex('payamount2'),true);
		
		var HomeDocuBankItem_grid_cm = HomeDocuBankItem_grid.getColumnModel();
		HomeDocuBankItem_grid_cm.setHidden(HomeDocuBankItem_grid_cm.findColumnIndex('cashflowitem'),true);
		HomeDocuBankItem_grid_cm.setHidden(HomeDocuBankItem_grid_cm.findColumnIndex('docuarypayamoun2'),true);
	}
}

/**
 * 得到合同或立项的发票信息
 * @param strContractList
 * @param strProjectList
 * @return
 */
function _getContractOrProjectBillInfo(strContractList,strProjectList){
	Ext.Ajax.request({
        url : contextPath+'/xdss3/payment/homePaymentController.spr?action=_autoassign&contractList='+strContractList + '&projectList=' + strProjectList,
        success : function(xhr){
            if(xhr.responseText){
               var jsonData = Ext.util.JSON.decode(xhr.responseText);
               
               for(var j=0;j<jsonData.data.length;j++){
                   var data = jsonData.data[j];
                   
                   var isExists = false;
                   for (var k = 0;k<HomePaymentCbill_store.getCount();k++){
	           			if (HomePaymentCbill_store.getAt(k).get('billid') == data.billid){
	           				isExists = true;
	           				break;
	           			}
           			}
                   
                   if (isExists == false){
	                   var p = new Ext.data.Record(data);
	                   HomePaymentCbill_grid.stopEditing();
	                   HomePaymentCbill_grid.getStore().insert(0, p);
	                   HomePaymentCbill_grid.startEditing(0, 0);
	                   
	                   var record = HomePaymentCbill_grid.getStore().getAt(0);
	                   record.set('clearamount2','');
                   }
               }
           }
        },
        scope : this
    });
}

function _predeletesHomePaymentItem(){
	if (HomePaymentItem_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
			title:Txt.cue,
		    msg: Txt.homePaymentItem_Deletes_ConfirmOperation,
			buttons: {yes:Txt.ok, no:Txt.cancel},
			icon: Ext.MessageBox.QUESTION,
			fn:function(buttonid){
			    if (buttonid == 'yes'){
				var records = HomePaymentItem_grid.selModel.getSelections();
				for (var i=0;i<records.length;i++){
					
					/*手工修改*/
					for (var j=0;j<HomePaymentCbill_store.getCount();j++){
						if (records[i].get('contract_no') == HomePaymentCbill_store.getAt(j).get('contract_no')){
							var record = HomePaymentCbill_grid.getStore().getAt(j);
							
							HomePaymentCbill_store.remove(record);
							
							j--;
						}
					}
					/*手工修改*/
					
					HomePaymentItem_grid.getStore().remove(records[i]);
				}
			}
			}
		});
	}else{
		_getMainFrame().showInfo(Txt.importPaymentItem_Deletes_SelectToOperation);
	}
	return false;
}

function _postdeletesHomePaymentItem(){
	
}

function winPurchaseCallBack(jsonArrayData){
	//var contractList = "";
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j<HomePaymentItem_store.getCount();j++){
			if (HomePaymentItem_store.getAt(j).get('contract_no') == jsonArrayData[i].CONTRACT_NO){
				isExists = true;
				break;
			}
		}

		if (isExists == false){
			var p = new Ext.data.Record({
				client:'',
				paymentitemid:'',
				paymentno:'',
				billisclear:0,
				paymentid:'',
				contract_no:jsonArrayData[i].CONTRACT_NO,
				old_contract_no:jsonArrayData[i].EKKO_UNSEZ,
				pick_list_no:'',
				project_no:jsonArrayData[i].PROJECT_NO,
				project_no_text:jsonArrayData[i].PROJECT_NAME,
				contractamount:jsonArrayData[i].TOTAL,
				assignamount:0,
				prepayamount:0,
				assignamount2:0,
				goodsamount:0,
				ymat_group:jsonArrayData[i].WGBEZ
			});
			HomePaymentItem_grid.stopEditing();
			HomePaymentItem_grid.getStore().insert(0, p);
			HomePaymentItem_grid.startEditing(0, 0);
		}
	}
}

var searchPurchaseHelpWin = new Ext.SearchHelpWindow({
	shlpName : 'YHCONTRAPURCHASE',
	callBack : winPurchaseCallBack
});
   
/**
 *  增加采购合同
 */
function _addPurchaseHomePaymentItem()
{
   //方法执行体
	if(div_supplier_sh_sh.getValue()==''){
        _getMainFrame().showInfo("必须先选择[供应商]！");
        return;
	}
	searchPurchaseHelpWin.defaultCondition = "EKKO_LIFNR='" + div_supplier_sh_sh.getValue() + "'" + "  and DEPT_ID='" + div_dept_id_sh_sh.getValue() + "' " + "and order_state in('3','5','7','9')";
	searchPurchaseHelpWin.show();
}

    
function winPageCallBack(jsonArrayData){
	var contractList = '';
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j<HomePaymentItem_store.getCount();j++){
			if (HomePaymentItem_store.getAt(j).get('contract_no') == jsonArrayData[i].CONTRACT_NO){
				isExists = true;
				break;
			}
		}
		
		if (isExists == false){
			contractList = contractList + jsonArrayData[i].CONTRACT_NO + "|";
			var p = new Ext.data.Record({
				client:'',
				paymentitemid:'',
				paymentno:'',
				billisclear:0,
				paymentid:'',
				contract_no:jsonArrayData[i].CONTRACT_NO,
				old_contract_no:jsonArrayData[i].EKKO_UNSEZ,
				pick_list_no:'',
				project_no:jsonArrayData[i].PROJECT_NO,
				project_no_text:jsonArrayData[i].PROJECT_NAME,
				contractamount:jsonArrayData[i].TOTAL,
				assignamount:0,
				prepayamount:0,
				assignamount2:0,
				goodsamount:0
			});
			
			HomePaymentItem_grid.stopEditing();
			HomePaymentItem_grid.getStore().insert(0, p);
			HomePaymentItem_grid.startEditing(0, 0);
		}
	}

	if (contractList != ''){
		_getContractOrProjectBillInfo(contractList,'');
	}
}

var searchPageHelpWin = new Ext.SearchHelpWindow({
	shlpName : 'YHCONTRAPURCHASE',
	callBack : winPageCallBack
});
    
/**
 *  增加外部纸合同
 */
function _addPageContractHomePaymentItem()
{
   //方法执行体
	if(div_supplier_sh_sh.getValue()==''){
        _getMainFrame().showInfo("必须先选择[供应商]！");
        return;
	}
	
	searchPageHelpWin.defaultCondition = "EKKO_LIFNR='" + div_supplier_sh_sh.getValue() + "'";
	searchPageHelpWin.show();
}

    
function winProjectCallBack(jsonArrayData){
	//var projectList = '';
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j<HomePaymentItem_store.getCount();j++){
			if (HomePaymentItem_store.getAt(j).get('project_no') == jsonArrayData[i].PROJECT_NO &&
				HomePaymentItem_store.getAt(j).get('contract_no').trim()==''){
				isExists = true;
				break;
			}
		}
		
		if (isExists == false){
			//projectList = projectList + jsonArrayData[i].PROJECT_NO + '|';
			var p = new Ext.data.Record({
				client:'',
				paymentitemid:'',
				paymentno:'',
				billisclear:0,
				paymentid:'',
				contract_no:'',
				old_contract_no:'',
				pick_list_no:'',
				project_no:jsonArrayData[i].PROJECT_NO,
				project_no_text:jsonArrayData[i].PROJECT_NAME,
				contractamount:'',
				assignamount:0,
				prepayamount:0,
				assignamount2:0,
				goodsamount:0,
				ymat_group:jsonArrayData[i].WGBEZ
			});
			
			HomePaymentItem_grid.stopEditing();
			HomePaymentItem_grid.getStore().insert(0, p);
			HomePaymentItem_grid.startEditing(0, 0);
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
 *  增加立项
 */
function _addProjectHomePaymentItem(){
	//searchProjectHelpWin.defaultCondition = "PROVIDER_ID='" + div_supplier_sh_sh.getValue() + "'";
	searchProjectHelpWin.defaultCondition = "DEPT_ID='" + div_dept_id_sh_sh.getValue() + "' " + "and IS_AVAILABLE='1' and PROJECT_STATE in('3','8')";
	searchProjectHelpWin.show();
}
    
function _predeletesHomePaymentCbill(){
	return true;
}

function _postdeletesHomePaymentCbill(){}
    
function winBillClearItemPayCallBack(jsonArrayData){
	var billnoList = '';
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j<HomePaymentCbill_store.getCount();j++){
			if (HomePaymentCbill_store.getAt(j).get('billno') == jsonArrayData[i].INVOICE){
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
	        url : contextPath+'/xdss3/payment/homePaymentController.spr?action=_addBillInfo&billnolist='+billnoList ,
	        success : function(xhr){
	            if(xhr.responseText){
	               var jsonData = Ext.util.JSON.decode(xhr.responseText);

	               for(var j=0;j<jsonData.data.length;j++){
	                   var data = jsonData.data[j];
	                   
	                   var p = new Ext.data.Record(data);
	                   HomePaymentCbill_grid.stopEditing();
	                   HomePaymentCbill_grid.getStore().insert(0, p);
	                   HomePaymentCbill_grid.startEditing(0, 0);
	                   var record = HomePaymentCbill_grid.getStore().getAt(0);
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
 *  增加
 */
function _addbillHomePaymentCbill()
{
   //方法执行体
	if(div_supplier_sh_sh.getValue()==''){
        _getMainFrame().showInfo("必须先选择[供应商]！");
        return;
	}
	
	/**
	 * @修改作者：邱杰烜
	 * @修改日期：2010-08-30
	 * 在内贸付款增加“内贸付款清票”时添加搜索帮助的过滤条件
	 */
	/****************************************************************/
	var contractnos = "";
	var projectnos = "";
	for(var i=0;i<HomePaymentItem_grid.getStore().getCount();i++){
		var rec = HomePaymentItem_grid.getStore().getAt(i);
		
		if(rec.get('contract_no').trim() !=''){
			contractnos += "'"+rec.get('contract_no') + "',";
		}else{
			if(rec.get('project_no').trim() !=''){
				projectnos += "'"+rec.get('project_no') + "',";
			}
		}
	}
	if(contractnos==""){
		contractnos = "''";
	}else{
		contractnos = contractnos.substring(0,contractnos.length-1);
	}
	if(projectnos==""){
		projectnos = "''";
	}else{
		projectnos = projectnos.substring(0,projectnos.length-1);
	}
	
	var dc = "LIFNR="+div_supplier_sh_sh.getValue() + " and ((VERKF in ("+contractnos+")) or (LIXIANG in ("+projectnos+")))"  + " and ISCLEARED='0'";
	
	searchBillClearItemPayHelpWin.defaultCondition = dc;
	searchBillClearItemPayHelpWin.show();	
//	searchBillClearItemPayHelpWin.defaultCondition = "LIFNR='" + div_supplier_sh_sh.getValue() + "'";
//	searchBillClearItemPayHelpWin.show();
	/****************************************************************/
}

    

    
function winBankCallBack(jsonArrayData){
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j<HomePayBankItem_store.getCount();j++){
			if (HomePayBankItem_store.getAt(j).get('paybankaccount') == jsonArrayData[i].BANK_ACCOUNT){
				isExists = true;
				break;
			}
		}
		
		if (isExists == false){
			var bankRecvFields = new HomePayBankItem_fields({
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
			
			HomePayBankItem_grid.stopEditing();
			HomePayBankItem_grid.getStore().insert(0, bankRecvFields);
			HomePayBankItem_grid.startEditing(0, 0);
			var record = HomePayBankItem_grid.getStore().getAt(0);
			
			record.set('paybankname',jsonArrayData[i].BANK_NAME);
			record.set('paybankaccount',jsonArrayData[i].BANK_ACCOUNT);
			record.set('payamount','0');
			record.set('cashflowitem','151');
			record.set('cashflowitem_text','购买商品、接受劳务支付的现金');			
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
 *  增加
 */
function _addBankHomePayBankItem()
{
	//searchBankHelpWin.defaultCondition = "BANK_ACCOUNT<>' ' and Isusing='1' ";
    searchBankHelpWin.defaultCondition = "BANK_ACCOUNT<>' ' and Isusing='1' and bukrs=(select company_code from t_sys_dept where dept_id='"+div_dept_id_sh_sh.getValue()+"')";
	
   //方法执行体
	searchBankHelpWin.show();
}

    

    

/**
 *内贸付款行项目
 *批量删除
 */
function _predeletesHomePayBankItem()
{
	return true;
}

/**
 *内贸付款行项目
 *批量删除
 */
function _postdeletesHomePayBankItem()
{

}
    

    
  
function winDocuBankCallBack(jsonArrayData){
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j<HomeDocuBankItem_store.getCount();j++){
			if (HomeDocuBankItem_store.getAt(j).get('docuarybankacco') == jsonArrayData[i].BANK_ACCOUNT){
				isExists = true;
				break;
			}
		}
		
		if (isExists == false){
			var bankRecvFields = new HomePayBankItem_fields({
				client:'',
				docuaryitemid:'',
				paymentid:'',
				docuarybankid:'',
				cashflowitem:'',
				docuarybanksubj:'',
				docuarybankname:'',
				docuarybankacco:'',
				docuarypayamount:'',
				docuarypayamoun2:''
			});
			
			HomeDocuBankItem_grid.stopEditing();
			HomeDocuBankItem_grid.getStore().insert(0, bankRecvFields);
			HomeDocuBankItem_grid.startEditing(0, 0);
			var record = HomeDocuBankItem_grid.getStore().getAt(0);
			
			record.set('docuarybankname',jsonArrayData[i].BANK_NAME);
			record.set('docuarybankacco',jsonArrayData[i].BANK_ACCOUNT);
			record.set('docuarypayamount','0');
			record.set('cashflowitem','');
			record.set('docuarypayamoun2','0');
			record.set('docuaryitemid','');
			record.set('docuarybanksubj','');
			record.set('docuarybankid',jsonArrayData[i].BANK_ID);
			record.set('client','');
			record.set('paymentid','');
		}
	}
}

var searchDocuBankHelpWin = new Ext.SearchHelpWindow({
	shlpName : 'YHBANKINFO',
	callBack : winDocuBankCallBack
});                  
   
/**
 *  增加
 */
function _addDocuBankHomeDocuBankItem()
{
   //方法执行体
	searchDocuBankHelpWin.show();
}

    

    

/**
 *内贸付款行项目
 *批量删除
 */
function _predeletesHomeDocuBankItem()
{
	return true;
}

/**
 *内贸付款行项目
 *批量删除
 */
function _postdeletesHomeDocuBankItem()
{

}
    

    
  
                  
   
  
                  
   
    
    
  
                  
   
/**
 * grid 上的 创建按钮调用方法 
 * 新增内贸付款,付款相关单据
 */
function _precreateHomePaymentRelat()
{
   return true;
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增内贸付款,付款相关单据
 */
function _postcreateHomePaymentRelat()
{

}
	 
    

    
/**
 *内贸付款行项目
 *复制创建
 */
function _precopyCreateHomePaymentRelat()
{
    return true;
}

/**
 *内贸付款行项目
 *复制创建
 */
function _postcopyCreateHomePaymentRelat()
{

}
    

    

/**
 *内贸付款行项目
 *批量删除
 */
function _predeletesHomePaymentRelat()
{
	return true;
}

/**
 *内贸付款行项目
 *批量删除
 */
function _postdeletesHomePaymentRelat()
{

}
    

    
/**
 * 判断合同发配项信息
 * @return
 */
function _chectItemInfo(){
	var isAssingItemValue = false;
	var showText = '';
	for (var j = 0;j<HomePaymentItem_store.getCount();j++){
		if (Utils.isNumber(HomePaymentItem_store.getAt(j).get('assignamount')) == false){
			isAssingItemValue = true;
			
			if (Utils.isEmpty(HomePaymentItem_store.getAt(j).get('contract_no')) == false){
				showText = '合同号为:' + HomePaymentItem_store.getAt(j).get('contract_no') + '的分配金额为空!';
			}else if (Utils.isEmpty(HomePaymentItem_store.getAt(j).get('project_no')) == false){
				showText = '立项号为:' + HomePaymentItem_store.getAt(j).get('project_no') + '的分配金额为空!';
			}
			break;
		}                                                               
	}
	
	if (isAssingItemValue == true){
		_getMainFrame().showInfo(showText);
		return false;
	}
	
	return true;
}  
          
/**
 * 判断发票上的信息
 * @return
 */
function _checkBillInfo(){
	
	var isAssignBillValue = false;
	var showText = '';
	var isDisaffinityCurrency = false;

	for (var k = 0;k<HomePaymentCbill_store.getCount();k++){
		var firstCurrency = HomePaymentCbill_store.getAt(0).get('currency');
		
		if (HomePaymentCbill_store.getAt(k).get('clearamount2') == '' || HomePaymentCbill_store.getAt(k).get('clearamount2') == '0'){
			isAssignBillValue = true;
			showText = '发票号为:' + HomePaymentCbill_store.getAt(k).get('billno') + '的分配金额为空或为零!';
			break;
		}
		
		if (firstCurrency != HomePaymentCbill_store.getAt(k).get('currency')){
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

	return true;
}
          
/**
 * 实际的保存动作
 * @return
 */
function _factSave(){
	if(isCreateCopy){
		document.getElementById("HomePayment.paymentid").value = "";
	}
	
	var param = Form.serialize('mainForm');	
	           
     
	if(isCreateCopy)
	{
		param = param + ""+ getAllHomePaymentItemGridData();
	}
	else
	{
		param = param + ""+ getHomePaymentItemGridData(); 
	}
		        	           
     
	if(isCreateCopy)
	{
		param = param + ""+ getAllHomePaymentCbillGridData();
	}
	else
	{
		param = param + ""+ getHomePaymentCbillGridData(); 
	}
		        	           
     
	if(isCreateCopy)
	{
		param = param + ""+ getAllHomePayBankItemGridData();
	}
	else
	{
		param = param + ""+ getHomePayBankItemGridData(); 
	}
		        	           
     
	if(isCreateCopy)
	{
		param = param + ""+ getAllHomeDocuBankItemGridData();
	}
	else
	{
		param = param + ""+ getHomeDocuBankItemGridData(); 
		}
			        	           
 
		//param = param + "&" + Form.serialize('homeSettSubjForm');		
           
 
		//param = param + "&" + Form.serialize('homeFundFlowForm');		
       
     
	if(isCreateCopy)
	{
		param = param + ""+ getAllHomePaymentRelatGridData();
	}
	else
	{
		param = param + ""+ getHomePaymentRelatGridData(); 
	}
	
	new AjaxEngine(contextPath + '/xdss3/payment/homePaymentController.spr?action=_saveOrUpdate', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
}

/**
 * 处理预付款金额字段
 * @return
 */
function _dealPrepayamount(){
	var allItemAssignValue = 0;
	for (var j = 0;j<HomePaymentItem_store.getCount();j++){
		var ItemAssignValue = HomePaymentItem_store.getAt(j).get('assignamount');
		allItemAssignValue = parseFloat(allItemAssignValue) + parseFloat(ItemAssignValue);
		var BillAssignValue = 0;
		
		if (Utils.isEmpty(HomePaymentItem_store.getAt(j).get('contract_no')) == false){
			for (var k = 0;k<HomePaymentCbill_store.getCount();k++){
				if (Utils.isEmpty(HomePaymentItem_store.getAt(j).get('contract_no')) == false &&
					Utils.isEmpty(HomePaymentCbill_store.getAt(k).get('contract_no')) == false &&
					HomePaymentItem_store.getAt(j).get('contract_no') == HomePaymentCbill_store.getAt(k).get('contract_no')){
					BillAssignValue = parseFloat(BillAssignValue) + parseFloat(HomePaymentCbill_store.getAt(k).get('clearamount2'));
				}
			}
		}else if (Utils.isEmpty(HomePaymentItem_store.getAt(j).get('project_no')) == false){
			for (var k = 0;k<HomePaymentCbill_store.getCount();k++){
				if (Utils.isEmpty(HomePaymentItem_store.getAt(j).get('contract_no')) == true &&
					Utils.isEmpty(HomePaymentCbill_store.getAt(k).get('contract_no')) == true &&
					HomePaymentItem_store.getAt(j).get('project_no') == HomePaymentCbill_store.getAt(k).get('project_no')){
					BillAssignValue = parseFloat(BillAssignValue) + parseFloat(HomePaymentCbill_store.getAt(k).get('clearamount2'));
				}
			}
		}
		
		var record = HomePaymentItem_grid.getStore().getAt(j);
		//record.set('prepayamount',parseFloat(ItemAssignValue)-parseFloat(BillAssignValue));
		//record.set('assignamount2',parseFloat(ItemAssignValue) * parseFloat(document.getElementById('HomePayment.closeexchangerat').value));
		//record.set('goodsamount',parseFloat(ItemAssignValue));
	}
	
	//判断申请金额和实际公配金额是不是一致
	if ( round(parseFloat(allItemAssignValue),2) == round(parseFloat(document.getElementById('HomePayment.applyamount').value),2) ){
		_factSave();
		return true;
	}else{
		var showText = '申请金额为：'+document.getElementById('HomePayment.applyamount').value + '\n实际分配金额为：' + allItemAssignValue+'\n两个金额不一致，是否继续？';
//		_getMainFrame().Ext.MessageBox.show({
//			title:'系统提示',
//		    msg: showText,
//			buttons: {yes:Txt.ok, no:Txt.cancel},
//			icon: Ext.MessageBox.QUESTION,
//			fn:function(buttonid){
//			    if (buttonid == 'yes'){
//			    	_factSave();
//			    }else{
//			    	return false;
//			    }
//			}
//		});
		if(confirm(showText)){
			_factSave();
		}else{
			return false;
		}
	}
}

/**
 * 检查背书时的银行承兑汇票号信息
 * @return
 */
function _checkDraft(){
	if (dict_div_paymenttype_dict.getValue() == '09' ){
		var conn = Ext.lib.Ajax.getConnectionObject().conn; 
		conn.open("POST", contextPath+'/xdss3/payment/homePaymentController.spr?action=_checkDraftInfo&draftinfo='+
				document.getElementById('HomePayment.draft').value + '&paymentid='+
				document.getElementById('HomePayment.paymentid').value,false);
		conn.send(null);
		var jsonData = Ext.util.JSON.decode(conn.responseText);
		
		if (jsonData.result == '-1'){
			return true;
		}else{
			_getMainFrame().showInfo(jsonData.text);
			return false;
		}
	}
	
	return true;
}

/**
 * 检查客户信用额度、是否有立项、是否选择了供应商
 * @return
 */
function _checkCredit(){
	if(div_supplier_sh_sh.getValue()==''){
        _getMainFrame().showInfo("请选择一个供应商！");
        return false;
	}
	if (HomePaymentItem_store.getCount() <= 0){
		_getMainFrame().showInfo('请选择一个立项信息!');
		return false;
	}
	var providerId = div_supplier_sh_sh.getValue();
	var projectId = HomePaymentItem_store.getAt(0).get('project_no');
	var value = parseFloat(document.getElementById('HomePayment.applyamount').value) * parseFloat(document.getElementById('HomePayment.closeexchangerat').value);
		

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
	
	if (HomePaymentItem_store.getCount() <= 0){
		_getMainFrame().showInfo('请分配金额!');
		return false;
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
	for (var k = 0;k<HomePayBankItem_store.getCount();k++){
		if (HomePayBankItem_store.getAt(k).get('payamount') == '' || HomePayBankItem_store.getAt(k).get('payamount') == '0'){
			isAssignBankValue = true;
			showText = '付款银行为:' + HomePayBankItem_store.getAt(k).get('paybankname') + '的分配金额为空或为零!';
			break;
		}else{
			assignBankValue = parseFloat(assignBankValue) + parseFloat(HomePayBankItem_store.getAt(k).get('payamount'));
			
			//var record = HomePayBankItem_grid.getStore().getAt(k);
			//record.set('payamount2',parseFloat(HomePayBankItem_store.getAt(k).get('payamount')) * parseFloat(document.getElementById('HomePayment.closeexchangerat').value));
		}
	}
	
	if (isAssignBankValue == true){
		_getMainFrame().showInfo(showText);
		return false;
	}
	
	if (HomePayBankItem_store.getCount() > 0){
		if (round(assignBankValue,2) != round(Ext.getDom('HomePayment.applyamount').value,2)){
			showText = '实际申请金额为：'+ round(Ext.getDom('HomePayment.applyamount').value,2) + '\n银行分配金额为：'+ round(assignBankValue,2) + '\n两个金额不一致，是否继续？' ;
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
	for (var k = 0;k<HomeDocuBankItem_store.getCount();k++){
		if (HomeDocuBankItem_store.getAt(k).get('docuarypayamount') == '' || HomeDocuBankItem_store.getAt(k).get('docuarypayamount') == '0'){
			isAssignBankValue = true;
			showText = '付款银行为:' + HomeDocuBankItem_store.getAt(k).get('docuarybankname') + '的分配金额为空或为零!';
			break;
		}else{
			assignBankValue = parseFloat(assignBankValue) + parseFloat(HomeDocuBankItem_store.getAt(k).get('docuarypayamount'));
			
			var record = HomeDocuBankItem_grid.getStore().getAt(k);
			record.set('docuarypayamoun2',parseFloat(HomeDocuBankItem_store.getAt(k).get('docuarypayamount')) * parseFloat(document.getElementById('HomePayment.closeexchangerat').value));
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
	var paymentCbills = HomePaymentCbill_grid.getStore();
	for(var j=0;j<paymentCbills.getCount();j++){
		var unpaidamount = parseFloat(paymentCbills.getAt(j).get('unpaidamount'));	// 未付款
		var onroadamount = parseFloat(paymentCbills.getAt(j).get('onroadamount'));	// 在批金额
		var clearamount2 = parseFloat(paymentCbills.getAt(j).get('clearamount2'));	// 清账金额
		var difference = (unpaidamount - onroadamount - clearamount2);				// 未付款 - 在批金额 - 清账金额（差值）
		if(difference < 0){
			_getMainFrame().showInfo('未付款 - 在批金额不能小于清帐金额！');
			return false;
		}
	}
	return true;
}

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
	
	//检查背书时的银行承兑汇票号信息
	//if (_checkDraft() == false){
	//	return false;
	//}
	
	// 检查未收款-在批金额是否小于清帐金额
	if(!_checkClearAmount()){
		return false;
	}
	
	return true;
}
          
          
/**
 * 保存 
 * ------------------------------ 修改记录 ------------------------------
 * 邱杰烜 2010-09-14 保存时计算预付票款
 */
function _presaveOrUpdateHomePayment(){
	/*
	 * 清除金额分配、清票、银行页签下金额为0的数据
	 */
	_clearAllZeroAmount();
	
	/*
	 * 保存时计算预付票款
	 */
	_calcPrepayAmount();
	
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
	
	var size = HomePaymentCbill_grid.getStore().getCount();
	if(size !=0){
		var param = Form.serialize('mainForm');	  
		
		param = param + ""+ getAllHomePaymentItemGridData();         	          
	 	param = param + ""+ getAllHomePaymentCbillGridData(); 
	//	param = param +  "&"+ Form.serialize('settleSubjectForm'); 
 //		param = param +  "&"+ Form.serialize('fundFlowForm');		
		
		//ajax同步调用
		var url = contextPath + '/xdss3/payment/homePaymentController.spr?action=checkClearData&';	
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
function _postsaveOrUpdateHomePayment(){}

          

/**
 * 取消
 */
function _precancel(){
	return true;
}

/**
 * 取消
 */
function _postcancel(){}
          
/**
 * 提交
 * ------------------------------ 修改记录 ------------------------------
 * 邱杰烜 2010-09-14 提交时再计算一次预付票款
 */
function _presubmitProcessHomePayment(){
	/*
	 * 清除金额分配、清票、银行页签下金额为0的数据
	 */
	_clearAllZeroAmount();
	
	if (HomePaymentItem_store.getCount() <= 0){//付款金额页签里如果没有数据不允许提交
		_getMainFrame().showInfo('请分配金额!');
		return false;
	}
	/*
	 * 业务申请时，预计到货日、应收票日必填
	 */
	if(calendar_HomePayment_arrivegoodsdate.getValue()==''){
		_getMainFrame().showInfo('业务申请时[预计到货日]不能为空！');
		return false;
	}
	if(calendar_HomePayment_musttaketickleda.getValue()==''){
		_getMainFrame().showInfo('业务申请时[应收票日]不能为空！');
		return false;
	}
	/*
	 * 提交时再计算一次预付票款
	 */
	_calcPrepayAmount();
	
	//检查清账金额与分配金额是否相等
	var msg = checkAmount();
	if(msg!=""){
		 _getMainFrame().showInfo(msg);
		return false;
	}
	
	var size = HomePaymentCbill_grid.getStore().getCount();
	if(size !=0){
		var param = Form.serialize('mainForm');	  
		
		param = param + ""+ getAllHomePaymentItemGridData();         	          
	 	param = param + ""+ getAllHomePaymentCbillGridData(); 
	//	param = param +  "&"+ Form.serialize('settleSubjectForm'); 
 //		param = param +  "&"+ Form.serialize('fundFlowForm');		
		
		//ajax同步调用
		var url = contextPath + '/xdss3/payment/homePaymentController.spr?action=checkClearData&';	
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
	return true ;
}

/**
 * 提交
 */
function _postsubmitProcessHomePayment(){}

/**
 * 自动分配
 * --------------------------------- 修改记录 ----------------------------------
 * 邱杰烜 2010-09-09 重新改造自动分配功能（金额分配计算部分）
 */
function _autoassignHomePayment(){
	// 若付款清票页签下有数据，则不到数据库里带出该合同下的所有清票，直接给那些数据填写金额
	if(HomePaymentCbill_store.getCount() > 0){
		_autoAssignAmount();
		_getMainFrame().showInfo("自动分配成功！");
	// 否则根据付款分配页签下的所有合同与立项号去获取对应的所有发票， 然后再计算分配金额
	}else{
		_listPaymentCbills();
	}
	
}

/**
 * @修改作者：邱杰烜
 * @修改日期：2010-09-09
 * 根据付款分配页签下的所有合同与立项号去获取对应的所有清票
 */
/******************************************************************************/
function _listPaymentCbills(){
	var projectList = '';
	var contractList = '';
	for(var j = 0;j < HomePaymentItem_store.getCount();j++){
		var record = HomePaymentItem_store.getAt(j);
		if(record.get('contract_no').trim() != ''){
			contractList += record.get('contract_no') + '|';
		}else if(record.get('project_no').trim() != ''){
			projectList += record.get('project_no') + '|';
		}
	}
	Ext.Ajax.request({
		url : contextPath+'/xdss3/payment/homePaymentController.spr?action=_autoassign&contractList=' + 
			  contractList + '&projectList=' + projectList + '&lifnr=' + div_supplier_sh_sh.getValue(),
		success : function(xhr){
		if(xhr.responseText){
			var jsonData = Ext.util.JSON.decode(xhr.responseText);
			
			for(var j=0;j<jsonData.data.length;j++){
				var data = jsonData.data[j];
				
				var isExists = false;
				for (var k = 0;k<HomePaymentCbill_store.getCount();k++){
					if (HomePaymentCbill_store.getAt(k).get('billid') == data.billid){
						isExists = true;
						break;
					}
				}
				
				if (isExists == false){
					var p = new Ext.data.Record(data);
					HomePaymentCbill_grid.stopEditing();
					HomePaymentCbill_grid.getStore().insert(0, p);
					HomePaymentCbill_grid.startEditing(0, 0);
					
					var record = HomePaymentCbill_grid.getStore().getAt(0);
					record.set('clearamount2',0);
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
/******************************************************************************/

/**
 * @创建作者：邱杰烜
 * @创建日期：2010-09-09
 * 自动计算并分配金额
 */
/******************************************************************************/
function _autoAssignAmount(){
	var paymentItems = HomePaymentItem_grid.getStore();
	var paymentCbills = HomePaymentCbill_grid.getStore();
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
		assignAmount = parseFloat(paymentItems.getAt(i).get('assignamount'));
		if(!Utils.isEmpty(cNo)){	// 若为合同号
			for(var j=0;j<paymentCbills.getCount();j++){
				if(paymentCbills.getAt(j).get('contract_no')==cNo){		// 且该合同号与付款清票的合同号相同
					var unpaidamount = parseFloat(paymentCbills.getAt(j).get('unpaidamount'));		// 未付款
					var onroadamount = parseFloat(paymentCbills.getAt(j).get('onroadamount'));		// 在批金额
					var clearamount2 = parseFloat(paymentCbills.getAt(j).get('clearamount2'));		// 清账金额
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
					var unpaidamount = parseFloat(paymentCbills.getAt(j).get('unpaidamount'));		// 未付款
					var onroadamount = parseFloat(paymentCbills.getAt(j).get('onroadamount'));		// 在批金额
					var clearamount2 = parseFloat(paymentCbills.getAt(j).get('clearamount2'));		// 清账金额
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
	HomePaymentCbill_grid.recalculation();	
}
/******************************************************************************/

/**
 * @创建作者：邱杰烜
 * @创建日期：2010-09-14
 * 计算每个付款金额分配页签下的预付金额（阉割版的自动分配）
 */
/******************************************************************************/
function _calcPrepayAmount(){
	var paymentItems = HomePaymentItem_grid.getStore();
	var paymentCbills = HomePaymentCbill_grid.getStore();
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
					var clearamount2 = parseFloat(paymentCbills.getAt(j).get('clearamount2'));		// 清账金额
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
	HomePaymentCbill_grid.recalculation();	
}
/******************************************************************************/

          
/**
 *  清除分配
 */
function _clearassignHomePayment(){
   //方法执行体
	_getMainFrame().Ext.MessageBox.show({
		title:'系统提示',
	    msg: '您选择了【清除分配】操作，是否确定继续该操作？',
		buttons: {yes:Txt.ok, no:Txt.cancel},
		icon: Ext.MessageBox.QUESTION,
		fn:function(buttonid){
		    if (buttonid == 'yes'){
		    	_clearBillValue();
		    	_getMainFrame().showInfo("清除分配成功！");
		    	HomePaymentCbill_grid.recalculation();	
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
	for(var i=HomePaymentItem_store.getCount()-1;i>=0;i--){
		var amount = HomePaymentItem_store.getAt(i).get('assignamount');
		if(amount==0 || amount==''){
			HomePaymentItem_store.removeAt(i);
		}
	}
	/*
	 * 清空付款清票中"清账金额"为0的数据
	 */
	for(var i=HomePaymentCbill_store.getCount()-1;i>=0;i--){
		var amount = HomePaymentCbill_store.getAt(i).get('clearamount2');
		if(amount==0 || amount==''){
			HomePaymentCbill_store.removeAt(i);
		}
	}
	/*
	 * 清空付款银行中"付款金额"为0的数据
	 */
	for(var i=HomePayBankItem_store.getCount()-1;i>=0;i--){
		var amount = HomePayBankItem_store.getAt(i).get('payamount');
		if(amount==0 || amount==''){
			HomePayBankItem_store.removeAt(i);
		}
	}
}

/**
 * 清除发票行项目的分配金额
 * @return
 */
function _clearBillValue(){
	for (var k = 0;k<HomePaymentCbill_store.getCount();k++){
		var record = HomePaymentCbill_grid.getStore().getAt(k);
		record.set('clearamount2','0');
	}
	for(var i=0; i<HomePaymentItem_store.getCount(); i++){
		var record = HomePaymentItem_store.getAt(i);
		record.set('prepayamount','0');
	}
}
          
/**
 *  现金日记帐
 */
function _bookofaccountHomePayment(){}

/**
 * 查看信用额度
 * @return
 */
function _viewCreditHomePayment(){
	if (HomePaymentItem_store.getCount() <= 0){
		_getMainFrame().showInfo('请分配金额!');
		return false;
	}
	if(div_supplier_sh_sh.getValue()==''){
        _getMainFrame().showInfo("请选择一个供应商！");
        return;
	}
	var providerId = div_supplier_sh_sh.getValue();
	var projectId = HomePaymentItem_store.getAt(0).get('project_no');
	var value = parseFloat(document.getElementById('HomePayment.applyamount').value) * parseFloat(document.getElementById('HomePayment.closeexchangerat').value);

	var conn = Ext.lib.Ajax.getConnectionObject().conn; 
	conn.open("POST", contextPath+'/xdss3/payment/importPaymentController.spr?action=checkCredit&providerid='+ providerId + 
			'&projectno='+ projectId +
			'&value='+ round(value,2),false);
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
function _simulateHomePayment(){}

/**
 * 重分配提交
 * @return
 */
function _submitForReassignHomePayment(){
	if(isCreateCopy){
		document.getElementById("HomePayment.paymentid").value = "";
	}
	if(_presubmitProcessHomePayment()){
		var param = Form.serialize('mainForm');	
		param = param + "&" + getAllHomePaymentItemGridData();
		param = param + "&" + getAllHomePaymentCbillGridData();
		param = param + "&" + getAllHomePayBankItemGridData();
		param = param + "&" + getAllHomeDocuBankItemGridData();
		//param = param + "&" + Form.serialize('homeSettSubjForm');		
		//param = param + "&" + Form.serialize('homeFundFlowForm');		
		param = param + "&" + getAllHomePaymentRelatGridData();
		        		param = param + "&"+ Form.serialize('workflowForm');
		new AjaxEngine(contextPath +'/xdss3/payment/homePaymentController.spr?action=_submitForReassign', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	  }
	  _postsubmitProcessHomePayment();
}


/**
*检查清账金额是否跟收款行项目的金额一致
**/
function checkAmount(){
	var sumclearamount = 0;
	var sumitemamount = 0;
	
	for(var k = 0;k<HomePaymentCbill_store.getCount();k++){
		var record = HomePaymentCbill_store.getAt(k);		
		sumclearamount += parseFloat(record.get('clearamount2'));
	}
	for(var i=0; i<HomePaymentItem_store.getCount(); i++){
		var record = HomePaymentItem_store.getAt(i);
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
