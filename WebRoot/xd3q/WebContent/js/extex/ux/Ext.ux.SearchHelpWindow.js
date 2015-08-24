/**  
 * Ext JS Library 2.0 extend  
 * Version : 1.1  
 * Author : huanghu
 */  
Ext.SearchHelpWindow = Ext.extend(Ext.Window,  {
    /**  
     * @cfg {String} type  
     * 搜索帮助名称 
     *   
     */  
	shlpName : '',
    /**  
     * @cfg {function} callback  
     * 回调方法
     *   
     */  	
	callBack : null,
	/**
     * 默认查询条件
     */
    defaultCondition : '',
    /**  
     * @cfg {String} pageSize  
     * 搜索帮助的页大小
     *   
     */      
    pageSize:10,
    /**  
     * @cfg {String} about  
     * 关于搜索帮助对话框  
     */      
    about : "about searchhelpwindow",

    title : '搜索帮助',
    layout : 'fit',
    width : 550,
    height : 440,
    minWidth: 550,
    minHeight: 410,
    minimizable: false,
    maximizable: true,
    resizable: true,
    border : false,
    constrainHeader: true,
    collapsible : true,
    animCollapse: Ext.isIE,
    closeAction:'hide',
    modal : true,
    plain: false,

    initComponent : function(){   
        Ext.SearchHelpWindow.superclass.initComponent.call(this);   
        
    	var url = contextPath+'/searchHelpQueryController.spr?action=createShlpGrid&shlpName='+this.shlpName;
    	var conn = Ext.lib.Ajax.getConnectionObject().conn; 
		conn.open("GET", url, false); 
		conn.send({
                shlpName: this.shlpName
            }); 
        var o = Ext.util.JSON.decode(conn.responseText);
    	if (o.success) {
			var global = Ext.util.JSON.decode(o.global);
			this.shlpName = global.shlpName;
			this.dialogType = global.dialogType;

    		this.queryField = Ext.util.JSON.decode(o.queryField); 
            this.record = Ext.util.JSON.decode(o.record);
            
			var columns = Ext.util.JSON.decode(o.columns);
			this.columns = [];
	        this.columns.push(new Ext.grid.CheckboxSelectionModel());
			for (var i=1;i<=columns.columns.size();i++)this.columns.push(columns.columns[i-1]);
    	}
    }, 
    initEvents : function(){
        Ext.SearchHelpWindow.superclass.initEvents.call(this);
        if(this.resizable){
            this.on('resize', function(r, w, h){
            	var cw = w-this.width;
            	var ch = h-this.height;
            	if (cw!=0) this.grid.setWidth(w-50);
            	if (ch!=0) this.grid.setHeight(h-210);
            	this.width = w;
            	this.height = h;
            }, this);
        }        
    },
    onRender : function(ct, position){   
        Ext.SearchHelpWindow.superclass.onRender.call(this, ct, position);  
        
		var url = contextPath+'/searchHelpQueryController.spr?action=queryShlpGrid&shlpName='+this.shlpName+'&defaultCondition='+this.defaultCondition;
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel(this.columns);
		var fields = Ext.data.Record.create(this.record);
		
		var store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({url:url}),
			reader: new Ext.data.JsonReader({
    					root: 'data',
    					totalProperty: 'totalSize'
    				},fields),
            remoteSort: true
		});

		var pagingBar = new Ext.PagingToolbar({
        	displayInfo:true,
        	store:store,
        	pageSize:this.pageSize
    	});
    	
		var grid = new Ext.grid.GridPanel({
			store:store,
			sm:sm,
			cm:cm,
			border:true,
			loadMask:{hideOnReady :false,msg:'数据载入中...'},
			autoScroll:true,
			width:this.width-50,
			height:this.height-210,
			viewConfig: {
		    },
		    iconCls:'icon-grid',
			bbar:pagingBar
		});
		
		var btnQuery = new Ext.Button({
            text:'查询',
            width:100,
            iconCls :'icon-table-view',
            handler: function(){
            	//var queryJson = this.findParentByType('form').form.getValues();
            	//Ext.apply(queryJson, {start:0, limit:grid.getBottomToolbar().pageSize});
            	//grid.getStore().reload({params:queryJson});			       
            	var f = this.findParentByType('form');
				var g = f.findByType('grid')[0];
            	var queryJson = f.form.getValues();
    			var paraUrl = store.url+"&"+Ext.urlEncode(queryJson);
    			//把查询条件带到proxy中，使其在翻页是能保持原来的查询条件
    			//by zhangchzh
    			g.getStore().proxy = new Ext.data.HttpProxy({url:paraUrl});
    			g.getStore().load({params:{start:0, limit:g.getBottomToolbar().pageSize},arg:[]});
            }
        });
		var btnClear = new Ext.Button({
            text:'清空',
            width:100,
            iconCls :'icon-table-clear',
            handler: function(){
            	var formPanel = this.findParentByType('form');
            	formPanel.form.reset();
            	var shlps = formPanel.findByType('searchhelpfield');
            	for (var i=0;i<shlps.size();i++)shlps[i].reset();
            }
        }); 	    		

        var itemary = [];
		for (var i=0;i<this.queryField.count;i++)itemary.push(this.queryField[i]);
		itemary.push({columnWidth:0.5,layout: 'form',style:'text-align:right;margin-right:10px;margin-top:10px;margin-bottom:10px;',items:btnQuery});
		itemary.push({columnWidth:0.5,layout: 'form',style:'text-align:left;margin-left:10px;margin-top:10px;margin-bottom:10px;',items:btnClear});
		itemary.push({columnWidth:1,layout: 'form',items:grid});
		
        var form = new Ext.form.FormPanel({
            labelWidth:1,
            border : false,
            bodyStyle : 'padding: 5px 5px',
            layout: 'column',
            frame : true,
            autoScroll:true,
		    defaults: {
		        msgTarget: 'side',
		        labelSeparator: ' '
		    },
		    layoutConfig: {
		        anchor:'90%'
		    },
            buttons: [{
	            text: '确定',
	            width:100,
	            iconCls :'icon-table-checked2',
	            handler: function(){
			    	var recs = grid.getSelectionModel().getSelections();
			    	var jsonArrayData = [];
			        for(var i=0;i<recs.size();i++)jsonArrayData.push(recs[i].data);
			        var win = this.findParentByType('searchhelpwindow');
			        win.hide();
			    	if (win.callBack)win.callBack(jsonArrayData);
	            }                   
            }],
            items: itemary
   		});	
        this.grid = grid;
        this.form = form;
        
   		this.add(form);
    },
    show : function(animateTarget, cb, scope){
    	Ext.SearchHelpWindow.superclass.show.call(this, animateTarget, cb, scope);
    	
    	var url = contextPath+'/searchHelpQueryController.spr?action=queryShlpGrid&shlpName='+this.shlpName+'&defaultCondition='+this.defaultCondition;
    	this.grid.getStore().url = url;
    	this.grid.getStore().proxy = new Ext.data.HttpProxy({url:url});
    	if (this.dialogType=='D') this.grid.getStore().load({params:{start:0, limit:this.grid.getBottomToolbar().pageSize}});
    },
    hide : function(animateTarget, cb, scope){
    	Ext.SearchHelpWindow.superclass.hide.call(this, animateTarget, cb, scope);
		this.form.form.reset();
    	var shlps = this.form.findByType('searchhelpfield');
    	for (var i=0;i<shlps.size();i++)shlps[i].reset();
    }
});   
Ext.reg('searchhelpwindow', Ext.SearchHelpWindow);