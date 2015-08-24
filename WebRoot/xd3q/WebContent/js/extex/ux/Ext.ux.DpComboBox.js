/**  
 * Ext JS Library 2.0 extend  
 * Version : 1.1  
 * Author : huanghu
 */  
Ext.form.DpComboBox = Ext.extend(Ext.form.ComboBox,{
    
    initComponent : function(){
        Ext.form.DpComboBox.superclass.initComponent.call(this);
        this.addEvents(
            'triggerclick'
        );
    }, 
    initEvents : function(){
        Ext.form.DpComboBox.superclass.initEvents.call(this);
    },
    onRender : function(ct, position){   
        Ext.form.DpComboBox.superclass.onRender.call(this, ct, position);           
    },
    onTriggerClick : function(){
        if(this.disabled||!this.editable){
            return;
        }
        if(this.isExpanded()){
            this.collapse();
            this.el.focus();
        }else {
	        if(this.fireEvent('triggerclick', this) !== false){
	            this.onFocus({});
	            if(this.triggerAction == 'all') {
	                this.doQuery(this.allQuery, true);
	            } else {
	                this.doQuery(this.getRawValue());
	            }
	            this.el.focus();
	        }
        }
    }
});   
Ext.reg('dpcombo', Ext.form.DpComboBox);