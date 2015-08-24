/*
 * 
 * huanghu
 * 
 * Porlet
 * 
 */
	var tools = [{
        id : 'maximize',
        handler : handleMaximize
    }, {
        id : 'close',
        handler : function(e, target, panel) {
        panel.ownerCt.remove(panel, true);
       }
    }];
  
	function handleMaximize(event, toolEl, panel) {
        panel.originalOwnerCt = panel.ownerCt;
        panel.originalPosition = panel.ownerCt.items.indexOf(panel);
        panel.originalSize = panel.getSize();

        if (!toolEl.window) {
           var defaultConfig = {
               id : (panel.getId() + '-MAX'),
               width : Ext.getBody().getSize().width,//(Ext.getBody().getSize().width - 100),
               height : Ext.getBody().getSize().height,//(Ext.getBody().getSize().height - 100),
               //resizable : true,
               //draggable : true,
               closable : true,
               closeAction : 'hide',
               hideBorders : true,
               plain : true,
               layout : 'fit',
               autoScroll : false,
               border : false,
               bodyBorder : false,
               frame : true,
               pinned : true,
               //y : 80,
               bodyStyle : 'background-color: #ffffff;',
	           listeners: {
	               'hide': function(e){
	               		panel.setSrc();
	               }
	           }
           };
           toolEl.window = new Ext.Window(defaultConfig);
           toolEl.window.on('hide', handleMinimize, panel);
        }
        if (!panel.dummyComponent) {
           var dummyCompConfig = {
               title : panel.title,
               width : panel.getSize().width,
               height : panel.getSize().height,
               html : '&nbsp;'
           };
           panel.dummyComponent = new Ext.Panel(dummyCompConfig);
        }

        toolEl.window.add(panel);
        if (panel.tools['toggle'])
           panel.tools['toggle'].setVisible(false);
        if (panel.tools['close'])
           panel.tools['close'].setVisible(false);
        panel.tools['maximize'].setVisible(false);

        panel.originalOwnerCt.insert(panel.originalPosition,
               panel.dummyComponent);
        panel.originalOwnerCt.doLayout();
        panel.dummyComponent.setSize(panel.originalSize);
        panel.dummyComponent.setVisible(true);
        panel.dummyComponent.getEl().mask('最大化中....');
        toolEl.window.show(this);
        panel.setSrc();//panel.defaultSrc
    };

    function handleMinimize(window) {
   	    this.dummyComponent.getEl().unmask();
        this.dummyComponent.setVisible(false);
        this.originalOwnerCt.insert(this.originalPosition, this);
        this.originalOwnerCt.doLayout();
        this.setSize(this.originalSize);
        this.tools['maximize'].setVisible(true);
        if (this.tools['toggle'])
           this.tools['toggle'].setVisible(true);
        if (this.tools['close'])
           this.tools['close'].setVisible(true);
    }
 
Ext.onReady(function(){	
	Ext.Ajax.request({
		//加入web上下文路径引用，contextPath在commons.jsp中声明
    	url : contextPath+'/platform/basicApp/sysConsole/portlet/portletController.spr?action=_createPortlet',
        params : {communityid: communityid},
        success : function(xhr){
	        var o = Ext.util.JSON.decode(xhr.responseText);
    
		    var viewport = new Ext.Viewport({
		        layout:'border',
		        items:[{
		            xtype:'portal',
		            region:'center',
		            margins:'0 5 5 0',
		            items:Ext.util.JSON.decode(o.portlet)
		            /*
		            [{
		                columnWidth:.33,
		                style:'padding:10px 0 10px 10px',
		                items:[{
		                    title: 'Panel 1',
		                    tools: tools,
		                    defaultSrc: 'http://www.baidu.com'
		                },{
		                    title: 'Another Panel 1',
		                    tools: tools,
		                    defaultSrc: 'http://www.baidu.com'
		                }]
		            },{
		                columnWidth:.33,
		                style:'padding:10px 0 10px 10px',
		                items:[{
		                    title: 'Panel 2',
		                    tools: tools,
		                    defaultSrc: 'http://www.baidu.com'
		                },{
		                    title: 'Another Panel 2',
		                    tools: tools,
		                    defaultSrc: 'http://www.baidu.com'
		                }]
		            },{
		                columnWidth:.33,
		                style:'padding:10px',
		                items:[{
		                    title: 'Panel 3',
		                    tools: tools,
		                    defaultSrc: 'http://www.baidu.com'
		                },{
		                    title: 'Another Panel 3',
		                    tools: tools,
		                    defaultSrc: 'http://www.baidu.com'
		                }]
		            }]
		            
		            
		             * Uncomment this block to test handling of the drop event. You could use this
		             * to save portlet position state for example. The event arg e is the custom 
		             * event defined in Ext.ux.Portal.DropZone.
		             
		            ,listeners: {
		                'drop': function(e){
		                    Ext.Msg.alert('Portlet Dropped', e.panel.title + '<br />Column: ' + 
		                        e.columnIndex + '<br />Position: ' + e.position);
		                }
		            }
		            */
		        }]
		        
		    });
        },
        scope : this
    });
	    
});

