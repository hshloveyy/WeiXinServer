/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年06月11日 03点10分50秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象票清预收款(BillClearCollect)查看页面JS文件
 */
function _autoAssignBillClearCollect()
{
	
}

function _clearAssignBillClearCollect()
{
	
}

function _precopyCreateBillClearCollect()
{
	return true ;
}

function _postcopyCreateBillClearCollect()
{

}
function _assignAmount(customertitleids)
{
	
}
          

/**
 * 删除票清预收款
 */
function _predeleteBillClearCollect()
{
	return true ;
}

/**
 * 删除票清预收款
 */
function _postdeleteBillClearCollect()
{

}
          

/**
 * 创建按钮调用方法 
 * 新增票清预收款
 */
function _precreateBillClearCollect()
{
	return true ;
}

/**
 * 创建按钮调用方法 
 * 新增票清预收款
 */
function _postcreateBillClearCollect()
{

}
          
/**
 * 保存 
 */
function _presaveOrUpdateBillClearCollect()
{
	return true ;
}

/**
 * 保存 
 */
function _postsaveOrUpdateBillClearCollect()
{

}

          

/**
 * 取消
 */
function _precancelBillClearCollect()
{
	return true ;
}

/**
 * 取消
 */
function _postcancelBillClearCollect()
{

}
          

/**
 * 提交
 */
function _presubmitProcessBillClearCollect(id)
{
	return true ;
}

/**
 * 提交
 */
function _postsubmitProcessBillClearCollect(id)
{

}

/**
 * 模拟凭证
 */
function _voucherPreviewBillClearCollect()
{

	
	// 方法执行体
	var param = Form.serialize('mainForm');
	param = param + "" + getAllBillClearItemGridData();
	param = param + "" + getAllBillInCollectGridData();
	param = param + "&" + Form.serialize('settleSubjectBccForm');
	param = param + "&" + Form.serialize('fundFlowBccForm');
	
	new AjaxEngine(contextPath + '/xdss3/billclearcollect/billClearCollectController.spr?action=_voucherPreview',
			{
				method		: "post",
				parameters	: param,
				onComplete	: callBackHandle,
				callback	: voucherPreviewCallBackHandle
			});
	
}

/**
 * 凭证预览回调函数。
 * 
 * @param {}
 *            transport
 */
function voucherPreviewCallBackHandle(transport)
{
	if (transport.responseText)
	{
		var responseUtil = new AjaxResponseUtils(transport.responseText);
		var result = responseUtil.getCustomField("coustom");		
		if(result==null || result ==""){
        	_getMainFrame().showInfo("没有凭证预览可生成");        
        }else{
        	_getMainFrame().maintabs.addPanel('票清收款凭证预览', '', contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid=' + result);
		}
	}
}


/**
 * 重分配提交
 * @return
 */
function _submitForReassignBillClearCollect()
{	
	if(_presubmitProcessBillClearCollect()){
		var param = Form.serialize('mainForm');	
	          
	 
		param = param + "&" + Form.serialize('settleSubjectBccForm');		
	          
	         
		param = param + "&" + getAllBillClearItemGridData();
		        	          
	 
		param = param + "&" + Form.serialize('fundFlowBccForm');		
	          
	         
		param = param + "&" + getAllBillInCollectGridData();
		        		param = param + "&"+ Form.serialize('workflowForm');
		
		new AjaxEngine(contextPath +'/xdss3/billclearcollect/billClearCollectController.spr?action=_submitForReassign', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	  }
	  _postsubmitProcessBillClearCollect();
}


Ext.onReady(function(){
  	 Ext.getCmp('_voucherPreview').hide();
			Ext.getCmp('_submitProcess').hide();
			Ext.getCmp('_autoAssign').hide();
			Ext.getCmp('_clearAssign').hide();
			Ext.getCmp('_submitForReassign').hide();
   		
 });
 
 
 function _showVoucherBillClearCollect(){
 	_getMainFrame().maintabs.addPanel2('凭证查看', contextPath+'/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.get('BillClearCollect.billclearid').dom.value);
 
 }