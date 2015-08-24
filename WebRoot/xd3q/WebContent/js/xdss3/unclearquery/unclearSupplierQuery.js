

Ext.onReady(function(){
	
	
  
});

/**
 * 查询
 */
function _search(){	
	
		var para = Form.serialize('mainForm');
		//Ext.getCmp('_search').setDisabled(true);
	//	reload_UnclearCustomer_grid(para);
	reload_UnclearSupplier_grid2(para);
}

/**
 * 清空
 */
function _clear(){
	document.all.mainForm.reset();	
}


function reload_UnclearSupplier_grid2(urlParmeters) {
	var paraUrl = encodeURI(context + "/unclearSupplierQueryController.spr?action=queryGrid&"+urlParmeters);
	UnclearSupplier_store.proxy = new Ext.data.HttpProxy( {
		url : paraUrl
	});
	UnclearSupplier_store.load( {
		params : {
			start : 0,
			limit : 10
		},
		arg : []
	});
	UnclearSupplier_grid.getStore().commitChanges();
}