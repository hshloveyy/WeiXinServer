/**  
 * Ext JS Library 2.0 extend  
 * Version : 1.1  
 * Author : huanghu
 */  
Ext.DpTabPanel = Ext.extend(Ext.TabPanel,{
    /**  
     * @cfg {} cmpArray  
     * tab上所有的需要手动清空cmp对象  
     *   
     */ 
    cmpArray : {},
    
    initComponent : function(){
        Ext.DpTabPanel.superclass.initComponent.call(this);      
    }, 
    initEvents : function(){
        Ext.DpTabPanel.superclass.initEvents.call(this);
        
        this.strip.on('dblclick', this.onDblClick, this);
    },
    addPanel2 : function(title,defaultSrc){
    	var itemTab = this.getItem(title);
    	this.remove(this.getActiveTab());
    	if(itemTab){
    		this.setActiveTab(itemTab);
    		itemTab.setSrc(defaultSrc);
    	}else{
            if(typeof(runCallBack) == "undefined")
            {
                runCallBack = false;
            }
	        var tab = this.add(new Ext.DpIframePanel({
	        	id: title,
	    		title: title,
	    		iconCls: 'icon-cls',
	            defaultSrc : defaultSrc,
                returnValue:''
	        }));
	        this.setActiveTab(tab);
    	}
    },
    addPanel : function(title,parentGrid,defaultSrc,callBack,runCallBack){
    	var itemTab = this.getItem(title);
    	if(itemTab){
    		this.setActiveTab(itemTab);
    		itemTab.setSrc(defaultSrc);
    	}else{
            if(typeof(runCallBack) == "undefined")
            {
                runCallBack = false;
            }
	        var tab = this.add(new Ext.DpIframePanel({
	        	id: title,
	    		title: title,
	    		iconCls: 'icon-cls',
	    		parentTab: this.getActiveTab(),
	    		parentGrid: parentGrid,
	            defaultSrc : defaultSrc,
                runCallBack : runCallBack,
                returnValue:'',
	            callBack:callBack
	        }));
	        this.setActiveTab(tab);
    	}
    },
    onRender : function(ct, position){   
        Ext.DpTabPanel.superclass.onRender.call(this, ct, position);           
    },
	onRemove: function(tp,item){
		Ext.DpTabPanel.superclass.onRemove.call(this, tp, item);
        
		var parentTab = item.parentTab ;
		var parentGrid = item.parentGrid ;
		var callBack = item.callBack ;
        var runCallBack = item.runCallBack;
		if(parentTab)this.setActiveTab(parentTab);
		if(callBack)
        {
            if(runCallBack)
                callBack(item.flag);
        }
		else {
			if(parentGrid)parentGrid.getStore().reload();
		}
	},
    onDblClick : function(e){
         this.fireEvent("dblclick", this, e);
    }
});   
Ext.reg('dptabpanel', Ext.DpTabPanel);

Ext.DpIframePanel = Ext.extend(Ext.ux.ManagedIframePanel, {
	parentTab : null,
	parentGrid : null,
	callBack : null,
    closable: true,
    autoScroll:true,
    loadMask:{hideOnReady :false,msg:'数据载入中...'},
    autoShow:true,
    returnValue: null,
    runCallBack:false,
    flag:false,
            
    initComponent : function(){

        Ext.DpIframePanel.superclass.initComponent.call(this);
    },
    getReturnValue : function(){
        return this.returnValue;
    },
    setReturnValue : function(value){
        return this.returnValue=value;
    }

});
Ext.reg('dpiframepanel', Ext.DpIframePanel);