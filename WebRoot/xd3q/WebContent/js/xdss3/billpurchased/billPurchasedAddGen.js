/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年12月20日 11点02分31秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象出口押汇(BillPurchased)增加页面JS文件
 */

             
    
    
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
					var applyamount = Ext.getDom("BillPurchased.applyamount");
					if (applyamount) {
						applyamount.value = 0 ;
						for(var i=0;i<BillPurBillItem_grid.getStore().getCount();i++){
							applyamount.value = parseFloat(applyamount.value) + parseFloat(BillPurBillItem_grid.getStore().getAt(i).get('amount'));
						}
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

    
    
  
    
    
  

          
          
          
          
          
/**
 * 保存 
 */
function _saveOrUpdateBillPurchased()
{					 
    if(isCreateCopy){
		document.getElementById("BillPurchased.billpurid").value = "";
	}
	if(_presaveOrUpdateBillPurchased()){
		var param = Form.serialize('mainForm');	
		           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllBillPurBillItemGridData();
		}
		else
		{
			param = param + ""+ getBillPurBillItemGridData(); 
		}
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllBillPurBankItemGridData();
		}
		else
		{
			param = param + ""+ getBillPurBankItemGridData(); 
		}
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllBillPurReBankItemGridData();
		}
		else
		{
			param = param + ""+ getBillPurReBankItemGridData(); 
		}
		
		if(isCreateCopy)
		{
			param = param + ""+ getAllBillPurReBankTwoGridData();
		}
		else
		{
			param = param + "" + getBillPurReBankTwoGridData();
		}
			        	           
 
		param = param + "&" + Form.serialize('settleSubjectForm');		
           
 
		param = param + "&" + Form.serialize('fundFlowForm');		
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
	var billpurid=result.billpurid;
	var billpur_no=result.billpur_no;
	document.getElementById("BillPurchased.billpurid").value = billpurid;
	document.getElementById("BillPurchased.billpur_no").value = billpur_no;
	
	document.getElementById("BillPurchased.creator_text").value = result.creator_text;
	document.getElementById("BillPurchased.creator").value = result.creator;
	document.getElementById("BillPurchased.createtime").value = result.createtime;
	isCreateCopy = false;	
	reload_BillPurBillItem_grid("defaultCondition=YBILLPURBILLITEM.BILLPURID='"+ billpurid +"'");
	reload_BillPurBankItem_grid("defaultCondition=YBILLPURBANKITEM.BILLPURID='"+ billpurid +"'");
	reload_BillPurReBankItem_grid("defaultCondition=YBILLPURREBANK.BILLPURID='"+ billpurid +"'");
	document.getElementById("SettleSubject.settlesubjectid").value=result.settlesubjectid;
	document.getElementById("FundFlow.fundflowid").value=result.fundflowid;

    if(Ext.getCmp("_submitProcess").disabled){
    	Ext.getCmp("_submitProcess").setDisabled(false);}
}

          
/**
 * 取消
 */
function _cancel()
{
	if(_precancel()){
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	 }
	 _postcancel();
}
          

/**
 * 提交
 */
function _submitProcessBillPurchased()
{
if(_presubmitProcessBillPurchased()){
	var param = Form.serialize('mainForm');	
          
         
	param = param + "&" + getAllBillPurBillItemGridData();
	        	          
         
	param = param + "&" + getAllBillPurBankItemGridData();
	        	          
         
	param = param + "&" + getAllBillPurReBankItemGridData();
	        	          
 
	param = param + "&" + Form.serialize('settleSubjectForm');		
          
 
	param = param + "&" + Form.serialize('fundFlowForm');		
	param = param + "&"+ Form.serialize('workflowForm');
	
	new AjaxEngine(contextPath +'/xdss3/billpurchased/billPurchasedController.spr?action=_submitProcess', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
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

          
