/**
  * Author(s):java业务平台代码生成工具
  * Date: 2011年05月19日 16点48分20秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象打包贷款(PackingLoan)管理页面JS用户可编程扩展文件
 */


/**
 * 查询动作执行前
 */
function _presearch()
{
	return true;
}

/**
 * 查询动作执行后
 * 当 _presearch() 返回 false 时候则执行本函数。
 */
function _postsearch()
{

}


/**
 * 清空操作
 */
function _preresetForm()
{
	return true;
}
/**
 * 清空操作
 */
function _postresetForm()
{

}


/**
 * grid 上的 创建按钮调用方法 
 * 新增打包贷款
 */
function _precreate()
{
	 return true ;
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增打包贷款
 */
function _postcreate()
{

}

/**
 * grid上的 复制创建按钮调用方法
 */
function _precopyCreate()
{
	return true ;
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _postcopyCreate()
{

}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除打包贷款
 */
function _predeletes()
{
   return true ;
}
/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除打包贷款
 */
function _postdeletes()
{

}
/**
 * 查看凭证
 * */
function _viewVoucher()
{
	if (PackingLoan_grid.selModel.hasSelection() == 1){
		var records = PackingLoan_grid.selModel.getSelections();
		var id = records[0].json.packingid;
		_getMainFrame().maintabs.addPanel("查看凭证",null,contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid='+ id);
	}
}
