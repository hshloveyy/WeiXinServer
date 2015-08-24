/**
 * @创建作者：
 * @创建日期：2010-11-15
 */
Ext.onReady(function(){	
	
});

function _search(){
var param = Form.serialize('mainForm');
var paraUrl =  'exchangeRateController.spr?action=exchangeRateGrid&' + param;
	
	exchangerate_store.proxy = new Ext.data.HttpProxy( {
		url : paraUrl
	});
	exchangerate_store.load( {
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
	exchangerate_grid.getStore().commitChanges();
}

/**
 * 清空
 */
function _clear(){
	document.all.mainForm.reset();
}


function _create(){

	
	//增加页签
		_getMainFrame().maintabs.addPanel("增加汇率",null, 'exchangeRateController.spr?action=create');
			

}

function saveCallBackHandle(transport)
{
	if (transport.responseText)
	{
        var responseUtil = new AjaxResponseUtils(transport.responseText);
        var result = responseUtil.getCustomField("coustom");   
        var promptMessagebox = new MessageBoxUtil();
			promptMessagebox.Close();
		
		if(null != result.error){
			showInfo(result.error);	
		} 
			
	     
        	
    }
}

function _save(){
	var param = Form.serialize('mainForm');
	
	new AjaxEngine( 'exchangeRateController.spr?action=save',
			{
				method		: "post",
				parameters	: param,
				onComplete	: callBackHandle,
				callback	: saveCallBackHandle
			});
}

function _cancel(){
 	var promptMessagebox = new MessageBoxUtil();
			promptMessagebox.Close();
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}


function _view(id){
		_getMainFrame().maintabs.addPanel("查看汇率",null, 'exchangeRateController.spr?action=view&id=' + id);
}

function _del(id){
	var param = 'id=' + id;
	new AjaxEngine( 'exchangeRateController.spr?action=del',
			{
				method		: "post",
				parameters	: param,
				onComplete	: callBackHandle,
				callback	: delCallBackHandle
			});
}

function delCallBackHandle(transport)
{
	if (transport.responseText)
	{
        var responseUtil = new AjaxResponseUtils(transport.responseText);
        var result = responseUtil.getCustomField("coustom");   
        var promptMessagebox = new MessageBoxUtil();
			promptMessagebox.Close();
		
		if(null != result.error){
			showInfo(result.error);	
		} 
		 _search();        	
    }
}