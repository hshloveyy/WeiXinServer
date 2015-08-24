/**
 * @创建作者：邱杰烜
 * @创建日期：2010-11-15
 */
Ext.onReady(function(){	
	_search();
});

/**
 * 查询
 */
function _search(){
	var para = Form.serialize('mainForm');
	reload_UnclearDetail_grid2(para);
}

/**
 * 清空
 */
function _clear(){
	//document.all.mainForm.reset();
}

function reload_UnclearDetail_grid2(urlParmeters) {
	var paraUrl = encodeURI(context + "/ageAnalysisController.spr?action=queryUnclearDetailGrid&"+urlParmeters);
	var conn=new Ext.data.Connection({
	    url: paraUrl,
	    timeout : 600000, //自定义超时时间，这里是600秒 (默认30s)
	    autoAbort : false,
	    disableCaching : true ,
	    method : "POST"
	});
	UnclearDetail_store.proxy = new Ext.data.HttpProxy(conn);
	/**
	UnclearDetail_store.proxy = new Ext.data.HttpProxy( {
		url : paraUrl
	});
	**/
	UnclearDetail_store.load( {
		params : {
			start : 0,
			limit : 10000
		},		
		callback : function(xhr){
		//	Ext.getCmp('_search').setDisabled(false);
		},
		scope:this,		
		arg : []
	});
	UnclearDetail_grid.getStore().commitChanges();
}

function _commonVerification(){
}



/**
 * 导出excel
 */
function _expExcel()
{
	Ext.ux.Grid2Excel.Save2Excel(UnclearDetail_grid);
    var vExportContent = UnclearDetail_grid.getExcelXml();
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