/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月14日 11点04分14秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象供应商单清帐(SupplierSinglClear)管理页面JS文件
 */

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
 * grid 上的超级链接, 删除按钮调用方法
 * 删除供应商单清帐
 */
function _deleteSupplierSinglClear(id,url)
{
  if(_predeleteSupplierSinglClear(id,url)){
	    url = url + '&suppliersclearid=' + id;
		_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
				msg: Txt.supplierSinglClear_Delete_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
				   if (result == 'yes'){
						var param = '';
						new AjaxEngine(contextPath + "/" + url,{method:"post", parameters: [], onComplete: callBackHandle});
					}
				}
		});
	}
	_postdeleteSupplierSinglClear();
}



/**
 * grid 上的 创建按钮调用方法 
 * 新增供应商单清帐
 */
function _create()
{
	if(_precreate()){
	   var para = "";
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.supplierSinglClear_Create,SupplierSinglClear_grid,contextPath + '/xdss3/singleClear/supplierSinglClearController.spr?action=_create'+ para);
    }
    _postcreate();
}
