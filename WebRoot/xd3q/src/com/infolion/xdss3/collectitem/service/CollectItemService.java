/*
 * @(#)CollectItemService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月12日 07点01分37秒
 *  描　述：创建
 */
package com.infolion.xdss3.collectitem.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.xdss3.billincollect.service.BillInCollectService;
import com.infolion.xdss3.collectitem.dao.CollectItemJdbcDao;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.collectitemGen.service.CollectItemServiceGen;

/**
 * <pre>
 * 收款金额分配(CollectItem)服务类
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
public class CollectItemService extends CollectItemServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	private CollectItemJdbcDao collectItemJdbcDao;

	/**
	 * @param collectItemJdbcDao
	 *            the collectItemJdbcDao to set
	 */
	public void setCollectItemJdbcDao(CollectItemJdbcDao collectItemJdbcDao)
	{
		this.collectItemJdbcDao = collectItemJdbcDao;
	}

	@Autowired
	protected BillInCollectService billInCollectService;
	
	public void setBillInCollectService(BillInCollectService billInCollectService)
	{
		this.billInCollectService = billInCollectService;
	}
	/**
	 * 取得未清预收票款
	 * 
	 * @param id
	 * @return
	 */
	public List<CollectItem> getCustomerUnclearCollectList(String customer)
	{
//		String hql = "from CollectItem where isclear = '0' order by contract_no desc";
//		return this.collectItemHibernateDao.find(hql);
		return collectItemJdbcDao.getCollectItemByCustomer(customer);
	}

	/**
	 * 根据合同号取得原合同号(纸质合同号)
	 * 
	 * @param Contract_No
	 * @return
	 */
	public String getOldContractNo(String Contract_No)
	{
		return this.collectItemJdbcDao.getOldContractNo(Contract_No);
	}
	
	/**
	 * 判断是否有未清收款
	 * 
	 * @param id
	 * @return
	 */
	public Boolean getCustomerUnclearCollectList(String customer, String contract_no, String project_no)
	{
		String hql = "from CollectItem where isclear = '0' and ((contract_no = ?) or (project_no = ? and trim(contract_no) is null))";
		List list = this.collectItemHibernateDao.find(hql, new Object[] { contract_no, project_no });
		if (list.size() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 取得未清预收票款
	 * 
	 * @param id
	 * @return
	 */
	public List getUnclearPreBillCollectList(String collectitemids)
	{
		return this.collectItemHibernateDao.getUnclearPreBillCollectList(collectitemids);
	}

	/**
	 * 更新收款分配表 自动判断是否需要结清
	 * 
	 * @param id
	 * @return
	 */
	public boolean autoSettleCollectItem(String collectitemid)
	{
		CollectItem collectitem = this._get(collectitemid);
		//收款单已审批完的
		BigDecimal clearedcollectamount = this.billInCollectService.getSumClearedAmount(collectitemid, "1");
		//预收票款 = 审批完的清票 + 本次清票  则结清
		if(collectitem.getPrebillamount().compareTo(clearedcollectamount)==1){
			this.collectItemHibernateDao.clearCollectItem(collectitemid);
			return true;
		}
		return false;
	}

	/**
	 * 根据业务状态、收款分配行项ID，取得收款已经审批完金额(款已清金额)、在途金额(款在途金额)。
	 * 
	 * @param collectitemid
	 * @param businessstates
	 *            eg: '1','2'
	 * @return
	 */
	public BigDecimal getSumClearAmount(String collectitemid, String businessstates)
	{
		return this.collectItemJdbcDao.getSumClearAmount(collectitemid, businessstates);
	}
	
	/**
	 * 根据业务状态、收款分配行项ID，取得收款已经审批完金额(款已清金额)、在途金额(款在途金额)。去掉本次的
	 * 
	 * @param collectitemid
	 * @param businessstates
	 *            eg: '1','2'
	 * @return
	 */
	public BigDecimal getSumClearAmount(String collectitemid, String businessstates,String billclearid)
	{
		return this.collectItemJdbcDao.getSumClearAmount(collectitemid, businessstates,billclearid);
	}
	/**
	 * 根据业务状态、收款分配行项ID，取得退款保证金已经审批完金额(款已清金额)、在途金额(款在途金额)。去掉本次的
	 * 
	 * @param collectitemid
	 * @param businessstates
	 *            eg: '1','2'
	 * @return
	 */
	public BigDecimal getSumClearAmountByRefundment(String collectitemid, String businessstates)
	{
		return this.collectItemJdbcDao.getSumClearAmountByRefundment(collectitemid, businessstates);
	}
	/**
	 * 取得保证金转货款金额
	 * @param collectitemid
	 * @return
	 */
	public BigDecimal getSumAmountBySuretybond(String collectitemid)
	{
		return this.collectItemJdbcDao.getSumAmountBySuretybond(collectitemid);
	}
	
	/**
	 * 更新收款分配表行项目是否已结清标志。
	 * 
	 * @param vendortitleid
	 * @param isCleared
	 */
	public void updateCollectItemIsCleared(String collectItemId, String isCleared)
	{
		this.collectItemJdbcDao.updateCollectItemIsCleared(collectItemId, isCleared);
	}
	
	public CollectItem get(String id){
		return this.collectItemHibernateDao.get(id);
	}
}