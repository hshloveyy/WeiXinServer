function view(){
	if(div_bukrs_sh_sh.getValue()==''){
		_getMainFrame().showInfo('请输入[公司代码]！');
		return false;
	}
	if(div_gsber_sh_sh.getValue()==''){
		_getMainFrame().showInfo('请输入[部门]！');
		return false;
	}
	if(Ext.getDom('p_checkdate').value==''){
		_getMainFrame().showInfo('请输入[过账日期]！');
		return false;
	}
	
	var param = Form.serialize('mainForm');
	/**
	new AjaxEngine(contextPath + '/foreignExchangeController.spr?action=voucherPreview',
			{
				method		: "post",
				parameters	: param,
				onComplete	: callBackHandle,
				callback	: voucherPreviewCallBackHandle
			});
			**/
	var ProcessingHint = new MessageBoxUtil();
			ProcessingHint.waitShow();
	var paraUrl = encodeURI(contextPath + "/kpiController.spr?action=getKpi");
	var conn=new Ext.data.Connection({
	    url: paraUrl,
	    timeout : 1200000, //自定义超时时间，这里是600秒 (默认30s)
	    autoAbort : false,
	    disableCaching : true ,
	    method : "POST"
	});
	conn.request({
		params :param,		
		callback	: viewCallBackHandle
	});
	
}


function viewCallBackHandle(arg1,arg2,transport)
{
	if (transport.responseText)
	{
        var responseUtil = new AjaxResponseUtils(transport.responseText);
        var result = responseUtil.getCustomField("coustom");   
        var promptMessagebox = new MessageBoxUtil();
			promptMessagebox.Close();			
			Ext.getDom('sap_pjzy').innerHTML = result.SAP_PJZY;
			Ext.getDom('sap_zzl').innerHTML = result.SAP_ZZL;
			Ext.getDom('sap_jll').innerHTML = result.SAP_JLL;
	     	Ext.getDom('zzl').innerHTML = result.ZZL;
	     	Ext.getDom('jll').innerHTML = result.JLL;
	     	Ext.getDom('pjzy').innerHTML = result.PJZY;
        	
    }
}

function _search()
{
	if(div_bukrs_sh_sh.getValue()==''){
		_getMainFrame().showInfo('请输入[公司代码]！');
		return false;
	}
	if(div_gsber_sh_sh.getValue()==''){
		_getMainFrame().showInfo('请输入[部门]！');
		return false;
	}
	if(Ext.getDom('p_checkdate').value==''){
		_getMainFrame().showInfo('请输入[过账日期]！');
		return false;
	}
	var para = Form.serialize('mainForm');
	reload_Kpi_grid_custom(para);
}

//向外暴露重新加载grid方法，支持查询组件参数方式
function reload_Kpi_grid_custom(urlParmeters)
{
	var paraUrl = encodeURI(encodeURI( contextPath +"/kpiController.spr?action=getBankAcceptance&"+urlParmeters));

	bankAcceptance_store.proxy = new Ext.data.HttpProxy({url:paraUrl});

	bankAcceptance_store.load({params:{start:0, limit:1000},arg:[]});
}

function _clear(){
	document.all.mainForm.reset();
}