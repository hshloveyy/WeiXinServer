/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年07月02日 09点27分05秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象客户退款(CustomerRefundment)管理页面JS文件
 */

/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
	reload_CustomerRefundment_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_CustomerRefundment_grid("");
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
 * 新增客户退款
 */
function _create()
{
	if(_precreate()){
	   var para = "";
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.customerRefundment_Create,CustomerRefundment_grid,contextPath + '/xdss3/customerDrawback/customerRefundmentController.spr?action=_create'+ para);
    }
    _postcreate();
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _copyCreate()
{
if(_precopyCreate()){
	if (CustomerRefundment_grid.selModel.hasSelection() > 0){
		var records = CustomerRefundment_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo(Txt.customerRefundment_CopyCreate_AllowOnlyOneItemForOperation);
			return;
		}
		var refundmentid = records[0].json.refundmentid;
		_getMainFrame().maintabs.addPanel(Txt.customerRefundment_CopyCreate,CustomerRefundment_grid,contextPath + '/xdss3/customerDrawback/customerRefundmentController.spr?action=_copyCreate&refundmentid='+refundmentid);
	}else{
		_getMainFrame().showInfo(Txt.customerRefundment_CopyCreate_SelectToOperation);
	}	
  }
  _postcopyCreate();
}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除客户退款
 */
function _deletes()
{
if(_precreate()){
	if (CustomerRefundment_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
				msg: Txt.customerRefundment_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
					if (result == 'yes'){
						var records = CustomerRefundment_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.refundmentid + '|';
						}
		
						var param = '&customerRefundmentIds='+ids;
						new AjaxEngine(contextPath + '/xdss3/customerDrawback/customerRefundmentController.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});
	}else{
		_getMainFrame().showInfo(Txt.customerRefundment_Deletes_SelectToOperation);
	}
   }
  _postcreate();
}
