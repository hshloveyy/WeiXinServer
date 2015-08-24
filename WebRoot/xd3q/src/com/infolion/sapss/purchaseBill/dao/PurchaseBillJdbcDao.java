/*
 * @(#)PurchaseBillJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：王懿璞
 *  时　间：2009-6-12
 *  描　述：创建
 */

package com.infolion.sapss.purchaseBill.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.console.workflow.dto.TaskHisDto;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.sapss.purchaseBill.domain.TPurchaseBill;
import com.infolion.sapss.purchaseBill.domain.TPurchaseBillMaterial;
import com.infolion.sapss.common.ExcelObject;

/**
 * 
 * <pre></pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 王懿璞
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Repository
public class PurchaseBillJdbcDao extends BaseDao
{
	/**
	 * 根据ID删除申请单信息
	 * @param PurchaseBillId
	 * @return
	 */
	public int delete(String PurchaseBillId){
		String sql="update T_PURCHASE_BILL set is_available='0' where PURCHASE_BILL_ID=?";
		System.out.println(sql);
		return this.getJdbcTemplate().update(sql, new Object[]{PurchaseBillId});
	}
	
	/**
	 * 取得最新的PurchaseBillNo单据流水号
	 * @param PurchaseBillNo
	 * @return
	 */
	public String getPurchaseBillNo()
	{
		final List<String> returnFalg = new ArrayList();
		String sql = "select lpad(nvl(count(a.PURCHASE_BILL_NO) + 1,1),10,'0') BILL_APPLY_NO from T_PURCHASE_BILL a";

		getJdbcTemplate().query(sql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				returnFalg.add(rs.getString("BILL_APPLY_NO"));
			}
		});

		if (returnFalg.size() == 0)
			return "";
		else
			return returnFalg.get(0);
	}
	
	/**
	 * 修改开票申请单物料表
	 * @param PurchaseBillDetailId
	 * @param colName
	 * @param colValue
	 */
	public void updatePurchaseBillMateriel(String purchaseBillMateId, String colName,
			String colValue)
	{
		String strSql = " update T_PURCHASE_BILL_MATERIAL " + "set " + colName
				+ "='" + colValue + "' where PURCHASE_BILL_MATE_ID = ?";

		getJdbcTemplate().update(strSql, new Object[] { purchaseBillMateId });
	}
	
	/**
	 * 更改指定字段
	 * @param PurchaseBillDetailId
	 * @param colName
	 * @param colValue
	 */
	public void updatePurchaseBills(String PurchaseBillDetailId, String colName,
			String colValue)
	{
		String strSql = " update T_PURCHASE_BILL " + "set " + colName
		+ "='" + colValue + "' where BILL_APPLY_ID = ?";
		
		getJdbcTemplate().update(strSql, new Object[] { PurchaseBillDetailId });
	}	

	/**
	 * 提交流程审批后更新状态
	 * @param purchaseBillId
	 */
	public int submitUpdateState(String purchaseBillId){
		String sql = "update T_PURCHASE_BILL set EXAMINE_STATE=? where BILL_APPLY_ID=?";
		System.out.println(sql);
		return this.getJdbcTemplate().update(sql, new Object[]{"3",purchaseBillId});
	}
	
	public List<BigDecimal> getPurchaseBillMtMoney(String purchaseBillId) {
		String sql = "select nvl(sum(QUANTITY),0) quantityTotal,nvl(sum(TOTAL_MONEY),0) priceTotal from T_PURCHASE_BILL_MATERIAL where purchase_bill_id=? ";
		List list = this.getJdbcTemplate().queryForList(sql,
				new Object[] { purchaseBillId });
		return list;
	}
	/**
	 * 
	 * @param purchaseBillId
	 * @param sapBillNo
	 * @param taxNo
	 */
	public void saveSapInfo(String purchaseBillId,String taxNo) {
		String sql="update T_PURCHASE_BILL set TAX_NO=? where PURCHASE_BILL_ID=?";
		this.getJdbcTemplate().update(sql,new Object[]{taxNo,purchaseBillId});
	}
	/**
	 * 
	 * @param billAppleId
	 * @return
	 */
	public List<TPurchaseBillMaterial> getPurchaseBillMaterial(String billAppleId){
		String sql = "select * from T_PURCHASE_BILL_material t where t.purchase_bill_id=?" ;
		final List<TPurchaseBillMaterial> list = new ArrayList<TPurchaseBillMaterial>();
		getJdbcTemplate().query(sql,new Object[]{billAppleId}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				TPurchaseBillMaterial ma = new TPurchaseBillMaterial();
                ma.setMaterialCode(rs.getString("Material_Code"));
                ma.setMaterialName(rs.getString("Material_Name"));
                ma.setMaterialUnit(rs.getString("Material_unit"));
                ma.setQuantity(rs.getDouble("Quantity"));
                ma.setTotalMoney(rs.getDouble("Total_Money"));
                ma.setPrice(rs.getDouble("price"));
				list.add(ma);
			}});
		return list;
	}
	/**
	 * 
	 * @param billAppleId
	 * @return
	 */
	public List<TPurchaseBillMaterial> magerPurchaseBillMaterial(String shipId){
		String sql = "";
		sql+=" select sum(a.quantity) sum_quantity,a.material_code, a.material_name, a.batch_no,c.contract_no,c.contract_sales_id,";
		sql+=" b.row_taxes_rale, a.material_unit, b.konv_kbetr/nvl(b.vbap_kpein,1) price, c.VBAK_WAERK currency";
		sql+=" from (select * from t_ship_material where is_available='1' and ship_id in ("+shipId+") ) a";
		sql+=" left join t_contract_sales_materiel b on a.sales_rows_id = b.sales_rows_id ";
		sql+=" left join t_contract_sales_info c on b.contract_sales_id = c.contract_sales_id ";
		sql+=" group by a.material_code,   a.material_name,   a.batch_no,    b.row_taxes_rale,";   
		sql+=" a.material_unit,   b.konv_kbetr/nvl(b.vbap_kpein,1), c.VBAK_WAERK ,c.contract_no,c.contract_sales_id";

		
		
		final List<TPurchaseBillMaterial> list = new ArrayList<TPurchaseBillMaterial>();
		getJdbcTemplate().query(sql, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				TPurchaseBillMaterial ma = new TPurchaseBillMaterial();
                ma.setMaterialCode(rs.getString("material_code"));
                ma.setMaterialName(rs.getString("material_name"));
                ma.setMaterialUnit(rs.getString("material_unit"));
                ma.setQuantity(rs.getDouble("sum_quantity"));
                ma.setPrice(rs.getDouble("price"));
                ma.setContractNo(rs.getString("contract_no"));
                ma.setCurrency(rs.getString("currency"));
				list.add(ma);
			}});
		return list;
	}
	
	public void dealOutToExcel(String sql ,final ExcelObject excel){
		//final IntObject i= new IntObject();
		
		getJdbcTemplate().query(sql,new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[14];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("DEPT_NAME");
				values[2] = rs.getString("PROJECT_NO");
				values[3] = rs.getString("PROJECT_NAME");
				values[4] = rs.getString("MATERIAL_NAME");
				values[5] = rs.getString("MATERIAL_UNIT");
				values[6] = rs.getString("QUANTITY");
				values[7] = rs.getString("PRICE");
				values[8] = rs.getString("LOAN_MONEY");
				values[9] = rs.getString("TAX");
				values[10] = rs.getString("TOTAL_MONEY");
				values[11] = rs.getString("APPLY_TIME");
				values[12] = rs.getString("APPROVED_TIME");
				values[13] = rs.getString("CMD");
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}
	
}
