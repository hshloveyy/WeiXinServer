/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年12月17日 10点34分51秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象职务(Post)编辑页面JS文件
 */
 

          

/**
 * 创建按钮调用方法 
 * 新增职务
 */
function _createPost()
{
	if(_precreatePost()){
		//增加页签
		_getMainFrame().maintabs.addPanel('职务新增','',contextPath + '/platform/basicApp/authManage/postController.spr?action=_create');
     }
     _postcreatePost();
}
          

function _copyCreatePost()
{
   if(_precopyCreatePost()){
		_getMainFrame().maintabs.addPanel('复制创建职务','',contextPath + '/platform/basicApp/authManage/postController.spr?action=_copyCreate&postid='+postid);
   }
   _postcopyCreatePost();
}
          

/**
 * 删除职务
 */
function _deletePost()
{
if(_predeletePost()){
	_getMainFrame().Ext.MessageBox.show({
		title:'系统提示',
		msg: '您选择了【职务删除】操作，是否确定继续该操作？',
		buttons: {yes:'确定', no:'取消'},
		icon: Ext.MessageBox.QUESTION,
		fn:function(result){
			if (result == 'yes'){
				var param = '&postid='+postid;
				new AjaxEngine(contextPath + '/platform/basicApp/authManage/postController.spr?action=_delete', 
				{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
			}
		 }
	});
  }
  _postdeletePost();
}

/**
 * 删除成功后的回调动作
 */
function _deleteCallBackHandle()
{
	_getMainFrame().showInfo('操作成功！       ');
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

          
/**
 * 保存 
 */
function _saveOrUpdatePost()
{
if(_presaveOrUpdatePost()){
	var param = Form.serialize('mainForm');	
    //alert(param);
	new AjaxEngine(contextPath + '/platform/basicApp/authManage/postController.spr?action=_saveOrUpdate', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
   }
   _postsaveOrUpdatePost();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	//var id = responseUtil.getCustomField('coustom');
	var postid=result.postid;
	//document.getElementById("Post.postid").value = id;
}
          
/**
 * 取消
 */
function _cancelPost()
{
  if(_precancelPost()){
	new AjaxEngine(contextPath + '/platform/basicApp/authManage/postController.spr?action=_cancel&postid='+postid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelPostCallBack});
   }
   _postcancelPost();
}
function _cancelPostCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
