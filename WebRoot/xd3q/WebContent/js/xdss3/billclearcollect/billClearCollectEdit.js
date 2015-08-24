/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年06月11日 03点10分50秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象票清预收款(BillClearCollect)编辑页面用户可编程扩展JS文件
 */
 
          
    
  
function _predeletesBillClearItem()
{
	var ischange = false;
	var customertitleids = '';
	var records = BillClearItem_grid.getSelectionModel().getSelections();        			
	for(i=0;i<records.size();i++)
    {
    	ischange = true;
		BillClearItem_grid.getStore().remove(records[i]);
    }

	if(ischange){
		for(var i=0;i<BillClearItem_grid.getStore().getCount();i++){
			var rec = BillClearItem_grid.getStore().getAt(i);
			customertitleids += rec.get('titleid') + ",";
		}
		
		customertitleids = customertitleids.substring(0,customertitleids.length-1);
		_assignAmount(customertitleids);
	}
	return false ;
}

function _predeletesBillInCollect() 
{
	var records = BillInCollect_grid.getSelectionModel().getSelections();        			
	for(i=0;i<records.size();i++)
    {
		BillInCollect_grid.getStore().remove(records[i]);
    }
    return false ;
}
    
function _postdeletesBillClearItem()
{

}

function _postdeletesBillInCollect()
{

}

function _precopyCreateBillClearCollect()
{
	return true ;
}

function _postcopyCreateBillClearCollect()
{

}
          

/**
 * 删除票清预收款
 */
function _predeleteBillClearCollect()
{
	return true ;
}

/**
 * 删除票清预收款
 */
function _postdeleteBillClearCollect()
{

}
          

/**
 * 创建按钮调用方法 
 * 新增票清预收款
 */
function _precreateBillClearCollect()
{
	return true ;
}

/**
 * 创建按钮调用方法 
 * 新增票清预收款
 */
function _postcreateBillClearCollect()
{

}
          
/**
 * 保存 
 */
function _presaveOrUpdateBillClearCollect()
{
	removeGrid();
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
	
		var param = Form.serialize('mainForm');	   
	param = param + "&" + Form.serialize('settleSubjectBccForm');
	param = param + ""+ getAllBillClearItemGridData();         	          
 	param = param + ""+ getAllBillInCollectGridData(); 
	param = param + "&" + Form.serialize('fundFlowBccForm');	
	param = param + "&"+ Form.serialize('workflowForm');
	//ajax同步调用
	var url = contextPath + '/xdss3/billclearcollect/billClearCollectController.spr?action=checkClearData&';	
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
function _postsaveOrUpdateBillClearCollect()
{
	return true;
}
          
/**
 * 取消
 */
function _precancelBillClearCollect()
{
	return true ;
}

/**
 * 取消
 */
function _postcancelBillClearCollect()
{

}
          

/**
 * 提交
 */
function _presubmitProcessBillClearCollect()
{
	var ns = Ext.getDom('workflowLeaveTransitionName').value;		// 下一步操作
	if(document.getElementById('BillClearCollect.processstate').value == '会计审批' && ns == '退回业务修改')return true;
	
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
	
	var param = Form.serialize('mainForm');	   
	param = param + "&" + Form.serialize('settleSubjectBccForm');
	param = param + ""+ getAllBillClearItemGridData();         	          
 	param = param + ""+ getAllBillInCollectGridData(); 
	param = param + "&" + Form.serialize('fundFlowBccForm');	
	param = param + "&"+ Form.serialize('workflowForm');
	//ajax同步调用
	var url = contextPath + '/xdss3/billclearcollect/billClearCollectController.spr?action=checkClearData&';	
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
function _postsubmitProcessBillClearCollect()
{

}

/**
 * 模拟凭证
 */
function _voucherPreviewBillClearCollect()
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
	param = param + "" + getAllBillClearItemGridData();
	param = param + "" + getAllBillInCollectGridData();
	param = param + "&" + Form.serialize('settleSubjectBccForm');
	param = param + "&" + Form.serialize('fundFlowBccForm');
	
	new AjaxEngine(contextPath + '/xdss3/billclearcollect/billClearCollectController.spr?action=_voucherPreview',
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
        	Ext.get('currentWorkflowTask').mask();
			Ext.get('centercontent').mask();
			Ext.get(Ext.getCmp("tabs").getActiveTab().getItemId()).mask();
			
			Ext.getCmp("tabs").on('tabchange',function(t,p){
				Ext.get(p.getItemId()).mask();
			});
			
        	_getMainFrame().maintabs.addPanel('票清收款凭证预览', '', contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid=' + result,closeVoucherCallBack,true);
		}
	}
}
/**
 * 模拟凭证
 */
function _voucherPreviewBillClearCollect2()
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
	param = param + "" + getAllBillClearItemGridData();
	param = param + "" + getAllBillInCollectGridData();
	param = param + "&" + Form.serialize('settleSubjectBccForm');
	param = param + "&" + Form.serialize('fundFlowBccForm');
	
	new AjaxEngine(contextPath + '/xdss3/billclearcollect/billClearCollectController.spr?action=voucherPreview2',
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

function closeVoucherCallBack(flag){
	Ext.get('currentWorkflowTask').unmask();
	Ext.get('centercontent').unmask();
	Ext.get(Ext.getCmp("tabs").getActiveTab().getItemId()).unmask();
	
	Ext.getCmp("tabs").on('tabchange',function(t,p){
		Ext.get(p.getItemId()).unmask();
	});
	if(flag){
		voucherFlag = flag;
		_submitProcessBillClearCollect(flag);
		
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
				    	_getMainFrame().maintabs.addPanel2('凭证查看', contextPath+'/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.get('BillClearCollect.billclearid').dom.value);
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

//未清收款
function _addBillInCollect() 
{
	if(div_customer_sh_sh.getValue()==''){
		_getMainFrame().showInfo("必须先选择客户(付款单位)");
		return;
	}
	if (div_deptid_sh_sh.getValue() == '')
	{
		_getMainFrame().showInfo("必须先选择[部门]！");
		return;
	}
	var deptid = div_deptid_sh_sh.getValue();
	
	searchCollectItemWin.defaultCondition = "CUSTOMER='" + div_customer_sh_sh.getValue() + "'  and ( ISCLEAR='0' or trim(ISCLEAR) is null )   and trim(VOUCHERNO) is not null and BUSINESSSTATE != '-1'  and VOUCHERCLASS='1'  and dept_id='" + deptid + "'";
	searchCollectItemWin.show();
}

//发票清账页签
function _addBillClearItem() 
{
	if(div_customer_sh_sh.getValue()==''){
		_getMainFrame().showInfo("必须先选择客户(付款单位)");
		return;
	}
	if (div_deptid_sh_sh.getValue() == '')
	{
		_getMainFrame().showInfo("必须先选择[部门]！");
		return;
	}
	var deptid = div_deptid_sh_sh.getValue();
	
	searchCustomerTitleWin.defaultCondition = "KUNNR='" + div_customer_sh_sh.getValue() + "' and ( ISCLEARED='0' or trim(ISCLEARED) is null ) and trim(INVOICE) is not null and dept_id='" + deptid+ "'";
	searchCustomerTitleWin.show();
}

var searchCustomerTitleWin = new Ext.SearchHelpWindow({
	    shlpName : 'YHCUSTOMERTITLE2'
		,callBack : winCustomerTitleCallBack
});

var searchCollectItemWin = new Ext.SearchHelpWindow({
	    shlpName : 'YHCOLLECTITEM'
		,callBack : winCollectItemCallBack
});

function winCollectItemCallBack(jsonArrayData)
{
	var collectitemids = '';
	for(var i=0;i<jsonArrayData.length;i++){
		var num = BillInCollect_grid.getStore().find('collectitemid',jsonArrayData[i].COLLECTITEMID);
		if(num==-1)collectitemids += jsonArrayData[i].COLLECTITEMID + ",";
	}
	collectitemids = collectitemids.substring(0,collectitemids.length-1);
	var billclearid = Ext.get("BillClearCollect.billclearid").dom.value;
	if(collectitemids!=''){
		Ext.Ajax.request({
	    	url : contextPath+'/xdss3/billclearcollect/billClearCollectController.spr?action=getCollectItemData',
	        params : {collectitemids:collectitemids,billclearid:billclearid},
	        success : function(xhr){
				if(xhr.responseText){
		        	var jsonData = Ext.util.JSON.decode(xhr.responseText);
		        	
		        	//var billclearitemid = BillClearItem_grid.getSelectionModel().getSelected().get('billclearitemid');
		        	//BillInCollect_grid.getStore().clearFilter();
		        	for(var j=0;j<jsonData.data.length;j++){
		        		var data = jsonData.data[j];
				    	//var nowclearedamount = BillInCollect_grid.getStore().sumquery('collectitemid',data.collectitemid,'nowclearamount');
		        		//Ext.apply(data,{'billclearitemid':billclearitemid,'nowclearedamount':nowclearedamount});
		        		var p = new Ext.data.Record(data);
						BillInCollect_grid.stopEditing();
						BillInCollect_grid.getStore().insert(0, p);
					    BillInCollect_grid.startEditing(0, 0);
		        	}
		        	//BillInCollect_grid.getStore().filter('billclearitemid',billclearitemid);
				}
	        },
	        scope : this
	    });
	}
}

function winCustomerTitleCallBack(jsonArrayData){
	var customertitleids = '';
	for(var i=0;i<jsonArrayData.length;i++){
		var num = BillClearItem_grid.getStore().find('titleid',jsonArrayData[i].CUSTOMERTITLEID);
		if(num==-1){
			customertitleids += jsonArrayData[i].CUSTOMERTITLEID + ",";
		}
	}
	if(customertitleids=="")return;
	customertitleids = customertitleids.substring(0,customertitleids.length-1);
	var billclearid = Ext.get("BillClearCollect.billclearid").dom.value;
	
	Ext.Ajax.request({
    	url : contextPath+'/xdss3/billclearcollect/billClearCollectController.spr?action=getBillClearItemData',
        params : {customertitleids:customertitleids,billclearid:billclearid},
        success : function(xhr){
			if(xhr.responseText){
				
	        	var jsonData = Ext.util.JSON.decode(xhr.responseText);	        	
	        	BillClearItem_grid.getStore().clearFilter();	        	
	        	for(var j=0;j<jsonData.data.length;j++){	        		
	        		var p = new Ext.data.Record(jsonData.data[j]);
					BillClearItem_grid.stopEditing();
					BillClearItem_grid.getStore().insert(0, p);
				    BillClearItem_grid.startEditing(0, 0);
	        	}
	        	//UnClearCustomBill_grid.getStore().sort([{field:'contract_no',direction: 'ASC'}, {field:'project_no',direction:'ASC'}], 'ASC');
			}
        },
        scope : this
    });
}
/**
function winCustomerTitleCallBack(jsonArrayData)
{
	var isExists = true;
	var changed = false;
	var customertitleids = '';
	var invoicenos = '';
	for(var i=0;i<jsonArrayData.length;i++){
		var num = BillClearItem_grid.getStore().find('titleid',jsonArrayData[i].CUSTOMERTITLEID);
		if(num == -1){
			isExists = false;
		}
		
		if (isExists == false){
			changed = true;
			var projectRecvFields = new  BillClearItem_fields({
				client:'',
				billclearitemid:'',
				billclearid:'',
				contract_no:'',
				project_no:'',
				voucherno:'',
				titleid:'',
				invoice:'',
				old_contract_no:'',
				sap_order_no:'',
				currency:'',
				exchangerate:'',
				receivableamount:'',
				receivabledate:'',
				receivedamount:'',
				unreceivedamount:'',
				onroadamount:'',
				clearbillamount:'',
				adjustamount:'',
				bktxt:'',
				accountdate:''
			});
			
			BillClearItem_grid.stopEditing();
			BillClearItem_grid.getStore().insert(0, projectRecvFields);
			BillClearItem_grid.startEditing(0, 0);
			var record = BillClearItem_grid.getStore().getAt(0);
			
			record.set('client','');
			record.set('billclearitemid','');
			record.set('billclearid','');
			record.set('contract_no',jsonArrayData[i].IHREZ);
			record.set('project_no',jsonArrayData[i].BNAME);
			record.set('voucherno',jsonArrayData[i].BELNR);
			record.set('titleid',jsonArrayData[i].CUSTOMERTITLEID);
			record.set('invoice',jsonArrayData[i].INVOICE);
			record.set('old_contract_no',jsonArrayData[i].OLD_CONTRACT_NO);
			record.set('sap_order_no',jsonArrayData[i].VBELN);
			record.set('currency',jsonArrayData[i].WAERS);
			//record.set('exchangerate','');//押汇汇率
			record.set('receivableamount',jsonArrayData[i].DMBTR);
			record.set('receivabledate','');
			//record.set('receivedamount','');
			//record.set('unreceivedamount','0');
			//record.set('onroadamount','0');
			record.set('clearbillamount','0');
			record.set('adjustamount','0');
			record.set('bwbje',jsonArrayData[i].BWBJE);//本位币
			record.set('bktxt',jsonArrayData[i].BKTXT);//抬头文本
			record.set('accountdate',jsonArrayData[i].BUDAT);//记账日期
		}
	}
	
	if(changed){
		for(var i=0;i<BillClearItem_grid.getStore().getCount();i++){
			var rec = BillClearItem_grid.getStore().getAt(i);
			invoicenos += rec.get('invoice') + ",";
		}
		invoicenos  = invoicenos.substring(0,invoicenos.length-1);
		var conn = Ext.lib.Ajax.getConnectionObject().conn; 
		conn.open("POST", contextPath + '/xdss3/billclearcollect/billClearCollectController.spr?action=getBillClearItemData&invoicenos=' + invoicenos,false);
		conn.send(null);
		var jsonArrayData = Ext.util.JSON.decode(conn.responseText);
		for(var j=0;j<jsonArrayData.data.length;j++){
	        var jsonData = jsonArrayData.data[j];
	        //alert(jsonData.receivedamount + " " + jsonData.onroadamount + " " + jsonData.onroadamount + " & " + BillClearItem_grid.getStore().getAt(j).get('receivableamount'));
	        BillClearItem_grid.stopEditing();
	        var receivableamount = BillClearItem_grid.getStore().getAt(j).get('receivableamount');
	        BillClearItem_grid.getStore().getAt(j).set('receivedamount',jsonData.receivedamount);
	        BillClearItem_grid.getStore().getAt(j).set('onroadamount',jsonData.onroadamount);
	        BillClearItem_grid.getStore().getAt(j).set('unreceivedamount',parseFloat(receivableamount)-parseFloat(jsonData.onroadamount));
	        BillClearItem_grid.startEditing(j, j);
	    }
	}
}
**/

/**
 * 自动分配票清收款
 */
function _autoAssignBillClearCollect()
{
	var customertitleids = '';
	var invoicenos = '';
	for(var i=0;i<BillClearItem_grid.getStore().getCount();i++){
		var rec = BillClearItem_grid.getStore().getAt(i);
		customertitleids += rec.get('titleid') + ",";
		invoicenos += rec.get('invoice') + ',';
	}
	
	customertitleids = customertitleids.substring(0,customertitleids.length-1);
	invoicenos = invoicenos.substring(0, invoicenos.length-1); 
	
	_assignAmount(customertitleids,invoicenos);
}

function _clearAssignBillClearCollect()
{
	for(var i=0;i<BillClearItem_grid.getStore().getCount();i++){
		var rec = BillClearItem_grid.getStore().getAt(i);
		rec.set('clearbillamount', 0);
		rec.set('adjustamount', 0);
	}
    
	for(var i=0;i<BillInCollect_grid.getStore().getCount();i++){
		var rec = BillInCollect_grid.getStore().getAt(i);
		rec.set('nowclearamount', 0);
	}
}

function _assignAmount(customertitleids,invoicenos)
{
	if(customertitleids==""){
		BillInCollect_grid.getStore().removeAll();
		
		return;
	}
	// 若收款清票页签下有数据，则不到数据库里带出该合同下的所有清票，直接给那些数据填写金额
	var collectitemids='';	
	for(var i=0;i<BillInCollect_grid.getStore().getCount();i++){
		var rec = BillInCollect_grid.getStore().getAt(i);
		collectitemids += rec.get('collectitemid') + ',';
	}
	
	var billclearid = Ext.get("BillClearCollect.billclearid").dom.value;
	var url= contextPath+'/xdss3/billclearcollect/billClearCollectController.spr?action=assignAmount';
	var param={customer:div_customer_sh_sh.getValue(),customertitleids:customertitleids,invoicenos:invoicenos,billclearid:billclearid,collectitemids:collectitemids};
	
	new AjaxEngine(url, {method:"post", parameters: param, onComplete: assignAmountCallBackHandle});
	
	
}

function assignAmountCallBackHandle(xhr){
	var promptMessagebox = new MessageBoxUtil();
	promptMessagebox.Close();
	
	if(xhr.responseText){
	        	var jsonData = Ext.util.JSON.decode(xhr.responseText);
	        	_clearAssignBillClearCollect();
	        	for(var j=0;j<jsonData.data.length;j++){
	        		var num = BillClearItem_grid.getStore().find('titleid',jsonData.data[j].titleid);
	        		
	        		if(num==-1){
		        		var p = new Ext.data.Record(jsonData.data[j]);
						BillClearItem_grid.stopEditing();
						BillClearItem_grid.getStore().insert(0, p);
					    BillClearItem_grid.startEditing(0, 0);
	        		}else{
	        			var recs = BillClearItem_grid.getStore().query('titleid',jsonData.data[j].titleid);
	        			
	        			recs.first().set('clearbillamount',jsonData.data[j].clearbillamount);
	        		}
	        	}
	        	
	        	for(var j=0;j<jsonData.data2.length;j++){
	        		var num = BillInCollect_grid.getStore().find('collectitemid',jsonData.data2[j].collectitemid);
	        		
	        		if(num==-1){
		        		var p = new Ext.data.Record(jsonData.data2[j]);
						BillInCollect_grid.stopEditing();
						BillInCollect_grid.getStore().insert(0, p);
					    BillInCollect_grid.startEditing(0, 0);
	        		}else{
	        			var recs = BillInCollect_grid.getStore().query('collectitemid',jsonData.data2[j].collectitemid);
	        			
	        			recs.first().set('nowclearamount',jsonData.data2[j].nowclearamount);
	        		}
	        		
	        	}
	        	showTitle();
			}

}
/**
 * 重分配提交
 * @return
 */
function _submitForReassignBillClearCollect()
{	
	if(_presubmitProcessBillClearCollect()){
		var param = Form.serialize('mainForm');	
	          
	 
		param = param + "&" + Form.serialize('settleSubjectBccForm');		
	          
	         
		param = param + "&" + getAllBillClearItemGridData();
		        	          
	 
		param = param + "&" + Form.serialize('fundFlowBccForm');		
	          
	         
		param = param + "&" + getAllBillInCollectGridData();
		        		param = param + "&"+ Form.serialize('workflowForm');
		
		new AjaxEngine(contextPath +'/xdss3/billclearcollect/billClearCollectController.spr?action=_submitForReassign', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	  }
	  _postsubmitProcessBillClearCollect();
}

Ext.onReady(function(){
	Ext.getCmp("_voucherPreview").hide();
	if(isReassign != 'Y'){	// 若不为重分配
		if (document.getElementById('BillClearCollect.processstate').value == '会计审批') {
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
			Ext.getCmp('_voucherPreview2').hide();
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
			Ext.getCmp('_voucherPreview2').hide();
		}
	}
	/**
    div_customer_sh_sh.callBack = function(data){
		//先同步一下客户单清帐
		
		Ext.Ajax.request({
		    	url : contextPath+'/xdss3/collect/collectController.spr?action=syncUnclearCustomer',
		        params : {customer:data.KUNNR},
		        success : function(xhr){
		        },
		        scope : this
		    });
	}
    **/
   var uncbToolbar = BillClearItem_grid.getTopToolbar();
			uncbToolbar.items.get('BillClearItemaddRow').hide();		// 隐藏"增加行"按钮
	 var uncbToolbar = BillInCollect_grid.getTopToolbar();
			uncbToolbar.items.get('BillInCollectaddRow').hide();		// 隐藏"增加行"按钮
	
	BillClearItem_grid.on('afteredit',calclear,BillClearItem_grid);
	BillInCollect_grid.on('afteredit',calclear,BillInCollect_grid);
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
	for (var i = 0; i < BillClearItem_grid.getStore().getCount(); i++)
	{
		var rec = BillClearItem_grid.getStore().getAt(i);
		sumclearamount += parseFloat(rec.get('clearbillamount'));
		sumadjustamount += parseFloat(rec.get('adjustamount'));
	}
	
	for (var i = 0; i < BillInCollect_grid.getStore().getCount(); i++)
	{
		var rec = BillInCollect_grid.getStore().getAt(i);
		sumnowclearamount += parseFloat(rec.get('nowclearamount'));
	}	
	var m = parseFloat(sumclearamount) - parseFloat(sumnowclearamount);
	BillClearItem_grid.setTitle('清帐金额：'+ round(sumclearamount,2)+'   本次已抵消金额：'+Math.abs(round(sumnowclearamount,2)) +'      调整金额：'+ round(sumadjustamount,2)+'   差额:' + round(m,2) );
	BillInCollect_grid.setTitle('清帐金额：'+ round(sumclearamount,2)+'   本次已抵消金额：'+Math.abs(round(sumnowclearamount,2)) +'      调整金额：'+ round(sumadjustamount,2)+'   差额:' + round(m,2) );
 	
 }
 /**
 * 财务流程模拟凭证、提交流程时调用该方法
 * @returns {String}
 */
function _checkFilds()
{
	if (div_customer_sh_sh.getValue() == '')
	{
		return "必须先选择[客户]！";
	}
	if (document.getElementById('BillClearCollect.processstate').value == '会计审批'){
		if(document.getElementById("BillClearCollect.accountdate").value == ''){
			return "必须先填写[记账日期]！";
		}
		if(document.getElementById("BillClearCollect.voucherdate").value == ''){
			return "必须先填写[凭证日期]！";
		}
		
		var sumclearamount = 0;
		var sumadjustamount = 0;
		var sumnowclearamount = 0;
		//未收款金额总和
		var unclearBillAmount = 0;
		//未抵消金额总和
		var unclearAmount = 0;
		var accountdate = document.getElementById("BillClearCollect.accountdate").value;
		for (var i = 0; i < BillClearItem_grid.getStore().getCount(); i++)
		{
			var rec = BillClearItem_grid.getStore().getAt(i);
			sumclearamount += parseFloat(rec.get('clearbillamount'));
			sumadjustamount += parseFloat(rec.get('adjustamount'));
			unclearBillAmount += parseFloat(rec.get('unreceivedamount'));
			//日期比较大小,0:小于 1：等于 2：大于 3 错误	
			if( DateUtils.dateCompareStr(accountdate,rec.get('accountdate')) ==0 )return "清账的过账日期一定要大于或等于借贷方的过账日期";
		}
		
		for (var i = 0; i < BillInCollect_grid.getStore().getCount(); i++)
		{
			var rec = BillInCollect_grid.getStore().getAt(i);
			sumnowclearamount += parseFloat(rec.get('nowclearamount'));
			unclearAmount += parseFloat(rec.get('unoffsetamount'));
			//日期比较大小,0:小于 1：等于 2：大于 3 错误	
			if( DateUtils.dateCompareStr(accountdate,rec.get('accountdate')) ==0 )return "清账的过账日期一定要大于或等于借贷方的过账日期";
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
		
		if ($('FundFlowBcc.amount1').value)
		{
			famount1 = parseFloat($('FundFlowBcc.amount1').value);
			if (famount1 < 0)
			{
				return "纯资金往来[金额1]必需输入正数！";
			}
			fdebtcredit1 = $('FundFlowBcc.debtcredit1').value;
			if (!fdebtcredit1 && famount1>0)
			{
				return "必须选择纯资金往来[借贷1]！";
			}
		}
		if ($('FundFlowBcc.amount2').value)
		{
			famount2 = parseFloat($('FundFlowBcc.amount2').value);
			if (famount2 < 0)
			{
				return "纯资金往来[金额2]必需输入正数！";
			}
			fdebtcredit2 = $('FundFlowBcc.debtcredit2').value;
			if (!fdebtcredit2 && famount2>0)
			{
				return "必须选择纯资金往来[借贷2]！";
			}
		}
		if ($('FundFlowBcc.amount3').value)
		{
			famount3 = parseFloat($('FundFlowBcc.amount3').value);
			if (famount3 < 0)
			{
				return "纯资金往来[金额1]必需输入正数！";
			}
			fdebtcredit3 = $('FundFlowBcc.debtcredit3').value;
			if (!fdebtcredit3 && famount3>0)
			{
				return "必须选择纯资金往来[借贷3]！";
			}
		}
		
		if ($('SettleSubjectBcc.amount1').value)
		{
			samount1 = parseFloat($('SettleSubjectBcc.amount1').value);
			if (samount1 < 0)
			{
				return "结算科目[金额1]必需输入正数！";
			}
			sdebtcredit1 = $('SettleSubjectBcc.debtcredit1').value;
			if (!sdebtcredit1 && samount1>0)
			{
				return "必须选择结算科目[借贷1]！";
			}
		}
		if ($('SettleSubjectBcc.amount2').value)
		{
			samount2 = parseFloat($('SettleSubjectBcc.amount2').value);
			if (samount2 < 0)
			{
				return "结算科目[金额2]必需输入正数！";
			}
			sdebtcredit2 = $('SettleSubjectBcc.debtcredit2').value;
			if (!sdebtcredit2  && samount2>0)
			{
				return "必须选择结算科目[借贷2]！";
			}
		}
		if ($('SettleSubjectBcc.amount3').value)
		{
			samount3 = parseFloat($('SettleSubjectBcc.amount3').value);
			if (samount3 < 0)
			{
				return "结算科目[金额3]必需输入正数！";
			}
			sdebtcredit3 = $('SettleSubjectBcc.debtcredit3').value;
			if (!sdebtcredit3  && samount3>0)
			{
				return "必须选择结算科目[借贷3]！";
			}
		}
		if ($('SettleSubjectBcc.amount4').value)
		{
			samount4 = parseFloat($('SettleSubjectBcc.amount4').value);
			if (samount4 < 0)
			{
				return "结算科目[金额4]必需输入正数！";
			}
			sdebtcredit4 = $('SettleSubjectBcc.debtcredit4').value;
			if (!sdebtcredit4 && samount4>0)
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
		
	}else{
	 	return "";
	}
}

/**
*自动删除为0的行项目
**/
function removeGrid(){
	for (var i = BillClearItem_grid.getStore().getCount()-1; i >=0 ; i--)
	{
		var ds = BillClearItem_grid.getStore();
		var rec = ds.getAt(i);
		var clearmount = rec.get('clearbillamount');
		var receivableamount = rec.get('receivableamount');
		if((clearmount=='0' || clearmount=='') && receivableamount !='0'){
			ds.remove(rec);
		}		
	}
	
	for (var i = BillInCollect_grid.getStore().getCount()-1; i >=0 ; i--)
	{
		var ds = BillInCollect_grid.getStore();
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
	BillClearItem_grid.setTitle('清帐金额：'+ round(sumclearamount,2)+'   本次已抵消金额：'+Math.abs(round(sumnowclearamount,2)) +'      调整金额：'+ round(sumadjustamount,2)+'   差额:' + round(m,2) );
	BillInCollect_grid.setTitle('清帐金额：'+ round(sumclearamount,2)+'   本次已抵消金额：'+Math.abs(round(sumnowclearamount,2)) +'      调整金额：'+ round(sumadjustamount,2)+'   差额:' + round(m,2) );
 	
}


function checkGridItem(){
	var flag = false;
	var flag2 =  false;
	for (var i = BillClearItem_grid.getStore().getCount()-1; i >=0 ; i--)
	{
		var ds = BillClearItem_grid.getStore();
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
	
	for (var i = BillInCollect_grid.getStore().getCount()-1; i >=0 ; i--)
	{
		var ds = BillInCollect_grid.getStore();
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
