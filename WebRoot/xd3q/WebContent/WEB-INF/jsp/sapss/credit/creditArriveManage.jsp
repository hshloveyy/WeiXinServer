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
<form action="" id="findcreditArriveFrom" name="findcreditArriveFrom">
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
	<td align="right">收证日期从：</td>
	<td>
		<div id="arrivesDateDiv"></div>
	</td>
	<td align="right">到：</td>
	<td>
		<div id="arriveeDateDiv"></div>
	</td>
	<td align="right">合同编码：</td>
	<td>
		<input type="text" id="contractNo" name="contractNo" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">客户开证日期从：</td>
	<td>
		<div id="sDateDiv"></div>
	</td>
	<td align="right">到：</td>
	<td>
		<div id="eDateDiv"></div>
	</td>
	<td align="right">SAP订单号：</td>
	<td>
		<input type="text" id="sapOrderNo" name="sapOrderNo" value=""></input>
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
//贸易类型
var tradeType = '${tradeType}';

var strcreditArriveTitle ='';

var purchaserrecord;
var purchaserfieldName;
strcreditArriveTitle = Utils.getTradeTypeTitle(tradeType);

var creditArriveInfogrid;		//信用证到证信息列表
var creditArriveInfods;

//creditArrive 查找按钮的单击事件
function findCreditArriveInfo(){
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
     	var requestUrl = 'creditArriveController.spr?action=query&tradeType='+ tradeType +'&deptid=' + selectId_dept + '&'+Form.serialize('findcreditArriveFrom');
		creditArriveInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
		creditArriveInfods.load({params:{start:0, limit:10},arg:[]});
     }
}

//creditArrive 删除信用证到证数据的回调函数
function funDeletecreditArriveCallBack(transport){
	callBackHandle(transport);
	creditArriveInfods.reload();
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	
	//增加信用证到证的回调函数
    function funAddcreditArriveCallBack(){
		creditArriveInfods.reload();
    }
    
    //增加
    var addcreditArriveInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show(strcreditArriveTitle + '信用证到证审查登记表',
			'creditArriveController.spr?action=create&tradeType='+tradeType,
			'',
			funAddcreditArriveCallBack,
			{width:925,height:618});
		}
   	});
   	
   	//删除
   	var deletecreditArriveInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (creditArriveInfogrid.selModel.hasSelection()){
				var records = creditArriveInfogrid.selModel.getSelections();
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个信用证到证信息！');
				}else{
					if (records[0].data.CREDIT_STATE_D_CREDIT_STATE != "信用证收证" )
						top.ExtModalWindowUtil.alert('提示','不能删除已送审的单据！');
					else
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var param = "?action=delete";
								param = param + "&creditId=" + records[0].data.CREDIT_ID;

							    new AjaxEngine('creditArriveController.spr', 
						   			{method:"post", parameters: param, onComplete: funDeletecreditArriveCallBack});
						   			
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的信用证到证信息！');
			}
		}
   	});
   	
   	//参考创建
   	var copyCreate = new Ext.Toolbar.Button({
   		text:'参考创建',
	    iconCls:'add',
		handler:function(){
			if (creditArriveInfogrid.selModel.hasSelection()){
				var records = creditArriveInfogrid.selModel.getSelections();
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','只允许选择一条记录参考创建！');
				}else{
					
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定参考创建选择的记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
							    top.ExtModalWindowUtil.show('参考创建信用证到证',
   										'creditArriveController.spr?action=copyCreate&creditId='+records[0].data.CREDIT_ID,
   										'',
   										funAddcreditArriveCallBack,
   										{width:925,height:618});

						   			
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要参考创建的信用证到证信息！');
			}
		}
   	});
    
    var creditArriveInfotbar = new Ext.Toolbar({
		items:[addcreditArriveInfo,'-',deletecreditArriveInfo,'-',copyCreate]
	});
	
	var creditArriveInfoPlant = Ext.data.Record.create([
		{name:'CREDIT_ID'},           //信用证号
		{name:'CREDIT_NO'},           //信用证编号
		{name:'CUSTOM_CREATE_DATE'},  //客户开证日期 CUSTOM_CREATE_DATE
		{name:'CREDIT_REC_DATE'},      //到证日期 CREDIT_REC_DAT
		{name:'DEPT_NAME'},             //部门名称
		{name:'PROJECT_NO'},          //立项号
		{name:'CONTRACT_NO'},         //合同编码
		{name:'SAP_ORDER_NO'},        //SAP订单编号
		{name:'CREATE_BANK'},         //开证行
	    {name:'CREDIT_STATE_D_CREDIT_STATE'}, //信用证状态CREDIT_STATE
	    {name:'CREATOR'},             //创建者
		{name:'operaRD'},             //操作
		{name:'viewContract'},        //查看合同
	]);
	
	creditArriveInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'creditArriveController.spr?action=getcreditArriveList&tradeType='+tradeType}),
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
        tbar: creditArriveInfotbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    creditArriveInfods.load({params:{start:0, limit:10},arg:[]});
    
 //creditArriveInfogrid.addListener('rowclick', creditArriveInfogridrowclick);

   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: strcreditArriveTitle + "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:110
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
		id:'sDate',
		name:'sDate',
		width: 160,
	    readOnly:true,
		renderTo:'sDateDiv'
   	});
   	
   	var eDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'eDate',
	    name:'eDate',
		width: 160,
	    readOnly:true,
		renderTo:'eDateDiv'
   	});
   	
   	var arrivesDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'arrivesDate',
		name:'arrivesDate',
		width: 160,
	    readOnly:true,
		renderTo:'arrivesDateDiv'
   	});
   	
   	var arriveeDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'arriveeDate',
	    name:'arriveeDate',
		width: 160,
	    readOnly:true,
		renderTo:'arriveeDateDiv'
   	});
   	
});


//CreditArriveManage 修改信用证开证链接
function operaRD(value,metadata,record){
	var state = record.data.CREDIT_STATE_D_CREDIT_STATE;
    var creditId = record.data.CREDIT_ID;
    var hasHis ='';
    
    var requesturl = 'creditArriveController.spr?action=getHasHis&creditId=' + creditId;
   /* 
	Ext.Ajax.request({
		url:encodeURI(requesturl),
		success: function(response, options){
			var responseArray = Ext.util.JSON.decode(response.responseText);
			hasHis = responseArray.HASHIS;
		},
		failure:function(response, options){
			//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			hasHis = '0';
		}
	});  
	*/  
	if('${loginer}'== record.data.CREATOR){
		if (state=='信用证收证'){    		
	   		return '<a href="#" onclick="modifyOperate();" >修改</a> <a href="#" style="color:red" onclick="submitWorkflowForm()">提交</a>';
	  	 	}
	  	else if((state=='生效' || state=='备用' || state=='关闭' || state=='撤销'))
	  	{
		  return '<a href="#" onclick="viewCreditHisInfo()">查看</a> <a href="#" onclick="modifyCreditHisInfo()">改证</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
        }
        else if(state=='改证')
        {
        	 return '<a href="#" onclick="viewCreditHisInfo()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
        	
        }
        else if(state=='改证通过') 
        {
            return '<a href="#" onclick="viewCreditHisInfo()">查看</a> <a href="#" onclick="modifyCreditHisInfo()">改证</a>  <a href="#" onclick="viewHistory()">流程跟踪</a>';	
	  	}
  	 	else{
	   		return '<a href="#" onclick="viewCreditArriveForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
	   	}		
	}else{
	    if((state=='生效' || state=='备用' || state=='关闭' || state=='撤销'))
	  	{
		  return '<a href="#" onclick="viewCreditHisInfo()">查看</a><a href="#" onclick="viewHistory()">流程跟踪</a>';
        }
        else if(state=='改证')
        {
        	 return '<a href="#" onclick="viewCreditHisInfo()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
        	
        }
        else if(state=='改证通过') 
        {
            return '<a href="#" onclick="viewCreditHisInfo()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';	
	  	}
  	 	else{
	   		return '<a href="#" onclick="viewCreditArriveForm()">查看</a>';
	   	}
	}
}

//修改信用证信息历史
function viewCreditHisInfo(){
		if(creditArriveInfogrid.selModel.hasSelection()){
			var record = creditArriveInfogrid.selModel.getSelections();
			top.ExtModalWindowUtil.show(strcreditArriveTitle + '查看信用证收证信息','creditArriveController.spr?action=view&operate=view&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID,'',modifyCallback,{width:900,height:610});
		}
}


//查看合同
function viewContract(value,metadata,record){
	if('${loginer}'== record.data.CREATOR)
	{  		
	   return '<a href="#" onclick="viewContractOperate();" >查看合同</a>';
	}
	else
	{return ""}		
}

//改证
function modifyCreditHisInfo(){
		if(creditArriveInfogrid.selModel.hasSelection()){
			var record = creditArriveInfogrid.selModel.getSelections();
			   //alert('creditArriveHisController.spr?action=modifyCreditHisInfo&operate=change&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID);
			   top.ExtModalWindowUtil.show(strcreditArriveTitle + '信用证到证改证','creditArriveHisController.spr?action=modifyCreditHisInfo&operate=change&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID,'',modifyCallback,{width:930,height:610});
				   
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

//CreditArriveManage 修改信用证信息
function modifyOperate(){
		if(creditArriveInfogrid.selModel.hasSelection()){
			var record = creditArriveInfogrid.selModel.getSelections();
			top.ExtModalWindowUtil.show(strcreditArriveTitle + '修改信用证到证信息','creditArriveController.spr?action=modify&operate=modify&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID,'',modifyCallback,{width:925,height:618});
		}
}

//查看信用证信息，只读不能修改。
function viewCreditArriveForm()
{
   if(creditArriveInfogrid.selModel.hasSelection())
   {
	   var record = creditArriveInfogrid.selModel.getSelections();
	   top.ExtModalWindowUtil.show(strcreditArriveTitle + '查看信用证到证信息','creditArriveController.spr?action=modify&operate=view&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID,'',modifyCallback,{width:925,height:618});
   }
}


//CreditArriveManage 提交信用证开证申请
function submitWorkflowForm(){
		if(creditArriveInfogrid.selModel.hasSelection()){
			var record = creditArriveInfogrid.selModel.getSelections();
			top.ExtModalWindowUtil.show(strcreditArriveTitle + '提交信用证到证申请','creditArriveController.spr?action=modify&operate=workflow&tradeType='+ tradeType +'&creditId='+record[0].json.CREDIT_ID,'',modifyCallback,{width:925,height:618});
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
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155"></fiscxd:dept>
