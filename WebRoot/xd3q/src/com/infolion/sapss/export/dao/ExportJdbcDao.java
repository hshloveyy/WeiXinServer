package com.infolion.sapss.export.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.export.domain.TExportBillExam;


@Repository
public class ExportJdbcDao extends BaseDao{
	
	/**
	 * 取得最新的EXPORT_BILL_NO单据流水号
	 * @param export_bill_No
	 * @return
	 */
	public String getExportBillNo()
	{
		final List<String> returnFalg = new ArrayList();
		String sql = "select lpad(nvl(count(a.export_bill_No) + 1,1),10,'0') export_bill_No from t_export_bills a";

		getJdbcTemplate().query(sql, new RowCallbackHandler()
		{
			public void processRow(ResultSet rs) throws SQLException
			{
				returnFalg.add(rs.getString("export_bill_No"));
			}
		});

		if (returnFalg.size() == 0)
			return "";
		else
			return returnFalg.get(0);
	}
	
	public void assemble(TExportBillExam obj){
		
		final Set<String> writeNoSet = new HashSet<String>();
		final Set<String> contractNoSet = new HashSet<String>();
		final Set<String> noticeNoSet = new HashSet<String>();
		final Set<String> lcdpdaNoSet = new HashSet<String>();
		
		String strSql = "select * from T_EXPORT_BILL_EXAM_D t where t.EXPORT_BILL_EXAM_ID=?";
		
		getJdbcTemplate().query(strSql,new Object[]{obj.getExportBillExamId()}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				writeNoSet.add(rs.getString("WRITE_NO"));
				contractNoSet.add(rs.getString("CONTRACT_NO"));
				noticeNoSet.add(rs.getString("NOTICE_NO"));
				lcdpdaNoSet.add(rs.getString("LCDPDA_NO"));
		}});
		obj.setWriteNo(setToString(writeNoSet));
		obj.setContractNo(setToString(contractNoSet));
		obj.setExportApplyNo(setToString(noticeNoSet));
		obj.setLcdpdaNo(setToString(lcdpdaNoSet));
	}
	
	private String setToString(Set<String> set){
		String result = "";
		for(Iterator<String> it = set.iterator();it.hasNext();)
			result+=it.next()+",";
		if(result.length()>0) return result.substring(0,result.length()-1);
		return result;
	}
	
	public boolean checkIsExitExportApply(String examBillId,String exportApplyId){
		String sql = "SELECT count(*) FROM T_EXPORT_BILL_EXAM_D T WHERE T.EXPORT_BILL_EXAM_ID='"+examBillId+"' AND T.EXPORT_APPLY_ID='"+exportApplyId+"'";
		int i = getJdbcTemplate().queryForInt(sql);
		return (i>0)?true:false;
	}
	
	public void dealOutToExcel(String sql , final ExcelObject excel){
		getJdbcTemplate().query(sql,new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[17];
				values[0] = rs.getString("BATCH_NO");
				values[1] = rs.getString("EXPORT_DATE");
				values[2] = rs.getString("PRS_NUM");
				values[3] = rs.getString("UNIT");
				values[4] = rs.getString("TOTAL");
				values[5] = rs.getString("CURRENCY");
				values[6] = rs.getString("ADDED_TAX_INV_VALUE");
				values[7] = rs.getString("ADDED_TAX_VALUE");
				values[8] = rs.getString("WRITE_NO");
				values[9] = rs.getString("CATCH_NO");
				values[10] = rs.getString("PORT");
				values[11] = rs.getString("CUSTOME_NAME");
				values[12] = rs.getString("RATE");
				values[13] = rs.getString("RECEIPT_DATE");
				values[14] = rs.getString("DRAWBACK_DATE");
				values[15] = rs.getString("DRAWBACK_END_DATE");
				values[16] = rs.getString("MARK");
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}
	/**
	 * 封装打印所需数据
	 * @param exam
	 */
	public void assemExportBillExamPrint(TExportBillExam exam){
		String sql = "select CONTRACT_NO,LCDPDA_NO,WRITE_NO from t_export_bill_exam_d d where d.export_bill_exam_id=?";
		final Set contractNoSet = new HashSet<String>();
		final Set noSet = new HashSet<String>();
		final Set writeSet = new HashSet<String>();
		getJdbcTemplate().query(sql,new Object[]{exam.getExportBillExamId()}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
                contractNoSet.add(rs.getString("CONTRACT_NO"));
                noSet.add(rs.getString("LCDPDA_NO"));
                writeSet.add(rs.getString("WRITE_NO"));
	    }});
		exam.setWriteNo(assamSet(writeSet));
		exam.setContractNo(assamSet(contractNoSet));
		exam.setLcdpdaNo(assamSet(noSet));
	}
	private String assamSet(Set<String> set){
		String result = "";
		for(String s :set){
			result+=s+",";
		}
		if (result.length()>1) return result.substring(0, result.length()-1);
		return result;
	}
	
	public void updateExportBillExamOfCollect(String invNo, String collectid, String collectno)
	{
		String sql = "update T_EXPORT_BILL_EXAM set collect_id ='" + collectid + "',collect_no ='" + collectno + "' where inv_No ='" + invNo + "'";
		log.debug("更新出单的收款单据信息:" + sql);
		this.getJdbcTemplate().execute(sql);
	}
	
	public void dealOutToExcelDrawBack(String sql , final ExcelObject excel){
		getJdbcTemplate().query(sql,new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[25];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("EXPORT_APPLY_NO");
				values[2] = rs.getString("WRITE_NO");
				values[3] = rs.getString("HDRQ");
				values[4] = rs.getString("ylrh");
				values[5] = "'"+rs.getString("ckrq");
				values[6] = rs.getString("bgdh");
				values[7] = "'"+rs.getString("write_Date");
				values[8] = rs.getString("cjbb");
				values[9] = rs.getString("bgsl");
				values[10] = rs.getString("bgje");
				values[11] = rs.getString("bgdw");
				values[12] = rs.getString("hxje");
				values[13] = rs.getString("shiping_Value");
				values[14] = rs.getString("drawback_Rate");
				values[15] = "'"+rs.getString("drawback_Date");
				values[16] = rs.getString("drawback_Value");
				values[17] = rs.getString("drawback_Real");
				values[18] = rs.getString("skdzr");
				values[19] = rs.getString("zzsfpje");
				values[20] = rs.getString("hhb");
				values[21] = rs.getString("hgsb");
				values[22] = rs.getString("ckka");
				values[23] = rs.getString("mdg");
				values[24] = rs.getString("mark");
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}
	
	
	public void dealOutToExcelIncome(String sql , final ExcelObject excel){
		getJdbcTemplate().query(sql,new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[15];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("PROJECT_NO");
				values[2] = rs.getString("CONTRACT_NO");
				values[3] = rs.getString("INV_NO");
				values[4] = rs.getString("EXPORT_APPLY_NO");
				values[5] = rs.getString("WRITE_NO");
				values[6] = rs.getString("TOTAL");
				values[7] = rs.getString("ACCEPT_DATE");
				values[8] = rs.getString("NEGOTIAL_RATE");
				values[9] = rs.getString("NEGOTIAL_INCOME");
				values[10] = rs.getString("NEGOTIAT_DATE");
				values[11] = rs.getString("CURRENCY");
				values[12] = rs.getString("PAY_BANK");
				values[13] = rs.getString("CNY_TOTAL");
				values[14] = rs.getString("CMD");
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}
	
	public void dealOutToExcelVerificationReceipt(String sql , final ExcelObject excel){
		getJdbcTemplate().query(sql,new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[9];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("EXPORT_APPLY_NO");
				values[2] = rs.getString("WRITE_NO");
				values[3] = rs.getString("RECEIPT_DEPT_NAME");
				values[4] = rs.getString("RECEIPT_MAN");
				values[5] = rs.getString("RECEIPT_DATE");
				values[6] = rs.getString("BACK_DATE");
				values[7] = rs.getString("TAX_NO");
				values[8] = rs.getString("MARK");
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}
	
	/**
	 * 删除出单审单清货表
	 * @param strPurchaseRowsId
	 * @return
	 */
	public void delExportCcollect(String exportbillexamid){
		String strDel = "delete yExportCcollect a where a.exportbillexamid = ?";
		getJdbcTemplate().update(strDel,new Object[]{exportbillexamid});
	}
	
	/**
	 * 返回未清的记录数
	 * @param collectid
	 * @return
	 */
	public int getNotClear(String collectid) {
		String sql = "select nvl(count(*),0) from yovercollect where collectid=? and exportisclear ='0'";
		return this.getJdbcTemplate().queryForInt(sql, new Object[]{collectid});
	}
	
	/**
	 * 返回未清的记录数
	 * @param collectid
	 * @return
	 */
	public Long getSumClearAmount(String overcollectid, String exportbillexamid ) {
		String sql = "select nvl(sum(t.clearamount),0) from yexportccollect t where t.overcollectid = ? and t.exportbillexamid != ?";
		return this.getJdbcTemplate().queryForLong(sql, new Object[]{overcollectid,exportbillexamid});
	}
	
	/**
	 * 得到五联单的部门信息
	 * @param strExportApplyId
	 * @return
	 */
	public String getExportDeptId(String strExportApplyId)
	{
		final List<String> returnFalg = new ArrayList();
		
		if (strExportApplyId != null && !"".equals(strExportApplyId)){
		
			String sql = "select a.dept_id from t_export_apply a where a.export_apply_id = '" + strExportApplyId + "'";
	
			getJdbcTemplate().query(sql, new RowCallbackHandler()
			{
				public void processRow(ResultSet rs) throws SQLException
				{
					returnFalg.add(rs.getString("dept_id"));
				}
			});
		}

		if (returnFalg.size() == 0)
			return "";
		else
			return returnFalg.get(0);
	}
	
	public boolean isExistInvNo(String billID,String invNo){
		String sql = "select count(*) from t_export_bill_exam where export_bill_exam_id <>'"+billID+"' and inv_no='"+invNo+"' and is_available='1'";
		int i = getJdbcTemplate().queryForInt(sql);
		if(i>0) return true;
		else return false;
	}
}
