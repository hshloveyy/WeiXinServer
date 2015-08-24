/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年09月16日 09点35分05秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象票清收款(BillClearCollect)增加页面JS文件
 */

             
    
    
  
    
    
             
    
    
  
    
    
             
    
  
    
    
             
    
    
  
    
    
  

          

          

          

          
          
          
          
/**
 * 保存 
 */
function _saveOrUpdateBillClearCollect()
{					 
    if(isCreateCopy){
		document.getElementById("BillClearCollect.billclearid").value = "";
	}
	if(_presaveOrUpdateBillClearCollect()){
		var param = Form.serialize('mainForm');	
		           
 
		param = param + "&" + Form.serialize('settleSubjectBccForm');		
           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllBillClearItemGridData();
		}
		else
		{
			param = param + ""+ getBillClearItemGridData(); 
		}
			        	           
 
		param = param + "&" + Form.serialize('fundFlowBccForm');		
           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllBillInCollectGridData();
		}
		else
		{
			param = param + ""+ getBillInCollectGridData(); 
		}
			        		    new AjaxEngine(contextPath + '/xdss3/billclearcollect/billClearCollectController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdateBillClearCollect();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var billclearid=result.billclearid;
	document.getElementById("BillClearCollect.billclearid").value = billclearid;
	
	document.getElementById("BillClearCollect.billclearno").value = result.billclearno;
	document.getElementById("BillClearCollect.creator_text").value = result.creator_text;
	document.getElementById("BillClearCollect.creator").value = result.creator;
	document.getElementById("BillClearCollect.createtime").value = result.createtime;
	document.getElementById("BillClearCollect.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("BillClearCollect.lastmodifytime").value = result.lastmodifytime;
	isCreateCopy = false;	
	document.getElementById("SettleSubjectBcc.settlesubjectid").value=result.settlesubjectid;
	reload_BillClearItem_grid("defaultCondition=YBILLCLEARITEM.BILLCLEARID='"+ billclearid +"'");
	document.getElementById("FundFlowBcc.fundflowid").value=result.fundflowid;
	reload_BillInCollect_grid("defaultCondition=YBILLINCOLLECT.BILLCLEARID='"+ billclearid +"'");

    if(Ext.getCmp("_submitProcess").disabled){
    	Ext.getCmp("_submitProcess").setDisabled(false);}
    if(Ext.getCmp("_submitForReassign").disabled){
    	Ext.getCmp("_submitForReassign").setDisabled(false);} 
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
function _submitProcessBillClearCollect()
{
if(_presubmitProcessBillClearCollect()){
	var param = Form.serialize('mainForm');	
          
 
	param = param + "&" + Form.serialize('settleSubjectBccForm');		
          
         
//	param = param + "&" + getAllBillClearItemGridData();
	param = param + ""+ getBillClearItemGridData();         	          
 	param = param + ""+ getBillInCollectGridData(); 
	param = param + "&" + Form.serialize('fundFlowBccForm');		
          
         
//	param = param + "&" + getAllBillInCollectGridData();
	        		param = param + "&"+ Form.serialize('workflowForm');
	
	new AjaxEngine(contextPath +'/xdss3/billclearcollect/billClearCollectController.spr?action=_submitProcess', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
  }
  _postsubmitProcessBillClearCollect();
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
function _deletesBillClearItem(){}

function _deletesBillInCollect(){}