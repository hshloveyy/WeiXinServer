/*
 * @(#)BillApplyJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：王懿璞
 *  时　间：2009-6-12
 *  描　述：创建
 */

package com.infolion.sapss.bill.dao;

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
import com.infolion.sapss.bill.domain.TBillApply;
import com.infolion.sapss.bill.domain.TBillApplyMaterial;
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
public class BillApplyJdbcDao extends BaseDao
{
	/**
	 * 根据ID删除申请单信息
	 * @param BillApplyId
	 * @return
	 */
	public int delete(String BillApplyId){
		String sql="update T_BILL_APPLY set is_available='0' where BILL_APPLY_ID=?";
		System.out.println(sql);
		return this.getJdbcTemplate().update(sql, new Object[]{BillApplyId});
	}
	
	/**
	 * 取得最新的BillApplyNo单据流水号
	 * @param BillApplyNo
	 * @return
	 */
	public String getBillApplyNo()
	{
		final List<String> returnFalg = new ArrayList();
		String sql = "select lpad(nvl(count(a.BILL_APPLY_NO) + 1,1),10,'0') BILL_APPLY_NO from T_BILL_APPLY a";

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
	 * @param BillApplyDetailId
	 * @param colName
	 * @param colValue
	 */
	public void updateBillApplyMateriel(String exportMateId, String colName,
			String colValue)
	{
		String strSql = " update T_BILL_APPLY_MATERIAL " + "set " + colName
				+ "='" + colValue + "' where EXPORT_MATE_ID = ?";

		getJdbcTemplate().update(strSql, new Object[] { exportMateId });
	}
	
	/**
	 * 更改指定字段
	 * @param BillApplyDetailId
	 * @param colName
	 * @param colValue
	 */
	public void updateBillApplys(String BillApplyDetailId, String colName,
			String colValue)
	{
		String strSql = " update T_BILL_APPLY " + "set " + colName
		+ "='" + colValue + "' where BILL_APPLY_ID = ?";
		
		getJdbcTemplate().update(strSql, new Object[] { BillApplyDetailId });
	}	

	/**
	 * 提交流程审批后更新状态
	 * @param billApplyId
	 */
	public int submitUpdateState(String billApplyId){
		String sql = "update T_BILL_APPLY set EXAMINE_STATE=? where BILL_APPLY_ID=?";
		System.out.println(sql);
		return this.getJdbcTemplate().update(sql, new Object[]{"3",billApplyId});
	}
	
	public List<BigDecimal> getBillApplyMtMoney(String billApplyId) {
		String sql = "select nvl(sum(QUANTITY),0) quantityTotal,nvl(sum(TAX),0) taxTotal,nvl(sum(LOAN_MONEY),0) loanTotal,nvl(sum(TOTAL_MONEY),0) priceTotal from T_BILL_APPLY_MATERIAL where bill_apply_id=? ";
		List list = this.getJdbcTemplate().queryForList(sql,
				new Object[] { billApplyId });
		return list;
	}
	/**
	 * 
	 * @param billApplyId
	 * @param sapBillNo
	 * @param taxNo
	 */
	public void saveSapInfo(String billApplyId, String sapBillNo, String taxNo) {
		String sql="update T_BILL_APPLY set SAP_BILL_NO=?,TAX_NO=? where BILL_APPLY_ID=?";
		this.getJdbcTemplate().update(sql,new Object[]{sapBillNo,taxNo,billApplyId});
	}
	/**
	 * 
	 * @param billAppleId
	 * @return
	 */
	public List<TBillApplyMaterial> getBillApplyMaterial(String billAppleId){
		String sql = "select * from t_bill_apply_material t where t.bill_apply_id=?" ;
		final List<TBillApplyMaterial> list = new ArrayList<TBillApplyMaterial>();
		getJdbcTemplate().query(sql,new Object[]{billAppleId}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				TBillApplyMaterial ma = new TBillApplyMaterial();
                ma.setMaterialCode(rs.getString("Material_Code"));
                ma.setMaterialName(rs.getString("Material_Name"));
                ma.setMaterialUnit(rs.getString("Material_unit"));
                ma.setQuantity(rs.getDouble("Quantity"));
                ma.setLoanMoney(rs.getDouble("Loan_Money"));
                ma.setTax(rs.getDouble("tax"));
                ma.setTaxRate(rs.getString("tax_Rate"));
                ma.setTotalMoney(rs.getDouble("Total_Money"));
                ma.setRate(rs.getDouble("rate"));
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
	public List<TBillApplyMaterial> magerBillApplyMaterial(String shipId){
		String sql = "";
		sql+=" select sum(a.quantity) sum_quantity,a.material_code, a.material_name, a.batch_no,c.contract_no,c.contract_sales_id,";
		sql+=" b.row_taxes_rale, a.material_unit, b.konv_kbetr/nvl(b.vbap_kpein,1) price, c.VBAK_WAERK currency";
		sql+=" from (select * from t_ship_material where is_available='1' and ship_id in ("+shipId+") ) a";
		sql+=" left join t_contract_sales_materiel b on a.sales_rows_id = b.sales_rows_id ";
		sql+=" left join t_contract_sales_info c on b.contract_sales_id = c.contract_sales_id ";
		sql+=" group by a.material_code,   a.material_name,   a.batch_no,    b.row_taxes_rale,";   
		sql+=" a.material_unit,   b.konv_kbetr/nvl(b.vbap_kpein,1), c.VBAK_WAERK ,c.contract_no,c.contract_sales_id";

		
		
		final List<TBillApplyMaterial> list = new ArrayList<TBillApplyMaterial>();
		getJdbcTemplate().query(sql, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				TBillApplyMaterial ma = new TBillApplyMaterial();
                ma.setMaterialCode(rs.getString("material_code"));
                ma.setMaterialName(rs.getString("material_name"));
                ma.setMaterialUnit(rs.getString("material_unit"));
                ma.setQuantity(rs.getDouble("sum_quantity"));
                ma.setPrice(rs.getDouble("price"));
                ma.setContractNo(rs.getString("contract_no"));
                ma.setContractSalesId(rs.getString("contract_sales_id"));
                String rate = rs.getString("row_taxes_rale");
                ma.setTaxRate((rate==null||rate.equals(""))?"0":rate);
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
	
	
	public void dealOutToExcel1(String sql ,final ExcelObject excel){
		//final IntObject i= new IntObject();

		getJdbcTemplate().query(sql,new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[17];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("DEPT_NAME");
				values[2] = rs.getString("REAL_NAME");
				values[3] = rs.getString("CONTRACT_SALES_NO");
				values[4] = rs.getString("PAPER_CONTRACT_NO");
				values[5] = rs.getString("SAP_ORDER_NO");
				values[6] = rs.getString("EXPORT_APPLY_NO");
				values[7] = rs.getString("BILL_TO_PARTY_NAME");
				values[8] = rs.getString("BILL_APPLY_NO");
				values[9] = rs.getString("TAX_NO");
				values[10] = rs.getString("BILL_TIME");
				values[11] = rs.getString("QUANTITY_TOTAL");
				values[12] = rs.getString("TAX_TOTAL");
				values[13] = rs.getString("LOAN_TOTAL");
				values[14] = rs.getString("PRICE_TOTAL");
				values[15] = rs.getString("APPLY_TIME");
				values[16] = rs.getString("APPROVED_TIME");
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}
	
	public int checkAgentQuantity(String contractSalesId){
		String sql1 = "select sum(s.vbap_zmeng) from t_contract_sales_materiel s where s.contract_sales_id='"+contractSalesId+"'";
		String sql2 = "select sum(m.quantity) from t_bill_apply_material m left outer join t_bill_apply b on b.bill_apply_id=m.bill_apply_id "
			          +"where b.is_available=1 and b.examine_state in ('1','2','3') and m.contract_sales_id='"+contractSalesId+"'";
		Double d1 = (Double)getJdbcTemplate().queryForObject(sql1, Double.class);
		Double d2 = (Double)getJdbcTemplate().queryForObject(sql2, Double.class);
		d2=(null==d2?0:d2);
		if(d1-d2>=0) return 1;
		return -1;
	}
	
	public void markPrint(String id){
		String sql="update T_BILL_APPLY set Is_Print=1 where BILL_APPLY_ID=?";
		this.getJdbcTemplate().update(sql,new Object[]{id});
		//待办标记
		String sql11="update t_sys_wf_process_instance set business_note=business_note||'|已打印' where business_record_id=?";
		String sql12="update v_sys_wf_process_user set business_note=business_note||'|已打印' where business_record_id=?";
		this.getJdbcTemplate().update(sql11,new Object[]{id});
		try{
			this.getJdbcTemplate().update(sql12,new Object[]{id});
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
