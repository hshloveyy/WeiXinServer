<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form action="" name="mainForm" id="mainForm">
</form>
</body>
<script type="text/javascript" defer="defer">
	var thenStateTypes = ${thenStateTypes};
	var taskActorCondition = ${taskActorCondition};
	
	var combo = new Ext.form.ComboBox({
	  store: new Ext.data.SimpleStore({
	      fields: ['id', 'text'],
	      data : ${boproperties}
	  }),
	  valueField :"id",
	  displayField:'text',
	  //allowBlank:false,
	  hiddenName:'boProperty', 
	  typeAhead: true,
	  editable:true,
	  mode: 'local',
	  triggerAction: 'all',
	  selectOnFocus:true,
	  anchor:'90%'
	});
	
	var lcombo = new Ext.form.ComboBox({
	  store: new Ext.data.SimpleStore({
	      fields: ['id', 'text'],
	      data : [[' ',' '],['(','('],['((','(('],['(((','((('],['((((','((((']]
	  }),
	  valueField :"id",
	  displayField:'text',
	  //allowBlank:false,
	  hiddenName:'lParenthesis', 
	  typeAhead: true,
	  editable:true,
	  mode: 'local',
	  triggerAction: 'all',
	  selectOnFocus:true,
	  anchor:'90%'
	});
	
	var rcombo = new Ext.form.ComboBox({
	  store: new Ext.data.SimpleStore({
	      fields: ['id', 'text'],
	      data : [[' ',' '],[')',')'],['))','))'],[')))',')))'],['))))','))))']]
	  }),
	  valueField :"id",
	  displayField:'text',
	  //allowBlank:false,
	  hiddenName:'rParenthesis', 
	  typeAhead: true,
	  editable:true,
	  mode: 'local',
	  triggerAction: 'all',
	  selectOnFocus:true,
	  anchor:'90%'
	});
		
	var operatorCombo = new Ext.form.ComboBox({
	  store: new Ext.data.SimpleStore({
	      fields: ['id', 'text'],
	      data : [['>','大于'],['==','等于'],['<','小于'],['>=','大于等于'],['<=','小于等于'],['!=','不等于']] 
	  }),
	  valueField :"id",
	  displayField:'text',
	  //allowBlank:false,
	  hiddenName:'operator', 
	  typeAhead: true,
	  editable:true,
	  mode: 'local',
	  triggerAction: 'all',
	  selectOnFocus:true,
	  anchor:'90%'
	});
	
	var connectorCombo = new Ext.form.ComboBox({
	  store: new Ext.data.SimpleStore({
	      fields: ['id', 'text'],
	      data : [['and','并且'],['or','或者']] 
	  }),
	  valueField :"id",
	  displayField:'text',
	  //allowBlank:false,
	  hiddenName:'connector', 
	  typeAhead: true,
	  editable:true,
	  mode: 'local',
	  triggerAction: 'all',
	  selectOnFocus:true,
	  anchor:'90%'
	});

	var rec = Ext.data.Record.create([
		{name: 'lParenthesis', type: 'string'},
	    {name: 'boProperty', type: 'string'},
	    {name: 'operator', type: 'string'},
	    {name: 'value', type: 'string'},
	    {name: 'rParenthesis', type: 'string'},
	    {name: 'connector', type: 'string'}
	]);	

	var cm = new Ext.grid.ColumnModel([
    	{
           header: "左括号",
           dataIndex: 'lParenthesis',
           editor: lcombo,
           fixed:true,
           width:50,
           renderer: Ext.util.Format.comboRenderer(lcombo)
        },{
           header: "业务对象属性",
           dataIndex: 'boProperty',
           editor: combo,
           fixed:true,
           width: 100,
           renderer: Ext.util.Format.comboRenderer(combo)
        },{
           header: "操作符",
           dataIndex: 'operator',
           editor: operatorCombo,
           fixed:true,
           width: 100,
           renderer: Ext.util.Format.comboRenderer(operatorCombo)
        },{
           header: "值",
           dataIndex: 'value',
           fixed:true,
           width: 100,
           editor: new Ext.form.TextField({
           })
        },{
           header: "右括号",
           dataIndex: 'rParenthesis',
           editor: rcombo,
           fixed:true,
           width: 50,
           renderer: Ext.util.Format.comboRenderer(rcombo)
        },{
           header: "连接符",
           dataIndex: 'connector',
           fixed:true,
           width: 50,
           editor: connectorCombo,
           renderer: Ext.util.Format.comboRenderer(connectorCombo)
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
		        lParenthesis: '',
		        boProperty: '',
		        operator: '',
		        value: '',
		        rParenthesis: '',
		        connector: ''
	        });
	        s.insert(s.getCount(), p);
    	}
    });
    
    var formPanel;
    formPanel = new Ext.form.FormPanel({
    	region:"center",
        labelAlign: 'right',
        border: false,
        labelWidth : 50,
        layout: 'column',
        frame : true,
        autoScroll:true,
	    items: [{
	    	columnWidth: 1,
            layout: 'form',
            items:{
	            xtype: 'hidden',
	            id : 'tskActCondId',
	            value: taskActorCondition.tskActCondId
     		}
        },{
	    	columnWidth: .15,
            layout: 'form',
            items:{
	        	text : '参与者条件',
	            xtype: 'label'
     		}
        },{
	    	columnWidth: .85,
	        layout: 'form',
	        items:{
	            xtype:'editorgrid',
	            store: store,
	            cm: cm,
	            enableHdMenu:true,
	            enableColumnMove:true,
	            enableColumnResize:true,
	            iconCls:'icon-grid',
	            height:150,
	            //frame:true,
	            clicksToEdit:1,
	            autoScroll:true,
	            stripeRows : true,
    			viewConfig: {
    				//forceFit: true,
    				autoFill: true
			    }
	        }
	    },{
	    	columnWidth: .15,
            layout: 'form',
            items:{
	        	text : '参与者类型',
	            xtype: 'label'
     		}
        },{
	       	columnWidth: .85,
	        layout: 'form',
	        items:{
	        	labelSeparator: '',
	            xtype: 'combo',
	            id : 'typeId',
	            store: new Ext.data.SimpleStore({
	                fields: ['id', 'text'],
	                data : ${thenStatementTypes}
	            }),
	            valueField :"id",
	            displayField:'text',
	            //allowBlank:false,
	            hiddenName:'owncert',
	            typeAhead: true,
	            editable:true,
	            mode: 'local',
	            triggerAction: 'all',
	            selectOnFocus:true,
	            anchor:'30%',
	            listeners: {
	                change : function(w,n,o) {
		           		if(thenStateTypes[n].valueFecthType=='1') Ext.getCmp('thenStatement1').setVisible(true);
		           		else Ext.getCmp('thenStatement2').setVisible(true);
	                },
	                select : function(c,r,i) {
	                	var v = c.getValue();
		           		if(thenStateTypes[v].valueFecthType=='1'){
		           			Ext.getCmp('thenStatement2').setVisible(false);
		           			Ext.getCmp('thenStatement1').setVisible(true);
		           			Ext.getCmp('thenStatement1').changeShlp(thenStateTypes[v].shlpName.trim(), thenStateTypes[v].shlpIdColumn, thenStateTypes[v].shlpTextColumn, '');
		           		}else{
		           			Ext.getCmp('thenStatement1').setVisible(false);
		           			Ext.getCmp('thenStatement2').setVisible(true);
		           			Ext.getCmp('thenStatement2').setValue(thenStateTypes[5].statement);
		           		}
	                }
	                
	            }
	        }
	    },{
	    	columnWidth: 1,
            layout: 'form',
            items:{
	        	text : '参与者',
	            xtype: 'label'
     		}
        },{
	    	columnWidth: 1,
            layout: 'form',
            items:{
            	labelSeparator: '',
	            xtype: 'searchhelpfield',
	            shlpType:'field',
	            hiddenName:'boMethodId',
	            hidden:true,
	            id : 'thenStatement1'
     		}
        },{
	    	columnWidth: 1,
            layout: 'form',
            items:{
            	labelSeparator: '',
	            xtype: 'textarea',
	            hidden:true,
	            id : 'thenStatement2',
	            width:'100%'
     		}
        }],
        buttons: [{
            text: '确定',
            handler: function(){
				var g = formPanel.findByType('editorgrid')[0];
            	var s = g.getStore();
            	
            	var value = "$"+"{";
            	var jsonvalue = "{'data':[";
            	
            	for (var i=0;i<s.getCount();i++){
            		var rec = s.getAt(i);
            		
            		var obj = rec.data; 
	            	for(var j in obj){
	            		if (obj[j]!="")value += obj[j] + " ";
	            	}
            		jsonvalue += Ext.util.JSON.encode(rec.data) + ",";
            	}
            	value = value + "}";
            	jsonvalue = jsonvalue.substring(0, jsonvalue.length-1) + "]}";

				var _thenStatement;
				var _thenStatementText;
               	var v = Ext.getCmp('typeId').getValue();
               	if(v==''){
               		_getMainFrame().showInfo('参与者类型不能为空！');
               		return;
               	}
           		if(thenStateTypes[v].valueFecthType=='1'){
           			_thenStatement = Ext.getCmp('thenStatement1').getValue();
           			_thenStatementText = Ext.getCmp('thenStatement1').getRawValue();
           		}else{
           			_thenStatement = Ext.getCmp('thenStatement2').getValue();
           			_thenStatementText = Ext.getCmp('thenStatement2').getValue();
           		}

				Ext.Ajax.request({
					//加入web上下文路径引用，contextPath在commons.jsp中声明
			    	url : contextPath+'/workflow/taskActorController.spr?action=saveOrUpdateTaskActorCondition',
			        params : {tskActCondId:taskActorCondition.tskActCondId, nodeId:taskActorCondition.nodeId, 
			        	processId:taskActorCondition.processId,extProcessId:taskActorCondition.extProcessId, ifStatement:value, ifStatementJson:jsonvalue, 
			        	typeId:Ext.getCmp('typeId').getValue(),type:thenStateTypes[Ext.getCmp('typeId').getValue()].valueFecthType,
			        	thenStatement:_thenStatement, thenStatementText:_thenStatementText},
			        success : function(xhr){
				    	_getMainFrame().showInfo('成功保存记录！');
						_getMainFrame().ExtModalWindowUtil.markUpdated();
						_getMainFrame().ExtModalWindowUtil.close();
			        },
			        scope : this
			    });
            	
            }                    
        },{
            text: '取消',
            handler: function(){
				_getMainFrame().ExtModalWindowUtil.markUpdated();
				_getMainFrame().ExtModalWindowUtil.close();
            }
        }]  
    });

	if(taskActorCondition.tskActCondId==''){
	   	var g = formPanel.findByType('editorgrid')[0];
	    g.getStore().removeAll();
	    g.stopEditing();
	   	var p = new rec({
	       lParenthesis: '',
	       boProperty: '',
	       operator: '',
	       value: '',
	       rParenthesis: '',
	       connector: ''
	   	});
	   	g.getStore().insert(0, p);
	    //g.startEditing(0, 0);
	}else{
		var typeId = taskActorCondition.thenStatementTypeId;
		formPanel.findById('typeId').setValue(typeId);
		if(thenStateTypes[typeId].valueFecthType=='1'){
			formPanel.findById('thenStatement2').setVisible(false);
			formPanel.findById('thenStatement1').setVisible(true);
			formPanel.findById('thenStatement1').changeShlp(thenStateTypes[typeId].shlpName.trim(), thenStateTypes[typeId].shlpIdColumn, thenStateTypes[typeId].shlpTextColumn, taskActorCondition.thenStatement);
			formPanel.findById('thenStatement1').setValue(taskActorCondition.thenStatement);
		}else{
			formPanel.findById('thenStatement1').setVisible(false);
			formPanel.findById('thenStatement2').setVisible(true);
			formPanel.findById('thenStatement2').setValue(taskActorCondition.thenStatement);
		}
		
	   	var g = formPanel.findByType('editorgrid')[0];
	   	g.getStore().removeAll();
	    g.stopEditing();
		g.getStore().loadData(Ext.util.JSON.decode(taskActorCondition.ifStatementJson));  
	    //g.startEditing(0, 0);
	}

	var viewport = new Ext.Viewport({
		layout:"border",
		items:[formPanel]
	});
	
	viewport.doLayout();
</script>
</html>