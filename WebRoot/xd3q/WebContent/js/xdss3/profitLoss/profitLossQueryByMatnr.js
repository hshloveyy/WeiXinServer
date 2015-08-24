
Ext.onReady(function(){
	//_search();
});

/**
 * 查询
 */
function _search(){
	var para = Form.serialize('mainForm');
	
	var companyid = div_companyid_sh_sh.getValue();
	var datevalue = document.getElementById('datevalue.fieldValue').value;
	if (datevalue == ""){
		_getMainFrame().showInfo('请输入查询日期！');
		return false;	
	}
	if (companyid=="") {
		_getMainFrame().showInfo('请输入公司！');
		return false;	
	}
	Ext.getCmp('_search').setDisabled(true);
	reload_div_southForm_grid_custom(para);
	return true;
}

//向外暴露重新加载grid方法，支持查询组件参数方式
function reload_div_southForm_grid_custom(urlParmeters)
{
	var paraUrl = encodeURI( contextPath + "/xdss3/profitLoss/profitLossController.spr?action=queryMatnrGrid&"+urlParmeters);

//	div_southForm_store.proxy = new Ext.data.HttpProxy({url:paraUrl});
//
//	div_southForm_store.load({params:{start:0, limit:10},arg:[]});
//	div_southForm_grid.getStore().commitChanges();
	
	
	var conn=new Ext.data.Connection({
	    url: paraUrl,
	    timeout : 600000, //自定义超时时间，这里是600秒 (默认30s)
	    autoAbort : false,
	    disableCaching : true ,
	    method : "POST"
	});
	div_southForm_store.proxy = new Ext.data.HttpProxy(conn);
	div_southForm_store.load( {
		params : {
			start : 0,
			limit : 10000
		},
		callback : function(xhr){
			Ext.getCmp('_search').setDisabled(false);
		},
		scope:this,
		arg : []
	});
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
		var id = records[0].json.billpurid;
		_getMainFrame().maintabs.addPanel("查看凭证",null,contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid='+ id);
	}
}


/**
 * 导出excel
 */
function _expExcel()
{
	if (!_search()){
		return false;
	}
	var para = Form.serialize('mainForm');
	para = para + '&detailName=matnr';
	para = encodeURI(para);
	window.location.href(contextPath+'/xdss3/profitLoss/profitLossController.spr?action=detailToExcel_matnr&'+para);
}
