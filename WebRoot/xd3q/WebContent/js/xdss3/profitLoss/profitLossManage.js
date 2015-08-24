﻿/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年05月21日 11点48分47秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象合同信息及市场单价维护(ProfitLoss)管理页面JS用户可编程扩展文件
 */

Ext.onReady(function(){

//	// 重新渲染金额－单价
//	var contIndex = ProfitLoss_grid.getColumnModel().findColumnIndex('unitprice');
//	ProfitLoss_grid.getColumnModel().setRenderer(contIndex,function(unitprice){
//		var re=/(\d{1,3})(?=(\d{3})+(?:$|\D))/g;
//		return 	unitprice.replace(re,"$1,");
//	});
//	
//	// 重新渲染金额－总金额（成本）
//	var contIndex = ProfitLoss_grid.getColumnModel().findColumnIndex('totalvalue');
//	ProfitLoss_grid.getColumnModel().setRenderer(contIndex,function(value){
//		var re=/(\d{1,3})(?=(\d{3})+(?:$|\D))/g;
//			return 	value.replace(re,"$1,");
//	});	
//	
//	// 重新渲染金额－市场单价（不含税）
//	var contIndex = ProfitLoss_grid.getColumnModel().findColumnIndex('marketunitprice');
//	ProfitLoss_grid.getColumnModel().setRenderer(contIndex,function(value){
//		var re=/(\d{1,3})(?=(\d{3})+(?:$|\D))/g;
//		return 	value.replace(re,"$1,");
//	});	
//	
	// 重新渲染金额－单位浮亏/盈
//	var contIndex = ProfitLoss_grid.getColumnModel().findColumnIndex('unitprofitloss');
//	ProfitLoss_grid.getColumnModel().setRenderer(contIndex,function(value){
//		var re=/(\d{1,3})(?=(\d{3})+(?:$|\D))/g;
//		return 	value.replace(re,"$1,");
//	});	
	
});

/**
 * 仅用于测试，请暂时保留
function _reloadAll(){
	new AjaxEngine(contextPath + '/xdss3/profitLoss/profitLossController.spr?action=_reloadAllForTest', {
			method:"post", 
			onComplete:function(transport){
				var promptMessagebox = new MessageBoxUtil();
				promptMessagebox.Close();
				_getMainFrame().showInfo("重载完毕");
			}, 
			callback:''});
}
 */

/**
 * 查询动作执行前
 */
function _presearch()
{
	var companyid = div_companyid_sh_sh.getValue();
	var datevalue = document.getElementById('datevalue.fieldValue').value;
	
	if (datevalue == ""){
		_getMainFrame().showInfo('请输入日期！');
		return false;	
	}
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
	ProfitLoss_grid.setTitle("存货浮动盈亏表(业务)");
	var para = Form.serialize('mainForm');
	para = para + '&detailName=profitloss';
	para = encodeURI(para);
	window.location.href(contextPath+'/xdss3/profitLoss/profitLossController.spr?action=detailToExcel&'+para);
	
//	Ext.ux.Grid2Excel.Save2Excel(ProfitLoss_grid);
//    var vExportContent = ProfitLoss_grid.getExcelXml();
//    if (Ext.isIE6 || Ext.isIE7 || Ext.isSafari || Ext.isSafari2 || Ext.isSafari3) {
//        var fd=Ext.get('frmDummy');
//        if (!fd) {
//            fd=Ext.DomHelper.append(Ext.getBody(),{tag:'form',method:'post',id:'frmDummy',action:contextPath+'/xdss3/profitLoss/profitLossController.spr?action=_expExcel', 
//            	target:'_blank',name:'frmDummy',cls:'x-hidden',
//            	cn:[{
//            		tag:'input',name:'exportContent',id:'exportContent',type:'hidden'}
//            	]},true);
//        }
//        fd.child('#exportContent').set({value:vExportContent});
//        fd.dom.submit();
//    } else {
//        document.location = 'data:application/vnd.ms-excel;base64,'+Base64.encode(vExportContent);
//    }
}