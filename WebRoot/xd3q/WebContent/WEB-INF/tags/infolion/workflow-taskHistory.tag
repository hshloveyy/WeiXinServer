<%@ tag pageEncoding="utf-8"%>
<%@ tag	import="com.infolion.platform.dpframework.util.StringUtils"%>
<%@ attribute name="businessRecordId" required="true" rtexprvalue="true" description="业务记录id"%>
<%@ attribute name="divId" required="true" rtexprvalue="true"	description="显示DIV"%>
<%@ attribute name="width" required="true" rtexprvalue="true"	description="宽度"%>
<%@taglib prefix="fisc" tagdir="/WEB-INF/tags/infolion" %>
<%jspContext.setAttribute("width", StringUtils.isNullBlank(width) ?  "850":width); %>
<fisc:grid divId="${divId}" defaultCondition=" BUSINESSID='${businessRecordId}' or PARENTBUSINESSID='${businessRecordId}'"  orderBySql=" TASKCREATETIME desc " handlerClass="com.infolion.platform.workflow.engine.web.TaskHistoryGrid" tableSqlClass="taskHistoryTableSql" editable="false" needCheckBox="false" needAutoLoad="true" needToolbar="false"></fisc:grid>
<script type="text/javascript" defer="defer">
Ext.onReady(function(){
	${divId}_grid.on('rowclick', function(grid, rowIndex, e){
		var examine = ${divId}_store.getAt(rowIndex).get('EXAMINE');
		_getMainFrame().showInfo(examine);
	});
	
	var cm = ${divId}_grid.getColumnModel();
	cm.setRenderer(cm.findColumnIndex('EXAMINE'),function(v,m,r){
		return v;
	});
	
});
</script>
