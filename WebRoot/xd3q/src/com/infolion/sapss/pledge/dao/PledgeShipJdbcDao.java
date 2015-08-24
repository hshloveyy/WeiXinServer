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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.sapss.ReceiptShipConstants;
import com.infolion.sapss.payment.PaymentContants;
import com.infolion.sapss.pledge.domain.PledgeShipsItem;
import com.infolion.sapss.ship.domain.TShipMaterial;

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
public class PledgeShipJdbcDao extends BaseDao
{
	/**
	 * 根据ID删除出仓单信息
	 * @param receiptId
	 * @return
	 */
	public int delete(String pledgeShipsInfoId){
		String sql="delete from T_PLEDGESHIPS_INFO where PLEDGESHIPS_INFO_ID=?";
		return this.getJdbcTemplate().update(sql, new Object[]{pledgeShipsInfoId});
	}
	/**
	 * 取得最新的ShipNo单据流水号
	 * @param 
	 * @return
	 */
	public synchronized String getShipNo(String code)
	{
		final List<String> returnFalg = new ArrayList();
		String sql = "select lpad(nvl(substr(max(a.pledgeships_info_no),3,8) + 1,1),8,'0') pledgeships_info_no from t_pledgeships_info a where a.pledgeships_info_no like '"+code+"%'";

		getJdbcTemplate().query(sql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				returnFalg.add(rs.getString("pledgeships_info_no"));
			}
		});

		if (returnFalg.size() == 0)
			return "";
		else
			return code+returnFalg.get(0);
	}
	public String queryDeptCode(String shipId)
	{
		final List<String> returnFalg = new ArrayList();
		String sql = "select dept_code from t_sys_dept where dept_id=(select dept_id from t_pledgeships_info where pledgeships_info_id='"+shipId+"')";
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
	public int submitUpdateState(String shipId){
		String sql = "update T_PLEDGESHIPS_INFO set EXAMINE_STATE=? where PLEDGESHIPS_INFO_ID=?";
		return this.getJdbcTemplate().update(sql, new Object[]{"3",shipId});
	}
	
	public BigDecimal getShipQuality(String materialCode, String warehouse, String batchNo,String shipDetailId,String deptId){
		//已入库
		String receiptSql = "select nvl(sum(a.quantity),0) from T_PLEDGERECEIPTS_ITEM a "+
						  "left outer join T_PLEDGERECEIPTS_INFO b on a.PLEDGERECEIPTS_ID=b.PLEDGERECEIPTS_INFO_ID where b.is_available=1 "+
						  "and a.material_code='"+materialCode+"' and b.warehouse='"+warehouse+"' and a.batch_no='"+batchNo+"' and b.dept_id='"+deptId+"' "+
						  "and ((b.examine_state = 3 and b.approved_time>'"+ReceiptShipConstants.START_DATE+"') or b.examine_state=5)";
		BigDecimal receiptValue = (BigDecimal)getJdbcTemplate().queryForObject(receiptSql, BigDecimal.class);		
		//计算已出仓数（包括新增和在批数据）
		String shipSql = "select nvl(sum(a1.quantity),0) from T_PLEDGESHIPS_ITEM a1 " +
                         "left outer join T_PLEDGESHIPS_INFO b1 on a1.PLEDGESHIPS_ID=b1.PLEDGESHIPS_INFO_ID where b1.is_available=1 "+
						 "and a1.material_code='"+materialCode+"' and b1.warehouse='"+warehouse+"' and a1.batch_no='"+batchNo+"' and a1.PLEDGESHIPS_ITEM_ID!='"+shipDetailId+"' and b1.dept_id='"+deptId+"' "+
						 "and ((b1.examine_state in (1,2) and b1.creator_time>'"+ReceiptShipConstants.START_DATE+"') or (b1.examine_state=3 and b1.approved_time>'"+ReceiptShipConstants.START_DATE+"') or b1.examine_state=5)";
		BigDecimal shipValue = (BigDecimal)getJdbcTemplate().queryForObject(shipSql, BigDecimal.class);
		//计算可用库存
		return receiptValue.subtract(shipValue);		
	}
	
	/**
	 * 查询库存数量
	 * 
	 * @param materialCode
	 * @param wareHouse
	 * @param batchNo
	 */
	public Map queryInventoryNum(String materialCode, String warehouse, String batchNo, String shipId, String shipDetailId,String deptId)
	{
        BigDecimal value = getShipQuality(materialCode, warehouse, batchNo,shipDetailId, deptId);
        
		StringBuffer sb = new StringBuffer();
		Map map = new HashMap();
		// 取得进仓单物料的单价,币别,条件定价单位
		StringBuffer sql = new StringBuffer();
		sql.append("select b.* from T_PLEDGERECEIPTS_ITEM b left outer join T_PLEDGERECEIPTS_INFO a on b.PLEDGERECEIPTS_ID = a.PLEDGERECEIPTS_INFO_ID ");
		sql.append("where a.warehouse=? ");
		sql.append("and a.is_available='1' ");
		sql.append("and b.material_code=? and b.batch_no=? and a.dept_id=? and a.examine_state in (1,2,3,5)");
		List list1 = this.getJdbcTemplate().queryForList(sql.toString(), new Object[] { warehouse, materialCode, batchNo,deptId });
		if (value.signum()>0 && list1 != null && list1.size() > 0)
		{ 
			Map m = (Map) list1.get(0);
			Object price ="0";
			if(m.get("PRICE")!=null&&((BigDecimal)m.get("PRICE")).doubleValue()>0)
				price =  m.get("PRICE");
			map.put("PRICE", price);
			map.put("CURRENCY", m.get("CURRENCY")!=null?m.get("CURRENCY"):" ");
			map.put("EKPO_PEINH", m.get("EKPO_PEINH")!=null?m.get("EKPO_PEINH"):"1");
			map.put("HAS_SHIP_QUALITY", value);
		}
		else
		{
			map.put("kucunshu", "0");
			map.put("PRICE", "0");
			map.put("CURRENCY", " ");
			map.put("EKPO_PEINH", "1");
			map.put("HAS_SHIP_QUALITY", "0");
		}

		// 更新进仓物料表
		String sql1 = "update T_PLEDGESHIPS_ITEM a set a.price=?,a.currency=?,a.ekpo_peinh=?,a.quantity=?," + 
		              "a.total = round(? * nvl(?,0)/nvl(?,1),2) where a.PLEDGESHIPS_ITEM_ID=?";
		
		this.getJdbcTemplate().update(sql1, new Object[] { map.get("PRICE"), map.get("CURRENCY"), map.get("EKPO_PEINH"), 
				map.get("HAS_SHIP_QUALITY"),map.get("HAS_SHIP_QUALITY"),
				map.get("PRICE"), map.get("EKPO_PEINH"), shipDetailId });
		
		return map;
		
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
		String sql = " update T_PLEDGESHIPS_ITEM " + "set " + colName + "='" + colValue + "' where PLEDGESHIPS_ITEM_ID = ?";

		getJdbcTemplate().update(sql, new Object[] { shipDetailId });

		if (colName.equals("QUANTITY") || colName.equals("PRICE") || "EKPO_PEINH".equals(colName))
		{
			sql = " update T_PLEDGESHIPS_ITEM set total = round(nvl(quantity,0) * nvl(price,0)/nvl(EKPO_PEINH,1),2) where PLEDGESHIPS_ITEM_ID = ?";
			getJdbcTemplate().update(sql, new Object[] { shipDetailId });
		}
	}
	public void update(String shipsInfoNo,String shipsInfoId) {
		String sql = " update T_PLEDGESHIPS_INFO set PLEDGESHIPS_INFO_NO=? where PLEDGESHIPS_INFO_ID = ?";
		getJdbcTemplate().update(sql, new Object[] { shipsInfoNo, shipsInfoId});
	}
	
	public List<PledgeShipsItem> queryShipMaterialList(String shipId)
	{
		final List<PledgeShipsItem> list = new ArrayList<PledgeShipsItem>();
		String strSql = "select k.material_code,k.material_name,k.material_unit,k.quantity,k.price,k.batch_no,k.currency,k.total from T_PLEDGESHIPS_ITEM k where " + "k.PLEDGESHIPS_ID=? ";

		getJdbcTemplate().query(strSql, new Object[] { shipId }, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				PledgeShipsItem material = new PledgeShipsItem();
				material.setMaterialCode(rs.getString("material_code"));
				material.setMaterialName(rs.getString("material_name"));
				material.setMaterialUnit(rs.getString("material_unit"));
				material.setQuantity(rs.getDouble("quantity"));
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
	


}
