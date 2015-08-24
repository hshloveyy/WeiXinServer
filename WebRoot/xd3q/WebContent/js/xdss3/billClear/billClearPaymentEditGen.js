/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年09月17日 08点27分44秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象票清付款(BillClearPayment)编辑页面JS文件
 */
 
             

    

    
/**
 *票清付款行项目
 *批量删除
 */
function _deletesBillClearItemPay()
{
	if(_predeletesBillClearItemPay()){
		if (BillClearItemPay_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.billClearItemPay_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = BillClearItemPay_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						BillClearItemPay_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.billClearItemPay_Deletes_SelectToOperation);
		}
	}
	_postdeletesBillClearItemPay();
}
    

    
  

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
             

    

    
/**
 *票清付款行项目
 *批量删除
 */
function _deletesBillInPayment()
{
	if(_predeletesBillInPayment()){
		if (BillInPayment_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.billInPayment_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = BillInPayment_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						BillInPayment_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.billInPayment_Deletes_SelectToOperation);
		}
	}
	_postdeletesBillInPayment();
}
    

    
  

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
             
    
    
    
  

    
             

    
  

          
          
/**
 * 保存 
 */
function _saveOrUpdateBillClearPayment()
{
if(_presaveOrUpdateBillClearPayment()){
	var param = Form.serialize('mainForm');	
	           
         
		        		param = param + ""+ getBillClearItemPayGridData();
		        	           
         
		        		param = param + ""+ getBillInPaymentGridData();
		        	           
 
		        		param = param +  "&"+ Form.serialize('fundFlowForm');		
           
 
		        		param = param +  "&"+ Form.serialize('settleSubjectForm');		
    //alert(param);
	new AjaxEngine(contextPath + '/xdss3/billClear/billClearPaymentController.spr?action=_saveOrUpdate', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
   }
   _postsaveOrUpdateBillClearPayment();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	//var id = responseUtil.getCustomField('coustom');
	var billclearid=result.billclearid;
	//document.getElementById("BillClearPayment.billclearid").value = id;
	document.getElementById("BillClearPayment.createtime").value = result.createtime;
	document.getElementById("BillClearPayment.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("BillClearPayment.lastmodifytime").value = result.lastmodifytime;
	document.getElementById("BillClearPayment.creator").value = result.creator;
	reload_BillClearItemPay_grid("defaultCondition=YBILLCLEARITEM.BILLCLEARID='"+ billclearid +"'");
	reload_BillInPayment_grid("defaultCondition=YBILLINPAYMENT.BILLCLEARID='"+ billclearid +"'");
}
          

/**
 * 提交
 */
function _submitProcessBillClearPayment()
{
if(_presubmitProcessBillClearPayment()){
	var param = Form.serialize('mainForm');	
          
         
	//param = param + "&" + getAllBillClearItemPayGridData();
	        	          
         
	//param = param + "&" + getAllBillInPaymentGridData();
	        	          
 	param = param + ""+ getBillClearItemPayGridData();
		        	           
         
	param = param + ""+ getBillInPaymentGridData();
	
	param = param + "&" + Form.serialize('fundFlowForm');		
          
 
	param = param + "&" + Form.serialize('settleSubjectForm');		
	param = param + "&"+ Form.serialize('workflowForm');
	
	new AjaxEngine(contextPath +'/xdss3/billClear/billClearPaymentController.spr?action=_submitProcess', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
  }
  _postsubmitProcessBillClearPayment();
}

/**
 * 提交后的回调动作
 */
 /**
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
**/
          
/**
 * 取消
 */
function _cancelBillClearPayment()
{
  if(_precancelBillClearPayment()){
	new AjaxEngine(contextPath + '/xdss3/billClear/billClearPaymentController.spr?action=_cancel&billclearid='+billclearid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelBillClearPaymentCallBack});
   }
   _postcancelBillClearPayment();
}
function _cancelBillClearPaymentCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
          
          
