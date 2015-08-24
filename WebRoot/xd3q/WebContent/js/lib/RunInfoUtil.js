/**
 * 用来控制运行信息
 *
 */ 
RunInfoUtil = Class.create();

RunInfoUtil.prototype = {
	
	/*------------------ initializer --------------*/
	
	initialize : function(containerId, imageSrc, title, options){
		
		this._title = title || "运行信息";
		this.containerId = containerId;
		this._infoImageSrc = imageSrc;
		
		this._options = {
			boxClass : "div-run-info-container",	//运行信息样式
			titleClass : "div-run-info-title",		//运行信息标题样式
			contentClass : "div-run-info-content",	//运行信息主体样式
			hintClass : "emphasis"                  //需要提醒用户的信息样式
		};
		
		Object.extend(this._options, (options || {}));
	},
	
	/*------------ public methods ---------------------*/
	
	show : function(msg, styleCss){
		this._show(msg, styleCss);
	},
	
	showError : function(msg, styleCss){
		
		styleCss = styleCss || this._options.hintClass;
		this._show(msg, styleCss);
	},
	
	hide : function(){
		var runInfoConsole = $(this._generateBoxId());
		if(!runInfoConsole)
			return;
		this._setMsg("&nbsp;");
		Element.hide(this.containerId);	
	},
	
	setInfoImage : function(imageSrc){
		this._infoImageSrc = imageSrc;
	},
	
	/*------------ private methods ---------------------*/
	
	_show : function(msg, styleCss){
		var runInfoConsole = $(this._generateBoxId());
		if(!runInfoConsole)
			$(this.containerId).appendChild(this._createRunInfo());
	
		this._setMsg(msg, styleCss);
		
		Element.show(this.containerId);	
	},
	
	_createRunInfo : function(){
		var runInfoBox = document.createElement("DIV");
			runInfoBox.className = this._options.boxClass;
			runInfoBox.id = this._generateBoxId();
			
			runInfoBox.appendChild(this._createHeader());
			runInfoBox.appendChild(this._createContent());
			
		return runInfoBox;
	},
	
	_createHeader : function(){
		var header = document.createElement("DIV");
			header.className = this._options.titleClass;
			
			header.innerHTML = this._title;
			
		return header;
	},
	
	_createContent : function(){
		var content = document.createElement("DIV");
			content.className = this._options.contentClass;
			
			if(this._infoImageSrc)
				content.appendChild(this._createInfoImage());
				
			content.appendChild(this._createMsg());
			
		return content;
	},
	
	_createInfoImage : function(){
		var img = document.createElement("img");
			img.src = this._infoImageSrc;
					
		return img;
	},
	
	_createMsg : function(){
		var spanObj = document.createElement("span");
			spanObj.id = this._generateMsgId();
					
		return spanObj;
	},
	
	_setMsg : function(msg, styleCss){
		var msgObj = $(this._generateMsgId());
		msgObj.innerHTML = msg;
		msgObj.className = styleCss || "";
	},
	
	_generateMsgId : function(){
		return "__run_info_msg_id__";
	},
	
	_generateBoxId : function(){
		return "__run_info_console_id__";
	}
}