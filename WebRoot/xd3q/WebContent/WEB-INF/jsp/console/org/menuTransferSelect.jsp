<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="div_west_contidion" style="overflow: auto; width: 100%; height: 100%">
<form action="" name="findform">
<table>
<tr>
<td>操作对像：</td>
<td>
<select name="authObject" id="authObject" size="1" width="100" style="width: 100px">
	<option value="" selected="selected">请选择</option>
	<option value="1">授权者</option>
	<option value="2">被授权者</option>
</select>
</td>
</tr>

<tr>
<td>起始时间：</td>
<td>
<div id="div_startdate"></div>
</td>
</tr>

<tr>
<td>终始时间：</td>
<td>
<div id="div_enddate"></div>
</td>
</tr>

<tr>
<td>是否有效：</td>
<td>
<input type="checkbox" name="isEffect" id="isEffect" checked="true"></input>
</td>
</tr>

<tr>
<td colspan="2" align="left">
<input type="button" value="搜索" onclick="findAuth();"></input>
</td>
</tr>
</table>
</form>
</div>
<div id="div_center_center"></div>
</body>
</html>
<script type="text/javascript">
var ds;

function findAuth(){
	if (document.findform.authObject.value == ''){
		top.ExtModalWindowUtil.alert('提示','请选择一种操作对像！');
	}else{
		var url = 'orgController.spr?action=queryAuthByCondition&';
		url += Form.serialize('findform');

		ds.proxy= new Ext.data.HttpProxy({url: url});
		ds.load();
	}
}

Ext.apply(Ext.form.VTypes, {
  daterange: function(val, field) {
    var date = field.parseDate(val);

    var dispUpd = function(picker) {
      var ad = picker.activeDate;
      picker.activeDate = null;
      picker.update(ad);
    };
    
    if (field.startDateField) {
      var sd = Ext.getCmp(field.startDateField);
      sd.maxValue = date;
      if (sd.menu && sd.menu.picker) {
        sd.menu.picker.maxDate = date;
        dispUpd(sd.menu.picker);
      }
    } else if (field.endDateField) {
      var ed = Ext.getCmp(field.endDateField);
      ed.minValue = date;
      if (ed.menu && ed.menu.picker) {
        ed.menu.picker.minDate = date;
        dispUpd(ed.menu.picker);
      }
    }
    return true;
  }
});

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	var start = new Ext.form.DateField({
   		format:'Ymd',
		name:"startdate",
		id:"startdate",
		width: 100,
		vtype: 'daterange',
		endDateField: 'enddate',
		renderTo:'div_startdate'
   	});
   	
   	var end = new Ext.form.DateField({
   		format:'Ymd',
		name:"enddate",
		id:"enddate",
		width: 100,
		vtype: 'daterange',
		startDateField: 'startdate',
		renderTo:'div_enddate'
   	});
   	
   	ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'authId'},
					{name:'fromUserId'},
					{name:'fromUserName'},
					{name:'toUserId'},
					{name:'toUserName'},
					{name:'typeId'},
					{nmae:'typeName'},
					{name:'toId'},
					{name:'toName'},
					{name:'authTime'},
					{name:'unAuthTime'},
					{name:'lastTime'},
					{name:'isEffect'}
          		])
    });
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		  
		　{header: '授权编号',
           width: 100,
           sortable: false,
           dataIndex: 'authId',
           hidden:true
           },
		  {header: '授权人编号',
           width: 100,
           sortable: false,
           dataIndex: 'fromUserId',
           hidden:true
           },
           {header: '授权人工号',
           width: 100,
           sortable: true,
           dataIndex: 'fromUserName'
           },
           {header: '被授权人编号',
           width: 100,
           sortable: true,
           dataIndex: 'toUserId',
           hidden:true
           },
           {header: '被授权人工号',
           width: 120,
           sortable: true,
           dataIndex: 'toUserName'
           },
           {header: '授权类型',
           width: 100,
           sortable: true,
           dataIndex: 'typeId',
           hidden:true
           },
           {header: '授权类型名称',
           width: 100,
           sortable: true,
           dataIndex: 'typeName'
           },
           {header: '授权资源编号',
           width: 100,
           sortable: true,
           dataIndex: 'toId',
           hidden:true
           },
           {header: '授权资源名称',
           width: 100,
           sortable: true,
           dataIndex: 'toName',
           hidden:true
           },
           {header: '授权时间',
           width: 100,
           sortable: true,
           dataIndex: 'authTime',
           hidden:true
           },
           {
           header: '回收时间',
           width: 100,
           sortable: true,
           dataIndex: 'unAuthTime'
           },
           {
           header: '最迟回收时间',
           width: 100,
           sortable: true,
           dataIndex: 'lastTime'
           },
           {
           header: '是否有效',
           width: 100,
           sortable: true,
           dataIndex: 'isEffect'
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
    
    var grid = new Ext.grid.GridPanel({
    	id:'dictGrid',
        ds: ds,
        cm: cm,
        bbar: bbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center_center',
        autowidth:true,
        layout:'fit'
    });
   	
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"west",
			title:"查询条件",
			split:true,			 //可改变框体大小
			collapsible: true,   //可收缩
            width: 200,
            minSize: 175,
            maxSize: 400,
            margins:'0 0 0 5',
            contentEl: 'div_west_contidion'
		},{
			region:"center",
			layout:'border',
			items:[{
				region:'center',
				contentEl:'div_center_center',
				xtype:'tabpanel',
            	plain:true,
            	activeTab: 0,
            	defaults:{bodyStyle:'padding:0px'},
            	items:[{
	                title: '员工列表详情',
	                id:'userlist',
	                layout:'fit',
	                items:[grid]
            	}]
			}]
		}]
	});
});
</script>