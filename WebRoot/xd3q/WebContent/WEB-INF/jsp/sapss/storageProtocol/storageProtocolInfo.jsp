<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>仓储协议信息</title>
<style type="text/css">
.add{
	background-image:url(<%= request.getContextPath()%>/images/fam/add.gif) !important;
}
.delete{
	background-image:url(<%= request.getContextPath()%>/images/fam/delete.gif) !important;
}
.update{
	background-image:url(<%= request.getContextPath()%>/images/fam/refresh.gif) !important;
}
.find{
	background-image:url(<%= request.getContextPath()%>/images/fam/find.png) !important;
}
</style>
</head>
<body>
<div id="div_main" class="x-hide-display">
<form id="mainForm" name="mainForm">
	 <table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
      <tr>
        <td align="right">申请类型<font color="red">▲<font></td>
        <td align="left">
        	<select name="applyType" id="applyType">
        		<option value="">请选择</option>
        		<option value="1">仓储协议</option>
        		<option value="2">货运代理协议</option>
        		<option value="3">保险协议</option>
        		<option value="4">报关/报检协议</option>
        	</select>
        </td>
        <td align="right">申请部门</td>
        <td align="left">
           <c:if test="${empty(info.deptName)}">
           <input type="text" name="deptName" value="${deptName}" readonly="readonly"/>
           </c:if>
           <c:if test="${!empty(info.deptName)}">
           <input type="text" name="deptName" value="${info.deptName}" readonly="readonly"/>
           </c:if>
        </td>
      </tr>
       <tr>
      <td align="right">企业<font color="red">▲<font></td>
      <td>
      	<input type="text" name="com" value="${info.com}" readonly="readonly"/>
      	<input type="hidden" name="comNo" value="${info.comNo}"/>
      	<input type="hidden" name="comType" value="${info.comType}"/>
      	<input type="button" name="客户" value="客户" onclick="showFindCustomer();"/>/
      	<input type="button" name="供应商" value="供应商" onclick="showFindSupplier();"/>
      </td>
       <td align="right">协议号<font color="red">▲<font></td>
      <td>
      	<input type="text" name="protocolNo" value="${info.protocolNo}"/>
      </td>
      </tr>
       <tr>
      <td align="right">货物名称<font color="red">▲<font></td>
      <td>
      	<input type="text" name="goods" value="${info.goods}"/>
      </td>
      <td align="right">立项号<font color="red">▲<font></td>
		<td>
			<input type="text" name="projectNo" value="${info.projectNo}" />
			<input type="hidden" name="projectId" value="${info.projectId}" />
			<input id="selectProjectBtn" type="button" value="..." onclick="selectProjectInfo()"/>
			<a href="#" onclick="viewProjectForm()">查看</a>
		</td>
		
      </tr>
     <tr>
        <td align="right">申请日期</td>
		<td>
			<input type="text" value="${info.applyTime}"  readonly="readonly" name="applyTime"/>
		</td>
		<td align="right">生效日期<font color="red">▲<font></td>
		<td>
			<input type="text" name="availableTime" value="${info.availableTime}" />
		</td>
      </tr>
      
      <tr>
      <td align="right">备注:</td>
      <td colspan="5">
      	<textarea rows="3" name="cmd" style="width:600;overflow-y:visible;word-break:break-all;" ${readable==true?"readonly='readonly'":''}>${info.cmd}</textarea>
      </td>
      </tr>
      </table>
      		<input type="hidden" name="infoId" value="${info.infoId}"/>
      		<input type="hidden" name="status" value="${info.status}"/>
      		<input type="hidden" name="creator" value="${info.creator}"/>
      		<input type="hidden" name="creatorId" value="${info.creatorId}"/>
      		<input type="hidden" name="deptId" value="${info.deptId}"/>
      		<input type="hidden" name="createTime" value="${info.createTime}"/>
 </form>
</div>
<div id="fileDiv" class="x-hide-display" ></div>
<div id="div_contractInfogrid"></div>
<div id="div_contract"></div>
<div id="workflowHistory" class="x-hide-display" ></div>
</body>
<script type="text/javascript">
var contractInfogrid;
var contractInfods;
var strOperType;
var tabs;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	$('attacheFile').value='${info.infoId}';
   	$('applyType').value='${info.applyType}';
   	//----------------------------------------------------------------------

    function addContractCallBack(){
		var returnValues = top.ExtModalWindowUtil.getReturnValue();
		if (returnValues != null && returnValues.length > 0) {
			contractInfogrid.stopEditing();
			for (var i = 0; i < returnValues.length; i++) {
				var returnValue = returnValues[i].data;
				var p = new contractInfoPlant({
					CONTRACT_NO: returnValue.CONTRACT_NO,
					CONTRACT_TYPE: returnValue.CONTRACT_TYPE,
					PAPER_NO: returnValue.PAPER_NO,
					CONTRACT_ID: returnValue.CONTRACT_PURCHASE_ID,
					SAP_ORDER_NO: returnValue.SAP_ORDER_NO,
					CONTRACT_INFO: returnValue.CONTRACT_INFO
				});
				contractInfods.insert(contractInfods.getCount(), p);
			}
			contractInfogrid.startEditing(0, 0);
		}
		
	}
    var contractInfoPlant = Ext.data.Record.create([
        {name:'CONTRACT_ID'},				//合同号
		{name:'CONTRACT_NO'},				//合同号
		{name:'CONTRACT_TYPE'},				//合同类别
		{name:'PAPER_NO'},				//纸质合同号
		{name:'CONTRACT_ID'},		//合同ID
		{name:'SAP_ORDER_NO'},				//SAP订单号
		{name:'CONTRACT_INFO'}				//详情
	]);

	contractInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'storageProtocolController.spr?action=queryContract&infoId=${info.infoId}'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},contractInfoPlant)
    });
     contractInfods.load();
    var contractInfobbar = new Ext.PagingToolbar({
        pageSize: 5,
        store:contractInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    var contractInfosm = new Ext.grid.CheckboxSelectionModel();
    
    var contractInfocm = new Ext.grid.ColumnModel([
	new Ext.grid.RowNumberer(),
	contractInfosm,
		{
			header: '合同类型',
			width: 100,
			sortable: true,
			dataIndex: 'CONTRACT_TYPE'
		},
		{
			header: '合同ID',
			width: 100,
			sortable: true,
			dataIndex: 'CONTRACT_ID'
		},
		{
			header: '合同编号',
			width: 100,
			sortable: true,
			dataIndex: 'CONTRACT_NO'
		},
		{
			header: '纸质合同号',
			width: 100,
			sortable: true,
			dataIndex: 'PAPER_NO'
		},
		{
			header: '合同ID',
			width: 100,
			sortable: true,
			dataIndex: 'CONTRACT_ID',
			hidden: true
		},
		{
			header: 'SAP订单号',
			width: 100,
			sortable: true,
			dataIndex: 'SAP_ORDER_NO'
		},
		{
			header: '合同详情',
			width: 100,
			sortable: true,
			dataIndex: 'CONTRACT_INFO',
			renderer:contractOper
		}
	]);
	
	function contractOper(value,metadata,record){
    	return  '<a href="#" onclick="openContractWin()">合同详情</a>'
    }
    
    var addInnerPayContract = new Ext.Toolbar.Button({
   		text:'选择合同',
	    iconCls:'add',
	     hidden:${readable==true},
		handler:function(){
		    var projectId = Ext.getDom('projectId').value;
		    if(projectId==''){
		       showMsg('请先选择立项信息！');
		       return;
		    }
			top.ExtModalWindowUtil.show('合同选择',
			'innerTradePayController.spr?action=selectInnerPayContract&payType=2&projectId='+projectId+'&projectNo='+Ext.getDom('projectNo').value,
			'',
			addContractCallBack,
			{width:800,height:500});
		}
   	});
   	
   	
   	//删除
   	var deleteInnerPayContract = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
	     hidden:${readable==true},
		handler:function(){
			if (contractInfogrid.selModel.hasSelection()){
				var records = contractInfogrid.selModel.getSelections();
				top.Ext.Msg.show({
					title:'提示',
						msg: '是否确定删除选择的记录!   ',
						buttons: {yes:'是', no:'否'},
						fn: function(buttonId,text){
							for (var i=0; i<records.length; i++){
								contractInfods.remove(records[i]);
							}
						},
						icon: Ext.MessageBox.QUESTION
				});
		}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的相关合同！');
			}
		}
   	});
    var innerPayContractInfotbar = new Ext.Toolbar({
		items:[addInnerPayContract,'-',deleteInnerPayContract]
	});
    contractInfogrid = new Ext.grid.EditorGridPanel({
    	id:'innerPayContractInfogrid',
        ds: contractInfods,
        cm: contractInfocm,
		sm: contractInfosm,
		bbar: contractInfobbar,
		tbar: innerPayContractInfotbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_contractInfogrid',
        autowidth:true,
		height:170,
        layout:'fit'
    });

	
   	
   	////////////////////
	tabs = new Ext.TabPanel({
		id:'mainTabPanel',
        autoWidth:true,
        activeTab: 0,
        autoScroll:true,
        loadMask:true,
        frame:true,
        defaults:{autoHeight: true},
        items:[{
            	title:'业务信息',
            	layout:'form',
            	buttonAlign:'center',
                items:[{
                		miniHeight:80,
                		autoHeight:true,
        				contentEl: 'div_main'
                },{
		            	title:'合同相关信息',
		            	contentEl: '',
		            	id:'creditEntryInfo',
						name:'creditEntryInfo',
						autoScroll:true,
		            	layout:'fit',
		            	items:[contractInfogrid]
		            	}],
                buttons:[{
    	            text:'保存',
    	            hidden:${readable==true},
    	            handler:function(){
    	                //
    	                var projectId = Ext.getDom("projectId").value
					 	if(projectId=='') {
					    		showMsg('请选择立项信息！');
					    		return ;
					 	}
					 	var com = Ext.getDom("com").value
					 	if(com=='') {
					    		showMsg('请填写企业信息！');
					    		return ;
					 	}
					 	var goods = Ext.getDom("goods").value
					 	if(goods=='') {
					    		showMsg('请填写货物名称！');
					    		return ;
					 	}
					 	var protocolNo = Ext.getDom("protocolNo").value
					 	if(protocolNo=='') {
					    		showMsg('请填写协议号！');
					    		return ;
					 	}
						contractInfogrid. getSelectionModel().selectAll();
						var recoder = contractInfogrid.selModel.getSelections();
						var myform = document.getElementById("mainForm");
						for (var i=0; i< recoder.length; i++) {
							var returnValue = recoder[i].data;
							var myinput = document.createElement('input');
							myinput.setAttribute('name','contractPurchaseId');
							myinput.setAttribute('id','contractPurchaseId'+i);
							myinput.setAttribute('type','hidden');
							myinput.value=returnValue.CONTRACT_ID+'/'+returnValue.CONTRACT_TYPE;
							myform.appendChild(myinput);
						};
    	                //
    	            	strOperType='save';
    	            	var param1 = Form.serialize('mainForm');
    					var param = param1 + "&action=saveInfo&TYPE=${TYPE}";
    					new AjaxEngine('storageProtocolController.spr', 
    							   {method:"post", parameters: param, onComplete: callBackHandle});
    				}
    			},{
                	text:'提交',
                	hidden:${info.infoId=="" || info.infoId==null || readable==true||nosub=='1'},
                	handler:function(){
                			strOperType = 'submit';
                			var param1 = Form.serialize('mainForm');
    						var param = param1  + "&action=firstSubmit&infoId=${infoId}";
    						new AjaxEngine('storageProtocolController.spr', 
    						   {method:"post", parameters: param, onComplete: callBackHandle});
                		}
    			 },{
                	text:'关闭',
                	hidden:${readable==true},
                	handler:function(){
                			top.ExtModalWindowUtil.markUpdated();
    				 		top.ExtModalWindowUtil.close();
    	            }
                }]    
            },{
				title:'协议附件',
				disabled:${info.infoId=="" || info.infoId==null},
				contentEl:'fileDiv',
				id:'fileEl',
				listeners:{
					activate:handlerActivate
				}
            },{
				title:'审批信息',
				contentEl:'workflowHistory'
            },
             {
				id:'alterEl',
				title:'仓储协议变更',
				disabled:false,
				html:'<iframe src="storageProtocolController.spr?action=toChangeTab&infoId=${info.infoId}&from=changeR" width="770" height="430" id="alter" ></iframe>'
             }
            ]
	});
  	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			layout:'fit',
			items:[tabs]
		}]
	});
	var d1 = new Ext.form.DateField({
   		format:'Y-m-d',
		id:"applyTime",
		name:"applyTime",
		width: 160,
		applyTo:'applyTime'
   	});
	var d2 = new Ext.form.DateField({
   		format:'Y-m-d',
		id:"availableTime",
		name:"availableTime",
		width: 160,
		applyTo:'availableTime'
   	});

});//end of Ext.onReady  
function handlerActivate(tab){
	fileDiv_ns_ds.load({params:{start:0,limit:5,recordId:Ext.getDom('attacheFile').value}});
}

//回调函数
function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	if (strOperType == 'save'){
		tabs.getItem('fileEl').enable();
		$('infoId').value=customField.INFO_ID;
		$('attacheFile').value=customField.INFO_ID;
	}	
	if (strOperType == 'submit'){
		top.ExtModalWindowUtil.markUpdated();
	    top.ExtModalWindowUtil.close();
	}	
}
//立项选择回调函数
function selectProjectInfoCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	$('projectId').value = returnvalue.PROJECT_ID + "";
	$('projectNo').value = returnvalue.PROJECT_NO + "";
	contractInfods.removeAll();
}

//立项选择窗体
function selectProjectInfo(){
	top.ExtModalWindowUtil.show('查询立项信息',
	'projectController.spr?action=selectProjrctInfo',
	'',
	selectProjectInfoCallBack,
	{width:800,height:400});
}
function viewProjectForm(){
      var projectId = Ext.getDom("projectId").value
      if(projectId=='') {
         showMsg('请选择立项信息！');
         return ;
      }
top.ExtModalWindowUtil.show('查看立项申请','projectController.spr?action=modify&from=view&projectId='+projectId,'','',{width:800,height:500});
 }
//提示窗口
function showMsg(msg){
	top.Ext.Msg.show({
			title:'提示',
			closable:false,
			msg:msg,
			buttons:{yes:'关闭'},
			fn:Ext.emptyFn,
			icon:Ext.MessageBox.INFO
	});
}
function openContractWin(){
	var records = contractInfogrid.selModel.getSelections();
	var contractType= records[0].data.CONTRACT_TYPE;
	var contractNo= records[0].data.CONTRACT_NO;
	var contractName= records[0].data.CONTRACT_NAME;
	var state= records[0].data.ORDER_STATE_D_ORDER_STATE;
	
	if (records[0].data.CONTRACT_TYPE.trim() == '采购合同'){
		top.ExtModalWindowUtil.show(contractType+'-'+contractNo,
			'contractController.spr?action=ArchPurchaseInfoView&businessRecordId='+records[0].data.CONTRACT_ID,
			'',
			'',
			{width:800,height:550});
	}

	if (records[0].data.CONTRACT_TYPE.trim() == '销售合同'){
		top.ExtModalWindowUtil.show(contractType+'-'+contractNo,
		'contractController.spr?action=addSaleContractView&contractid='+records[0].data.CONTRACT_ID,
		'',
		'',
		{width:800,height:600});
	}
}
function showFindSupplier(){
	top.ExtModalWindowUtil.show('查找供应商','masterQueryController.spr?action=toFindSupplier','',supplierCallback,{width:755,height:410});
}
function showFindCustomer(){
	top.ExtModalWindowUtil.show('查找客户','masterQueryController.spr?action=toFindCustomer','',customerCallback,{width:755,height:410});
}
function customerCallback(){
	var cb = top.ExtModalWindowUtil.getReturnValue();
	if(cb==''){
	    Ext.getDom('com').value='';
		Ext.getDom('comNo').value='';
		Ext.getDom('comType').value='';
	}
	else {
		Ext.getDom('com').value=cb.NAME1;
		Ext.getDom('comNo').value=cb.KUNNR;
		Ext.getDom('comType').value='1';
	}
}
function supplierCallback(){
	var cb = top.ExtModalWindowUtil.getReturnValue();
	if(cb==''){
		Ext.getDom('com').value='';
		Ext.getDom('comNo').value='';
		Ext.getDom('comType').value='';
	}
	else {
    	Ext.getDom('com').value=cb.NAME1;
		Ext.getDom('comNo').value=cb.LIFNR;
		Ext.getDom('comType').value='2';
	}
}
</script>
</html>
<fiscxd:workflow-taskHistory divId="workflowHistory" businessRecordId="${info.infoId}" width="800"></fiscxd:workflow-taskHistory>
<fiscxd:fileUpload divId="fileDiv" erasable="${readable!=true}"  increasable="${readable!=true}"
 resourceId="2222" resourceName="33333" recordIdHiddenInputName="attacheFile"></fiscxd:fileUpload>



