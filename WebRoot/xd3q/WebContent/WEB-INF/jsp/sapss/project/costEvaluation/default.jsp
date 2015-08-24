<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  </head>
  
  <body>
    <table width="755" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" >
  <tr>
    <td width="120"><font color="red">*</font>品名</td>
    <td width="150" colspan="3">
    	<input name="className" type="text" class="text" size="18" align="left" value="${main.className}"/>
    	<input type="button"  onclick="showFindMaterial()"value="..."/>
    </td>
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
	<td  >	
		<div id="div_ymatGroup"></div>
		<fisc:dictionary width="100" hiddenName = "ymatGroup" dictionaryName="YDMATGROUP" value="${main.ymatGroup}" divId="div_ymatGroup" isNeedAuth="false"   ></fisc:dictionary>
	</td>
	<td align="right" colspan="5">	</td>
 </tr>
  <tr>
    <td><font color="red">*</font>金额</td>
    <td width="0">
    </td>
    <td>
    	<input name="sum" type="text" class="text" size="10" value="${main.sum}"/>万
    </td>
    <td>	
      <div align="center">
   	 	<select name="currency" id="currency">
			<option value="">请选择</option>
			<c:forEach items="${currency}" var="row">
				<option value="${row.code}">${row.code}</option>
			</c:forEach>
		</select>
	 </div>
    </td>
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
  	<textarea name="mask" id="mask" style="width:100%;overflow-y:visible;word-break:break-all;" >${main.mask}</textarea>
  </td>
  </tr>
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
    <td rowspan="6">进货成本</br>与价格</td>
    <td>&nbsp;</td>
    <td colspan="2">&nbsp;</td>
    <td colspan="2"><div  align="center">数量</div></td>
    <td colspan="2" align="center">
		<input name="rmb1" value="${rmb1}" type="text" class="text" size="25"/>
    </td>
    <td colspan="2"><div align="center"><input name="us1" value="${us1}" type="text" class="text" size="25"/></div></td>
  </tr>
  <tr>
    <td>1</td>
    <td colspan="2">商品购进成本（FOB）</td>
    <td colspan="2"><div  align="center">单价</div></td>
    <td colspan="2" align="center">
		<input name="rmb2" value="${rmb2}" type="text" class="text" size="25"/>
    </td>
    <td colspan="2"><div align="center"><input name="us2" value="${us2}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="2">&nbsp;</td>
    <td colspan="2"><div  align="center">金额</div></td>
    <td colspan="2" align="center">
		<input name="rmb3" value="${rmb3}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us3" value="${us3}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>2</td>
    <td colspan="4">国外运费(F)</td>
    <td colspan="2" align="center">
		<input name="rmb4" value="${rmb4}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us4" value="${us4}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>3</td>
    <td colspan="4">国外保险费(I)</td>
    <td colspan="2" align="center">
		<input name="rmb5" value="${rmb5}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us5" value="${us5}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>4</td>
    <td colspan="4">小计（C&amp;F.CIF.进货总额）</td>
    <td colspan="2" align="center">
		<input name="rmb6" value="${rmb6}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us6" value="${us6}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td rowspan="9">经营费用</td>
    <td>5</td>
    <td colspan="4">直接流通费合计:</td>
    <td colspan="2" align="center">
		<input name="rmb7" value="${rmb7}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us7" value="${us7}" type="text" class="text" size="25" style="backgroundColor:red"/></div></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="4">(1)短途运杂、装卸旨</td>
    <td colspan="2" align="center">
		<input name="rmb8" value="${rmb8}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us8" value="${us8}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="4">(2)码头费</td>
    <td colspan="2" align="center">
		<input name="rmb9" value="${rmb9}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us9" value="${us9}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="4">(3)仓储费</td>
    <td colspan="2" align="center">
		<input name="rmb10" value="${rmb10}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us10" value="${us10}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="4">(4)报关、报检费等</td>
    <td colspan="2" align="center">
		<input name="rmb11" value="${rmb11}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us11" value="${us11}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>6</td>
    <td colspan="4">间接管理(差旅费、执行费、邮电费)</td>
    <td colspan="2" align="center">
		<input name="rmb12" value="${rmb12}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us12" value="${us12}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>7</td>
    <td colspan="4">佣金或手续费</td>
    <td colspan="2" align="center">
		<input name="rmb13" value="${rmb13}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us13" value="${us13}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>8</td>
    <td colspan="4">效益工资提成</td>
    <td colspan="2" align="center">
		<input name="rmb14" value="${rmb14}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us14" value="${us14}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>9</td>
    <td colspan="4" bordercolor="#66CCFF">其它(样品费、商品损耗、包装费等)</td>
    <td colspan="2" align="center">
		<input name="rmb15" value="${rmb15}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us15" value="${us15}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>管理费用</td>
    <td>10</td>
    <td colspan="4">分摊公司管理费</td>
    <td colspan="2" align="center">
		<input name="rmb16" value="${rmb16}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us16" value="${us16}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td rowspan="3">财务费用</td>
    <td>11</td>
    <td colspan="4">货款利息</td>
    <td colspan="2" align="center">
		<input name="rmb17" value="${rmb17}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us17" value="${us17}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>12</td>
    <td colspan="4">退税款占用利息</td>
    <td colspan="2" align="center">
		<input name="rmb18" value="${rmb18}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us18" value="${us18}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>13</td>
    <td colspan="4">银行结算费</td>
    <td colspan="2" align="center">
		<input name="rmb19" value="${rmb19}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us19" value="${us19}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td rowspan="4">各种税费</td>
    <td>14</td>
    <td colspan="4">海关关税
    <input name="other1" value="${other1}" type="text" size="4" />
    %,税则号:
    <input name="other2" value="${other2}" type="text" size="10" /></td>
    <td colspan="2" align="center">
		<input name="rmb20" value="${rmb20}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us20" value="${us20}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>15</td>
    <td colspan="4">增值税及附加费（利润×18.87%、14.43%）</td>
    <td colspan="2" align="center">
		<input name="rmb21" value="${rmb21}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us21" value="${us21}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>16</td>
    <td colspan="4">营业税及附加费（5.55%）</td>
    <td colspan="2" align="center">
		<input name="rmb22" value="${rmb22}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us22" value="${us22}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>17</td>
    <td colspan="4">印花税</td>
    <td colspan="2" align="center">
		<input name="rmb23" value="${rmb23}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us23" value="${us23}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>退税</td>
    <td>18</td>
    <td colspan="4">退税率
    <input name="other3" value="${other3}" type="text" size="4" />
    %</td>
    <td colspan="2" align="center">
		<input name="rmb24" value="${rmb24}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us24" value="${us24}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>手续费</td>
    <td>19</td>
    <td colspan="4">预计利润率（百分比或单位利润）</td>
    <td colspan="2" align="center">
		<input name="rmb25" value="${rmb25}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us25" value="${us25}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td rowspan="4">成本与销</br>售收入</td>
    <td>20</td>
    <td colspan="4">单位成本合计</td>
    <td colspan="2" align="center">
		<input name="rmb26" value="${rmb26}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us26" value="${us26}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="2" >&nbsp;</td>
    <td colspan="2"><div  align="center">数量</div></td>
    <td colspan="2" align="center">
		<input name="rmb27" value="${rmb27}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us27" value="${us27}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>21</td>
    <td colspan="2">商品销售价</td>
    <td colspan="2"><div  align="center">单价</div></td>
    <td colspan="2" align="center">
		<input name="rmb28" value="${rmb28}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us28" value="${us28}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="2">&nbsp;</td>
    <td colspan="2"><div  align="center">金额</div></td>
    <td colspan="2" align="center">
		<input name="rmb29" value="${rmb29}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us29" value="${us29}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td rowspan="2">换汇成本</br>及盈亏</td>
    <td>22</td>
    <td colspan="4">换汇成本</td>
    <td colspan="2" align="center">
		<input name="rmb30" value="${rmb30}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us30" value="${us30}" type="text" class="text" size="25" /></div></td>
  </tr>
  <tr>
    <td>23</td>
    <td colspan="4">预计利润总额或亏损总额</td>
    <td colspan="2" align="center">
		<input name="rmb31" value="${rmb31}" type="text" class="text" size="25" />
    </td>
    <td colspan="2"><div align="center"><input name="us31" value="${us31}" type="text" class="text" size="25" /></div></td>
  </tr>
  
</table>
  </body>
</html>
