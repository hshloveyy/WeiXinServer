<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/lib/TreeCheckNodeUI.js"></script>
<style type="text/css">
.delete{
	background-image:url(images/fam/delete.gif) !important;
}
</style>
</head>
<body>
<div div_center_north>
</div>
<div id="div_center_center">
</div>

<div id="div_role_tree"></div>

<div id="div_resource_tree"></div>

<div id="div_already_grid"></div>
</body>
</html>
<script type="text/javascript">
var ds;
var alreadyds;
var rolecheckresult = '';
var resourcecheckresult = '';

function customCallBackHandle(transport){
	alreadyds.reload();
	
	var maintab = Ext.getCmp("selecttab");
	maintab.setActiveTab("already");
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	var roleTreeLoader = new Ext.tree.TreeLoader({
   		baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI },
    	url:'orgController.spr?action=queryRoleForTransferManage'
    });
    
    roleTreeLoader.on("beforeload", function(treeLoader, node) {   
                roleTreeLoader.baseParams.id = node.attributes.id;   
    }, roleTreeLoader);
    
    var roletree = new Ext.tree.TreePanel({
        el:'div_role_tree',
        autoScroll:true,
        animate:true,
        enableDD:true,
        checkModel: 'cascade',
		onlyLeafCheckable: false,
        rootVisible:true,
        containerScroll: true,
        loader: roleTreeLoader
    });
    
    var roleroot = new Ext.tree.AsyncTreeNode({
        text: '我的角色信息',
        draggable:false,
        id:'-1'
    });
    roletree.setRootNode(roleroot);
    roletree.render();
    roleroot.expand();
    
    
    function roletradeList(prant,children)
	{            
		if(prant.childNodes && prant.childNodes.length>0)
		{
			var list;
			for (var i=0;i<prant.childNodes.length;i++)
			{
	           	list = prant.childNodes[i];
	           	if (list.getUI().checkbox.checked == true)
	           	{
	           		rolecheckresult += list.id +"|";
	           	}
	           	
	           	if (list!=null && list.text.length>0 )
	           	{
	              	roletradeList(list,list.text);                        
	           	}
			}
		}
	}
    
    
    var resourceTreeLoader = new Ext.tree.TreeLoader({
    	baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI },
    	url:'orgController.spr?action=queryResourceForTransferManage'
    });
    
     resourceTreeLoader.on("beforeload", function(treeLoader, node) {   
                resourceTreeLoader.baseParams.id = node.attributes.id;   
    }, resourceTreeLoader);
    
    var resourcetree = new Ext.tree.TreePanel({
        el:'div_resource_tree',
        autoScroll:true,
        animate:true,
        enableDD:true,
        checkModel: 'cascade',
		onlyLeafCheckable: false,
        rootVisible:true,
        containerScroll: true,
        loader: resourceTreeLoader
    });
    
    var resourceroot = new Ext.tree.AsyncTreeNode({
        text: '我的资源信息',
        draggable:false,
        id:'-1'
    });
    resourcetree.setRootNode(resourceroot);
    resourcetree.render();
    resourceroot.expand();
    
    function resourcetradeList(prant,children)
	{            
		if(prant.childNodes && prant.childNodes.length>0)
		{
			var list;
			for (var i=0;i<prant.childNodes.length;i++)
			{
	           	list = prant.childNodes[i];
	           	if (list.getUI().checkbox.checked == true)
	           	{
	           		resourcecheckresult += list.id +"|";
	           	}
	           	
	           	if (list!=null && list.text.length>0 )
	           	{
	              	roletradeList(list,list.text);                        
	           	}
			}
		}
	}
    
   	ds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'userId'},
					{name:'deptId'},
					{name:'deptName'},
					{name:'userName'},
					{name:'realName'},
					{name:'sex'},
					{name:'address'},
					{name:'idCard'},
					{name:'password'},
					{name:'EMail'},
					{name:'creator'},
					{name:'createTime'},
					{name:'deleted'},
					{name:'deptUserId'}
          		])
    });
    
    var sm = new Ext.grid.CheckboxSelectionModel();
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		  {
           header: '用户名',
           width: 100,
           sortable: true,
           dataIndex: 'userName'
           },
		　{header: '员工编号',
           width: 100,
           sortable: false,
           dataIndex: 'userId',
           hidden:true
           },
		  {header: '部门编号',
           width: 100,
           sortable: false,
           dataIndex: 'deptId',
           hidden:true
           },
           {header: '部门名称',
           width: 100,
           sortable: true,
           dataIndex: 'deptName'
           },
           {header: '姓名',
           width: 100,
           sortable: true,
           dataIndex: 'realName'
           },
           {header: '身份证号',
           width: 120,
           sortable: true,
           dataIndex: 'idCard'
           },
           {header: '性别',
           width: 100,
           sortable: true,
           dataIndex: 'sex'
           },
           {header: '地址',
           width: 100,
           sortable: true,
           dataIndex: 'address'
           },
           {header: '邮箱',
           width: 100,
           sortable: true,
           dataIndex: 'EMail'
           },
           {header: '记录创建者',
           width: 100,
           sortable: true,
           dataIndex: 'creator',
           hidden:true
           },
           {header: '创建时间',
           width: 100,
           sortable: true,
           dataIndex: 'createTime',
           hidden:true
           },
           {header: '员工部门编号',
           width: 100,
           sortable: true,
           dataIndex: 'deptUserId',
           hidden:true
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
        sm:sm,
        bbar: bbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center_center',
        autowidth:true,
        layout:'fit'
    });
    
    
    var comboWithTooltip = new Ext.form.ComboBox({
    	fieldLabel:"部门",
		store:new Ext.data.SimpleStore({fields:[],data:[[]]}), 
		editable:false, //禁止手写及联想功能
		mode: 'local', 
		triggerAction:'all', 
		maxHeight: 200, 
		tpl: '<div id="tree" style="height:200px"></div>', //html代码
		selectedClass:'', 
		onSelect:Ext.emptyFn,
		emptyText:'请选择...'
	});
	
	comboWithTooltip.on('expand',function(){ 
            tree.render('tree'); 
    });
    
    var systemTreeLoader = new Ext.tree.TreeLoader({
    	url:'orgController.spr?action=queryDeptByParentId'
    });
      
    systemTreeLoader.on("beforeload", function(treeLoader, node) {   
                systemTreeLoader.baseParams.id = node.attributes.id;   
            }, systemTreeLoader);
    
    var tree = new Ext.tree.TreePanel({
    	border:false,
        autoScroll:true,
        animate:true,
        autoWidth:true,
        autoHeight:true,
        enableDD:true,
        rootVisible:true,
        containerScroll: true,
        loader: systemTreeLoader
    }); 
    
    var root = new Ext.tree.AsyncTreeNode({
        text: '部门信息',
        draggable:false,
        id:'-1'
    });
    
    tree.setRootNode(root);
    comboWithTooltip.on('expand',function(){ 
		tree.render('tree'); 
    });

	tree.on("click",function(node,e)
	{       
		comboWithTooltip.setValue(node.text);
	    comboWithTooltip.collapse();
	    
	    if (Ext.getCmp("selecttab").getActiveTab().id != 'userlist'){
	    	var maintab = Ext.getCmp("selecttab");
			maintab.setActiveTab("userlist");
	    }
	    
	    ds.proxy= new Ext.data.HttpProxy({url: 'orgController.spr?action=queryUserByDeptIdToGrid&deptid='+node.id});
		ds.load();
	});
	
	
	var deleteUser = new Ext.Toolbar.Button({
   		text:'回收',
	    iconCls:'delete',
		handler:function(){
			if (alreadygrid.selModel.hasSelection()){
				var records = alreadygrid.selModel.getSelections();
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
   	
   	var alreadytbar = new Ext.Toolbar({
		items:[deleteUser]
	});
	
	alreadyds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'orgController.spr?action=queryAuthByCondition&authObject=1&isEffect=on'}),
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
					{name:'isEffect'}
          		])
    });
    
    var alreadysm = new Ext.grid.CheckboxSelectionModel();
    var alreadycm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		  alreadysm,
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
    alreadycm.defaultSortable = true;
    
    var alreadybbar = new Ext.PagingToolbar({
        pageSize: 50,
        store:alreadyds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    var alreadygrid = new Ext.grid.GridPanel({
        ds: alreadyds,
        cm: alreadycm,
        sm:alreadysm,
        tbar:alreadytbar,
        bbar: alreadybbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_already_grid',
        autowidth:true,
        layout:'fit'
    });
    
    alreadyds.load();
   	
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:'west',
			width:200,
			xtype:'tabpanel',
			id:'transtype',
			name:'transtype',
			plain:true,
            height:300,
            activeTab: 0,
			items:[{
				title:'角色',
				id:'role',
				name:'role',
				layout:'fit',
				items:[roletree]
			},{
				title:'资源',
				id:'resource',
				name:'resource',
				layout:'fit',
				items:[resourcetree]
			}]
		},{
			region:"center",
			layout:'border',
			items:[{
				region:'center',
				xtype:'tabpanel',
            	plain:true,
            	height:200,
            	activeTab: 0,
            	defaults:{bodyStyle:'padding:0px'},
            	items:[{
	                title: '输入项信息',
	                xtype:'panel',
	                labelWidth:50,
	                buttonAlign:'center',
	                border:false,
	                labelAlign:"left",
	                bodyStyle:'padding:5px 10px 0',
					hideLabels:false,
					layout:"form",
	                items:[{
	                	layout: "column",
	                	border:false,
	                	items:[{
	                		columnWidth: 0.5,
	                		border:false,
　　　　　　　　　　			layout: "form",
	                		items:[comboWithTooltip]
	                	},{
	                		columnWidth: 0.5,
	                		border:false,
　　　　　　　　　　			layout: "form",
	                		items:{
	                			xtype:'datefield',
								fieldLabel:"最后回收日期",
			                	format:'Ymd',
								name:"lastCalldate",
								id:"lastCalldate",
								width: 150
	                		}
	                	}]
	                }],
	                buttons:[{
	                	text:'提交',
	                	handler:function(){
	                		//判断是否选择了要转移的员工
	                		if (grid.selModel.hasSelection()){
	                			var toId = '';
	                			var transtype = '';
	                			rolecheckresult = '';
	                			resourcecheckresult = '';
	                			if (Ext.getCmp("transtype").getActiveTab().id == 'role')
		                		{
		                			var rolenode = roletree.getRootNode();
									roletradeList(rolenode,rolenode.text);
									toId = rolecheckresult;
									transtype = '1';
								}
								
								if (Ext.getCmp("transtype").getActiveTab().id == 'resource')
								{
									var resourcenode = resourcetree.getRootNode();
									resourcetradeList(resourcenode,resourcenode.text);
									toId = resourcecheckresult;
									transtype = '2';
								}

								//判断是否选 择了要转移的角色或资源
								if (toId == ''){
									if (transtype == '1')
										top.ExtModalWindowUtil.alert('提示','请选择要转移权限的角色！');
									else
										top.ExtModalWindowUtil.alert('提示','请选择要转移权限的资源！');
								}else{
									//判断日期是否选择
									if (Ext.get("lastCalldate").dom.value == ''){
										top.ExtModalWindowUtil.alert('提示','请选择要转移权限的最后回收时间！');
									}else{
										var records = grid.selModel.getSelections();
								        var idList = '';            					
						  				for(var i=0;i<records.length;i++){
						  						idList = idList + records[i].json.deptUserId + '|';
						  				}
						  				
						  				var param = '?action=addUserAuth';
						  				param = param + '&toUserId=' + idList;
						  				param = param + '&typeId=' + transtype;
						  				param = param + '&toId=' + toId;
						  				param = param + '&lastTime=' + Ext.get("lastCalldate").dom.value;
						  				
						  				new AjaxEngine('orgController.spr', 
							   				{method:"post", parameters: param, onComplete: callBackHandle});
									}
								}
							}
							else{
								top.ExtModalWindowUtil.alert('提示','请选择要转移权限的员工！');
							}
	                	}
	                },{
	                	text:'关闭',
	                	handler:function(){
	                		top.ExtModalWindowUtil.close();
	                	}
	                }]
            	}]
			},{
				region:'south',
				contentEl:'div_center_center',
				xtype:'tabpanel',
				id:'selecttab',
				name:'selecttab',
            	plain:true,
            	height:410,
            	activeTab: 0,
            	defaults:{bodyStyle:'padding:0px'},
            	items:[{
            		title: '已转移列表',
            		layout:'fit',
            		id:'already',
            		name:'already',
            		items:[alreadygrid]
            	},{
	                title: '员工列表详情',
	                id:'userlist',
	                name:'userlist',
	                layout:'fit',
	                items:[grid]
            	}]
			}]
		}]
	});
});
</script>