/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年05月27日 19点36分16秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象进口付款(ImportPayment)管理页面JS用户可编程扩展文件
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
 * 新增进口付款
 */
function _precreate()
{
	var para = "&paymenttype=" + strPaymentType;
	if(strPaymentType == '16'){
		Txt.importPayment_Create = '远期国内信用证创建';
	}
	if(strPaymentType == '15'){
		Txt.importPayment_Create ='即期国内信用证创建';
	}
	//增加页签
	_getMainFrame().maintabs.addPanel(Txt.importPayment_Create,ImportPayment_grid,contextPath + '/xdss3/payment/importPaymentController.spr?action=_create'+ para);

	return false ;
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增进口付款
 */
function _postcreate()
{

}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除进口付款
 */
function _predeletes()
{
   return true ;
}
/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除进口付款
 */
function _postdeletes()
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

function _viewVoucher()
{
	if (ImportPayment_grid.selModel.hasSelection() == 1){
		var records = ImportPayment_grid.selModel.getSelections();
		var id = records[0].json.paymentid;
		_getMainFrame().maintabs.addPanel("查看凭证",null,contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid='+ id);
	}
}
 