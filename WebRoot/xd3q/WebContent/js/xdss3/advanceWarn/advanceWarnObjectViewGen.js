/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年06月13日 18点01分18秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象预警对像配置(AdvanceWarnObject)查看页面JS文件
 */
          
   
/**
 * grid 上的 创建按钮调用方法 
 * 新增预警对像配置,预警对像接收人
 */
function _createAdvanceWarnRecv()
{
  if(_precreateAdvanceWarnRecv()){
     _commonMethodOperation('1',Txt.advanceWarnRecv_Create,AdvanceWarnRecv_grid,'xdss3/advanceWarn/advanceWarnRecvController.spr?action=_create','_createAdvanceWarnRecvCallBack',true);
   }
   _postcreateAdvanceWarnRecv();
}

/**
 * 打开页签，回调
 */
function _createAdvanceWarnRecvCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_insertAdvanceWarnRecvRow(json);
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增预警对像配置,预警对像接收人
 */
function _createAdvanceWarnRecvm()
{
  if(_precreateAdvanceWarnRecv()){
	_getMainFrame().ExtModalWindowUtil.show(Txt.advanceWarnRecv_Create,
			contextPath + '/xdss3/advanceWarn/advanceWarnRecvController.spr?action=_create','',_createAdvanceWarnRecvmCallBack,{width:660,height:300});
   }
   _postcreateAdvanceWarnRecv();
}

/**
 * 弹出窗口，回调
 */
function _createAdvanceWarnRecvmCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_insertAdvanceWarnRecvRow(json);
}

function _insertAdvanceWarnRecvRow(json)
{
	if(json){
	var advanceWarnRecvFields = new AdvanceWarnRecv_fields({
		receiveid:''
       ,creator:''
       ,createtime:''
       ,lastmodifyer:''
       ,lastmodifytime:''
       ,warnid:''
	   ,createdeptid:''
	   ,createdeptid_text:''
	   ,receiveuserid:''
	   ,receiveuserid_text:''
       ,receivetype:''
       ,receiveaddr:''
	});		
	
		AdvanceWarnRecv_grid.stopEditing();
		AdvanceWarnRecv_grid.getStore().insert(0, advanceWarnRecvFields);
		AdvanceWarnRecv_grid.startEditing(0, 0);
		var record = AdvanceWarnRecv_grid.getStore().getAt(0);
			record.set('receiveid',json["AdvanceWarnRecv.receiveid"]);
			record.set('creator',json["AdvanceWarnRecv.creator"]);
			record.set('createtime',json["AdvanceWarnRecv.createtime"]);
			record.set('lastmodifyer',json["AdvanceWarnRecv.lastmodifyer"]);
			record.set('lastmodifytime',json["AdvanceWarnRecv.lastmodifytime"]);
			record.set('warnid',json["AdvanceWarnRecv.warnid"]);
			record.set('createdeptid',json["AdvanceWarnRecv.createdeptid"]);
			record.set('createdeptid_text',json["AdvanceWarnRecv.createdeptid_htext"]);
			record.set('receiveuserid',json["AdvanceWarnRecv.receiveuserid"]);
			record.set('receiveuserid_text',json["AdvanceWarnRecv.receiveuserid_htext"]);
			record.set('receivetype',json["AdvanceWarnRecv.receivetype"]);
			record.set('receiveaddr',json["AdvanceWarnRecv.receiveaddr"]);
	}
}

    

    
/**
 *预警对像配置行项目
 *复制创建
 */
function _copyCreateAdvanceWarnRecv()
{
  if(_precopyCreateAdvanceWarnRecv()){
		if (AdvanceWarnRecv_grid.selModel.hasSelection() > 0){
			var records = AdvanceWarnRecv_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.advanceWarnRecv_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
		    _commonMethodOperation('1',Txt.advanceWarnRecv_CopyCreate,AdvanceWarnRecv_grid,'xdss3/advanceWarn/advanceWarnRecvController.spr?action=_copyCreate'+pars,'_createAdvanceWarnRecvCallBack',true);
		
		}else{
			_getMainFrame().showInfo(Txt.advanceWarnRecv_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateAdvanceWarnRecv();
}


/**
 *预警对像配置行项目
 *复制创建
 */
function _copyCreateAdvanceWarnRecvm()
{
  if(_precopyCreateAdvanceWarnRecv()){
		if (AdvanceWarnRecv_grid.selModel.hasSelection() > 0){
			var records = AdvanceWarnRecv_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.advanceWarnRecv_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
			_getMainFrame().ExtModalWindowUtil.show(Txt.advanceWarnRecv_CopyCreate,contextPath + '/xdss3/advanceWarn/advanceWarnRecvController.spr?action=_copyCreate'+pars,'',_createAdvanceWarnRecvmCallBack,{width:610,height:200});
		}else{
			_getMainFrame().showInfo(Txt.advanceWarnRecv_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateAdvanceWarnRecv();
}
    

    
  

        
    
    
    
    
    
    

function _editAdvanceWarnRecvpCallBack()
{
}


function _editAdvanceWarnRecvmCallBack()
{
}

/**
  * 预警对像配置行项目编辑操作
  */
function _editAdvanceWarnRecv(id,url)
{
	if(_preeditAdvanceWarnRecv()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到编辑页面
		var records = AdvanceWarnRecv_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.advanceWarnRecv_Edit,	url,'',_editAdvanceWarnRecvCallBack,{width:660,height:300});
      
      }
      _posteditAdvanceWarnRecv();
}
function _editAdvanceWarnRecvCallBack()
{
}

    
/**
  * 预警对像配置行项目查看操作
  */
function _viewAdvanceWarnRecv(id,url)
{
	if(_previewAdvanceWarnRecv()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到查看页面
		var records = AdvanceWarnRecv_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.advanceWarnRecv_View,	url,'','',{width:660,height:300});
	}
	_postviewAdvanceWarnRecv();
}
/**
  * 预警对像配置行项目查看，打开到页签 ,回调函数
  */
function _viewAdvanceWarnRecvpCallBack()
{
}

/**
  * 预警对像配置行项目查看，弹出窗口 ,回调函数
  */
function _viewAdvanceWarnRecvmCallBack()
{
}

    
    
    
    
    

          
   
/**
 * grid 上的 创建按钮调用方法 
 * 新增预警对像配置,预警对像条件
 */
function _createAdvanceWarnCond()
{
  if(_precreateAdvanceWarnCond()){
     _commonMethodOperation('1',Txt.advanceWarnCond_Create,AdvanceWarnCond_grid,'xdss3/advanceWarn/advanceWarnCondController.spr?action=_create','_createAdvanceWarnCondCallBack',true);
   }
   _postcreateAdvanceWarnCond();
}

/**
 * 打开页签，回调
 */
function _createAdvanceWarnCondCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_insertAdvanceWarnCondRow(json);
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增预警对像配置,预警对像条件
 */
function _createAdvanceWarnCondm()
{
  if(_precreateAdvanceWarnCond()){
	_getMainFrame().ExtModalWindowUtil.show(Txt.advanceWarnCond_Create,
			contextPath + '/xdss3/advanceWarn/advanceWarnCondController.spr?action=_create','',_createAdvanceWarnCondmCallBack,{width:660,height:300});
   }
   _postcreateAdvanceWarnCond();
}

/**
 * 弹出窗口，回调
 */
function _createAdvanceWarnCondmCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_insertAdvanceWarnCondRow(json);
}

function _insertAdvanceWarnCondRow(json)
{
	if(json){
	var advanceWarnCondFields = new AdvanceWarnCond_fields({
		conditionid:''
       ,warnid:''
       ,condno:''
       ,leftparentheses:''
	   ,fieldcode:''
	   ,fieldcode_text:''
       ,condrole:''
       ,condvalue:''
       ,rightparentheses:''
       ,connectcond:''
       ,creator:''
       ,createtime:''
       ,lastmodifyer:''
       ,lastmodifytime:''
	});		
	
		AdvanceWarnCond_grid.stopEditing();
		AdvanceWarnCond_grid.getStore().insert(0, advanceWarnCondFields);
		AdvanceWarnCond_grid.startEditing(0, 0);
		var record = AdvanceWarnCond_grid.getStore().getAt(0);
			record.set('conditionid',json["AdvanceWarnCond.conditionid"]);
			record.set('warnid',json["AdvanceWarnCond.warnid"]);
			record.set('condno',json["AdvanceWarnCond.condno"]);
			record.set('leftparentheses',json["AdvanceWarnCond.leftparentheses"]);
			record.set('fieldcode',json["AdvanceWarnCond.fieldcode"]);
			record.set('fieldcode_text',json["AdvanceWarnCond.fieldcode_htext"]);
			record.set('condrole',json["AdvanceWarnCond.condrole"]);
			record.set('condvalue',json["AdvanceWarnCond.condvalue"]);
			record.set('rightparentheses',json["AdvanceWarnCond.rightparentheses"]);
			record.set('connectcond',json["AdvanceWarnCond.connectcond"]);
			record.set('creator',json["AdvanceWarnCond.creator"]);
			record.set('createtime',json["AdvanceWarnCond.createtime"]);
			record.set('lastmodifyer',json["AdvanceWarnCond.lastmodifyer"]);
			record.set('lastmodifytime',json["AdvanceWarnCond.lastmodifytime"]);
	}
}

    

    
/**
 *预警对像配置行项目
 *复制创建
 */
function _copyCreateAdvanceWarnCond()
{
  if(_precopyCreateAdvanceWarnCond()){
		if (AdvanceWarnCond_grid.selModel.hasSelection() > 0){
			var records = AdvanceWarnCond_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.advanceWarnCond_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
		    _commonMethodOperation('1',Txt.advanceWarnCond_CopyCreate,AdvanceWarnCond_grid,'xdss3/advanceWarn/advanceWarnCondController.spr?action=_copyCreate'+pars,'_createAdvanceWarnCondCallBack',true);
		
		}else{
			_getMainFrame().showInfo(Txt.advanceWarnCond_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateAdvanceWarnCond();
}


/**
 *预警对像配置行项目
 *复制创建
 */
function _copyCreateAdvanceWarnCondm()
{
  if(_precopyCreateAdvanceWarnCond()){
		if (AdvanceWarnCond_grid.selModel.hasSelection() > 0){
			var records = AdvanceWarnCond_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.advanceWarnCond_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
			_getMainFrame().ExtModalWindowUtil.show(Txt.advanceWarnCond_CopyCreate,contextPath + '/xdss3/advanceWarn/advanceWarnCondController.spr?action=_copyCreate'+pars,'',_createAdvanceWarnCondmCallBack,{width:610,height:200});
		}else{
			_getMainFrame().showInfo(Txt.advanceWarnCond_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateAdvanceWarnCond();
}
    

    
  

    
function _editAdvanceWarnCondpCallBack()
{
}


function _editAdvanceWarnCondmCallBack()
{
}

/**
  * 预警对像配置行项目编辑操作
  */
function _editAdvanceWarnCond(id,url)
{
	if(_preeditAdvanceWarnCond()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到编辑页面
		var records = AdvanceWarnCond_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.advanceWarnCond_Edit,	url,'',_editAdvanceWarnCondCallBack,{width:660,height:300});
      
      }
      _posteditAdvanceWarnCond();
}
function _editAdvanceWarnCondCallBack()
{
}

    
/**
  * 预警对像配置行项目查看操作
  */
function _viewAdvanceWarnCond(id,url)
{
	if(_previewAdvanceWarnCond()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到查看页面
		var records = AdvanceWarnCond_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.advanceWarnCond_View,	url,'','',{width:660,height:300});
	}
	_postviewAdvanceWarnCond();
}
/**
  * 预警对像配置行项目查看，打开到页签 ,回调函数
  */
function _viewAdvanceWarnCondpCallBack()
{
}

/**
  * 预警对像配置行项目查看，弹出窗口 ,回调函数
  */
function _viewAdvanceWarnCondmCallBack()
{
}

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

  

          
/**
 * 保存 
 */
function _saveOrUpdateAdvanceWarnObject()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("AdvanceWarnObject.warnid").value = id;	
}
          

/**
 * 取消
 */
function _cancelAdvanceWarnObject()
{
  if(_precancelAdvanceWarnObject()){
	new AjaxEngine(contextPath + '/xdss3/advanceWarn/advanceWarnObjectController.spr?action=_cancel&warnid='+warnid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelAdvanceWarnObjectCallBack});
   }
   _postcancelAdvanceWarnObject();
}

function _cancelAdvanceWarnObjectCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
