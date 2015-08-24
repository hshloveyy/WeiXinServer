/**
  * Author(s):java业务平台代码生成工具
  * Date: 2011年11月25日 11点42分18秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象利率(Rate)查看页面JS文件
 */

          
/**
 * 保存 
 */
function _saveOrUpdateRate()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("Rate.rateid").value = id;	
}
          

/**
 * 取消
 */
function _cancelRate()
{
  if(_precancelRate()){
	new AjaxEngine(contextPath + '/xdss3/rate/rateController.spr?action=_cancel&rateid='+rateid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelRateCallBack});
   }
   _postcancelRate();
}

function _cancelRateCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
