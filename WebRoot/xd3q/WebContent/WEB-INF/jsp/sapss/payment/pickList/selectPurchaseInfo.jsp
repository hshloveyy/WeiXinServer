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
<form action="" id="findPurchaseForm" name="findPurchaseForm">
<table width="100%">
<tr>
	<td align="center">合同组编码</td>
	<td align="center">合同组名称</td>
	<td align="center">合同编码</td>
	<td align="center">合同名称</td>
	<td align="center">贸易方式</td> 
	<td></td>
	<td></td>
</tr>
<tr>
	<td>
	<input type="hidden" id="deptid" name="deptid" value="${deptid}"></input>
	<input type="hidden" id="orderState" name="orderState" value="${orderState}"></input>
	<input type="text" id="contractGroupNo" name="contractGroupNo" value=""></input>
	</td>
	<td>
	<input type="text" id="contractGroupName" name="contractGroupName" value=""></input>
	</td>
	<td>
	<input type="text" id="contractNo" name="contractNo" value=""></input>
	</td>		
	<td>
	<input type="text" id="contractName" name="contractName" value=""></input>
	</td>
	<td>
	<div id="div_tradeType"></div>
	</td>
	<td>
	<input type="button" value="查找" onclick="findPurchaseInfo()"></input>
	</td>
	<td>
	<input type="reset" value="清空"></input>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_purchase"></div>
</body>
</html>
<script type="text/javascript">
var purchaseds;

var purchasegrid;

function findPurchaseInfo(){
	var param = Form.serialize('findPurchaseForm');
	var requestUrl  = 'pickListInfoController.spr?action=queryPurchaseInfo&' + param;
	requestUrl = requestUrl + "&tradeType=" + dict_div_tradeType.getSelectedValue().trim()

	purchaseds.proxy= new Ext.data.HttpProxy({url: requestUrl});
	purchaseds.load({params:{start:0, limit:20},arg:[]});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	purchaseds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'PROJECT_ID'},
            		{name:'PROJECT_NO'},
					{name:'PROJECT_NAME'},
					{name:'CONTRACT_GROUP_NO'},
            		{name:'CONTRACT_GROUP_NAME'},
					{name:'CONTRACT_PURCHASE_ID'},
					{name:'CONTRACT_NO'},
            		{name:'CONTRACT_NAME'},
					{name:'SAP_ORDER_NO'},
					{name:'EKKO_INCO1'},
					{name:'EKKO_WAERS'},
					{name:'EKKO_ZTERM_D_PAYMENT_TYPE'},
					{name:'EKKO_LIFNR_NAME'},
					{name:'YMAT_GROUP'}
          		])
    });
    
    var purchasesm = new Ext.grid.CheckboxSelectionModel();
    
    var purchasecm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		purchasesm,
		   {header: '合同ID',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_PURCHASE_ID',
           hidden:true
           },
		   {header: '项目ID',
           width: 100,
           sortable: true,
           dataIndex: 'PROJECT_ID',
           hidden:true
           },
		　 {header: '项目编码',
           width: 100,
           sortable: false,
           dataIndex: 'PROJECT_NO'
           },
           {header: '项目名称',
           width: 150,
           sortable: false,
           dataIndex: 'PROJECT_NAME'
           },
           {header: '项目详情',
           width: 150,
           sortable: false,
           dataIndex: 'projectDetail',
           renderer:projectRender
           },
		　 {header: '合同组编码',
           width: 100,
           sortable: false,
           dataIndex: 'CONTRACT_GROUP_NO'
           },
           {header: '合同组名称',
           width: 150,
           sortable: false,
           dataIndex: 'CONTRACT_GROUP_NAME'
           },
           {header: '合同组详情',
           width: 150,
           sortable: false,
           dataIndex: 'contractGroupDetail',
           renderer:contractGroupRender
           },
		　 {header: '合同编码',
           width: 100,
           sortable: false,
           dataIndex: 'CONTRACT_NO'
           },
           {header: '合同名称',
           width: 150,
           sortable: false,
           dataIndex: 'CONTRACT_NAME'
           },
           {header: '合同详情',
           width: 150,
           sortable: false,
           dataIndex: 'contractDetail',
           renderer:contractRender
           },
		　 {header: 'SAP订单号',
           width: 100,
           sortable: false,
           dataIndex: 'SAP_ORDER_NO'
           },
		　 {header: '币种',
           width: 100,
           sortable: false,
           dataIndex: 'EKKO_WAERS'
           }
           ,
		　 {header: '付款条件',
           width: 100,
           sortable: false,
           dataIndex: 'EKKO_ZTERM_D_PAYMENT_TYPE'
           },           
		　 {header: '国际贸易条款',
           width: 100,
           sortable: false,
           hidden: true,
           dataIndex: 'EKKO_INCO1'
           },
           {header: '物料组编号',
           width: 100,
           sortable: false,
           hidden : true,
           dataIndex: 'YMAT_GROUP'
           }        
    ]);
    purchasecm.defaultSortable = true;
    function projectRender(value, p, record){
    	return String.format('<a href="#" onclick="showProject()">立项详情</a>',value);
    }
    function contractGroupRender(value, p, record){
    	return String.format('<a href="#" onclick="showContractGroup()">合同组详情</a>',value);
    }
    function contractRender(value, p, record){
    	return String.format('<a href="#" onclick="showContract()">合同详情</a>',value);
    }
    
    var purchasebbar = new Ext.PagingToolbar({
        pageSize: 20,
        store:purchaseds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
   purchasegrid = new Ext.grid.GridPanel({
    	id:'purchasegrid',
        ds: purchaseds,
        cm: purchasecm,
        sm: purchasesm,
        bbar: purchasebbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_purchase',
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
			contentEl: 'div_purchase',
			buttonAlign:'center',
			items:[purchasegrid],
			buttons:[{
				text:'确定',
				handler:function(){
					if (purchasegrid.selModel.hasSelection()){
						var records = purchasegrid.selModel.getSelections();
						
						if (records.length > 1){
							top.ExtModalWindowUtil.alert('提示','只能选择一个项目信息！');
						}else{
							top.ExtModalWindowUtil.setReturnValue(records[0].data);
							top.ExtModalWindowUtil.markUpdated();
							top.ExtModalWindowUtil.close();
						}
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
	
	
	purchasegrid.on('rowdblclick',function(grid,rowIndex){
  		if (grid.selModel.hasSelection()){
			var records = grid.selModel.getSelections();
			if (records.length > 1){
				top.ExtModalWindowUtil.alert('提示','只能选择一条记录进行修改！');
			}else{
				top.ExtModalWindowUtil.setReturnValue(records[0].data);
				top.ExtModalWindowUtil.markUpdated();
				top.ExtModalWindowUtil.close();
			}
		}
    });
});
function showProject(){
	var records = purchasegrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('查看立项申请','projectController.spr?action=modify&from=view&projectId='+records[0].json.PROJECT_ID,'','',{width:800,height:500});}
function showContractGroup(){
	var records = purchasegrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('查看合同组','projectController.spr?action=showContractGroup&projectId='+records[0].json.PROJECT_ID,'','',{width:605,height:320});}
function showContract(){
	var records = purchasegrid.selModel.getSelections();
	var contractNo= records[0].data.CONTRACT_NO;
	var contractName= records[0].data.CONTRACT_NAME;
	
	top.ExtModalWindowUtil.show(contractName+'-'+contractNo,
			'contractController.spr?action=ArchPurchaseInfoView&businessRecordId='+records[0].data.CONTRACT_PURCHASE_ID,
			'',
			'',
			{width:800,height:550});
}

</script>
<fiscxd:dictionary divId="div_tradeType" fieldName="tradeType" dictionaryName="BM_BUSINESS_TYPE" selectedValue="${tradeType}" width="153"></fiscxd:dictionary>