<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信用证开证管理</title>
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
<form id="findcreditEntryFrom" name="findcreditEntryFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
	<td align="right">部门名称：</td>
	<td>
		<div id="dept"></div>
	</td>
	<td align="right">信用证号：</td>
	<td>
		<input type="text" id="creditNo" name="creditNo"></input>
	</td>
	<td align="right">开证行：</td>
	<td>
		<input type="text" id="createBank" name="createBank"></input>
	</td>
</tr>
<tr>
	<td align="right">合同编码：</td>
	<td>
		<input type="text" id="contractNo" name="contractNo"></input>
	</td>
	<td align="right">SAP订单号：</td>
	<td>
		<input type="text" id="sapOrderNo" name="sapOrderNo" ></input>
	</td>
    <td align="right">贸易类型:</td>
    <td align="left">
        <div id="tradeTypeDiv" name="tradeTypeDiv"></div>
    </td>
</tr>
<tr>
	<td align="right">开证日期从：</td>
	<td>
		<input type="text" name="sDate" id="sDate" readonly="readonly"/>
	</td>
	<td align="right">到：</td>
	<td>
		<input type="text" name="eDate" id="eDate" readonly="readonly"/>
	</td>
	<td align="center">
	</td>
	<td>
	</td>
</tr>
<tr>
	<td align="right">信用证状态：</td>
	<td>
      <div id="statusDiv" ></div>
	</td>
	<td align="right">立项号：</td>
	<td><input type="text" name="projectName" id="projectName"/>
	</td>
	<td align="center">
		<input type="button" value="查找" onclick="findCreditEntryInfo()"></input>
		<input type="button" value="清空" onclick="clearForm()"></input>
		<a onbeforeactivate="onbeforClickA()" href="creditEntryController.spr?action=dealOutToExcel" id="aaa" target="_self">下载</a>
	</td>
    <td>
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
document.onkeydown = function(){if (event.keyCode == 13){findCreditEntryInfo();}}
var strcreditEntryTitle ='';

var purchaserrecord;
var purchaserfieldName;

var creditEntryInfogrid;		//信用证开证信息列表
var creditEntryInfods;
var tradeType="";

//creditEntry 查找按钮的单击事件
function findCreditEntryInfo()
{   
	//var tradeType = Ext.getCmp('tradeTypeComb').getValue();
    var requestUrl = 'creditEntryController.spr?action=queryList&deptid=' + selectId_dept + '&'+Form.serialize('findcreditEntryFrom');
	creditEntryInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	creditEntryInfods.load({params:{start:0, limit:10},arg:[]});
}
function onbeforClickA(){
	//var tradeType = Ext.getCmp('tradeTypeComb').getValue();
    var requestUrl = 'creditEntryController.spr?action=dealOutToExcel&deptid=' + selectId_dept + '&'+Form.serialize('findcreditEntryFrom');
	$('aaa').href=requestUrl;
}

function clearForm(){
	$('findcreditEntryFrom').reset();
	Ext.getCmp('tradeTypeComb').setValue('');
	selectId_dept='';
}
Ext.onReady(function(){

	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	var creditEntryInfoPlant = Ext.data.Record.create([
		{name:'CREDIT_ID'},           //信用证号
		{name:'CREDIT_NO'},           //信用证编号
		{name:'CREATE_DATE'},         //开证日期
		{name:'DEPT_NAME'},           //部门名称
		{name:'PROJECT_NO'},          //立项号
		{name:'CONTRACT_NO'},         //合同编码
		{name:'SAP_ORDER_NO'},        //SAP订单编号
		{name:'CREATE_BANK'},         //开证行
		{name:'AMOUNT'},         //开证金额
	    {name:'CREDIT_STATE_D_CREDIT_STATE'}, //信用证状态
	    {name:'CREATOR'},             //创建者
		{name:'operaRD'},             //操作
		{name:'viewContract'}        //查看合同 {name:'CREDIT_HIS_ID'}        //信用证改证历史ID,creditHisId
	]);

	creditEntryInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'creditEntryController.spr?action=queryList&'+Form.serialize('findcreditEntryFrom')}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},creditEntryInfoPlant)
    });
    
    var creditEntryInfosm = new Ext.grid.CheckboxSelectionModel();

    var creditEntryInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		creditEntryInfosm,
		  {
			   header: '信用证编号',
	           width: 100,
	           sortable: true,
	           dataIndex: 'CREDIT_ID',
	           hidden:true
           },
           {
	           header: '操作',
	           width: 130,
	           sortable: true,
	           dataIndex: 'operaRD',
	           renderer: operaRD
          },
           {
	       		header: '信用证状态',
	       		width: 100,
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
			   header: '开证日期',
	           width: 100,
	           sortable: false,
	           dataIndex: 'CREATE_DATE'
           },
           {
	           header: '部门名称',
	           width: 100,
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
	           header: '开证金额',
	           width: 100,
	           sortable: true,
	           dataIndex: 'AMOUNT'
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

    creditEntryInfocm.defaultSortable = true;

    var creditEntryInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:creditEntryInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });

    creditEntryInfogrid = new Ext.grid.EditorGridPanel({
    	id:'creditEntryInfoGrid',
        ds: creditEntryInfods,
        cm: creditEntryInfocm,
        sm: creditEntryInfosm,
        bbar: creditEntryInfobbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    var requestUrl = 'creditEntryController.spr?action=queryList&creditState=5';
	creditEntryInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	creditEntryInfods.load({params:{start:0, limit:10},arg:[]});
    
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: strcreditEntryTitle + "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:130
		},{
			region:"center",
			layout:'fit',
			collapsible: true,
			title: strcreditEntryTitle + "信用证列表",
			items:[creditEntryInfogrid]
		}]
	});
	
	var sDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'stard',
		name:'stard',
		width: 160,
	    readOnly:true,
		applyTo:'sDate'
   	});
   	
   	var ssDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'end',
	    name:'end',
		width: 160,
	    readOnly:true,
	    applyTo:'eDate'
   	});
 /**
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
  					       ['1', '外贸自营进口*业务'],
  					       ['3', '外贸自营进口业务'],
						   ['6', '外贸代理进口业务'],
						   ['9', '外贸自营进口敞口业务']]
 				 }),
        valueField : 'value',  //传送的值
        displayField : 'text',  //UI列表显示的文本
        forceSelection:true
     });
   	**/

});

//creditEntryManage 修改信用证开证链接
function operaRD(value,metadata,record){
	var state = record.data.CREDIT_STATE_D_CREDIT_STATE;
	
	
		if (state=='信用证开证')
		{    		
	   		return '<a href="#" onclick="viewCreditEntryForm()">查看</a>';
	  	}
	  	else if(state=='生效' || state=='备用'|| state=='关闭')
	  	{
        		return '<a href="#" onclick="viewCreditEntryForm()">查看</a> <a href="#" onclick="viewChangeForm()">查看历史</a> <c:if test="${deptIndex>=0}"><a href="#" onclick="prePrint()">打印</a> </c:if><a href="#" onclick="viewHistory()">流程跟踪</a>';
        }
        else if(state=='改证')
        {
        	 return '<a href="#" onclick="viewChangeForm()">查看改证历史</a> <a href="#" onclick="viewChangeHistory()">流程跟踪</a>';
        	
        }
        else if(state=='改证通过')
        {
            return '<a href="#" onclick="viewCreditEntryForm()">查看</a> <a href="#" onclick="viewChangeForm()">查看改证历史</a> <a href="#" onclick="viewChangeHistory()">流程跟踪</a>';	
	  	} 	
	  	 else
	  	 {
	   		return '<a href="#" onclick="viewCreditEntryForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
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

//查看信用证开证相关联采购合同信息
function viewContractOperate()
{
  if(creditEntryInfogrid.selModel.hasSelection())
  {
		var record = creditEntryInfogrid.selModel.getSelections();
		top.ExtModalWindowUtil.show(strcreditEntryTitle + '查看信用证开证相关联采购合同信息','creditEntryController.spr?action=viewContractInfo&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID+"&contractType=P",'',modifyCallback,{width:605,height:320});
  } 
}

//查看信用证信息，只读不能修改。
function viewCreditEntryForm()
{
   if(creditEntryInfogrid.selModel.hasSelection())
   {
	   var record = creditEntryInfogrid.selModel.getSelections();
	   top.ExtModalWindowUtil.show(strcreditEntryTitle + '查看信用证开证信息','creditEntryController.spr?action=modify&operate=view&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID,'',modifyCallback,{width:900,height:600});
   }
}

function prePrint()
{
   if(creditEntryInfogrid.selModel.hasSelection())
   {
	   var record = creditEntryInfogrid.selModel.getSelections();
	   var creditId = record[0].json.CREDIT_ID;
       window.open('creditEntryController.spr?action=dealPrint&creditId='+creditId,'_blank','location=no,resizable=yes');
   }
}

//查看信用证改证信息
function viewChangeForm()
{
   if(creditEntryInfogrid.selModel.hasSelection())
   {
	   var record = creditEntryInfogrid.selModel.getSelections();
	   top.ExtModalWindowUtil.show(strcreditEntryTitle + '查看信用证开证改证信息','creditEntryHisController.spr?action=modifyCreditHisInfo&operate=view&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID,'',modifyCallback,{width:900,height:600});
   }
}

//流程跟踪
function viewHistory()
{
	var records = creditEntryInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show(strcreditEntryTitle + '审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].json.CREDIT_ID,
		'',	'',	{width:880,height:400});
}

//信用证改证，流程跟踪
function viewChangeHistory()
{
	var records = creditEntryInfogrid.selModel.getSelections();
	//alert("CREDIT_HIS_ID:" + records[0].json.CREDIT_HIS_ID);
	
	top.ExtModalWindowUtil.show(strcreditEntryTitle + '审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].json.CREDIT_HIS_ID,
		'',	'',	{width:880,height:400});
}

//creditEntryManage 修改动作完成后回调函数
function modifyCallback(){
	creditEntryInfods.load({params:{start:0, limit:10}});
}

 function renderHallName(value, meta, rec, rowIdx, colIdx, ds)
 {
    return '<div ext:qtitle="" ext:qtip="' + value + '">'+ value +'</div>';
 } 
</script>
<fiscxd:dept divId="dept" rootTitle="部门信息" isMutilSelect="true" width="155"></fiscxd:dept>
<fiscxd:dictionary divId="statusDiv" fieldName="creditState" dictionaryName="BM_CREDIT_STATE" width="153" selectedValue="5"></fiscxd:dictionary>
<fiscxd:dictionary divId="tradeTypeDiv" fieldName="tradeType" dictionaryName="BM_BUSINESS_TYPE" width="158"></fiscxd:dictionary>

