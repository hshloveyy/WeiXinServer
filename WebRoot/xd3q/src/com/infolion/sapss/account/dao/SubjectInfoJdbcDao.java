package com.infolion.sapss.account.dao;

import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.sapss.account.web.SubjectAdditionalVO;
import com.infolion.sapss.account.web.SubjectFinalVO;

@Repository
public class SubjectInfoJdbcDao extends BaseDao {

	public int saveAdditionalInfo(SubjectAdditionalVO info) {
		StringBuffer sb = new StringBuffer();
		sb.append("update t_subject_info set ");
		sb.append("is_bs=?,");
		sb.append("is_is=?,");
		sb.append("CURRENCY=?,");
		sb.append("IS_ONLY_CNY=?,");
		sb.append("TAX_TYPE=?,");
		sb.append("IS_ALLOW_NOTAX=?,");
		sb.append("CONTROL_TYPE=?,");
		sb.append("IS_NOT_CLEAR=?,");
		sb.append("IS_SHOW_ROWS=?,");
		sb.append("ORDER_NO=?,");
		sb.append("IS_ONLY_AUTO=?,");
		sb.append("IS_CASH_RELATED=?,");
		sb.append("IS_NEW=? ");
		sb.append("where subject_ID=?");
		return this.getJdbcTemplate().update(
				sb.toString(),
				new Object[] { info.getIsBS(), info.getIsIS(),
						info.getCurrency(), info.getIsOnlyCNY(),
						info.getTaxType(), info.getIsAllowNoTax(),
						info.getControlType(), info.getIsNotClear(),
						info.getIsShowRows(), info.getOrderNO(),
						info.getIsOnlyAuto(), info.getIsCashRelated(),
						info.getIsNew(), info.getSubjectID() });
	}

	public int saveFinalInfo(SubjectFinalVO info) {
		StringBuffer sb = new StringBuffer();
		sb.append("update t_subject_info set ");
		sb.append("COL_STATE_GROUP=?,");
		sb.append("PLAN_LAYER=? ");
		sb.append("where subject_ID=?");
		return this.getJdbcTemplate().update(
				sb.toString(),
				new Object[] { info.getColStateGroup(), info.getPlanLayer(),
						info.getSubjectID() });
	}
}
