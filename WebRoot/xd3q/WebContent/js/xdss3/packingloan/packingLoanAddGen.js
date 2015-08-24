/**
  * Author(s):java业务平台代码生成工具
  * Date: 2011年05月26日 10点55分55秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象打包贷款(PackingLoan)增加页面JS文件
 */

             
    
    
/**
 *打包贷款行项目
 *批量删除
 */
function _deletesPackingBankItem()
{
}

    
    
  
    
    
             
    
    
/**
 *打包贷款行项目
 *批量删除
 */
function _deletesPackingReBankItem()
{
}

    
    
  
    
    
             
  
    
    
             
    
    
    
  
    
    
  

          
          
          
          
/**
 * 保存 
 */
function _saveOrUpdatePackingLoan()
{					 
    if(isCreateCopy){
		document.getElementById("PackingLoan.packingid").value = "";
	}
	if(_presaveOrUpdatePackingLoan()){
		var param = Form.serialize('mainForm');	
		           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllPackingBankItemGridData();
		}
		else
		{
			param = param + ""+ getPackingBankItemGridData(); 
		}
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllPackingReBankItemGridData();
		}
		else
		{
			param = param + ""+ getPackingReBankItemGridData(); 
		}
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllPackingReBankTwoGridData();
		}
		else
		{
			param = param + ""+ getPackingReBankTwoGridData(); 
		}
			        	           
 
		param = param + "&" + Form.serialize('settleSubjectForm');		
           
 
		param = param + "&" + Form.serialize('fundFlowForm');		
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
	var packingid=result.packingid;
	document.getElementById("PackingLoan.packingid").value = packingid;
	
	document.getElementById("PackingLoan.packing_no").value = result.packing_no;
	document.getElementById("PackingLoan.createtime").value = result.createtime;
	document.getElementById("PackingLoan.creator_text").value = result.creator_text;
	document.getElementById("PackingLoan.creator").value = result.creator;
	document.getElementById("PackingLoan.lastmodifytime").value = result.lastmodifytime;
	isCreateCopy = false;	
	reload_PackingBankItem_grid("defaultCondition=YPACKINGBANKITEM.PACKINGID='"+ packingid +"'");
	reload_PackingReBankItem_grid("defaultCondition=yPackingReBank.PACKINGID='"+ packingid +"'");
	reload_PackingReBankTwo_grid("defaultCondition=YPACKINGREBANK.PACKINGID='"+ packingid +"'");
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
function _submitProcessPackingLoan()
{
if(_presubmitProcessPackingLoan()){
	var param = Form.serialize('mainForm');	
          
         
	param = param + "&" + getAllPackingBankItemGridData();
	        	          
         
	param = param + "&" + getAllPackingReBankItemGridData();
	        	          
         
	param = param + "&" + getAllPackingReBankTwoGridData();
	        	          
 
	param = param + "&" + Form.serialize('settleSubjectForm');		
          
 
	param = param + "&" + Form.serialize('fundFlowForm');		
	param = param + "&"+ Form.serialize('workflowForm');
	
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

          
          
