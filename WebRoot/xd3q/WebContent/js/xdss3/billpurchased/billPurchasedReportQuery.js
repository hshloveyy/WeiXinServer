
Ext.onReady(function(){
	//_search();
});

/**
 * 查询
 */
function _search(){
	var para = Form.serialize('mainForm');
	if ( $("issuing_date").value =='' ) {
		_getMainFrame().showInfo('请添加[起始日期]！');
			return false;
	}
	if ( div_bukrs_sh_sh.getValue() =='' ) {
		_getMainFrame().showInfo('请添加[公司]！');
			return false;
	}
	Ext.getCmp('_search').setDisabled(true);
	reload_div_southForm_grid_custom(para);
}

//向外暴露重新加载grid方法，支持查询组件参数方式
function reload_div_southForm_grid_custom(urlParmeters)
{
	var paraUrl = encodeURI( contextPath + "/xdss3/billpurchased/billPurchasedController.spr?action=reportQuery&"+urlParmeters);
	var conn=new Ext.data.Connection({
	    url: paraUrl,
	    timeout : 6000000, //自定义超时时间，这里是600秒 (默认30s)
	    autoAbort : false,
	    disableCaching : true ,
	    method : "POST"
	});
//	div_southForm_store.proxy = new Ext.data.HttpProxy({url:paraUrl});
//
//	div_southForm_store.load({params:{start:0, limit:10000},arg:[]});
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
		var id = records[0].json.BILLPURID;
		_getMainFrame().maintabs.addPanel("查看凭证",null,contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid='+ id);
	}
}

 /**
 * 导出excel
 */
function _expExcel()
{
	
	Ext.ux.Grid2Excel.Save2Excel(div_southForm_grid);
    var vExportContent = div_southForm_grid.getExcelXml();
    if (Ext.isIE6 || Ext.isIE7 || Ext.isSafari || Ext.isSafari2 || Ext.isSafari3) {
        var fd=Ext.get('frmDummy');
        if (!fd) {
            fd=Ext.DomHelper.append(Ext.getBody(),{tag:'form',method:'post',id:'frmDummy',action:contextPath+'/xdss3/profitLoss/profitLossController.spr?action=_expExcel', 
            	target:'_blank',name:'frmDummy',cls:'x-hidden',
           	cn:[{
           		tag:'input',name:'exportContent',id:'exportContent',type:'hidden'}
            	]},true);
        }
        fd.child('#exportContent').set({value:vExportContent});
        fd.dom.submit();
    } else {
        document.location = 'data:application/vnd.ms-excel;base64,'+Base64.encode(vExportContent);
    }
}

