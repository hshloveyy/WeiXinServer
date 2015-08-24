<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>仓储协议变更页面</title>
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
		proxy : new Ext.data.HttpProxy({url:'storageProtocolController.spr?action=findChange&infoId=${infoId}'}),
		reader: new Ext.data.JsonReader({
    		root: 'root',
    		totalProperty: 'totalProperty'
    	},[
    		{name:'CHANGE_ID'},
    		{name:'INFO_ID'},
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
		   	dataIndex:'INFO_ID',
		   	sortable: true,
		   	hidden:true
		 },{
		   header: '变更描述',
		   width: 100,
		   sortable: false,
		   dataIndex:'CHANGE_DESC'
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
		
	});
	////////////////
	var mainForm = new Ext.form.FormPanel({
		renderTo:'alterTab',
		layout:'form',
		autoHeight:true,
		items:[{
			title:'变更信息',
			contentEl:'changeInfoformDiv',
			minHeight:100,
			autoHeight:true,
			collapsible: false
		},{
			title:'变更执行',
			contentEl:'changeRunformDiv',
			minHeight:100,
			autoHeight:true,
			hidden:${closeChange},
			collapsible: false
		},{
			title:'变更列表',
			items:[itemGrid],
			collapsible: true
		}]
	});	
});//end of Ext.onReady    
function viewChangeHistory(){
	var records = outGrid.selModel.getSelections();
	
	top.ExtModalWindowUtil.show('审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].json.CHANGE_ID,
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
</script>
</head>
<body>
<div id="alterTab"></div>
<div id="changeInfoformDiv" class="x-hide-display">
<form id="changeInfoform" name="changeInfoform">
<table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
  <tr>
    <td width="90" height="15">变更人:</td>
    <td width="91"><input type="text" id="changer" name="changer" value="${bo.changer}" readonly="readonly"/></td>
    <td width="100">变更时间:</td>
    <td width="97"><input type="text" id="changeTime" name="changeTime" value="${bo.changeTime}" readonly="readonly"/></td>
    <td width="65">变更批次:</td>
    <td width="243"><input type="text" id="changeNo" name="changeNo" value="${bo.changeNo}" readonly="readonly"/></td>
  </tr>
  <tr>
    <td>描述:</td>
    <td colspan="5" width=""><textarea name="changeDesc" id="changeDesc" readonly="readonly" style="width:596px;overflow-y:visible;word-break:break-all;">${bo.changeDesc}</textarea></td>
  </tr>
   
  <tr>
    <td  colspan="6" align="center">
    	<input type="hidden" name="changeId" id="changeId" value="${bo.changeId}"/>
    	<input type="hidden" name="infoId" id="infoId" value="${bo.infoId}"/>
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
