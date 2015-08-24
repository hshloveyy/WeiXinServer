<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.add{
	background-image:url(images/fam/add.gif) !important;
}
.delete{
	background-image:url(images/fam/delete.gif) !important;
}
.update{
	background-image:url(images/fam/refresh.gif) !important;
}
.find{
	background-image:url(images/fam/find.png) !important;
}
</style>
</head>
<body>
<div id="div_north" style="width:100%;height:100%">
<form action="" id="findInnerContractForm" name="findInnerContractForm">
<table width="100%">
<tr>
	<td>合同号</td>
	<td>合同名称</td>
	<td>立项号</td> 
	
	<td>${radioTitle}</td>
	<td></td>
</tr>
<tr>
	<td>
	<input type="hidden" id="deptId" name="deptId" value="${deptId}"></input>
	<input type="hidden" id="payType" name="payType" value="${payType}"></input>
	<input type="hidden" id="projectId" name="projectId" value="${projectId}"></input>
	<input type="text" id="contractNo" name="contractNo" value="" size=15></input>
	</td>		
	<td>
	<input type="text" id="contractName" name="contractName" value="" size=15></input>
	</td>
	<td>
	${projectNo }
	</td>
	<td>
	${radio}
	</td>
	<td>
	<input type="button" value="查找" onclick="findInnerContract()"></input>
	<input type="reset" value="清空"></input>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_sales"></div>
</body>
</html>
<script type="text/javascript">
var innerContractds;
var innerContractgrid;


function findInnerContract(){
	var param = Form.serialize('findInnerContractForm');
	var requestUrl  = 'innerTradePayController.spr?action=queryInnerPayContract&' + param;
	//var requestUrl  = 'receiptsController.spr?action=queryInnerPayContract&' + param;
	//requestUrl = requestUrl + "&tradeType=" + Ext.getDom('tradeType').value;
	requestUrl = requestUrl + "&tradeType=";

	innerContractds.proxy= new Ext.data.HttpProxy({url: requestUrl});
	innerContractds.load({params:{start:0, limit:15},arg:[]});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	innerContractds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
					{name:'CONTRACT_TYPE'},                 
					{name:'PROJECT_NO'},                    
					{name:'PROJECT_NAME'},                   
				    {name:'CONTRACT_GROUP_NO'},               
					{name:'SAP_ORDER_NO'},
					{name:'CONTRACT_PURCHASE_ID'},
					{name:'DEPT_ID'}, 
					{name:'ORDER_STATE_D_ORDER_STATE'},    
					{name:'CMD'},    
					{name:'PAPER_NO'},             
					{name:'CONTRACT_INFO'},                    
					{name:'APPROVED_TIME'},                   
				    {name:'IS_AVAILABLE'},               
					{name:'CREATOR_TIME'},                       
					{name:'CREATOR'},	
					{name:'CONTRACT_NAME'},	
					{name:'EXPORTINFO'},
					{name:'TOTAL'},
					{name:'CONTRACT_NO'},
					{name:'PROVIDER'}
          		])
    });
    
    var innerContractsm = new Ext.grid.CheckboxSelectionModel();
    
    var innerContractcm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		innerContractsm,
		   {header: '合同ID',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_PURCHASE_ID',
           hidden:true
		   },
		   {header: '合同类型',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_TYPE',
           hidden:true
           },                    
		   {header: '项目号',
           width: 100,
           sortable: false,
           dataIndex: 'PROJECT_NO'
           },
           {header: '项目名称',
           width: 150,
           sortable: false,
           dataIndex: 'PROJECT_NAME'
           },
		   {header: '合同组编码',
           width: 100,
           sortable: false,
           dataIndex: 'CONTRACT_GROUP_NO'
           },           
		   {header: '合同号',
           width: 100,
           sortable: false,
           dataIndex: 'CONTRACT_NO'
           },
           {header: '合同名称',
           width: 150,
           sortable: false,
           dataIndex: 'CONTRACT_NAME'
           },
   		{
   			header: '合同详情',
   			width: 100,
   			sortable: true,
   			dataIndex: 'CONTRACT_INFO',
   			renderer:contractOper
   		},
           {header: 'SAP订单号',
           width: 150,
           sortable: false,
           dataIndex: 'SAP_ORDER_NO'
           },
           {header: '审批通过时间',
           width: 150,
           sortable: false,
           dataIndex: 'APPROVED_TIME'
           },
           {header: '总金额',
           width: 150,
           sortable: false,
           dataIndex: 'TOTAL'
           },
   		{
   			header: '合同状态',
   			width: 100,
   			sortable: true,
   			dataIndex: 'ORDER_STATE_D_ORDER_STATE'
   		},
   		{
   			header: '纸质合同号',
   			width: 100,
   			sortable: true,
   			dataIndex: 'PAPER_NO'
   		}
    ]);
    innerContractcm.defaultSortable = true;
    
    var innerContractbbar = new Ext.PagingToolbar({
        pageSize: 15,
        store:innerContractds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    innerContractgrid = new Ext.grid.GridPanel({
    	id:'innerContractgrid',
        ds: innerContractds,
        cm: innerContractcm,
        sm: innerContractsm,
        bbar: innerContractbbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_sales',
        autowidth:true,
        layout:'fit'
    });
   		
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			height:40,
			contentEl:'div_north'
		},{
			region:"center",
			layout:'border',
			contentEl: 'div_sales',
			buttonAlign:'center',
			items:[innerContractgrid],
			buttons:[{
				text:'确定',
				handler:function(){
					if (innerContractgrid.selModel.hasSelection()){
						var records = innerContractgrid.selModel.getSelections();
						//top.ExtModalWindowUtil.setReturnValue(records[0].data);
						top.ExtModalWindowUtil.setReturnValue(records);
						top.ExtModalWindowUtil.markUpdated();
						top.ExtModalWindowUtil.close();
					}
					else{
						top.ExtModalWindowUtil.alert('提示','请选择指定的项目信息！');
					}
				}
			},{
				text:'关闭',
				handler:function(){
					top.ExtModalWindowUtil.close();
				}
			}]
		}]
	});
	
    function contractOper(value, p, record){
    	return String.format('<a href="#" onclick="openContractWin()">{0}</a>',value);
    }
    	
	innerContractgrid.on('rowdblclick',function(grid,rowIndex){
  		if (grid.selModel.hasSelection()){
			var records = grid.selModel.getSelections();
			//top.ExtModalWindowUtil.setReturnValue(records[0].data);
			top.ExtModalWindowUtil.setReturnValue(records);
			top.ExtModalWindowUtil.markUpdated();
			top.ExtModalWindowUtil.close();
		}
    });
});

function openContractWin(){
	var records = innerContractgrid.selModel.getSelections();
	var contractType= records[0].data.CONTRACT_TYPE;
	var contractNo= records[0].data.CONTRACT_NO;
	var contractName= records[0].data.CONTRACT_NAME;
	var state= records[0].data.ORDER_STATE_D_ORDER_STATE;
	
	if (records[0].data.CONTRACT_TYPE.trim() == '采购合同'){
		top.ExtModalWindowUtil.show(contractType+'-'+contractNo+'-'+contractName+'-'+state,
			'contractController.spr?action=ArchPurchaseInfoView&businessRecordId='+records[0].data.CONTRACT_PURCHASE_ID,
			'',
			'',
			{width:800,height:550});
	}

	if (records[0].data.CONTRACT_TYPE.trim() == '销售合同'){
		top.ExtModalWindowUtil.show(contractType+'-'+contractNo+'-'+contractName+'-'+state,
		'contractController.spr?action=addSaleContractView&contractid='+records[0].data.CONTRACT_PURCHASE_ID,
		'',
		'',
		{width:800,height:600});
	}
}
</script>
