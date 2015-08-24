<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  </head>
  <!--代理业务--->
  <body>
<table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
   <tbody id = "tp"  style="display: none" >   
  <tr>
    <td width="120"><font color="red">*</font>品名</td>
    <td width="150" colspan="3">
    	<input name="className" type="text" class="text" size="18" align="left" value="${main.className}"/>
    	<input type="button"  onclick="showFindMaterial()"value="..."/>    </td>
    <td width="43"><div align="right">规格</div></td>
    <td width="90"><input name="spec" type="text" class="text" size="10" value="${main.spec}"/></td>
    <td width="37"><div align="center">装运期</div></td>
    <td width="121"><input name="shipmentDate" type="text" class="text" size="15" value="${main.shipmentDate}"/></td>
    <td width="48"><div align="center"><div align="center">装运港</div></td>
    <td width="98"><input name="shipmentPort" type="text" class="text" size="20" value="${main.shipmentPort}"/></td>
  </tr>
   <tr>
		<td >	
			物料组
		</td>
		<td colspan="5">
			<div id="div_ymatGroup"></div>
			<fisc:dictionary width="100" hiddenName = "ymatGroup" dictionaryName="YDMATGROUP" value="${main.ymatGroup}" divId="div_ymatGroup" isNeedAuth="false"   ></fisc:dictionary>
		</td>
		<td>
        <div align="center"><nobr>原产地</nobr></div></td>
	    <td colspan="3"><input name="placeOfProduction" type="text" size="10" class="text" value="${main.placeOfProduction}"/></td>
		
   </tr>
  <tr>
    <td><font color="red">*</font>金额</td>
    <td width="0">    </td>
    <td>
    	<input name="sum" type="text" class="text" size="10" value="${main.sum}"/>万    </td>
    <td>	
      <div align="center">
   	 	<select name="currency" id="currency">
			<option value="">请选择</option>
			<c:forEach items="${currency}" var="row">
				<option value="${row.code}">${row.code}</option>
			</c:forEach>
		</select>
	 </div>    </td>
    <td><div align="right">数量</div></td>
    <td ><input name="no" type="text" class="text" size="10" value="${main.no}"/></td>
    <td><div align="center">单位</div></td>
    <td><input type="text" size="10" name="other4" value="${other4}"/></td>
    <td><div align="center">目的港</div></td>
    <td><input name="destinationPort" type="text" class="text" size="20" value="${main.destinationPort}"/></td>
  </tr>
  <tr>
  <td align="center"> 备注</td>
  <td colspan="9">
  	<textarea name="mask" id="mask" style="width:100%;overflow-y:visible;word-break:break-all;" >${main.mask}</textarea>  </td>
  </tr>
   </tbody>
  <tr>
    <td rowspan="2" valign="middle"><div align="center">类别</div></td>
    <td width="14" rowspan="2" valign="middle"><div align="center">序号</div></td>
    <td colspan="4" rowspan="2" valign="middle"><div align="center">核算项目</div></td>
    <td colspan="4" align="center">立项成本核算(汇率<input type="text" size=8 name="exchangeRate" id="exchangeRate" value="${main.exchangeRate}"/>)</td>
  </tr>
  <tr>
    <td colspan="2"><div align="center">RMB￥</div>
    <td colspan="2"><div align="center" id="bibei"></div></td>
  </tr>
  <tr>
    <td rowspan="5">进货成本</br>与价格</td>
    <td rowspan="3">1</td>
    <td colspan="2" rowspan="3">商品购进成本</td>
    <td colspan="2"><div  align="center">数量</div></td>
    <td colspan="2" align="center">
		<input name="rmb1" value="${rmb1}" type="text" class="text" size="25"/>    </td>
    <td colspan="2"><div align="center"><input name="us1" value="${us1}" type="text" class="text" size="25"/></div></td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">单价</div></td>
    <td colspan="2" align="center">
		<input name="rmb2" value="${rmb2}" type="text" class="text" size="25"/>    </td>
    <td colspan="2"><div align="center"><input name="us2" value="${us2}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">金额</div></td>
    <td colspan="2" align="center">
		<input name="rmb3" value="${rmb3}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us3" value="${us3}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>2</td>
    <td colspan="4">其它</td>
    <td colspan="2" align="center">
		<input name="rmb40" value="${rmb40}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us40" value="${us40}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>4</td>
    <td colspan="4">小计（进货总额）</td>
    <td colspan="2" align="center">
		<input name="rmb6" value="${rmb6}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="us6" value="${us6}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td rowspan="9">经营费用</td>
    <td>4</td>
    <td colspan="4">(1)运杂费、装卸费</td>
    <td colspan="2" align="center">
		<input name="rmb8" value="${rmb8}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us8" value="${us8}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>5</td>
    <td colspan="4">(2)码头费</td>
    <td colspan="2" align="center">
		<input name="rmb9" value="${rmb9}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us9" value="${us9}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>6</td>
    <td colspan="4">(3)仓储费</td>
    <td colspan="2" align="center">
		<input name="rmb10" value="${rmb10}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us10" value="${us10}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>7</td>
    <td colspan="4">(4)报关、报检费等</td>
    <td colspan="2" align="center">
		<input name="rmb11" value="${rmb11}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us11" value="${us11}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>8</td>
    <td colspan="4">直接流通费合计:</td>
    <td colspan="2" align="center">
		<input name="rmb7" value="${rmb7}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="us7" value="${us7}" type="text" class="text" size="25" style="backgroundColor:red"/></div></td>
  </tr>
  <tr>
    <td>9</td>
    <td colspan="4">间接管理(差旅费、执行费、邮电费)</td>
    <td colspan="2" align="center">
		<input name="rmb12" value="${rmb12}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us12" value="${us12}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>10</td>
    <td colspan="4">佣金或手续费</td>
    <td colspan="2" align="center">
		<input name="rmb13" value="${rmb13}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us13" value="${us13}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>12</td>
    <td colspan="4" bordercolor="#66CCFF">其它(样品费、商品损耗、包装费等)</td>
    <td colspan="2" align="center">
		<input name="rmb15" value="${rmb15}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us15" value="${us15}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>13</td>
    <td colspan="4">小计</td>
    <td colspan="2" align="center">
		<input name="rmb41" value="${rmb41}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="us41" value="${us41}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>管理费用</td>
    <td>14</td>
    <td colspan="4">分摊公司管理费</td>
    <td colspan="2" align="center">
		<input name="rmb16" value="${rmb16}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us16" value="${us16}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td rowspan="6">财务费用</td>
    <td>15</td>
    <td colspan="4">货款利息（年利率${empty main.interestRate?INTEREST_RATE:main.interestRate}%）</td>
    <td colspan="2" align="center">
		<input name="rmb17" value="${rmb17}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us17" value="${us17}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>16</td>
    <td colspan="4">贴现利息</td>
    <td colspan="2" align="center">
		<input name="rmb42" value="${rmb42}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us42" value="${us42}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>17</td>
    <td colspan="4">押汇利息</td>
    <td colspan="2" align="center">
		<input name="rmb43" value="${rmb43}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us43" value="${us43}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>18</td>
    <td colspan="4">银行结算费</td>
    <td colspan="2" align="center">
		<input name="rmb19" value="${rmb19}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us19" value="${us19}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>19</td>
    <td colspan="4">其它</td>
    <td colspan="2" align="center">
		<input name="rmb44" value="${rmb44}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us44" value="${us44}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>20</td>
    <td colspan="4">小计</td>
    <td colspan="2" align="center">
		<input name="rmb45" value="${rmb45}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="us45" value="${us45}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td rowspan="2">代理收入</td>
    <td>21</td>
    <td colspan="4">合同预计利润率（百分比或单位利润）<font color="red">▲</font></td>
    <td colspan="2" align="center">
		<input name="rmb25" value="${rmb25}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="us25" value="${us25}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr> 
  	 <td ></td>
    
      <td colspan="4">合同预计利润率描述<font color="red">▲</font></td>   
      <td colspan="2" align="center"><textarea name="rmb58" id="rmb58" style="width:98%;overflow-y:visible;word-break:break-all;" >${rmb58}</textarea>     </td>
   		<td colspan="2"></td>
    </tr>
  <tr>
  	 <td ></td>
    <td>22</td>
    <td colspan="4">代理收入（开票金额）<font color="red">▲</font></td>
    <td colspan="2" align="center">
		<input name="rmb46" value="${rmb46}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us46" value="${us46}" type="text" class="text" size="25" /></div></td>
  </tr>
  <!--<tr>
    
    <td>23</td>
    <td colspan="4">增值税及附加费</td>
    <td colspan="2" align="center">
		<input name="rmb21" value="${rmb21}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us21" value="${us21}" type="text" class="text" size="25" /></div></td>
  </tr>
   -->
  <tr>
    <td rowspan="3">税收</td>
    <td>23</td>
    <td colspan="4">营业税及附加费（5.6%）<font color="red">▲</font></td>
    <td colspan="2" align="center">
		<input name="rmb22" value="${rmb22}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us22" value="${us22}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>24</td>
    <td colspan="4">印花税</td>
    <td colspan="2" align="center">
		<input name="rmb23" value="${rmb23}" type="text" class="text" size="25"/>    </td>
    <td colspan="2"><div align="center"><input name="us23" value="${us23}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>25</td>
    <td colspan="4">小计</td>
    <td colspan="2" align="center">
		<input name="rmb47" value="${rmb47}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="us47" value="${us47}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>利润</td>
    <td>26</td>
    <td colspan="4">预计利润总额或亏损总额</td>
    <td colspan="2" align="center">
		<input name="rmb31" value="${rmb31}" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="us31" value="${us31}" type="text" class="text" size="25" /></div></td>
  </tr>
</table>
<script language="javascript">
 var sa = '${sales.contractSalesId}';
  function init(){
 	if(sa != '') {  	
  		document.getElementById('tp').style.display="none";
  	}else{
  		document.getElementById('tp').style.display="";
  	}
  	for(var i=1;i<60;i++){
		if(i==1 || i == 2 || i==3 || i == 40 || i == 29 || i == 27 || i==28 || i==52)continue;
		var rmb = Ext.getDom('rmb' + i);
		if((typeof(rmb)=='undefined') || null == rmb){
		 	continue;
		}else{
			if(rmb.value =='')rmb.value='0';			
		}		
	}
	for(var i=1;i<60;i++){	
		if(i==1 || i == 2 || i==3  || i == 27 || i==28 || i==52)continue;
		var us = Ext.getDom('us' + i);
		if((typeof(us)=='undefined') || null == us ){
		 	continue;
		}else{
			if(us.value =='')us.value='0';		
		}	
	}
  }
  init();
function addListener(element,e,fn){
     if((typeof(from)=='undefined')) return;
     if(from=='modify'||from=='iframe'||from=='add'){
     if(element.addEventListener){
          element.addEventListener(e,fn,false); 
     } else {
          element.attachEvent("on" + e,fn); 
     } 
     }
}
function getNumValue(element){
if($(element).value=='') return 0;
return parseFloat($(element).value);
}
/***进货成本与价格小计**/
function jhcbyjgxj(){
	$('rmb6').value = Utils.roundoff(getNumValue('rmb3')+getNumValue('rmb40'),2);
}
addListener($('rmb3'),"propertychange",jhcbyjgxj); 
addListener($('rmb40'),"propertychange",jhcbyjgxj);
/***直接流通费合计***/
function zjltfhj(){
$('rmb7').value = Utils.roundoff(getNumValue('rmb8') + getNumValue('rmb9') + getNumValue('rmb10') + getNumValue('rmb11'),2);
}
addListener($('rmb8'),"propertychange",zjltfhj);
addListener($('rmb9'),"propertychange",zjltfhj);
addListener($('rmb10'),"propertychange",zjltfhj);
addListener($('rmb11'),"propertychange",zjltfhj);
/***经营费用小计***/
function jyfyxj(){
$('rmb41').value = Utils.roundoff(getNumValue('rmb7') + getNumValue('rmb12') + getNumValue('rmb13') + getNumValue('rmb15'),2);
}
addListener($('rmb7'),"propertychange",jyfyxj);
addListener($('rmb12'),"propertychange",jyfyxj);
addListener($('rmb13'),"propertychange",jyfyxj);
addListener($('rmb15'),"propertychange",jyfyxj);
/***财务费用小计***/
function cwfyxj(){
$('rmb45').value = Utils.roundoff(getNumValue('rmb17') + getNumValue('rmb19') + getNumValue('rmb42') + getNumValue('rmb43')+ getNumValue('rmb44'),2);
}
addListener($('rmb17'),"propertychange",cwfyxj);
addListener($('rmb19'),"propertychange",cwfyxj);
addListener($('rmb42'),"propertychange",cwfyxj);
addListener($('rmb43'),"propertychange",cwfyxj);
addListener($('rmb44'),"propertychange",cwfyxj);
/***营业税及附加费****/
function yysjfjf(){
$('rmb22').value = Utils.roundoff($('rmb46').value*0.056,2);
}
addListener($('rmb46'),"propertychange",yysjfjf);
/***印花税***/
function yhs(){
$('rmb23').value = Utils.roundoff(0.0003*getNumValue('rmb3'),2);
}
addListener($('rmb3'),"propertychange",yhs);
/***税收小计***/
function ssxj(){
$('rmb47').value = Utils.roundoff(getNumValue('rmb22') + getNumValue('rmb23'),2);
}
addListener($('rmb22'),"propertychange",ssxj);
addListener($('rmb23'),"propertychange",ssxj);
/**预计利润总额或亏损总额**/
function yjlrzehksze(){
$('rmb31').value = Utils.roundoff(getNumValue('rmb46')-getNumValue('rmb41')-getNumValue('rmb16') - getNumValue('rmb45') - getNumValue('rmb47'),2);
}
addListener($('rmb46'),"propertychange",yjlrzehksze);
addListener($('rmb41'),"propertychange",yjlrzehksze);
addListener($('rmb16'),"propertychange",yjlrzehksze);
addListener($('rmb45'),"propertychange",yjlrzehksze);
addListener($('rmb47'),"propertychange",yjlrzehksze);

/**合同预计利润率（百分比或单位利润）自动计算=预计利润总额/数量**/
function htyjlrl(){
	$('rmb25').value = Utils.roundoff(getNumValue('rmb31')*100/getNumValue('rmb46'),2);
}
addListener($('rmb31'),"propertychange",htyjlrl);
addListener($('rmb46'),"propertychange",htyjlrl);

</script>
  </body>
</html>
