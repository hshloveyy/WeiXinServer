/**
  * Author(s):java业务平台代码生成工具
  * Date: 2012年06月08日 11点51分25秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象其他公司供应商单清帐(SupplierSinglOther)管理页面JS文件
 */

/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
	reload_SupplierSinglOther_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_SupplierSinglOther_grid("");
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
 * 新增其他公司供应商单清帐
 */
function _create()
{
	if(_precreate()){
	   var para = "";
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.supplierSinglOther_Create,SupplierSinglOther_grid,contextPath + '/xdss3/singleClearOther/supplierSinglOtherController.spr?action=_create'+ para);
    }
    _postcreate();
}
