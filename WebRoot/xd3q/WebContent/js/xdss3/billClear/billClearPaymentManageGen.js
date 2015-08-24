/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年09月17日 08点27分42秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象票清付款(BillClearPayment)管理页面JS文件
 */

/**
 * 查询
 */
function _search()
{
if(_presearch()){
	var para = Form.serialize('mainForm');
	reload_BillClearPayment_grid(para);
	}
	_postsearch();
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_BillClearPayment_grid("");
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
 * 新增票清付款
 */
function _create()
{
	if(_precreate()){
	   var para = "";
	   //开始取得从_manage网址上带过来的参数，并传递给控制器_create方法。
	   para = para + "&cleartype=" + cleartype;
		//增加页签
		_getMainFrame().maintabs.addPanel(Txt.billClearPayment_Create,BillClearPayment_grid,contextPath + '/xdss3/billClear/billClearPaymentController.spr?action=_create'+ para);
    }
    _postcreate();
}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除票清付款
 */
function _deletes()
{
if(_precreate()){
	if (BillClearPayment_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:Txt.cue,
				msg: Txt.billClearPayment_Deletes_ConfirmOperation,
				buttons: {yes:Txt.ok, no:Txt.cancel},
				icon: Ext.MessageBox.QUESTION,
				fn:function(result){
					if (result == 'yes'){
						var records = BillClearPayment_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.billclearid + '|';
						}
		
						var param = '&billClearPaymentIds='+ids;
						new AjaxEngine(contextPath + '/xdss3/billClear/billClearPaymentController.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});
	}else{
		_getMainFrame().showInfo(Txt.billClearPayment_Deletes_SelectToOperation);
	}
   }
  _postcreate();
}
