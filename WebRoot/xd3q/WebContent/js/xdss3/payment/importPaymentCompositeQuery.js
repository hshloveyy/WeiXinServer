
Ext.onReady(function(){
	
	var store = dict_div_paymenttype_dict.getStore();
	store.removeAll(false);
	
	var TopicRecord = Ext.data.Record.create([{name: 'id',mapping:'text'}]); 
	var myNewRecord1 = new TopicRecord({id: '',text: '请选择'});
	var myNewRecord2 = new TopicRecord({id: '01',text: 'TT'});
	var myNewRecord3 = new TopicRecord({id: '02',text: 'DP'});
	var myNewRecord4 = new TopicRecord({id: '03',text: '即期信用证'});
	var myNewRecord5 = new TopicRecord({id: '04',text: '远期信用证'});
	var myNewRecord6 = new TopicRecord({id: '05',text: 'DA'});
	var myNewRecord7 = new TopicRecord({id: '14',text: '预付款'});
	
	store.add(myNewRecord1);
	store.add(myNewRecord2);
	store.add(myNewRecord3);
	store.add(myNewRecord4);
	store.add(myNewRecord5);
	store.add(myNewRecord6);
	store.add(myNewRecord7);
	
	_search(); //自动查询
});


/**
 * 查询
 */
function _search()
{
	var para = Form.serialize('mainForm');
	reload_ImportPayment_grid_custom(para);
}
function _export(){
    var urlParmeters = Form.serialize('mainForm');
    var paraUrl = encodeURI(encodeURI( contextPath +"/xdss3/payment/importPaymentController.spr?action=_export&boName=ImportPayment&defaultCondition=YPAYMENT.TRADE_TYPE = '01' AND YPAYMENT.PAYMENTTYPE='06'&editable=false&needAuthentication=true&orderSql=YPAYMENT.LASTMODIFYTIME desc&groupBySql=null&authSqlClass=com.infolion.xdss3.CommonDataAuthSql&distinctSupport=false")+"&"+urlParmeters);

	window.location.href(paraUrl);
}

//向外暴露重新加载grid方法，支持查询组件参数方式
function reload_ImportPayment_grid_custom(urlParmeters)
{
	var paraUrl = encodeURI(encodeURI( contextPath +"/xdss3/payment/importPaymentController.spr?action=queryGrid&boName=ImportPayment&defaultCondition=YPAYMENT.TRADE_TYPE = '01' AND YPAYMENT.PAYMENTTYPE='06'&editable=false&needAuthentication=true&orderSql=YPAYMENT.LASTMODIFYTIME desc&groupBySql=null&authSqlClass=com.infolion.xdss3.CommonDataAuthSql&distinctSupport=false")+"&"+urlParmeters);

	div_southForm_store.proxy = new Ext.data.HttpProxy({url:paraUrl});

	div_southForm_store.load({params:{start:0, limit:10},arg:[]});
}

/**
 * 清空操作
 */
function _resetForm()
{
	document.all.mainForm.reset();
}


function _viewVoucher()
{
	if (div_southForm_grid.selModel.hasSelection() == 1){
		var records = div_southForm_grid.selModel.getSelections();
		var id = records[0].json.paymentid;
		_getMainFrame().maintabs.addPanel("查看凭证",null,contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid='+ id);
	}
}
 