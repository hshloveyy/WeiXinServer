/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年03月05日 10点27分07秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象预算模版(BudgetTemplate)编辑页面用户可编程扩展JS文件
 */
 
          
   
/**
 * grid 上的 创建按钮调用方法 
 * 新增预算模版,模版预算项
 */
function _precreateBudgetTemplateItem()
{
	return true ;
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增预算模版,模版预算项
 */
function _postcreateBudgetTemplateItem()
{

}
   
    

    
/**
*预算模版行项目
*复制创建
*/
function _precopyCreateBudgetTemplateItem()
{
	return true ;
}

/**
*预算模版行项目
*复制创建
*/
function _postcopyCreateBudgetTemplateItem()
{

}
    

    
  

    
/**
  * 预算模版行项目编辑操作
  */
function _preeditBudgetTemplateItem(id,url)
{
	return true ;
}

/**
  * 预算模版行项目编辑操作
  */
function _posteditBudgetTemplateItem(id,url)
{

}

    

/**
  * 预算模版行项目查看操作
  */
function _previewBudgetTemplateItem(id,url)
{
	return  true ;
}

/**
  * 预算模版行项目查看操作
  */
function _postviewBudgetTemplateItem(id,url)
{

}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
  

          
/**
 * 保存 
 */
function _presaveOrUpdateBudgetTemplate()
{
	var breakflag = 'N';
	for (var i = 0; i <BudgetTemplateItem_grid.getStore().getCount(); i++) {
		var recordFirst = BudgetTemplateItem_grid.getStore().getAt(i);
		
		for (var j=i+1;j< BudgetTemplateItem_grid.getStore().getCount();j++){
			var recordSecond = BudgetTemplateItem_grid.getStore().getAt(j);
			
			if (recordFirst.get('seq') == recordSecond.get('seq')){
				breakflag = 'Y';
				_getMainFrame().showInfo('有相同的序号存在！');
				break;
			}

			if (recordFirst.get('buditemid') == recordSecond.get('buditemid')){
				breakflag = 'Y';
				_getMainFrame().showInfo('有相同的预算项存在！');
				break;
			}
		}
		
		if (breakflag == 'Y'){
			break;
		}
	}
	
	if (breakflag == 'Y')
		return false ;
	else
		return true ;
	return true ;
}

/**
 * 保存 
 */
function _postsaveOrUpdateBudgetTemplate()
{

}
          
/**
 * 取消
 */
function _precancelBudgetTemplate()
{
	return true ;
}

/**
 * 取消
 */
function _postcancelBudgetTemplate()
{

}
