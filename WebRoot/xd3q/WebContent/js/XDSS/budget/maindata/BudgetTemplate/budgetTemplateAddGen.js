/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年03月05日 10点28分25秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象预算模版(BudgetTemplate)增加页面JS文件
 */

             
/**
 * grid 上的 创建按钮调用方法 
 * 新增预算模版,模版预算项
 */
function _createBudgetTemplateItem()
{
  if(_precreateBudgetTemplateItem()){
     _commonMethodOperation('1',Txt.budgetTemplateItem_Create,BudgetTemplateItem_grid,'XDSS/budget/maindata/BudgetTemplateItem/budgetTemplateItemController.spr?action=_create','_createBudgetTemplateItemCallBack',true);
   }
   _postcreateBudgetTemplateItem();
}

/**
 * 打开页签，回调
 */
function _createBudgetTemplateItemCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_insertBudgetTemplateItemRow(json);
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增预算模版,模版预算项
 */
function _createBudgetTemplateItemm()
{
  if(_precreateBudgetTemplateItem()){
	_getMainFrame().ExtModalWindowUtil.show(Txt.budgetTemplateItem_Create,
			contextPath + '/XDSS/budget/maindata/BudgetTemplateItem/budgetTemplateItemController.spr?action=_create','',_createBudgetTemplateItemmCallBack,{width:660,height:300});
   }
   _postcreateBudgetTemplateItem();
}

/**
 * 弹出窗口，回调
 */
function _createBudgetTemplateItemmCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_insertBudgetTemplateItemRow(json);
}

function _insertBudgetTemplateItemRow(json)
{
	if(json){
	var budgetTemplateItemFields = new BudgetTemplateItem_fields({
		budtemitemid:''
		,buditemid:''
		,buditemid_text:''
		,seq:''
		,subject:''
		,subject_text:''
		,budconcycle:''
		,status:''
		,creator:''
		,createtime:''
		,lastmodifyer:''
		,lastmodifytime:''
		,budtemid:''
	});		

		BudgetTemplateItem_grid.stopEditing();
		BudgetTemplateItem_grid.getStore().insert(0, budgetTemplateItemFields);
		BudgetTemplateItem_grid.startEditing(0, 0);
		var record = BudgetTemplateItem_grid.getStore().getAt(0);
		record.set('budtemitemid',json["BudgetTemplateItem.budtemitemid"]);
		record.set('buditemid',json["BudgetTemplateItem.buditemid"]);
		record.set('buditemid_text',json["BudgetTemplateItem.buditemid_htext"]);
		record.set('seq',json["BudgetTemplateItem.seq"]);
		record.set('subject',json["BudgetTemplateItem.subject"]);
		record.set('subject_text',json["BudgetTemplateItem.subject_htext"]);
		record.set('budconcycle',json["BudgetTemplateItem.budconcycle"]);
		record.set('status',json["BudgetTemplateItem.status"]);
		record.set('creator',json["BudgetTemplateItem.creator"]);
		record.set('createtime',json["BudgetTemplateItem.createtime"]);
		record.set('lastmodifyer',json["BudgetTemplateItem.lastmodifyer"]);
		record.set('lastmodifytime',json["BudgetTemplateItem.lastmodifytime"]);
		record.set('budtemid',json["BudgetTemplateItem.budtemid"]);
	}
}	
    

    
/**
 *预算模版行项目
 *复制创建
 */
function _copyCreateBudgetTemplateItem()
{
  if(_precopyCreateBudgetTemplateItem()){
		if (BudgetTemplateItem_grid.selModel.hasSelection() > 0){
			var records = BudgetTemplateItem_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.budgetTemplateItem_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
		    _commonMethodOperation('1',Txt.budgetTemplateItem_CopyCreate,BudgetTemplateItem_grid,'XDSS/budget/maindata/BudgetTemplateItem/budgetTemplateItemController.spr?action=_copyCreate'+pars,'_createBudgetTemplateItemCallBack',true);
		
		}else{
			_getMainFrame().showInfo(Txt.budgetTemplateItem_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateBudgetTemplateItem();
}


/**
 *预算模版行项目
 *复制创建
 */
function _copyCreateBudgetTemplateItemm()
{
  if(_precopyCreateBudgetTemplateItem()){
		if (BudgetTemplateItem_grid.selModel.hasSelection() > 0){
			var records = BudgetTemplateItem_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.budgetTemplateItem_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
			_getMainFrame().ExtModalWindowUtil.show(Txt.budgetTemplateItem_CopyCreate,contextPath + '/XDSS/budget/maindata/BudgetTemplateItem/budgetTemplateItemController.spr?action=_copyCreate'+pars,'',_createBudgetTemplateItemmCallBack,{width:610,height:200});
		}else{
			_getMainFrame().showInfo(Txt.budgetTemplateItem_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateBudgetTemplateItem();
}

    

    
  
    


function _editBudgetTemplateItempCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_modifyBudgetTemplateItemRow(json);
}


function _editBudgetTemplateItemmCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_modifyBudgetTemplateItemRow(json);
}

/**
  * 预算模版行项目编辑操作
  */
function _editBudgetTemplateItem(id,url)
{
	if(_preeditBudgetTemplateItem()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到编辑页面
		var records = BudgetTemplateItem_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.budgetTemplateItem_Edit,	url,'',_editBudgetTemplateItemCallBack,{width:660,height:300});
      
      }
      _posteditBudgetTemplateItem();
}
function _editBudgetTemplateItemCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_modifyBudgetTemplateItemRow(json);
}

/**
 * 行项目编辑后的grid更新
 */
function _modifyBudgetTemplateItemRow(json)
{
	if(json){
		var records = BudgetTemplateItem_grid.selModel.getSelections();
		var record = records[0];
		record.set('buditemid',json["BudgetTemplateItem.buditemid"]);
		record.set('seq',json["BudgetTemplateItem.seq"]);
		record.set('subject',json["BudgetTemplateItem.subject"]);
		record.set('budconcycle',json["BudgetTemplateItem.budconcycle"]);
		record.set('status',json["BudgetTemplateItem.status"]);
		record.set('creator',json["BudgetTemplateItem.creator"]);
		record.set('createtime',json["BudgetTemplateItem.createtime"]);
		record.set('lastmodifyer',json["BudgetTemplateItem.lastmodifyer"]);
		record.set('lastmodifytime',json["BudgetTemplateItem.lastmodifytime"]);
		record.set('budtemid',json["BudgetTemplateItem.budtemid"]);
	}
}
/**
  * 预算模版行项目查看操作
  */
function _viewBudgetTemplateItem(id,url)
{
	if(_previewBudgetTemplateItem()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到查看页面
		var records = BudgetTemplateItem_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.budgetTemplateItem_View,	url,'','',{width:660,height:300});
	}
	_postviewBudgetTemplateItem();
}
/**
  * 预算模版行项目查看，打开到页签 ,回调函数
  */
function _viewBudgetTemplateItempCallBack()
{
}

/**
  * 预算模版行项目查看，弹出窗口 ,回调函数
  */
function _viewBudgetTemplateItemmCallBack()
{
}

    
  
          
/**
 * 保存 
 */
function _save()
{
	//if(isCreateCopy){
	//	document.getElementById("BudgetTemplate.budtemid").value = "";
	//}
	if(_presave()){
		var param = Form.serialize('mainForm');	
		           
			        	         
		if(isCreateCopy)
		{
			param = param + ""+ getAllBudgetTemplateItemGridData();
		}
		else
		{
			param = param + ""+ getBudgetTemplateItemGridData(); 
		}
			        		    new AjaxEngine(contextPath + '/XDSS/budget/maindata/BudgetTemplate/budgetTemplateController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
	}
	_postsave();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var budtemid=result.budtemid;
	document.getElementById("BudgetTemplate.budtemid").value = budtemid;
	document.getElementById("BudgetTemplate.creator_text").value = result.creator_text;
	document.getElementById("BudgetTemplate.creator").value = result.creator;
	document.getElementById("BudgetTemplate.createtime").value = result.createtime;
	document.getElementById("BudgetTemplate.lastmodifyer_text").value = result.lastmodifyer_text;
	document.getElementById("BudgetTemplate.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("BudgetTemplate.lastmodifytime").value = result.lastmodifytime;
	isCreateCopy = false;	
	reload_BudgetTemplateItem_grid("defaultCondition=YBUDTEMITEM.BUDTEMID='"+ budtemid +"'");

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
