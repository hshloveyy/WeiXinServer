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
<div id="div_north" style="width:100%;height:100%">
<form action="" name="findform">
<table width="100%">
<tr>
<td align="right">操&nbsp&nbsp&nbsp作&nbsp&nbsp&nbsp对&nbsp&nbsp像：</td>
<td>
<select name="authObject" id="authObject" size="1" width="100" style="width: 100px">
	<option value="" selected="selected">请选择</option>
	<option value="1">授权者</option>
	<option value="2">被授权者</option>
</select>
</td>
<td align="right">授权起始时间：</td>
<td>
<div id="div_startdate"></div>
</td>
<td align="right">授权终始时间：</td>
<td>
<div id="div_enddate"></div>
</td>
</tr>

<tr>
<td align="right">回收起始时间：</td>
<td>
<div id="div_callback_startdate"></div>
</td>
<td align="right">回收终始时间：</td>
<td>
<div id="div_callback_enddate"></div>
</td>
<td align="right">是&nbsp&nbsp&nbsp否&nbsp&nbsp&nbsp有&nbsp&nbsp效：</td>
<td>
<input type="checkbox" name="isEffect" id="isEffect" checked="true"></input>
</td>
</tr>

<tr>
<td colspan="6" align="right">
<input type="button" value="搜索" onclick="findAuth();"></input>
</td>
</tr>
</table>
</form>
</div>

<div id="div_center">
</div>
</body>
</html>
<script type="text/javascript">
var ds;
var grid;
function customCallBackHandle(transport){
	ds.reload();
}

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
   	
   	var callbackstart = new Ext.form.DateField({
   		format:'Ymd',
		name:"callbackstartdate",
		id:"callbackstartdate",
		width: 100,
		vtype: 'daterange',
		endDateField: 'callbackenddate',
		renderTo:'div_callback_startdate'
   	});
   	
   	var callbackend = new Ext.form.DateField({
   		format:'Ymd',
		name:"callbackenddate",
		id:"callbackenddate",
		width: 100,
		vtype: 'daterange',
		startDateField: 'callbackstartdate',
		renderTo:'div_callback_enddate'
   	});
   	
   	
   	var addUser = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('增加用户权转移信息',
			'orgController.spr?action=addUserAuthView',
			'',
			'',
			{width:800,height:550});
		}
   	});
   	
   	var deleteUser = new Ext.Toolbar.Button({
   		text:'回收',
	    iconCls:'delete',
		handler:function(){
			if (grid.selModel.hasSelection()){
				var records = grid.selModel.getSelections();
		        var idList = '';            					
  					for(var i=0;i<records.length;i++){
  						idList = idList + records[i].json.authId + '|';
  					}
  					
  					var param = '?action=deleteUserAuth';
	  				param = param + '&authidlist=' + idList;
	  				
	  				new AjaxEngine('orgController.spr', 
		   				{method:"post", parameters: param, onComplete: callBackHandle});
			}else{
				top.ExtModalWindowUtil.alert('提示','请选择要回收的转移信息！');
			}
		}
   	});
   	
   	var tbar = new Ext.Toolbar({
		items:[addUser,'-',deleteUser]
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
					{name:'typeName'},
					{name:'toId'},
					{name:'toName'},
					{name:'authTime'},
					{name:'unAuthTime'},
					{name:'lastTime'},
					{name:'isEffect'},
					{name:'createTime'},
					{name:'operate'}
          		])
    });
    var sm = new Ext.grid.CheckboxSelectionModel();
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		  sm,
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
           {
               header: '查看审批记录',
               width: 80,
               dataIndex: 'operate',
               renderer:funOperate
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
           dataIndex: 'toName'
           },
           {header: '授权时间',
           width: 100,
           sortable: true,
           dataIndex: 'authTime'
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
    
    grid = new Ext.grid.GridPanel({
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
        autowidth:true,
        layout:'fit'
    });
    
    var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title:"条件查询",
			collapsible: true,
			height:95,
			items:[{
				xtype:'panel',
				border:false,
				contentEl:'div_north'
			}]
		},{
			region:"center",
			layout:'border',
			title:"数据显示",
			contentEl: 'div_center',
			items:[grid]
		}]
	});
});
function funOperate(value, p, record){
    return '<a href="#" onclick="viewApproved()">查看</a>';
}
function viewApproved(){
	var records = grid.selModel.getSelections();
	_getMainFrame().maintabs.addPanel('授权审批查看','', 'orgController.spr?action=viewApproved&toUserId='+records[0].data.toUserId+'&startTime='+records[0].data.createTime+'&endTime='+records[0].data.unAuthTime+'&toId='+records[0].data.toId);
		//top.ExtModalWindowUtil.show('授权审批查看',
			//'orgController.spr?action=viewApproved&toUserId='+records[0].data.toUserId+'&startTime='+records[0].data.createTime+'&endTime='+records[0].data.unAuthTime+'&toId='+records[0].data.toId,
			//'','',
		//	{width:900,height:550});
}
</script>