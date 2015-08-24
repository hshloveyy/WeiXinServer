/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年03月11日 12点13分09秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象预算分类(BudgetClass)查看页面JS文件
 */
          
   
/**
 * grid 上的 创建按钮调用方法 
 * 新增预算分类,预算模版
 */
function _createBudgetTemplate()
{
  if(_precreateBudgetTemplate()){
     _commonMethodOperation('1',Txt.budgetTemplate_Create,BudgetTemplate_grid,'XDSS/budget/maindata/BudgetTemplate/budgetTemplateController.spr?action=_create','_createBudgetTemplateCallBack',true);
   }
   _postcreateBudgetTemplate();
}

/**
 * 打开页签，回调
 */
function _createBudgetTemplateCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_insertBudgetTemplateRow(json);
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增预算分类,预算模版
 */
function _createBudgetTemplatem()
{
  if(_precreateBudgetTemplate()){
	_getMainFrame().ExtModalWindowUtil.show(Txt.budgetTemplate_Create,
			contextPath + '/XDSS/budget/maindata/BudgetTemplate/budgetTemplateController.spr?action=_create','',_createBudgetTemplatemCallBack,{width:660,height:300});
   }
   _postcreateBudgetTemplate();
}

/**
 * 弹出窗口，回调
 */
function _createBudgetTemplatemCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_insertBudgetTemplateRow(json);
}

function _insertBudgetTemplateRow(json)
{
	if(json){
	var budgetTemplateFields = new BudgetTemplate_fields({
		budtemid:''
       ,budtemname:''
       ,budtemtype:''
	   ,boid:''
	   ,boid_text:''
       ,budconcycle:''
       ,creator:''
       ,createtime:''
       ,lastmodifyer:''
       ,lastmodifytime:''
       ,budclassid:''
	});		
	
		BudgetTemplate_grid.stopEditing();
		BudgetTemplate_grid.getStore().insert(0, budgetTemplateFields);
		BudgetTemplate_grid.startEditing(0, 0);
		var record = BudgetTemplate_grid.getStore().getAt(0);
			record.set('budtemid',json["BudgetTemplate.budtemid"]);
			record.set('budtemname',json["BudgetTemplate.budtemname"]);
			record.set('budtemtype',json["BudgetTemplate.budtemtype"]);
			record.set('boid',json["BudgetTemplate.boid"]);
			record.set('boid_text',json["BudgetTemplate.boid_htext"]);
			record.set('budconcycle',json["BudgetTemplate.budconcycle"]);
			record.set('creator',json["BudgetTemplate.creator"]);
			record.set('createtime',json["BudgetTemplate.createtime"]);
			record.set('lastmodifyer',json["BudgetTemplate.lastmodifyer"]);
			record.set('lastmodifytime',json["BudgetTemplate.lastmodifytime"]);
			record.set('budclassid',json["BudgetTemplate.budclassid"]);
	}
}

    

    
/**
 *预算分类行项目
 *复制创建
 */
function _copyCreateBudgetTemplate()
{
  if(_precopyCreateBudgetTemplate()){
		if (BudgetTemplate_grid.selModel.hasSelection() > 0){
			var records = BudgetTemplate_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.budgetTemplate_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
		    _commonMethodOperation('1',Txt.budgetTemplate_CopyCreate,BudgetTemplate_grid,'XDSS/budget/maindata/BudgetTemplate/budgetTemplateController.spr?action=_copyCreate'+pars,'_createBudgetTemplateCallBack',true);
		
		}else{
			_getMainFrame().showInfo(Txt.budgetTemplate_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateBudgetTemplate();
}


/**
 *预算分类行项目
 *复制创建
 */
function _copyCreateBudgetTemplatem()
{
  if(_precopyCreateBudgetTemplate()){
		if (BudgetTemplate_grid.selModel.hasSelection() > 0){
			var records = BudgetTemplate_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.budgetTemplate_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
			_getMainFrame().ExtModalWindowUtil.show(Txt.budgetTemplate_CopyCreate,contextPath + '/XDSS/budget/maindata/BudgetTemplate/budgetTemplateController.spr?action=_copyCreate'+pars,'',_createBudgetTemplatemCallBack,{width:610,height:200});
		}else{
			_getMainFrame().showInfo(Txt.budgetTemplate_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateBudgetTemplate();
}
    

    
    

    
  

    

function _editBudgetTemplatepCallBack()
{
}


function _editBudgetTemplatemCallBack()
{
}

/**
  * 预算分类行项目编辑操作
  */
function _editBudgetTemplate(id,url)
{
	if(_preeditBudgetTemplate()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到编辑页面
		var records = BudgetTemplate_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.budgetTemplate_Edit,	url,'',_editBudgetTemplateCallBack,{width:660,height:300});
      
      }
      _posteditBudgetTemplate();
}
function _editBudgetTemplateCallBack()
{
}

    
/**
  * 预算分类行项目查看操作
  */
function _viewBudgetTemplate(id,url)
{
	if(_previewBudgetTemplate()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到查看页面
		var records = BudgetTemplate_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.budgetTemplate_View,	url,'','',{width:660,height:300});
	}
	_postviewBudgetTemplate();
}
/**
  * 预算分类行项目查看，打开到页签 ,回调函数
  */
function _viewBudgetTemplatepCallBack()
{
}

/**
  * 预算分类行项目查看，弹出窗口 ,回调函数
  */
function _viewBudgetTemplatemCallBack()
{
}

    
    
    
    
    
    
    
    
    
    
    
    
    
  

          
function _copyCreateBudgetClass()
{
	if(_precopyCreateBudgetClass()){
		_getMainFrame().maintabs.addPanel(Txt.budgetClass_CopyCreate,'',contextPath + '/XDSS/budget/maindata/BudgetClass/budgetClassController.spr?action=_copyCreate&budclassid='+budclassid);
	}
	_postcopyCreateBudgetClass();
}

          

/**
 * 删除预算分类
 */
function _deleteBudgetClass()
{
if(_predeleteBudgetClass()){
		_getMainFrame().Ext.MessageBox.show({
			title:Txt.cue,
		    msg: Txt.budgetClass_Delete_ConfirmOperation,
			buttons: {yes:Txt.ok, no:Txt.cancel},
			icon: Ext.MessageBox.QUESTION,
			fn:function(result){
			   if (result == 'yes'){
					var param = '&budclassid='+budclassid;
					new AjaxEngine(contextPath + '/XDSS/budget/maindata/BudgetClass/budgetClassController.spr?action=_delete', 
					{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
				}
			}
		});
	 }
 	 _postdeleteBudgetClass();
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
 * 新增预算分类
 */
function _createBudgetClass()
{
	if(_precreateBudgetClass()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.budgetClass_Create,'',contextPath + '/XDSS/budget/maindata/BudgetClass/budgetClassController.spr?action=_create');
	}
	_postcreateBudgetClass();
}
          
/**
 * 保存 
 */
function _saveOrUpdateBudgetClass()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("BudgetClass.budclassid").value = id;	
}
          

/**
 * 取消
 */
function _cancelBudgetClass()
{
  if(_precancelBudgetClass()){
	new AjaxEngine(contextPath + '/XDSS/budget/maindata/BudgetClass/budgetClassController.spr?action=_cancel&budclassid='+budclassid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelBudgetClassCallBack});
   }
   _postcancelBudgetClass();
}

function _cancelBudgetClassCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
