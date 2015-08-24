/**
  * Author(s):java业务平台代码生成工具
  * Date: 2011年11月03日 17点08分06秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象资金占用调整(FundAdjustment)查看页面JS文件
 */

          

/**
 * 删除资金占用调整
 */
function _deleteFundAdjustment()
{
if(_predeleteFundAdjustment()){
		_getMainFrame().Ext.MessageBox.show({
			title:Txt.cue,
		    msg: Txt.fundAdjustment_Delete_ConfirmOperation,
			buttons: {yes:Txt.ok, no:Txt.cancel},
			icon: Ext.MessageBox.QUESTION,
			fn:function(result){
			   if (result == 'yes'){
					var param = '&fundid='+fundid;
					new AjaxEngine(contextPath + '/xdss3/fundadjustment/fundAdjustmentController.spr?action=_delete', 
					{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
				}
			}
		});
	 }
 	 _postdeleteFundAdjustment();
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
 * 新增资金占用调整
 */
function _createFundAdjustment()
{
	if(_precreateFundAdjustment()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.fundAdjustment_Create,'',contextPath + '/xdss3/fundadjustment/fundAdjustmentController.spr?action=_create');
	}
	_postcreateFundAdjustment();
}
          
/**
 * 保存 
 */
function _saveOrUpdateFundAdjustment()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("FundAdjustment.fundid").value = id;	
}
          

/**
 * 取消
 */
function _cancelFundAdjustment()
{
  if(_precancelFundAdjustment()){
	new AjaxEngine(contextPath + '/xdss3/fundadjustment/fundAdjustmentController.spr?action=_cancel&fundid='+fundid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelFundAdjustmentCallBack});
   }
   _postcancelFundAdjustment();
}

function _cancelFundAdjustmentCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
