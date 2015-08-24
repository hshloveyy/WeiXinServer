/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月16日 12点02分38秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象重分配(Reassign)查看页面JS文件
 */

          
/**
 * 保存 
 */
function _saveOrUpdateReassign()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("Reassign.reassignid").value = id;	
}
          

/**
 * 取消
 */
function _cancelReassign()
{
  if(_precancelReassign()){
	new AjaxEngine(contextPath + '/xdss3/reassign/reassignController.spr?action=_cancel&reassignid='+reassignid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelReassignCallBack});
   }
   _postcancelReassign();
}

function _cancelReassignCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          

/**
 * 提交
 */
function _submitProcessReassign(id)
{
	if(_presubmitProcessReassign()){
		var param = Form.serialize('mainForm');	
		param = param + "&"+ Form.serialize('workflowForm')  + "&type=view";
	
		new AjaxEngine(contextPath +'/xdss3/reassign/reassignController.spr?action=_submitProcess', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	}
	_postsubmitProcessReassign();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
