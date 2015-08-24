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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.ReceiptShipConstants;
import com.infolion.sapss.payment.PaymentContants;
import com.infolion.sapss.ship.domain.TShipInfo;
import com.infolion.sapss.ship.domain.TShipMaterial;

@Repository
public class ShipInfoJdbcDao extends BaseDao
{

	/**
	 * 如果预计收款日期、预计开票日期有修改，则同时更新仓单行项目数据上的日期。
	 * 
	 * @param tShipInfo
	 * @param changeIgTime
	 * @param changeMiTime
	 */
	public void updateShipMaterielDateFields(TShipInfo tShipInfo, boolean changeIgTime, boolean changeMiTime)
	{
		StringBuffer sb = new StringBuffer();
		if (changeIgTime && changeMiTime)
		{
			sb.append("update T_SHIP_MATERIAL set INTEND_GATHER_TIME=? ,MAKE_INVOICE_TIME=? where SHIP_ID=?");
			this.getJdbcTemplate().update(sb.toString(), new Object[] { tShipInfo.getIntendGatherTime(), tShipInfo.getMakeInvoiceTime(), tShipInfo.getShipId() });
		}
		else if (changeIgTime && !changeMiTime)
		{
			sb.append("update T_SHIP_MATERIAL set INTEND_GATHER_TIME=?  where SHIP_ID=?");
			this.getJdbcTemplate().update(sb.toString(), new Object[] { tShipInfo.getIntendGatherTime(), tShipInfo.getShipId() });
		}
		else if (!changeIgTime && changeMiTime)
		{
			sb.append("update T_SHIP_MATERIAL set MAKE_INVOICE_TIME=? where SHIP_ID=?");
			this.getJdbcTemplate().update(sb.toString(), new Object[] { tShipInfo.getMakeInvoiceTime(), tShipInfo.getShipId() });
		}
	}

	/**
	 * 修改出仓物料表
	 * 
	 * @param shipDetailId
	 * @param colName
	 * @param colValue
	 */
	public void updateShipMateriel(String shipDetailId, String colName, String colValue)
	{
		String sql = " update t_ship_material " + "set " + colName + "='" + colValue + "' where ship_detail_id = ?";

		getJdbcTemplate().update(sql, new Object[] { shipDetailId });

		if (colName.equals("QUANTITY") || colName.equals("PRICE") || "EKPO_PEINH".equals(colName))
		{
			sql = " update t_ship_material set total = round(nvl(quantity,0) * nvl(price,0)/nvl(EKPO_PEINH,1),2) where ship_detail_id = ?";
			getJdbcTemplate().update(sql, new Object[] { shipDetailId });
		}

		/*************** fuyy *******************/
		if (colName.equals("QUANTITY") || colName.equals("SALE_PRICE") || "VBAP_KPEIN".equals(colName))
		{
			sql = " update t_ship_material set sale_total = round(nvl(quantity,0) * nvl(sale_price,0)/nvl(VBAP_KPEIN,1),2) where ship_detail_id = ?";
			getJdbcTemplate().update(sql, new Object[] { shipDetailId });
		}
		/*************** fuyy *******************/
	}

	public synchronized String getShipNo(String code)
	{
		final List<String> returnFalg = new ArrayList();
		String sql = "select lpad(nvl(substr(max(a.ship_no),3,8) + 1,1),8,'0') ship_no  from t_ship_info a where a.ship_no like '"+code+"%'";

		getJdbcTemplate().query(sql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				returnFalg.add(rs.getString("ship_no"));
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
		String sql = "select lpad(nvl(substr(max(a.SerialNo),3,8) + 1,1),8,'0') SerialNo  from t_ship_info a ";

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
	 * 获得销售物料的剩余数量
	 * 
	 * @param salesRowsId
	 * @return
	 */
	public BigDecimal getSalesHasQuantity(String salesRowsId)
	{
		String sql = "select has_quantity from v_sel_sales_ship_mt where sales_rows_id=? ";
		List list = this.getJdbcTemplate().queryForList(sql, new Object[] { salesRowsId });
		if (list != null && list.size() > 0)
		{
			return (BigDecimal) ((Map) list.get(0)).get("has_quantity");
		}
		else
			return BigDecimal.valueOf(0);
	}

	/**
	 * 获得出货通知单物料的剩余数量
	 * 
	 * @param agentMaterialId
	 * @return
	 */
	public BigDecimal getApplyHasQuantity(String exportMateId)
	{
		String sql = "select has_quantity from v_sel_apply_ship_mt where export_mate_id=? ";
		List list = this.getJdbcTemplate().queryForList(sql, new Object[] { exportMateId });
		if (list != null && list.size() > 0)
		{
			return (BigDecimal) ((Map) list.get(0)).get("has_quantity");
		}
		else
			return BigDecimal.valueOf(0);
	}

	/**
	 * 获得出仓单的物料总金额
	 * 
	 * @param shipId
	 * @return
	 */
	public BigDecimal getShipInfoTotal(String shipId)
	{
		String sql = "select nvl(sum(nvl(total,0)),0) total from t_ship_material where ship_id=? ";
		List list = this.getJdbcTemplate().queryForList(sql, new Object[] { shipId });
		if (list != null && list.size() > 0)
		{
			return (BigDecimal) ((Map) list.get(0)).get("total");
		}
		else
			return BigDecimal.valueOf(0);
	}

	/**
	 * 获得进料加工业务"成品物料采购"的剩余数量
	 * 
	 * @param bomId
	 * @return
	 */
	public BigDecimal getPurchaseHasQuantity(String bomId, String shipId, String shipDetailId)
	{
		String sql = "select has_quantity from v_sel_purchase_ship_mt where bom_id=? ";
		List list = this.getJdbcTemplate().queryForList(sql, new Object[] { bomId });
		if (list != null && list.size() > 0)
		{
			BigDecimal has_quantity = (BigDecimal) ((Map) list.get(0)).get("has_quantity");

			if (StringUtils.isNotBlank(shipId))
			{
				sql = "select SUM(NVL(quantity,0)) as quantity from t_ship_material where ship_id=? ";
				sql += " And BOM_ID='" + bomId + "'";
				if (shipDetailId != "")
					sql += " And ship_detail_id='" + shipDetailId + "'";
				list = this.getJdbcTemplate().queryForList(sql, new Object[] { shipId });
				if (list != null && list.size() > 0)
				{
					BigDecimal quantity = (BigDecimal) ((Map) list.get(0)).get("quantity");
					if (quantity != null)
						has_quantity = has_quantity.add(quantity);
				}
			}

			return has_quantity;
		}
		else
			return BigDecimal.valueOf(0);
	}

	/**
	 * 查询库存数量
	 * 
	 * @param materialCode
	 * @param wareHouse
	 * @param batchNo
	 */
	public Map queryInventoryNum(String materialCode, String warehouse, String batchNo, String shipId, String shipDetailId,String deptId,String examState)
	{
        BigDecimal value = getShipQuality(materialCode, warehouse, batchNo,shipDetailId, deptId);
        
		StringBuffer sb = new StringBuffer();
		Map map = new HashMap();
		// 取得进仓单物料的单价,币别,条件定价单位
		StringBuffer sql = new StringBuffer();
		sql.append("select b.*,a.DECLARATIONS_NO from t_receipt_material b left outer join t_receipt_info a on b.receipt_id = a.receipt_id ");
		sql.append("where a.warehouse=? ");
		sql.append("and a.is_available='1' ");
		sql.append("and b.material_code=? and b.batch_no=? and a.dept_id=? and a.examine_state in (1,2,3,5)");
		List list1 = this.getJdbcTemplate().queryForList(sql.toString(), new Object[] { warehouse, materialCode, batchNo,deptId });
		if (value.signum()>0 && list1 != null && list1.size() > 0)
		{ 
			Map m = (Map) list1.get(0);
			//map.put("PRICE", m.get("PRICE")!=null?m.get("PRICE"):"0");
			Object price ="0";
			if(m.get("COST_PRICE")!=null&&((BigDecimal)m.get("COST_PRICE")).doubleValue()>0)
				price =  m.get("COST_PRICE");
			else if(m.get("PRICE")!=null&&((BigDecimal)m.get("PRICE")).doubleValue()>0)
				price =  m.get("PRICE");
			map.put("PRICE", price);
			map.put("CURRENCY", m.get("CURRENCY")!=null?m.get("CURRENCY"):" ");
			map.put("EKPO_PEINH", m.get("EKPO_PEINH")!=null?m.get("EKPO_PEINH"):"1");
			map.put("DECLARATIONS_NO", m.get("DECLARATIONS_NO")!=null?m.get("DECLARATIONS_NO"):"");
			map.put("HAS_SHIP_QUALITY", value);
		}
		else
		{
			map.put("kucunshu", "0");
			map.put("PRICE", "0");
			map.put("CURRENCY", " ");
			map.put("EKPO_PEINH", "1");
			map.put("DECLARATIONS_NO", "");
			map.put("HAS_SHIP_QUALITY", value.doubleValue()>0?value:"0");
		}

		// 更新进仓物料表
		String sql1 = "update t_ship_material a set a.price=?,a.currency=?,a.ekpo_peinh=?,a.quantity=?," + 
		              "a.total = round(? * nvl(?,0)/nvl(?,1),2),a.SALE_TOTAL= round(? * nvl(a.sale_price,0)/nvl(a.VBAP_KPEIN,1),2) where a.ship_detail_id=?";
		
		String sql2 = "update t_ship_material a set a.price=?,a.currency=?,a.ekpo_peinh=?,a.sequantity=?," + 
                      "a.total = round(? * nvl(?,0)/nvl(?,1),2),a.SALE_TOTAL= round(? * nvl(a.sale_price,0)/nvl(a.VBAP_KPEIN,1),2) where a.ship_detail_id=?";
		
		this.getJdbcTemplate().update("5".equals(examState)?sql2:sql1, new Object[] { map.get("PRICE"), map.get("CURRENCY"), map.get("EKPO_PEINH"), 
				map.get("HAS_SHIP_QUALITY"),map.get("HAS_SHIP_QUALITY"),
				map.get("PRICE"), map.get("EKPO_PEINH"),map.get("HAS_SHIP_QUALITY"), shipDetailId });
		// //更新库存物料
		// String
		// sql2="update T_MATERIAL_INVENTORY b set b.inventory_num=? where b.warehouse=? and b.material_code=? and b.batch_no=?";
		// this.getJdbcTemplate().update(sql2,new
		// Object[]{map.get("kucunshu"),warehouse,materialCode,batchNo});
		return map;
		// String sql =
		// "select SUM(INVENTORY_NUM) as INVENTORY_NUM from T_MATERIAL_INVENTORY"
		// +
		// " where MATERIAL_CODE=?";
		//		
		// // if (StringUtils.isNotBlank(warehouse))
		// sql+=" And WAREHOUSE='"+warehouse+"'";
		//		
		// // if (StringUtils.isNotBlank(batchNo))
		// sql+=" And BATCH_NO='"+batchNo+"'";
		//		
		// List list = this.getJdbcTemplate().queryForList(sql,new Object[] {
		// materialCode});
		// if (list != null && list.size() > 0)
		// {
		// BigDecimal inventoryNum = (BigDecimal)((Map)
		// list.get(0)).get("INVENTORY_NUM");
		// if (inventoryNum!=null)
		// return inventoryNum.doubleValue();
		// else
		// return 0;
		// }
		// else
		// return 0;
	}
	
	public BigDecimal getShipQuality(String materialCode, String warehouse, String batchNo,String shipDetailId,String deptId){
		/***20120608**/
		//已入库
		String receiptSql = "select nvl(sum(a.quantity),0) from t_receipt_material a "+
						  "left outer join t_receipt_info b on a.receipt_id=b.receipt_id where b.is_available=1 "+
						  "and a.material_code='"+materialCode+"' and b.warehouse='"+warehouse+"' and a.batch_no='"+batchNo+"' and b.dept_id='"+deptId+"' "+
						  "and ((b.examine_state = 3 and b.approved_time>'"+ReceiptShipConstants.START_DATE+"') or b.examine_state=5)";
		BigDecimal receiptValue = (BigDecimal)getJdbcTemplate().queryForObject(receiptSql, BigDecimal.class);
		//计算期初库存
		String orignSql = "select nvl(sum(c.quantity),0) From t_profitloss_origin c " +
                          "where c.material_code='"+materialCode+"' and c.warehouse='"+warehouse+"' and c.batch_no='"+batchNo+"' and c.dept_id='"+deptId+"'";
		BigDecimal orignValue = (BigDecimal)getJdbcTemplate().queryForObject(orignSql, BigDecimal.class);
		
		//销售退货
		String salesSql = "select nvl(sum(a.quantity),0) from t_sales_return_material a "+
		                  "left outer join t_sales_return b on a.return_id=b.return_id where b.is_available=1 "+
		                  "and a.material_code='"+materialCode+"' and b.warehouse='"+warehouse+"' and a.batch_no='"+batchNo+"' and b.dept_id='"+deptId+"' "+
		                  "and b.examine_state = 3 and b.approved_time>'"+ReceiptShipConstants.START_DATE+"'";
		BigDecimal salesValue = (BigDecimal)getJdbcTemplate().queryForObject(salesSql, BigDecimal.class);
		
		//计算已出仓数（包括新增和在批数据）
		String shipSql = "select nvl(sum(a1.quantity),0) from t_ship_material a1 " +
                         "left outer join t_ship_info b1 on a1.ship_id=b1.ship_id where b1.is_available=1 "+
						 "and a1.material_code='"+materialCode+"' and b1.warehouse='"+warehouse+"' and a1.batch_no='"+batchNo+"' and a1.ship_detail_id!='"+shipDetailId+"' and b1.dept_id='"+deptId+"' "+
						 "and ((b1.examine_state in (1,2) and b1.creator_time>'"+ReceiptShipConstants.START_DATE+"') or (b1.examine_state=3 and b1.approved_time>'"+ReceiptShipConstants.START_DATE+"') or b1.examine_state=5)";
		BigDecimal shipValue = (BigDecimal)getJdbcTemplate().queryForObject(shipSql, BigDecimal.class);
		//计算可用库存
		return receiptValue.add(orignValue).add(salesValue).subtract(shipValue);
		/*****/		
	}

	/**
	 * 获得出仓单的币别
	 * 
	 * @param shipId
	 * @return
	 */
	public String getShipCurrency(String shipId)
	{
		String sql = "select currency from t_ship_material where ship_id=? And nvl(Currency,'0')<>'0'";
		List list = this.getJdbcTemplate().queryForList(sql, new Object[] { shipId });
		if (list != null && list.size() > 0)
		{
			return (String) ((Map) list.get(0)).get("currency");
		}
		else
			return "CNY";
	}

	/**
	 * 获得币别汇率
	 * 
	 * @param currency
	 * @return
	 */
	public String getCurrentRate(String currency)
	{
		final List<String> list = new ArrayList<String>();
		getJdbcTemplate().query("SELECT RATE FROM BM_CURRENCY WHERE ID=?", new Object[] { currency }, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				list.add(rs.getString("rate"));
			}
		});
		if (list.isEmpty())
			return "1";
		return list.get(0);
	}

	/**
	 * 申请不通过时,把记录更新为不通过,使得所申请数量返回仓内
	 * 
	 * @param businessRecordId
	 * @return
	 */
	public int updateMaterialRecordToDisavailable(String businessRecordId)
	{
		String sql = "update t_ship_material t set t.is_available=0 where t.ship_id=?";
		return this.getJdbcTemplate().update(sql, new Object[] { businessRecordId });
	}

	/**
	 * 更改指定字段
	 * 
	 * @param receiptDetailId
	 * @param colName
	 * @param colValue
	 */
	public void updateShipInfo(String shipId, String colName, String colValue)
	{
		String strSql = " update t_ship_info " + "set " + colName + "='" + colValue + "' where ship_id = ?";

		getJdbcTemplate().update(strSql, new Object[] { shipId });
	}

	public List<TShipMaterial> queryShipMaterialList(String shipId)
	{
		final List<TShipMaterial> list = new ArrayList<TShipMaterial>();
		String strSql = "select k.material_code,k.material_name,k.material_unit,k.quantity,k.price,k.batch_no,k.currency,k.total from t_ship_material k where " + "k.ship_id=? ";

		getJdbcTemplate().query(strSql, new Object[] { shipId }, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				TShipMaterial material = new TShipMaterial();
				material.setMaterialCode(rs.getString("material_code"));
				material.setMaterialName(rs.getString("material_name"));
				material.setMaterialUnit(rs.getString("material_unit"));
				material.setQuantity(new BigDecimal(rs.getDouble("quantity")).setScale(3, BigDecimal.ROUND_HALF_UP));
				String p =rs.getString("price");
				material.setCmd(PaymentContants.round(p==null?"0":p, 2));
				material.setBatchNo(rs.getString("batch_no"));
				material.setCurrency(rs.getString("currency"));
				String t = rs.getString("total");
				material.setTotal(new BigDecimal(t==null?"0":t).setScale(2, BigDecimal.ROUND_HALF_UP));
				list.add(material);
			}
		});

		return list;
	}

	public String queryDeptCode(String shipId)
	{
		final List<String> returnFalg = new ArrayList();
		String sql = "select dept_code from t_sys_dept where dept_id=(select dept_id from t_ship_info where ship_id='" + shipId + "')";
		getJdbcTemplate().query(sql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{

				returnFalg.add(rs.getString("dept_code"));
			}
		});
		if (returnFalg.size() == 0)
			return "";
		else
			return returnFalg.get(0);
	}
	
	public void updateSuccess(TShipInfo info){
		String sql ;
		//进仓冲销
		if(StringUtils.isNotBlank(info.getOldShipId())&&StringUtils.isNotBlank(info.getBillApplyId())){
			sql = "update t_bill_apply b set b.export_apply_no=replace(b.export_apply_no,'"+info.getOldShipNo()+"',''), b.sap_return_no=replace(b.sap_return_no,'"+info.getOldSapReturnNo()+"','') where bill_apply_id='"+info.getBillApplyId()+"'";
		    getJdbcTemplate().update(sql);
		}
		//进仓
		else if(StringUtils.isBlank(info.getOldShipId())&&StringUtils.isNotBlank(info.getBillApplyId())){
			sql ="update t_bill_apply b set b.export_apply_no=b.export_apply_no||','||'"+info.getShipNo()+"', b.sap_return_no=b.sap_return_no||','||'"+info.getSapReturnNo()+"' where bill_apply_id='"+info.getBillApplyId()+"'";
			getJdbcTemplate().update(sql);
		}
		
	}
	
	public List queryTaskHis(String businessid,boolean isPrint){
		String sql = "select TASK_NAME,EXAMINE_DEPT_NAME,EXAMINE_PERSON,EXAMINE_RESULT,EXAMINE,TASK_CREATE_TIME,TASK_END_TIME from (SELECT * fROM t_sys_wf_task_history union SELECT * FROM t_sys_wf_task_history_O) where business_record_id='"+businessid+"' order by TASK_END_TIME";
		List list =  getJdbcTemplate().queryForList(sql);
		//默认不屏蔽，打印屏蔽
		if(!isPrint&&!ReceiptShipConstants.ins().isShouldHide()) return list;
		List listR = new ArrayList();
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			String taskName = (String)map.get("TASK_NAME");
			String examineResult = (String)map.get("EXAMINE_RESULT");
			if("部门经理确认*".equals(taskName)){
				listR.removeAll(listR);
			}
			map.put("TASK_NAME", taskName.replace("*", ""));
			map.put("EXAMINE_RESULT", examineResult.replace("二次结算", "确认"));
			listR.add(map);
		}
		return listR;
		//return null;
	}
	
	public void updateSeReceiptQua(String receiptId,String type){
		String strSql ;
		if("1".equals(type))
		   strSql = " update t_ship_material  set SEQUANTITY = QUANTITY  where ship_id = ?";
		else if("2".equals(type)){
			strSql = " update t_ship_material  set QUANTITY = SEQUANTITY,total = round(nvl(sequantity*price/ekpo_peinh,0),2),sale_total = round(nvl(sequantity*sale_price/VBAP_KPEIN,0),2)  where ship_id = ?";
		}
		else strSql = " update t_ship_material  set SEQUANTITY = ''  where ship_id = ?";
		getJdbcTemplate().update(strSql, new Object[] { receiptId });
	}
	
	public void updateDate(TShipInfo info ,String oldStr,String newStr,String userId){
		String strSql = " update t_ship_info set INTEND_GATHER_TIME =?,MAKE_INVOICE_TIME=? where ship_id = ?";
		String strsqlm = " update t_ship_material set INTEND_GATHER_TIME =?,MAKE_INVOICE_TIME=? where ship_id = ?";
		String strsqllog = " insert into t_saveDateLog values (?,?,?,?,?,?,?)";
		getJdbcTemplate().update(strSql, new Object[] {info.getIntendGatherTime(),info.getMakeInvoiceTime(),info.getShipId()});
		getJdbcTemplate().update(strsqlm, new Object[] {info.getIntendGatherTime(),info.getMakeInvoiceTime(),info.getShipId()});
		getJdbcTemplate().update(strsqllog, new Object[] { CodeGenerator.getUUID(),info.getShipId(),"出仓",oldStr,newStr,userId,DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE) });
    }
	/**
	public List fuckDealQuery(){
		//2015-03-30 16:08:30  2015-04-06 16:00:03
		String sql = "select ship_id,  sap_return_no from t_ship_info where "+
        "approved_time>'2015-03-30 16:08:30' and approved_time<'2015-04-06 16:00:03' and examine_state=3 and is_available=1 and sap_return_no!='纯代理' and ship_id !='0A270A9B-C234-4930-8585-C2B6F4CAC60A' and 1=2  order by approved_time  ";
		return this.getJdbcTemplate().queryForList(sql);
	}
	public void fuckDeal(String shipid,String oldorderNO,String newOrderNo,String msg){
		String strSql ="insert into SAPWRONT_DEAL_LOG " +
				  "(ID, " +
				   "busid, " +
				   "oldorderNO, " +
				   "type, " +
				   "newOrderNo,creatorTime,msg) " +
				"values(?,?,?,?,?,?,?)";
		getJdbcTemplate().update(strSql,new Object[]{CodeGenerator.getUUID(),shipid,oldorderNO,"出仓",newOrderNo,DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE),msg});
	}
	public int fuckCheck(String ship_id){
		String strSql ="select count(*) cn from SAPWRONT_DEAL_LOG where busid='"+ship_id+"' and trim(neworderno) is not null";
		return getJdbcTemplate().queryForInt(strSql);
	}
	public int fuckDealBill(){
		//这个逻辑有问题。
		/**
		String sql = " SELECT oldorderno,neworderno,busid,id fROM SAPWRONT_DEAL_LOG where type='出仓' and neworderno is not null order by creatortime ";
		List list = this.getJdbcTemplate().queryForList(sql);
		for(int i = 0 ; i<list.size() ; i++){
			Map map = (Map)list.get(i);
			String id = (String)map.get("id".toUpperCase());
			String busid = (String)map.get("busid".toUpperCase());
			String oldorderno = (String)map.get("oldorderno".toUpperCase());
			String neworderno = (String)map.get("neworderno".toUpperCase());
			String sql1 = "select bill_apply_id, sap_Return_No from t_bill_apply where sap_Return_No like '%"+oldorderno+"%'";
			List list1 = this.getJdbcTemplate().queryForList(sql1);
			for(int k = 0 ;k<list1.size();k++){
				Map map1 = (Map)list1.get(k);
				String bill_apply_id = (String)map1.get("bill_apply_id".toUpperCase());
				String sap_Return_No = (String)map1.get("sap_Return_No".toUpperCase());
				String sap_new_return_No = sap_Return_No.replace(oldorderno, neworderno);
				getJdbcTemplate().update("update t_bill_apply set sap_Return_No=? where bill_apply_id=?" ,new Object[]{sap_new_return_No,bill_apply_id});
				String strSql ="insert into SAPWRONT_DEAL_LOG " +
						  "(ID, " +
						   "busid, " +
						   "oldorderNO, " +
						   "type, " +
						   "newOrderNo,creatorTime,msg) " +
						"values(?,?,?,?,?,?,?)";
				getJdbcTemplate().update(strSql,new Object[]{CodeGenerator.getUUID(),bill_apply_id,sap_Return_No,"开票",sap_new_return_No,DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE),"logId:"+id+"shipid:"+busid});
			}
		}**/
	    /**
		String sql = "select  a.bill_apply_id, a.export_Apply_No,a.sap_return_no from t_bill_apply a "+
                     " where a.creator_time>'2015-03-30 15:02:49'  ";
		List list = this.getJdbcTemplate().queryForList(sql);
		for(int i = 0 ; i<list.size() ; i++){			
			Map map = (Map)list.get(i);
            String bill_apply_id = (String)map.get("bill_apply_id".toUpperCase());
			String export_Apply_No = (String)map.get("export_Apply_No".toUpperCase());
			String sap_return_no = (String)map.get("sap_return_no".toUpperCase());
			if(null==export_Apply_No||"".equals(export_Apply_No)) continue;
			String sql1 = "select distinct sap_return_no from t_ship_info where ship_no in ('"+export_Apply_No.replaceAll(",", "','")+"')";
			String newSapReturnNo = "";
			List list1 = this.getJdbcTemplate().queryForList(sql1);
			for(int k = 0 ;k<list1.size();k++){
				Map map1 = (Map)list1.get(k);
				newSapReturnNo+=(String)map1.get("sap_return_no".toUpperCase())+",";
			}
			getJdbcTemplate().update("update t_bill_apply set sap_Return_No=? where bill_apply_id=?" ,new Object[]{newSapReturnNo,bill_apply_id});
			String strSql ="insert into SAPWRONT_DEAL_LOG " +
					  "(ID, " +
					   "busid, " +
					   "oldorderNO, " +
					   "type, " +
					   "newOrderNo,creatorTime,msg) " +
					"values(?,?,?,?,?,?,?)";
			getJdbcTemplate().update(strSql,new Object[]{CodeGenerator.getUUID(),bill_apply_id,sap_return_no,"开票更新1",newSapReturnNo,DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE),""});
		}
		return 0;
	}**/
}
