/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年08月17日 16点23分59秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象未明户收款(UnnamerCollect)查看页面JS文件
 */

          
/**
 * 保存 
 */
function _saveOrUpdateUnnamerCollect()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("UnnamerCollect.unnamercollectid").value = id;	
}
          

/**
 * 取消
 */
function _cancelUnnamerCollect()
{
  if(_precancelUnnamerCollect()){
	new AjaxEngine(contextPath + '/xdss3/unnameCollect/unnamerCollectController.spr?action=_cancel&unnamercollectid='+unnamercollectid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelUnnamerCollectCallBack});
   }
   _postcancelUnnamerCollect();
}

function _cancelUnnamerCollectCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
var voucherFlag = false;     
/**
 * 提交
 */
function _submitProcessUnnamerCollect(flag)
{
	/**
	 * @修改作者：邱杰烜
	 * @修改日期：2010-08-17
	 */
	if(!checkOnSubmit()){
		return;
	}
	
	if(_presubmitProcessUnnamerCollect()){
		voucherFlag = flag;
		var param = Form.serialize('mainForm');	
		param = param + "&"+ Form.serialize('workflowForm')  + "&type=view";
	
		new AjaxEngine(contextPath +'/xdss3/unnameCollect/unnamerCollectController.spr?action=_submitProcess', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	}
	_postsubmitProcessUnnamerCollect();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var msgType = responseUtil.getMessageType();
	if (msgType == 'info')
	{
		if(voucherFlag==true){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.submitSuccess+'是否需要查看凭证？',
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
				    	_getMainFrame().maintabs.addPanel2('凭证查看', contextPath+'/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.get('UnnamerCollect.unnamercollectid').dom.value);
					}else{
						_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
					}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.submitSuccess);
			_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		}
	}
}
          
