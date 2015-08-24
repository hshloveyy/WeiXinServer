<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信用证到证管理</title>
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
<form id="findcreditArriveFrom" name="findcreditArriveFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
	<td align="right">部门名称：</td>
	<td>
		<div id="dept"></div>
	</td>
	<td align="right">信用证号：</td>
	<td>
		<input type="text" id="creditNo" name="creditNo" value=""></input>
	</td>
	<td align="right">开证行：</td>
	<td>
		<input type="text" id="createBank" name="createBank" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">到证日期从：</td>
	<td>
		<table>
		<tr>
		<td>
			<input type="text" id="creditRecDates" size="10" name="creditRecDates" readonly="readonly"/> 
		</td>
		<td align="right">到：</td>
		<td>
			<input type="text" id="creditRecDatee" size="10" name="creditRecDatee" readonly="readonly"/> 
		</td>
		</tr>
		</table>
	</td>
	<td align="right">合同编码：</td>
	<td>
		<input type="text" id="contractNo" name="contractNo" value=""></input>
	</td>
	<td align="right">立项名称：</td>
	<td>
		<input type="text" id="projectName" name="projectName" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">客户开证日期从：</td>
	<td>
	<table>
		<tr>
		<td>
			<input type="text" id="customerCreateDates" size="10" name="customerCreateDates" readonly="readonly"/> 
		</td>
		<td align="right">到：</td>
		<td>
			<input type="text" id="customerCreateDatee" size="10" name="customerCreateDatee" readonly="readonly"/> 
		</td>
		</tr>
		</table>
	</td>
	<td align="right">SAP订单号：</td>
	<td>
		<input type="text" id="sapOrderNo" name="sapOrderNo" value=""></input>
	</td>
	<td></td>
	<td></td>
</tr>
<tr>
	<td align="right">信用证状态：</td>
	<td>
	    <div id="creditStateDiv" name="creditStateDiv"></div>
	</td>
	<td align="right">贸易类型:</td>
    <td align="left">
	  <div id="tradeTypeDiv" name="tradeTypeDiv"></div>
    </td>
    <td align="right">
    </td>
    <td align="left">
    	<input type="button" value="查找" onclick="findCreditArriveInfo()"></input>
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
document.onkeydown = function(){if (event.keyCode == 13){findCreditArriveInfo();}}

var strcreditArriveTitle ='';

var purchaserrecord;
var purchaserfieldName;
var creditArriveInfogrid;		//信用证到证信息列表
var creditArriveInfods;
var tradeType;

//creditArrive 查找按钮的单击事件
function findCreditArriveInfo(){
    var creditState ;
    creditState = Ext.getCmp("creditStateComb").getValue();
    tradeType = Ext.getCmp("tradeTypeComb").getValue();
    var requestUrl = 'creditArriveController.spr?action=queryList&tradeType='+ tradeType +'&creditState='+ creditState +'&deptid=' + selectId_dept + '&'+Form.serialize('findcreditArriveFrom');
	creditArriveInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	creditArriveInfods.load({params:{start:0, limit:10},arg:[]});
}


Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	
	var creditArriveInfoPlant = Ext.data.Record.create([
		{name:'CREDIT_ID'},           //信用证号
		{name:'CREDIT_NO'},           //信用证编号
		{name:'CUSTOM_CREATE_DATE'},  //客户开证日期 CUSTOM_CREATE_DATE
		{name:'CREDIT_REC_DATE'},      //到证日期 CREDIT_REC_DAT
		{name:'DEPT_NAME'},             //部门名称
		{name:'PROJECT_NO'},          //立项号
		{name:'PROJECT_NAME'},          //立项号
		{name:'AMOUNT'},          //立项号
		{name:'REQUEST'},          //立项号
		{name:'CONTRACT_NO'},         //合同编码
		{name:'SAP_ORDER_NO'},        //SAP订单编号
		{name:'CREATE_BANK'},         //开证行
	    {name:'CREDIT_STATE_D_CREDIT_STATE'}, //信用证状态
	    {name:'CREATOR'},             //创建者
		{name:'operaRD'},             //操作
		{name:'viewContract'}        //查看合同
	]);
	
	creditArriveInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'creditArriveController.spr?action=queryList&'+Form.serialize('findcreditArriveFrom')}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},creditArriveInfoPlant)
    });
    
    var creditArriveInfosm = new Ext.grid.CheckboxSelectionModel();
    
    var creditArriveInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		creditArriveInfosm,
		   {
			   header: '信用证编号',
	           width: 100,
	           sortable: true,
	           dataIndex: 'CREDIT_ID',
	           hidden:true
           },
           {
	           header: '操作',
	           width: 100,
	           sortable: true,
	           dataIndex: 'operaRD',
	           renderer: operaRD
          },
           {
	       		header: '信用证状态',
	       		width: 50,
	       		sortable: false,
	       		dataIndex: 'CREDIT_STATE_D_CREDIT_STATE'
          },
		   {
			   header: '信用证号',
	           width: 100,
	           sortable: true,
	           dataIndex: 'CREDIT_NO'
           },
		   {
			   header: '客户开证日期',
	           width: 100,
	           sortable: false,
	           dataIndex: 'CUSTOM_CREATE_DATE'
           },
           {
			   header: '到证日期',
	           width: 100,
	           sortable: false,
	           dataIndex: 'CREDIT_REC_DATE'
           },
           {
	           header: '部门',
	           width: 50,
	           sortable: true,
	           dataIndex: 'DEPT_NAME',
	           renderer:renderHallName
	           // hidden:true
           },
           {
	           header: '立项号',
	           width: 100,
	           sortable: true,
	           dataIndex: 'PROJECT_NO',
	           renderer:renderHallName
           },
           {
	           header: '立项名',
	           width: 100,
	           sortable: true,
	           dataIndex: 'PROJECT_NAME',
	           renderer:renderHallName
           },
           {
	           header: '金额',
	           width: 100,
	           sortable: true,
	           dataIndex: 'AMOUNT',
	           renderer:renderHallName
           },
           {
	           header: '申请人',
	           width: 100,
	           sortable: true,
	           dataIndex: 'REQUEST',
	           renderer:renderHallName
           },
           {
	           header: '合同编码',
	           width: 100,
	           sortable: true,
	           dataIndex: 'CONTRACT_NO',
	           renderer:renderHallName
           },
           {
	           header: 'SAP订单编号',
	           width: 100,
	           sortable: true,
	           dataIndex: 'SAP_ORDER_NO'
           },
           {
	           header: '开证行',
	           width: 100,
	           sortable: true,
	           dataIndex: 'CREATE_BANK'
           },
           {
           	   header: '查看合同',
	           width: 100,
	           sortable: true,
	           dataIndex: 'viewContract',
	           renderer: viewContract
           },
           {
           		header: '创建者',
           		width: 100,
           		sortable: false,
           		hidden:true,
           		dataIndex: 'CREATOR'
           }
    ]);
    creditArriveInfocm.defaultSortable = true;
    
    var creditArriveInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:creditArriveInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    creditArriveInfogrid = new Ext.grid.EditorGridPanel({
    	id:'creditArriveInfogrid',
        ds: creditArriveInfods,
        cm: creditArriveInfocm,
        sm: creditArriveInfosm,
        bbar: creditArriveInfobbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    var requestUrl = 'creditArriveController.spr?action=queryList&creditState=5';
	creditArriveInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	creditArriveInfods.load({params:{start:0, limit:10},arg:[]});

   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: strcreditArriveTitle + "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:130
		},{
			region:"center",
			layout:'fit',
		    collapsible: true,
			title: strcreditArriveTitle + "信用证列表",
			items:[creditArriveInfogrid]
		}]
	});
	
	var sDate = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 90,
	    readOnly:true,
		applyTo:'creditRecDates'
   	});
   	
   	var eDate = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 90,
	    readOnly:true,
	    applyTo:'creditRecDatee'
   	});
   	
   	var arrivesDate = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 90,
	    readOnly:true,
		applyTo:'customerCreateDates'
   	});
   	
   	var arriveeDate = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 90,
	    readOnly:true,
	    applyTo:'customerCreateDatee'
   	});
   	
   var creditState = new Ext.form.ComboBox({
        typeAhead: true,
        triggerAction: 'all',
        mode : 'local',
        renderTo:'creditStateDiv',
        id:'creditStateComb',
        width:157,
        allowBlank:true,
        blankText:'请选择',
        emptyText:'请选择',
        store : new Ext.data.SimpleStore({  //填充的数据
  				fields : ['text', 'value'],
  					data : [
  					       ['请选择',''],
  					       ['信用证收证','2'],
  					       ['审批中','3'],
						   ['生效','4'],
						   ['备用','5'],
						   ['过期','6'],
						   ['撤销','7'],
						   ['关闭','8'],
						   ['作废','9'],
						   ['改证','10'],
						   ['改证通过','11']]
 				 }),
        valueField : 'value',  //传送的值
        displayField : 'text',  //UI列表显示的文本
        forceSelection:true
     });
     creditState.setValue('5');

     
 var tradeType = new Ext.form.ComboBox({
        typeAhead: true,
        id:'tradeTypeComb',
        triggerAction: 'all',
        mode : 'local',
        renderTo:'tradeTypeDiv',
        width:157,
        allowBlank:true,
        blankText:'请选择',
        emptyText:'请选择',
        store : new Ext.data.SimpleStore({  //填充的数据
  				fields : ['value', 'text'],
  					data : [
  					       ['2', '外贸自营出口*业务'],
  					       ['4', '外贸自营出口业务'],
						   ['5', '外贸代理出口业务']]
 				 }),
        valueField : 'value',  //传送的值
        displayField : 'text',  //UI列表显示的文本
        forceSelection:true
     });

});


//CreditArriveManage 修改信用证开证链接
function operaRD(value,metadata,record){
	var state = record.data.CREDIT_STATE_D_CREDIT_STATE;
	
		if (state=='信用证收证'){    		
	   		return '<a href="#" onclick="viewCreditArriveForm()">查看</a>';
	    }
	   	else if((state=='生效' || state=='备用' || state=='关闭' || state=='撤销'))
	  	{
		  return '<a href="#" onclick="viewCreditHisInfo()">查看</a> <c:if test="${deptIndex>=0}"><a href="#" onclick="prePrint()">打印</a> </c:if><a href="#" onclick="viewHistory()">流程跟踪</a>';
        }
        else if(state=='改证')
        {
        
        	 return '<a href="#" onclick="viewCreditHisInfo()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
        	
        }
        else if(state=='改证通过') 
        {
            return '<a href="#" onclick="viewCreditHisInfo()">查看</a>  <a href="#" onclick="viewHistory()">流程跟踪</a>';	
	  	}
  	 	else{
	   		return '<a href="#" onclick="viewCreditArriveForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
	   	}		
}

//查看合同
function viewContract(value,metadata,record)
{
	//var state = record.data.CREDIT_STATE_D_CREDIT_STATE;
	if('${loginer}'== record.data.CREATOR)
	{  		
	   return '<a href="#" onclick="viewContractOperate();" >查看合同</a>';
	}		
}
function viewCreditHisInfo(){
		if(creditArriveInfogrid.selModel.hasSelection()){
			var record = creditArriveInfogrid.selModel.getSelections();
			top.ExtModalWindowUtil.show(strcreditArriveTitle + '查看信用证收证信息','creditArriveController.spr?action=view&operate=view&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID,'',modifyCallback,{width:900,height:610});
		}
}

//修改信用证信息历史
function modifyCreditHisInfo(){
		if(creditArriveInfogrid.selModel.hasSelection()){
			var record = creditArriveInfogrid.selModel.getSelections();
			top.ExtModalWindowUtil.show(strcreditEntryTitle + '修改信用证收证信息','creditArriveController.spr?action=modifyCreditHisInfo&operate=modify&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID,'',modifyCallback,{width:900,height:610});
		}
}
function prePrint()
{
   if(creditArriveInfogrid.selModel.hasSelection())
   {
	   var record = creditArriveInfogrid.selModel.getSelections();
	   var creditId = record[0].json.CREDIT_ID;
       window.open('creditArriveController.spr?action=dealPrint&creditId='+creditId,'_blank','location=no,resizable=yes');
   }
}

//查看信用证开证相关联合同信息
function viewContractOperate()
{
  if(creditArriveInfogrid.selModel.hasSelection())
  {
		var record = creditArriveInfogrid.selModel.getSelections();
		top.ExtModalWindowUtil.show(strcreditArriveTitle + '查看信用证到证相关联销售合同信息','creditArriveController.spr?action=viewContractInfo&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID+"&contractType=S",'',modifyCallback,{width:605,height:320});
  } 
}


//查看信用证信息，只读不能修改。
function viewCreditArriveForm()
{
   if(creditArriveInfogrid.selModel.hasSelection())
   {
	   var record = creditArriveInfogrid.selModel.getSelections();
	   top.ExtModalWindowUtil.show(strcreditArriveTitle + '查看信用证到证信息','creditArriveController.spr?action=modify&operate=view&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID,'',modifyCallback,{width:900,height:618});
   }
}


//流程跟踪
function viewHistory()
{
	var records = creditArriveInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show(strcreditArriveTitle + '审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].json.CREDIT_ID,
		'',	'',	{width:880,height:400});
}

//CreditArriveManage 修改动作完成后回调函数
function modifyCallback()
{   
	creditArriveInfods.load({params:{start:0, limit:10}});
}

 function renderHallName(value, meta, rec, rowIdx, colIdx, ds)
 {
    return '<div ext:qtitle="" ext:qtip="' + value + '">'+ value +'</div>';
 } 
</script>
<fiscxd:dept divId="dept" rootTitle="部门信息" isMutilSelect="true"  width="155"></fiscxd:dept>
