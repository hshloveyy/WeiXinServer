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
.close{
	background-image:url(images/fam/forward.gif) !important;
}
</style>
</head>
<body>
<div id="div_otherInfo"></div><form action="" id="testform" name="testform">
</body>
</html>
<script type="text/javascript">
var otherds;

function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	
	otherds.reload();
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	var fm = Ext.form;
   
   	var otherPlant = Ext.data.Record.create([
   		{name:'TRANSITIONID'},
   		{name:'PROCESSDEFID'},
   		{name:'NODEID'},
   		{name:'TRANSITIONNAME'},
   		{name:'LOGICSQL'}
	]);

	otherds = new Ext.data.Store({
   		proxy : new Ext.data.HttpProxy({url:'workflowController.spr?action=queryTransition&nodeid=${nodeid}'}),
   		reader: new Ext.data.JsonReader({
       	root: 'root',
       	totalProperty: 'totalProperty'
       	},otherPlant)
    });
    
    var othersm = new Ext.grid.CheckboxSelectionModel();
    
    var othercm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		othersm,
			{header: 'ID',
           	width: 100,
           	sortable: true,
           	dataIndex: 'TRANSITIONID',
           	hidden:true
			},
           {header: 'PROCESSDEFID',
           width: 100,
           sortable: true,
           dataIndex: 'PROCESSDEFID',
           hidden:true
           },
           {header: 'NODEID',
           width: 100,
           sortable: true,
           dataIndex: 'NODEID',
           hidden:true
           },
           {header: 'TRANSITIONNAME',
           width: 150,
           sortable: true,
           dataIndex: 'TRANSITIONNAME'
           },
           {header: 'LOGICSQL',
           width: 450,
           sortable: true,
           dataIndex: 'LOGICSQL',
           editor: new fm.TextArea({
               allowBlank: false
           })
           }
    ]);
    othercm.defaultSortable = true;
    
    var addOther = new Ext.Toolbar.Button({
   		text:'重构',
   		iconCls:'add',
		handler:function(){
			top.Ext.Msg.show({
				title:'提示',
						msg: '是否确定重构记录',
						buttons: {yes:'是', no:'否'},
						fn: function(buttonId,text){
							if(buttonId=='yes'){
								var requestUrl = 'workflowController.spr?action=reloadTransition&nodeid=${nodeid}';
								Ext.Ajax.request({
									url: requestUrl,
									success: function(response, options){
										otherds.reload();
									},
									failure:function(response, options){
										//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
									}
								});
							}
						},
						icon: Ext.MessageBox.QUESTION
			});
			
		}
   	});
   	
   	var deleteOther = new Ext.Toolbar.Button({
   		text:'删除',
   		iconCls:'delete',
		handler:function(){
			if (othergrid.selModel.hasSelection()){
				var records = othergrid.selModel.getSelections();
				var idList = '';
				for (var i=0;i<records.length;i++){
					idList = idList + records[i].data.TRANSITIONID + '|';
				}
				
				top.Ext.Msg.show({
					title:'提示',
  						msg: '是否确定删除记录',
  						buttons: {yes:'是', no:'否'},
  						fn: function(buttonId,text){
  							if(buttonId=='yes'){
  								var param = "?action=deleteTransition";
							param = param + "&idList=" + idList;

							new AjaxEngine('workflowController.spr', 
					   			{method:"post", parameters: param, onComplete: callBackHandle});
  							}
  						},
  						icon: Ext.MessageBox.QUESTION
				});
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的信息！');
			}
		}
   	});
   	
   	var closeOther = new Ext.Toolbar.Button({
   		text:'关闭',
   		iconCls:'close',
		handler:function(){
			top.ExtModalWindowUtil.close();
		}
   	});
   	
   	var othertbar = new Ext.Toolbar({
		items:[addOther,'-',deleteOther,'-',closeOther]
	});
    
    var otherbbar = new Ext.PagingToolbar({
        pageSize: 50,
        store:otherds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    var othergrid = new Ext.grid.EditorGridPanel({
    	id:'otherGrid',
        ds: otherds,
        cm: othercm,
        sm: othersm,
        tbar: othertbar,
        bbar: otherbbar,
        border:false,
        loadMask:true,
        clicksToEdit:1,
        autoScroll:true,
        el: 'div_otherInfo',
        layout:"fit"
    });
    
    otherds.load({params:{start:0, limit:10}});
    
     othergrid.on("afteredit", afterEdit, othergrid);
     
     function afterEdit(obj){
    	var row = obj.record;//获取被修改的行
        var colname = obj.field;//获取被修改的列
        var transitionId = row.get("TRANSITIONID");
        var colvalue = row.get(colname);
       
        var requestUrl = 'workflowController.spr?action=updateTransition';
			requestUrl = requestUrl + '&transitionId=' + transitionId;
			requestUrl = requestUrl + '&colname=' + colname;
			requestUrl = requestUrl + '&colvalue=' + colvalue;
			
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				otherds.commitChanges();
			},
			failure:function(response, options){
			}
		});
    }
    
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			layout:'fit',
			items:[othergrid]
		}]
	});
});


</script>