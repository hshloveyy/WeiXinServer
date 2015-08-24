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
	background-image:url(images/fam/find.png) !important;
}
.x-grid-row-bgcolor{  
    background-color:#ffffcc;
}
</style>
</head>
<body>
<div id="main" class="x-hide-display">
<form id="findCondictionFR" method="post">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
    <tr >
      <td width="8%" height="20" align="right">模块名称</td>
      <td width="15%"><input name="modalName" type="text" width="15%"/></td>
      <td width="8%" align="right">业务信息</td>
      <td width="30%"><input name="businessInfo" type="text" width="30%"/></td>
      <td width="8%" align="right">任务名称</td>
      <td width="15%"><input name="taskName" type="text" width="15%" /></td>
    </tr>
    <tr align="center">
      <td align="center" colspan="6"><input type="button" value="查找" onclick="find()"/> <input type="reset" value="清空"/></td>
	</tr>
</table>
</form>
</div>

<div id="div_center">
</div>
</body>
</html>
<script type="text/javascript">
var ds;
var grid;

function find(){
	var url = 'workflowController.spr?action=getMyProcessInstances&'+Form.serialize('findCondictionFR');
	ds.proxy= new Ext.data.HttpProxy({url:url});
	ds.load({params:{start:0, limit:10}});
}


function callbackFunction(){
	ds.reload();
}

/**
 * 审批
 */
function operWorkItem(){
	var records = grid.selModel.getSelections();
	var modelName = records[0].json.modelName;
	var businessNote = records[0].json.businessNote;
	var taskName = records[0].json.taskName;
	//工作流类别 1：为信达旧系统流程， 2：为BDP系统流程。
	var processType  = records[0].json.processType;
	var taskId = records[0].json.taskId ;
	var processUrl = records[0].json.processUrl;
	var processId = records[0].json.processId;
	var businessId = records[0].json.businessRecordId ;
	var nodeId = records[0].json.nodeId ;
	var extProcessId = records[0].json.extProcessId;

	//XDSS旧系统，审批
	if(processType=="1")
	{
		var strUrl=processUrl + 
			'&processId='+ processId + 
			'&taskId='+ taskId +
			'&businessRecordId='+businessId;
		//+'-'+taskName+'-'+businessNote
		_getMainFrame().maintabs.addPanel(modelName,grid,strUrl);	
	}
	//BDP系统，审批
	else if(processType=="2")
	{
		 //alert("extProcessId" + extProcessId + "processUrl" +  processUrl + ",taskId:" +  taskId + ",businessId:" +  businessId + ",taskName:" + taskName + ",nodeId:" + nodeId);
		_doWork(processUrl,taskId,businessId,taskName,nodeId,extProcessId);
	}
}

/**
 * BDP系统，办理待办任务(审批)
 */
function _doWork(processUrl,taskId,businessId,taskName,nodeId,extProcessId){
	var url = "<%=request.getContextPath()%>/"+processUrl+"&workflowNodeDefId="+extProcessId+ "|" + nodeId + "&workflowTaskId="+taskId+"&businessId="+businessId+"&taskName="+taskName+"&extProcessId="+extProcessId;
	_getMainFrame().maintabs.addPanel('办理待办任务',grid,url);	
}

/**
 * 显示流程图。
 */
function showWorkFlow(){
	var records = grid.selModel.getSelections();
	var modelName = records[0].json.modelName;
	var businessNote = records[0].json.businessNote;
	var taskName = records[0].json.taskName;
	//工作流类别 1：为信达旧系统流程， 2：为BDP系统流程。
	var processType  = records[0].json.processType;
	var taskId = records[0].json.taskId ;

	//XDSS旧系统流程图查看。
	if(processType=="1")
	{
		var strUrl = "common/showProcessStateImg.jsp?taskId="+taskId;
	
		top.ExtModalWindowUtil.show(modelName+'-'+taskName+'-'+businessNote,
		strUrl,
		'',
		'',
		{width:1000,height:500});
	}
	//BDP系统，流程图查看
	else if(processType=="2")
	{
		_viewBdpWorkFlowMap(taskId);
	}
}

/**
 * BDP查看流程图。
 */
function _viewBdpWorkFlowMap(taskId){
	var url = "<%=request.getContextPath()%>/platform/workflow/manager/processDefinitionController.spr?action=_showProcessTask&taskId="+taskId;
	_getMainFrame().maintabs.addPanel('查看流程图',grid,url);	
}


function findWairForItem(){
	
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'workflowController.spr?action=getMyProcessInstances'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'modelName'},
            		{name:'businessNote'},
					{name:'taskName'},
					{name:'insCreateTime'},
					{name:'examineAndApprove'},
					{name:'workFlowState'},
					{name:'processUrl'},
					{name:'processId'},
					{name:'taskId'},
					{name:'businessRecordId'},
					{name:'nodeId'},
					{name:'processType'},
					{name:'assignLogic'},
					{name:'boId'},
					{name:'taskCreateTime'},
					{name:'insEndTime'},
					{name:'endNodeName'},
					{name:'parentCommonProcessId'},
					{name:'commonProcessId'},
					{name:'extProcessId'}
			  ])
    });

    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		　{header: '模块名称',
           width: 80,
           sortable: false,
           dataIndex: 'modelName'
           },
           {header: '业务信息',
               width: 240,
               sortable: false,
               dataIndex: 'businessNote',
               renderer:renderBusiness
           },
		  {header: '任务名称',
           width: 100,
           sortable: false,
           dataIndex: 'taskName'
           },
           {header: '操作',
           width: 50,
           sortable: true,
           dataIndex: 'examineAndApprove',
           renderer:renderExamineAndApprove
           },
           {header: '开始时间',
           width: 100,
           sortable: true,
           dataIndex: 'insCreateTime'
           },
           {header: '流程状态',
           width: 120,
           sortable: true,
           dataIndex: 'workFlowState',
           renderer:renderWorkFlowState
           },
           {header: 'processUrl',
           width: 100,
           sortable: true,
           dataIndex: 'processUrl',
           hidden:true
           },
           {header: 'processId',
           width: 100,
           sortable: true,
           dataIndex: 'processId',
           hidden:true
           },
           {header: '流程任务实例ID',
           width: 100,
           sortable: true,
           dataIndex: 'taskId',
           hidden:true
           },
           {header: '业务记录ID',
           width: 100,
           sortable: true,
           dataIndex: 'businessRecordId',
           hidden:true
           },
           {header: '流程节点ID',
           width: 100,
           sortable: true,
           dataIndex: 'nodeId',
           hidden:true
           },
           {header: '工作流类别',
           width: 100,
           sortable: true,
           dataIndex: 'processType',
           hidden:true
           },
           {header: 'assignLogic',
           width: 100,
           sortable: true,
           dataIndex: 'assignLogic',
           hidden:true
           },
           {header: '业务对象ID',
           width: 100,
           sortable: true,
           dataIndex: 'boId',
           hidden:true
           },
           {header: '任务创建时间',
           width: 100,
           sortable: true,
           dataIndex: 'taskCreateTime',
           hidden:true
           },
           {header: '任务结束时间',
           width: 100,
           sortable: true,
           dataIndex: 'insEndTime',
           hidden:true
           },
           {header: '结束节点名称',
           width: 100,
           sortable: true,
           dataIndex: 'endNodeName',
           hidden:true
           },
           {header: 'parentCommonProcessId',
           width: 100,
           sortable: true,
           dataIndex: 'parentCommonProcessId',
           hidden:true
           },
           {header: 'commonProcessId',
           width: 100,
           sortable: true,
           dataIndex: 'commonProcessId',
           hidden:true
           },
           {header: 'extProcessId',
               width: 100,
               sortable: true,
               dataIndex: 'extProcessId',
               hidden:true
           }  
    ]);
    cm.defaultSortable = true;
    
    var bbar = new Ext.PagingToolbar({
        pageSize: 10,
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
        region:'center',
        el: 'div_center',
        autowidth:true,
		viewConfig : {  
                forceFit : true,  
                enableRowBody:true,
                getRowClass : 
                	function(record,rowIndex,rowParams,store){ 
                		var css;
                		if(rowIndex%2==0){
                			css= 'x-grid-row-bgcolor'; 
                		}else
                			css= '';	
                		return css;
                	}  
        },          
        layout:'fit'
    });
    
    ds.load({params:{start:0, limit:10},arg:[]});

    /**
     * '操作'
     */
    function renderExamineAndApprove(value, p, record){
    	return String.format('<a href="#" onclick="operWorkItem()">{0}</a>',value);
    }

    /**
    * '业务信息'
    */
    function renderBusiness(value,p,record){
	    try
		{
		  if(value.indexOf('|付款日:')>0){
		     var paydate = value.substring(value.indexOf('|付款日:')+5,value.indexOf('|付款日:')+15);
		     var paydate1 = paydate.replace(/-/g, "/"); 
		     paydate1 = new Date(paydate1);
		     s2 = new Date();
		     var days= paydate1.getTime() - s2.getTime(); 
		     var time = parseInt(days / (1000 * 60 * 60 * 24));
		     if(time<=3){
		        return '<font color="red">'+value+'</font>';
		     }
		     return  value;
		   }
		   else if(value.indexOf('|押汇到期付款日:')>0){
		     var paydate = value.substring(value.indexOf('|押汇到期付款日:')+9,value.indexOf('|押汇到期付款日:')+19);
		     var paydate1 = paydate.replace(/-/g, "/"); 
		     paydate1 = new Date(paydate1);
		     s2 = new Date();
		     var days= paydate1.getTime() - s2.getTime(); 
		     var time = parseInt(days / (1000 * 60 * 60 * 24));
		     if(time<=3){
		        return '<font color="red">'+value+'</font>';
		     }
		     return  value;
		   }
		   return value;
		}
		catch(err)
		{
		  return value;
		}
    }

    /**
     * 流程状态
     */
    function renderWorkFlowState(value, p, record){
    	return String.format('<a href="#" onclick="showWorkFlow()">{0}</a>',value);
    }
    
    
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title:"查询",
			collapsible: true,
			height:82,
			items:[{contentEl:'main'}]
		},{
			region:"center",
			layout:'border',
			title:"数据显示",
			contentEl: 'div_center',
			items:[grid]
		}]
	});
});
//间隔一段时间查询一次,以保证显示未结束的提审
function reflash()
{   
	ds.reload();
	setTimeout("reflash()",20000*60);
}
setTimeout("reflash()",20000*60);
</script>  