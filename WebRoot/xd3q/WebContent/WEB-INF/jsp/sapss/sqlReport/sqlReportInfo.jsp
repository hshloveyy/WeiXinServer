<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ taglib prefix="ffcs" tagdir="/WEB-INF/tags/infolion"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SQL报表</title>
<style type="text/css">
.add{
	background-image:url(<%=request.getContextPath()%>/images/fam/add.gif) !important;
}
.delete{
	background-image:url(<%=request.getContextPath()%>/images/fam/delete.gif) !important;
}
.update{
	background-image:url(<%=request.getContextPath()%>/images/fam/refresh.gif) !important;
}
.find{
	background-image:url(<%=request.getContextPath()%>/images/fam/find.png) !important;
}
</style>
<script type="text/javascript">

var sqlFieldgrid;		//物料明细列表
var sqlFieldds;

function analySqlCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	var sReturnMessage=customField.returnMessage;
	top.ExtModalWindowUtil.alert('提示',sReturnMessage);  
	sqlFieldds.reload();
}


function customCallBackHandle(transport)
{
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	var sReturnMessage=customField.returnMessage;
	top.ExtModalWindowUtil.alert('提示',sReturnMessage);  
	sqlFieldds.reload();
	if ('${close}'=='false' && sReturnMessage.indexOf("提交成功")>-1)
	{	
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	}
}
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Date.prototype.toString=function(){ return this.format("Y-m-d");};
   	Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/images/fam/s.gif';
   	var fm = Ext.form;  



    //增加进仓单明细资料(物料)
    var addsqlField = new Ext.Toolbar.Button({
   		text:'解析',
	    iconCls:'add',
		handler:function(){			
			var param1 = Form.serialize('mainForm');
    		var param = param1 + "&action=analySqlElement";
			new AjaxEngine('sqlReportController.spr', 
				   {method:"post", parameters: param, onComplete: callBackHandle});
			/**
			var param1 = Form.serialize('mainForm');
    		var param = param1 + "&action=analySqlElement";
			new AjaxEngine('sqlReportController.spr', 
				   {method:"post", parameters: param, onComplete: customCallBackHandle});**/
		}
   	});
   	

    var sqlFieldtbar = new Ext.Toolbar({
		items:[addsqlField,'-']
	});

	var sqlFieldPlant = Ext.data.Record.create([
		{name:'SQLFIELDDFID'},  
		{name:'SQLELEMENTID'},
		{name:'FILEDNAME'},
		{name:'FILEDSHOWNAME'},
		{name:'FILEDTYPE'},
		{name:'DICNAME'},
	    {name:'ISSHOW'},
	    {name:'ISQUERY'},
	    {name:'WIDTH'},
	    {name:'ORDERNO'}
	]);

	sqlFieldds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'sqlReportController.spr?action=queryField&elementId=' + '${info.sqlElementId}'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},sqlFieldPlant)
    });
    
    var sqlFieldsm = new Ext.grid.CheckboxSelectionModel();
    
    var fieldTypeDS = new Ext.data.SimpleStore({  //填充的数据
			fields : ['value', 'text'],
			data : [
				       ['0', '字符'],
				       ['1', '数值'],
				       ['2', '日期'],
				       ['3', '部门'],
				       ['4', '数据字典'],
				       ['5', '搜索帮助']
				       ]
		 });
    
    var sqlFieldcm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sqlFieldsm,
		   {header: 'ID',
           width: 80,
           sortable: true,
           dataIndex: 'SQLFIELDDFID',
           hidden: true
           },
           {header: 'MAINID',
           width: 80,
           sortable: true,
           dataIndex: 'SQLELEMENTID',
           hidden: true
           },  
		   {header: '字段名称',
           width: 120,
           sortable: true,
           dataIndex: 'FILEDNAME'
           },   
          {header: '字段显示名',
			width: 80,
			sortable: true,
			dataIndex: 'FILEDSHOWNAME',
			editor: new fm.TextField({
	               allowBlank: true,
	               maxLength:20
	        })
           },
           {header: '字段类型',
			width: 80,
			sortable: false,
			dataIndex: 'FILEDTYPE',
			editor: new Ext.form.ComboBox({
               typeAhead: true,
               id:'fieldTypeComb',
               triggerAction: 'all',
               mode : 'local',
               width:157,
               allowBlank:false,
               blankText:'请选择',
               store : fieldTypeDS,
               valueField : 'value',
               displayField : 'text',  
               forceSelection:true
            }),
            renderer: function(value, cellmeta, record) {
                //通过匹配value取得ds索引
                var index = fieldTypeDS.find(Ext.getCmp('fieldTypeComb').valueField,value); 
                //通过索引取得记录ds中的记录集
                var record = fieldTypeDS.getAt(index); 
                //返回记录集中的value字段的值
                if(record) return record.data.text;
                return '';
           }  
          },
          {
           header: '字典表/搜索帮助名称',
           width: 300,
           sortable: true,
           dataIndex: 'DICNAME',
           editor: new fm.TextField({
               allowBlank: true,
               maxLength:100
           })
           },
           {header: '是否显示',
           width: 80,
           sortable: true,
           dataIndex: 'ISSHOW',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               allowDecimals: false,
               maxValue: 1
           })            
           },
           {
           header: '是否查询',
           width: 80,
           sortable: true,
           dataIndex: 'ISQUERY',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               allowDecimals: false,
               maxValue: 1
           })              
           },
           {
               header: '宽度',
               width: 50,
               sortable: true,
               dataIndex: 'WIDTH',
               editor: new fm.NumberField({
                   allowBlank: false,
                   allowNegative: false,
                   allowDecimals: false,
                   maxValue: 300
               })           
           },
           {
           header: '排序',
           width: 50,
           sortable: true,
           dataIndex: 'ORDERNO',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               allowDecimals: false,
               maxValue: 100
           })           
           }
    ]);
    
    sqlFieldcm.defaultSortable = true;
        
    var sqlFieldbbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:sqlFieldds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
	 
   sqlFieldds.load({params:{start:0, limit:10},arg:[]});
   

	sqlFieldgrid = new Ext.grid.EditorGridPanel({
    	id:'sqlFieldGrid',
        ds: sqlFieldds,
        cm: sqlFieldcm,
        sm: sqlFieldsm,
        bbar: sqlFieldbbar,
        tbar: sqlFieldtbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_userinfo',
        autowidth:true,
        clicksToEdit:1,
        layout:'fit'
    });
 
    sqlFieldgrid.render();
    
    
    sqlFieldgrid.on("afteredit", afterUserHisEdit, sqlFieldgrid);
    
    function afterUserHisEdit(obj){
    	var row = obj.record;//获取被修改的行
        var colName = obj.field;//获取被修改的列
        var sqlFieldId = row.get("SQLFIELDDFID");
        var colValue = row.get(colName);
        var requestUrl = 'sqlReportController.spr?action=updateSqlField';
		requestUrl = requestUrl + '&sqlFieldId=' + sqlFieldId;
		requestUrl = requestUrl + '&colName=' + colName;
		requestUrl = requestUrl + '&colValue=' + colValue;

		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				sqlFieldds.commitChanges();
			},
			failure:function(response, options){
			}  
		});
    } 

    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			height:190,
			xtype:'tabpanel',
			id:'infotype',
			name:'infotype',
			plain:true,
            autoScroll:'true',
            activeTab: 0,			
           items:[{
            	title:'主信息',
            	contentEl: 'div_main',
            	id:'maininfo',
				name:'maininfo',
				autoScroll:'true',
            	layout:'fit'
            }]
		},{
			region:"center",
			layout:'fit',
			buttonAlign:'center',
			autoScroll:'true',
			items:[{
				region:'center',
				xtype:'tabpanel',
				id:'infotype',
				name:'infotype',
				plain:true,
	            height:300,
	            autoScroll:'true',
	            activeTab: 0,
	            items:[{
			            	title:'列信息',
			            	contentEl: '',
			            	id:'sqlField',
							name:'sqlField',
							autoScroll:'true',
			            	layout:'fit',
			            	items:[sqlFieldgrid]
	                  }]
	             }],
	             buttons:[{
		            text:'保存',
	            	handler:function(){
	            		var param1 = Form.serialize('mainForm');
	            		var param = param1 + "&action=updateShip";
						new AjaxEngine('sqlReportController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});

					}
 
	            },
	            {
		        	text:'关闭',
		        	handler:function(){
		        	        top.ExtModalWindowUtil.markUpdated();
		                    top.ExtModalWindowUtil.close();
		                   }
		        }]
		}]
		
	});//end fo viewPort

});//end of Ext.onReady   

</script>
</head>
<body>
<div id='div_progressBar'></div>
<div id="div_main" class="x-hide-display">
<form id="mainForm" action="" name="mainForm">
	 <table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
     <tr>
        <td width="11%" align="right">名称<font color="red">▲</font>：</td>
        <td width="22%" align="left">
            <input type="text" id="name" name="name" size="30" tabindex="1" value="${info.name}"/> 
            <input type="hidden" id="creator" name="creator" value="${info.creator}"></input>
            <input type="hidden" id="deptId" name="deptId" value="${info.deptId}"></input>
            <input type="hidden" id="createrTime" name="createrTime" value="${info.createrTime}"></input>
            <input type="hidden" id="isAvailable" name="isAvailable" value="${info.isAvailable}"></input>
        </td>
        <td width="11%" align="right">是否部门权限控制：</td>
        <td width="22%" align="left">
			<input id="isDeptAuthority" name="isDeptAuthority" type="text" size="5" tabindex="1" value="${info.isDeptAuthority}"/> 
        </td>
      </tr>
      <tr>
        <td width="11%" align="right">备注：</td>
        <td width="22%" align="left">
            <input type="text" id="cmd" name="cmd" size="30" tabindex="1" value="${info.cmd}"/> 
        </td>
        <td width="11%" align="right">ID：</td>
        <td width="22%" align="left">
			<input id="sqlElementId" name="sqlElementId" type="text" size="36" tabindex="1" value="${info.sqlElementId}" readonly="readonly"/> 
        </td>
      </tr>
      <tr>
        <td align="right">sql：</td>
        <td colspan="5">
        	 <textarea cols="80" rows="8" id="sql" name="sql" style="width:95%;overflow-y:visible;word-break:break-all;">${info.sql}</textarea>
        </td>
    </tr>
	</table>
</form>
</div>
<div id="div_userinfo"></div>
</body>
</html>

