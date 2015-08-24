/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年04月21日 15点06分41秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象项目（测试）(TestProject)增加页面JS文件
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
function _cancel()
{
	if(_precancel()){
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	 }
	 _postcancel();
}
          
          
          
          
/**
 * 保存 
 */
function _saveOrUpdateTestProject()
{					 
//if(isCreateCopy){
	//	document.getElementById("TestProject.projectid").value = "";
	//}
	if(_presaveOrUpdateTestProject()){
		var param = Form.serialize('mainForm');	
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
	var projectid=result.projectid;
	document.getElementById("TestProject.projectid").value = projectid;
	
	document.getElementById("TestProject.creator_text").value = result.creator_text;
	document.getElementById("TestProject.creator").value = result.creator;
	document.getElementById("TestProject.createtime").value = result.createtime;
	document.getElementById("TestProject.lastmodifyer_text").value = result.lastmodifyer_text;
	document.getElementById("TestProject.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("TestProject.lastmodifytime").value = result.lastmodifytime;
	isCreateCopy = false;	

    if(Ext.getCmp("_submitProcess").disabled){
    	Ext.getCmp("_submitProcess").setDisabled(false);}
}

