Ext.onReady(function() {
    
    //千分位
    var re=/(\d{1,3})(?=(\d{3})+(?:$|\.))/g;
    var order_totalIndex = Collect_grid.getColumnModel().findColumnIndex('ORDER_TOTAL');
    Collect_grid.getColumnModel().setRenderer(order_totalIndex,function(value,css,record,rowIndex,colIndex,store){
        return value.replace(re,"$1,");
    });
    var margin_ratioIndex = Collect_grid.getColumnModel().findColumnIndex('MARGIN_RATIO');
    Collect_grid.getColumnModel().setRenderer(margin_ratioIndex,function(value,css,record,rowIndex,colIndex,store){
        return value.replace(re,"$1,");
    });
    var suretybondIndex = Collect_grid.getColumnModel().findColumnIndex('SURETYBOND');
    Collect_grid.getColumnModel().setRenderer(suretybondIndex,function(value,css,record,rowIndex,colIndex,store){
        return value.replace(re,"$1,");
    });
//    var remainsuretybondIndex = Collect_grid.getColumnModel().findColumnIndex('remainsuretybond');
//    Collect_grid.getColumnModel().setRenderer(remainsuretybondIndex,function(value,css,record,rowIndex,colIndex,store){
//        return value.replace(re,"$1,");
//    });
    

    /**
     * 1、业务类型
     */
    var arr = [ [ "'1'", '外贸自营进口*业务' ], [ "'2'", '外贸自营出口*业务' ], [ "'3'", '外贸自营进口业务' ], [ "'4'", '外贸自营出口业务' ],
            [ "'5'", '外贸代理出口业务' ], [ "'6'", '外贸代理进口业务' ], [ "'7'", '内贸业务' ], [ "'8'", '进料加工业务内销' ],
            [ "'9'", '自营进口敞口业务' ], [ "'10'", '内贸敞口' ], [ "'11'", '转口业务' ], [ "'12'", '进料加工业务外销' ] ];
    var arr2 = [ [ "*", "*" ] ];
    var t_typeField = new Ext.form.MultiSelectField({
        applyTo : 't_typeMultiselect',
        hiddenName : 'trade_type',
        contextArray : arr,
        fieldLabel : '11',
        id : 't_typeField',
        defaltValueArray : arr2,
        name : 't_typeField'
    });
    
    /**
     * 2、立项状态
     */
    var arrP = [ ["'1'",'立项'],
                ["'2'",'审批中'],
                ["'3'",'生效'],
                ["'4'",'结项'],
                ["'5'",'归档'],
                ["'6'",'变更'],
                ["'7'",'作废'],
                ["'8'",'变更通过'],
                ["'9'",'关闭'] ];
    var arr2P = [ [ "*", "*" ] ];
    var t_typeField = new Ext.form.MultiSelectField({
        applyTo : 't_projectstateMultiselect',
        hiddenName : 'project_state',
        contextArray : arrP,
        fieldLabel : '11',
        id : 't_typeField',
        defaltValueArray : arr2P,
        name : 't_typeField'
    });

    _search();
});

/**
 * 查询
 */
function _search() {
    var para = Form.serialize('mainForm');
    reload_Collect_grid2(para);
}

/**
 * 清空
 */
function _clear() {
    document.all.mainForm.reset();
}

function _viewVoucher() {
    if (Collect_grid.selModel.hasSelection() == 1) {
        var records = Collect_grid.selModel.getSelections();
        var id = records[0].json.collectid;
        _getMainFrame().maintabs.addPanel("查看凭证", null, contextPath
                + '/xdss3/voucher/voucherController.spr?action=_manage&businessid=' + id);
    }
}

function reload_Collect_grid2(urlParmeters) {
    var paraUrl = encodeURI(context
            + "/xdss3/collect/collectController.spr?action=_suretyBondContractQuery")
            + "&" + urlParmeters;
    Collect_store.proxy = new Ext.data.HttpProxy({
        url : paraUrl
    });
    Collect_store.load({
        params : {
            start : 0,
            limit : 10
        },
        arg : []
    });
    Collect_grid.getStore().commitChanges();
}





/**
 * 导出excel
 */
function _expExcel()
{
    var para = Form.serialize('mainForm');
    para = encodeURI(para);
    window.location.href(contextPath+'/xdss3/collect/collectController.spr?action=_suretyBondContractToExcel&'+para);
}
