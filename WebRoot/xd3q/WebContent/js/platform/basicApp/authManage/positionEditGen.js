/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年12月17日 10点34分51秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象职位(Position)编辑页面JS文件
 */
 

          

/**
 * 创建按钮调用方法 
 * 新增职位
 */
function _createPosition()
{
	if(_precreatePosition()){
		//增加页签
		_getMainFrame().maintabs.addPanel('职位新增','',contextPath + '/platform/basicApp/authManage/positionController.spr?action=_create');
     }
     _postcreatePosition();
}
          

function _copyCreatePosition()
{
   if(_precopyCreatePosition()){
		_getMainFrame().maintabs.addPanel('复制创建职位','',contextPath + '/platform/basicApp/authManage/positionController.spr?action=_copyCreate&positionid='+positionid);
   }
   _postcopyCreatePosition();
}
          

/**
 * 删除职位
 */
function _deletePosition()
{
if(_predeletePosition()){
	_getMainFrame().Ext.MessageBox.show({
		title:'系统提示',
		msg: '您选择了【职位删除】操作，是否确定继续该操作？',
		buttons: {yes:'确定', no:'取消'},
		icon: Ext.MessageBox.QUESTION,
		fn:function(result){
			if (result == 'yes'){
				var param = '&positionid='+positionid;
				new AjaxEngine(contextPath + '/platform/basicApp/authManage/positionController.spr?action=_delete', 
				{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
			}
		 }
	});
  }
  _postdeletePosition();
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
function _saveOrUpdatePosition()
{
if(_presaveOrUpdatePosition()){
	var param = Form.serialize('mainForm');	
    //alert(param);
	new AjaxEngine(contextPath + '/platform/basicApp/authManage/positionController.spr?action=_saveOrUpdate', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
   }
   _postsaveOrUpdatePosition();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	//var id = responseUtil.getCustomField('coustom');
	var positionid=result.positionid;
	//document.getElementById("Position.positionid").value = id;
}
          
/**
 * 取消
 */
function _cancelPosition()
{
  if(_precancelPosition()){
	new AjaxEngine(contextPath + '/platform/basicApp/authManage/positionController.spr?action=_cancel&positionid='+positionid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelPositionCallBack});
   }
   _postcancelPosition();
}
function _cancelPositionCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
