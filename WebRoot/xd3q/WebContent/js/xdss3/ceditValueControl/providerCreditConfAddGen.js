﻿/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年05月28日 09点10分41秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象供应商信用额度配置(ProviderCreditConf)增加页面JS文件
 */

             
/**
 * grid 上的 创建按钮调用方法 
 * 新增供应商信用额度配置,供应商授限立项配置
 */
function _createProviderCreditProj()
{
  if(_precreateProviderCreditProj()){
     _commonMethodOperation('1',Txt.providerCreditProj_Create,ProviderCreditProj_grid,'xdss3/ceditValueControl/providerCreditProjController.spr?action=_create','_createProviderCreditProjCallBack',true);
   }
   _postcreateProviderCreditProj();
}

/**
 * 打开页签，回调
 */
function _createProviderCreditProjCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_insertProviderCreditProjRow(json);
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增供应商信用额度配置,供应商授限立项配置
 */
function _createProviderCreditProjm()
{
  if(_precreateProviderCreditProj()){
	_getMainFrame().ExtModalWindowUtil.show(Txt.providerCreditProj_Create,
			contextPath + '/xdss3/ceditValueControl/providerCreditProjController.spr?action=_create','',_createProviderCreditProjmCallBack,{width:660,height:300});
   }
   _postcreateProviderCreditProj();
}

/**
 * 弹出窗口，回调
 */
function _createProviderCreditProjmCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_insertProviderCreditProjRow(json);
}

function _insertProviderCreditProjRow(json)
{
	if (json) {
		for ( var i = 0; i < ProviderCreditProj_store.getCount(); i++) {
			if (json["ProviderCreditProj.projectid"] == ProviderCreditProj_store.getAt(i).get('projectid')) {
				_getMainFrame().showInfo('请勿添加同一立项！');
				return;
			}
		}
		var providerCreditProjFields = new ProviderCreditProj_fields({
			configprojectid : '',
			configid : '',
			otherprepayvalue : '',
			projectid : '',
			projectno : '',
			projectid_text : '',
			creator : '',
			createtime : '',
			lastmodifyer : '',
			lastmodifytime : ''
		});

		ProviderCreditProj_grid.stopEditing();
		ProviderCreditProj_grid.getStore().insert(0, providerCreditProjFields);
		ProviderCreditProj_grid.startEditing(0, 0);
		var record = ProviderCreditProj_grid.getStore().getAt(0);
		record.set('configprojectid', json["ProviderCreditProj.configprojectid"]);
		record.set('configid', json["ProviderCreditProj.configid"]);
		record.set('otherprepayvalue', json["ProviderCreditProj.otherprepayvalue"]);
		record.set('projectid', json["ProviderCreditProj.projectid"]);
		record.set('projectno', json["ProviderCreditProj.projectno"]);
		record.set('projectid_text', json["ProviderCreditProj.projectid_htext"]);
		record.set('creator', json["ProviderCreditProj.creator"]);
		record.set('createtime', json["ProviderCreditProj.createtime"]);
		record.set('lastmodifyer', json["ProviderCreditProj.lastmodifyer"]);
		record.set('lastmodifytime', json["ProviderCreditProj.lastmodifytime"]);
	}
}	
    
    
/**
 *供应商信用额度配置行项目
 *复制创建
 */
function _copyCreateProviderCreditProj()
{
  if(_precopyCreateProviderCreditProj()){
		if (ProviderCreditProj_grid.selModel.hasSelection() > 0){
			var records = ProviderCreditProj_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.providerCreditProj_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
		    _commonMethodOperation('1',Txt.providerCreditProj_CopyCreate,ProviderCreditProj_grid,'xdss3/ceditValueControl/providerCreditProjController.spr?action=_copyCreate'+pars,'_createProviderCreditProjCallBack',true);
		
		}else{
			_getMainFrame().showInfo(Txt.providerCreditProj_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateProviderCreditProj();
}


/**
 *供应商信用额度配置行项目
 *复制创建
 */
function _copyCreateProviderCreditProjm()
{
  if(_precopyCreateProviderCreditProj()){
		if (ProviderCreditProj_grid.selModel.hasSelection() > 0){
			var records = ProviderCreditProj_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.providerCreditProj_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
			_getMainFrame().ExtModalWindowUtil.show(Txt.providerCreditProj_CopyCreate,contextPath + '/xdss3/ceditValueControl/providerCreditProjController.spr?action=_copyCreate'+pars,'',_createProviderCreditProjmCallBack,{width:610,height:200});
		}else{
			_getMainFrame().showInfo(Txt.providerCreditProj_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateProviderCreditProj();
}

    
    
  
    
/**
  * 供应商信用额度配置行项目查看操作
  */
function _viewProviderCreditProj(id,url)
{
	if(_previewProviderCreditProj()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到查看页面
		var records = ProviderCreditProj_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.providerCreditProj_View,	url,'','',{width:660,height:300});
	}
	_postviewProviderCreditProj();
}
/**
  * 供应商信用额度配置行项目查看，打开到页签 ,回调函数
  */
function _viewProviderCreditProjpCallBack()
{
}

/**
  * 供应商信用额度配置行项目查看，弹出窗口 ,回调函数
  */
function _viewProviderCreditProjmCallBack()
{
}


function _editProviderCreditProjpCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_modifyProviderCreditProjRow(json);
}


function _editProviderCreditProjmCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_modifyProviderCreditProjRow(json);
}

/**
  * 供应商信用额度配置行项目编辑操作
  */
function _editProviderCreditProj(id,url)
{
	if(_preeditProviderCreditProj()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到编辑页面
		var records = ProviderCreditProj_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.providerCreditProj_Edit,	url,'',_editProviderCreditProjCallBack,{width:660,height:300});
      
      }
      _posteditProviderCreditProj();
}
function _editProviderCreditProjCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_modifyProviderCreditProjRow(json);
}

/**
 * 行项目编辑后的grid更新
 */
function _modifyProviderCreditProjRow(json)
{
	if(json){
		var records = ProviderCreditProj_grid.selModel.getSelections();
		var record = records[0];
		record.set('configid',json["ProviderCreditProj.configid"]);
		record.set('otherprepayvalue',json["ProviderCreditProj.otherprepayvalue"]);
		record.set('projectid',json["ProviderCreditProj.projectid"]);
		record.set('creator',json["ProviderCreditProj.creator"]);
		record.set('createtime',json["ProviderCreditProj.createtime"]);
		record.set('lastmodifyer',json["ProviderCreditProj.lastmodifyer"]);
		record.set('lastmodifytime',json["ProviderCreditProj.lastmodifytime"]);
	}
}
    
  

          
/**
 * 保存 
 */
function _saveOrUpdateProviderCreditConf()
{					 
if(isCreateCopy){
		document.getElementById("ProviderCreditConf.configid").value = "";
	}
	if(_presaveOrUpdateProviderCreditConf()){
		var param = Form.serialize('mainForm');	
		           
			        	         
		if(isCreateCopy)
		{
			param = param + ""+ getAllProviderCreditProjGridData();
		}
		else
		{
			param = param + ""+ getProviderCreditProjGridData(); 
		}
			        		    new AjaxEngine(contextPath + '/xdss3/ceditValueControl/providerCreditConfController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}

	_postsaveOrUpdateProviderCreditConf();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var configid=result.configid;
	document.getElementById("ProviderCreditConf.configid").value = configid;
	
	document.getElementById("ProviderCreditConf.creator_text").value = result.creator_text;
	document.getElementById("ProviderCreditConf.creator").value = result.creator;
	document.getElementById("ProviderCreditConf.createtime").value = result.createtime;
	document.getElementById("ProviderCreditConf.lastmodifyer_text").value = result.lastmodifyer_text;
	document.getElementById("ProviderCreditConf.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("ProviderCreditConf.lastmodifytime").value = result.lastmodifytime;
	isCreateCopy = false;	
	reload_ProviderCreditProj_grid("defaultCondition=YPROVCREDPROJ.CONFIGID='"+ configid +"'");

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
