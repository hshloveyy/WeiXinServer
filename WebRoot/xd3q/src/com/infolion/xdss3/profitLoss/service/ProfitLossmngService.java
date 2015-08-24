/*
 * @(#)ProfitLossmngService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月21日 11点46分24秒
 *  描　述：创建
 */
package com.infolion.xdss3.profitLoss.service;

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
import java.math.BigDecimal;

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
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.dpframework.core.service.AdvanceService;
import com.infolion.xdss3.profitLoss.domain.ProfitLoss;
import com.infolion.xdss3.profitLoss.domain.ProfitLossmng;
import com.infolion.xdss3.profitLoss.service.ProfitLossmngService;
import com.infolion.xdss3.profitLoss.dao.ProfitLossmngHibernateDao;
import com.infolion.xdss3.profitLossGen.service.ProfitLossmngServiceGen;

/**
 * <pre>
 * 存货浮动盈亏调查表(ProfitLossmng)服务类
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
public class ProfitLossmngService extends ProfitLossmngServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	/**
	 *
	 * 保存  
	 *   
	 * @param profitLossSet
	 */
	public void _saveOrUpdate(Set<ProfitLossmng> profitLossmngSet
,BusinessObject businessObject		
	)
	{
		Iterator<ProfitLossmng> items = profitLossmngSet.iterator();
		while (items.hasNext())
		{
			ProfitLossmng profitLossmng =  (ProfitLossmng) items.next();
			_saveOrUpdate(profitLossmng,businessObject);
		}
	}
	
	/**
	 * 保存修改的值
	 * @param strProfitlossidList
	 * @param strBackcommentList
	 * @param strMaxlosscommentList
	 * @param strRemaintotalvalueList
	 */
	public void saveChange(String strProfitlossidList,String strBackcommentList,
			String strMaxlosscommentList,String strRemaintotalvalueList, String strPositionvalueList){
		String[] IdList = strProfitlossidList.split("\\|");
		String[] BackcommentList = strBackcommentList.split("\\|");
		String[] MaxlosscommentList = strMaxlosscommentList.split("\\|");
		String[] RemaintotalvalueList = strRemaintotalvalueList.split("\\|");
		String[] PositionvalueList = strPositionvalueList.split("\\|");
		for (int i = 0; i < IdList.length; i++) {
		    if (StringUtils.isNullBlank(IdList[i])) {
		        continue;
		    }
			ProfitLossmng profitLossmng =  this._get(IdList[i]);
			
			profitLossmng.setBackcomment(BackcommentList[i]);
//			profitLossmng.setMaxlosscomment(MaxlosscommentList[i]);
//			profitLossmng.setRemaintotalvalue(new BigDecimal(RemaintotalvalueList[i]));
//			profitLossmng.setPositionvalue(new BigDecimal(PositionvalueList[i])); // 持仓费用
			
			this.profitLossmngHibernateDao.saveOrUpdate(profitLossmng);
			
		}
	}
}