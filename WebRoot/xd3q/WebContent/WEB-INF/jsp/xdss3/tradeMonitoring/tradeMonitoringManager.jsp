<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年04月21日 15点06分41秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>贸易监控管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ page import="com.infolion.xdss3.tradeMonitoring.domain.XdssBusinessType" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>贸易监控</title>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
   <tr>
		<td width="15%" align="right">查询字段：</td>
		<td width="15%" >
			<div id="div_QueryFieldDict"></div>
			<fisc:dictionary hiddenName="queryField" dictionaryName="YDTRADEQUERYFIELD" divId="div_QueryFieldDict" isNeedAuth="false" ></fisc:dictionary>
		</td>
		<td width="15%" align="right">
		<input type="text" id="queryValue" name="queryValue"></input>
		</td>
		<td width="55%">
		</td>
	</tr>
	<tr>
		<td align="center" colspan="4" height="10"></td>
	</tr>	
</table>
</form>
</div>
<div id="div_southForm"></div>
</body>
</html>
<script type="text/javascript">
var isCreateCopy=false;
// 全局路径
var context = '<%= request.getContextPath() %>';
var tradeMonitoringColumnTree ; // 贸易监控树
var tradeMonitoringTreeLoader ;

    // example of custom renderer function
  function change(val)
  {
    if (val > 0) {
      val = '<span style="color:green;">' + val + '</span>';
    } 
    else if(val < 0) {
            val = '<span style="color:red;">' + val + '</span>';
    }
    return val;
  }

  // example of custom renderer function
  function pctChange(val)
  {
    if (val > 0) {
      val = '<span style="color:green;">' + val + '%</span>';
    } 
    else if(val < 0) {
      val = '<span style="color:red;">' + val + '%</span>';
    }
    return val;
  }
  
function _search()
{

}

function _resetForm()
{
}

/**
 * EXT 布局
 */
Ext.onReady(function(){

    // create the data store
    var record = Ext.data.Record.create([
        {name: 'nodeId'},
        {name: 'nodeName'},
        {name: 'businessType'},
        {name: 'tradeType'},
        {name: 'businessTypeName'},
        {name: 'businessNo'},
        {name: 'businessName'},
        {name: 'sdate'},
        {name: 'remark'},
        {name: 'processState'},
        {name: 'url'},
        //{name: '_id', type: 'int'},
        //{name: '_level', type: 'int'},
        {name: '_parent', type: 'auto'},
        {name: '_lft', type: 'int'},
        {name: '_rgt', type: 'int'},
        {name: '_is_leaf', type: 'bool'}
    ]);
    var store = new Ext.ux.maximgb.treegrid.AdjacencyListStore({
        autoLoad : true,
        url: context+'/tradeMonitoringController.spr?action=getTradeMonitoringGridData',
        params :{'nodeId': 'BBBB-BBBB-BBBB-BBB-1ADFD-BBBB'},
         reader: new Ext.data.JsonReader(
                {
                    id: 'nodeId',
                    root: 'data',
                    totalProperty: 'total',
                    successProperty: 'success'
                }, 
                record
            )
    });
    // create the Grid
    var grid = new Ext.ux.maximgb.treegrid.GridPanel({
      store: store,
      master_column_id : 'nodeName',
      columns: [
                {id:'nodeId',header: "nodeId", width: 160, sortable: true, dataIndex: 'nodeId',hidden:true},                 
                {id:'nodeName',header: "nodeName", width: 160, sortable: true, dataIndex: 'nodeName'},
                {id:'businessType',header: "businessType", width: 160, sortable: true, dataIndex: 'businessType'},    
                {id:'tradeType',header: "tradeType", width: 160, sortable: true, dataIndex: 'tradeType'}, 
                {id:'businessTypeName',header: "businessTypeName", width: 160, sortable: true, dataIndex: 'businessTypeName'}, 
                {id:'businessNo',header: "businessNo", width: 160, sortable: true, dataIndex: 'businessNo'}, 
                {id:'businessName',header: "businessName", width: 160, sortable: true, dataIndex: 'businessName'},                 
                {id:'sdate',header: "sdate", width: 160, sortable: true, dataIndex: 'sdate'},         
                {id:'remark',header: "remark", width: 160, sortable: true, dataIndex: 'remark'},                 
                {id:'processState',header: "processState", width: 160, sortable: true, dataIndex: 'processState'},                 
                {id:'url',header: "url", width: 160, sortable: true, dataIndex: 'url'}              
      ],
      stripeRows: true,
      autoExpandColumn: 'nodeName',
      title: 'Nested set server grid.',
      root_title: 'Companies',
      bbar: new Ext.ux.maximgb.treegrid.PagingToolbar({
        store: store,
        displayInfo: true,
        pageSize: 2
      })
    });
    
    var vp = new Ext.Viewport({
        layout : 'fit',
        items : grid
    });
    
    grid.getSelectionModel().selectFirstRow();
    
  
	 /**
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			items:[{
					region:"north",
					contentEl:'div_toolbar',
					border:false,
					height:26
				},{
					region:'center',
					border:false,
					contentEl:'div_center'
				},		
				{
					region:"south",
					layout:'fit',
					border:false,
					autoScroll: false,
		            title:'信息列表详情',
		            height:580,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'div_southForm'
				}]
			}]
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'查询',handler:_search,iconCls:'icon-search'},'-',
				{id:'_cls',text:'清空',handler:_resetForm,iconCls:'icon-clean'},'-'
				],
		renderTo:"div_toolbar"
	});
    **/
  
	
});
</script>
