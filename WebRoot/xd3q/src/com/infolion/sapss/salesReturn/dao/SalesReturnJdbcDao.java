/*
 * @(#)ExportApplyJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林哲文
 *  时　间：2009-06-11
 *  描　述：创建
 */

package com.infolion.sapss.salesReturn.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.sapss.salesReturn.domain.TSalesReturn;

@Repository
public class SalesReturnJdbcDao extends BaseDao {

	/**
	 * 获得销售退货编号
	 * 
	 * @return
	 */
	public String getReturnNo() {
		final List<String> returnFalg = new ArrayList();
		String sql = "select lpad(nvl(max(a.return_no) + 1,1),10,'0') return_no  from t_sales_return a";

		getJdbcTemplate().query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				returnFalg.add(rs.getString("return_no"));
			}
		});

		if (returnFalg.size() == 0)
			return "";
		else
			return returnFalg.get(0);
	}

	/**
	 * 更新物料单信息
	 * 
	 * @param tSalesReturn
	 * @param returnMaterialId
	 * @param colName
	 * @param colValue
	 */
	public void updateMaterial(TSalesReturn tSalesReturn,
			String returnMaterialId, String colName, String colValue) {
		//更新相应的输入字段
		String sql = " update t_sales_return_material " + "set " + colName+ "='" + colValue + "' where return_material_id = ?";
		getJdbcTemplate().update(sql, new Object[] { returnMaterialId });
//		if ("0".equals(tSalesReturn.getReturnType())) {
//			// 更新总额
//			if (colName.equals("QUANTITY") || colName.equals("PRICE")|| colName.equals("RATE")) {
//				sql = " update t_sales_return_material "
//						+ "		  set price = nvl(total_money, 0) * nvl(QUANTITY, 1)  where return_material_id = ? ";
//				getJdbcTemplate().update(sql, new Object[] { returnMaterialId });
//			}
//		}
//		if ("1".equals(tSalesReturn.getReturnType())) {
			// 更新行总计
//			if (colName.equals("QUANTITY")||colName.equals("PRICE")) {
//				sql = "update t_sales_return_material t set t.total_money = nvl(t.quantity,0) * nvl(t.price,0)"
//						+ " where t.return_material_id = ?";
//				getJdbcTemplate().update(sql, new Object[] { returnMaterialId });
//			}
			// 更新单价,税额
		if ("0".equals(tSalesReturn.getReturnType())) {
			if (colName.equals("QUANTITY")) {
				sql = "update t_sales_return_material t set TOTAL_MONEY = "+colValue+"*price/price_unit";
				sql = sql + " where t.return_material_id = ?";
				getJdbcTemplate().update(sql, new Object[] { returnMaterialId });
			}
		}
		if ("1".equals(tSalesReturn.getReturnType())) {
			if (colName.equals("QUANTITY")|| colName.equals("TOTAL_MONEY") || colName.equals("RATE")) {
				//单价
				String up_price=" t.price = t.total_money / nvl(t.quantity,1)/nvl(t.price_unit,1),";
				//税额
				String up_tax=" t.taxes = t.total_money *  nvl(t.rate,0)/100 /(1+ nvl(t.rate,0)/100),";
				//净价
				String up_net_money="t.money = t.total_money - t.total_money * nvl(t.rate,0)/100 /(1+nvl(t.rate,0)/100)";
				//
				sql = "update t_sales_return_material t set ";
				sql = sql + up_price+up_tax+up_net_money;
				sql = sql + " where t.return_material_id = ?";
				getJdbcTemplate().update(sql, new Object[] { returnMaterialId });
			}
		}
//		}
	}

	public TSalesReturn updateSalesReturnMoney(TSalesReturn tSalesReturn) {
		String sql = "select nvl(sum(money),0) money,nvl(sum(taxes),0) taxes,nvl(sum(total_money),0) total_money from t_sales_return_material where return_id=? ";
		List list = this.getJdbcTemplate().queryForList(sql,
				new Object[] { tSalesReturn.getReturnId() });
		BigDecimal netMoney = BigDecimal.valueOf(0);
		BigDecimal taxMoney = BigDecimal.valueOf(0);
		BigDecimal totalMoney = BigDecimal.valueOf(0);
		if (list != null && list.size() > 0) {
			netMoney = (BigDecimal) ((Map) list.get(0)).get("money");
			taxMoney = (BigDecimal) ((Map) list.get(0)).get("taxes");
			totalMoney = (BigDecimal) ((Map) list.get(0)).get("total_money");
		}
		tSalesReturn.setNetMoney(netMoney.toString());
		tSalesReturn.setTaxMoney(taxMoney.toString());
		tSalesReturn.setTotalMoney(totalMoney.toString());
		return tSalesReturn;
	}

	public List<BigDecimal> getSalesReturnMtMoney(String returnId) {
		String sql = "select nvl(sum(money),0) money,nvl(sum(taxes),0) taxes,nvl(sum(total_money),0) total_money from t_sales_return_material where return_id=? ";
		List list = this.getJdbcTemplate().queryForList(sql,
				new Object[] { returnId });
		return list;
	}

}
