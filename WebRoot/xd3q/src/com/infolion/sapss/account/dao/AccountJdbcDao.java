package com.infolion.sapss.account.dao;

import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.sapss.account.domain.TAccountInfo;
import com.infolion.sapss.account.web.AccountFundVO;
import com.infolion.sapss.account.web.AccountGeneralVO;
import com.infolion.sapss.account.web.AccountMaintainVO;

@Repository
public class AccountJdbcDao extends BaseDao {
	public int updateAccountInfo(TAccountInfo info) {
		StringBuffer sb = new StringBuffer();
		sb.append("update t_account_info set ");
		sb.append("tel=?,");
		sb.append("email=?,");
		sb.append("COMPANY_CODE=?,");
		sb.append("COUNTRY=?,");
		sb.append("HEADOFFICE_NAME=?,");
		sb.append("PROVINCE=?,");
		sb.append("CITY=?,");
		sb.append("STREET=?,");
		sb.append("BANK_NAME=?,");
		sb.append("ACCOUNT_CODE=?,");
		sb.append("CURRENCY=?,");
		sb.append("IS_PVA=?,");
		sb.append("BALANCE=?,");
		sb.append("PURPOSE=?,");
		sb.append("MEMO=?");
		sb.append("where account_ID=?");
		return this.getJdbcTemplate()
				.update(
						sb.toString(),
						new Object[] { info.getTel(), info.getEmail(),
								info.getCompanyCode(), info.getCountry(),
								info.getHeadOfficeName(), info.getProvince(),
								info.getCity(), info.getStreet(),
								info.getBankName(), info.getAccountCode(),
								info.getCurrency(), info.getIsPVA(),
								info.getBalance(), info.getPurpose(), info.getMemo(), info.getAccountID()});
	}

	public int saveFundInfo(AccountFundVO info) {
		StringBuffer sb = new StringBuffer();
		sb.append("update t_account_info set ");
		sb.append("headOffice_NO=?,");
		sb.append("bank_code=?,");
		sb.append("headOffice_code=?,");
		sb.append("account_flag=?");
		sb.append(" where account_id=?");
		return this.getJdbcTemplate().update(
				sb.toString(),
				new Object[] { info.getHeadOfficeNO(), info.getBankCode(),
						info.getHeadOfficeCode(), info.getAccountFlag(),
						info.getAccountID() });
	}

	public int saveGeneralInfo(AccountGeneralVO info) {
		StringBuffer sb = new StringBuffer();
		sb.append("update t_account_info set ");
		sb.append("subject_NO=?,");
		sb.append("short_Description=?,");
		sb.append("long_Description=?");
		sb.append(" where account_id=?");
		return this.getJdbcTemplate().update(
				sb.toString(),
				new Object[] { info.getSubjectNO(), info.getShortDescription(),
						info.getLongDescription(), info.getAccountID() });
	}
	
	public int saveMaintainInfo(AccountMaintainVO info) {
		StringBuffer sb = new StringBuffer();
		sb.append("update t_account_info set ");
		sb.append("account_title=?,");
		sb.append("balance_maintain=?,");
		sb.append("cashier_account=?");
		sb.append(" where account_id=?");
		return this.getJdbcTemplate().update(
				sb.toString(),
				new Object[] { info.getAccountTitle(), info.getBalanceMaintain(),
						info.getCashierAccount(), info.getAccountID() });
	}
}
