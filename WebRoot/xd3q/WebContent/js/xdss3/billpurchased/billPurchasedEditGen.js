/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年12月20日 11点02分31秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象出口押汇(BillPurchased)编辑页面JS文件
 */
 
             

    
var voucherFlag = false;
    
/**
 *出口押汇行项目
 *批量删除
 */
function _deletesBillPurBillItem()
{
	if(_predeletesBillPurBillItem()){
		if (BillPurBillItem_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.billPurBillItem_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = BillPurBillItem_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						BillPurBillItem_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.billPurBillItem_Deletes_SelectToOperation);
		}
	}
	_postdeletesBillPurBillItem();
}
    

    
  

    
    
    
    
    
    
    
    
    
    
    
    
    
    
             

    

    
/**
 *出口押汇行项目
 *批量删除
 */
function _deletesBillPurBankItem()
{
	if(_predeletesBillPurBankItem()){
		if (BillPurBankItem_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.billPurBankItem_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = BillPurBankItem_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						BillPurBankItem_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.billPurBankItem_Deletes_SelectToOperation);
		}
	}
	_postdeletesBillPurBankItem();
}
    

    
  

    
    
    
    
    
    
    
    
    
    
    
    
             

    

    
/**
 *出口押汇行项目
 *批量删除
 */
function _deletesBillPurReBankItem()
{
	if(_predeletesBillPurReBankItem()){
		if (BillPurReBankItem_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.billPurReBankItem_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = BillPurReBankItem_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						BillPurReBankItem_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.billPurReBankItem_Deletes_SelectToOperation);
		}
	}
	_postdeletesBillPurReBankItem();
}
    

    
  

    
    
    
    
    
    
    
    
    
    
    
    
    
  

          
          

function _copyCreateBillPurchased()
{
   if(_precopyCreateBillPurchased()){
		_getMainFrame().maintabs.addPanel(Txt.billPurchased_CopyCreate,'',contextPath + '/xdss3/billpurchased/billPurchasedController.spr?action=_copyCreate&billpurid='+billpurid);
   }
   _postcopyCreateBillPurchased();
}
          

/**
 * 删除出口押汇
 */
function _deleteBillPurchased()
{
if(_predeleteBillPurchased()){
	_getMainFrame().Ext.MessageBox.show({
		title:Txt.cue,
		msg: Txt.billPurchased_Delete_ConfirmOperation,
		buttons: {yes:Txt.ok, no:Txt.cancel},
		icon: Ext.MessageBox.QUESTION,
		fn:function(result){
			if (result == 'yes'){
				var param = '&billpurid='+billpurid;
				new AjaxEngine(contextPath + '/xdss3/billpurchased/billPurchasedController.spr?action=_delete', 
				{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
			}
		 }
	});
  }
  _postdeleteBillPurchased();
}

/**
 * 删除成功后的回调动作
 */
function _deleteCallBackHandle()
{
	_getMainFrame().showInfo(Txt.operateSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

          

/**
 * 创建按钮调用方法 
 * 新增出口押汇
 */
function _createBillPurchased()
{
	if(_precreateBillPurchased()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.billPurchased_Create,'',contextPath + '/xdss3/billpurchased/billPurchasedController.spr?action=_create');
     }
     _postcreateBillPurchased();
}
          
/**
 * 保存 
 */
function _saveOrUpdateBillPurchased()
{
if(_presaveOrUpdateBillPurchased()){
	var param = Form.serialize('mainForm');	
	           
         
		        		param = param + "&"+ getBillPurBillItemGridData();
		        	           
         
		        		param = param + "&"+ getBillPurBankItemGridData();
		        	           
         
		        		param = param + "&"+ getBillPurReBankItemGridData();
		        		param = param + "&" + getBillPurReBankTwoGridData();
		        	           
 
		        		param = param +  "&"+ Form.serialize('settleSubjectForm');		
           
 
		        		param = param +  "&"+ Form.serialize('fundFlowForm');		
	new AjaxEngine(contextPath + '/xdss3/billpurchased/billPurchasedController.spr?action=_saveOrUpdate', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
   }
   _postsaveOrUpdateBillPurchased();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	//var id = responseUtil.getCustomField('coustom');
	var billpurid=result.billpurid;
	var billpur_no=result.billpur_no;
	//document.getElementById("BillPurchased.billpurid").value = id;
	document.getElementById("BillPurchased.billpur_no").value = billpur_no;
	document.getElementById("BillPurchased.creator_text").value = result.creator_text;
	document.getElementById("BillPurchased.creator").value = result.creator;
	document.getElementById("BillPurchased.createtime").value = result.createtime;
	reload_BillPurBillItem_grid("defaultCondition=YBILLPURBILLITEM.BILLPURID='"+ billpurid +"'");
	reload_BillPurBankItem_grid("defaultCondition=YBILLPURBANKITEM.BILLPURID='"+ billpurid +"'");
	reload_BillPurReBankItem_grid("defaultCondition=YBILLPURREBANK.BILLPURID='"+ billpurid +"'");
	reload_BillPurReBankTwo_grid("defaultCondition=YBILLPURREBANK.BILLPURID='"+ billpurid +"'");
}
          
/**
 * 取消
 */
function _cancelBillPurchased()
{
  if(_precancelBillPurchased()){
	new AjaxEngine(contextPath + '/xdss3/billpurchased/billPurchasedController.spr?action=_cancel&billpurid='+billpurid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelBillPurchasedCallBack});
   }
   _postcancelBillPurchased();
}
function _cancelBillPurchasedCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          

/**
 * 提交
 */
function _submitProcessBillPurchased(flag)
 {
	voucherFlag = flag;
	if (_presubmitProcessBillPurchased()) {
		var param = Form.serialize('mainForm');
		param = param + "&" + getBillPurBillItemGridData();
		param = param + "&" + getBillPurBankItemGridData();
		param = param + "&" + getBillPurReBankItemGridData();
		param = param + "&" + getBillPurReBankTwoGridData();
		param = param + "&" + Form.serialize('settleSubjectForm');
		param = param + "&" + Form.serialize('fundFlowForm');
		param = param + "&" + Form.serialize('workflowForm');

		var ns = Ext.getDom('workflowLeaveTransitionName').value; // 下一步操作
		if ((ps == '资金部出纳确认押汇收款' && ns == '已收押汇款，提交财务做帐')
				|| (ps == '资金部出纳确认还押汇' && ns == '确认还押汇款已收，提交财务做帐')) {
			Ext.MessageBox.confirm(Txt.cue, '请确认是否已经登记现金日记账？', function(result) {
				if (result == 'yes') {
					new AjaxEngine(
							contextPath
									+ '/xdss3/billpurchased/billPurchasedController.spr?action=_submitProcess',
							{
								method : "post",
								parameters : param,
								onComplete : callBackHandle,
								callback : _submitCallBackHandle
							});
				}
			});
		} else { // 不是资金部节点
			new AjaxEngine(
					contextPath
							+ '/xdss3/billpurchased/billPurchasedController.spr?action=_submitProcess',
					{
						method : "post",
						parameters : param,
						onComplete : callBackHandle,
						callback : _submitCallBackHandle
					});
		}
	}
	_postsubmitProcessBillPurchased();
}


/**
 * 提交后的回调动作
 */
function _submitCallBackHandle(transport) {
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
				    	_getMainFrame().maintabs.addPanel2('凭证查看', contextPath+'/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.get('BillPurchased.billpurid').dom.value);
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
          
