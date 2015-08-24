/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年10月16日 09点40分32秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>子业务对象Portlet(Portlet)查看页面JS文件
 */
 

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	_getMainFrame().ExtModalWindowUtil.markUpdated();
	_getMainFrame().showInfo('操作成功！   ');
	_getMainFrame().ExtModalWindowUtil.close();
}

/**
 * 取消
 */
function _cancel()
{
	_getMainFrame().ExtModalWindowUtil.close();
}
