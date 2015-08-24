/**
 * @author adeitcher
 */
Ext.apply(Ext.data.Store.prototype,
{
	// to keep track of changes
	journal : [],
	maxId : 0,
	
	// fixed types
	types : {change: 'change', add: 'add', remove: 'remove'},
	
	// the added config is automatically there - these are defaults
	updateProxy : null,
	
	// if you want to replace all, set options.replace = true
	write : function(options) {
		if (this.fireEvent('beforewrite',this,options) != false) {
			// get the appropriate records
			var records = this.writeRecords(options);
			// structure the params we need for the update to the server
			var params = {
				data: this.reader.write(records)
			}
			// add any options
			Ext.applyIf(params, options);
            this.updateProxy.update(params);			
		}
	},
	
	writeRecords : function(options) {
		var data = [];
		var tmp;
		// we take only the journal, unless we have explicitly asked to replace all
		
		// sure, we could use a trinary operator, but this may get more complex in the future,
		// and if-then-else is cleaner to understand
		
		// to be supported later
		if (options != null && options.replace == true) {
			tmp = this.journal;
			//data = this.data.getRange(0);
		} else {
			tmp = this.journal;
		}

		// get the actual data in the record
		for (var i=0; i<tmp.length; i++) {
			if (tmp[i] != null) {
				data[i] = {
					type: tmp[i].type,
					data: tmp[i].record.data
				};
			}
		}
		
		return(data);
	},
	
	// these all need to be modified to keep track of real changes
    add : function(records){
        records = [].concat(records);
        for(var i = 0, len = records.length; i < len; i++){
            records[i].join(this);
        }
        var index = this.data.length;
        this.data.addAll(records);
		for (var i=0; i<records.length; i++) {
			this.journal.push({type: this.types.add, index: index+i, record: records[i]});
		}
        this.fireEvent("add", this, records, index);
    },

    
    remove : function(record){
        var index = this.data.indexOf(record);
        this.data.removeAt(index);
		this.journal.push({type: this.types.remove, index: index, record: record});
        this.fireEvent("remove", this, record, index);
    },

    
    removeAll : function(){
		// record that all objects have been removed
		for (var i=0,len = this.data.getCount(); i<len; i++) {
			this.journal.push({type: this.types.remove, index: i, record: this.data[i]});			
		}
        this.data.clear();
        this.fireEvent("clear", this);
    },

    
    insert : function(index, records){
        records = [].concat(records);
        for(var i = 0, len = records.length; i < len; i++){
            this.data.insert(index, records[i]);
            records[i].join(this);
        }
		for (var i=0; i<records.length; i++) {
			this.journal.push({type: this.types.add, index: index+i, record: records[i]});
		}
        this.fireEvent("add", this, records, index);
    },
	
    commitChanges : function(){
		// commit the changes and clean out
		var m = this.journal.slice(0);
		this.journal = [];
		// only changes need commitment
		for (var i=0, len=m.length; i<len; i++) {
			if (m[i].type == this.types.change) {
				m[i].record.commit();
			}
		}
		
		// now write the changes to persistent storage
		if (this.updateProxy != null) {
			this.write();
		}
    },

    rejectChanges : function(){
		// back out the changes in reverse order
        var m = this.journal.slice(0).reverse();
        this.journal = [];
        for(var i = 0, len = m.length; i < len; i++){
			var jType = m[i].type;
			if (jType == this.types.change) {
				// reject the change
				m[i].record.reject();
			} else if (jType == this.types.add) {
				// undo the add
				this.data.removeAt(m[i].index);
			} else if (jType == this.types.remove) {
				// put it back
				this.data.insert(m[i].index,m[i].data);
			}
        }
    },

	/*
	 *  The next three are for changes affected directly to a record
	 *  Ideally, this should never happen: all changes go through the Store, 
	 *  and are passed through after being recorded. However, in an object-oriented paradigm,
	 *  it is accepted that you can gain access to the direct object, unlike a SQL paradigm.
	 *  Thus, we need to put events on the Record directly.
	 */
	
	// if we edited a record directly, we need to update the journal	
    afterEdit : function(record){
        this.journal.push({type: this.types.change, index: this.data.indexOf(record), record: record});
        this.fireEvent("update", this, record, Ext.data.Record.EDIT);
    },

	// if we rejected a change to a record directly, we need to remove it from the journal
    afterReject : function(record){
		// find the last edit we had, and remove it
		for (var i=this.journal.length-1; i>=0; i++) {
			if (this.journal[i].type == this.types.change && this.journal[i].record == record) {
				this.journal = this.journal.splice(i,1);
				break;
			}
		}
        this.fireEvent("update", this, record, Ext.data.Record.REJECT);
    },

    // if we committed a change to a record directly, we still keep it in the journal
    afterCommit : function(record){
        this.fireEvent("update", this, record, Ext.data.Record.COMMIT);
    },	

	getNextId : function() {
		// send back the maxId + 1
		return(this.getMaxId()+1);
	},
	
	getMaxId : function() {
		var maxId = 1000;
		if (this.data != null) {
			var records = this.data.getRange(0);
			for (var i=0; i<records.length; i++) {
				if (records[i].id > maxId)
					maxId = records[i].id;
			}
		}
		return(maxId);
	}
});


// basic data writer
Ext.data.DataWriter = function(meta, recordType) {
    this.meta = meta;
    this.recordType = recordType instanceof Array ? 
        Ext.data.Record.create(recordType) : recordType;	
};

// Json data writer extension to Reader
Ext.apply(Ext.data.JsonReader.prototype, {

	// write - input objects, write out JSON    
	write : function(records) {
		// hold our new structure
		var j = Ext.util.JSON.encode(records);
		if (!j) {
			throw{message: "JsonWriter.write: unable to encode records into Json"};
		}
		return(j);
	}
	
});

// extend the HttpProxy to write
Ext.apply(Ext.data.HttpProxy.prototype,
{
    update : function(params){
        if(this.fireEvent("beforeupdate", this, params) !== false){
            var  o = {
                params : params || {},
                callback : this.updateResponse,
				method: 'POST',
                scope: this
            };
            if(this.useAjax){
                Ext.applyIf(o, this.conn);
                if(this.activeRequest){
                    Ext.Ajax.abort(this.activeRequest);
                }
                this.activeRequest = Ext.Ajax.request(o);
            }else{
                this.conn.request(o);
            }
        }else{
            callback.call(scope||this, null, arg, false);
        }
    },

    updateResponse : function(o, success, response){
        delete this.activeRequest;
        if(!success){
            this.fireEvent("updateexception", this, o, response);
            return;
        }
        this.fireEvent("update", this, o, o.request.arg);
    }

    
    
	
});