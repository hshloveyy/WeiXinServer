/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年03月05日 13点59分05秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象预算组织(BudgetOrganization)管理页面JS文件
 */


/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
	reload_BudgetOrganization_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_BudgetOrganization_grid("");
}

/**
 * 清空操作
 */
function _resetForm()
{
  if(_preresetForm()){
		document.all.mainForm.reset();
	}
	_postresetForm();
}


/**
 * grid 上的 创建按钮调用方法 
 * 新增预算组织
 */
function _create()
{
	if(_precreate()){
	   var para = "";
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.budgetOrganization_Create,BudgetOrganization_grid,contextPath + '/XDSS/budget/maindata/BudgetOrganization/budgetOrganizationController.spr?action=_create'+ para);
    }
    _postcreate();
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _copyCreate()
{
if(_precopyCreate()){
	if (BudgetOrganization_grid.selModel.hasSelection() > 0){
		var records = BudgetOrganization_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo(Txt.budgetOrganization_CopyCreate_AllowOnlyOneItemForOperation);
			return;
		}
		var budorgid = records[0].json.budorgid;
		_getMainFrame().maintabs.addPanel(Txt.budgetOrganization_CopyCreate,BudgetOrganization_grid,contextPath + '/XDSS/budget/maindata/BudgetOrganization/budgetOrganizationController.spr?action=_copyCreate&budorgid='+budorgid);
	}else{
		_getMainFrame().showInfo(Txt.budgetOrganization_CopyCreate_SelectToOperation);
	}	
  }
  _postcopyCreate();
}
