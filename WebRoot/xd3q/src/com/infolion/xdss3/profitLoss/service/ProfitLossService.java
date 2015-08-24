/*
 * @(#)ProfitLossService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月21日 11点48分47秒
 *  描　述：创建
 */
package com.infolion.xdss3.profitLoss.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.basicApp.authManage.domain.User;
import com.infolion.platform.basicApp.authManage.service.UserService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.console.login.service.LoginService;
import com.infolion.platform.console.login.service.LoginXdss3Service;
import com.infolion.platform.console.org.service.SysDeptService;
import com.infolion.platform.console.org.service.SysRoleService;
import com.infolion.platform.console.org.service.SysUserService;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.sapss.bapi.dto.MasterDataDto;
import com.infolion.xdss3.masterData.domain.SyncMasterLog;
import com.infolion.xdss3.masterData.service.SyncMasterLogService;
import com.infolion.xdss3.profitLoss.dao.ProfitLossJdbcDao;
import com.infolion.xdss3.profitLoss.domain.ProfitLoss;
import com.infolion.xdss3.profitLossGen.service.ProfitLossServiceGen;

/**
 * <pre>
 * 合同信息及市场单价维护(ProfitLoss)服务类
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
public class ProfitLossService extends ProfitLossServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	@Autowired
    private ProfitLossJdbcDao profitLossJdbcDao;
	@Autowired
    private LoginService loginService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    
    /**
     * @param profitLossJdbcDao the profitLossJdbcDao to set
     */
    public void setProfitLossJdbcDao(ProfitLossJdbcDao profitLossJdbcDao) {
        this.profitLossJdbcDao = profitLossJdbcDao;
    }
	
	/**
     * @return the profitLossJdbcDao
     */
    public ProfitLossJdbcDao getProfitLossJdbcDao() {
        return profitLossJdbcDao;
    }
    
    

    public void setLoginService(LoginService loginService){
        this.loginService = loginService;
    }

    public void setSysDeptService(SysDeptService sysDeptService){
        this.sysDeptService = sysDeptService;
    }

    public void setSysUserService(SysUserService sysUserService){
        this.sysUserService = sysUserService;
    }

    public void setSysRoleService(SysRoleService sysRoleService){
        this.sysRoleService = sysRoleService;
    }
    
    @Autowired
    private UserService userService;

    public void setUserService(UserService userService){
        this.userService = userService;
    }
    
    @Autowired
    private LoginXdss3Service loginXdss3Service;
    
    public void setLoginXdss3Service(LoginXdss3Service loginXdss3Service){
        this.loginXdss3Service = loginXdss3Service;
    }

	@Autowired
	private SyncMasterLogService syncMasterLogService;

	/**
	 * @param syncMasterLogService
	 *            the syncMasterLogService to set
	 */
	public void setSyncMasterLogService(SyncMasterLogService syncMasterLogService)
	{
		this.syncMasterLogService = syncMasterLogService;
	}

	
    /**
	 * 保存  
	 *   
	 * @param profitLoss
	 */
	public void _save(ProfitLoss profitLoss)
	{
		//计算合计列的值
		//含税总金额
		profitLoss.setTaxincludevlaue(profitLoss.getTotalvalue().multiply(new BigDecimal(profitLoss.getTaxrate()).add(new BigDecimal("1"))));
		//估计售价总额
		profitLoss.setEstimatevalue(profitLoss.getMarketunitprice().multiply(profitLoss.getQuantity()));
		//单位浮亏/盈
		profitLoss.setUnitprofitloss(profitLoss.getMarketunitprice().subtract(profitLoss.getUnitprice()));
		//总浮亏/盈
		profitLoss.setTotalprofitloss(profitLoss.getEstimatevalue().subtract(profitLoss.getTotalvalue()));
		// 扣除持仓费用后的总浮盈
		profitLoss.setRemaintotalvalue(profitLoss.getTotalprofitloss().subtract(profitLoss.getPositionvalue()));
		if (profitLoss.getRemaintotalvalue() == null)
			profitLoss.setRemaintotalvalue(new BigDecimal("0"));
		
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(profitLoss);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		//profitLoss.setProfitlossid(null);
		profitLoss.setClient("800");
       	profitLossHibernateDao.getHibernateTemplate().saveOrUpdate(profitLoss);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(profitLoss);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param profitLossSet
	 */
	public void _saveOrUpdate(Set<ProfitLoss> profitLossSet
,BusinessObject businessObject		
	)
	{
		Iterator<ProfitLoss> items = profitLossSet.iterator();
		while (items.hasNext())
		{
			ProfitLoss profitLoss =  (ProfitLoss) items.next();
			//_saveOrUpdate(profitLoss,businessObject);
			_save(profitLoss);
		}
	}
	
	/**
	 * 重新读取  
	 *   
	 * @param map
	 * @return
	 */
	public void _reload(Map<String,Object> parm1, Map<String,Object> parm2){
		Map<String,Object> tbParm = new HashMap<String,Object>();
		String key ="",value="";
		String companyId = (String) parm1.get("BUKRS");
		String dateValue = (String) parm1.get("BUDAT");
		dateValue = dateValue.replace("-", "");
		parm1.put("BUDAT", dateValue); // RFC日期传递8位
		String deptId = (String) parm2.get("WERKS"); //部门ID
		String deptSql = "";
		if (StringUtils.isNotBlank(deptId)){
            // 根据部门ID取得部门CODE传入SAP
            List<Map<String, String>> deptCodeList = profitLossJdbcDao.getDeptCodeListById(deptId);
            for (Map deptCodeMap : deptCodeList) {
                tbParm.put("WERKS", deptCodeMap.get("DEPT_CODE"));
            }
	            
			String[] deptList = StringUtils.splitString(deptId, ",");
			for (int i = 0; i < deptList.length; i++) {
				deptSql +=  " '" + deptList[i].toString() + "'" + ",";
			}
			if (StringUtils.isNotBlank(deptSql)){
				deptSql = deptSql.substring(0, deptSql.length()-1);	
				deptSql = "(" + deptSql + ")";
			}
		}
		String sql = "from ProfitLoss where 1=1 ";
		if (StringUtils.isNotBlank(companyId))
			sql += " and companyid = '" + companyId + "'";
		if (StringUtils.isNotBlank(dateValue))
			sql += " and datevalue = '" + dateValue + "'";
		if (StringUtils.isNotBlank(deptSql))
			sql += " and deptid in " + deptSql ;
		List<ProfitLoss> profitLossList = this.profitLossHibernateDao.find(sql);
		//删除已经存在数据
		for (int i = 0; i < profitLossList.size(); i++) {
			ProfitLoss profitLossDel = (ProfitLoss)profitLossList.get(i);
			this._delete(profitLossDel);
		}
		//从RFC调用数据循环保存
		MasterDataDto data = ExtractSapData.getProfitLossData(parm1,tbParm);
		List<Map<String, String>> mapList = data.getData();
		List<ProfitLoss> reSaveList = new ArrayList();
		for (int i=0; i<mapList.size(); i++){
			Map<String, String> map = mapList.get(i);
			Iterator<Entry<String, String>> it = map.entrySet().iterator();
			ProfitLoss profitLoss = new ProfitLoss();
			profitLoss.setMarketunitprice(new BigDecimal(0));
			profitLoss.setCompanyid(companyId);
			profitLoss.setDatevalue(dateValue);
			profitLoss.setPositionvalue(new BigDecimal(0)); // 持仓费用
			 while (it.hasNext()){
				 Map.Entry<String, String> s = (Map.Entry<String, String>)it.next();
				 key = s.getKey();
				 value = s.getValue().trim();
			 	 if (key.equals("WERKS")){					//部门
			 		profitLoss.setDeptid(value);
			 	 } else if (key.equals("VERKF")){			//外部纸质合同号
			 		profitLoss.setEkko_unsez(value);       
			 	 } else if (key.equals("MATKL")){			//物料组
			 		profitLoss.setMaterial_group(value);   
			 	 } else if (key.equals("MATNR")){			//物料号
				    profitLoss.setMaterial_code(value);			 		 
			 	 } else if (key.equals("TXZ01")){			//物料名称
			 		profitLoss.setMeterial_name(value);    
			 	 }  else if (key.equals("EBELN")){			//采购订单
			 		profitLoss.setOrderid(value);
			 	 } else if (key.equals("CHARG")){			//批次
			 		 profitLoss.setBatch_no(value);			
			 	 } else if (key.equals("LIFNR")){			//供应商
			 		profitLoss.setProviderid(value);
			 	 } else if (key.equals("NAME1")){
			 		profitLoss.setProvidername(value);
			 	 } else if (key.equals("SHUILV")){			//税率
			 		 if (StringUtils.isBlank(value))
				 			value = "0";			 		 
			 		profitLoss.setTaxrate(value);
			 	 } else if (key.equals("MEINS")){		    //单位
				 	profitLoss.setUnit(value);
			 	 } else if (key.equals("MENGE")){			//数量
			 		 if (StringUtils.isBlank(value))
			 			value = "0";
			 		profitLoss.setQuantity(new BigDecimal(value));
			 	 } else if (key.equals("NETPR")){			//单价
			 		 if (StringUtils.isBlank(value))
			 			 value = "0";
			 		 profitLoss.setUnitprice(new BigDecimal(value));
			 	 } else if (key.equals("DMBTR")){			//总金额（成本）
			 		 if (StringUtils.isBlank(value))
			 			 value = "0";
			 		 profitLoss.setTotalvalue(new BigDecimal(value));
			 	 } else if (key.equals("KEMU")){			//科目
			 		 profitLoss.setSubject(value);
			 	 } else if (key.equals("BATXT")){			//类型
			 		 profitLoss.setType(" ");
			 	 } else if (key.equals("FLAG")){			//类型
			 	     profitLoss.setFlag(value);
			 	 }else if (key.equals("BUDAT")){			//过账日期
			 	     profitLoss.setBuDate(value);
			 	     profitLoss.setStorageTime(String.valueOf(DateUtils.getIntervalDays(dateValue, value)));        //库龄
			 	 }else if (key.equals("LGORT")){			//过账日期
			 	     profitLoss.setWareHouse(value);        //仓库
			 	 }
			 }
			 this._save(profitLoss);
			 reSaveList.add(profitLoss);
		}
		profitLossHibernateDao.flush();
		
		// 批量更新立项号、采购合同号、业务类型
		profitLossJdbcDao.updateProfitLossInfo(companyId, dateValue, deptSql);
		
	}
	
	/**
	 * 保存修改的信息值
	 * @param strProfitlossidList
	 * @param strMarketunitpriceList
	 */
	public void saveChange(String strProfitlossidList,String strMarketunitpriceList, String strMaxlosscommentList, String strPositionvalueList){
		String[] IdList = strProfitlossidList.split("\\|");
		String[] valueList = strMarketunitpriceList.split("\\|");
		String[] maxlosscommentList = strMaxlosscommentList.split("\\|");
		String[] positionvalueList = strPositionvalueList.split("\\|");
		for (int i = 0; i < IdList.length; i++) {
			ProfitLoss profitLoss =  this._get(IdList[i]);
            if (StringUtils.isNullBlank(IdList[i])) {
                continue;
            }
			profitLoss.setMarketunitprice(new BigDecimal(valueList[i]));
			profitLoss.setMaxlosscomment(maxlosscommentList[i]);
			profitLoss.setPositionvalue(new BigDecimal(positionvalueList[i]));
			this._save(profitLoss);
			
		}
	}
	
	/**
     * @创建作者：邱杰烜
     * @创建日期：2010-12-01
     * 每月8号自动重载"浮动盈亏"的数据（定时器调度执行）
     * 在执行重载时，先把帐号为admin的登陆信息放入UserContext里
     */
	public void reloadProfitloss(){
	    /* 2010-12-09  邱杰烜  加入UserContext，否则在插入数据时会拋空指针异常  */
	    Map bdpUserBaseInfo = loginXdss3Service.getBDPUserBaseInfo("admin");
        String bdpUserId = (String) bdpUserBaseInfo.get("USERID");
	    com.infolion.platform.basicApp.authManage.domain.UserContext userContext = new com.infolion.platform.basicApp.authManage.domain.UserContext();
	    User user = this.userService.getUser(bdpUserId);
	    Set dataAUTHXObjectValue = this.userService.getUserDataAUTHXobjectValues(user);
	    Set userAuthMoveauthResource = this.userService.getOtherUserAuthMoveAuthResources(user);
	    Set moveDataAUTHXObjectValue = this.userService.getUserAuthMoveDataAUTHXobjectValues(user);
	    Set userAllRoles = this.userService.getUserAllRoles(bdpUserId, "800");
	    userContext.setClient("800");
	    userContext.setLanguageCode("1");
	    userContext.setLanguageIso("ZH");
	    userContext.setUser(user);
	    userContext.setDataAUTHXObjectValue(dataAUTHXObjectValue);
	    userContext.setMoveResource(userAuthMoveauthResource);
	    Set propertyAuths = this.userService.getPropertyAuths(user);
	    userContext.setPropertyAuths(propertyAuths);
	    userContext.setUserAllRoles(userAllRoles);
	    com.infolion.platform.basicApp.authManage.UserContextHolder.setCurrentContext(userContext);
	    /* 2010-12-09 **/
	    Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.DATE, -1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfLastMonth = simpleDateFormat.format(cal.getTime());
        List<Map<String, String>> companyIds = this.profitLossJdbcDao.getCompanyIds();
        try{
            log.debug("----------------开始重载浮动盈亏数据：公司(" + companyIds.size() + "笔)，日期(" + lastDayOfLastMonth + ")……");
            for(int i=0; i<companyIds.size(); i++){
                Map<String,Object> map = new HashMap<String,Object>();
                Map<String,Object> tbMap = new HashMap<String,Object>();
                String companyId = companyIds.get(i).get("COMPANYID");
                map.put("BUKRS", companyId);
                map.put("BUDAT", lastDayOfLastMonth);
                tbMap.put("WERKS", "");
                log.debug("----------------重载公司为" + companyId + "、日期为" + lastDayOfLastMonth + "的浮动盈亏数据……");
                this._reload(map, tbMap);
                log.debug("----------------成功重载公司为" + companyId + "、日期为" + lastDayOfLastMonth + "的浮动盈亏数据！");
                map.clear();
            }
            SyncMasterLog syncMasterLog = new SyncMasterLog();
            syncMasterLog.setIssucceed("Y");
            syncMasterLog.setSyncdate(lastDayOfLastMonth);
            syncMasterLog.setSynctablename("YPROFITLOSS");
            syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
            syncMasterLog.setErrMessage(" ");
            syncMasterLogService._saveSyncMasterLog(syncMasterLog);
            log.debug("----------------成功重载浮动盈亏数据：公司(共" + companyIds.size() + "笔)，日期(" + lastDayOfLastMonth + ")！");
        }catch(Exception e){
        	String errMessage = e.getMessage() + e.getCause();
            SyncMasterLog syncMasterLog = new SyncMasterLog();
            syncMasterLog.setIssucceed("N");
            syncMasterLog.setSyncdate(lastDayOfLastMonth);
            syncMasterLog.setSynctablename("YPROFITLOSS");
            syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
            syncMasterLog.setErrMessage(errMessage);
            syncMasterLogService._saveSyncMasterLog(syncMasterLog);
            log.error("----------------重载浮动盈亏数据发生错误，错误信息：" + e.getMessage());
            e.printStackTrace();
        }
	}

	/**
     * 重新读取  到 t_temp_profitloss 临时表中
     *   
     * @param map
     * @return
     */
    public void _reload2T_TEMP_PROFITLOSS(Map<String,Object> parm1, Map<String,Object> parm2){
        Map<String,Object> tbParm = new HashMap<String,Object>();
        String key ="",value="";
        String companyId = (String) parm1.get("BUKRS");
        String dateValue = (String) parm1.get("BUDAT");
        dateValue = dateValue.replace("-", "");
        parm1.put("BUDAT", dateValue); // RFC日期传递8位
        String deptId = (String) parm2.get("WERKS"); //部门ID
        String deptSql = "";
        if (StringUtils.isNotBlank(deptId)){
            // 根据部门ID取得部门CODE传入SAP
            List<Map<String, String>> deptCodeList = profitLossJdbcDao.getDeptCodeListById(deptId);
            for (Map deptCodeMap : deptCodeList) {
                tbParm.put("WERKS", deptCodeMap.get("DEPT_CODE"));
            }
                
            String[] deptList = StringUtils.splitString(deptId, ",");
            for (int i = 0; i < deptList.length; i++) {
                deptSql +=  " '" + deptList[i].toString() + "'" + ",";
            }
            if (StringUtils.isNotBlank(deptSql)){
                deptSql = deptSql.substring(0, deptSql.length()-1); 
                deptSql = "(" + deptSql + ")";
            }
        }
        String sql = "SELECT * FROM T_TEMP_PROFITLOSS where 1=1 ";
        if (StringUtils.isNotBlank(companyId))
            sql += " and companyid = '" + companyId + "'";
        if (StringUtils.isNotBlank(dateValue))
            sql += " and datevalue = '" + dateValue + "'";
        if (StringUtils.isNotBlank(deptSql))
            sql += " and deptid in " + deptSql ;
       
        List list = null;
        list = profitLossJdbcDao.getJdbcTemplate().queryForList(sql);
        // 如果查有记录，那里直接返回
//        if (list!=null && list.size()>0) {
//            log.debug("如果查有记录，则直接返回");
//            return;
//        }
        
        //删除已经存在数据
        String delSQL = " delete from T_TEMP_PROFITLOSS where 1=1 ";
        if (StringUtils.isNotBlank(companyId))
            delSQL += " and companyid = '" + companyId + "'";
        if (StringUtils.isNotBlank(dateValue))
            delSQL += " and datevalue = '" + dateValue + "'";
        if (StringUtils.isNotBlank(deptSql))
            delSQL += " and deptid in " + deptSql ;
        profitLossJdbcDao.getJdbcTemplate().execute(delSQL);
        log.debug("-------------删除数据SQL："+delSQL);
        
        //从RFC调用数据循环保存
        MasterDataDto data = ExtractSapData.getProfitLossData(parm1,tbParm);
        List<Map<String, String>> mapList = data.getData();
        List<ProfitLoss> reSaveList = new ArrayList<ProfitLoss>();
        for (int i=0; i<mapList.size(); i++){
            Map<String, String> map = mapList.get(i);
            Iterator<Entry<String, String>> it = map.entrySet().iterator();
            ProfitLoss profitLoss = new ProfitLoss();
            profitLoss.setMarketunitprice(new BigDecimal(0));
            profitLoss.setCompanyid(companyId);
            profitLoss.setDatevalue(dateValue);
            profitLoss.setPositionvalue(new BigDecimal(0)); // 持仓费用
             while (it.hasNext()){
                 Map.Entry<String, String> s = (Map.Entry<String, String>)it.next();
                 key = s.getKey();
                 value = s.getValue().trim();
                 if (key.equals("WERKS")){                  //部门
                    profitLoss.setDeptid(value);
                 } else if (key.equals("VERKF")){           //外部纸质合同号
                    profitLoss.setEkko_unsez(value);       
                 } else if (key.equals("MATKL")){           //物料组
                    profitLoss.setMaterial_group(value);   
                 } else if (key.equals("MATNR")){           //物料号
                    profitLoss.setMaterial_code(value);                  
                 } else if (key.equals("TXZ01")){           //物料名称
                    profitLoss.setMeterial_name(value);    
                 }  else if (key.equals("EBELN")){          //采购订单
                    profitLoss.setOrderid(value);
                 } else if (key.equals("CHARG")){           //批次
                     profitLoss.setBatch_no(value);         
                 } else if (key.equals("LIFNR")){           //供应商
                    profitLoss.setProviderid(value);
                 } else if (key.equals("NAME1")){
                    profitLoss.setProvidername(value);
                 } else if (key.equals("SHUILV")){          //税率
                     if (StringUtils.isBlank(value))
                            value = "0";                     
                    profitLoss.setTaxrate(value);
                 } else if (key.equals("MEINS")){           //单位
                    profitLoss.setUnit(value);
                 } else if (key.equals("MENGE")){           //数量
                     if (StringUtils.isBlank(value))
                        value = "0";
                    profitLoss.setQuantity(new BigDecimal(value));
                 } else if (key.equals("NETPR")){           //单价
                     if (StringUtils.isBlank(value))
                         value = "0";
                     profitLoss.setUnitprice(new BigDecimal(value));
                 } else if (key.equals("DMBTR")){           //总金额（成本）
                     if (StringUtils.isBlank(value))
                         value = "0";
                     profitLoss.setTotalvalue(new BigDecimal(value));
                 } else if (key.equals("KEMU")){            //科目
                     profitLoss.setSubject(value);
                 } else if (key.equals("BATXT")){           //类型
                     profitLoss.setType(" ");
                 } else if (key.equals("FLAG")){            //类型
                     profitLoss.setFlag(value);
                 }else if (key.equals("BUDAT")){			//过账日期
			 	     profitLoss.setBuDate(value);
			 	     profitLoss.setStorageTime(String.valueOf(DateUtils.getIntervalDays(dateValue, value)));        //库龄
			 	 }else if (key.equals("LGORT")){
			 	     profitLoss.setWareHouse(value);        //仓库
			 	 }
             }
             reSaveList.add(profitLoss);
        }
        profitLossJdbcDao.saveProfitloss2t_temp_profitloss(reSaveList);
        
        // 批量更新立项号、采购合同号、业务类型
        profitLossJdbcDao.updateT_Temp_ProfitLoss(companyId, dateValue, deptSql);
        
    }
}