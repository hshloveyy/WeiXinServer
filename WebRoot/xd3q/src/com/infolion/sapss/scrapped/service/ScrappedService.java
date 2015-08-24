/*
 * @(#)ScrappedService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-17
 *  描　述：创建
 */

package com.infolion.sapss.scrapped.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.scrapped.dao.ScrappedHibernate;
import com.infolion.sapss.scrapped.dao.ScrappedJdbcDao;
import com.infolion.sapss.scrapped.dao.ScrappedMaterialHibernate;
import com.infolion.sapss.scrapped.domain.TScrapped;
import com.infolion.sapss.scrapped.domain.TScrappedMaterial;
import com.infolion.sapss.workflow.ProcessCallBack;
@Service
public class ScrappedService extends BaseJdbcService implements ProcessCallBack{
	@Autowired
	private ScrappedJdbcDao scrappedJdbcDao;
	@Autowired
	private ScrappedHibernate scrappedHibernate;
	@Autowired
	private ScrappedMaterialHibernate scrappedMaterialHibernate;
	
	public void setScrappedJdbcDao(ScrappedJdbcDao scrappedJdbcDao) {
		this.scrappedJdbcDao = scrappedJdbcDao;
	}

	public void setScrappedHibernate(ScrappedHibernate scrappedHibernate) {
		this.scrappedHibernate = scrappedHibernate;
	}

	public void setScrappedMaterialHibernate(
			ScrappedMaterialHibernate scrappedMaterialHibernate) {
		this.scrappedMaterialHibernate = scrappedMaterialHibernate;
	}

	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 保存物料
	 * @param sm
	 */
	public void saveMaterial(TScrappedMaterial sm) {
		this.scrappedMaterialHibernate.save(sm);
	}
	/**
	 * 更新物料字段
	 * @param scrappedMaterialId
	 * @param colName
	 * @param colValue
	 */
	public int updateMaterial(String scrappedMaterialId, String colName,
			String colValue) {
		return this.scrappedJdbcDao.updateMaterial(scrappedMaterialId,colName,colValue);
		
	}
	/**
	 * 保存报废主信息
	 * @param s
	 */
	public void saveScrapped(TScrapped s) {
		this.scrappedHibernate.saveOrUpdate(s);
	}
	/**
	 * 删除物料
	 */
	public int delMaterial(String materialId) {
		return this.scrappedJdbcDao.delMaterial(materialId);
	}
	/**
	 * 查找报废主信息
	 * @param scrappedId
	 */
	public TScrapped findScrapped(String scrappedId) {
		List<TScrapped> list= this.scrappedHibernate.find("from TScrapped t where t.scrappedId=?", new String[]{scrappedId});
		if(list!=null && list.size()>0)
			return list.get(0);
		return new TScrapped();
	}
	/**
	 * 删除报废主信息
	 * @param scrappedId
	 */
	public int delScrapped(String scrappedId) {
		return this.scrappedJdbcDao.delScrapped(scrappedId);
		
	}
	/**
	 * 提交流程审批
	 * @param taskId
	 * @param ts
	 */
	public void submit(String taskId, TScrapped ts) {
		Map map = new HashMap();
		map.put("SCRAPPED_ID", ts.getScrappedId());
		ts.setWorkflowProcessName(WorkflowUtils
				.chooseWorkflowName("storage_scrap_v1"));
		ts.setWorkflowUserDefineTaskVariable(map);
		if(null==taskId||"".equals(taskId)){
			WorkflowService.createAndSignalProcessInstance(ts, ts.getScrappedId());
			this.scrappedJdbcDao.updateState(ts,"2",false);
		}else{
			WorkflowService.signalProcessInstance(ts,ts.getScrappedId(), null);
		}
		
	}

}
