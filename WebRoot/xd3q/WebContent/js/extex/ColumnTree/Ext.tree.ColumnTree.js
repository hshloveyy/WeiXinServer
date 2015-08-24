/*
 * Ext JS Library 2.0
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */


Ext.tree.ColumnTree = Ext.extend(Ext.tree.TreePanel, {
    lines:false,
    borderWidth: Ext.isBorderBox ? 0 : 2, // the combined left/right border for each cell
    cls:'x-column-tree',
    scrollOffset : 18,
    objectName:null,
    leafConditions:null,
    added: [],
    modified: [],
    removed: [],
    editable:true,
    onRender : function(){
        Ext.tree.ColumnTree.superclass.onRender.apply(this, arguments);
        this.headers = this.body.createChild(
            {cls:'x-tree-headers'},this.innerCt.dom);
       // this.headers = this.getEl().createChild(                                             
        //    {cls:'x-tree-headers'},this.body); 
            
        var cols = this.columns, c;
        var totalWidth = 0;

        if(cols){
	        for(var i = 0, len = cols.length; i < len; i++){
	             c = cols[i];
	             totalWidth += c.width;
	             var align = c.headeralign ? c.headeralign:'left';
	             this.headers.createChild({
	                 cls:'x-tree-hd ' + (c.cls?c.cls+'-hd':''),
	                 cn: {
	                     cls:'x-tree-hd-text',
	                     style:'text-align: '+align,
	                     html: c.header
	                 },
	                 style:'width:'+(c.width-this.borderWidth)+'px;'  //text-align:'+c.align ? c.align:'left'+';
	             });
	        }
        }
        
        this.headers.createChild({
             cls:'x-tree-hd ',
             cn: {
                 html: ''
             },
             style:'width:'+this.scrollOffset+'px;'
         });
        totalWidth += this.scrollOffset;
        
        this.headers.createChild({cls:'x-clear'});
        // prevent floats from wrapping when clipped
        this.headers.setWidth(totalWidth);
        totalWidth -= this.scrollOffset;
		//this.innerCt.setWidth(totalWidth);
    },
    fixScroll: function () {
        // Only fix the height once when loading data, if not the component will shrink every time a node is expanded
        if (!this.isFixed()) this.body.setHeight(this.body.getHeight() - 22);
        this.setFixed(true);
    },
    // private
    initEvents : function(){
        Ext.tree.ColumnTree.superclass.initEvents.call(this);
        
        this.addEvents(
        	'addnode',
        	'updatenode',
        	'removenode',
        	'commitchanges',
        	'iconclick'
        );
        
	    this.on('removenode', function(node,o){
	    	if(node.isLeaf()||o){
		        if(this.modified.indexOf(node) != -1){
		            this.modified.remove(node);
		        }
				if(this.added.indexOf(node) == -1){
					this.removed.push(node);
				}else{
					this.added.remove(node);
				}
	    	}
    		if(node.hasChildNodes()){
				var ns = node.childNodes;
				if(ns || ns.length>0){
					for(var j=0;j<ns.length;j++){
				        if(this.modified.indexOf(ns[j]) != -1){
				            this.modified.remove(ns[j]);
				        }
						if(this.added.indexOf(ns[j]) == -1){
							this.removed.push(ns[j]);
						}else{
							this.added.remove(ns[j]);
						}
					}
				}
    		}
	    });	

	    this.on('updatenode', function(node,o){
	    	if(node.isLeaf()||o){
		        if(this.modified.indexOf(node) == -1){
		            this.modified.push(node);
		            node.ui.addClass("x-tree-modified");
		            
		            var tree = node.getOwnerTree();
		            if(tree.leafConditions&&eval(tree.leafConditions))node.leaf = true;
		            if(tree.leafConditions&&!eval(tree.leafConditions)) node.leaf = false;
		            node.ui.updateExpandIcon();
		        }
	    	}
    		if(node.hasChildNodes()&&!o){
				var ns = node.childNodes;
				if(ns || ns.length>0){
					for(var j=0;j<ns.length;j++){
				        if(this.modified.indexOf(ns[j]) == -1){
				            this.modified.push(ns[j]);
				            ns[j].ui.addClass("x-tree-modified");
				        }
					}
				}
    		}
	    });	 
	    
	    this.on('addnode', function(node,o){
	    	if(node.isLeaf()||o){
		        if(this.added.indexOf(node) == -1){
		            this.added.push(node);
		            this.modified.push(node);
		            node.ui.addClass("x-tree-modified");
		        }
	    	}
    		if(node.hasChildNodes()&&!o){
				var ns = node.childNodes;
				if(ns || ns.length>0){
					for(var j=0;j<ns.length;j++){
				        if(this.added.indexOf(ns[j]) == -1){
				            this.added.push(ns[j]);
				            this.modified.push(ns[j]);
				            ns[j].ui.addClass("x-tree-modified");
				        }
					}
				}
    		}
	    });
	    
	    this.on('commitchanges', function(){
	    	for(var i=0;i<this.added.length;i++){
	    		this.added[i].ui.removeClass("x-tree-modified");
	    	}
	    	for(var i=0;i<this.modified.length;i++){
	    		this.modified[i].ui.removeClass("x-tree-modified");
	    	}	    	
	    	this.added = [];
	    	this.modified = [];
	    	this.removed = [];
	    });
	    
	    this.on('checkchange', function(node, c){
	    	if(!node.isExpanded())node.expand();
	    });	    
    },
    
    updateAllNodes : function(node, o){
    	var nodes = node.childNodes;
    	for (var i=0;i<nodes.length;i++){
    		if(o||(!nodes[i].hasChildNodes()&&!o)){
            	this.modified.push(nodes[i]);
            	nodes[i].ui.addClass("x-tree-modified");
    		}
            if(nodes[i].hasChildNodes())this.updateAllNodes(nodes[i]);
    	}
    },
    
    getModifiedRecords : function(){
    	return this.modified;
    },
    
    getRemovedRecords : function(){
    	return this.removed;
    }
});

Ext.reg('columntree', Ext.tree.ColumnTree);

Ext.tree.ColumnTreeNodeUI = Ext.extend(Ext.tree.TreeNodeUI, {
    focus: Ext.emptyFn, // prevent odd scrolling behavior
    renderElements : function(n, a, targetNode, bulkRender){
        this.indentMarkup = n.parentNode ? n.parentNode.ui.getChildIndent() : '';

        var t = n.getOwnerTree();
        var cols = t.columns;
        var bw = t.borderWidth;
        var c = cols[0];
		
		var cb = typeof a.checked == 'boolean';
		if(typeof this.checkModel != 'undefined'){
			cb = (!this.onlyLeafCheckable || n.isLeaf());
		}
		var href = a.href ? a.href : Ext.isGecko ? "" : "#";
        var buf = ['<li class="x-tree-node"><div ext:tree-node-id="',n.id,'" class="x-tree-node-el x-tree-node-leaf x-unselectable ', a.cls,'" unselectable="on">',
                '<div class="x-tree-col" style="width:',c.width-bw,'px;">',
                    '<span class="x-tree-node-indent">',this.indentMarkup,"</span>",
                    '<img src="', this.emptyIcon, '" class="x-tree-ec-icon x-tree-elbow">',
                    '<img src="', a.icon || this.emptyIcon, '" class="x-tree-node-icon',(a.icon ? " x-tree-node-inline-icon" : ""),(a.iconCls ? " "+a.iconCls : ""),'" unselectable="on">',
                    cb ? ('<input class="x-tree-node-cb" type="checkbox" ' + (a.checked ? 'checked="checked" />' : '/>')) : '',
                    '<a hidefocus="on" class="x-tree-node-anchor" href="',href,'" tabIndex="1" ',
                    a.hrefTarget ? ' target="'+a.hrefTarget+'"' : "", '>',
                    '<span unselectable="on" ext:qtip="', n.text || (a[c.dataIndex]?(c.renderer ? c.renderer(a[c.dataIndex], n, a) : a[c.dataIndex]):''),'">', n.text || (a[c.dataIndex]?(c.renderer ? c.renderer(a[c.dataIndex], n, a) : a[c.dataIndex]):''),"</span></a>",
                "</div>"];
         for(var i = 1, len = cols.length; i < len; i++){
             c = cols[i];
             buf.push('<div class="x-tree-col ',(c.cls?c.cls:''),'" style="width:',c.width-bw,'px;">',
                        '<span class="x-tree-col-text"  ext:qtip="',(a[c.dataIndex]?(c.renderer ? c.renderer(a[c.dataIndex], n, a) : a[c.dataIndex]):''),'" style="text-align:',c.align ? c.align:'left',';">',(a[c.dataIndex]?(c.renderer ? c.renderer(a[c.dataIndex], n, a) : a[c.dataIndex]):''),"</div>",
                      "</span>");
         }
         buf.push('<div class="x-clear"></div>',
            '</div>',
            '<ul class="x-tree-node-ct" style="display:none;"></ul>',
            "</li>");

        if(bulkRender !== true && n.nextSibling && n.nextSibling.ui.getEl()){
            this.wrap = Ext.DomHelper.insertHtml("beforeBegin",
                                n.nextSibling.ui.getEl(), buf.join(""));
        }else{
            this.wrap = Ext.DomHelper.insertHtml("beforeEnd", targetNode, buf.join(""));
        }

        this.elNode = this.wrap.childNodes[0];
        this.ctNode = this.wrap.childNodes[1];
        var cs = this.elNode.firstChild.childNodes;

        this.indentNode = cs[0];
        this.ecNode = cs[1];
        this.iconNode = cs[2];
        
        var index = 3;
        if(cb){
            this.checkbox = cs[3];
            index++;
        }
        this.anchor = cs[index];
        this.textNode = cs[index].firstChild;
        this.columnNode = [];
        this.columnNode.push(cs[index].firstChild);
        for(var i = 1, len = cols.length; i < len; i++){
        	var ot = this.elNode.childNodes[i];
        	this.columnNode.push(ot.firstChild);
        }
        
		Ext.fly(this.iconNode).on('click', function(){
			if(t.editable)t.fireEvent('iconclick', n);
		});
    },
    
    onDblClick : function(e){
        e.preventDefault();
        if(this.disabled){
            return;
        }
        if(this.checkbox&&!this.checkbox.disabled){
            this.toggleCheck();
        }
        if(!this.animating && this.node.isExpandable()){
            this.node.toggle();
        }
        this.fireEvent("dblclick", this.node, e);
    }    
});


Ext.ux.ColumnTreeCheckNodeUI = function() {
	// 'multiple'(Ĭ��)
	//'single'
	//'cascade';'parentCascade';'childCascade'
	this.checkModel = 'multiple';
	
	//only leaf can checked
	this.onlyLeafCheckable = false;
	
	Ext.ux.ColumnTreeCheckNodeUI.superclass.constructor.apply(this, arguments);
};

Ext.extend(Ext.ux.ColumnTreeCheckNodeUI, Ext.tree.ColumnTreeNodeUI, {
    
    renderElements : function(n, a, targetNode, bulkRender){
    	var t = n.getOwnerTree();
		this.checkModel = t.checkModel || this.checkModel;
		this.onlyLeafCheckable = t.onlyLeafCheckable || false;
    	
    	Ext.ux.ColumnTreeCheckNodeUI.superclass.renderElements.apply(this, arguments);
		
		var cb = (!this.onlyLeafCheckable || n.isLeaf());
        if(cb){
            Ext.fly(this.checkbox).on('click', this.check.createDelegate(this,[null]));
        }
    },
    
    // private
    check : function(checked){
        var n = this.node;
		var tree = n.getOwnerTree();
		this.checkModel = tree.checkModel || this.checkModel;
		
		if( checked === null ) {
			checked = this.checkbox.checked;
		} else {
			this.checkbox.checked = checked;
		}
		
		n.attributes.checked = checked;
		tree.fireEvent('check', n, checked);
		
		if(!this.onlyLeafCheckable){
			if(this.checkModel == 'cascade' || this.checkModel == 'parentCascade'){
				var parentNode = n.parentNode;
				if(parentNode !== null) {
					this.parentCheck(parentNode,checked);
				}
			}
			if(this.checkModel == 'cascade' || this.checkModel == 'childCascade'){
				if( !n.expanded && !n.childrenRendered ) {
					n.expand(false,false,this.childCheck);
				}else {
					this.childCheck(n);  
				}
			}
		} else if(this.checkModel == 'single'){
			var checkedNodes = tree.getChecked();
			for(var i=0;i<checkedNodes.length;i++){
				var node = checkedNodes[i];
				if(node.id != n.id){
					node.getUI().checkbox.checked = false;
					node.attributes.checked = false;
					tree.fireEvent('check', node, false);
				}
			}
		}
    },
    
    // private
	childCheck : function(node){
		var a = node.attributes;
		if(!a.leaf) {
			var cs = node.childNodes;
			var csui;
			for(var i = 0; i < cs.length; i++) {
				csui = cs[i].getUI();
				if(csui.checkbox.checked ^ a.checked)
					csui.check(a.checked);
			}
		}
	},
	
	// private
	parentCheck : function(node ,checked){
		var checkbox = node.getUI().checkbox;
		if(typeof checkbox == 'undefined')return ;
		if(!(checked ^ checkbox.checked))return;
		if(!checked && this.childHasChecked(node))return;
		checkbox.checked = checked;
		node.attributes.checked = checked;
		node.getOwnerTree().fireEvent('check', node, checked);
		
		var parentNode = node.parentNode;
		if( parentNode !== null){
			this.parentCheck(parentNode,checked);
		}
	},
	
	// private
	childHasChecked : function(node){
		var childNodes = node.childNodes;
		if(childNodes || childNodes.length>0){
			for(var i=0;i<childNodes.length;i++){
				if(childNodes[i].getUI().checkbox.checked)
					return true;
			}
		}
		return false;
	},
	
    toggleCheck : function(value){
    	var cb = this.checkbox;
        if(cb){
            var checked = (value === undefined ? !cb.checked : value);
            this.check(checked);
        }
    }
});