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
<form action="" id="findsalesForm" name="findPurchaseForm">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
	<td align="center">合同组编码</td>
	<td align="center">合同组名称</td>
	<td align="center">合同编码</td>
	<td align="center">合同名称</td>
	<td align="center">纸质合同号</td>
	<td align="center">贸易方式</td> 
	<td></td>
	<td></td>
</tr>
<tr>
	<td>
	<input type="hidden" id="deptid" name="deptid" value="${deptid}"></input>
	<input type="hidden" id="orderState" name="orderState" value="${orderState}"></input>
	<input type="text" id="contractGroupNo" name="contractGroupNo" value="" size="13"></input>
	</td>
	<td>
	<input type="text" id="contractGroupName" name="contractGroupName" value="" size="13"></input>
	</td>
	<td>
	<input type="text" id="contractNo" name="contractNo" value="" size="13"></input>
	</td>		
	<td>
	<input type="text" id="contractName" name="contractName" value="" size="13"></input>
	</td>
	<td>
	<input type="text" id="ekkoUnsez" name="ekkoUnsez" value="" size="13"></input>
	</td>
	<td>
	<div id="div_tradeType"></div>
	</td>
	<td>
		<input type="button" value="查找" onclick="findPurchaseInfo()"></input>
	</td>
	<td>
		<input type="button" id ="btnClear" value="清空" onclick="javascript:reclear();"></input>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_Purchase"></div>
</body>
</html>
<script type="text/javascript">
var Purchaseds;
var Purchasesm;
var contractNo = '${contractNo}';
var Purchasegrid;

function reclear()
{
   Ext.getDom('contractGroupNo').value = "";
   Ext.getDom('contractNo').value = "";      
   Ext.getDom('contractName').value = "";
   Ext.getDom('contractGroupName').value = "";
   Ext.getDom('ekkoUnsez').value="";
}

function findPurchaseInfo(){
    var sm =Purchasegrid.getSelectionModel();
    //被选中的合同号列表
    var contractNoLst = contractNo.split(",");  
    var n = contractNoLst.length;  
    
    var param = Form.serialize('findPurchaseForm');
    var requestUrl  = 'creditEntryController.spr?action=queryPurchaseInfo&' + param;
    requestUrl = requestUrl + "&tradeType=" + dict_div_tradeType.getSelectedValue().trim();

    Purchaseds.removeAll();

	Purchaseds.proxy= new Ext.data.HttpProxy({url: requestUrl});
  
	Purchaseds.load({
    params:{start:0, limit:20},
    callback:function(records, options, success){
        var records = Purchaseds.getRange(0, 1);
         
        for (var i = 0; i < records.length; i++)
	    {
	        var record = records[i];
            for(var j = 0;j < n ; j++)
            {
                var index = Purchaseds.find('CONTRACT_NO',contractNoLst[j]);
                if(index>=0)
                {
                  sm.selectRow(index, true);
                }
//	            if(record.get('CONTRACT_NO')==contractNoLst[j])
//	            {
//	              sm.selectRow(i, true)  
//	            }
            }
	    }
    }
    ,
    arg:[],
    add: false
    });
}


Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	Purchaseds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
                    {name:'CHECKED'},
            		{name:'PROJECT_NO'},
					{name:'PROJECT_NAME'},
					{name:'CONTRACT_GROUP_NO'},
            		{name:'CONTRACT_GROUP_NAME'},
					{name:'CONTRACT_PURCHASE_ID'},
					{name:'CONTRACT_NO'},
            		{name:'CONTRACT_NAME'},
            		{name:'EKKO_UNSEZ'},
					{name:'SAP_ORDER_NO'},
					{name:'YMAT_GROUP'}
          		])
    });
    
    Purchasesm = new Ext.grid.CheckboxSelectionModel();

    var Purchasecm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		Purchasesm,
		   {header: '合同ID',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_PURCHASE_ID',
           hidden:true
           },
		　 {header: '项目编码',
           width: 60,
           sortable: true,
           dataIndex: 'PROJECT_NO'
           },
           {header: '项目名称',
           width: 150,
           sortable: false,
           dataIndex: 'PROJECT_NAME'
           },
		　 {header: '合同组编码',
           width: 80,
           sortable: false,
           dataIndex: 'CONTRACT_GROUP_NO'
           },
           {header: '合同组名称',
           width: 150,
           sortable: false,
           dataIndex: 'CONTRACT_GROUP_NAME'
           },
		　 {header: '合同编码',
           width: 80,
           sortable: false,
           dataIndex: 'CONTRACT_NO'
           },
           {header: '合同名称',
           width: 150,
           sortable: false,
           dataIndex: 'CONTRACT_NAME'
           },
           {header: '纸质合同号',
           width: 150,
           sortable: false,
           dataIndex: 'EKKO_UNSEZ'
           },
		　 {header: 'SAP订单号',
           width: 100,
           sortable: false,           
           dataIndex: 'SAP_ORDER_NO'
           },
           {header: '物料组编号',
           width: 100,
           sortable: false,
           hidden : true,
           dataIndex: 'YMAT_GROUP'
           }                          
    ]);
    
    Purchasecm.defaultSortable = true;
    
    var Purchasebbar = new Ext.PagingToolbar({
        pageSize: 20,
        store:Purchaseds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
      
    Purchasegrid = new Ext.grid.GridPanel({
    	id:'Purchasegrid',
        ds: Purchaseds,
        cm: Purchasecm,
        sm: Purchasesm,
        bbar: Purchasebbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_Purchase',
        autowidth:true,
        layout:'fit'
    });
   		
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			height:45,
			contentEl:'div_north'
		},{
			region:"center",
			layout:'border',
			contentEl: 'div_Purchase',
			buttonAlign:'center',
			items:[Purchasegrid],
			buttons:[{
				text:'确定',
				handler:function(){
					if (Purchasegrid.selModel.hasSelection()){
						var records = Purchasegrid.selModel.getSelections();

						if (records.length < 1){
							top.ExtModalWindowUtil.alert('提示','请至少选择一个合同信息！');
						}else{
							top.ExtModalWindowUtil.setReturnValue(records);
							top.ExtModalWindowUtil.markUpdated();
							top.ExtModalWindowUtil.close();
						}
					}
					else{
						top.ExtModalWindowUtil.alert('提示','请选择指定的合同信息！');
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
	
});
</script>
<fiscxd:dictionary divId="div_tradeType" fieldName="tradeType" dictionaryName="BM_BUSINESS_TYPE" selectedValue="${tradeType}" width="153" readonly="true" disable="${!empty tradeType}"></fiscxd:dictionary>