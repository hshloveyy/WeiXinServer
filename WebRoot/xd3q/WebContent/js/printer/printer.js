

function doMyPrint(action,way,width,height){
	try{
		myreport = new Object();
		myreport.print_settings = new Object();
		myreport.orientation=way;
		myreport.pageWidth=width;
		myreport.pageHeight=height;
		myreport.print_settings_id="mysettings";
		myreport.page_div_prefix="";
		myreport.documents =document; // 打印页面div存在本页面中
		myreport.copyrights ='杰创软件拥有版权 www.jatools.com';
		
		//直接打印
		
		if(action==1){
			jatoolsPrinter.print(myreport,false);
	    }
		//打印设置
		else if(action==2){
			jatoolsPrinter.print(myreport,true);
	    }
		//打印预览
		else{
			jatoolsPrinter.printPreview(myreport);
		}
	}
	catch(e)
	{
		if(confirm('您可能未正确安装打印控件，是否使用普通打印来打印本页?')){
			window.print();
	    }
	}
}


//打印功能列表
function insertButton(way,width,height)
{
	var printer = '<OBJECT style="display:none;" ID="jatoolsPrinter" CLASSID="CLSID:B43D3361-D975-4BE2-87FE-057188254255" codebase="jatoolsP.cab#version=1,2,0,2"></OBJECT>';
	var button =''
			+'<table cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable" align="center"><tr><td colspan=10 align="center"><br></td></tr>'
			+'<tr>'
			+'<td align=center><input type=button value=控件安装 onclick=window.open(\'/js/printer/printer.html\') /></td>'
			+'<td align=center><input type=button value=打印设置 onclick=doMyPrint(2,'+way+','+width+','+height+') /></td>'
			+'<td align=center><input type=button value=打印预览 onclick=doMyPrint(3,'+way+','+width+','+height+') /></td>'
			+'<td align=center><input type=button value=直接打印 onclick=doMyPrint(1,'+way+','+width+','+height+') /></td>'
			+'</tr>'
			+'</table>';
	var txt = document.body.innerHTML ;

	document.body.innerHTML = printer + button + txt;
}

//检查此页是否需要打印
function doPrint(way,width,height){
			setUpPrinter();//安装打印控件
			insertButton(way,width,height);
}

//检查是否已经安装过打印控件
function checkPrinter()
{
	var errmsg = "请您按照页面顶部上的黄色提示下载ActiveX控件."
			+ "\n如果没有提示请按以下步骤设置ie浏览器. "
			+ "\n工具-> internet选项->安全->自定义级别->设置->下载未签名的ActiveX->启用"
			+ "\n如还无法打印，请点击“控件安装”，安装该控件."
			+ "\n然后刷新本页或者关闭IE后，重新进入系统.进行安装.";
	try{
		if(typeof(jatoolsPrinter.page_div_prefix)=='undefined'){
	        alert(errmsg);
	        return false;
	    }
	}catch(e)
	{
		alert(errmsg);
	    return false;
	}
	return true;
}

//打印机安装
function setUpPrinter()
{
	var printerIframe = document.getElementById("printerIframe");
	
	if(printerIframe==null)
	{
		var node=document.createElement('<iframe style="display:none;" id="printerIframe" name="printerIframe" width=110 height=110></iframe>');
		document.body.insertBefore(node);
	}
	var printerIframe = document.getElementById("printerIframe");
	printerIframe.src="/js/printer/printer.html";
}