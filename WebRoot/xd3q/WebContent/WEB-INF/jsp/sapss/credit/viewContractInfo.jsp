<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同组查询页面</title>
</head>
<body>
<div id="grid"></div>
</body>
</html>
<script type="text/javascript">
//信用证ID
var creditId = '${creditId}';

//贸易类型
var tradeType = '${tradeType}';
//合同类型 S 销售 P 采购
var contractType='${contractType}';
var contractgrid;
var strTitle ='';

strTitle = Utils.getTradeTypeTitle(tradeType);

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
    
    var requestUrl;
    if(contractType=="P") //采购合同
    {
   	    requestUrl = 'creditEntryController.spr?action=viewPurchaseListByCreditId&creditId='+ creditId;
    }
    else //销售合同
    {
       	requestUrl = 'creditArriveController.spr?action=viewSalesListByCreditId&creditId='+ creditId;
    }
    
    //alert(contractType);
         	
	contractds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:requestUrl}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'CONTRACT_ID'},
					{name:'CREDIT_ID'},
					{name:'CONTRACT_NO'},
            		{name:'CONTRACT_NAME'},
					{name:'SAP_ORDER_NO'},
					{name:'viewContract'},
					{name:'YMAT_GROUP'}
          		])
    });
    
    var contractsm = new Ext.grid.CheckboxSelectionModel();
    
    var contractcm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		contractsm,
		   {
			   header: '合同ID',
	           width: 100,
	           sortable: true,
	           dataIndex: 'CONTRACT_ID',
	           hidden:true
           },
           {
	           header: '信用证ID',
	           width: 100,
	           sortable: true,
	           dataIndex: 'CREDIT_ID',
	           hidden:true
           },
		　 {
			   header: '合同编码',
	           width: 100,
	           sortable: true,
	           dataIndex: 'CONTRACT_NO'
           },
           {   
               header: '合同名称',
	           width: 180,
	           sortable: false,
	           dataIndex: 'CONTRACT_NAME'
           },
           {   
               header: 'SAP订单号',
	           width: 150,
	           sortable: false,
	           dataIndex: 'SAP_ORDER_NO'
           },
           {
           	   header: '查看合同',
	           width: 100,
	           sortable: true,
	           dataIndex: 'viewContract',
	           renderer: viewContract
           },
           {header: '物料组编号',
           width: 100,
           sortable: false,
           hidden : true,
           dataIndex: 'YMAT_GROUP'
           }  
                           
    ]);
    
    contractcm.defaultSortable = true;
    
    var contractbbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:contractds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    contractgrid  = new Ext.grid.GridPanel({
    	id:'contractgrid',
        ds: contractds,
        cm: contractcm,
        sm: contractsm,
        bbar: contractbbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'grid',
        autowidth:true,
        layout:'fit'
    });
   		
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			layout:'border',
			contentEl: 'grid',
			buttonAlign:'center',
			items:[contractgrid],
			buttons:[{
				text:'关闭',
				handler:function(){
					top.ExtModalWindowUtil.close();
				}
			}]
		}]
	});
	
	contractds.load({params:{start:0, limit:10},arg:[]});
	
});


//查看采购,销售合同详细信息
function openContractWinByReadOnly()
{
	if(contractgrid.selModel.hasSelection())
    {
        var records = contractgrid.selModel.getSelections();
		if (contractType=='P'){
			top.ExtModalWindowUtil.show("查看" + strTitle + '采购合同信息',
				'contractController.spr?action=ArchPurchaseInfoView&businessRecordId='+records[0].data.CONTRACT_ID,
				'','',
				{width:900,height:550});
		}
	
		if (contractType=='S'){
			top.ExtModalWindowUtil.show("查看" +  strTitle + '销售合同信息',
			'contractController.spr?action=archSalesInfoView&businessRecordId='+records[0].data.CONTRACT_ID,
			'','',
			{width:900,height:550});
		}
	}
}

//查看合同
function viewContract(value,metadata,record)
{
	return '<a href="#" onclick="openContractWinByReadOnly();" >查看合同</a>';
}

</script>