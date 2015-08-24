/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年03月26日 10点11分24秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象采购订单(OporDoc)查看页面JS文件
 */
          
   
/**
 * grid 上的 创建按钮调用方法 
 * 新增采购订单,采购订单明细
 */
function _createOporDocItem()
{
  if(_precreateOporDocItem()){
     _commonMethodOperation('1',Txt.oporDocItem_Create,OporDocItem_grid,'sample/b1/opor/oporDocItemController.spr?action=_create','_createOporDocItemCallBack',true);
   }
   _postcreateOporDocItem();
}

/**
 * 打开页签，回调
 */
function _createOporDocItemCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_insertOporDocItemRow(json);
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增采购订单,采购订单明细
 */
function _createOporDocItemm()
{
  if(_precreateOporDocItem()){
	_getMainFrame().ExtModalWindowUtil.show(Txt.oporDocItem_Create,
			contextPath + '/sample/b1/opor/oporDocItemController.spr?action=_create','',_createOporDocItemmCallBack,{width:660,height:300});
   }
   _postcreateOporDocItem();
}

/**
 * 弹出窗口，回调
 */
function _createOporDocItemmCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_insertOporDocItemRow(json);
}

function _insertOporDocItemRow(json)
{
	if(json){
	var oporDocItemFields = new OporDocItem_fields({
		oporDocItemId:''
       ,itemCode:''
       ,itemName:''
	   ,whsCode:''
	   ,whsCode_text:''
       ,vatGroup:''
       ,unitPrice:''
       ,quantity:''
       ,lineTotal:''
       ,u_qc:''
       ,oporDocId:''
	});		
	
		OporDocItem_grid.stopEditing();
		OporDocItem_grid.getStore().insert(0, oporDocItemFields);
		OporDocItem_grid.startEditing(0, 0);
		var record = OporDocItem_grid.getStore().getAt(0);
			record.set('oporDocItemId',json["OporDocItem.oporDocItemId"]);
			record.set('itemCode',json["OporDocItem.itemCode"]);
			record.set('itemName',json["OporDocItem.itemName"]);
			record.set('whsCode',json["OporDocItem.whsCode"]);
			record.set('whsCode_text',json["OporDocItem.whsCode_htext"]);
			record.set('vatGroup',json["OporDocItem.vatGroup"]);
			record.set('unitPrice',json["OporDocItem.unitPrice"]);
			record.set('quantity',json["OporDocItem.quantity"]);
			record.set('lineTotal',json["OporDocItem.lineTotal"]);
			record.set('u_qc',json["OporDocItem.u_qc"]);
			record.set('oporDocId',json["OporDocItem.oporDocId"]);
	}
}

    

    
/**
 *采购订单行项目
 *复制创建
 */
function _copyCreateOporDocItem()
{
  if(_precopyCreateOporDocItem()){
		if (OporDocItem_grid.selModel.hasSelection() > 0){
			var records = OporDocItem_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.oporDocItem_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
		    _commonMethodOperation('1',Txt.oporDocItem_CopyCreate,OporDocItem_grid,'sample/b1/opor/oporDocItemController.spr?action=_copyCreate'+pars,'_createOporDocItemCallBack',true);
		
		}else{
			_getMainFrame().showInfo(Txt.oporDocItem_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateOporDocItem();
}


/**
 *采购订单行项目
 *复制创建
 */
function _copyCreateOporDocItemm()
{
  if(_precopyCreateOporDocItem()){
		if (OporDocItem_grid.selModel.hasSelection() > 0){
			var records = OporDocItem_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.oporDocItem_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
			_getMainFrame().ExtModalWindowUtil.show(Txt.oporDocItem_CopyCreate,contextPath + '/sample/b1/opor/oporDocItemController.spr?action=_copyCreate'+pars,'',_createOporDocItemmCallBack,{width:610,height:200});
		}else{
			_getMainFrame().showInfo(Txt.oporDocItem_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateOporDocItem();
}
    

    
  


function _editOporDocItempCallBack()
{
}


function _editOporDocItemmCallBack()
{
}

/**
  * 采购订单行项目编辑操作
  */
function _editOporDocItem(id,url)
{
	if(_preeditOporDocItem()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到编辑页面
		var records = OporDocItem_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.oporDocItem_Edit,	url,'',_editOporDocItemCallBack,{width:660,height:300});
      
      }
      _posteditOporDocItem();
}
function _editOporDocItemCallBack()
{
}

    
/**
  * 采购订单行项目查看操作
  */
function _viewOporDocItem(id,url)
{
	if(_previewOporDocItem()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到查看页面
		var records = OporDocItem_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.oporDocItem_View,	url,'','',{width:660,height:300});
	}
	_postviewOporDocItem();
}
/**
  * 采购订单行项目查看，打开到页签 ,回调函数
  */
function _viewOporDocItempCallBack()
{
}

/**
  * 采购订单行项目查看，弹出窗口 ,回调函数
  */
function _viewOporDocItemmCallBack()
{
}

    
    
    
    
    
    
    
    
    
    
    
    
  

          

/**
 * 创建按钮调用方法 
 * 新增采购订单
 */
function _createOporDoc()
{
	if(_precreateOporDoc()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.oporDoc_Create,'',contextPath + '/sample/b1/opor/oporDocController.spr?action=_create');
	}
	_postcreateOporDoc();
}
          
function _copyCreateOporDoc()
{
	if(_precopyCreateOporDoc()){
		_getMainFrame().maintabs.addPanel(Txt.oporDoc_CopyCreate,'',contextPath + '/sample/b1/opor/oporDocController.spr?action=_copyCreate&oporDocId='+oporDocId);
	}
	_postcopyCreateOporDoc();
}

          

/**
 * 删除采购订单
 */
function _deleteOporDoc()
{
if(_predeleteOporDoc()){
		_getMainFrame().Ext.MessageBox.show({
			title:Txt.cue,
		    msg: Txt.oporDoc_Delete_ConfirmOperation,
			buttons: {yes:Txt.ok, no:Txt.cancel},
			icon: Ext.MessageBox.QUESTION,
			fn:function(result){
			   if (result == 'yes'){
					var param = '&oporDocId='+oporDocId;
					new AjaxEngine(contextPath + '/sample/b1/opor/oporDocController.spr?action=_delete', 
					{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
				}
			}
		});
	 }
 	 _postdeleteOporDoc();
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
 * 保存 
 */
function _saveOrUpdateOporDoc()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("OporDoc.oporDocId").value = id;	
}
          

/**
 * 取消
 */
function _cancelOporDoc()
{
  if(_precancelOporDoc()){
	new AjaxEngine(contextPath + '/sample/b1/opor/oporDocController.spr?action=_cancel&oporDocId='+oporDocId, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelOporDocCallBack});
   }
   _postcancelOporDoc();
}

function _cancelOporDocCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          

/**
 * 提交
 */
function _submitProcessOporDoc(id)
{
	if(_presubmitProcessOporDoc()){
		var param = Form.serialize('mainForm');	
	            
		        	         
		param = param + "&" + getAllOporDocItemGridData();
		        			param = param + "&"+ Form.serialize('workflowForm');
	
		new AjaxEngine(contextPath +'/sample/b1/opor/oporDocController.spr?action=_submitProcess', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
	}
	_postsubmitProcessOporDoc();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
