/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年10月16日 09点40分31秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>子业务对象Portlet(Portlet)新增页面JS文件
 */
 
 
/**
 * 保存 
 */
function _save()
{
	var param = tranFormToJSON(document.all.mainForm);
	//Ext.apply(param, {'Portlet.url':$('Portlet.url').value});
	_getMainFrame().ExtModalWindowUtil.setReturnValue(param);
	_getMainFrame().ExtModalWindowUtil.markUpdated();
	_getMainFrame().ExtModalWindowUtil.close();
}

/**
 * 取消
 */
function _cancel()
{
	_getMainFrame().ExtModalWindowUtil.close();
}
