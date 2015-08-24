/*
 * @(#)CustomSingleClear.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-7-16
 *  描　述：创建
 */

/**
 * 
 */
package com.infolion.xdss3.singleClear.dao;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.util.StringUtils;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.singleClear.domain.CustomSingleClear;
import com.infolion.xdss3.singleClear.domain.UnClearCollect;
import com.infolion.xdss3.singleClear.domain.UnClearCustomBill;

/**
 * <pre>
 * (CustomSingleClear),JDBC 操作类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Repository
public class CustomSingleClearJdbcDao extends BaseJdbcDao
{
	/**
	 * JDBC删除客户单清对象下，所有1对多子业务对象数据。
	 * 
	 * @return
	 */
	public void deleteCustomSingleClearUnderOneToManySubObject(String customsclearid)
	{
		if (!StringUtils.isNullBlank(customsclearid))
		{
			String strSql1 = "delete from YUNCLEARCUSTBILL where CUSTOMSCLEARID='" + customsclearid + "'";
			String strSql2 = "delete from YUNCLEARCOLLECT where CUSTOMSCLEARID='" + customsclearid + "'";
			this.getJdbcTemplate().execute(strSql1);
			this.getJdbcTemplate().execute(strSql2);
		}
	}

	/**
	 * 更新单据业务状态。
	 * 
	 * @param suppliersClearId
	 */
	public void updateBusinessstate(String customsclearid, String businessState)
	{
		String strSql1 = "update YCUSTOMSICLEAR set BUSINESSSTATE ='" + businessState + "' where CUSTOMSCLEARID='" + customsclearid + "'";
		this.getJdbcTemplate().execute(strSql1);
	}
	
	/**
	 * 根据业务状态、customertitleid，取得款已经审批完金额(款已清金额)、在途金额(款在途金额)。
	 * 
	 * @param customertitleid
	 * @param businessstates
	 * @return
	 */
	public BigDecimal getSumClearAmount(String customertitleid, String businessstates)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(AMOUNT),0) from (");
		// 供应商单清
		sb.append(" select nvl(sum(NOWCLEARAMOUNT),0) as AMOUNT from YUNCLEARCOLLECT a ,YCUSTOMSICLEAR b ");
		sb.append(" where a.CUSTOMSCLEARID=b.CUSTOMSCLEARID and a.CUSTOMERTITLEID='" + customertitleid + "' and b.BUSINESSSTATE in(" + businessstates + ")");
		sb.append(" )");
		log.debug(" 根据业务状态、customertitleid，取得款已经审批完金额(款已清金额)、在途金额(款在途金额)。:" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	/**
	 * 判断sap手工调汇率，是否有清账
	 * @param customertitleid
	 * @return
	 */
	public boolean getSapCollect(String customertitleid)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" select COUNT(*) from YUNCLEARCOLLECT a ,YCUSTOMSICLEAR b ");
		sb.append(" where a.CUSTOMSCLEARID=b.CUSTOMSCLEARID and a.CUSTOMERTITLEID='" + customertitleid + "' and b.BUSINESSSTATE !='-1'");	
		log.debug(" 根据customertitleid，判断sap手工调汇率，是否有清账:" + sb.toString());
		int list = (int) this.getJdbcTemplate().queryForInt(sb.toString());

		if (list == 0)
			return false;
		else
			return true;
	}
	
	/**
	 * 根据业务状态、customertitleid，取得票已经审批完金额(款已清金额)、在途金额(款在途金额)。
	 * 
	 * @param customertitleid
	 * @param businessstates
	 * @return
	 */
	public BigDecimal getSumClearAmount2(String customertitleid, String businessstates)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(AMOUNT),0) from (");
		// 供应商单清
		sb.append(" select nvl(sum(CLEARAMOUNT),0) as AMOUNT from YUNCLEARCUSTBILL a ,YCUSTOMSICLEAR b ");
		sb.append(" where a.CUSTOMSCLEARID=b.CUSTOMSCLEARID and a.CUSTOMERTITLEID='" + customertitleid + "' and b.BUSINESSSTATE in(" + businessstates + ")");
		sb.append(" )");
		log.debug(" 根据业务状态、customertitleid，取得票已经审批完金额(款已清金额)、在途金额(款在途金额)。:" + sb.toString());
		BigDecimal list = (BigDecimal) this.getJdbcTemplate().queryForObject(sb.toString(), BigDecimal.class);

		if (null == list)
			return new BigDecimal(0);
		else
			return list;
	}
	
	public CustomSingleClear getCustomSingleClearById(String id){
		String sql = "select * from YCUSTOMSICLEAR a where  a.CUSTOMSCLEARID='" + id + "'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		if(rowList.size()==0)return null;
		CustomSingleClear customSingleClear = new CustomSingleClear();
		ExBeanUtils.setBeanValueFromMap(customSingleClear, rowList.get(0));
		
		sql = "select * from YUNCLEARCUSTBILL a where  a.CUSTOMSCLEARID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Set<UnClearCustomBill> unClearCustomBillSet = new HashSet<UnClearCustomBill>();
		for(Map ci:rowList){
			UnClearCustomBill unClearCustomBill = new UnClearCustomBill();
			ExBeanUtils.setBeanValueFromMap(unClearCustomBill, ci);
			unClearCustomBill.setCustomSingleClear(customSingleClear);
			unClearCustomBillSet.add(unClearCustomBill);
		}	
		customSingleClear.setUnClearCustomBill(unClearCustomBillSet);
		
		sql = "select * from YUNCLEARCOLLECT a where  a.CUSTOMSCLEARID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());
		Set<UnClearCollect> unClearCollectSet = new HashSet<UnClearCollect>();
		for(Map ci:rowList){
			UnClearCollect uc = new UnClearCollect();
			ExBeanUtils.setBeanValueFromMap(uc, ci);
			uc.setCustomSingleClear(customSingleClear);
			unClearCollectSet.add(uc);
		}	
		customSingleClear.setUnClearCollect(unClearCollectSet);
		
		sql="select * from YFUNDFLOW a where  a.CUSTOMSCLEARID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());	
		if(null != rowList && rowList.size()!=0){
			FundFlow ff = new FundFlow();
			ExBeanUtils.setBeanValueFromMap(ff,  rowList.get(0));
			ff.setCustomSingleClear(customSingleClear);
			customSingleClear.setFundFlow(ff);
		}
		
		sql="select * from YSETTLESUBJECT a where  a.CUSTOMSCLEARID='" + id + "'";
		rowList = this.getJdbcTemplate().queryForList(sql.toString());	
		if(null != rowList && rowList.size()!=0){
			SettleSubject ss = new SettleSubject();
			ExBeanUtils.setBeanValueFromMap(ss,  rowList.get(0));
			ss.setCustomSingleClear(customSingleClear);
			customSingleClear.setSettleSubject(ss);
		}		
		return customSingleClear;
	}
}
