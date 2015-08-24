<!-- 此JSP功能已经被 extProcessDefMgr.jsp 替代。 -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String whereSql = request.getAttribute("whereSql").toString();
request.setAttribute("whereSql",whereSql);
%><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>扩展流程定义</title>
</head>
<body>
<form name="mainForm" id="mainForm" class="search" style="width:;">
	<input type="hidden" id="extProcessId" name="extProcessId" value="${main1.extProcessId}" >
	<input type="hidden" id="processId" name="processId" value="${main1.processId}" >
	<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">流程名：</td>
		<td width="30%">
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
			<fisc:user boProperty="creator" boName="" userId="${main.creator}"></fisc:user>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">创建时间：</td>
		<td width="30%">
			<input class="inputText" type="text" id="createTime" name="createTime" readonly="readonly" value="${main.createTime}" readonly="readonly">
		</td>
		<td width="15%" align="right">最后修改者：</td>
		<td width="40%">
			<fisc:user boProperty="lastModifyer" boName="" userId="${main.lastModifyer}"></fisc:user>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">最后修改时间：</td>
		<td width="30%">
			<input class="inputText" type="text" id="lastModifyTime" name="lastModifyTime" readonly="readonly" value="${main.lastModifyTime}" readonly="readonly">
		</td>
		<td width="15%" align="right">版本：</td>
		<td width="40%">
			<input class="inputText" type="text" id="version" name="version" value="${main1.version}" readonly="readonly">
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">激活状态：</td>
		<td width="15%"><%
		if(request.getAttribute("isActive")!=null&&StringUtils.isNotBlank(request.getAttribute("isActive").toString())){
			if("Y".equals(request.getAttribute("isActive"))){ %>
			<input type="hidden" id="active" name="active" value="${main1.active}">
			<input class="inputText" type="text" readonly="readonly" value="激活" style="font:bolder;color: red;"><%
			}else{ %>
			<input type="hidden" id="active" name="active" value="${main1.active}">
			<input class="inputText" type="text" readonly="readonly" value="非激活" style="font:bolder;color: blue;" size="4">
			<input type="button" value="激活此流程" title="激活此流程" onclick="changeStatus()"><%
			}
		}else{ %>
			<input class="inputText" type="text" id="active" name="active" value="${main1.active}" readonly="readonly"><%
		}%>
		</td>
		<td colspan="2" align="center"><input type="button" value="查看流程图" onclick="showProcessImage(${main1.processId});"></td>
	</tr>
	</table>
</form>
<div id="gridDiv"></div>
<script type="text/javascript" defer="defer">
var operationType = ""
//Ext.onReady(function(){
   	
	var tabs = new Ext.Panel({
		id:'mainPanel',
        renderTo: document.body,
        autoWidth:true,
        loadMask:true,
        frame:true,
        //layout:"absolute",
        //cls:'search',
        baseCls:'scarch',
        //defaults:{autoHeight: true},
        items:[
            	{contentEl:'mainForm',id:'mainForm1', title: '流程属性'},
            	{contentEl:'gridDiv',id:'gridDiv1'}
              ]
    });
//});

function functionCallBack(transport){
	gridDiv_grid.getStore().reload();
}

function _manager(value,metadata,record){
	//alert("value="+value+"\r\nmetadata="+Ext.util.JSON.encode(metadata)+"\r\nrecord="+Ext.util.JSON.encode(record.data));
	var url = '<a href="#" onclick="nodeLogicControl('+value+',\''+record.data.name_+'\',\''+record.data.class_+'\');" style="color:green">节点逻辑</a>'
	if(record.data.class_=='K'||record.data.class_=='C'||record.data.class_=='R'){
		url += ' <a href="#" style="color:green" onclick="nodeActorControl('+value+',\''+record.data.extProcessId+'\');">参与者</a>';
		if(record.data.class_!='R')
			url += ' <a href="#" style="color:green" onclick="nodeDateAuthControl('+value+',\''+record.data.class_+'\',\''+record.data.extProcessId+'\');">数据权限</a>';
	}
	return url;
}

function nodeLogicControl(value,name_,class_){
	//alert(value+name_+class_);
	var extProcessId = '${main1.extProcessId}';
	var boId = '${main.boId}';
	var processId = '${main1.processId}';
	var url = '<%=request.getContextPath()%>/workflow/extNodeDefinitionController.spr?action=edit&extProcessId='+extProcessId+
	'&nodeId='+value+'&nodeName='+escape(escape(name_))+'&nodeClass='+class_+'&boId='+boId+'&processId='+processId;
	_getMainFrame().maintabs.addPanel('节点逻辑配置',gridDiv_grid,url);
	/*_getMainFrame().ExtModalWindowUtil.show('节点逻辑配置',
			'<%=request.getContextPath()%>/workflow/extNodeDefinitionController.spr?action=edit&extProcessId='+extProcessId+'&nodeId='+value+'&nodeName='+escape(escape(name_))+'&nodeClass='+class_,
			'',
			functionCallBack,
			{width:694,height:550}
		);*/
}

function nodeActorControl(value,extProcessId){
	if(extProcessId!=null&&extProcessId!=''){
		var processId = '${main1.processId}';
		var boId = '${main.boId}';
		var url = '<%=request.getContextPath()%>/workflow/taskActorController.spr?action=edit&boId='+boId+'&nodeId='+value+'&processId='+processId;
		_getMainFrame().maintabs.addPanel('节点参与者分配',gridDiv_grid,url);
		/*_getMainFrame().ExtModalWindowUtil.show('节点参与者分配',
				'<%=request.getContextPath()%>/workflow/taskActorController.spr?action=edit&nodeId='+value,
				'',
				functionCallBack,
				{width:694,height:554}
			);*/
	}else
		_getMainFrame().showInfo('在进行"节点参与者分配"前请先进行"节点逻辑控制"');
}
function nodeDateAuthControl(value,class_,extProcessId){
	if(extProcessId!=null&&extProcessId!=''){
		var url = '<%=request.getContextPath()%>/workflow/taskAuthenticationController.spr?action=edit&nodeId='+value+'&nodeClass='+class_+'&boId=${main.boId}';
		_getMainFrame().maintabs.addPanel('节点数据控制',gridDiv_grid,url);
		/*_getMainFrame().ExtModalWindowUtil.show('节点数据控制',
				'<%=request.getContextPath()%>/workflow/taskAuthenticationController.spr?action=edit&nodeId='+value+'&nodeClass='+class_,
				'',
				functionCallBack,
				{width:694,height:516}
			);*/
	}else
		_getMainFrame().showInfo('在进行"节点数据控制"前请先进行"节点逻辑控制"');
}

function changeStatus(){
	_getMainFrame().Ext.Msg.show({
		msg: '是否确定激活此版本的流程？',
		buttons: {yes:'确定', no:'取消'},
		fn: function(buttonId,text){
			if(buttonId=='yes'){
				document.getElementById('active').value = 'Y';
				var param1 = Form.serialize('mainForm');
				//alert(param1);
				//return;
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
	//alert(transport.responseText);
	if(operationType=="changeStatus")
		window.location.reload();
	else{	
		var responseUtil1 = new AjaxResponseUtils(transport.responseText);
		var customField = responseUtil1.getCustomField("coustom");
		_getMainFrame().showInfo(customField.returnMessage);
	}
}

function showProcessImage(processDefinitionId){
	if(processDefinitionId==null||processDefinitionId==''){
		_getMainFrame().showInfo('查看流程图前请先选择一个流程！');
		return false;
	}
	var url = '<%=request.getContextPath()%>/platform/workflow/manager/processDefinitionController.spr?action=_showProcess&processDefinitionId='+processDefinitionId;
	_getMainFrame().maintabs.addPanel('流程图展示',null,url);
}
</script>
<fisc:dictionary hiddenName="boId" dictionaryName="YBUSINESSOBJECT" divId="boIdDiv" value="${main.boId}" allowBlank="false" disabled="true" isNeedAuth="false" ></fisc:dictionary>
<fisc:dictionary hiddenName="processType" dictionaryName="YDBPWFTYPE" divId="processTypeDiv" value="${main.processType}" allowBlank="false" disabled="true" isNeedAuth="false" ></fisc:dictionary>
<fisc:grid divId="gridDiv" editable="false" needCheckBox="true" pageSize="12" title="流程节点" tableName="JBPM_NODE J LEFT OUTER JOIN WF_NODEDEF N ON J.ID_=N.NODEID AND J.NAME_=N.NODEDEFINITIONNAME,DD07T D" 
 handlerClass="com.infolion.platform.workflow.manager.web.grid.ExtProcessDefinitionGrid" whereSql="${whereSql}" orderBySql="ID_" 
 height="360" needAutoLoad="true"></fisc:grid>
</body>
</html>