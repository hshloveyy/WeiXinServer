/**
  * Author(s):java业务平台代码生成工具
  * Date: 2009年12月14日 11点16分15秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象固定资产(Fixedasset)查看页面JS文件
 */

          
function _copyCreateFixedasset()
{
	if(_precopyCreateFixedasset()){
		_getMainFrame().maintabs.addPanel('复制创建固定资产','',contextPath + '/sample/fixedasset/fixedassetController.spr?action=_copyCreate&anln1='+anln1);
	}
	_postcopyCreateFixedasset();
}

          

/**
 * 删除固定资产
 */
function _deleteFixedasset()
{
if(_predeleteFixedasset()){
		_getMainFrame().Ext.MessageBox.show({
			title:'系统提示',
			msg: '您选择了【固定资产删除】操作，是否确定继续该操作？',
			buttons: {yes:'确定', no:'取消'},
			icon: Ext.MessageBox.QUESTION,
			fn:function(result){
			   if (result == 'yes'){
					var param = '&anln1='+anln1;
					new AjaxEngine(contextPath + '/sample/fixedasset/fixedassetController.spr?action=_delete', 
					{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
				}
			}
		});
	 }
 	 _postdeleteFixedasset();
}

/**
 * 删除成功后的回调动作
 */
function _deleteCallBackHandle()
{
	_getMainFrame().showInfo('操作成功！                   ');
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

          

/**
 * 创建按钮调用方法 
 * 新增固定资产
 */
function _createFixedasset()
{
	if(_precreateFixedasset()){
		//增加页签
		_getMainFrame().maintabs.addPanel('固定资产新增','',contextPath + '/sample/fixedasset/fixedassetController.spr?action=_create');
	}
	_postcreateFixedasset();
}
          
/**
 * 保存 
 */
function _saveOrUpdateFixedasset()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("Fixedasset.anln1").value = id;	
}
          

/**
 * 取消
 */
function _cancelFixedasset()
{
  if(_precancelFixedasset()){
	new AjaxEngine(contextPath + '/sample/fixedasset/fixedassetController.spr?action=_cancel&anln1='+anln1, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelFixedassetCallBack});
   }
   _postcancelFixedasset();
}

function _cancelFixedassetCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          

/**
 * 提交
 */
function _submitProcessFixedasset(id)
{
	if(_presubmitProcessFixedasset()){
		var param = Form.serialize('mainForm');	
		param = param + "&"+ Form.serialize('workflowForm');
	
		new AjaxEngine(contextPath +'/sample/fixedasset/fixedassetController.spr?action=_submitProcess', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	}
	_postsubmitProcessFixedasset();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo('提交成功！             ');
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
