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
<div id="div_west_tree" style="overflow: auto; width: 100%; height: 100%">
</div>
<div id="div_west_condition">
</div>

<!-- 下面是右边上部信息 -->
<div id="div_center_north" style="overflow: auto; width: 100%; height: 100%">
<div id="tool_bar" style="overflow: auto; width: 100%;">
</div>
<form action="" name="deptform">
<table width="100%">
<tr>
<td>部&nbsp&nbsp&nbsp&nbsp&nbsp门&nbsp&nbsp&nbsp&nbsp&nbsp名&nbsp&nbsp&nbsp&nbsp称:</td>
<td>
<input type="hidden" name="deptid" id="deptid" value=""></input>
<input type="text" name="deptname" id="deptname" size="15" value=""></input>
</td>
<td>部&nbsp&nbsp&nbsp&nbsp&nbsp门&nbsp&nbsp&nbsp&nbsp&nbsp编&nbsp&nbsp&nbsp&nbsp&nbsp码:</td>
<td>
<input type="text" name="deptcode" id="deptcode" size="15" value=""></input>
</td>
</tr>

<tr>
<td>
部&nbsp&nbsp&nbsp&nbsp&nbsp门&nbsp&nbsp&nbsp&nbsp&nbsp代&nbsp&nbsp&nbsp&nbsp&nbsp码:
</td>
<td>
<input type="text" name="deptShortCode" id="deptShortCode" size="15" value=""></input>
</td>
<td>
职&nbsp&nbsp&nbsp&nbsp&nbsp能&nbsp&nbsp&nbsp&nbsp&nbsp部&nbsp&nbsp&nbsp&nbsp&nbsp门:
</td>
<td>
<select name="isFuncDept" id="isFuncDept" size="1">
<option VALUE="">请选译</option>
<option VALUE="1">是</option>
<option VALUE="2">否</option>
</select>
</td>
</tr>

<tr>
<td>
上&nbsp&nbsp&nbsp&nbsp&nbsp级&nbsp&nbsp&nbsp&nbsp&nbsp部&nbsp&nbsp&nbsp&nbsp门:
</td>
<td colspan="3">
<input type="text" name="pdeptname" id="pdeptname" size="59" value=""></input>
</td>
<td>
<input type="hidden" name="pdeptid" id="pdeptid" value=""></input>
</td>
</tr>

<tr>
<td>业务部门SAP代码:</td>
<td>
<input type="text" name="ywbmcode" id="ywbmcode" size="15" value=""></input>
</td>
<td>利润中心SAP代码:</td>
<td>
<input type="text" name="lrzxcode" id="lrzxcode" size="15" value=""></input>
</td>
</tr>

<tr>
<td>成本中心SAP代码:</td>
<td>
<input type="text" name="cbzxcode" id="cbzxcode" size="15" value=""></input>
</td>
<td>销售组织SAP代码:</td>
<td>
<input type="text" name="xszzcode" id="xszzcode" size="15" value=""></input>
</td>
</tr>

<tr>
<td>采购组织SAP代码:</td>
<td>
<input type="text" name="cgzzcode" id="cgzzcode" size="15" value=""></input>
</td>
<td>预算组织SAP代码:</td>
<td>
<input type="text" name="yszzcode" id="yszzcode" size="15" value=""></input>
</td>
</tr>

<tr>
<td>公司代码:</td>
<td>
<input type="text" name="companyCode" id="companyCode" size="15" value=""></input>
</td>
<td>
</td>
<td>
</td>
</tr>

<tr>
<td colspan="4" align="center">
<input type="button" disabled="true" name="submitdept" id="submitdept" value="提交" onclick="adddept();"></input>
</td>

</tr>
</table>
</form>
</div>
<div id="div_center_center" style="overflow: auto; width: 100%; height: 100%">
</div>
</body>
</html>
<script type="text/javascript">
var tree;
var depttbr;

function adddept(){
	var param = Form.serialize("deptform");
	param += "&action=addDept";
	new AjaxEngine('orgController.spr', 
		   {method:"post", parameters: param, onComplete: callBackHandle});
}

function customCallBackHandle(transport)
{
	transport.responseText;
	var treeNodeId = document.deptform.deptid.value;
	var treeNode = tree.getNodeById(treeNodeId);
	var parentNode = treeNode.parentNode;
	if (parentNode.childNodes.length == 1)
		tree.root.reload();
	else
		parentNode.reload();
		
	depttbr.setDisabled(false);
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	var systemTreeLoader = new Ext.tree.TreeLoader({
    	url:'orgController.spr?action=queryDeptByLoginUser'
    });
      
    systemTreeLoader.on("beforeload", function(treeLoader, node) {   
                systemTreeLoader.baseParams.id = node.attributes.id;   
            }, systemTreeLoader);
    
    tree = new Ext.tree.TreePanel({
    	id:'dept_tree',
        el:'div_west_tree',
        layout:'fit',
        autoScroll:true,
        animate:true,
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
    tree.render();
    tree.on('click',treeclick);
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
		
		ds.proxy= new Ext.data.HttpProxy({url: 'orgController.spr?action=queryUserByDeptIdToGrid&deptid='+node.id});
		ds.load();
		
		//如果点的是根目录就不用再去查询部门信息
		if (node.id == '-1'){
			document.deptform.reset();
			document.deptform.deptid.value = '-1';
		}
		else{
			Ext.Ajax.request({
				url: 'orgController.spr?action=queryDeptById&deptid='+node.id,
				success: function(response, options){
					var responseArray = Ext.util.JSON.decode(response.responseText);
					
					document.deptform.deptid.value = responseArray.deptid;
					document.deptform.deptname.value = responseArray.deptname;
					document.deptform.deptcode.value = responseArray.deptcode;
					document.deptform.pdeptid.value = responseArray.pdeptid;
					document.deptform.pdeptname.value = responseArray.pdeptname;
					document.deptform.ywbmcode.value = responseArray.ywbmcode;
					document.deptform.lrzxcode.value = responseArray.lrzxcode;
					document.deptform.cbzxcode.value = responseArray.cbzxcode;
					document.deptform.xszzcode.value = responseArray.xszzcode;
					document.deptform.cgzzcode.value = responseArray.cgzzcode;
					document.deptform.yszzcode.value = responseArray.yszzcode;
					document.deptform.deptShortCode.value = responseArray.deptShortCode;
					document.deptform.isFuncDept.value = responseArray.isFuncDept;
					document.deptform.companyCode.value = responseArray.companyCode;
					
					document.deptform.submitdept.disabled = true;
				}
			});
			
			depttbr.setDisabled(false);
		}
    }
    
    var addDept = new Ext.Toolbar.Button({
   		text:'增加下级部门',
   		iconCls:'add',
   		handler:function(){
   			if (document.deptform.deptid.value == '' ||
   				document.deptform.deptid.value == 'new')
   				return;
   				
   			var treeNodeId = document.deptform.deptid.value;
   			var treeNode = tree.getNodeById(treeNodeId);
   			treeNode.expand();
   			var chlidNode=new Ext.tree.TreeNode({
				id:'new',
				text:'请增加部门信息',
				leaf : true
			});
			treeNode.leaf = false;
			treeNode.appendChild(chlidNode);
			treeNode.expand();
			document.deptform.reset();
			document.deptform.deptid.value = 'new';
			document.deptform.pdeptid.value = treeNode.id;
			document.deptform.pdeptname.value = treeNode.text;
			document.deptform.submitdept.disabled = false;

			depttbr.setDisabled(true);
   		}
   	});


	var updateDept = new Ext.Toolbar.Button({
   		text:'修改当前部门',
   		iconCls:'update',
   		handler:function(){
   			document.deptform.submitdept.disabled = false;
   		}
   	});

    var deleteDept = new Ext.Toolbar.Button({
   		text:'删除当前部门',
   		iconCls:'delete',
   		handler:function(){
   			if (ds.getCount() > 0){
   				top.ExtModalWindowUtil.alert('提示','请先删除部门下的员工再删除本部门！');
   				return;
   			}
   			var nodeId = document.deptform.deptid.value;
   			var node = tree.getNodeById(nodeId);
   			var parentNode = node.parentNode;
   			if (node.leaf == false){
				top.ExtModalWindowUtil.alert('提示','请先删除下级子部门后再删除本部门！');
			}else{
				if (nodeId != 'new'){
					Ext.Ajax.request({
						url: 'orgController.spr?action=deleteDept&deptid='+nodeId,
						success: function(response, options){
							top.ExtModalWindowUtil.alert('提示','操作成功！');
						},
						failure:function(response, options){
							top.ExtModalWindowUtil.alert('提示','操作失败！');
						}
					});
				}
				node.remove();
				if (parentNode.firstChild == null)
				{
					parentNode.leaf = true;
				}
			}
   		}
   	});
    	
   depttbr = new Ext.Toolbar({
    	rendTo:'tool_bar',
   		items:[addDept,'-',updateDept,'-',deleteDept]
   	});
   depttbr.render('tool_bar');
   depttbr.setDisabled(true);
   
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
					{name:'deleted'}
          		])
    });
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
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
        el: 'div_center_center',
        autowidth:true,
        layout:'fit'
    });
   	
   	grid.render('div_center_center');
   	
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"west",
			title:"部门信息查询",
			split:true,			 //可改变框体大小
			collapsible: true,   //可收缩
            width: 200,
            minSize: 175,
            maxSize: 400,
            margins:'0 0 0 5',
            layout:'accordion',
            layoutConfig:{
               animate:true
            },
            items:[{
            	title:'目录树查询',
				border:false,
				layout:'fit',
				contentEl: 'div_west_tree',
				tools:[{
					id:'refresh',   
                    qtip: '刷新部门树信息',
                     handler: function(event, toolEl, panel) {
                     	var tree = Ext.getCmp('dept_tree');
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
		},{
			region:"center",
			layout:'border',
			items:[{
				region:'center',
				contentEl:'div_center_north',
				title: '资源信息',
				plain:true,			
            	height:300,
            	defaults:{bodyStyle:'padding:0px'}
			},{
				region:'south',
				contentEl:'div_center_center',
				title: '员工列表详情',
            	plain:true,
            	height:260,
            	defaults:{bodyStyle:'padding:0px'},
	            layout:'fit',
	            items:[grid]
			}]
		}]
	});
});
</script>