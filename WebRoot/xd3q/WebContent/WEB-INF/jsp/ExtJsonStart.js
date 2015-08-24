// Ext.onReady is probably the first method that you'll use on every page. 
// This method is automatically called once the DOM is fully loaded, 
// guaranteeing that any page elements that you may want to reference
// will be available when the script runs.
Ext.onReady(function() {
	// Data reader class to create an Array of Ext.data.Record objects from a 
	// JSON response based on mappings in a provided Ext.data.Record constructor.
 	var myReader = new Ext.data.JsonReader(
 		{root: 'people'}, 
 	[
		{name: 'id'},
		{name: 'firstName'},
		{name: 'lastName'},
		{name: 'birthdate', type: 'date', dateFormat: 'Y/m/d'},
		{name: 'street', mapping: 'address.street'},
		{name: 'city', mapping: 'address.city'},
		{name: 'state', mapping: 'address.state'},
		{name: 'zip', mapping: 'address.zip.zipcode'},
		{name: 'phone', mapping: 'phoneNumbers[0].number'}
	]);
	
	// The Store class encapsulates a client side cache of Record objects which 
	// provide input data for Components such as the GridPanel, the ComboBox, 
	// or the DataView.
	// A Store object uses its configured implementation of DataProxy to access 
	// a data object unless you call loadData directly and pass in your data.
	// A Store object has no knowledge of the format of the data returned by 
	// the Proxy.
	// A Store object uses its configured implementation of DataReader to 
	// create Record instances from the data object. These Records are 
	// cached and made available through accessor functions.
	var dataFromServer = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url: '/infolion/strutsORspringMVCReturningJson'}),
			reader: myReader
		});
 	
		var dataProxy = new Ext.data.HttpProxy({url: '/infolion/strutsORspringMVCReturningJson'});
 	// Append an event handler to this Element.
 	// The type of event to listen for.
 	// The method the event invokes.
 	dataFromServer.on('load', function() {
 
 	// A callback function that is called when the data is returned from the
 	// Store (dataFromServer) load() method call.
 	//function loadAndShowGrid() {
		
		var grid = new Ext.grid.GridPanel({
			store: dataFromServer,
			columns: [
				{hidden: true, dataIndex: 'id'},
				{header: 'First Name', width: 90, sortable: true, dataIndex: 'firstName'},
				{header: 'Last Name', width: 90, sortable: true, dataIndex: 'lastName'},
				{header: 'Birthdate', width: 90, sortable: true, 
					renderer: Ext.util.Format.dateRenderer('m/d/Y'), 
		                        dataIndex: 'birthdate'},
		        {header: 'City', width: 90, sortable: true, dataIndex: 'city'},
		        {header: 'Zip', width: 90, sortable: true, dataIndex: 'zip'},                
		        {header: 'Phone', width: 90, sortable: false, dataIndex: 'phone'}
			],
			viewConfig: {
				forceFit: true 
			},
			height: 100,
			enableColumnMove: true,
			// The default SelectionModel used by Ext.grid.GridPanel. 
			// It supports multiple selections and keyboard selection/navigation. 
			// The objects stored as selections and returned by getSelected, and 
			// getSelections are the Records which provide the data for the 
			// selected rows.
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			split: true,
			region: 'west',
			// Uncomment if you want the grid to be standalone.
			//renderTo: 'content',
			//title: 'Ajax Json Grid',
			//frame: true,
			//collapsible: true,
			width: 500
		});
	 	 
	 	// Define a template to use for the detail view.
	 	// Represents an HTML fragment template.
		var personTplMarkup = [
			'Name: {firstName} {lastName}<br/>',
			'Address: {street} {city} {state} {zip}<br/>',
			'Phone: {phone}<br/>'
		];
		var personTpl = new Ext.Template(personTplMarkup);
	
		// Panel is a container that has specific functionality and structural
		// components that make it the perfect building block for 
		// application-oriented user interfaces. The Panel contains bottom 
		// and top toolbars, along with separate header, footer and body sections. 
		// It also provides built-in expandable and collapsible behavior, 
		// along with a variety of prebuilt tool buttons that can be wired up 
		// to provide other customized behavior. Panels can be easily dropped 
		// into any Container or layout, and the layout and rendering pipeline
		// is completely managed by the framework.
		var ct = new Ext.Panel({
			renderTo: 'content',
			frame: true,
			title: 'Person Information',
			width: 800,
			height: 200,
			collapsible: true,
			layout: 'border',
			items: [
				grid,
				{
					id: 'detailPanel',
					region: 'center',
					bodyStyle: {
						background: '#ffffff',
						padding: '7px'
					},
					html: 'Please select a person to see additional details.'
				}
			]
		}); 
		
		// Add an event onto the row selection model. The rowselect accepts
		// 3 arguments, the section model, the index of the selected row and 
		// the Ext.data.Record object.
		grid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
			//Ext.MessageBox.alert('Status', 'The row index you selected is: ' + rowIdx +
			//		' Name: ' + r.get('firstName') + ' ' + r.get('lastName'));
			
			var detailPanel = Ext.getCmp('detailPanel');
			personTpl.overwrite(detailPanel.body, r.data);		
		});	
		
		grid.getSelectionModel().selectFirstRow();
	//}
	});
		
	// Loads the Record cache from the configured Proxy using the configured Reader.
	// If using remote paging, then the first load call must specify the start and 
	// limit properties in the options.params property to establish the initial 
	// position within the dataset, and the number of Records to cache on each read
	// from the Proxy.
	// It is important to note that for remote data sources, loading is asynchronous,
	// and this call will return before the new data has been loaded. Perform 
	// any post-processing in a callback function, or in a "load" event handler.
	
	// Use an event to load and create the Grid.
	dataFromServer.load();
	
	// Use a callback method to load and create the Grid.
	//dataFromServer.load({callback: loadAndShowGrid});
});