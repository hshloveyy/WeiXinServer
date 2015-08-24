/**
  * Author(s):java业务平台代码生成工具
  * Date: 2012年06月11日 09点24分59秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象其他公司客户单清帐(CustomSingleOther)编辑页面JS文件
 */
 
             

    

    

    

    
/**
 * 保存 
 */
function _saveOrUpdateCustomSingleOther()
{					 
 
	if(_presaveOrUpdateCustomSingleOther()){
		var param = Form.serialize('mainForm');		
		param = param + ""+ getAllUnCustomBillOtherGridData();
		param = param + "" + getAllUnCleaCollectOtherGridData();  
		param = param + "&" + Form.serialize('settleSubjectOtherForm');	
		param = param + "&" + Form.serialize('fundFlowOtherForm');		
	    new AjaxEngine(contextPath + '/xdss3/singleClearOther/customSingleOtherController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

//	_postsaveOrUpdateCustomSingleClear();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var customsclearid=result.customsclearid;
	document.getElementById("CustomSingleOther.customsclearid").value = customsclearid;
	
	document.getElementById("CustomSingleOther.createtime").value = result.createtime;
	
	document.getElementById("CustomSingleOther.customclearno").value = result.customclearno;
   
	document.getElementById("CustomSingleOther.lastmodifytime").value = result.lastmodifytime;
	document.getElementById("CustomSingleOther.lastmodifyer").value = result.lastmodifyer;
	
	reload_UnCustomBillOther_grid("defaultCondition=YUNCUSTBILLOTHER.CUSTOMSCLEARID='"+ customsclearid +"'");
	reload_UnCleaCollectOther_grid("defaultCondition=YUNCOLLECTOTHER.CUSTOMSCLEARID='"+ customsclearid +"'");
	document.getElementById("SettleSubjectOther.settlesubjectid").value=result.settlesubjectid;
	document.getElementById("FundFlowOther.fundflowid").value=result.fundflowid;
	document.getElementById("CustomSingleOther.creator_text").value = result.creator_text;
	document.getElementById("CustomSingleOther.creator").value = result.creator;
}
    
    

    

    

    
  

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
             

    

    

    

    

    

    

    

    
  

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
             

    
             

    
  

          
          
