/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年12月20日 11点02分31秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象出口押汇(BillPurchased)查看页面JS文件
 */
          
   
    

    
    

    
  

        
    
    
    
    
    
    
    
    
    
    
    
    

          
   
    

    
    

    
  

        
    
    
    
    
    
    
    
    
    
    

          
   
    

    
    

    
  

        
    
    
    
    
    
    
    
    
    
    
    

  

          
          
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

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("BillPurchased.billpurid").value = id;	
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
function _submitProcessBillPurchased(id)
{
	if (_presubmitProcessBillPurchased()) {
		var param = Form.serialize('mainForm');
		param = param + "&" + getAllBillPurBillItemGridData();
		param = param + "&" + getAllBillPurBankItemGridData();
		param = param + "&" + getAllBillPurReBankItemGridData();
		param = param + "&" + Form.serialize('settleSubjectForm');
		param = param + "&" + Form.serialize('fundFlowForm');
		param = param + "&" + Form.serialize('workflowForm') + "&type=view";

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
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
