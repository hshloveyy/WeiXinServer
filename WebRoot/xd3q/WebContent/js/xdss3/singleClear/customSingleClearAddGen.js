/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月19日 11点44分18秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象客户单清帐(CustomSingleClear)增加页面JS文件
 */

             
  
    
    
             
  
    
    
             
  
    
    
             
    
    
    
  
    
    
  

          

          
/**
 * 保存 
 */
function _saveOrUpdateCustomSingleClear()
{					 
    if(isCreateCopy){
		document.getElementById("CustomSingleClear.customsclearid").value = "";
	}
	if(_presaveOrUpdateCustomSingleClear()){
		var param = Form.serialize('mainForm');	
		           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllUnClearCustomBillGridData();
		}
		else
		{
			//param = param + ""+ getUnClearCustomBillGridData(); 
			param = param + ""+ getAllUnClearCustomBillGridData();
		}
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllUnClearCollectGridData();
		}
		else
		{
			//param = param + ""+ getUnClearCollectGridData(); 
			param = param + "" + getAllUnClearCollectGridData();  
		}
			        	           
 
		param = param + "&" + Form.serialize('settleSubjectForm');		
           
 
		param = param + "&" + Form.serialize('fundFlowForm');		
	    new AjaxEngine(contextPath + '/xdss3/singleClear/customSingleClearController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdateCustomSingleClear();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var customsclearid=result.customsclearid;
	document.getElementById("CustomSingleClear.customsclearid").value = customsclearid;
	
	document.getElementById("CustomSingleClear.createtime").value = result.createtime;
	document.getElementById("CustomSingleClear.creator_text").value = result.creator_text;
	document.getElementById("CustomSingleClear.creator").value = result.creator;
	document.getElementById("CustomSingleClear.lastmodifytime").value = result.lastmodifytime;
	document.getElementById("CustomSingleClear.lastmodifyer").value = result.lastmodifyer;
	isCreateCopy = false;	
	reload_UnClearCustomBill_grid("defaultCondition=YUNCLEARCUSTBILL.CUSTOMSCLEARID='"+ customsclearid +"'");
	reload_UnClearCollect_grid("defaultCondition=YUNCLEARCOLLECT.CUSTOMSCLEARID='"+ customsclearid +"'");
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
          

          

          

          

          

