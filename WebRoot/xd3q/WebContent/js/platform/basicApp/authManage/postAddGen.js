/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年12月17日 10点34分51秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象职务(Post)增加页面JS文件
 */

          
          
          
          
/**
 * 保存 
 */
function _save()
{
	//if(isCreateCopy){
	//	document.getElementById("Post.postid").value = "";
	//}
	if(_presave()){
		var param = Form.serialize('mainForm');	
	    new AjaxEngine(contextPath + '/platform/basicApp/authManage/postController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}
	_postsave();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var postid=result.postid;
	document.getElementById("Post.postid").value = postid;
	document.getElementById("Post.postno").value = result.postno;
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
