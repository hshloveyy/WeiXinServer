/**
  * Author(s):java业务平台代码生成工具
  * Date: 2011年05月26日 10点55分55秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象打包贷款(PackingLoan)编辑页面JS文件
 */
 
             

    

    
/**
 *打包贷款行项目
 *批量删除
 */
function _deletesPackingBankItem()
{
	if(_predeletesPackingBankItem()){
		if (PackingBankItem_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.packingBankItem_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = PackingBankItem_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						PackingBankItem_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.packingBankItem_Deletes_SelectToOperation);
		}
	}
	_postdeletesPackingBankItem();
}
    

    
  

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
             

    

    
/**
 *打包贷款行项目
 *批量删除
 */
function _deletesPackingReBankItem()
{
	if(_predeletesPackingReBankItem()){
		if (PackingReBankItem_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.packingReBankItem_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = PackingReBankItem_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						PackingReBankItem_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.packingReBankItem_Deletes_SelectToOperation);
		}
	}
	_postdeletesPackingReBankItem();
}
    

    
  

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
             

    
             
    
    
    
  

    
  

          

function _copyCreatePackingLoan()
{
   if(_precopyCreatePackingLoan()){
		_getMainFrame().maintabs.addPanel(Txt.packingLoan_CopyCreate,'',contextPath + '/xdss3/packingloan/packingLoanController.spr?action=_copyCreate&packingid='+packingid);
   }
   _postcopyCreatePackingLoan();
}
          

/**
 * 删除打包贷款
 */
function _deletePackingLoan()
{
if(_predeletePackingLoan()){
	_getMainFrame().Ext.MessageBox.show({
		title:Txt.cue,
		msg: Txt.packingLoan_Delete_ConfirmOperation,
		buttons: {yes:Txt.ok, no:Txt.cancel},
		icon: Ext.MessageBox.QUESTION,
		fn:function(result){
			if (result == 'yes'){
				var param = '&packingid='+packingid;
				new AjaxEngine(contextPath + '/xdss3/packingloan/packingLoanController.spr?action=_delete', 
				{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
			}
		 }
	});
  }
  _postdeletePackingLoan();
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
 * 新增打包贷款
 */
function _createPackingLoan()
{
	if(_precreatePackingLoan()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.packingLoan_Create,'',contextPath + '/xdss3/packingloan/packingLoanController.spr?action=_create');
     }
     _postcreatePackingLoan();
}
          
/**
 * 保存 
 */
function _saveOrUpdatePackingLoan()
{
if(_presaveOrUpdatePackingLoan()){
	var param = Form.serialize('mainForm');	
	           
         
		        		param = param + ""+ getPackingBankItemGridData();
		        	           
         
		        		param = param + ""+ getPackingReBankItemGridData();
		        	           
         
		        		param = param + ""+ getPackingReBankTwoGridData();
		        	           
 
		        		param = param +  "&"+ Form.serialize('settleSubjectForm');		
           
 
		        		param = param +  "&"+ Form.serialize('fundFlowForm');		
    //alert(param);
	new AjaxEngine(contextPath + '/xdss3/packingloan/packingLoanController.spr?action=_saveOrUpdate', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
   }
   _postsaveOrUpdatePackingLoan();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	//var id = responseUtil.getCustomField('coustom');
	var packingid=result.packingid;
	//document.getElementById("PackingLoan.packingid").value = id;
	document.getElementById("PackingLoan.createtime").value = result.createtime;
	document.getElementById("PackingLoan.creator_text").value = result.creator_text;
	document.getElementById("PackingLoan.creator").value = result.creator;
	document.getElementById("PackingLoan.lastmodifytime").value = result.lastmodifytime;
	reload_PackingBankItem_grid("defaultCondition=YPACKINGBANKITEM.PACKINGID='"+ packingid +"'");
	reload_PackingReBankItem_grid("defaultCondition=yPackingReBank.PACKINGID='"+ packingid +"'");
	reload_PackingReBankTwo_grid("defaultCondition=YPACKINGREBANK.PACKINGID='"+ packingid +"'");
}
          
/**
 * 取消
 */
function _cancelPackingLoan()
{
  if(_precancelPackingLoan()){
	new AjaxEngine(contextPath + '/xdss3/packingloan/packingLoanController.spr?action=_cancel&packingid='+packingid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelPackingLoanCallBack});
   }
   _postcancelPackingLoan();
}
function _cancelPackingLoanCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          

/**
 * 提交
 */
function _submitProcessPackingLoan(flag)
 {
	voucherFlag = flag;
	if (_presubmitProcessPackingLoan()) {
		var param = Form.serialize('mainForm');
		param = param + "&"+ getAllPackingBankItemGridData();
		param = param + "&"+ getAllPackingReBankItemGridData();
		param = param + "&" + getAllPackingReBankTwoGridData();
		param = param +  "&"+ Form.serialize('settleSubjectForm');		
		param = param +  "&"+ Form.serialize('fundFlowForm');	
		param = param + "&"+ Form.serialize('workflowForm');
		var ns = Ext.getDom('workflowLeaveTransitionName').value; // 下一步操作
		if ((ps == '资金部出纳确认打包贷款收款' && ns == '已收款，提交财务做帐')
				|| (ps == '资金部出纳确认还打包贷款' && ns == '确认还打包贷款已收，提交财务做帐')) {
			Ext.MessageBox.confirm(Txt.cue, '请确认是否已经登记现金日记账？', function(result) {
				if (result == 'yes') {
					new AjaxEngine(
							contextPath
									+ '/xdss3/packingloan/packingLoanController.spr?action=_submitProcess',
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
							+ '/xdss3/packingloan/packingLoanController.spr?action=_submitProcess',
					{
						method : "post",
						parameters : param,
						onComplete : callBackHandle,
						callback : _submitCallBackHandle
					});
		}
	}
	_postsubmitProcessPackingLoan();
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
				    	_getMainFrame().maintabs.addPanel2('凭证查看', contextPath+'/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.get('PackingLoan.packingid').dom.value);
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
          
