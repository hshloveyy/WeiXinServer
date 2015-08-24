/**
 * Author(s):java业务平台代码生成工具 Date: 2010年07月09日 10点26分57秒 Copyright Notice:版权声明
 * 福建讯盟软件有限公司, 版权所有 违者必究
 * 
 * @(#) Description: <功能>主对象供应商单清帐(SupplierSinglClear)管理页面JS用户可编程扩展文件
 */


/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
//	para +="&businessstate.fieldName=YSUPPLIERSICLEAR.BUSINESSSTATE";
//	para +="&businessstate.dataType=S";
	
	var businessstate = document.getElementById("businessstate").value;
	/**
	if(businessstate == '0'){
		para +="&businessstate.option=notequal";
		para +="&businessstate.fieldValue=4";
	
	}
	if(businessstate == '1'){
		para +="&businessstate.option=equal";
		para +="&businessstate.fieldValue=4";
		
	}
	
	**/
	reload_SupplierSinglClear_grid(para);
	}
	_postsearch();
}


/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	var para = Form.serialize('mainForm');
	para +="&businessstate.fieldName=YSUPPLIERSICLEAR.BUSINESSSTATE";
	para +="&businessstate.dataType=S";
	para +="&businessstate.option=notequal";
	para +="&businessstate.fieldValue=4";	
	reload_SupplierSinglClear_grid(para);
}
/**
 * 查询动作执行前
 */
function _presearch()
{
	return true;
}

/**
 * 查询动作执行后 当 _presearch() 返回 false 时候则执行本函数。
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
 * grid 上的 创建按钮调用方法 新增供应商单清帐
 */
function _precreate()
{
	return true;
}

/**
 * grid 上的 创建按钮调用方法 新增供应商单清帐
 */
function _postcreate()
{
	
}

/**
 * grid 上的 删除按钮调用方法，批量删除 批量删除供应商单清帐
 */
function _predeletes()
{
	return true;
}
/**
 * grid 上的 删除按钮调用方法，批量删除 批量删除供应商单清帐
 */
function _postdeletes()
{
	
}
function _edit(id,url){
	var records = SupplierSinglClear_grid.getSelectionModel().getSelections();        			
	for(i=0;i<records.size();i++)
    {
    	var suppliersclearid = records[i].get('suppliersclearid');    	
		var businessstate = records[i].get('businessstate');
		if(suppliersclearid == id && (businessstate=='4' || businessstate=='-1')){
			_getMainFrame().showInfo("已经确定清账过，不能编辑，只能查看！ ");
			return false;
		}
		
    }
	url = url +"&suppliersclearid=" + id;
	_getMainFrame().maintabs.addPanel('供应商单清账编辑', '', contextPath + "/" + url);
}
/**
 * 删除
 * 
 * @return {Boolean}
 */
function _predeleteSupplierSinglClear(id,url)
{
	var records = SupplierSinglClear_grid.getSelectionModel().getSelections();        			
	for(i=0;i<records.size();i++)
    {
    	var suppliersclearid = records[i].get('suppliersclearid');    	
		var businessstate = records[i].get('businessstate');
		if(suppliersclearid == id && (businessstate=='4' || businessstate=='-1')){
			_getMainFrame().showInfo("已经确定清账过，不能删除，只能作废！ ");
			return false;
		}
		
    }
	return true;
}

/**
 * 删除
 * 
 * @param {}
 *            id
 * @param {}
 *            url
 */
function _postdeleteSupplierSinglClear()
{

}
