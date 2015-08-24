<%-- 
  - Author(s):张崇镇
  - Date: 2008-12-25
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:
  - <功能一>数据字典标签，完成数据字典的取数和显示功能
  - LJX 20100309 加入 defaultValue，可以设定下拉框默认值。
--%>
<%@ tag pageEncoding="utf-8"%>
<%@ tag	import="com.infolion.platform.dpframework.uicomponent.dictionary.DictionaryTagHandler"%>
<%@ tag	import="com.infolion.platform.basicApp.authManage.service.AuthenticationService"%>
<%@ tag	import="com.infolion.platform.basicApp.authManage.domain.OperationType"%>
<%@ tag	import="com.infolion.platform.dpframework.util.StringUtils"%>
<%@ attribute name="boName" required="false" description="业务对象名"%>
<%@ attribute name="boProperty" required="false" description="业务对象属性"%>
<%@ attribute name="parentBoProperty" required="false" description="父业务对象属性"%>
<%@ attribute name="divId" required="true" description="显示DIV"%>
<%@ attribute name="dictionaryName" required="true" description="数据字典名称"%>
<%@ attribute name="hiddenName" required="false" description="隐藏表单提交表单域名称"%>
<%@ attribute name="isNeedAuth" required="false" description="是否需要权限校验，默认为true"%>
<%@ attribute name="groupValue" required="false" description="分组字段的值，若填写该值则只显示分组字段值等于该值的表数据" %>
<%@ attribute name="width" required="false" description="宽度（默认值：100）"%>
<%@ attribute name="needDisplayCode" required="false" description="是否需要显示code"%>
<%@ attribute name="displayId" required="false" description="显示ID"%>
<%@ attribute name="value" required="false" description="选中值"%>
<%@ attribute name="defaultValue" required="false" description="默认选中值，当选中值为空时候生效"%>
<%@ attribute name="disabled" required="false" description="屏蔽"%>
<%@ attribute name="hidden" required="false" description="不可见"%>
<%@ attribute name="editable" required="false" description="可编辑"%>
<%@ attribute name="readOnly" required="false" description="只读"%>
<%@ attribute name="allowBlank" required="false" description="必填"%>
<%@ attribute name="isTextValue" required="false" description="是否传输显示值，默认传ID"%>
<%
	hiddenName = (StringUtils.isNullBlank(hiddenName)) ?  boName+"."+boProperty:hiddenName;
	jspContext.setAttribute("hiddenName", hiddenName);
	String jsonOptions = null;
	isNeedAuth = StringUtils.isNullBlank(isNeedAuth)?"true":"false".equals(isNeedAuth)?"false":"true";
	//是否需要显示编码
	if("true".equals(needDisplayCode))
	{
		jsonOptions = DictionaryTagHandler.getDictionaryArrayStringWithCode(dictionaryName,groupValue);
	}else{
		jsonOptions = DictionaryTagHandler.getDictionaryArrayString(dictionaryName,groupValue);
	}	
	//
	if("true".equals(isTextValue))
	{
		jspContext.setAttribute("valueField", "text");
	}else{
		jspContext.setAttribute("valueField", "id");
	}	
	jspContext.setAttribute("contextPath", request.getContextPath());
	jspContext.setAttribute("jsonOptions", jsonOptions);
	jspContext.setAttribute("width", StringUtils.isNullBlank(width) ?  "203":width);
	jspContext.setAttribute("value", StringUtils.isNullBlank(value) ? (StringUtils.isNullBlank(defaultValue)?"":defaultValue) : value);
	String operationType = OperationType.DEFAULT;
	if("true".equals(isNeedAuth)){
		operationType = AuthenticationService.isAttributePermission(hiddenName).getOperationType();
	}
	if(OperationType.UNVISIABLE.equals(operationType)){
		jspContext.setAttribute("hidden", "true");
	}
	else if(OperationType.EDIEABLE.equals(operationType)){
		jspContext.setAttribute("editable", "true");
	}
	else if(OperationType.READONLY.equals(operationType)){
		jspContext.setAttribute("readOnly", "true");
	}
	else if(OperationType.REQUIREDINPUT.equals(operationType)){
		jspContext.setAttribute("allowBlank", "false");
	}
	jspContext.setAttribute("disabled", StringUtils.isNullBlank(disabled) ?  "false":disabled);
	jspContext.setAttribute("hidden", StringUtils.isNullBlank(hidden) ?  "false":hidden);
	jspContext.setAttribute("editable", StringUtils.isNullBlank(editable) ?  "true":editable);
	jspContext.setAttribute("readOnly", StringUtils.isNullBlank(readOnly) ?  "false":readOnly);
	jspContext.setAttribute("allowBlank", StringUtils.isNullBlank(allowBlank) ?  "true":allowBlank);
	
	// 请选择
	String txtPleaseSelect=LanguageService.getText(LangConstants.SYS_PLEASE_SELECT);
	
%>

<%@tag import="com.infolion.platform.dpframework.language.LanguageService"%>
<%@tag import="com.infolion.platform.dpframework.language.LangConstants"%>
<script type="text/javascript" defer="defer">
var dict_${divId};
Ext.namespace('${divId}_ns');
 ${divId}_ns.fuc=function(){
 	return {
 		dictionary:function(){
   			var dictionary = new Ext.form.DpComboBox({
   				renderTo:'${divId}',
   				id:'${hiddenName}'+'_text',
   				hiddenName:'${hiddenName}',
   				valueField:'${valueField}',
		        <%if(null!=displayId){%>
		        displayField:'id',
		        <%}else{%>
		        displayField:'text',
		        <%}%>
   				disabled:${disabled},
   				hidden:${hidden},
   				editable:${editable},
   				readOnly:${readOnly},
   				width:${width},
   				allowBlank:${allowBlank},
   				value:'${value}',
			    store: new Ext.data.SimpleStore({
			        fields: ['id', 'text'],
			        data: ${jsonOptions}
			    }),
			    mode: 'local',
			    triggerAction: 'all',
			    emptyText:'<%=txtPleaseSelect%>...',
			    selectOnFocus:true,
	            listeners: {
	                triggerclick : function(o) {
	                	if('${parentBoProperty}'=='') return;
	                	var url;
	                	if('${needDisplayCode}'=='true') url=contextPath+'/dictionaryController.spr?action=getDictionaryArrayStringWithCode';
	                	else url=contextPath+'/dictionaryController.spr?action=getDictionaryArrayString',
	                	
						Ext.Ajax.request({
					    	url : url,
					        params : {dictName: '${dictionaryName}', groupValue: (Ext.getCmp('${boName}'+'.'+'${parentBoProperty}'+'_text')) ? Ext.getCmp('${boName}'+'.'+'${parentBoProperty}'+'_text').getValue() : ''},
					        scope : this,
					        sync:true,
					        success : function(xhr){
					        	this.store.loadData(Ext.util.JSON.decode(xhr.responseText)); 
					        }
					    });
	                }
	            }
   			});
   			
   			dict_${divId} = dictionary;
 		}
 	};  
 }();
 Ext.onReady(${divId}_ns.fuc.dictionary,${divId}_ns.fuc);
</script>
