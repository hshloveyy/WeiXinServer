/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年03月04日 13点22分32秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象预算分类(BudgetSort)查看页面JS文件
 */
          
   
    
  

          
function _copyCreateBudgetSort()
{
	if(_precopyCreateBudgetSort()){
		_getMainFrame().maintabs.addPanel(Txt.budgetSort_CopyCreate,'',contextPath + '/XDSS/budget/maindata/BudgetSort/budgetSortController.spr?action=_copyCreate&budsortid='+budsortid);
	}
	_postcopyCreateBudgetSort();
}

          

/**
 * 删除预算分类
 */
function _deleteBudgetSort()
{
if(_predeleteBudgetSort()){
		_getMainFrame().Ext.MessageBox.show({
			title:Txt.cue,
		    msg: Txt.budgetSort_Delete_ConfirmOperation,
			buttons: {yes:Txt.ok, no:Txt.cancel},
			icon: Ext.MessageBox.QUESTION,
			fn:function(result){
			   if (result == 'yes'){
					var param = '&budsortid='+budsortid;
					new AjaxEngine(contextPath + '/XDSS/budget/maindata/BudgetSort/budgetSortController.spr?action=_delete', 
					{method:"post", parameters: param, onComplete: callBackHandle,callback:_deleteCallBackHandle});
				}
			}
		});
	 }
 	 _postdeleteBudgetSort();
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
function _createBudgetSort()
{
	if(_precreateBudgetSort()){
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.budgetSort_Create,'',contextPath + '/XDSS/budget/maindata/BudgetSort/budgetSortController.spr?action=_create');
	}
	_postcreateBudgetSort();
}
          
/**
 * 保存 
 */
function _saveOrUpdateBudgetSort()
{

}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var id = responseUtil.getCustomField('coustom');
	document.getElementById("BudgetSort.budsortid").value = id;	
}
          

/**
 * 取消
 */
function _cancelBudgetSort()
{
  if(_precancelBudgetSort()){
	new AjaxEngine(contextPath + '/XDSS/budget/maindata/BudgetSort/budgetSortController.spr?action=_cancel&budsortid='+budsortid, 
			   {method:"post", parameters: '', onComplete: callBackHandle,callback:_cancelBudgetSortCallBack});
   }
   _postcancelBudgetSort();
}

function _cancelBudgetSortCallBack()
{
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}
          
