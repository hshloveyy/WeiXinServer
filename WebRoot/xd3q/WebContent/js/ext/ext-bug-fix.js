/*
 * 修复ext ui组件bug
 * Author huanghu
 * 09 April 2009
 */
if(Ext.form.DateField){
	Ext.apply(Ext.form.DateField.prototype, {
	    setEditable : function(value){
	        if(value == this.editable){
	            return;
	        }
	        this.editable = value;
	        if (!value) {
                this.el.dom.readOnly = true;
                this.el.addClass('x-trigger-noedit');
                //this.un(this.el, 'click', this.onTriggerClick, this);
	        } else {
                this.el.dom.readOnly = false;
                this.el.removeClass('x-trigger-noedit');
                //this.on(this.el, 'click', this.onTriggerClick, this);
	        }
	        this.trigger.setDisplayed(value);
    	}
	});
}
if(Ext.form.Field){
   Ext.apply( Ext.form.Field.prototype, {
	    onRender : function(ct, position){
	        Ext.form.Field.superclass.onRender.call(this, ct, position);
	        if(!this.el){
	            var cfg = this.getAutoCreate();
	            if(!cfg.name){
	                cfg.name = this.name || this.id;
	            }
	            if(this.inputType){
	                cfg.type = this.inputType;
	            }
	            this.el = ct.createChild(cfg, position);
	        }
	        var type = this.el.dom.type;
	        if(type){
	            if(type == 'password'){
	                type = 'text';
	            }
	            this.el.addClass('x-form-'+type);
	        }
	        if(this.readOnly){
	            this.el.dom.readOnly = true;
	        }
	        if(this.editable==false){
	            this.el.dom.readOnly = true;
	            this.el.addClass('x-combo-noedit');
	        }
	        if(this.tabIndex !== undefined){
	            this.el.dom.setAttribute('tabIndex', this.tabIndex);
	        }
	
	        this.el.addClass([this.fieldClass, this.cls]);
	    },
    	// private
	     setEditable : function(value){
	        if(!value){
	            this.el.addClass('x-combo-noedit');
	        }else{
	            this.el.removeClass('x-combo-noedit');
	        }
	     }
   });
}

if(Ext.grid.GridPanel){
   Ext.apply( Ext.grid.GridPanel.prototype, {
    	// private
	     processEvent : function(name, e){
	        this.fireEvent(name, e);
	        var t = e.getTarget();
	        var v = this.view;
	        var header = v.findHeaderIndex(t);
	        if(header !== false&&header !== -1){
	            this.fireEvent("header" + name, this, header, e);
	        }else{
	            var row = v.findRowIndex(t);
	            var cell = v.findCellIndex(t);
	            if(row !== false){
	                this.fireEvent("row" + name, this, row, e);
	                if(cell !== false){
	                    this.fireEvent("cell" + name, this, row, cell, e);
	                }
	            }
	        }
	    }
   });
}

Ext.override(Ext.grid.RowSelectionModel, {  
    // private
    onEditorKey : function(field, e){
        var k = e.getKey(), newCell, g = this.grid, ed = g.activeEditor;
        var shift = e.shiftKey;
        if(k == e.TAB){
            e.stopEvent();
            ed.completeEdit();
            if(shift){
                newCell = g.walkCells(ed.row, ed.col-1, -1, this.acceptsNav, this);
            }else{
                newCell = g.walkCells(ed.row, ed.col+1, 1, this.acceptsNav, this);
            }
        }else if(k == e.ENTER){
            e.stopEvent();
            ed.completeEdit();
			if(this.moveEditorOnEnter !== false && field.getXType()!= 'searchhelpfield'){
				if(shift){
					newCell = g.walkCells(ed.row - 1, ed.col, -1, this.acceptsNav, this);
				}else{
					newCell = g.walkCells(ed.row + 1, ed.col, 1, this.acceptsNav, this);
				}
			}
        }else if(k == e.ESC){
            ed.cancelEdit();
        }
        if(newCell){
            g.startEditing(newCell[0], newCell[1]);
        }
    }
}); 

Ext.override(Ext.grid.GridView, {   
    initTemplates : function(){   
        var ts = this.templates || {};   
        if(!ts.master){   
            ts.master = new Ext.Template(   
                '<div class="x-grid3" hidefocus="true">',   
                    '<div class="x-grid3-viewport">',   
                        '<div class="x-grid3-header"><div class="x-grid3-header-inner" style="{ostyle}" mce_style="{ostyle}"><div class="x-grid3-header-offset">{header}</div></div><div class="x-clear"></div></div>',   
                        '<div class="x-grid3-scroller"><div class="x-grid3-body" style="{bstyle}" mce_style="{bstyle}">{body}</div><a href="#" mce_href="#" class="x-grid3-focus" tabIndex="-1"></a></div>',   
                    '</div>',   
                    '<div class="x-grid3-resize-marker"> </div>',   
                    '<div class="x-grid3-resize-proxy"> </div>',   
                '</div>'  
            );   
        }   
        if(!ts.header){   
            ts.header = new Ext.Template(   
                '<table border="0" cellspacing="0" cellpadding="0" style="{tstyle}" mce_style="{tstyle}">',   
                '<thead><tr class="x-grid3-hd-row">{cells}</tr></thead>',   
                '</table>'  
            );   
        }   
        if(!ts.hcell){   
            ts.hcell = new Ext.Template(   
                '<td class="x-grid3-hd x-grid3-cell x-grid3-td-{id} {css}" style="{style}" mce_style="{style}"><div {tooltip} {attr} class="x-grid3-hd-inner x-grid3-hd-{id}" unselectable="on" style="{istyle}" mce_style="{istyle}">', this.grid.enableHdMenu ? '<a class="x-grid3-hd-btn" href="#" mce_href="#"></a>' : '',   
                '{value}<img class="x-grid3-sort-icon" src="', Ext.BLANK_IMAGE_URL, '" mce_src="', Ext.BLANK_IMAGE_URL, '" />',   
                '</div></td>'  
            );   
        }   
        if(!ts.body){   
            ts.body = new Ext.Template('{rows}');   
        }   
        if(!ts.row){   
            ts.row = new Ext.Template(   
                '<div class="x-grid3-row {alt}" style="{tstyle}" mce_style="{tstyle}"><table class="x-grid3-row-table" border="0" cellspacing="0" cellpadding="0" style="{tstyle}" mce_style="{tstyle}">',   
                '<tbody><tr>{cells}</tr>',   
                (this.enableRowBody ? '<tr class="x-grid3-row-body-tr" style="{bodyStyle}" mce_style="{bodyStyle}"><td colspan="{cols}" class="x-grid3-body-cell" tabIndex="0" hidefocus="on"><div class="x-grid3-row-body">{body}</div></td></tr>' : ''),   
                '</tbody></table></div>'  
            );   
        }   
        if(!ts.cell){   
            ts.cell = new Ext.Template(   
                '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} {css}" style="{style}" mce_style="{style}" tabIndex="0" {cellAttr}>',   
                '<div class="x-grid3-cell-inner x-grid3-col-{id}" unselectable="on" {attr}>{value}</div>',   
                '</td>'  
            );   
        }   
        for(var k in ts){   
            var t = ts[k];   
            if(t && typeof t.compile == 'function' && !t.compiled){   
                t.disableFormats = true;   
                t.compile();   
            }   
        }   
        this.templates = ts;   
        this.colRe = new RegExp("x-grid3-td-([^\\s]+)", "");   
    },   
    updateAllColumnWidths : function(){   
        var tw = this.getTotalWidth();   
        var clen = this.cm.getColumnCount();   
        var ws = [];   
        for(var i = 0; i < clen; i++){   
            ws[i] = this.getColumnWidth(i);   
        }   
        this.innerHd.firstChild.style.width = this.getOffsetWidth();   
        this.innerHd.firstChild.firstChild.style.width = tw;   
        this.mainBody.dom.style.width = tw;   
        for(var i = 0; i < clen; i++){   
            var hd = this.getHeaderCell(i);   
            hd.style.width = ws[i];   
        }   
        var ns = this.getRows(), row, trow;   
        for(var i = 0, len = ns.length; i < len; i++){   
            row = ns[i];   
            row.style.width = tw;   
            if(row.firstChild){   
                row.firstChild.style.width = tw;   
                trow = row.firstChild.rows[0];   
                for (var j = 0; j < clen; j++) {   
                    trow.childNodes[j].style.width = ws[j];   
                }   
            }   
        }   
        this.onAllColumnWidthsUpdated(ws, tw);   
    },   
    updateColumnWidth : function(col, width){   
        var w = this.getColumnWidth(col);   
        var tw = this.getTotalWidth();   
        this.innerHd.firstChild.style.width = this.getOffsetWidth();   
        this.innerHd.firstChild.firstChild.style.width = tw;   
        this.mainBody.dom.style.width = tw;   
        var hd = this.getHeaderCell(col);   
        hd.style.width = w;   
        var ns = this.getRows(), row;   
        for(var i = 0, len = ns.length; i < len; i++){   
            row = ns[i];   
            row.style.width = tw;   
            if(row.firstChild){   
                row.firstChild.style.width = tw;   
                row.firstChild.rows[0].childNodes[col].style.width = w;   
            }   
        }   
        this.onColumnWidthUpdated(col, w, tw);   
    },   
    updateColumnHidden : function(col, hidden){   
        var tw = this.getTotalWidth();   
        this.innerHd.firstChild.style.width = this.getOffsetWidth();   
        this.innerHd.firstChild.firstChild.style.width = tw;   
        this.mainBody.dom.style.width = tw;   
        var display = hidden ? 'none' : '';   
        var hd = this.getHeaderCell(col);   
        hd.style.display = display;   
        var ns = this.getRows(), row;   
        for(var i = 0, len = ns.length; i < len; i++){   
            row = ns[i];   
            row.style.width = tw;   
            if(row.firstChild){   
                row.firstChild.style.width = tw;   
                row.firstChild.rows[0].childNodes[col].style.display = display;   
            }   
        }   
        this.onColumnHiddenUpdated(col, hidden, tw);   
        delete this.lastViewWidth;   
        this.layout();   
    },   
    afterRender: function(){   
        this.mainBody.dom.innerHTML = this.renderRows() || ' ';   
        this.processRows(0, true);   
        if(this.deferEmptyText !== true){   
            this.applyEmptyText();   
        }   
    },   
    renderUI : function(){   
        var header = this.renderHeaders();   
        var body = this.templates.body.apply({rows: ' '});   
        var html = this.templates.master.apply({   
            body: body,   
            header: header,   
            ostyle: 'width:'+this.getOffsetWidth()+';',   
            bstyle: 'width:'+this.getTotalWidth()+';'  
        });   
        var g = this.grid;   
        g.getGridEl().dom.innerHTML = html;   
        this.initElements();   
        Ext.fly(this.innerHd).on("click", this.handleHdDown, this);   
        this.mainHd.on("mouseover", this.handleHdOver, this);   
        this.mainHd.on("mouseout", this.handleHdOut, this);   
        this.mainHd.on("mousemove", this.handleHdMove, this);   
        this.scroller.on('scroll', this.syncScroll, this);   
        if(g.enableColumnResize !== false){   
            this.splitZone = new Ext.grid.GridView.SplitDragZone(g, this.mainHd.dom);   
        }   
        if(g.enableColumnMove){   
            this.columnDrag = new Ext.grid.GridView.ColumnDragZone(g, this.innerHd);   
            this.columnDrop = new Ext.grid.HeaderDropZone(g, this.mainHd.dom);   
        }   
        if(g.enableHdMenu !== false){   
            if(g.enableColumnHide !== false){   
                this.colMenu = new Ext.menu.Menu({id:g.id + "-hcols-menu"});   
                this.colMenu.on("beforeshow", this.beforeColMenuShow, this);   
                this.colMenu.on("itemclick", this.handleHdMenuClick, this);   
            }   
            this.hmenu = new Ext.menu.Menu({id: g.id + "-hctx"});   
            this.hmenu.add(   
                {id:"asc", text: this.sortAscText, cls: "xg-hmenu-sort-asc"},   
                {id:"desc", text: this.sortDescText, cls: "xg-hmenu-sort-desc"}   
            );   
            if(g.enableColumnHide !== false){   
                this.hmenu.add('-',   
                    {id:"columns", text: this.columnsText, menu: this.colMenu, iconCls: 'x-cols-icon'}   
                );   
            }   
            this.hmenu.on("itemclick", this.handleHdMenuClick, this);   
        }   
        if(g.trackMouseOver){   
            this.mainBody.on("mouseover", this.onRowOver, this);   
            this.mainBody.on("mouseout", this.onRowOut, this);   
        }   
        if(g.enableDragDrop || g.enableDrag){   
            this.dragZone = new Ext.grid.GridDragZone(g, {   
                ddGroup : g.ddGroup || 'GridDD'  
            });   
        }   
        this.updateHeaderSortState();   
    },   
    onColumnWidthUpdated : function(col, w, tw){   
        // empty   
    },   
    onAllColumnWidthsUpdated : function(ws, tw){   
        // empty   
    },   
    onColumnHiddenUpdated : function(col, hidden, tw){   
        // empty   
    },   
    getOffsetWidth: function() {   
        return (this.cm.getTotalWidth() + this.scrollOffset) + 'px';   
    },   
    renderBody : function(){   
        var markup = this.renderRows() || ' ';   
        return this.templates.body.apply({rows: markup});   
    },   
    hasRows : function(){   
        var fc = this.mainBody.dom.firstChild;   
        return fc && fc.nodeType == 1 && fc.className != 'x-grid-empty';   
    }   
}); 

if(Ext.grid.EditorGridPanel){
   Ext.apply( Ext.grid.EditorGridPanel.prototype, {
    	// private
	    startEditing : function(row, col){
	        this.stopEditing();
	        if(this.colModel.isCellEditable(col, row)){
	            this.view.ensureVisible(row, col, true);
	            var r = this.store.getAt(row);
	            var field = this.colModel.getDataIndex(col);
	            var e = {
	                grid: this,
	                record: r,
	                field: field,
	                value: r.data[field],
	                row: row,
	                column: col,
	                cancel:false
	            };
	            if(this.fireEvent("beforeedit", e) !== false && !e.cancel){
	                this.editing = true;
	                var ed = this.colModel.getCellEditor(col, row);
	                if(!ed)return;
	                if(!ed.rendered){
	                    ed.render(this.view.getEditorParent(ed));
	                }
	                (function(){ // complex but required for focus issues in safari, ie and opera
	                    ed.row = row;
	                    ed.col = col;
	                    ed.record = r;
	                    ed.on("complete", this.onEditComplete, this, {single: true});
	                    ed.on("specialkey", this.selModel.onEditorKey, this.selModel);
	                    
	                    this.activeEditor = ed;
	                    var v = this.preEditValue(r, field);
	                    ed.startEdit(this.view.getCell(row, col).firstChild, v === undefined ? '' : v);
	                }).defer(50, this);
	            }
	        }
	    },
	    
    	// private
		preEditValue : function(r, field){
	        var value = r.data[field];
	        if (typeof value == 'number'&&value==0)value = '';
	        if (typeof value == 'string')value = value.replace(/(^\s*)|(\s*$)/g, "");
			return this.autoEncode && typeof value == 'string' ? Ext.util.Format.htmlDecode(value) : value;
		}
   });
}

if(Ext.form.ComboBox){
   Ext.apply(Ext.form.ComboBox.prototype, {
    	// private
    	onDestroy : function(){
			//VVVVVVVVVVV add by guig //unset listener
	        Ext.getDoc().un('mousewheel', this.collapseIf, this);
	        Ext.getDoc().un('mousedown', this.collapseIf, this);
			//^^^^^^^^^^^ add by guig
	        if(this.view){
	            Ext.destroy(this.view);//"this.view" need run function "destroy" for destroy element. //chang by guig
	        }
	        if(this.list){
				//VVVVVVVVVVV add by guig // unset listeners
	            if (this.innerList) {
	                this.innerList.un('mouseover', this.onViewOver, this);
	                this.innerList.un('mousemove', this.onViewMove, this);
	            }
				//^^^^^^^^^^^ add by guig
	            this.list.destroy();
	        }
	        if (this.dqTask){
	            this.dqTask.cancel();
	            this.dqTask = null;
	        }
	        this.bindStore(null);
	        Ext.form.ComboBox.superclass.onDestroy.call(this);
    	}
   });
}

if(Ext.Component){
   Ext.apply(Ext.Component.prototype, {
	    // private
	    onRender : function(ct, position){
	        if(this.autoEl){
	            if(typeof this.autoEl == 'string'){
	                this.el = document.createElement(this.autoEl);
	            }else{
	                var div = document.createElement('div');
	                Ext.DomHelper.overwrite(div, this.autoEl);
	                this.el = div.firstChild;
	                this._parentDivForAutoEl = div; //for destroy  //add by guig
	            }
	            if (!this.el.id) {
	            	this.el.id = this.getId();
	            }
	        }
	        if(this.el){
	            this.el = Ext.get(this.el);
	            if(this.allowDomMove !== false){
	                ct.dom.insertBefore(this.el.dom, position);
					//VVVVVVVVVVVVVVVV add by guig
		            if (this._parentDivForAutoEl) {//remove the parent div for autoEl
		                Ext.removeNode(this._parentDivForAutoEl);
		                delete this._parentDivForAutoEl;
		            }
					//^^^^^^^^^^^^^^^ add by guig
	            }
	            if(this.overCls) {
	                this.el.addClassOnOver(this.overCls);
	            }
	        }
	    },
	    destroy : function(){
	        if(this.fireEvent("beforedestroy", this) !== false){
	            this.beforeDestroy();
	            if(this.rendered && !Ext.isIE){//if is ie then do this follow! //change by guig
	                this.el.removeAllListeners();
	                this.el.remove();
	                if(this.actionMode == "container"){
	                    this.container.remove();
	                }
	            }
	            this.onDestroy();
	            Ext.ComponentMgr.unregister(this);
	            this.fireEvent("destroy", this);
	            this.purgeListeners();
				//VVVVVVVVVVVVVVVV add by guig
	            if(this.rendered && Ext.isIE){//if is ie, do this after all be done, because the "this.el.remove()" will delete el and el's children in "Ext.removeNode", so child component can't get right element by element's id ("document.getElementById()" can't find element). in ie do "this.el.remove()" after onDestroy.
	                this.el.removeAllListeners();
	                this.el.remove();
	                if(this.actionMode == "container"){
	                    this.container.remove();
	                }
	            }
				//^^^^^^^^^^^^^^^ add by guig
	
				//VVVVVVVVVVVVVVVV add by guig
	            if (this._parentDivForAutoEl) {//remove the parent div for autoEl
	                Ext.removeNode(this._parentDivForAutoEl);
	                this._parentDivForAutoEl = null;
	            }
				//^^^^^^^^^^^^^^^ add by guig
	        }
	    }
   });
}

if(Ext.Toolbar){
   Ext.apply(Ext.Toolbar.prototype, {
	    destroy : function(){
	        if (this.el) {//remove el's AllListeners
	            var el = Ext.get(this.el);
	            Ext.destroy(el);
	        }
	        if(this.td){
	            Ext.removeNode(this.td);
	        }
	    }
   });
}

if(Ext.Window){
	Ext.Window.prototype.beforeDestroy = function(){   
        if(this.el)this.hide();//do hide before destroy
        if(this.doAnchor){
            Ext.EventManager.removeResizeListener(this.doAnchor, this);
            Ext.EventManager.un(window, 'scroll', this.doAnchor, this);
        }        
        Ext.destroy(
            this.focusEl,//destroy this.focusEl //add by guig
            this.resizer,
            this.dd,
            this.proxy,
            this.mask
        );
        Ext.Window.superclass.beforeDestroy.call(this);
	}
}

if(Ext.ux.ManagedIframePanel){
   Ext.apply(Ext.ux.ManagedIframePanel.prototype, {
        beforeDestroy : function() {
            if (this.rendered) {
            	//清空创建在main.层上的组件
		    	_clearCmps(this.getId());
                if (this.tools) {
                    for (var k in this.tools) {
                        Ext.destroy(this.tools[k]);
                    }
                }

                if (this.header && this.headerAsText) {
                    var s;
                    if (s = this.header.child('span'))s.remove(true,true);
                    this.header.update('');
                }

                Ext.each(['iframe', 'shim', 'header', 'topToolbar',
                                'bottomToolbar', 'footer', 'loadMask', 'body',
                                'bwrap'],
                       function(elName) {
                            if (this[elName]) {
                                if (typeof this[elName].destroy == 'function') {
                                    this[elName].destroy();
                                } else {
                                    Ext.destroy(this[elName]);
                                }

                                this[elName] = null;
                                delete this[elName];
                            }
                        }, this);
            }

            Ext.ux.ManagedIframePanel.superclass.beforeDestroy.call(this);
        }
   });
}


if(Ext.data.Store){
   Ext.apply(Ext.data.Store.prototype, {
	    remove : function(record){
	        var index = this.data.indexOf(record);
	        this.data.removeAt(index);
	        //2009-06-19 fuyy 注掉if的判断条件
	        //if(this.pruneModifiedRecords){
	            this.modified.remove(record);
		        if(this.added.indexOf(record) == -1){
		        	this.removed.push(record);
		        }else{
		        	this.added.remove(record);
		        }
	            
	        //}
	          //2009-06-19 fuyy 注掉if的判断条件 end
	        if(this.snapshot){
	            this.snapshot.remove(record);
	        }
	        this.fireEvent("remove", this, record, index);
	    },
	    
	    removeAll : function(){
			for(var i=0;i<this.getCount();i++){
				var record = this.getAt(i);
		        if(this.added.indexOf(record) == -1){
		        	this.removed.push(record);
		        }
			}
	    	
	        this.data.clear();
	        if(this.snapshot){
	            this.snapshot.clear();
	        }
	        //if(this.pruneModifiedRecords){
	        	this.added = [];
	            this.modified = [];
	        //}
	        this.fireEvent("clear", this);
	    },
    
	    insert : function(index, records){
	        records = [].concat(records);
	        for(var i = 0, len = records.length; i < len; i++){
	            this.data.insert(index, records[i]);
	            records[i].join(this);
	            //2009-06-19 fuyy add
	            this.added.push(records[i]);
	            this.modified.push(records[i]);
	        	//2009-06-19 fuyy add end
	        }
	        this.fireEvent("add", this, records, index);
	    },
	    
	    getRemovedRecords : function(){
	        return this.removed;
	    },
	    
	    // private
	    afterReject : function(record){
	        this.modified.remove(record);
	        this.removed.remove(record);
	        this.added.remove(record);
	        this.fireEvent("update", this, record, Ext.data.Record.REJECT);
	    },
	
	    // private
	    afterCommit : function(record){
	        this.modified.remove(record);
	        this.removed.remove(record);
	        this.added.remove(record);
	        this.fireEvent("update", this, record, Ext.data.Record.COMMIT);
	    },
	
	    /**
	     * Commit all Records with outstanding changes. To handle updates for changes, subscribe to the
	     * Store's "update" event, and perform updating when the third parameter is Ext.data.Record.COMMIT.
	     */	    commitChanges : function(){
	        var m = this.modified.slice(0);
	        this.modified = [];
	        for(var i = 0, len = m.length; i < len; i++){
	            m[i].commit();
	        }
	        this.removed = [];
	        this.added = [];
	    },
	
	    /**
	     * Cancel outstanding changes on all changed records.
	     */
	    rejectChanges : function(){
	        var m = this.modified.slice(0);
	        this.modified = [];
	        for(var i = 0, len = m.length; i < len; i++){
	            m[i].reject();
	        }
	        var d = this.removed.slice(0);
	        this.removed = [];
	        for(var i = 0, len = d.length; i < len; i++){
	            d[i].reject();
	        }
	        this.added = [];
	    },
	
	    // private
	    onMetaChange : function(meta, rtype, o){
	        this.recordType = rtype;
	        this.fields = rtype.prototype.fields;
	        delete this.snapshot;
	        this.sortInfo = meta.sortInfo;
	        this.modified = [];
	        this.removed = [];
	        this.added = [];
	        this.fireEvent('metachange', this, this.reader.meta);
	    },
	    
	    sumquery : function(property, value, sumproperty, anyMatch, caseSensitive){
	        var fn = this.createFilterFn(property, value, anyMatch, caseSensitive);
	        var querydata = fn ? this.queryBy(fn) : this.data.clone();
	        
	        var rs = querydata.items, v = 0;
	
	        for(var i = 0; i <= rs.length-1; i++){
	            v += (rs[i].data[sumproperty] || 0);
	        }
	        return v;
	    },
	    
	    sumqueryBy : function(property, sumproperty, fn, scope){
	        var data = this.snapshot || this.data;
	        var querydata = data.filterBy(fn, scope||this);
	        
	        var rs = querydata.items, v = 0;
	
	        for(var i = 0; i <= rs.length-1; i++){
	            v += (rs[i].data[sumproperty] || 0);
	        }
	        return v;
	    }
	    
   });
}

Ext.form.BasicForm.prototype.setValues = function(values){   
      if(values instanceof Array){                
          for(var i = 0, len = values.length; i < len; i++){   
              var v = values[i];   
              var f = this.findField(v.id);   
              if(f){   
                  if ( f.getEl().dom.type == 'radio' ) {   
                      var group = this.el.dom.elements[f.getName()];   
                      for (var i=0; i < group.length; i++ ) {   
                          if(group[i].__ext_field) {   
                              group[i].__ext_field.setValue(group[i].value == v);   
                              if(this.trackResetOnLoad){   
                                  group[i].__ext_field.originalValue = group[i].__ext_field.getValue();   
                              }   
                          }   
                      }   
               }   
                else  
                 {   
                    f.setValue(v.value);   
                  if(this.trackResetOnLoad){   
                        f.originalValue = f.getValue();   
                   }   
               }   
            }   
        }   
    }else{   
       var field, id;   
       for(id in values){   
            if(typeof values[id] != 'function' && (field = this.findField(id))){   
               if( field.getEl().dom.type == 'radio' ) {   
                   var group = this.el.dom.elements[field.getName()];   
                    for (var i=0; i < group.length; i++ ) {   
                        if(group[i].__ext_field) {   
                            group[i].__ext_field.setValue(group[i].value == values[id]);   
                            if(this.trackResetOnLoad){   
                               group[i].__ext_field.originalValue = group[i].__ext_field.getValue();   
                            }   
                       }   
                    }   
               }   
                else  
                {   
                    field.setValue(values[id]);   
                   if(this.trackResetOnLoad){   
                         field.originalValue = field.getValue();   
                    }   
                }   
            }   
         }   
    }   
    return this;   
}   

Ext.form.Radio.prototype.onRender = function(ct, position) {   
  Ext.form.Radio.superclass.onRender.call(this, ct, position);   
   this.el.dom.__ext_field = this;   
}   
  
Ext.form.Radio.prototype.setValue = function(v) {   
    if(v === true || v === 'true' || v == '1' || v === false || v === 'false' || v == '0') {   
   
        // Select all radios of this group   
        var radios = this.el.up('form').select('input[type=radio]');   
  
        // Uncheck all other values   
        for(var i = 0; i < radios.elements.length; i++) {   
            if(radios.elements[i].__ext_field && radios.elements[i].__ext_field != this && radios.elements[i].name == this.el.dom.name)   
            {   
               radios.elements[i].__ext_field.checked = false;   
                   
                // DOM checked is set by the browser   
  
                radios.elements[i].__ext_field.fireEvent("check", this, this.checked);   
           }   
         }   
            
        this.checked = (v === true || v === 'true' || v == '1');   
        if(this.el && this.el.dom) {   
            this.el.dom.checked = this.checked;   
        }   
         this.fireEvent("check", this, this.checked);   
    }   
} 
 
Ext.ux.TabCloseMenu = function(){
    var tabs, menu, ctxItem;
    this.init = function(tp){
        tabs = tp;
        tabs.on('contextmenu', onContextMenu);
    }
    
    function onContextMenu(ts, item, e){
        if (!menu) { // create context menu on first right click
            menu = new Ext.menu.Menu([{
                id: tabs.id + '-close',
                text: '关闭当前标签页',
                handler: function(){
                    tabs.remove(ctxItem);
                }
            }, {
                id: tabs.id + '-close-others',
                iconCls: 'closeOthersTabs',
                text: '关闭其他标签页',
                handler: function(){
                    tabs.items.each(function(item){
                        if (item.closable && item != ctxItem) {
                            tabs.remove(item);
                        }
                    });
                }
            }]);
        }
        ctxItem = item;
        var items = menu.items;
        items.get(tabs.id + '-close').setDisabled(!item.closable);
        var disableOthers = true;
        tabs.items.each(function(){
            if (this != item && this.closable) {
                disableOthers = false;
                return false;
            }
        });
        items.get(tabs.id + '-close-others').setDisabled(disableOthers);
        menu.showAt(e.getPoint());
    }
};

if(Ext.form.ComboBox){
   Ext.apply(Ext.form.ComboBox.prototype, {
	    onTriggerClick : function(){
	        if(this.disabled){//||!this.editable
	            return;
	        }
	        if(this.isExpanded()){
	            this.collapse();
	            this.el.focus();
	        }else {
	            this.onFocus({});
	            if(this.triggerAction == 'all') {
	                this.doQuery(this.allQuery, true);
	            } else {
	                this.doQuery(this.getRawValue());
	            }
	            this.el.focus();
	        }
	    }
   });
}

//Current bug in Radio/Checkbox
// http://extjs.com/forum/showthread.php?t=44603
Ext.override(Ext.form.Checkbox, {
    onRender: function(ct, position){
        Ext.form.Checkbox.superclass.onRender.call(this, ct, position);
        if (this.inputValue !== undefined) {
            this.el.dom.value = this.inputValue;
        }
        //this.el.addClass('x-hidden');
        this.innerWrap = this.el.wrap({
            //tabIndex: this.tabIndex,
            cls: this.baseCls + '-wrap-inner'
        });
        this.wrap = this.innerWrap.wrap({
            cls: this.baseCls + '-wrap'
        });
        this.imageEl = this.innerWrap.createChild({
            tag: 'img',
            src: Ext.BLANK_IMAGE_URL,
            cls: this.baseCls
        });
        if (this.boxLabel) {
            this.labelEl = this.innerWrap.createChild({
                tag: 'label',
                htmlFor: this.el.id,
                cls: 'x-form-cb-label',
                html: this.boxLabel
            });
        }
        //this.imageEl = this.innerWrap.createChild({
        //tag: 'img',
        //src: Ext.BLANK_IMAGE_URL,
        //cls: this.baseCls
        //}, this.el);
        if (this.checked) {
            this.setValue(true);
        }
        else {
            this.checked = this.el.dom.checked;
        }
        this.originalValue = this.checked;
    },
    afterRender: function(){
        Ext.form.Checkbox.superclass.afterRender.call(this);
        //this.wrap[this.checked ? 'addClass' : 'removeClass'](this.checkedCls);
        this.imageEl[this.checked ? 'addClass' : 'removeClass'](this.checkedCls);
    },
    initCheckEvents: function(){
        //this.innerWrap.removeAllListeners();
        this.innerWrap.addClassOnOver(this.overCls);
        this.innerWrap.addClassOnClick(this.mouseDownCls);
        this.innerWrap.on('click', this.onClick, this);
        //this.innerWrap.on('keyup', this.onKeyUp, this);
    },
    onFocus: function(e){
        Ext.form.Checkbox.superclass.onFocus.call(this, e);
        //this.el.addClass(this.focusCls);
        this.innerWrap.addClass(this.focusCls);
    },
    onBlur: function(e){
        Ext.form.Checkbox.superclass.onBlur.call(this, e);
        //this.el.removeClass(this.focusCls);
        this.innerWrap.removeClass(this.focusCls);
    },
    onClick: function(e){
        if (e.getTarget().htmlFor != this.el.dom.id) {
            if (e.getTarget() !== this.el.dom) {
                this.el.focus();
            }
            if (!this.disabled && !this.readOnly) {
                this.toggleValue();
            }
        }
        //e.stopEvent();
    },
    onEnable: Ext.form.Checkbox.superclass.onEnable,
    onDisable: Ext.form.Checkbox.superclass.onDisable,
    onKeyUp: undefined,
    setValue: function(v){
        var checked = this.checked;
        this.checked = (v === true || v === 'true' || v == '1' || String(v).toLowerCase() == 'on');
        if (this.rendered) {
            this.el.dom.checked = this.checked;
            this.el.dom.defaultChecked = this.checked;
            //this.wrap[this.checked ? 'addClass' : 'removeClass'](this.checkedCls);
            this.imageEl[this.checked ? 'addClass' : 'removeClass'](this.checkedCls);
        }
        if (checked != this.checked) {
            this.fireEvent("check", this, this.checked);
            if (this.handler) {
                this.handler.call(this.scope || this, this, this.checked);
            }
        }
    },
    getResizeEl: function(){
        //if(!this.resizeEl){
        //this.resizeEl = Ext.isSafari ? this.wrap : (this.wrap.up('.x-form-element', 5) || this.wrap);
        //}
        //return this.resizeEl;
        return this.wrap;
    }
});

Ext.override(Ext.form.Radio, {
    checkedCls: 'x-form-radio-checked'
});

if(Ext.form.Label){
	Ext.override(Ext.form.Label, {
	    setText: function(t){
	        this.text = t;
	        if(this.rendered){
	            this.el.update(t);
	        }
	    }
	});
}
/*if(Ext.form.DateField){
	Ext.override(Ext.form.DateField, {
		parseDate : function(value){
			this.format='Ymd';
	        if(!value || Ext.isDate(value)){
	            return value;
	        }
	        var v = Date.parseDate(value, this.format);
	        if(!v && this.altFormats){
	            if(!this.altFormatsArray){
	                this.altFormatsArray = this.altFormats.split("|");
	            }
	            for(var i = 0, len = this.altFormatsArray.length; i < len && !v; i++){
	                v = Date.parseDate(value, this.altFormatsArray[i]);
	            }
	        }
	        return v;
	    }
	});
}*/
if(Ext.tree.TreeNodeUI){
	Ext.override(Ext.tree.TreeNodeUI, {
	    onTextChange : function(node, text, oldText){
	        if(this.rendered){
	            this.textNode.innerText = text;
	        }
	    }
	});
}

Ext.layout.SlideLayout = Ext.extend(Ext.layout.FitLayout, {

    deferredRender: false,
    renderHidden: false,
    easing: 'backBoth',
    duration: .5,
    opacity: 1,
    
    setActiveItem: function(itemInt){
    
        if (typeof(itemInt) == 'string') {
            itemInt = this.container.items.keys.indexOf(itemInt);
        }
        else 
            if (typeof(itemInt) == 'object') {
                itemInt = this.container.items.items.indexOf(itemInt);
            }
        var item = this.container.getComponent(itemInt);
        if (this.activeItem != item) {
            if (this.activeItem) {
                if (item && (!item.rendered || !this.isValidParent(item, this.container))) {
                    this.renderItem(item, itemInt, this.container.getLayoutTarget());
                    item.show();
                }
                var s = [this.container.body.getX() - this.container.body.getWidth(), this.container.body.getX() + this.container.body.getWidth()];
                this.activeItem.el.shift({
                    duration: this.duration,
                    easing: this.easing,
                    opacity: this.opacity,
                    x: (this.activeItemNo < itemInt ? s[0] : s[1])
                });
                item.el.setY(this.container.body.getY());
                item.el.setX((this.activeItemNo < itemInt ? s[1] : s[0]));
                item.el.shift({
                    duration: this.duration,
                    easing: this.easing,
                    opacity: 1,
                    x: this.container.body.getX()
                });
            }
            this.activeItemNo = itemInt;
            this.activeItem = item;
            this.layout();
        }
    },
    
    
    renderAll: function(ct, target){
        if (this.deferredRender) {
            this.renderItem(this.activeItem, undefined, target);
        }
        else {
            Ext.layout.CardLayout.superclass.renderAll.call(this, ct, target);
        }
    }
});
Ext.Container.LAYOUTS['slide'] = Ext.layout.SlideLayout;

//http://extjs.com/forum/showthread.php?t=44790
Ext.override(Ext.Editor, {
    doAutoSize: function(){
        if (this.autoSize) {
            var sz = this.boundEl.getSize(), fs = this.field.getSize();
            switch (this.autoSize) {
                case "width":
                    this.setSize(sz.width, fs.height);
                    break;
                case "height":
                    this.setSize(fs.width, sz.height);
                    break;
                case "none":
                    this.setSize(fs.width, fs.height);
                    break;
                default:
                    this.setSize(sz.width, sz.height);
            }
        }
    }
});

if(Ext.tree.TreeSorter){
	Ext.apply(Ext.tree.TreeSorter.prototype, {
	    doSort : function(node){
	    	var p = this.property || "text";
	    	if(typeof(node.attributes[p])=='object')node.sort(this.sortFn);
	    }
    });
}

Ext.util.Format.comboRenderer = function(combo){
    return function(value){
        var record = combo.findRecord(combo.valueField, value);
        return record ? record.get(combo.displayField) : combo.valueNotFoundText;
    }
}

Ext.lib.Ajax.syncRequest = function(method, uri, callback, postData)
{
    var o = this.getConnectionObject();

    if (!o) {
        return null;
    }
    else {
        o.conn.open(method, uri, false); 

        if (this.useDefaultXhrHeader) {
            if (!this.defaultHeaders['X-Requested-With']) {
                this.initHeader('X-Requested-With', this.defaultXhrHeader, true);
            }
        }

        if(postData && this.useDefaultHeader && (!this.hasHeaders || !this.headers['Content-Type'])){
            this.initHeader('Content-Type', this.defaultPostHeader);
        }

        if (this.hasDefaultHeaders || this.hasHeaders) {
            this.setHeader(o);
        }

        o.conn.send(postData || null);
        this.handleTransactionResponse(o, callback);
        return o;
    }
};

Ext.lib.Ajax.request = function(method, uri, cb, data, options) {
    if(options){
        var hs = options.headers;
        if(hs){
            for(var h in hs){
                if(hs.hasOwnProperty(h)){
                    this.initHeader(h, hs[h], false);
                }
            }
        }
        if(options.xmlData){
            if (!hs || !hs['Content-Type']){
                this.initHeader('Content-Type', 'text/xml', false);
            }
            method = (method ? method : (options.method ? options.method : 'POST'));
            data = options.xmlData;
        }else if(options.jsonData){
            if (!hs || !hs['Content-Type']){
                this.initHeader('Content-Type', 'application/json', false);
            }
            method = (method ? method : (options.method ? options.method : 'POST'));
            data = typeof options.jsonData == 'object' ? Ext.encode(options.jsonData) : options.jsonData;
        }
    }

   return this["sync" in options ? "syncRequest" : "asyncRequest"](method, uri, cb, data);
   //这句制定调用的方法，如果sync传递了就调用syncRequest， 否则调用原来的方法asyncRequest
};
