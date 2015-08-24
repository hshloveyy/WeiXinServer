/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年01月26日 09点27分57秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象地址信息(Address)查看页面JS文件
 */

          
function _copyCreateAddress()
{
	if(_precopyCreateAddress()){
		_getMainFrame().maintabs.addPanel(Txt.address_CopyCreate,'',contextPath + '/platform/basicApp/authManage/addressController.spr?action=_copyCreate&addressid='+addressid);
	}
	_postcopyCreateAddress();
}

          

/**
 * 删除地址信息
 */
function _deleteAddress()
{
if(_predeleteAddress()){
		_getMainFrame().Ext.MessageBox.show({
			title:Txt.cue,
		    msg: Txt.address_Delete_ConfirmOperation,
			buttons: {yes:Txt.ok, no:Txt.cancel},
			icon: Ext.MessageBox.QUESTION,
			fn:function(result){
			   if (result == 'yes'){
					var param = '&addressid='+addressid;
					new AjaxEngine(contextPath + '/platform/basicApp/authManage/addressController.spr?action=_delete', 
					{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
				}
			}
		});
	 }
 	 _postdeleteAddress();
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
 * 新增地址信息
 */
function _createAddress()
{
	if(_precreateAddress()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.address_Create,'',contextPath + '/platform/basicApp/authManage/addressController.spr?action=_create');
	}
	_postcreateAddress();
}
          
/**
 * 保存 
 */
function _saveOrUpdateAddress()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("Address.addressid").value = id;	
}
          

/**
 * 取消
 */
function _cancelAddress()
{
  if(_precancelAddress()){
	new AjaxEngine(contextPath + '/platform/basicApp/authManage/addressController.spr?action=_cancel&addressid='+addressid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelAddressCallBack});
   }
   _postcancelAddress();
}

function _cancelAddressCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
