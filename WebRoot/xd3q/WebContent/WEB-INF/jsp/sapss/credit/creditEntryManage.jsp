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
<form action="" id="findcreditEntryFrom" name="findcreditEntryFrom">
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
	<td align="right">合同编码：</td>
	<td>
		<input type="text" id="contractNo" name="contractNo" value=""></input>
	</td>
	<td align="right">SAP订单号：</td>
	<td>
		<input type="text" id="sapOrderNo" name="sapOrderNo" value=""></input>
	</td>
	<td align="right">纸质合同号</td>
	<td>
	    <input type="text" id="contractPaperNo" name="contractPaperNo" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">开证日期从：</td>
	<td>
		<div id="sDateDiv"></div>
	</td>
	<td align="right">到：</td>
	<td>
		<div id="eDateDiv"></div>
	</td>
	<td align="center">
		<input type="button" value="查找" onclick="findCreditEntryInfo()"></input>
		<input type="reset" value="清空" ></input>
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
//贸易类型
var tradeType = '${tradeType}';

var strcreditEntryTitle ='';

var purchaserrecord;
var purchaserfieldName;
strcreditEntryTitle = Utils.getTradeTypeTitle(tradeType);

var creditEntryInfogrid;		//信用证开证信息列表
var creditEntryInfods;


//creditEntry 查找按钮的单击事件
function findCreditEntryInfo()
{
    var sDate= Ext.getDom("sDate").value;
    var eDate= Ext.getDom("eDate").value;
    
    if(Utils.isEmpty(sDate)==false && Utils.isEmpty(eDate)==false && Utils.daysBetween(sDate,eDate)>0)
    {

      top.Ext.Msg.show({
		title:'提示',
		closable:false,
		msg:"[开证日期从]不能大于[开证日期到]，请重新选择日期",
		buttons:{yes:'关闭'},
		fn:Ext.emptyFn,
		icon:Ext.MessageBox.INFO
      });
     }
     else
     {
        //'&deptid=' + selectId_dept;
        //alert(selectId_dept);
     	var requestUrl = 'creditEntryController.spr?action=query&tradeType='+ tradeType +'&deptid=' + selectId_dept + '&'+Form.serialize('findcreditEntryFrom');
		creditEntryInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
		creditEntryInfods.load({params:{start:0, limit:10},arg:[]});
     }

}
 
//creditEntry 删除信用证开证数据的回调函数
function funDeletecreditEntryCallBack(transport){
	callBackHandle(transport);
	creditEntryInfods.reload();
}
function BuycallbackFunction(){
	creditEntryInfods.reload();
}
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	
	//增加信用证开证的回调函数
    function funAddcreditEntryCallBack(){
     creditEntryInfods.reload();
    }
    
    //增加
    var addcreditEntryInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show(strcreditEntryTitle + '信用证开证审查登记表',
			'creditEntryController.spr?action=create&tradeType='+tradeType,
			'',
			funAddcreditEntryCallBack,
			{width:930,height:600});
		}
   	});

   	//删除
   	var deletecreditEntryInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (creditEntryInfogrid.selModel.hasSelection()){
				var records = creditEntryInfogrid.selModel.getSelections();
		
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个信用证开证信息！');
				}else{
					if (records[0].data.CREDIT_STATE_D_CREDIT_STATE != "信用证开证" )
						top.ExtModalWindowUtil.alert('提示','不能删除已送审的单据！');
					else
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除选择的记录!   ',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var param = "?action=delete";
								param = param + "&creditId=" + records[0].data.CREDIT_ID;

								new AjaxEngine('creditEntryController.spr', 
						   			{method:"post", parameters: param, onComplete: funDeletecreditEntryCallBack});
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的信用证开证信息！');
			}
		}
   	});
    var referenceCreate = new Ext.Toolbar.Button({
   		text:'参考创建',
	    iconCls:'add',
		handler:function(){
					    top.ExtModalWindowUtil.show('信用证参考创建',
						'creditEntryController.spr?action=addReferenceView&tradeType=${tradeType}',
						'',
						BuycallbackFunction,
						{width:300,height:200});
	    }
   	});
    var creditEntryInfotbar = new Ext.Toolbar({
		items:[addcreditEntryInfo,'-',deletecreditEntryInfo,'-',referenceCreate]
	});
	
	var creditEntryInfoPlant = Ext.data.Record.create([
		{name:'CREDIT_ID'},           //信用证号
		{name:'CREDIT_NO'},           //信用证编号
		{name:'CREATE_DATE'},         //开证日期
		{name:'DEPT_NAME'},           //部门名称
		{name:'PROJECT_NO'},          //立项号
		{name:'CONTRACT_NO'},         //合同编码
		{name:'SAP_ORDER_NO'},        //SAP订单编号
		{name:'EKKO_UNSEZ'},
		{name:'CREATE_BANK'},         //开证行
	    {name:'CREDIT_STATE_D_CREDIT_STATE'}, //信用证状态
	    {name:'CREATOR'},             //创建者
		{name:'operaRD'},             //操作
		{name:'viewContract'}        //查看合同{name:'CREDIT_HIS_ID'}//信用证改证历史ID,creditHisId
	]);

	creditEntryInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'creditEntryController.spr?action=query&tradeType='+tradeType}),
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
	           width: 140,
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
	           header: '纸质合同号',
	           width: 100,
	           sortable: true,
	           dataIndex: 'EKKO_UNSEZ'
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
        tbar: creditEntryInfotbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    creditEntryInfods.load({params:{start:0, limit:10},arg:[]});
    
 //creditEntryInfogrid.addListener('rowclick', creditEntryInfogridrowclick);

   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: strcreditEntryTitle + "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:110
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
		id:'sDate',
		name:'sDate',
		width: 160,
	    readOnly:true,
		renderTo:'sDateDiv'
   	});
   	
   	var sDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'eDate',
	    name:'eDate',
		width: 160,
	    readOnly:true,
		renderTo:'eDateDiv'
   	});

});

//creditEntryManage 修改信用证开证链接
function operaRD(value,metadata,record){
	var state = record.data.CREDIT_STATE_D_CREDIT_STATE;
	
	if('${loginer}'== record.data.CREATOR){
		if (state=='信用证开证')
		{    		
	   		return '<a href="#" onclick="modifyOperate()" >修改</a> <a href="#" style="color:red" onclick="submitWorkflowForm()">提交</a>';
	  	}
	  	else if(state=='生效' || state=='备用')
	  	{
        		return '<a href="#" onclick="viewCreditEntryForm()">查看</a> <a href="#" onclick="modifyCreditHisInfo()">改证</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
        }
        else if(state=='改证')
        {
        	 return '<a href="#" onclick="viewCreditEntryForm()">查看</a> <a href="#" onclick="viewChangeForm()">改证历史</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
        	
        }
        else if(state=='改证通过')
        {
            return '<a href="#" onclick="viewChangeForm()">查看</a> <a href="#" onclick="modifyCreditHisInfo()">改证</a>  <a href="#" onclick="viewHistory()">流程跟踪</a>';	
//	  	} 	
//	  	else if(state=='作废'){
//	  		return '<a href="#" onclick="openSubmitParticularWorkflow()">特批</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';	
//		}else if(state.indexOf('特批')!=-1){
//			return '<a href="#" onclick="viewCreditEntryForm()">查看</a> <a href="#" onclick="businessAllProcessRecords()">流程跟踪</a>';
		}else{
	   		return '<a href="#" onclick="viewCreditEntryForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
	    }		
	}else{
		return '<a href="#" onclick="viewChangeForm()">查看</a>';
	}
}
function openSubmitParticularWorkflow()
{
   if(creditEntryInfogrid.selModel.hasSelection())
   {
	   var record = creditEntryInfogrid.selModel.getSelections();
	   top.ExtModalWindowUtil.show('特批','creditEntryController.spr?action=openSubmitParticularWorkflow&operate=view&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID,'',modifyCallback,{width:900,height:600});
   }
}
function businessAllProcessRecords(){
	var records = creditEntryInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('审批详情信息',
	'workflowController.spr?action=businessAllProcessRecords&businessRecordId='+records[0].data.CREDIT_ID,
	'',
	'',
	{width:800,height:500});	
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
	    //alert(record[0].json.CREDIT_ID+ ":" + tradeType);
		top.ExtModalWindowUtil.show(strcreditEntryTitle + '查看信用证开证相关联采购合同信息','creditEntryController.spr?action=viewContractInfo&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID+"&contractType=P",'',modifyCallback,{width:605,height:320});
  } 
}

//creditEntryManage 修改信用证信息
function modifyOperate(){
		if(creditEntryInfogrid.selModel.hasSelection()){
			var record = creditEntryInfogrid.selModel.getSelections();
			top.ExtModalWindowUtil.show(strcreditEntryTitle + '修改信用证开证信息','creditEntryController.spr?action=modify&operate=modify&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID,'',modifyCallback,{width:900,height:600});
		}
}

//改证
function modifyCreditHisInfo(){
		if(creditEntryInfogrid.selModel.hasSelection()){
			var record = creditEntryInfogrid.selModel.getSelections();
			top.ExtModalWindowUtil.show(strcreditEntryTitle + '信用证开证改证','creditEntryHisController.spr?action=modifyCreditHisInfo&operate=change&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID,'',modifyCallback,{width:930,height:610});
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

//查看信用证改证信息
function viewChangeForm()
{
   if(creditEntryInfogrid.selModel.hasSelection())
   {
	   var record = creditEntryInfogrid.selModel.getSelections();
	   top.ExtModalWindowUtil.show(strcreditEntryTitle + '查看信用证开证改证信息','creditEntryHisController.spr?action=modifyCreditHisInfo&operate=view&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID,'',modifyCallback,{width:930,height:600});
   }
}

//creditEntryManage 提交信用证开证申请
function submitWorkflowForm(){
		if(creditEntryInfogrid.selModel.hasSelection()){
			var record = creditEntryInfogrid.selModel.getSelections();
			top.ExtModalWindowUtil.show(strcreditEntryTitle + '提交信用证开证申请','creditEntryController.spr?action=modify&operate=workflow&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID,'',modifyCallback,{width:900,height:600});
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
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155"></fiscxd:dept>
