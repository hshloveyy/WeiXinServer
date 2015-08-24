/**
 * @创建作者：邱杰烜
 * @创建日期：2010-11-15
 */
var customerBt
var vendorBt;
var subjectCodeDict
var isExceedDict;

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
	 * 3.总账科目下拉框
	 */
	subjectCodeDict = new Ext.form.DpComboBox({
		renderTo : 'div_subjectCode_dict',
		id : 'subjectCode' + '_text',
		hiddenName : 'subjectCode',
		valueField : 'id',
		displayField : 'text',
		disabled : false,
		hidden : false,
		editable : true,
		readOnly : false,
		width : 203,
		allowBlank : true,
		value : '1122*',
		store : new Ext.data.SimpleStore( {
			fields : [ 'id', 'text' ],
			data : [[ '1122*', '应收账款' ], 
					[ '2203*', '预收账款' ],
					[ '1221*', '其他应收款' ], 
					[ '2202*', '应付账款' ], 
					[ '1123*', '预付账款' ],
					[ '2241*', '其他应付款' ], 
					[ '1241010001', '内部往来-内部交易（借）' ], 
					[ '1241010002', '内部往来-内部交易（贷）' ],
					[ '1241020001', '内部往来-资金划拨' ], 
					[ '1241030001', '内部往来-代收代付' ], 
					[ '1241040001', '内部往来-其他' ],
					[ '1121000001', '应收票据' ],
					[ '2201010001', '应付票据' ]]
		}),
		mode : 'local',
		triggerAction : 'all',
		emptyText : '请选择...',
		selectOnFocus : true,
		listeners : ''
   	});
	subjectCodeDict.on('select',function(e,n,o){
//		alert(Ext.getDom('subjectCode').value);
	});
	
	/**
	 * 4.业务类型拉框
	 *Z001 出口订单
	 *Z002 进口
	 *Z003 内贸
	 *ZRE  退货
	 *10 自营
	 *20 合作
	 *30 代理
	 *40 其他
	 *99 通用
	 *业务类型为组合字段
	 *如：合作出口 Z001 + 20
	 */
	 
	/**
	vbeltypeDict = new Ext.form.DpComboBox({
		renderTo : 'div_vbeltype_dict',
		id : 'vbeltype' + '_text',
		hiddenName : 'vbeltype',
		valueField : 'id',
		displayField : 'text',
		disabled : false,
		hidden : false,
		editable : true,
		readOnly : false,
		width : 203,
		allowBlank : true,
		value : '',
		store : new Ext.data.SimpleStore( {
			fields : [ 'id', 'text' ],
			data : [[ "'Z00310','Z00320','Z00330','Z00340'", '内贸' ], 
					[ 'Z00120', '合作出口' ],
					[ 'Z00220', '合作进口' ], 
					[ 'Z00110', '自营出口' ], 
					[ 'Z00210', '自营进口' ],
					[ 'Z00130', '代理出口' ],
					[ 'Z00230', '代理进口' ],
					[ 'Z006', '委托加工物资']]
		}),
		mode : 'local',
		triggerAction : 'all',
		emptyText : '请选择...',
		selectOnFocus : true,
		listeners : ''
   	});
	**/
	
	
	/**
	 * 5.是否逾期
	 */
	isExceedDict = new Ext.form.DpComboBox({
		renderTo : 'div_isExceed_dict',
		id : 'isExceed' + '_text',
		hiddenName : 'isExceed',
		valueField : 'id',
		displayField : 'text',
		disabled : false,
		hidden : false,
		editable : true,
		readOnly : false,
		width : 203,
		allowBlank : true,
		value : '',
		store : new Ext.data.SimpleStore( {
			fields : [ 'id', 'text' ],
			data : [[ '1', '是' ], 
					[ '0', '否' ]]
		}),
		mode : 'local',
		triggerAction : 'all',
		emptyText : '请选择...',
		selectOnFocus : true,
		listeners : ''
   	});

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
	/**业务类型
	**/
	var arr = [["'Z003_10','Z003_20','Z003_30','Z003_40'", '内贸'], ["'Z001_20'", '合作出口'], ["'Z002_20'", '合作进口'],["'Z001_10'", '自营出口'],["'Z002_10'", '自营进口'],["'Z001_30'", '代理出口'],["'Z002_30'", '代理进口'],["'Z006'", '委托加工物资']];
//    var arr2 = [['1', '项一'], ['2', '项二']];


    var vbeltype_field = new Ext.form.MultiSelectField({
        applyTo:'customerMultiselect',  
        hiddenName:'customer_hid',  //Ext.get('customer_hid').getValue() 可以得到索引号ID数组
        contextArray : arr,
        fieldLabel : '11',
        id : 'vbeltypeField',
  //      defaltValueArray:arr2,
        name : 'vbeltypeField'
    });   
    
  
});

/**
 * 查询
 */
function _search(){
	
	if(_commonVerification()){
		var para = Form.serialize('mainForm');
		Ext.getCmp('_search').setDisabled(true);
		reload_AgeAnalysis_grid2(para);
	}
}

/**
 * 清空
 */
function _clear(){
	document.all.mainForm.reset();
	subjectCodeDict.setValue('1122*');
}

function reload_AgeAnalysis_grid2(urlParmeters) {
	var paraUrl = encodeURI(context + "/ageAnalysisController.spr?action=queryAgeAnalysisGrid&"+urlParmeters);
	var conn=new Ext.data.Connection({
	    url: paraUrl,
	    timeout : 1200000, //自定义超时时间，这里是600秒 (默认30s)
	    autoAbort : false,
	    disableCaching : true ,
	    method : "POST"
	});
	/**
	AgeAnalysis_store.proxy = new Ext.data.HttpProxy( {
		url : paraUrl
	});
	**/
	AgeAnalysis_store.proxy = new Ext.data.HttpProxy(conn);
	AgeAnalysis_store.load( {
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
	AgeAnalysis_grid.getStore().commitChanges();
}

function _commonVerification(){
	if(subjectCodeDict.getValue()==''){
		_getMainFrame().showInfo('请选择[总账科目]！');
		return false;
	}
	if(Ext.getDom('augdt').value==''){
		_getMainFrame().showInfo('请选择[过账日期]！');
		return false;
	}
	if(div_bukrs_sh_sh.getValue()==''){
		_getMainFrame().showInfo('请选择[公司代码]！');
		return false;
	}
	if(subjectCodeDict.getValue()!=''){
		var subjectCode= subjectCodeDict.getValue();
		if(subjectCode=='2202*' || subjectCode=='1123*' || subjectCode=='2241*' || subjectCode=='1241010002' || subjectCode=='2201010001'  ){
			//供应商
			var bussinessType =Ext.getCmp('vbeltypeField').getValue();
			var bussinessType2="";
			//内贸
			if(bussinessType.indexOf("'Z003_10','Z003_20','Z003_30','Z003_40'")!=-1){
				bussinessType2 = bussinessType2 + ",'Z005'";
			}
			//合作出口
			if(bussinessType.indexOf("'Z001_20'")!=-1){
				bussinessType2= bussinessType2 + ",'Z002'";
			}
			//合作进口
			if(bussinessType.indexOf("'Z002_20'")!=-1){
				bussinessType2= bussinessType2 + ",'Z001'";
			}
			//自营出口
			if(bussinessType.indexOf("'Z001_10'")!=-1){
				bussinessType2= bussinessType2 + ",'Z004'";
			}
			//自营进口
			if(bussinessType.indexOf("'Z002_10'")!=-1){
				bussinessType2= bussinessType2 + ",'Z003'";
			}
			//代理出口?
			if(bussinessType.indexOf("'Z001_30'")!=-1){
				bussinessType2= bussinessType2 + ",' '";
			}
			//代理进口?
			if(bussinessType.indexOf("'Z002_30'")!=-1){
				bussinessType2= bussinessType2 + ",' '";
			}
			//委托加工物资
			if(bussinessType.indexOf("'Z006'")!=-1){
				bussinessType2= bussinessType2 + ",'Z006'";
			}
			/**
			if(Ext.getDom('customerName').value !=''){
				_getMainFrame().showInfo('总账科目与客户不一致！');
				return false;
			}
			**/
			Ext.getDom('vbeltype').value=bussinessType2.substr(1,bussinessType2.length);	
			Ext.getDom('customerNo').value = Ext.getDom('vendorName').value;
			Ext.getDom('customerType').value = '2';		// 类型设为客户
		}else{
			//客户
			var bussinessType =Ext.getCmp('vbeltypeField').getValue();
			/**
			if(Ext.getDom('vendorName').value !=''){
				_getMainFrame().showInfo('总账科目与供应商不一致！');
				return false;
			}
			**/
			Ext.getDom('vbeltype').value=bussinessType;	
			Ext.getDom('customerNo').value = Ext.getDom('customerName').value;
			Ext.getDom('customerType').value = '1';		// 类型设为客户
		}
	}
	return true;
}


/**
 * 导出excel
 */
function _expExcel()
{
	
	Ext.ux.Grid2Excel.Save2Excel(AgeAnalysis_grid);
    var vExportContent = AgeAnalysis_grid.getExcelXml();
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