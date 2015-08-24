/*
 * @(#)PackingReBankItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年05月26日 10点55分57秒
 *  描　述：创建
 */
package com.infolion.xdss3.packingloanGen.service;

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
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.xdss3.packingloan.domain.PackingReBankItem;
import com.infolion.xdss3.packingloan.service.PackingReBankItemService;
import com.infolion.xdss3.packingloan.dao.PackingReBankItemHibernateDao;

/**
 * <pre>
 * 还打包贷款银行(PackingReBankItem)服务类
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
public class PackingReBankItemServiceGen extends BaseService {
    protected Log log = LogFactory.getFactory().getLog(this.getClass());

    @Autowired
    protected PackingReBankItemHibernateDao packingReBankItemHibernateDao;

    public void setPackingReBankItemHibernateDao(PackingReBankItemHibernateDao packingReBankItemHibernateDao) {
        this.packingReBankItemHibernateDao = packingReBankItemHibernateDao;
    }

    /**
     * 注入扩展服务类
     */
    @Autowired(required = false)
    public void setAdvanceService(@Qualifier("packingReBankItemAdvanceService") AdvanceService advanceService) {
        super.setAdvanceService(advanceService);
    }

    /**
     * 根据主键ID,取得还打包贷款银行实例
     * 
     * @param id
     * @return
     */
    public PackingReBankItem _getDetached(String id) {
        PackingReBankItem packingReBankItem = new PackingReBankItem();
        if (StringUtils.isNotBlank(id)) {
            packingReBankItem = packingReBankItemHibernateDao.getDetached(id);
        }
        // 需要转移到service服务里，要不存在事务问题。
        String operationType = LockService.lockBOData(packingReBankItem);
        if (OperationType.UNVISIABLE.equals(operationType)) {
            throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
        }
        packingReBankItem.setOperationType(operationType);

        return packingReBankItem;
    }

    /**
     * 根据主键ID,取得还打包贷款银行实例
     * 
     * @param id
     * @return
     */
    public PackingReBankItem _get(String id) {
        PackingReBankItem packingReBankItem = new PackingReBankItem();
        if (StringUtils.isNotBlank(id)) {
            packingReBankItem = packingReBankItemHibernateDao.get(id);
        }
        // 需要转移到service服务里，要不存在事务问题。
        String operationType = LockService.lockBOData(packingReBankItem);
        if (OperationType.UNVISIABLE.equals(operationType)) {
            throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
        }
        packingReBankItem.setOperationType(operationType);

        return packingReBankItem;
    }

    /**
     * 根据主键ID,取得还打包贷款银行实例
     * 
     * @param id
     * @return
     */
    public PackingReBankItem _getForEdit(String id) {
        PackingReBankItem packingReBankItem = _get(id);
        // 需要转移到service服务里，要不存在事务问题。
        String operationType = packingReBankItem.getOperationType();
        if (OperationType.READONLY.equals(operationType)) {
            throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
        }

        return packingReBankItem;
    }

    /**
     * 根据主键ID,取得还打包贷款银行实例副本
     * 
     * @param id
     * @return
     */
    public PackingReBankItem _getEntityCopy(String id) {
        PackingReBankItem packingReBankItem = new PackingReBankItem();
        PackingReBankItem packingReBankItemOld = this._get(id);
        try {
            BeanUtils.copyProperties(packingReBankItem, packingReBankItemOld);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // packingReBankItem.setBankitemid(null);
        return packingReBankItem;
    }

    /**
     * 删除
     * 
     * @param packingReBankItem
     */
    public void _delete(PackingReBankItem packingReBankItem) {
        if (null != advanceService)
            advanceService.preDelete(packingReBankItem);

        LockService.isBoInstanceLocked(packingReBankItem, PackingReBankItem.class);
        packingReBankItemHibernateDao.remove(packingReBankItem);

        if (null != advanceService)
            advanceService.postDelete(packingReBankItem);
    }

    /**
     * 根据主键删除
     * 
     * @param packingReBankItemId
     */
    public void _delete(String packingReBankItemId, BusinessObject businessObject) {
        if (StringUtils.isNullBlank(packingReBankItemId))
            // TODO DataElementText
            throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank,
                    businessObject.getBoPropertyText("bankitemid"));
        PackingReBankItem packingReBankItem = this.packingReBankItemHibernateDao.load(packingReBankItemId);
        _delete(packingReBankItem);
    }

    /**
     * 删除
     * 
     * @param Set
     *            <PackingReBankItem> packingReBankItems
     */
    public void _deletes(Set<PackingReBankItem> packingReBankItems, BusinessObject businessObject) {
        if (null == packingReBankItems || packingReBankItems.size() < 1)
            // TODO DataElementText
            throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank,
                    businessObject.getBoText());
        Iterator<PackingReBankItem> it = packingReBankItems.iterator();
        while (it.hasNext()) {
            PackingReBankItem packingReBankItem = (PackingReBankItem) it.next();
            _delete(packingReBankItem);
        }
    }

    /**
     * 根据多个主键(由，分割)删除多个对象
     * 
     * @param packingReBankItemIds
     */
    public void _deletes(String packingReBankItemIds, BusinessObject businessObject) {
        if (StringUtils.isNullBlank(packingReBankItemIds))
            // TODO DataElementText
            throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank,
                    businessObject.getBoPropertyText("bankitemid"));
        String[] ids = StringUtils.splitString(packingReBankItemIds);
        for (int i = 0; i < ids.length; i++) {
            _delete(ids[i], businessObject);
        }
    }

    /**
     * 提交工作流
     * 
     * @param packingReBankItem
     */
    public void _submitProcess(PackingReBankItem packingReBankItem, BusinessObject businessObject) {
        String id = packingReBankItem.getBankitemid();
        /**
         * if (StringUtils.isNullBlank(id)) { _save(packingReBankItem); } else {
         * _update(packingReBankItem , businessObject); }
         **/

        String taskId = packingReBankItem.getWorkflowTaskId();
        id = packingReBankItem.getBankitemid();
        if (null == taskId || "".equals(taskId))
            WorkflowService.createAndSignalProcessInstance(packingReBankItem, id);
        else
            WorkflowService.signalProcessInstance(packingReBankItem, id, null);
    }

    /**
     * 保存或更新还打包贷款银行 保存
     * 
     * @param packingReBankItem
     */
    public void _update(PackingReBankItem packingReBankItem, BusinessObject businessObject) {
        // 用户二次开发服务，保存前执行
        if (null != advanceService)
            advanceService.preSaveOrUpdate(packingReBankItem);
        packingReBankItemHibernateDao.saveOrUpdate(packingReBankItem);
        // 用户二次开发服务，保存后执行
        if (null != advanceService)
            advanceService.postSaveOrUpdate(packingReBankItem);
    }

    /**
     * 保存
     * 
     * @param packingReBankItem
     */
    public void _save(PackingReBankItem packingReBankItem) {
        // 用户二次开发服务，保存前执行
        if (null != advanceService)
            advanceService.preSaveOrUpdate(packingReBankItem);
        // 对id赋空值，使之执行insert操作(适应复制创建场景)
        packingReBankItem.setBankitemid(null);
        packingReBankItemHibernateDao.saveOrUpdate(packingReBankItem);
        // 用户二次开发服务，保存后执行
        if (null != advanceService)
            advanceService.postSaveOrUpdate(packingReBankItem);
    }

    /**
     * 
     * 保存
     * 
     * @param packingReBankItem
     */
    public void _saveOrUpdate(PackingReBankItem packingReBankItem, BusinessObject businessObject) {
        if (StringUtils.isNullBlank(packingReBankItem.getBankitemid())) {
            _save(packingReBankItem);
        } else {
            _update(packingReBankItem, businessObject);
        }
    }

    /**
     * 查询
     * 
     * @param queryCondition
     * @return
     */
    public List _query(String queryCondition) {
        return null;
    }
}