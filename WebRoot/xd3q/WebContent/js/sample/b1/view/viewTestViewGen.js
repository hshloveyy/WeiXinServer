/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年03月22日 15点32分32秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象视图测试(ViewTest)查看页面JS文件
 */

          

/**
 * 取消
 */
function _cancelViewTest()
{
  if(_precancelViewTest()){
	new AjaxEngine(contextPath + '/sample/b1/view/viewTestController.spr?action=_cancel&yeuserid='+yeuserid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelViewTestCallBack});
   }
   _postcancelViewTest();
}

function _cancelViewTestCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
