
package com.infolion.xdss3.masterData.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.infolion.platform.util.CodeGenerator;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.xdss3.masterData.domain.Bseg;


@Repository
public class BsegJdbcDao extends BaseJdbcDao{
    public void deleteAll(){
        
    }  
    
    /**
     * 根据公司代码删除YGETBSEG中的相关数据
     */
    public void deleteCompanys(String bukrs, int gjahr){
        String sql = "DELETE FROM YGETBSEG WHERE BUKRS='" + bukrs + "' AND GJAHR='" + gjahr + "'";
        this.getJdbcTemplate().execute(sql);
    }

    /**
     * 根据得所有的公司代码
     */
    public List<Map<String, String>> getAllCompanyCodes(){
        String sql = "select ID from BM_COMPANY_CODE ORDER BY ID";
        return this.getJdbcTemplate().queryForList(sql);
    }
    
    /**
     * 判断当前的凭证数据是否已存在外围数据库里
     */
    public boolean isBsegExists(String bukrs, String belnr, String gjahr, String buzei){
        String sql = "select count(*) from YGETBSEG where BUKRS = '" + bukrs + "' and BELNR = '" + belnr + "' and GJAHR = '" + gjahr
                + "' and BUZEI = '" + buzei + "'";
        int icount = this.getJdbcTemplate().queryForInt(sql);
        return (icount == 0) ? false : true;
    }
    
    /**
     * 根据月份找到该客户的月份范围的金额合计（如取系统时间的前6个月到3个月的合计）
     * @param from 系统时间的前6个月如-6
     * @param to   系统时间的前3个月如-3
     * @param date   分析时间
     * @return
     */
    public List<Bseg> getSumAmountByCustomer(String from ,String to,String date){
    	List<Bseg> bsegs=new ArrayList<Bseg>();    	
    	String sql="SELECT ge.kunnr,ge.hkont,ge.bukrs,ge.gsber,ge.shkzg ,nvl(sum(ge.wrbtr),0) as wrbtr,nvl(sum(ge.dmbtr),0) as dmbtr " +
		" FROM YGETBSEG ge where trim(ge.kunnr) is not null  " + 
		"  and (ge.augdt ='0000-00-00' or ge.augdt> to_char((to_date('"+date+"', 'yyyymmdd') - 0), 'yyyy-mm-dd')) " +
		" and ((trim(ge.zfbdt) !='0000-00-00' and  zfbdt >= to_char((to_date('"+date+"','yyyymmdd')-"+from+"),'yyyy-mm-dd') and zfbdt < to_char((to_date('"+date+"','yyyymmdd')-"+to+"),'yyyy-mm-dd') ) " +
		" or (trim(ge.zfbdt) ='0000-00-00' and  BUDAT >= to_char((to_date('"+date+"','yyyymmdd')-"+from+"),'yyyy-mm-dd') and BUDAT < to_char((to_date('"+date+"','yyyymmdd')-"+to+"),'yyyy-mm-dd') ) " +
		" )group by ge.kunnr,ge.hkont,ge.bukrs,ge.gsber,ge.shkzg ";
    	
    	List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		log.debug("根据月份找到该客户的月份范围的金额合计数据集合:" + sql);
		for (Map map : rowList){
			Bseg bseg =new Bseg();
			ExBeanUtils.setBeanValueFromMap(bseg, map);
			bsegs.add(bseg);
		}
    	return bsegs;
    }
   
    
    /**
     * 根据月份找到该客户的月份范围的金额合计（如取系统时间的前6个月到3个月的合计）
     * @param from 系统时间的前6个月如-6
     * @param to   系统时间的前3个月如-3
     * @param date   分析时间    
    * @param companyno 公司代码
    * @param depid 业务范围   
    * @param customerNo 客户NO
    * @param subjectcode 总账科目代码
    * @param  businessstate 业务类型
    * @return
    */
    public List<Bseg> getSumAmountByCustomer(String from ,String to,String date,String companyno,String depid,String customerNo,String subjectcode,String businessstate){
    	List<Bseg> bsegs=new ArrayList<Bseg>();    	
    	String sql2="SELECT ge.kunnr,ge.hkont,ge.bukrs,ge.gsber,ge.shkzg ,nvl(sum(ge.wrbtr),0) as wrbtr,nvl(sum(ge.dmbtr),0) as dmbtr " ;
    	String sql3="SELECT ge.kunnr,ge.hkont,ge.bukrs,ge.gsber,ge.shkzg ,ge.vbeltype,nvl(sum(ge.wrbtr),0) as wrbtr,nvl(sum(ge.dmbtr),0) as dmbtr " ;
    	String sql= " FROM YGETBSEG ge where  ge.hkont like '"+ subjectcode+"' "  ;
    	if(!StringUtils.isNullBlank(companyno)){
    		sql =sql + " and ge.bukrs in ("+ companyno +") ";
    	}
    	if(!StringUtils.isNullBlank(depid)){    		
    		sql =sql + " and  ge.gsber in ("+ depid +") ";
    	}    	
    	if(!StringUtils.isNullBlank(businessstate)){
    		sql =sql + " and  ge.vbeltype in ("+ businessstate +") ";
    	}  
    	if(!StringUtils.isNullBlank(customerNo)){
    		sql =sql + " and  ge.kunnr in ("+ customerNo +") ";
    	}
    	sql =sql +  "  and (ge.augdt ='0000-00-00' or ge.augdt> to_char((to_date('"+date+"', 'yyyymmdd') - 0), 'yyyy-mm-dd')) " +
		" and ((trim(ge.zfbdt) !='0000-00-00' and  ge.zfbdt >= to_char((to_date('"+date+"','yyyymmdd')-"+from+"),'yyyy-mm-dd') and ge.zfbdt < to_char((to_date('"+date+"','yyyymmdd')-"+to+"),'yyyy-mm-dd') ) " +
		" or (trim(ge.zfbdt) ='0000-00-00' and  ge.BUDAT >= to_char((to_date('"+date+"','yyyymmdd')-"+from+"),'yyyy-mm-dd') and ge.BUDAT < to_char((to_date('"+date+"','yyyymmdd')-"+to+"),'yyyy-mm-dd') ) " +
		" )";
    	String sql4 =" group by ge.kunnr,ge.hkont,ge.bukrs,ge.gsber,ge.shkzg ";
    	String sql5 =" group by ge.kunnr,ge.hkont,ge.bukrs,ge.gsber,ge.shkzg ,ge.vbltype";
    	if(!StringUtils.isNullBlank(businessstate)){
    		sql = sql3+ sql + sql5;
    	}else{
    		sql = sql2+ sql + sql4;
    	}
    	List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		log.debug("根据月份找到该客户的月份范围的金额合计数据集合:" + sql);
		for (Map map : rowList){
			Bseg bseg =new Bseg();
			ExBeanUtils.setBeanValueFromMap(bseg, map);
			bsegs.add(bseg);
		}
    	return bsegs;
    }  
    
    /**
     * 根据月份找到该客户的月份范围的所有数据（如取系统时间的前6个月到3个月的合计）
     * @param from 系统时间的前6个月如-6
     * @param to   系统时间的前3个月如-3
     * @param date   分析时间    
    * @param companyno 公司代码 多个客户逗号分开如'12222','55666'
    * @param depid 业务范围    多个客户逗号分开如'12222','55666'
    * @param customerNo 客户NO 多个客户逗号分开如'12222','55666'
    * @param subjectcode 总账科目代码
    * @param  businessstate 业务类型 多个客户逗号分开如'12222','55666'
    * @return
    */
    public List<Bseg> getBsegByCustomer(String from ,String to,String date,String companyno,String depid,String customerNo,String subjectcode,String businessstate){
    	List<Bseg> bsegs=new ArrayList<Bseg>();    	
    	String sql="SELECT * " +
		" FROM YGETBSEG ge where  ge.hkont like '"+ subjectcode+"' "  ;
    	if(!StringUtils.isNullBlank(companyno)){
    		sql =sql + " and ge.bukrs in ("+ companyno +") ";
    	}
    	if(!StringUtils.isNullBlank(depid)){
    		sql =sql + " and  ge.gsber in ("+ depid +") ";
    	}    	
    	if(!StringUtils.isNullBlank(businessstate)){
    		sql =sql + " and  ge.vbeltype in ("+ businessstate +") ";
    	}  
    	if(!StringUtils.isNullBlank(customerNo)){
    		sql =sql + " and  ge.kunnr in ("+ customerNo +") ";
    	}
    	sql =sql +  "  and (ge.augdt ='0000-00-00' or ge.augdt> to_char((to_date('"+date+"', 'yyyymmdd') - 0), 'yyyy-mm-dd')) " +
		" and ((trim(ge.zfbdt) !='0000-00-00' and  ge.zfbdt >= to_char((to_date('"+date+"','yyyymmdd')-"+from+"),'yyyy-mm-dd') and ge.zfbdt < to_char((to_date('"+date+"','yyyymmdd')-"+to+"),'yyyy-mm-dd') ) " +
		" or (trim(ge.zfbdt) ='0000-00-00' and  ge.BUDAT >= to_char((to_date('"+date+"','yyyymmdd')-"+from+"),'yyyy-mm-dd') and ge.BUDAT < to_char((to_date('"+date+"','yyyymmdd')-"+to+"),'yyyy-mm-dd') ) " +
		" ) ";
    	
    	List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		log.debug("根据月份找到该客户的月份范围的金额合计数据集合:" + sql);
		for (Map map : rowList){
			Bseg bseg =new Bseg();
			ExBeanUtils.setBeanValueFromMap(bseg, map);
			bsegs.add(bseg);
		}
    	return bsegs;
    }  
    
    
    /**
     * 根据月份找到该客户的月份范围所有数据（如取系统时间的前6个月到3个月的合计）
     * @param from 系统时间的前6个月如-6
     * @param to   系统时间的前3个月如-3
     * @param date   分析时间    
    * @param companyno 公司代码
    * @param depid 业务范围   
    * @param customerNo 客户NO
    * @param subjectcode 总账科目代码
    * @param  businessstate 业务类型
    * @return
    */
    public List<Bseg> getUnClearByCustomer(String date,String companyno,String depid,String customerNo,String subjectcode,String businessstate){
    	List<Bseg> bsegs=new ArrayList<Bseg>();    	
    	String sql="SELECT * " +
		" FROM YGETBSEG ge where    ge.hkont like '"+ subjectcode+"' "  ;
    	if(!StringUtils.isNullBlank(companyno)){
    		sql =sql + " and ge.bukrs='"+ companyno +"' ";
    	}
    	if(!StringUtils.isNullBlank(depid)){
    		sql =sql + " and  ge.gsber ='"+ depid +"' ";
    	}    	
    	if(!StringUtils.isNullBlank(businessstate)){
    		sql =sql + " and  ge.vbeltype='"+ businessstate +"' ";
    	}  
    	if(!StringUtils.isNullBlank(customerNo)){
    		sql =sql + " and  ge.kunnr='"+ customerNo +"' ";
    	}
    	
    	sql =sql +  "  and (ge.augdt ='0000-00-00' or ge.augdt> to_char((to_date('"+date+"', 'yyyymmdd') - 0), 'yyyy-mm-dd')) " ;
    	
    	List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		log.debug("根据月份找到该客户的月份范围的金额合计数据集合:" + sql);
		for (Map map : rowList){
			Bseg bseg =new Bseg();
			ExBeanUtils.setBeanValueFromMap(bseg, map);
			bsegs.add(bseg);
		}
    	return bsegs;
    }  
   
    /**
     * 根据月份找到该供应商的月份范围的金额合计（如取系统时间的前6个月到3个月的合计）
     * @param from 系统时间的前6个月如-6
     * @param to   系统时间的前3个月如-3
     * @param date   分析时间
     * @return
     */
    public List<Bseg> getSumAmountBySupplier(String from ,String to,String date){
    	List<Bseg> bsegs=new ArrayList<Bseg>();
    	
    	String sql="SELECT ge.lifnr,ge.hkont,ge.bukrs,ge.gsber,ge.shkzg ,nvl(sum(ge.wrbtr),0) as wrbtr,nvl(sum(ge.dmbtr),0) as dmbtr " +
		" FROM YGETBSEG ge where trim(ge.lifnr) is not null  "+ 
		 "  and (ge.augdt ='0000-00-00' or ge.augdt> to_char((to_date('"+date+"', 'yyyymmdd') - 0), 'yyyy-mm-dd')) " +
		" and ((trim(ge.zfbdt) !='0000-00-00' and  zfbdt >= to_char((to_date('"+date+"','yyyymmdd')-"+from+"),'yyyy-mm-dd') and zfbdt < to_char((to_date('"+date+"','yyyymmdd')-"+to+"),'yyyy-mm-dd') ) " +
		" or (trim(ge.zfbdt) ='0000-00-00' and  BUDAT >= to_char((to_date('"+date+"','yyyymmdd')-"+from+"),'yyyy-mm-dd') and BUDAT < to_char((to_date('"+date+"','yyyymmdd')-"+to+"),'yyyy-mm-dd') ) " +
		" )group by ge.lifnr,ge.hkont,ge.bukrs,ge.gsber,ge.shkzg ";
    	
    	List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		log.debug("根据月份找到该供应商的月份范围的金额合计数据集合:" + sql);
		for (Map map : rowList){
			Bseg bseg =new Bseg();
			ExBeanUtils.setBeanValueFromMap(bseg, map);
			bsegs.add(bseg);
		}
    	return bsegs;
    }
    /**
     *  根据月份找到该供应商的月份范围的金额合计（如取系统时间的前6个月到3个月的合计）
     * @param from 系统时间的前6个月如-6
     * @param to   系统时间的前3个月如-3
     * @param date   分析时间    
    * @param companyno 公司代码 多个客户逗号分开如'12222','55666'
    * @param depid 业务范围    多个客户逗号分开如'12222','55666'
    * @param customerNo 供应商NO 多个客户逗号分开如'12222','55666'
    * @param subjectcode 总账科目代码 
    * * @param  businessstate 业务类型 多个客户逗号分开如'12222','55666'
    * @return
    */
    public List<Bseg> getSumAmountBySupplier(String from ,String to,String date,String companyno,String depid,String customerNo,String subjectcode,String businessstate){
    	List<Bseg> bsegs=new ArrayList<Bseg>();
    	
    	String sql2="SELECT ge.lifnr,ge.hkont,ge.bukrs,ge.gsber,ge.shkzg ,nvl(sum(ge.wrbtr),0) as wrbtr,nvl(sum(ge.dmbtr),0) as dmbtr " ;
    	String sql3="SELECT ge.lifnr,ge.hkont,ge.bukrs,ge.gsber,ge.shkzg ,ge.bsart,nvl(sum(ge.wrbtr),0) as wrbtr,nvl(sum(ge.dmbtr),0) as dmbtr " ;
    	String sql= " FROM YGETBSEG ge where    ge.hkont like '"+ subjectcode+"' "  ;
    	if(!StringUtils.isNullBlank(companyno)){
    		sql =sql + " and ge.bukrs in ("+ companyno +") ";
    	}
    	if(!StringUtils.isNullBlank(depid)){
    		sql =sql + " and  ge.gsber in ("+ depid +") ";
    	}  
    	if(!StringUtils.isNullBlank(businessstate)){
    		sql =sql + " and  ge.bsart in ("+ businessstate +") ";
    	}  
    	if(!StringUtils.isNullBlank(customerNo)){
    		sql =sql + " and  ge.lifnr in ("+ customerNo +") ";
    	}
    	sql =sql + "  and (ge.augdt ='0000-00-00' or ge.augdt> to_char((to_date('"+date+"', 'yyyymmdd') - 0), 'yyyy-mm-dd')) " +
		" and ((trim(ge.zfbdt) !='0000-00-00' and  ge.zfbdt >= to_char((to_date('"+date+"','yyyymmdd')-"+from+"),'yyyy-mm-dd') and ge.zfbdt < to_char((to_date('"+date+"','yyyymmdd')-"+to+"),'yyyy-mm-dd') ) " +
		" or (trim(ge.zfbdt) ='0000-00-00' and  ge.BUDAT >= to_char((to_date('"+date+"','yyyymmdd')-"+from+"),'yyyy-mm-dd') and ge.BUDAT < to_char((to_date('"+date+"','yyyymmdd')-"+to+"),'yyyy-mm-dd') ) " +
		" ) " ;
		String sql4="group by ge.lifnr,ge.hkont,ge.bukrs,ge.gsber,ge.shkzg ";
    	String sql5="group by ge.lifnr,ge.hkont,ge.bukrs,ge.gsber,ge.shkzg,ge.bsart ";
    	if(!StringUtils.isNullBlank(businessstate)){
    		sql = sql3+ sql + sql5;
    	}else{
    		sql = sql2 + sql + sql4;
    	}
    	List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		log.debug("根据月份找到该供应商的月份范围的金额合计数据集合:" + sql);
		for (Map map : rowList){
			Bseg bseg =new Bseg();
			ExBeanUtils.setBeanValueFromMap(bseg, map);
			bsegs.add(bseg);
		}
    	return bsegs;
    }
    
    /**
     *  根据月份找到该供应商的月份范围的所有数据（如取系统时间的前6个月到3个月的合计）
     * @param from 系统时间的前6个月如-6
     * @param to   系统时间的前3个月如-3
     * @param date   分析时间    
    * @param companyno 公司代码
    * @param depid 业务范围   
    * @param customerNo 供应商NO
    * @param subjectcode 总账科目代码
    * * @param  businessstate 业务类型
    * @return
    */
    public List<Bseg> getBsegBySupplier(String from ,String to,String date,String companyno,String depid,String customerNo,String subjectcode,String businessstate){
    	List<Bseg> bsegs=new ArrayList<Bseg>();
    	
    	String sql="SELECT * " +
		" FROM YGETBSEG ge where ge.hkont like '"+ subjectcode+"' "  ;
    	if(!StringUtils.isNullBlank(companyno)){
    		sql =sql + " and ge.bukrs in ("+ companyno +") ";
    	}
    	if(!StringUtils.isNullBlank(depid)){
    		sql =sql + " and  ge.gsber in ("+ depid +") ";
    	}  
    	if(!StringUtils.isNullBlank(businessstate)){
    		sql =sql + " and  ge.bsart in ("+ businessstate +") ";
    	}  
    	if(!StringUtils.isNullBlank(customerNo)){
    		sql =sql + " and  ge.lifnr in ("+ customerNo +") ";
    	}
    	sql =sql + "  and (ge.augdt ='0000-00-00' or ge.augdt> to_char((to_date('"+date+"', 'yyyymmdd') - 0), 'yyyy-mm-dd')) " +
		" and ((trim(ge.zfbdt) !='0000-00-00' and  ge.zfbdt >= to_char((to_date('"+date+"','yyyymmdd')-"+from+"),'yyyy-mm-dd') and ge.zfbdt < to_char((to_date('"+date+"','yyyymmdd')-"+to+"),'yyyy-mm-dd') ) " +
		" or (trim(ge.zfbdt) ='0000-00-00' and  ge.BUDAT >= to_char((to_date('"+date+"','yyyymmdd')-"+from+"),'yyyy-mm-dd') and ge.BUDAT < to_char((to_date('"+date+"','yyyymmdd')-"+to+"),'yyyy-mm-dd') ) " +
		" ) ";
    	
    	List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		log.debug("根据月份找到该供应商的月份范围的金额合计数据集合:" + sql);
		for (Map map : rowList){
			Bseg bseg =new Bseg();
			ExBeanUtils.setBeanValueFromMap(bseg, map);
			bsegs.add(bseg);
		}
    	return bsegs;
    }
    
    /**
     *  根据月份找到该供应商的月份范围的所有数据（如取系统时间的前6个月到3个月的合计）
     * @param from 系统时间的前6个月如-6
     * @param to   系统时间的前3个月如-3
     * @param date   分析时间    
    * @param companyno 公司代码
    * @param depid 业务范围   
    * @param customerNo 供应商NO
    * @param subjectcode 总账科目代码
    * * @param  businessstate 业务类型
    * @return
    */
    public List<Bseg> getUnClearBySupplier(String date,String companyno,String depid,String customerNo,String subjectcode,String businessstate){
    	List<Bseg> bsegs=new ArrayList<Bseg>();
    	
    	String sql="SELECT * " +
		" FROM YGETBSEG ge where  ge.hkont like '"+ subjectcode+"' "  ;
    	if(!StringUtils.isNullBlank(companyno)){
    		sql =sql + " and ge.bukrs='"+ companyno +"' ";
    	}
    	if(!StringUtils.isNullBlank(depid)){
    		sql =sql + " and  ge.gsber='"+ depid +"' ";
    	}  
    	if(!StringUtils.isNullBlank(businessstate)){
    		sql =sql + " and  ge.bsart='"+ businessstate +"' ";
    	}  
    	if(!StringUtils.isNullBlank(customerNo)){
    		sql =sql + " and  ge.lifnr='"+ customerNo +"' ";
    	}
    	sql =sql + "  and (ge.augdt ='0000-00-00' or ge.augdt> to_char((to_date('"+date+"', 'yyyymmdd') - 0), 'yyyy-mm-dd')) " ;		
    	
    	List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		log.debug("根据月份找到该供应商的月份范围的金额合计数据集合:" + sql);
		for (Map map : rowList){
			Bseg bseg =new Bseg();
			ExBeanUtils.setBeanValueFromMap(bseg, map);
			bsegs.add(bseg);
		}
    	return bsegs;
    }
    
    
    /**
     * 根据客户号，总账科目代码，查看是否含逾期,
     * @param customerNo
     * @param subjectCode
     * @return ,1为含逾期,0为不含逾期
     */
    public String isExceedByCustomer(String customerNo,String subjectCode){
    	String sql="SELECT COUNT(*) FROM YGETBSEG ge where (trim(ge.augdt) !='0000-00-00' and ge.augdt> ge.zfbdt) or (trim(ge.augdt) ='0000-00-00' and to_char(sysdate,'yyyy-mm-dd') > ge.zfbdt) " +
    				" and ge.kunnr='"+ customerNo +"' and ge.hkont='"+ subjectCode +"'";
    	//大于0说明含逾期
    	if(this.getJdbcTemplate().queryForInt(sql) > 0){
    		return "1";
    	}else{
    		return "0";
    	}    	
    }
    /**
     * 根据供应商号，总账科目代码，查看是否含逾期,
     * @param supplierNo
     * @param subjectCode
     * @return 1为含逾期,0为不含逾期
     */
    public String isExceedBySupplier(String supplierNo,String subjectCode){
    	String sql="SELECT COUNT(*) FROM YGETBSEG ge where (trim(ge.augdt) !='0000-00-00' and ge.augdt> ge.zfbdt) or (trim(ge.augdt) ='0000-00-00' and to_char(sysdate,'yyyy-mm-dd') > ge.zfbdt) " +
    				" and ge.lifnr='"+ supplierNo +"' and ge.hkont='"+ subjectCode +"'";
    	//大于0说明含逾期
    	if(this.getJdbcTemplate().queryForInt(sql) > 0){
    		return "1";
    	}else{
    		return "0";
    	}    
    }
    
    /**
     * 根据客户号，总账科目代码，查看是否含逾期,
     * @param customerNo
     * @param subjectCode
     * @param date   分析时间
     * @return ,1为含逾期,0为不含逾期
     */
    public String isExceedByCustomer(String customerNo,String subjectCode,String date){
    	String sql="SELECT COUNT(*) FROM YGETBSEG ge where (trim(ge.augdt) !='0000-00-00' and ge.augdt> ge.zfbdt) or (trim(ge.augdt) ='0000-00-00' and  to_char( to_date('" + date +"', 'yyyy-mm-dd'), 'yyyy-mm-dd')  > ge.zfbdt) " +
    				" and ge.kunnr='"+ customerNo +"' and ge.hkont='"+ subjectCode +"'";
    	//大于0说明含逾期
    	if(this.getJdbcTemplate().queryForInt(sql) > 0){
    		return "1";
    	}else{
    		return "0";
    	}    	
    }
    /**
     * 根据供应商号，总账科目代码，查看是否含逾期,
     * @param supplierNo
     * @param subjectCode
     * @param date   分析时间
     * @return 1为含逾期,0为不含逾期
     */
    public String isExceedBySupplier(String supplierNo,String subjectCode,String date){
    	String sql="SELECT COUNT(*) FROM YGETBSEG ge where (trim(ge.augdt) !='0000-00-00' and ge.augdt> ge.zfbdt) or (trim(ge.augdt) ='0000-00-00' and  to_char( to_date('" + date +"', 'yyyy-mm-dd'), 'yyyy-mm-dd') > ge.zfbdt) " +
    				" and ge.lifnr='"+ supplierNo +"' and ge.hkont='"+ subjectCode +"'";
    	//大于0说明含逾期
    	if(this.getJdbcTemplate().queryForInt(sql) > 0){
    		return "1";
    	}else{
    		return "0";
    	}    
    }
    /**
     * 取得未清的客户
     * @param companyno 公司代码
     * @param date 分析日期
     * @param customernos 客户集，多个客户逗号分开如'12222','55666'
     * @param depts 业务范围，多个逗号分开如'12222','55666'
     * @param businessstate 业务类型 ,多个逗号分开如'12222','55666'
     * @return list map中的key 为,customerno 客户no,dept 业务范围,businessstate 业务类型,     
     */
    public List<Map<String,String>>  getAllKunnr(String companyno,String date,String customernos ,String depts,String businessstates){
    	
    	List<Map<String,String>> list =new ArrayList<Map<String,String>>();
    	String sql ="";
    	if(StringUtils.isNullBlank(businessstates)){
	    	sql="select ge.kunnr,ge.gsber from YGETBSEG ge  WHERE trim(ge.kunnr) is not null and ";
	    	if(!StringUtils.isNullBlank(depts)){
	    		sql = sql + "ge.gsber in (" + depts + ") and "; 
	    	}
	    	if(!StringUtils.isNullBlank(customernos)){
	    		sql = sql + "ge.kunnr in (" + customernos + ") and "; 
	    	}
	    	sql =sql + " (ge.augdt ='0000-00-00' or ge.augdt> to_char((to_date('"+date+"', 'yyyymmdd') - 0), 'yyyy-mm-dd'))  GROUP BY ge.kunnr,ge.gsber" ;
	    	List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
	    	if(null != rowList && rowList.size()>0){		
	    		for(Map m: rowList){
	    			Map<String,String> map =new HashMap<String,String>();
		    		map.put("customerno", m.get("kunnr").toString());
		    		map.put("dept", m.get("gsber").toString());
					list.add(map);
	    		}	    		
				return list;
			}else{
				return null;
			}    	
    	}else{
    		sql="select ge.kunnr,ge.gsber,ge.vbeltype from YGETBSEG ge  WHERE  trim(ge.kunnr) is not null and ";
    		if(!StringUtils.isNullBlank(customernos)){
	    		sql = sql + " ge.kunnr in (" + customernos + ") and "; 
	    	}
    		if(!StringUtils.isNullBlank(depts)){
	    		sql = sql + " ge.gsber in (" + depts + ") and "; 
	    	}	
	    	sql =sql + " (ge.augdt ='0000-00-00' or ge.augdt> to_char((to_date('"+date+"', 'yyyymmdd') - 0), 'yyyy-mm-dd'))  GROUP BY ge.kunnr,ge.gsber,ge.vbeltype" ;
	    	List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
	    	if(null != rowList && rowList.size()>0){		
	    		for(Map m: rowList){
	    			Map<String,String> map =new HashMap<String,String>();
	    			map.put("customerno", m.get("kunnr").toString());
		    		map.put("dept", m.get("gsber").toString());
		    		map.put("businessstate", m.get("vbeltype").toString());
					list.add(map);
	    		}	    		
				return list;
			}else{
				return null;
			}    	
    	}
    }
    /**
     * 取得未清的供应商
     * @param companyno 公司代码
     * @param date 分析日期
     * @param customernos 客户集，多个客户逗号分开如'12222','55666'
     * @param depts 业务范围，多个逗号分开如'12222','55666'
     * @param businessstate 业务类型 ,多个逗号分开如'12222','55666'
     * @return list map中的key 为,customerno 客户no,dept 业务范围,businessstate 业务类型,
     */
    public List<Map<String,String>> getAllLifnr(String companyno,String date,String customernos ,String depts,String businessstates){
    	List<Map<String,String>> list =new ArrayList<Map<String,String>>();
    	String sql ="";
    	if(StringUtils.isNullBlank(businessstates)){
	    	sql="select ge.lifnr,ge.gsber from YGETBSEG ge  WHERE trim(ge.lifnr) is not null and ";
	    	if(!StringUtils.isNullBlank(depts)){
	    		sql = sql + "ge.gsber in (" + depts + ") and "; 
	    	}
	    	if(!StringUtils.isNullBlank(customernos)){
	    		sql = sql + "ge.lifnr in (" + customernos + ") and "; 
	    	}
	    	sql =sql + " (ge.augdt ='0000-00-00' or ge.augdt> to_char((to_date('"+date+"', 'yyyymmdd') - 0), 'yyyy-mm-dd'))  GROUP BY ge.lifnr,ge.gsber" ;
	    	List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
	    	if(null != rowList && rowList.size()>0){		
	    		for(Map m: rowList){
	    			Map<String,String> map =new HashMap<String,String>();		    		
		    		map.put("customerno", m.get("lifnr").toString());
		    		map.put("dept", m.get("gsber").toString());
					list.add(map);
	    		}	    		
				return list;
			}else{
				return null;
			}    	
    	}else{
    		sql="select ge.lifnr,ge.gsber,ge.bsart from YGETBSEG ge  WHERE trim(ge.lifnr) is not null and ";
    		if(!StringUtils.isNullBlank(customernos)){
	    		sql = sql + " ge.lifnr in (" + customernos + ") and "; 
	    	}
    		if(!StringUtils.isNullBlank(depts)){
	    		sql = sql + " ge.gsber in (" + depts + ") and "; 
	    	}	
	    	sql =sql + " (ge.augdt ='0000-00-00' or ge.augdt> to_char((to_date('"+date+"', 'yyyymmdd') - 0), 'yyyy-mm-dd'))  GROUP BY ge.lifnr,ge.gsber,ge.bsart" ;
	    	List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
	    	if(null != rowList && rowList.size()>0){		
	    		for(Map m: rowList){
	    			Map<String,String> map =new HashMap<String,String>();
	    			map.put("customerno", m.get("lifnr").toString());
		    		map.put("dept", m.get("gsber").toString());
		    		map.put("businessstate", m.get("bsart").toString());
					list.add(map);
	    		}	    		
				return list;
			}else{
				return null;
			}    	
    	}
    	
    }
    /**
     * 取得未清的所有业务范围
     * @param companyno 公司代码
     * @param date 分析日期
     * @return
     */
    public List<String> getDepts(String companyno,String date){
    	List<String> list =new ArrayList<String>();
    	String sql="select ge.gsber from YGETBSEG ge  WHERE (ge.augdt ='0000-00-00' or ge.augdt> to_char((to_date('"+date+"', 'yyyymmdd') - 0), 'yyyy-mm-dd'))  GROUP BY ge.gsber" ;	
    	List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
    	if(null != rowList && rowList.size()>0){				
			list.addAll(rowList.get(0).values());
			return list;
		}else{
			return null;
		}    	
    }
    /**
     * 批量插入
     * @param reSaveList
     */
    public void insertBsegs(final List<Map<String, String>> reSaveList){
    	String insertSQL = "insert into YGETBSEG (" +
		"MANDT, BSEGID, BUKRS, BELNR, GJAHR, " +
		"BUZEI, BUDAT, AUGDT, SHKZG, GSBER, " +
		"DMBTR, WRBTR, HKONT, KUNNR, LIFNR, " +
		"ZFBDT, REBZG, REBZJ, REBZZ, SGTXT, " +
		"VBEL2, EBELN, VBELTYPE, BSART, PSWSL, " +
		"AWKEY, IHREZ, BNAME ) " +
		"values (?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, " +  "?, ?, ?, ?, ?, " +  "?, ?, ?, ?, ?, " + 
		"?, ?, ?, ?, ?, " +  "?, ?, ? ) "; 
    	this.getJdbcTemplate().batchUpdate(insertSQL, new BatchPreparedStatementSetter() {
		    public int getBatchSize() {
		        return reSaveList.size();
		    }
		
		    public void setValues(PreparedStatement ps, int i) throws SQLException {
		        Map<String,String> map = reSaveList.get(i);
		        ps.setString(1, "800");
		        ps.setString(2, CodeGenerator.getUUID());
		        ps.setString(3, map.get("BUKRS"));
		        ps.setString(4, map.get("BELNR"));		        
		        ps.setString(5, map.get("GJAHR"));
		        
		        ps.setString(6, map.get("BUZEI"));
		        ps.setString(7, map.get("BUDAT"));
		        ps.setString(8, map.get("AUGDT"));
		        ps.setString(9, map.get("SHKZG"));
		        ps.setString(10, map.get("GSBER"));
		        
		        ps.setBigDecimal(11, new BigDecimal(map.get("DMBTR")));
		        ps.setBigDecimal(12, new BigDecimal(map.get("WRBTR")));
		        ps.setString(13, map.get("HKONT"));
		        ps.setString(14, map.get("KUNNR"));
		        ps.setString(15, map.get("LIFNR"));
		        
		        ps.setString(16, map.get("ZFBDT"));
		        ps.setString(17, map.get("REBZG"));
		        ps.setString(18, map.get("REBZJ"));
		        ps.setString(19, map.get("REBZZ"));
		        ps.setString(20, map.get("SGTXT"));
		        
		        ps.setString(21, map.get("VBEL2"));
		        ps.setString(22, map.get("EBELN"));
		        ps.setString(23, map.get("VBELTYPE"));
		        ps.setString(24, map.get("BSART"));
		        ps.setString(25, map.get("PSWSL"));
		        
		        ps.setString(26, map.get("AWKEY"));
		        ps.setString(27, map.get("IHREZ"));
		        ps.setString(28, map.get("BNAME"));
		    }
		});

    }
    /**
     * 批量插入
     * @param reSaveList
     */
    public void insertBsegs2(final List<Map<String, String>> reSaveList){
    	int totel = reSaveList.size();   
    	String insertSQL = "insert into YGETBSEG (" +
		"MANDT, BSEGID, BUKRS, BELNR, GJAHR, " +
		"BUZEI, BUDAT, AUGDT, SHKZG, GSBER, " +
		"DMBTR, WRBTR, HKONT, KUNNR, LIFNR, " +
		"ZFBDT, REBZG, REBZJ, REBZZ, SGTXT, " +
		"VBEL2, EBELN, VBELTYPE, BSART, PSWSL, " +
		"AWKEY, IHREZ, BNAME ) " +
		"values (?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, " +  "?, ?, ?, ?, ?, " +  "?, ?, ?, ?, ?, " + 
		"?, ?, ?, ?, ?, " +  "?, ?, ? ) "; 
    	
	    try {     
	    	   int count = 0;
	    	   Connection conn =this.getConnection();
	    	   conn.setAutoCommit(false);     		    
			   Long beginTime = System.currentTimeMillis(); 		    
			   PreparedStatement ps = conn.prepareStatement(insertSQL);		    
			   for(Map<String, String> map : reSaveList){ 
			        ps.setString(1, "800");
			        ps.setString(2, CodeGenerator.getUUID());
			        ps.setString(3, map.get("BUKRS"));
			        ps.setString(4, map.get("BELNR"));		        
			        ps.setString(5, map.get("GJAHR"));
			        
			        ps.setString(6, map.get("BUZEI"));
			        ps.setString(7, map.get("BUDAT"));
			        ps.setString(8, map.get("AUGDT"));
			        ps.setString(9, map.get("SHKZG"));
			        ps.setString(10, map.get("GSBER"));
			        
			        ps.setBigDecimal(11, new BigDecimal(map.get("DMBTR")));
			        ps.setBigDecimal(12, new BigDecimal(map.get("WRBTR")));
			        ps.setString(13, map.get("HKONT"));
			        ps.setString(14, map.get("KUNNR"));
			        ps.setString(15, map.get("LIFNR"));
			        
			        ps.setString(16, map.get("ZFBDT"));
			        ps.setString(17, map.get("REBZG"));
			        ps.setString(18, map.get("REBZJ"));
			        ps.setString(19, map.get("REBZZ"));
			        ps.setString(20, map.get("SGTXT"));
			        
			        ps.setString(21, map.get("VBEL2"));
			        ps.setString(22, map.get("EBELN"));
			        ps.setString(23, map.get("VBELTYPE"));
			        ps.setString(24, map.get("BSART"));
			        ps.setString(25, map.get("PSWSL"));
			        
			        ps.setString(26, map.get("AWKEY"));
			        ps.setString(27, map.get("IHREZ"));
			        ps.setString(28, map.get("BNAME"));    
			        ps.addBatch();     		    
			        if(count!=0 && count%1000==0){  //可以设置不同的大小；如50，100，500，1000等等     		    
					    ps.executeBatch();     		    
					    conn.commit();     		    
					    ps.clearBatch();    
					    log.debug("----------------已刷入 " + count + " 笔进数据库，剩余 " + (totel-count) + " 笔！");
			        }  	
			        count++;
			   }  		
			   ps.executeBatch();     		    
			   conn.commit();     		    
			   ps.clearBatch();  
			   
			   Long endTime = System.currentTimeMillis();
			   System.out.println("批量插入pst+batch："+(endTime-beginTime)/1000+"秒"); 
			   ps.close();     
//			   conn.close(); 
		}catch(SQLException e){ 
			e.printStackTrace();     		    
		}  
    } 
}
