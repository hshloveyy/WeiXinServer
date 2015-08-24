/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年05月28日 11点05分03秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>子业务对象客户信用额度下挂立项配置表(CustomerCreditProj)新增页面JS文件
 */
 
 
/**
 * 保存 
 */
function _save()
{
   if(_presave()){
		var param = tranFormToJSON(document.all.mainForm);
		
		if (_getMainFrame().ExtModalWindowUtil.getActiveExtWin())
		{
			_getMainFrame().ExtModalWindowUtil.setReturnValue(param);
			_getMainFrame().ExtModalWindowUtil.markUpdated();
			_getMainFrame().ExtModalWindowUtil.close();
		}
		else
		{
         	_getMainFrame().maintabs.getActiveTab().parentTab.setReturnValue(param);
        	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());	
        }

	}
	_postsave();
}

/**
 * 取消
 */
function _cancel()
{
	if(_precancel()){
		if (_getMainFrame().ExtModalWindowUtil.getActiveExtWin())
		{
            _getMainFrame().ExtModalWindowUtil.close();
		}
		else
		{
            _getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		}
	}
	_postcancel();
}
