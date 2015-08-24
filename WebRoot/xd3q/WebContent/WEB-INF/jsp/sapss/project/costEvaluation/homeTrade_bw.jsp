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
			<option value="0">请选择</option>
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
    <tr>
      <td rowspan="2" valign="middle"><div align="center">类<br>别</div></td>
      <td width="29" rowspan="2" valign="middle"><div align="center">序<br>号</div></td>
      <td colspan="3" rowspan="2" valign="middle"><div align="center">核算项目</div></td>
      <td colspan="2" align="center">立项成本核算<input type="hidden" name="exchangeRate" id="exchangeRate" value="1"/></td>
      <td colspan="2" align="center">实际执行情况</td>
      <td colspan="2" align="center">差异</td>
    </tr>
    <tr>
      <td colspan="2"><div align="center">rmbs￥</div>
    <div align="center" id="bibei" style="display:none"></div></tr>     
      <td colspan="2"><div align="center">rmbs￥</div>
    <div align="center" id="bibei_bw" style="display:none"></div></tr>     
      <td colspan="2"><div align="center">rmbs￥</div>
    <div align="center" id="bibei_xy" style="display:none"></div></tr>
    <tr>
      <td rowspan="8" align="center">进<br>货<br>成<br>本</td>
      <td rowspan="6">1</td>
      <td width="161" rowspan="6" align="center" valign="middle">商品购进成本</td>
      <td colspan="2"><div  align="center">数量</div></td>
      <td colspan="2" align="center"><input name="rmb1" value="${rmb1}" type="text" class="text" size="25"/>      </td>
      <td colspan="2" align="center"><input name="rmbs1" value="${rmbs1}" type="text" class="text" size="25"/>      </td>
      <td colspan="2" align="center"><input name="rmbs1_xy" value="0" type="text" class="text" size="25"/>      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center">单价</div></td>
      <td colspan="2" align="center"><input name="rmb2" value="${rmb2}" type="text" class="text" size="25"/>      </td>
      <td colspan="2" align="center"><input name="rmbs2" value="${rmbs2}" type="text" class="text" size="25"/>      </td>
       <td colspan="2" align="center"><input name="rmbs2_xy" value="0" type="text" class="text" size="25"/>      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center">税率(填小数)<font color="red">▲</font></div></td>
      <td colspan="2" align="center"><input name="rmb40" value="${rmb40}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs40" value="${rmbs40}" type="text" class="text" size="25" />      </td>
       <td colspan="2" align="center"><input name="rmbs40_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center">货款总额（含税）<font color="red">▲</font></div></td>
      <td colspan="2" align="center"><input name="rmb3" value="${rmb3}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs3" value="${rmbs3}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs3_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center">不含税货款</div></td>
      <td colspan="2" align="center"><input name="rmb41" value="${rmb41}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs41" value="${rmbs41}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs41_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center">进项税金</div></td>
      <td colspan="2" align="center"><input name="rmb42" value="${rmb42}" type="text" class="text" size="25"/>      </td>
      <td colspan="2" align="center"><input name="rmbs42" value="${rmbs42}" type="text" class="text" size="25"/>      </td>
       <td colspan="2" align="center"><input name="rmbs42_xy" value="0" type="text" class="text" size="25"/>      </td>
    </tr>
    <tr>
      <td>2</td>
      <td colspan="3">其它</td>
      <td colspan="2" align="center"><input name="rmb4" value="${rmb4}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs4" value="${rmbs4}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs4_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>3</td>
      <td colspan="3">小计</td>
      <td colspan="2" align="center"><input name="rmb6" value="${rmb6}" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>      </td>
      <td colspan="2" align="center"><input name="rmbs6" value="${rmbs6}" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>      </td>
      <td colspan="2" align="center"><input name="rmbs6_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td rowspan="9" align="center">经<br>营<br>费<br>用</td>
      <td>4</td>
      <td colspan="3">(1)运杂费、装卸费</td>
      <td colspan="2" align="center"><input name="rmb8" value="${rmb8}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs8" value="${rmbs8}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs8_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>5</td>
      <td colspan="3">(2)码头费</td>
      <td colspan="2" align="center"><input name="rmb9" value="${rmb9}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs9" value="${rmbs9}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs9_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>6</td>
      <td colspan="3">(3)仓储费</td>
      <td colspan="2" align="center"><input name="rmb10" value="${rmb10}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs10" value="${rmbs10}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs10_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>7</td>
      <td colspan="3">(4)保险费等</td>
      <td colspan="2" align="center"><input name="rmb11" value="${rmb11}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs11" value="${rmbs11}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs11_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>8</td>
      <td colspan="3">直接流通费合计：</td>
      <td colspan="2" align="center"><input name="rmb7" value="${rmb7}" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>      </td>
      <td colspan="2" align="center"><input name="rmbs7" value="${rmbs7}" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>      </td>
      <td colspan="2" align="center"><input name="rmbs7_xy" value="0" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>      </td>
    </tr>
    <tr>
      <td>9</td>
      <td colspan="3">间接管理(差旅费、执行费、邮电费)</td>
      <td colspan="2" align="center"><input name="rmb12" value="${rmb12}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs12" value="${rmbs12}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs12_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>10</td>
      <td colspan="3">佣金或手续费</td>
      <td colspan="2" align="center"><input name="rmb13" value="${rmb13}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs13" value="${rmbs13}" type="text" class="text" size="25" />      </td>
       <td colspan="2" align="center"><input name="rmbs13_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>12</td>
      <td colspan="3" bordercolor="#66CCFF">其它(样品费、商品损耗、包装费等)</td>
      <td colspan="2" align="center"><input name="rmb15" value="${rmb15}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs15" value="${rmbs15}" type="text" class="text" size="25" />      </td>
       <td colspan="2" align="center"><input name="rmbs15_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>13</td>
      <td colspan="3">小计</td>
      <td colspan="2" align="center"><input name="rmb14" value="${rmb14}" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>      </td>
      <td colspan="2" align="center"><input name="rmbs14" value="${rmbs14}" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>      </td>
      <td colspan="2" align="center"><input name="rmbs14_xy" value="0" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>      </td>
    </tr>
    <tr>
      <td>管理费用</td>
      <td>14</td>
      <td colspan="3">分摊公司管理费</td>
      <td colspan="2" align="center"><input name="rmb16" value="${rmb16}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs16" value="${rmbs16}" type="text" class="text" size="25" />      </td>
       <td colspan="2" align="center"><input name="rmbs16_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td rowspan="5" align="center">财<br>务<br>费<br>用</td>
      <td>15</td>
      <td colspan="3">货款利息（年利率7.216%）</td>
      <td colspan="2" align="center"><input name="rmb17" value="${rmb17}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs17" value="${rmbs17}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs17_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>16</td>
      <td colspan="3">贴现利息</td>
      <td colspan="2" align="center"><input name="rmb18" value="${rmb18}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs18" value="${rmbs18}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs18_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>17</td>
      <td colspan="3">银行结算费</td>
      <td colspan="2" align="center"><input name="rmb19" value="${rmb19}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs19" value="${rmbs19}" type="text" class="text" size="25" />      </td>
       <td colspan="2" align="center"><input name="rmbs19_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>18</td>
      <td colspan="3">其它</td>
      <td colspan="2" align="center"><input name="rmb43" value="${rmb43}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs43" value="${rmbs43}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs43_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td>19</td>
      <td colspan="3">小计</td>
      <td colspan="2" align="center"><input name="rmb44" value="${rmb44}" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>      </td>
      <td colspan="2" align="center"><input name="rmbs44" value="${rmbs44}" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>      </td>
      <td colspan="2" align="center"><input name="rmbs44_xy" value="0" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>      </td>
    </tr>
    <tr>
      <td rowspan="7" align="center">销<br>售<br>收<br>入</td>
      <td rowspan="7">20</td>
      <td colspan="3">税前合同预计利润率（百分比或单位利润）<font color="red">▲</font></td>
      <td colspan="2" align="center"><input name="rmb25" value="${rmb25}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs25" value="${rmbs25}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs25_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td rowspan="6" >商品销售价</td>
      <td colspan="2"><div  align="center">数量</div></td>
      <td colspan="2" align="center"><input name="rmb27" value="${rmb27}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs27" value="${rmbs27}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs27_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center">单价</div></td>
      <td colspan="2" align="center"><input name="rmb28" value="${rmb28}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs28" value="${rmbs28}" type="text" class="text" size="25" />      </td>
       <td colspan="2" align="center"><input name="rmbs28_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center">税率(填小数)<font color="red">▲</font></div></td>
      <td colspan="2" align="center"><input name="rmb45" value="${rmb45}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs45" value="${rmbs45}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs45_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center">货款总额（含税）<font color="red">▲</font></div></td>
      <td colspan="2" align="center"><input name="rmb29" value="${rmb29}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs29" value="${rmbs29}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs29_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center">不含税货款</div></td>
      <td colspan="2" align="center"><input name="rmb46" value="${rmb46}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs46" value="${rmbs46}" type="text" class="text" size="25" />      </td>
       <td colspan="2" align="center"><input name="rmbs46_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
    <tr>
      <td colspan="2"><div  align="center"> 销项税金</div></td>
      <td colspan="2" align="center"><input name="rmb47" value="${rmb47}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs47" value="${rmbs47}" type="text" class="text" size="25" />      </td>
      <td colspan="2" align="center"><input name="rmbs47_xy" value="0" type="text" class="text" size="25" />      </td>
    </tr>
     <tr>
    <td>税前利润</td>
    <td>21</td>
    <td colspan="3">税前利润总额</td>
    <td colspan="2" align="center">
		<input name="rmb_sqln" value="0" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>    </td>
   
    <td colspan="2" align="center">
		<input name="rmbs_sqln" value="0" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>    </td>
   <td colspan="2" align="center">
		<input name="rmbs_sqln_xy" value="0" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>    </td>
  </tr>
    <tr>
      <td rowspan="5" align="center">税<br>收</td>
      <td height="35">22</td>
      <td colspan="3">增值税及附加费</td>
      <td colspan="2" align="center"><input name="rmb21" value="${rmb21}" type="text" class="text" size="25" /></td>
      <td colspan="2" align="center"><input name="rmbs21" value="${rmbs21}" type="text" class="text" size="25" /></td>
      <td colspan="2" align="center"><input name="rmbs21_xy" value="0" type="text" class="text" size="25" /></td>
    </tr>
    <tr>
      <td>23</td>
      <td colspan="3">营业税及附加费（5.55%）</td>
      <td colspan="2" align="center"><input name="rmb22" value="${rmb22}" type="text" class="text" size="25" /></td>
      <td colspan="2" align="center"><input name="rmbs22" value="${rmbs22}" type="text" class="text" size="25" /></td>
       <td colspan="2" align="center"><input name="rmbs22_xy" value="0" type="text" class="text" size="25" /></td>
    </tr>
    <tr>
      <td>24</td>
      <td colspan="3">印花税</td>
      <td colspan="2" align="center"><input name="rmb23" value="${rmb23}" type="text" class="text" size="25" /></td>
      <td colspan="2" align="center"><input name="rmbs23" value="${rmbs23}" type="text" class="text" size="25" /></td>
      <td colspan="2" align="center"><input name="rmbs23_xy" value="0" type="text" class="text" size="25" /></td>
    </tr>
    <tr>
      <td>25</td>
      <td colspan="3">其它</td>
      <td colspan="2" align="center"><input name="rmb48" value="${rmb48}" type="text" class="text" size="25" /></td>
      <td colspan="2" align="center"><input name="rmbs48" value="${rmbs48}" type="text" class="text" size="25" /></td>
      <td colspan="2" align="center"><input name="rmbs48_xy" value="0" type="text" class="text" size="25" /></td>
    </tr>
	<tr>
      <td>26</td>
      <td colspan="3">小计</td>
      <td colspan="2" align="center"><input name="rmb49" value="${rmb49}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/></td>
      <td colspan="2" align="center"><input name="rmbs49" value="${rmbs49}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/></td>
      <td colspan="2" align="center"><input name="rmbs49_xy" value="0" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/></td>
    </tr>
    <tr>
      <td align="center">利润</td>
      <td>27</td>
      <td colspan="3">预计利润总额或亏损总额</td>
      <td colspan="2" align="center"><input name="rmb31" value="${rmb31}" type="text" class="text" size="25"   readonly style="background-color:#D0D0D0"/>      </td>
      <td colspan="2" align="center"><input name="rmbs31" value="${rmbs31}" type="text" class="text" size="25"   readonly style="background-color:#D0D0D0"/>      </td>
   		<td colspan="2" align="center"><input name="rmbs31_xy" value="0" type="text" class="text" size="25"   readonly style="background-color:#D0D0D0"/>      </td>
    </tr>
  </table>
  <script language="javascript">
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
	$('rmb42').value=Utils.roundoff($('rmb3').value - $('rmb3').value/(1+getNumValue('rmb40')),2);
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
$('rmb47').value = Utils.roundoff(getNumValue('rmb29') - getNumValue('rmb29')/(1+getNumValue('rmb45')),2);
}
addListener($('rmb29'),"propertychange",xssj);
addListener($('rmb45'),"propertychange",xssj);
/***增值税及附加费用***/
function zzsjfjfy(){
var v = 1.12*(getNumValue('rmb47') - getNumValue('rmb42')) ;
if(v<0) v = 0;
$('rmb21').value = Utils.roundoff(v,2);
}

addListener($('rmb47'),"propertychange",zzsjfjfy);
addListener($('rmb42'),"propertychange",zzsjfjfy);
/***印花税***/
function yhs(){
$('rmb23').value = Utils.roundoff(0.0003*(getNumValue('rmb3') +getNumValue('rmb29')),2);
}
addListener($('rmb3'),"propertychange",yhs);
addListener($('rmb29'),"propertychange",yhs);
/***税收小计***/
function ssxj(){
$('rmb49').value = Utils.roundoff(getNumValue('rmb21') + getNumValue('rmb22') + getNumValue('rmb23') + getNumValue('rmb48'),2);
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

/***进货成本小计**/
function jhcbxj_s (){
$('rmbs6').value = getNumValue('rmbs4') + getNumValue('rmbs3');
}
addListener($('rmbs4'),"propertychange",jhcbxj_s);
addListener($('rmbs3'),"propertychange",jhcbxj_s);
/***直接流通费合计***/
function zjltfhj_s (){
$('rmbs7').value = Utils.roundoff(getNumValue('rmbs8') + getNumValue('rmbs9') + getNumValue('rmbs10') + getNumValue('rmbs11'),2);
}
addListener($('rmbs8'),"propertychange",zjltfhj_s);
addListener($('rmbs9'),"propertychange",zjltfhj_s);
addListener($('rmbs10'),"propertychange",zjltfhj_s);
addListener($('rmbs11'),"propertychange",zjltfhj_s);
/***经营费用小计***/
function jyfyxj_s (){
$('rmbs14').value = Utils.roundoff(getNumValue('rmbs7') + getNumValue('rmbs12') + getNumValue('rmbs13') + getNumValue('rmbs15'),2);
}
addListener($('rmbs7'),"propertychange",jyfyxj_s);
addListener($('rmbs12'),"propertychange",jyfyxj_s);
addListener($('rmbs13'),"propertychange",jyfyxj_s);
addListener($('rmbs15'),"propertychange",jyfyxj_s);
/***财务费用小计***/
function cwfyxj_s (){
$('rmbs44').value = Utils.roundoff(getNumValue('rmbs17') + getNumValue('rmbs18') + getNumValue('rmbs19') + getNumValue('rmbs43'),2);
}
addListener($('rmbs17'),"propertychange",cwfyxj_s);
addListener($('rmbs18'),"propertychange",cwfyxj_s);
addListener($('rmbs19'),"propertychange",cwfyxj_s);
addListener($('rmbs43'),"propertychange",cwfyxj_s);
/***商品销售单价***/
function spxsdj_s (){
$('rmbs28').value = Utils.roundoff(getNumValue('rmbs29')/getNumValue('rmbs27'),2);
}
addListener($('rmbs29'),"propertychange",spxsdj_s);
addListener($('rmbs27'),"propertychange",spxsdj_s);
/***不含税货款***/
function bhshk_s (){
$('rmbs46').value = Utils.roundoff(getNumValue('rmbs29')/(1+getNumValue('rmbs45')),2);
}
addListener($('rmbs29'),"propertychange",bhshk_s);
addListener($('rmbs45'),"propertychange",bhshk_s);
/***销项税金***/
function xssj_s (){
$('rmbs47').value = Utils.roundoff(getNumValue('rmbs29') - getNumValue('rmbs29')/(1+getNumValue('rmbs45')),2);
}
addListener($('rmbs29'),"propertychange",xssj_s);
addListener($('rmbs45'),"propertychange",xssj_s);
/***增值税及附加费用***/
function zzsjfjfy_s (){
var v = 1.11*(getNumValue('rmbs47') - getNumValue('rmbs42')) ;
if(v<0) v = 0;
$('rmbs21').value = Utils.roundoff(v,2);
}

addListener($('rmbs47'),"propertychange",zzsjfjfy_s);
addListener($('rmbs42'),"propertychange",zzsjfjfy_s);
/***印花税***/
function yhs_s (){
$('rmbs23').value = Utils.roundoff(0.0003*(getNumValue('rmbs3') +getNumValue('rmbs29')),2);
}
addListener($('rmbs3'),"propertychange",yhs_s);
addListener($('rmbs29'),"propertychange",yhs_s);
/***税收小计***/
function ssxj_s (){
$('rmbs49').value = Utils.roundoff(getNumValue('rmbs21') + getNumValue('rmbs22') + getNumValue('rmbs23') + getNumValue('rmbs48'),2);
}
addListener($('rmbs21'),"propertychange",ssxj_s);
addListener($('rmbs22'),"propertychange",ssxj_s);
addListener($('rmbs23'),"propertychange",ssxj_s);
addListener($('rmbs48'),"propertychange",ssxj_s);

/**预计利润总额或亏损总额**/
function yjlrzehksze_s (){
$('rmbs31').value = Utils.roundoff(getNumValue('rmbs29') - getNumValue('rmbs6') - getNumValue('rmbs14') - getNumValue('rmbs16') - getNumValue('rmbs44') - getNumValue('rmbs49'),2);
}
addListener($('rmbs29'),"propertychange",yjlrzehksze_s);
addListener($('rmbs6'),"propertychange",yjlrzehksze_s);
addListener($('rmbs14'),"propertychange",yjlrzehksze_s);
addListener($('rmbs16'),"propertychange",yjlrzehksze_s);
addListener($('rmbs44'),"propertychange",yjlrzehksze_s);
addListener($('rmbs49'),"propertychange",yjlrzehksze_s);
//税前利润总额
function sqlnze(){
	$('rmb_sqln').value = Utils.roundoff(getNumValue('rmb46')-getNumValue('rmb41')-getNumValue('rmb14')-getNumValue('rmb16')-getNumValue('rmb44'),2) ; 
	$('rmbs_sqln').value = Utils.roundoff(getNumValue('rmbs46')-getNumValue('rmbs41')-getNumValue('rmbs14')-getNumValue('rmbs16')-getNumValue('rmbs44'),2) ; 
	$('rmb25').value = Utils.roundoff($('rmb_sqln').value/$('rmb46').value * 100,2);
	$('rmbs25').value = Utils.roundoff($('rmbs_sqln').value/$('rmbs46').value * 100,2);
}
//差异
function xy(){
	for(var i=1;i<60;i++){
		if(i==58)continue;
		var rmb = Ext.getDom('rmb' + i);
		var rmbs = Ext.getDom('rmbs' + i);
		var rmbs_xy = Ext.getDom('rmbs' + i + '_xy');
		if((typeof(rmb)=='undefined') || null == rmb || (typeof(rmbs)=='undefined') || null == rmbs || (typeof(rmbs_xy)=='undefined') || null == rmbs_xy){			 	
		 	continue;
		}else{
			rmbs_xy.value = Utils.roundoff(getNumValue('rmb' + i)-getNumValue('rmbs' + i),2) ; 					
		}		
	}
	$('rmbs_sqln_xy').value =  Utils.roundoff(getNumValue('rmb_sqln')-getNumValue('rmbs_sqln'),2) ; 	
}
jhcbxj_s ();
zjltfhj_s ();
jyfyxj_s ();
cwfyxj_s ();
spxsdj_s ();
bhshk_s ();
xssj_s ();
zzsjfjfy_s ();
yhs_s ();
ssxj_s ();
yjlrzehksze_s ();
sqlnze();
xy();
</script>
  </body>
</html>
