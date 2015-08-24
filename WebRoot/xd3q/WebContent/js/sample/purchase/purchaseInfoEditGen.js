/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年01月09日 11点03分53秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象采购订单(SAP)(PurchaseInfo)编辑页面JS文件
 */
 
             
/**
 * grid 上的 创建按钮调用方法 
 * 新增采购订单(SAP),采购订单行项目信息(SAP)
 */
function _createPurchaseRows()
{
	if(_precreatePurchaseRows()){
		_getMainFrame().ExtModalWindowUtil.show(Txt.purchaseRows_Create,
				contextPath + '/sample/purchase/purchaseRowsController.spr?action=_create','',_insertPurchaseRowsRow,{width:660,height:300});
	}
	_postcreatePurchaseRows();
}

function _insertPurchaseRowsRow()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	if(json){
	var purchaseRowsFields = new PurchaseRows_fields({
		purchaserowsId:''
		,pstyp:''
		,matnr:''
		,txzo1:''
		,meins:''
		,menge:''
		,netpr:''
		,bprme:''
		,peinh:''
		,werks:''
		,werksName:''
		,eindt:''
		,webre:''
		,mwskz:''
		,mwskzName:''
		,factorylocal:''
		,totalValue:''
		,price:''
		,creator:''
		,createTime:''
		,lastmodifyer:''
		,lastmodifyTime:''
		,purchaseinfoId:''
		,sapRowNo:''
	});		
	
		PurchaseRows_grid.stopEditing();
		PurchaseRows_grid.getStore().insert(0, purchaseRowsFields);
		PurchaseRows_grid.startEditing(0, 0);
		var record = PurchaseRows_grid.getStore().getAt(0);
			record.set('purchaserowsId',json["PurchaseRows.purchaserowsId"]);
			record.set('pstyp',json["PurchaseRows.pstyp"]);
			record.set('matnr',json["PurchaseRows.matnr"]);
			record.set('txzo1',json["PurchaseRows.txzo1"]);
			record.set('meins',json["PurchaseRows.meins"]);
			record.set('menge',json["PurchaseRows.menge"]);
			record.set('netpr',json["PurchaseRows.netpr"]);
			record.set('bprme',json["PurchaseRows.bprme"]);
			record.set('peinh',json["PurchaseRows.peinh"]);
			record.set('werks',json["PurchaseRows.werks"]);
			record.set('werksName',json["PurchaseRows.werksName"]);
			record.set('eindt',json["PurchaseRows.eindt"]);
			record.set('webre',json["PurchaseRows.webre"]);
			record.set('mwskz',json["PurchaseRows.mwskz"]);
			record.set('mwskzName',json["PurchaseRows.mwskzName"]);
			record.set('factorylocal',json["PurchaseRows.factorylocal"]);
			record.set('totalValue',json["PurchaseRows.totalValue"]);
			record.set('price',json["PurchaseRows.price"]);
			record.set('creator',json["PurchaseRows.creator"]);
			record.set('createTime',json["PurchaseRows.createTime"]);
			record.set('lastmodifyer',json["PurchaseRows.lastmodifyer"]);
			record.set('lastmodifyTime',json["PurchaseRows.lastmodifyTime"]);
			record.set('purchaseinfoId',json["PurchaseRows.purchaseinfoId"]);
			record.set('sapRowNo',json["PurchaseRows.sapRowNo"]);
	}
}
	 
    

    
/**
*采购订单(SAP)行项目
*复制创建
*/
function _copyCreatePurchaseRows()
{
if(_precopyCreatePurchaseRows()){
	if (PurchaseRows_grid.selModel.hasSelection() > 0){
		var records = PurchaseRows_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo(Txt.purchaseRows_CopyCreate_AllowOnlyOneItemForOperation);
			return;
		}   
		var recordData = commonUrlEncode(records[0].data);
		var pars ="&"+ recordData;
		_getMainFrame().ExtModalWindowUtil.show(Txt.purchaseRows_CopyCreate,contextPath + '/sample/purchase/purchaseRowsController.spr?action=_copyCreate'+pars,'',_insertPurchaseRowsRow,{width:610,height:200});
	}else{
		_getMainFrame().showInfo(Txt.purchaseRows_CopyCreate_SelectToOperation);
	}	
}
_postcopyCreatePurchaseRows();
}
    

    
  


/**
  * 采购订单(SAP)行项目查看操作
  */
function _viewPurchaseRows(id,url)
{
	if(_previewPurchaseRows()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到查看页面
		var records = PurchaseRows_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.purchaseRows_View,	url,'','',{width:660,height:300});
   }
   _postviewPurchaseRows();
}
    
/**
  * 采购订单(SAP)行项目编辑操作
  */
function _editPurchaseRows(id,url)
{
	if(_preeditPurchaseRows()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到编辑页面
		var records = PurchaseRows_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.purchaseRows_Edit,	url,'',_modifyPurchaseRowsRow,{width:660,height:300});
      }
     _posteditPurchaseRows();
}

/**
 * 行项目编辑后的grid更新
 */
function _modifyPurchaseRowsRow()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	if(json){
		var records = PurchaseRows_grid.selModel.getSelections();
		var record = records[0];
		record.set('pstyp',json["PurchaseRows.pstyp"]);
		record.set('matnr',json["PurchaseRows.matnr"]);
		record.set('txzo1',json["PurchaseRows.txzo1"]);
		record.set('meins',json["PurchaseRows.meins"]);
		record.set('menge',json["PurchaseRows.menge"]);
		record.set('netpr',json["PurchaseRows.netpr"]);
		record.set('bprme',json["PurchaseRows.bprme"]);
		record.set('peinh',json["PurchaseRows.peinh"]);
		record.set('werks',json["PurchaseRows.werks"]);
		record.set('werksName',json["PurchaseRows.werksName"]);
		record.set('eindt',json["PurchaseRows.eindt"]);
		record.set('webre',json["PurchaseRows.webre"]);
		record.set('mwskz',json["PurchaseRows.mwskz"]);
		record.set('mwskzName',json["PurchaseRows.mwskzName"]);
		record.set('factorylocal',json["PurchaseRows.factorylocal"]);
		record.set('totalValue',json["PurchaseRows.totalValue"]);
		record.set('price',json["PurchaseRows.price"]);
		record.set('creator',json["PurchaseRows.creator"]);
		record.set('createTime',json["PurchaseRows.createTime"]);
		record.set('lastmodifyer',json["PurchaseRows.lastmodifyer"]);
		record.set('lastmodifyTime',json["PurchaseRows.lastmodifyTime"]);
		record.set('purchaseinfoId',json["PurchaseRows.purchaseinfoId"]);
		record.set('sapRowNo',json["PurchaseRows.sapRowNo"]);
	}
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
  

          

function _copyCreatePurchaseInfo()
{
   if(_precopyCreatePurchaseInfo()){
		_getMainFrame().maintabs.addPanel(Txt.purchaseInfo_CopyCreate,'',contextPath + '/sample/purchase/purchaseInfoController.spr?action=_copyCreate&purchaseinfoId='+purchaseinfoId);
   }
   _postcopyCreatePurchaseInfo();
}
          

/**
 * 删除采购订单(SAP)
 */
function _deletePurchaseInfo()
{
if(_predeletePurchaseInfo()){
	_getMainFrame().Ext.MessageBox.show({
		title:Txt.cue,
		msg: Txt.purchaseInfo_Delete_ConfirmOperation,
		buttons: {yes:Txt.ok, no:Txt.cancel},
		icon: Ext.MessageBox.QUESTION,
		fn:function(result){
			if (result == 'yes'){
				var param = '&purchaseinfoId='+purchaseinfoId;
				new AjaxEngine(contextPath + '/sample/purchase/purchaseInfoController.spr?action=_delete', 
				{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
			}
		 }
	});
  }
  _postdeletePurchaseInfo();
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
 * 新增采购订单(SAP)
 */
function _createPurchaseInfo()
{
	if(_precreatePurchaseInfo()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.purchaseInfo_Create,'',contextPath + '/sample/purchase/purchaseInfoController.spr?action=_create');
     }
     _postcreatePurchaseInfo();
}
          
/**
 * 保存 
 */
function _saveOrUpdatePurchaseInfo()
{
if(_presaveOrUpdatePurchaseInfo()){
	var param = Form.serialize('mainForm');	
	           
		        	         
		        		param = param + ""+ getPurchaseRowsGridData();
		        	    //alert(param);
	new AjaxEngine(contextPath + '/sample/purchase/purchaseInfoController.spr?action=_saveOrUpdate', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
   }
   _postsaveOrUpdatePurchaseInfo();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	//var id = responseUtil.getCustomField('coustom');
	var purchaseinfoId=result.purchaseinfoId;
	//document.getElementById("PurchaseInfo.purchaseinfoId").value = id;
	document.getElementById("PurchaseInfo.creator_text").value = result.creator_text;
	document.getElementById("PurchaseInfo.creator").value = result.creator;
	document.getElementById("PurchaseInfo.createTime").value = result.createTime;
	document.getElementById("PurchaseInfo.lastmodifyTime").value = result.lastmodifyTime;
	document.getElementById("PurchaseInfo.lastmodifyer_text").value = result.lastmodifyer_text;
	document.getElementById("PurchaseInfo.lastmodifyer").value = result.lastmodifyer;
	reload_PurchaseRows_grid("defaultCondition=YPURCHASEROWS.PURCHASEINFOID='"+ purchaseinfoId +"'");
}
          
/**
 * 取消
 */
function _cancelPurchaseInfo()
{
  if(_precancelPurchaseInfo()){
	new AjaxEngine(contextPath + '/sample/purchase/purchaseInfoController.spr?action=_cancel&purchaseinfoId='+purchaseinfoId, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelPurchaseInfoCallBack});
   }
   _postcancelPurchaseInfo();
}
function _cancelPurchaseInfoCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          

/**
 * 提交
 */
function _submitProcessPurchaseInfo()
{
if(_presubmitProcessPurchaseInfo()){
	var param = Form.serialize('mainForm');	
          
	        	         
	param = param + "&" + getPurchaseRowsGridData();
	        		param = param + "&"+ Form.serialize('workflowForm');
	
	new AjaxEngine(contextPath +'/sample/purchase/purchaseInfoController.spr?action=_submitProcess', 
		   {method:"post", parameters: param, onComplete: callBackHandle,callback:_submitCallBackHandle});
  }
  _postsubmitProcessPurchaseInfo();
}

/**
 * 提交后的回调动作
 */
function _submitCallBackHandle()
{
	_getMainFrame().showInfo(Txt.submitSuccess);
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
