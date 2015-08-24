package com.infolion.xdss3.payment.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.xdss3.payment.domain.HomePayment;
import com.infolion.xdss3.payment.domain.ImportPayment;
/**
 * @创建作者：邱杰烜
 * @创建日期：2010-09-27
 */
@Repository
public class PaymentJdbcDao extends BaseJdbcDao{
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-27
	 * 根据部门ID去取得YORGANIZATION
	 */
	public List getOrganizationById(String deptId){
		String sql = "SELECT * FROM YORGANIZATION WHERE ORGANIZATIONID = '" + deptId + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-08
     * @param paymentId
     * @param bankIndex 不做删除的银行索引
	 * 删除付款银行、押汇/海外代付银行、结算科目和纯资金
	 */
	public void deleteDocuInfo(String paymentId, String bankIndex){
		String sql1 = "delete from YPAYBANKITEM where paymentid = '" + paymentId + "'";		// 付款银行
		String sql2 = "delete from YDOUCARYBANKITEM where paymentid = '" + paymentId + "'";	// 押汇/海外代付银行
		String sql3 = "delete from YFUNDFLOW where paymentid = '" + paymentId + "'";		// 付款纯资金
		String sql4 = "delete from YSETTLESUBJECT where paymentid = '" + paymentId + "'";	// 付款结算科目
		this.getJdbcTemplate().execute(sql1);
		if (! "2".equals(bankIndex)) {
		    this.getJdbcTemplate().execute(sql2);
		}
		this.getJdbcTemplate().execute(sql3);
		this.getJdbcTemplate().execute(sql4);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-08
	 * 删除结算科目和纯资金
	 */
	public void deleteSettleFund(String paymentId){
		String sql1 = "delete from YSETTLESUBJECT where paymentid = '" + paymentId + "'";		
		String sql2 = "delete from YFUNDFLOW where paymentid = '" + paymentId + "'";	
		this.getJdbcTemplate().execute(sql1);
		this.getJdbcTemplate().execute(sql2);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-12
	 * 根据供应商编号获取供应商信息
	 */
	public List getSupplierByNo(String lifnr){
		String sql = "SELECT * FROM YGETLIFNR WHERE LIFNR = '" + lifnr + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-12
	 * 根据数据字典与值去查找该值对应的文本
	 */
	public List getDomainText(String domName, String domValue){
		String sql = "SELECT * FROM DD07T WHERE DOMNAME = '" + domName + "' AND DOMVALUE_L = '" + domValue + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-14
	 * 根据供应商编号从付款主表中取出该供应商最近付款所使用的银行
	 */
	public List getBankInfoBySupplierNo(String supplier){
		String sql = "SELECT * FROM YPAYMENT WHERE SUPPLIER='" + supplier + "' AND COLLECTBANKID<>' ' ORDER BY CREATETIME DESC";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	public void updatePayment(String paymentid, String businessstate){
		String sql = "update YPAYMENT set BUSINESSSTATE='"+businessstate+"' where PAYMENTID='"+paymentid+"'";
		this.getJdbcTemplate().execute(sql);
	}
	
	/**判断立项号是否是效期内的代垫立项*/
	public boolean isValidCustCreditPro(String projectNo){
		String sql = "select count(*) from YCUSTCREDPROJ a left outer join YCUSTCREDCONF b on a.configid=b.configid where b.credittype='2' " +
				"and to_char(sysdate,'yyyyMMdd') between b.startingtime and b.endtime and a.projectno=?";
		int i = this.getJdbcTemplate().queryForInt(sql, new Object[]{projectNo});
		return i>0;
	}
	
	public Object getPaymentByPaymentId(String paymentId,Class c) throws Exception{
		String sql = "select * from ypayment where paymentId='"+paymentId+"'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql.toString());
		if(rowList.size()>0){
			Object object = c.newInstance();
			ExBeanUtils.setBeanValueFromMap(object, rowList.get(0));
			return object;
		}else{
			return null;
		}
	}
	
	public void updateDate(Object info ,String oldStr,String newStr,String userId) throws Exception{
		String strSql = " update YPAYMENT set MUSTTAKETICKLEDA =?,ARRIVEGOODSDATE=?,REPLACEDATE=? where paymentid = ?";
		String strsqllog = " insert into t_saveDateLog values (?,?,?,?,?,?,?)";
		String s1 = BeanUtils.getProperty(info, "musttaketickleda").replaceAll("-", "");
		String s2 = BeanUtils.getProperty(info, "arrivegoodsdate").replaceAll("-", "");
		String s3 = BeanUtils.getProperty(info, "replacedate").replaceAll("-", "");
		s1 = s1.equals("")?" ":s1;
		s2 = s2.equals("")?" ":s2;
		s3 = s3.equals("")?" ":s3;
		getJdbcTemplate().update(strSql, new Object[] {s1,s2,s3,BeanUtils.getProperty(info, "paymentid")});
		getJdbcTemplate().update(strsqllog, new Object[] { CodeGenerator.getUUID(),BeanUtils.getProperty(info, "paymentid"),"付款",oldStr,newStr,userId,DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE) });
   }
	
	 public void updateYbillPurRebank(String id){
	        String updateSQL ="update YBILLPAYMENTBANK set BUSINESSTYPE='已做账' where paymentid='"+id+"'";
	        this.getJdbcTemplate().execute(updateSQL);
	 }
	 
	 public void updatePurRebank(String id,BigDecimal total){
	        String updateSQL ="update YPAYMENT set accountdate = ' ',voucherdate= ' ',redocaryamount=0,redocaryrate=0,redocaryamount2="+total+"  where paymentid='"+id+"'";
	        this.getJdbcTemplate().execute(updateSQL);
	 }
	 
    public void dealOutToExcel(String sql,String strAuthSql ,final ExcelObject excel){

		//final IntObject i= new IntObject();
		//序号","付款单号","供应商名称","部门","金额","币别","收款银行","审核状态","应收票日","付款方式","创建日期","文本","备注"
		getJdbcTemplate().query(sql+strAuthSql.replace("t.", " a."),new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[18];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("PAYMENTNO");
				values[2] = rs.getString("supplier_text");
				values[3] = rs.getString("dept_id_text");
				values[4] = rs.getString("applyamount");
				values[5] = rs.getString("currency");
				values[6] = rs.getString("collectbankid");
				values[7] = rs.getString("processstate");
				values[8] = rs.getString("musttaketickleda");
				values[9] = rs.getString("ddtext");
				values[10] = rs.getString("createtime");
				values[11] = rs.getString("text");
				values[12] = rs.getString("remark");
				values[13] = rs.getString("project_no");
				values[14] = rs.getString("contract_no");
				values[15] = rs.getString("arrivegoodsdate");
				values[16] = rs.getString("paydate");
				values[17] = rs.getString("draftdate");
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	
	}
	 
}
