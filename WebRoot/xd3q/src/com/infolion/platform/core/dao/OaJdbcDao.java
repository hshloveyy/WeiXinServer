package com.infolion.platform.core.dao;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class OaJdbcDao {
	
	protected static Log log = LogFactory.getFactory().getLog(OaJdbcDao.class);
	
	private JdbcTemplate oaJdbcTemplate;
	
	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public JdbcTemplate getOaJdbcTemplate() {
		if(oaJdbcTemplate==null){
			oaJdbcTemplate = new JdbcTemplate(getDataSource());
		}
		return oaJdbcTemplate;
	}

}
