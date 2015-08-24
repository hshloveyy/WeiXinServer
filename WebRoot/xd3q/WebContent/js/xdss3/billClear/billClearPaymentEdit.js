/**
 * Author(s):java业务平台代码生成工具 Date: 2010年06月23日 12点02分19秒 Copyright Notice:版权声明
 * 福建讯盟软件有限公司, 版权所有 违者必究
 * 
 * @(#) Description: <功能>主对象票清付款(BillClearPayment)编辑页面用户可编程扩展JS文件
 */

/**
 * 
 * 票清付款行项目,grid上的增加和删除操作(搜索帮助)回调函数
 */
/**
 * 当前流程状态
 */   
var ps = document.getElementById('BillClearPayment.processstate').value;

function winBillClearItemPayCallBack(jsonArrayData)
{
	var ischange = false;
	var vendortitleids = '';
	for (var i = 0; i < jsonArrayData.length; i++)
	{
		var num = BillClearItemPay_grid.getStore().find('titleid', jsonArrayData[i].VENDORTITLEID);
		if (num == -1)
		{
			ischange = true;
			vendortitleids +=  jsonArrayData[i].VENDORTITLEID + ",";
		}
	}
	
	if (ischange)
	{
		for (var i = 0; i < BillClearItemPay_grid.getStore().getCount(); i++)
		{
			var rec = BillClearItemPay_grid.getStore().getAt(i);
			vendortitleids +=  rec.get('titleid') + ",";
		}
		
		vendortitleids = vendortitleids.substring(0, vendortitleids.length - 1);
		// alert("ischange:" + ischange + ",vendortitleids:" + vendortitleids);
		//_assignAmount(vendortitleids);
		var billclearid = Ext.get("BillClearPayment.billclearid").dom.value;
		var url = 	contextPath + '/xdss3/billClear/billClearPaymentController.spr?action=getBillClearItemData';
		var param = {
						supplier		: div_supplier_sh_sh.getValue(),
						vendortitleids	: vendortitleids,
						billclearid :billclearid
					};
					
		
		new AjaxEngine(url, {method:"post", parameters: param, onComplete: getBillClearItemDataCallBackHandle});
	}
}

function getBillClearItemDataCallBackHandle(xhr){
	var promptMessagebox = new MessageBoxUtil();
	promptMessagebox.Close();
	
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
	}
}

/**
 * 
 * 票清付款行项目,grid上的增加和删除操作(搜索帮助)
 */
var searchBillClearItemPayHelpWin = new Ext.SearchHelpWindow(
		{
			shlpName	: 'YHUNCLEARBILL2',
			callBack	: winBillClearItemPayCallBack
		});

/**
 * 增加
 */
function _addBillClearItemPay()
{
	if (div_supplier_sh_sh.getValue() == '')
	{
		_getMainFrame().showInfo("必须先选择[供应商]！");
		return;
	}
	
	if (pre_addBillClearItemPay())
	{
		if (div_deptid_sh_sh.getValue() == '')
		{
			_getMainFrame().showInfo("必须先选择[部门]！");
			return;
		}
		var supplier = div_supplier_sh_sh.getValue();	
		var deptid = div_deptid_sh_sh.getValue();
		
		searchBillClearItemPayHelpWin.defaultCondition="LIFNR='" + supplier + "' and dept_id='" + deptid + "'   and ( ISCLEARED='0' or trim(ISCLEARED) is null ) and trim(INVOICE) is not null  and SHKZG='H' " ;
		// 方法执行体
		searchBillClearItemPayHelpWin.show();
	}
	post_addBillClearItemPay();
}

/**
 * 模拟凭证
 */
function _voucherPreviewBillClearPayment()
{
/**
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
**/	
	var msg = _checkFilds();
	if (msg != "")
	{
		showInfo(msg);
		return;
	}
	Ext.getCmp('_submitProcess').enable();  
	
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
        	Ext.get('currentWorkflowTask').mask();
			Ext.get('centercontent').mask();
			Ext.get(Ext.getCmp("tabs").getActiveTab().getItemId()).mask();
			
			Ext.getCmp("tabs").on('tabchange',function(t,p){
				Ext.get(p.getItemId()).mask();
			});
        	_getMainFrame().maintabs.addPanel('票清付款凭证预览', '', contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid=' + result,closeVoucherCallBack,true);
		}
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
		_submitProcessBillClearPayment(flag);		
		
	}
}


var voucherFlag = false; 
/**
 * 提交后的回调动作
 */
function _submitCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
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
				    	_getMainFrame().maintabs.addPanel2('凭证查看', contextPath+'/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.get('BillClearPayment.billclearid').dom.value);
					}else{
						_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
					}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.submitSuccess);
			_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		}
	}
}

function _assignAmount(vendortitleids)
{
	if (!vendortitleids)
		return;
	var billclearid = Ext.get("BillClearPayment.billclearid").dom.value;
	var paymentItemIds='';
	for (var i = 0; i < BillInPayment_grid.getStore().getCount(); i++)
	{
		var rec = BillInPayment_grid.getStore().getAt(i);
		paymentItemIds += "'" + rec.get('paymentitemid') + "',";
	}	
	paymentItemIds = paymentItemIds.substring(0, paymentItemIds.length - 1);
	
	var url = 	contextPath + '/xdss3/billClear/billClearPaymentController.spr?action=_autoAssign';
	var param = {
					supplier		: div_supplier_sh_sh.getValue(),
					vendortitleids	: vendortitleids,
					billclearid  :   billclearid,
					paymentItemIds :paymentItemIds
				};
				
	
	new AjaxEngine(url, {method:"post", parameters: param, onComplete: assignAmountCallBackHandle});
	
}

function assignAmountCallBackHandle(xhr){
	var promptMessagebox = new MessageBoxUtil();
	promptMessagebox.Close();
	
	if (xhr.responseText)
	{
		var jsonData = Ext.util.JSON.decode(xhr.responseText);
		_clearAssignBillClearPayment();
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
		for(var j=0;j<jsonData.data2.length;j++){
      		var num = BillInPayment_grid.getStore().find('paymentitemid',jsonData.data2[j].paymentitemid);
      		
      		if(num==-1){
	       		var p = new Ext.data.Record(jsonData.data2[j]);
				BillInPayment_grid.stopEditing();
				BillInPayment_grid.getStore().insert(0, p);
			    BillInPayment_grid.startEditing(0, 0);
      		}else{
      			var recs = BillInPayment_grid.getStore().query('paymentitemid',jsonData.data2[j].paymentitemid);
      			
      			recs.first().set('nowclearamount',jsonData.data2[j].nowclearamount);
      		}
      		
      	}
      	showTitle();
	}
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
		vendortitleids += rec.data.titleid + ",";
	}
	
	vendortitleids = vendortitleids.substring(0, vendortitleids.length - 1);
	// alert( "vendortitleids:" + vendortitleids);
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

var searchPaymentItemWin = new Ext.SearchHelpWindow(
		{
			shlpName	: 'YHPAYMENTITEM2',
			callBack	: winPaymentItemCallBack
		});

function winPaymentItemCallBack(jsonArrayData)
{
	var paymentItemIds = '';
	for (var i = 0; i < jsonArrayData.length; i++)
	{
		var num = BillInPayment_grid.getStore().find('paymentitemid', jsonArrayData[i].PAYMENTITEMID);
		if (num == -1)
			paymentItemIds += "'" + jsonArrayData[i].PAYMENTITEMID + "',";
	}
	paymentItemIds = paymentItemIds.substring(0, paymentItemIds.length - 1);
	var billclearid = Ext.get("BillClearPayment.billclearid").dom.value;
	if (paymentItemIds != '')
	{
		Ext.Ajax.request(
				{
					url		: contextPath + '/xdss3/billClear/billClearPaymentController.spr?action=getPaymentItemData',
					params	:
					{
						paymentItemIds	: paymentItemIds,
						billclearid :billclearid
					},
					success	: function(xhr)
					{
						if (xhr.responseText)
						{
							var jsonData = Ext.util.JSON.decode(xhr.responseText);
							
							// var billclearitemid =
							// BillClearItem_grid.getSelectionModel().getSelected().get('billclearitemid');
							// BillInPayment_grid.getStore().clearFilter();
							for (var j = 0; j < jsonData.data.length; j++)
							{
								var data = jsonData.data[j];
								// var nowclearedamount =
								// BillInPayment_grid.getStore().sumquery('paymentitemid',data.paymentitemid,'nowclearamount');
								// Ext.apply(data,{'billclearitemid':billclearitemid,'nowclearedamount':nowclearedamount});
								var p = new Ext.data.Record(data);
								BillInPayment_grid.stopEditing();
								BillInPayment_grid.getStore().insert(0, p);
								BillInPayment_grid.startEditing(0, 0);
							}
							// BillInPayment_grid.getStore().filter('billclearitemid',billclearitemid);
						}
					},
					scope	: this
				});
	}
}

function _addBillInPayment()
{
	if (pre_addBillInPayment())
	{
		if (div_supplier_sh_sh.getValue() == '')
		{
			_getMainFrame().showInfo("必须先选择[供应商]！");
			return;
		}
		if (div_deptid_sh_sh.getValue() == '')
		{
			_getMainFrame().showInfo("必须先选择[部门]！");
			return;
		}
		var supplier = div_supplier_sh_sh.getValue();	
		var deptid = div_deptid_sh_sh.getValue();
		
		searchPaymentItemWin.defaultCondition="SUPPLIER='" + supplier + "' and DEPT_ID='" + deptid + "'  and ( BILLISCLEAR='0' or trim(BILLISCLEAR) is null ) and trim(VOUCHERNO) is not null and BUSINESSSTATE != '-1'  and ( VOUCHERCLASS='1' or VOUCHERCLASS='4' ) " ;
		// 方法执行体
		searchPaymentItemWin.show();
	}
	post_addBillInPayment();
}

function pre_addBillInPayment()
{
	return true;
}

function post_addBillInPayment()
{
	
}

/**
 * 票清付款行项目 批量删除
 */
function _predeletesBillInPayment()
{
	return false
}

/**
 * 票清付款行项目 批量删除
 */
function _postdeletesBillInPayment()
{
	if (BillInPayment_grid.selModel.hasSelection() > 0)
	{
		_getMainFrame().Ext.MessageBox.show(
				{
					title	: Txt.cue,
					msg		: Txt.billInPayment_Deletes_ConfirmOperation,
					buttons	:
					{
						yes	: Txt.ok,
						no	: Txt.cancel
					},
					icon	: Ext.MessageBox.QUESTION,
					fn		: function(buttonid)
					{
						if (buttonid == 'yes')
						{
							var records = BillInPayment_grid.selModel.getSelections();
							for (var i = 0; i < records.length; i++)
							{
								BillInPayment_grid.getStore().remove(records[i]);
							}
						}
					}
				});
	}
	else
	{
		_getMainFrame().showInfo(Txt.billInPayment_Deletes_SelectToOperation);
	}
}

/**
 * 票清付款行项目 grid上的和删除操作
 */
function _predeletesBillClearItemPay()
{
	return false;
}

/**
 * 票清付款行项目 grid上的和删除操作
 */
function _postdeletesBillClearItemPay()
{
	if (BillClearItemPay_grid.selModel.hasSelection() > 0)
	{
		_getMainFrame().Ext.MessageBox.show(
				{
					title	: Txt.cue,
					msg		: Txt.billClearItemPay_Deletes_ConfirmOperation,
					buttons	:
					{
						yes	: Txt.ok,
						no	: Txt.cancel
					},
					icon	: Ext.MessageBox.QUESTION,
					fn		: function(buttonid)
					{
						if (buttonid == 'yes')
						{
							var ischange = false;
							var vendortitleids = '';
							var records = BillClearItemPay_grid.getSelectionModel().getSelections();
							for (i = 0; i < records.size(); i++)
							{
								ischange = true;
								BillClearItemPay_grid.getStore().remove(records[i]);
							}
							
							if (ischange)
							{
								for (var i = 0; i < BillClearItemPay_grid.getStore().getCount(); i++)
								{
									var rec = BillClearItemPay_grid.getStore().getAt(i);
									vendortitleids += rec.get('titleid') + ",";
								}
								
								vendortitleids = vendortitleids.substring(0, vendortitleids.length - 1);
								_assignAmount(vendortitleids);
							}
						}
					}
				});
	}
	else
	{
		_getMainFrame().showInfo(Txt.billClearItemPay_Deletes_SelectToOperation);
	}
}

/**
 * 保存
 */
function _presaveOrUpdateBillClearPayment()
{
	 removeGrid();
	 var msg = _checkFilds();
    if (msg != "")
    {
        showInfo(msg);
        return false;
    }
   
	var param = Form.serialize('mainForm');
	param = param + "" + getAllBillClearItemPayGridData();
	param = param + "" + getAllBillInPaymentGridData();
	param = param + "&" + Form.serialize('fundFlowForm');
	
	param = param + "&" + Form.serialize('settleSubjectForm');
	//ajax同步调用
	var url = contextPath + '/xdss3/billClear/billClearPaymentController.spr?action=checkClearData&';	
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

/**
 * 保存
 */
function _postsaveOrUpdateBillClearPayment()
{
   return true;
   /** 
    var param = Form.serialize('mainForm');
    param = param + "" + getAllBillClearItemPayGridData();
    param = param + "" + getAllBillInPaymentGridData();
    param = param + "&" + Form.serialize('fundFlowForm');
    
    param = param + "&" + Form.serialize('settleSubjectForm');
    new AjaxEngine(contextPath + '/xdss3/billClear/billClearPaymentController.spr?action=_saveOrUpdate',
            {
                method      : "post",
                parameters  : param,
                onComplete  : callBackHandle,
                callback    : saveOrUpdatecustomCallBackHandle
            });
      **/
}

/**
 * 操作成功后的回调动作
 */
function saveOrUpdatecustomCallBackHandle(transport)
{
    var responseUtil = new AjaxResponseUtils(transport.responseText);
    var result = responseUtil.getCustomField("coustom");
    var billclearid = result.billclearid;
    document.getElementById("BillClearPayment.billclearid").value = billclearid;
    
    document.getElementById("BillClearPayment.billclearno").value = result.billclearno;
    document.getElementById("BillClearPayment.createtime").value = result.createtime;
    document.getElementById("BillClearPayment.lastmodifyer").value = result.lastmodifyer;
    document.getElementById("BillClearPayment.lastmodifytime").value = result.lastmodifytime;
    document.getElementById("BillClearPayment.creator").value = result.creator;
    isCreateCopy = false;
    reload_BillClearItemPay_grid("defaultCondition=YBILLCLEARITEM.BILLCLEARID='" + billclearid + "'");
    reload_BillInPayment_grid("defaultCondition=YBILLINPAYMENT.BILLCLEARID='" + billclearid + "'");
    document.getElementById("FundFlow.fundflowid").value = result.fundflowid;
    document.getElementById("SettleSubject.settlesubjectid").value = result.settlesubjectid;
    
    if (Ext.getCmp("_submitProcess").disabled)
    {
        Ext.getCmp("_submitProcess").setDisabled(false);
    }
}

/**
 * 提交
 */
function _presubmitProcessBillClearPayment()
{
	var ns = Ext.getDom('workflowLeaveTransitionName').value;		// 下一步操作
	if(ps == '财务会计审核' && ns == '退回业务修改')return true;
	
    var msg = _checkFilds();
    if (msg != "")
    {
        showInfo(msg);
        return false;
    }
    removeGrid();
	
	var param = Form.serialize('mainForm');
	param = param + "" + getAllBillClearItemPayGridData();
	param = param + "" + getAllBillInPaymentGridData();
	param = param + "&" + Form.serialize('fundFlowForm');
	
	param = param + "&" + Form.serialize('settleSubjectForm');
	//ajax同步调用
	var url = contextPath + '/xdss3/billClear/billClearPaymentController.spr?action=checkClearData&';	
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

/**
 * 提交
 */
function _postsubmitProcessBillClearPayment()
{
	return true;
  /**  
    var param = Form.serialize('mainForm');
    param = param + "&" + getAllBillClearItemPayGridData();
    param = param + "&" + getAllBillInPaymentGridData();
    param = param + "&" + Form.serialize('fundFlowForm');
    param = param + "&" + Form.serialize('settleSubjectForm');
    param = param + "&" + Form.serialize('workflowForm');
    
    new AjaxEngine(contextPath + '/xdss3/billClear/billClearPaymentController.spr?action=_submitProcess',
            {
                method      : "post",
                parameters  : param,
                onComplete  : callBackHandle,
                callback    : _submitCallBackHandle
            });
	**/
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
 * 校验字段值
 */
function _checkFilds()
{
	if (div_supplier_sh_sh.getValue() == '')
	{
		return "必须先选择[供应商]！";
	}
	
	if (document.getElementById('BillClearPayment.processstate').value == '财务会计审核'){
		if(document.getElementById("BillClearPayment.accountdate").value == ''){
			return "必须先填写[记账日期]！";
		}
		if(document.getElementById("BillClearPayment.voucherdate").value == ''){
			return "必须先填写[凭证日期]！";
		}
		var sumclearbillamount = 0;
		var sumadjustamount = 0;
		var sumnowclearamount = 0;
		//未收款金额总和
		var unclearBillAmount = 0;
		//未抵消金额总和
		var unclearAmount = 0;
		var accountdate = document.getElementById("BillClearPayment.accountdate").value;
		for (var i = 0; i < BillClearItemPay_grid.getStore().getCount(); i++)
		{
			var rec = BillClearItemPay_grid.getStore().getAt(i);
			sumclearbillamount = sumclearbillamount + parseFloat(rec.get('clearbillamount'));
			sumadjustamount = sumadjustamount + parseFloat(rec.get('adjustamount'));
			unclearBillAmount += parseFloat(rec.get('unreceivedamount'));
			//日期比较大小,0:小于 1：等于 2：大于 3 错误	
			if( DateUtils.dateCompareStr(accountdate,rec.get('accountdate')) ==0 )return "清账的过账日期一定要大于或等于借贷方的过账日期";
		}
		
		for (var i = 0; i < BillInPayment_grid.getStore().getCount(); i++)
		{
			var rec = BillInPayment_grid.getStore().getAt(i);
			sumnowclearamount = sumnowclearamount + parseFloat(rec.get('nowclearamount'));
			unclearAmount += parseFloat(rec.get('unoffsetamount'));
			//日期比较大小,0:小于 1：等于 2：大于 3 错误	
			if( DateUtils.dateCompareStr(accountdate,rec.get('accountdate')) ==0 )return "清账的过账日期一定要大于或等于借贷方的过账日期";
		}
		
		if(isNaN(sumclearbillamount)){
			return "清帐金额的值不能为空，请输入清帐金额！";
		}
		if(isNaN(sumnowclearamount)){
			return "本次抵消付款的值不能为空，请输入清帐金额！";
		}
		if(isNaN(sumadjustamount)){
			return "调整差额的值不能为空，请输入清帐金额！";
		}
		if (sumclearbillamount == 0)
		{
			return "清帐金额的合计值不能等于0，请输入清帐金额！";
		}
		/**
		if(sumadjustamount ==0 && unclearBillAmount != sumclearamount && unclearAmount != sumnowclearamount ){
			return "借贷方最少要一边要全清！";
		}
		**/
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
			
		if (round((sumnowclearamount + sumadjustamount),2) != round(sumclearbillamount,2))
		{
			return "本次抵消付款的总和(" + round(sumnowclearamount,2) + ")加上调整差额的合计值(" + round(sumadjustamount,2) + ")必须等于清帐金额的合计值(" + round(sumclearbillamount,2) + ")！";
		}
		else if (round(totAmount,2) != round(sumadjustamount,2))
		{
			return "结算科目金额总计+纯资金往来金额总计的和(" + round(totAmount,2) + ")必须等于 调整差额的合计值(" + round(sumadjustamount,2) + ")！";
		}
		else
		{
			return "";
		}
	}else{
		return "";
	}
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
			Ext.getCmp('_submitForReassign').hide();
			Ext.getCmp("_voucherPreview").hide();
            div_supplier_sh_sh.defaultCondition = " LIFNR in(select LIFNR from YVENDORTITLE where ISCLEARED='0' and SHKZG='H')";
			div_supplier_sh_sh.on('change', function(e, n, o)
					{
						searchBillClearItemPayHelpWin.defaultCondition = " INVOICE is not null and INVOICE!=' ' and LIFNR='" + n + "'";
						
					});
					
			 var uncbToolbar = BillClearItemPay_grid.getTopToolbar();
			uncbToolbar.items.get('BillClearItemPayaddRow').hide();		// 隐藏"增加行"按钮
			uncbToolbar.items.get('BillClearItemPaydeleteRow').hide();	// 隐藏"删除行"按钮
			
			var unccToolbar = BillInPayment_grid.getTopToolbar();
			unccToolbar.items.get('BillInPaymentaddRow').hide();		// 隐藏"增加行"按钮
			unccToolbar.items.get('BillInPaymentdeleteRow').hide();	// 隐藏"删除行"按钮
			
			if(isReassign != 'Y'){	// 若不为重分配
				if (ps == '财务会计审核') {
					Ext.getCmp('_autoAssign').hide();
					Ext.getCmp('_clearAssign').hide();
					Ext.getCmp('_submitProcess').disable();
					Ext.getDom('workflowLeaveTransitionName').onchange = function() {
						var ns = this.value; // 下一步操作状态
						if (ns == '退回业务修改') {
							Ext.getCmp('_submitProcess').setDisabled(false); // 启用（按钮）提交
						} else {
							Ext.getCmp('_submitProcess').setDisabled(true); // 禁用（按钮）提交
						}
					}
				} else {
					Ext.getCmp('_voucherPreview').hide();
				}
			}else{				// 若为重分配
				if(Ext.getDom('workflowCurrentTaskName').value == '财务部审核'){
					Ext.getCmp('_autoAssign').hide();
					Ext.getCmp('_clearAssign').hide();
					Ext.getCmp('_submitProcess').disable();
					Ext.getDom('workflowLeaveTransitionName').onchange = function() {
						var ns = this.value; // 下一步操作状态
						if (ns=='作废' || ns=='退回出纳' || ns=='退回业务') {
							Ext.getCmp('_submitProcess').setDisabled(false); // 启用（按钮）提交
						} else {
							Ext.getCmp('_submitProcess').setDisabled(true); // 禁用（按钮）提交
						}
					}
				}else{
					Ext.getCmp('_voucherPreview').hide();
				}
			}
			/**
            div_supplier_sh_sh.callBack = function(data){
				//同步所选供应商未清数据
				Ext.Ajax.request({
			        url : contextPath+'/xdss3/payment/importPaymentController.spr?action=_synchronizeUnclearVendor&privadeid=' + div_supplier_sh_sh.getValue(),
			        success : function(xhr){
			        },
			        scope : this
			    });
			}
			**/
			BillClearItemPay_grid.on('afteredit',calclear,BillClearItemPay_grid);
			BillInPayment_grid.on('afteredit',calclear,BillInPayment_grid);
			function calclear(e){
				var cRecord = e.record;
				var cField  = e.field;
				
				if(cField == 'clearbillamount' && cRecord.get("clearbillamount")==''){
					cRecord.set("clearbillamount",0);
				}
				if(cField == 'adjustamount' && cRecord.get("adjustamount")==''){
					cRecord.set("adjustamount",0);
				}
				if(cField == 'nowclearamount' && cRecord.get("nowclearamount")==''){
					cRecord.set("nowclearamount",0);
				}
				showTitle();
			}
			showTitleByOnready();
		});
		
function showTitle(){
	var sumclearamount = 0;
	var sumadjustamount = 0;
	var sumnowclearamount = 0;
	for (var i = 0; i < BillClearItemPay_grid.getStore().getCount(); i++)
	{
		var rec = BillClearItemPay_grid.getStore().getAt(i);
		sumclearamount += parseFloat(rec.get('clearbillamount'));
		sumadjustamount += parseFloat(rec.get('adjustamount'));
	}
	
	for (var i = 0; i < BillInPayment_grid.getStore().getCount(); i++)
	{
		var rec = BillInPayment_grid.getStore().getAt(i);
		sumnowclearamount += parseFloat(rec.get('nowclearamount'));
	}	
	var m = parseFloat(sumclearamount) - parseFloat(sumnowclearamount);
	BillClearItemPay_grid.setTitle('清帐金额：'+ round(sumclearamount,2)+'   本次已抵消金额：'+Math.abs(round(sumnowclearamount,2)) +'      调整金额：'+ round(sumadjustamount,2)+'   差额:' + round(m,2) );
	BillInPayment_grid.setTitle('清帐金额：'+ round(sumclearamount,2)+'   本次已抵消金额：'+Math.abs(round(sumnowclearamount,2)) +'      调整金额：'+ round(sumadjustamount,2)+'   差额:' + round(m,2) );
	
}		
/**
*自动删除为0的行项目
**/
function removeGrid(){
	for (var i = BillClearItemPay_grid.getStore().getCount()-1; i >=0 ; i--)
	{
		var ds = BillClearItemPay_grid.getStore();
		var rec = ds.getAt(i);
		var clearmount = rec.get('clearbillamount');
		var receivableamount = rec.get('receivableamount');
		if((clearmount=='0' || clearmount=='') && receivableamount !='0'){
			ds.remove(rec);
		}		
	}
	
	for (var i = BillInPayment_grid.getStore().getCount()-1; i >=0 ; i--)
	{
		var ds = BillInPayment_grid.getStore();
		var rec = ds.getAt(i);
		var nowclearamount = rec.get('nowclearamount');
		var amount = rec.get('amount');
		if((nowclearamount=='0' || nowclearamount=='') && amount !='0'){
			ds.remove(rec);
		}
	}
	
}        

function showTitleByOnready(){
	var m = parseFloat(sumclearamount) - parseFloat(sumnowclearamount);
	BillClearItemPay_grid.setTitle('清帐金额：'+ round(sumclearamount,2)+'   本次已抵消金额：'+Math.abs(round(sumnowclearamount,2)) +'      调整金额：'+ round(sumadjustamount,2)+'   差额:' + round(m,2) );
	BillInPayment_grid.setTitle('清帐金额：'+ round(sumclearamount,2)+'   本次已抵消金额：'+Math.abs(round(sumnowclearamount,2)) +'      调整金额：'+ round(sumadjustamount,2)+'   差额:' + round(m,2) );
	
}

function checkGridItem(){
	var flag = false;
	var flag2 =  false;
	for (var i = BillClearItemPay_grid.getStore().getCount()-1; i >=0 ; i--)
	{
		var ds = BillClearItemPay_grid.getStore();
		var rec = ds.getAt(i);
		var clearmount = parseFloat(rec.get('clearbillamount'));
		var unreceivedamount = parseFloat(rec.get('unreceivedamount'));
		if(clearmount>unreceivedamount){
			return "清账金额不能大于未收款金额";
		}
		if(clearmount<0){
			return "清账金额不能小于0";
		}
		if(parseFloat(unreceivedamount) - parseFloat(clearmount) != 0){
			flag =true;
		}
	}
	
	for (var i = BillInPayment_grid.getStore().getCount()-1; i >=0 ; i--)
	{
		var ds = BillInPayment_grid.getStore();
		var rec = ds.getAt(i);
		var nowclearamount = parseFloat(rec.get('nowclearamount'));
		var unoffsetamount = parseFloat(rec.get('unoffsetamount'));
		if(nowclearamount>unoffsetamount){
			return "未抵消金额不能大于未清金额";
		}
		if(nowclearamount<0){
			return "未抵消金额不能小于0";
		}
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
 * 模拟凭证
 */
function _voucherPreviewBillClearPayment2()
{

			
	var msg = _checkFilds();
	if (msg != "")
	{
		showInfo(msg);
		return;
	}
	Ext.getCmp('_submitProcess').enable(); 
	// 方法执行体
	var param = Form.serialize('mainForm');
	param = param + "&" + getAllBillClearItemPayGridData();
	param = param + "&" + getAllBillInPaymentGridData();
	param = param + "&" + Form.serialize('fundFlowForm');
	param = param + "&" + Form.serialize('settleSubjectForm');
	
	new AjaxEngine(contextPath + '/xdss3/billClear/billClearPaymentController.spr?action=_voucherPreview2',
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
			if(!result.isClear){
	        	_getMainFrame().showInfo("没有凭证预览可生成");
			}else{
	        	Ext.get('currentWorkflowTask').mask();
				Ext.get('centercontent').mask();
				Ext.get(Ext.getCmp("tabs").getActiveTab().getItemId()).mask();
				
				Ext.getCmp("tabs").on('tabchange',function(t,p){
					Ext.get(p.getItemId()).mask();
				});
				
	        	_getMainFrame().maintabs.addPanel('票清收款凭证预览', '', contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid=' + result.businessid,closeVoucherCallBack,true);
			}
		}
	}
}  
 