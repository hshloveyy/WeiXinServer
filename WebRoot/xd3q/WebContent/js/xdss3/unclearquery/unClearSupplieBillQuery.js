

Ext.onReady(function(){
	
	
  
});

/**
 * 查询
 */
function _search(){	
	
		var para = Form.serialize('mainForm');
	reload_UnClearSupplieBill_grid2(para);
}

/**
 * 清空
 */
function _clear(){
	document.all.mainForm.reset();	
}


function reload_UnClearSupplieBill_grid2(urlParmeters) {
	var paraUrl = encodeURI(context + "/xdss3/singleClear/unClearSupplieBillController.spr?action=queryGrid&"+urlParmeters);
	UnClearSupplieBill_store.proxy = new Ext.data.HttpProxy( {
		url : paraUrl
	});
	UnClearSupplieBill_store.load( {
		params : {
			start : 0,
			limit : 10000
		},
		arg : []
	});
	UnClearSupplieBill_grid.getStore().commitChanges();
}

function _viewSupplierSinglClear(value,metadata,record){
	
	var hrefStr = '<a href="#" style="color:green;" onclick="_commonMethodOperation(\'1\',\'供应商单清帐\',UnClearSupplieBill_grid,\'xdss3/singleClear/supplierSinglClearController.spr?action=_view&suppliersclearid='+value+'\',\'\',\'_view\',\'false\')">'+value+'</a>    ';
	return hrefStr;
	
}