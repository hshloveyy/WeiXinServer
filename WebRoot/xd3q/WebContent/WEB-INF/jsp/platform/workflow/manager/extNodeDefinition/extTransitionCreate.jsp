<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>扩展流程流向设置</title>
</head>
<body>
<div id='div_center' class="search">
<form action="" name="mainForm" id="mainForm" >
	<input type="hidden" id="nodeId" name="nodeId" value="${main.nodeId}">
    <input type="hidden" name="extProcessId" value="${main.extProcessId}" id="extProcessId">
	<input type="hidden" id="processId" name="processId" value="${main.processId}">
	<input type="hidden" id="extendTransitionId" name="extendTransitionId" value="${main.extendTransitionId}">
	<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">路径名称：</td>
		<td width="30%" >
			<input class="inputText" type="text" id="transitionName" name="transitionName" value="${main.transitionName}" readonly="readonly">
		</td>
		<td width="15%" align="right">下一节点名：</td>
		<td width="40%" >
			<input class="inputText" type="text" id="nextNodeName" name="nextNodeName" value="${main.nextNodeName}" readonly="readonly">
			<div id="conditionTypeDiv"></div>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" valign="middle">条件内容：</td>
		<td width="85%" colspan="3">
			<div id="sign_div"></div>
			<input type="hidden" id="condition" name="condition" value="${main.condition}">
			<input type="hidden" id="jsonCondition" name="jsonCondition" value="">
		</td>
	</tr>
	</table>
	<table width="100%" id="buttonTable" border="0" cellpadding="4" cellspacing="1">
	<tr><td>&nbsp;</td></tr>
	<tr align="center">
		<td width="37%"></td>
		<td align="right" width="5%">
			<div id="save_div"></div>
		</td>
		<td width="5%"></td>
		<td align="left" width="5%">
			<div id="clean_div"></div>
		</td>
		<td width="5%"></td>
		<td align="left" width="5%">
			<div id="close_div"></div>
		</td>
		<td width="37%"></td>
	</tr>
	</table>
</form>
</div>
</body>
<script type="text/javascript" defer="defer">
Ext.onReady(function(){
	var combo = new Ext.form.ComboBox({
	  store: new Ext.data.SimpleStore({
	      fields: ['id', 'text'],
	      data : ${boproperties}
	  }),
	  valueField :"id",
	  displayField:'text',
	  //allowBlank:false,
	  hiddenName:'boProperty', 
	  typeAhead: true,
	  editable:true,
	  mode: 'local',
	  triggerAction: 'all',
	  selectOnFocus:true,
	  anchor:'90%'
	});
	
	var lcombo = new Ext.form.ComboBox({
	  store: new Ext.data.SimpleStore({
	      fields: ['id', 'text'],
	      data : [[' ',' '],['(','('],['((','(('],['(((','((('],['((((','((((']]
	  }),
	  valueField :"id",
	  displayField:'text',
	  //allowBlank:false,
	  hiddenName:'lParenthesis', 
	  typeAhead: true,
	  editable:true,
	  mode: 'local',
	  triggerAction: 'all',
	  selectOnFocus:true,
	  anchor:'90%'
	});
	
	var rcombo = new Ext.form.ComboBox({
	  store: new Ext.data.SimpleStore({
	      fields: ['id', 'text'],
	      data : [[' ',' '],[')',')'],['))','))'],[')))',')))'],['))))','))))']]
	  }),
	  valueField :"id",
	  displayField:'text',
	  //allowBlank:false,
	  hiddenName:'rParenthesis', 
	  typeAhead: true,
	  editable:true,
	  mode: 'local',
	  triggerAction: 'all',
	  selectOnFocus:true,
	  anchor:'90%'
	});
	
	var operatorCombo = new Ext.form.ComboBox({
	  store: new Ext.data.SimpleStore({
	      fields: ['id', 'text'],
	      data : [['>','大于'],['==','等于'],['<','小于'],['>=','大于等于'],['<=','小于等于'],['!=','不等于']] 
	  }),
	  valueField :"id",
	  displayField:'text',
	  //allowBlank:false,
	  hiddenName:'operator', 
	  typeAhead: true,
	  editable:true,
	  mode: 'local',
	  triggerAction: 'all',
	  selectOnFocus:true,
	  anchor:'90%'
	});
	
	var connectorCombo = new Ext.form.ComboBox({
	  store: new Ext.data.SimpleStore({
	      fields: ['id', 'text'],
	      data : [['and','并且'],['or','或者']] 
	  }),
	  valueField :"id",
	  displayField:'text',
	  //allowBlank:false,
	  hiddenName:'connector', 
	  typeAhead: true,
	  editable:true,
	  mode: 'local',
	  triggerAction: 'all',
	  selectOnFocus:true,
	  anchor:'90%'
	});
	
	var rec = Ext.data.Record.create([
		{name: 'lParenthesis', type: 'string'},
	    {name: 'boProperty', type: 'string'},
	    {name: 'operator', type: 'string'},
	    {name: 'value', type: 'string'},
	    {name: 'rParenthesis', type: 'string'},
	    {name: 'connector', type: 'string'}
	]);
	
	var cm = new Ext.grid.ColumnModel([
	   	{
	          header: "左括号",
	          dataIndex: 'lParenthesis',
	          editor: lcombo,
	          renderer: Ext.util.Format.comboRenderer(lcombo)
	       },{
	          header: "业务对象属性",
	          dataIndex: 'boProperty',
	          editor: combo,
	          renderer: Ext.util.Format.comboRenderer(combo)
	       },{
	          header: "操作符",
	          dataIndex: 'operator',
	          editor: operatorCombo,
	          renderer: Ext.util.Format.comboRenderer(operatorCombo)
	       },{
	          header: "值",
	          dataIndex: 'value',
	          editor: new Ext.form.TextField({
	          })
	       },{
	          header: "右括号",
	          dataIndex: 'rParenthesis',
	          editor: rcombo,
	          renderer: Ext.util.Format.comboRenderer(rcombo)
	       },{
	          header: "连接符",
	          dataIndex: 'connector',
	          editor: connectorCombo,
	          renderer: Ext.util.Format.comboRenderer(connectorCombo)
	       }
	   ]); 
	
	   var store = new Ext.data.Store({
	       reader: new Ext.data.JsonReader({
	      		root: 'data',
	              totalProperty: 'totalSize'
	         }, rec),
	       remoteSort: false
	   });
	   
	   store.on('update', function(s,r,o){
	   	if(s.getCount()==s.indexOf(r)+1){
	        var p = new rec({
		        lParenthesis: '',
		        boProperty: '',
		        operator: '',
		        value: '',
		        rParenthesis: '',
		        connector: ''
	        });
	        s.insert(s.getCount(), p);
	   	}
	});
	   
	var grid = new Ext.grid.EditorGridPanel({
		id:'conditionGrid',
	    store: store,
	    cm: cm,
	    height:200,
	    width:520,
	    enableHdMenu:false,
	    enableColumnMove:false,
	    enableColumnResize:false,
	    iconCls:'icon-grid',
	    //frame:true,
	    clicksToEdit:1,
	    autoScroll:true,
	    stripeRows : false,
	    renderTo :'sign_div',
		viewConfig: {
			//forceFit: true,
			autoFill: true
	  	}
	});
	                    
	//var arr = {'data':[{lParenthesis: '(',boProperty: '',operator: '=',value: '12',rParenthesis: ')',connector: 'and'},{lParenthesis: '((',boProperty: '',operator: '<',value: '23',rParenthesis: '))',connector: 'or'}]};
	//grid.getStore().loadData(arr);  
		
	grid.getStore().removeAll();
	grid.stopEditing();
	var value = ${jsonConditionStr};
	Ext.get('jsonCondition').dom.value=value
	if(value.trim()!=""){
		grid.getStore().loadData(Ext.util.JSON.decode(value));  
	}else{
	   	var p = new rec({
	       lParenthesis: '',
	       boProperty: '',
	       operator: '',
	       value: '',
	       rParenthesis: '',
	       connector: ''
	   	});
	   	grid.getStore().insert(0, p);
	}
	grid.startEditing(0, 0);
	
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[{
			region:'center',
			border:false,
			buttonAlign:'center',
			items:[{
					layout:'fit',
					region:'center',
					height:435,
					border:false,
					bodyStyle:"background-color:#DFE8F6",
					contentEl:'div_center'
				}]
		}]
	});
});

function _save(){
    var s = Ext.getCmp('conditionGrid').getStore();
        	
   	var value = "$"+"{";
   	var jsonvalue = "{'data':[";
   	
   	for (var i=0;i<s.getCount();i++){
   		var rec = s.getAt(i);
   		
   		var obj = rec.data; 
    	for(var j in obj){
    		if (obj[j]!="")value += obj[j] + " ";
    	}
   		jsonvalue += Ext.util.JSON.encode(rec.data) + ",";
   	}
   	value = value + "}";
   	jsonvalue = jsonvalue.substring(0, jsonvalue.length-1) + "]}";
   	Ext.get('condition').dom.value = value;
   	Ext.get('jsonCondition').dom.value = jsonvalue;
	var param1 = Form.serialize('mainForm');
	//alert(param1);
	//return;
	var param = '?action=saveOrUpdateTransition&' + param1;
	new AjaxEngine('<%=request.getContextPath()%>/workflow/extNodeDefinitionController.spr', 
				{method:"post", parameters: param, onComplete: callBackHandle});
}

function _clean(){
	_getMainFrame().Ext.MessageBox.show({
		title:'系统提示',
		msg: '是否确定要清空节点路径配置？',
		buttons: {yes:'确定', no:'取消'},
		icon: Ext.MessageBox.QUESTION,
		fn:function(buttonid){
			if (buttonid == 'yes'){
				var param = '?action=_cleanTransitionDef&extProcessId=${main.extProcessId}&nodeId=${main.nodeId}&extendTransitionId=${main.extendTransitionId}';
				new AjaxEngine('<%=request.getContextPath()%>/workflow/extNodeDefinitionController.spr', 
							{method:"post", parameters: param, onComplete: callBackHandle});
				operationObject = "delete";
			}
		}
	});
}

function _close(){
	_getMainFrame().ExtModalWindowUtil.markUpdated();
	_getMainFrame().ExtModalWindowUtil.close();
}

//回调函数
function customCallBackHandle(transport){
	//alert(transport.responseText);
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	//_getMainFrame().ExtModalWindowUtil.alert('[提示]',customField.returnMessage);
	_getMainFrame().showInfo(customField.returnMessage);
	setTimeout(function(){
		//var promptMessagebox = new MessageBoxUtil();
		//promptMessagebox.Close();
		_getMainFrame().ExtModalWindowUtil.markUpdated();
		_getMainFrame().ExtModalWindowUtil.close();},1500);
}
</script>
</html>
<fisc:button divId="save_div" text="确定" name="save1" onclick="_save" ></fisc:button>
<fisc:button divId="clean_div" text="清空" name="clean1" onclick="_clean" disable="${empty main.extendTransitionId}"></fisc:button>
<fisc:button divId="close_div" text="取消" name="close1" onclick="_close" ></fisc:button>
<fisc:dictionary dictionaryName="YDWFCONDITIONTYPE" divId="conditionTypeDiv" hiddenName="conditionType" value="2" isNeedAuth="false" hidden="true"></fisc:dictionary>