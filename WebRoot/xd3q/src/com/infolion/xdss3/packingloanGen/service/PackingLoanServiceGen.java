/*
 * @(#)PackingLoanServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年05月26日 10点55分54秒
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
import com.infolion.xdss3.packingloan.domain.PackingLoan;
import com.infolion.xdss3.packingloan.service.PackingLoanService;
import com.infolion.xdss3.packingloan.dao.PackingLoanHibernateDao;

import com.infolion.xdss3.packingloan.domain.PackingBankItem;
import com.infolion.xdss3.packingloan.service.PackingBankItemService;

import com.infolion.xdss3.packingloan.domain.PackingReBankItem;
import com.infolion.xdss3.packingloan.service.PackingReBankItemService;

import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.financeSquare.service.SettleSubjectService;

import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.service.FundFlowService;

/**
 * <pre>
 * 打包贷款(PackingLoan)服务类
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
public class PackingLoanServiceGen extends BaseService {
    protected Log log = LogFactory.getFactory().getLog(this.getClass());

    @Autowired
    protected PackingLoanHibernateDao packingLoanHibernateDao;

    public void setPackingLoanHibernateDao(PackingLoanHibernateDao packingLoanHibernateDao) {
        this.packingLoanHibernateDao = packingLoanHibernateDao;
    }

    /**
     * 注入扩展服务类
     */
    @Autowired(required = false)
    public void setAdvanceService(@Qualifier("packingLoanAdvanceService") AdvanceService advanceService) {
        super.setAdvanceService(advanceService);
    }

    @Autowired
    protected PackingBankItemService packingBankItemService;

    public void setPackingBankItemService(PackingBankItemService packingBankItemService) {
        this.packingBankItemService = packingBankItemService;
    }

    @Autowired
    protected PackingReBankItemService packingReBankItemService;

    public void setPackingReBankItemService(PackingReBankItemService packingReBankItemService) {
        this.packingReBankItemService = packingReBankItemService;
    }

    @Autowired
    protected SettleSubjectService settleSubjectService;

    public void setSettleSubjectService(SettleSubjectService settleSubjectService) {
        this.settleSubjectService = settleSubjectService;
    }

    @Autowired
    protected FundFlowService fundFlowService;

    public void setFundFlowService(FundFlowService fundFlowService) {
        this.fundFlowService = fundFlowService;
    }

    /**
     * 根据主键ID,取得打包贷款实例
     * 
     * @param id
     * @return
     */
    public PackingLoan _getDetached(String id) {
        PackingLoan packingLoan = new PackingLoan();
        if (StringUtils.isNotBlank(id)) {
            packingLoan = packingLoanHibernateDao.getDetached(id);
        }
        // 需要转移到service服务里，要不存在事务问题。
        String operationType = LockService.lockBOData(packingLoan);
        if (OperationType.UNVISIABLE.equals(operationType)) {
            throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
        }
        packingLoan.setOperationType(operationType);

        return packingLoan;
    }

    /**
     * 根据主键ID,取得打包贷款实例
     * 
     * @param id
     * @return
     */
    public PackingLoan _get(String id) {
        PackingLoan packingLoan = new PackingLoan();
        if (StringUtils.isNotBlank(id)) {
            packingLoan = packingLoanHibernateDao.get(id);
        }
        // 需要转移到service服务里，要不存在事务问题。
        String operationType = LockService.lockBOData(packingLoan);
        if (OperationType.UNVISIABLE.equals(operationType)) {
            throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
        }
        packingLoan.setOperationType(operationType);

        return packingLoan;
    }

    /**
     * 根据主键ID,取得打包贷款实例
     * 
     * @param id
     * @return
     */
    public PackingLoan _getForEdit(String id) {
        PackingLoan packingLoan = _get(id);
        // 需要转移到service服务里，要不存在事务问题。
        String operationType = packingLoan.getOperationType();
        if (OperationType.READONLY.equals(operationType)) {
            throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
        }

        return packingLoan;
    }

    /**
     * 根据主键ID,取得打包贷款实例副本
     * 
     * @param id
     * @return
     */
    public PackingLoan _getEntityCopy(String id) {
        PackingLoan packingLoan = new PackingLoan();
        PackingLoan packingLoanOld = this._get(id);
        try {
            BeanUtils.copyProperties(packingLoan, packingLoanOld);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        packingLoan.setPacking_no(null);
        // packingLoan.setPackingid(null);
        packingLoan.setProcessstate(" ");
        return packingLoan;
    }

    /**
     * 删除
     * 
     * @param packingLoan
     */
    public void _delete(PackingLoan packingLoan) {
        if (null != advanceService)
            advanceService.preDelete(packingLoan);

        // 流程状态
        String processState = packingLoan.getProcessstate();
        if (!StringUtils.isNullBlank(processState)) {
            throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
        }
        LockService.isBoInstanceLocked(packingLoan, PackingLoan.class);
        packingLoanHibernateDao.remove(packingLoan);

        if (null != advanceService)
            advanceService.postDelete(packingLoan);
    }

    /**
     * 根据主键删除
     * 
     * @param packingLoanId
     */
    public void _delete(String packingLoanId, BusinessObject businessObject) {
        if (StringUtils.isNullBlank(packingLoanId))
            // TODO DataElementText
            throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank,
                    businessObject.getBoPropertyText("packingid"));
        PackingLoan packingLoan = this.packingLoanHibernateDao.load(packingLoanId);
        _delete(packingLoan);
    }

    /**
     * 删除
     * 
     * @param Set
     *            <PackingLoan> packingLoans
     */
    public void _deletes(Set<PackingLoan> packingLoans, BusinessObject businessObject) {
        if (null == packingLoans || packingLoans.size() < 1)
            // TODO DataElementText
            throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank,
                    businessObject.getBoText());
        Iterator<PackingLoan> it = packingLoans.iterator();
        while (it.hasNext()) {
            PackingLoan packingLoan = (PackingLoan) it.next();
            _delete(packingLoan);
        }
    }

    /**
     * 根据多个主键(由，分割)删除多个对象
     * 
     * @param packingLoanIds
     */
    public void _deletes(String packingLoanIds, BusinessObject businessObject) {
        if (StringUtils.isNullBlank(packingLoanIds))
            // TODO DataElementText
            throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank,
                    businessObject.getBoPropertyText("packingid"));
        String[] ids = StringUtils.splitString(packingLoanIds);
        for (int i = 0; i < ids.length; i++) {
            _delete(ids[i], businessObject);
        }
    }

    /**
     * 提交工作流
     * 
     * @param packingLoan
     */
    public void _submitProcess(PackingLoan packingLoan, Set<PackingBankItem> deletedPackingBankItemSet,
            Set<PackingReBankItem> deletedPackingReBankItemSet, BusinessObject businessObject) {
        String id = packingLoan.getPackingid();
        /**
         * if (StringUtils.isNullBlank(id)) { _save(packingLoan); } else {
         * _update(packingLoan ,deletedPackingBankItemSet
         * ,deletedPackingReBankItemSet , businessObject); }
         **/

        String taskId = packingLoan.getWorkflowTaskId();
        id = packingLoan.getPackingid();
        if (null == taskId || "".equals(taskId))
            WorkflowService.createAndSignalProcessInstance(packingLoan, id);
        else
            WorkflowService.signalProcessInstance(packingLoan, id, null);
    }

    /**
     * 保存或更新打包贷款 保存
     * 
     * @param packingLoan
     */
    public void _update(PackingLoan packingLoan, Set<PackingBankItem> deletedPackingBankItemSet,
            Set<PackingReBankItem> deletedPackingReBankItemSet, BusinessObject businessObject) {
        // 用户二次开发服务，保存前执行
        if (null != advanceService)
            advanceService.preSaveOrUpdate(packingLoan);
        packingLoanHibernateDao.saveOrUpdate(packingLoan);
        // 删除关联子业务对象数据
        if (deletedPackingBankItemSet != null && deletedPackingBankItemSet.size() > 0) {
            packingBankItemService._deletes(deletedPackingBankItemSet, businessObject);
        }
        // 删除关联子业务对象数据
        if (deletedPackingReBankItemSet != null && deletedPackingReBankItemSet.size() > 0) {
            packingReBankItemService._deletes(deletedPackingReBankItemSet, businessObject);
        } // 用户二次开发服务，保存后执行
        if (null != advanceService)
            advanceService.postSaveOrUpdate(packingLoan);
    }

    /**
     * 保存
     * 
     * @param packingLoan
     */
    public void _save(PackingLoan packingLoan) {
        // 用户二次开发服务，保存前执行
        if (null != advanceService)
            advanceService.preSaveOrUpdate(packingLoan);
        // 对id赋空值，使之执行insert操作(适应复制创建场景)
        packingLoan.setPackingid(null);

        Set<PackingBankItem> packingBankItemSet = packingLoan.getPackingBankItem();
        Set<PackingBankItem> newPackingBankItemSet = null;
        if (null != packingBankItemSet) {
            newPackingBankItemSet = new HashSet();
            Iterator<PackingBankItem> itPackingBankItem = packingBankItemSet.iterator();
            while (itPackingBankItem.hasNext()) {
                PackingBankItem packingBankItem = (PackingBankItem) itPackingBankItem.next();
                packingBankItem.setBankitemid(null);
                newPackingBankItemSet.add(packingBankItem);
            }
        }
        packingLoan.setPackingBankItem(newPackingBankItemSet);

        Set<PackingReBankItem> packingReBankItemSet = packingLoan.getPackingReBankItem();
        Set<PackingReBankItem> newPackingReBankItemSet = null;
        if (null != packingReBankItemSet) {
            newPackingReBankItemSet = new HashSet();
            Iterator<PackingReBankItem> itPackingReBankItem = packingReBankItemSet.iterator();
            while (itPackingReBankItem.hasNext()) {
                PackingReBankItem packingReBankItem = (PackingReBankItem) itPackingReBankItem.next();
                packingReBankItem.setBankitemid(null);
                newPackingReBankItemSet.add(packingReBankItem);
            }
        }
        packingLoan.setPackingReBankItem(newPackingReBankItemSet);

        SettleSubject settleSubject = packingLoan.getSettleSubject();
        if (null != settleSubject) {
            settleSubject.setSettlesubjectid(null);
        }
        packingLoan.setSettleSubject(settleSubject);

        FundFlow fundFlow = packingLoan.getFundFlow();
        if (null != fundFlow) {
            fundFlow.setFundflowid(null);
        }
        packingLoan.setFundFlow(fundFlow);
        packingLoanHibernateDao.saveOrUpdate(packingLoan);
        // 用户二次开发服务，保存后执行
        if (null != advanceService)
            advanceService.postSaveOrUpdate(packingLoan);
    }

    /**
     * 
     * 保存
     * 
     * @param packingLoan
     */
    public void _saveOrUpdate(PackingLoan packingLoan, Set<PackingBankItem> deletedPackingBankItemSet,
            Set<PackingReBankItem> deletedPackingReBankItemSet, BusinessObject businessObject) {
        if (StringUtils.isNullBlank(packingLoan.getPacking_no())) {
            String packing_no = NumberService.getNextObjectNumber("PackingLoanNO", packingLoan);
            packingLoan.setPacking_no(packing_no);
        }
        if (StringUtils.isNullBlank(packingLoan.getPackingid())) {
            _save(packingLoan);
        } else {
            _update(packingLoan, deletedPackingBankItemSet, deletedPackingReBankItemSet, businessObject);
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