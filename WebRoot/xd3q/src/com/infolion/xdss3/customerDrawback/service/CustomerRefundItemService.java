/*
 * @(#)CustomerRefundItemService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月17日 14点59分11秒
 *  描　述：创建
 */
package com.infolion.xdss3.customerDrawback.service;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.uicomponent.number.service.NumberService;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.uicomponent.lock.LockException;
import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.dpframework.core.service.AdvanceService;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.service.CustomerRefundItemService;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemHibernateDao;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.customerDrawbackGen.service.CustomerRefundItemServiceGen;

/**
 * <pre>
 * 客户退款行项目(CustomerRefundItem)服务类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class CustomerRefundItemService extends CustomerRefundItemServiceGen
{
    private Log log = LogFactory.getFactory().getLog(this.getClass());


    @Autowired
    private CustomerRefundItemJdbcDao customerRefundItemJdbcDao;
    

    public void setCustomerRefundItemJdbcDao(CustomerRefundItemJdbcDao customerRefundItemJdbcDao) {
        this.customerRefundItemJdbcDao = customerRefundItemJdbcDao;
    }

    public CustomerRefundItem get(String id) {
        return this.customerRefundItemHibernateDao.get(id);
    	
    }
    
    /**
     * 更新付款分配表行项目是否已结清标志。
     * 
     * @param vendortitleid
     * @param isCleared
     */
    public void updateRefundItemIsCleared(String refundItemId, String isCleared)
    {
        this.customerRefundItemJdbcDao.updateRefundItemIsCleared(refundItemId, isCleared);
    }
    
    /**
     * @创建作者：邱杰烜
     * @创建日期：2010-12-22
     * 更新客户退款行项对应的收款行项的实际剩余保证金
     */
    public void updateActsuretybond(String refundmentId, String collectItemId){
        this.customerRefundItemJdbcDao.updateActsuretybond(refundmentId, collectItemId);
    }
    
}