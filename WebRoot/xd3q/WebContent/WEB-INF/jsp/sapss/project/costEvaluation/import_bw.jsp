<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  </head>
  <!-----进口利润测算表------->
  <body>
    <table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" >
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
  <tr>
    <td rowspan="2" valign="middle"><div align="center">类别</div></td>
    <td rowspan="2" width="30" valign="middle"><div align="center">序号</div></td>
    <td colspan="4" rowspan="2" valign="middle"><div align="center">核算项目</div></td>
    <td colspan="4" align="center">立项成本核算(汇率<input type="text" size=8 name="exchangeRate" id="exchangeRate" value="${main.exchangeRate}"/>)</td>
    <td colspan="4" align="center">实际执行情况</td>
    <td colspan="2" align="center">差异</td>
  </tr>
  <tr>
    <td colspan="2"><div align="center">RMB￥</div>
    <td colspan="2"><div align="center" id="bibei"></div></td>
    <td colspan="2"><div align="center">RMB￥</div>
    <td colspan="2"><div align="center" id="bibei_bw"></div></td>
    <td colspan="2"><div align="center">RMB￥</div>
    <td colspan="2"><div align="center" id="bibei_xy"></div></td>
  </tr>
  <tr>
    <td rowspan="12"><nobr>采购直接成本</nobr></td>
    <td rowspan="7">1</td>
    <td colspan="2" rowspan="7"><nobr>商品购进成本</nobr></td>
    <td colspan="2"><div  align="center">数量</div></td>
    <td colspan="2" align="center">
		<input name="rmb1" value="${rmb1}" type="text" class="text" size="25"/>    </td>
    <td colspan="2"><div align="center"><input name="us1" value="${us1}" type="text" class="text" size="25"/></div></td>
    <td colspan="2" align="center">
		<input name="rmbs1" value="${rmbs1}" type="text" class="text" size="25"/>    </td>
    <td colspan="2"><div align="center"><input name="uss1" value="${uss1}" type="text" class="text" size="25"/></div></td>
    <td colspan="2" align="center"><input name="rmbs1_xy" value="0" type="text" class="text" size="25"/>    </td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">单价</div></td>
    <td colspan="2" align="center">
		<input name="rmb2" value="${rmb2}" type="text" class="text" size="25"/>    </td>
    <td colspan="2"><div align="center"><input name="us2" value="${us2}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs2" value="${rmbs2}" type="text" class="text" size="25"/>    </td>
    <td colspan="2"><div align="center"><input name="uss2" value="${uss2}" type="text" class="text" size="25" /></div></td>
     <td colspan="2" align="center">
		<input name="rmbs2_xy" value="0" type="text" class="text" size="25"/>    </td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">货款金额<font color="red">▲</font></div></td>
    <td colspan="2" align="center">
		<input name="rmb3" value="${rmb3}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us3" value="${us3}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs3" value="${rmbs3}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss3" value="${uss3}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs3_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">海关关税<font color="red">▲</font></div></td>
    <td colspan="2" align="center">
		<input name="rmb40" value="${rmb40}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us40" value="${us40}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs40" value="${rmbs40}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss40" value="${uss40}" type="text" class="text" size="25" /></div></td>
      <td colspan="2" align="center">
		<input name="rmbs40_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">海关增值税<font color="red">▲</font></div></td>
    <td colspan="2" align="center">
		<input name="rmb41" value="${rmb41}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us41" value="${us41}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs41" value="${rmbs41_i}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss41" value="${uss41_i}" type="text" class="text" size="25" /></div></td>
     <td colspan="2" align="center">
		<input name="rmbs41_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">海关消费税<font color="red">▲</font></div></td>
    <td colspan="2" align="center">
		<input name="rmb42" value="${rmb42}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us42" value="${us42}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs42" value="${rmbs42_i}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss42" value="${uss42_i}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs42_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">小计金额</div></td>
    <td colspan="2" align="center">
		<input name="rmb43" value="${rmb43}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us43" value="${us43}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs43" value="${rmbs43}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss43" value="${uss43}" type="text" class="text" size="25" /></div></td>
      <td colspan="2" align="center">
		<input name="rmbs43_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td>2</td>
    <td colspan="4">进口运费</td>
    <td colspan="2" align="center">
		<input name="rmb4" value="${rmb4}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us4" value="${us4}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs4" value="${rmbs4_i}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss4" value="${uss4_i}" type="text" class="text" size="25" /></div></td>
      <td colspan="2" align="center">
		<input name="rmbs4_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td>3</td>
    <td colspan="4">进口保险费</td>
    <td colspan="2" align="center">
		<input name="rmb5" value="${rmb5}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us5" value="${us5}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs5" value="${rmbs5_i}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss5" value="${uss5_i}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs5_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td>4</td>
    <td colspan="4">其它</td>
    <td colspan="2" align="center">
		<input name="rmb44" value="${rmb44}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us44" value="${us44}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs44" value="${rmbs44}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss44" value="${uss44}" type="text" class="text" size="25" /></div></td>
     <td colspan="2" align="center">
		<input name="rmbs44_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td>5</td>
    <td colspan="4">其它</td>
    <td colspan="2" align="center">
		<input name="rmb45" value="${rmb45}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us45" value="${us45}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs45" value="${rmbs45}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss45" value="${uss45}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs45_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td>6</td>
    <td colspan="4">小计</td>
    <td colspan="2" align="center">
		<input name="rmb6" value="${rmb6}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="us6" value="${us6}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs6" value="${rmbs6}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="uss6" value="${uss6}" type="text" class="text" size="25" /></div></td>
     <td colspan="2" align="center">
		<input name="rmbs6_xy" value="" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
  </tr>
  <tr>
    <td rowspan="9">经营费用</td>
	<td>7</td>
    <td colspan="4">(1)运杂费、装卸费</td>
    <td colspan="2" align="center">
		<input name="rmb8" value="${rmb8}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us8" value="${us8}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs8" value="${rmbs8}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss8" value="${uss8}" type="text" class="text" size="25" /></div></td>
     <td colspan="2" align="center">
		<input name="rmbs8_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td>8</td>
    <td colspan="4">(2)码头费</td>
    <td colspan="2" align="center">
		<input name="rmb9" value="${rmb9}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us9" value="${us9}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs9" value="${rmbs9}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss9" value="${uss9}" type="text" class="text" size="25" /></div></td>
      <td colspan="2" align="center">
		<input name="rmbs9_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td>9</td>
    <td colspan="4">(3)仓储费</td>
    <td colspan="2" align="center">
		<input name="rmb10" value="${rmb10}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us10" value="${us10}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs10" value="${rmbs10}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss10" value="${uss10}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs10_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td>10</td>
    <td colspan="4">(4)报关、报检费等</td>
    <td colspan="2" align="center">
		<input name="rmb11" value="${rmb11}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us11" value="${us11}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs11" value="${rmbs11}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss11" value="${uss11}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs11_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td>11</td>
    <td colspan="4">直接流通费合计:</td>
    <td colspan="2" align="center"><input name="rmb7" value="${rmb7}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/></td>
    <td colspan="2"><div align="center">
      <input name="us7" value="${us7}" type="text" class="text" size="25" style="backgroundColor:red"/>
    <td colspan="2" align="center"><input name="rmbs7" value="${rmbs7}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/></td>
    <td colspan="2"><div align="center">
      <input name="uss7" value="${uss7}" type="text" class="text" size="25" style="backgroundColor:red"/>
    </div></td>
    <td colspan="2" align="center"><input name="rmbs7_xy" value="" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/></td>
  </tr>
  <tr>
    <td>12</td>
    <td colspan="4">间接管理(差旅费、执行费、邮电费)</td>
    <td colspan="2" align="center">
		<input name="rmb12" value="${rmb12}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us12" value="${us12}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs12" value="${rmbs12}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss12" value="${uss12}" type="text" class="text" size="25" /></div></td>
     <td colspan="2" align="center">
		<input name="rmbs12_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td>13</td>
    <td colspan="4">佣金或手续费</td>
    <td colspan="2" align="center">
		<input name="rmb13" value="${rmb13}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us13" value="${us13}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs13" value="${rmbs13}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss13" value="${uss13}" type="text" class="text" size="25" /></div></td>
     <td colspan="2" align="center">
		<input name="rmbs13_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td>15</td>
    <td colspan="4">其它(样品费、商品损耗、包装费等)</td>
    <td colspan="2" align="center"><input name="rmb15" value="${rmb15}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="us15" value="${us15}" type="text" class="text" size="25" />
    <td colspan="2" align="center"><input name="rmbs15" value="${rmbs15}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="uss15" value="${uss15}" type="text" class="text" size="25" />
    </div></td>
    <td colspan="2" align="center"><input name="rmbs15_xy" value="" type="text" class="text" size="25" /></td>
  </tr>
  <tr>
    <td>16</td>
    <td colspan="4" bordercolor="#66CCFF">小计</td>
    <td colspan="2" align="center"><input name="rmb46" value="${rmb46}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/></td>
    <td colspan="2"><div align="center">
      <input name="us46" value="${us46}" type="text" class="text" size="25" />
    </div></td>
    <td colspan="2" align="center"><input name="rmbs46" value="${rmbs46}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/></td>
    <td colspan="2"><div align="center">
      <input name="uss46" value="${uss46}" type="text" class="text" size="25" />
    </div></td>
    <td colspan="2" align="center"><input name="rmbs46_xy" value="" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/></td>
  </tr>
  <tr>
    <td>管理费用</td>
    <td>17</td>
    <td colspan="4">分摊公司管理费</td>
    <td colspan="2" align="center">
		<input name="rmb16" value="${rmb16}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us16" value="${us16}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs16" value="${rmbs16}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss16" value="${uss16}" type="text" class="text" size="25" /></div></td>
     <td colspan="2" align="center">
		<input name="rmbs16_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td rowspan="6">财务费用</td>
    <td>18</td>
    <td colspan="4">货款利息（年利率7.216%）</td>
    <td colspan="2" align="center">
		<input name="rmb17" value="${rmb17}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us17" value="${us17}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs17" value="${rmbs17}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss17" value="${uss17}" type="text" class="text" size="25" /></div></td>
     <td colspan="2" align="center">
		<input name="rmbs17_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td>19</td>
    <td colspan="4">押汇利息</td>
    <td colspan="2" align="center">
		<input name="rmb47" value="${rmb47}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us47" value="${us47}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs47" value="${rmbs47_i}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss47" value="${uss47_i}" type="text" class="text" size="25" /></div></td>
     <td colspan="2" align="center">
		<input name="rmbs47_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td>20</td>
    <td colspan="4">贴现利息</td>
    <td colspan="2" align="center">
		<input name="rmb48" value="${rmb48}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us48" value="${us48}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs48" value="${rmbs48}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss48" value="${uss48}" type="text" class="text" size="25" /></div></td>
     <td colspan="2" align="center">
		<input name="rmbs48_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
   <tr>
    <td>21</td>
    <td colspan="4">银行结算费（开征、承兑、付款等）</td>
    <td colspan="2" align="center">
		<input name="rmb19" value="${rmb19}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us19" value="${us19}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs19" value="${rmbs19}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss19" value="${uss19}" type="text" class="text" size="25" /></div></td>
     <td colspan="2" align="center">
		<input name="rmbs19_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
   <tr>
    <td>22</td>
    <td colspan="4">其它</td>
    <td colspan="2" align="center">
		<input name="rmb49" value="${rmb49}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us49" value="${us49}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs49" value="${rmbs49}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss49" value="${uss49}" type="text" class="text" size="25" /></div></td>
     <td colspan="2" align="center">
		<input name="rmbs49_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
   <tr>
    <td>23</td>
    <td colspan="4">小计</td>
    <td colspan="2" align="center">
		<input name="rmb50" value="${rmb50}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="us50" value="${us50}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs50" value="${rmbs50}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="uss50" value="${uss50}" type="text" class="text" size="25" /></div></td>
     <td colspan="2" align="center">
		<input name="rmbs50_xy" value="" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/>    </td>
  </tr>
   <tr>
    <td rowspan="7">销售收入</td>
    <td rowspan="7">24</td>
    <td colspan="4">税前合同预计利润率（百分比或单位利润）<font color="red">▲</font></td>
    <td colspan="2" align="center">
		<input name="rmb25" value="${rmb25}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us25" value="${us25}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs25" value="${rmbs25}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss25" value="${uss25}" type="text" class="text" size="25" /></div></td>
     <td colspan="2" align="center">
		<input name="rmbs25_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td colspan="2" rowspan="6" >商品销售价</td>
    <td colspan="2"><div  align="center">数量</div></td>
    <td colspan="2" align="center">
		<input name="rmb27" value="${rmb27}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us27" value="${us27}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs27" value="${rmbs27}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss27" value="${uss27}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs27_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">单价</div></td>
    <td colspan="2" align="center">
		<input name="rmb28" value="${rmb28}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us28" value="${us28}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs28" value="${rmbs28}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss28" value="${uss28}" type="text" class="text" size="25" /></div></td>
     <td colspan="2" align="center">
		<input name="rmbs28_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">税率<font color="red">▲</font></div></td>
    <td colspan="2" align="center">
		<input name="rmb52" value="${rmb52}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us52" value="${us52}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs52" value="${rmbs52}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss52" value="${uss52}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs52_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">货款总额（含税）<font color="red">▲</font></div></td>
    <td colspan="2" align="center">
		<input name="rmb29" value="${rmb29}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us29" value="${us29}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs29" value="${rmbs29}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss29" value="${uss29}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs29_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">不含税货款</div></td>
    <td colspan="2" align="center">
		<input name="rmb53" value="${rmb53}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us53" value="${us53}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs53" value="${rmbs53}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss53" value="${uss53}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs53_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td colspan="2"><div  align="center">销项税金</div></td>
    <td colspan="2" align="center">
		<input name="rmb54" value="${rmb54}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="us54" value="${us54}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs54" value="${rmbs47}" type="text" class="text" size="25" />    </td>
    <td colspan="2"><div align="center"><input name="uss54" value="${uss47}" type="text" class="text" size="25" /></div></td>
     <td colspan="2" align="center">
		<input name="rmbs54_xy" value="" type="text" class="text" size="25" />    </td>
  </tr>
  <tr>
    <td>税前利润</td>
    <td>25</td>
    <td colspan="4">税前利润总额</td>
    <td colspan="2" align="center">
		<input name="rmb_sqln" value="" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="us_sqln" value="" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs_sqln" value="" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="uss_sqln" value="" type="text" class="text" size="25" /></div></td>
     <td colspan="2" align="center">
		<input name="rmbs_sqln_xy" value="" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>    </td>
  </tr>
  <tr>
    <td rowspan="5">税收</td>
    <td>26</td>
    <td colspan="4">增值税及附加费</td>
    <td colspan="2" align="center"><input name="rmb21" value="${rmb21}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="us21" value="${us21}" type="text" class="text" size="25" />
    </div></td>
    <td colspan="2" align="center"><input name="rmbs21" value="${rmbs21}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="uss21" value="${uss21}" type="text" class="text" size="25" />
    </div></td>
    <td colspan="2" align="center"><input name="rmbs21_xy" value="" type="text" class="text" size="25" /></td>
  </tr>
  <tr>
    <td>27</td>
    <td colspan="4">营业税及附加费（5.55%）</td>
    <td colspan="2" align="center"><input name="rmb22" value="${rmb22}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="us22" value="${us22}" type="text" class="text" size="25" />
    </div></td>
    <td colspan="2" align="center"><input name="rmbs22" value="${rmbs22}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="uss22" value="${uss22}" type="text" class="text" size="25" />
    </div></td>
    <td colspan="2" align="center"><input name="rmbs22_xy" value="" type="text" class="text" size="25" /></td>
  </tr>
  <tr>
    <td>28</td>
    <td colspan="4">印花税</td>
    <td colspan="2" align="center"><input name="rmb23" value="${rmb23}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="us23" value="${us23}" type="text" class="text" size="25" />
    </div></td>
    <td colspan="2" align="center"><input name="rmbs23" value="${rmbs23}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="uss23" value="${uss23}" type="text" class="text" size="25" />
    </div></td>
     <td colspan="2" align="center"><input name="rmbs23_xy" value="" type="text" class="text" size="25" /></td>
  </tr>
  <tr>
    <td>29</td>
    <td colspan="4">其它</td>
    <td colspan="2" align="center"><input name="rmb55" value="${rmb55}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="us55" value="${us55}" type="text" class="text" size="25" />
    </div></td>
    <td colspan="2" align="center"><input name="rmbs55" value="${rmbs55}" type="text" class="text" size="25" /></td>
    <td colspan="2"><div align="center">
      <input name="uss55" value="${uss55}" type="text" class="text" size="25" />
    </div></td>
    <td colspan="2" align="center"><input name="rmbs55_xy" value="" type="text" class="text" size="25" /></td>
  </tr>
  <tr>
    <td>30</td>
    <td colspan="4">小计</td>
    <td colspan="2" align="center"><input name="rmb56" value="${rmb56}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/></td>
    <td colspan="2"><div align="center">
      <input name="us56" value="${us56}" type="text" class="text" size="25" />
    </div></td>
    <td colspan="2" align="center"><input name="rmbs56" value="${rmbs56}" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/></td>
    <td colspan="2"><div align="center">
      <input name="uss56" value="${uss56}" type="text" class="text" size="25" />
    </div></td>
    <td colspan="2" align="center"><input name="rmbs56_xy" value="" type="text" class="text" size="25" readonly style="background-color:#D0D0D0"/></td>
  </tr>
  <tr>
    <td>利润</td>
    <td>31</td>
    <td colspan="4">预计利润总额或亏损总额</td>
    <td colspan="2" align="center">
		<input name="rmb31" value="${rmb31}" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="us31" value="${us31}" type="text" class="text" size="25" /></div></td>
    <td colspan="2" align="center">
		<input name="rmbs31" value="${rmbs31}" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>    </td>
    <td colspan="2"><div align="center"><input name="uss31" value="${uss31}" type="text" class="text" size="25" /></div></td>
     <td colspan="2" align="center">
		<input name="rmbs31_xy" value="" type="text" class="text" size="25"  readonly style="background-color:#D0D0D0"/>    </td>
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
/***货款金额*****/
function hkje(){
    $('rmb3').value = $('us3').value*$('exchangeRate').value;
}
addListener($('us3'),"propertychange",hkje);
addListener($('exchangeRate'),"propertychange",hkje);
/***购进成本单价**/
function gjcbdj(){
    if($('rmb1').value==''||$('rmb1').value==0) return;
	$('rmb2').value = Utils.roundoff($('rmb3').value/$('rmb1').value,2);
}
addListener($('rmb3'),"propertychange",gjcbdj); 
addListener($('rmb1'),"propertychange",gjcbdj);
/***购进成本小计金额**/
function gjcbxjje(){
    $('rmb43').value = Utils.roundoff(getNumValue('rmb3')+getNumValue('rmb40')+getNumValue('rmb42'),2) ; 
}
addListener($('rmb3'),"propertychange",gjcbxjje); 
addListener($('rmb40'),"propertychange",gjcbxjje);
addListener($('rmb41'),"propertychange",gjcbxjje);
addListener($('rmb42'),"propertychange",gjcbxjje);
/***采购直接成本小计***/
function cgzjcbxj(){
    $('rmb6').value = Utils.roundoff(getNumValue('rmb4')+getNumValue('rmb5')+getNumValue('rmb43')+getNumValue('rmb44')+getNumValue('rmb45'),2) ; 
}
addListener($('rmb4'),"propertychange",cgzjcbxj);
addListener($('rmb5'),"propertychange",cgzjcbxj);
addListener($('rmb43'),"propertychange",cgzjcbxj);
addListener($('rmb44'),"propertychange",cgzjcbxj);
addListener($('rmb45'),"propertychange",cgzjcbxj);
/***直接流程费合计***/
function zjltfhj(){
    $('rmb7').value = Utils.roundoff(getNumValue('rmb8')+getNumValue('rmb9')+getNumValue('rmb10')+getNumValue('rmb11'),2) ; 
}
addListener($('rmb8'),"propertychange",zjltfhj);
addListener($('rmb9'),"propertychange",zjltfhj);
addListener($('rmb10'),"propertychange",zjltfhj);
addListener($('rmb11'),"propertychange",zjltfhj);
/***经营费用小计***/
function jyfyxj(){
    $('rmb46').value = Utils.roundoff(getNumValue('rmb7')+getNumValue('rmb12')+getNumValue('rmb13')+getNumValue('rmb15'),2) ; 
}
addListener($('rmb7'),"propertychange",jyfyxj);
addListener($('rmb12'),"propertychange",jyfyxj);
addListener($('rmb13'),"propertychange",jyfyxj);
addListener($('rmb15'),"propertychange",jyfyxj);
/***财务费用小计***/
function cwfyxj(){
    $('rmb50').value = Utils.roundoff(getNumValue('rmb17')+getNumValue('rmb19')+getNumValue('rmb47')+getNumValue('rmb48')+getNumValue('rmb49'),2) ; 
}
addListener($('rmb17'),"propertychange",cwfyxj);
addListener($('rmb19'),"propertychange",cwfyxj);
addListener($('rmb47'),"propertychange",cwfyxj);
addListener($('rmb48'),"propertychange",cwfyxj);
addListener($('rmb49'),"propertychange",cwfyxj);
/***商品销售单价***/
function spxsdj(){
   $('rmb28').value = Utils.roundoff($('rmb29').value/$('rmb27').value,2);
}
addListener($('rmb29'),"propertychange",spxsdj);
addListener($('rmb27'),"propertychange",spxsdj);
/***商品销售不含税货款***/
function spxsbhshk(){
  // $('rmb53').value = Utils.roundoff($('rmb29').value/(1+getNumValue('rmb52')),2);
}
addListener($('rmb29'),"propertychange",spxsbhshk);
addListener($('rmb52'),"propertychange",spxsbhshk);
/***商品销销项税金***/
function spxsxxsj(){
 //  $('rmb54').value = Utils.roundoff($('rmb29').value-($('rmb29').value/(1+getNumValue('rmb52'))),2);
}
addListener($('rmb29'),"propertychange",spxsxxsj);
addListener($('rmb52'),"propertychange",spxsxxsj);
/***增值税及附加费***/
function zzsjfjf(){
   var v = getNumValue('rmb54')-getNumValue('rmb41');
   if(v>0) $('rmb21').value = Utils.roundoff(1.11*v,2);
   else $('rmb21').value=0;
}
addListener($('rmb54'),"propertychange",zzsjfjf);
addListener($('rmb41'),"propertychange",zzsjfjf);
/***印花税***/
function yhs(){
   $('rmb23').value = Utils.roundoff(0.0003*(getNumValue('rmb3')+getNumValue('rmb29')),2);
}
addListener($('rmb3'),"propertychange",yhs);
addListener($('rmb29'),"propertychange",yhs);
/***税收小计***/
function ssxj(){
   $('rmb56').value = Utils.roundoff(getNumValue('rmb21')+getNumValue('rmb22')+getNumValue('rmb23')+getNumValue('rmb55'),2) ; 
}
addListener($('rmb21'),"propertychange",ssxj);
addListener($('rmb22'),"propertychange",ssxj);
addListener($('rmb23'),"propertychange",ssxj);
addListener($('rmb55'),"propertychange",ssxj);
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
/***购进成本小计金额**/
function gjcbxjje_s (){
    $('rmbs43').value = Utils.roundoff(getNumValue('rmbs3')+getNumValue('rmbs40')+getNumValue('rmbs42'),2) ; 
   
}
addListener($('rmbs3'),"propertychange",gjcbxjje_s); 
addListener($('rmbs40'),"propertychange",gjcbxjje_s);
addListener($('rmbs41'),"propertychange",gjcbxjje_s);
addListener($('rmbs42'),"propertychange",gjcbxjje_s);
/***采购直接成本小计***/
function cgzjcbxj_s (){
    $('rmbs6').value = Utils.roundoff(getNumValue('rmbs4')+getNumValue('rmbs5')+getNumValue('rmbs43')+getNumValue('rmbs44'),2) ; 
}
addListener($('rmbs4'),"propertychange",cgzjcbxj_s);
addListener($('rmbs5'),"propertychange",cgzjcbxj_s);
addListener($('rmbs43'),"propertychange",cgzjcbxj_s);
addListener($('rmbs44'),"propertychange",cgzjcbxj_s);
addListener($('rmbs45'),"propertychange",cgzjcbxj_s);
/***直接流程费合计***/
function zjltfhj_s (){
    $('rmbs7').value = Utils.roundoff(getNumValue('rmbs8')+getNumValue('rmbs9')+getNumValue('rmbs10')+getNumValue('rmbs11'),2) ; 
}
addListener($('rmbs8'),"propertychange",zjltfhj_s);
addListener($('rmbs9'),"propertychange",zjltfhj_s);
addListener($('rmbs10'),"propertychange",zjltfhj_s);
addListener($('rmbs11'),"propertychange",zjltfhj_s);
/***经营费用小计***/
function jyfyxj_s (){
    $('rmbs46').value = Utils.roundoff(getNumValue('rmbs7')+getNumValue('rmbs12')+getNumValue('rmbs13')+getNumValue('rmbs15'),2) ; 
}
addListener($('rmbs7'),"propertychange",jyfyxj_s);
addListener($('rmbs12'),"propertychange",jyfyxj_s);
addListener($('rmbs13'),"propertychange",jyfyxj_s);
addListener($('rmbs15'),"propertychange",jyfyxj_s);
/***财务费用小计***/
function cwfyxj_s (){
    $('rmbs50').value = Utils.roundoff(getNumValue('rmbs17')+getNumValue('rmbs19')+getNumValue('rmbs47')+getNumValue('rmbs48')+getNumValue('rmbs49'),2) ; 
}
addListener($('rmbs17'),"propertychange",cwfyxj_s);
addListener($('rmbs19'),"propertychange",cwfyxj_s);
addListener($('rmbs47'),"propertychange",cwfyxj_s);
addListener($('rmbs48'),"propertychange",cwfyxj_s);
addListener($('rmbs49'),"propertychange",cwfyxj_s);
/***商品销售单价***/
function spxsdj_s (){
   $('rmbs28').value = Utils.roundoff($('rmbs29').value/$('rmbs27').value,2);
}
addListener($('rmbs29'),"propertychange",spxsdj_s);
addListener($('rmbs27'),"propertychange",spxsdj_s);
/***商品销售不含税货款***/
function spxsbhshk_s (){
 //  $('rmbs53').value = Utils.roundoff($('rmbs29').value/(1+getNumValue('rmbs52')),2);
 $('rmbs53').value = Utils.roundoff($('rmbs29').value- $('rmbs54').value,2);
}
addListener($('rmbs29'),"propertychange",spxsbhshk_s);
addListener($('rmbs52'),"propertychange",spxsbhshk_s);
/***商品销销项税金***/
function spxsxxsj_s (){
  // $('rmbs54').value = Utils.roundoff($('rmbs29').value-($('rmbs29').value/(1+getNumValue('rmbs52'))),2); 
}
addListener($('rmbs29'),"propertychange",spxsxxsj_s);
addListener($('rmbs52'),"propertychange",spxsxxsj_s);
/***增值税及附加费***/
function zzsjfjf_s (){
   var v = getNumValue('rmbs54')-getNumValue('rmbs41');
   if(v>0) $('rmbs21').value = Utils.roundoff(1.11*v,2);
   else $('rmbs21').value=0;
}
addListener($('rmbs54'),"propertychange",zzsjfjf_s);
addListener($('rmbs41'),"propertychange",zzsjfjf_s);
/***印花税***/
function yhs_s (){
   $('rmbs23').value = Utils.roundoff(0.0003*(getNumValue('rmbs3')+getNumValue('rmbs29')),2);
}
addListener($('rmbs3'),"propertychange",yhs_s);
addListener($('rmbs29'),"propertychange",yhs_s);
/***税收小计***/
function ssxj_s (){
   $('rmbs56').value = Utils.roundoff(getNumValue('rmbs21')+getNumValue('rmbs22')+getNumValue('rmbs23')+getNumValue('rmbs55'),2) ; 
}
addListener($('rmbs21'),"propertychange",ssxj_s);
addListener($('rmbs22'),"propertychange",ssxj_s);
addListener($('rmbs23'),"propertychange",ssxj_s);
addListener($('rmbs55'),"propertychange",ssxj_s);
/***预计利润总额或亏损总额***/
function yjlrzehksze_s (){
   $('rmbs31').value = Utils.roundoff(getNumValue('rmbs29')-getNumValue('rmbs6')-getNumValue('rmbs46')-getNumValue('rmbs16')-getNumValue('rmbs50')-getNumValue('rmbs56'),2) ; 
}
addListener($('rmbs29'),"propertychange",yjlrzehksze_s);
addListener($('rmbs6'),"propertychange",yjlrzehksze_s);
addListener($('rmbs46'),"propertychange",yjlrzehksze_s);
addListener($('rmbs16'),"propertychange",yjlrzehksze_s);
addListener($('rmbs50'),"propertychange",yjlrzehksze_s);
addListener($('rmbs56'),"propertychange",yjlrzehksze_s);
//税前利润总额
function sqlnze(){
	$('rmb_sqln').value = Utils.roundoff(getNumValue('rmb53')-getNumValue('rmb6')-getNumValue('rmb16')-getNumValue('rmb50'),2) ; 
	$('rmbs_sqln').value = Utils.roundoff(getNumValue('rmbs53')-getNumValue('rmbs6')-getNumValue('rmbs16')-getNumValue('rmbs50'),2) ; 
	$('rmb25').value = Utils.roundoff($('rmb_sqln').value/$('rmb53').value * 100,2);
	$('rmbs25').value = Utils.roundoff($('rmbs_sqln').value/$('rmbs53').value * 100,2);
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
function init(){
//海关关税不用显示
	$('rmbs40').value =  '0';
	$('rmbs45').value =  '0';
	$('rmbs3').value =  Utils.roundoff($('rmbs3').value-$('rmbs41').value ,2);
	$('rmb6').value = Utils.roundoff(getNumValue('rmb4')+getNumValue('rmb5')+getNumValue('rmb43')+getNumValue('rmb44')+getNumValue('rmb45'),2) ; 
	$('rmb43').value = Utils.roundoff(getNumValue('rmb3')+getNumValue('rmb40')+getNumValue('rmb42'),2) ; 
}
 init();
gjcbxjje_s ();
cgzjcbxj_s ();
zjltfhj_s ();
jyfyxj_s ();
cwfyxj_s ();
spxsdj_s ();
spxsbhshk_s ();
spxsxxsj_s ();
zzsjfjf_s ();
yhs_s ();
ssxj_s ();
yjlrzehksze_s ();
sqlnze();
 xy();

</script>
  </body>
</html>
