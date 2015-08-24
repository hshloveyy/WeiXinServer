package com.infolion.xdss3.masterData.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.sapss.bapi.ConnectManager;
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.xdss3.masterData.SyncMasterDataConstants;
import com.infolion.xdss3.masterData.dao.BsegHibernateDao;
import com.infolion.xdss3.masterData.dao.BsegJdbcDao;
import com.infolion.xdss3.masterData.domain.Bseg;
import com.infolion.xdss3.masterData.domain.SyncMasterLog;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

/**
 * @创建作者：邱杰烜
 * @创建时间：2010-12-16
 */
@Service
public class BsegService extends BaseService{
    
    @Autowired
    private BsegHibernateDao bsegHibernateDao;
    
    @Autowired
    private BsegJdbcDao bsegJdbcDao;
    
    @Autowired
    private SyncMasterLogService syncMasterLogService;

    public void setBsegHibernateDao(BsegHibernateDao bsegHibernateDao){
        this.bsegHibernateDao = bsegHibernateDao;
    }

    public void setBsegJdbcDao(BsegJdbcDao bsegJdbcDao){
        this.bsegJdbcDao = bsegJdbcDao;
    }
    
    public void setSyncMasterLogService(SyncMasterLogService syncMasterLogService){
        this.syncMasterLogService = syncMasterLogService;
    }

    /**
     * 取得要同步的Bseg数据
     */
    public void _executeRfcGetMasterData(String syncDate, String zdate){

        ConnectManager manager = ConnectManager.manager;
        manager.getClient();
        JCO.Client client = null;
        try{
            IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate(SyncMasterDataConstants.BSEG_RFCFunctionName);
            if(ftemplate != null){
                JCO.Function function = ftemplate.getFunction();
                client = JCO.getClient(manager.poolName);
                JCO.ParameterList input = function.getImportParameterList();
                int startYear;
                int endYear = Integer.valueOf(syncDate.substring(0, 4));
                List<Map<String, String>> companyCodes = this.bsegJdbcDao.getAllCompanyCodes();
                /*
                 * RFC(ZF_GETBSEG)的参数列表
                 * I_GJAHR   会计年度
                 * I_BUKRS   公司代码
                 * ZDATE     上次同步时间   
                 * SYNCDATE  当前同步时间
                 * 全量同步：需要循环每个公司从2008年到当前年度去删除外围数据并作为参数去SAP获取数据插入外围数据库
                 * 增量同步：只需传入ZDATE和SYNCDATE去SAP获取数据，并判断是否存在外围数据库(是-更新，否-新增)
                 */
                if(StringUtils.isNotBlank(zdate)){  // 若上一次同步时间不为空，则执行增量同步
                    input.setValue(zdate, "ZDATE");
                    input.setValue(syncDate, "SYNCDATE");
                    client.execute(function);
                    JCO.Table returnDatas = function.getTableParameterList().getTable("OT_BSEG");
                    List<Map<String, String>> list = ExtractSapData.getList(returnDatas);
                    log.debug("----------------开始增量同步更新 " + list.size() + " 笔凭证数据(BSEG)！");
                    this._ayncIncMasterDate(list);
                    log.debug("----------------成功增量同步更新 " + list.size() + " 笔凭证数据(BSEG)！");
                }else{                              // 否则执行全量同步
                    for(int i=0; i<companyCodes.size(); i++){
                        String companyCode = companyCodes.get(i).get("ID");
                        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                        for(startYear=2008; startYear<=endYear; startYear++){
                            log.debug("----------------删除公司为 " + companyCode + "，年度为 " + startYear + " 的所有凭证数据(BSEG)！");
                            this.bsegJdbcDao.deleteCompanys(companyCode, startYear);      // 根据公司代码从删除YGETBSEG表中的数据
                            input.setValue(startYear,   "I_GJAHR");
                            input.setValue(companyCode, "I_BUKRS");
                            client.execute(function);
                            JCO.Table returnDatas = function.getTableParameterList().getTable("OT_BSEG");
                            list = ExtractSapData.getList(returnDatas);
                            log.debug("----------------开始更新公司为 " + companyCode + "，年度为 " + startYear + " 的凭证数据(BSEG)共 " + list.size() + " 笔！");
                            this._ayncAllMasterData(list);
                            log.debug("----------------成功更新公司为 " + companyCode + "，年度为 " + startYear + " 的凭证数据(BSEG)共 " + list.size() + " 笔！");
                            list.clear();
                        }
                    }
                }
                SyncMasterLog syncMasterLog = new SyncMasterLog();
                syncMasterLog.setIssucceed("Y");
                syncMasterLog.setSyncdate(syncDate);
                syncMasterLog.setSynctablename("YGETBSEG");
                syncMasterLog.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
                syncMasterLog.setErrMessage(" ");
                syncMasterLogService._saveSyncMasterLog(syncMasterLog);
            }else{
                log.error("----------------取得凭证数据(BSEG)发生错误：目标系统上不存在RFC:" + SyncMasterDataConstants.BSEG_RFCFunctionName);
            }
        }catch(Exception ex){
            log.error("----------------取得凭证数据(BSEG)发生错误" + ex);
        }finally{
            manager.cleanUp();
        }
    }
    
    /**
     * 执行全量同步Bseg
     */
    public void _ayncAllMasterData(List<Map<String, String>> bsegList) throws Exception{
        int count = 0;
        int totel = bsegList.size();        
//        Session session =bsegHibernateDao.getSessionFactory().openSession();
//         //开始事务           
//        Transaction tx = session.beginTransaction();         
//        for(Map<String, String> map : bsegList){
//            Bseg bseg = new Bseg();
//            ExBeanUtils.setBeanValueFromMap(bseg, map);
//            this.bsegHibernateDao.save(bseg);
//            if(count!=0 && count%1000==0){  // 每4000笔数据刷一次缓存
//                this.bsegHibernateDao.flush();
//                this.bsegHibernateDao.clear();
//                tx.commit();                     
//                tx = session.beginTransaction();  
//                log.debug("----------------已刷入 " + count + " 笔进数据库，剩余 " + (totel-count) + " 笔！");
//            }
//            count++;
//        }
//        this.bsegHibernateDao.flush();
//        this.bsegHibernateDao.clear();
//        tx.commit(); 
//        session.close();
        bsegJdbcDao.insertBsegs2(bsegList);
//        log.debug("----------------已刷入 " + count + " 笔进数据库！");
    }
    
    /**
     * 执行增量同步Bseg
     */
    public void _ayncIncMasterDate(List<Map<String, String>> bsegList) throws Exception{
        for(Map<String, String> map : bsegList){
            Bseg bsegSAP = new Bseg();
            ExBeanUtils.setBeanValueFromMap(bsegSAP, map);
            // 若该Bseg已经在外围数据库里存在，则执行更新操作；否则直接往数据库里插入一条新数据
            Bseg bsegOUT = this.bsegHibernateDao.getBsegIfExists(bsegSAP.getBukrs(), bsegSAP.getBelnr(), bsegSAP.getGjahr(), bsegSAP.getBuzei());
            if(bsegOUT == null){
                log.debug("----------------从SAP新增 1 笔BUKRS='" + bsegSAP.getBukrs() +
                        "',BELNR='" + bsegSAP.getBelnr() +
                        "',GJAHR='" + bsegSAP.getGjahr() +
                        "',BUZEI='" + bsegSAP.getBuzei() +
                "'的凭证数据！");
                this.bsegHibernateDao.save(bsegSAP);
            }else{
                log.debug("----------------从外围更新 1 笔BUKRS='" + bsegOUT.getBukrs() +
                        "',BELNR='" + bsegOUT.getBelnr() +
                        "',GJAHR='" + bsegOUT.getGjahr() +
                        "',BUZEI='" + bsegOUT.getBuzei() +
                "'的凭证数据！");
                String id = bsegOUT.getBsegid();
                ExBeanUtils.setBeanValueFromMap(bsegOUT, map);
                bsegOUT.setBsegid(id);
                this.bsegHibernateDao.saveOrUpdate(bsegOUT);
            }
        }
    }
}