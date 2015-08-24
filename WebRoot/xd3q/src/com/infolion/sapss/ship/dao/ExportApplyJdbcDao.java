/*
 * @(#)ExportApplyJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：Mar 11, 2009
 *  描　述：创建
 */

package com.infolion.sapss.ship.dao;

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
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.payment.PaymentContants;
import com.infolion.sapss.ship.domain.TShipMaterial;

@Repository
public class ExportApplyJdbcDao extends BaseDao
{

	/**
	 * 修改出货通知单的物料信息
	 * 
	 * @param exportMateId
	 * @param colName
	 * @param colValue
	 */
	public void updateExportApplyMateriel(String exportMateId, String colName,
			String colValue)
	{
		String sql = " update t_export_apply_material " + "set " + colName
				+ "='" + colValue + "' where export_mate_id = ?";

		getJdbcTemplate().update(sql, new Object[] { exportMateId });
		//更新总额
		if (colName.equals("QUANTITY") || colName.equals("PRICE")
				|| colName.equals("PEINH"))
		{
			sql = " update t_export_apply_material "
					+ "		  set total = nvl(quantity, 0) * nvl(price, 0) / nvl(peinh, 1) where export_mate_id = ? ";
			getJdbcTemplate().update(sql, new Object[] { exportMateId });
		}
	}

	public void deleteMaterielByApply(String exportApplyId){
		String sql = " update t_export_apply_material " + "set is_available"
			+ "='0' where export_apply_id = ?";
		getJdbcTemplate().update(sql, new Object[] { exportApplyId });
	}
	/**
	 * 获取通知单号
	 * 
	 * @return
	 */
	public String getNoticeNo()
	{
		final List<String> returnFalg = new ArrayList();
		String sql = "select lpad(nvl(max(a.notice_no) + 1,1),10,'0') notice_no  from t_export_apply a";

		getJdbcTemplate().query(sql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				returnFalg.add(rs.getString("notice_no"));
			}
		});

		if (returnFalg.size() == 0)
			return "";
		else
			return returnFalg.get(0);
	}

	/**
	 * 获得销售物料的剩余数量
	 * 
	 * @param salesRowsId
	 * @return
	 */
	public BigDecimal getSalesHasQuantity(String salesRowsId)
	{
		String sql = "select has_quantity from v_sel_sales_apply_mt where sales_rows_id=? ";
		List list = this.getJdbcTemplate().queryForList(sql,
				new Object[] { salesRowsId });
		if (list != null && list.size() > 0)
		{
			return (BigDecimal) ((Map) list.get(0)).get("has_quantity");
		}
		else
			return BigDecimal.valueOf(0);
	}

	/**
	 * 获得代理物料的剩余数量
	 * 
	 * @param agentMaterialId
	 * @return
	 */
	public BigDecimal getAgentHasQuantity(String agentMaterialId)
	{
		String sql = "select has_quantity from v_sel_agent_apply_mt where sales_rows_id=? ";
		List list = this.getJdbcTemplate().queryForList(sql,
				new Object[] { agentMaterialId });
		if (list != null && list.size() > 0)
		{
			return (BigDecimal) ((Map) list.get(0)).get("has_quantity");
		}
		else
			return BigDecimal.valueOf(0);
	}
	
	/**
	 * 获得出口货物通知单的物料总金额
	 * @param exportApplyId
	 * @return
	 */
	public BigDecimal getExportApplyTotal(String exportApplyId){
		String sql = "select nvl(sum(nvl(total,0)),0) total from t_export_apply_material where export_apply_id = ?";
		List list = this.getJdbcTemplate().queryForList(sql,
				new Object[] { exportApplyId });
		if (list != null && list.size() > 0)
		{
			return (BigDecimal) ((Map) list.get(0)).get("total");
		}
		else
			return BigDecimal.valueOf(0);		
	}
	
	/**查询库存数量
	 * @param materialCode
	 * @param wareHouse
	 * @param batchNo
	 */
	public double queryInventoryNum(String materialCode,String warehouse,String batchNo)
	{
		String sql = "select SUM(INVENTORY_NUM) as INVENTORY_NUM from T_MATERIAL_INVENTORY" +
				" where MATERIAL_CODE=?";
		
		if (StringUtils.isNotBlank(warehouse))
			sql+=" And WAREHOUSE='"+warehouse+"'";
		
		if (StringUtils.isNotBlank(batchNo))
			sql+=" And BATCH_NO='"+batchNo+"'";
		
		List list = this.getJdbcTemplate().queryForList(sql,
				new Object[] { materialCode });
		if (list != null && list.size() > 0)
		{
			BigDecimal inventoryNum = (BigDecimal)((Map) list.get(0)).get("INVENTORY_NUM");
			if (inventoryNum!=null)
				return inventoryNum.doubleValue();
			else
				return 0;		
		}
		else
			return 0;
	}
	
	public void dealOutToExcel(String sql ,final ExcelObject excel){
		//final IntObject i= new IntObject();
		
		getJdbcTemplate().query(sql,new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[19];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("dept_name");
				values[2] = rs.getString("project_no");
				values[3] = rs.getString("project_name");
				values[4] = rs.getString("material_name");
				values[5] = rs.getString("material_unit");
				values[6] = rs.getString("quantity");
				values[7] = rs.getString("price");
				values[8] = rs.getString("total");
				values[9] = rs.getString("currency");
				values[10] = rs.getString("write_no");
				values[11] = rs.getString("get_sheet_time");
				values[12] = rs.getString("Creator_Time");
				values[13] = rs.getString("export_date");
				values[14] = rs.getString("exchange_type");
				values[15] = rs.getString("trade_type");
				values[16] = rs.getString("export_port");
				values[17] = rs.getString("destinations");
				values[18] = rs.getString("cmd");
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}
	
	public void dealOutToExcelV1(String sql ,final ExcelObject excel){
		//final IntObject i= new IntObject();
		
		getJdbcTemplate().query(sql,new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[9];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("project_no");
				values[2] = rs.getString("project_name");
				values[3] = rs.getString("contract_name");
				values[4] = rs.getString("SALES_NO");
				values[5] = rs.getString("sap_order_no");
				values[6] = rs.getString("notice_no");
				values[7] = rs.getString("write_no");
				values[8] = rs.getString("total");
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}
	
	public boolean checkIsExistExportBillExam(String exportApplyId){
		String sql = "select count(*) from t_export_bill_exam_d d left outer join   " +
				"t_export_bill_exam a on d.export_bill_exam_id=a.export_bill_exam_id where  a.is_available=1 and d.export_apply_id='"+exportApplyId+"'";
		int i = getJdbcTemplate().queryForInt(sql);
		if(i>0) return true;
		else return false;
	}

}
