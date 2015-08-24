var LESSTHEN = 0;
var EQUALS = 1;
var GREATERTHEN = 2;
var ERROR = 3;

/**
 * 日期常用工具类，提供了大量静态常用方法。
 * 
 */
DateUtils = {};

/**
 * 校验输入内容为合法日期
 * @param {String} str 日期字符串
 * @return {Boolean} true:日期符合格式yyyy-MM-dd或yyyy/MM/dd false:日期不符合格式yyyy-MM-dd或yyyy/MM/dd
 */
DateUtils.isValidDate = function (str){
    var checkStr = str;  
    var reg = "/";  
    checkStr = checkStr.replace(/-/g,reg)  
    var m = checkStr.length;  
    var year;  
    var month;  
    var day;  
  
    if(!(checkStr == null || checkStr=="" ))  
    {  
        if(m == 10 && checkStr.charAt(4) == reg && checkStr.charAt(7) == reg)  
        {  
            year = parseInt(checkStr.split(reg)[0],10);  
            month = parseInt(checkStr.split(reg)[1],10);  
            day = parseInt(checkStr.split(reg)[2],10);  
            if(month > 12 || month == 0){  
                alert(checkStr + "的格式不符，要求月份在1-12之间！");  
                return false;  
            }  
            if(day > 31 || day ==0)  
            {  
                alert(checkStr + "的格式不符，要求日在1-31之间！");  
                return false;  
            }  
            else if((month == 2 || month == 4 || month ==6 || month ==9 || month ==11) && day == 31)  
            {  
                alert(checkStr + "的格式不符，" + month + "月无31日！");  
                return false;  
            }  
            else if(month == 2  && day == 30)  
            {  
                alert(checkStr + "的格式不符，"+year+"月" +month+"月无30日！");  
                return false;  
            }  
            else if((year%100==0)&&(year%400!=0) && month == 2  && day == 29)  
            {  
                alert(checkStr + "的格式不符，"+year+"月" +month+"月无29日！");  
                return false;  
            }  
            else if((year%4)!=0 && month == 2  && day == 29)  
            {  
                alert(checkStr + "的格式不符，"+year+"月" +month+"月无29日！");  
                return false;  
            }  
        }  
        else  
        {  
            alert(checkStr + "的格式不符，要求yyyy-MM-dd或yyyy/MM/dd！");  
            return false;  
        }  
    }  
    return true;  
}

/**
 * 日期比较
 * @param {Date} d1 日期1
 * @param {Date} d2 日期2
 * @return {Number} 0:小于 1：等于 2：大于 3 错误
 */
DateUtils.dateCompare = function (d1,d2)
{   
     //如果比较的不是日期，直接返回false  
    if(!(d1 instanceof Date) || !(d2 instanceof Date))  
    {  
        return ERROR;  
    }  
  
    var d1Year = d1.getFullYear();  
    var d2Year = d2.getFullYear();  
    var d1Month = d1.getMonth();  
    var d2Month = d2.getMonth();  
    var d1Date = d1.getDate();  
    var d2Date = d2.getDate();  
  
    //比较年  
    if (d1Year < d2Year)  
    {  
        return LESSTHEN;  
    }  
      
    //如果年相等，比较月  
    if (d1Year == d2Year)  
    {     
        if (d1Month < d2Month)  
        {  
            return LESSTHEN;  
        }  
    }  
    //如果年，月都相等，比较日  
    if (d1Year == d2Year)  
    {     
        if(d1Month == d2Month)  
        {  
            if (d1Date < d2Date)  
            {  
                return LESSTHEN;  
            }  
          
            else if (d1Date == d2Date)  
            {  
                return EQUALS;  
            }  
            else  
            {  
                return GREATERTHEN;  
            }  
        }  
        else  
        {  
           return GREATERTHEN;  
        }  
    }  
    else  
    {  
       return GREATERTHEN;  
    }  
    
}

/**
 * 日期比较
 * @param {String} d1 日期1 格式为：yyyy-MM-dd或yyyy/MM/dd
 * @param {String} d2 日期2 格式为：yyyy-MM-dd或yyyy/MM/dd
 * @return {Number} 0:小于 1：等于 2：大于 3 错误
 */
DateUtils.dateCompareStr2 = function(d1,d2)
{   var reg = "/";  
    if(this.isValidDate(d1)&& this.isValidDate(d2))  
    {  
        d1 = new Date(Date.parse(d1.replace(/-/g,reg)));  
        d2 = new Date(Date.parse(d2.replace(/-/g,reg)));  
      
        return this.dateCompare(d1,d2);  
    }  
    else  
    {     
        return ERROR;  
    }  
    
}
/**
 * 日期比较
 * @param {String} d1 日期1 格式为：yyyy-MM-dd或yyyy/MM/dd或yyyyMMdd
 * @param {String} d2 日期2 格式为：yyyy-MM-dd或yyyy/MM/dd或yyyyMMdd
 * @return {Number} 0:小于 1：等于 2：大于 3 错误
 */
DateUtils.dateCompareStr = function(d1,d2){	
	var reg = /^(\d{4})([-])(\d{2})([-])(\d{2})$/;
    var reg2 =/^(\d{4})([/])(\d{2})([/])(\d{2})$/;
    var reg3 =/^(\d{4})(\d{2})(\d{2})$/;
	var date1 = new Date();
	var date2 = new Date();
	if(reg.test(d1)){
		date1 = this.toDate(d1,'yyyy-MM-dd');
	}	
	if(reg.test(d2)){
		date2 = this.toDate(d2,'yyyy-MM-dd');
	}
	if(reg2.test(d1)){
		date1 = this.toDate(d1,'yyyy/MM/dd');
	}
	if(reg2.test(d2)){
		date2 = this.toDate(d2,'yyyy/MM/dd');
	}
	if(reg3.test(d1)){
		date1 = this.toDate(d1,'yyyyMMdd');
	}
	if(reg3.test(d2)){
		date2 = this.toDate(d2,'yyyyMMdd');
	}
	return this.dateCompare(date1,date2);  
}
/**
*转成日期如：todate('yyyy-MM-dd','2011-01-01'),todate('yyyyMMdd','20110101')
*能识别yyyymmdd及yymd这样的日期格式，或者yy、yyyy、M、MM、d、dd的任意组合。如yyyy年MM月dd日，匹配2009年06月10日。
**/
DateUtils.toDate = function(str,format){
	var pattern = format.replace("yyyy", "(\\~1{4})").replace("yy", "(\\~1{2})").replace("MM", "(\\~1{2})").replace("M", "(\\~1{1,2})").replace("dd", "(\\~1{2})").replace("d", "(\\~1{1,2})").replace(/~1/g, "d"); 
  
         var returnDate; 
         if (new RegExp(pattern).test(str)) { 
             var yPos = format.indexOf("yyyy"); 
             var mPos = format.indexOf("MM"); 
             var dPos = format.indexOf("dd"); 
             if (mPos == -1) mPos = format.indexOf("M"); 
             if (yPos == -1) yPos = format.indexOf("yy"); 
             if (dPos == -1) dPos = format.indexOf("d"); 
             var pos = new Array(yPos + "y", mPos + "m", dPos + "d").sort(); 
             var data = { y: 0, m: 0, d: 0 }; 
             var m = str.match(pattern); 
             for (var i = 1; i  < m.length; i++) { 
  
                 if (i == 0) return; 
                 var flag = pos[i - 1].split('')[1]; 
                 data[flag] = m[i]; 
             }; 
  
             if (data.y.toString().length == 2) { 
                 data.y = parseInt("20" + data.y); 
             } 
             data.m = data.m - 1; 
             returnDate = new Date(data.y, data.m, data.d); 
         } 
         if (returnDate == null || isNaN(returnDate)) returnDate = new Date(); 
         return returnDate; 
	
}

