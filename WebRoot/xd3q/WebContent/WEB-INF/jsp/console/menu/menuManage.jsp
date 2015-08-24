<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
html,body {
	font: normal 12px verdana;
	margin: 0;
	padding: 0;
	border: 0 none;
	overflow: hidden;
	height: 100%;
}

p {
	margin: 5px;
}

.settings {
	background-image: url(/infolionPlatform/images/fam/folder_wrench.png);
}
.add{
	background-image:url(images/fam/add.gif) !important;
}
.delete{
	background-image:url(images/fam/delete.gif) !important;
}
.update{
	background-image:url(images/fam/refresh.gif) !important;
}
</style>

</head>
<body>
<div id="tree-div" style="overflow: auto; width: 100%; height: 100%"></div>
<div id='center-south'></div>
<div id="menu_info" style="overflow: auto; width: 100%; height: 100%">
<div id="toolbar"></div>
<form action="" name="resourceData" method="post">
<table border="0" align="center" width="100%">
<tr>
<td align="right">
	<input type="hidden" id="resourceid" name="resourceid" value=""></input>
	资源类型:
</td>
<td>
<div id="typeidDiv"></div>
</td>
<td align="right">资源名称:</td>
<td>
<input type="text" id="resourcename" name="resourcename" value="" size="15"></input>
</td>
</tr>

<tr>
<td align="right">资源标题:</td>
<td>
<input type="text" id="resourcetitle" name="resourcetitle" value="" size="15"></input>
</td>
<td align="right">显示顺序:</td>
<td>
<input type="text" id="displayno" name="displayno" value="" size="15"></input>
</td>
</tr>
<tr>
<td align="right">资源图标:</td>
<td>
<input type="text" id="resourceicon" name="resourceicon" value="" size="15"></input>
</td>
<td align="right">
<input type="hidden" id="parentid" name="parentid" value="" size="15"></input>
上级资源:
</td>
<td>
	<input type="text" id="parenttitle" name="parenttitle" value="" size="15" readonly="readonly"></input>
</td>
</tr>

<tr>
<td align="right">资源地址:</td>
<td colspan="5">
	<input type="text" id="resourceurl" name="resourceurl" value="" size="63"></input>
</td>
</tr>

<tr>
<td align="right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</td>
<td colspan="5">
	<input type="text" id="cmd" name="cmd" value="" size="63"></input>
</td>
</tr>

<tr>
<td colspan="2" align="center">
	<input type="button" name="submitresource" id="submitresource" value="提交" onclick="addResource();" disabled="true"></input>
</td>
<td colspan="2" align="center">
	<input type="reset" name="resetresource" id="resetresource" value="重置" disabled="true"></input>
</td>
</tr>
<tr>
<td colspan="4">
	<div id="runInfoDiv" onclick="this.hide();"></div>
</td>
</tr>
</table>
</form>
</div>
</body>
</html>
<script>
var tree;

//用于取得url地址中的参数的值
function getUrlParaValue(url,key){
var ParamArray = new Array();
var l = new String(url);
var paramstring = new String(l.substring(l.lastIndexOf('?')+1,l.length));
ParamArray = paramstring.split('&');
for(var i = 0;i<ParamArray.length;i++)
{
    ParamArray[i] = ParamArray[i].toString().split('=');
}

for(var i=0;i<ParamArray.length;i++)
{
     if(ParamArray[i][0] == key)
     {
         return ParamArray[i][1];
     }
}

return "null";
}

function addResource(){
	var param = Form.serialize("resourceData");
	if (getUrlParaValue(param,"typeid") == ''){
		top.ExtModalWindowUtil.alert('提示','请选译资源类型！');
	}else{
		param += "&action=addResource";
		new AjaxEngine('menuController.spr', 
		   {method:"post", parameters: param, onComplete: callBackHandle});
	}
}

function customCallBackHandle(transport)
{
	var treeNodeId = document.resourceData.resourceid.value;
	var treeNode = tree.getNodeById(treeNodeId);
	
	var parentNode = treeNode.parentNode;
	
	if (parentNode.childNodes.length == 1)
		tree.root.reload();
	else
		parentNode.reload();
}

Ext.onReady(function(){	
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	var systemTreeLoader = new Ext.tree.TreeLoader({
    	url:'menuController.spr?action=getResourceByParentId'
    });
      
   systemTreeLoader.on("beforeload", function(treeLoader, node) {   
                systemTreeLoader.baseParams.id = node.attributes.id;   
            }, systemTreeLoader);
    
    tree = new Ext.tree.TreePanel({
    	id:'resource_tree',
        el:'tree-div',
        layout:'fit',
        autoScroll:true,
        animate:true,
        enableDD:true,
        rootVisible:true,
        containerScroll: true,
        loader: systemTreeLoader
    }); 
    
    var root = new Ext.tree.AsyncTreeNode({
        text: '系统资源',
        draggable:false,
        id:'-1'
    });
    tree.setRootNode(root);
    //tree.on('contextMenu',openmenu);
    tree.on('click',treeclick);
    tree.render();
    root.expand();
    
    function treeclick(node,e){
    	if (node.id == 'new')
    		return;
    	
    	//如果已经增加了一个新的节点没有提交，又转去点击别的节点，就先取消这个新增的结点
    	var treeNode = tree.getNodeById("new");
    	if (treeNode != null)
    	{
    		var parentNode = treeNode.parentNode;
    		treeNode.remove();
			if (parentNode.firstChild == null)
			{
				parentNode.leaf = true;
			}
    	}
    	
   		resourceds.proxy= new Ext.data.HttpProxy({url: 'menuController.spr?action=getResourceByParentIdToGrip&id='+node.id});
		resourceds.load({params:{start:0, limit:50},arg:[]});

		if (node.id == '-1'){
			document.resourceData.reset();
			document.resourceData.resourceid.value = node.id;
			document.resourceData.parentid.value = '';
			document.resourceData.parenttitle.value = '';

			document.resourceData.submitresource.disabled = true;
			document.resourceData.resetresource.disabled = true;
		}
		else{
			Ext.Ajax.request({
				url: 'menuController.spr?action=getResourceById&id='+node.id,
				success: function(response, options){
					var responseArray = Ext.util.JSON.decode(response.responseText);
					document.resourceData.resourceid.value = responseArray.resourceid;
					dict_typeidDiv.setComboValue(responseArray.typeid);
					document.resourceData.resourcename.value = responseArray.resourcename;
					document.resourceData.resourcetitle.value = responseArray.resourcetitle;
					document.resourceData.resourceurl.value = responseArray.resourceurl;
					document.resourceData.resourceicon.value = responseArray.resourceicon;
					document.resourceData.parentid.value = responseArray.parentid;
					document.resourceData.parenttitle.value = responseArray.parenttitle;
					document.resourceData.cmd.value = responseArray.cmd;
					document.resourceData.displayno.value = responseArray.displayno;

					document.resourceData.submitresource.disabled = true;
					document.resourceData.resetresource.disabled = true;
					
				},
				failure:function(response, options){
					
				}
			});
		}
		
		tbr.setDisabled(false);
    }
    
    var tbr = new Ext.Toolbar();   
    tbr.render('toolbar');
    tbr.addButton( new Ext.Toolbar.Button({
    		text:'增加下级资源',
    		iconCls:'add',
    		handler:function(){
    			var treeNodeId = document.resourceData.resourceid.value;

    			
	    			var treeNode = tree.getNodeById(treeNodeId);
					treeNode.expand();
					var chlidNode=new Ext.tree.TreeNode({
						id:'new',
						text:'请增加内容',
						leaf : true
					});
					treeNode.leaf = false;
					treeNode.appendChild(chlidNode);
					treeNode.expand();
					document.resourceData.reset();
					document.resourceData.resourceid.value = 'new'
					document.resourceData.parentid.value = treeNode.id;
					document.resourceData.parenttitle.value = treeNode.text;
					
					document.resourceData.submitresource.disabled = false;
					document.resourceData.resetresource.disabled = false;
					
					tbr.setDisabled(true);
    		}
    	})
    );
    tbr.addSpacer();
	tbr.addSeparator();
	tbr.addButton( new Ext.Toolbar.Button({
    		text:'修改',
    		iconCls:'update',
    		handler:function(){
				document.resourceData.submitresource.disabled = false;
				document.resourceData.resetresource.disabled = false;
    		}
    	})
    );
    tbr.addSpacer();
	tbr.addSeparator();
	tbr.addButton( new Ext.Toolbar.Button({
    		text:'删除',
    		iconCls:'delete',
    		handler:function(){
    			var nodeId = document.resourceData.resourceid.value;
    			var node = tree.getNodeById(nodeId);
    			var parentNode = node.parentNode;
				if (node.leaf == false){
					Ext.Msg.alert('提示','请先删除该节点下的子节点再删除此节点！')
				}else{
					if (nodeId != 'new'){
						Ext.Ajax.request({
							url: 'menuController.spr?action=deleteResource&id='+nodeId,
							success: function(response, options){
								node.remove();
								if (parentNode.firstChild == null)
								{
									parentNode.leaf = true;
								}
								var responseArray = Ext.util.JSON.decode(response.responseText);
								Ext.Msg.alert('提示',responseArray.message);
							},
							failure:function(response, options){
							}
						});
					}
				}
    		}
    	})
    );
    
    tbr.setDisabled(true);

    var resourceds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'menuController.spr?action=getResourceByParentIdToGrip&id=-1'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},[
            		{name:'resourceid'},
            		{name:'typeid'},
            		{name:'typename'},
            		{name:'resourcename'},
            		{name:'resourcetitle'},
            		{name:'resourceurl'},
            		{name:'resourceicon'},
            		{name:'parentid'},
            		{name:'parenttitle'},
            		{name:'cmd'}
          		])
    });
    
    var resourcecm = new Ext.grid.ColumnModel([
    								  new Ext.grid.RowNumberer(),
         							  {header: '资源编号',
                                        width: 100,
                                        sortable: false,
                                        dataIndex: 'resourceid',
                                        hidden:true
                                        },
                                        {header: '资源类型编号',
                                        width: 100,
                                        sortable: true,
                                        dataIndex: 'typeid',
                                        hidden:true
                                        },
                                        {header: '资源标题',
                                        width: 100,
                                        sortable: true,
                                        dataIndex: 'resourcetitle'
                                        },
                                        {header: '资源名称',
                                        width: 100,
                                        sortable: true,
                                        dataIndex: 'resourcename'
                                        },
                                        {header: '资源地址',
                                        width: 100,
                                        sortable: true,
                                        dataIndex: 'resourceurl'
                                        },
                                        {header: '上一级资源编号',
                                        width: 100,
                                        sortable: true,
                                        dataIndex: 'parentid',
                                        hidden:true
                                        },
                                        {header: '上一级资源标题',
                                        width: 100,
                                        sortable: true,
                                        dataIndex: 'parenttitle'
                                        },
                                        {
                                        header: '资源类型名称',
                                        width: 100,
                                        sortable: true,
                                        dataIndex: 'typename'
                                        },
                                        {header: '资源图标',
                                        width: 100,
                                        sortable: true,
                                        dataIndex: 'resourceicon'
                                        },
                                        {header: '备注',
                                        width: 100,
                                        sortable: true,
                                        dataIndex: 'cmd'
                                        }
    ]);
    resourcecm.defaultSortable = true;
    
    var resourcebbar = new Ext.PagingToolbar({
        pageSize: 50,
        store: resourceds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    resourceds.load({params:{start:0, limit:50},arg:[]});
    
    var resourcegrid = new Ext.grid.GridPanel({
        ds: resourceds,
        cm: resourcecm,
        bbar: resourcebbar,
        border:false,
        loadMask: true,
        autoScroll:true,
        el: 'center-south',
        autowidth:true,
        layout:'fit'
    });
   	
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[
		{
			region:"west",
			title:"资源信息询",
			split:true,			 //可改变框体大小
			collapsible: true,   //可收缩
            width: 200,
            minSize: 175,
            maxSize: 400,
            margins:'0 0 0 5',
            //contentEl: 'tree-div'
            layout:'accordion',
            layoutConfig:{
               animate:true
            },
            items:[{
            	title:'目录树查询',
				border:false,
				layout:'fit',
				contentEl: 'tree-div',
				tools:[{
					id:'refresh',   
                    qtip: '刷新资源树信息',
                     handler: function(event, toolEl, panel) {
                     	var tree = Ext.getCmp('resource_tree');
                        tree.body.mask('Loading', 'x-mask-loading');
                        tree.root.reload();
                        tree.root.collapse(true, false);
                        setTimeout(function(){
                        	tree.body.unmask();
                        }, 1000);
                     }
				}],
				items:[tree]
            }]
		},
		{
			region:'center',
			layout:'border',
			items:[{
				region:'center',
				contentEl:'menu_info',
				title: '资源信息',
            	plain:true,
            	height:300,
            	defaults:{bodyStyle:'padding:0px'}
			},{
				region:'south',
				contentEl:'center-south',
				title: '下级资源列表详情',
				layout:'fit',
            	height:284,
            	defaults:{bodyStyle:'padding:0px'},
	            items:[resourcegrid]
			}]
		}]
    });
    
    viewport.render();         
});
</script>
<fiscxd:dictionary divId="typeidDiv" fieldName="typeid" dictionaryName="BM_SYS_RES_TYPE" width="126"></fiscxd:dictionary>