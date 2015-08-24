package com.infolion.xdss3.masterData.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseHibernateDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.xdss3.masterData.domain.Bseg;

/**
 * @创建作者：邱杰烜
 * @创建时间：2010-12-16
 */
@Repository
public class BsegHibernateDao extends BaseHibernateDao<Bseg>{
    public Bseg getBsegIfExists(String bukrs, String belnr, String gjahr, String buzei){
        String hql = "from Bseg where bukrs = ? and belnr = ? and gjahr = ? and buzei = ?";
        List<Bseg> bsegList = this.getHibernateTemplate().find(hql, new Object[]{bukrs, belnr, gjahr, buzei});
        if(bsegList != null && bsegList.size()>0){
            return bsegList.get(0);
        }else{
            return null;
        }
    }
}