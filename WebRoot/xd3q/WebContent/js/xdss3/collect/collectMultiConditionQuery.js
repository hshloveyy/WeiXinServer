/**
 * @创建作者：邱杰烜
 * @创建日期：2010-11-15
 */
Ext.onReady(function(){
	_search();
//	var stateIndex = Collect_grid.getColumnModel().findColumnIndex('businessstate');
//	Collect_grid.getColumnModel().setRenderer(stateIndex,function(state){
//		if(state.trim()==''){
//			return '新增';
//		}
//	});
});

/**
 * 查询
 */
function _search(){
	var para = Form.serialize('mainForm');
	reload_Collect_grid2(para);
}

/**
 * 清空
 */
function _clear(){
	document.all.mainForm.reset();
}

function _viewVoucher(){
	if(Collect_grid.selModel.hasSelection() == 1){
		var records = Collect_grid.selModel.getSelections();
		var id = records[0].json.collectid;
		_getMainFrame().maintabs.addPanel("查看凭证",null,contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid='+ id);
	}
}
function _export(){
    var urlParmeters = Form.serialize('mainForm');
    var paraUrl = encodeURI(context + "/xdss3/collect/collectController.spr?action=_export&"+urlParmeters);
	window.location.href(paraUrl);
}
function reload_Collect_grid2(urlParmeters) {
	var paraUrl = encodeURI(context + "/xdss3/collect/collectController.spr?action=queryGrid&"+urlParmeters);
	Collect_store.proxy = new Ext.data.HttpProxy( {
		url : paraUrl
	});
	Collect_store.load( {
		params : {
			start : 0,
			limit : 10
		},
		arg : []
	});
	Collect_grid.getStore().commitChanges();
}