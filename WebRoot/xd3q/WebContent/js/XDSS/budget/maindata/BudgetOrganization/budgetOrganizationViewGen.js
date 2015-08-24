/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年03月05日 13点59分05秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象预算组织(BudgetOrganization)查看页面JS文件
 */
          
   
/**
 * grid 上的 创建按钮调用方法 
 * 新增预算组织,预算组织模版
 */
function _createBudgetOrgTemp()
{
  if(_precreateBudgetOrgTemp()){
     _commonMethodOperation('1',Txt.budgetOrgTemp_Create,BudgetOrgTemp_grid,'XDSS/budget/maindata/BudgetOrgTemp/budgetOrgTempController.spr?action=_create','_createBudgetOrgTempCallBack',true);
   }
   _postcreateBudgetOrgTemp();
}

/**
 * 打开页签，回调
 */
function _createBudgetOrgTempCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_insertBudgetOrgTempRow(json);
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增预算组织,预算组织模版
 */
function _createBudgetOrgTempm()
{
  if(_precreateBudgetOrgTemp()){
	_getMainFrame().ExtModalWindowUtil.show(Txt.budgetOrgTemp_Create,
			contextPath + '/XDSS/budget/maindata/BudgetOrgTemp/budgetOrgTempController.spr?action=_create','',_createBudgetOrgTempmCallBack,{width:660,height:300});
   }
   _postcreateBudgetOrgTemp();
}

/**
 * 弹出窗口，回调
 */
function _createBudgetOrgTempmCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_insertBudgetOrgTempRow(json);
}

function _insertBudgetOrgTempRow(json)
{
	if(json){
	var budgetOrgTempFields = new BudgetOrgTemp_fields({
		budorgtemid:''
       ,budorgid:''
	   ,budtemid:''
	   ,budtemid_text:''
       ,weastartdate:''
       ,weaentdate:''
       ,creator:''
       ,createtime:''
       ,lastmodifyer:''
       ,lastmodifytime:''
	});		
	
		BudgetOrgTemp_grid.stopEditing();
		BudgetOrgTemp_grid.getStore().insert(0, budgetOrgTempFields);
		BudgetOrgTemp_grid.startEditing(0, 0);
		var record = BudgetOrgTemp_grid.getStore().getAt(0);
			record.set('budorgtemid',json["BudgetOrgTemp.budorgtemid"]);
			record.set('budorgid',json["BudgetOrgTemp.budorgid"]);
			record.set('budtemid',json["BudgetOrgTemp.budtemid"]);
			record.set('budtemid_text',json["BudgetOrgTemp.budtemid_htext"]);
			record.set('weastartdate',json["BudgetOrgTemp.weastartdate"]);
			record.set('weaentdate',json["BudgetOrgTemp.weaentdate"]);
			record.set('creator',json["BudgetOrgTemp.creator"]);
			record.set('createtime',json["BudgetOrgTemp.createtime"]);
			record.set('lastmodifyer',json["BudgetOrgTemp.lastmodifyer"]);
			record.set('lastmodifytime',json["BudgetOrgTemp.lastmodifytime"]);
	}
}

    

    
/**
 *预算组织行项目
 *复制创建
 */
function _copyCreateBudgetOrgTemp()
{
  if(_precopyCreateBudgetOrgTemp()){
		if (BudgetOrgTemp_grid.selModel.hasSelection() > 0){
			var records = BudgetOrgTemp_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.budgetOrgTemp_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
		    _commonMethodOperation('1',Txt.budgetOrgTemp_CopyCreate,BudgetOrgTemp_grid,'XDSS/budget/maindata/BudgetOrgTemp/budgetOrgTempController.spr?action=_copyCreate'+pars,'_createBudgetOrgTempCallBack',true);
		
		}else{
			_getMainFrame().showInfo(Txt.budgetOrgTemp_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateBudgetOrgTemp();
}


/**
 *预算组织行项目
 *复制创建
 */
function _copyCreateBudgetOrgTempm()
{
  if(_precopyCreateBudgetOrgTemp()){
		if (BudgetOrgTemp_grid.selModel.hasSelection() > 0){
			var records = BudgetOrgTemp_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.budgetOrgTemp_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
			_getMainFrame().ExtModalWindowUtil.show(Txt.budgetOrgTemp_CopyCreate,contextPath + '/XDSS/budget/maindata/BudgetOrgTemp/budgetOrgTempController.spr?action=_copyCreate'+pars,'',_createBudgetOrgTempmCallBack,{width:610,height:200});
		}else{
			_getMainFrame().showInfo(Txt.budgetOrgTemp_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateBudgetOrgTemp();
}
    

    
  

    

function _editBudgetOrgTemppCallBack()
{
}


function _editBudgetOrgTempmCallBack()
{
}

/**
  * 预算组织行项目编辑操作
  */
function _editBudgetOrgTemp(id,url)
{
	if(_preeditBudgetOrgTemp()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到编辑页面
		var records = BudgetOrgTemp_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.budgetOrgTemp_Edit,	url,'',_editBudgetOrgTempCallBack,{width:660,height:300});
      
      }
      _posteditBudgetOrgTemp();
}
function _editBudgetOrgTempCallBack()
{
}

    
/**
  * 预算组织行项目查看操作
  */
function _viewBudgetOrgTemp(id,url)
{
	if(_previewBudgetOrgTemp()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到查看页面
		var records = BudgetOrgTemp_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.budgetOrgTemp_View,	url,'','',{width:660,height:300});
	}
	_postviewBudgetOrgTemp();
}
/**
  * 预算组织行项目查看，打开到页签 ,回调函数
  */
function _viewBudgetOrgTemppCallBack()
{
}

/**
  * 预算组织行项目查看，弹出窗口 ,回调函数
  */
function _viewBudgetOrgTempmCallBack()
{
}

    
    
    
    
    
    
    
    
    
    
    
    
  

          
function _copyCreateBudgetOrganization()
{
	if(_precopyCreateBudgetOrganization()){
		_getMainFrame().maintabs.addPanel(Txt.budgetOrganization_CopyCreate,'',contextPath + '/XDSS/budget/maindata/BudgetOrganization/budgetOrganizationController.spr?action=_copyCreate&budorgid='+budorgid);
	}
	_postcopyCreateBudgetOrganization();
}

          

/**
 * 删除预算组织
 */
function _deleteBudgetOrganization()
{
if(_predeleteBudgetOrganization()){
		_getMainFrame().Ext.MessageBox.show({
			title:Txt.cue,
		    msg: Txt.budgetOrganization_Delete_ConfirmOperation,
			buttons: {yes:Txt.ok, no:Txt.cancel},
			icon: Ext.MessageBox.QUESTION,
			fn:function(result){
			   if (result == 'yes'){
					var param = '&budorgid='+budorgid;
					new AjaxEngine(contextPath + '/XDSS/budget/maindata/BudgetOrganization/budgetOrganizationController.spr?action=_delete', 
					{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
				}
			}
		});
	 }
 	 _postdeleteBudgetOrganization();
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
 * 新增预算组织
 */
function _createBudgetOrganization()
{
	if(_precreateBudgetOrganization()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.budgetOrganization_Create,'',contextPath + '/XDSS/budget/maindata/BudgetOrganization/budgetOrganizationController.spr?action=_create');
	}
	_postcreateBudgetOrganization();
}
          
/**
 * 保存 
 */
function _saveOrUpdateBudgetOrganization()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("BudgetOrganization.budorgid").value = id;	
}
          

/**
 * 取消
 */
function _cancelBudgetOrganization()
{
  if(_precancelBudgetOrganization()){
	new AjaxEngine(contextPath + '/XDSS/budget/maindata/BudgetOrganization/budgetOrganizationController.spr?action=_cancel&budorgid='+budorgid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelBudgetOrganizationCallBack});
   }
   _postcancelBudgetOrganization();
}

function _cancelBudgetOrganizationCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
