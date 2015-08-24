/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年09月17日 07点13分35秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象授信日志表(Creditlog)管理页面JS用户可编程扩展文件
 */

Ext.onReady(function(){
	div_providerno_sh_sh.editable = false;
	div_custno_sh_sh.editable = false;
	dict_div_credittype_dict.editable = false;
	dict_div_ytype_dict.on("change",function(){
		if (dict_div_ytype_dict.getValue() == '1'){//客户
			div_providerno_sh_sh.editable = false;
			div_custno_sh_sh.editable = true;
			dict_div_credittype_dict.editable = true;
			div_providerno_sh_sh.setValue('');
		}else if(dict_div_ytype_dict.getValue() == '2'){//供应商
			div_providerno_sh_sh.editable = true;
			div_custno_sh_sh.editable = false;
			dict_div_credittype_dict.editable = false;
			dict_div_credittype_dict.setValue('');
			div_custno_sh_sh.setValue('');
		}else{
			div_providerno_sh_sh.editable = false;
			div_custno_sh_sh.editable = false;
			dict_div_credittype_dict.clearValue();
			dict_div_credittype_dict.editable = true;
			div_custno_sh_sh.setValue('');
			div_providerno_sh_sh.setValue('');
		}
	});
	
	var busnum = Creditlog_grid.getColumnModel().findColumnIndex('busnum');
//	Creditlog_grid.getColumnModel().setRenderer(busnum,function(value, metadata, record ){
//		if (record.get('ymodule')=="6") {
//			return '<a href="#" style="color:green;" onclick="_commonMethodOperation(\'1\',\'查看\',Creditlog_grid,\'xdss3/collect/collectController.spr?action=_view&collectid='+value+'\',\'\',\'_view\',\'false\')">查看</a>    ';
//		} else if (record.get('ymodule')=="10") {
//			return 12310;
//		} else if (record.get('ymodule')=="10") {
//			return '<a href="#" style="color:green;" onclick="_commonMethodOperation(\'1\',\'查看\',Creditlog_grid,\'xdss3/customerDrawback/customerRefundmentController.spr?action=_view&refundmentid='+value+'\',\'\',\'_view\',\'false\')">查看</a>    ';
//		} else {
//			return value;
//		}
//	});	
	
    //千分位
    var re=/(\d{1,3})(?=(\d{3})+(?:$|\.))/g;
    var amoutIndex = Creditlog_grid.getColumnModel().findColumnIndex('amout');
    Creditlog_grid.getColumnModel().setRenderer(amoutIndex,function(value,css,record,rowIndex,colIndex,store){
        return String(value).replace(re,"$1,");
    });
    
});


/**
 * 查询动作执行前
 */
function _presearch()
{
	var customerId = '';
	var providerId = '';
	var creditType = '';
	var ytype = '';
	customerId = div_custno_sh_sh.getValue();
	providerId = div_providerno_sh_sh.getValue();
	creditType = dict_div_credittype_dict.getValue();
	ytype = dict_div_ytype_dict.getValue();
	prov_projectid = div_prov_projectno_sh_sh.getValue();
	cust_projectid = div_cust_projectno_sh_sh.getValue();
	//alert(customerId + '&' + providerId + '&' + creditType + '&' + ytype);
	var ytype = dict_div_ytype_dict.getValue();
	
	if(ytype == ''){
		_getMainFrame().showInfo("请选择客户或者供应商！");
		return false;
	}
	
	if(ytype == '1'){
		if(customerId == ''){
			_getMainFrame().showInfo("请选择一个客户！");
			return false;
		}
//		if(cust_projectid == ''){
//			_getMainFrame().showInfo("请选择一个客户项目！");
//			return false;
//		}
	}
	
	if(ytype == '2'){
		if (providerId == ''){
			_getMainFrame().showInfo("请选择一个供应商！");
			return false;
		}
//		if (prov_projectid == ''){
//			_getMainFrame().showInfo("请选择一个供应商项目！");
//			return false;
//		}
	}
	
	var conn = Ext.lib.Ajax.getConnectionObject().conn; 
	conn.open("POST", contextPath+'/xdss3/creditlog/creditlogController.spr?action=queryCreditData&customerid='+ customerId + 
				'&providerId='+ providerId + '&crdittype=' + creditType + '&ytype=' + ytype + '&prov_projectid=' + prov_projectid + '&cust_projectid=' + cust_projectid,false);
	conn.send(null);
	return true;
	
}

/**
 * 查询动作执行后
 * 当 _presearch() 返回 false 时候则执行本函数。
 */
function _postsearch()
{

}


/**
 * 清空操作
 */
function _preresetForm()
{
	return true;
}
/**
 * 清空操作
 */
function _postresetForm()
{

}

/**
 * 导出excel
 */
function _expExcel()
{
		
	if (_presearch() == false ){
		return false;
	}
	_search();
	Creditlog_grid.setTitle("授信日志");
	var para = Form.serialize('mainForm');
	para = encodeURI(para);
	window.location.href(contextPath+'/xdss3/creditlog/creditlogController.spr?action=detailToExcel&'+para);
	
}



/*
 * 合同详细信息查看
 */ 
function viewContractInfo(contNo){
	var contUrl = contextPath + '/contractController.spr?action=viewSaleContract&contractno=' + contNo;
	if(contNo.trim()!=''){
		_getMainFrame().ExtModalWindowUtil.show('合同信息', contUrl, '', '', {width:700,height:600});
	}
}
/*
 * 立项详细信息查看
 */
function viewProjectInfo(projNo){
	var projUrl = contextPath + '/projectController.spr?action=modify&from=view&projectNo=' + projNo;
	if(projNo.trim()!=''){
		_getMainFrame().ExtModalWindowUtil.show('立项信息', projUrl, '', '', {width:700,height:600});
	}
}
/*
 * 相关单据信息查看 
 */
function viewRelatedInfo(relaNo){
	var relaUrl = contextPath + '/xdss3/collect/collectController.spr?action=viewRelatedInfo&relatedNo=' + relaNo;
	if(relaNo.trim()!=''){
		_getMainFrame().maintabs.addPanel('原收款单信息','',relaUrl);
		   
	}
}