<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
  <title>index</title>
	<%@ include file="/common/commons.jsp"%>
</head>
<body>

</body>
</html>
<script>
var tree;
Ext.onReady(function(){
	//_getMainFrame().maintabs.addPanel('标题','',contextPath +'/component/grid/index.jsp'); 
	//_getMainFrame().ExtModalWindowUtil.alert("ddd","dddd");

    tree = new Ext.tree.ColumnTree({
    	id:'auth-tree',
    	region:'center',
        rootVisible:false,
        autoScroll:true,
        title: '数据权限',
        columns:[{
            header:'用户主记录中的权限对象',
            width:200,
            dataIndex:'FIELD'
        },{
            header:'用户主记录中的权限描述',
            width:300,
            dataIndex:'TTEXT',
            headeralign: 'center'
        },{
            header:'值',
            width:450,
            dataIndex:'VALUE'
        }],
        tbar:[{
            text:'增加数据对象',
            iconCls:'icon-add',
            handler : function(){
            	if (typeof((_getMainFrame().Ext).getCmp("YHDATAAUTHXOBJ")) == 'undefined'){ 
					new (_getMainFrame().Ext).SearchHelpWindow({
						id: 'YHDATAAUTHXOBJ',
						shlpName : 'YHDATAAUTHXOBJ',
						callBack : winCallBack
					});
					_pushCmps((_getMainFrame().Ext).getCmp("YHDATAAUTHXOBJ"));
            	};
            	(_getMainFrame().Ext).getCmp("YHDATAAUTHXOBJ").show();
            }
        },'-',{
            text:'删除数据对象',
            iconCls:'icon-delete',
            handler : function(){
            	var t = Ext.getCmp("auth-tree");
				var checkedNodes = t.getChecked();
				for(var i=0;i<checkedNodes.length;i++){
					checkedNodes[i].remove();
					t.fireEvent('removenode', checkedNodes[i]);
				}
            }
        }
        /*
        ,'-',{
            text:'保存',
            handler : function(){
            	var t = Ext.getCmp("auth-tree");
            	var modifies = [];
            	for (var i=0;i<t.modified.length;i++){
            		var node = t.modified[i];
            		alert(Ext.util.JSON.encode(node.attributes));
            		modifies.push({dataAUTHXobjectValueId:node.attributes.VALUEID,dataAuthxObjectName:node.attributes.OBJCT,dataAUTHField:node.attributes.FIELD,value:node.attributes.VALUE,roleId:node.attributes.ROLEID,client:node.attributes.CLIENT});
            	}
            	
            	var removes = [];
            	for (var i=0;i<t.removed.length;i++){
            		var node = t.removed[i];
            		removes.push({dataAUTHXobjectValueId:node.attributes.VALUEID});
            	}
            	var dataModify= Ext.util.JSON.encode({objectName:'DataAUTHXobjectValue',operType:'modify',values:modifies});
            	var dataRemove= Ext.util.JSON.encode({objectName:'DataAUTHXobjectValue',operType:'delete',values:removes});	//需要优化huanghu
            	
            	var url = contextPath + '/basicApp/authManage/roleController.spr?action=_saveRoleAuth&subObject='+dataModify+"&subObject="+dataRemove;

				Ext.Ajax.request({
				    url: url,
				    params: {
				    },
				    method : 'POST',
				    success: callBackHandle,
				    failure: function(xhr) {
				    	Ext.Msg.alert("Failed", xhr.responseText);
				    },
				    scope : this
				});
				
				function customCallBackHandle(transport){
			    	t.fireEvent('commitchanges');
			    	t.root.reload();
				};
            }
        }
        */
        ],
        loader: new Ext.tree.TreeLoader({
            dataUrl: contextPath + '/basicApp/authManage/roleController.spr?action=_getRoleAuth',
            uiProviders:{
                'col': Ext.tree.ColumnTreeNodeUI
            }
        }),
        root: new Ext.tree.AsyncTreeNode({
        	id:''
        })
    });
    
    var rec = Ext.data.Record.create([
    	{name: 'authfrom', type: 'string'},
        {name: 'authto', type: 'string'}
    ]);
		
    tree.on('iconclick', function(node){
    	if(!node.isLeaf())return;
        var t = node.getOwnerTree();
        var a = node.attributes;
        var cols = t.columns;

        if (typeof((_getMainFrame().Ext).getCmp("auth-win")) == 'undefined'){ 
		    var cm = new Ext.grid.ColumnModel([
		    	{
		           header: "从",
		           dataIndex: 'authfrom',
		           width: 120,
		           editor: new Ext.form.TextField({
		           })
		        },{
		           header: "至",
		           dataIndex: 'authto',
		           width: 120,
		           editor: new Ext.form.TextField({
		           })
		        }
		    ]); 

		    var store = new Ext.data.Store({
		        reader: new Ext.data.JsonReader({
		        		root: 'data',
		                totalProperty: 'totalSize'
		           }, rec),
		        remoteSort: false
		    });
		    
		    store.on('update', function(s,r,o){
		    	if(s.getCount()==s.indexOf(r)+1){
			        var p = new rec({
			            authfrom: '',
			            authto: ''
			        });
			        s.insert(s.getCount(), p);
		    	}
		    });

		    new (_getMainFrame().Ext).Window({
		        id : 'auth-win',
		        title : '数据授权窗口',
		        iconCls: 'icon-form',
		        width : 550,
		        height : 390,
		        minWidth: 450,
		        minHeight: 300, 
		        layout: 'fit',
		        plain:true,
		        border:false, 
		        buttonAlign : 'center',
		        //autoScroll: true,
		        //minimizable: true,
		        maximizable: true,
		        closeAction:'hide',
		        modal : true,
		        items: {
			        xtype: 'form',
			        labelAlign: 'right',
			        border : false,
			        labelWidth : 70,
			        bodyStyle : 'padding: 5px 5px',
			        layout: 'column',
			        frame : true,
			        autoScroll:true,
				    items: [{
				    	columnWidth: 0.5,
				        layout: 'form',
				        items:{
				        	fieldLabel : '数据对象',
				         	xtype: 'textfield',
				         	readOnly:true,
				            id : 'objectName'
				        }
				    },{
				       	columnWidth: 0.5,
				        layout: 'form',
				        items:{
				        	fieldLabel : '授权字段',
				            xtype: 'textfield',
				            readOnly:true,
				            id : 'fieldName'
				        }
				    },{
				    	columnWidth: 1,
			            layout: 'form',
			            items:{
			                xtype: 'hidden',
			         		id : 'fieldId'
			     		}
			        },{
				        columnWidth: 1,
				        layout: 'form',
				        items:{
				        	fieldLabel : '输入值',
				            xtype:'editorgrid',
				            store: store,
				            cm: cm,
				            enableHdMenu:false,
				            anchor: '100% 100%',
				            iconCls:'icon-grid',
				            //border:false,
				            //autoExpandColumn:'authfrom',
				            title:'输入值',
				            frame:true,
				            clicksToEdit:1,
				            autoScroll:true,
				            stripeRows : true
				        }                    
			        }]            
			    },
		        buttons: [{
		            text: '确定',
		            handler: function(){
		            	var w = (_getMainFrame().Ext).getCmp("auth-win");
						var f = w.findByType('form')[0];
						var g = w.findByType('editorgrid')[0];
		            	var s = g.getStore();
		            	
		            	var value = "";
		            	for (var i=0;i<s.getCount();i++){
		            		var rec = s.getAt(i);
		            		if(rec.data.authfrom && rec.data.authfrom!="")value += rec.data.authfrom;
		            		if(rec.data.authto && rec.data.authto!="")value += "-"+rec.data.authto;
		            		if(rec.data.authfrom && rec.data.authfrom!="")value += ",";
		            	}
		            	
		            	var t = Ext.getCmp("auth-tree");
		            	var nodeid = f.getComponent(2).getComponent('fieldId').getValue();
		            	var node = t.getNodeById(nodeid);
		            	node.attributes.VALUE = value;
		            	node.ui.columnNode[2].innerHTML = value;   //node.set('VALUE',value);  需要优化huanghu
		            	t.fireEvent('updatenode', node);
		            	
		            	w.hide();
		            }                    
		        },{
		            text: '取消',
		            handler: function(){
		                (_getMainFrame().Ext).getCmp("auth-win").hide();
		            }
		        }],            
		        listeners: {
		            'hide': {
		                fn: function(){
		                }
		            },
		            'resize': {
		                fn: function(){
			            	var w = (_getMainFrame().Ext).getCmp("auth-win");
							var g = w.findByType('editorgrid')[0];
		                	g.setHeight(w.getInnerHeight()-60);
		                }
		            }
		        }
		    });
		    
		    _pushCmps((_getMainFrame().Ext).getCmp("auth-win"));
    	}

        var w = (_getMainFrame().Ext).getCmp("auth-win");
		var f = w.findByType('form')[0];
		var g = w.findByType('editorgrid')[0];
        
        w.show();
		f.getComponent(0).getComponent('objectName').setValue(node.parentNode.id);
		f.getComponent(1).getComponent('fieldName').setValue(node.attributes.FIELD);
		f.getComponent(2).getComponent('fieldId').setValue(node.id);
        //f.getForm().setValues({objectName:'aa',fieldName:'bb'});

        g.getStore().removeAll();

        var value = node.attributes.VALUE;
        if(value!=""){
	        var jsonAry = packSJson(value);
	        g.stopEditing();
	        for (var i=jsonAry.length-1;i>=0;i--){
		        var p = new rec(jsonAry[i]);
		        g.getStore().insert(0, p);
	        }
	        
        }else{
	        g.stopEditing();
	        var p = new rec({
	            authfrom: '',
	            authto: ''
	        });
	        g.getStore().insert(0, p);
        }
        g.startEditing(0, 0);
    });	
    
	function winCallBack(jsonArrayData)
	{
		var t = Ext.getCmp("auth-tree");
		for (var i=0;i<jsonArrayData.length;i++){
			var child = [];
			if (t.getNodeById(jsonArrayData[i].OBJCT))continue;
			for (var j=1;j<6;j++){
				var field = eval("jsonArrayData[i].FIEL"+j).replace(/^\s+|\s+$/g,'');
				
				if (field!=""){
					child.push({
				        leaf : true,
					    OBJCT:jsonArrayData[i].OBJCT,
					    FIELD:field,
					    TTEXT:'',
					    VALUE:'',
				        uiProvider:Ext.tree.ColumnTreeNodeUI,
					    cls:'master-task',
					    iconCls:'icon-user-edit'
			    	});
				}
			}
		    var node = new Ext.tree.AsyncTreeNode({
		        leaf : false,
		        id:jsonArrayData[i].OBJCT,
			    OBJCT:jsonArrayData[i].OBJCT,
			    FIELD:jsonArrayData[i].OBJCT,
			    TTEXT:jsonArrayData[i].TTEXT,
			    VALUE:'',
		        uiProvider:Ext.tree.ColumnTreeNodeUI,
		        checked:false,
			    cls:'master-task',
			    iconCls:'task-folder',
			    expanded:true,
			    children:child
		    });
			t.getRootNode().appendChild(node);
			
			t.fireEvent('addnode', node);
			t.fireEvent('updatenode', node);
		}
	}
	
	function packSJson(s){
		var chars = s.split(",");
		var jsonary = [];
		for (var i=0;i<chars.length;i++){
			var fields = chars[i].split("-");
			jsonary.push({authfrom:fields[0],authto:fields[1]});
		}
		return jsonary;
	}

    var viewport = new Ext.Viewport({
        layout:'border',
        items:tree
    });

    viewport.doLayout();
});
</script>