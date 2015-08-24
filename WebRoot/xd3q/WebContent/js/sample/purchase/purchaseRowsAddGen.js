/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年01月09日 11点03分54秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>子业务对象采购订单行项目信息(SAP)(PurchaseRows)新增页面JS文件
 */
 
 
/**
 * 保存 
 */
function _save()
{
   if(_presave()){
		var param = tranFormToJSON(document.all.mainForm);
		_getMainFrame().ExtModalWindowUtil.setReturnValue(param);
		_getMainFrame().ExtModalWindowUtil.markUpdated();
		_getMainFrame().ExtModalWindowUtil.close();
	}
	_postsave();
}

/**
 * 取消
 */
function _cancel()
{
	if(_precancel()){
		_getMainFrame().ExtModalWindowUtil.close();
	}
	_postcancel();
}
