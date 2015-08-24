<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>sql报表管理</title>
<style type="text/css">
.add{
	background-image:url(images/fam/add.gif) !important;
}
.delete{
	background-image:url(images/fam/delete.gif) !important;
}
.reset{
	background-image:url(images/fam/refresh.gif) !important;
}
.find{
	background-image:url(images/fam/find.png) !important;
}
</style>
</head>
<body>
<div id="div_northForm">
<form action="" id="findsqlFrom" name="findsqlFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
	<td align="left" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名称：
		<input type="text" name="name" id="name" value="">
		<input type="button" value="查找" onclick="findSqlInfo()"></input>
		<input type="reset" value="清空"></input>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_center"></div>

</body>
</html>
<script type="text/javascript">
document.onkeydown = function(){if (event.keyCode == 13){findSqlInfo();}}
var strOperType = '0';

var sqlRecord;

var sqlInfogrid;		//信息列表
var sqlInfods;

//sql 查找按钮的单击事件
function findSqlInfo(){
	var param = Form.serialize('findsqlFrom');
	var requestUrl = 'sqlReportController.spr?action=query&' + param;;
	sqlInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	sqlInfods.load({params:{start:0, limit:10},arg:[]});
}

//sql 删除数据的回调函数
function customCallBackHandle(transport){
	var responseCustomUtil = new AjaxResponseUtils(transport.responseText);
	var customField = responseCustomUtil.getCustomField("coustom");	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);	
	if (strOperType == '1'){
		sqlInfods.reload();
	}
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
    
    //增加
    var addsqlInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('新增信息',
					'sqlReportController.spr?action=addSqlInfoView',
					'',
					SqlcallbackFunction,
					{width:900,height:550});
		}
	});
   	
   	
   	//删除
   	var deletesqlInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (sqlInfogrid.selModel.hasSelection()){
				var records = sqlInfogrid.selModel.getSelections();
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个出仓单信息！');
				}else{
					if (records[0].data.EXAMINE_STATE != "1" )
						top.ExtModalWindowUtil.alert('提示','不能删除已送审的单据！');
					else
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var param = "?action=delete";
								param = param + "&sqlId=" + records[0].data.SHIP_ID;

								new AjaxEngine('sqlReportController.spr', 
						   			{method:"post", parameters: param, onComplete: callBackHandle});
						   			
						   		strOperType = '1';
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的出仓单信息！');
			}
		}
   	});
    
    var sqlInfotbar ;
   

     sqlInfotbar = new Ext.Toolbar({
		items:[addsqlInfo,'-',deletesqlInfo]
	});

	var sqlInfoPlant = Ext.data.Record.create([
		{name:'SQLELEMENTID'},                 
		{name:'NAME'},                 
		{name:'CMD'},                 
	    {name:'CREATERTIME'}                 
	]);

	sqlInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'sqlReportController.spr?action=query'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},sqlInfoPlant)
    });
    
    var sqlInfosm = new Ext.grid.CheckboxSelectionModel();
    
    var sqlInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sqlInfosm,
		   {header: 'ID',
           width: 100,
           sortable: true,
           dataIndex: 'SQLELEMENTID',
            hidden:true
           },                       
           {
               header: '操作',
               width: 100,
               sortable: true,
               dataIndex: 'EXPORTINFO',
               renderer: operaRD
           },        
           {
               header: '名称',
               width: 100,
               sortable: true,
               dataIndex: 'NAME'
               },
		 {header: '备注',
           width: 100,
           sortable: true,
           dataIndex: 'CMD'
           },
		  {header: '创建时间',
           width: 100,
           sortable: true,
           dataIndex: 'CREATERTIME'
           }
    ]);
    sqlInfocm.defaultSortable = true;
    
    var sqlInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:sqlInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    sqlInfogrid = new Ext.grid.EditorGridPanel({
    	id:'sqlInfoGrid',
        ds: sqlInfods,
        cm: sqlInfocm,
        sm: sqlInfosm,
        bbar: sqlInfobbar,
        tbar: sqlInfotbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    sqlInfods.load({params:{start:0, limit:10},arg:[]});
    
    sqlInfogrid.addListener('rowclick', sqlInfogridrowclick);
    
    function sqlInfogridrowclick(grid, rowIndex, e){
    	sqlRecord = grid.getStore().getAt(rowIndex);
    }    
 
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:55
		},
		{
			region:"center",
			layout:'fit',
		    split:true,
			collapsible: true,
			height:220,
			minSize: 210,
            maxSize: 400,
			title: "列表",
			items:[sqlInfogrid]
		}]
	});
	
});

//sqlManage 修改出仓单链接
function operaRD(value,metadata,record){
	if('${loginer}'== record.data.CREATOR){
	    return '<a href="#" onclick="operaForm();" >修改</a><a href="#" onclick="viewForm()">查看</a>';
	}else{
		return '<a href="#" onclick="viewForm()">查看</a>';
	}
}

function operaForm(sqlId){
	if (sqlId == null || sqlId == ''){
		var records = sqlInfogrid.selModel.getSelections();		
		sqlId = records[0].data.SQLELEMENTID;
	}

	top.ExtModalWindowUtil.show('SQL报表信息',
		'sqlReportController.spr?action=viewSqlInfo&sqlElementId='+ sqlId,
		'',
		SqlcallbackFunction,
		{width:900,height:550});
}


function viewForm(sqlId){
	if (sqlId == null || sqlId == ''){
		var records = sqlInfogrid.selModel.getSelections();		
		sqlId = records[0].data.SQLELEMENTID;
	}

	top.ExtModalWindowUtil.show('SQL报表信息',
		'sqlReportController.spr?action=viewSqlInfo&sqlElementId='+ sqlId,
		'',
		SqlcallbackFunction,
		{width:900,height:550});
}

function SqlcallbackFunction(){
	sqlInfods.reload();
}

</script>
