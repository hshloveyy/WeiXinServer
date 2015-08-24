/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年04月21日 15点06分43秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象项目服务（测试）(TestProjectService)查看页面JS文件
 */

          

/**
 * 提交
 */
function _submitProcessTestProjectService(id)
{
	if(_presubmitProcessTestProjectService()){
		var param = Form.serialize('mainForm');	
		param = param + "&"+ Form.serialize('workflowForm');
	
		new AjaxEngine(contextPath +'/TEMP/Temp/testProjectServiceController.spr?action=_submitProcess', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	}
	_postsubmitProcessTestProjectService();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          

/**
 * 创建按钮调用方法 
 * 新增项目服务（测试）
 */
function _createTestProjectService()
{
	if(_precreateTestProjectService()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.testProjectService_Create,'',contextPath + '/TEMP/Temp/testProjectServiceController.spr?action=_create');
	}
	_postcreateTestProjectService();
}
          
function _copyCreateTestProjectService()
{
	if(_precopyCreateTestProjectService()){
		_getMainFrame().maintabs.addPanel(Txt.testProjectService_CopyCreate,'',contextPath + '/TEMP/Temp/testProjectServiceController.spr?action=_copyCreate&tsid='+tsid);
	}
	_postcopyCreateTestProjectService();
}

          

/**
 * 删除项目服务（测试）
 */
function _deleteTestProjectService()
{
if(_predeleteTestProjectService()){
		_getMainFrame().Ext.MessageBox.show({
			title:Txt.cue,
		    msg: Txt.testProjectService_Delete_ConfirmOperation,
			buttons: {yes:Txt.ok, no:Txt.cancel},
			icon: Ext.MessageBox.QUESTION,
			fn:function(result){
			   if (result == 'yes'){
					var param = '&tsid='+tsid;
					new AjaxEngine(contextPath + '/TEMP/Temp/testProjectServiceController.spr?action=_delete', 
					{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
				}
			}
		});
	 }
 	 _postdeleteTestProjectService();
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
function _saveOrUpdateTestProjectService()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("TestProjectService.tsid").value = id;	
}
          

/**
 * 取消
 */
function _cancelTestProjectService()
{
  if(_precancelTestProjectService()){
	new AjaxEngine(contextPath + '/TEMP/Temp/testProjectServiceController.spr?action=_cancel&tsid='+tsid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelTestProjectServiceCallBack});
   }
   _postcancelTestProjectService();
}

function _cancelTestProjectServiceCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
