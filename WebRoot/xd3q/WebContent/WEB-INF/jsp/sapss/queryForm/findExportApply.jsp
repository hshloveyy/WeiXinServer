<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.add {
	background-image: url(images/fam/add.gif) !important;
}

.delete {
	background-image: url(images/fam/delete.gif) !important;
}

.update {
	background-image: url(images/fam/refresh.gif) !important;
}

.find {
	background-image: url(images/fam/find.png) !important;
}
</style>
</head>
<body>
<div id="div_north" style="width: 100%; height: 100%">
<form action="" id="findExportApplyForm" name="findExportApplyForm">
<table width="100%">
	<tr>
		<td align="center">通知单号</td>
		<td align="center">立项号</td>
		<td align="center">立项名称</td>
		<td align="center">合同号</td>
		<td align="center">合同名称</td>
		<td align="center">贸易方式</td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td><input type="hidden" id="deptid" name="deptid"
			value="${deptid}"></input> <input type="hidden" id="examineState"
			name="examineState" value="${examineState}"></input> <input
			type="text" id="noticNo" name="noticNo" value="" size=15></input></td>
		<td><input type="text" id="projectNo" name="projectNo" value=""
			size=15></input></td>
		<td><input type="text" id="projectName" name="projectName"
			value="" size=15></input></td>
		<td><input type="text" id="contractNo" name="contractNo" value=""
			size=15></input></td>
		<td><input type="text" id="contractName" name="contractName"
			value="" size=15></input></td>
		<td>
		<div id="div_tradeType"></div>
		</td>
		<td><input type="button" value="查找" onclick="findExportApply()"></input>
		</td>
		<td><input type="reset" value="清空"></input></td>
	</tr>
</table>
</form>
</div>

<div id="div_sales"></div>
</body>
</html>
<script type="text/javascript">
var exportApplyds;

function findExportApply(){
	var param = Form.serialize('findExportApplyForm');
	var requestUrl  = 'shipQueryController.spr?action=queryExportApply&' + param;
	requestUrl = requestUrl + "&tradeType=" + dict_div_tradeType.getSelectedValue().trim()

	exportApplyds.proxy= new Ext.data.HttpProxy({url: requestUrl});
	exportApplyds.load({params:{start:0, limit:20},arg:[]});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	exportApplyds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
					{name:'EXPORT_APPLY_ID'}, 
					{name:'CONTRACT_SALES_ID'},                 
					{name:'PROJECT_NO'},                    
					{name:'PROJECT_NAME'},                   
				    {name:'CONTRACT_GROUP_NO'},               
					{name:'SALES_NO'},                       
					{name:'SAP_ORDER_NO'},
					{name:'VBKD_IHREZ'},//vbkdIhrez
					{name:'PAYER'},
					{name:'PAYER_NAME'},
					{name:'KUAGV_KUNNR'},
					{name:'KUAGV_KUNNR_NAME'},
					{name:'KUWEV_KUNNR'},		//kuwevKunnr
					{name:'KUWEV_KUNNR_NAME'},	//kuwevKunnrName
					{name:'BILL_TO_PARTY'},		
					{name:'BILL_TO_PARTY_NAME'},	
					{name:'BILL_STATE'},                     
					{name:'NOTICE_NO'},   
					{name:'EXCHANGE_TYPE'}, 
					{name:'ORIGIN'},                 
					{name:'SHIPMENT_DATE'},                    
					{name:'LCNO'},                   
				    {name:'COMMITMENT'},               
					{name:'EXPORT_PORT'},                       
					{name:'WRITE_NO'},
					{name:'SUPPLIER'},                     
					{name:'DESTINATIONS'},  
					{name:'TRADE_TERMS'},   
					{name:'DEPT_ID'}, 
					{name:'EXAMINE_STATE'},    
					{name:'EXAMINE_STATE_D_EXAMINE_STATE'},              
					{name:'APPLY_TIME'},                    
					{name:'APPROVED_TIME'},                   
				    {name:'IS_AVAILABLE'},               
					{name:'CREATOR_TIME'},                       
					{name:'CREATOR'},	
					{name:'CONTRACT_NAME'},	
					{name:'EXPORTINFO'}  
          		])
    });
    
    var exportApplysm = new Ext.grid.CheckboxSelectionModel();
    
    var exportApplycm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		exportApplysm,
		   {header: '出货通知单ID',
           width: 100,
           sortable: true,
           dataIndex: 'EXPORT_APPLY_ID',
           hidden:true
           },
		   {header: '合同ID',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_SALES_ID',
           hidden:true
           },  
		　 {header: '通知单号',
           width: 80,
           sortable: false,
           dataIndex: 'NOTICE_NO'
           },                    
		　 {header: '项目号',
           width: 80,
           sortable: false,
           dataIndex: 'PROJECT_NO'
           },
           {header: '项目名称',
           width: 120,
           sortable: false,
           dataIndex: 'PROJECT_NAME'
           },
		　 {header: '合同组编码',
           width: 100,
           sortable: false,
           dataIndex: 'CONTRACT_GROUP_NO'
           },           
		　 {header: '合同编码',
           width: 100,
           sortable: false,
           dataIndex: 'SALES_NO'
           },
           {header: '合同名称',
           width: 120,
           sortable: false,
           dataIndex: 'CONTRACT_NAME'
           } ,
           {header: 'SAP订单号',
           width: 150,
           sortable: false,
           dataIndex: 'SAP_ORDER_NO'
           },
            {header: '纸质合同号',
           width: 80,
           sortable: false,
           dataIndex: 'VBKD_IHREZ'
           },
           {header: '付款方',
           width: 100,
           sortable: false,
           dataIndex: 'PAYER'
           },
           {header: '付款方名称',
           width: 100,
           sortable: false,
           dataIndex: 'PAYER_NAME'
           },
		   {header: '售达方',
           width: 100,
           sortable: false,
           dataIndex: 'KUAGV_KUNNR'
           },
           {header: '售达方名称',
           width: 100,
           sortable: false,
           dataIndex: 'KUAGV_KUNNR_NAME'
           },
      	   {header: '送达方',
           width: 100,
           sortable: false,
           dataIndex: 'KUWEV_KUNNR'
           },
           {header: '送达方名称',
           width: 100,
           sortable: false,
           dataIndex: 'KUWEV_KUNNR_NAME'
           },
      	   {header: '收票方',
           width: 100,
           sortable: false,
           dataIndex: 'BILL_TO_PARTY'
           },
           {header: '收票方名称',
           width: 100,
           sortable: false,
           dataIndex: 'BILL_TO_PARTY_NAME'
           }            
    ]);
    exportApplycm.defaultSortable = true;
    
    var exportApplybbar = new Ext.PagingToolbar({
        pageSize: 20,
        store:exportApplyds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
   var exportApplygrid = new Ext.grid.GridPanel({
    	id:'exportApplygrid',
        ds: exportApplyds,
        cm: exportApplycm,
        sm: exportApplysm,
        bbar: exportApplybbar,
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
			items:[exportApplygrid],
			buttons:[{
				text:'确定',
				handler:function(){
					checkSelect(exportApplygrid,0);
				}
			},{
				text:'关闭',
				handler:function(){
					top.ExtModalWindowUtil.close();
				}
			}]
		}]
	});
	
	exportApplygrid.on('rowdblclick',function(grid,rowIndex){
  		checkSelect(grid,rowIndex);
    });
    
    function checkSelect(grid,rowIndex)
    {
    	if (grid.selModel.hasSelection()){
			var records = grid.selModel.getSelections();
			if (records.length < 1){
				top.ExtModalWindowUtil.alert('提示','请至少选择一张出仓通知单！');
			}else{
									
				var i, num=records.length;
				var contractSalesId = records[0].data.CONTRACT_SALES_ID;
				for (i=1; i<num ;i++)
				{
					if(contractSalesId != records[i].data.CONTRACT_SALES_ID)
					{
						top.ExtModalWindowUtil.alert('提示','只能选择一张销售合同！');
						return;
					} 
				}
				
				top.ExtModalWindowUtil.setReturnValue(records);
				top.ExtModalWindowUtil.markUpdated();
				top.ExtModalWindowUtil.close();
			}
		}else{
			top.ExtModalWindowUtil.alert('提示','请选择指定的项目信息！');
		}
    }
});
</script>
<fiscxd:dictionary divId="div_tradeType" fieldName="tradeType"
	dictionaryName="BM_BUSINESS_TYPE" selectedValue="${tradeType}"
	width="153"></fiscxd:dictionary>