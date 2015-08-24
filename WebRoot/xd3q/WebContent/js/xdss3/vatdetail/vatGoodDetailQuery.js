Ext.onReady(function(){
    
    
    /**
     * 1.业务范围添加按钮与绑定事件
     */
    var gsberBt = new Ext.Button({
            text:'添加',
            id:'gsberBt',
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
        var gsbers = Ext.getDom('t_gsber').value;
        for(var i=0; i<jsonArrayData.length; i++){
            // 判断要添加的业务范围是否已经存在，若存在则不添加
            if(gsbers.indexOf(jsonArrayData[i].DEPTID) == -1){
                gsbers += ',' + jsonArrayData[i].DEPTID + '';
            }
        }
        //除去前部逗号
        while (gsbers.substring(0,1) == '*' || gsbers.substring(0,1) == ',') {
            gsbers = gsbers.substring(1,gsbers.length);
        }
        Ext.getDom('t_gsber').value=gsbers;  
    }
	
    /**2、业务类型
     **/
     var arr = [
                    ['代理出口','代理出口'],
                    ['代理进口','代理进口'],
                    ['合作出口','合作出口'],
                    ['合作进口','合作进口'],
                    ['进口其他','进口其他'],
                    ['进口通用','进口通用'],
                    ['内贸','内贸'],
                    ['委托加工','委托加工'],
                    ['虚拟订单','虚拟订单'],
                    ['转口','转口'],
                    ['自营出口','自营出口'],
                    ['自营进口','自营进口'],
                    ['自营其他','自营其他'],
                    ['自营通用','自营通用'],
                    ['空','空'], 
                    ['无','无'],
                    ['*','*']  ];
     var arr2 = [["*", "*"]];
     var t_typeField = new Ext.form.MultiSelectField({
         applyTo:'t_typeMultiselect',  
         hiddenName:'t_type2',
         contextArray : arr,
         fieldLabel : '11',
         id : 't_typeField',
         value:'*',
         defaltValueArray:arr2,
         name : 't_typeField'
     });   
     
     /**3、 物料组*/
     var sstore = dict_div_ymatGroup.getStore();
     var snum = sstore.getCount();
     var arrMat = new Array();
     for (var i=0; i < snum; i++ ) {
         arrMat[i] = [sstore.getAt(i).get('id'), sstore.getAt(i).get('text')];
     }
     var len = arrMat.length;
     arrMat[len] = ["空","空"];
     arrMat[len + 1] = ["无","无"];
     arrMat[len + 2] = ["*","*"];
     var arr2Mat = [["*", "*"]];
     var matGroupMultiselect = new Ext.form.MultiSelectField({
         applyTo:'matGroupMultiselect',  
         hiddenName:'t_matgroup',
         contextArray : arrMat,
         fieldLabel : '11',
         id : 'matGroupMultiselect',
         defaltValueArray:arr2Mat,
         name : 'matGroupMultiselect'
     });
     Ext.getDom('t_matgroup').value = "*";
     /**
      * 4.物料号
      */
     var matnr_bt = new Ext.Button({
             text:'添加',
             id:'matnr_bt',
             name:'matnr_bt',
             handler:_addMatnr,
             renderTo:'div_matnr_bt',
             hidden:false,
             disabled:false
     });
     function _addMatnr(){
         top.ExtModalWindowUtil.show('查询物料信息',
                 'masterQueryController.spr?action=toFindMaterial_vat&materialOrg=\'\'',
                 '',
                 materielcallbackFunction,
                 {width:755,height:478});
     }
     function materielcallbackFunction(){
         var store = top.ExtModalWindowUtil.getReturnValue();
         var matnrs = Ext.getDom('t_matnr').value;
         for(var i=0; i<store.length; i++){
             // 判断要添加的数据是否已经存在，若存在则不添加
             if(matnrs.indexOf(store[i].get('MATNR')) == -1){
                 matnrs += ',' + store[i].get('MATNR');
             }
         }
         //除去前部逗号
         while (matnrs.substring(0,1) == '*' || matnrs.substring(0,1) == ',') {
             matnrs = matnrs.substring(1,matnrs.length);
         }
         Ext.getDom('t_matnr').value=matnrs;
     }
	
});

/**
 * 查询
 */
function _search(){
	

	var bussinessType =Ext.getCmp('t_typeField').getValue();
	if(bussinessType != ''){
		Ext.getDom('t_type').value=bussinessType;
	}else{
		Ext.getDom('t_type').value='*';
	}
	var para = Form.serialize('mainForm');
	
	var bukrs = div_bukrs_sh_sh.getValue();
    if (bukrs == '') {
        _getMainFrame().showInfo('请先输入公司');
        return;
    }
    
    var begda = calendar_begda.getValue();
    if (begda == '') {
        _getMainFrame().showInfo('请先输入起始时间');
        return;
    }
    
    var endda = calendar_endda.getValue();
    if (endda == '') {
        _getMainFrame().showInfo('请先输入结束时间');
        return;
    }    
	reload_div_southForm_grid_custom(para);
}

//向外暴露重新加载grid方法，支持查询组件参数方式
function reload_div_southForm_grid_custom(urlParmeters)
{
	var paraUrl = encodeURI( contextPath + "/xdss3/vatdetail/vatDetailController.spr?action=queryvatGoodDetailGrid&"+urlParmeters);

//	div_southForm_store.proxy = new Ext.data.HttpProxy({url:paraUrl});

//	div_southForm_store.load({params:{start:0, limit:10},arg:[]});
//	div_southForm_grid.getStore().commitChanges();
	
	var conn=new Ext.data.Connection({
	    url: paraUrl,
	    timeout : 600000, //自定义超时时间，这里是600秒 (默认30s)
	    autoAbort : false,
	    disableCaching : true ,
	    method : "POST"
	});
	div_southForm_store.proxy = new Ext.data.HttpProxy(conn);
	
	div_southForm_store.load( {
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
	div_southForm_grid.getStore().commitChanges();
	
}

/**
 * 清空
 */
function _resetForm()
{
	document.all.mainForm.reset();
}


/**
 * @param value
 * @param metadata
 * @param record
 * @returns {String}
 */
function _manager(value,metadata,record){
    var hrefStr = '';
    hrefStr = '<a href="#" style="color: green;" onclick="_commonMethodOperation(\'1\',\'期初已到税票未进仓(税额)查看\',div_southForm_grid,\'xdss3/vatdetail/vatDetailController.spr?action=_manage,\'\',\'_view\',\'false\')">'+value+'</a>  ';
    return hrefStr;
}

//"FLAG ='4' 期末税金明细
//"FLAG ='3' 期末入库明细
//"FLAG ='2' 期初税金明细
//"FLAG ='1' 期初入库明细
function _view4(){
    _getMainFrame().maintabs.addPanel("期末税金明细",null,contextPath + '/xdss3/vatdetail/vatDetailController.spr?action=_manage&saptype=4');
}
function _view3(){
    _getMainFrame().maintabs.addPanel("期末入库明细",null,contextPath + '/xdss3/vatdetail/vatDetailController.spr?action=_manage&saptype=3');
}
function _view2(){
    _getMainFrame().maintabs.addPanel("期初税金明细",null,contextPath + '/xdss3/vatdetail/vatDetailController.spr?action=_manage&saptype=2');
}
function _view1(){
    _getMainFrame().maintabs.addPanel("期初入库明细",null,contextPath + '/xdss3/vatdetail/vatDetailController.spr?action=_manage&saptype=1');
}


