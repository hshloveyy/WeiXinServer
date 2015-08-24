/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年03月05日 10点28分25秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象预算模版(BudgetTemplate)管理页面JS文件
 */


/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
	reload_BudgetTemplate_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
/*function customCallBackHandle()
{
	reload_BudgetTemplate_grid("");
}*/

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
 * 新增预算模版
 */
function _create()
{
	if(_precreate()){
	   var para = "";
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.budgetTemplate_Create,BudgetTemplate_grid,contextPath + '/XDSS/budget/maindata/BudgetTemplate/budgetTemplateController.spr?action=_create'+ para);
    }
    _postcreate();
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _copyCreate()
{
if(_precopyCreate()){
	if (BudgetTemplate_grid.selModel.hasSelection() > 0){
		var records = BudgetTemplate_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo(Txt.budgetTemplate_CopyCreate_AllowOnlyOneItemForOperation);
			return;
		}
		var budtemid = records[0].json.budtemid;

		_getMainFrame().maintabs.addPanel(Txt.budgetTemplate_CopyCreate,BudgetTemplate_grid,contextPath + '/XDSS/budget/maindata/BudgetTemplate/budgetTemplateController.spr?action=_copyCreate&budtemid='+budtemid);
	}else{
		_getMainFrame().showInfo(Txt.budgetTemplate_CopyCreate_SelectToOperation);
	}	
  }
  _postcopyCreate();
}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除预算模版
 */
function _deletes()
{
if(_precreate()){
	if (BudgetTemplate_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
				msg: Txt.budgetTemplate_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
					if (result == 'yes'){
						var records = BudgetTemplate_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.budtemid + '|';
						}
		
						var param = '&budgetTemplateIds='+ids;
						new AjaxEngine(contextPath + '/XDSS/budget/maindata/BudgetTemplate/budgetTemplateController.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});
	}else{
		_getMainFrame().showInfo(Txt.budgetTemplate_Deletes_SelectToOperation);
	}
   }
  _postcreate();
}
