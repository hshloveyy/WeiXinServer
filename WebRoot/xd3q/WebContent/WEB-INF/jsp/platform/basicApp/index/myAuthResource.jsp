<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>我的授权</title>
</head>

<body>
<div id="div_toolbar" ></div>
<div id="div_top" class="search">
<form id="mainForm" name="mainForm">
	  <table width="100%" border="0" cellpadding="4" cellspacing="1">
	  <tr>
	  		<td width="15%" align="right">用户:</td>
		  	<td width="85%" align="left" colspan="3">
		  	<input type="hidden" id="userId" name="userId" value="${userId}" readOnly="true">
		  	<input class="inputText" type="text" id="userName" name="userName" value="${userName}" readOnly="true">
		  	</td>
	  </tr>
	  <tr>
	  		<td width="15%" align="right">最后修改人:</td>
		  	<td width="30%" align="left">
		  	<input class="inputText" type="text" id="lastModifName" name="lastModifName" value="${lastModifyor}" readOnly="true">
		  	</td>
		  	<td width="15%" align="right">最后修改时间:</td>
		  	<td width="40%" align="left">
		  	<div id="lastModifyTimeDiv"></div>
		  	<input class="inputText" type="text" id="lastModifyTime" name="lastModifyTime" value="${lastModifyTime}" readOnly="true">
		  	</td>
	  </tr>
	  	<tr>
		<td align="center" colspan="4" height="10">
		</td>
	</tr>
	  </table>
	 </form>
 </div>

  <div id="div_tabPanel">
  	<div id="div_role"></div>
  	<div id="div_menu"></div>
  	<div id="div_method"></div>
  	<div id="div_workflow"></div>
  	<div id="div_alreadyRemove"></div>
  	<div id="div_otherAuth"></div>
  </div>

  <div id="div_east"></div>
  <div id="div_west"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//全局路径
var context = '<%= request.getContextPath() %>';
//操作类别，菜单(menu)、角色(role)、方法(method)、流程(workflow)
var operType ;
var winAfreshAuth ; //重新授权  窗口
var formAfreshAuth ; //重新授权  Form表单
var isaAfreshAuth ; 
//已授权ColumnTree
var alreadyRemoveColumnTree ;

var menuUrl = context + '/index/indexMainController.spr?action=myAuthMenu';
var roleUrl = context + '/index/indexMainController.spr?action=myAuthRole';
var methodUrl = context + '/index/indexMainController.spr?action=myAuthMethod';
var workflowUrl = context + '/index/indexMainController.spr?action=myAuthWorkflow';
//被授权的权限资源列表
var otherAuthRemoveUrl = context + '/index/indexMainController.spr?action=myAuthOtherAuthRemove'; 
var isDelete ;
var tabs;

Ext.onReady(function(){
    
     tabs = new Ext.TabPanel({
   		id:'gridPanel',
   		renderTo:'div_tabPanel',
   		autoWidth:false,
   		loadMask:true,
   		frame:true,
   		//border:false,
   		autoScroll:true, 
   		activeItem:0,
   		enableTabScroll:true,
   		deferredRender:false,
   		items:[{
   	   		xtype:'iframepanel',
			name:'roleTab',
            id:'roleTab',
			title:'角色',
			height:413,
			autoWidth:true,
			closable: false
   		},
   		{
   	   		xtype:'iframepanel',
   	   	    autoWidth:true,
   	   		name:'menuTab',
            id:'menuTab',
			title:'菜单',
			autoWidth:true,
			height:413,
			closable: false
   		},{
   	   		xtype:'iframepanel',
			name:'methodTab',
            id:'methodTab',
			title:'方法',
			height:413,
			autoWidth:true,
			closable: false
   		},{
   	   		xtype:'iframepanel',
   	   		name:'workflowTab',
            id:'workflowTab',
   	   		height:413,
   	   		autoWidth:true,
			title:'流程',
			closable: false
   		}
   		,{
   	        title: '已转移权限',
   	        id:'alreadyRemove',
   	        name:'alreadyRemove',
   	    	 height:430,
   	        layout:'fit',
   	        contentEl:'div_alreadyRemove'
   		},{
   			xtype:'iframepanel',
   	        title: '被授权 ',
   	        name:'otherAuth',
            id:'otherAuth',
   	        height: 413,
			autoWidth:true,
			closable: false
   		}]
   		});

   	  tabs.on('tabchange', tabsOnTabChange);
   	  function tabsOnTabChange()
   	  {
   	   	  var tab = tabs.getActiveTab();
   	   	  if(tab.name=='roleTab')
   	   	  { 
   	   	   	  if(!tab.iframe.src)
   	   	   	  {
   	   	   	  	tab.setSrc(roleUrl);
   	   	   	  }
   	   	  }
   	   	  else if(tab.name=='menuTab')
   	   	  {
	   	   	  if(!tab.iframe.src)
   	   	   	  {
   	   	   	  	tab.setSrc(menuUrl);
   	   	   	  }
   	   	  }
   	   	  else if(tab.name=='methodTab')
   	   	  {
	   	   	  if(!tab.iframe.src)
   	   	   	  {
   	   	   	  	tab.setSrc(methodUrl);
   	   	   	  }
   	   	  }
   	   	  else if(tab.name=='workflowTab')
   	   	  {
	   	   	  if(!tab.iframe.src)
   	   	   	  {
   	   	   	  	tab.setSrc(workflowUrl);
   	   	   	  }
   	   	  }
   	   	  else if(tab.name=='otherAuth')
   	   	  {
	   	   	  if(!tab.iframe.src)
   	   	   	  {
   	   	   	  	tab.setSrc(otherAuthRemoveUrl);
   	   	   	  }
   	   	  }
   	  }
    
   	//tabs.getActiveTab(0);
   	var tabRole = tabs.getActiveTab();
   	tabRole.setSrc(roleUrl);

    var toolbars = new Ext.Toolbar({
 	  items:[
 				'-','->',
 				{text:' 删除',iconCls:'icon-delete',handler:_deleteAuthRemove},
 				{text:' 回收权限',iconCls:'icon-add',handler:_callbackAuth},
 				{text:' 重新授权',iconCls:'icon-add',handler:_afreshAuth},
 				{text:' 展开树',handler:function(){alreadyRemoveColumnTree.expandAll();}},
 				{text:' 收起树',handler:function(){alreadyRemoveColumnTree.collapseAll();}},
 				{text:' 刷新树',handler:function(){
 					alreadyRemoveColumnTree.body.mask('Loading', 'x-mask-loading');
 					alreadyRemoveColumnTree.root.reload();
 					alreadyRemoveColumnTree.root.collapse(true, false);
		             setTimeout(function(){
		            	 alreadyRemoveColumnTree.body.unmask();
		             }, 1000); 
	 				}},
 				'-'
 				]
 	});
 	
	 var alreadyRemoveTreeLoader = new Ext.tree.TreeLoader({
    	url:context+'/index/indexMainController.spr?action=getAlreadyRemoveColumnTreeGroupByUser',
        baseAttrs:{uiProvider:Ext.ux.ColumnTreeCheckNodeUI}//如果不需要checkbox ,则需要使用 Ext.tree.ColumnTreeNodeUI  
    });   
      
	 alreadyRemoveTreeLoader.on("beforeload", function(treeLoader, node) {   
                alreadyRemoveTreeLoader.baseParams = {'resourceType': node.attributes.resourceType,
                                                          'authType': node.attributes.authType,
                                                    'userAuthMoveId': node.attributes.userAuthMoveId,
                                                        'toUserName': node.attributes.toUserName,
                                                        'resourceId': node.attributes.resourceId,
                                                        'name': node.attributes.name
                                                     };
            }, alreadyRemoveTreeLoader);
	 
	 
	 alreadyRemoveColumnTree = new Ext.tree.ColumnTree({   
				id:'alreadyRemoveColumnTree',
		        region:'center',
		        split:true,
		        el:'div_alreadyRemove',
		        tbar:[toolbars],
		        cmargins:'0 0 0 0',
		        animCollapse:false,
		        animate: false,
		        collapseMode:'mini',
			    border: false,   
			    lines: true,   
                height:430,
			    rootVisible: false,   
			    autoScroll:true,   
			    checkModel:'cascade',//级联多选，如果不需要checkbox,该属性去掉   
			    onlyLeafCheckable: false,//所有结点可选，如果不需要checkbox,该属性去掉   
			    loader:alreadyRemoveTreeLoader,   
			    root: new Ext.tree.AsyncTreeNode({ id:'-1'}),   
			    columns:[   
			        //{ header:'权限转移ID', width:140, dataIndex:'userAuthMoveId' },
			        //{ header:'权限转移类别名称', width:140, dataIndex:'typeName'},
			        { header:'名称', width:260, dataIndex:'name'},
			        { header:'名称描述', width:300, dataIndex:'nameDesc'},
			        { header:'权限转给用户', width:120, dataIndex:'toUserName',align :'center',headeralign :'center'},
			        { header:'授权开始时间', width:120, dataIndex:'authStartTime',align :'center',headeralign :'center'},
			        { header:'授权结束时间', width:120, dataIndex:'authEndTime',align :'center',headeralign :'center'},
			        { header:'是否有效', width:120, dataIndex:'isEffect',align :'center',headeralign :'center'}
			        //{ header:'menuId', width:140, dataIndex:'menuId'},
			        //{ header:'resourceId', width:140, dataIndex:'resourceId'},
			        //{ header:'roleId', width:140, dataIndex:'roleId'}

			    ],  
   	            listeners: {
	            beforecheck : function(n) {
	            	if (n.attributes.checked) return true;
					//if( !n.expanded && !n.childrenRendered ) n.expand();
					if (n.parentNode.id == '-1'){
						var checkedNodes = alreadyRemoveColumnTree.getChecked();
						for(var i=0;i<checkedNodes.length;i++){
							var node = checkedNodes[i];
							//if(node.id != n.id){
								node.getUI().checkbox.checked = false;
								node.attributes.checked = false;
								alreadyRemoveColumnTree.fireEvent('check', node, false);
							//}
						}
					}
	            	return true;
	            },
	            
	            afterrenderer : function(n) {
	            	var checkbox = n.getUI().checkbox;
	            	//if((typeof checkbox != 'undefined') && (n.parentNode != n.getOwnerTree().getRootNode())) 
		                	//checkbox.disabled = false ;
	            }
	        }
		}); 
	 alreadyRemoveColumnTree.render(); 
	 alreadyRemoveColumnTree.expand(); 
	 alreadyRemoveColumnTree.expandAll();
	 
   	var viewport = new Ext.Viewport({
   		layout:"border",
   		border:false,
   		items:[{
   			region:'center',
   			border:false,
   			layout:'fit',
   			items:[{
				region:"center",
				border:false,
				items:[{
		   				region:'north',
		   				height:0,
		   				border:false,
		   				border:false,
		   				contentEl:'div_toolbar'
		   				},
		   				{
			   				region:"center",
			   	            layout:'fit',
			   	         	border:false,
			   	            //height:73,
			   				border:false,
			   				contentEl: 'div_top'
			   			},
			   			{
			   				region:'south',
			   				autoWidth:false,
			   				height: 410,
			   				border:false,
			   				layout:'fit',
			   				contentEl:'div_tabPanel'
			   			}]
		   			}]
   		}]
   	});
   
    
});



/**
 * 重新授权
 */
function _afreshAuth()
{
	var dateNow= '${dateNow}';
	//alert(dateNow);
	
	isDelete = false;
	var checkedNodes = alreadyRemoveColumnTree.getChecked();
	if (checkedNodes.length  > 0){	
		var authRemoveIdList = "";		
		
		var strmsg = "";
		for(var i=0;i<checkedNodes.length;i++){
			var node = checkedNodes[i];
	        if(node.attributes.leaf)
	        {
				var isEffect = node.attributes.isEffect;
	    		//判断
				if (isEffect == 'Y'){
					//strmsg = "部分数据已经转移过，不能重新授权！请检查！" ;			
				}
	        	authRemoveIdList = authRemoveIdList + node.attributes.userAuthMoveId + ",";
	        }
		}
		
		if(strmsg != "")
		{
			_getMainFrame().showInfo(strmsg );
			return;	
		}
        
		formAfreshAuth=new Ext.form.FormPanel({
			frame:true,
			baseCls:'x-plain',
			autoHeight:true,
			labelWidth:60,
			name:'formAfreshAuth',
			id:'formAfreshAuth',
			width:200,
			fileUpload:true,
			bodyStyle:'padding: 10px 10px 0 10px;',
			defaults:{
				anchor:'95%',
				msgTarget:'side'
			},
			items:[{
				id:'authStartTime',
				width:200,
				allowBlank:false,
				xtype:'datefield',
				fieldLabel:'授权开始',
				blankText:'[授权开始]不能为空！',
				anchor:'95%'
			},{
				id:'authEndTime',
				width:200,
				allowBlank:false,
				xtype:'datefield',
				fieldLabel:'授权结束',
				blankText:'[授权结束]不能为空！',
				anchor:'95%'
			}
			]
		});	

		winAfreshAuth = new Ext.Window({
		    title: '重新授权',
		    width: 250,
		    height:160,
		    layout: 'fit',
		    iconCls:'icon-window',
		    plain:true,
		    modal:true,
		    bodyStyle:'padding:5px;',
		    buttonAlign:'center',
		    maximizable:false,
			closeAction:'close',
			closable:false,
			collapsible:false,
		    items: formAfreshAuth,
		    buttons: [
		    {
		        text: '确认重新授权',
		        handler:function()
		        {
					if(formAfreshAuth.form.isValid()){
						isaAfreshAuth = true ;	
						if(parseInt(parseInt(removeDash(Ext.getDom('authEndTime').value))-parseInt(removeDash(Ext.getDom('authStartTime').value)))<0){
							_getMainFrame().showInfo('授权结束日期不能小于授权开始日期！');
							return;
						}
						if(parseInt(parseInt(removeDash(Ext.getDom('authEndTime').value))-parseInt(removeDash(dateNow)))<0){
							_getMainFrame().showInfo('授权结束日期不能小于当前日期！');
							return;
						}
						var param = '&authRemoveIdList='+authRemoveIdList + "&"+ Form.serialize('formAfreshAuth') ;
						new AjaxEngine('<%= request.getContextPath() %>/index/indexMainController.spr?action=_afreshAuth', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}	
		    },
		    {
		        text: '取消',
		        handler:function()
		        {
		    	    formAfreshAuth.form.reset();
		        	winAfreshAuth.close();
		        }
		    }]
		});
			
		winAfreshAuth.show();
	   
	}else{
		_getMainFrame().showInfo('请选择要重新授权的权限转移信息！');
	}
}
function removeDash(v){
	var s = v.split('-');
	var t='';
	for(var i=0;i<s.size();i++){
		t = t+s[i];
	}
	return t;
}

/**
 * 回收权限
 */
function _callbackAuth()
{
	isDelete = false
	var checkedNodes = alreadyRemoveColumnTree.getChecked();
    var strmsg ="";
 
	if (checkedNodes.length > 0){
		var authRemoveIdList = "";		
		for(var i=0;i<checkedNodes.length;i++){
			var node = checkedNodes[i];
	        if(node.attributes.leaf)
	        {
				var isEffect = node.attributes.isEffect;
	    		//判断
				if (isEffect == 'N'){
					strmsg = "部分数据已经回收过，不必再次回收！请检查！" ;			
				}
	        	authRemoveIdList = authRemoveIdList + node.attributes.userAuthMoveId + ",";
	        }
		}

		if(strmsg != "")
		{
			_getMainFrame().showInfo(strmsg );
			return;	
		}
		
		var param = '&authRemoveIdList='+authRemoveIdList;
		new AjaxEngine('<%= request.getContextPath() %>/index/indexMainController.spr?action=_callbackAuth', 
				{method:"post", parameters: param, onComplete: callBackHandle});
		
	}else{
		_getMainFrame().showInfo('请选择要回收的权限转移信息！');
	}
}


/**
 * 删除授权
 */
function _deleteAuthRemove()
{
	var checkedNodes = alreadyRemoveColumnTree.getChecked();
    var strmsg ="";
    
	if (checkedNodes.length > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '数据删除后将不可恢复，是否继续执行此操作？',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
					if (buttonid == 'yes'){
		   				var authRemoveIdList = "";		
		   				isDelete = true

		   				for(var i=0;i<checkedNodes.length;i++){
		   					var node = checkedNodes[i];
		                    
		   			        if(node.attributes.leaf)
		   			        {
		   			        	authRemoveIdList = authRemoveIdList + node.attributes.userAuthMoveId + ",";
		   			        }
		   				}
						var param = '&authRemoveIdList='+authRemoveIdList;
						new AjaxEngine('<%= request.getContextPath() %>/index/indexMainController.spr?action=_deleteAuthRemove', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});
	}else{
		_getMainFrame().showInfo('请选择要删除的权限转移信息！');
	}
}

function customCallBackHandle(transport)
{   
    //刷新树
    alreadyRemoveColumnTree.body.mask();
    alreadyRemoveColumnTree.root.reload();
    alreadyRemoveColumnTree.root.collapse(true, false);
    alreadyRemoveColumnTree.expandAll();
    setTimeout(function(){
        alreadyRemoveColumnTree.body.unmask();
    }, 1000); 

	if(isDelete==true)
	{
        //div_role_grid.getStore().reload();
        //div_method_grid.getStore().reload();
        //div_workflow_grid.getStore().reload();
         //刷新树 菜单
        var tab1 = Ext.getCmp("menuTab");
        tab1.setSrc(menuUrl);
        //tab.resetUrl();
        var tab2 = Ext.getCmp("roleTab");
        tab2.setSrc(roleUrl);
        var tab3 = Ext.getCmp("methodTab");
        tab3.setSrc(methodUrl);
        var tab4 = Ext.getCmp("workflowTab");
        tab4.setSrc(workflowUrl);
	}
	
	if(isaAfreshAuth==true)
	{
	    formAfreshAuth.form.reset();
		winAfreshAuth.close();
		isaAfreshAuth = false;
	}
}
</script>

