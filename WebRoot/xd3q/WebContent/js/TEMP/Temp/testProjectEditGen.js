/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年04月21日 15点06分41秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象项目（测试）(TestProject)编辑页面JS文件
 */
 

          

/**
 * 提交
 */
function _submitProcessTestProject()
{
if(_presubmitProcessTestProject()){
	var param = Form.serialize('mainForm');	
	param = param + "&"+ Form.serialize('workflowForm');
	
	new AjaxEngine(contextPath +'/TEMP/Temp/testProjectController.spr?action=_submitProcess', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
  }
  _postsubmitProcessTestProject();
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
 * 取消
 */
function _cancelTestProject()
{
  if(_precancelTestProject()){
	new AjaxEngine(contextPath + '/TEMP/Temp/testProjectController.spr?action=_cancel&projectid='+projectid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelTestProjectCallBack});
   }
   _postcancelTestProject();
}
function _cancelTestProjectCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          

function _copyCreateTestProject()
{
   if(_precopyCreateTestProject()){
		_getMainFrame().maintabs.addPanel(Txt.testProject_CopyCreate,'',contextPath + '/TEMP/Temp/testProjectController.spr?action=_copyCreate&projectid='+projectid);
   }
   _postcopyCreateTestProject();
}
          

/**
 * 删除项目（测试）
 */
function _deleteTestProject()
{
if(_predeleteTestProject()){
	_getMainFrame().Ext.MessageBox.show({
		title:Txt.cue,
		msg: Txt.testProject_Delete_ConfirmOperation,
		buttons: {yes:Txt.ok, no:Txt.cancel},
		icon: Ext.MessageBox.QUESTION,
		fn:function(result){
			if (result == 'yes'){
				var param = '&projectid='+projectid;
				new AjaxEngine(contextPath + '/TEMP/Temp/testProjectController.spr?action=_delete', 
				{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
			}
		 }
	});
  }
  _postdeleteTestProject();
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
 * 创建按钮调用方法 
 * 新增项目（测试）
 */
function _createTestProject()
{
	if(_precreateTestProject()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.testProject_Create,'',contextPath + '/TEMP/Temp/testProjectController.spr?action=_create');
     }
     _postcreateTestProject();
}
          
/**
 * 保存 
 */
function _saveOrUpdateTestProject()
{
if(_presaveOrUpdateTestProject()){
	var param = Form.serialize('mainForm');	
    //alert(param);
	new AjaxEngine(contextPath + '/TEMP/Temp/testProjectController.spr?action=_saveOrUpdate', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
   }
   _postsaveOrUpdateTestProject();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	//var id = responseUtil.getCustomField('coustom');
	var projectid=result.projectid;
	//document.getElementById("TestProject.projectid").value = id;
	document.getElementById("TestProject.creator_text").value = result.creator_text;
	document.getElementById("TestProject.creator").value = result.creator;
	document.getElementById("TestProject.createtime").value = result.createtime;
	document.getElementById("TestProject.lastmodifyer_text").value = result.lastmodifyer_text;
	document.getElementById("TestProject.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("TestProject.lastmodifytime").value = result.lastmodifytime;
}
