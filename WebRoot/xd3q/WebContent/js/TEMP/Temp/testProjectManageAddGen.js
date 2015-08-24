/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年04月22日 17点19分08秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象项目管理（测试）(TestProjectManage)增加页面JS文件
 */


          
          
          
          
/**
 * 保存 
 */
function _saveOrUpdateTestProjectManage()
{					 
//if(isCreateCopy){
	//	document.getElementById("TestProjectManage.projectid").value = "";
	//}
	if(_presaveOrUpdateTestProjectManage()){
		var param = Form.serialize('mainForm');	
	    new AjaxEngine(contextPath + '/TEMP/Temp/testProjectManageController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdateTestProjectManage();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var projectid=result.projectid;
	document.getElementById("TestProjectManage.projectid").value = projectid;
	
	document.getElementById("TestProjectManage.creator_text").value = result.creator_text;
	document.getElementById("TestProjectManage.creator").value = result.creator;
	document.getElementById("TestProjectManage.createtime").value = result.createtime;
	document.getElementById("TestProjectManage.lastmodifyer_text").value = result.lastmodifyer_text;
	document.getElementById("TestProjectManage.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("TestProjectManage.lastmodifytime").value = result.lastmodifytime;
	isCreateCopy = false;	

    if(Ext.getCmp("_submitProcess").disabled){
    	Ext.getCmp("_submitProcess").setDisabled(false);}
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
 * 提交
 */
function _submitProcessTestProjectManage()
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

