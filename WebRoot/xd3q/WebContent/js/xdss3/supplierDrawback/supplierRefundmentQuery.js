
Ext.onReady(function(){
	_search();
});

/**
 * 查询
 */
function _search(){
	var para = Form.serialize('mainForm');
	reload_div_southForm_grid_custom(para);
}

//向外暴露重新加载grid方法，支持查询组件参数方式
function reload_div_southForm_grid_custom(urlParmeters)
{
	var paraUrl = encodeURI( contextPath + "/xdss3/supplierDrawback/supplierRefundmentController.spr?action=queryGrid&"+urlParmeters);

	div_southForm_store.proxy = new Ext.data.HttpProxy({url:paraUrl});

	div_southForm_store.load({params:{start:0, limit:10},arg:[]});
	div_southForm_grid.getStore().commitChanges();
}

/**
 * 清空
 */
function _resetForm()
{
	document.all.mainForm.reset();
}

function _viewVoucher()
{
	if (div_southForm_grid.selModel.hasSelection() == 1){
		var records = div_southForm_grid.selModel.getSelections();
		var id = records[0].json.refundmentid;
		_getMainFrame().maintabs.addPanel("查看凭证",null,contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid='+ id);
	}
}
