/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年03月23日 15点52分47秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象OrdrRep(OrdrRep)查看页面JS文件
 */

          

/**
 * 取消
 */
function _cancelOrdrRep()
{
  if(_precancelOrdrRep()){
	new AjaxEngine(contextPath + '/sample/b1/ordr/ordrRepController.spr?action=_cancel&docentry='+docentry, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelOrdrRepCallBack});
   }
   _postcancelOrdrRep();
}

function _cancelOrdrRepCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
