
Ext.onReady(function(){
	
	var store = dict_div_paymenttype_dict.getStore();
	store.removeAll(false);
	
	var TopicRecord = Ext.data.Record.create([{name: 'id',mapping:'text'}]); 
	var myNewRecord1 = new TopicRecord({id: '',text: '请选择'});
	var myNewRecord2 = new TopicRecord({id: '06',text: '国内信用证'});
	var myNewRecord3 = new TopicRecord({id: '07',text: '银行/商业承兑汇票'});
	var myNewRecord4 = new TopicRecord({id: '08',text: '现金'});
	var myNewRecord5 = new TopicRecord({id: '09',text: '背书'});
	var myNewRecord6 = new TopicRecord({id: '10',text: '转账'});
	var myNewRecord7 = new TopicRecord({id: '11',text: '电汇'});
	var myNewRecord8 = new TopicRecord({id: '12',text: '网银'});
	var myNewRecord9 = new TopicRecord({id: '13',text: '银行汇票'});
	var myNewRecord10 = new TopicRecord({id: '15',text: '即期国内信用证'});
	var myNewRecord11 = new TopicRecord({id: '16',text: '远期国内信用证'});
	
	store.add(myNewRecord1);
	store.add(myNewRecord2);
	store.add(myNewRecord3);
	store.add(myNewRecord4);
	store.add(myNewRecord5);
	store.add(myNewRecord6);
	store.add(myNewRecord7);
	store.add(myNewRecord8);
	store.add(myNewRecord9);
	store.add(myNewRecord10);
	store.add(myNewRecord11);
	
	// 付款类型	
	var store = dict_div_pay_type_dict.getStore();
	store.removeAll(false);
	
	var TopicRecord = Ext.data.Record.create([{name: 'id',mapping:'text'}]); 
	var myNewRecord1 = new TopicRecord({id: '',text: '请选择'});
	var myNewRecord2 = new TopicRecord({id: '3',text: '一般付款'});
	
	store.add(myNewRecord1);
	store.add(myNewRecord2);
	
	_search(); //自动查询
});



/**
 * 查询
 */
function _search()
{
	var para = Form.serialize('mainForm');
	reload_HomePayment_grid_custom(para);
}


/** 
 * 向外暴露重新加载grid方法，支持查询组件参数方式 
 */
function reload_HomePayment_grid_custom(urlParmeters)
{
	var paraUrl = encodeURI(encodeURI( contextPath +"/xdss3/payment/homePaymentController.spr?action=queryGrid&boName=HomePayment&defaultCondition=YPAYMENT.TRADE_TYPE = '01' AND YPAYMENT.PAYMENTTYPE='06'&editable=false&needAuthentication=true&orderSql=YPAYMENT.LASTMODIFYTIME desc&groupBySql=null&authSqlClass=com.infolion.xdss3.CommonDataAuthSql&distinctSupport=false")+"&"+urlParmeters);

	div_southForm_store.proxy = new Ext.data.HttpProxy({url:paraUrl});

	div_southForm_store.load({params:{start:0, limit:10},arg:[]});
	
}
/** 
 * 导出
 */
function _export()
{
	var urlParmeters = Form.serialize('mainForm');
	var paraUrl = encodeURI(encodeURI( contextPath +"/xdss3/payment/homePaymentController.spr?action=_export&boName=HomePayment&defaultCondition=YPAYMENT.TRADE_TYPE = '01' AND YPAYMENT.PAYMENTTYPE='06'&editable=false&needAuthentication=true&orderSql=YPAYMENT.LASTMODIFYTIME desc&groupBySql=null&authSqlClass=com.infolion.xdss3.CommonDataAuthSql&distinctSupport=false")+"&"+urlParmeters);
	window.location.href(paraUrl);	
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
 