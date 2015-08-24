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
<form action="" id="findContractFrom" name="findContractFrom">
<table width="100%">
<tr>
	<td align="right">部门名称：</td>
	<td>
	<div id="dept"></div>
	</td>
	
	<td align="right">贸易方式：</td>
	<td>
	<div id="div_tradeType"></div>
	</td>
	<td align="right">SAP订单号：</td>
	<td>
	<input type="text" id="sapOrderNo" name="sapOrderNo" value=""></input>
	</td>
	
</tr>
<tr>
	<td align="right">合同编码：</td>
	<td>
	<input type="text" id="contractNo" name="contractNo" value=""></input>
	</td>
	<td align="right">外合同号：</td>
	<td>
	<input type="text" id="outContractNo" name="outContractNo" value=""></input>
	</td>
	<td align="right">客户/供应商名称</td>
	<td>
	<input type="text" id="unitName" name="unitName" value=""></input>
	</td>
</tr>
<tr>
	
	<td align="right">立项名称：</td>
	<td>
	<input type="text" id="projectName" name="projectName" value=""></input>
	</td>
	<td align="right">物料名称：</td>
	<td>
	<input type="text" id="className" name="className" value=""></input>
	</td>
	<td>
	<input type="button" value="查找" onclick="findContractInfo()"></input>
	<input type="reset" value="清空"></input>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_center"></div>

<div id="div_south"></div>
</body>
</html>
<script type="text/javascript">
document.onkeydown = function(){if (event.keyCode == 13){findContractInfo();}}
var strContractTitle ='';

var contractInfogrid;		//合同期本信息列表
var contractInfods;
var contractgrid;			//合同列表
var contractds;

//项目查询回调函数
function selectProjectInfoCallBack(){
    
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	document.findContractFrom.projectNo.value = returnvalue.PROJECT_NO;
	document.findContractFrom.projectId.value = returnvalue.PROJECT_ID;
}

//查询项目信息事件
function findProjectInfo(){
	top.ExtModalWindowUtil.show('查询所属登陆员工总门的立项信息',
	'contractController.spr?action=selectProjrctInfo&deptid=' + selectId_dept,
	'',
	selectProjectInfoCallBack,
	{width:560,height:300});
}

//查找按钮的单击事件
function findContractInfo(){
	var requestUrl = 'contractController.spr?action=selectContractGroup&';
	requestUrl += Form.serialize('findContractFrom');
	requestUrl = requestUrl + '&deptid=' + selectId_dept;
	//requestUrl = requestUrl + '&projectid=' + document.findContractFrom.projectId.value;
	//requestUrl = requestUrl + '&projectNo=' + document.findContractFrom.projectNo.value;
	//requestUrl = requestUrl + '&contractgroupno=' + document.findContractFrom.contractGroupNo.value;
	/*requestUrl = requestUrl + '&contractno=' + document.findContractFrom.contractNo.value;
	requestUrl = requestUrl + '&outcontractno=' + document.findContractFrom.outContractNo.value;
	requestUrl = requestUrl + '&saporderno=' + document.findContractFrom.sapOrderNo.value;
	requestUrl = requestUrl + '&tradetype=' + dict_div_tradeType.getSelectedValue().trim();
    requestUrl = requestUrl + '&projectName=' + document.findContractFrom.projectName.value;
    requestUrl = requestUrl + '&className=' + document.findContractFrom.className.value;*/
    
	contractInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	contractInfods.load({params:{start:0, limit:10},arg:[]});
	
	
}

function openContractWin(){
	var records = contractgrid.selModel.getSelections();
	var contractType= records[0].data.CONTRACT_TYPE;
	var contractNo= records[0].data.CONTRACT_NO;
	var contractName= records[0].data.CONTRACT_NAME;
	var state= records[0].data.ORDER_STATE_D_ORDER_STATE;
	
	if (records[0].data.CONTRACT_TYPE.trim() == '采购合同'){
		top.ExtModalWindowUtil.show(contractType+'-'+contractNo+'-'+contractName+'-'+state,
			'contractController.spr?action=ArchPurchaseInfoView&businessRecordId='+records[0].data.CONTRACT_ID,
			'',
			'',
			{width:800,height:550});
	}
	
	if (records[0].data.CONTRACT_TYPE.trim() == '代理出口协议'){
		top.ExtModalWindowUtil.show(contractType+'-'+contractNo+'-'+contractName+'-'+state,
			'contractController.spr?action=ArchPurchaseInfoView&businessRecordId='+records[0].data.CONTRACT_ID,
			'',
			'',
			{width:800,height:550});
	}

	if (records[0].data.CONTRACT_TYPE.trim() == '销售合同'){
		top.ExtModalWindowUtil.show(contractType+'-'+contractNo+'-'+contractName+'-'+state,
		'contractController.spr?action=addSaleContractView&contractid='+records[0].data.CONTRACT_ID,
		'',
		'',
		{width:800,height:600});
	}
}
function BuycallbackFunction(){
	contractds.reload();
}
function openContractWin1(){
	var records = contractgrid.selModel.getSelections();
	
	if (records[0].data.CONTRACT_TYPE.trim() == '采购合同'){
		top.ExtModalWindowUtil.show(strContractTitle + '采购合同信息',
			'contractController.spr?action=addPurchaseContractView&modify=true&contractid='+records[0].data.CONTRACT_ID+'&contractType='+records[0].data.CONTRACT_TYPE,
			'',
			BuycallbackFunction,
			{width:900,height:550});
	}
	
	if (records[0].data.CONTRACT_TYPE.trim() == '代理出口协议'){
		top.ExtModalWindowUtil.show('代理出口协议',
			'contractController.spr?action=addPurchaseContractView&modify=true&contractid='+records[0].data.CONTRACT_ID+'&contractType='+records[0].data.CONTRACT_TYPE,
			'',
			BuycallbackFunction,
			{width:900,height:550});
	}

	if (records[0].data.CONTRACT_TYPE.trim() == '销售合同'){
		top.ExtModalWindowUtil.show(strContractTitle + '销售合同信息',
		'contractController.spr?action=addSaleContractView&modify=true&contractid='+records[0].data.CONTRACT_ID+'&contractType='+records[0].data.CONTRACT_TYPE,
		'',
		BuycallbackFunction,
		{width:900,height:550});
	}
}
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	var contractInfoPlant = Ext.data.Record.create([
		{name:'CONTRACT_GROUP_ID'},
		{name:'CONTRACT_GROUP_NAME'},
		{name:'CONTRACT_GROUP_NO'},
		{name:'CMD'},
		{name:'PROJECT_ID'},
		{name:'PROJECT_NAME'},
		{name:'CREATE_TIME'},
		{name:'REAL_NAME'}
	]);

	contractInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},contractInfoPlant)
    });
    
    var contractInfosm = new Ext.grid.CheckboxSelectionModel();
    
    var contractInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		contractInfosm,
		   {header: '合同组ID',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_GROUP_ID',
           hidden:true
           },
		　 {header: '合同组名称',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_GROUP_NAME'
           },
		　 {header: '合同组编码',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_GROUP_NO'
           },
		　 {header: '备注',
           width: 100,
           sortable: false,
           dataIndex: 'CMD'
           },
           {header: '项目ID',
           width: 100,
           sortable: true,
           dataIndex: 'PROJECT_ID',
           hidden:true
           },
           {header: '项目名称',
           width: 100,
           sortable: true,
           dataIndex: 'PROJECT_NAME'
           },
           {
           header: '创建时间',
           width: 100,
           sortable: true,
           dataIndex: 'CREATE_TIME'
           },
           {
           header: '创建者',
           width: 100,
           sortable: true,
           dataIndex: 'REAL_NAME'
           }
    ]);
    contractInfocm.defaultSortable = true;
    
    var contractInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:contractInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
   var print1 = new Ext.Toolbar.Button({
   		text:'打印',
	    iconCls:'find',
		handler:function(){
   				if (contractgrid.selModel.hasSelection()){
					var records = contractgrid.selModel.getSelections();
					if (records.length > 1){
						top.ExtModalWindowUtil.alert('提示','只能选择一条记录！');
					}else{
					    
					    if(records[0].json.CONTRACT_TYPE=='销售合同'){
					       window.open('contractController.spr?action=dealPrintV2&contractId='+records[0].json.CONTRACT_ID,'_blank','');
					    }
					    else if(records[0].json.CONTRACT_TYPE=='采购合同'){
					       window.open('contractController.spr?action=dealPrintV1&contractId='+records[0].json.CONTRACT_ID,'_blank','');
					    }
					}
				}else{
					top.ExtModalWindowUtil.alert('提示','请选择一条记录');
				}
				//selectWindow.hide();
		}
   	});
   
   var file = new Ext.Toolbar.Button({
  		text:'归档',
	    iconCls:'find',
	    disabled:${fileDisable=='false'?false:true},
		handler:function(){
  				if (contractgrid.selModel.hasSelection()){
					    var records = contractgrid.selModel.getSelections();
					    var purIdList = '';
					    var saleIdList = '';
					    for (var i=0;i<records.length;i++){
						    if(records[i].data.CONTRACT_TYPE=='销售合同'){
						    	saleIdList = saleIdList + records[i].data.CONTRACT_ID + '|';
						    }
						    else if(records[i].data.CONTRACT_TYPE=='采购合同'){
						    	purIdList = purIdList + records[i].data.CONTRACT_ID + '|';
						    }
					    }
					    top.Ext.Msg.show({
							title:'提示',
		  						msg: '是否归档所选记录',
		  						buttons: {yes:'是', no:'否'},
		  						fn: function(buttonId,text){
		  							if(buttonId=='yes'){
		  								if(saleIdList!=''){
		  								   var param = "?action=fileSales&idList=" + saleIdList;
									       new AjaxEngine('contractController.spr', {method:"post", parameters: param, onComplete: callBackHandle});
		  								}
		  								if(purIdList!=''){
			  							   var param = "?action=filePurchase&idList=" + purIdList;
										   new AjaxEngine('contractController.spr', {method:"post", parameters: param, onComplete: callBackHandle});
			  						    }
		  							}
		  							contractds.reload();
		  							
		  						},
		  						icon: Ext.MessageBox.QUESTION
						});
					
				}else{
					top.ExtModalWindowUtil.alert('提示','请选择一条记录');
				}
				//selectWindow.hide();
		}
  	});

    var itemTbar = new Ext.Toolbar({
		items:[print1,'-',file]
	});
    contractInfogrid = new Ext.grid.EditorGridPanel({
    	id:'contractInfoGrid',
        ds: contractInfods,
        cm: contractInfocm,
        sm: contractInfosm,
        bbar: contractInfobbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    contractInfogrid.addListener('rowclick', contractInfogridrowclick);
    
    function contractInfogridrowclick(grid, rowIndex, e){
    	var record = grid.getStore().getAt(rowIndex);
    	var data = record.get('CONTRACT_GROUP_ID');
    	
    	var requestUrl = 'contractController.spr?action=queryContractInfoByGroupId&groupid=' + data +'&';
    	requestUrl += Form.serialize('findContractFrom');

		contractds.proxy= new Ext.data.HttpProxy({url:requestUrl});
		contractds.load({params:{start:0, limit:10},arg:[]});
    }
	
	var contractPlant = Ext.data.Record.create([
		{name:'CONTRACT_ID'},
        {name:'CONTRACT_TYPE'},
        {name:'CONTRACT_NO'},
		{name:'CONTRACT_NAME'},
		{name:'OUT_CONTRACT_NO'},
		{name:'TOTAL'},
		{name:'CURRENCY'},
		{name:'APPLY_TIME'},
		{name:'APPROVED_TIME'},
		{name:'SAP_ORDER_NO'},
		{name:'KEEP_FLAG'},
		{name:'ORDER_STATE_D_ORDER_STATE'},
		{name:'QUALITYTOTAL'},
		{name:'CONTRACT_INFO'}
	]);

	contractds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},contractPlant)
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
           },{header: '合同详情',
               width: 50,
               dataIndex: 'CONTRACT_INFO',
               renderer:funContractOper
           },{header: '合同状态',
               width: 100,
               sortable: true,
               dataIndex: 'ORDER_STATE_D_ORDER_STATE'
            },{
           		header: '归档标识',
           		width: 50,
           		sortable: false,
           		dataIndex: 'KEEP_FLAG',
           		renderer:function(value,metaData){
           			if(value=='1'){
           				metaData.css='x-grid-row-bgcolor-effect';
           				return '归档';           			
           			}
           			return '';
           		}
           },
           {header: '合同类型',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_TYPE'
           },
		　 {header: '合同编码',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_NO'
           },
		　 {header: '合同名称',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_NAME'
           },
           {header: '外合同号',
           width: 100,
           sortable: true,
           dataIndex: 'OUT_CONTRACT_NO'
           },
           {header: '合同金额',
           width: 100,
           sortable: true,
           dataIndex: 'TOTAL'
           },
           {header: '数量',
           width: 100,
           sortable: true,
           dataIndex: 'QUALITYTOTAL'
           },
           {header: '合同币别',
           width: 100,
           sortable: true,
           dataIndex: 'CURRENCY'
           },
           {header: '申报日期',
           width: 100,
           sortable: true,
           dataIndex: 'APPLY_TIME'
           },
           {header: '通过日期',
           width: 100,
           sortable: true,
           dataIndex: 'APPROVED_TIME'
           },
           {header: 'SAP订单编号',
           width: 100,
           sortable: true,
           dataIndex: 'SAP_ORDER_NO'
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
    
    contractgrid = new Ext.grid.EditorGridPanel({
    	id:'contractGrid',
        ds: contractds,
        cm: contractcm,
        sm: contractsm,
        bbar: contractbbar,
        tbar: itemTbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'south',
        el: 'div_south',
        autowidth:true,
        layout:'fit'
    });
    
    function funContractOper(value, p, record){
    	return String.format('<a href="#" onclick="openContractWin()">{0}</a><c:if test="${loginer=='51E0C161-558F-4387-A25A-4439C2996E88'}"><a href="#" onclick="openContractWin1()">修改</a></c:if>',value);
    }

   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:100
		},{
			region:"center",
			layout:'fit',
			title: "合同组列表",
			items:[contractInfogrid]
		},
		{
			region:"south",
			layout:'fit',
			height:250,
			minSize: 210,
            maxSize: 400,
            split:true,
			collapsible: true,
			title: "合同列表",
			items:[contractgrid]
		}]
	});
});
</script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155" isMutilSelect="true"></fiscxd:dept>
<fiscxd:dictionary divId="div_tradeType" fieldName="tradetype" dictionaryName="BM_BUSINESS_TYPE" width="153"></fiscxd:dictionary>