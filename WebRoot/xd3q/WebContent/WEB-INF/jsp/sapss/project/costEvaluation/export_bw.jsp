<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  </head>
  <!---出口利润测算表----->
  <body>
    <table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" >
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
	<td colspan="6">	
		<div id="div_ymatGroup"></div>
		<fisc:dictionary width="100" hiddenName = "ymatGroup" dictionaryName="YDMATGROUP" value="${main.ymatGroup}" divId="div_ymatGroup" isNeedAuth="false"   ></fisc:dictionary>
	</td>
 </tr>
  <tr>
    <td><input name="placeOfProduction" type="hidden" size="10" class="text" value="${main.placeOfProduction}"/><font color="red">*</font>金额</td>
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
  <tr>
    <td rowspan="2" valign="middle"><div align="center">类别</div></td>
    <td width="14" rowspan="2" valign="middle"><div align="center">序号</div></td>
    <td colspan="4" rowspan="2" valign="middle"><div align="center">核算项目</div></td>
    <td colspan="4" align="center">立项成本核算(汇率<input type="text" size=8 name="exchangeRate" id="exchangeRate" value="${main.exchangeRate}"/>)</td>
    <td colspan="4" align="center">实际执行情况</td>
  </tr>
  <tr>
    <td colspan="2"><div align="center">rmbs￥</div>
    <td colspan="2"><div align="center" id="bibei"></div></td>
    <td colspan="2"><div align="center">rmbs￥</div>
    <td colspan="2"><div align="center" id="bibei_bw"></div></td>
  </tr>
  <tr>
    <td rowspan="8">进货成本</br></td>
    <td rowspan="6">1</td>
    <td colspan="2" rowspan="6">商品购进成本</td>
    <td colspan="2"><div  align="center">数量</div></td>
    <td colspan="2" align="center">
		<input name="rmb1" value="${rmb1}" type="text" class="text" size="25"/>    </td>
    <td colspan="2"><div align="center"><input name="us1" value="${us1}" type="text" class="text" size="25"/></div></td>
    <td colspan="2" align="center">
		<input name="rmbs1" value="${rmbs1}" type="text" class="text" size="25"/>    </td>
    <td colspan="2"><div align="center"><input name="uss1" value="${uss1}" type="text" class="text" size="25"/></div></td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">单价</div></td>
    <td colspan="2" align="center">
		<input name="rmb2" value="${rmb2}" type="text" class="text" size="25"/>    </td>
    <td colspan="2"><div align="center"><input name="us2" value="${us2}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs2" value="${rmbs2}" type="text" class="text" size="25"/>    </td>
    <td colspan="2"><div align="center"><input name="uss2" value="${uss2}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">税率(填小数)<font color="red">▲</font></div></td>
    <td colspan="2" align="center">
		<input name="rmb40" value="${rmb40}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us40" value="${us40}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs40" value="${rmbs40}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss40" value="${uss40}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">货款总额（含税）<font color="red">▲</font></div></td>
    <td colspan="2" align="center">
		<input name="rmb3" value="${rmb3}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us3" value="${us3}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs3" value="${rmbs3}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss3" value="${uss3}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">不含税货款</div></td>
    <td colspan="2" align="center">
		<input name="rmb41" value="${rmb41}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us41" value="${us41}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs41" value="${rmbs41}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss41" value="${uss41}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">进项税金</div></td>
    <td colspan="2" align="center">
		<input name="rmb42" value="${rmb42}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us42" value="${us42}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs42" value="${rmbs42}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss42" value="${uss42}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>2</td>
    <td colspan="4">其它</td>
    <td colspan="2" align="center">
		<input name="rmb43" value="${rmb43}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us43" value="${us43}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs43" value="${rmbs43}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss43" value="${uss43}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>3</td>
    <td colspan="4">小计</td>
    <td colspan="2" align="center">
		<input name="rmb6" value="${rmb6}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="us6" value="${us6}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs6" value="${rmbs6}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="uss6" value="${uss6}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td rowspan="9">经营费用</td>
	<td>4</td>
    <td colspan="4">(1)运杂费、装卸费</td>
    <td colspan="2" align="center">
		<input name="rmb8" value="${rmb8}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us8" value="${us8}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs8" value="${rmbs8_e}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss8" value="${uss8_e}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>5</td>
    <td colspan="4">(2)码头费</td>
    <td colspan="2" align="center">
		<input name="rmb9" value="${rmb9}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us9" value="${us9}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs9" value="${rmbs9}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss9" value="${uss9}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>6</td>
    <td colspan="4">(3)仓储费</td>
    <td colspan="2" align="center">
		<input name="rmb10" value="${rmb10}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us10" value="${us10}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs10" value="${rmbs10}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss10" value="${uss10}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>7</td>
    <td colspan="4">(4)报关、报检费等</td>
    <td colspan="2" align="center">
		<input name="rmb11" value="${rmb11}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us11" value="${us11}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs11" value="${rmbs11_e}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss11" value="${uss11_e}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>8</td>
    <td colspan="4">直接流通费合计:</td>
    <td colspan="2" align="center"><input name="rmb7" value="${rmb7}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/></td>
    <td colspan="2"><div align="center">
      <input name="us7" value="${us7}" type="text" class="text" size="25" style="backgroundColor:red"/>
    <td colspan="2" align="center"><input name="rmbs7" value="${rmbs7}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/></td>
    <td colspan="2"><div align="center">
      <input name="uss7" value="${uss7}" type="text" class="text" size="25" style="backgroundColor:red"/>
    </div></td>
  </tr>
  <tr>
    <td>9</td>
    <td colspan="4">间接管理(差旅费、执行费、邮电费)</td>
    <td colspan="2" align="center">
		<input name="rmb12" value="${rmb12}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us12" value="${us12}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs12" value="${rmbs12}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss12" value="${uss12}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>10</td>
    <td colspan="4">佣金或手续费</td>
    <td colspan="2" align="center">
		<input name="rmb13" value="${rmb13}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us13" value="${us13}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs13" value="${rmbs13}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss13" value="${uss13}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>12</td>
    <td colspan="4" bordercolor="#66CCFF">其它(样品费、商品损耗、包装费等)</td>
    <td colspan="2" align="center">
		<input name="rmb15" value="${rmb15}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us15" value="${us15}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs15" value="${rmbs15}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss15" value="${uss15}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>13</td>
    <td colspan="4">小计</td>
    <td colspan="2" align="center">
		<input name="rmb44" value="${rmb44}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="us44" value="${us44}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs44" value="${rmbs44}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="uss44" value="${uss44}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>管理费用</td>
    <td>14</td>
    <td colspan="4">分摊公司管理费</td>
    <td colspan="2" align="center">
		<input name="rmb16" value="${rmb16}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us16" value="${us16}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs16" value="${rmbs16}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss16" value="${uss16}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td rowspan="6">财务费用</td>
    <td>15</td>
    <td colspan="4">货款利息（年利率7.216%）</td>
    <td colspan="2" align="center">
		<input name="rmb17" value="${rmb17}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us17" value="${us17}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs17" value="${rmbs17}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss17" value="${uss17}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>16</td>
    <td colspan="4">贴现利息</td>
    <td colspan="2" align="center"><input name="rmb45" value="${rmb45}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center"><input name="us45" value="${us45}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center"><input name="rmbs45" value="${rmbs45}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center"><input name="uss45" value="${uss45}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>17</td>
    <td colspan="4">退税款占用利息T=7.216%<font color="red">▲</font></td>
    <td colspan="2" align="center"><input name="rmb18" value="${rmb18}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="us18" value="${us18}" type="text" class="text" size="25" />
    </div></td>
    <td colspan="2" align="center"><input name="rmbs18" value="${rmbs18}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="uss18" value="${uss18}" type="text" class="text" size="25" />
    </div></td>
  </tr>
  <tr>
    <td>18</td>
    <td colspan="4">银行结算费</td>
    <td colspan="2" align="center">
		<input name="rmb19" value="${rmb19}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us19" value="${us19}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs19" value="${rmbs19}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss19" value="${uss19}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>19</td>
    <td colspan="4">其它</td>
    <td colspan="2" align="center">
		<input name="rmb46" value="${rmb46}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us46" value="${us46}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs46" value="${rmbs46}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss46" value="${uss46}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>20</td>
    <td colspan="4">小计</td>
    <td colspan="2" align="center">
		<input name="rmb47" value="${rmb47}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="us47" value="${us47}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs47" value="${rmbs47}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="uss47" value="${uss47}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td rowspan="4">销售收入</td>
    <td>20</td>
    <td colspan="4">合同预计利润率（百分比或单位利润）<font color="red">▲</font></td>
    <td colspan="2" align="center"><input name="rmb25" value="${rmb25}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="us25" value="${us25}" type="text" class="text" size="25" />
    </div></td>
    <td colspan="2" align="center"><input name="rmbs25" value="${rmbs25}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="uss25" value="${uss25}" type="text" class="text" size="25" />
    </div></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="2" >&nbsp;</td>
    <td colspan="2"><div  align="center">数量</div></td>
    <td colspan="2" align="center">
		<input name="rmb27" value="${rmb27}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us27" value="${us27}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs27" value="${rmbs27}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss27" value="${uss27}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>21</td>
    <td colspan="2">商品销售价</td>
    <td colspan="2"><div  align="center">单价</div></td>
    <td colspan="2" align="center">
		<input name="rmb28" value="${rmb28}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us28" value="${us28}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs28" value="${rmbs28}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss28" value="${uss28}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="2">&nbsp;</td>
    <td colspan="2"><div  align="center">金额<font color="red">▲</font></div></td>
    <td colspan="2" align="center">
		<input name="rmb29" value="${rmb29}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us29" value="${us29}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs29" value="${rmbs29}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss29" value="${uss29}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td rowspan="7">税收</td>
    <td>22</td>
    <td colspan="4">增值税及附加费（出口征税填此栏,含出口视同销售）</td>
    <td colspan="2" align="center"><input name="rmb21" value="${rmb21}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="us21" value="${us21}" type="text" class="text" size="25" />
    </div></td>
    <td colspan="2" align="center"><input name="rmbs21" value="${rmbs21}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="uss21" value="${uss21}" type="text" class="text" size="25" />
    </div></td>
  </tr>
  <tr>
    <td>23</td>
    <td colspan="4">营业税及附加费（5.55%）</td>
    <td colspan="2" align="center"><input name="rmb22" value="${rmb22}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="us22" value="${us22}" type="text" class="text" size="25" />
    </div></td>
    <td colspan="2" align="center"><input name="rmbs22" value="${rmbs22}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="uss22" value="${uss22}" type="text" class="text" size="25" />
    </div></td>
  </tr>
  <tr>
    <td>24</td>
    <td colspan="4">印花税</td>
    <td colspan="2" align="center"><input name="rmb23" value="${rmb23}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="us23" value="${us23}" type="text" class="text" size="25" />
    </div></td>
    <td colspan="2" align="center"><input name="rmbs23" value="${rmbs23}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="uss23" value="${uss23}" type="text" class="text" size="25" />
    </div></td>
  </tr>
  <tr>
    <td>25</td>
    <td colspan="4">出口退税额（退税率<font color="red">▲</font>
      <input name="other3" value="${other3}" type="text" size="4" />
    %)</td>
    <td colspan="2" align="center"><input name="rmb24" value="${rmb24}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="us24" value="${us24}" type="text" class="text" size="25" />
    </div></td>
    <td colspan="2" align="center"><input name="rmbs24" value="${rmbs24}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="uss24" value="${uss24}" type="text" class="text" size="25" />
    </div></td>
  </tr>
  <tr>
    <td>26</td>
    <td colspan="4">出口关税</td>
    <td colspan="2" align="center"><input name="rmb20" value="${rmb20}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="us20" value="${us20}" type="text" class="text" size="25" />
    </div></td>
    <td colspan="2" align="center"><input name="rmbs20" value="${rmbs20}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="uss20" value="${uss20}" type="text" class="text" size="25" />
    </div></td>
  </tr>
  <tr>
    <td>27</td>
    <td colspan="4">其它</td>
    <td colspan="2" align="center">
		<input name="rmb48" value="${rmb48}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us48" value="${us48}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs48" value="${rmbs48}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss48" value="${uss48}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>28</td>
    <td colspan="4">小计</td>
    <td colspan="2" align="center">
		<input name="rmb49" value="${rmb49}" type="text" class="text" size="25"   readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="us49" value="${us49}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs49" value="${rmbs49}" type="text" class="text" size="25"   readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="uss49" value="${uss49}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>利润</td>
    <td>23</td>
    <td colspan="4">预计利润总额或亏损总额</td>
    <td colspan="2" align="center">
		<input name="rmb31" value="${rmb31}" type="text" class="text" size="25"   readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="us31" value="${us31}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs31" value="${rmbs31}" type="text" class="text" size="25"   readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="uss31" value="${uss31}" type="text" class="text" size="25" /></div></td>
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
$('rmb6').value = Utils.roundoff(getNumValue('rmb43') + getNumValue('rmb3'),2);
}
addListener($('rmb43'),"propertychange",jhcbxj);
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
$('rmb44').value = Utils.roundoff(getNumValue('rmb7') + getNumValue('rmb12') + getNumValue('rmb13') + getNumValue('rmb15'),2);
}
addListener($('rmb7'),"propertychange",jyfyxj);
addListener($('rmb12'),"propertychange",jyfyxj);
addListener($('rmb13'),"propertychange",jyfyxj);
addListener($('rmb15'),"propertychange",jyfyxj);
/***退税占用利息**/
function tszylx(){
$('rmb18').value=Utils.roundoff($('rmb24').value*0.07216*0.25,2);
}
addListener($('rmb24'),"propertychange",tszylx);
/***财务费用小计***/
function cwfyxj(){
$('rmb47').value = Utils.roundoff(getNumValue('rmb17') + getNumValue('rmb18') + getNumValue('rmb19') + getNumValue('rmb45')+ getNumValue('rmb46'),2);
}
addListener($('rmb17'),"propertychange",cwfyxj);
addListener($('rmb18'),"propertychange",cwfyxj);
addListener($('rmb19'),"propertychange",cwfyxj);
addListener($('rmb45'),"propertychange",cwfyxj);
addListener($('rmb46'),"propertychange",cwfyxj);
/***商品销售数量**/
function spxsslRmb(){
$('rmb27').value=$('us27').value;
}
addListener($('us27'),"propertychange",spxsslRmb);
/**
function spxsslUs(){
$('us27').value=$('rmb27').value;
}
addListener($('rmb27'),"propertychange",spxsslUs);
***/
/***商品销售单价***/
function spxsdjRmb(){
$('rmb28').value = Utils.roundoff(getNumValue('rmb29')/getNumValue('rmb27'),2);
}
addListener($('rmb29'),"propertychange",spxsdjRmb);
addListener($('rmb27'),"propertychange",spxsdjRmb);
function spxsdjUs(){
$('us28').value = Utils.roundoff(getNumValue('us29')/getNumValue('rmb27'),2);
}
addListener($('us29'),"propertychange",spxsdjUs);
addListener($('rmb27'),"propertychange",spxsdjUs);
/***商品销售金额**/
function spxsje(){
$('rmb29').value = Utils.roundoff(getNumValue('us29')*getNumValue('exchangeRate'),2);
}
addListener($('us29'),"propertychange",spxsje);
addListener($('exchangeRate'),"propertychange",spxsje);
/***印花税***/
function yhs(){
$('rmb23').value = Utils.roundoff(0.0003*(getNumValue('rmb3') +getNumValue('rmb29')),2);
}
addListener($('rmb3'),"propertychange",yhs);
addListener($('rmb29'),"propertychange",yhs);
/***出口退税额***/
function cktse(){
$('rmb24').value = Utils.roundoff($('rmb41').value*$('other3').value/100 ,2)
}
addListener($('rmb41'),"propertychange",cktse);
addListener($('other3'),"propertychange",cktse);
/***税收小计***/
function ssxj(){
$('rmb49').value = Utils.roundoff(getNumValue('rmb21')+getNumValue('rmb22') + getNumValue('rmb23')-getNumValue('rmb24')+getNumValue('rmb20')+getNumValue('rmb48'),2);
}
addListener($('rmb21'),"propertychange",ssxj);
addListener($('rmb22'),"propertychange",ssxj);
addListener($('rmb23'),"propertychange",ssxj);
addListener($('rmb24'),"propertychange",ssxj);
addListener($('rmb20'),"propertychange",ssxj);
addListener($('rmb48'),"propertychange",ssxj);

/**预计利润总额或亏损总额**/
function yjlrzehksze(){
$('rmb31').value = Utils.roundoff(getNumValue('rmb29')-getNumValue('rmb6')-getNumValue('rmb44') - getNumValue('rmb16') - getNumValue('rmb47') - getNumValue('rmb49'),2);
}
addListener($('rmb29'),"propertychange",yjlrzehksze);
addListener($('rmb6'),"propertychange",yjlrzehksze);
addListener($('rmb44'),"propertychange",yjlrzehksze);
addListener($('rmb16'),"propertychange",yjlrzehksze);
addListener($('rmb47'),"propertychange",yjlrzehksze);
addListener($('rmb49'),"propertychange",yjlrzehksze);

/***进货成本小计**/
function jhcbxj_s (){
$('rmbs6').value = Utils.roundoff(getNumValue('rmbs43') + getNumValue('rmbs3'),2);
}
addListener($('rmbs43'),"propertychange",jhcbxj_s);
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
$('rmbs44').value = Utils.roundoff(getNumValue('rmbs7') + getNumValue('rmbs12') + getNumValue('rmbs13') + getNumValue('rmbs15'),2);
}
addListener($('rmbs7'),"propertychange",jyfyxj_s);
addListener($('rmbs12'),"propertychange",jyfyxj_s);
addListener($('rmbs13'),"propertychange",jyfyxj_s);
addListener($('rmbs15'),"propertychange",jyfyxj_s);
/***退税占用利息**/
function tszylx_s (){
$('rmbs18').value=Utils.roundoff($('rmbs24').value*0.07216*0.25,2);
}
addListener($('rmbs24'),"propertychange",tszylx_s);
/***财务费用小计***/
function cwfyxj_s (){
$('rmbs47').value = Utils.roundoff(getNumValue('rmbs17') + getNumValue('rmbs18') + getNumValue('rmbs19') + getNumValue('rmbs45')+ getNumValue('rmbs46'),2);
}
addListener($('rmbs17'),"propertychange",cwfyxj_s);
addListener($('rmbs18'),"propertychange",cwfyxj_s);
addListener($('rmbs19'),"propertychange",cwfyxj_s);
addListener($('rmbs45'),"propertychange",cwfyxj_s);
addListener($('rmbs46'),"propertychange",cwfyxj_s);
/***商品销售数量**/
function spxsslrmbs_s (){
$('rmbs27').value=$('us27').value;
}
addListener($('us27'),"propertychange",spxsslrmbs_s);
/**
function spxsslUs_s (){
$('us27').value=$('rmbs27').value;
}
addListener($('rmbs27'),"propertychange",spxsslUs_s);
***/
/***商品销售单价***/
function spxsdjrmbs_s (){
$('rmbs28').value = Utils.roundoff(getNumValue('rmbs29')/getNumValue('rmbs27'),2);
}
addListener($('rmbs29'),"propertychange",spxsdjrmbs_s);
addListener($('rmbs27'),"propertychange",spxsdjrmbs_s);
function spxsdjUs_s (){
$('us28').value = Utils.roundoff(getNumValue('us29')/getNumValue('rmbs27'),2);
}
addListener($('us29'),"propertychange",spxsdjrmbs_s);
addListener($('rmbs27'),"propertychange",spxsdjrmbs_s);
/***商品销售金额**/
function spxsje_s (){
$('rmbs29').value = Utils.roundoff(getNumValue('us29')*getNumValue('exchangeRate'),2);
}
addListener($('us29'),"propertychange",spxsje);
addListener($('exchangeRate'),"propertychange",spxsje);
/***印花税***/
function yhs_s (){
$('rmbs23').value = Utils.roundoff(0.0003*(getNumValue('rmbs3') +getNumValue('rmbs29')),2);
}
addListener($('rmbs3'),"propertychange",yhs_s);
addListener($('rmbs29'),"propertychange",yhs_s);
/***出口退税额***/
function cktse_s (){
$('rmbs24').value = Utils.roundoff($('rmbs41').value*$('other3').value/100 ,2)
}
addListener($('rmbs41'),"propertychange",cktse_s);
addListener($('other3'),"propertychange",cktse_s);
/***税收小计***/
function ssxj_s (){
$('rmbs49').value = Utils.roundoff(getNumValue('rmbs21')+getNumValue('rmbs22') + getNumValue('rmbs23')-getNumValue('rmbs24')+getNumValue('rmbs20')+getNumValue('rmbs48'),2);
}
addListener($('rmbs21'),"propertychange",ssxj_s);
addListener($('rmbs22'),"propertychange",ssxj_s);
addListener($('rmbs23'),"propertychange",ssxj_s);
addListener($('rmbs24'),"propertychange",ssxj_s);
addListener($('rmbs20'),"propertychange",ssxj_s);
addListener($('rmbs48'),"propertychange",ssxj_s);

/**预计利润总额或亏损总额**/
function yjlrzehksze_s (){
$('rmbs31').value = Utils.roundoff(getNumValue('rmbs29')-getNumValue('rmbs6')-getNumValue('rmbs44') - getNumValue('rmbs16') - getNumValue('rmbs47') - getNumValue('rmbs49'),2);
}
addListener($('rmbs29'),"propertychange",yjlrzehksze_s);
addListener($('rmbs6'),"propertychange",yjlrzehksze_s);
addListener($('rmbs44'),"propertychange",yjlrzehksze_s);
addListener($('rmbs16'),"propertychange",yjlrzehksze_s);
addListener($('rmbs47'),"propertychange",yjlrzehksze_s);
addListener($('rmbs49'),"propertychange",yjlrzehksze_s);

jhcbxj_s ();
zjltfhj_s ();
jyfyxj_s ();
tszylx_s ();
cwfyxj_s ();
spxsslrmbs_s ();
//spxsslUs_s ();
spxsdjrmbs_s ();
spxsdjUs_s ();
spxsje_s ();
yhs_s ();
cktse_s ();
ssxj_s ();
yjlrzehksze_s ();

</script>
  </body>
</html>
