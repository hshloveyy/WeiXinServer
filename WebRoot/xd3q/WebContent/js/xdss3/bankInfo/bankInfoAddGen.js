/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年06月29日 11点38分28秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象银行信息(BankInfo)增加页面JS文件
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
function _saveOrUpdateBankInfo()
{					 
    if(isCreateCopy){
		document.getElementById("BankInfo.bank_id").value = "";
	}
	if(_presaveOrUpdateBankInfo()){
		var param = Form.serialize('mainForm');	
	    new AjaxEngine(contextPath + '/xdss3/bankInfo/bankInfoController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdateBankInfo();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var bank_id=result.bank_id;
	document.getElementById("BankInfo.bank_id").value = bank_id;
	
	isCreateCopy = false;	

}

