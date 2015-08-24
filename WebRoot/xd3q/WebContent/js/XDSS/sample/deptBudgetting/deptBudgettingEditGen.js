/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年04月12日 11点34分36秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象部门预算编制(DeptBudgetting)编辑页面JS文件
 */
 

          

function _copyCreateDeptBudgetting()
{
   if(_precopyCreateDeptBudgetting()){
		_getMainFrame().maintabs.addPanel(Txt.deptBudgetting_CopyCreate,'',contextPath + '/XDSS/sample/deptBudgetting/deptBudgettingController.spr?action=_copyCreate&deptbudgettingid='+deptbudgettingid);
   }
   _postcopyCreateDeptBudgetting();
}
          

/**
 * 删除部门预算编制
 */
function _deleteDeptBudgetting()
{
if(_predeleteDeptBudgetting()){
	_getMainFrame().Ext.MessageBox.show({
		title:Txt.cue,
		msg: Txt.deptBudgetting_Delete_ConfirmOperation,
		buttons: {yes:Txt.ok, no:Txt.cancel},
		icon: Ext.MessageBox.QUESTION,
		fn:function(result){
			if (result == 'yes'){
				var param = '&deptbudgettingid='+deptbudgettingid;
				new AjaxEngine(contextPath + '/XDSS/sample/deptBudgetting/deptBudgettingController.spr?action=_delete', 
				{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
			}
		 }
	});
  }
  _postdeleteDeptBudgetting();
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
 * 新增部门预算编制
 */
function _createDeptBudgetting()
{
	if(_precreateDeptBudgetting()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.deptBudgetting_Create,'',contextPath + '/XDSS/sample/deptBudgetting/deptBudgettingController.spr?action=_create');
     }
     _postcreateDeptBudgetting();
}
          
/**
 * 保存 
 */
function _saveOrUpdateDeptBudgetting()
{
if(_presaveOrUpdateDeptBudgetting()){
	var param = Form.serialize('mainForm');	
    //alert(param);
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
	//var id = responseUtil.getCustomField('coustom');
	var deptbudgettingid=result.deptbudgettingid;
	//document.getElementById("DeptBudgetting.deptbudgettingid").value = id;
}
          
/**
 * 取消
 */
function _cancelDeptBudgetting()
{
  if(_precancelDeptBudgetting()){
	new AjaxEngine(contextPath + '/XDSS/sample/deptBudgetting/deptBudgettingController.spr?action=_cancel&deptbudgettingid='+deptbudgettingid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelDeptBudgettingCallBack});
   }
   _postcancelDeptBudgetting();
}
function _cancelDeptBudgettingCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
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
