/*
 * Ext JS Library 2.2.1
 * Copyright(c) 2006-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

Ext.onReady(function(){

/*
 * create by 
 * 
 * */  
Ext.YearPicker = Ext.extend(Ext.Panel,{
	height:130,
	width:100,
	border:false,
	bodyStyle:'border:0;',
	initComponent : function(){
        Ext.YearPicker.superclass.initComponent.call(this);
        this.value = this.value ?this.value : new Date().getFullYear();
        this.addEvents(
            'select'
        );
    },
    setValue : function(value){
        var old = this.value;
        this.value = value.clearTime(true);
        if(this.el){
            this.update(this.value);
        }
    },
    getValue : function(){
        return this.value;
    },
    getEl:function(){
    	 return this.el;
    },
    onRender : function(ct, position){ 
    	Ext.YearPicker.superclass.onRender.call(this, ct, position);
    	var buf = ['<table border="0" cellspacing="0" class="x-year-picker"><tr><td td=colspan="2" style="height:5px;"></td></tr>'];
        for(var i = 0; i < 6; i++){
            buf.push(
                i == 0 ?
                '<td class="x-date-mp-ybtn" align="center"><a class="x-date-mp-prev"></a></td><td class="x-date-mp-ybtn" align="center"><a class="x-date-mp-next"></a></td></tr>' :
                '<td class="x-date-mp-year"><a href="#"></a></td><td class="x-date-mp-year"><a href="#"></a></td></tr>'
            );
        }
        buf.push('<tr><td td=colspan="2" style="height:5px;"></td></tr></table>');
        var el = document.createElement('div');        
        el.className='x-unselectable';
        el.style.border="0px;";
        el.innerHTML = buf.join('');
       // alert(ct.id);
        
         this.body.dom.insertBefore(el, position);
        this.el = Ext.get(el);
        this.eventEl = Ext.get(el.firstChild);
        this.mon(this.eventEl, 'click', this.onMonthClick, this);
        //this.mon(this.eventEl, 'dblclick', this.onMonthDblClick, this);
        this.mpYears = this.el.select('td.x-date-mp-year');
       
        this.mpSelYear=this.value;
        this.updateMPYear(this.value);
         
    },
   
    updateMPYear : function(y){
        this.mpyear = y;
        var ys = this.mpYears.elements;
        for(var i = 1; i <= 10; i++){
            var td = ys[i-1], y2;
            if((i%2) == 0){
                y2 = y + Math.round(i * .5);
                td.firstChild.innerHTML = y2;
                td.xyear = y2;
            }else{
                y2 = y - (5-Math.round(i * .5));
                td.firstChild.innerHTML = y2;
                td.xyear = y2;
            }
            this.mpYears.item(i-1)[y2 == this.mpSelYear ? 'addClass' : 'removeClass']('x-date-mp-sel');
        }
    },
    onMonthClick : function(e, t){
        e.stopEvent();
        var el = new Ext.Element(t), pn;
        if((pn = el.up('td.x-date-mp-year', 2))){
            this.mpYears.removeClass('x-date-mp-sel');
            pn.addClass('x-date-mp-sel');
            this.mpSelYear = pn.dom.xyear;
            this.value=pn.dom.xyear;;
            this.fireEvent('select', this, this.value);
        }if(el.is('a.x-date-mp-prev')){
            this.updateMPYear(this.mpyear-10);
        }
        else if(el.is('a.x-date-mp-next')){
            this.updateMPYear(this.mpyear+10);
        }
        
    },
     onMonthDblClick : function(e, t){
        e.stopEvent();
        var el = new Ext.Element(t), pn;
        if((pn = el.up('td.x-date-mp-year', 2))){
             this.value=pn.dom.xyear;
             this.mpSelYear=pn.dom.xyear;
             alert(this.value);
        }
    }
});
Ext.reg('yearpicker', Ext.YearPicker);
Ext.YearPickerCombo = Ext.extend(Ext.form.ComboBox, {
	width:100,
	initList : function() {
		this.list = new Ext.YearPicker({
		     value:this.value,
		     listeners:{
		         select:{scope:this,fn:this.onYearClick}
		     }
		})  
	},
	expand : function() {
		if (!this.list.rendered) {
			//this.list.render(document.body);
			this.list.render(this.container);
			this.innerList = this.list.body;
			this.list.hide();
			 
		}
		this.el.focus();
		this.list.show();
		Ext.getDoc().on('mousewheel', this.collapseIf, this);
    Ext.getDoc().on('mousedown', this.collapseIf, this);
  
	},
	doQuery : function(q, forceAll) {
		this.expand();
	},
	collapseIf : function(e) {
		if (!e.within(this.wrap) && !e.within(this.list.el)) {
			this.collapse();
			this.list.hide();
		}
	},
	onYearClick : function(thiz, value) {
     this.setValue(value)
		 this.collapse();
	}
});
Ext.reg('yearpickercombo', Ext.YearPickerCombo);

 
 
 
/* var pn=new Ext.YearPicker({
 	   value:2009,
 	   
 	   listeners:{
 	   	
 	   	   select:function(){
 	   	   	   alert(1);
 	   	   }
 	   	}
 });
 pn.render(Ext.getBody());*/
 /*
 new Ext.YearPickerCombo({value:2009,
 	   renderTo:Ext.getBody()})
 */


 
});