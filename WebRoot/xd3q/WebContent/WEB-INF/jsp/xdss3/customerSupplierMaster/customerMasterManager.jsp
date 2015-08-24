<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户主数据</title>
<link href="<%=request.getContextPath()%>/css/public.css" rel="stylesheet" type="text/css" />
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
.x-grid-row-bgcolor{  
    background-color:#ffffcc;
}
.x-grid-row-bgcolor-effect{
	color:red;
}
</style>
</head>
<body>

<div id="div_center"></div>

<div id="div_south"></div>
<div id="gridTagDiv"></div>
<div id="main" class="x-hide-display">
<form id="findCondictionFR" method="post">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
    <tr >
      <td width="12%" height="20" align="right">客户编号:</td>
      <td width="25%" colspan="3"><input name="guest_Code" type="text" size="20"/></td>
      <td width="8%" align="right">客户名称:</td>
      <td width="25%" colspan="3"><input name="guest_Name" type="text" size="20"/></td>
      <td width="10%" align="right"></td>
      <td width="24%"></td>
    </tr>
     <tr>
        <td><div align="right">公司性质:</div></td>
        <td colspan="3">
        <select id="nature" name="nature" tabindex="27">
				<option value="">请选择</option>
				<option value="国有"
					<c:if test="${main.nature=='国有'}"> selected </c:if>>国有</option>
				<option value="民营"
					<c:if test="${main.nature=='民营'}"> selected </c:if>>民营</option>
					<option value="合资"
					<c:if test="${main.nature=='合资'}"> selected </c:if>>合资</option>
					<option value="外资"
					<c:if test="${main.nature=='外资'}"> selected </c:if>>外资</option>
			</select>
        </td>
         <td><div align="right">公司类型:</div></td>
        <td colspan="3"><input type="text" name="companytype" value="${main.companytype}"/></td>
         <td><div align="right">成立时间:</div></td>
        <td><input type="text" name="formed" value="${main.formed}"/></td>
        </tr>
        <tr>
        <td><div align="right">注册资本（单位万元）:</div></td>
        <td colspan="3"><input type="text" name="capital" value="${main.capital}"/></td>
        <td><div align="right">经营期限:</div></td>
        <td colspan="3">
           <table><tr><td><input type="text" name="periodStart" value="${main.periodStart}"/></td>
      <td >至</td>
      <td ><input type="text" name="periodEnd" value="${main.periodEnd}"/></td></tr></table>
      
        </td>
         <td><div align="right">营业执照最新年检年度:</div></td>
        <td><input type="text" name="annualInspection" value="${main.annualInspection}"/></td>
        </tr>
        <tr>
        <td><div align="right">国家</div></td>
        <td >
        	<input type="text" name="country" value="${main.country}"/>
        </td>
        <td colspan="3"><div align="right">地区</div></td>
        <td>
        <input type="text" name="area" value="${main.area}"/>
        </td>
       
      </tr>
    <tr align="center">
      <td align="center" colspan="10"><input type="button" value="查找" onclick="find()"/> <input type="reset" value="清空"/><input type="button" value="导出" onclick="exportToExcel()"/>
      </td>
	</tr>
</table>
</form>
</div>
</body>
</body>
</html>
<script type="text/javascript">
document.onkeydown = function(){if (event.keyCode == 13){find();}}
//<button onclick="window.showModalDialog('projectController.spr?action=create&tradeType=7','','width:300;height:400');"> test</button>
var ds;
var tempGrid;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	//project_name,apply_time,project_no,trade_type,nuder_taker,org_name
	//extComponentServlet?type=grid&grid_columns=PROJECT_ID,PROJECT_NAME,APPLY_TIME,PROJECT_NO&grid_sql=select * from t_project_info&grid_size=10'}),
	var itemStore = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'customerMasterController.spr?action=find&state=3'}),
        		reader: new Ext.data.JsonReader({
            		root: 'root',
            		totalProperty: 'totalProperty'
            	},[
            		{name:'CUSTOMERMASTER_ID'},
            		{name:'ACCOUNT_GROUP'},
            		{name:'COMPANY_CODE'},
            		{name:'SALES_ORG'},
            		{name:'TITLE'},
            		{name:'GUEST_CODE'},
            		{name:'GUEST_NAME'},
            		{name:'ADDRESS'},
            		{name:'SEARCH_TERM'},
            		{name:'CITY'},
            		{name:'COUNTRY'},
					{name:'AREA'},
					{name:'VAT_NUM'},
					{name:'BUSINESS_NO'},
            		{name: 'ZIP_CODE'}, 					
            		{name: 'PHONE'},							
            		{name:'PHONE_EXT'},
            		{name:'FAX'},
            		{name:'CONTACT_MAN'},
					{name:'EMAIL'},
					{name:'SAP_SUPPLIERS_CODE'},
					{name:'SAP_SUPPLIERS_NAME'},
            		{name: 'CURRENCY'}, 					
            		{name: 'TAX_SORT'},									
            		{name:'ACCOUNT_ALLOT'},
            		{name:'SUBJECTS'},
            		{name: 'CASH_GROUP'}, 					
            		{name: 'PAY_TERMS'},							
            		{name:'PAY_WAY'},            		
            		{name:'CMD'},
					{name:'DEPT_ID'},      		
            		{name:'CREATOR'},
					{name:'CREATOR_TIME'},
					{name:'APPLY_TIME'},
					{name:'APPROVED_TIME'},
            		{name: 'EXAMINE_STATE'}, 					
            		{name: 'EXAMINE_STATE_D_EXAMINE_STATE'}, 
            		{name: 'LICENSEPATH'}, 
            		{name: 'ORGANIZATIONPATH'}, 
            		{name: 'TAXATIONPATH'}, 
            		{name: 'SURVEYPATH'}, 
            		{name: 'FINANCIALPATH'}, 
            		{name: 'CREDITPATH'}, 
            		{name: 'OTHERPATH'}, 					
            		{name: 'TYPE'}, 					
            		{name: 'CODE'}, 					
            		{name: 'FORMED'}, 					
            		{name: 'CAPITAL'},
            		{name: 'CAPITALCURRENCY'}, 					
            		{name: 'NATURE'}, 					
            		{name: 'COMPANYTYPE'}, 					
            		{name: 'PERIODSTART'}, 					
            		{name: 'PERIODEND'}, 					
            		{name: 'ANNUALINSPECTION'}, 					
            		{name: 'OTHERINFO'}, 
            		{name: 'UPDATETIME'}, 					
            		{name: 'GUESTID'}, 					
            		{name: 'CREATEDEPT'}	, 					
            		{name: 'CREATEOR'}											
          		])
    });
    //增加
    var addmaterialInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('新增客户主数据申请',
			'customerMasterController.spr?action=createCustomerMaster',
			'',
			funAddreceiptsCallBack,
			{width:900,height:600});
		}
   	});
   	
   	//修证数据
    var updatematerialInfo = new Ext.Toolbar.Button({
   		text:'修证数据',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('查找客户','masterQueryController.spr?action=toFindCustomer','',guestCallback,{width:755,height:450});
			
		}
   	});
   	
   	    //删除
    var deletematerialInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
   				if (itemGrid.selModel.hasSelection()){
					var records = itemGrid.selModel.getSelections();
					if (records.length > 1){
						top.ExtModalWindowUtil.alert('提示','只能选择一条记录！');
					}else{
					
					     top.Ext.Msg.show({
   								title:'提示',
   								msg: '是否确定删除记录',
   								buttons: {yes:'是', no:'否'},
   								fn: function(buttonId,text){
   									callbackFlag='del';
 									if(buttonId=='yes'){
 	 									var parm ='?action=delete&id='+records[0].json.CUSTOMERMASTER_ID; 
 										new AjaxEngine('customerMasterController.spr',
 												 {method:"post", parameters: parm,onComplete: callBackHandle});
									}
   								},
   							    animEl: 'elId',
   								icon: Ext.MessageBox.QUESTION
							});
					
				}		
				}else{
					top.ExtModalWindowUtil.alert('提示','请选择一条记录');
				}
				//selectWindow.hide();
		}
   	});
   	function operaRD(value,metadata,record){
   	
   	if('${addupdate}' == 'true'){
   		 return '<a href="#" onclick="modify();" >修改</a> <a href="#" onclick="view()">查看</a>';
   	}else{
   		 return '<a href="#" onclick="view()">查看</a>';
   	}
   	/**
		var state = record.data.EXAMINE_STATE;
		if('${loginer}'== record.data.CREATOR){
			if (state=='1'){    		
		   		return '<a href="#" onclick="modify();" >修改</a> ';
		  	 	}else{
		   		return '<a href="#" onclick="view()">查看</a> ';
		   	}		
		}else{
			return '<a href="#" onclick="view()">查看</a>';
		}
		**/
     }
     
     function issf(value,metadata,record){
     
     	if(value ==''  || value == ' ' || value == null){
     		return '否';
     	}else{
     		return '是';
     	}
     }
   	//增加物料主数据单的回调函数
    function funAddreceiptsCallBack(){
    	itemStore.reload();
    }
    itemStore.load();
    ds = itemStore;
    var itemSm = new Ext.grid.CheckboxSelectionModel();
    var itemCm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		itemSm,{
			header: '客户ID',
           width: 50,
           sortable: false,
           hidden:true,
           dataIndex: 'CUSTOMERMASTER_ID'
           },{
          		header: '操作',
          		width: 100,
          		sortable: false,
          		dataIndex: 'oper',
          		renderer:operaRD
          },{
               header: '部门',
               width: 100,
               sortable: true,
               dataIndex: 'CREATEDEPT'
           },{
			header: '类别',
           width: 50,
           sortable: true,
           dataIndex: 'TYPE'
           },{
			header: '客户编号',
           width: 100,
           sortable: true,
           dataIndex: 'GUEST_CODE'
           },{
			header: '客户名称',
           width: 100,
           sortable: true,
           dataIndex: 'GUEST_NAME'
           },{
			header: '公司性质',
           	width: 100,
           	dataIndex: 'NATURE',
           	sortable: true
           },{
			header: '公司类型',
           	width: 50,
           	dataIndex: 'COMPANYTYPE',
           	sortable: true
           },
           {header: '成立时间',
           width: 100,
           sortable: false,
           dataIndex: 'FORMED'
           },
           {header: '营业期限到期日',
           width: 100,
           sortable: false,
           dataIndex: 'PERIODEND'
           },
           {header: '注册资本（万元)',
           width: 100,
           sortable: false,
           dataIndex: 'CAPITAL'
           },
           {header: '币别',
           width: 100,
           sortable: false,
           dataIndex: 'CAPITALCURRENCY'
           },
           {header: '营业执照最新年检年度',
           width: 100,
           sortable: false,
           dataIndex: 'ANNUALINSPECTION'
           },
           {header: '企业法人营业执照',
           width: 100,
           sortable: false,
           dataIndex: 'LICENSEPATH',
           renderer:issf
           },
           {header: '组织机构代码证',
           width: 100,
           sortable: false,
           dataIndex: 'ORGANIZATIONPATH',
           renderer:issf
           },
           {header: '税务登记证',
           width: 100,
           sortable: false,
           dataIndex: 'TAXATIONPATH',
           renderer:issf
           },
           {header: '资信调查报告',
           width: 100,
           sortable: false,
           dataIndex: 'SURVEYPATH',
           renderer:issf
           },
           {header: '财务报表',
           width: 100,
           sortable: false,
           dataIndex: 'FINANCIALPATH',
           renderer:issf
           },
           {header: '授信额度申请表',
           width: 100,
           sortable: false,
           dataIndex: 'CREDITPATH',
           renderer:issf
           },
           {header: '公司地址',
           width: 100,
           sortable: false,
           dataIndex: 'ADDRESS'
           },
           {header: '省（地区）',
           width: 100,
           sortable: false,
           dataIndex: 'AREA'
           },
           {header: '国别',
           width: 100,
           sortable: false,
           dataIndex: 'COUNTRY'
           },
           {header: '联系人',
           width: 100,
           sortable: false,
           dataIndex: 'CONTACT_MAN'
           },
           {header: '电话',
           width: 100,
           sortable: false,
           dataIndex: 'PHONE'
           },
           {header: '其他信息',
           width: 100,
           sortable: false,
           dataIndex: 'OTHERINFO'
           }
    ]);
    itemCm.defaultSortable = true;
    function approvedTimeRD(value,metadata,record){
    	var apptime = record.data.APPROVED_TIME;
    	if(apptime!=null)
    		return apptime.substring(0,4)+'-'+apptime.substring(4,6)+'-'+apptime.substring(6,8)+' '+apptime.substring(8,10)+':'+apptime.substring(10,12)+':'+apptime.substring(12,14);
    	else
    		return '';	
    }    
     var paging = new Ext.PagingToolbar({
        pageSize: 10,
        store:itemStore,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: '没有记录'
    });
    var itemTbar = new Ext.Toolbar({
		items:[//addmaterialInfo,'-'
		updatematerialInfo,'-'
		//,deletematerialInfo
		]
	});
    //itemStore.load();进入页面时不加载
    var itemGrid = new Ext.grid.GridPanel({
    	id:'contractGrid',
        ds: itemStore,
        cm: itemCm,
        sm: itemSm,
        bbar: paging,
        tbar: itemTbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'south',
        el: 'div_south',
        autowidth:true,
		viewConfig : {  
                forceFit : false,  
                autoScroll:true,
                enableRowBody:true,
                getRowClass : 
                	function(record,rowIndex,rowParams,store){ 
                		if(rowIndex%2==0)
                			return 'x-grid-row-bgcolor'; 
                		else
                			return '';	
                	}  
        },          
        layout:'fit'
    });
    
    tempGrid = itemGrid;
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title:"查询",
			collapsible: true,
			height:190,
			items:[{contentEl:'main'}]
		},{
			region:"center",
			layout:'fit',
			title:"客户信息一览表",
			items:[itemGrid]
		}]
	});
  //data range
	Ext.apply(Ext.form.VTypes, {
	  daterange: function(val, field) {
	    var date = field.parseDate(val);
	
	    var dispUpd = function(picker) {
	      var ad = picker.activeDate;
	      picker.activeDate = null;
	      picker.update(ad);
	    };
	    
	    if (field.startDateField) {
	      var sd = Ext.getCmp(field.startDateField);
	      sd.maxValue = date;
	      if (sd.menu && sd.menu.picker) {
	        sd.menu.picker.maxDate = date;
	        dispUpd(sd.menu.picker);
	      }
	    } else if (field.endDateField) {
	      var ed = Ext.getCmp(field.endDateField);
	      ed.minValue = date;
	      if (ed.menu && ed.menu.picker) {
	        ed.menu.picker.minDate = date;
	        dispUpd(ed.menu.picker);
	      }
	    }
	    return true;
	  }
	});
	
	//日期
   	var formed = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'formed',
	    name:'formed',
		width: 160,
	    readOnly:true,
		applyTo:'formed'
   	});   	
   	//日期
   	var periodStart = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'periodStart',
	    name:'periodStart',
		width: 160,
		applyTo:'periodStart'
   	});  	
   	var periodEnd = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'periodEnd',
	    name:'periodEnd',
		width: 160,
		applyTo:'periodEnd'
   	}); 
   	
   	 var ds2 = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'masterQueryController.spr?action=rtnFindContry'}),
        		reader: new Ext.data.JsonReader({
            		root: 'root',
            		totalProperty: 'totalProperty'
            	},[
            		{name:'ID'},
            		{name:'TITLE'},
            		{name:'CMD'}									
          		])
    });
    var ds1 = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'masterQueryController.spr?action=rtnFindArea'}),
        		reader: new Ext.data.JsonReader({
            		root: 'root',
            		totalProperty: 'totalProperty'
            	},[
            		{name:'ID'},
            		{name:'TITLE'},
            		{name:'CMD'}									
          		])
    });
       ds2.load();
       ds1.load();
        combo = new Ext.form.ComboBox({ 
        store: ds2, 
        displayField:'TITLE', 
        valueField: 'ID' , 
        typeAhead: true, 
        mode: 'local', 
        triggerAction: 'query', 
        emptyText:'请选择...', 
        applyTo:'country',
        selectOnFocus:true 
        }); 
        combo2 = new Ext.form.ComboBox({ 
        store: ds1, 
        displayField:'TITLE', 
        valueField: 'ID' , 
        typeAhead: true, 
        mode: 'local', 
        triggerAction: 'query',
        emptyText:'请选择...', 
        applyTo:'area',
        selectOnFocus:true 
        });
        combo.on('select', function() { 
        combo2.reset();
        ds1.proxy= new Ext.data.HttpProxy({url: 'masterQueryController.spr?action=rtnFindArea&id=' + combo.getValue()});
        ds1.load(); 
    });
    if('${addupdate}' == 'false'){
    	updatematerialInfo.setVisible(false);
    }
    function guestCallback(){    
		var cb = top.ExtModalWindowUtil.getReturnValue();
	    	var kunnr=cb.KUNNR;//客户编码	
	    	
	    	new AjaxEngine('customerMasterController.spr?action=updateCustomerMaster&kunnr=' + kunnr,
 												 {method:"post", parameters: '',onComplete: updateMastercallBackHandle});
 												 
				
	}
	
	function updateMastercallBackHandle(trans){
		var promptMessagebox = new MessageBoxUtil();
		promptMessagebox.Close();
		var responseUtil1 = new AjaxResponseUtils(trans.responseText);
		var customField = responseUtil1.getCustomField("coustom");
		
		if(customField.success == 'true'){
		top.ExtModalWindowUtil.show('修证数据客户主数据申请',
				'customerMasterController.spr?action=forModify&id=' + customField.id,
				'',
				funAddreceiptsCallBack,
				{width:900,height:600});
		}else{
			top.ExtModalWindowUtil.alert('提示',customField.msg);
		}
	}
    
});
var customCallBackHandle=function(trans){
	var responseUtil1 = new AjaxResponseUtils(trans.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	top.ExtModalWindowUtil.alert('提示',customField.ok);
	if(callbackFlag=='del')
		find();
  }
  //流程跟踪
function viewHistory()
{
	var records = tempGrid.selModel.getSelections();
//	alert(records[0].data.RECEIPT_ID);
	top.ExtModalWindowUtil.show('客户主数据审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].json.CUSTOMERMASTER_ID,
		'',	'',	{width:880,height:400});
}
function modify(){
	var record = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('修改','customerMasterController.spr?action=forModify&id='+record[0].json.CUSTOMERMASTER_ID,'',find,{width:900,height:600});
}
function view(){
	var record = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('查看','customerMasterController.spr?action=forView&id='+record[0].json.CUSTOMERMASTER_ID,'',find,{width:900,height:600});
}
function modifyCallback(){
	ds.load({params:{start:0, limit:10}});
}
function find(){
	var url = 'customerMasterController.spr?action=find&'+Form.serialize('findCondictionFR');
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}
function exportToExcel(){
	var url = 'customerMasterController.spr?action=exportToExcel&'+Form.serialize('findCondictionFR');
	window.location.href(url);
}


    
</script>