/**
 * Ajax请求引擎。
 * 该引擎是继承prototype中的Ajax.Request类，对请求的响应进行拦截，统一验证响应的后，再交由客户
 * 的callback处理。
 * 
 * 该引擎只允许客户端程序通过Ajax请求的onComplete事件执行自己的callback。
 *
 */
AjaxEngine = Class.create();

AjaxEngine.prototype = Object.extend(AjaxEngine.prototype, Ajax.Request.prototype);
AjaxEngine.prototype = Object.extend(AjaxEngine.prototype,{
	
	/*------------------ initializer --------------*/
	
	initialize : function(url, options){
		this.transport = Ajax.getTransport();
		if (options.method)
			options.method = options.method.toLowerCase();
		
    	this._setOptions(options);
   		this._request(url);

    	if (!this.options.asynchronous){
    		if (!this._validateResponse()){
    			this.transport = null;
    			return ;
    		}
    	}
	},
	
	/*------------ private methods ---------------------*/
	
	_request : function(url){
		if (this.options.asynchronous && this.options.showProcessing){
			var ProcessingHint = new MessageBoxUtil();
			ProcessingHint.waitShow();
		}
//			ProcessingHint.showProcessing();
			
		this.request(url);
	},
	
	/**
	 * 设置请求选项，设置处理响应的拦截器。
	 * 
	 * @param Array options
	 */
	_setOptions : function(options){
		
		var showProcessing = true;
		var _options = Object.extend({showProcessing : true}, (options || {}));
		
		
		this.setOptions(_options);
		
		/*
		 * 只允许设置onComplete方法，屏蔽其它事件响应
		 */
		this.options.onUninitialized = null;
		this.options.onLoading = null;
		this.options.onLoaded = null;
		this.options.onInteractive = null;
		
		this._onComplete = this.options.onComplete || Prototype.emptyFunction;
		
		/*
		 * 设置onComplete方法，调用拦截器处理响应
		 */
		this.options.onComplete = this._intercept.bind(this);
	},
	
	/**
	 * 处理响应的拦截器。
	 * 对所返回的响应，进行统一验证，比如是否已登录的验证，并采取统一处理方式。
	 * 
	 */
	_intercept : function(){
			
		if (!this._validateResponse()){
			return ;
		}
		
		/**
		 * 执行响应中的脚本
		 */
		this._evalScripts();

		if (Utils.isFunction(this._onComplete))
			this._onComplete(this.transport,this.options.callback);
		if (this.options.showProcessing){
			var ProcessingHint = new MessageBoxUtil();
			ProcessingHint.close();
		}
	},
	
	/**
	 * 执行中的javascript脚本
	 * 
	 */
	_evalScripts : function(){
    	if (this.options.evalScripts) {
      		Utils.evalScripts(this.transport.responseText);
    	}
	},
	
	/**
	 * 对响应进行统一验证，做出处理
	 * 
	 * @return bool
	 */
	_validateResponse : function(){
		var reponseStr = this.transport.responseText;
		
		if(reponseStr == null || reponseStr.trim() == "")
			return;
		return true;
	}
});