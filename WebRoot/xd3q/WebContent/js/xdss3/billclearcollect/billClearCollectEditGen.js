/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年09月16日 09点35分04秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象票清收款(BillClearCollect)编辑页面JS文件
 */
function _deletesBillInCollect()
{
	if(_predeletesBillInCollect()){
		if (BillInCollect_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.billInCollect_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = BillInCollect_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						BillInCollect_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.billInCollect_Deletes_SelectToOperation);
		}
	}
	_postdeletesBillInCollect();
}

function _deletesBillClearItem()
{
	if(_predeletesBillClearItem()){
		if (BillClearItem_grid.selModel.hasSelection() > 0){
			_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
			    msg: Txt.billClearItem_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
					var records = BillClearItem_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						BillClearItem_grid.getStore().remove(records[i]);
					}
				}
				}
			});
		}else{
			_getMainFrame().showInfo(Txt.billClearItem_Deletes_SelectToOperation);
		}
	}
	_postdeletesBillClearItem();
}

function _copyCreateBillClearCollect()
{
   if(_precopyCreateBillClearCollect()){
		_getMainFrame().maintabs.addPanel(Txt.billClearCollect_CopyCreate,'',contextPath + '/xdss3/billclearcollect/billClearCollectController.spr?action=_copyCreate&billclearid='+billclearid);
   }
   _postcopyCreateBillClearCollect();
}
          

/**
 * 删除票清收款
 */
function _deleteBillClearCollect()
{
if(_predeleteBillClearCollect()){
	_getMainFrame().Ext.MessageBox.show({
		title:Txt.cue,
		msg: Txt.billClearCollect_Delete_ConfirmOperation,
		buttons: {yes:Txt.ok, no:Txt.cancel},
		icon: Ext.MessageBox.QUESTION,
		fn:function(result){
			if (result == 'yes'){
				var param = '&billclearid='+billclearid;
				new AjaxEngine(contextPath + '/xdss3/billclearcollect/billClearCollectController.spr?action=_delete', 
				{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
			}
		 }
	});
  }
  _postdeleteBillClearCollect();
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
 * 新增票清收款
 */
function _createBillClearCollect()
{
	if(_precreateBillClearCollect()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.billClearCollect_Create,'',contextPath + '/xdss3/billclearcollect/billClearCollectController.spr?action=_create');
     }
     _postcreateBillClearCollect();
}
          
/**
 * 保存 
 */
function _saveOrUpdateBillClearCollect()
{
if(_presaveOrUpdateBillClearCollect()){
	var param = Form.serialize('mainForm');	
	           
 
		        		param = param +  "&"+ Form.serialize('settleSubjectBccForm');		
           
         
		        		param = param + ""+ getBillClearItemGridData();
		        	           
 
		        		param = param +  "&"+ Form.serialize('fundFlowBccForm');		
           
         
		        		param = param + ""+ getBillInCollectGridData();
		        	    //alert(param);
	new AjaxEngine(contextPath + '/xdss3/billclearcollect/billClearCollectController.spr?action=_saveOrUpdate', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
   }
   _postsaveOrUpdateBillClearCollect();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	//var id = responseUtil.getCustomField('coustom');
	var billclearid=result.billclearid;
	//document.getElementById("BillClearCollect.billclearid").value = id;
	document.getElementById("BillClearCollect.creator_text").value = result.creator_text;
	document.getElementById("BillClearCollect.creator").value = result.creator;
	document.getElementById("BillClearCollect.createtime").value = result.createtime;
	document.getElementById("BillClearCollect.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("BillClearCollect.lastmodifytime").value = result.lastmodifytime;
	reload_BillClearItem_grid("defaultCondition=YBILLCLEARITEM.BILLCLEARID='"+ billclearid +"'");
	reload_BillInCollect_grid("defaultCondition=YBILLINCOLLECT.BILLCLEARID='"+ billclearid +"'");
}
          
/**
 * 取消
 */
function _cancelBillClearCollect()
{
  if(_precancelBillClearCollect()){
	new AjaxEngine(contextPath + '/xdss3/billclearcollect/billClearCollectController.spr?action=_cancel&billclearid='+billclearid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelBillClearCollectCallBack});
   }
   _postcancelBillClearCollect();
}
function _cancelBillClearCollectCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          

/**
 * 提交
 */
function _submitProcessBillClearCollect()
{
if(_presubmitProcessBillClearCollect()){
	var param = Form.serialize('mainForm');	
          
 
	param = param + "&" + Form.serialize('settleSubjectBccForm');		
          
    param = param + ""+ getBillClearItemGridData();         	          
 	param = param + ""+ getBillInCollectGridData();       
//	param = param + "&" + getAllBillClearItemGridData();
	        	          
 
	param = param + "&" + Form.serialize('fundFlowBccForm');		
          
         
//	param = param + "&" + getAllBillInCollectGridData();
	        		param = param + "&"+ Form.serialize('workflowForm');
	
	new AjaxEngine(contextPath +'/xdss3/billclearcollect/billClearCollectController.spr?action=_submitProcess', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
  }
  _postsubmitProcessBillClearCollect();
}

/**
 * 提交后的回调动作
 */
 /**
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

**/
