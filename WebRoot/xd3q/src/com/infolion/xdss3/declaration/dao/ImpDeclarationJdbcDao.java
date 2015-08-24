/*
 * @(#)ImpDeclarationJdbcDao
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-7-16
 *  描　述：创建
 */

package com.infolion.xdss3.declaration.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.xdss3.declaration.domain.ImpDeclaration;


/**
 * @author HONG
 */
@Repository
public class ImpDeclarationJdbcDao extends BaseJdbcDao
{
    private Log log = LogFactory.getFactory().getLog(this.getClass());
    
    /**
     * 判断是否存在
     */
    public boolean hasExists(ImpDeclaration impDeclaration){
        String sql = "SELECT  'x' FROM YIMPDECLARATION WHERE declarationsno = '"+impDeclaration.getDeclarationsno()+"' "; 
        return this.getJdbcTemplate().queryForRowSet(sql).next();
    }
	
    
}
