/*
 * @(#)PackingBankItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年05月26日 10点55分56秒
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
import com.infolion.xdss3.packingloan.domain.PackingBankItem;
import com.infolion.xdss3.packingloan.service.PackingBankItemService;
import com.infolion.xdss3.packingloan.dao.PackingBankItemHibernateDao;

/**
 * <pre>
 * 打包贷款银行(PackingBankItem)服务类
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
public class PackingBankItemServiceGen extends BaseService {
    protected Log log = LogFactory.getFactory().getLog(this.getClass());

    @Autowired
    protected PackingBankItemHibernateDao packingBankItemHibernateDao;

    public void setPackingBankItemHibernateDao(PackingBankItemHibernateDao packingBankItemHibernateDao) {
        this.packingBankItemHibernateDao = packingBankItemHibernateDao;
    }

    /**
     * 注入扩展服务类
     */
    @Autowired(required = false)
    public void setAdvanceService(@Qualifier("packingBankItemAdvanceService") AdvanceService advanceService) {
        super.setAdvanceService(advanceService);
    }

    /**
     * 根据主键ID,取得打包贷款银行实例
     * 
     * @param id
     * @return
     */
    public PackingBankItem _getDetached(String id) {
        PackingBankItem packingBankItem = new PackingBankItem();
        if (StringUtils.isNotBlank(id)) {
            packingBankItem = packingBankItemHibernateDao.getDetached(id);
        }
        // 需要转移到service服务里，要不存在事务问题。
        String operationType = LockService.lockBOData(packingBankItem);
        if (OperationType.UNVISIABLE.equals(operationType)) {
            throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
        }
        packingBankItem.setOperationType(operationType);

        return packingBankItem;
    }

    /**
     * 根据主键ID,取得打包贷款银行实例
     * 
     * @param id
     * @return
     */
    public PackingBankItem _get(String id) {
        PackingBankItem packingBankItem = new PackingBankItem();
        if (StringUtils.isNotBlank(id)) {
            packingBankItem = packingBankItemHibernateDao.get(id);
        }
        // 需要转移到service服务里，要不存在事务问题。
        String operationType = LockService.lockBOData(packingBankItem);
        if (OperationType.UNVISIABLE.equals(operationType)) {
            throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
        }
        packingBankItem.setOperationType(operationType);

        return packingBankItem;
    }

    /**
     * 根据主键ID,取得打包贷款银行实例
     * 
     * @param id
     * @return
     */
    public PackingBankItem _getForEdit(String id) {
        PackingBankItem packingBankItem = _get(id);
        // 需要转移到service服务里，要不存在事务问题。
        String operationType = packingBankItem.getOperationType();
        if (OperationType.READONLY.equals(operationType)) {
            throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
        }

        return packingBankItem;
    }

    /**
     * 根据主键ID,取得打包贷款银行实例副本
     * 
     * @param id
     * @return
     */
    public PackingBankItem _getEntityCopy(String id) {
        PackingBankItem packingBankItem = new PackingBankItem();
        PackingBankItem packingBankItemOld = this._get(id);
        try {
            BeanUtils.copyProperties(packingBankItem, packingBankItemOld);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // packingBankItem.setBankitemid(null);
        return packingBankItem;
    }

    /**
     * 删除
     * 
     * @param packingBankItem
     */
    public void _delete(PackingBankItem packingBankItem) {
        if (null != advanceService)
            advanceService.preDelete(packingBankItem);

        LockService.isBoInstanceLocked(packingBankItem, PackingBankItem.class);
        packingBankItemHibernateDao.remove(packingBankItem);

        if (null != advanceService)
            advanceService.postDelete(packingBankItem);
    }

    /**
     * 根据主键删除
     * 
     * @param packingBankItemId
     */
    public void _delete(String packingBankItemId, BusinessObject businessObject) {
        if (StringUtils.isNullBlank(packingBankItemId))
            // TODO DataElementText
            throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank,
                    businessObject.getBoPropertyText("bankitemid"));
        PackingBankItem packingBankItem = this.packingBankItemHibernateDao.load(packingBankItemId);
        _delete(packingBankItem);
    }

    /**
     * 删除
     * 
     * @param Set
     *            <PackingBankItem> packingBankItems
     */
    public void _deletes(Set<PackingBankItem> packingBankItems, BusinessObject businessObject) {
        if (null == packingBankItems || packingBankItems.size() < 1)
            // TODO DataElementText
            throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank,
                    businessObject.getBoText());
        Iterator<PackingBankItem> it = packingBankItems.iterator();
        while (it.hasNext()) {
            PackingBankItem packingBankItem = (PackingBankItem) it.next();
            _delete(packingBankItem);
        }
    }

    /**
     * 根据多个主键(由，分割)删除多个对象
     * 
     * @param packingBankItemIds
     */
    public void _deletes(String packingBankItemIds, BusinessObject businessObject) {
        if (StringUtils.isNullBlank(packingBankItemIds))
            // TODO DataElementText
            throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank,
                    businessObject.getBoPropertyText("bankitemid"));
        String[] ids = StringUtils.splitString(packingBankItemIds);
        for (int i = 0; i < ids.length; i++) {
            _delete(ids[i], businessObject);
        }
    }

    /**
     * 提交工作流
     * 
     * @param packingBankItem
     */
    public void _submitProcess(PackingBankItem packingBankItem, BusinessObject businessObject) {
        String id = packingBankItem.getBankitemid();
        /**
         * if (StringUtils.isNullBlank(id)) { _save(packingBankItem); } else {
         * _update(packingBankItem , businessObject); }
         **/

        String taskId = packingBankItem.getWorkflowTaskId();
        id = packingBankItem.getBankitemid();
        if (null == taskId || "".equals(taskId))
            WorkflowService.createAndSignalProcessInstance(packingBankItem, id);
        else
            WorkflowService.signalProcessInstance(packingBankItem, id, null);
    }

    /**
     * 保存或更新打包贷款银行 保存
     * 
     * @param packingBankItem
     */
    public void _update(PackingBankItem packingBankItem, BusinessObject businessObject) {
        // 用户二次开发服务，保存前执行
        if (null != advanceService)
            advanceService.preSaveOrUpdate(packingBankItem);
        packingBankItemHibernateDao.saveOrUpdate(packingBankItem);
        // 用户二次开发服务，保存后执行
        if (null != advanceService)
            advanceService.postSaveOrUpdate(packingBankItem);
    }

    /**
     * 保存
     * 
     * @param packingBankItem
     */
    public void _save(PackingBankItem packingBankItem) {
        // 用户二次开发服务，保存前执行
        if (null != advanceService)
            advanceService.preSaveOrUpdate(packingBankItem);
        // 对id赋空值，使之执行insert操作(适应复制创建场景)
        packingBankItem.setBankitemid(null);
        packingBankItemHibernateDao.saveOrUpdate(packingBankItem);
        // 用户二次开发服务，保存后执行
        if (null != advanceService)
            advanceService.postSaveOrUpdate(packingBankItem);
    }

    /**
     * 
     * 保存
     * 
     * @param packingBankItem
     */
    public void _saveOrUpdate(PackingBankItem packingBankItem, BusinessObject businessObject) {
        if (StringUtils.isNullBlank(packingBankItem.getBankitemid())) {
            _save(packingBankItem);
        } else {
            _update(packingBankItem, businessObject);
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