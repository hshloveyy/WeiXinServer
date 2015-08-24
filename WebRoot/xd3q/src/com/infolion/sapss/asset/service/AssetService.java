package com.infolion.sapss.asset.service;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.service.BaseService;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.asset.AssetBapiUtils;
import com.infolion.sapss.asset.dao.AssetInfoHibernateDao;
import com.infolion.sapss.asset.dao.AssetJdbcDao;
import com.infolion.sapss.asset.dao.AssetMaintainHibernateDao;
import com.infolion.sapss.asset.dao.AssetUserHisHibernateDao;
import com.infolion.sapss.asset.domain.AssetInfo;
import com.infolion.sapss.asset.domain.AssetMaintain;
import com.infolion.sapss.asset.domain.AssetUserHis;


@Service
public class AssetService extends BaseService{
	

	@Autowired
	private AssetInfoHibernateDao assetInfoHibernateDao;
	
	@Autowired
	private AssetUserHisHibernateDao assetUserHisHibernateDao;
	
	@Autowired
	private AssetMaintainHibernateDao assetMaintainHibernateDao;
	
	@Autowired
	private AssetJdbcDao assetJdbcDao;
	
	public AssetInfoHibernateDao getAssetInfoHibernateDao() {
		return assetInfoHibernateDao;
	}

	public void setAssetInfoHibernateDao(AssetInfoHibernateDao assetInfoHibernateDao) {
		this.assetInfoHibernateDao = assetInfoHibernateDao;
	}
	
	public AssetUserHisHibernateDao getAssetUserHisHibernateDao() {
		return assetUserHisHibernateDao;
	}

	public void setAssetUserHisHibernateDao(
			AssetUserHisHibernateDao assetUserHisHibernateDao) {
		this.assetUserHisHibernateDao = assetUserHisHibernateDao;
	}

	public AssetMaintainHibernateDao getAssetMaintainHibernateDao() {
		return assetMaintainHibernateDao;
	}

	public void setAssetMaintainHibernateDao(
			AssetMaintainHibernateDao assetMaintainHibernateDao) {
		this.assetMaintainHibernateDao = assetMaintainHibernateDao;
	}
	
	public AssetJdbcDao getAssetJdbcDao() {
		return assetJdbcDao;
	}

	public void setAssetJdbcDao(AssetJdbcDao assetJdbcDao) {
		this.assetJdbcDao = assetJdbcDao;
	}

	public String getQueryAssetSql(Map<String, String> filter) {
		String sql = "select distinct t.*,t.assetType ASSETTYPE_D_ASSET_TYPE,t.businessScope businessScope_D_business_Scope " +
				",T.category  category_D_ASSET_category,T.STATE STATE_D_ASSET_STATE from t_asset_info t " +
		"left outer join T_ASSET_USERHIS u on t.assetInfoId=u.assetInfoId  where 1=1 ";

		if (filter != null && !filter.isEmpty()) {

			String comCode = filter.get("comCode");
			if (StringUtils.isNotBlank(comCode)) {
				sql += " and t.comCode = '" + comCode + "'";
			}
			String assetType = filter.get("assetType");
			if (StringUtils.isNotBlank(assetType)) {
				sql += " and t.assetType = '" + assetType + "'";
			}
			String businessScope = filter.get("businessScope");
			if (StringUtils.isNotBlank(businessScope)) {
				sql += " and t.businessScope = '" + businessScope + "'";
			}
			String costCenterName = filter.get("costCenterName");
			if (StringUtils.isNotBlank(costCenterName)) {
				sql += " and t.costCenterName like '%" + costCenterName + "%'";
			}
			
			String userMan = filter.get("userMan");
			if (StringUtils.isNotBlank(userMan)) {
				sql += " and u.userMan like '%" + userMan + "%'";
			}
			String assetName = filter.get("assetName");
			if (StringUtils.isNotBlank(assetName)) {
				sql += " and t.assetName like '%" + assetName
				+ "%'";
			}
			String assetConfig = filter.get("assetConfig");
			if (StringUtils.isNotBlank(assetConfig)) {
				sql += " and t.assetConfig like '%" + assetConfig + "%'";
			}
			String assetSerialNo = filter.get("assetSerialNo");
			if (StringUtils.isNotBlank(assetSerialNo)) {
				sql += " and t.assetSerialNo like '%" + assetSerialNo + "%'";
			}
			String outsideNo = filter.get("outsideNo");
			if (StringUtils.isNotBlank(outsideNo)) {
				sql += " and t.outsideNo like '%" + outsideNo + "%'";
			}
			String location = filter.get("location");
			if (StringUtils.isNotBlank(location)) {
				sql += " and t.location like '%" + location + "%'";
			}
			String spurchaseDate = filter.get("spurchaseDate");
			if (StringUtils.isNotBlank(spurchaseDate)) {
				sql += " and t.purchaseDate >= '" + spurchaseDate + "'";
			}
			String epurchaseDate = filter.get("epurchaseDate");
			if (StringUtils.isNotBlank(epurchaseDate)) {
				sql += " and t.purchaseDate <= '" + epurchaseDate + "'";
			}
			String contractPuchaseNo = filter.get("contractPuchaseNo");
			if (StringUtils.isNotBlank(contractPuchaseNo)) {
				sql += " and t.contractPuchaseNo like '%" + contractPuchaseNo + "%'";
			}
			String supplierName = filter.get("supplierName");
			if (StringUtils.isNotBlank(supplierName)) {
				sql += " and t.supplierName like '%" + supplierName + "%'";
			}
			String sapAssetNo = filter.get("sapAssetNo");
			if (StringUtils.isNotBlank(sapAssetNo)) {
				sql += " and t.sapAssetNo like '%" + sapAssetNo + "%'";
			}
			String scrapDate = filter.get("scrapDate");
			if (StringUtils.isNotBlank(scrapDate)) {
				sql += " and t.crapDate >= '" + scrapDate + "'";
			}
			String escrapDate = filter.get("escrapDate");
			if (StringUtils.isNotBlank(escrapDate)) {
				sql += " and t.scrapDate <= '" + escrapDate + "'";
			}
			String state = filter.get("state");
			if (StringUtils.isNotBlank(state)) {
				sql += " and t.state ='" + state + "'";
			}
			String category = filter.get("category");
			if (StringUtils.isNotBlank(category)) {
				sql += " and t.category ='" + category + "'";
			}
			String type = filter.get("type");
			if (StringUtils.isNotBlank(type)) {
				sql += " and t. like '%" + type + "%'";
			}
		}

		sql += " order by t.createrTime desc";

		return sql;

	}
	
	@Transactional(readOnly = true)
	public void saveOrUpdateAssetInfo(AssetInfo assetInfo) {
		this.assetInfoHibernateDao.saveOrUpdate(assetInfo);
	}

	public AssetInfo getAssetInfo(String assetInfoId) {
		return assetInfoHibernateDao.get(assetInfoId);
	}
	

	public AssetUserHis getAssetUserHis(String assetUserHisId) {
		return assetUserHisHibernateDao.get(assetUserHisId);
	}
	
	public void saveOrUpdateAssetUserHis(AssetUserHis userHis) {
		this.assetUserHisHibernateDao.saveOrUpdate(userHis);
	}
	
	public void saveOrUpdateAssetMaintain(AssetMaintain maintain) {
		this.assetMaintainHibernateDao.saveOrUpdate(maintain);
	}
	
	public void updateUserHis(String assetUserHisId,
			String strColName, String strColValue) {
		assetJdbcDao.updateUserHis(assetUserHisId, strColName,
				strColValue);
		AssetUserHis assetUserHis=getAssetUserHis(assetUserHisId);
		AssetInfo assetInfo=getAssetInfo(assetUserHis.getAssetInfoId());
		if("USERMAN".equals(strColName)){
			AssetBapiUtils.updateBapi(assetInfo,strColValue);
		}
	}
	
	public void updateMaintain(String assetMaintainId,
			String strColName, String strColValue) {
		assetJdbcDao.updateMaintain(assetMaintainId, strColName,
				strColValue);
	}
	
	public void deleteUserHis(String IdList) {
		String[] toIdList = IdList.split("\\|");
		for (int i = 0; i < toIdList.length; i++) {
			assetJdbcDao.deleteUserHis(toIdList[i]);
		}
	}
	
	public void deleteMaintain(String IdList) {
		String[] toIdList = IdList.split("\\|");
		for (int i = 0; i < toIdList.length; i++) {
			assetJdbcDao.deleteMaintain(toIdList[i]);
		}
	}
	
	public void deleteAssetInfo(String id) {
		AssetInfo obj = getAssetInfo(id);
		assetInfoHibernateDao.remove(obj);
	}
	
	public List queryDepts(String pdeptid){
		return assetJdbcDao.queryDepts(pdeptid);
	}
	
	public Map<String,String> submitToSap(AssetInfo info){
		String userMan = assetJdbcDao.queryUserMan(info.getAssetInfoId());
		if("".equals(userMan)) {
			throw new BusinessException("请填写使用人!");
		}
		if("1".equals(info.getFrozenMark())){
		AssetBapiUtils.lockAsset(info);
		}
		Map<String,String> map =  AssetBapiUtils.createBapi(info,userMan);
		if("0".equals(map.get("SUBRC"))){
			info.setSapAssetNo(map.get("WK_ANLN1"));
			assetInfoHibernateDao.update(info);
		}
		return map;

	}
	
	/**
	 * 该方法要更新SAP相应数据
	 * @param assetInfo
	 */
	@Transactional(readOnly = true)
	public void updateAssetInfo(AssetInfo assetInfo) {
		String userMan = assetJdbcDao.queryUserMan(assetInfo.getAssetInfoId());
		AssetInfo temp = getAssetInfo(assetInfo.getAssetInfoId());	
		temp.setLastModifyTime(DateUtils.getCurrTime(2));
		temp.setIsAvailable("1");
		if(StringUtils.isNotEmpty(temp.getSapAssetNo())&&
		   (!dealNull(temp.getComCode()).equals(dealNull(assetInfo.getComCode()))||
		   !dealNull(temp.getAssetName()).equals(dealNull(assetInfo.getAssetName()))||
		   !dealNull(temp.getAssetConfig()).equals(dealNull(assetInfo.getAssetConfig()))||
		   !dealNull(temp.getAssetName()).equals(dealNull(assetInfo.getAssetName()))||
		   !dealNull(temp.getAssetSerialNo()).equals(dealNull(assetInfo.getAssetSerialNo()))||
		   !dealNull(temp.getOutsideNo()).equals(dealNull(assetInfo.getOutsideNo()))||
		   !dealNull(temp.getLocation()).equals(dealNull(assetInfo.getLocation()))||
		   !dealNull(temp.getBusinessScope()).equals(dealNull(assetInfo.getBusinessScope()))||
		   !dealNull(temp.getCostCenter()).equals(dealNull(assetInfo.getCostCenter())))){
			AssetBapiUtils.updateBapi(assetInfo,userMan);
		}
		if(StringUtils.isNotEmpty(temp.getSapAssetNo())&&temp.getFrozenMark()!=assetInfo.getFrozenMark()){
			if("1".equals(assetInfo.getFrozenMark())){
				AssetBapiUtils.lockAsset(assetInfo);
			}
			else {
				AssetBapiUtils.unlockAsset(assetInfo);
			}
			
		}
		BeanUtils.copyProperties(assetInfo, temp, new String[]{"creator","createrTime"});
        this.assetInfoHibernateDao.saveOrUpdate(temp);
	}
	
	private String dealNull(String str){
		if(str==null) return "";
		return str.trim();
	}
}


