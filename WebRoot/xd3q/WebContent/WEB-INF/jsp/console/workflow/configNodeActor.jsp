<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.add{
	background-image:url(images/fam/add.gif) !important;
}
.delete{
	background-image:url(images/fam/delete.gif) !important;
}
.update{
	background-image:url(images/fam/refresh.gif) !important;
}
.find{
	background-image:url(images/fam/user_comment.png) !important;
}
.selectuser{
background-image:url(images/fam/user_add.gif) !important;
}
.roleuser{
background-image:url(images/fam/user_edit.png) !important;
}
.x-grid3-td-topic .x-grid3-cell-inner {
    white-space:normal;
}
.red-row .x-grid3-cell-inner{
	color:red;
}
</style>
</head>
<body>
<div id="div_northForm"></div>
<div id="div_center">
</div>
</body>
</html>
<script type="text/javascript">
var ds;
var grid;

var NodeCombo;
var NodeStore;

var VerCombo;
var VerStore;

function callbackFunction(){
	ds.reload();
}

function openWin(){

	var records = grid.selModel.getSelections();
	top.ExtModalWindowUtil.show('配置('+records[0].json.processdefitionName+')中('+records[0].json.name+')节点的参与人信息',
	'workflowController.spr?action=addConfigNodeActorView&processid='+records[0].json.processdefitionId+'&taskid='+records[0].json.id,
	'',
	callbackFunction,
	{width:650,height:500});
}
function openWinDecision(){

	var records = grid.selModel.getSelections();
	top.ExtModalWindowUtil.show('配置('+records[0].json.processdefitionName+')中('+records[0].json.name+')判断配置信息',
	'workflowController.spr?action=addConfigNodeDecisionView&processid='+records[0].json.processdefitionId+'&nodeid='+records[0].json.id,
	'',
	callbackFunction,
	{width:650,height:500});
}
function openWinAction(){

	var records = grid.selModel.getSelections();
	top.ExtModalWindowUtil.show('配置('+records[0].json.processdefitionName+')中('+records[0].json.name+')动作配置信息',
	'workflowController.spr?action=addConfigNodeActionView&processid='+records[0].json.processdefitionId+'&nodeid='+records[0].json.id,
	'',
	callbackFunction,
	{width:650,height:500});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	var PrecessRecordDef = Ext.data.Record.create([    
       	{name: 'id'},
       	{name: 'text'},
       	{name: 'nick'}
   	]);
   	
   	var ProcessStore=new Ext.data.Store({    
    	//设定读取的地址
    	proxy: new Ext.data.HttpProxy({url: 'workflowController.spr?action=queryProcessForComboBox'}),    
    	//设定读取的格式    
    	reader: new Ext.data.JsonReader({    
        	id:"id",totalProperty:'totalProperty',root:'root'
    	}, PrecessRecordDef),    
    	remoteSort: true   
	});
	
	var ProcessCombo=new Ext.form.ComboBox({
		id:'processecombox',
		selectOnFocus:true,
		valueField:'id',
		hiddenName:'ProcessCombo',
		displayField:'text',
		fieldLabel: '流程',
		blankText:'请选择一个流程',
		emptyText:'请选择一个流程',
		editable:true,
		anchor:'100%',
		forceSelection:true,
		triggerAction:'all',
		allowBlank:false,
		store:ProcessStore,
		typeAhead: true,
		listeners:{select:function(){
			NodeCombo.reset();
			NodeStore.proxy= new Ext.data.HttpProxy({url: 'workflowController.spr?action=queryProcessNodeForComboBox&processid='+ProcessCombo.getValue()});
			NodeStore.load();
		},
			beforequery:function(e){                   
		              var combo = e.combo;     
		              if(!e.forceAll){
		                 var input = e.query;     
		                  // 检索的正则   
		                  var regExp = new RegExp(".*" + input + ".*");   
		                 // 执行检索   
		                  combo.store.filterBy(function(record,id){     
		                       // 得到每个record的项目名称值   
		                      var text = record.get(combo.displayField);     
		                       return regExp.test(text);    
		                  });   
		                  combo.expand();     
		                  return false;   
		              }   
		    }
	  }
    });
    
    var coProcessCombo = new Ext.form.ComboBox({
		id:'coprocessecombox',
		selectOnFocus:true,
		valueField:'id',
		hiddenName:'coProcessCombo',
		displayField:'text',
		fieldLabel: '参考流程',
		blankText:'请选择一个流程',
		emptyText:'请选择一个流程',
		editable:true,
		anchor:'100%',
		forceSelection:true,
		triggerAction:'all',
		allowBlank:false,
		store:ProcessStore,
		typeAhead: true,
		listeners:{select:function(){
			VerCombo.reset();
			VerStore.proxy= new Ext.data.HttpProxy({url: 'workflowController.spr?action=queryProcessVerForComboBox&processid='+coProcessCombo.getValue()});
			VerStore.load();
		},
			beforequery:function(e){                   
		              var combo = e.combo;     
		              if(!e.forceAll){
		                 var input = e.query;     
		                  // 检索的正则   
		                  var regExp = new RegExp(".*" + input + ".*");   
		                 // 执行检索   
		                  combo.store.filterBy(function(record,id){     
		                       // 得到每个record的项目名称值   
		                      var text = record.get(combo.displayField);     
		                       return regExp.test(text);    
		                  });   
		                  combo.expand();     
		                  return false;   
		              }   
		    }
	  }
    });
    
    var NodeRecordDef = Ext.data.Record.create([    
       	{name: 'id'},
       	{name: 'text'},
       	{name: 'nick'}
   	]);
   	
   	NodeStore=new Ext.data.Store({    
    	//设定读取的地址
    	proxy: new Ext.data.HttpProxy({url: ''}),    
    	//设定读取的格式    
    	reader: new Ext.data.JsonReader({    
        	id:"id",totalProperty:'totalProperty',root:'root'
    	}, NodeRecordDef),    
    	remoteSort: true   
	});
	
	VerStore=new Ext.data.Store({    
    	//设定读取的地址
    	proxy: new Ext.data.HttpProxy({url: ''}),    
    	//设定读取的格式    
    	reader: new Ext.data.JsonReader({    
        	id:"id",totalProperty:'totalProperty',root:'root'
    	}, NodeRecordDef),    
    	remoteSort: true   
	});
	
	NodeCombo=new Ext.form.ComboBox({
		id:'nodecombox',
		selectOnFocus:true,
		valueField:'id',
		hiddenName:'NodeCombo',
		displayField:'text',
		fieldLabel: '节点',
		blankText:'请选择一个节点',
		emptyText:'请选择一个节点',
		editable:false,
		anchor:'100%',
		forceSelection:true,
		triggerAction:'all',
		allowBlank:false,
		store:NodeStore,
		typeAhead: true
    });
    
    
    VerCombo=new Ext.form.ComboBox({
		id:'vercombox',
		selectOnFocus:true,
		valueField:'id',
		hiddenName:'VerCombo',
		displayField:'text',
		fieldLabel: '版本',
		blankText:'请选择一个版本',
		emptyText:'请选择一个版本',
		editable:false,
		anchor:'100%',
		forceSelection:true,
		triggerAction:'all',
		allowBlank:false,
		store:VerStore,
		typeAhead: true
    });
    
    var simple = new Ext.form.FormPanel({
    	baseCls: 'x-plain',
    	el:'div_northForm',
	    labelWidth: 55,
	    height:70,
	    width:960,
	    labelAlign:'right',
	    buttonAlign:'right',
	    bodyStyle:'padding:5px 5px 0',
	    autoTabs:true,
	    activeTab:0,                    
	    deferredRender:false,
	    border:false,
	    items: [{
	    	layout:"column",
	    	border:false,
	    	items:[{
	    		columnWidth: 0.25,
				layout: "form",
				border:false,
	    		items:[ProcessCombo]
	    	},{
	    		columnWidth: 0.25,
				layout: "form",
				border:false,
	    		items:[NodeCombo]
	    	},{
	    		columnWidth: 0.25,
				layout: "form",
				border:false,
	    		items:[coProcessCombo]
	    	},{
	    		columnWidth: 0.25,
				layout: "form",
				border:false,
	    		items:[VerCombo]
	    	}]
	    }],
	    buttons:[{
	    	text:'搜索',
	    	handler:function(){
	    		ds.proxy= new Ext.data.HttpProxy({url: 'workflowController.spr?action=queryProcessNodeForGrid&processid='+ProcessCombo.getValue()+ '&taskid='+NodeCombo.getValue()});
				ds.load();
	    	}
	    },{
	    	text:'复制',
	    	handler:function(){	    	
	    	    Ext.Msg.show({
                   title:'提示',
                   closable:false,
                   msg:'参考复制配置？',
                   buttons:{yes:'确定',no:'取消'},
                   fn:function(btnId,txt){
                           if(btnId=='yes'){
                                var param = '?poid=' + ProcessCombo.getValue();
	            				param = param + '&copoid=' + VerCombo.getValue();            				
								param = param + "&action=copyProcessConfig";
								new AjaxEngine('workflowController.spr', 
								   {method:"post", parameters: param, onComplete: callBackHandle});
                             
                           }
                       },
                   icon:Ext.MessageBox.INFO
                });
	    	}
	    }]
	});	
   	
   	ds = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({url:''}),
        reader: new Ext.data.JsonReader({
        root: 'root',
        totalProperty: 'totalProperty'
        },[
	    {name:'id'},
		{name:'name'},
		{name:'description'},
		{name:'processdefitionId'},
		{name:'processdefitionName'},
		{name:'childcount'},
		{name:'showText'}
	    ])
    });

    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		　{header: '节点编号',
           width: 100,
           sortable: false,
           dataIndex: 'id',
           hidden:true
           },
		  {header: '节点名称',
           width: 200,
           sortable: false,
           dataIndex: 'name'
           },
           {header: '流程编号',
           width: 100,
           sortable: true,
           dataIndex: 'processdefitionId',
           hidden:true
           },
           {header: '流程名称',
           width: 120,
           sortable: true,
           dataIndex: 'processdefitionName'
           },
           {header: '节点描述',
           width: 300,
           sortable: true,
           dataIndex: 'description'
           },
           {
           header: '参与者个数',
           width: 100,
           sortable: true,
           dataIndex: 'childcount',
           hidden:true
           },
           {
           header: '操作',
           width: 100,
           sortable: true,
           dataIndex: 'showText',
           renderer:renderOper
           }
    ]);
    cm.defaultSortable = true;
    
    var bbar = new Ext.PagingToolbar({
        pageSize: 50,
        store:ds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    grid = new Ext.grid.GridPanel({
    	id:'dictGrid',
        ds: ds,
        cm: cm,
        bbar: bbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        stripeRows: true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    function renderOper(value, p, record){
    	
    	if(value=='配置'){
    	    return String.format('<a href="#" onclick="openWin()">{0}</a>',value);
    	}else if(value=='判断配置'){
    	    return String.format('<a href="#" onclick="openWinDecision()">{0}</a>','判断配置');
    	}else if(value=='动作配置'){
    	    return String.format('<a href="#" onclick="openWinAction()">{0}</a>','动作配置');
    	}
    	//return value;
    }
    
    grid.getView().getRowClass = function(record, index){
        return (record.data.childcount< 1 ? 'red-row' : '');   
    };
   	
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title:"条件查询",
			collapsible: true,
			height:100,
			items:[simple]
		},{
			region:"center",
			layout:'border',
			title:"数据显示",
			contentEl: 'div_center',
			items:[grid]
		}]
	});
});
</script>