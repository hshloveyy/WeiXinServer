/**
  * Author(s):java业务平台代码生成工具
  * Date: 2011年11月24日 16点19分06秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象利率(Rate)增加页面用户可编程扩展JS文件
 */

Ext.onReady(function(){
	var startdate = calendar_Rate_startdate.getValue();
	if (startdate != '') {
		calendar_Rate_startdate.setEditable(false);
	}
	
	calendar_Rate_enddate.minValue = calendar_Rate_startdate.getValue();
    
});
          
/**
 * 保存 
 */
function _presaveOrUpdateRate()
{
	return true ;
}

/**
 * 保存 
 */
function _postsaveOrUpdateRate()
{
}

          

/**
 * 取消
 */
function _precancel()
{
	return true;
}

/**
 * 取消
 */
function _postcancel()
{

}
          
