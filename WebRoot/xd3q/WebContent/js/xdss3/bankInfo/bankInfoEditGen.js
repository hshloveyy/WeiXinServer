/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年06月29日 11点38分28秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象银行信息(BankInfo)编辑页面JS文件
 */
 

          
/**
 * 取消
 */
function _cancelBankInfo()
{
  if(_precancelBankInfo()){
	new AjaxEngine(contextPath + '/xdss3/bankInfo/bankInfoController.spr?action=_cancel&bank_id='+bank_id, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelBankInfoCallBack});
   }
   _postcancelBankInfo();
}
function _cancelBankInfoCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
/**
 * 保存 
 */
function _saveOrUpdateBankInfo()
{
if(_presaveOrUpdateBankInfo()){
	var param = Form.serialize('mainForm');	
    //alert(param);
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
	//var id = responseUtil.getCustomField('coustom');
	var bank_id=result.bank_id;
	//document.getElementById("BankInfo.bank_id").value = id;
}
