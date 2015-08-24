/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年12月04日 10点48分24秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象SAP采购凭证(PurchasingDoc)增加页面JS文件
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
