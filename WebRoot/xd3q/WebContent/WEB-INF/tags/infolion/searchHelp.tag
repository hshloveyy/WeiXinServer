<%-- 
  - Author(s):黄虎
  - Date: 2009-5-25
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:
  - <功能一>搜索帮助tag
--%>
<%@ tag pageEncoding="utf-8"%>
<%@ tag	import="com.infolion.platform.dpframework.util.StringUtils"%>
<%@ attribute name="boName" required="true"  description="业务对象名"%>
<%@ attribute name="boProperty" required="true"  description="业务对象属性"%>
<%@ attribute name="divId" required="true" description="div id"%>
<%@ attribute name="disabled" required="false" description="屏蔽"%>
<%@ attribute name="hidden" required="false" description="不可见"%>
<%@ attribute name="editable" required="false" description="可编辑"%>
<%@ attribute name="readOnly" required="false" description="只读"%>
<%@ attribute name="allowBlank" required="false" description="必填"%>
<%@ attribute name="value" required="false" description="值"%>
<%@ attribute name="pageSize" required="false" description="页大小"%>
<%@ attribute name="callBackHandler" required="false" description="回调函数"%>
<%@ attribute name="searchHelpName" required="false" description="搜索帮助名称"%>
<%@ attribute name="searchType" required="false" description="搜索帮助类型：field或者form"%>
<%@ attribute name="hiddenName" required="false" description="隐藏表单提交表单域名称"%>
<%@ attribute name="valueField" required="false" description="用于保存真实数据的字段名 ，来源于搜索帮助所配置的表字段"%>
<%@ attribute name="displayField" required="false" description="用于显示数据的字段名，来源于搜索帮助所配置的表字段"%>
<%@ attribute name="defaultCondition" required="false" description="默认查询条件"%>
<%
	//如果不指定搜索帮助类型，默认为form
	if(StringUtils.isNullBlank(searchType)){
		searchType="form";
	}else if(StringUtils.equals(searchType,"field")){
		//如果是字段型搜索帮助，hiddenName,valueField,displayField三个属性都不允许为空
		if(StringUtils.isNullBlank(hiddenName)||StringUtils.isNullBlank(valueField)||StringUtils.isNullBlank(displayField))
			throw new RuntimeException("业务对象："+boName+"的属性："+boProperty+"上的搜索帮助配置，如果是字段(field)型搜索帮助，hiddenName,valueField,displayField三个属性都不允许为空，请检查业务对象配置。");
	}
	jspContext.setAttribute("initValue",request.getAttribute(boName+boProperty));
	jspContext.setAttribute("nodeId",request.getAttribute("workflowNodeDefId"));
	jspContext.setAttribute("disabled", StringUtils.isNullBlank(disabled) ?  "false":disabled);
	jspContext.setAttribute("hidden", StringUtils.isNullBlank(hidden) ?  "false":hidden);
	jspContext.setAttribute("editable", StringUtils.isNullBlank(editable) ?  "true":editable);
	jspContext.setAttribute("readOnly", StringUtils.isNullBlank(readOnly) ?  "false":readOnly);
	jspContext.setAttribute("allowBlank", StringUtils.isNullBlank(allowBlank) ?  "true":allowBlank);
	jspContext.setAttribute("value", StringUtils.isNullBlank(value) ?  "":value);
	jspContext.setAttribute("pageSize", StringUtils.isNullBlank(pageSize) ?  "10":pageSize);
	jspContext.setAttribute("hiddenName", StringUtils.isNullBlank(hiddenName) ?  boName+"."+boProperty:hiddenName);
	
	
%>
<script type="text/javascript" defer="defer">
var ${divId}_sh;
Ext.namespace('${divId}_ns');
 ${divId}_ns.fuc=function(){
 	return {
 		searchHelp:function(){
    			var searchHelp = new Ext.form.SearchHelpField({
					<%if(StringUtils.isNullBlank(boName) || "null".equals(boName)){%>
						<%if(!StringUtils.isNullBlank(hiddenName)&&!"null".equals(hiddenName)){%>
					id:'${hiddenName}'+'_text',
						<%}else{%>
					id:'${divId}'+'_text',
						<%}%>
						<%}else{%>       			
    				id:'${boName}'+'.'+'${boProperty}'+'_text',
						<%}%>
    				renderTo:'${divId}',
    				boName:'${boName}',
    				boProperty:'${boProperty}',
    				nodeId:'${nodeId}',
    				disabled:${disabled},
    				hidden:${hidden},
    				hideTrigger:${hidden},
    				editable:${editable},
    				readOnly:${readOnly},
    				allowBlank:${allowBlank},
    				defaultCondition:"${defaultCondition}",
    				value:'${value}',
    				initValue1: '${initValue}',
    				<%if(!StringUtils.isNullBlank(searchHelpName)&&!"null".equals(searchHelpName)){%>
    				shlpName:'${searchHelpName}',
    				<%}%>
    				<%if(!StringUtils.isNullBlank(callBackHandler)&&!"null".equals(callBackHandler)){%>
    				callBack:${callBackHandler},
    				<%}%>
    				<%if(StringUtils.equals(searchType,"field")){%>
    				shlpType:'field',
    				hiddenName:'${hiddenName}',
    				valueField:'${valueField}',
    				displayField:'${displayField}',
    				<%}else{%>
    				shlpType:'form',
    				hiddenName:'${boName}'+'.'+'${boProperty}',
    				<%}%>
    				pageSize:${pageSize}
    			});
    			
    			${divId}_sh = searchHelp;
 		}
 	};  
 }();
 Ext.onReady(${divId}_ns.fuc.searchHelp,${divId}_ns.fuc);
</script>