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
		pageSize: 10,
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
		
		//Ext.getDom('saveA').disabled=true;
		if(r.data.CHANGE_STATE_D_CHANGE_STATE!='变更申请'){
			Ext.getDom('submitA').disabled=true;
			Ext.getDom('deleteA').disabled=true;
			Ext.getDom('saveA').disabled=true;
			if(r.data.CHANGE_STATE_D_CHANGE_STATE=='变更审批中'){
				if(${wfCanModify}){
					Ext.getDom('addA').disabled=true;
					Ext.getDom('saveA').disabled=false;
				}
			}
		}else{
			Ext.getDom('deleteA').disabled=false;
			Ext.getDom('submitA').disabled=false;
			Ext.getDom('addA').disabled=true;
			Ext.getDom('saveA').disabled=false;
		}	
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
	top.ExtModalWindowUtil.show( '审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].json.CHANGE_ID,
		'',	'',	{width:880,height:400});
}

function closeChangeCallback(){
	outChangeDS.load({params:{start:0,limit:10}});
}
/**
 * 删除变更
 */
function deleteChange(){
	if (outGrid.selModel.hasSelection()){
		var records = outGrid.selModel.getSelections();
		if (records.length > 1){
			top.ExtModalWindowUtil.alert('提示','只能选择一条记录');
			return ;
		}else if(records[0].json.CHANGE_STATE_D_CHANGE_STATE!='变更申请'){
			top.ExtModalWindowUtil.alert('提示','此记录不能删除');
			return;
		}else{
				top.Ext.Msg.show({
						title:'提示',
						msg: '是否确定删除记录',
						buttons: {yes:'是', no:'否'},
						fn: function(buttonId,text){
							if(buttonId=='yes'){
									Ext.Ajax.request({
									url: 'storageProtocolController.spr?action=deleteChange&changeId='+records[0].json.CHANGE_ID,
									success: function(response, options){
									   var json = Ext.util.JSON.decode(response.responseText);
										if(json.success){
											Ext.Msg.alert('提示',json.msg);
											closeChangeCallback();
											Ext.getDom('changeDesc').value='';
											Ext.getDom('changeNo').value='';
											Ext.getDom('changeId').value='';
											Ext.getDom('addA').disabled=false;
										}else{
											Ext.Msg.alert('提示',json.msg);
										}
									}
								});
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
/**
 * 保存
 */
function saveChange(){
	var count = outChangeDS.getTotalCount();
	var addFlag = true;
	var hasApply = false;
	for(var i=0;i<count;i++){
		var r = outChangeDS.getAt(i);
		if(r.data.CHANGE_STATE_D_CHANGE_STATE=='变更审批中'){
			addFlag = false;
			break;
		}else if (r.data.CHANGE_STATE_D_CHANGE_STATE=='变更申请'){
			hasApply = true;
			break;
		}
	}
	
	if(hasApply && !outGrid.selModel.hasSelection()){
		Ext.Msg.alert('提示','请选择一条记录进行修改');
		return;
	}
	//来自流程,判断是否可修改
	if(${wfCanModify}){
		addFlag = true;
	}
	if(addFlag){
		var desc = Ext.getDom('changeDesc').value;
		if(desc==null || desc.trim()==''){
			Ext.Msg.alert('提示',"请输入描述");
		}else{
			Ext.Ajax.request({
				url: 'storageProtocolController.spr?action=saveChange&infoId=${infoId}',
				params:Form.serialize('changeInfoform'),
				success: function(response, options){
					var json = Ext.util.JSON.decode(response.responseText);
					Ext.Msg.alert('提示',json.msg);
					Ext.getDom('changeId').value=json.changeId;
					Ext.getDom('addA').disabled=true;
					if(json.changeNo!=null)
						Ext.getDom('changeNo').value=json.changeNo;
					closeChangeCallback();
				}
			});
		}
	}else{
		Ext.Msg.alert('提示','还有变更未完成,不能增加变更记录');
	}
}
/**
 * 提交工作流审批
 */
 var submitAction='';
function submitChange(){
	if(!outGrid.selModel.hasSelection()){
		Ext.Msg.alert('提示','请选择一条记录进行提交');
		return;
	}
	var records = outGrid.selModel.getSelections();
	if(records[0].json.CHANGE_STATE_D_CHANGE_STATE=='变更申请'){
	  Ext.Msg.show({
   		title:'提示',
   		msg: '确定提交流程审批',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			if(buttonId=='yes'){
   				submitAction = 'submit';
				Ext.Ajax.request({
					url: 'storageProtocolController.spr?action=submitChangeWorkflow',
					params:Form.serialize('changeInfoform'),
					success: function(response, options){
								var txt = Ext.util.JSON.decode(response.responseText);
								Ext.Msg.alert('提示',txt['infolion-json']['coustom'].returnMessage);
								setTimeout(function(){
									top.ExtModalWindowUtil.markUpdated();
									top.ExtModalWindowUtil.close();
								},1000);	
							}
				});
   			}
   		}
	});	
   }else{
	 Ext.Msg.alert('提示','只能提交变更申请的记录');
   }			
}
/**
 * 回调
 */
function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	showMsg(customField.returnMessage);
	if(submitAction=='submit'){
		setTimeout(function(){
			top.ExtModalWindowUtil.markUpdated();
			top.ExtModalWindowUtil.close();
		},1000);	
	}	
}
/**
 * 保存信息中心执行信息
 */
function saveChanged(){
	var changeNote = Ext.getDom('changeNote');
	
	if(changeNote!=null && changeNote.value!=null && changeNote.value!=''){
		Ext.Ajax.request({
			url: 'storageProtocolController.spr?action=saveChanged&changeId=${bo.changeId}',
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
function addChange(){
	Ext.getDom('changeId').value='';
	Ext.getDom('changeDesc').value='';
	Ext.getDom('changeNo').value='';
	Ext.getDom('saveA').disabled=false;
	Ext.getDom('submitA').disabled=false;
	Ext.getDom('deleteA').disabled=false;
	init();
}
function init(){
	var count = outChangeDS.getTotalCount();
	var hasApply=false;
	for(var i=0;i<count;i++){
		var r = outChangeDS.getAt(i);
		if (r.data.CHANGE_STATE_D_CHANGE_STATE=='变更申请' ){
			Ext.getDom('addA').disabled=true;
			Ext.getDom('saveA').disabled=true;
			break;
		}else if(r.data.CHANGE_STATE_D_CHANGE_STATE=='变更审批中'){
			Ext.getDom('addA').disabled=true;
			Ext.getDom('saveA').disabled=true;
			Ext.getDom('submitA').disabled=true;
			Ext.getDom('deleteA').disabled=true;
			break;
		}
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
    <td width=""><input type="text" id="changeNo" name="changeNo" value="${bo.changeNo}" readonly="readonly"/></td>
  </tr>
  <tr>
    <td>描述:</td>
    <td colspan="5" width=""><textarea name="changeDesc" id="changeDesc" style="width:596px;overflow-y:visible;word-break:break-all;">${bo.changeDesc}</textarea></td>
  </tr>  
  <tr>
    <td  colspan="6" align="center">
    	<input type="button" id="addA" onclick="addChange()" value="增加"/> 
    	<input type="button" id="saveA" onclick="saveChange()" value="保存"/> 
    	<input type="button" id="submitA" onclick="submitChange()" value="提交"/>
    	<input type="button" id="deleteA" onclick="deleteChange()" value="删除"/>
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
<script type="text/javascript">
	Ext.getDom('submitA').disabled=true;
	Ext.getDom('deleteA').disabled=true;
	Ext.getDom('saveA').disabled=true;
</script>
</html>