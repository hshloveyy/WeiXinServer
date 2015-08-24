<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程节点配置</title>
</head>
<%
	request.setAttribute("contextPath", request.getContextPath());
//AND J.CLASS_ not in('C')
%>
<body>
<fisc:grid divId="gridDiv" editable="false" needCheckBox="true"
	pageSize="12"
	tableName="JBPM_NODE J LEFT OUTER JOIN WF_NODEDEF N ON J.ID_=N.NODEID AND J.NAME_=N.NODEDEFINITIONNAME,DD07T D"
	handlerClass="com.infolion.platform.workflow.manager.web.grid.ExtProcessDefinitionGrid"
	whereSql=" " orderBySql="ID_" height="230" needAutoLoad="false"></fisc:grid>
<fisc:tree tableName="YAPPMODEL" sourceClass="boProcessDefinitionTree"
	style="" onclick="treeClick" 
	parentColumnName="" 
	titleColumnName="APPMODELTEXT" linkUrl="#"  
	treeTitle="业务流程树" idColumnName="APPMODEL" divId="div_tree"></fisc:tree>

<div id="div_toolbar"></div>
<div id="div_north"></div>
<div id="div_center">
<form name="mainForm" id="mainForm" class="search" >
	<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">流程名：</td>
		<td width="30%">
			<input type="hidden" id="extProcessId" name="extProcessId" value="${main1.extProcessId}" >
			<input type="hidden" id="processId" name="processId" value="${main1.processId}" >
			<input type="hidden" id="boId" name="boId" value="${main.boId}">
			<input class="inputText" type="text" id="bpName" name="bpName" value="${main.bpName}" readonly="readonly">
		</td>
		<td width="15%" align="right">流程描述：</td>
		<td width="40%">
			<input class="inputText" type="text" id="bpDesc" name="bpDesc" value="${main.bpDesc}" readonly="readonly">
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">流程类型：</td>
		<td width="30%">
			<div id="processTypeDiv"></div>
		</td>
		<td width="15%" align="right">关联业务对象：</td>
		<td width="40%">
			<div id="boIdDiv"></div>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">关联jbpm流程名：</td>
		<td width="30%">
			<input class="inputText" type="text" id="processDefinitionName" name="processDefinitionName" readonly="readonly" value="${main.processDefinitionName}" readonly="readonly">
		</td>
		<td width="15%" align="right">创建者：</td>
		<td width="40%">
			<input class="inputText" type="text" id="creator_text" name="creator_text" readonly="readonly" value="" readonly="readonly">
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">创建时间：</td>
		<td width="30%">
			<input class="inputText" type="text" id="createTime" name="createTime" readonly="readonly" value="${main.createTime}" readonly="readonly">
		</td>
		<td width="15%" align="right">最后修改者：</td>
		<td width="40%">
			<input class="inputText" type="text" id="lastmodifyer_text" name="lastmodifyer_text" value="" readonly="readonly">
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">最后修改时间：</td>
		<td width="30%">
			<input class="inputText" type="text" id="lastModifyTime" name="lastModifyTime"  value="${main.lastModifyTime}" readonly="readonly">
		</td>
		<td width="15%" align="right">版本：</td>
		<td width="40%">
			<input class="inputText" type="text" id="version" name="version" value="${main1.version}" readonly="readonly">
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">激活状态：</td>
		<td colspan="2"  width="45%">
			<input type="hidden" id="active" name="active" value="${main1.active}">
			<input class="inputText" id="activeText" style="font:bolder" name="activeText" type="text" readonly="readonly"  size="4">
			<input type="button" id="btnActive"  name="btnActive" style="display: none"  value="激活此流程" title="激活此流程" onclick="changeStatus()">
		</td>
		<td width="40%"  align="left">
			<input type="button" value="查看流程图" onclick="showProcessImage($('processId').value);">
		</td>
	</tr>
	</table>
</form>
</div>
<div id="gridDiv"></div>
<div id="div_tree"></div>
</body>
<%
	String whereSql = request.getAttribute("whereSql").toString();
	request.setAttribute("whereSql", whereSql);
%>
<script type="text/javascript" defer="defer">
var operationType = ""

	function _manager(value,metadata,record){
	//alert("value="+value+"\r\nmetadata="+Ext.util.JSON.encode(metadata)+"\r\nrecord="+Ext.util.JSON.encode(record.data));
	var url = '';
	if(record.data.class_!='C')
    url='<a href="#" onclick="nodeLogicControl('+value+',\''+record.data.name_+'\',\''+record.data.class_+'\');" style="color:green">节点逻辑</a>'
    //||record.data.class_=='C'
	if(record.data.class_=='K'||record.data.class_=='R'){
		url += ' <a href="#" style="color:green" onclick="nodeActorControl('+value+',\''+record.data.extProcessId+'\');">参与者</a>';
		if(record.data.class_!='R')
			url += ' <a href="#" style="color:green" onclick="nodeDateAuthControl('+value+',\''+record.data.class_+'\',\''+record.data.extProcessId+'\');">数据权限</a>';
	}
    if(record.data.class_=='C'){
       //alert(value +" ,"+ record.data.name_+" ,"+record.data.class_);
       url='<a href="#" onclick="subWfnodeLogicControl('+value+',\''+record.data.name_+'\',\''+record.data.subProcessDefinition_+'\');" style="color:green">子流程节点逻辑</a>'
    } 
    
	return url;
}

/**
 * 子流程节点逻辑
*/
function subWfnodeLogicControl(value,name_,subProcessDefinition_)
{
	var extProcessId = $('extProcessId').value;
	var boId = $('boId').value ;
	var processId = $('processId').value ;
	var url = '<%=request.getContextPath()%>/workflow/extProcessDefinitionController.spr?action=getSubWfInfo&extProcessId='+extProcessId+
	'&nodeId='+value+'&nodeName='+escape(escape(name_))+'&subProcessDefinition_='+subProcessDefinition_+'&boId='+boId+'&processId='+processId +'&nodeClass=C';
	_getMainFrame().maintabs.addPanel('子流程('+ name_ +')节点配置',gridDiv_grid,url);

}

/**
 * 节点逻辑配置
 */
function nodeLogicControl(value,name_,class_){
	//alert(value+name_+class_);
	var extProcessId = $('extProcessId').value;//'${main1.extProcessId}';
	var boId = $('boId').value ;
	var processId = $('processId').value ;//'${main1.processId}';
	var url = '<%=request.getContextPath()%>/workflow/extNodeDefinitionController.spr?action=edit&extProcessId='+extProcessId+
	'&nodeId='+value+'&nodeName='+escape(escape(name_))+'&nodeClass='+class_+'&boId='+boId+'&processId='+processId;
	_getMainFrame().maintabs.addPanel('节点逻辑配置',gridDiv_grid,url);

}

/**
 * 节点参与者分配
 */
function nodeActorControl(value,extProcessId){
	if(extProcessId!=null&&extProcessId!=''){
		var processId = $('processId').value ;//'${main1.processId}';
		var boId = $('boId').value ;
		var url = '<%=request.getContextPath()%>/workflow/taskActorController.spr?action=edit&boId='+boId+'&nodeId='+value+'&processId='+processId+'&extProcessId='+extProcessId;
		_getMainFrame().maintabs.addPanel('节点参与者分配',gridDiv_grid,url);

	}else
		_getMainFrame().showInfo('在进行"节点参与者分配"前请先进行"节点逻辑控制"');
}

/**
 * 节点数据权限控制
 */
function nodeDateAuthControl(value,class_,extProcessId){
	if(extProcessId!=null&&extProcessId!=''){
		var boId = $('boId').value ;
		var processId = $('processId').value ;
		var url = '<%=request.getContextPath()%>/workflow/taskAuthenticationController.spr?action=edit&nodeId='+value+'&nodeClass='+class_+'&boId='+boId+'&processId='+processId+'&extProcessId='+extProcessId;
		_getMainFrame().maintabs.addPanel('节点数据权限控制',gridDiv_grid,url);
	}else
		_getMainFrame().showInfo('在进行"节点数据权限控制"前请先进行"节点逻辑控制"');
}

/**
 * 激活流程
 */
function changeStatus(){
	_getMainFrame().Ext.Msg.show({
		title:'系统提示',
		msg: '是否确定激活此版本的流程？',
		buttons: {yes:'确定', no:'取消'},
		fn: function(buttonId,text){
			if(buttonId=='yes'){
				document.getElementById('active').value = 'Y';
				var param1 = Form.serialize('mainForm');
				var param = '?action=saveOrUpdate&' + param1;
				new AjaxEngine('<%=request.getContextPath()%>/workflow/extProcessDefinitionController.spr', 
							{method:"post", parameters: param, onComplete: callBackHandle});
				operationType = "changeStatus";
			}
		},
		icon: Ext.MessageBox.WARNING
    });
}

function customCallBackHandle(transport){		
	if(operationType=="changeStatus")
	{
		$('activeText').value = "激活";
		document.getElementById("btnActive").style.display="none";
		document.getElementById("activeText").style["color"]="red";
	}
	else{	
		var responseUtil1 = new AjaxResponseUtils(transport.responseText);
		var customField = responseUtil1.getCustomField("coustom");
		_getMainFrame().showInfo(customField.returnMessage);
	}
}

/**
 * 查看流程图
 */
function showProcessImage(processDefinitionId){
	if(processDefinitionId==null||processDefinitionId==''){
		_getMainFrame().showInfo('查看流程图前请先选择一个流程！');
		return false;
	}
	var url = '<%=request.getContextPath()%>/platform/workflow/manager/processDefinitionController.spr?action=_showProcess&processDefinitionId='+processDefinitionId;
	_getMainFrame().maintabs.addPanel('流程图展示','',url);
}

function addCallBack(){
	
}

function functionCallBack(transport){
	gridDiv_grid.getStore().reload();
}


function reSet()
{
	$('bpName').value= '';
	$('boId').value= '';
	$('bpDesc').value= '';
	$('processDefinitionName').value= '';
	$('creator_text').value= '';
	$('createTime').value= '';
	$('lastmodifyer_text').value= '';
	$('version').value= '';
	$('lastModifyTime').value= '';

	//extProcessId  processId
	$('extProcessId').value= '';
	$('processId').value= '';			
	dict_boIdDiv.setValue('');
	dict_processTypeDiv.setValue('');
	$('activeText').value = "";
	reload_gridDiv_grid("defaultCondition= 1=2 ");
}

//树的单击事件
function treeClick(node){
	//node.expand();
	reSet();
	if(node.leaf)
	{
		//取得节点ID对应的信息并显示
		Ext.Ajax.request({
			url:'<%=request.getContextPath()%>/workflow/extProcessDefinitionController.spr',
			params:{'action':'getInfo','nodeId': node.id,'leaf':node.leaf},
			success: function(response,	options){				
				var	responseArray =	Ext.util.JSON.decode(response.responseText);

				var haveExtProDef = responseArray.haveExtProDef;
				if(haveExtProDef && haveExtProDef==true)
				{
					$('bpName').value= responseArray.main.bpName;
					$('bpDesc').value= responseArray.main.bpDesc;
					$('processDefinitionName').value= responseArray.main.processDefinitionName;
					$('creator_text').value= responseArray.main.creator_text;
					$('createTime').value= responseArray.main.createTime;
					$('lastmodifyer_text').value= responseArray.main.lastmodifyer_text;
					$('version').value= responseArray.main1.version;
					$('lastModifyTime').value= responseArray.main.lastModifyTime;
					//extProcessId  processId
					$('extProcessId').value= responseArray.main1.extProcessId;
					$('processId').value= responseArray.main1.processId;
					$('boId').value= responseArray.main.boId;
							
					dict_boIdDiv.setValue(responseArray.main.boId);
					dict_processTypeDiv.setValue(responseArray.main.processType);
					
					var active = responseArray.main1.active;
					$('active').value= active;
					if(active)
					{
						if(active=="Y")
						{
							$('activeText').value = "激活";
							//document.getElementById("btnActive").disabled="disabled";
							document.getElementById("btnActive").style.display="none";
							document.getElementById("activeText").style["color"]="red";
						}
						else
						{
							$('activeText').value = "非激活";
							//document.getElementById("btnActive").disabled="";
							document.getElementById("btnActive").style.display="";
							document.getElementById("activeText").style["color"]="blue";
						}
					}
	
	                var strWhere = responseArray.whereSql;
					reload_gridDiv_grid("defaultCondition= 1=1 "+ strWhere +"");
				}
			},
			failure:function(response,	options){	
				_getMainFrame().showInfo('获取流程信息错误！');
			}
		});
	}
	else
	{
		
	}
}

Ext.onReady(function(){
	
	var addButton = new Ext.Toolbar.Button({
		text:'流程配置复制',
		iconCls:'icon-add',
		handler:function(){
			if(div_tree_currentNodeId==null||div_tree_currentNodeId==''){
				_getMainFrame().Ext.MessageBox.show({
					title:'系统提示',
					msg: '请选择“流程配置复制”要用到的流程！',
					buttons: {yes:'确定'},
					icon: Ext.MessageBox.INFO
				});
				//showInfo("请选择复制流程配置要用的流程！");
				return false;
			}
			_getMainFrame().ExtModalWindowUtil.show('请选择要复制配置到哪个流程',
					contextPath + '/workflow/extProcessDefinitionController.spr?action=view&operationType=copyCreate&extProcessId='+div_tree_currentNodeId
				+'&parentNodeId='+div_tree_currentNode.parentNode.id+"&treeName="+escape(escape(div_tree_currentNode.parentNode.text)),
			'',
			addCallBack,
			{width:320,height:470});
		}
	});
	var sourText =  new Ext.form.TextField({fieldLabel:'sourText', id:'extProId',width:180});
	var targetText =  new Ext.form.TextField({fieldLabel:'targetText', id:'toExtProId',width:180});
	var copyButton = new Ext.Toolbar.Button({
		text:'复制',
		iconCls:'icon-copy',
		handler:function(){
			if(sourText.getValue()==''||targetText.getValue()==''){
			   _getMainFrame().Ext.MessageBox.show({
					title:'系统提示',
					msg: '请录入原外部扩展流程ID和要复制的外部扩展流程ID！',
					buttons: {yes:'确定'},
					icon: Ext.MessageBox.INFO
			   });
			   return;
			}
			var param = '?extProId=' + sourText.getValue();
         		param = param + '&toExtProId=' + targetText.getValue();            				
				param = param + "&action=copyBDPProcessConfig";
				new AjaxEngine(contextPath + '/workflowController.spr', 
				   {method:"post", parameters: param, onComplete: callBackHandle});
		}
	});

	var addToolbar = new Ext.Toolbar({
		renderTo:'div_north',
		items:[addButton,'->','原(EXTPROID)',sourText,'目标(TO_EXTPROID)',targetText,'->',copyButton,'->']
	});

	//布局
   	var viewport = new Ext.Viewport({
   		layout:"border",
   		items:[{
   			region:'center',
   			border:false,
			layout:'border', 
			items:[{
				region:'north',
				height:0,
                border:false,
				contentEl:'div_north'
			},{
                region:"west",
	            title:"",
	            split:true,          //可改变框体大小
	            collapsible: true,   //可收缩
	            width: 200,
	            minSize: 175,
	            maxSize: 400,
	            margins:'0 0 0 5',
	            layout:'fit',
	            layoutConfig:{
	               animate:true
	            },
	            items:[{
	                title:'业务流程树',
	                border:false,
	                layout:'fit',
	                tools:[{
	                    id:'refresh',   
	                    qtip: '刷新资源树信息',
	                     handler: function(event, toolEl, panel) {
	                		div_tree_tree.body.mask('刷新中...');
	                		reload_div_tree_tree();
	                        div_tree_tree.root.collapse(true, false);
	                        setTimeout(function(){
	                        	div_tree_tree.body.unmask();
	                        }, 1000);
	                     }
	                },{
	                id:'maximize',
	                qtip:'展开树',
	                handler:function(event, toolEl, panel) {
	                	div_tree_tree.expandAll();
	                }
	                },{
	                id:'minimize',
	                qtip:'收起树',
	                handler:function(event, toolEl, panel) {
	                	div_tree_tree.collapseAll();
	                }}],
	                items:[{autoScroll:true,contentEl:'div_tree'}]
	            }]
			},{
				region:'center',
        		autoScroll: true,
        		autoWidth: true ,
        		autoHeight: true ,
        		
                border:false,
	            items:[{
	        		region:'center',
	        		title:'流程属性',
	        		contentEl:'div_center'
		        },{
		        	region:'south',
		        	layout:'fit',
		        	title:'流程节点',
		        	contentEl:'gridDiv'
		    	}]
			}]
   	   	}]
   	});
});
</script>
</html>
<fisc:dictionary hiddenName="boId" dictionaryName="YBUSINESSOBJECT"
	divId="boIdDiv" value="${main.boId}" allowBlank="false" disabled="true"
	isNeedAuth="false"></fisc:dictionary>
<fisc:dictionary hiddenName="processType" dictionaryName="YDBPWFTYPE"
	divId="processTypeDiv" value="${main.processType}" allowBlank="false"
	disabled="true" isNeedAuth="false"></fisc:dictionary>
