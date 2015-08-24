ExtModalWindowUtil = Class.create();
ExtModalWindowUtil.Ext_Win_List = [];     //虽然调用Ext.WindowMgr.getActive()可以取得当前激活窗口(即Ext会帮我们管理所有弹出的window组件),但是有些窗口不是我们封装的ExtModalWindowUtil,所以设置该变量,能够自己管理弹出的ExtModalWindowUtil
ExtModalWindowUtil.Main_Ext_Win = null;   //用来保存第一层弹出窗口对象，之后凡是第一层弹出窗口，都只是修改该窗口iframe的内容
ExtModalWindowUtil.Main_Dialog_Container_Div_Id = "ext-modal-dialog-win";    //显示弹出窗口div的ID
ExtModalWindowUtil.Return_Value = null;    //存储模态弹出窗口的返回值   


/**
 * 显示Ext的window(模态弹出窗口)
 * 
 * @param title 窗口的标题名称
 * @param url 窗口显示内容的URL
 * @param winOpener 打开extDialog的源窗口
 * @param callback 当关闭该窗口时的回调函数
 * @param options 控制窗口显示的一些属性
 */
ExtModalWindowUtil.show = function(title, url, winOpener, callback, options){
	/*
	 * 为了防止页面没有重新加载，而是使用缓存的页面，所以在URL后加上时间搓
	 */
	url = Utils.getUncacheUrl(url);
	
	var extDialogObj = {};
		extDialogObj._winOpener = winOpener;         //打开extDialog的源窗口
		extDialogObj._callback = callback;          //关闭(隐藏)Ext的window时执行的回调函数
	var extWin = null;
		
	options = options || {};	
	
	if(ExtModalWindowUtil.Main_Ext_Win == null){  //如果Ext的window从来没有打开过，则创建，并且设置该window关闭的方式是隐藏
		Object.extend(options, {title: title, closeAction: 'hide', el: ExtModalWindowUtil.Main_Dialog_Container_Div_Id});
		
		extWin = ExtModalWindowUtil._create(url, options);  //创建ext的window
		extWin.on('hide', ExtModalWindowUtil._hideAfterHandle); //注册一个隐藏ext的window后的事件

		ExtModalWindowUtil.Main_Ext_Win = extWin;
	}else{
		
		if(ExtModalWindowUtil.getActiveExtWin() == null){
			
			ExtModalWindowUtil._reloadIframe(ExtModalWindowUtil.Main_Ext_Win.getId(), url);
						
			var width = options["width"] || 500; 
			var height = options["height"] || 300;
				
			ExtModalWindowUtil.Main_Ext_Win.setWidth(width);
			ExtModalWindowUtil.Main_Ext_Win.setHeight(height);
			ExtModalWindowUtil.Main_Ext_Win.setTitle(title);
							
			extWin = ExtModalWindowUtil.Main_Ext_Win;
			
			
		}else{   //这里执行的是第二层及以后弹出窗口执行的内容，并且设置该window关闭的方式是销毁
			
			Object.extend(options, {title: title, closeAction: 'close'});
			extWin = ExtModalWindowUtil._create(url, options);  //创建ext的window
			
			extWin.on('beforeclose', ExtModalWindowUtil._destroyBeforeHandle); //注册一个关闭ext的window前的事件
			extWin.on('close', ExtModalWindowUtil._destroyAfterHandle); //注册一个关闭ext的window后的事件
		}
	}
	
	extWin.show();                             //显示ext的window
	extWin.center();                           //弹出窗口居中显示

	ExtModalWindowUtil._setGpmsExtDialogObj(extWin, extDialogObj); //将GPMS自己的额外的信息附加到EXT的window对象中
	ExtModalWindowUtil._initExtWin(extWin);	      //初始化当前弹出窗口的信息
}

/**
 * 关闭Ext的window
 */
ExtModalWindowUtil.close = function(){
	var extWin = ExtModalWindowUtil.getActiveExtWin();
	
	if(extWin.closeAction == 'hide')
		extWin.hide();
	else
		extWin.close();
}

/**
 * 如果Ext的window界面已经向服务器提交了内容，就需要调用该函数
 * 主要用于执行当关闭(隐藏)Ext的window时，是否需要执行回调函数
 */
ExtModalWindowUtil.markUpdated = function(){
	var activeExtWin = ExtModalWindowUtil.getActiveExtWin();
		activeExtWin._isExcecuteCallBack = true;
}

/**
 * 设置模态窗口的返回值
 */
ExtModalWindowUtil.setReturnValue = function(returnValue){
	ExtModalWindowUtil.Return_Value = returnValue;
}

/**
 * 取得当前显示(激活)的Ext的window
 */
ExtModalWindowUtil.getActiveExtWin = function(){
	return ExtModalWindowUtil.Ext_Win_List[ExtModalWindowUtil.Ext_Win_List.length-1];
}

/**
 * 取得打开当前显示(激活)的Ext的window的窗口对象
 */
ExtModalWindowUtil.getWinOpener = function(){
	var extWin = ExtModalWindowUtil.getActiveExtWin();
	return ExtModalWindowUtil._getGpmsExtDialogObj(extWin)._winOpener;
}

/**
 * 取得模态窗口的返回值
 */
ExtModalWindowUtil.getReturnValue = function(){
	return ExtModalWindowUtil.Return_Value;
}

/**
 * 初始化弹出窗口
 */
ExtModalWindowUtil._initExtWin = function(extWin){
	
	ExtModalWindowUtil._addExtWin(extWin);     //添加一个extWin弹出窗口

	ExtModalWindowUtil._resetUpdated(extWin);    //将extWin的更新标识重置为初始状态
	ExtModalWindowUtil.setReturnValue(null);   //清空弹出窗口的返回值
}

/**
 * 添加一个extWin弹出窗口
 */
ExtModalWindowUtil._addExtWin = function(extWin){
	ExtModalWindowUtil.Ext_Win_List.push(extWin);
}

/**
 * 移除一个extWin弹出窗口
 */
ExtModalWindowUtil._removeExtWin = function(){
	ExtModalWindowUtil.Ext_Win_List.pop();
}

/**
 * 创建Ext的window对象
 */
ExtModalWindowUtil._create = function(url, options){
	
	var defaultOptions = {
			width:500,
	        height:300,
	        closeAction: 'close',  //当关闭ext的window时，销毁ext的window
	        modal:true,         //模态对话框
	        resizable: false,   //不可调整大小
	        maximizable :true   //允许最大化
		};
		
	Object.extend(defaultOptions, (options || {}));
	
	var result = new Ext.Window(defaultOptions);
		result.html = ExtModalWindowUtil._generateIframe(result.getId(), url);
	
	return result;
}

/**
 * 重置执行回调函数的刷新标识
 */
ExtModalWindowUtil._resetUpdated = function(extWin){
	extWin._isExcecuteCallBack = false;
}

/**
 * 取得执行回调函数的刷新标识
 */
ExtModalWindowUtil._isUpdated = function(extWin){
	return extWin._isExcecuteCallBack;
}

/**
 * 取得自定义的存储在EXT的window对象中的信息
 */
ExtModalWindowUtil._getGpmsExtDialogObj = function(extWin){
	return extWin._gpmsExtDialogObj;
}

/**
 * 设置自定义的信息存储在EXT的window对象中
 */
ExtModalWindowUtil._setGpmsExtDialogObj = function(extWin, customObj){
	extWin._gpmsExtDialogObj = customObj;
}

/**
 * 隐藏ext的window后执行的操作
 */
ExtModalWindowUtil._hideAfterHandle = function(extWin){
	ExtModalWindowUtil._closeBeforeHandle(extWin);
	
	ExtModalWindowUtil._closeAfterHandle(extWin);
}

/**
 * 销毁ext的window前执行的操作
 */
ExtModalWindowUtil._destroyBeforeHandle = function(extWin){
	ExtModalWindowUtil._closeBeforeHandle(extWin);
}

/**
 * 销毁ext的window后执行的操作
 */
ExtModalWindowUtil._destroyAfterHandle = function(extWin){

	ExtModalWindowUtil._closeAfterHandle(extWin);

	ExtModalWindowUtil._setGpmsExtDialogObj(extWin, null);
	extWin = null;
} 

/**
 * 关闭ext的window前执行的操作
 */
ExtModalWindowUtil._closeBeforeHandle = function(extWin){
	
	ExtModalWindowUtil._removeExtWin();  //关闭(隐藏/销毁)当前window窗口前,先从保存extWin列表中移除 

	ExtModalWindowUtil._clearIframeContent(extWin.getId());  //清空iframeContent的内容
}

/**
 * 关闭ext的window后执行的操作
 */
ExtModalWindowUtil._closeAfterHandle = function(extWin){
	ExtModalWindowUtil._executeCallBack(extWin);	
}

/**
 * 执行关闭(隐藏)后的回调函数
 */
ExtModalWindowUtil._executeCallBack = function(extWin){	
	var extDialogObj = ExtModalWindowUtil._getGpmsExtDialogObj(extWin);
	var extDialogCallBack = extDialogObj._callback;
	if(extDialogObj != null && ExtModalWindowUtil._isUpdated(extWin)){
		extDialogCallBack();
	}
}

/**
 * 生成Iframe的ID
 */
ExtModalWindowUtil._generateIframeId = function(winId){
	return "__infolion_window_iframe__" + winId;
}

/**
 * 生成ExtModalWindowUtil中的iframe
 */
ExtModalWindowUtil._generateIframe = function(winId, targetUrl){
	var iframeId = ExtModalWindowUtil._generateIframeId(winId);
	var result = '<iframe id="' + iframeId + '" name="' + iframeId 
				    +'" src="' + targetUrl + '" frameBorder="0" scrolling="auto" width="100%" height="100%"></iframe>';
	return result;
}

/**
 * 清空iframe的内容，避免内存泄露
 */
ExtModalWindowUtil._clearIframeContent = function(winId){

	var iframeEl = $(ExtModalWindowUtil._generateIframeId(winId));
		iframeEl.src = 'javascript:false';
		iframeEl.src = "about:blank";
}

/**
 * 重新加载Iframe的内容
 */
ExtModalWindowUtil._reloadIframe = function(winId, url){
	var iframeEl = $(ExtModalWindowUtil._generateIframeId(winId));
	
	if(iframeEl == null)
		return;
	
	iframeEl.src = url;
	
	return iframeEl;
}

ExtModalWindowUtil.alert = function(title,msg){
	Ext.MessageBox.alert(title,msg);
}