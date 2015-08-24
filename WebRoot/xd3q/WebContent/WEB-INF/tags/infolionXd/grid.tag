<%@ tag pageEncoding="utf-8"%>
<%@ attribute name="divId" required="true" description="div id"%>
<%@ attribute name="url" required="false" description="数据源路径"%>
<%@ attribute name="height" required="true"  description="表格高度"%>
<%@ attribute name="width" required="true"  description="表格宽度"%>
<%@ attribute name="title" required="false"  description="表格标题"%>
<%@ attribute name="pageSize" required="true"  description="每页显示条数"%>
<%@ attribute name="columns" required="true"  description="列索引,多列用,分隔"%>
<%@ attribute name="headers" required="true"  description="列表头,多列用,分隔"%>
<%@ attribute name="rendererHeaders" required="false"  description="EXTJS渲染列标题"%>
<%@ attribute name="rendererColumns" required="false"  description="EXTJS渲染列列"%>
<%@ attribute name="rendererHandlers" required="false"  description="EXTJS渲染列列处理类"%>
<%@ attribute name="sql" required="true"  description=""%>
<%@ attribute name="showMsgColumn" required="false"  description=""%>

<%
	columns = columns.toUpperCase();
	String[] fields = columns.split(",");
 	String[] clms = headers.split(",");
 	String[] urltitles = null;
 	String[] urlcols = null;
 	String[] urlhands = null;
 	if(rendererHeaders!=null){
 		urltitles = rendererHeaders.split(",");
 	}
 	if(rendererColumns!=null){
 		urlcols = rendererColumns.split(",");
 	}
 	if(rendererHandlers!=null){
 		urlhands = rendererHandlers.split(",");
 	}
 	if(sql!=null&&!"".equals(sql)){
 		jspContext.setAttribute("url",request.getContextPath()+"/extComponentServlet?type=grid&grid_sql="+sql+"&grid_columns="+columns+"&grid_size="+pageSize);
 	}
 	
%>   
<script type="text/javascript">

var ${divId}_grid;
 Ext.namespace('${divId}_ns');
 ${divId}_ns.fuc=function(){
 	return {
 		grid:function(){
 				var fields=[<%for(int i=0;i<fields.length;i++){if(i!=fields.length-1){%>{name:'<%=fields[i]%>'},<%}else{%>{name:'<%=fields[i]%>'}<%}}%>];
 				var columns=[new Ext.grid.RowNumberer(),
 				<%for(int i=0;i<clms.length;i++){
 				    if(i!=clms.length-1){%>
 					{header:'<%=clms[i]%>',sortable:true,dataIndex:'<%=fields[i]%>'},
 				<%}else{%>
 					{header:'<%=clms[i]%>',sortable:true,dataIndex:'<%=fields[i]%>'}
 				<%}}%>
 				<%if(urltitles!=null && urlcols!=null && urlhands!=null){%>
	 				<%for(int i=0;i<urltitles.length;i++){%>
	 					,{header:'<%=urltitles[i]%>',sortable:false,dataIndex:'<%=urlcols[i]%>',renderer:<%=urlhands[i]%>}
	 				<%}%>
 				<%}%>
 				];
 				var store = new Ext.data.Store({
        			proxy : new Ext.data.HttpProxy({url:"${url}"}),
        			reader: new Ext.data.JsonReader({
            					root: 'root',
            					totalProperty: 'totalProperty'
            				},
            				fields
 							)
 				});
 				var cm = new Ext.grid.ColumnModel(
 					columns
 				);
 				var pagingBar = new Ext.PagingToolbar({
	            	displayInfo:true,
	            	emptyMsg:'没有数据显示',
	            	displayMsg:"显示从{0}条数据到{1}条数据，共{2}条数据",
	            	store:store,
	            	pageSize:${pageSize}
	        	});
 				
    			var grid = new Ext.grid.GridPanel({
        			renderTo:'${divId}',
    				ds:store,
    				cm:cm,
    				bbar:pagingBar,
    				height:${height},
    				width:${width},
        			border:true,
        			loadMask:true,
        			autoScroll:true,
        			frame:true,
        			title:'${title}',
        			viewConfig: {
    			        forceFit: true
    			    },
    			    iconCls:'icon-grid'
    			});   
    			${divId}_grid = grid;
    			grid.on('rowclick',function(g,r,e){
        			if('${showMsgColumn}'!=null){
            			<%=showMsgColumn==null?"":"Ext.Msg.alert('Msg',store.getAt(r).data."+showMsgColumn+")"%>
        			}
    			});
    			store.load({params:{start:0, limit:${pageSize}}});
 		}
 	};  
 }();
 Ext.onReady(${divId}_ns.fuc.grid,${divId}_ns.fuc);
</script>