/**
  * Author(s):java业务平台代码生成工具
  * Date: 2011年11月03日 17点08分05秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象资金占用调整(FundAdjustment)增加页面JS文件
 */


          
          
          
/**
 * 保存 
 */
function _saveOrUpdateFundAdjustment()
{					 
    if(isCreateCopy){
		document.getElementById("FundAdjustment.fundid").value = "";
	}
	if(_presaveOrUpdateFundAdjustment()){
		var param = Form.serialize('mainForm');	
	    new AjaxEngine(contextPath + '/xdss3/fundadjustment/fundAdjustmentController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdateFundAdjustment();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var fundid=result.fundid;
	document.getElementById("FundAdjustment.fundid").value = fundid;
	
	document.getElementById("FundAdjustment.fundno").value = result.fundno;
	document.getElementById("FundAdjustment.creator_text").value = result.creator_text;
	document.getElementById("FundAdjustment.creator").value = result.creator;
	document.getElementById("FundAdjustment.createtime").value = result.createtime;
	document.getElementById("FundAdjustment.lastmodifytime").value = result.lastmodifytime;
	document.getElementById("FundAdjustment.lastmodifyer_text").value = result.lastmodifyer_text;
	document.getElementById("FundAdjustment.lastmodifyer").value = result.lastmodifyer;
	isCreateCopy = false;	

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
          
