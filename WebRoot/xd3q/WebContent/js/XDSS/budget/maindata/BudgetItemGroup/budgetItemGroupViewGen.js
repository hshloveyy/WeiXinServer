/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年03月16日 11点36分09秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象预算项分组(BudgetItemGroup)查看页面JS文件
 */
          
   
/**
 * grid 上的 创建按钮调用方法 
 * 新增预算项分组,预算项
 */
function _createBudgetItem()
{
  if(_precreateBudgetItem()){
     _commonMethodOperation('1',Txt.budgetItem_Create,BudgetItem_grid,'XDSS/budget/maindata/BudgetItem/budgetItemController.spr?action=_create','_createBudgetItemCallBack',true);
   }
   _postcreateBudgetItem();
}

/**
 * 打开页签，回调
 */
function _createBudgetItemCallBack()
{
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_insertBudgetItemRow(json);
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增预算项分组,预算项
 */
function _createBudgetItemm()
{
  if(_precreateBudgetItem()){
	_getMainFrame().ExtModalWindowUtil.show(Txt.budgetItem_Create,
			contextPath + '/XDSS/budget/maindata/BudgetItem/budgetItemController.spr?action=_create','',_createBudgetItemmCallBack,{width:660,height:300});
   }
   _postcreateBudgetItem();
}

/**
 * 弹出窗口，回调
 */
function _createBudgetItemmCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_insertBudgetItemRow(json);
}

function _insertBudgetItemRow(json)
{
	if(json){
	var budgetItemFields = new BudgetItem_fields({
		buditemid:''
       ,buditemname:''
       ,budguidetype:''
       ,budfareguide:''
       ,weadesc:''
       ,buditemtype:''
	   ,budupitemid:''
	   ,budupitemid_text:''
	   ,yearitemid:''
	   ,yearitemid_text:''
       ,budgroupid:''
       ,creator:''
       ,createtime:''
       ,lastmodifyer:''
       ,lastmodifytime:''
	});		
	
		BudgetItem_grid.stopEditing();
		BudgetItem_grid.getStore().insert(0, budgetItemFields);
		BudgetItem_grid.startEditing(0, 0);
		var record = BudgetItem_grid.getStore().getAt(0);
			record.set('buditemid',json["BudgetItem.buditemid"]);
			record.set('buditemname',json["BudgetItem.buditemname"]);
			record.set('budguidetype',json["BudgetItem.budguidetype"]);
			record.set('budfareguide',json["BudgetItem.budfareguide"]);
			record.set('weadesc',json["BudgetItem.weadesc"]);
			record.set('buditemtype',json["BudgetItem.buditemtype"]);
			record.set('budupitemid',json["BudgetItem.budupitemid"]);
			record.set('budupitemid_text',json["BudgetItem.budupitemid_htext"]);
			record.set('yearitemid',json["BudgetItem.yearitemid"]);
			record.set('yearitemid_text',json["BudgetItem.yearitemid_htext"]);
			record.set('budgroupid',json["BudgetItem.budgroupid"]);
			record.set('creator',json["BudgetItem.creator"]);
			record.set('createtime',json["BudgetItem.createtime"]);
			record.set('lastmodifyer',json["BudgetItem.lastmodifyer"]);
			record.set('lastmodifytime',json["BudgetItem.lastmodifytime"]);
	}
}

    

    
/**
 *预算项分组行项目
 *复制创建
 */
function _copyCreateBudgetItem()
{
  if(_precopyCreateBudgetItem()){
		if (BudgetItem_grid.selModel.hasSelection() > 0){
			var records = BudgetItem_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.budgetItem_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
		    _commonMethodOperation('1',Txt.budgetItem_CopyCreate,BudgetItem_grid,'XDSS/budget/maindata/BudgetItem/budgetItemController.spr?action=_copyCreate'+pars,'_createBudgetItemCallBack',true);
		
		}else{
			_getMainFrame().showInfo(Txt.budgetItem_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateBudgetItem();
}


/**
 *预算项分组行项目
 *复制创建
 */
function _copyCreateBudgetItemm()
{
  if(_precopyCreateBudgetItem()){
		if (BudgetItem_grid.selModel.hasSelection() > 0){
			var records = BudgetItem_grid.selModel.getSelections();
			if(records.length>1){
				_getMainFrame().showInfo(Txt.budgetItem_CopyCreate_AllowOnlyOneItemForOperation);
				return;
			}   
			var recordData = commonUrlEncode(records[0].data);
			var pars ="&"+ recordData;
			_getMainFrame().ExtModalWindowUtil.show(Txt.budgetItem_CopyCreate,contextPath + '/XDSS/budget/maindata/BudgetItem/budgetItemController.spr?action=_copyCreate'+pars,'',_createBudgetItemmCallBack,{width:610,height:200});
		}else{
			_getMainFrame().showInfo(Txt.budgetItem_CopyCreate_SelectToOperation);
		}	
    }
    _postcopyCreateBudgetItem();
}
    

    
    

    
  


function _editBudgetItempCallBack()
{
}


function _editBudgetItemmCallBack()
{
}

/**
  * 预算项分组行项目编辑操作
  */
function _editBudgetItem(id,url)
{
	if(_preeditBudgetItem()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到编辑页面
		var records = BudgetItem_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.budgetItem_Edit,	url,'',_editBudgetItemCallBack,{width:660,height:300});
      
      }
      _posteditBudgetItem();
}
function _editBudgetItemCallBack()
{
}

    
/**
  * 预算项分组行项目查看操作
  */
function _viewBudgetItem(id,url)
{
	if(_previewBudgetItem()){
		url = contextPath+"/"+url + "&";
		//取得行数据，转发到查看页面
		var records = BudgetItem_grid.getSelectionModel().getSelections();     
		url = url + commonUrlEncode(records[0].data);
		_getMainFrame().ExtModalWindowUtil.show(Txt.budgetItem_View,	url,'','',{width:660,height:300});
	}
	_postviewBudgetItem();
}
/**
  * 预算项分组行项目查看，打开到页签 ,回调函数
  */
function _viewBudgetItempCallBack()
{
}

/**
  * 预算项分组行项目查看，弹出窗口 ,回调函数
  */
function _viewBudgetItemmCallBack()
{
}

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
  

          
/**
 * 保存 
 */
function _saveOrUpdateBudgetItemGroup()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("BudgetItemGroup.budgroupid").value = id;	
}
          

/**
 * 取消
 */
function _cancelBudgetItemGroup()
{
  if(_precancelBudgetItemGroup()){
	new AjaxEngine(contextPath + '/XDSS/budget/maindata/BudgetItemGroup/budgetItemGroupController.spr?action=_cancel&budgroupid='+budgroupid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelBudgetItemGroupCallBack});
   }
   _postcancelBudgetItemGroup();
}

function _cancelBudgetItemGroupCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
          
          
