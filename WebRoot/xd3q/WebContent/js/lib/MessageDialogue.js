/**
#---------------------------------------------------------------------------------------
#           Author: huanghu
#			Email：huanghu@ffcs.cn
#           Msn：huang_hu@msn.com
#			对话框
#---------------------------------------------------------------------------------------
*/

    
function showMessage(msg){
	msg = msg.replace(/(\r\n|\n)/g,"<br>");
    var title = '系统错误';
    if(sys)
    {
        title = sys.error;
    }
    
	var btnOk = {yes:'确定'};
	if(sys)
	{
	    btnOk = {yes:sys.ok};
	}
	Ext.onReady(function(){
        _getMainFrame().Ext.MessageBox.show({
           title: title,
           msg: msg,
           width: 200,
           buttons: btnOk,
           icon: Ext.MessageBox.INFO,
           fn:function(btn){
           		if (btn=='ok'){
           			_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
           		}
           }
       });		
	}); 
}

function showInfo(msg){
	msg = msg.replace(/(\r\n|\n)/g,"<br>");
    var title = '系统提示';
    if(sys)
    {
        title = sys.info;
    }
    
	var btnOk = {yes:'确定'};
	if(sys)
	{
	    btnOk = {yes:sys.ok};
	}
	Ext.onReady(function(){
        _getMainFrame().Ext.MessageBox.show({
           title: title,
           msg: msg,
           buttons: btnOk,
           icon: Ext.MessageBox.INFO
       });		
	}); 
}

function showError(msg){
	msg = msg.replace(/(\r\n|\n)/g,"<br>");
    var title = '系统错误';
    if(sys)
    {
        title = sys.error;
    }
    
	var btnOk = {yes:'确定'};
	if(sys)
	{
	    btnOk = {yes:sys.ok};
	}
	Ext.onReady(function(){       
        _getMainFrame().Ext.MessageBox.show({
           title: title,
           msg: msg,
           buttons: btnOk,
           icon: Ext.MessageBox.ERROR
       });		
	}); 
}

function showWarn(msg){
	msg = msg.replace(/(\r\n|\n)/g,"<br>");
    var title = '系统警告';
    if(sys)
    {
        title = sys.warn;
    }
    
	var btnOk = {yes:'确定'};
	if(sys)
	{
	    btnOk = {yes:sys.ok};
	}
	Ext.onReady(function(){       
        _getMainFrame().Ext.MessageBox.show({
           title: title,
           msg: msg,
           buttons: btnOk,
           icon: Ext.MessageBox.WARNING
       });		
	}); 
}

function showQues(msg){
	msg = msg.replace(/(\r\n|\n)/g,"<br>");
    var title = '系统帮助';
    if(sys)
    {
        title = sys.help;
    }
	Ext.onReady(function(){       
        _getMainFrame().Ext.MessageBox.show({
           title: title,
           msg: msg,
           buttons: btnOk,
           icon: Ext.MessageBox.QUESTION
       });		
	}); 
}

/**
 * 不知道那里用到，暂时不做多语言。
 */
function questionDiag(){
	Ext.onReady(function(){
        _getMainFrame().Ext.MessageBox.show({
           title:'保存修改?',
           msg: '您所关闭的窗口尚未保存. <br />你要保存所做的修改吗?',
           buttons: Ext.MessageBox.YESNOCANCEL,
           fn: showResult,
           icon: Ext.MessageBox.QUESTION
       });
       
	    function showResult(btn){
	    	if (btn=='yes') ;
	    	if (btn=='no') ;
	    	if (btn=='cancel') ;
	    };       
	});	
}