<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<html>
<head>
  <title>index</title>
</head>
<body>
</body>
</html>
<script>
var tree;
var vwin;
var shlpWin;
var gridrec;
//YHSYSVARIABLE
var shName= "";

Ext.onReady(function(){
    tree = new Ext.tree.ColumnTree({
    	id:'auth-tree',
    	region:'center',
        rootVisible:false,
        autoScroll:true,
        title: '数据权限',
        objectName:'DataAUTHXobjectValue',
        editable:${isToolBar},
        columns:[{
            header:'用户主记录中的权限对象',
            width:200,
            dataIndex:'dataAUTHField'
        },{
            header:'用户主记录中的权限描述',
            width:300,
            dataIndex:'tText',
            headeralign: 'center'
        },{
            header:'值文本',
            width:500,
            dataIndex:'dataAUTHText'
        }],
        tbar:[{
            text:'分配数据对象',
            iconCls:'icon-add',
            handler : function(){
            	if (!shlpWin){ 
					shlpWin = new (_getMainFrame().Ext).SearchHelpWindow({
						shlpName : 'YHDATAAUTHXOBJ',
						callBack : winCallBack
					});
					_pushCmps(shlpWin);
            	};
            	shlpWin.show();
            },
            hidden:!${isToolBar}
        },'-',{
            text:'删除数据对象',
            iconCls:'icon-delete',
            handler : function(){
            	var t = Ext.getCmp("auth-tree");
				var checkedNodes = t.getChecked();
                if(checkedNodes.length>0)
                {
					_getMainFrame().Ext.MessageBox.show({
						title:'系统提示',
						msg: '您选择了【删除数据对象】操作，是否继续执行此操作？',
						buttons: {yes:'确定', no:'取消'},
						icon: Ext.MessageBox.QUESTION,
						fn:function(buttonid){
							if (buttonid == 'yes'){
								for(var i=0;i<checkedNodes.length;i++){
									checkedNodes[i].remove();
									t.fireEvent('removenode', checkedNodes[i], false);
								}
							}
						}
					});
                }
                else
                {
                	_getMainFrame().showInfo('请选择要删除的数据权限信息！');
                }
            },
            hidden:!${isToolBar}
        }],
        loader: new Ext.tree.TreeLoader({
            dataUrl: contextPath + '/basicApp/authManage/roleController.spr?action=_getRoleAuth',
            uiProviders:{
                'col': Ext.tree.ColumnTreeNodeUI
            }
        }),
        root: new Ext.tree.AsyncTreeNode({
        	id:'${roleId}'
        })
    });
    
    var rec = Ext.data.Record.create([
    	{name: 'authfrom', type: 'string'},
    	{name: 'authfromtext', type: 'string'},
    	{name: 'authto', type: 'string'},
        {name: 'authtotext', type: 'string'}
    ]);
		
    tree.on('iconclick', function(node){
    	if(!node.isLeaf())return;
    	
        var t = node.getOwnerTree();
        var a = node.attributes;
        var cols = t.columns;

        if (!vwin){ 
		    var cm = new Ext.grid.ColumnModel([
		    	{
		           header: "从",
		           dataIndex: 'authfrom',
		           width: 120,
		           editor: new Ext.form.SearchHelpField({
			       	   shlpType:'field',
			           hiddenName:'fshlpid',
			           id : 'fshlp',
			           callBack:function(data){
						    gridrec.set('authfrom',this.getRawValue());
						    if(shName && shName=="YHSYSVARIABLE"){
						     gridrec.set('authfromtext',data.MEMO);
						    }else{
							gridrec.set('authfromtext',this.getRawValue());}
			           }
			       })
		        },{
		           header: "从文本",
		           dataIndex: 'authfromtext'
		        },{
		           header: "至",
		           dataIndex: 'authto',
		           width: 120,
		           editor:  new Ext.form.SearchHelpField({
				       shlpType:'field',
				       hiddenName:'tshlpid',
				       id : 'tshlp',
			           callBack:function(data){
					    	gridrec.set('authto',this.getRawValue());
						    if(shName && shName=="YHSYSVARIABLE"){
							     gridrec.set('authtotext',data.MEMO);
							    }else{
								gridrec.set('authtotext',this.getRawValue());}
			           }
				   })
		        },{
		           header: "至文本",
		           dataIndex: 'authtotext'
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
			            authto: '',
			            authfromtext: '',
			            authtotext: ''
			        });
			        s.insert(s.getCount(), p);
		    	}
		    });

		    vwin = new (_getMainFrame().Ext).Window({
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
				            id : 'objectNames'
				        }
				    },{
				       	columnWidth: 0.5,
				        layout: 'form',
				        items:{
				        	fieldLabel : '授权字段',
				            xtype: 'textfield',
				            readOnly:true,
				            id : 'fieldNames'
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
				            stripeRows : true,
				            listeners: {
				                beforeedit : function(e) {
				                    var row = e.row;
				                    gridrec = this.store.getAt(row);
				                }
				            }
				        }                    
			        }]            
			    },
		        buttons: [{
		            text: '确定',
		            handler: function(){
						var f = vwin.findByType('form')[0];
						var g = vwin.findByType('editorgrid')[0];
		            	var s = g.getStore();
		            	
		            	var value = "";
		            	var text = "";
		            	for (var i=0;i<s.getCount();i++){
		            		var rec = s.getAt(i);
		            		if(rec.data.authfrom && rec.data.authfrom!="")value += rec.data.authfrom;
		            		if(rec.data.authto && rec.data.authto!="")value += "-"+rec.data.authto;
		            		if(rec.data.authfrom && rec.data.authfrom!="")value += ",";
		            		
		            		if(rec.data.authfromtext && rec.data.authfromtext!="")text += rec.data.authfromtext;
		            		if(rec.data.authtotext && rec.data.authtotext!="")text += "-"+rec.data.authtotext;
		            		if(rec.data.authfromtext && rec.data.authfromtext!="")text += ",";
		            	}

		            	//alert("text:" + text +",value:" + value);
		            	var t = Ext.getCmp("auth-tree");
		            	var nodeid = f.getComponent(2).getComponent('fieldId').getValue();
		            	var node = t.getNodeById(nodeid);
		            	node.attributes.value = value;
		            	node.attributes.entityAttributes.value = value;
		            	//node.attributes.entityAttributes.dataAUTHText = (text != '') ? text : value;
		            	//node.ui.columnNode[2].innerHTML = (text != '') ? text : value; 
		            	node.attributes.entityAttributes.dataAUTHText =  (text != '') ? text : value ;
		            	node.ui.columnNode[2].innerHTML = value;   //node.set('VALUE',value);  需要优化huanghu
		            	t.fireEvent('updatenode', node, false);
		            	
		            	vwin.hide();
		            }                    
		        },{
		            text: '取消',
		            handler: function(){
		                vwin.hide();
		            }
		        }],            
		        listeners: {
		            'hide': {
		                fn: function(){
		                }
		            },
		            'resize': {
		                fn: function(){
							var g = vwin.findByType('editorgrid')[0];
		                	g.setHeight(vwin.getInnerHeight()-60);
		                }
		            }
		        }
		    });
		    
		    //_pushCmps(vwin);
    	}
    	
		var f = vwin.findByType('form')[0];
		var g = vwin.findByType('editorgrid')[0];
		f.getComponent(0).getComponent('objectNames').setValue(node.parentNode.id);
		f.getComponent(1).getComponent('fieldNames').setValue(node.attributes.dataAUTHField);
		f.getComponent(2).getComponent('fieldId').setValue(node.id);
        g.getStore().removeAll();

        var value = node.attributes.value;
        var text = node.attributes.entityAttributes.dataAUTHText;
        if(value!=""){
	        var jsonAry = packSJson(value,text);
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
        //g.startEditing(0, 0);
        
		Ext.Ajax.request({
			//加入web上下文路径引用，contextPath在commons.jsp中声明
	    	url : contextPath + '/basicApp/authManage/roleController.spr?action=_getAuthField',
	        params : {dataAUTHField:node.attributes.dataAUTHField},
	        success : function(xhr){
	        	var o = Ext.util.JSON.decode(xhr.responseText);
	        	//YHSYSVARIABLE
	        	shName=o.shlpName.trim();
	        	//alert(o.shlpName.trim() + ";" + o.shlpField.trim() + ";" + o.shlpDisplayField.trim());
	        	Ext.getCmp('fshlp').changeShlp(o.shlpName.trim(), o.shlpField.trim(), o.shlpDisplayField.trim(), '');
	        	Ext.getCmp('tshlp').changeShlp(o.shlpName.trim(), o.shlpField.trim(), o.shlpDisplayField.trim(), '');
	        	vwin.show();
	        },
	        scope : this
	    });
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
				        leaf:true,
				        uiProvider:Ext.tree.ColumnTreeNodeUI,
					    cls:'master-task',
					    iconCls:'icon-user-edit',
					    dataAuthxObjectName:jsonArrayData[i].OBJCT,
					    dataAUTHField:field,
					    tText:'',
					    value:'',
					    entityAttributes:{dataAuthxObjectName:jsonArrayData[i].OBJCT,dataAUTHField:field,value:''}
			    	});
				}
			}
		    var node = new Ext.tree.AsyncTreeNode({
		        leaf:false,
		        id:jsonArrayData[i].OBJCT,
			    dataAuthxObjectName:jsonArrayData[i].OBJCT,
			    dataAUTHField:jsonArrayData[i].OBJCT,
			    tText:jsonArrayData[i].TTEXT,
			    value:'',
		        uiProvider:Ext.tree.ColumnTreeNodeUI,
		        checked:false,
			    cls:'master-task',
			    iconCls:'task-folder',
			    expanded:true,
			    children:child
		    });
			t.getRootNode().appendChild(node);
			
			t.fireEvent('addnode', node, false);
		}
	}
	
	function packSJson(v,t){
		//alert("V:" + v + ",T:" + t);
		var vs = v.split(",");
		var ts = t.split(",");
		var jsonary = [];
		for (var i=0;i<vs.length;i++){
			var fvs="";
			var fts="";
			var fts0="";
			var fts1="";
			var fvs0="";
			var fvs1="";

			if(vs[i])
				fvs = vs[i].split("-")
			if(ts[i])
				fts = ts[i].split("-")
			if(fts){
				fts0 = fts[0];
				fts1 = fts[1];
			}
			if(fvs){
				fvs0 = fvs[0];
				fvs1 = fvs[1];
			}
			jsonary.push({authfrom:fvs0,authto:fvs1,authfromtext:fts0,authtotext:fts1});
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