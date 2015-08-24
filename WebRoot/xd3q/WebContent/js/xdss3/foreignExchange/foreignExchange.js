/**
 * @创建作者：
 * @创建日期：2010-11-15
 */
Ext.onReady(function(){	
	
});

/**
 * 查询
 */
function voucherPreview(){
	
	if(div_bukrs_sh_sh.getValue()==''){
		_getMainFrame().showInfo('请输入[公司代码]！');
		return false;
	}
	if(!Ext.getDom('ck_sybm').checked && div_gsber_sh_sh.getValue()==''){
		_getMainFrame().showInfo('请输入[部门]！');
		return false;
	}
	if(Ext.getDom('p_gjahr').value==''){
		_getMainFrame().showInfo('请输入[会计年度]！');
		return false;
	}
	
	if(Ext.getDom('p_monat').value==''){
		_getMainFrame().showInfo('请输入[会计期间]！');
		return false;
	}
	if(Ext.getDom('p_monat').value.length !=2){
		_getMainFrame().showInfo('请输入两位数的[会计期间]！');
		return false;
	}
	if(!Ext.getDom('ck_kh').checked && !Ext.getDom('ck_gys').checked && !Ext.getDom('ck_wbzj').checked && !Ext.getDom('ck_zz').checked){
		_getMainFrame().showInfo('请选择输出格式！');
		return false;
	}
	/**
	if(div_bukrs_sh_sh.getValue()=='2100' && div_gsber_sh_sh.getValue()=='2199'){
		
		if(Ext.getDom('ck_kh').checked || Ext.getDom('ck_gys').checked){
			_getMainFrame().showInfo('2199部门，不能选客户供应商！');
			return false;
		}
	}
	**/
	if(div_bukrs_sh_sh.getValue()=='2100' && div_gsber_sh_sh.getValue()!='2199' && !Ext.getDom('ck_sybm').checked){
		if(Ext.getDom('ck_wbzj').checked ){
			_getMainFrame().showInfo('非2199部门，不能选外币存款！');
			return false;
		}
	}
	if(Ext.getDom('ck_sybm').checked && div_gsber_sh_sh.getValue() !=''){
			_getMainFrame().showInfo('选择了所有部门不能再输入部门');
			return false;
	}
	//股份本部和光电不做控制
	if(Ext.getDom('ck_sybm').checked && div_bukrs_sh_sh.getValue()!='1001' && div_bukrs_sh_sh.getValue()!='8200' ){
		if(Ext.getDom('ck_kh').checked || Ext.getDom('ck_gys').checked || Ext.getDom('ck_zz').checked ){
			_getMainFrame().showInfo('选择了所有部门，只能选外币资金！');
			return false;
		}
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
	var paraUrl = encodeURI(contextPath + "/foreignExchangeController.spr?action=voucherPreview");
	var conn=new Ext.data.Connection({
	    url: paraUrl,
	    timeout : 1200000, //自定义超时时间，这里是600秒 (默认30s)
	    autoAbort : false,
	    disableCaching : true ,
	    method : "POST"
	});
	conn.request({
		params :param,		
		callback	: voucherPreviewCallBackHandle
	});
			
}


/**
 * 凭证预览回调函数。
 * 
 * @param {}
 *            transport
 */

function voucherPreviewCallBackHandle(arg1,arg2,transport)
{
	if (transport.responseText)
	{
        var responseUtil = new AjaxResponseUtils(transport.responseText);
        var result = responseUtil.getCustomField("coustom");   
        var promptMessagebox = new MessageBoxUtil();
			promptMessagebox.Close();
			
			Ext.getDom('businessid').value = result.businessid;
	     	_getMainFrame().maintabs.addPanel('调汇凭证预览', '', contextPath + '/xdss3/voucher/voucherController.spr?action=_manage&businessid=' + result.businessid,closeVoucherCallBack,true);
        	
    }
}

function closeVoucherCallBack(flag){	
	if(flag){
		_getMainFrame().Ext.MessageBox.show({
				title:'凭证生成成功',
			    msg: '凭证生成成功，是否需要查看凭证？',
				buttons: {yes:'是', no:'否'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
				    	_getMainFrame().maintabs.addPanel2('凭证查看', contextPath+'/xdss3/voucher/voucherController.spr?action=_manage&businessid='+Ext.getDom('businessid').value);
					}else{
						_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
					}
				}
			});
	}
}

/**
 * 清空
 */
function _clear(){
	document.all.mainForm.reset();
}


