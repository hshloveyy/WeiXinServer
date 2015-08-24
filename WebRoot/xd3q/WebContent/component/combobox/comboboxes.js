 /*
 *@created by: cxp
 *@date:
 *note: if the transfer content is object,it must transferred meaing,other like string would be direct use
 */
 Ext.onReady(function(){
    var json_store = new Ext.data.JsonStore({
        fields: ['op_value', 'tip'],
        url : combobox_url,
        root:'combo_root' 
    });
    var data_store = new Ext.data.SimpleStore({
        fields: ['op_value', 'tip'],
        data:combobox_data=='Ext.exampledata.states'?Ext.exampledata.states:'' 
    });    
    var combo = new Ext.form.ComboBox({
        store: combobox_store=='data_store'?data_store:json_store,
        displayField:'op_value',
        typeAhead: true,
        mode: 'local',
        forceSelection: true,
        triggerAction: 'all',
        emptyText:'input or select a value...',
        selectOnFocus:true,
        applyTo: 'combo1'
     });
});