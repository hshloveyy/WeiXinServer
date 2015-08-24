/**
 * @创建作者：邱杰烜
 * @创建日期：2010-11-15
 */
var customerBt
var vendorBt;
var subjectCodeDict
var isExceedDict;
var flag=false;
Ext.onReady(function(){
	
	/**
	 * 1.客户添加按钮与绑定事件
	 */
	customerBt = new Ext.Button({
			text:'添加',
		    id:'div_customer_bt',
		    name:'customerBt',
		    handler:_addCustomer,
		    renderTo:'div_customer_bt',
		    hidden:false,
		    disabled:false
   		});
	var searchCustomerWin = new Ext.SearchHelpWindow({
		    shlpName : 'YHGETKUNNR',
		    callBack : winCustomerCallBack
	});
	function _addCustomer(){
		searchCustomerWin.show();
	}
	function winCustomerCallBack(jsonArrayData){
		if(jsonArrayData.length <= 0){
			return;
		}
		var customerNos = Ext.getDom('customerName').value;
		if(customerNos == ''){
			for(var i=0; i<jsonArrayData.length; i++){
				// 判断要添加的客户是否已经存在，若存在则不添加
				if(customerNos.indexOf(jsonArrayData[i].KUNNR) == -1){
					customerNos += '\'' + jsonArrayData[i].KUNNR + '\',';
				}
			}
			customerNos = customerNos.substring(0,customerNos.length-1);
		}else{
			for(var i=0; i<jsonArrayData.length; i++){
				// 判断要添加的客户是否已经存在，若存在则不添加
				if(customerNos.indexOf(jsonArrayData[i].KUNNR) == -1){
					customerNos += ',\'' + jsonArrayData[i].KUNNR + '\'';
				}
			}
		}
		Ext.getDom('vendorName').value = '';
		Ext.getDom('customerName').value = customerNos;
		Ext.getDom('customerNo').value = customerNos;
		Ext.getDom('customerType').value = '1';		// 类型设为客户
	}
	
	/**
	 * 2.供应商添加按钮与绑定事件
	 */
	vendorBt = new Ext.Button({
			text:'添加',
		    id:'div_vendor_bt',
		    name:'vendorBt',
		    handler:_addVendor,
		    renderTo:'div_vendor_bt',
		    hidden:false,
		    disabled:false
   		});
	var searchVendorWin = new Ext.SearchHelpWindow({
		    shlpName : 'YHGETLIFNR',
		    callBack : winVendorCallBack
	});
	function _addVendor(){
		searchVendorWin.show();
	}
	function winVendorCallBack(jsonArrayData){
		if(jsonArrayData.length <= 0){
			return;
		}
		var vendorNos = Ext.getDom('vendorName').value;
		if(vendorNos == ''){
			for(var i=0; i<jsonArrayData.length; i++){
				// 判断要添加的客户是否已经存在，若存在则不添加
				if(vendorNos.indexOf(jsonArrayData[i].LIFNR) == -1){
					vendorNos += '\'' + jsonArrayData[i].LIFNR + '\',';
				}
			}
			vendorNos = vendorNos.substring(0,vendorNos.length-1);
		}else{
			for(var i=0; i<jsonArrayData.length; i++){
				// 判断要添加的客户是否已经存在，若存在则不添加
				if(vendorNos.indexOf(jsonArrayData[i].LIFNR) == -1){
					vendorNos += ',\'' + jsonArrayData[i].LIFNR + '\'';
				}
			}
		}
		Ext.getDom('customerName').value = '';
		Ext.getDom('vendorName').value = vendorNos;
		Ext.getDom('customerNo').value = vendorNos;
		Ext.getDom('customerType').value = '2';		// 类型设为供应商
	}
	
	
	

	/**
	 * 1.业务范围添加按钮与绑定事件
	 */
	gsberBt = new Ext.Button({
			text:'添加',
		    id:'div_gsber_bt',
		    name:'gsberBt',
		    handler:_addGsber,
		    renderTo:'div_gsber_bt',
		    hidden:false,
		    disabled:false
   		});
	var searchGsberWin = new Ext.SearchHelpWindow({
		    shlpName : 'YHXD3QDEPT',
		    callBack : winGsberCallBack
	});
	function _addGsber(){
		searchGsberWin.show();
	}
	function winGsberCallBack(jsonArrayData){
		if(jsonArrayData.length <= 0){
			return;
		}
		var gsbers = Ext.getDom('gsber').value;
		if(gsbers == ''){
			for(var i=0; i<jsonArrayData.length; i++){
				// 判断要添加的业务范围是否已经存在，若存在则不添加
				if(gsbers.indexOf(jsonArrayData[i].DEPTID) == -1){
					gsbers += '\'' + jsonArrayData[i].DEPTID + '\',';
				}
			}
			gsbers = gsbers.substring(0,gsbers.length-1);
		}else{
			for(var i=0; i<jsonArrayData.length; i++){
				// 判断要添加的业务范围是否已经存在，若存在则不添加
				if(gsbers.indexOf(jsonArrayData[i].DEPTID) == -1){
					gsbers += ',\'' + jsonArrayData[i].DEPTID + '\'';
				}
			}
		}
		Ext.getDom('gsber').value=gsbers;		
	}
	
  
});

/**
 * 查询
 */
function _search(){
	
	if(_commonVerification()){
		var para = Form.serialize('mainForm');
		Ext.getCmp('_search').setDisabled(true);		
		reload_CashFlow_grid2(para);
	}
}

/**
 * 清空
 */
function _clear(){
	document.all.mainForm.reset();
	
}

function reload_CashFlow_grid2(urlParmeters) {
	var paraUrl = encodeURI(context + "/cashFlowQueryController.spr?action=queryCashFlowGrid&"+urlParmeters);
	var conn=new Ext.data.Connection({
	    url: paraUrl,
	    timeout : 1200000, //自定义超时时间，这里是600秒 (默认30s)
	    autoAbort : false,
	    disableCaching : true ,
	    method : "POST"
	});
	/**
	CashFlow_store.proxy = new Ext.data.HttpProxy( {
		url : paraUrl
	});
	**/
	CashFlow_store.proxy = new Ext.data.HttpProxy(conn);
	CashFlow_store.load( {
		params : {
			start : 0,
			limit : 100
		},
		callback : function(xhr){
			Ext.getCmp('_search').setDisabled(false);		
			flag=true;	
			conn.url= encodeURI(context + "/cashFlowQueryController.spr?action=queryGrid&"+urlParmeters);
		},
		scope:this,		
		arg : []
	});
	CashFlow_grid.getStore().commitChanges();
}

function _commonVerification(){
	
	if(div_bukrs_sh_sh.getValue()==''){
		_getMainFrame().showInfo('请选择[公司代码]！');
		return false;
	}
	if(Ext.getDom('augdt_to').value==''){
		_getMainFrame().showInfo('请选择[过账日期]！');
		return false;
	}
	if(Ext.getDom('augdt_from').value==''){
		_getMainFrame().showInfo('请选择[过账日期]！');
		return false;
	}
	return true;
}


/**
 * 导出excel
 */
function _expExcel()
{
	
	Ext.ux.Grid2Excel.Save2Excel(CashFlow_grid);
    var vExportContent = CashFlow_grid.getExcelXml();
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

/**
 * 本地导出excel
 */
function _expExcel2()
{
	var grid = CashFlow_grid;
	try {  
 			var xls = new ActiveXObject ("Excel.Application");  
 		}catch(e) {  
 			alert( "要打印该表，您必须安装Excel电子表格软件，同时浏览器须使用“ActiveX 控件”，您的浏览器须允许执行控件。 请点击【帮助】了解浏览器设置方法！");  
 			return "";  
 		}  
 		
		Ext.getCmp("_exp").setDisabled(true);
 		_getMainFrame().showInfo("正在导出请等待！");
 		
 		xls.visible =false;  //设置excel为不可见  
 		
 		var xlBook = xls.Workbooks.Add;  //新建一个Excel工作簿 
 		var xlSheet = xlBook.Worksheets(1);  //指定要写入内容的工作表为活动工作表 
 		 
 		var cm = grid.getColumnModel();  
 		var colCount = cm.getColumnCount();  
 		var temp_obj = [];  
 		//只下载没有隐藏的列(isHidden()为true表示隐藏,其他都为显示)  
 		//临时数组,存放所有当前显示列的下标  
 		for(i=0;i  <colCount;i++){  
 			// alert(cm.getColumnId(i));
 			 
 			if(cm.isHidden(i) == true){  
 				 
 			}else  if(cm.getColumnId(i)!= 'checker'){ 
 				// Header Name 带 "checker" 的表示为checkbox的列 要去掉 
 				temp_obj.push(i);  
 			} 
 		}  
 		for(i=1;i  <=temp_obj.length;i++){  
 			//显示列的列标题  
 			xlSheet.Cells(1,i).Value = cm.getColumnHeader(temp_obj[i - 1]);  
 		}  
 		 
 		var store = grid.getStore();  
 		var recordCount = store.getCount();  
 		var view = grid.getView();  
 		for(i=1;i  <=recordCount;i++){  
 			for(j=1;j  <=temp_obj.length;j++){ 
 				//定义格式
 				if(  j==14 || j == 15 || j==16 || j==17){
 				//15,16,17,18,是金额，数字类型
 				}else{
			        xlSheet.Cells(i+1,j+1).NumberFormatLocal   =   "@ ";
			        //!!!!!!!上面这一句是将单元格的格式定义为文本
		        }
		       // xlSheet.Cells(i+1,j+1).Font.Bold   =   true;//加粗
		       // xlSheet.Cells(i+1,j+1).Font.Size   =   10;//字体大小  
		        
 				//EXCEL数据从第二行开始,故row = i + 1;  
 				xlSheet.Cells(i + 1,j).Value = view.getCell(i - 1,temp_obj[j - 1]).innerText;  
 			}  
 		}  
 		xlSheet.Columns.AutoFit;  
 		xls.ActiveWindow.Zoom = 75  
		
 		xls.UserControl = true;  //很重要,不能省略,不然会出问题 意思是excel交由用户控制  
 		xls.visible =true;  //设置excel为可见 
 		xls=null;  
 		xlBook=null;  
 		xlSheet=null;  
 		Ext.getCmp("_exp").setDisabled(false);
		var promptMessagebox = new MessageBoxUtil(); 
		promptMessagebox.Close();
}
//分页导出
function _expExcel3(){
	if(flag==false){
		 _getMainFrame().showInfo("要先查询后导出！");
		return;
	}
	var para = Form.serialize('mainForm');
    para = encodeURI(context + "/cashFlowQueryController.spr?action=getCashFlowByExportToExcel&"+para);
    
    window.location.href(para);
    _getMainFrame().showInfo("由于数据量大导出需要一些时间，请等待！");
    Ext.getCmp('_exp').setDisabled(true);
    setTimeout("closeEx()", 180000);
}
function closeEx(){
	Ext.getCmp('_exp').setDisabled(false);
	var promptMessagebox = new MessageBoxUtil(); 
		promptMessagebox.Close();
}
//
//function _expExcel2_2(tableid)   
//{   
//	var grid = CashFlow_grid;
//	var table =grid2Dom(CashFlow_grid);
//	var table = Ext.getDom('CashFlow');
// //   var table = document.getElementById(tableid);   
//    var ax =new ActiveXObject("Excel.Application");    
//    var workbook = ax.Workbooks.Add();   
//     var xlSheet = workbook.ActiveSheet;   
//     var cm = grid.getColumnModel();  
// 		var colCount = cm.getColumnCount();  
// 		var temp_obj = [];  
// 		//只下载没有隐藏的列(isHidden()为true表示隐藏,其他都为显示)  
// 		//临时数组,存放所有当前显示列的下标  
// 		for(i=0;i  <colCount;i++){  
// 			// alert(cm.getColumnId(i));
// 			 
// 			if(cm.isHidden(i) == true){  
// 				 
// 			}else  if(cm.getColumnId(i)!= 'checker'){ 
// 				// Header Name 带 "checker" 的表示为checkbox的列 要去掉 
// 				temp_obj.push(i);  
// 			} 
// 		}  
// 		for(i=1;i  <=temp_obj.length;i++){  
// 			//显示列的列标题  
// 			xlSheet.Cells(1,i).Value = cm.getColumnHeader(temp_obj[i - 1]);  
// 		}  
// 		 
// 		var store = grid.getStore();  
// 		var recordCount = store.getCount();  
// 		var view = grid.getView();  
// 		for(i=1;i  <=recordCount;i++){  
// 			for(j=1;j  <=temp_obj.length;j++){ 
// 				//定义格式
// 				if(  j==14 || j == 15 || j==16 || j==17){
// 				//15,16,17,18,是金额，数字类型
// 				}else{
//			        xlSheet.Cells(i+1,j+1).NumberFormatLocal   =   "@ ";
//			        //!!!!!!!上面这一句是将单元格的格式定义为文本
//		        }
//		       // xlSheet.Cells(i+1,j+1).Font.Bold   =   true;//加粗
//		       // xlSheet.Cells(i+1,j+1).Font.Size   =   10;//字体大小  
//		        
// 				//EXCEL数据从第二行开始,故row = i + 1;  
// 				xlSheet.Cells(i + 1,j).Value = view.getCell(i - 1,temp_obj[j - 1]).innerText;  
// 			}  
// 		}  
// 	//	xlSheet.Columns.AutoFit;  
// 		
//     var sel = document.body.createTextRange();   
//       
//     //把table中的数据移到sel中   
//     sel.moveToElementText(table);   
//       
//     sel.select(); //选中sel中所有数据   
//     sel.execCommand("Copy");//复制sel中的数据    
//           
//  //    sheet.Columns("A").ColumnWidth =35;//设置列宽  
//  //    sheet.Columns("B").ColumnWidth =35;  
//  //    sheet.Rows(1).RowHeight = 35;//设置表头高  
//          
//     //将sel中数据拷贝到sheet工作薄中  
//     xlSheet.Paste();
//     Ext.getDom('_search').focus();            
//     ax.Visible = true;   
//     //通过打印机直接将Excel数据打印出来  
//     xlSheet.Printout;  
//     ax.UserControl = true;   
//}    
//
//function grid2Dom (gridObj){
//     var cmObj = gridObj.getColumnModel();
//
//    var storeObj = gridObj.getStore();
//
//    var dom_room,dom_table,dom_thead,dom_tr,dom_td,dom_tbody,dom_txt;
//
// 
//
//    dom_room = document.createElement('div');
//
//    dom_table = document.createElement('table');
//
//    dom_thead = document.createElement('thead');
//
//    dom_tbody = document.createElement('tbody');
//
// 
//
//    dom_tr = document.createElement('tr');
//
//    for (var i=0; i<cmObj.getColumnCount(); i++){
//
//        if (cmObj.getDataIndex(i)){
//
//            dom_td = document.createElement('td');
//
//            dom_td.noWrap = true;
//
//            dom_td.width = cmObj.getColumnWidth(i)-0;
//
//            dom_txt = document.createTextNode(cmObj.getColumnHeader(i));
//
// 
//
//            dom_td.appendChild(dom_txt);
//
//            dom_tr.appendChild(dom_td);
//
//        }
//
//    }
//
//    dom_thead.appendChild(dom_tr);
//
// 
//
//    for (i=0; i<storeObj.getCount(); i++){
//
//        dom_tr = document.createElement('tr');
//
//        for (var j=0; j<cmObj.getColumnCount(); j++){
//
//            if (cmObj.getDataIndex(j)){
//
//                dom_td = document.createElement('td');
//
//                dom_td.noWrap = true;
//
//                dom_txt = document.createTextNode(storeObj.getAt(i).get(cmObj.getDataIndex(j)));
//
//                dom_td.appendChild(dom_txt);
//
//                dom_tr.appendChild(dom_td);
//
//            }
//
//        }
//
//        dom_tbody.appendChild(dom_tr);
//
//    }
//
// 
//
//    dom_table.appendChild(dom_thead);
//
//    dom_table.appendChild(dom_tbody);
//
//    dom_room.appendChild(dom_table);
//document.appendChild(dom_room);
//    return dom_room;
//
//}
