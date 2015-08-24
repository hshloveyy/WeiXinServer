/**  
 * Ext JS Library 2.0 extend  
 * Version : 1.1  
 * Author : huanghu
 */  
Ext.form.SearchHelpField = Ext.extend(Ext.form.TriggerField,  {
	/**
	 * 默认宽度
	 */
	width:203,
    /**  
     * @cfg {String} boName  
     * BO对象  
     *   
     */ 	
	boName : '',
    /**  
     * @cfg {String} boProperty  
     * BO对象属性
     *   
     */ 	
	boProperty : '',
    /**  
     * @cfg {String} type  
     * 搜索帮助名称 
     *   
     */  
	shlpName : '',
    /**  
     * @cfg {String} type  
     * 搜索帮助类型 grid/form/field
     *   
     */  
	shlpType : null,
    /**  
     * @cfg {String} nodeId  
     * 流程节点ID
     *   
     */ 
	nodeId : null,
    /**  
     * @cfg {function} callback  
     * 回调方法
     *   
     */  	
	callBack : null,
    /**  
     * @cfg {String} trigger1Class  
     * 下拉图标CSS  
     *   
     */  	
    trigger1Class:'x-form-search-trigger',
    /**  
     * @cfg {String} operationType  
     * 权限资源类型
     *   
     */
    operationType : null,
    /**  
     * @cfg {Array} linkPropertys  
     * 用于同一搜索帮助 
     *   
     */      
    seachHelpMapPara : [],
    /**  
     * @cfg {String} pageSize  
     * 搜索帮助的页大小
     *   
     */      
    pageSize:10,
    /**  
     * @cfg {String} hiddenName  
     * 保存真实数据的隐藏域名  
     */  
    hiddenName : null,
    /**  
     * @cfg {String} hiddenName  
     * 保存显示数据的隐藏域名  
     */  
    hiddenText : null,
    /**  
     * @cfg {String} valueField  
     * 用于保存真实数据的字段名  
     */  
    valueField : null,
    /**  
     * @cfg {String} displayField  
     * 用于显示数据的字段名  
     *   
     */  
    displayField : null,
    /**  
     * @cfg {String} value  
     * 默认值  
     */  
    value : null,
    /**  
     * @cfg {String} value  
     * 默认json值  
     */  
    jsonValue : {},
    /**  
     * @cfg {String} value  
     * 默认值  
     */  
    initValue1 : null,
    /**  
     * @cfg {String} displayValue  
     * 用于显示的默认值  
     */  
    displayValue : null,
    /**  
     * @cfg {Integer} listWidth  
     * 下拉框的宽度  
     */  
    winWidth : 550,   
    /**  
     * @cfg {Integer} listHeight  
     * 下拉框高度  
     */  
    winHeight : 410,   
    /**  
     * @cfg {String} linkBoUrl  
     * 数据协同URL  
     */  
    linkBoUrl : null,   
    /**  
     * @cfg {String} dialogType  
     * 对话类型
     */  
    dialogType : null,
    /**  
     * @cfg {Ext.FormPanel} form  
     * 搜索帮助对话框  
     */  
    form : null,
    /**  
     * @cfg {Ext.GridPanel} grid  
     * 搜索帮助对话框  
     */  
    grid : null,
    /**  
     * @cfg {Ext.Window} win  
     * 搜索帮助对话框  
     */  
    win : null,
    /**
     * 默认查询条件
     */
    defaultCondition : null,
    /**  
     * @cfg {String} about  
     * 关于搜索帮助对话框  
     */      
    about : "about searchhelp",
    /**
     * @cfg {Number} queryDelay The length of time in milliseconds to delay between the start of typing and sending the
     * query to filter the dropdown list (defaults to 500 if mode = 'remote' or 10 if mode = 'local')
     */
    queryDelay: 500,
    /**  
     * @cfg {Boolean} win  
     * 完成初始化
     */ 
    completeInit : false,
    /**
     * @cfg {Boolean} lazyInit True to not initialize the list for this combo until the field is focused. (defaults to false)
     */    
    lazyInit : true,
    /**
     * @cfg {Boolean} resizable True to add a resize handle to the bottom of the dropdown list (defaults to false)
     */
    resizable: true,  
    /**
     * @cfg {Boolean} readOnly
     */    
    readOnly : false,
    /**
     * @cfg {Boolean} editable False to prevent the user from typing text directly into the field, just like a
     * traditional select (defaults to true)
     */
    editable: true,
    /**
     * @cfg {Boolean} editable False to prevent the user from typing text directly into the field, just like a
     * traditional select (defaults to true)
     */    
    hideTrigger:false,
    /**  
     * @cfg {Object} baseParams  
     * 向后台传递的参数集合  
     */  
    baseParams : {},   
    /**  
     * @cfg {String/Object} autoCreate  
     * A DomHelper element spec, or true for a default element spec (defaults to  
     * {tag: "input", type: "text", size: "24", autocomplete: "off"})  
     */  
    defaultAutoCreate : {tag: "input", type: "text", size: "24", autocomplete: "off"},   
    
    initComponent : function(){   
        Ext.form.SearchHelpField.superclass.initComponent.call(this);   
        this.addEvents(
        	'beforeclick',
        	'select'
        );
        this.hideTrigger = (this.shlpType == 'grid') ? false : this.hideTrigger;
        this.triggerConfig = {
            tag:'span', cls:'x-form-twin-triggers', cn:[
            {tag: "img", src: Ext.BLANK_IMAGE_URL, cls: "x-form-trigger " + this.trigger1Class}
        ]};
        
        this.queryShlpGrid();
    }, 
    initEvents : function(){
        Ext.form.SearchHelpField.superclass.initEvents.call(this);
        
        this.keyMap = new Ext.KeyMap(this.el, {
			key:Ext.EventObject.F2,
	        fn: function(e){
	        	this.onTriggerClick();
	        },
            scope : this
        });
        this.keyMap1 = new Ext.KeyMap(this.el, {
			key:Ext.EventObject.ENTER,
	        fn: function(e){
	        	this.setValue(this.getRawValue(),null,true);
	        },
            scope : this
        });
        this.queryDelay = Math.max(this.queryDelay || 10, this.mode == 'local' ? 10 : 250);
        //this.dqTask = new Ext.util.DelayedTask(this.initQuery, this);
        if(this.editable)this.el.on("keyup", this.onKeyUp, this);
        this.el.on('dblclick', this.doDblClick, this);
        this.on('blur', this.doForce, this);
    },
    onRender : function(ct, position){   
        Ext.form.SearchHelpField.superclass.onRender.call(this, ct, position);   
        if(this.hiddenName){   
            this.hiddenField = this.el.insertSibling({tag:'input',    
                                                     type:'hidden', 
                                                     name: this.hiddenName,    
                                                     id: (this.hiddenId||this.hiddenName)},   
                    'before', true);   
            this.hiddenField.value =   
                this.hiddenValue !== undefined ? this.hiddenValue :   
                this.value !== undefined ? this.value : '';   

            this.hiddenText = this.el.insertSibling({tag:'input',    
                                                     type:'hidden', 
                                                     name: this.hiddenName+"_htext",    
                                                     id: (this.hiddenId||this.hiddenName)+"_htext"},   
                    'before', true);
            this.hiddenText.value =   
                this.displayValue !== undefined ? this.displayValue : '';

            Ext.form.SearchHelpField.superclass.setValue.call(this, this.displayValue ? this.displayValue : '');
            this.el.dom.removeAttribute('name');  
        }   
        
        if(Ext.isGecko)this.el.dom.setAttribute('autocomplete', 'off');   
        
        if(!this.lazyInit){
            this.initWin();
        }else{
            this.on('focus', this.initWin, this, {single: true});
        } 
        
        if(!this.editable){
            this.editable = true;
            this.setEditable(false);
        }
    },
    queryShlpGrid : function(){
    	if(this.boName==''&&this.boProperty==''&&this.shlpName==''){
    		if(this.trigger)this.trigger.hide();
    		return;
    	}else {
    		if(this.trigger)this.trigger.show();
    	}

		Ext.Ajax.request({
			//加入web上下文路径引用，contextPath在commons.jsp中声明
	    	url : contextPath+'/searchHelpQueryController.spr?action=createShlpGrid',
	        params : {boName:this.boName, boProperty:this.boProperty, shlpName:this.shlpName, 
	        	valueField: (this.valueField == undefined || this.valueField == null) ? "" : this.valueField,
	        	value:(this.value != null && (this.initValue1 == null || this.initValue1 == '')) ? this.value : "",
	        	nodeId:(this.nodeId == undefined || this.nodeId == null) ? "" : this.nodeId},
	        success : function(xhr){
		        var o = Ext.util.JSON.decode(xhr.responseText);
		    	if (o.success) {
		    		var seachHelpMapPara = Ext.util.JSON.decode(o.seachHelpMapPara);
					for (var i=0;i<seachHelpMapPara.length;i++){
						if (seachHelpMapPara[i].property == this.boProperty){
							if (this.valueField == undefined || this.valueField == null)this.valueField = seachHelpMapPara[i].id;
							if (this.displayField == undefined || this.displayField == null)this.displayField = seachHelpMapPara[i].text;					
						}
					}
					this.seachHelpMapPara = seachHelpMapPara;
					
					var global = Ext.util.JSON.decode(o.global);
					this.shlpName = global.shlpName;
					this.dialogType = global.dialogType;
					
					if(this.initValue1 != ''){
						this.setJsonValue(Ext.util.JSON.decode(this.initValue1));
					}else{
						if(o.shlpData){
							var shlpData = Ext.util.JSON.decode(o.shlpData);
							this.displayValue = shlpData[this.displayField || this.valueField];
							this.setJsonValue(shlpData);
						}
					}
					
					this.linkBoUrl = o.linkBoUrl;
		    		this.queryField = Ext.util.JSON.decode(o.queryField); 
		    		this.columns = Ext.util.JSON.decode(o.columns);
		            this.record = Ext.util.JSON.decode(o.record);
		            
					this.operationType = o.operationType.operationType;
					if (this.operationType == '0') {
						this.hidden = true;
						this.el.hide();
						this.trigger.setDisplayed(false);
					}
					//else if (this.operationType == '1') this.setEditable(true);
					else if (this.operationType == '2') this.setEditable(false);
					else if (this.operationType == '3') this.allowBlank = false;
					this.completeInit = true;
		    	}
	        },
	        scope : this
	    });
    },
    changeShlp : function(shlpName, valueField, displayField, value){
    	this.setValue('');
        if(this.grid){
        	Ext.destroy(this.grid);
        	this.grid = null;
        }
        if(this.form){
        	Ext.destroy(this.form);
        	this.form = null;
        }
        if(this.win){
        	Ext.destroy(this.win);
        	this.win = null;
        }
        
        this.shlpName = shlpName;
    	this.valueField = valueField;
    	this.displayField = displayField;
    	this.value = value;
    	
        this.queryShlpGrid();
    },
    initWin : function(){
    	if(this.boName==''&&this.boProperty==''&&this.shlpName==''){
    		if(this.trigger)this.trigger.hide();
    		return;
    	}else {
    		if(this.trigger)this.trigger.show();
    	}
    	if(!this.win){
			var url = contextPath+'/searchHelpQueryController.spr?action=queryShlpGrid&shlpName='+this.shlpName+'&defaultCondition='+this.defaultCondition;
			var cm = new Ext.grid.ColumnModel(this.columns);
			var fields = Ext.data.Record.create(this.record);
			
			var store = new Ext.data.Store({
				url:url,
    			proxy : new Ext.data.HttpProxy({url:url}),
    			reader: new Ext.data.JsonReader({
        					root: 'data',
        					totalProperty: 'totalSize'
        				},fields),
	            remoteSort: true
			});
			var pagingBar = new (_getMainFrame().Ext).PagingToolbar({
	        	displayInfo:true,
	        	store:store,
	        	pageSize:this.pageSize
	    	});
	    	
	    	_pushCmps(pagingBar);
	    	
			var btnQuery = {
				xtype:'button',
	            text:this.txtQuery,
	            width:100,
	            iconCls :'icon-table-view',
	            handler: function(){
					var f = this.findParentByType('form');
					var g = f.findByType('grid')[0];
	            	var queryJson = f.form.getValues();
	            	
	    			var paraUrl = store.url+"&"+commonUrlEncode(queryJson);
	    			//把查询条件带到proxy中，使其在翻页是能保持原来的查询条件
	    			//by zhangchzh
	    			g.getStore().proxy = new Ext.data.HttpProxy({url:paraUrl});
	    			g.getStore().load({params:{start:0, limit:g.getBottomToolbar().pageSize},arg:[]});
	            }
	        };
			var btnClear = {
				xtype:'button',
	            text:this.txtClean,
	            width:100,
	            iconCls :'icon-table-clear',
	            handler: function(){
	            	var f = this.findParentByType('form');
	            	f.form.reset();
	            	var shlps = f.findByType('searchhelpfield');
	            	for (var i=0;i<shlps.length;i++)shlps[i].reset();
	            }
	        }; 	    		

            var itemary = [];
   			for (var i=0;i<this.queryField.count;i++)itemary.push(this.queryField[i]);
   			itemary.push({columnWidth:0.5,layout: 'form',style:'text-align:right;margin-right:30px;margin-top:10px;margin-bottom:10px;',items:btnQuery});
   			itemary.push({columnWidth:0.5,layout: 'form',style:'text-align:left;margin-left:30px;margin-top:10px;margin-bottom:10px;',items:btnClear});
   			itemary.push({columnWidth:1,layout: 'form',items:{
   					xtype: 'grid',
					store:store,
					cm:cm,
	    			border:true,
	    			loadMask:{hideOnReady :false,msg:this.txtDataOnload},
	    			autoScroll:true,
	    			height:this.winHeight-180,
	    			width:this.winWidth-50,
	    			viewConfig: {
	    				forceFit: true,
	    				autoFill: true
				    },
				    bbar: pagingBar,
				    iconCls:'icon-grid'
				}
			});
   			
       		var about = this.about;
	        this.win = new (_getMainFrame().Ext).Window({
	            title: this.txtTitle,
	            layout: 'fit',
	            width: this.winWidth,
	            height: this.winHeight,
	            minWidth: this.winWidth,
	            minHeight: this.winHeight,
	            minimizable: false,
	            maximizable: this.resizable,
	            resizable:this.resizable,
	            border : false,
	            constrainHeader: true,
	            animCollapse: Ext.isIE,
	            closeAction:'hide',
	            modal : true,
	            plain: false,
                tools:[{
                    id:'help',
                    on:{
                        click: function(){
                        	showQues(about);
                        }
                    }
                }],
                collapsible : true,
	            items: {
	            	xtype: 'form',
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
		            items: itemary
	       		}
	        });
	        
	        _pushCmps(this.win);
	        
        	this.form = this.win.findByType('form')[0];
			this.grid = this.form.findByType('grid')[0];
			this.grid.on('dblclick',this.select,this);
			this.win.on('hide',this.resetForm,this);
			
	        if(this.resizable){
	            this.win.on('resize', function(r, w, h){
	            	var cw = w-this.winWidth;
	            	var ch = h-this.winHeight;
	            	if (cw!=0) this.grid.setWidth(w-50);
	            	if (ch!=0) this.grid.setHeight(h-180);
	            	this.winWidth = w;
	            	this.winHeight = h;
	            }, this);
	        } 
        }
    },
    onTriggerClick : function(){
    	if(this.fireEvent('beforeclick', this) !== false){
	    	this.el.blur();
	        if(this.disabled || !this.editable || !this.completeInit) return;
	
	        if (!this.win) this.initWin();
	        if(this.win){
	        	this.win.show();
	        	var url = contextPath+'/searchHelpQueryController.spr?action=queryShlpGrid&shlpName='+this.shlpName+'&defaultCondition='+this.defaultCondition;
	        	this.grid.getStore().url = url;
	        	this.grid.getStore().proxy = new Ext.data.HttpProxy({url:url});
	        	if (this.dialogType=='D') this.grid.getStore().load({params:{start:0, limit:this.grid.getBottomToolbar().pageSize}});
	        }
    	}
    },
    onFocus: function(e){
        Ext.form.SearchHelpField.superclass.onFocus.call(this, e);
        this.startValue = this.getValue();
        if(this.win && this.editable && this.completeInit && !e.isSpecialKey()) this.trigger.show();
    },
    onEnable: function(){
        Ext.form.SearchHelpField.superclass.onEnable.apply(this, arguments);
        if(this.hiddenField){
            this.hiddenField.disabled = false;
        }
    },
    onDisable: function(){
        Ext.form.SearchHelpField.superclass.onDisable.apply(this, arguments);
        if(this.hiddenField){
            this.hiddenField.disabled = true;
        }
    },
    onKeyUp : function(e){
        if(this.editable && !e.isSpecialKey()){
            this.lastKey = e.getKey();
            //this.dqTask.delay(this.queryDelay);
        }
    },
    beforeDestroy : function(){
        if(this.rendered){
	        if(this.grid)Ext.destroy(this.grid);   
	        if(this.form)Ext.destroy(this.form);  
	        if(this.win)Ext.destroy(this.win);   
             if(this.tools){
                for(var k in this.tools){
                      Ext.destroy(this.tools[k]);
                }
             }

             if(this.header && this.headerAsText){
                var s;
                if( s=this.header.child('span')) s.remove();
                this.header.update('');
             }

             Ext.each(['iframe','shim','header','topToolbar','bottomToolbar','footer','loadMask','body','bwrap'],
                function(elName){
                  if(this[elName]){
                    if(typeof this[elName].destroy == 'function'){
                         this[elName].destroy();
                    } else { Ext.destroy(this[elName]); }

                    this[elName] = null;
                    delete this[elName];
                  }
             },this);
        }
		Ext.form.SearchHelpField.superclass.beforeDestroy.call(this);
    },    
    setEditable : function(value){
    	Ext.form.SearchHelpField.superclass.setEditable.call(this,value);
        if(value == this.editable){
            return;
        }
        this.editable = value;
        if(!value){
            this.el.dom.setAttribute('readOnly', true);
            this.el.on('mousedown', this.onTriggerClick,  this);
            //this.el.addClass('x-combo-noedit');
        }else{
            this.el.dom.removeAttribute('readOnly');
            this.el.un('mousedown', this.onTriggerClick,  this);
            //this.el.removeClass('x-combo-noedit');
        }
    },    
	reset : function() {
		this.setValue('');
	},
    doDblClick : function(){
    	if (!this.linkBoUrl||this.linkBoUrl === null||!this.editable) return;
    	_getMainFrame().maintabs.addPanel(this.txtDataCollaborative,'',
    	contextPath+"/"+this.linkBoUrl+'='+this.getValue());
    	return;
    	
		_getMainFrame().ExtModalWindowUtil.show(this.txtDataCollaborative,
		contextPath+"/"+this.linkBoUrl+'='+this.getValue(),
		this,
		'',
		{width:600,height:300});    
    },
    doForce : function(){
    	//this.trigger.hide();
    	if(this.shlpType != 'grid')this.setValue(this.getRawValue());
        if(this.el.dom.value.length > 0){
        	if(this.shlpType == 'field' && this.valueField == undefined)return;
            this.el.dom.value =
                this.lastSelectionText === undefined ? '' : this.lastSelectionText;
            this.applyEmptyText();
        }
    },
    resetForm: function(e){
		this.form.form.reset();
		var shlps = this.form.findByType('searchhelpfield');
		for (var i=0;i<shlps.length;i++)shlps[i].reset();
    },
    select : function(e){
    	var rec = this.grid.getSelectionModel().getSelected();
		if(rec.data)this.setJsonValue(rec.data);
		this.fireEvent('select', this, rec.data);
        this.win.hide();
    },
    setJsonValue : function(jsonData){
    	if(jsonData==null)return;
    	this.jsonValue = jsonData;
    	if(this.shlpType == 'grid'){
			if (Ext.getCmp(this.boName)){
				var gridrec = Ext.getCmp(this.boName).getSelectionModel().getSelected(0);
				for(var j=0;j<this.seachHelpMapPara.length;j++){
					var property = this.seachHelpMapPara[j].property;
					var id = this.seachHelpMapPara[j].id;
					var text = this.seachHelpMapPara[j].text;
					gridrec.set(property,jsonData[id]);
					gridrec.set(property+'_text',jsonData[text]);
				}
				this.setValue(jsonData);
			}
    	}else if(this.shlpType == 'form'){
    		for(var j=0;j<this.seachHelpMapPara.length;j++){
   				var id = this.seachHelpMapPara[j].id;
   				var text = this.seachHelpMapPara[j].text;
   				var property = this.seachHelpMapPara[j].property;
   				if (Ext.getCmp(this.boName+'.'+property+'_text')){
   					if (Ext.getCmp(this.boName+'.'+property+'_text').getXType()=='searchhelpfield'){		
   						if(property==this.boProperty)Ext.getCmp(this.boName+'.'+property+'_text').setValue(jsonData);
   					}else if(Ext.getCmp(this.boName+'.'+property+'_text').getXType()=='combo'
   						||Ext.getCmp(this.boName+'.'+property+'_text').getXType()=='dpcombo'){
   						Ext.getCmp(this.boName+'.'+property+'_text').setValue(jsonData[id]);
   					}
   				}else{
	   				if (Ext.get(this.boName+'.'+property) && jsonData[id])
	   					Ext.get(this.boName+'.'+property).dom.value =jsonData[id];
	   				if (Ext.get(this.boName+'.'+property+'_text') && jsonData[text])
	   					Ext.get(this.boName+'.'+property+'_text').dom.value = jsonData[text];
   				}
   			}
    	}else if(this.shlpType == 'field'){
    		this.setValue(jsonData);
    	}
    	if (this.callBack)this.callBack(jsonData);
    },
    setValue : function(data,dataCallBack,isEvent){
    	if(typeof data != 'object'){
    		if((this.jsonValue[this.displayField]==data)||(this.jsonValue[this.valueField]==data))return;
    		if(this.shlpType == 'grid'&&data==this.lastSelectionText) return;
    	}
        var text,value;   
        if(!data||data==null){
	        if(this.hiddenField){ 
	            this.hiddenField.value = "";   
	        }
	        if(this.hiddenText){ 
	            this.hiddenText.value = "";   
	        }
	        Ext.form.SearchHelpField.superclass.setValue.call(this, "");   
	        this.value = "";
	        this.jsonValue = {};
	        this.lastSelectionText = "";
        }else{
	        if(typeof data == 'object'){   
	            text = data[this.displayField || this.valueField];   
	            value = data[this.valueField || this.displayField];
		        if(this.hiddenField){
		            this.hiddenField.value = value;
		        }   
		        if(this.hiddenText){ 
		            this.hiddenText.value = text;
		        }
		        if(this.shlpType == 'grid') {
		        	Ext.form.SearchHelpField.superclass.setValue.call(this, value);
		        	this.lastSelectionText = value;
		        }else{
		        	 Ext.form.SearchHelpField.superclass.setValue.call(this, text);
		        	this.lastSelectionText = text;
		        }
		        this.value = value; 
	        }else{
    			if(this.shlpType == 'grid'){
			        if(this.hiddenField){ 
			            this.hiddenField.value = data;   
			        }  
			        if(this.hiddenText){
			            this.hiddenText.value = data;
			        }
			        Ext.form.SearchHelpField.superclass.setValue.call(this, data);   
			        this.value = data; 
			        this.lastSelectionText = data;
    			}
    			if((this.shlpType!='grid')||(this.shlpType=='grid'&&isEvent)){
	        		if(this.boName!=""&&this.boProperty!=""){
						Ext.Ajax.request({
					    	url : contextPath+'/searchHelpQueryController.spr?action=queryShlpDataByBoName',
					        params : {boName:this.boName, boProperty:this.boProperty, value:data},
					        success : function(xhr){
								if(xhr.responseText){
						        	var jsonData = Ext.util.JSON.decode(xhr.responseText);
						        	if(jsonData)this.setJsonValue(jsonData);
								}
					        	if (dataCallBack)dataCallBack(xhr.responseText);
					        },
					        scope : this
					    });
	        		}else{
		        		if(this.shlpName!=""){
							Ext.Ajax.request({
						    	url : contextPath+'/searchHelpQueryController.spr?action=queryShlpDataByShlpName',
						        params : {shlpName:this.shlpName, valueField:this.valueField, value:data},
						        success : function(xhr){
									if(xhr.responseText){
							        	var jsonData = Ext.util.JSON.decode(xhr.responseText);
							        	if(jsonData)this.setJsonValue(jsonData);
									}
						        	if (dataCallBack)dataCallBack(xhr.responseText);
						        },
						        scope : this
						    });
		        		}else{
					        if(this.hiddenField){ 
					            this.hiddenField.value = data;   
					        }  
					        if(this.hiddenText){
					            this.hiddenText.value = data;
					        }
					        Ext.form.SearchHelpField.superclass.setValue.call(this, data);   
					        this.value = data; 
					        this.lastSelectionText = data;
		        		}
	        		}
    			}
	        }
        }
        if (dataCallBack)dataCallBack();
        if(String(value) !== String(this.startValue)){
        	this.fireEvent('change', this, value, this.startValue);
        }
    },
    getJsonValue : function(){ 
    	return this.jsonValue;
    },
    getValue : function(){
        if(this.valueField){   
            return typeof this.value != 'undefined' ? this.value : Ext.get(this.boName+'.'+this.boProperty) ? Ext.get(this.boName+'.'+this.boProperty).dom.value : '';   
        }else{   
            return Ext.form.SearchHelpField.superclass.getValue.call(this);   
        }   
    },
    findRecord : function(value){
    	var record;
		if(this.boName!=""&&this.boProperty!=""){
			Ext.Ajax.request({
		    	url : contextPath+'/searchHelpQueryController.spr?action=getShlpDataByBoName',
		        params : {boName:this.boName, boProperty:this.boProperty, value:data},
		        sync:true,
		        success : function(xhr){
					if(xhr.responseText){
			        	record = Ext.util.JSON.decode(xhr.responseText);
			        	return record;
					}
		        },
		        scope : this
		    });
		}
		if(this.shlpName!=""){
			Ext.Ajax.request({
		    	url : contextPath+'/searchHelpQueryController.spr?action=queryShlpDataByShlpName',
		        params : {shlpName:this.shlpName, valueField:this.valueField, value:data},
		        sync:true,
		        success : function(xhr){
					if(xhr.responseText){
			        	record = Ext.util.JSON.decode(xhr.responseText);
			        	return record;
					}
		        },
		        scope : this
		    });
		}
    },
    // 多语言支持文本(默认语言英文)
    txtQuery:'Search',
    txtClean:'Reset',
    txtTitle:'Search Help',
    txtDataOnload:'Data on load...',
    txtDataCollaborative:'Data Collaborative'
});   
Ext.reg('searchhelpfield', Ext.form.SearchHelpField);