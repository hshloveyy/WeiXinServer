

Ext.onReady(function(){
	
	
  
});

/**
 * 查询
 */
function _search(){	
	
		var para = Form.serialize('mainForm');
	reload_UnClearCustomBill_grid2(para);
}

/**
 * 清空
 */
function _clear(){
	document.all.mainForm.reset();	
}


function reload_UnClearCustomBill_grid2(urlParmeters) {
	var paraUrl = encodeURI(context + "/xdss3/singleClear/unClearCustomBillController.spr?action=queryGrid&"+urlParmeters);
	UnClearCustomBill_store.proxy = new Ext.data.HttpProxy( {
		url : paraUrl
	});
	UnClearCustomBill_store.load( {
		params : {
			start : 0,
			limit : 10000
		},
		arg : []
	});
	UnClearCustomBill_grid.getStore().commitChanges();
}

function _viewCustomSingleClear(value,metadata,record){
	
	var hrefStr = '<a href="#" style="color:green;" onclick="_commonMethodOperation(\'1\',\'客户单清帐\',UnClearCustomBill_grid,\'xdss3/singleClear/customSingleClearController.spr?action=_view&customsclearid='+value+'\',\'\',\'_view\',\'false\')">'+value+'</a>    ';
	return hrefStr;
	
}
