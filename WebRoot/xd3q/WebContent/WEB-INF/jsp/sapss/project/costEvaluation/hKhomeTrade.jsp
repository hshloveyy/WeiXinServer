<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  </head>
  <!--
      内贸成本核算，币别默认为CNY，汇率默认为1
   --->
  <body>
  <table width="550" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" >
     <tbody id = "tp"  style="display: none" >   
    <tr>
      <td width="120"><font color="red">*</font>品名</td>
      <td colspan="2"><input name="className" type="text" class="text" size="18" align="left" value="${main.className}"/>
          <input name="button" type="button"  onClick="showFindMaterial()"value="..."/>      </td>
      <td width="33"><div align="right">规格</div></td>
      <td width="81"><input name="spec" type="text" class="text" size="10" value="${main.spec}"/></td>
      <td width="56"><div align="center">装运期</div></td>
      <td width="118"><input name="shipmentDate" type="text" class="text" size="15" value="${main.shipmentDate}"/></td>
    </tr>
    <tr>
		<td >	
			物料组
		</td>
		<td colspan="6">	
			<div id="div_ymatGroup"></div>
			<fisc:dictionary width="100" hiddenName = "ymatGroup" dictionaryName="YDMATGROUP" value="${main.ymatGroup}" divId="div_ymatGroup" isNeedAuth="false"   ></fisc:dictionary>
		</td>
   </tr>
    <tr>
      <td><input name="placeOfProduction" type="hidden" size="10" class="text" value="${main.placeOfProduction}"/><font color="red">*</font>金额</td>
	  <td colspan="2"><table><tr>
      <td><input name="sum" type="text" class="text" size="10" value="${main.sum}"/>万</td>
      <td><div align="center">
   	 	<select name="currency" id="currency">
			<option value="">请选择</option>
			<c:forEach items="${currency}" var="row">
				<option value="${row.code}">${row.code}</option>
			</c:forEach>
		</select>
	 </div></td>
	 </tr></table>
	 </td>
      <td><div align="right">数量</div></td>
      <td ><input name="no" type="text" class="text" size="10" value="${main.no}"/></td>
      <td><div align="center">单位</div></td>
      <td><input type="text" size="10" name="other4" value="${other4}"/></td>
    </tr>
    <tr>
      <td align="center"> 备注</td>
      <td colspan="6"><textarea name="mask" id="mask" style="width:98%;overflow-y:visible;word-break:break-all;" >${main.mask}</textarea></td>
    </tr>
     </tbody>
    <tr>
      <td rowspan="2" valign="middle"><div align="center">类<br>别</div></td>
      <td width="29" rowspan="2" valign="middle"><div align="center">序<br>号</div></td>
      <td colspan="3" rowspan="2" valign="middle"><div align="center">核算项目</div></td>
      <td colspan="2" align="center">立项成本核算<input type="hidden" name="exchangeRate" id="exchangeRate" value="1"/></td>
    </tr>
    <tr>
      <td colspan="2"><div align="center">USD＄</div>
    <div align="center" id="bibei" style="display:none"></div></tr>
    <tr>
      <td rowspan="8" align="center">进<br>货<br>成<br>本</td>
      <td rowspan="6">1</td>
      <td width="161" rowspan="6" align="center" valign="middle">商品购进成本</td>
      <td colspan="2"><div  align="center">数量</div></td>
      <td colspan="2" align="center"><input name="rmb1" value="${rmb1}" type="text" class="text" size="25"/>      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center">单价</div></td>
      <td colspan="2" align="center"><input name="rmb2" value="${rmb2}" type="text" class="text" size="25"/>      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center">税率(填小数)<font color="red">▲</font></div></td>
      <td colspan="2" align="center"><input name="rmb40" value="0" type="text" class="text" size="25" readonly="readonly"/>      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center">货款总额（含税）<font color="red">▲</font></div></td>
      <td colspan="2" align="center"><input name="rmb3" value="${rmb3}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center">不含税货款</div></td>
      <td colspan="2" align="center"><input name="rmb41" value="${rmb41}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center">进项税金</div></td>
      <td colspan="2" align="center"><input name="rmb42" value="0" type="text" class="text" size="25" readonly="readonly"/>      </td>
    </tr>
    <tr>
      <td>2</td>
      <td colspan="3">其它</td>
      <td colspan="2" align="center"><input name="rmb4" value="${rmb4}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>3</td>
      <td colspan="3">小计</td>
      <td colspan="2" align="center"><input name="rmb6" value="${rmb6}" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>      </td>
    </tr>
    <tr>
      <td rowspan="9" align="center">经<br>营<br>费<br>用</td>
      <td>4</td>
      <td colspan="3">(1)运杂费、装卸费</td>
      <td colspan="2" align="center"><input name="rmb8" value="${rmb8}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>5</td>
      <td colspan="3">(2)码头费</td>
      <td colspan="2" align="center"><input name="rmb9" value="${rmb9}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>6</td>
      <td colspan="3">(3)仓储费</td>
      <td colspan="2" align="center"><input name="rmb10" value="${rmb10}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>7</td>
      <td colspan="3">(4)保险费等</td>
      <td colspan="2" align="center"><input name="rmb11" value="${rmb11}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>8</td>
      <td colspan="3">直接流通费合计：</td>
      <td colspan="2" align="center"><input name="rmb7" value="${rmb7}" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>      </td>
    </tr>
    <tr>
      <td>9</td>
      <td colspan="3">间接管理(差旅费、执行费、邮电费)</td>
      <td colspan="2" align="center"><input name="rmb12" value="${rmb12}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>10</td>
      <td colspan="3">佣金或手续费</td>
      <td colspan="2" align="center"><input name="rmb13" value="${rmb13}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>12</td>
      <td colspan="3" bordercolor="#66CCFF">其它(样品费、商品损耗、包装费等)</td>
      <td colspan="2" align="center"><input name="rmb15" value="${rmb15}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>13</td>
      <td colspan="3">小计</td>
      <td colspan="2" align="center"><input name="rmb14" value="${rmb14}" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>      </td>
    </tr>
    <tr>
      <td>管理费用</td>
      <td>14</td>
      <td colspan="3">分摊公司管理费</td>
      <td colspan="2" align="center"><input name="rmb16" value="${rmb16}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td rowspan="5" align="center">财<br>务<br>费<br>用</td>
      <td>15</td>
      <td colspan="3">货款利息（年利率6.5%）</td>
      <td colspan="2" align="center"><input name="rmb17" value="${rmb17}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>16</td>
      <td colspan="3">贴现利息</td>
      <td colspan="2" align="center"><input name="rmb18" value="${rmb18}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>17</td>
      <td colspan="3">银行结算费</td>
      <td colspan="2" align="center"><input name="rmb19" value="${rmb19}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>18</td>
      <td colspan="3">其它</td>
      <td colspan="2" align="center"><input name="rmb43" value="${rmb43}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>19</td>
      <td colspan="3">小计</td>
      <td colspan="2" align="center"><input name="rmb44" value="${rmb44}" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>      </td>
    </tr>
    <tr>
      <td rowspan="8" align="center">销<br>售<br>收<br>入</td>
      <td rowspan="8">20</td>
      <td colspan="3">合同预计利润率（百分比或单位利润）<font color="red">▲</font></td>
      <td colspan="2" align="center"><input name="rmb25" value="${rmb25}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>      </td>
    </tr>
     <tr> 
      <td colspan="3">合同预计利润率描述<font color="red">▲</font></td>   
      <td colspan="2" align="center"><textarea name="rmb58" id="rmb58" style="width:98%;overflow-y:visible;word-break:break-all;" >${rmb58}</textarea>     </td>
    </tr>
    <tr>
      <td rowspan="6" >商品销售价</td>
      <td colspan="2"><div  align="center">数量</div></td>
      <td colspan="2" align="center"><input name="rmb27" value="${rmb27}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center">单价</div></td>
      <td colspan="2" align="center"><input name="rmb28" value="${rmb28}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center">税率(填小数)<font color="red">▲</font></div></td>
      <td colspan="2" align="center"><input name="rmb45" value="0" type="text" class="text" size="25" readonly="readonly"/>      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center">货款总额（含税）<font color="red">▲</font></div></td>
      <td colspan="2" align="center"><input name="rmb29" value="${rmb29}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center">不含税货款</div></td>
      <td colspan="2" align="center"><input name="rmb46" value="${rmb46}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center"> 销项税金</div></td>
      <td colspan="2" align="center"><input name="rmb47" value="${rmb47}" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td rowspan="5" align="center">税<br>收</td>
      <td height="35">21</td>
      <td colspan="3">增值税及附加费</td>
      <td colspan="2" align="center"><input name="rmb21" value="0" type="text" class="text" size="25" readonly="readonly"/></td>
    </tr>
    <tr>
      <td>22</td>
      <td colspan="3">营业税及附加费（5.55%）</td>
      <td colspan="2" align="center"><input name="rmb22" value="0" type="text" class="text" size="25" readonly="readonly"/></td>
    </tr>
    <tr>
      <td>23</td>
      <td colspan="3">印花税</td>
      <td colspan="2" align="center"><input name="rmb23" value="0" type="text" class="text" size="25" readonly="readonly"/></td>
    </tr>
    <tr>
      <td>24</td>
      <td colspan="3">其它</td>
      <td colspan="2" align="center"><input name="rmb48" value="0" type="text" class="text" size="25" readonly="readonly"/></td>
    </tr>
	<tr>
      <td>25</td>
      <td colspan="3">小计</td>
      <td colspan="2" align="center"><input name="rmb49" value="0" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/></td>
    </tr>
    <tr>
      <td align="center">利润</td>
      <td>27</td>
      <td colspan="3">预计利润总额或亏损总额</td>
      <td colspan="2" align="center"><input name="rmb31" value="${rmb31}" type="text" class="text" size="25"   readonly style="background-color:#D0D0D0"/>      </td>
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
/***购进成本单价**/
function gjcbdj(){
    if($('rmb1').value==''||$('rmb1').value==0) return;
	$('rmb2').value = Utils.roundoff($('rmb3').value/$('rmb1').value,2);
}
addListener($('rmb3'),"propertychange",gjcbdj); 
addListener($('rmb1'),"propertychange",gjcbdj);
/***计算不含税货款和进项税金**/
function bhshkJxsj(){
	$('rmb41').value = Utils.roundoff($('rmb3').value/(1+getNumValue('rmb40')),2);
	//$('rmb42').value=Utils.roundoff($('rmb3').value - $('rmb3').value/(1+getNumValue('rmb40')),2);
}
addListener($('rmb3'),"propertychange",bhshkJxsj); 
addListener($('rmb40'),"propertychange",bhshkJxsj);
/***进货成本小计**/
function jhcbxj(){
$('rmb6').value = getNumValue('rmb4') + getNumValue('rmb3');
}
addListener($('rmb4'),"propertychange",jhcbxj);
addListener($('rmb3'),"propertychange",jhcbxj);
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
$('rmb14').value = Utils.roundoff(getNumValue('rmb7') + getNumValue('rmb12') + getNumValue('rmb13') + getNumValue('rmb15'),2);
}
addListener($('rmb7'),"propertychange",jyfyxj);
addListener($('rmb12'),"propertychange",jyfyxj);
addListener($('rmb13'),"propertychange",jyfyxj);
addListener($('rmb15'),"propertychange",jyfyxj);
/***财务费用小计***/
function cwfyxj(){
$('rmb44').value = Utils.roundoff(getNumValue('rmb17') + getNumValue('rmb18') + getNumValue('rmb19') + getNumValue('rmb43'),2);
}
addListener($('rmb17'),"propertychange",cwfyxj);
addListener($('rmb18'),"propertychange",cwfyxj);
addListener($('rmb19'),"propertychange",cwfyxj);
addListener($('rmb43'),"propertychange",cwfyxj);
/***商品销售单价***/
function spxsdj(){
$('rmb28').value = Utils.roundoff(getNumValue('rmb29')/getNumValue('rmb27'),2);
}
addListener($('rmb29'),"propertychange",spxsdj);
addListener($('rmb27'),"propertychange",spxsdj);
/***不含税货款***/
function bhshk(){
$('rmb46').value = Utils.roundoff(getNumValue('rmb29')/(1+getNumValue('rmb45')),2);
}
addListener($('rmb29'),"propertychange",bhshk);
addListener($('rmb45'),"propertychange",bhshk);
/***销项税金***/
function xssj(){
//$('rmb47').value = Utils.roundoff(getNumValue('rmb29') - getNumValue('rmb29')/(1+getNumValue('rmb45')),2);
}
addListener($('rmb29'),"propertychange",xssj);
addListener($('rmb45'),"propertychange",xssj);
/***增值税及附加费用***/
function zzsjfjfy(){
//var v = 1.11*(getNumValue('rmb47') - getNumValue('rmb42')) ;
//if(v<0) v = 0;
//$('rmb21').value = Utils.roundoff(v,2);
}

addListener($('rmb47'),"propertychange",zzsjfjfy);
addListener($('rmb42'),"propertychange",zzsjfjfy);
/***印花税***/
function yhs(){
//$('rmb23').value = Utils.roundoff(0.0003*(getNumValue('rmb3') +getNumValue('rmb29')),2);
}
addListener($('rmb3'),"propertychange",yhs);
addListener($('rmb29'),"propertychange",yhs);
/***税收小计***/
function ssxj(){
//$('rmb49').value = Utils.roundoff(getNumValue('rmb21') + getNumValue('rmb22') + getNumValue('rmb23') + getNumValue('rmb48'),2);
}
addListener($('rmb21'),"propertychange",ssxj);
addListener($('rmb22'),"propertychange",ssxj);
addListener($('rmb23'),"propertychange",ssxj);
addListener($('rmb48'),"propertychange",ssxj);

/**预计利润总额或亏损总额**/
function yjlrzehksze(){
$('rmb31').value = Utils.roundoff(getNumValue('rmb29') - getNumValue('rmb6') - getNumValue('rmb14') - getNumValue('rmb16') - getNumValue('rmb44') - getNumValue('rmb49'),2);
}
addListener($('rmb29'),"propertychange",yjlrzehksze);
addListener($('rmb6'),"propertychange",yjlrzehksze);
addListener($('rmb14'),"propertychange",yjlrzehksze);
addListener($('rmb16'),"propertychange",yjlrzehksze);
addListener($('rmb44'),"propertychange",yjlrzehksze);
addListener($('rmb49'),"propertychange",yjlrzehksze);

/**合同预计利润率（百分比或单位利润）自动计算=预计利润总额/数量**/
function htyjlrl(){
	$('rmb25').value = Utils.roundoff(getNumValue('rmb31')*100/getNumValue('rmb46'),2);
}
addListener($('rmb31'),"propertychange",htyjlrl);
addListener($('rmb46'),"propertychange",htyjlrl);

</script>
  </body>
</html>
