package com.infolion.xdss3.profitLoss.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.project.domain.TProjectAccounting;
import com.infolion.xdss3.profitLoss.domain.ProfitLoss;

@Repository
public class ProfitLossJdbcDao extends BaseJdbcDao{


    /**
     * 批量更新立项号、采购合同号、业务类型、部门ID
     * @param companyId
     * @param dateValue
     * @param deptSql  使用组装过的SQL
     */
    public void updateProfitLossInfo(String companyId, String dateValue, String deptSql) {
        
        String whereSQL = "   and p.companyid = '"+companyId+"'" +
                "   and p.datevalue = '"+dateValue+"'" ;
        if (StringUtils.isNotBlank(deptSql)){
            whereSQL = whereSQL + " and p.deptid in " + deptSql + "";
        }
        
        
        /** 1、更新部门ID */
        String updateSQL = "update yprofitloss y set y.deptid = ( select max(s.dept_id) from t_sys_dept s where y.deptid = s.dept_code )" +
                "where y.deptid in ( select s.dept_code from t_sys_dept s )" +
                " and length(y.deptid) = 4";
        
        log.debug("更新存货浮动盈亏 YPROFITLOSS 部门ID：" + updateSQL);
        this.getJdbcTemplate().execute(updateSQL);
        
        
        /** 2、更新立项号、采购合同号、业务类型 */
        updateSQL = "update YPROFITLOSS p" +
                "   set (p.project_no, p.contractno, p.type) =" +
                "       (select max(w.project_no), max(v.contract_no), max(b.title)" +
                "          from YXCONTRAPURCHASE v, T_PROJECT_INFO w, BM_BUSINESS_TYPE b" +
                "         where p.orderid = v.sap_order_no" +
                "           and b.id = w.trade_type" +
                "           and v.project_id = w.project_id" +
                "           and trim(v.sap_order_no) is not null)" +
                " where trim(p.orderid) is not null" + whereSQL;

        
        log.debug("更新存货浮动盈亏 YPROFITLOSS 立项号、合同号、业务类型：" + updateSQL);
        this.getJdbcTemplate().execute(updateSQL);
        
        
        /** 3、回填上一次持仓费用、市场单价 */
        // 取得上一次日期
        String dateValueSQL = "select max(p.datevalue) DATEVALUE from YPROFITLOSS p " +
        		"where 1=1 and p.datevalue < " +dateValue  + "   and p.companyid = '"+companyId+"'";
        if (StringUtils.isNotBlank(deptSql)){
            dateValueSQL = dateValueSQL + " and p.deptid in " + deptSql + "";
        }
        Map map = this.getJdbcTemplate().queryForMap(dateValueSQL);
        String lastDateValue = (String) map.get("DATEVALUE");
        
        if (StringUtils.isNotBlank(lastDateValue)) {
            // 取得上一次所有记录
            String lastInfoSQL = " select *  from YPROFITLOSS p where 1=1 and p.datevalue='" + lastDateValue + "' "+
                                            "   and p.companyid = '"+companyId+"' ";
            if (StringUtils.isNotBlank(deptSql)){
                lastInfoSQL = lastInfoSQL + " and p.deptid in " + deptSql + "";
            }
            List<Map> lastInfo = this.getJdbcTemplate().queryForList(lastInfoSQL);
            
            // 取得本次所有记录
            String currentInfoSQL = " select *  from YPROFITLOSS p where 1=1 " + whereSQL;
            List<Map> currentInfo = this.getJdbcTemplate().queryForList(currentInfoSQL);
            
            for (Map cmap : currentInfo) {
                String material_code = (String)cmap.get("MATERIAL_CODE");
                String batch_no = (String)cmap.get("BATCH_NO");
                for (Map lmap : lastInfo) {
                    if (StringUtils.equals( (String)lmap.get("MATERIAL_CODE"), material_code ) 
                            && StringUtils.equals( (String)lmap.get("BATCH_NO"), batch_no ) ) {
                        BigDecimal pv = (BigDecimal)lmap.get("POSITIONVALUE");
                        BigDecimal mp = (BigDecimal)lmap.get("MARKETUNITPRICE");
                        BigDecimal remaintotalvalue = (BigDecimal)lmap.get("REMAINTOTALVALUE");
                        String backcomment = (String)lmap.get("BACKCOMMENT"); //备注2
                        String maxlosscomment = (String)lmap.get("MAXLOSSCOMMENT");
                        if (pv==null) {
                            pv = new BigDecimal(0);
                        }
                        if (mp==null) {
                            mp = new BigDecimal(0);
                        }
                        if (remaintotalvalue==null) {
                            remaintotalvalue = new BigDecimal(0);
                        }
                        if (backcomment==null) {
                            backcomment = "";
                        }
                        if (maxlosscomment==null){
                            maxlosscomment = "";
                        }
                        StringBuffer uValue = new StringBuffer(
                                "update YPROFITLOSS p set p.positionvalue = '").append( pv.toString() ).append("', ")
                                .append("                             p.marketunitprice='").append(mp).append("', ")
                                .append("                             p.remaintotalvalue='").append(remaintotalvalue).append("', ")
                                .append("                             p.backcomment='").append(backcomment).append("', ")
                                .append("                             p.maxlosscomment='").append(maxlosscomment).append("' ")
                                .append(" where 1=1 ")
                                .append(whereSQL)
                                .append("    and MATERIAL_CODE = '").append(material_code).append("' ")
                                .append("    and BATCH_NO = '").append(batch_no).append("' ");
                        this.getJdbcTemplate().execute(uValue.toString());
                    }
                }
            }
        }
        
        /** 4、当flag为X时，更新合同、采购订单号为空 */
        updateSQL = "update YPROFITLOSS p set p.contractno=' ', p.orderid=' ' where p.flag='X'";

        log.debug("当flag为X时，更新合同为空：" + updateSQL);
        this.getJdbcTemplate().execute(updateSQL);
        
        /** 5、重新 估计售价总额、单位浮亏/盈 */
        
        updateSQL = "update YPROFITLOSS p set p.Estimatevalue = p.Marketunitprice*p.Quantity, " +
        		" p.Unitprofitloss = p.Marketunitprice-p.Unitprice" +
        		" where 1=1 "+ whereSQL;

        log.debug("重新 估计售价总额、单位浮亏/盈" + updateSQL);
        this.getJdbcTemplate().execute(updateSQL);
        
        updateSQL = "update YPROFITLOSS p set  p.Totalprofitloss = p.Estimatevalue-p.Totalvalue  " +
        " where 1=1 "+ whereSQL;
        
        log.debug("总浮亏/盈" + updateSQL);
        this.getJdbcTemplate().execute(updateSQL);
        
        updateSQL = "update YPROFITLOSS p set  p.remaintotalvalue = p.Totalprofitloss - p.positionvalue " +
        " where 1=1 "+ whereSQL;
        
        log.debug("扣除持仓费用后的总浮盈" + updateSQL);
        this.getJdbcTemplate().execute(updateSQL);
        
	}
    
    /**
     * 批量更新数据
     * @param companyId
     * @param dateValue
     * @param deptSql  使用组装过的SQL
     */
    public void updateT_Temp_ProfitLoss(String companyId, String dateValue, String deptSql) {
        
        String whereSQL = "   and p.companyid = '"+companyId+"'" +
        "   and p.datevalue = '"+dateValue+"'" ;
        if (StringUtils.isNotBlank(deptSql)){
            whereSQL = whereSQL + " and p.deptid in " + deptSql + "";
        }
        
        
        /** 1、更新部门ID */
        String updateSQL = "update t_temp_profitLoss y set y.deptid = ( select max(s.dept_id) from t_sys_dept s where y.deptid = s.dept_code )" +
        "where y.deptid in ( select s.dept_code from t_sys_dept s )" +
        " and length(y.deptid) = 4";
        
        log.debug("更新存货浮动盈亏 t_temp_profitLoss 部门ID：" + updateSQL);
        this.getJdbcTemplate().execute(updateSQL);
        
        
        /** 2、更新立项号、采购合同号、业务类型 */
        updateSQL = "update t_temp_profitLoss p" +
        "   set (p.project_no, p.contractno, p.type) =" +
        "       (select max(w.project_no), max(v.contract_no), max(b.title)" +
        "          from YXCONTRAPURCHASE v, T_PROJECT_INFO w, BM_BUSINESS_TYPE b" +
        "         where p.orderid = v.sap_order_no" +
        "           and b.id = w.trade_type" +
        "           and v.project_id = w.project_id" +
        "           and trim(v.sap_order_no) is not null)" +
        " where trim(p.orderid) is not null" + whereSQL;
        
        
        log.debug("更新存货浮动盈亏 t_temp_profitLoss 立项号、合同号、业务类型：" + updateSQL);
        this.getJdbcTemplate().execute(updateSQL);
        
        
        /** 3、回填上一次持仓费用、市场单价 */
        // 取得上一次日期
        String dateValueSQL = "select max(p.datevalue) DATEVALUE from t_temp_profitLoss p " +
        "where 1=1 and p.datevalue < " +dateValue  + "   and p.companyid = '"+companyId+"'";
        if (StringUtils.isNotBlank(deptSql)){
            dateValueSQL = dateValueSQL + " and p.deptid in " + deptSql + "";
        }
        Map map = this.getJdbcTemplate().queryForMap(dateValueSQL);
        String lastDateValue = (String) map.get("DATEVALUE");
        
        if (StringUtils.isNotBlank(lastDateValue)) {
            // 取得上一次所有记录
            String lastInfoSQL = " select *  from t_temp_profitLoss p where 1=1 and p.datevalue='" + lastDateValue + "' "+
            "   and p.companyid = '"+companyId+"' ";
            if (StringUtils.isNotBlank(deptSql)){
                lastInfoSQL = lastInfoSQL + " and p.deptid in " + deptSql + "";
            }
            List<Map> lastInfo = this.getJdbcTemplate().queryForList(lastInfoSQL);
            
            // 取得本次所有记录
            String currentInfoSQL = " select *  from t_temp_profitLoss p where 1=1 " + whereSQL;
            List<Map> currentInfo = this.getJdbcTemplate().queryForList(currentInfoSQL);
            
            for (Map cmap : currentInfo) {
                String material_code = (String)cmap.get("MATERIAL_CODE");
                String batch_no = (String)cmap.get("BATCH_NO");
                for (Map lmap : lastInfo) {
                    if (StringUtils.equals( (String)lmap.get("MATERIAL_CODE"), material_code ) 
                            && StringUtils.equals( (String)lmap.get("BATCH_NO"), batch_no ) ) {
                        BigDecimal pv = (BigDecimal)lmap.get("POSITIONVALUE");
                        BigDecimal mp = (BigDecimal)lmap.get("MARKETUNITPRICE");
                        BigDecimal remaintotalvalue = (BigDecimal)lmap.get("REMAINTOTALVALUE");
                        String backcomment = (String)lmap.get("BACKCOMMENT"); //备注2
                        String maxlosscomment = (String)lmap.get("MAXLOSSCOMMENT");
                        if (pv==null) {
                            pv = new BigDecimal(0);
                        }
                        if (mp==null) {
                            mp = new BigDecimal(0);
                        }
                        if (remaintotalvalue==null) {
                            remaintotalvalue = new BigDecimal(0);
                        }
                        if (backcomment==null) {
                            backcomment = "";
                        }
                        if (maxlosscomment==null){
                            maxlosscomment = "";
                        }
                        StringBuffer uValue = new StringBuffer(
                        "update t_temp_profitLoss p set p.positionvalue = '").append( pv.toString() ).append("', ")
                        .append("                             p.marketunitprice='").append(mp).append("', ")
                        .append("                             p.remaintotalvalue='").append(remaintotalvalue).append("', ")
                        .append("                             p.backcomment='").append(backcomment).append("', ")
                        .append("                             p.maxlosscomment='").append(maxlosscomment).append("' ")
                        .append(" where 1=1 ")
                        .append(whereSQL)
                        .append("    and MATERIAL_CODE = '").append(material_code).append("' ")
                        .append("    and BATCH_NO = '").append(batch_no).append("' ");
                        this.getJdbcTemplate().execute(uValue.toString());
                    }
                }
            }
        }
        
        /** 4、当flag为X时，更新合同、采购订单号为空 */
        updateSQL = "update t_temp_profitLoss p set p.contractno=' ', p.orderid=' ' where p.flag='X'";
        
        log.debug("当flag为X时，更新合同为空：" + updateSQL);
        this.getJdbcTemplate().execute(updateSQL);
        
        /** 5、重新 估计售价总额、单位浮亏/盈 */
        
        updateSQL = "update t_temp_profitLoss p set p.Estimatevalue = p.Marketunitprice*p.Quantity, " +
        " p.Unitprofitloss = p.Marketunitprice-p.Unitprice" +
        " where 1=1 "+ whereSQL;
        
        log.debug("重新 估计售价总额、单位浮亏/盈" + updateSQL);
        this.getJdbcTemplate().execute(updateSQL);
        
        updateSQL = "update t_temp_profitLoss p set  p.Totalprofitloss = p.Estimatevalue-p.Totalvalue  " +
        " where 1=1 "+ whereSQL;
        
        log.debug("总浮亏/盈" + updateSQL);
        this.getJdbcTemplate().execute(updateSQL);
        
        updateSQL = "update t_temp_profitLoss p set  p.remaintotalvalue = p.Totalprofitloss - p.positionvalue " +
        " where 1=1 "+ whereSQL;
        log.debug("扣除持仓费用后的总浮盈" + updateSQL);
        
        updateSQL = "update t_temp_profitLoss p set  p.maxlosscomment = '' " +
        " where 1=1 and p.maxlosscomment = 'null' "+ whereSQL;
        log.debug("更新null字段" + updateSQL);
        
        this.getJdbcTemplate().execute(updateSQL);
        
    }
    
    /**
     * 转换 dept_id 为 dept_code
     * @param deptId 格式应为 XX,XX,XX
     * @return
     */
    public List<Map<String, String>> getDeptCodeListById(String deptId){
        String[] deptList = StringUtils.splitString(deptId, ",");
        StringBuffer sb = new StringBuffer("'");
        for (String dept :deptList) {
            sb.append(dept).append("', '");
        }
        sb.append("'");
        
        String querySQL = " select t.dept_code DEPT_CODE from T_SYS_DEPT  t where t.dept_id in ("+sb+") ";
        log.debug("转换 dept_id 为 dept_code" + querySQL);
        return this.getJdbcTemplate().queryForList(querySQL);
    }
    
    /**
     * @创建作者：邱杰烜
     * @创建日期：2010-12-01
     * 取得所有"SAP同步公司信息"的公司编号
     */
    public List<Map<String, String>> getCompanyIds(){
        String sql = "SELECT COMPANYID FROM YCOMPANYINFO";
        return this.getJdbcTemplate().queryForList(sql);
    }
    
	/**
	 * 保存到临时表中
	 */
	public void saveProfitloss2t_temp_profitloss(final List<ProfitLoss> reSaveList){
	    String insertSQL = "insert into T_TEMP_PROFITLOSS (" +
	    		"MANDT, PROFITLOSSID, COMPANYID, DATEVALUE, DEPTID, " +
	    		"EKKO_UNSEZ, MATERIAL_GROUP, MATERIAL_CODE, METERIAL_NAME, ORDERID, " +
	    		"BATCH_NO, PROVIDERID, PROVIDERNAME, TAXRATE, UNIT, " +
	    		"QUANTITY, UNITPRICE, TOTALVALUE, SUBJECT, TYPE, " +
	    		"MARKETUNITPRICE, TAXINCLUDEVLAUE, ESTIMATEVALUE, UNITPROFITLOSS, TOTALPROFITLOSS, " +
	    		"MAXLOSSCOMMENT, REMAINTOTALVALUE, BACKCOMMENT, CREATOR, CREATETIME, " +
	    		"LASTMODIFYER, LASTMODIFYTIME, PROJECT_NO, CONTRACTNO, POSITIONVALUE, " +
	    		"FLAG,BUDATE,STORAGETIME,WAREHOUSE) " +
	    		"values (?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, " +  "?, ?, ?, ?, ?, " +  "?, ?, ?, ?, ?, " + 
	    		"?, ?, ?, ?, ?, " +  "?, ?, ?, ?, ?, " +  "?, ?, ?, ?, ?, " +  "?,?,?,? ) "; 
        this.getJdbcTemplate().batchUpdate(insertSQL, new BatchPreparedStatementSetter() {
            public int getBatchSize() {
                return reSaveList.size();
            }

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ProfitLoss profitLoss = reSaveList.get(i);
                ps.setString(1, "800");
                ps.setString(2, UUID.randomUUID().toString().replace("-", ""));
                ps.setString(3, profitLoss.getCompanyid());
                ps.setString(4, profitLoss.getDatevalue());
                ps.setString(5, profitLoss.getDeptid());
                ps.setString(6, profitLoss.getEkko_unsez());
                ps.setString(7, profitLoss.getMaterial_group());
                ps.setString(8, profitLoss.getMaterial_code());
                ps.setString(9, profitLoss.getMeterial_name());
                ps.setString(10, profitLoss.getOrderid());
                ps.setString(11, profitLoss.getBatch_no());
                ps.setString(12, profitLoss.getProviderid());
                ps.setString(13, profitLoss.getProvidername());
                ps.setString(14, profitLoss.getTaxrate());
                ps.setString(15, profitLoss.getUnit());
                ps.setBigDecimal(16, profitLoss.getQuantity());
                ps.setBigDecimal(17, profitLoss.getUnitprice());
                ps.setBigDecimal(18, profitLoss.getTotalvalue());
                ps.setString(19, profitLoss.getSubject());
                ps.setString(20, profitLoss.getType());
                ps.setBigDecimal(21, profitLoss.getMarketunitprice());
                ps.setBigDecimal(22, profitLoss.getTaxincludevlaue());
                ps.setBigDecimal(23, profitLoss.getEstimatevalue());
                ps.setBigDecimal(24, profitLoss.getUnitprofitloss());
                ps.setBigDecimal(25, profitLoss.getTotalprofitloss());
                ps.setString(26, StringUtils.isBlank(profitLoss.getMaxlosscomment())?" ":profitLoss.getMaxlosscomment());
                ps.setBigDecimal(27, profitLoss.getRemaintotalvalue());
                ps.setString(28, StringUtils.isBlank(profitLoss.getBackcomment())?" ":profitLoss.getBackcomment());
                ps.setString(29, profitLoss.getCreator());
                ps.setString(30, profitLoss.getCreatetime());
                ps.setString(31, profitLoss.getLastmodifyer());
                ps.setString(32, profitLoss.getLastmodifytime());
                ps.setString(33, profitLoss.getProject_no());
                ps.setString(34, profitLoss.getContractno());
                ps.setBigDecimal(35, profitLoss.getPositionvalue());
                ps.setString(36, profitLoss.getFlag());
                ps.setString(37, profitLoss.getBuDate());
                ps.setString(38, profitLoss.getStorageTime());
                ps.setString(39, profitLoss.getWareHouse());
            }
        });
	    
	    
	}
}
