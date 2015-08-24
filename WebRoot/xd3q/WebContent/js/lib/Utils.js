/**
 * 常用工具类，提供了大量静态常用方法。
 * 
 */
Utils = Class.create();

/**
 * 设置常量
 */
Constant = Class.create();

/**
 * 判断对象是否为对象
 * 
 * @param Object
 *            obj 欲判断的对象
 * @return boolean true - 是对象，false - 不是对象
 */
Utils.isObject = function(obj)
{
	return (obj == null || typeof(obj) == "undefined") ? false : true;
}

/**
 * 判断输入是否为货币数值
 * 
 * @param float
 *            money
 * @return boolean
 */
Utils.isMoney = function(money)
{
	return /^(([1-9]\d*)|0)(\.\d{1,2})?$/.test(money);
}

/**
 * 判断输入的字符串是否为数字
 * 
 * @param {}
 *            n
 * @return {}
 */
Utils.isNumber = function(n)
{
	return /^(-|\+)?\d+(\.\d+)?$/.test(n);
	
}
/**
 * 判断一个对象是不是函数对象
 * 
 * @param fun
 *            对象
 * @return boolean
 */
Utils.isFunction = function(fun)
{
	return (fun != null && typeof(fun) == "function");
}

/**
 * 判断是否为整数
 */
Utils.isInteger = function(num)
{
	return /^(-?[1-9]\d*|0)$/.test(num);
}

/**
 * 判断字符串中是否含有不能提交的特殊字符(不全，目前只发现%和？)
 */
Utils.replaceSpecialCharacter = function(str)
{
	res = new Array([/%/g, '%', '％'], [/\?/g, '?', '？'], [/\*/g, '*', '×'], [/#/g, '#', '＃']);
	for (var i = 0; i < res.length; i++)
	{
		var ar = res[i];
		if (str.indexOf(ar[1]) != -1)
		{
			str = str.replace(ar[0], ar[2]);
		}
	}
	return str;
}

/**
 * 判断字符串中是否含有特殊字符(不全)
 */
Utils.isContainSpecialCharacter = function(str)
{
	return /^[^\\\/\^\"\'\.\|\?\*@%#\(\)\s]+$/.test(str);
}
/**
 * 判断一个数组是否包含了特定的值
 * 
 * @param values
 * @param value
 */
Utils.contains = function(values, value)
{
	for (var i = 0; i < values.length; i++)
	{
		if (values[i] == value)
			return true;
	}
	
	return false;
}

/**
 * 判断一个数值（或字符串）是否为非负数。
 * 
 */
Utils.isNonNegative = function(num)
{
	
	return /^[1-9][0-9]*$/.test(num) || num == 0;
}

Utils.isCharacter = function(str)
{
	
	var reg = /^\w+$/;
	if (!reg.test(str))
	{
		return false;
	}
	return true;
}

/**
 * 验证字符串是否为空
 * 
 * @param 需要验证的字符串
 * @return 布尔值
 */
Utils.isEmpty = function(str)
{
	if (str == null || str.trim().length == 0)
		return true;
	else
		return false;
}

Utils.isOpera = (navigator.userAgent.toLowerCase().indexOf("opera") > -1);
Utils.isSafari = (/webkit|khtml/).test(navigator.userAgent.toLowerCase());
Utils.isIE = (!Utils.isOpera && navigator.userAgent.toLowerCase().indexOf("msie") > -1);
Utils.isIE5 = (Utils.isIE && /msie 5\.0/i.test(navigator.userAgent));
Utils.isIE7 = (!Utils.isOpera && navigator.userAgent.toLowerCase().indexOf("msie 7") > -1);
Utils.isGecko = (!Utils.isSafari && navigator.userAgent.toLowerCase().indexOf("gecko") > -1);
Utils.isKHtml = /Konqueror|Safari|KHTML/i.test(navigator.userAgent);

/**
 * 获取某HTML元素的符合指定样式名称的第一个父元素。
 * 
 * @param HTMLElement
 *            需要匹配的HTML元素
 * @param String
 *            className 匹配的样式名称
 * @return HTMLElement 返回符合条件的父元素，或返回空
 */
Utils.getParentByClassName = function(el, className)
{
	if (!el)
		return null;
	
	var e = el.parentNode;
	if (e.className == className)
		return e;
	
	Utils.getParentByClassName(e, className);
}

/**
 * 获取某HTMLElement下指定样式的子元素集合
 * 
 * @param HTMLElment
 *            el
 * @param String
 *            className
 * @return Array
 */
Utils.getChildrenByClassName = function(el, className)
{
	if (!el)
		return null;
	
	var children = new Array();
	for (var i = 0; i < el.childNodes.length; i++)
	{
		if (el.childNodes[i].className == className)
			children.push(el.childNodes[i]);
	}
	
	return children;
}

/**
 * 
 */
Utils.ScriptFragmentRegExp = new RegExp(Prototype.ScriptFragment, 'img');

/**
 * 在指定的字符串中执行其中包含的脚本。
 * 
 * @param String
 *            str
 */
Utils.evalScripts = function(str)
{
	var scripts = str.match(Utils.ScriptFragmentRegExp);
	
	if (scripts)
	{
		match = new RegExp(Prototype.ScriptFragment, 'im');
		setTimeout((function()
				{
					for (var i = 0; i < scripts.length; i++)
						eval(scripts[i].match(match)[1]);
				}).bind(this), 10);
	}
}

/**
 * 删除指定字符串中的所有脚本块。
 * 
 * @param String
 *            str
 * @return String
 */
Utils.omitScriptFragment = function(str)
{
	return str.replace(Utils.ScriptFragmentRegExp, '');
}

Utils.getComputedStyle = function(str)
{
	var reg = /[0-9]+/i;
	var value = str.match(reg);
	
	return (value == null) ? 0 : parseInt(value);
}

/**
 * 获取某个元素的绝对坐标。
 * 
 * @param HMTLElement
 *            el
 * @return Object
 */
Utils.getAbsolutePos = function(el)
{
	var SL = 0, ST = 0;
	var is_div = /^div$/i.test(el.tagName);
	if (is_div && el.scrollLeft)
		SL = el.scrollLeft;
	if (is_div && el.scrollTop)
		ST = el.scrollTop;
	var r =
	{
		x	: el.offsetLeft - SL,
		y	: el.offsetTop - ST
	};
	if (el.offsetParent)
	{
		var tmp = this.getAbsolutePos(el.offsetParent);
		r.x += tmp.x;
		r.y += tmp.y;
	}
	
	return r;
}

/**
 * 验证是否为浮点数
 * 
 * @param String
 *            str
 * @return boolean
 */
Utils.isFloatValue = function(floatValue)
{
	
	var reg = /(^((-|\+)?0\.)(\d*)$)|(^((-|\+)?[1-9])+\d*(\.\d*)?$)/;
	
	return reg.test(floatValue);
}

/**
 * 移除HTML元素对象中所有子元素
 * 
 * @param htmlObj
 *            HTML元素对象
 */
Utils.removeChildren = function(htmlObj)
{
	if (!Utils.isObject(htmlObj) && !Utils.isObject($(htmlObj)))
		return;
	
	while (htmlObj.hasChildNodes())
	{
		htmlObj.removeChild(htmlObj.childNodes[0]);
	}
}

/**
 * 更新HTML元素对象的innerHTML。 在更新内容之前，删除其包含的所有子节点。
 * 
 * @param htmlObj
 * @param str
 */
Utils.updateElementInnerHTML = function(htmlObj, str)
{
	
	if (!Utils.isObject(htmlObj) && !Utils.isObject($(htmlObj)))
		return;
	
	Utils.removeChildren(htmlObj);
	htmlObj.innerHTML = str;
}

/**
 * 在给定的URL后加上时间搓，防止页面没有重新加载，而是使用缓存的页面
 */
Utils.getUncacheUrl = function(url)
{
	
	if (url == null)
		return null;
	
	if (url.indexOf("?") != -1)
		url += "&" + (new Date()).getTime();
	else
		url += "?" + (new Date()).getTime();
	
	return url;
}

/*------------------- String ------------------------------*/

/**
 * 去除字符串中的头尾空白字符
 * 
 */
if (!String.prototype.trim)
{
	String.prototype.trim = function()
	{
		return this.replace(/(^\s*)|(\s*$)/g, "");
	}
}

/**
 * 根据显示字符串的容器的宽度，自动截取字符串。
 * 
 * @param int
 *            containerWidth 显示字符串的容器的宽度
 * @return String
 */
String.prototype.substrByContainerWidth = function(containerWidth)
{
	var titleWidth = Utils.calculateStringWidth(this);
	if (titleWidth > containerWidth)
	{
		var length = parseInt(this.length * (containerWidth / titleWidth));
		return this.substr(0, length - 1)
	}
	else
		return this;
}

/**
 * 判断字符串是否以某个字符串开始
 * 
 * @param String
 *            prefix
 * @return boolean
 */
if (!String.startsWith)
{
	String.prototype.startsWith = function(prefix)
	{
		if (StringUtils.isEmpty(prefix))
			return false;
		
		return (this.indexOf(prefix) > -1);
	}
}

/*----------------- StringUtils ----------------------------*/

var StringUtils = Class.create();

StringUtils.isEmpty = function(str)
{
	return (!Utils.isObject(str) || str.length == 0) ? true : false;
}
Utils.showSaleContract = function(contractId)
{
	if (contractId == '' || contractId == null)
	{
		return false;
	}
	top.ExtModalWindowUtil.show('查看合同信息', 'contractController.spr?action=archSalesInfoView&businessRecordId=' + contractId, '', '',
			{
				width	: 800,
				height	: 500
			});
}
Utils.showPurcharseContract = function(contractId)
{
	if (contractId == '' || contractId == null)
	{
		return false;
	}
	top.ExtModalWindowUtil.show('采购合同信息', 'contractController.spr?action=ArchPurchaseInfoView&businessRecordId=' + contractId, '', '',
			{
				width	: 900,
				height	: 550
			});
}
Utils.showProject = function(projectId)
{
	if (projectId == '' || projectId == null)
	{
		return false;
	}
	top.ExtModalWindowUtil.show('查看立项申请', 'projectController.spr?action=modify&from=view&projectId=' + projectId, '', '',
			{
				width	: 800,
				height	: 500
			});
}
Utils.showProjectByNo = function(projectNo)
{
	if (projectNo == '' || projectNo == null)
	{
		return false;
	}
	top.ExtModalWindowUtil.show('查看立项申请', 'projectController.spr?action=modify&from=view&projectNo=' + projectNo, '', '',
			{
				width	: 800,
				height	: 500
			});
}

Utils.commafy = function commafy(num)
{
	num = num + "";
	var re = /(-?\d+)(\d{3})/
	while (re.test(num))
	{
		num = num.replace(re, "$1,$2")
	}
	return num;
}
Utils.roundoff = function roundoff(i, j)
{
	return Math.round(i * Math.pow(10, j)) / (Math.pow(10, j));
}

/**
 * 把form表单域的值转换为json串
 * 
 * @param form
 * @return
 */
function tranFormToJSON(form)
{
	if (!form)
		return;
	if (typeof form !== 'object')
		form = document[form];
	
	json = '{';
	isarray = false;
	for (var i = 0, max = form.elements.length; i < max; i++)
	{
		e = form.elements[i];
		id = e.id || e.name;
		if (id == '' || Ext.getCmp(id))
			continue;
		
		if (id.substring(id.length - 2) == '[]')
		{
			id = id.substring(0, id.length - 2);
			lastarr = id;
			if (isarray == false)
			{
				json += '"' + id + '":[';
			}
			isarray = true;
		}
		else
		{
			if (isarray)
			{
				json = json.substring(0, json.length - 1) + '],';
			}
			isarray = false;
		}
		
		switch (e.type) {
			case 'checkbox' :
			case 'radio' :
				if (!e.checked)
				{
					break;
				}
			case 'hidden' :
			case 'password' :
			case 'text' :
				if (!isarray)
				{
					json += '"' + id + '":';
				}
				if (e.value.indexOf('?') > -1)
				{
					json += '"",';
				}
				else
				{
					json += '"' + e.value.replace(new RegExp('(["\\\\])', 'g'), '\\$1') + '",';
				}
				break;
			case 'textarea' :
				if (!isarray)
				{
					json += '"' + id + '":';
				}
				if (e.value.indexOf('?') > -1)
				{
					json += '"",';
					// json += '"' + Ext.urlEncode(e.value).replace(
					// new RegExp('(["\\\\])', 'g'), '\\$1') + '",';
				}
				else
				{
					json += '"' + e.value.replace(new RegExp('(["\\\\])', 'g'), '\\$1') + '",';
				}
				break;
			case 'button' :
			case 'file' :
			case 'image' :
			case 'reset' :
			case 'submit' :
			default :
		}
	};
	return Ext.util.JSON.decode(json.substring(0, json.length - 1) + '}');
}

/**
 * 把json串传值给form表单域
 * 
 * @param form
 * @param values
 * @return
 */
function setJSONToForm(form, values)
{
	if (!form || !values)
		return;
	if (typeof form !== 'object')
		form = document[form];
	
	for (var i = 0, max = form.elements.length; i < max; i++)
	{
		field = form.elements[i];
		id = field.id;
		
		if (id && field)
		{
			if (Ext.getCmp(id))
			{
				if ((Ext.getCmp(id).getXType() == 'searchhelpfield' || Ext.getCmp(id).getXType() == 'combo' || Ext.getCmp(id).getXType() == 'dpcombo') && values[id.replace('_text', '')])
				{
					Ext.getCmp(id).setValue(values[id.replace('_text', '')].trim());
				}
			}
			
			if (values[id])
			{
				if (Ext.getCmp(id))
				{
					Ext.getCmp(id).setValue(values[id].trim());
				}
				else
				{
					Ext.get(id).dom.value = values[id].trim();
				}
			}
		}
		// field.setValue(values[id]);
		if (this.trackResetOnLoad)
		{
			Ext.get(id).dom.originalValue = Ext.get(id).dom.value;
		}
	};
}

/**
 * form表单重置
 * 
 * @param form
 * @param values
 * @return
 */
function resetForm(form)
{
	if (!form)
		return;
	if (typeof form !== 'object')
		form = document[form];
	form.reset();
	
	for (var i = 0, max = form.elements.length; i < max; i++)
	{
		field = form.elements[i];
		id = field.id;
		
		if (id && field)
		{
			if (Ext.getCmp(id))
			{
				if (Ext.getCmp(id).getXType() == 'searchhelpfield' || Ext.getCmp(id).getXType() == 'combo' || Ext.getCmp(id).getXType() == 'dpcombo')
				{
					Ext.getCmp(id).reset();
				}
			}
		}
	};
}
/**
 * 把json对象转换为url请求串，并对汉字进行编码处理，服务端用StringUtils.urlEncode方法解码
 * 
 * @param o
 * @return
 */
function commonUrlEncode(o)
{
	if (!o)
	{
		return ""
	}
	var buf = [];
	for (var key in o)
	{
		var ov = o[key], k = encodeURIComponent(key);
		var type = typeof ov;
		if (type == "undefined")
		{
			buf.push(k, "=&")
		}
		else
		{
			if (type != "function" && type != "object")
			{
				buf.push(k, "=", escape(escape((ov))), "&");
			}
			else
			{
				if (Ext.isDate(ov))
				{
					var s = Ext.encode(ov).replace(/"/g, "");
					buf.push(k, "=", s, "&")
				}
				else
				{
					if (Ext.isArray(ov))
					{
						if (ov.length)
						{
							for (var i = 0, len = ov.length; i < len; i++)
							{
								buf.push(k, "=", encodeURIComponent(ov[i] === undefined ? "" : ov[i]), "&")
							}
						}
						else
						{
							buf.push(k, "=&")
						}
					}
				}
			}
		}
	}
	buf.pop();
	return buf.join("")
}

Utils.getXdssBusinessTypeTitle = function(xdssBusinessTypeTitle)
{
	var title = '';
	switch (xdssBusinessTypeTitle) {
		case 'PRO' :
			title = '立项申请';
			break;
		case 'CG' :
			title = '';
			break;
		case 'PC' :
			title = '采购合同';
			break;
		case 'SC' :
			title = '销售合同';
			break;
		case 'REC' :
			title = '收货';
			break;
		case 'SRE' :
			title = '退货';
			break;
		case 'PAY' :
			title = '付款';
			break;
		case 'COL' :
			title = '收款';
			break;
		case 'BAK' :
			title = '退款';
			break;
		case 'CREC' :
			title = '信用证开证';
			break;
		case 'PIC' :
			title = '到单';
			break;
		case 'SHIP' :
			title = '发货';
			break;
		case 'BILL' :
			title = '开票';
			break;
		case 'CRER' :
			title = '信用证收证';
			break;
		case 'EXP' :
			title = '出单';
			break;
		case 'IV' :
			title = '发票校验';
			break;
		case 'EXPORTBILL' :
			title = '开票';
			break;
	}
	return title;
}

Utils.getTradeTypeTitle = function(tradeType)
{
	var title = '';
	switch (tradeType) {
		case '1' :
			title = '外贸自营进口*业务';
			break;
		case '2' :
			title = '外贸自营出口*业务';
			break;
		case '3' :
			title = '外贸自营进口业务';
			break;
		case '4' :
			title = '外贸自营出口业务';
			break;
		case '5' :
			title = '外贸代理出口业务';
			break;
		case '6' :
			title = '外贸代理进口业务';
			break;
		case '7' :
			title = '内贸业务';
			break;
		case '8' :
			title = '进料加工业务内销';
			break;
		case '9' :
			title = '自营进口敞口业务';
			break;
		case '10' :
			title = '内贸敞口业务';
			break;
		case '11' :
			title = '转口业务';
			break;
		case '12' :
		title = '进料加工业务外销';
		break;
	}
	return title;
}
