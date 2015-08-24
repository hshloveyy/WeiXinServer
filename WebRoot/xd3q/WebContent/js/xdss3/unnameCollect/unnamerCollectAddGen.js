/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年08月17日 16点23分59秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象未明户收款(UnnamerCollect)增加页面JS文件
 */


          
/**
 * 保存 
 */
function _saveOrUpdateUnnamerCollect()
{					 
	if(isCreateCopy){
		document.getElementById("UnnamerCollect.unnamercollectid").value = "";
	}
	if(_presaveOrUpdateUnnamerCollect()){
		var param = Form.serialize('mainForm');	
	    new AjaxEngine(contextPath + '/xdss3/unnameCollect/unnamerCollectController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdateUnnamerCollect();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var unnamercollectid=result.unnamercollectid;
	document.getElementById("UnnamerCollect.unnamercollectid").value = unnamercollectid;
	
	document.getElementById("UnnamerCollect.unnamercollectno").value = result.unnamercollectno;
	document.getElementById("UnnamerCollect.lastmodifytime").value = result.lastmodifytime;
	document.getElementById("UnnamerCollect.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("UnnamerCollect.createtime").value = result.createtime;
	document.getElementById("UnnamerCollect.creator_text").value = result.creator_text;
	document.getElementById("UnnamerCollect.creator").value = result.creator;
	isCreateCopy = false;	
	
    if(Ext.getCmp("_submitProcess").disabled){
    	Ext.getCmp("_submitProcess").setDisabled(false);}
    if(Ext.getCmp("_cashJournal").disabled){
    	Ext.getCmp("_cashJournal").setDisabled(false);}
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
function _submitProcessUnnamerCollect()
{
	
if(_presubmitProcessUnnamerCollect()){
	var param = Form.serialize('mainForm');	
	param = param + "&"+ Form.serialize('workflowForm');
	
	new AjaxEngine(contextPath +'/xdss3/unnameCollect/unnamerCollectController.spr?action=_submitProcess', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
  }
  _postsubmitProcessUnnamerCollect();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

          
