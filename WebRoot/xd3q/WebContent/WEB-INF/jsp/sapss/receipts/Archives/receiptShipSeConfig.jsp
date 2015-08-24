<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>二次结算物料查询</title>
<style type="text/css">
.add{
	background-image:url(images/fam/add.gif) !important;
}
.delete{
	background-image:url(images/fam/delete.gif) !important;
}
.reset{
	background-image:url(images/fam/refresh.gif) !important;
}
.find{
	background-image:url(images/fam/find.png) !important;
}
</style>
</head>
<body>
<div id="div_northForm">
<form action="" id="findreceiptsFrom" name="findreceiptsFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
	<td align="right">确认时间：</td>
	<td>
		<table><tr><td><div id="sDateDiv"></div></td><td><div id="sDate1Div"></div></td></tr></table>
	</td>
	<td align="right">公司：</td>
	<td>
		<div id="div_companyID"></div>
	</td>
	<td align="right">部门：</td>
	<td>
		<div id="dept"></div>
	</td>
</tr>
<tr>
	<td align="right">业务类型：</td>
	<td>
	<div id="div_tradeType"></div>
	<td align="right">立项号：</td>
	<td>
		<input type="text" id="projectNo" name="projectNo" value=""></input>
	</td>
	<td align="right">合同号：</td>
	<td>
		<input type="text" id="contractNo" name="contractNo" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">纸质合同号：</td>
	<td>
		<input type="text" id="contractPaperNo" name="contractPaperNo" value=""></input>
	</td>
	<td align="right">SAP订单号：</td>
	<td>
		<input type="text" id="sapOrderNo" name="sapOrderNo" value=""></input>
	</td>
	<td align="right">物料组：</td>
	<td>
		<div id="div_materialGroup"></div>
	</td>
</tr>
<tr>
	<td align="right">物料名称：</td>
	<td>
		<input type="text" id="materialName" name="materialName" value=""></input>
	</td>
	<td align="right">销货单位：</td>
	<td>
		<input type="text" id="providerName" name="providerName" value=""></input>
	</td>
	<td align="right">仓库：</td>
	<td>
		<select name="wareHouse" id="wareHouse">
		<option value="">请选择</option>
		<c:forEach items="${wareHouse}" var="row">
			<option value="${row.code}">${row.code}-${row.title}</option>
		</c:forEach>
	    </select>
	</td>
</tr>
<tr>
	<td align="right">进出仓标记：</td>
	<td>
		<select name="mark" id="mark"><option value="">请选择..</option><option value="进仓">进仓</option><option value="出仓">出仓</option></select>
	</td>
	<td align="right">审批标记：</td>
	<td>
		<select name="eState" id="eState"><option value="5" selected="selected">二次结算</option><option value="3">正常出仓</option><option value="">全部</option></select>
	</td>
	<td align="right">批次号：</td>
	<td>
		<input type="text" id="batchNo" name="batchNo" value=""></input>
	</td>	
</tr>
</table>
</form>
</div>

<div id="div_center"></div>

<div id="div_south"></div>

<div id="div_toolbar"></div>

<script type="text/javascript" language="javascript">
<!--
document.onkeydown = function(){if (event.keyCode == 13){findreceiptsInfo();}}

var receiptsRecord;
var purchaserrecord;
var purchasefrieldName;


var receiptsInfogrid;		//进仓单信息列表
var receiptsInfods;

//receipts 查找按钮的单击事件
function findreceiptsInfo(){
	var sDate= Ext.getDom("sDate").value;
	var param = Form.serialize('findreceiptsFrom');
	var requestUrl = 'receiptsController.spr?action=querySeConfig&' + param;
	requestUrl = requestUrl + '&deptId=' + selectId_dept;
	requestUrl = requestUrl + '&sDate=' + sDate;
	receiptsInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	receiptsInfods.load({params:{start:0, limit:10},arg:[]});
}
function _resetForm(){
	document.getElementById('findreceiptsFrom').reset();
}

/**
 * 导出excel
 */
function _expExcel()
{
	var sDate= Ext.getDom("sDate").value;
	var param = Form.serialize('findreceiptsFrom');
	param = param + '&deptId=' + selectId_dept;
	param = param + '&sDate=' + sDate;
	window.location.href(contextPath+'/receiptsController.spr?action=dealSeConfigToExcel&'+param);
}
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	var receiptsInfoPlant = Ext.data.Record.create([
	    {name:'MARK'},
	    {name:'SERIALNO'},
		{name:'SECONFIGTIME'},
		{name:'COMPANY_CODE'},
		{name:'WAREHOUSE_ADD'},
		{name:'DEPT_NAME'},
		{name:'TRADETYPENAME'},
		{name:'PROJECT_NO'},
		{name:'CONTRACT_NO'}, 
		{name:'CONTRACT_PAPER_NO'}, 
		{name:'SAP_ORDER_NO'},  
		{name:'UNIT_NAME'},  
		{name:'MGTITLE'},    
		{name:'MATERIAL_CODE'},     
		{name:'MATERIAL_NAME'},
	    {name:'BATCH_NO'},       
	    {name:'MATERIAL_UNIT'},  
	    {name:'QUANTITY'},
		{name:'PRICE'},
	    {name:'EKPO_PEINH'},
		{name:'CURRENCY'},
		{name:'TOTAL'}
	]);

	receiptsInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'receiptsController.spr?action=querySeConfig'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},receiptsInfoPlant)
    });
    
    var receiptsInfosm = new Ext.grid.CheckboxSelectionModel();    
    var receiptsInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		receiptsInfosm,
		   {
               header: '标识',
               width: 50,
               sortable: true,
               dataIndex: 'MARK'
          },{
               header: '序号',
               width: 80,
               sortable: true,
               dataIndex: 'SERIALNO'
          },
           {
               header: '确认时间',
               width: 100,
               sortable: true,
               dataIndex: 'SECONFIGTIME'
          },{
               header: '公司代码',
               width: 50,
               sortable: true,
               dataIndex: 'COMPANY_CODE'
          },{
               header: '仓库',
               width: 120,
               sortable: true,
               dataIndex: 'WAREHOUSE_ADD'
          },{
               header: '部门',
               width: 80,
               sortable: true,
               dataIndex: 'DEPT_NAME'
          },{
               header: '业务类型',
               width: 100,
               sortable: true,
               dataIndex: 'TRADETYPENAME'
          },{
               header: '立项号',
               width: 80,
               sortable: true,
               dataIndex: 'PROJECT_NO'
          },{
               header: '采购合同号',
               width: 80,
               sortable: true,
               dataIndex: 'CONTRACT_NO'
          },{
               header: '纸质合同号',
               width: 100,
               sortable: true,
               dataIndex: 'CONTRACT_PAPER_NO'
          },{
               header: '订单号',
               width: 80,
               sortable: true,
               dataIndex: 'SAP_ORDER_NO'
          },{
               header: '供应商',
               width: 120,
               sortable: true,
               dataIndex: 'UNIT_NAME'
          },{
               header: '物料组',
               width: 70,
               sortable: true,
               dataIndex: 'MGTITLE'
          },{
               header: '物料编码',
               width: 80,
               sortable: true,
               dataIndex: 'MATERIAL_CODE'
          },{
               header: '物料名称',
               width: 80,
               sortable: true,
               dataIndex: 'MATERIAL_NAME'
          },{
               header: '批次号',
               width: 80,
               sortable: true,
               dataIndex: 'BATCH_NO'
          },{
               header: '单位',
               width: 50,
               sortable: true,
               dataIndex: 'MATERIAL_UNIT'
          },{
               header: '数量',
               width: 80,
               sortable: true,
               dataIndex: 'QUANTITY'
          },{
               header: '单价',
               width: 80,
               sortable: true,
               dataIndex: 'PRICE'
          },{
               header: '定价单位',
               width: 80,
               sortable: true,
               dataIndex: 'EKPO_PEINH'
          },{
               header: '金额',
               width: 80,
               sortable: true,
               dataIndex: 'TOTAL'
          },{
               header: '币别',
               width: 50,
               sortable: true,
               dataIndex: 'CURRENCY'
          }
    ]);
    receiptsInfocm.defaultSortable = true;
    
    var receiptsInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:receiptsInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    receiptsInfogrid = new Ext.grid.EditorGridPanel({
    	id:'receiptsInfoGrid',
        ds: receiptsInfods,
        cm: receiptsInfocm,
        sm: receiptsInfosm,
        bbar: receiptsInfobbar,
        el: 'div_center',
        height: 240,        
        width: window.screen.width-215,        
        anchor:'100%',
        autoScroll:true,
        autoWidth:true
    });
    
    
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
					contentEl:'div_northForm'
				},		
				{
					region:"south",
					layout:'fit',
					border:true,
					autoScroll: true,
					autowidth:true,
		            title:'库存综合查询',
		            height:380,
		            bodyStyle:"background-color:#DFE8F6",
					items:[receiptsInfogrid]
				}]
			}]
	});
    
    
    var wareHouse_combo = new Ext.form.ComboBox({
        typeAhead: true,
        triggerAction: 'all',
        transform:'wareHouse',
        width:157,
        allowBlank:false,
        blankText:'请输入仓库',
        forceSelection:true
     });
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'查询',handler:findreceiptsInfo,iconCls:'icon-search'},'-',
				{id:'_cls',text:'清空',handler:_resetForm,iconCls:'icon-clean'},'-',
				{id:'_exp',text:'导出',handler:_expExcel},'-'
				],
		renderTo:"div_toolbar"
	});
	
	var sDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'sDate',
		name:'sDate',
		width: 90,
		readOnly:true,
	    minValue:'2012-07-01',
		renderTo:'sDateDiv'
   	});
   	var sDate1 = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'sDate1',
		name:'sDate1',
		width: 90,
	    readOnly:true,
	    minValue:'2012-07-01',
		renderTo:'sDate1Div'
   	});
   	
   	
});

--></script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155" isMutilSelect="true"></fiscxd:dept>
<fiscxd:dictionary divId="div_tradeType" fieldName="tradeType" dictionaryName="BM_BUSINESS_TYPE" width="153"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_companyID" fieldName="companyID" dictionaryName="BM_COMPANY_CODE" width="153"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_materialGroup" fieldName="materialGroup" dictionaryName="BM_MATERIAL_GROUP" width="153"></fiscxd:dictionary>
</body>
</html>