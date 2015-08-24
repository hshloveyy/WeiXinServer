/*
 * @(#)ReceiptsJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：王懿璞
 *  时　间：2009-3-10
 *  描　述：创建
 */

package com.infolion.sapss.pledge.dao;

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
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.payment.PaymentContants;
import com.infolion.sapss.pledge.domain.PledgeReceiptsItem;
import com.infolion.sapss.receipts.domain.TReceiptMaterial;
import com.sun.mail.handlers.message_rfc822;

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
public class PledgeReceiptsJdbcDao extends BaseDao
{
	/**
	 * 根据ID删除进仓单信息
	 * @param receiptId
	 * @return
	 */
	public int delete(String pledgeReceiptsInfoId){
		String sql="update T_PLEDGERECEIPTS_INFO set is_available='0' where PLEDGERECEIPTS_INFO_ID=?";
		return this.getJdbcTemplate().update(sql, new Object[]{pledgeReceiptsInfoId});
	}
	
	/**
	 * 取得最新的ReceiptNo单据流水号
	 * @param receiptNo
	 * @return
	 */
	public synchronized String getReceiptNo(String code)
	{
		final List<String> returnFalg = new ArrayList();
		String sql = "select lpad(nvl(substr(max(a.pledgereceipts_info_no),3,8) + 1,1),8,'0') pledgereceipts_info_no from t_pledgereceipts_info a where a.pledgereceipts_info_no like '"+code+"%'";

		getJdbcTemplate().query(sql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				returnFalg.add(rs.getString("pledgereceipts_info_no"));
			}
		});

		if (returnFalg.size() == 0)
			return "";
		else
			return code+returnFalg.get(0);
	}
	
	/**
	 * 修改进仓单物料表
	 * @param receiptDetailId
	 * @param colName
	 * @param colValue
	 */
	public void updateReceiptMateriel(String pledgeReceiptsItemId, String colName,
			String colValue)
	{
		String strSql = " update T_PLEDGERECEIPTS_ITEM " + "set " + colName
				+ "='" + colValue + "' where PLEDGERECEIPTS_ITEM_ID = ?";

		getJdbcTemplate().update(strSql, new Object[] { pledgeReceiptsItemId });
	}
	
	
	/**
	 * 提交流程审批后更新状态
	 * @param receiptId
	 */
	public int submitUpdateState(String receiptId){
		String sql = "update T_PLEDGERECEIPTS_INFO set EXAMINE_STATE=? where PLEDGERECEIPTS_INFO_ID=?";
		return this.getJdbcTemplate().update(sql, new Object[]{"3",receiptId});
	}
	
	public String queryDeptCode(String receiptId)
	{
		final List<String> returnFalg = new ArrayList();
		String sql = "select dept_code from t_sys_dept where dept_id=(select dept_id from t_pledgereceipts_info where pledgereceipts_info_id='"+receiptId+"')";
		getJdbcTemplate().query(sql, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				
				returnFalg.add(rs.getString("dept_code"));
			}
		});
		if (returnFalg.size() == 0)
			return "";
		else
			return returnFalg.get(0);
	}
	

	
	public List<PledgeReceiptsItem> queryReceiptMaterial(String receiptId){

		final List<PledgeReceiptsItem> list = new ArrayList<PledgeReceiptsItem>();
		String strSql = "select k.material_code,k.material_name,k.material_unit,k.quantity,k.price,k.batch_no,k.currency,k.total from T_PLEDGERECEIPTS_ITEM k where " +
                        "k.PLEDGERECEIPTS_ID=? ";
		
		getJdbcTemplate().query(strSql,new Object[]{receiptId}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				PledgeReceiptsItem material = new PledgeReceiptsItem();
				material.setMaterialCode(rs.getString("material_code"));
			    material.setMaterialName(rs.getString("material_name"));
			    material.setMaterialUnit(rs.getString("material_unit"));
			    material.setQuantity(rs.getDouble("quantity"));
			    material.setPrice(Double.parseDouble(PaymentContants.round(rs.getString("price"),2)));
			    material.setBatchNo(rs.getString("batch_no"));
			    material.setCurrency(rs.getString("currency"));
			    material.setTotal(Double.parseDouble(PaymentContants.round(rs.getString("total"),2)));
				list.add(material);
			}});

		return list;
	}

	public void update(String receiptsInfoNo, String pledgeReceiptsInfoId) {
		String sql = " update T_PLEDGERECEIPTS_INFO set PLEDGERECEIPTS_INFO_NO=? where PLEDGERECEIPTS_INFO_ID = ?";
		getJdbcTemplate().update(sql, new Object[] { receiptsInfoNo, pledgeReceiptsInfoId});
		
	}

	public void dealStorageToExcel(String sql ,final ExcelObject excel){
		getJdbcTemplate().query(sql,new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[17];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("APPROVED_TIME");
				values[2] = rs.getString("COMPANY_CODE");
				values[3] = rs.getString("WAREHOUSENAME");
				values[4] = rs.getString("DEPT_NAME");
				values[5] = rs.getString("PROJECT_NO");
				values[6] = rs.getString("MATERIAL_CODE");
				values[7] = rs.getString("MATERIAL_NAME");
				values[8] = rs.getString("BATCH_NO");
				values[9] = rs.getString("KULIN");
				values[10] = rs.getString("MATERIAL_UNIT");
				values[11] = rs.getString("KUCU");
				values[12] = rs.getString("UNITPRICE");
				values[13] = rs.getString("EKPO_PEINH");
				values[14] = rs.getString("CURRENCY");
				values[15] = rs.getString("TOTAL");
				values[16] = rs.getString("CNYTOTAL");
				//values[17] = rs.getString("YUQI");
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}
	
}
