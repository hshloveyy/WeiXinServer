package com.infolion.sapss.sqlReport.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.sqlReport.dao.SqlElementHibernateDao;
import com.infolion.sapss.sqlReport.dao.SqlElementJdbcDao;
import com.infolion.sapss.sqlReport.dao.SqlFieldDfHibernateDao;
import com.infolion.sapss.sqlReport.domain.TSqlElement;
import com.infolion.sapss.sqlReport.domain.TSqlFieldDf;

@Service
public class SqlReportService extends BaseJdbcService {
	
	@Autowired
	SqlElementHibernateDao SqlElementHibernateDao;
	
	@Autowired
	private SqlElementJdbcDao sqlElementJdbcDao;
	
	@Autowired
	private SqlFieldDfHibernateDao SqlFieldDfHibernateDao;

	public SqlFieldDfHibernateDao getSqlFieldDfHibernateDao() {
		return SqlFieldDfHibernateDao;
	}

	public void setSqlFieldDfHibernateDao(
			SqlFieldDfHibernateDao sqlFieldDfHibernateDao) {
		SqlFieldDfHibernateDao = sqlFieldDfHibernateDao;
	}

	public SqlElementJdbcDao getSqlElementJdbcDao() {
		return sqlElementJdbcDao;
	}

	public void setSqlElementJdbcDao(SqlElementJdbcDao sqlElementJdbcDao) {
		this.sqlElementJdbcDao = sqlElementJdbcDao;
	}

	

	public SqlElementHibernateDao getSqlElementHibernateDao() {
		return SqlElementHibernateDao;
	}

	public void setSqlElementHibernateDao(
			SqlElementHibernateDao sqlElementHibernateDao) {
		SqlElementHibernateDao = sqlElementHibernateDao;
	}

	public String getQuerySqlSql(Map<String, String> filter) {
		String sql = "select * from t_sql_element t " ;		
		if (filter != null && !filter.isEmpty()) {			
			String name = filter.get("name");
			if (StringUtils.isNotBlank(name)) {
				sql += " and t.name like  '%" + name + "%'";
			}
		}
		sql += " order by t.createrTime desc";
		return sql;
	}
	
	public TSqlElement addSqlElementInfo(){
		TSqlElement info = new TSqlElement();
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		info.setSqlElementId(CodeGenerator.getUUID());
		info.setCreaterTime(DateUtils.getCurrDate(DateUtils.HYPHEN_DISPLAY_DATE));
		info.setCreator(userContext.getSysUser().getUserId());
		SqlElementHibernateDao.save(info);
		return info;
	}
	
	public TSqlElement getSqlElementInfo(String id ){
		TSqlElement info = SqlElementHibernateDao.get(id); 
		return info;
	}
	
	public void updateSqlElementInfo(TSqlElement info){
		SqlElementHibernateDao.update(info);
	}
	
	public List<TSqlFieldDf> getSqlFieldDfList(String sqlElementId){
		List<TSqlFieldDf> sqlFields = SqlElementHibernateDao.find("from TSqlFieldDf a where a.sqlElementId ='" + sqlElementId + "' order by orderNo");
        return sqlFields;			
	}

	
	public void analySqlElementInfo(TSqlElement info){
		SqlElementHibernateDao.update(info);
		List<String> analyFields = sqlElementJdbcDao.analySqlFields(info.getSql());
        if(analyFields.isEmpty()) throw new BusinessException("无效语句、语句必须含有数据");
		List<TSqlFieldDf> sqlFields = getSqlFieldDfList(info.getSqlElementId());
		
		//新解析
		if(sqlFields.isEmpty()){
			int i = 0;
			for(String fieldName:analyFields){
				TSqlFieldDf newField = new TSqlFieldDf();
				newField.setSqlFieldDfId(CodeGenerator.getUUID());
				newField.setSqlElementId(info.getSqlElementId());
				newField.setFiledName(fieldName);
				newField.setOrderNo(i++);
				SqlFieldDfHibernateDao.save(newField);
			}
		} 
		//旧数据拷贝
		else{
			int i = 0;
			for(String fieldName:analyFields){
				TSqlFieldDf newField = new TSqlFieldDf();				
				for(TSqlFieldDf oldField : sqlFields){
					if(fieldName.equals(oldField.getFiledName())){
						BeanUtils.copyProperties(oldField, newField);
					}
				}
				newField.setSqlFieldDfId(CodeGenerator.getUUID());
				newField.setSqlElementId(info.getSqlElementId());
				newField.setFiledName(fieldName);
				newField.setOrderNo(i++);
				SqlFieldDfHibernateDao.save(newField);
			}
			//删除旧数据
			for(TSqlFieldDf oldField : sqlFields){
				SqlFieldDfHibernateDao.remove(oldField);
			}
		}
	}
	
	public void updateSqlField(String sqlFieldId, String colName, String colValue){
		sqlElementJdbcDao.updateSqlField(sqlFieldId,colName,colValue);
	}
	
	public void dealOutToExcel(String sql ,final ExcelObject excel,final List<String> fields){
		sqlElementJdbcDao.dealOutToExcel(sql, excel, fields);
	}
}
