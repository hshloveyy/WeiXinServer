/**
 * Author(s):java业务平台代码生成工具 Date: 2010年07月16日 11点32分11秒 Copyright Notice:版权声明
 * 福建讯盟软件有限公司, 版权所有 违者必究
 * 
 * @(#) Description: <功能>主对象客户单清帐(CustomSingleClear)增加页面用户可编程扩展JS文件
 */

/**
 * 保存
 */
function _presaveOrUpdateCustomSingleClear()
{	
	
	
	
	var clearid = document.getElementById("CustomSingleClear.customsclearid").value;
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
		// 方法执行体
		var param = Form.serialize('mainForm');
		param = param + "" + getAllUnClearCustomBillGridData();
		param = param + "" + getAllUnClearCollectGridData();
		param = param + "&" + Form.serialize('settleSubjectForm');
		param = param + "&" + Form.serialize('fundFlowForm');
		//ajax同步调用,url字符串后面要加&不然会出错
			var url = contextPath + '/xdss3/singleClear/customSingleClearController.spr?action=checkClearData&';
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
		var url = contextPath + '/xdss3/singleClear/customSingleClearController.spr?action=getVoucherId&customsclearid=' + document.getElementById("CustomSingleClear.customsclearid").value;	
		var conn = Ext.lib.Ajax.getConnectionObject().conn; 
		conn.open("post", url,false); 
		conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");  
		conn.send(null); 
//		alert(conn.responseText);
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
			// 方法执行体
			var param = Form.serialize('mainForm');
			param = param + "" + getAllUnClearCustomBillGridData();
			param = param + "" + getAllUnClearCollectGridData();
			param = param + "&" + Form.serialize('settleSubjectForm');
			param = param + "&" + Form.serialize('fundFlowForm');
			//ajax同步调用,url字符串后面要加&不然会出错
			var url = contextPath + '/xdss3/singleClear/customSingleClearController.spr?action=checkClearData&';
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
 * 保存
 */
function _postsaveOrUpdateCustomSingleClear()
{
	
	
	return true;
	
	/**
	var param = Form.serialize('mainForm');
	param = param + "" + getAllUnClearCustomBillGridData();
	param = param + "" + getAllUnClearCollectGridData();
	param = param + "&" + Form.serialize('settleSubjectForm');
	param = param + "&" + Form.serialize('fundFlowForm');
	new AjaxEngine(contextPath + '/xdss3/singleClear/customSingleClearController.spr?action=_saveOrUpdate',
			{
				method		: "post",
				parameters	: param,
				onComplete	: callBackHandle,
				callback	: customCallBackHandle
			});
	**/
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
 * 查询
 */
function _queryUnClearCustomSingleClear()
{
	
	//先同步一下客户单清帐
	/**
	Ext.Ajax.request({
	    	url : contextPath+'/xdss3/collect/collectController.spr?action=syncUnclearCustomer',
	        params : {customer:div_custom_sh_sh.getValue()},
	        success : function(xhr){
	        },
	        scope : this
	    });
	 **/   
	// 方法执行体
	if (div_custom_sh_sh.getValue() == '')
	{
		_getMainFrame().showInfo("必须先选择[客户]！");
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
	var clearid = document.getElementById("CustomSingleClear.customsclearid").value;
	if(clearid==""){
	
			UnClearCustomBill_grid.getStore().removeAll();
			UnClearCollect_grid.getStore().removeAll();
			
			var url = contextPath + '/xdss3/singleClear/customSingleClearController.spr?action=_queryUnClear';
			var param = {
							custom	: div_custom_sh_sh.getValue(),
							subject	: div_subject_sh_sh.getValue(),
							currencytext : div_currencytext_sh_sh.getValue(),
							companyno : div_companyno_sh_sh.getValue(),
							depid : div_depid_sh_sh.getValue()
						};
			new AjaxEngine(url, {method:"post", parameters: param, onComplete: queryUnClearcallBackHandle});
			
	}else{
			var param2 ={customsclearid : document.getElementById("CustomSingleClear.customsclearid").value};
			new AjaxEngine(contextPath + '/xdss3/singleClear/customSingleClearController.spr?action=getVoucherId',
					{
						method		: "post",
						parameters	: param2,
						onComplete	: function(xhr){
										if (xhr.responseText){
											//alert(xhr.responseText);
											var voucherid=xhr.responseText;
											if(voucherid !='0'){
												_getMainFrame().showInfo("该单清账已生成凭证不能再查询！");
												var promptMessagebox = new MessageBoxUtil();
												promptMessagebox.Close();
												return;
											}
											
											UnClearCustomBill_grid.getStore().removeAll();
											UnClearCollect_grid.getStore().removeAll();
											var url = contextPath + '/xdss3/singleClear/customSingleClearController.spr?action=_queryUnClear';
											var param = {
															custom	: div_custom_sh_sh.getValue(),
															subject	: div_subject_sh_sh.getValue(),
															currencytext : div_currencytext_sh_sh.getValue(),
															companyno : div_companyno_sh_sh.getValue(),
															depid : div_depid_sh_sh.getValue(),
															customsclearid : document.getElementById("CustomSingleClear.customsclearid").value
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
			UnClearCustomBill_grid.stopEditing();
			UnClearCustomBill_grid.getStore().insert(0, p);
			UnClearCustomBill_grid.startEditing(0, 0);
		}
		// UnClearCustomBill_grid.getStore().commitChanges();
		for (var j = 0; j < jsonData.data2.length; j++)
		{
			var p = new Ext.data.Record(jsonData.data2[j]);
			UnClearCollect_grid.stopEditing();
			UnClearCollect_grid.getStore().insert(0, p);
			UnClearCollect_grid.startEditing(0, 0);
		}
		// UnClearCollect_grid.getStore().commitChanges();
		showTitle();
	}
}

/**
 * 清除分配
 */
function _clearAssignCustomSingleClear()
{
	// 方法执行体
	for (var i = 0; i < UnClearCustomBill_grid.getStore().getCount(); i++)
	{
		var rec = UnClearCustomBill_grid.getStore().getAt(i);
		rec.set('clearamount', 0);
		rec.set('adjustamount', 0);
	}
	
	for (var i = 0; i < UnClearCollect_grid.getStore().getCount(); i++)
	{
		var rec = UnClearCollect_grid.getStore().getAt(i);
		rec.set('nowclearamount', 0);
	}
	
}

/**
 * 自动分配
 */
function _autoAssignCustomSingleClear()
{
	// 方法执行体
	if (div_custom_sh_sh.getValue() == '')
	{
		_getMainFrame().showInfo("必须先选择[客户]！");
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
				url		: contextPath + '/xdss3/singleClear/customSingleClearController.spr?action=_autoAssign',
				params	:
				{
					custom	: div_custom_sh_sh.getValue(),
					subject	: div_subject_sh_sh.getValue(),
					currencytext : div_currencytext_sh_sh.getValue(),
					companyno : div_companyno_sh_sh.getValue(),
					depid : div_depid_sh_sh.getValue()
				},
				success	: function(xhr)
				{
					if (xhr.responseText)
					{
						UnClearCustomBill_grid.getStore().removeAll();
						UnClearCollect_grid.getStore().removeAll();
						
						var jsonData = Ext.util.JSON.decode(xhr.responseText);
						for (var j = 0; j < jsonData.data.length; j++)
						{
							var p = new Ext.data.Record(jsonData.data[j]);
							UnClearCustomBill_grid.stopEditing();
							UnClearCustomBill_grid.getStore().insert(0, p);
							UnClearCustomBill_grid.startEditing(0, 0);
						}
						// UnClearCustomBill_grid.getStore().commitChanges();
						
						for (var j = 0; j < jsonData.data2.length; j++)
						{
							var p = new Ext.data.Record(jsonData.data2[j]);
							UnClearCollect_grid.stopEditing();
							UnClearCollect_grid.getStore().insert(0, p);
							UnClearCollect_grid.startEditing(0, 0);
						}
						// UnClearCollect_grid.getStore().commitChanges();
						
					}
				},
				scope	: this
			});
	
}

/**
 * 确认清帐
 */
function _submitClearCustomSingleClear()
{
	// 方法执行体
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
	param = param + "" + getAllUnClearCustomBillGridData();
	param = param + "" + getAllUnClearCollectGridData();
	//param = param + ""+ getUnClearCustomBillGridData();
	//param = param + ""+ getUnClearCollectGridData();
	param = param + "&" + Form.serialize('settleSubjectForm');
	param = param + "&" + Form.serialize('fundFlowForm');
	
	new AjaxEngine(contextPath + '/xdss3/singleClear/customSingleClearController.spr?action=_submitClear2',
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
				    	
				    	_getMainFrame().maintabs.addPanel('凭证查看', '', contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.get('CustomSingleClear.customsclearid').dom.value);
				    	
					}else{
						_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
					}
				}
			});
		}else{
			var customsclearid = result.customsclearid;
			document.getElementById("CustomSingleClear.customsclearid").value = customsclearid;
			
			document.getElementById("CustomSingleClear.creator_text").value = result.creator_text;
			document.getElementById("CustomSingleClear.creator").value = result.creator;
			document.getElementById("CustomSingleClear.createtime").value = result.createtime;
			document.getElementById("CustomSingleClear.lastmodifyer").value = result.lastmodifyer;
			document.getElementById("CustomSingleClear.lastmodifytime").value = result.lastmodifytime;
			isCreateCopy = false;
			reload_UnClearCustomBill_grid("defaultCondition=YUNCLEARCUSTBILL.CUSTOMSCLEARID='" + customsclearid + "'");
			reload_UnClearCollect_grid("defaultCondition=YUNCLEARCOLLECT.CUSTOMSCLEARID='" + customsclearid + "'");
			document.getElementById("SettleSubject.settlesubjectid").value = result.settlesubjectid;
			document.getElementById("FundFlow.fundflowid").value = result.fundflowid;
			Ext.getCmp("_saveOrUpdateCustomSingleClear").setDisabled(true);
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
function _blankOutCustomSingleClear()
{
	// 方法执行体
	var param = Form.serialize('mainForm');
	param = param + "" + getAllUnClearCustomBillGridData();
	param = param + "" + getAllUnClearCollectGridData();
	param = param + "&" + Form.serialize('settleSubjectForm');
	param = param + "&" + Form.serialize('fundFlowForm');
	new AjaxEngine(contextPath + '/xdss3/singleClear/customSingleClearController.spr?action=_blankOut',
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
	var customsclearid = result.customsclearid;
	document.getElementById("CustomSingleClear.businessstate").value = result.businessstate;
	
	Ext.getCmp("_blankOut").setDisabled(true);
	Ext.getCmp("_voucherPreview").setDisabled(true);
	Ext.getCmp("_saveOrUpdateCustomSingleClear").setDisabled(true);
	Ext.getCmp("_queryUnClear").setDisabled(true);
	Ext.getCmp("_autoAssign").setDisabled(true);
	Ext.getCmp("_clearAssign").setDisabled(true);
	Ext.getCmp("_submitClear").setDisabled(true);
	
}

/**
 * 模拟凭证
 */
function _voucherPreviewCustomSingleClear()
{
	var cus_id = document.getElementById("CustomSingleClear.customsclearid").value;
	
	if(cus_id==""){
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
	// 方法执行体
	var param = Form.serialize('mainForm');
	param = param + "" + getAllUnClearCustomBillGridData();
	param = param + "" + getAllUnClearCollectGridData();
	param = param + "&" + Form.serialize('settleSubjectForm');
	param = param + "&" + Form.serialize('fundFlowForm');
	
	new AjaxEngine(contextPath + '/xdss3/singleClear/customSingleClearController.spr?action=_voucherPreview',
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
function _voucherPreviewCustomSingleClear2()
{
	var cus_id = document.getElementById("CustomSingleClear.customsclearid").value;
	
	if(cus_id==""){
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
	// 方法执行体
	var param = Form.serialize('mainForm');
	param = param + "" + getAllUnClearCustomBillGridData();
	param = param + "" + getAllUnClearCollectGridData();
	param = param + "&" + Form.serialize('settleSubjectForm');
	param = param + "&" + Form.serialize('fundFlowForm');
	
	new AjaxEngine(contextPath + '/xdss3/singleClear/customSingleClearController.spr?action=_voucherPreview2',
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
				var customsclearid = result.customsclearid;
				document.getElementById("CustomSingleClear.customsclearid").value = customsclearid;
	        	_getMainFrame().maintabs.addPanel('客户单清凭证预览', '', contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid=' + result.businessid,closeVoucherCallBack,true);
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
			var customsclearid = result.customsclearid;
			document.getElementById("CustomSingleClear.customsclearid").value = customsclearid;
        	_getMainFrame().maintabs.addPanel('客户单清凭证预览', '', contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid=' + result.businessid,closeVoucherCallBack,true);
		
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
		_submitClearCustomSingleClear();	
		
	}
}

/**
*自动删除为0的行项目
**/
function removeGrid(){
	for (var i = UnClearCustomBill_grid.getStore().getCount()-1; i >=0 ; i--)
	{
		var ds = UnClearCustomBill_grid.getStore();
		var rec = ds.getAt(i);
		var clearmount = rec.get('clearamount');
		var receivableamount = rec.get('receivableamount');
		if((clearmount=='0' || clearmount=='') && receivableamount !='0'){
			ds.remove(rec);
		}		
	}
	
	for (var i = UnClearCollect_grid.getStore().getCount()-1; i >=0 ; i--)
	{
		var ds = UnClearCollect_grid.getStore();
		var rec = ds.getAt(i);
		var nowclearamount = rec.get('nowclearamount');
		var amount = rec.get('amount');
		if((nowclearamount=='0' || nowclearamount=='') && amount !='0'){
			ds.remove(rec);
		}
	}
	
}
/**
**校验grid
**/
function _checkGrid(){
	var flag = false;
	var flag2 =  false;
	for (var i = 0; i < UnClearCustomBill_grid.getStore().getCount(); i++)
	{
		var rec = UnClearCustomBill_grid.getStore().getAt(i);
		var clearmount = rec.get('clearamount');
		var receivableamount = rec.get('receivableamount');
		if((clearmount=='0' || clearmount=='') && receivableamount !='0'){
		
			return "消帐金额不能为空，或为0";
		}	
		var unreceivableamou =  rec.get('unreceivableamou');
		if(parseFloat(unreceivableamou) - parseFloat(clearmount) != 0){
			flag =true;
		}
			
	}
	
	for (var i = 0; i < UnClearCollect_grid.getStore().getCount(); i++)
	{
		var rec = UnClearCollect_grid.getStore().getAt(i);
		var nowclearamount = rec.get('nowclearamount');
		var amount = rec.get('amount');
		if((nowclearamount=='0' || nowclearamount=='') && amount !='0'){		
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
	if (div_custom_sh_sh.getValue() == '')
	{
		return "必须先选择[客户]！";
	}
	
	if (div_subject_sh_sh.getValue() == '')
	{
		return "必须先选择[清帐科目]！";
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
	
	if(document.getElementById("CustomSingleClear.text").value == ''){
		return "必须先填写[清帐用途]！";
	}
	if(document.getElementById("CustomSingleClear.accountdate").value == ''){
		return "必须先填写[记账日期]！";
	}
	if(document.getElementById("CustomSingleClear.voucherdate").value == ''){
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
	//过账日期
	var accountdate = document.getElementById("CustomSingleClear.accountdate").value;
	for (var i = 0; i < UnClearCustomBill_grid.getStore().getCount(); i++)
	{
		var rec = UnClearCustomBill_grid.getStore().getAt(i);
		sumclearamount += parseFloat(rec.get('clearamount'));
		sumadjustamount += parseFloat(rec.get('adjustamount'));
		unclearBillAmount += parseFloat(rec.get('unreceivableamou'));
		
		if(rec.get('receivableamount')!='0')flagAmount=false;
		//日期比较大小,0:小于 1：等于 2：大于 3 错误		
		if( DateUtils.dateCompareStr(accountdate,rec.get('accountdate')) ==0 )return "清账的过账日期一定要大于或等于借贷方的过账日期";
	}
	
	for (var i = 0; i < UnClearCollect_grid.getStore().getCount(); i++)
	{
		var rec = UnClearCollect_grid.getStore().getAt(i);
		sumnowclearamount += parseFloat(rec.get('nowclearamount'));
		unclearAmount += parseFloat(rec.get('unoffsetamount'));
		if(rec.get('amount')!='0')flagAmount=false;
		if(DateUtils.dateCompareStr(accountdate,rec.get('accountdate')) ==0 )return "清账的过账日期一定要大于或等于借贷方的过账日期";
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
	
	if (fdebtcredit1 == "H")
		totAmount = totAmount - famount1;
	else if (fdebtcredit1 == "S")
		totAmount = totAmount + famount1;
	
	if (fdebtcredit2 == "H")
		totAmount = totAmount - famount2;
	else if (fdebtcredit2 == "S")
		totAmount = totAmount + famount2;
	
	if (fdebtcredit3 == "H")
		totAmount = totAmount - famount3;
	else if (fdebtcredit3 == "S")
		totAmount = totAmount + famount3;
	
	if (sdebtcredit1 == "H")
		totAmount = totAmount - samount1;
	else if (sdebtcredit1 == "S")
		totAmount = totAmount + samount1;
	if (sdebtcredit2 == "H")
		totAmount = totAmount - samount2;
	else if (sdebtcredit2 == "S")
		totAmount = totAmount + samount2;
	if (sdebtcredit3 == "H")
		totAmount = totAmount - samount3;
	else if (sdebtcredit3 == "S")
		totAmount = totAmount + samount3;
	if (sdebtcredit4 == "H")
		totAmount = totAmount - samount4;
	else if (sdebtcredit4 == "S")
		totAmount = totAmount + samount4;
	
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


var searchCustomerTitleWin = new Ext.SearchHelpWindow({
	    shlpName : 'YHCUSTOMERTITLE3',
	    callBack : winCustomerTitleCallBack
});
var searchCustomerTitleWin2 = new Ext.SearchHelpWindow({
	    shlpName : 'YHCUSTOMERTITLE3',
	    callBack : winCustomerTitleCallBack2
});
function winCustomerTitleCallBack(jsonArrayData){
	var customertitleids = '';
	for(var i=0;i<jsonArrayData.length;i++){
		var num = UnClearCustomBill_grid.getStore().find('customertitleid',jsonArrayData[i].CUSTOMERTITLEID);
		if(num==-1){
			customertitleids += jsonArrayData[i].CUSTOMERTITLEID + ",";
		}
	}
	if(customertitleids=="")return;
	customertitleids = customertitleids.substring(0,customertitleids.length-1);
	Ext.Ajax.request({
    	url : contextPath+'/xdss3/singleClear/customSingleClearController.spr?action=getUnSingleClearData',
        params : {customertitleids:customertitleids,
        			customerclearid:document.getElementById("CustomSingleClear.customsclearid").value
        		},
        success : function(xhr){
			if(xhr.responseText){
				
	        	var jsonData = Ext.util.JSON.decode(xhr.responseText);	        	
	        	UnClearCustomBill_grid.getStore().clearFilter();	        	
	        	for(var j=0;j<jsonData.data.length;j++){	        		
	        		var p = new Ext.data.Record(jsonData.data[j]);
					UnClearCustomBill_grid.stopEditing();
					UnClearCustomBill_grid.getStore().insert(0, p);
				    UnClearCustomBill_grid.startEditing(0, 0);
	        	}
	        	//UnClearCustomBill_grid.getStore().sort([{field:'contract_no',direction: 'ASC'}, {field:'project_no',direction:'ASC'}], 'ASC');
			}
        },
        scope : this
    });
}

function winCustomerTitleCallBack2(jsonArrayData){
	var customertitleids = '';
	for(var i=0;i<jsonArrayData.length;i++){
		var num = UnClearCollect_grid.getStore().find('customertitleid',jsonArrayData[i].CUSTOMERTITLEID);
		if(num==-1){
			customertitleids += jsonArrayData[i].CUSTOMERTITLEID + ",";
		}
	}
	if(customertitleids=="")return;
	customertitleids = customertitleids.substring(0,customertitleids.length-1);
	Ext.Ajax.request({
    	url : contextPath+'/xdss3/singleClear/customSingleClearController.spr?action=getUnSingleClearData',
        params : {customertitleids:customertitleids,
        		customerclearid:document.getElementById("CustomSingleClear.customsclearid").value
        		},
        success : function(xhr){
			if(xhr.responseText){
			
	        	var jsonData = Ext.util.JSON.decode(xhr.responseText);
	        	UnClearCollect_grid.getStore().clearFilter();
	        	for(var j=0;j<jsonData.data.length;j++){
	        		var p = new Ext.data.Record(jsonData.data[j]);
					UnClearCollect_grid.stopEditing();
					UnClearCollect_grid.getStore().insert(0, p);
				    UnClearCollect_grid.startEditing(0, 0);
	        	}
	        	//UnClearCollect_grid.getStore().sort([{field:'contract_no',direction: 'ASC'}, {field:'project_no',direction:'ASC'}], 'ASC');
			}
        },
        scope : this
    });
}

function add(flag){

	if (div_custom_sh_sh.getValue() == '')
	{
		_getMainFrame().showInfo("必须先选择[客户]！");
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

	var custom = div_custom_sh_sh.getValue();
	var subject = div_subject_sh_sh.getValue();
	var currencytext = div_currencytext_sh_sh.getValue();
	var depid = div_depid_sh_sh.getValue();
	var companyno = div_companyno_sh_sh.getValue();
	
	
	if(flag == 'S'){
	searchCustomerTitleWin.defaultCondition ="SHKZG ='" + flag +  "' and KUNNR ='" + custom + "' and SAKNR='" + subject + "' and WAERS='" + currencytext + "' and BUKRS='" + companyno + "' and GSBER='" + depid + "' and ISCLEARED='0'";
		searchCustomerTitleWin.show();
	}
	
	if(flag == 'H'){
	searchCustomerTitleWin2.defaultCondition ="SHKZG ='" + flag +  "' and KUNNR ='" + custom + "' and SAKNR='" + subject + "' and WAERS='" + currencytext + "' and BUKRS='" + companyno + "' and GSBER='" + depid + "' and ISCLEARED='0'";
		searchCustomerTitleWin2.show();
	}
}

function _addUnClearCustomBill(){	
	add('S');
}

function _addUnClearCollect(){
	
	add('H');
}

function _delUnClearCustomBill(){
	
	var records = UnClearCustomBill_grid.getSelectionModel().getSelections();        			
	for(var i=0;i<records.size();i++)
    {
		UnClearCustomBill_grid.getStore().remove(records[i]);
    }
     showTitle();
}

function _delUnClearCollect(){
	var records = UnClearCollect_grid.getSelectionModel().getSelections();        			
	for(var i=0;i<records.size();i++)
    {
		UnClearCollect_grid.getStore().remove(records[i]);
    }
     showTitle();
}

function _resetUnClearCustomBill(){	
	var records = UnClearCustomBill_grid.getSelectionModel().getSelections();        			
	for(var i=0;i<records.size();i++)
    {
		records[i].set("clearamount",0);
		records[i].set("adjustamount",0);
    }
     showTitle();
}

function _resetUnClearCollect(){
	
	var records = UnClearCollect_grid.getSelectionModel().getSelections();        			
	for(var i=0;i<records.size();i++)
    {
		records[i].set("nowclearamount",0);
    }
     showTitle();
}

function _assignSetUnClearCustomBill(){	
	var records = UnClearCustomBill_grid.getSelectionModel().getSelections();        			
	for(var i=0;i<records.size();i++)
    {
		records[i].set("clearamount",records[i].get("unreceivableamou"));
    }
     showTitle();
}

function _assignSetUnClearCollect(){
	
	var records = UnClearCollect_grid.getSelectionModel().getSelections();        			
	for(var i=0;i<records.size();i++)
    {
		records[i].set("nowclearamount",records[i].get("unoffsetamount"));
    }
     showTitle();
}

Ext.onReady(function()
		{
			//先把自动分配给隐藏，以后再做这个功能
			Ext.getCmp("_autoAssign").setVisible(false);
			Ext.getCmp("_clearAssign").setVisible(false);
			Ext.getCmp("_submitClear").hide();
			Ext.getCmp("_voucherPreview").hide();
			
			var businessState = $('CustomSingleClear.businessstate').value;
			if (businessState)
			{
				// alert(businessState);
				if (businessState == "3")
				{
					Ext.getCmp("_blankOut").setDisabled(false);
					// Ext.getCmp("_blankOut").setVisible(true);
					Ext.getCmp("_saveOrUpdateCustomSingleClear").setDisabled(true);
					Ext.getCmp("_queryUnClear").setDisabled(true);
					Ext.getCmp("_autoAssign").setDisabled(true);
					Ext.getCmp("_clearAssign").setDisabled(true);
					//Ext.getCmp("_submitClear").setDisabled(true);
					Ext.getCmp("_voucherPreview").setDisabled(false);
				}
				else if (businessState == "9")
				{
					Ext.getCmp("_blankOut").setDisabled(true);
					Ext.getCmp("_saveOrUpdateCustomSingleClear").setDisabled(true);
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
			Ext.getCmp("_queryUnClear").setDisabled(true);
			Ext.getCmp("_blankOut").hide();
			div_custom_sh_sh.defaultCondition = " KUNNR in(select KUNNR from YCUSTOMERTITLE where ISCLEARED='0')";
			div_custom_sh_sh.callBack = function(data){
				//先同步一下客户单清帐

				Ext.Ajax.request({
				    	url : contextPath+'/xdss3/collect/collectController.spr?action=syncUnclearCustomer',
				        params : {customer:data.KUNNR},
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
				    	url : contextPath+'/xdss3/collect/collectController.spr?action=syncWriteoff',
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
			
			var uncbToolbar = UnClearCustomBill_grid.getTopToolbar();
			uncbToolbar.items.get('UnClearCustomBilladdRow').hide();		// 隐藏"增加行"按钮
			uncbToolbar.items.get('UnClearCustomBilldeleteRow').hide();	// 隐藏"删除行"按钮
			
			var unccToolbar = UnClearCollect_grid.getTopToolbar();
			unccToolbar.items.get('UnClearCollectaddRow').hide();		// 隐藏"增加行"按钮
			unccToolbar.items.get('UnClearCollectdeleteRow').hide();	// 隐藏"删除行"按钮
			
			
			UnClearCollect_grid.on('afteredit',calclear,UnClearCollect_grid);
			UnClearCustomBill_grid.on('afteredit',calclear,UnClearCustomBill_grid);
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
	for (var i = 0; i < UnClearCustomBill_grid.getStore().getCount(); i++)
	{
		var rec = UnClearCustomBill_grid.getStore().getAt(i);
		sumclearamount += parseFloat(rec.get('clearamount'));
		sumadjustamount += parseFloat(rec.get('adjustamount'));
	}
	
	for (var i = 0; i < UnClearCollect_grid.getStore().getCount(); i++)
	{
		var rec = UnClearCollect_grid.getStore().getAt(i);
		sumnowclearamount += parseFloat(rec.get('nowclearamount'));
	}	
	var m = parseFloat(sumclearamount) - parseFloat(sumnowclearamount);
	UnClearCustomBill_grid.setTitle('清帐金额：'+ round(sumclearamount,2)+'   本次已抵消金额：'+Math.abs(round(sumnowclearamount,2)) +'      调整金额：'+ round(sumadjustamount,2)+'   差额:' + round(m,2) );
	UnClearCollect_grid.setTitle('清帐金额：'+ round(sumclearamount,2)+'   本次已抵消金额：'+Math.abs(round(sumnowclearamount,2)) +'      调整金额：'+ round(sumadjustamount,2)+'   差额:' + round(m,2) );
	

}


function checkGridItem(){
	for (var i = UnClearCustomBill_grid.getStore().getCount()-1; i >=0 ; i--)
	{
		var ds = UnClearCustomBill_grid.getStore();
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
	
	for (var i = UnClearCollect_grid.getStore().getCount()-1; i >=0 ; i--)
	{
		var ds = UnClearCollect_grid.getStore();
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

function _updateCT(){
	if (div_custom_sh_sh.getValue() == '')
	{
		_getMainFrame().showInfo("必须先选择[客户]！");
		return;
	}
	Ext.getCmp("_updateCT").setDisabled(true);
	Ext.Ajax.request({
				    	url : contextPath+'/xdss3/collect/collectController.spr?action=updateCT',
				        params : {customer:div_custom_sh_sh.getValue()},
				        success : function(xhr){
				        	Ext.getCmp("_updateCT").setDisabled(true);
				        },
				        callback:function(x){
				        	Ext.getCmp("_updateCT").setDisabled(true);
				        },
				        scope : this
				    });
}
 