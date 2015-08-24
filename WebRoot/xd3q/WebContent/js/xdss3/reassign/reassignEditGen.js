/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月16日 12点02分38秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象重分配(Reassign)编辑页面JS文件
 */
 

          
/**
 * 保存 
 */
function _saveOrUpdateReassign()
{
if(_presaveOrUpdateReassign()){
	var param = Form.serialize('mainForm');	
    //alert(param);
	new AjaxEngine(contextPath + '/xdss3/reassign/reassignController.spr?action=_saveOrUpdate', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
   }
   _postsaveOrUpdateReassign();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	//var id = responseUtil.getCustomField('coustom');
	var reassignid=result.reassignid;
	//document.getElementById("Reassign.reassignid").value = id;
	document.getElementById("Reassign.creator_text").value = result.creator_text;
	document.getElementById("Reassign.creator").value = result.creator;
	document.getElementById("Reassign.createtime").value = result.createtime;
	document.getElementById("Reassign.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("Reassign.lastmodifytime").value = result.lastmodifytime;
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
function _submitProcessReassign()
{
if(_presubmitProcessReassign()){
	var param = Form.serialize('mainForm');	
	param = param + "&"+ Form.serialize('workflowForm');
	
	new AjaxEngine(contextPath +'/xdss3/reassign/reassignController.spr?action=checkReassign', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle2});
	
  }
  _postsubmitProcessReassign();
}
/**
 * 提交后的回调动作
 */
function _submitCallBackHandle2(transport){
	var promptMessagebox = new MessageBoxUtil();
	promptMessagebox.Close();
	var flag = transport.responseText;
	var param = Form.serialize('mainForm');	
	param = param + "&"+ Form.serialize('workflowForm');	
	if(flag=='0'){
		new AjaxEngine(contextPath +'/xdss3/reassign/reassignController.spr?action=_submitProcess', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	}else if(flag=='-1'){
		showInfo("原始单据有问题！");
		return false;	
	}else{
		if(confirm("有多张关联的清账单号，确定后会把关联的清账的单号全部重分配掉，是否继续！")){
			new AjaxEngine(contextPath +'/xdss3/reassign/reassignController.spr?action=_submitProcess', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
		}else{
			return false;
		}				
	}
}
/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
