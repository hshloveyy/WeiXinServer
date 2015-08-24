/**  
 * Ext JS Library 2.0 extend  
 * Version : 1.0 
 * Author : Jay Lue
 */

//PrintButton temporary data
PrintButton = Class.create();
PrintButton.BusinessId = '';
PrintButton.ClassName = '';
PrintButton.BoName = '';
PrintButton.MethodName = '';

/**
 * set temporary data
 */
PrintButton.setTemporaryData = function(businessId , className , boName, methodName){
	PrintButton.BusinessId = businessId;
	PrintButton.ClassName = className;
	PrintButton.BoName = boName;
	PrintButton.MethodName = methodName;
};

/**
 * clear temporary data
 */
PrintButton.clearTemporaryData = function(){
	PrintButton.BusinessId = '';
	PrintButton.ClassName = '';
	PrintButton.BoName = '';
	PrintButton.MethodName = '';
};

/**
 * 询问是否只有一个模板后的响应处理
 */
PrintButton.queryCallback = function(transport){
	var jsonObj=transport.responseText.evalJSON();
	var root = jsonObj[AjaxResponseUtils.JSON_ROOT_NODE];
	if(root.templateId && root.templateNum == 1){
		PrintButton.print(root.templateId,PrintButton.BusinessId,PrintButton.ClassName,PrintButton.BoName);
	}
	else if(root.templateNum<=0){
		_getMainFrame().ExtModalWindowUtil.alert(Ext.PrintButton.txtExcelTempNotAppoint);
	}
	else{
		_getMainFrame().ExtModalWindowUtil.show(Ext.PrintButton.txtSelectPrntTemp,
				contextPath+'/excelTemplateController.spr?action=_tempSelect&boName='+PrintButton.BoName+'&methodName='+PrintButton.MethodName,'',PrintButton.selectCallback,{width:500,height:365});		
	}
};

/**
 * 用户选择模板后向服务器提交打印请求
 */
PrintButton.selectCallback = function(){
	//取得选择的模板文件id
	var templateId = _getMainFrame().ExtModalWindowUtil.getReturnValue();
	PrintButton.print(templateId,PrintButton.BusinessId,PrintButton.ClassName,PrintButton.BoName);
};

PrintButton.print = function(templateId,businessId,className,boName){
	var strurl = contextPath + '/excelTemplateController.spr?action=_print';
	strurl += '&templateId=' + templateId + '&businessId=' + businessId + '&className=' + className+'&boName='+boName;
	//clear temporary data
	PrintButton.clearTemporaryData();
	window.open(strurl,'','top=0, left=0,width=0,height=0,toolbar=no,scrollbars=no,menubar=no,screenX=0,screenY=0,resizable=no,location=no, status=no');	
	
}

Ext.PrintButton = Ext.extend(Ext.Button, {
	/**
	 * 要打印的业务对象对应的类名
	 */
	className : '',
	/**
	 * 要打印的业务对象ID
	 */
	businessId : '',
	/**
	 * 业务对象名
	 */
	boName : '',
	/**
	 * 方法名
	 */
	methodName : '',
    initComponent : function(){   
        Ext.PrintButton.superclass.initComponent.call(this);
        if(Utils.isEmpty(this.getText()) ){
        	this.setText(this.txtPrint);
        }
        this.setHandler(this._printAction,this);
    },
    /**
	 * 打印动作
	 */
	_printAction : function(){
		PrintButton.setTemporaryData(this.businessId,this.className,this.boName,this.methodName);
		//向服务器询问是否可以直接打印
		var param = '&businessId='+this.businessId+'&className='+this.className+'&boName='+this.boName+'&methodName='+this.methodName;
		new AjaxEngine(contextPath + '/excelTemplateController.spr?action=_queryPrint', 
			{method:"post", parameters: param, onComplete: callBackHandle,callback: PrintButton.queryCallback});	
	},
	/**
	 * 打印
	 * @type String
	 */
	txtPrint:"Print"
});

/**
 * 选择打印模板
 * 
 * @type String
 */
Ext.PrintButton.txtSelectPrntTemp="Select Print Template";
/**
 * 所选择的业务对象未指定Excel模板!
 * @type String
 */
Ext.PrintButton.txtExcelTempNotAppoint="The selected business object is not specified Excel template!";

Ext.ComponentMgr.registerType('printbutton',Ext.PrintButton);