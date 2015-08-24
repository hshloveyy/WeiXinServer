<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>立项变更页面</title>
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
<script type="text/javascript">
var outGrid;
var outChangeDS;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	var changeDS = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url:'changeProjectController.spr?action=findChange&projectId=${projectId}'}),
		reader: new Ext.data.JsonReader({
    		root: 'root',
    		totalProperty: 'totalProperty'
    	},[
    		{name:'CHANGE_ID'},
    		{name:'PROJECT_ID'},
			{name:'CHANGE_DESC'},
			{name:'CHANGE_TIME'},
			{name:'CHANGER'},
			{name:'CHANGE_NO'},
			{name:'APPLY_TIME'},
			{name:'APPROVED_TIME'},
			{name:'IS_ALLOW'},
			{name:'IS_CHANGED'},
			{name:'CHANGE_OPERATOR'},
			{name:'CHANGE_NOTE'},
			{name:'OPERATOR_TIME'},
			{name:'CREATE_TIME'},
			{name:'CREATOR'},
			{name:'MASK'},
			{name:'CURRENCY'},
			{name:'AMOUNT'},
			{name:'opera'},
			{name:'CHANGE_STATE_D_CHANGE_STATE'}
			
  		])
	});
	changeDS.load({params:{start:0,limit:5}});
	outChangeDS = changeDS;
	var itemSm = new Ext.grid.CheckboxSelectionModel();
	var itemCm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),itemSm,
		 {
		   header: '变更ID',
		   width: 100,
		   sortable: false,
		   hidden:true,
		   dataIndex:'CHANGE_ID'
		 },{
			header:'项目ID',
		   	width: 100,
		   	dataIndex:'PROJECT_ID',
		   	sortable: true,
		   	hidden:true
		 },{
		   header: '变更描述',
		   width: 100,
		   sortable: false,
		   dataIndex:'CHANGE_DESC'
		 },{
			   header: '金额(万)',
			   width: 100,
			   sortable: false,
			   dataIndex:'AMOUNT'
			 },{
			   header: '币别',
			   width: 100,
			   sortable: false,
			   dataIndex:'CURRENCY'
		 },{
			header:'变更时间',
		   width: 100,
		   sortable: true,
		   hidden:true,
		   dataIndex: 'CHANGE_TIME'
		 },{
		   	header:'变更者',
		  	width: 70,
		   	sortable: false,
		   	dataIndex:'CHANGER'
		 },{
			header:'变更状态',
			width: 70,
			sortable: false,
			dataIndex:'CHANGE_STATE_D_CHANGE_STATE'
		 },{
			header: '变更批次',
		   width: 60,
		   sortable: false,
		   dataIndex: 'CHANGE_NO',
		   hidden:false
		 },{
			header: '申报时间',
		   width: 100,
		   sortable: false,
		   dataIndex:'APPLY_TIME'
		 },{
		   	header: '审批通过时间',
		   	width: 100,
		   	sortable: false,
		   	dataIndex:'APPROVED_TIME'
		 },{
           	header: '操作',
           	width: 100,
           	sortable: false,
           	dataIndex:'opera',
           	renderer: operaRD
           }
		]);
	itemCm.defaultSortable = true;
    function operaRD(value,metadata,record){
    	var state = record.data.PROJECT_STATE_D_PROJECT_STATE;
    	return '<a href="#" onclick="viewChangeHistory();" >流程跟踪</a>';
    }
	var paging = new Ext.PagingToolbar({
		pageSize: 5,
		store:changeDS,
		displayInfo: true,
		displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
		emptyMsg: '没有记录'
	});
 	var itemGrid = new Ext.grid.GridPanel({
		id:'alterGridId',
		height:210,
		width:750,
		ds: changeDS,
		cm: itemCm,
		sm: itemSm,
		bbar:paging,
		//tbar:buttonBar,
		border:false,
		loadMask:true,
		autoScroll:true,
		autowidth:true,
		layout:'fit',
		region: 'north'
		//applyTo:'alterTab'
	});
	outGrid = itemGrid;
	itemGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		Ext.getDom('changer').value=r.data.CHANGER;
		Ext.getDom('changeTime').value=r.data.APPLY_TIME;
		Ext.getDom('changeNo').value=r.data.CHANGE_NO;
		Ext.getDom('changeDesc').value=r.data.CHANGE_DESC;
		Ext.getDom('changeId').value=r.data.CHANGE_ID;
		dict_currency_div.setComboValue(r.data.CURRENCY);
		Ext.getDom('amount').value=r.data.AMOUNT;
		
		Ext.getDom('recordhdinputname').value=r.data.CHANGE_ID;
	    var fileEl = Ext.getCmp("fileChange");
	    fileEl.enable();
	    var url = '<%=request.getContextPath()%>/extComponentServlet?type=fileGrid&resourceId=projectChange&resourceName=projectChange&recordId='+r.data.CHANGE_ID;
		rule_ns_ds.proxy= new Ext.data.HttpProxy({url:url});
		rule_ns_ds.load({params:{start:0, limit:5}});
		
	});
	////////////////
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[{ region:'north',
			title:'变更执行',
			contentEl:'changeRunformDiv',
			height:100,
			autoHeight:true,
			hidden:${closeChange},
			collapsible: false
		},{
		    region:"center",
			layout:'fit',
			buttonAlign:'center',
			items:[{
				region:'center',
				xtype:'tabpanel',
				id:'infotype',
				name:'infotype',
				plain:true,
	            minHeight:100,
	            autoScroll:'true',
	            activeTab: 0,
	            items:[{
						title:'变更信息',
						contentEl:'changeInfoformDiv',
						minHeight:100,
						autoHeight:true,
						autoScroll:'true',
						collapsible: false
					   },{
						title:'变更附件信息',
			           	contentEl: 'rule',
			           	id:'fileChange',
			           	autoScroll:'true',
			           	disabled:false,
						name:'fileChange',
						listeners:{activate:handlerActivate}
		               }]
		         }]
		},{
			border:false,
		    region:'south',
		    height:100,
			collapsible: true,
			autoScroll:true,
			items:[itemGrid]
		}]
	});	
});//end of Ext.onReady    
function viewChangeHistory(){
	var records = outGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show(records[0].json.TRADE_TYPE_D_BUSINESS_TYPE+'变更详情信息','changeProjectController.spr?action=toChagneWorkflowPictureViw&projectId='+records[0].json.CHANGE_ID,
		'',	'',	{width:880,height:400});
}

/**
 * 回调
 */
function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	showMsg(customField.returnMessage);
}
/**
 * 保存信息中心执行信息
 */
function saveChanged(){
	var changeNote = Ext.getDom('changeNote');
	
	if(changeNote!=null && changeNote.value!=null && changeNote.value!=''){
		//new AjaxEngine('changeProjectController.spr?action=saveChanged&changeId=${bo.changeId}',
		// 	{method:"post", parameters: Form.serialize('changeRunform'), onComplete: callBackHandle});
		Ext.Ajax.request({
			url: 'changeProjectController.spr?action=saveChanged&changeId=${bo.changeId}',
			params:Form.serialize('changeRunform'),
			success: function(response, options){
						var txt = Ext.util.JSON.decode(response.responseText);
							top.Ext.Msg.alert('提示',txt.returnMessage);
					}
		});
	 	
	}else{
		Ext.Msg.alert('提示','请输入意见!');
	} 	
}
/**
 * 查看贸易监控
 */
function viewTradeMonint(){
	_getMainFrame().maintabs.addPanel('贸易监控','','tradeMonitoringController.spr?action=_conditionManage&isdefault=01&queryValue='+ '${projectNo}');
}

function handlerActivate(tab){
	rule_ns_ds.load({params:{start:0,limit:5,recordId:Ext.getDom('recordhdinputname').value}});
} 
</script>
</head>
<body>
<div id="rule" class="x-hide-display" ></div>
<fiscxd:fileUpload divId="rule" increasable="${fileEdit=='true'}" erasable="${fileEdit=='true'}" 
 resourceId="projectChange" resourceName="projectChange" recordIdHiddenInputName="recordhdinputname" recordId="${bo.changeId}"></fiscxd:fileUpload>
<div id="alterTab"></div>
<div id="changeInfoformDiv" class="x-hide-display">
<form id="changeInfoform" name="changeInfoform">
<table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
  <tr>
    <td width="90" height="15">变更人:</td>
    <td width="91"><input type="text" name="changer" value="${bo.changer}" readonly="readonly"/></td>
    <td width="100">变更时间:</td>
    <td width="97"><input type="text" name="changeTime" value="${bo.changeTime}" readonly="readonly"/></td>
    <td width="65">变更批次:</td>
    <td width="243"><input type="text" name="changeNo" value="${bo.changeNo}" readonly="readonly"/></td>
  </tr>
  <tr>
    <td>描述:</td>
    <td colspan="5" width=""><textarea name="changeDesc" id="changeDesc" readonly="readonly" style="width:596px;overflow-y:visible;word-break:break-all;">${bo.changeDesc}</textarea></td>
  </tr>
  <tr>
    <td colspan="1">金额(万):</td>
  	<td>
  		<input type="text" name="amount" value="${bo.amount}" readonly="readonly"/>
  	</td>
  	
  	<td colspan="1">币别:</td>
  	<td>
  		<div id='currency_div'></div>
  	</td>
  	<td>贸易监控:</td>
  	<td><input type="button" value="..." onclick="viewTradeMonint();"/></td>
  </tr>
   
  <tr>
    <td  colspan="6" align="center">
    	<input type="hidden" name="changeId" id="changeId" value="${bo.changeId}"/>
    	<input type="hidden" name="projectId" id="projectId" value="${bo.projectId}"/>
    </td>
  </tr>
</table>
</form>
</div>
<div id="changeRunformDiv" class="x-hide-display" >
<form id="changeRunform" name="changeRunform" >
<table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" >
  <tr>
    <td width="15%">变更执行人:</td>
    <td width="25%"><input type="text" name="changeOperator" value="${bo.changeOperator}"/></td>
    <td width="15%">变更是否已执行:</td>
    <td width="25%">
    	是 <input type="radio" name="isChanged" id="c1" value="1" <%="1".equals(request.getAttribute("isChanged"))?"checked='true'":"" %>/>
		否<input name="isChanged" type="radio"  id="c0"  value="0" <%="0".equals(request.getAttribute("isChanged"))?"checked='true'":"" %>/>
	</td>
  </tr>
  <tr>
    <td width="25%">意见:</td>
    <td colspan="3"><textarea name="changeNote" style="width:90%;overflow-y:visible;word-break:break-all;">${bo.changeNote}</textarea></td>
  </tr>
  
  <tr>
    <td height="15" colspan="4" align="center">
    	<input type="button" name="saveB" value="保存" onclick="saveChanged()"/>
    </td>
  </tr>
</table>
</form>
</div>
</body>
</html>
<fiscxd:dictionary divId="currency_div" fieldName="currency" dictionaryName="BM_CURRENCY" width="150" selectedValue="${bo.currency}" needDisplayCode="true" disable="true"></fiscxd:dictionary>
