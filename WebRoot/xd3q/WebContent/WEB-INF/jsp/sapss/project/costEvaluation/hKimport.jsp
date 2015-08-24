<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  </head>
  <!-----进口利润测算表------->
  <body>
    <table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" >
	  <tbody id = "tp"  style="display: none" >   
  <tr>
    <td width="120"><font color="red">*</font>品名</td>
	<td></td>
	<td colspan="2"><nobr>
    	<input name="className" type="text" class="text" size="18" align="left" value="${main.className}"/>
   	<input type="button"  onclick="showFindMaterial()"value="..."/></nobr></td>
    <td width="32"><div align="right"><nobr>规格</nobr></div></td>
    <td width="70"><input name="spec" type="text" class="text" size="10" value="${main.spec}"/></td>
    <td width="52"><div align="center"><nobr>装运期</nobr></div></td>
    <td width="112"><input name="shipmentDate" type="text" class="text" size="15" value="${main.shipmentDate}"/></td>
    <td width="48">
    <div align="center"><nobr>装运港</nobr></div></td>
    <td width="140"><input name="shipmentPort" type="text" class="text" size="20" value="${main.shipmentPort}"/></td>
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
	
	<td colspan="3"><table>
	  <tr>
	    <td><input name="sum" type="text" class="text" size="10" value="${main.sum}"/>万</td>
      <td>
        <select name="currency" id="currency">
          <option value="">请选择</option>
          <c:forEach items="${currency}" var="row">
            <option value="${row.code}">${row.code}</option>
            </c:forEach>
          </select>	 </td>
	   </tr>
    </table></td>
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
    <td rowspan="2" width="30" valign="middle"><div align="center">序号</div></td>
    <td colspan="4" rowspan="2" valign="middle"><div align="center">核算项目</div></td>
    <td colspan="4" align="center">立项成本核算(汇率<input type="text" size=8 name="exchangeRate" id="exchangeRate" value="${main.exchangeRate}"/>)</td>
  </tr>
  <tr>
    <td colspan="2"><div align="center">HKD</div>
    <td colspan="2"><div align="center" id="bibei"></div></td>
  </tr>
  <tr>
    <td rowspan="12"><nobr>采购直接成本</nobr></td>
    <td rowspan="7">1</td>
    <td colspan="2" rowspan="7"><nobr>商品购进成本</nobr></td>
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
    <td colspan="2"><div  align="center">货款金额<font color="red">▲</font></div></td>
    <td colspan="2" align="center">
		<input name="rmb3" value="${rmb3}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us3" value="${us3}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">海关关税<font color="red">▲</font></div></td>
    <td colspan="2" align="center">
		<input name="rmb40" value="${rmb40}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us40" value="${us40}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">海关增值税<font color="red">▲</font></div></td>
    <td colspan="2" align="center">
		<input name="rmb41" value="${rmb41}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us41" value="${us41}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">海关消费税<font color="red">▲</font></div></td>
    <td colspan="2" align="center">
		<input name="rmb42" value="${rmb42}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us42" value="${us42}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">小计金额</div></td>
    <td colspan="2" align="center">
		<input name="rmb43" value="${rmb43}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us43" value="${us43}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>2</td>
    <td colspan="4">进口运费</td>
    <td colspan="2" align="center">
		<input name="rmb4" value="${rmb4}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us4" value="${us4}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>3</td>
    <td colspan="4">进口保险费</td>
    <td colspan="2" align="center">
		<input name="rmb5" value="${rmb5}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us5" value="${us5}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>4</td>
    <td colspan="4">其它</td>
    <td colspan="2" align="center">
		<input name="rmb44" value="${rmb44}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us44" value="${us44}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>5</td>
    <td colspan="4">其它</td>
    <td colspan="2" align="center">
		<input name="rmb45" value="${rmb45}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us45" value="${us45}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>6</td>
    <td colspan="4">小计</td>
    <td colspan="2" align="center">
		<input name="rmb6" value="${rmb6}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="us6" value="${us6}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td rowspan="9">经营费用</td>
	<td>7</td>
    <td colspan="4">(1)运杂费、装卸费</td>
    <td colspan="2" align="center">
		<input name="rmb8" value="${rmb8}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us8" value="${us8}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>8</td>
    <td colspan="4">(2)码头费</td>
    <td colspan="2" align="center">
		<input name="rmb9" value="${rmb9}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us9" value="${us9}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>9</td>
    <td colspan="4">(3)仓储费</td>
    <td colspan="2" align="center">
		<input name="rmb10" value="${rmb10}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us10" value="${us10}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>10</td>
    <td colspan="4">(4)报关、报检费等</td>
    <td colspan="2" align="center">
		<input name="rmb11" value="${rmb11}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us11" value="${us11}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>11</td>
    <td colspan="4">直接流通费合计:</td>
    <td colspan="2" align="center"><input name="rmb7" value="${rmb7}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/></td>
    <td colspan="2"><div align="center">
      <input name="us7" value="${us7}" type="text" class="text" size="25" style="backgroundColor:red"/>
    </div></td>
  </tr>
  <tr>
    <td>12</td>
    <td colspan="4">间接管理(差旅费、执行费、邮电费)</td>
    <td colspan="2" align="center">
		<input name="rmb12" value="${rmb12}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us12" value="${us12}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>13</td>
    <td colspan="4">佣金或手续费</td>
    <td colspan="2" align="center">
		<input name="rmb13" value="${rmb13}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us13" value="${us13}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>15</td>
    <td colspan="4">其它(样品费、商品损耗、包装费等)</td>
    <td colspan="2" align="center"><input name="rmb15" value="${rmb15}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="us15" value="${us15}" type="text" class="text" size="25" />
    </div></td>
  </tr>
  <tr>
    <td>16</td>
    <td colspan="4" bordercolor="#66CCFF">小计</td>
    <td colspan="2" align="center"><input name="rmb46" value="${rmb46}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/></td>
    <td colspan="2"><div align="center">
      <input name="us46" value="${us46}" type="text" class="text" size="25" />
    </div></td>
  </tr>
  <tr>
    <td>管理费用</td>
    <td>17</td>
    <td colspan="4">分摊公司管理费</td>
    <td colspan="2" align="center">
		<input name="rmb16" value="${rmb16}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us16" value="${us16}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td rowspan="6">财务费用</td>
    <td>18</td>
    <td colspan="4">货款利息（年利率9%）</td>
    <td colspan="2" align="center">
		<input name="rmb17" value="${rmb17}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us17" value="${us17}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>19</td>
    <td colspan="4">押汇利息</td>
    <td colspan="2" align="center">
		<input name="rmb47" value="${rmb47}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us47" value="${us47}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>20</td>
    <td colspan="4">贴现利息</td>
    <td colspan="2" align="center">
		<input name="rmb48" value="${rmb48}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us48" value="${us48}" type="text" class="text" size="25" /></div></td>
  </tr>
   <tr>
    <td>21</td>
    <td colspan="4">银行结算费（开征、承兑、付款等）</td>
    <td colspan="2" align="center">
		<input name="rmb19" value="${rmb19}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us19" value="${us19}" type="text" class="text" size="25" /></div></td>
  </tr>
   <tr>
    <td>22</td>
    <td colspan="4">其它</td>
    <td colspan="2" align="center">
		<input name="rmb49" value="${rmb49}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us49" value="${us49}" type="text" class="text" size="25" /></div></td>
  </tr>
   <tr>
    <td>23</td>
    <td colspan="4">小计</td>
    <td colspan="2" align="center">
		<input name="rmb50" value="${rmb50}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="us50" value="${us50}" type="text" class="text" size="25" /></div></td>
  </tr>
   <tr>
    <td rowspan="8">销售收入</td>
    <td rowspan="8">24</td>
    <td colspan="4">合同预计利润率（百分比或单位利润）<font color="red">▲</font></td>
    <td colspan="2" align="center">
		<input name="rmb25" value="${rmb25}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="us25" value="${us25}" type="text" class="text" size="25" /></div></td>
  </tr>
   <tr> 
      <td colspan="3">合同预计利润率描述<font color="red">▲</font></td>   
      <td colspan="2" align="center"><textarea name="rmb58" id="rmb58" style="width:98%;overflow-y:visible;word-break:break-all;" >${rmb58}</textarea>     </td>
    </tr>
  <tr>
    <td colspan="2" rowspan="6" >商品销售价</td>
    <td colspan="2"><div  align="center">数量</div></td>
    <td colspan="2" align="center">
		<input name="rmb27" value="${rmb27}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us27" value="${us27}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">单价</div></td>
    <td colspan="2" align="center">
		<input name="rmb28" value="${rmb28}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us28" value="${us28}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">税率<font color="red">▲</font></div></td>
    <td colspan="2" align="center">
		<input name="rmb52" value="${rmb52}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us52" value="${us52}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">货款总额（含税）<font color="red">▲</font></div></td>
    <td colspan="2" align="center">
		<input name="rmb29" value="${rmb29}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us29" value="${us29}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">不含税货款</div></td>
    <td colspan="2" align="center">
		<input name="rmb53" value="${rmb53}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us53" value="${us53}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">销项税金</div></td>
    <td colspan="2" align="center">
		<input name="rmb54" value="${rmb54}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us54" value="${us54}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td rowspan="6">税收</td>
    <td>25</td>
    <td colspan="4">增值税进销差（销项-进项）</td>
    <td colspan="2" align="center"><input name="rmb57" value="${rmb57}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="us21" value="${us57}" type="text" class="text" size="25" />
    </div></td>
  </tr>
  <tr>
    <td>26</td>
    <td colspan="4">增值税及附加费</td>
    <td colspan="2" align="center"><input name="rmb21" value="${rmb21}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="us21" value="${us21}" type="text" class="text" size="25" />
    </div></td>
  </tr>
  <tr>
    <td>27</td>
    <td colspan="4">营业税及附加费（5.6%）</td>
    <td colspan="2" align="center"><input name="rmb22" value="${rmb22}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="us22" value="${us22}" type="text" class="text" size="25" />
    </div></td>
  </tr>
  <tr>
    <td>28</td>
    <td colspan="4">印花税</td>
    <td colspan="2" align="center"><input name="rmb23" value="${rmb23}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="us23" value="${us23}" type="text" class="text" size="25" />
    </div></td>
  </tr>
  <tr>
    <td>29</td>
    <td colspan="4">其它</td>
    <td colspan="2" align="center"><input name="rmb55" value="${rmb55}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="us55" value="${us55}" type="text" class="text" size="25" />
    </div></td>
  </tr>
  <tr>
    <td>30</td>
    <td colspan="4">小计</td>
    <td colspan="2" align="center"><input name="rmb56" value="${rmb56}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/></td>
    <td colspan="2"><div align="center">
      <input name="us56" value="${us56}" type="text" class="text" size="25" />
    </div></td>
  </tr>
  <tr>
    <td>利润</td>
    <td>31</td>
    <td colspan="4">预计利润总额或亏损总额</td>
    <td colspan="2" align="center">
		<input name="rmb31" value="${rmb31}" type="text" class="text" size="25"  />    </td>
    <td colspan="2"><div align="center"><input name="us31" value="${us31}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/></div></td>
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
/***货款金额*****/
function hkje(){
    $('rmb3').value = $('us3').value*$('exchangeRate').value;
}
addListener($('us3'),"propertychange",hkje);
addListener($('exchangeRate'),"propertychange",hkje);
/***购进成本货款金额**/
function gjcbdj(){
	$('us3').value = Utils.roundoff($('us1').value*$('us2').value,2);
}
addListener($('us1'),"propertychange",gjcbdj); 
addListener($('us2'),"propertychange",gjcbdj);
/***购进成本小计金额**/
function gjcbxjje(){
    $('rmb43').value = Utils.roundoff(getNumValue('rmb3')+getNumValue('rmb40')+getNumValue('rmb41')+getNumValue('rmb42'),2) ; 
}
addListener($('rmb3'),"propertychange",gjcbxjje); 
addListener($('rmb40'),"propertychange",gjcbxjje);
addListener($('rmb41'),"propertychange",gjcbxjje);
addListener($('rmb42'),"propertychange",gjcbxjje);
/**购进成本小计金额外币金额**/
function gjcbxjjewb(){
    $('us43').value = Utils.roundoff(getNumValue('us3')+getNumValue('us40')+getNumValue('us41')+getNumValue('us42'),2) ; 
}
addListener($('us3'),"propertychange",gjcbxjjewb); 
addListener($('us40'),"propertychange",gjcbxjjewb);
addListener($('us41'),"propertychange",gjcbxjjewb);
addListener($('us42'),"propertychange",gjcbxjjewb);
/****进口运费***/
function jkyf(){
    $('rmb4').value = Utils.roundoff(getNumValue('us4')*getNumValue('exchangeRate'),2) ; 
}
addListener($('us4'),"propertychange",jkyf);
addListener($('exchangeRate'),"propertychange",jkyf);
/****进口保险费***/
function jkbxf(){
    $('rmb5').value = Utils.roundoff(getNumValue('us5')*getNumValue('exchangeRate'),2) ; 
}
addListener($('us5'),"propertychange",jkbxf);
addListener($('exchangeRate'),"propertychange",jkbxf);
/****采购直接成本其它***/
function cgzjcbqt(){
    $('rmb44').value = Utils.roundoff(getNumValue('us44')*getNumValue('exchangeRate'),2) ; 
}
addListener($('us44'),"propertychange",cgzjcbqt);
addListener($('exchangeRate'),"propertychange",cgzjcbqt);
/****采购直接成本其它1***/
function cgzjcbqt1(){
    $('rmb45').value = Utils.roundoff(getNumValue('us45')*getNumValue('exchangeRate'),2) ; 
}
addListener($('us45'),"propertychange",cgzjcbqt1);
addListener($('exchangeRate'),"propertychange",cgzjcbqt1);
/*******/
/***采购直接成本小计***/
function cgzjcbxj(){
    $('rmb6').value = Utils.roundoff(getNumValue('rmb3')+getNumValue('rmb40')+getNumValue('rmb42')+getNumValue('rmb4')+getNumValue('rmb5')+getNumValue('rmb44')+getNumValue('rmb45'),2) ; 
}
addListener($('rmb3'),"propertychange",cgzjcbxj);
addListener($('rmb40'),"propertychange",cgzjcbxj);
addListener($('rmb42'),"propertychange",cgzjcbxj);
addListener($('rmb4'),"propertychange",cgzjcbxj);
addListener($('rmb5'),"propertychange",cgzjcbxj);
addListener($('rmb44'),"propertychange",cgzjcbxj);
addListener($('rmb45'),"propertychange",cgzjcbxj);
/***采购直接成本小计外币***/
function cgzjcbxjwb(){
    $('us6').value = Utils.roundoff(getNumValue('us3')+getNumValue('us40')+getNumValue('us42')+getNumValue('us4')+getNumValue('us5')+getNumValue('us44')+getNumValue('us45'),2) ; 
}
addListener($('us3'),"propertychange",cgzjcbxjwb);
addListener($('us40'),"propertychange",cgzjcbxjwb);
addListener($('us42'),"propertychange",cgzjcbxjwb);
addListener($('us4'),"propertychange",cgzjcbxjwb);
addListener($('us5'),"propertychange",cgzjcbxjwb);
addListener($('us44'),"propertychange",cgzjcbxjwb);
addListener($('us45'),"propertychange",cgzjcbxjwb);
/****运杂费、装卸费1***/
function yzfzxf(){
    $('rmb8').value = Utils.roundoff(getNumValue('us8')*getNumValue('exchangeRate'),2) ; 
}
addListener($('us8'),"propertychange",yzfzxf);
addListener($('exchangeRate'),"propertychange",yzfzxf);
/****码头费1***/
function mtf(){
    $('rmb9').value = Utils.roundoff(getNumValue('us9')*getNumValue('exchangeRate'),2) ; 
}
addListener($('us9'),"propertychange",mtf);
addListener($('exchangeRate'),"propertychange",mtf);
/****仓储费1***/
function ccf(){
    $('rmb10').value = Utils.roundoff(getNumValue('us10')*getNumValue('exchangeRate'),2) ; 
}
addListener($('us10'),"propertychange",ccf);
addListener($('exchangeRate'),"propertychange",ccf);
/****报关、报检费等1***/
function bfbjf(){
    $('rmb11').value = Utils.roundoff(getNumValue('us11')*getNumValue('exchangeRate'),2) ; 
}
addListener($('us11'),"propertychange",bfbjf);
addListener($('exchangeRate'),"propertychange",bfbjf);


/***直接流程费合计***/
function zjltfhj(){
    $('rmb7').value = Utils.roundoff(getNumValue('rmb8')+getNumValue('rmb9')+getNumValue('rmb10')+getNumValue('rmb11'),2) ; 
}
addListener($('rmb8'),"propertychange",zjltfhj);
addListener($('rmb9'),"propertychange",zjltfhj);
addListener($('rmb10'),"propertychange",zjltfhj);
addListener($('rmb11'),"propertychange",zjltfhj);
/***直接流程费合计外币***/
function zjltfhjwb(){
    $('us7').value = Utils.roundoff(getNumValue('us8')+getNumValue('us9')+getNumValue('us10')+getNumValue('us11'),2) ; 
}
addListener($('us8'),"propertychange",zjltfhjwb);
addListener($('us9'),"propertychange",zjltfhjwb);
addListener($('us10'),"propertychange",zjltfhjwb);
addListener($('us11'),"propertychange",zjltfhjwb);

/****经营费用间接管理(差旅费、执行费、邮电费)1***/
function jyfyjjgl(){
    $('rmb12').value = Utils.roundoff(getNumValue('us12')*getNumValue('exchangeRate'),2) ; 
}
addListener($('us12'),"propertychange",jyfyjjgl);
addListener($('exchangeRate'),"propertychange",jyfyjjgl);
/****经营费用佣金或手续费1***/
function jyfyyjhsjf(){
    $('rmb13').value = Utils.roundoff(getNumValue('us13')*getNumValue('exchangeRate'),2) ; 
}
addListener($('us13'),"propertychange",jyfyyjhsjf);
addListener($('exchangeRate'),"propertychange",jyfyyjhsjf);
/****经营费用其它(样品费、商品损耗、包装费等)1***/
function jyfyqt(){
    $('rmb15').value = Utils.roundoff(getNumValue('us15')*getNumValue('exchangeRate'),2) ; 
}
addListener($('us15'),"propertychange",jyfyqt);
addListener($('exchangeRate'),"propertychange",jyfyqt);

/***经营费用小计***/
function jyfyxj(){
    $('rmb46').value = Utils.roundoff(getNumValue('rmb7')+getNumValue('rmb12')+getNumValue('rmb13')+getNumValue('rmb15'),2) ; 
}
addListener($('rmb7'),"propertychange",jyfyxj);
addListener($('rmb12'),"propertychange",jyfyxj);
addListener($('rmb13'),"propertychange",jyfyxj);
addListener($('rmb15'),"propertychange",jyfyxj);
/***经营费用小计外币***/
function jyfyxjwb(){
    $('us46').value = Utils.roundoff(getNumValue('us7')+getNumValue('us12')+getNumValue('us13')+getNumValue('us15'),2) ; 
}
addListener($('us7'),"propertychange",jyfyxjwb);
addListener($('us12'),"propertychange",jyfyxjwb);
addListener($('us13'),"propertychange",jyfyxjwb);
addListener($('us15'),"propertychange",jyfyxjwb);

/****财务费用货款利息***/
function cwfyhklx(){
    $('rmb17').value = Utils.roundoff(getNumValue('us17')*getNumValue('exchangeRate'),2) ; 
}
addListener($('us17'),"propertychange",cwfyhklx);
addListener($('exchangeRate'),"propertychange",cwfyhklx);
/****财务费用押汇利息***/
function cwfyyhlx(){
    $('rmb47').value = Utils.roundoff(getNumValue('us47')*getNumValue('exchangeRate'),2) ; 
}
addListener($('us47'),"propertychange",cwfyyhlx);
addListener($('exchangeRate'),"propertychange",cwfyyhlx);
/****财务费用贴现利息***/
function cwfytxlx(){
    $('rmb48').value = Utils.roundoff(getNumValue('us48')*getNumValue('exchangeRate'),2) ; 
}
addListener($('us48'),"propertychange",cwfytxlx);
addListener($('exchangeRate'),"propertychange",cwfytxlx);
/****财务费用银行结算费***/
function cwfyyhjsf(){
    $('rmb19').value = Utils.roundoff(getNumValue('us19')*getNumValue('exchangeRate'),2) ; 
}
addListener($('us19'),"propertychange",cwfyyhjsf);
addListener($('exchangeRate'),"propertychange",cwfyyhjsf);
/****财务费用其它***/
function cwfyqt(){
    $('rmb49').value = Utils.roundoff(getNumValue('us49')*getNumValue('exchangeRate'),2) ; 
}
addListener($('us49'),"propertychange",cwfyqt);
addListener($('exchangeRate'),"propertychange",cwfyqt);

/***财务费用小计***/
function cwfyxj(){
    $('rmb50').value = Utils.roundoff(getNumValue('rmb17')+getNumValue('rmb19')+getNumValue('rmb47')+getNumValue('rmb48')+getNumValue('rmb49'),2) ; 
}
addListener($('rmb17'),"propertychange",cwfyxj);
addListener($('rmb19'),"propertychange",cwfyxj);
addListener($('rmb47'),"propertychange",cwfyxj);
addListener($('rmb48'),"propertychange",cwfyxj);
addListener($('rmb49'),"propertychange",cwfyxj);
/***财务费用小计外币***/
function cwfyxjwb(){
    $('us50').value = Utils.roundoff(getNumValue('us17')+getNumValue('us19')+getNumValue('us47')+getNumValue('us48')+getNumValue('us49'),2) ; 
}
addListener($('us17'),"propertychange",cwfyxjwb);
addListener($('us19'),"propertychange",cwfyxjwb);
addListener($('us47'),"propertychange",cwfyxjwb);
addListener($('us48'),"propertychange",cwfyxjwb);
addListener($('us49'),"propertychange",cwfyxjwb);
/***商品销售收入***/
function spxssr(){
   $('us29').value = Utils.roundoff($('us27').value*$('us28').value,2);
}
addListener($('us27'),"propertychange",spxssr);
addListener($('us28'),"propertychange",spxssr);
/***商品销售收入外币***/
function spxssrwb(){
   $('rmb29').value = Utils.roundoff($('us29').value*$('exchangeRate').value,2);
}
addListener($('us29'),"propertychange",spxssrwb);
addListener($('exchangeRate'),"propertychange",spxssrwb);
/***商品销售不含税货款***/
function spxsbhshk(){
   $('rmb53').value = Utils.roundoff($('rmb29').value/(1+getNumValue('rmb52')),2);
}
addListener($('rmb29'),"propertychange",spxsbhshk);
addListener($('rmb52'),"propertychange",spxsbhshk);
/***商品销销项税金
function spxsxxsj(){
   $('rmb54').value = Utils.roundoff($('rmb29').value-($('rmb29').value/(1+getNumValue('rmb52'))),2);
}
addListener($('rmb29'),"propertychange",spxsxxsj);
addListener($('rmb52'),"propertychange",spxsxxsj);
***/
/***增值税及附加费
function zzsjfjf(){
   var v = getNumValue('rmb54')-getNumValue('rmb41');
   if(v>0) $('rmb21').value = Utils.roundoff(1.12*v,2);
   else $('rmb21').value=0;
}
addListener($('rmb54'),"propertychange",zzsjfjf);
addListener($('rmb41'),"propertychange",zzsjfjf);
***/
/***印花税
function yhs(){
   $('rmb23').value = Utils.roundoff(0.0003*(getNumValue('rmb3')+getNumValue('rmb29')),2);
}
addListener($('rmb3'),"propertychange",yhs);
addListener($('rmb29'),"propertychange",yhs);
***/
/***税收小计
function ssxj(){
   $('rmb56').value = Utils.roundoff(getNumValue('rmb21')+getNumValue('rmb22')+getNumValue('rmb23')+getNumValue('rmb55'),2) ; 
}
addListener($('rmb21'),"propertychange",ssxj);
addListener($('rmb22'),"propertychange",ssxj);
addListener($('rmb23'),"propertychange",ssxj);
addListener($('rmb55'),"propertychange",ssxj);
***/
/***预计利润总额或亏损总额***/
function yjlrzehksze(){
   $('rmb31').value = Utils.roundoff(getNumValue('rmb29')-getNumValue('rmb6')-getNumValue('rmb46')-getNumValue('rmb16')-getNumValue('rmb50')-getNumValue('rmb56'),2) ; 
}
addListener($('rmb29'),"propertychange",yjlrzehksze);
addListener($('rmb6'),"propertychange",yjlrzehksze);
addListener($('rmb46'),"propertychange",yjlrzehksze);
addListener($('rmb16'),"propertychange",yjlrzehksze);
addListener($('rmb50'),"propertychange",yjlrzehksze);
addListener($('rmb56'),"propertychange",yjlrzehksze);
/***预计利润总额或亏损总额外币***/
function yjlrzehkszewb(){
   $('us31').value = Utils.roundoff(getNumValue('us29')-getNumValue('us6')-getNumValue('us46')-getNumValue('us16')-getNumValue('us50')-getNumValue('us56'),2) ; 
}
addListener($('us29'),"propertychange",yjlrzehkszewb);
addListener($('us6'),"propertychange",yjlrzehkszewb);
addListener($('us46'),"propertychange",yjlrzehkszewb);
addListener($('us16'),"propertychange",yjlrzehkszewb);
addListener($('us50'),"propertychange",yjlrzehkszewb);
addListener($('us56'),"propertychange",yjlrzehkszewb);

/**合同预计利润率（百分比或单位利润）自动计算=预计利润总额/数量**/
function htyjlrl(){
	$('us25').value = Utils.roundoff(getNumValue('us31')/getNumValue('us1'),2);
}
addListener($('us31'),"propertychange",htyjlrl);
addListener($('us1'),"propertychange",htyjlrl);
/**合同预计利润率（百分比或单位利润）自动计算=预计利润总额/数量**/
function htyjlrl2(){
	$('rmb25').value = Utils.roundoff(getNumValue('rmb31')*100/getNumValue('rmb53'),2);
}
addListener($('rmb31'),"propertychange",htyjlrl2);
addListener($('rmb53'),"propertychange",htyjlrl2);
</script>
  </body>
</html>
