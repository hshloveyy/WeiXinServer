Ext.ux = function(){description: '扩展Ext组件'};

/******开始：扩展简单搜索输入框********************************************************************************************/
/*
 * 功能：扩展简单搜索输入框,配合Ext.grid、Ext.ux.PagingToolbar使用
 * 注释：未扩展前的使用例子../developer/module.js
 */
Ext.ux.SearchField = Ext.extend(Ext.form.TwinTriggerField, {
    id: 'mySearchField',
    validationEvent:false,
    validateOnBlur:false,
    trigger1Class:'x-form-clear-trigger',
    trigger2Class:'x-form-search-trigger',
    hideTrigger1:true,
    width:180,
    hasSearch : false, //判断是否查询状态
    paramName : 'filter', //后台接收查询内容的参数名
    grid: null, //配合的Ext.grid
    
    initComponent : function(){
        Ext.ux.SearchField.superclass.initComponent.call(this);
        this.on('specialkey', function(f, e){
            if(e.getKey() == e.ENTER){
                this.onTrigger2Click();
            }
        }, this);
    },
    
    onTrigger1Click : function(){
        if(this.hasSearch){
            this.el.dom.value = '';
            var o = {start: 0,limit:this.grid.bottomToolbar.pageSize};
            this.grid.store.baseParams = this.grid.store.baseParams || {}; //store.baseParams中添加查询参数
            this.grid.store.baseParams[this.paramName] = '';
            this.grid.store.reload({params:o});
            this.triggers[0].hide();
            this.hasSearch = false;
        }
    },

    onTrigger2Click : function(){
        var v = this.getRawValue();
        if(v.length < 1){
            this.onTrigger1Click();
            return;
        }
        var o = {start: 0,limit:this.grid.bottomToolbar.pageSize};
        this.grid.store.baseParams = this.grid.store.baseParams || {};
        this.grid.store.baseParams[this.paramName] = v;     
        this.grid.store.reload({params:o});
        this.triggers[0].show();
        this.hasSearch = true;
    }
});
/******结束：扩展简单搜索输入框********************************************************************************************/


/******开始：扩展树的checkbox操作函数********************************************************************************************/
/*
 * 功能：扩展树的checkbox操作函数
 * 注释：扩展使用例子../examples/tree/treecheckbox.js
 */
Ext.ux.treecheckbox = {
          /**
           *函数:[递归]renderCheckBox(parentNode) 
           *功能:重新呈现子节点的checkbox状态
           *参数:parentNode 当前节点
           *说明:当收起一个节点的子节点后再展开,子节点的CheckBox都被初始化,该函数解决该问题
           */             
           renderCheckBoxUI : function(parentNode){
                var childNodes = parentNode.childNodes;
                for(var i=0;i<childNodes.length;i++){
                    var childNode = childNodes[i]
                    childNode.ui.checkbox.checked = childNode.attributes.checked; //重新呈现checkbox状态
                    if(!childNode.leaf) this.renderCheckBoxUI(childNode); //递归自己
                }
            },    

          /**
           *函数:getCheckedNodeIDs(tree) 
           *功能:获取以","分隔的node.attributes.checked=true的节点字串
           *参数:tree Ext.tree.TreePanel
           *说明:当收起一个节点的子节点后再展开,子节点的CheckBox都被初始化,该函数解决该问题
           */            
            getCheckedNodeIDs : function(tree){
                var nodes = tree.getChecked();
                var nodeIDs = "";
                for(var i=0;i<nodes.length;i++){
                    if(i==0) nodeIDs = nodes[i].id;
                    else nodeIDs = nodeIDs + "," + nodes[i].id;
                }
                return nodeIDs;
            },
            
           /**
           *函数:[递归]isChildNodesChecked(tree) 
           *功能:判断是否有选中的子节点checked=true
           *参数:parentNode 节点
           *说明:只判断UI层面的checked
           */              
           isChildNodesChecked : function(parentNode){//判断是否有选中的子节点checked=true
                var childNodes = parentNode.childNodes;
                for(var i=0;i<childNodes.length;i++){
                    var childNode = childNodes[i]
                    if ((childNode.ui.checkbox.checked) || (childNode.ui.checkbox.indeterminate)) return false; //有子节点选择中了
                    
                    //selectChildCallback(node.childNodes[i]);
                    if(!childNode.leaf) this.isChildNodesChecked(childNode); //递归自己
                }
                return true; //所有子节点checked=false
            },
            
           /**
           *函数:[递归]isChildNodesAllChecked(tree) 
           *功能:判断是否有选中的所有子节点checked=true
           *参数:parentNode 节点
           *说明:只判断UI层面的checked
           */              
           isChildNodesAllChecked : function(parentNode){//判断是否有选中的子节点checked=true
                var childNodes = parentNode.childNodes;
                for(var i=0;i<childNodes.length;i++){
                    var childNode = childNodes[i]
                    if (!childNode.ui.checkbox.checked) return false; //有子节点没有选中
                    //selectChildCallback(node.childNodes[i]);
                    if(!childNode.leaf) this.isChildNodesAllChecked(childNode); //递归自己
                }
                return true; //所有子节点checked=false
            },
            
          /******************************
           *函数:setChildNodesChecked(parentNode) 
           *功能:遍历并设置节点的所有子节点checkbox:childNode.checked=parentNode.checked
           *参数:parentNode 节点
           *说明:同时设置UI的checkbox和attributes的checked
           ******************************/             
           setChildNodesChecked : function(parentNode){
                //node.expand();
                var childNodes = parentNode.childNodes;
                for(var i=0;i<childNodes.length;i++){
                    var childNode = childNodes[i]
                    
                    //选中的节点,但收起子节点再展开时候,子节点的ui选中状态丢失,但attributes中状态不变
                    childNode.ui.checkbox.checked = parentNode.ui.checkbox.checked;
                    childNode.ui.checkbox.indeterminate = parentNode.ui.checkbox.indeterminate;
                    childNode.attributes.checked = parentNode.ui.checkbox.checked; //
                    //selectChildCallback(node.childNodes[i]);
                    if(!childNode.leaf) childNode.expand(false,true,this.setChildNodesChecked(childNode)); //递归自己
                }
            },
          
          /******************************
           *函数:setParentNodesChecked(childNode) 
           *功能:遍历并设置节点的所有父节点checkbox
           *参数:childNode,tree 节点,节点的树
           *说明:同时设置UI的checkbox和attributes的checked
           ******************************/             
           setParentNodesChecked : function(childNode,tree){
                if(tree.rootVisible){//根节点显示状态
                    if(tree.getRootNode().id == childNode.id) return; //根节点  
                }else{//根节点隐藏状态
                    if(tree.getRootNode().id == childNode.parentNode.id) return; //根节点 
                }
                
                var parentNode = childNode.parentNode;
                
		        var chkselall = true;
		        var chknosel = true;
        
	            if(chkselall)chkselall = this.isChildNodesAllChecked(parentNode);
	            if(chknosel)chknosel = this.isChildNodesChecked(parentNode);
            
	            if (chkselall) {
	            	parentNode.ui.checkbox.checked = true;
	                parentNode.ui.checkbox.indeterminate = false;
	                parentNode.attributes.checked = true;
	            }
	            if (chknosel) {
	            	parentNode.ui.checkbox.checked = false;
	                parentNode.ui.checkbox.indeterminate = false;
	                parentNode.attributes.checked = false;
	            }
	            if(!chkselall&&!chknosel) {
	            	parentNode.ui.checkbox.checked = false;
	            	parentNode.ui.checkbox.indeterminate = true;
	            	parentNode.attributes.checked = false;
	            }
	            
                this.setParentNodesChecked(parentNode,tree);
            }            
};
/******结束：扩展树的checkbox操作函数********************************************************************************************/

// Add the additional 'advanced' VTypes
Ext.apply(Ext.form.VTypes, {
  daterange: function(val, field) {
    var date = field.parseDate(val);
    
    // We need to force the picker to update values to recaluate the disabled dates display
    var dispUpd = function(picker) {
      var ad = picker.activeDate;
      picker.activeDate = null;
      picker.update(ad);
    };
    
    if (field.startDateField) {
      var sd = Ext.getCmp(field.startDateField);
      sd.maxValue = date;
      if (sd.menu && sd.menu.picker) {
        sd.menu.picker.maxDate = date;
        dispUpd(sd.menu.picker);
      }
    } else if (field.endDateField) {
      var ed = Ext.getCmp(field.endDateField);
      ed.minValue = date;
      if (ed.menu && ed.menu.picker) {
        ed.menu.picker.minDate = date;
        dispUpd(ed.menu.picker);
      }
    }
    /* Always return true since we're only using this vtype
     * to set the min/max allowed values (these are tested
     * for after the vtype test)
     */
    return true;
  },
  
  password: function(val, field) {
    if (field.initialPassField) {
      var pwd = Ext.getCmp(field.initialPassField);
      return (val == pwd.getValue());
    }
    return true;
  },
  
  passwordText: '密码不匹配'
});

//格式化人民币
Ext.util.Format.zhMoney = function(v){
    v = (Math.round((v-0)*100))/100;
    v = (v == Math.floor(v)) ? v + ".00" : ((v*10 == Math.floor(v*10)) ? v + "0" : v);
    v = String(v);
    var ps = v.split('.');
    var whole = ps[0];
    var sub = ps[1] ? '.'+ ps[1] : '.00';
    var r = /(\d+)(\d{3})/;
    while (r.test(whole)) {
        whole = whole.replace(r, '$1' + ',' + '$2');
    }
    v = whole + sub;
    if(v.charAt(0) == '-'){
        return '-￥' + v.substr(1);
    }
    return "￥" +  v;
}