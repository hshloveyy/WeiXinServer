/**
  * Author(s):java业务平台代码生成工具
  * Date: 2010年09月13日 09点53分56秒
  * Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  * @(#)
  * Description:  
  * <功能>主对象客户项目余额(CustomerRemSum)管理页面JS用户可编程扩展文件
 */

Ext.onReady(function(){
	div_projectno_sh_sh.on('beforeclick',function(){
		div_projectno_sh_sh.defaultCondition = "CUSTOMER_ID='" + div_customerno_sh_sh.getJsonValue().KUNNR + "'";
	});
	//hongjj 渲染ext的grid取得行同行另一字段
	var customernoIndex = CustomerRemSum_grid.getColumnModel().findColumnIndex('customerno_text');
	CustomerRemSum_grid.getColumnModel().setRenderer(customernoIndex,function(value,css,record,rowIndex,colIndex,store){
		return '<a href="#" style="color:green;" onclick="_commonMethodOperation(\'1\',\'客户代垫额度和放货额度配置查看\',CustomerRemSum_grid,\'xdss3/ceditValueControl/customerCreditConfController.spr?action=_view&configid='+record.get('config')+'\',\'\',\'_view\',\'false\')">'+value+'</a>';
	});
	
    //千分位
    var re=/(\d{1,3})(?=(\d{3})+(?:$|\.))/g;
    var prepayvalueIndex = CustomerRemSum_grid.getColumnModel().findColumnIndex('prepayvalue');
    CustomerRemSum_grid.getColumnModel().setRenderer(prepayvalueIndex,function(value,css,record,rowIndex,colIndex,store){
        return value.replace(re,"$1,");
    });
    var sendvalueIndex = CustomerRemSum_grid.getColumnModel().findColumnIndex('sendvalue');
    CustomerRemSum_grid.getColumnModel().setRenderer(sendvalueIndex,function(value,css,record,rowIndex,colIndex,store){
        return value.replace(re,"$1,");
    });
    var remainingsumIndex = CustomerRemSum_grid.getColumnModel().findColumnIndex('remainingsum');
    CustomerRemSum_grid.getColumnModel().setRenderer(remainingsumIndex,function(value,css,record,rowIndex,colIndex,store){
        return value.replace(re,"$1,");
    });
    
    //染色日期
    var endtimeIndex = CustomerRemSum_grid.getColumnModel().findColumnIndex('endtime');
    CustomerRemSum_grid.getColumnModel().setRenderer(endtimeIndex,function(value,css,record,rowIndex,colIndex,store){
        var b = _validateDate(value);
        if (b == 0) {
            return '<font color="red">'+value+'</font>';
        } else if (b == -1) {
            return '<font color="blue">'+value+'</font>';
        } else {
            return value;
        }
    });
});


/**
 * 与当前日期相比大于10天　返回　1
 * 与当前日期相比小于10天且不晚于当前日期　返回　0
 * 与当前日期相比晚于当前日期　返回　-1
 */
function _validateDate(date)
{
    date = date.substring(0,4) + "/"+date.substring(4,6) + "/"+date.substring(6,8) ;
    var _date = new Date(date);
    var _now = new Date();
    var _aft = new Date();
    _aft.setDate(_now.getDate()+10);
    if ( _aft.getTime() < _date.getTime()) {
        return 1;
    } else if (_date.getTime() < _now.getTime()) {
        return -1;
    } else {
        return 0;
    }

}

/**
 * 查询动作执行前
 */
function _presearch()
{
	var customerId = '';
	var projectId = '';
	var creditType = '';
	customerId = div_customerno_sh_sh.getValue();
	projectId = div_projectno_sh_sh.getValue();
	creditType = dict_div_credittype_dict.getValue();
//	alert(customerId + ' ' + projectId + ' ' + creditType);
	if (creditType=='') {
		_getMainFrame().showInfo("请选择一个授信类型！");
		return false;
	}
	if(projectId != '' &&  customerId == ''){
		_getMainFrame().showInfo("请选择一个客户！");
		return false;
	}else{
		var conn = Ext.lib.Ajax.getConnectionObject().conn; 
		conn.open("POST", contextPath+'/xdss3/customerremainsum/customerRemSumController.spr?action=insertData&customerid='+ customerId + 
				'&projectid='+ projectId + '&credittype=' + creditType,false);
		conn.send(null);
		return true;
	}
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


///**
// * 导出excel
// */
//function _expExcel()
//{
//	
//	Ext.ux.Grid2Excel.Save2Excel(CustomerRemSum_grid);
//    var vExportContent = CustomerRemSum_grid.getExcelXml();
//    if (Ext.isIE6 || Ext.isIE7 || Ext.isSafari || Ext.isSafari2 || Ext.isSafari3) {
//        var fd=Ext.get('frmDummy');
//        if (!fd) {
//            fd=Ext.DomHelper.append(Ext.getBody(),{tag:'form',method:'post',id:'frmDummy',action:contextPath+'/xdss3/profitLoss/profitLossController.spr?action=_expExcel', 
//            	target:'_blank',name:'frmDummy',cls:'x-hidden',
//           	cn:[{
//           		tag:'input',name:'exportContent',id:'exportContent',type:'hidden'}
//            	]},true);
//        }
//        fd.child('#exportContent').set({value:vExportContent});
//        fd.dom.submit();
//    } else {
//        document.location = 'data:application/vnd.ms-excel;base64,'+Base64.encode(vExportContent);
//    }
//}


/**
 * 导出excel
 */
function _expExcel()
{
        
    if (_presearch() == false ){
        return false;
    }
    _search();
    var para = Form.serialize('mainForm');
    para = encodeURI(para);
    window.location.href(contextPath+'/xdss3/customerremainsum/customerRemSumController.spr?action=detailToExcel&' +para);
    
}
