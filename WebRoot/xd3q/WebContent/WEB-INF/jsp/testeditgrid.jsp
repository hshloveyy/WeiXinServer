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
</style>
</head>
<body>
<div id="div_center">
</div>
<div id="dicttypeDiv"></div>

<select name="testType" id="testType" style="display: none;">
	<option value="1">类型一</option>
	<option value="2">类型二</option>
	<option value="3">类型三</option>
	<option value="4">类型四</option>
	<option value="5">类型五</option>
</select>
</body>
</html>
<script type="text/javascript">
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	var fm = Ext.form;
   	
   	var Plant = Ext.data.Record.create([
   		{name:'TEST_ID',type: 'string'},
   		{name:'TEST_NAME',type: 'string'},
   		{name:'PRICE',type: 'int'},
   		{name:'TEST_TYPE',type: 'string'},
   		{name:'CREATE_DATE',mapping:'CREATE_DATE',type:'date'}
	]);
   	
	var ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'workflowController.spr?action=queryTestTable'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},Plant)
    });
    var sm = new Ext.grid.CheckboxSelectionModel();
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		  {header: '编号',
           width: 100,
           sortable: false,
           dataIndex: 'TEST_ID',
           hidden:true
           },
           {header: '名称',
           width: 100,
           sortable: true,
           dataIndex: 'TEST_NAME',
           editor: new fm.TextField({
               allowBlank: false
           })
           },
           {
           header: '价格',
           width: 100,
           sortable: true,
           dataIndex: 'PRICE',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 100000
           })
           },
           {header: '类型',
           width: 100,
           sortable: true,
           dataIndex: 'TEST_TYPE',
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               transform:'testType',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })
           },
           {header: '日期',
           width: 100,
           sortable: true,
           renderer: Ext.util.Format.dateRenderer('Y-m-d'),
           dataIndex: 'CREATE_DATE',
           editor: new fm.DateField({
                format: 'Y-m-d',
                disabledDaysText: '请输入日期'
            })
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
    
    var addUser = new Ext.Toolbar.Button({
   		text:'增加',
		handler:function(){
			var p = new Plant({
                    TEST_ID: '',
                    TEST_NAME: '',
                    PRICE: 0,
                    TEST_TYPE: '',
                    CREATE_DATE: (new Date()).clearTime()
                });
                grid.stopEditing();
                ds.insert(0, p);
                grid.startEditing(0, 0);
		}
   	});
   	
   	var commit = new Ext.Toolbar.Button({
   		text:'提交',
		handler:function(){
			var records = grid.getStore().getModifiedRecords();
			
			alert(records.length);
			
			grid.getStore().commitChanges();
		}
   	});
   	
   	var tbar = new Ext.Toolbar({
		items:[addUser,'-',commit]
	});
    
    var grid = new Ext.grid.EditorGridPanel({
    	id:'dictGrid',
        ds: ds,
        cm: cm,
        sm:sm,
        tbar:tbar,
        bbar: bbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        layout:"fit"
    });
    
    ds.load({params:{start:0, limit:50},arg:[]});
    
    grid.on("afteredit", afterEdit, grid); 
    
    function afterEdit(obj){
        var r = obj.record;//获取被修改的行
        var l = obj.field;//获取被修改的列
        var id = r.get("id");
        if (l == "CREATE_DATE") 
           var lie = Ext.util.Format.date(r.get(l), "Y-m-d");
        else 
            var lie = r.get(l);
		alert(l);
		alert(lie);
//        Ext.Ajax.request({
//            url: '_action.php?action=edit',
//            params: "id=" + id + "&name=" + l + '&value=' + lie
//        });
    }

    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			layout:'border',
			title:"数据显示",
			contentEl: 'div_center',
			items:[grid]
		}]
	});
});
</script>
