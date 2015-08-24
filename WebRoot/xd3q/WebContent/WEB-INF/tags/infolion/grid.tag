<%-- 
  - Author(s):张崇镇
  - Date: 2009-5-25
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:
  - grid的名称以boName为优先，entityName为其次，在没有指定这两个属性的情况下以divid为grid name
  - <功能一>表格标签，默认支持从业务对象中取得表格，
  -也可通过自定义实现com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler和特定数据源的表格
--%>
<%@ tag pageEncoding="utf-8"%>
<%@ tag	import="com.infolion.platform.dpframework.uicomponent.grid.web.BoGridTagHandler"%>
<%@ tag	import="com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler"%>
<%@ tag	import="com.infolion.platform.dpframework.uicomponent.grid.domain.Grid"%>
<%@ tag	import="com.infolion.platform.dpframework.util.StringUtils"%>
<%@ tag	import="java.net.URLEncoder"%>
<%@ attribute name="boName" required="false"  description="业务对象名"%>
<%@ attribute name="gridId" required="false"  description="grid Id用于同一个业务对象，在一个页面中多次出现grid的情况，用于明确指定grid"%>
<%@ attribute name="divId" required="true" description="div id"%>
<%@ attribute name="pageSize" required="false"  description="每页显示条数"%>
<%@ attribute name="height" required="false"  description="表格高度"%>
<%@ attribute name="width" required="false"  description="表格宽度"%>
<%@ attribute name="title" required="false"  description="表格标题"%>
<%@ attribute name="url" required="false" description="数据源路径"%>
<%@ attribute name="tableName" required="false" description="表名"%>
<%@ attribute name="whereSql" required="false" description="条件语句"%>
<%@ attribute name="orderBySql" required="false" description="排序条件语句"%>
<%@ attribute name="groupBySql" required="false" description="分组语句"%>
<%@ attribute name="handlerClass" required="false" description="表格构造实现类完整类名，必须继承com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler抽象类"%>
<%@ attribute name="defaultCondition" required="false"  description="默认grid过滤条件"%>
<%@ attribute name="editable" required="false"  description="是否可编辑"%>
<%@ attribute name="needCheckBox" required="false"  description="是否需要显示复选框"%>
<%@ attribute name="needToolbar" required="false"  description="是否需要显示toolbar"%>
<%@ attribute name="needAutoLoad" required="false"  description="是否需要自动加载数据"%>
<%@ attribute name="needAuthentication" required="false"  description="是否需要行数据权限校验"%>
<%@ attribute name="needOperationColumn" required="false"  description="是否需要操作列"%>
<%@ attribute name="nameSapceSupport" required="false"  description="是否支持子对象命名空间，如果为true，会在生成的函数名中加入业务对象名，以区分不同业务对象的相同语义的方法"%>
<%@ attribute name="entityName" required="false" description="实体类名，区别于业务对象，在可编辑grid时使用"%>
<%@ attribute name="isFixed" required="false" description="是否固定宽度，如果否，则将自动根据grid的总宽度作为宽度，默认为是"%>
<%@ attribute name="tableSqlClass" required="false" description="table查询语句的构造类，实现com.infolion.platform.dpframework.uicomponent.grid.data.TableSql接口"%>
<%@ attribute name="authSqlClass" required="false" description="table外部权限构造类，实现com.infolion.platform.dpframework.uicomponent.grid.data.AuthSql接口"%>
<%@ attribute name="distinctSupport" required="false"  description="是否支持数据的distinct查询"%>
<%
	GridTagHandler gridTagHandler = null;
	String userUrl="";
	String gridUrl= "";
	boolean editablee = (StringUtils.isNullBlank(editable)||"false".equals(editable))?false:true;
	boolean needCheckBoxx = (StringUtils.isNullBlank(needCheckBox)||"false".equals(needCheckBox))?false:true;
	boolean needToolbarx = (StringUtils.isNullBlank(needToolbar)||"true".equals(needToolbar))?true:false;
	boolean needOperationColumnx = (StringUtils.isNullBlank(needOperationColumn)||"true".equals(needOperationColumn))?true:false;
	boolean nameSapceSupportx = (StringUtils.isNullBlank(nameSapceSupport)||"false".equals(nameSapceSupport))?false:true;
	boolean needAuthenticationx = (StringUtils.isNullBlank(needAuthentication)||"false".equals(needAuthentication))?false:true;
	String distinctSupportx = (StringUtils.isNullBlank(distinctSupport)||"false".equals(distinctSupport))?"false":"true";

	boolean isFixedx = (StringUtils.isNullBlank(isFixed)||"false".equals(isFixed))?false:true;

	if(StringUtils.isNullBlank(tableSqlClass)){
		tableSqlClass= "";
	}
	if(needAuthenticationx&&StringUtils.isNullBlank(authSqlClass)){
		authSqlClass= "com.infolion.xdss3.CommonDataAuthSql";
	}else{
		authSqlClass= "";
	}
	if(StringUtils.isNullBlank(handlerClass)){
 		gridTagHandler = new BoGridTagHandler(boName);	
		userUrl= request.getContextPath()+ "/gridQueryController.spr?action=queryGrid&boName="+boName+"&defaultCondition="+defaultCondition+"&editable="+editablee+"&needAuthentication="+needAuthenticationx+"&orderSql="+orderBySql+"&groupBySql="+groupBySql+"&authSqlClass="+authSqlClass+"&distinctSupport="+distinctSupportx;
	}
	else{
		if(StringUtils.isNullBlank(url)&&StringUtils.isNullBlank(tableName)&&StringUtils.isNullBlank(tableSqlClass))
			throw new Exception("如果指定了自定义表格构造实现类，则必须指定grid取数url或者指定表名。");
		try{
			gridTagHandler = (GridTagHandler)Class.forName(handlerClass).newInstance();
		}catch(Exception ex){
			throw new Exception("类不存在、构造异常，或者表格构造实现类未继承com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler抽象类："+ex.getMessage());
		}
		userUrl= request.getContextPath()+ "/gridQueryController.spr?action=queryGrid&boName="+boName+"&defaultCondition="+defaultCondition+"&handlerClass="+handlerClass+"&tableName="+tableName+"&whereSql="+whereSql+"&orderSql="+orderBySql+"&groupBySql="+groupBySql+"&editable="+editablee+"&needAuthentication="+needAuthenticationx+"&tableSqlClass="+tableSqlClass+"&authSqlClass="+authSqlClass+"&distinctSupport="+distinctSupportx;
	}	
	//url = /gridQueryController.spr?action=queryGrid
	String url2="";
	url2 = request.getContextPath()+ url + "&boName="+boName+"&defaultCondition="+defaultCondition+"&handlerClass="+handlerClass+"&tableName="+tableName+"&whereSql="+whereSql+"&orderSql="+orderBySql+"&groupBySql="+groupBySql+"&editable="+editablee+"&needAuthentication="+needAuthenticationx+"&tableSqlClass="+tableSqlClass+"&authSqlClass="+authSqlClass+"&distinctSupport="+distinctSupportx;
	gridUrl = StringUtils.isNullBlank(url)?userUrl:url2;
	Grid grid = gridTagHandler.getGrid();
	grid.setGridId(gridId);
	grid.setEditable(editablee);
	grid.setNeedCheckBox(needCheckBoxx);
	grid.setNeedToolbar(needToolbarx);
	grid.setNeedOperationColumn(needOperationColumnx);
	grid.setNameSapceSupport(nameSapceSupportx);
	grid.setNeedAuthentication(needAuthenticationx);
	grid.setFixed(isFixedx);
	gridTagHandler.setGrid(grid);
	String gridName = "";
	if(!StringUtils.isNullBlank(gridId)){
		gridName = gridId;
		if(!StringUtils.isNullBlank(boName))
			entityName = boName;
		else
			entityName = divId;	
	}
	else if(!StringUtils.isNullBlank(boName)){
		gridName = boName;
		entityName = boName;
	}
	else {
		if(StringUtils.isNullBlank(entityName)&&editablee==true)
			throw new Exception("如果业务对象名为空，同时grid可编辑时，必须指定entityName属性，表示grid关联的实体，请检查配置。");
		else if(!StringUtils.isNullBlank(entityName)){
			gridName = entityName;
		}else{
			gridName = divId;		
			entityName = divId;	
		}
	}
	grid.setName(gridName);
	jspContext.setAttribute("gridName", gridName);
	jspContext.setAttribute("boName", boName);
	jspContext.setAttribute("entityName", entityName);
	jspContext.setAttribute("userUrl", userUrl);
	jspContext.setAttribute("gridUrl", gridUrl);
	jspContext.setAttribute("height", StringUtils.isNullBlank(height) ?  "300":height);
	
	jspContext.setAttribute("width", StringUtils.isNullBlank(width) ?  "''":width);
	jspContext.setAttribute("pageSize", StringUtils.isNullBlank(pageSize) ?  "10":pageSize);

	//jspContext.setAttribute("defaultCondition", StringUtils.isNullBlank(defaultCondition) ?  defaultCondition:defaultCondition);
%>

<script type="text/javascript" defer="defer">
/**
Ext.grid.EditorGridPanel.prototype.doLayout = function(){
	Ext.grid.EditorGridPanel.superclass.doLayout.call(this);
	alert("grid Tag ^^ div(TAG):${divId}," + Ext.get("${divId}").getWidth());
    //this.setWidth(Ext.get("${divId}").getWidth()); 
};
**/
	
//grid对象
var ${gridName}_grid;
var ${gridName}_store;
var ${gridName}_fields;
//向外暴露重新加载grid方法，支持查询组件参数方式
function reload_${gridName}_grid(urlParmeters)
{
	var paraUrl = encodeURI("${gridUrl}")+"&"+urlParmeters;
	${gridName}_store.proxy = new Ext.data.HttpProxy({url:paraUrl});
	${gridName}_store.load({params:{start:0, limit:${pageSize}},arg:[]});
	${gridName}_grid.getStore().commitChanges();
}
 Ext.namespace('${gridName}_ns');
 ${gridName}_ns.fuc=function(){
 	return {
 		grid:function(){
 				var checkcolumns = <%=gridTagHandler.getCheckColumn()%>; 
 				var checkplugins = [];
 				for(var i=0;i<checkcolumns.length;i++){
 					eval("var checkColumn"+i+" =new Ext.grid.CheckColumn(checkcolumns[i]);");
 					eval("checkplugins[i]=checkColumn"+i+";" );
 				}
    			
 				var toolbar = <%=gridTagHandler.getToolbar()%>;
 				var columns = <%=gridTagHandler.getColumns()%>; 				 				 	
 				var rec = <%=gridTagHandler.getRecord()%>;
 				var defaultValue = <%=gridTagHandler.getDefaultValue()%>;
 				
 				var cm = new Ext.grid.ColumnModelEx(columns);
				var fields=Ext.data.Record.create(rec);
				${gridName}_fields = fields;
 				var url = encodeURI(encodeURI("${gridUrl}"));
 				var store = new Ext.data.Store({
        			proxy : new Ext.data.HttpProxy({url:url}),
        			reader: new Ext.data.JsonReader({
            					root: 'data',
            					totalProperty: 'totalSize'
            				},fields)
		            //,remoteSort: true
 				});
 				${gridName}_store = store;
 				var pagingBar = new Ext.PagingToolbar({
	            	displayInfo:true,
	            	store:store,
	            	pageSize:${pageSize}
	        	});
                var gwidth =  Ext.get("${divId}").getWidth();
                //alert("gwidth1:" + gwidth + "，toolBar:" + Ext.get("div_toolbar").getWidth());
                if(!gwidth || gwidth<=0)
                {
                    //alert("gwidth" + gwidth);
                   gwidth = window.screen.width-215;
                }
                
			    var grid = new Ext.grid.EditorGridPanelEx({
			    	id:'${gridName}',
			        renderTo: '${divId}',
			        region:'center',
			        store: store,
			        cm: cm,
		            sm: new Ext.grid.CheckboxSelectionModel(),
			        height: ${height},
			        <%if(null!=width){%>
			        width: ${width},
			        <%}else{%>
			        //width: window.screen.width-215,
			        width: gwidth, 
			        <%}%>
			        plugins:checkplugins,
			        clicksToEdit:1,
			        anchor:'100%',
			        autoScroll:true,
			        autoWidth:true,
			        //monitorWindowResize:true,
			        //layout:'form',
			        //autoSizeColumns:true,
			        //stripeRows:true,
	    			//viewConfig: {
	    			//	forceFit: true
	    				//autoFill: true
				    //},
    			    title:'${title}',
			        loadMask:true,
			        tbar:toolbar,
			        bbar:pagingBar,
			        frame:true,
			        iconCls:'icon-grid'
			    });
			    <%if(null!=needAutoLoad&&"true".equals(needAutoLoad)){%>
    			store.load({params:{start:0, limit:${pageSize}}});  
    			<%}%>
				//增加空行
    			function addRow${gridName}()
    			{
    				var p = new Ext.data.Record(<%=gridTagHandler.getDefaultValue()%>);
    				grid.stopEditing();
    				grid.getStore().insert(0, p);
    			    grid.startEditing(0, 0);	
    			}
    			//删除行
    			function deleteRow${gridName}()
    			{
        			var records = grid.getSelectionModel().getSelections();        			
        			for(i=0;i<records.size();i++)
        		    {
        				grid.getStore().remove(records[i]);
        		    }
    			}		  			  			
    			${gridName}_grid = grid;  
 		}
 	}; 
 }();
 Ext.onReady(${gridName}_ns.fuc.grid,${gridName}_ns.fuc);

 //取得grid的变动数据
function get${gridName}GridData()
{
	//修改后的对象集合json串
	var modify_row="&subObject={\"objectName\":\"${entityName}\",\"operType\":\"modify\",\"values\":[{}";
	//删除的对象集合json串
	var delete_row="&subObject={\"objectName\":\"${entityName}\",\"operType\":\"delete\",\"values\":[{}";
	var records = ${gridName}_grid.getStore().getModifiedRecords();
    for(var i=0;i<records.size();i++)
    {
    	modify_row = modify_row +","+ Ext.util.JSON.encode(records[i].data).replace("&","-");
    }
    var delrecords = ${gridName}_grid.getStore().getRemovedRecords();
    for(var i=0;i<delrecords.size();i++)
    {
    	delete_row = delete_row +","+ Ext.util.JSON.encode(delrecords[i].data).replace("&","-");
    }
    modify_row = modify_row+"]}";
    delete_row = delete_row+"]}";
    var returnVal = modify_row+delete_row;
    return returnVal;
}

//取得所有grid数据
function getAll${gridName}GridData()
{
	var add_row="&subObject={\"objectName\":\"${entityName}\",\"operType\":\"modify\",\"values\":[{}";
	for (var i = 0; i <${gridName}_grid.getStore().getCount(); i++) {
		var record = ${gridName}_grid.getStore().getAt(i);  
		add_row = add_row +","+ Ext.util.JSON.encode(record.data).replace("&","-");    
	}
	return add_row+"]}";
}
</script>