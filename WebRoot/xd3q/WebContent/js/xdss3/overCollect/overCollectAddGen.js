/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年06月01日 06点24分20秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>子业务对象多收款表(OverCollect)新增页面JS文件
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
