

Ext.onReady(function(){
	
	
  
});

/**
 * 查询
 */
function _search(){	
	
		var para = Form.serialize('mainForm');
		//Ext.getCmp('_search').setDisabled(true);
	//	reload_UnclearCustomer_grid(para);
	reload_UnclearCustomer_grid2(para);
}

/**
 * 清空
 */
function _clear(){
	document.all.mainForm.reset();	
}


function reload_UnclearCustomer_grid2(urlParmeters) {
	var paraUrl = encodeURI(context + "/unclearCustomerQueryController.spr?action=queryGrid&"+urlParmeters);
	UnclearCustomer_store.proxy = new Ext.data.HttpProxy( {
		url : paraUrl
	});
	UnclearCustomer_store.load( {
		params : {
			start : 0,
			limit : 10
		},
		arg : []
	});
	UnclearCustomer_grid.getStore().commitChanges();
}