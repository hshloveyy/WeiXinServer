/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年06月30日 11点22分36秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象供应商退款(SupplierRefundment)管理页面JS用户可编程扩展文件
 */

/**
 * 查看凭证
 */
function _viewVoucher(){
	if(SupplierRefundment_grid.selModel.hasSelection() == 1){
		var records = SupplierRefundment_grid.selModel.getSelections();
		var id = records[0].json.refundmentid;
		_getMainFrame().maintabs.addPanel("查看凭证",null,contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid='+ id);
	}
}

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
 * 新增供应商退款
 */
function _precreate()
{
	 return true ;
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增供应商退款
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
 * 批量删除供应商退款
 */
function _predeletes()
{
   return true ;
}
/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除供应商退款
 */
function _postdeletes()
{

}
