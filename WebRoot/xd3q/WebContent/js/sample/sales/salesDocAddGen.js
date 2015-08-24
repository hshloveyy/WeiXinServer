/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年12月04日 16点13分42秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象SAP销售订单(SalesDoc)增加页面JS文件
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
