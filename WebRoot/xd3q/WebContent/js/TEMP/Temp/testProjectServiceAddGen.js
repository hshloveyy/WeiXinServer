/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年04月21日 15点06分43秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象项目服务（测试）(TestProjectService)增加页面JS文件
 */


          

/**
 * 提交
 */
function _submitProcessTestProjectService()
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
 * 保存 
 */
function _saveOrUpdateTestProjectService()
{					 
//if(isCreateCopy){
	//	document.getElementById("TestProjectService.tsid").value = "";
	//}
	if(_presaveOrUpdateTestProjectService()){
		var param = Form.serialize('mainForm');	
	    new AjaxEngine(contextPath + '/TEMP/Temp/testProjectServiceController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdateTestProjectService();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var tsid=result.tsid;
	document.getElementById("TestProjectService.tsid").value = tsid;
	
	document.getElementById("TestProjectService.creator_text").value = result.creator_text;
	document.getElementById("TestProjectService.creator").value = result.creator;
	document.getElementById("TestProjectService.createtime").value = result.createtime;
	document.getElementById("TestProjectService.lastmodifyer_text").value = result.lastmodifyer_text;
	document.getElementById("TestProjectService.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("TestProjectService.lastmodifytime").value = result.lastmodifytime;
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
