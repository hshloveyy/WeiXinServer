/*
 * @(#)VendorTitleJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-6-23
 *  描　述：创建
 */

package com.infolion.xdss3.masterData.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.util.StringUtils;
import com.infolion.xdss3.Constants;
import com.infolion.xdss3.Constants.cleared;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.masterData.domain.VendorTitle;
import com.infolion.xdss3.masterData.domain.VendorTitleOthers;
import com.infolion.xdss3.payment.domain.ClearVoucherItemStruct;

/**
 * <pre>
 * 未清供应商数据抬头(VendorTitle)JDBC
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
public class VendorTitleOthersJdbcDao extends BaseJdbcDao
{

	/**
	 * 根据未清供应商数据抬头ID(可以多个逗号隔开),取得供应商下未清发票数据集合
	 * 
	 * @param user
	 * @return
	 */
	public List<VendorTitleOthers> getVendorTitleList(String vendortitleids)
	{
		List<VendorTitleOthers> vendorTitles = new ArrayList<VendorTitleOthers>();
		String ids = vendortitleids.replaceAll(",", "','");
		String sql = "select * from YVENDORTITLEOTHERS where VENDORTITLEID in('" + ids + "')";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		for (Map map : rowList)
		{
			VendorTitleOthers vendorTitle = new VendorTitleOthers();
			ExBeanUtils.setBeanValueFromMap(vendorTitle, map);
			vendorTitles.add(vendorTitle);
		}

		return vendorTitles;
	}

	
	
	/**
	 * 更新未清供应商抬头是否已结清标志:
	 * 
	 * @param vendortitleid
	 * @param isCleared
	 */
	public void updateVendorTitleIsCleared(String vendortitleid, String isCleared)
	{
		String sql = "update YVENDORTITLEOTHERS set ISCLEARED ='" + isCleared + "' where VENDORTITLEID ='" + vendortitleid + "'";
		log.debug("更新未清供应商抬头是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 更新未清供应商抬头是否已结清标志:
	 * 
	 * @param vendortitleidsSqlIn
	 * @param isCleared
	 */
	public void updateIsCleared(String vendortitleidsSqlIn, String isCleared)
	{
		String sql = "update YVENDORTITLEOTHERS set ISCLEARED ='" + isCleared + "' where VENDORTITLEID in(" + vendortitleidsSqlIn + ")";
		log.debug("更新未清供应商抬头是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
	}

	

	
	/**
	 * 根据供应商编号、科目，取得供应商下未清发票数据集合
	 * 
	 * @param supplier
	 * @param shkzg
	 *            借方/贷方标识
	 */
	public List<VendorTitleOthers> getVendorTitleListBySupplier(String supplier, String subject, String shkzg)
	{
		List<VendorTitleOthers> vendorTitles = new ArrayList<VendorTitleOthers>();
		String sql = "select * from YVENDORTITLEOTHERS where LIFNR ='" + supplier + "' and SAKNR='" + subject + "' and ISCLEARED='" + Constants.cleared.notCleared + "'";
		if (!StringUtils.isNullBlank(shkzg))
		{
			sql = sql + " and SHKZ='" + shkzg + "'";
		}
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		log.debug("根据供应商编号、科目，取得供应商下未清发票数据集合:" + sql);
		for (Map map : rowList)
		{
			VendorTitleOthers vendorTitle = new VendorTitleOthers();
			ExBeanUtils.setBeanValueFromMap(vendorTitle, map);
			vendorTitles.add(vendorTitle);
		}

		return vendorTitles;
	}

	/**
	 * 根据供应商编号,科目，货币文本，公司代码,取得供应商下未清发票数据集合
	 * @param supplier
	 * @param subject
	 * 
	 * @param currencytext
	 * @param companyno
	 * @param shkzg
	 * @return
	 */
	public List<VendorTitleOthers> getVendorTitleListBySupplier(String supplier, String subject,String currencytext ,String companyno,String depid, String shkzg)
	{
		List<VendorTitleOthers> vendorTitles = new ArrayList<VendorTitleOthers>();
		String sql = "select * from YVENDORTITLEOTHERS where LIFNR ='" + supplier + "' and SAKNR='" + subject + "' and WAERS='" + currencytext + "' and BUKRS='" + companyno +  "' and GSBER='" + depid +  "' and ISCLEARED='" + Constants.cleared.notCleared + "'  ";
//		String sql = "select * from YVENDORTITLE where LIFNR ='" + supplier + "' and SAKNR='" + subject + "' and WAERS='" + currencytext + "' and BUKRS='" + companyno +   "' and ISCLEARED='" + Constants.cleared.notCleared + "'";
		if (!StringUtils.isNullBlank(shkzg))
		{
			sql = sql + " and SHKZ='" + shkzg + "'";
		}
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		log.debug("根据供应商编号、科目，货币文本，公司代码，取得供应商下未清发票数据集合:" + sql);
		for (Map map : rowList)
		{
			VendorTitleOthers vendorTitle = new VendorTitleOthers();
			ExBeanUtils.setBeanValueFromMap(vendorTitle, map);
			vendorTitles.add(vendorTitle);
		}

		return vendorTitles;
	}
	
	
	
	
	




	

	

	
	
	


	
	/**
	 * 
	 * @param vendortitleid
	 * @return
	 */
	public VendorTitleOthers getByVendortitleid(String vendortitleid)
	{
		String hql = "select * from YVENDORTITLEOTHERS where VENDORTITLEID = '" + vendortitleid + "'";
		
		List<Map> rowList = this.getJdbcTemplate().queryForList(hql);
		log.debug("根据vendortitleid取得:" + hql);
		VendorTitleOthers vendorTitle = new VendorTitleOthers();
		if (rowList.size() > 0){
			ExBeanUtils.setBeanValueFromMap(vendorTitle, rowList.get(0));
		}
		return vendorTitle;
	}
	
	
	
	/**
	 * 
	 * @param vendortitleid
	 * @return
	 */
	public VendorTitleOthers getByVoucherNo(String companycode,String voucherno,String fiyear,String rownumber)
	{
		String hql = "select * from YVENDORTITLEOTHERS where bukrs = '" + companycode + "' and belnr = '" + voucherno + "' and gjahr = '" + fiyear + "' and buzei = '" + rownumber + "'";

		List<Map> rowList = this.getJdbcTemplate().queryForList(hql);
		log.debug("根据vendortitleno取得:" + hql);
		VendorTitleOthers vendorTitle = new VendorTitleOthers();
		if (rowList.size() > 0){
			ExBeanUtils.setBeanValueFromMap(vendorTitle, rowList.get(0));
		}
		return vendorTitle;
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @修改作者：2010-10-20
	 * 根据供应商编号判断该供应商数据是否已清
	 */
	public boolean isVendorTitleClear(VendorTitleOthers vendorTitle){
		String sql1 = "SELECT COUNT(*) FROM YREFUNDMENT a WHERE a.REFUNDMENTID IN (SELECT b.BUSINESSID FROM YVOUCHER b WHERE b.VOUCHERNO='" +
					 vendorTitle.getBelnr() + 
		 			 "')";
		String sql2 = "SELECT BUSINESSID FROM YVOUCHER WHERE VOUCHERNO='" + vendorTitle.getBelnr() + 
					  "' AND COMPANYCODE='" + vendorTitle.getBukrs() + 
					  "' AND FIYEAR='" + vendorTitle.getGjahr() + "'";
		if(this.getJdbcTemplate().queryForInt(sql1) > 0){						// 若供应商退款凭证已生成
			return true;
		}else{																	// 若在单清与票清楚已经生成凭证
			String sql3 = "SELECT COUNT(*) FROM YSUPPLIERSICLEAR WHERE SUPPLIERSCLEARID IN (" + sql2 + ")";
			String sql4 = "SELECT COUNT(*) FROM YBILLCLEAR WHERE BILLCLEARID IN (" + sql2 + ")";
			if(this.getJdbcTemplate().queryForInt(sql3)>0 || this.getJdbcTemplate().queryForInt(sql4)>0){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 
	 * @param bukrs
	 * @param gsber
	 * @param lifnr
	 * @param waers
	 * @param saknr
	 * @param augdt_to
	 * @param augdt_from
	 * @return
	 */
	public List<VendorTitleOthers> getVendorTitle(String bukrs,String gsber,String lifnr,String waers,String saknr,String augdt_to,String augdt_from){
		StringBuffer sql = new StringBuffer(" select * from YVENDORTITLEOTHERS a where 1=1 ");
		if (!StringUtils.isNullBlank(bukrs)) {
			sql.append("and a.bukrs='"+bukrs+"'");
		}
		if (!StringUtils.isNullBlank(gsber)) {
			sql.append("and a.gsber='"+gsber+"'");
		}
		if (!StringUtils.isNullBlank(lifnr)) {
			sql.append("and a.lifnr='"+lifnr+"'");
		}
		if (!StringUtils.isNullBlank(waers)) {
			sql.append("and a.waers='"+waers+"'");
		}
		if (!StringUtils.isNullBlank(saknr)) {
			sql.append("and a.saknr='"+saknr+"'");
		}
		if (!StringUtils.isNullBlank(augdt_to) && !StringUtils.isNullBlank(augdt_from)) {			
			sql.append("AND a.budat BETWEEN '" + augdt_to + "' AND '"
					+ augdt_from + "'");
		}else if(!StringUtils.isNullBlank(augdt_to)){
			sql.append("AND a.budat > '" + augdt_to + "'");
		}else if(!StringUtils.isNullBlank(augdt_from)){
			sql.append("AND a.budat < '" + augdt_from + "'");
		}
		List<VendorTitleOthers> vendorTitles = new ArrayList<VendorTitleOthers>();		
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		for (Map map : rowList)
		{
			VendorTitleOthers vendorTitle = new VendorTitleOthers();
			ExBeanUtils.setBeanValueFromMap(vendorTitle, map);
			vendorTitles.add(vendorTitle);
		}
		return vendorTitles;
	}
	/**
	 * 更新冲销的状态
	 * @param belnr
	 * @param burks
	 * @param gjahr
	 * @param stblg
	 * @param stjah
	 * @param isCleared
	 */
	public void updateVendorTitleIsClearedByBelnr(String belnr,String burks,String gjahr,String stblg,String stjah, String isCleared)
	{
		String sql = "update YVENDORTITLEOTHERS set ISCLEARED ='" + isCleared + "' , stblg ='" + stblg + "' , stjah ='" + stjah + "'  where belnr ='" + belnr + "' and burks ='" +burks+ "' and gjahr ='" +gjahr+  "'";
		log.debug("更新未清供应商抬头数据上是否已结清标志:" + sql);
		this.getJdbcTemplate().execute(sql);
	}
}
