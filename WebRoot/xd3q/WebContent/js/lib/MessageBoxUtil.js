function MessageBoxUtil(){
	var messageBox = new Object;
	var waitMsg = '正在处理您的请求，请稍侯...';
    var progressText  = '请稍侯...';

    if(sys)
    {
        progressText = sys.progressText;
        waitMsg = sys.waitMsg;
    }    
    
	messageBox.show = function(showtype,msg){
		var MessageBoxType = '';
		var Title = '';
		var Message = msg;
	
		if (showtype == 'info'){
			MessageBoxType = Ext.MessageBox.INFO;
			Title = '系统提示';
		    if(sys)
		    {
		        Title = sys.info;
		    }
		}
		
		if (showtype == 'quest'){
			MessageBoxType = Ext.MessageBox.QUESTION;
			Title = '问题';
		}
		
		if (showtype == 'warn'){
			MessageBoxType = Ext.MessageBox.WARNING;
			Title = '系统警告';
            if(sys)
            {
                Title = sys.warn;
            }
		}
		
		if (showtype == 'error'){
			MessageBoxType = Ext.MessageBox.ERROR;
			Title = '系统错误';
            if(sys)
            {
                Title = sys.error;
            }
		}
        
		var btnOk = {yes:'确定'};
	    if(sys)
	    {
	        btnOk = {yes:sys.ok};
	    }
		Ext.MessageBox.show({
           title: Title,
           msg: Message,
           buttons: btnOk,
           icon: MessageBoxType
       });
	},
	
	messageBox.waitShow = function(){
		Ext.MessageBox.show({
           msg: waitMsg,
           progressText: progressText,
           width:300,
           wait:true,
           waitConfig: {interval:200}
       });
	}
	
	messageBox.Close = function(){
		Ext.MessageBox.hide();
	}
	
	return messageBox;
}
