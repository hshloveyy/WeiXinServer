﻿/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年04月19日 11点09分35秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象采购订单(PurchaseOrder)增加页面JS文件
 */

             
/**
 * grid 上的 创建按钮调用方法 
 * 新增采购订单,采购行项目
 */
function _createOrderItems()
{
  if(_precreateOrderItems()){
     _commonMethodOperation('1',Txt.orderItems_Create,OrderItems_grid,'sample/orderManage/purchaseOrder/orderItemsController.spr?action=_create','_createOrderItemsCallBack',true);
   }
   _postcreateOrderItems();
}

/**
 * 打开页签，回调
 */
function _createOrderItemsCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_insertOrderItemsRow(json);
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增采购订单,采购行项目
 */
function _createOrderItemsm()
{
  if(_precreateOrderItems()){
	_getMainFrame().ExtModalWindowUtil.show(Txt.orderItems_Create,
			contextPath + '/sample/orderManage/purchaseOrder/orderItemsController.spr?action=_create','',_createOrderItemsmCallBack,{width:660,height:300});
   }
   _postcreateOrderItems();
}

/**
 * 弹出窗口，回调
 */
function _createOrderItemsmCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_insertOrderItemsRow(json);
}

function _insertOrderItemsRow(json)
{
	if(json){
	var orderItemsFields = new OrderItems_fields({
		orderItemsId:''
		,purchaseOrderId:''
		,itemNo:''
		,materielNo:''
		,materielNo_text:''
		,quantity:''
		,deliveryDate:''
		,measureUnit:''
		,address:''
	});		

		OrderItems_grid.stopEditing();
		OrderItems_grid.getStore().insert(0, orderItemsFields);
		OrderItems_grid.startEditing(0, 0);
		var record = OrderItems_grid.getStore().getAt(0);
		record.set('orderItemsId',json["OrderItems.orderItemsId"]);
		record.set('purchaseOrderId',json["OrderItems.purchaseOrderId"]);
		record.set('itemNo',json["OrderItems.itemNo"]);
		record.set('materielNo',json["OrderItems.materielNo"]);
		record.set('materielNo_text',json["OrderItems.materielNo_htext"]);
		record.set('quantity',json["OrderItems.quantity"]);
		record.set('deliveryDate',json["OrderItems.deliveryDate"]);
		record.set('measureUnit',json["OrderItems.measureUnit"]);
		record.set('address',json["OrderItems.address"]);
	}
}	
    
    
/**
 *采购订单行项目
 *复制创建
 */
function _copyCreateOrderItems()
{
  if(_precopyCreateOrderItems()){
		if (OrderItems_grid.selModel.hasSelection() > 0){
			var records = OrderItems_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.orderItems_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
		    _commonMethodOperation('1',Txt.orderItems_CopyCreate,OrderItems_grid,'sample/orderManage/purchaseOrder/orderItemsController.spr?action=_copyCreate'+pars,'_createOrderItemsCallBack',true);
		
		}else{
			_getMainFrame().showInfo(Txt.orderItems_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateOrderItems();
}


/**
 *采购订单行项目
 *复制创建
 */
function _copyCreateOrderItemsm()
{
  if(_precopyCreateOrderItems()){
		if (OrderItems_grid.selModel.hasSelection() > 0){
			var records = OrderItems_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.orderItems_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
			_getMainFrame().ExtModalWindowUtil.show(Txt.orderItems_CopyCreate,contextPath + '/sample/orderManage/purchaseOrder/orderItemsController.spr?action=_copyCreate'+pars,'',_createOrderItemsmCallBack,{width:610,height:200});
		}else{
			_getMainFrame().showInfo(Txt.orderItems_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateOrderItems();
}

    
    
  
    
/**
  * 采购订单行项目查看操作
  */
function _viewOrderItems(id,url)
{
	if(_previewOrderItems()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到查看页面
		var records = OrderItems_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.orderItems_View,	url,'','',{width:660,height:300});
	}
	_postviewOrderItems();
}
/**
  * 采购订单行项目查看，打开到页签 ,回调函数
  */
function _viewOrderItemspCallBack()
{
}

/**
  * 采购订单行项目查看，弹出窗口 ,回调函数
  */
function _viewOrderItemsmCallBack()
{
}


function _editOrderItemspCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_modifyOrderItemsRow(json);
}


function _editOrderItemsmCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_modifyOrderItemsRow(json);
}

/**
  * 采购订单行项目编辑操作
  */
function _editOrderItems(id,url)
{
	if(_preeditOrderItems()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到编辑页面
		var records = OrderItems_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.orderItems_Edit,	url,'',_editOrderItemsCallBack,{width:660,height:300});
      
      }
      _posteditOrderItems();
}
function _editOrderItemsCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_modifyOrderItemsRow(json);
}

/**
 * 行项目编辑后的grid更新
 */
function _modifyOrderItemsRow(json)
{
	if(json){
		var records = OrderItems_grid.selModel.getSelections();
		var record = records[0];
		record.set('itemNo',json["OrderItems.itemNo"]);
		record.set('materielNo',json["OrderItems.materielNo"]);
		record.set('quantity',json["OrderItems.quantity"]);
		record.set('deliveryDate',json["OrderItems.deliveryDate"]);
		record.set('measureUnit',json["OrderItems.measureUnit"]);
		record.set('address',json["OrderItems.address"]);
	}
}
    
              
    
  

          

          
/**
 * 保存 
 */
function _saveOrUpdatePurchaseOrder()
{					 
//if(isCreateCopy){
	//	document.getElementById("PurchaseOrder.purchaseOrderId").value = "";
	//}
	if(_presaveOrUpdatePurchaseOrder()){
		var param = Form.serialize('mainForm');	
		param = param + "&"+ Form.serialize('purchaseOrderForm');
		           
			        	         
		if(isCreateCopy)
		{
			param = param + ""+ getAllOrderItemsGridData();
		}
		else
		{
			param = param + ""+ getOrderItemsGridData(); 
		}
			        	           
			        	         
		if(isCreateCopy)
		{
			param = param + ""+ getAllAttachementGridData();
		}
		else
		{
			param = param + ""+ getAttachementGridData(); 
		}
			        		    new AjaxEngine(contextPath + '/sample/orderManage/purchaseOrder/purchaseOrderController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdatePurchaseOrder();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var purchaseOrderId=result.purchaseOrderId;
	document.getElementById("PurchaseOrder.purchaseOrderId").value = purchaseOrderId;
	
	document.getElementById("PurchaseOrder.purchaseOrderNo").value = result.purchaseOrderNo;
	document.getElementById("PurchaseOrder.creator_text").value = result.creator_text;
	document.getElementById("PurchaseOrder.creator").value = result.creator;
	document.getElementById("PurchaseOrder.createTime").value = result.createTime;
	document.getElementById("PurchaseOrder.lastModifyor_text").value = result.lastModifyor_text;
	document.getElementById("PurchaseOrder.lastModifyor").value = result.lastModifyor;
	document.getElementById("PurchaseOrder.lastModifyTime").value = result.lastModifyTime;
	isCreateCopy = false;	
	reload_OrderItems_grid("defaultCondition=YORDERITEMS.PURCHASEORDERID='"+ purchaseOrderId +"'");
	reload_Attachement_grid("defaultCondition=YATTACHEMENT.BUSINESSID='"+ purchaseOrderId +"'");

    if(Ext.getCmp("_submitProcess").disabled){
    	Ext.getCmp("_submitProcess").setDisabled(false);}
}

          
/**
 * 取消
 */
function _cancel()
{
	if(_precancel()){
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	 }
	 _postcancel();
}
          

/**
 * 提交
 */
function _submitProcessPurchaseOrder()
{
if(_presubmitProcessPurchaseOrder()){
	var param = Form.serialize('mainForm');	
	param = param + "&"+ Form.serialize('purchaseOrderForm');
          
	        	         
	param = param + "&" + getAllOrderItemsGridData();
	        	          
	        	         
	param = param + "&" + getAllAttachementGridData();
	        		param = param + "&"+ Form.serialize('workflowForm');
	
	new AjaxEngine(contextPath +'/sample/orderManage/purchaseOrder/purchaseOrderController.spr?action=_submitProcess', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
  }
  _postsubmitProcessPurchaseOrder();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

          
          
          
