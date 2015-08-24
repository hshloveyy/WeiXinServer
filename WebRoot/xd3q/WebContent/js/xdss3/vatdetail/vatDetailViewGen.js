/**
  * Author(s):java业务平台代码生成工具
  * Date: 2011年12月12日 16点14分48秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象期初已到税票未进仓(税额)(VatDetail)查看页面JS文件
 */

          
function _copyCreateVatDetail()
{
	if(_precopyCreateVatDetail()){
		_getMainFrame().maintabs.addPanel(Txt.vatDetail_CopyCreate,'',contextPath + '/xdss3/vatdetail/vatDetailController.spr?action=_copyCreate&tid='+tid);
	}
	_postcopyCreateVatDetail();
}

          

/**
 * 删除期初已到税票未进仓(税额)
 */
function _deleteVatDetail()
{
if(_predeleteVatDetail()){
		_getMainFrame().Ext.MessageBox.show({
			title:Txt.cue,
		    msg: Txt.vatDetail_Delete_ConfirmOperation,
			buttons: {yes:Txt.ok, no:Txt.cancel},
			icon: Ext.MessageBox.QUESTION,
			fn:function(result){
			   if (result == 'yes'){
					var param = '&tid='+tid;
					new AjaxEngine(contextPath + '/xdss3/vatdetail/vatDetailController.spr?action=_delete', 
					{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
				}
			}
		});
	 }
 	 _postdeleteVatDetail();
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
 * 新增期初已到税票未进仓(税额)
 */
function _createVatDetail()
{
	if(_precreateVatDetail()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.vatDetail_Create,'',contextPath + '/xdss3/vatdetail/vatDetailController.spr?action=_create');
	}
	_postcreateVatDetail();
}
          
/**
 * 保存 
 */
function _saveOrUpdateVatDetail()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("VatDetail.tid").value = id;	
}
          

/**
 * 取消
 */
function _cancelVatDetail()
{
  if(_precancelVatDetail()){
	new AjaxEngine(contextPath + '/xdss3/vatdetail/vatDetailController.spr?action=_cancel&tid='+tid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelVatDetailCallBack});
   }
   _postcancelVatDetail();
}

function _cancelVatDetailCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
