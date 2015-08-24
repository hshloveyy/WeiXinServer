/*
 * @(#)VatDetailHibernateDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年12月12日 15点58分45秒
 *  描　述：创建
 */
package com.infolion.xdss3.vatdetail.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.xdss3.cashFlow.domain.CashFlow;
import com.infolion.xdss3.vatdetail.domain.VatDetail;

/**
 * <pre>
 *  期初已到税票未进仓(税额)JDBC操作类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 洪俊杰
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Repository
public class VatDetailJdbcDao extends BaseJdbcDao {
    
    
    /**
     * 返回所有业务范围信息
     * 
     * @return
     */
    public void delVatDetail(String userid) {
        this.getJdbcTemplate().execute("DELETE FROM yvatdetail WHERE userid = '"+userid+"'");
    }
    
    /**
     * 返回所有业务范围信息
     * 
     * @return
     */
    public List<Map> getAllGsber() {
        return this.getJdbcTemplate().queryForList("SELECT DISTINCT * FROM YDEPTINFO");
    }
    
    /**
     * 返回所有物料信息
     * 
     * @return
     */
    public List<Map> getAllBm_material_group() {
        return this.getJdbcTemplate().queryForList("SELECT DISTINCT * FROM BM_MATERIAL_GROUP");
    }
    
 // 批量插入,  
	 public void saveOrUpdateAll(final List<VatDetail> list) {  
	         this.getJdbcTemplate().batchUpdate(  
	                "INSERT INTO YVATDETAIL  (MANDT,   TID,   DEPARTMENT,   AUFNR,   BELNR,   TAX,   BUDAT,   MATNR,"+
	                "MATERAILNAME,   MATNRGROUP,   MATGROUPNAME,   TAXRATE,   TAXCODE,   VOUCHERDATE,   RECEAMOUNT,   SUMMARY,   "+
	                "USERID,   FLAG,   BUZEI,   SAPTYPE )"+
	                "VALUES  (?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,"+
	                		" ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,"+	                		
	                		" ?,   ?,   ?,   ?)",  
	                new BatchPreparedStatementSetter() { 	                    
	                     public void setValues(PreparedStatement ps, int i)  
	                             throws SQLException {  
	                         ps.setString(1, list.get(i).getClient());  
	                         ps.setString(2, list.get(i).getTid());  
	                         ps.setString(3, list.get(i).getDepartment());  
	                         ps.setString(4, list.get(i).getAufnr());  
	                         ps.setString(5, list.get(i).getBelnr()); 
	                         ps.setFloat(6, list.get(i).getTax().floatValue());	                        
	                         ps.setString(7, list.get(i).getBudat());  
	                         ps.setString(8, list.get(i).getMatnr());  
	                         ps.setString(9, list.get(i).getMaterailname());  
	                         ps.setString(10, list.get(i).getMatnrgroup());  
	                         ps.setString(11, list.get(i).getMatgroupname());  
	                         ps.setString(12, list.get(i).getTaxrate());  
	                         ps.setString(13, list.get(i).getTaxcode());  
	                         ps.setString(14, list.get(i).getVoucherdate());  
	                         ps.setString(15, list.get(i).getReceamount());  
	                         ps.setString(16, list.get(i).getSummary());  
	                         ps.setString(17, list.get(i).getUserid());  
	                         ps.setString(18, list.get(i).getFlag());  
	                         ps.setString(19, list.get(i).getBuzei());  
	                         ps.setString(20, list.get(i).getSaptype());  
	                       
	                     }  
	                     public int getBatchSize() {  
	                         return list.size();  
	                    }  
	                 });  
	         
	  }
    
}