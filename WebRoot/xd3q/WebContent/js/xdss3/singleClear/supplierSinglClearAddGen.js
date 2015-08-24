/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月14日 16点59分08秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象供应商单清帐(SupplierSinglClear)增加页面JS文件
 */

             
  
    
    
             
  
    
    
             
    
    
    
  
    
    
             
  
    
    
  

          
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
 * 保存 
 */
function _saveOrUpdateSupplierSinglClear()
{					 
    if(isCreateCopy){
		document.getElementById("SupplierSinglClear.suppliersclearid").value = "";
	}
	if(_presaveOrUpdateSupplierSinglClear()){
		var param = Form.serialize('mainForm');	
		           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllUnClearSupplieBillGridData();
		}
		else
		{
			//param = param + ""+ getUnClearSupplieBillGridData(); 
			param = param + ""+ getAllUnClearSupplieBillGridData();
		}
			        	           
         
		if(isCreateCopy)
		{
			param = param + ""+ getAllUnClearPaymentGridData();
		}
		else
		{
			//param = param + ""+ getUnClearPaymentGridData(); 
			param = param + ""+ getAllUnClearPaymentGridData();
		}
			        	           
 
		param = param + "&" + Form.serialize('fundFlowForm');		
           
 
		param = param + "&" + Form.serialize('settleSubjectForm');		
	    new AjaxEngine(contextPath + '/xdss3/singleClear/supplierSinglClearController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdateSupplierSinglClear();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var suppliersclearid=result.suppliersclearid;
	document.getElementById("SupplierSinglClear.suppliersclearid").value = suppliersclearid;
	
	document.getElementById("SupplierSinglClear.createtime").value = result.createtime;
	document.getElementById("SupplierSinglClear.creator").value = result.creator;
	document.getElementById("SupplierSinglClear.lastmodifytime").value = result.lastmodifytime;
	document.getElementById("SupplierSinglClear.lastmodifyer").value = result.lastmodifyer;
	isCreateCopy = false;	
	reload_UnClearSupplieBill_grid("defaultCondition=YUNCLEARSUPPBILL.SUPPLIERSCLEARID='"+ suppliersclearid +"'");
	reload_UnClearPayment_grid("defaultCondition=YUNCLEARPAYMENT.SUPPLIERSCLEARID='"+ suppliersclearid +"'");
	document.getElementById("FundFlow.fundflowid").value=result.fundflowid;
	document.getElementById("SettleSubject.settlesubjectid").value=result.settlesubjectid;

    if(Ext.getCmp("_submitProcess").disabled){
    	Ext.getCmp("_submitProcess").setDisabled(false);}
}

          

