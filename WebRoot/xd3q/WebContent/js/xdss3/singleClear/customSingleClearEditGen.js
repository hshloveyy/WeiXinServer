/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月19日 11点44分18秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象客户单清帐(CustomSingleClear)编辑页面JS文件
 */
 
             

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
             

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
             

    
             
    
    
    
  

    
  

          
          
/**
 * 保存 
 */
function _saveOrUpdateCustomSingleClear()
{
if(_presaveOrUpdateCustomSingleClear()){
	var param = Form.serialize('mainForm');	
	           
         
		        		//param = param + ""+ getUnClearCustomBillGridData();
		        	    param = param + ""+ getAllUnClearCustomBillGridData();       
         
		        		//param = param + ""+ getUnClearCollectGridData();
		        	    param = param + "" + getAllUnClearCollectGridData();       
 
		        		param = param +  "&"+ Form.serialize('settleSubjectForm');		
           
 
		        		param = param +  "&"+ Form.serialize('fundFlowForm');		
    //alert(param);
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
	//var id = responseUtil.getCustomField('coustom');
	var customsclearid=result.customsclearid;
	//document.getElementById("CustomSingleClear.customsclearid").value = id;
	document.getElementById("CustomSingleClear.createtime").value = result.createtime;
	document.getElementById("CustomSingleClear.creator_text").value = result.creator_text;
	document.getElementById("CustomSingleClear.creator").value = result.creator;
	document.getElementById("CustomSingleClear.lastmodifytime").value = result.lastmodifytime;
	document.getElementById("CustomSingleClear.lastmodifyer").value = result.lastmodifyer;
	reload_UnClearCustomBill_grid("defaultCondition=YUNCLEARCUSTBILL.CUSTOMSCLEARID='"+ customsclearid +"'");
	reload_UnClearCollect_grid("defaultCondition=YUNCLEARCOLLECT.CUSTOMSCLEARID='"+ customsclearid +"'");
}
          
/**
 * 取消
 */
function _cancelCustomSingleClear()
{
  if(_precancelCustomSingleClear()){
	new AjaxEngine(contextPath + '/xdss3/singleClear/customSingleClearController.spr?action=_cancel&customsclearid='+customsclearid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelCustomSingleClearCallBack});
   }
   _postcancelCustomSingleClear();
}
function _cancelCustomSingleClearCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
          
          
          
          
