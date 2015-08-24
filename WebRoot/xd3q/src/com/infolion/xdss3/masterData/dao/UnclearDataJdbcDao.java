package com.infolion.xdss3.masterData.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.CodeGenerator;
import com.infolion.xdss3.masterData.domain.CashFlowItem;
import com.infolion.xdss3.masterData.domain.CertificateNo;
import com.infolion.xdss3.masterData.domain.CostCenter;
import com.infolion.xdss3.masterData.domain.Hkont;

@Repository
public class UnclearDataJdbcDao extends BaseJdbcDao {

	/**
	 * 根据输出凭证更新客户是否结清状态
	 * 
	 * @param certificateNoList
	 */
	public void updateUnclearFlagForCustomer(
			List<CertificateNo> certificateNoList) {
		for (int i = 0; i < certificateNoList.size(); i++) {
			// 更新抬头表
			String updateSql = "update ycustomertitle set iscleared = '1' "
					+ "where BUKRS = '" + certificateNoList.get(i).getBukrs()
					+ "' and GJAHR = '" + certificateNoList.get(i).getGjahr()
					+ "' and BELNR = '" + certificateNoList.get(i).getBelnr()
					+ "'";

			this.getJdbcTemplate().execute(updateSql);

			// 更新行项目表
			updateSql = "update ycustomerdetail set iscleared = '1' "
					+ "where INVOICE in (select INVOICE from ycustomertitle where BUKRS ='"
					+ certificateNoList.get(i).getBukrs() + "' and GJAHR = '"
					+ certificateNoList.get(i).getGjahr() + "' and BELNR = '"
					+ certificateNoList.get(i).getBelnr()
					+ "' and replace(INVOICE, ' ', '') is not null )";
			this.getJdbcTemplate().execute(updateSql);
		}
	}

	/**
	 * 根据输出凭证更新供应商是否结清状态
	 * 
	 * @param certificateNoList
	 */
	public void updateUnclearFlagForVendor(List<CertificateNo> certificateNoList) {
		for (int i = 0; i < certificateNoList.size(); i++) {
			// 更新抬头表
			String updateSql = "update yvendortitle set iscleared = '1' "
					+ "where BUKRS = '" + certificateNoList.get(i).getBukrs()
					+ "' and GJAHR = '" + certificateNoList.get(i).getGjahr()
					+ "' and BELNR = '" + certificateNoList.get(i).getBelnr()
					+ "'";

			this.getJdbcTemplate().execute(updateSql);

			// 更新行项目表
			String selectSql = "select INVOICE, GJAHR from yvendortitle where BUKRS = '"
					+ certificateNoList.get(i).getBukrs()
					+ "' and GJAHR = '"
					+ certificateNoList.get(i).getGjahr()
					+ "' and BELNR = '"
					+ certificateNoList.get(i).getBelnr()
					+ "' and replace(INVOICE, ' ', '') is not null";
			List<Map<String, String>> list = this.getJdbcTemplate()
					.queryForList(selectSql);

			if (list != null && list.size() > 0) {
				updateSql = "update yvendordetail set iscleared = '1' "
						+ "where INVOICE = '" + list.get(0).get("INVOICE")
						+ "' and GJAHR = '" + list.get(0).get("GJAHR") + "'";
				this.getJdbcTemplate().execute(updateSql);
			}
		}
	}

	/**
	 * 判断未清客户抬头数据是否已经存在
	 * 
	 * @param bukrs 	公司代码
	 * @param belnr		会计凭证号码
	 * @param gjahr		会计年度
	 * @return
	 */
	public boolean isCustomerTitleExist(String bukrs, String belnr, String gjahr, String buzei) {
		String sql = "select count(*) from YCUSTOMERTITLE where BUKRS = '" + bukrs 
					+ "' and BELNR = '" + belnr 
					+ "' and GJAHR = '" + gjahr
					+ "' and BUZEI = '" + buzei + "'";

		int icount = this.getJdbcTemplate().queryForInt(sql);

		return (icount == 0) ? false : true;
	}
	
	/**
	 * 判断未清客户明细数据是否存在
	 * 
	 * @param invoice
	 *            发票号
	 * @return
	 */
	public boolean isCustomerDetailExist(String invoice) {
		String sql = "select count(*) from YCUSTOMERDETAIL where INVOICE = '"
				+ invoice + "'";
		int icount = this.getJdbcTemplate().queryForInt(sql);
		return (icount == 0) ? false : true;
	}

	/**
	 * 判断未清供应商抬头数据是否已经存在
	 * 
	 * @param bukrs 	公司代码
	 * @param belnr		会计凭证号码
	 * @param gjahr		会计年度
	 * @return
	 */
	public boolean isVendorTitleExist(String bukrs, String belnr, String gjahr, String buzei) {
		String sql = "select count(*) from YVENDORTITLE where BUKRS = '" + bukrs 
					+ "' and BELNR = '" + belnr 
					+ "' and GJAHR = '" + gjahr
					+ "' and BUZEI = '" + buzei + "'";

		int icount = this.getJdbcTemplate().queryForInt(sql);

		return (icount == 0) ? false : true;
	}

	/**
	 * 判断未清供应商明细数据是否已经存在
	 * 
	 * @param invoice
	 * @param gjahr
	 * @return
	 */
	public boolean isSupplierDetailExist(String invoice, String gjahr) {
		String sql = "select count(*) from YVENDORTITLE where INVOICE = '"
				+ invoice + "' and GJAHR = '" + gjahr
				+ "'";

		int icount = this.getJdbcTemplate().queryForInt(sql);

		return (icount == 0) ? false : true;
	}
}
