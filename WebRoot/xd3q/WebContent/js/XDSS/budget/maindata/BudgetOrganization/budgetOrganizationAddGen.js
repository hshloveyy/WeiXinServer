/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年03月05日 13点59分05秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象预算组织(BudgetOrganization)增加页面JS文件
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
		,budclassid:''
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
		record.set('budclassid',json["BudgetOrgTemp.budclassid"]);
		record.set('budclassid_text',json["BudgetOrgTemp.budclassid_htext"]);
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
	var json = _getMainFrame().maintabs.getActiveTab().getReturnValue();
	_modifyBudgetOrgTempRow(json);
}


function _editBudgetOrgTempmCallBack()
{
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_modifyBudgetOrgTempRow(json);
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
	var json = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	_modifyBudgetOrgTempRow(json);
}

/**
 * 行项目编辑后的grid更新
 */
function _modifyBudgetOrgTempRow(json)
{
	if(json){
		var records = BudgetOrgTemp_grid.selModel.getSelections();
		var record = records[0];
		record.set('budorgid',json["BudgetOrgTemp.budorgid"]);
		record.set('budclassid',json["BudgetOrgTemp.budclassid"]);
		record.set('weastartdate',json["BudgetOrgTemp.weastartdate"]);
		record.set('weaentdate',json["BudgetOrgTemp.weaentdate"]);
		record.set('creator',json["BudgetOrgTemp.creator"]);
		record.set('createtime',json["BudgetOrgTemp.createtime"]);
		record.set('lastmodifyer',json["BudgetOrgTemp.lastmodifyer"]);
		record.set('lastmodifytime',json["BudgetOrgTemp.lastmodifytime"]);
	}
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

    
  
          
          
          
          
/**
 * 保存 
 */
function _save()
{
	//if(isCreateCopy){
	//	document.getElementById("BudgetOrganization.budorgid").value = "";
	//}
	if(_presave()){
		var param = Form.serialize('mainForm');	
		           
			        	         
		if(isCreateCopy)
		{
			param = param + ""+ getAllBudgetOrgTempGridData();
		}
		else
		{
			param = param + ""+ getBudgetOrgTempGridData(); 
		}
			        		    new AjaxEngine(contextPath + '/XDSS/budget/maindata/BudgetOrganization/budgetOrganizationController.spr?action=_saveOrUpdate', 
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
	var budorgid=result.budorgid;
	document.getElementById("BudgetOrganization.budorgid").value = budorgid;
	document.getElementById("BudgetOrganization.creator_text").value = result.creator_text;
	document.getElementById("BudgetOrganization.creator").value = result.creator;
	document.getElementById("BudgetOrganization.createtime").value = result.createtime;
	document.getElementById("BudgetOrganization.lastmodifyer_text").value = result.lastmodifyer_text;
	document.getElementById("BudgetOrganization.lastmodifyer").value = result.lastmodifyer;
	document.getElementById("BudgetOrganization.lastmodifytime").value = result.lastmodifytime;
	isCreateCopy = false;	
	reload_BudgetOrgTemp_grid("defaultCondition=YBUDORGTEM.BUDORGID='"+ budorgid +"'");

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
