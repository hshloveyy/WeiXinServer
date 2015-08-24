/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年04月22日 17点19分07秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象项目管理（测试）(TestProjectManage)查看页面JS文件
 */

          

/**
 * 创建按钮调用方法 
 * 新增项目管理（测试）
 */
function _createTestProjectManage()
{
	if(_precreateTestProjectManage()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.testProjectManage_Create,'',contextPath + '/TEMP/Temp/testProjectManageController.spr?action=_create');
	}
	_postcreateTestProjectManage();
}
          
function _copyCreateTestProjectManage()
{
	if(_precopyCreateTestProjectManage()){
		_getMainFrame().maintabs.addPanel(Txt.testProjectManage_CopyCreate,'',contextPath + '/TEMP/Temp/testProjectManageController.spr?action=_copyCreate&projectid='+projectid);
	}
	_postcopyCreateTestProjectManage();
}

          

/**
 * 删除项目管理（测试）
 */
function _deleteTestProjectManage()
{
if(_predeleteTestProjectManage()){
		_getMainFrame().Ext.MessageBox.show({
			title:Txt.cue,
		    msg: Txt.testProjectManage_Delete_ConfirmOperation,
			buttons: {yes:Txt.ok, no:Txt.cancel},
			icon: Ext.MessageBox.QUESTION,
			fn:function(result){
			   if (result == 'yes'){
					var param = '&projectid='+projectid;
					new AjaxEngine(contextPath + '/TEMP/Temp/testProjectManageController.spr?action=_delete', 
					{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
				}
			}
		});
	 }
 	 _postdeleteTestProjectManage();
}

/**
 * 删除成功后的回调动作
 */
function _deleteCallBackHandle()
{
	_getMainFrame().showInfo(Txt.operateSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

          
/**
 * 保存 
 */
function _saveOrUpdateTestProjectManage()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("TestProjectManage.projectid").value = id;	
}
          

/**
 * 取消
 */
function _cancelTestProjectManage()
{
  if(_precancelTestProjectManage()){
	new AjaxEngine(contextPath + '/TEMP/Temp/testProjectManageController.spr?action=_cancel&projectid='+projectid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelTestProjectManageCallBack});
   }
   _postcancelTestProjectManage();
}

function _cancelTestProjectManageCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          

/**
 * 提交
 */
function _submitProcessTestProjectManage(id)
{
	if(_presubmitProcessTestProjectManage()){
		var param = Form.serialize('mainForm');	
		param = param + "&"+ Form.serialize('workflowForm');
	
		new AjaxEngine(contextPath +'/TEMP/Temp/testProjectManageController.spr?action=_submitProcess', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	}
	_postsubmitProcessTestProjectManage();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
