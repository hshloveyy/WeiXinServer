/**
 * 创建作者：邱杰烜
 * 创建时间：2010-11-04
 */

Ext.onReady(function(){
	/*
	 * 将结清标识由数字渲染成中文
	 */
	var index1 = FundBill_grid.getColumnModel().findColumnIndex('iscleared');	// 结清标识
	var index2 = FundBill_grid.getColumnModel().findColumnIndex('isclear');		// 票清款结标识
	FundBill_grid.getColumnModel().setRenderer(index1,function(value){		// 设置结清标识
		if(value=='1'){
			return '外围已清';
		}else if(value=='2'){
			return 'SAP已清';
		}else{
			return '未清(在批)';
		}
	});
	FundBill_grid.getColumnModel().setRenderer(index2,function(value){		// 设置票清款结标识
		if(value=='1'){
			return '外围已清';
		}else if(value=='2'){
			return 'SAP已清';
		}else{
			return '未清(在批)';
		}
	});
});

/**
 * 查询
 */
function _search(){
	var para = Form.serialize('mainForm');
	reload_FundBill_grid(para);
}

/**
 * 清空
 */
function _clear(){
	document.all.mainForm.reset();
}
