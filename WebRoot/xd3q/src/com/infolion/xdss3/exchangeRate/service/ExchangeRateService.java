package com.infolion.xdss3.exchangeRate.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.service.AdvanceService;
import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.uicomponent.lock.LockException;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.xdss3.exchangeRate.dao.ExchangeRateHibernateDao;
import com.infolion.xdss3.exchangeRate.dao.ExchangeRateJdbcDao;
import com.infolion.xdss3.exchangeRate.domain.ExchangeRate;


@Service
public class ExchangeRateService extends BaseService{
	@Autowired
	private ExchangeRateHibernateDao exchangeRateHibernatgeDao;
	
	@Autowired
	private ExchangeRateJdbcDao exchangeRateJdbcDao;
	
	protected Log log = LogFactory.getFactory().getLog(this.getClass());
	
	 /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("rateAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得利率实例
	 * @param id
	 * @return
	 */
	public ExchangeRate _getDetached(String id)
	{		
	    ExchangeRate rate = new ExchangeRate();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  rate = exchangeRateHibernatgeDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(rate);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    rate.setOperationType(operationType);
	    
		return rate;	
	}
	
	/**
	 * 根据主键ID,取得利率实例
	 * @param id
	 * @return
	 */
	public ExchangeRate _get(String id)
	{		
	    ExchangeRate rate = new ExchangeRate();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  rate = exchangeRateHibernatgeDao.get(id);
	    }	    
	    
		return rate;	
	}
	
	/**
	 * 根据主键ID,取得利率实例
	 * @param id
	 * @return
	 */
	public ExchangeRate _getForEdit(String id)
	{		
	    ExchangeRate rate = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = rate.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return rate;	
	}
	
	/**
	 * 根据主键ID,取得利率实例副本
	 * @param id
	 * @return
	 */
	public ExchangeRate _getEntityCopy(String id)
	{		
	    ExchangeRate rate = new ExchangeRate();
		ExchangeRate rateOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(rate, rateOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//rate.setRateid(null); 
		return rate;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param rate
	 */
	public void _delete(ExchangeRate rate)
	{
		if (null != advanceService)
			advanceService.preDelete(rate);
	
 		
 		exchangeRateHibernatgeDao.remove(rate);

		if (null != advanceService)
			advanceService.postDelete(rate);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param rateId
	 */
	public void _delete(String rateId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(rateId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("id"));
		ExchangeRate rate = this.exchangeRateHibernatgeDao.load(rateId);
		_delete(rate);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<ExchangeRate> rates
	 */
	public void _deletes(Set<ExchangeRate> rates,BusinessObject businessObject)
	{
		if (null == rates || rates.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<ExchangeRate> it = rates.iterator();
		while (it.hasNext())
		{
			ExchangeRate rate = (ExchangeRate) it.next();
			_delete(rate);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param rateIds
	 */
	public void _deletes(String rateIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(rateIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("rateid"));
		String[] ids = StringUtils.splitString(rateIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	

	/**
	 * 保存或更新利率
	 * 保存  
	 *  
	 * @param rate
	 */
	public void _update(ExchangeRate rate){
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(rate);
		exchangeRateHibernatgeDao.saveOrUpdate(rate);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(rate);
	}
	
	/**
	 * 保存  
	 *   
	 * @param rate
	 */
	public void _save(ExchangeRate rate){
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(rate);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)		
		rate.setId(null);
		exchangeRateHibernatgeDao.saveOrUpdate(rate);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(rate);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param rate
	 */
	public void _saveOrUpdate(ExchangeRate rate){
		ExchangeRate r = exchangeRateJdbcDao.getExchangeRate(rate.getRate_date(),rate.getCurrency(),rate.getCurrency2());
		if (StringUtils.isNullBlank(rate.getId()))
		{	
			if(null == r){
				_save(rate);
			}else{
				rate.setId(r.getId());
				_update(rate);
			}
		}
		else
		{
			_update(rate);
		}
	}
	
	
	/**
	 * 查询  
	 *   
	 * @param queryCondition
	 * @return
	 */
	public List<ExchangeRate> _query(String queryCondition)
	{
		return this.exchangeRateJdbcDao.getList(queryCondition);
		
	}
}
