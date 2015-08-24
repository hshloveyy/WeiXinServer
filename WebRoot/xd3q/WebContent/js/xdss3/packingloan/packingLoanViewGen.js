/**
  * Author(s):java业务平台代码生成工具
  * Date: 2011年05月26日 10点55分56秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象打包贷款(PackingLoan)查看页面JS文件
 */
          
   
    

    
    

    
  

        
    
    
    
    
    
    
    
    
    
    
    
    
    

          
   
    

    
    

    
  

        
    
    
    
    
    
    
    
    
    
    
    
    
    
    

          
   

    
          
   
    
    
    
  

    
  

          
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

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("PackingLoan.packingid").value = id;	
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
function _submitProcessPackingLoan(id)
{
	if(_presubmitProcessPackingLoan()){
		var param = Form.serialize('mainForm');	
	            
         
		param = param + "&" + getAllPackingBankItemGridData();
		param = param + "&" + getAllPackingReBankItemGridData();
		param = param + "&" + getAllPackingReBankTwoGridData();
		param = param  + "&" +  Form.serialize('settleSubjectForm');		
		param = param  + "&" +  Form.serialize('fundFlowForm');		
		param = param + "&"+ Form.serialize('workflowForm')  + "&type=view";
	
		new AjaxEngine(contextPath +'/xdss3/packingloan/packingLoanController.spr?action=_submitProcess', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	}
	_postsubmitProcessPackingLoan();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
          
