/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年04月12日 11点34分36秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象部门预算编制(DeptBudgetting)增加页面JS文件
 */


          
          
          
          
/**
 * 保存 
 */
function _saveOrUpdateDeptBudgetting()
{					 
//if(isCreateCopy){
	//	document.getElementById("DeptBudgetting.deptbudgettingid").value = "";
	//}
	if(_presaveOrUpdateDeptBudgetting()){
		var param = Form.serialize('mainForm');	
	    new AjaxEngine(contextPath + '/XDSS/sample/deptBudgetting/deptBudgettingController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdateDeptBudgetting();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var deptbudgettingid=result.deptbudgettingid;
	document.getElementById("DeptBudgetting.deptbudgettingid").value = deptbudgettingid;
	
	isCreateCopy = false;	
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
function _submitProcessDeptBudgetting()
{
if(_presubmitProcessDeptBudgetting()){
	var param = Form.serialize('mainForm');	
	param = param + "&"+ Form.serialize('workflowForm');
	
	new AjaxEngine(contextPath +'/XDSS/sample/deptBudgetting/deptBudgettingController.spr?action=_submitProcess', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
  }
  _postsubmitProcessDeptBudgetting();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

