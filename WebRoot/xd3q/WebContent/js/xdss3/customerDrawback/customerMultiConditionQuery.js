/**
 * 创建作者：陈非
 * 创建时间：2011-01-21
 */

Ext.onReady(function(){
	_search();
});

/**
 * 查询
 */
function _search(){
	var para = Form.serialize('mainForm');
	reload_CustomerRefund_grid_custom(para);
}

//向外暴露重新加载grid方法，支持查询组件参数方式
function reload_CustomerRefund_grid_custom(urlParmeters)
{
	var paraUrl = encodeURI( contextPath + "/xdss3/customerDrawback/customerRefundmentController.spr?action=queryGrid&"+urlParmeters);

	CustomerRefund_store.proxy = new Ext.data.HttpProxy({url:paraUrl});

	CustomerRefund_store.load({params:{start:0, limit:10},arg:[]});
	CustomerRefund_grid.getStore().commitChanges();
}

/**
 * 清空
 */
function _clear()
{
	document.all.mainForm.reset();
}

function _viewVoucher()
{
	if (CustomerRefund_grid.selModel.hasSelection() == 1){
		var records = CustomerRefund_grid.selModel.getSelections();
		var id = records[0].json.refundmentid;
		_getMainFrame().maintabs.addPanel("查看凭证",null,contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid='+ id);
	}
}
