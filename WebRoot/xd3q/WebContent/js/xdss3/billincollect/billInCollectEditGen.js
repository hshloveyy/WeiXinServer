/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年06月13日 09点36分33秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>子业务对象未清预收款(BillInCollect)编辑页面JS文件
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

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	_getMainFrame().ExtModalWindowUtil.markUpdated();
	_getMainFrame().showInfo(Txt.operateSuccess);
	_getMainFrame().ExtModalWindowUtil.close();
}

