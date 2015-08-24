/**
 * 对通过AJAX请求返回的JSON字符串进行解析
 *
 */
AjaxResponseUtils = Class.create();

/**
 * 与服务器端对应的JSON节点的名称常量
 */
AjaxResponseUtils.JSON_ROOT_NODE = "infolion-json";
AjaxResponseUtils.JSON_MSG_TYPE_NODE = "type";
AjaxResponseUtils.JSON_MSG_FIELD_NODE = "field";
AjaxResponseUtils.JSON_MSG_NODE = "message";

AjaxResponseUtils.prototype = {

	/*------------------ initializer --------------*/
	
	initialize : function(jsonDoc){
		this._root = null;
		
		if (jsonDoc){			
			var jsonObj = jsonDoc.evalJSON();			
			this._root = jsonObj[AjaxResponseUtils.JSON_ROOT_NODE];
			
			//if (this._root == null)
				//alert("服务器返回了非法的JSON字符串文档");
		}
	},		
	/*
	 * 取得root
	 */
	getRoot : function(){
		return this._root;
	},
	/*
	 * 取得提示信息
	 */
	getMessage : function(){
		return this._root[AjaxResponseUtils.JSON_MSG_NODE];
	},
	/**
	 * 取得消息提示类型
	 */
	getMessageType : function(){
		return this._root[AjaxResponseUtils.JSON_MSG_TYPE_NODE];
	},
	/**
	 * 取得消息提示对应的字段
	 */
	getMessageField : function(){
		return this._root[AjaxResponseUtils.JSON_MSG_FIELD_NODE];
	},
	/*
	 * 取得自定义值域
	 */
	getCustomField : function(fieldName){
		return this._root[fieldName];
	}
}