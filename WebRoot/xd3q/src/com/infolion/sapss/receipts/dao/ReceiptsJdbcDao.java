/*
 * @(#)ReceiptsJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：王懿璞
 *  时　间：2009-3-10
 *  描　述：创建
 */

package com.infolion.sapss.receipts.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.payment.PaymentContants;
import com.infolion.sapss.receipts.domain.TReceiptInfo;
import com.infolion.sapss.receipts.domain.TReceiptMaterial;

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
public class ReceiptsJdbcDao extends BaseDao
{
	/**
	 * 根据ID删除进仓单信息
	 * @param receiptId
	 * @return
	 */
	public int delete(String receiptId){
		String sql="update T_RECEIPT_INFO set is_available='0' where receipt_id=?";
		System.out.println(sql);
		return this.getJdbcTemplate().update(sql, new Object[]{receiptId});
	}
	
	/**
	 * 取得最新的ReceiptNo单据流水号
	 * @param receiptNo
	 * @return
	 */
	public synchronized String getReceiptNo(String code)
	{
		final List<String> returnFalg = new ArrayList();
		String sql = "select lpad(nvl(substr(max(a.receipt_no),3,8) + 1,1),8,'0') receipt_no from t_receipt_info a where a.receipt_no like '"+code+"%'";

		getJdbcTemplate().query(sql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				returnFalg.add(rs.getString("receipt_no"));
			}
		});

		if (returnFalg.size() == 0)
			return "";
		else
			return code+returnFalg.get(0);
	}
	
	public synchronized String getSerialNo()
	{
		final List<String> returnFalg = new ArrayList();
		String sql = "select lpad(nvl(substr(max(a.SerialNo),3,8) + 1,1),8,'0') SerialNo from t_receipt_info a ";

		getJdbcTemplate().query(sql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				returnFalg.add(rs.getString("SerialNo"));
			}
		});

		if (returnFalg.size() == 0)
			return "";
		else
			return returnFalg.get(0);
	}
	
	/**
	 * 修改进仓单物料表
	 * @param receiptDetailId
	 * @param colName
	 * @param colValue
	 */
	public void updateReceiptMateriel(String receiptDetailId, String colName,String colValue)
	{
		String strSql = " update t_receipt_material " + "set " + colName
				+ "='" + colValue + "' where receipt_detail_id = ?";

		getJdbcTemplate().update(strSql, new Object[] { receiptDetailId });
	}
	
	public void updateSeReceiptQua(String receiptId,String type){
		String strSql ;
		if("1".equals(type))
		   strSql = " update t_receipt_material  set SEQUANTITY = QUANTITY  where receipt_id = ?";
		else if("2".equals(type)){
			strSql = " update t_receipt_material  set QUANTITY = SEQUANTITY,total = round(nvl(sequantity*price/ekpo_peinh,0),2)  where receipt_id = ?";
		}
		else strSql = " update t_receipt_material  set SEQUANTITY = ''  where receipt_id = ?";
		getJdbcTemplate().update(strSql, new Object[] { receiptId });
	}
	/**
	 * 更改指定字段
	 * @param receiptDetailId
	 * @param colName
	 * @param colValue
	 */
	public int updateReceipts(String receiptDetailId, String colName,
			String colValue)
	{
		String strSql = " update t_receipt_info " + "set " + colName
		+ "='" + colValue + "' where receipt_id = ?";
		
		return getJdbcTemplate().update(strSql, new Object[] { receiptDetailId });
	}
	
	/**
	 * 提交流程审批后更新状态
	 * @param receiptId
	 */
	public int submitUpdateState(String receiptId){
		String sql = "update T_RECEIPT_INFO set EXAMINE_STATE=? where RECEIPT_ID=?";
		//System.out.println(sql);
		return this.getJdbcTemplate().update(sql, new Object[]{"3",receiptId});
	}
	
	/**
	 * 获得采购物料的剩余数量
	 * 
	 * @param purchaseRowsId
	 * @param receiptId
	 * @param receiptDetailId
	 * @return
	 */
	public BigDecimal getPurchaseHasQuantity(String purchaseRowsId,String receiptId,String receiptDetailId)
	{
		String sql = "select has_quantity from v_sel_purchase_apply_mt where purchase_rows_id=? ";
		List list = this.getJdbcTemplate().queryForList(sql,
				new Object[] { purchaseRowsId });
		if (list != null && list.size() > 0)
		{
			BigDecimal has_quantity = (BigDecimal)((Map) list.get(0)).get("has_quantity");

			if (StringUtils.isNotBlank(receiptId))
			{
				sql = "select SUM(NVL(quantity,0)) as quantity from t_receipt_material where receipt_id=? ";
				sql += " And PURCHASE_ROWS_ID='"+ purchaseRowsId +"'";
				if (receiptDetailId!="")
					sql+=" And receipt_detail_id='"+ receiptDetailId +"'";
				list = this.getJdbcTemplate().queryForList(sql,
						new Object[] { receiptId });
				if (list != null && list.size() > 0)
				{
					BigDecimal quantity = (BigDecimal)((Map) list.get(0)).get("quantity");
					if (quantity!=null)
						has_quantity = has_quantity.add(quantity);
				}
			}
			return has_quantity;
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
	
	public boolean isRightBatchNo(String materialCode,String warehouse,String batchNo,String tradeType,String deptId){
		//存代理批次号不允许重复,现全部校验,除了五部纸的业务
		//if("5".equals(tradeType)||"6".equals(tradeType)){
		if(!materialCode.startsWith("Z01")){
			String sql = "select count(*) from t_receipt_material m left outer join t_receipt_info i on m.receipt_id=i.receipt_id "+
                         "where i.old_receipt_id is null and i.receipt_id not in (select old_receipt_id from t_receipt_info where old_receipt_id is not null) " +
                         " and m.material_code='"+materialCode+"' and i.warehouse='"+warehouse+"' and m.batch_no='"+batchNo+"' and i.dept_id='"+deptId+"' and i.is_available=1 and i.EXAMINE_STATE!=4";
		    int i = getJdbcTemplate().queryForInt(sql);
		    if(i>0) return false;
		    return true;
		}
		return true;
	}
	
	public List<TReceiptMaterial> queryReceiptMaterial(String receiptId){

		final List<TReceiptMaterial> list = new ArrayList<TReceiptMaterial>();
		String strSql = "select k.material_code,k.material_name,k.material_unit,k.quantity,k.price,k.batch_no,k.currency,k.total from t_receipt_material k where " +
                        "k.receipt_id=? ";
		
		getJdbcTemplate().query(strSql,new Object[]{receiptId}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				TReceiptMaterial material = new TReceiptMaterial();
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
	
	public String queryDeptCode(String receiptId)
	{
		final List<String> returnFalg = new ArrayList();
		String sql = "select dept_code from t_sys_dept where dept_id=(select dept_id from t_receipt_info where receipt_id='"+receiptId+"')";
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
	
	/**
	 * 更新进仓物料表中的日期信息
	 * @param strFieldName
	 * @param strDieldValue
	 * @param strReceiptId
	 */
	public void updateDate(String strFieldName,String strDieldValue,String strReceiptId){
		String strSql = "update t_receipt_material a set a."+strFieldName+
		" = '"+strDieldValue+"' where a.receipt_id = '" +strReceiptId+ "'";
		
		this.getJdbcTemplate().update(strSql);
	}
	
	public void dealStorageToExcel(String sql ,final ExcelObject excel){
		getJdbcTemplate().query(sql,new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[24];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("APPROVED_TIME");
				values[2] = rs.getString("COMPANY_CODE");
				values[3] = rs.getString("WAREHOUSENAME");
				values[4] = rs.getString("DEPT_NAME");
				values[5] = rs.getString("TRADETYPENAME");
				values[6] = rs.getString("PROJECT_NO");
				values[7] = rs.getString("CONTRACT_NO");
				values[8] = rs.getString("CONTRACT_PAPER_NO");
				values[9] = rs.getString("SAP_ORDER_NO");
				values[10] = rs.getString("UNIT_NAME");
				values[11] = rs.getString("GROUPNAME");
				values[12] = rs.getString("MATERIAL_CODE");
				values[13] = rs.getString("MATERIAL_NAME");
				values[14] = rs.getString("BATCH_NO");
				values[15] = rs.getString("KULIN");
				values[16] = rs.getString("MATERIAL_UNIT");
				values[17] = rs.getString("KUCU");
				values[18] = rs.getString("UNITPRICE");
				values[19] = rs.getString("EKPO_PEINH");
				values[20] = rs.getString("CURRENCY");
				values[21] = rs.getString("TOTAL");
				values[22] = rs.getString("CNYTOTAL");
				values[23] = rs.getString("YUQI");
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}
	public void dealSeConfigToExcel(String sql ,final ExcelObject excel){
		getJdbcTemplate().query(sql,new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[24];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("TRADETYPENAME");
				values[2] = rs.getString("MARK");
				values[3] = rs.getString("COMPANY_CODE");
				values[4] = rs.getString("SECONFIGTIME");
				values[5] = rs.getString("DEPT_NAME");
				values[6] = rs.getString("WAREHOUSE_ADD");
				values[7] = rs.getString("PROJECT_NO");
				values[8] = rs.getString("PROJECT_NAME");
				values[9] = rs.getString("SERIALNO");
				values[10] = rs.getString("CONTRACT_NO");
				values[11] = rs.getString("CONTRACT_PAPER_NO");
				values[12] = rs.getString("SAP_ORDER_NO");
				values[13] = rs.getString("UNIT_NAME");
				values[14] = rs.getString("MGTITLE");
				values[15] = rs.getString("MATERIAL_CODE");
				values[16] = rs.getString("MATERIAL_NAME");
				values[17] = rs.getString("MATERIAL_UNIT");
				values[18] = rs.getString("BATCH_NO");
				values[19] = rs.getString("QUANTITY");
				values[20] = rs.getString("PRICE");
				values[21] = rs.getString("EKPO_PEINH");
				values[22] = rs.getString("TOTAL");
				values[23] = rs.getString("CURRENCY");
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}
	
	public void updateDate(TReceiptInfo info ,String oldStr,String newStr,String userId){
			String strSql = " update t_receipt_info set RECEIPT_TIME =?,PAYABLEDATE=?,RECEIVEBILLDATE=?,SENDGOODSDATE=? where receipt_id = ?";
			String strsqlm = " update t_receipt_material set RECEIPTTIME =?,PAYABLEDATE=?,RECEIVEBILLDATE=?,SENDGOODSDATE=? where receipt_id = ?";
			String strsqllog = " insert into t_saveDateLog values (?,?,?,?,?,?,?)";
			getJdbcTemplate().update(strSql, new Object[] { info.getReceiptTime(),info.getPayAbleDate(),info.getReceiveBillDate(),info.getSendGoodsDate(),info.getReceiptId() });
			getJdbcTemplate().update(strsqlm, new Object[] { info.getReceiptTime(),info.getPayAbleDate(),info.getReceiveBillDate(),info.getSendGoodsDate(),info.getReceiptId() });
			getJdbcTemplate().update(strsqllog, new Object[] { CodeGenerator.getUUID(),info.getReceiptId(),"进仓",oldStr,newStr,userId,DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE) });
	}
	/**
	public List fuckDealQuery(){
		//String sql = "select receipt_id, nvl(post_date,substr(approved_time,0,10)) post_date from t_receipt_info where "+ 
        //"approved_time>'2015-03-30 16:00:03' and approved_time<'2015-04-07 16:00:03' and examine_state=3 and sap_return_no!='纯代理' and sap_order_no not  in ('4500005124','4300000544')  order by approved_time";
		String sql = "select receipt_id, nvl(post_date,substr(approved_time,0,10)) post_date from t_receipt_info where "+ 
		        "approved_time>'2015-03-31 09:49:46' and  approved_time<'2015-04-06 16:00:03' and examine_state=3 and sap_return_no!='纯代理' and sap_order_no not  in ('4500005124','4300000544') and receipt_id !='5659F738-3851-4296-BB0D-09E5448D62D2' and 1=2 order by approved_time";
		return this.getJdbcTemplate().queryForList(sql);
	}
	public void fuckDeal(String receiptid,String oldorderNO,String newOrderNo,String msg){
		String strSql ="insert into SAPWRONT_DEAL_LOG " +
				  "(ID, " +
				   "busid, " +
				   "oldorderNO, " +
				   "type, " +
				   "newOrderNo,creatorTime,msg) " +
				"values(?,?,?,?,?,?,?)";
		getJdbcTemplate().update(strSql,new Object[]{CodeGenerator.getUUID(),receiptid,oldorderNO,"进仓",newOrderNo,DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE),msg});
	}
	public int fuckCheck(String receiptid){
		String strSql ="select count(*) cn from SAPWRONT_DEAL_LOG where busid='"+receiptid+"' and trim(neworderno) is not null";
		return getJdbcTemplate().queryForInt(strSql);
	}**/
}
