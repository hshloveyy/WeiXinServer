/*
 * @(#)VatDetailService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年12月12日 15点58分46秒
 *  描　述：创建
 */
package com.infolion.xdss3.vatdetail.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.sapss.bapi.ConnectManager_bw;
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.xdss3.vatdetail.dao.VatDetailJdbcDao;
import com.infolion.xdss3.vatdetail.domain.SapBusType;
import com.infolion.xdss3.vatdetail.domain.VatDetail;
import com.infolion.xdss3.vatdetail.domain.VatGoodDetail;
import com.infolion.xdss3.vatdetailGen.service.VatDetailServiceGen;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

/**
 * <pre>
 * 期初已到税票未进仓(税额)(VatDetail)服务类
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
public class VatDetailService extends VatDetailServiceGen {
    private Log log = LogFactory.getFactory().getLog(this.getClass());

    private String rfcName = "ZFIVAT";

    @Autowired
    VatDetailJdbcDao vatDetailJdbcDao = null;

    public void setVatDetailJdbcDao(VatDetailJdbcDao vatDetailJdbcDao) {
        this.vatDetailJdbcDao = vatDetailJdbcDao;
    }

    /**
     * 取得SAP数据
     */
    public List<VatGoodDetail> fetchSAPData(String bukrs, String gsber, String begda, String endda,
            String matnr, String matgroup, String type, String taxp) throws Exception {

        if (!StringUtils.isNullBlank(begda)) {
            begda = begda.replaceAll("-", "");
        }
        if (!StringUtils.isNullBlank(endda)) {
            endda = endda.replaceAll("-", "");
        }

        JCO.Table vatDatas = null;
        JCO.Table taxamountDatas = null;

        ConnectManager_bw manager = ConnectManager_bw.manager;
        manager.getClient();
        JCO.Client client = null;
        List<Map<String, String>> vatList = new ArrayList<Map<String, String>>();
        List<Map<String, String>> taxamountList = new ArrayList<Map<String, String>>();
        try {
            IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate(rfcName);
            if (ftemplate != null) {
                JCO.Function function = ftemplate.getFunction();
                client = JCO.getClient(manager.poolName);
                JCO.ParameterList input = function.getImportParameterList();

                // ① 公司代码：单选 BUKRS(必填)
                input.setValue(bukrs, "BUKRS");

                // ② 业务范围：多选 模糊 T_GSBER(不输入,则传入空串,*号则传入所有业务范围)
                JCO.Table t_gsber = function.getTableParameterList().getTable("T_GSBER");
                if ("*".equals(gsber)) {
//                    List<Map> allGsberList = vatDetailJdbcDao.getAllGsber();
//                    for (Map map : allGsberList) {
//                        t_gsber.appendRow();
//                        t_gsber.setValue(map.get("DEPTID"), "STR");
//                    }
                	 t_gsber.appendRow();
                     t_gsber.setValue("*", "STR");
                } else if (!StringUtils.isNullBlank(gsber)) {
                    String tx[] = gsber.split(",");
                    for (String str : tx) {
                        t_gsber.appendRow();
                        t_gsber.setValue(str, "STR");
                    }
                } else {
//                    t_gsber.appendRow();
//                    t_gsber.setValue("", "STR");
                }

                // ③ 日期（期间）：开始日期BEGDA，结束日期，ENDDA(必填)
                input.setValue(begda, "BEGDA");

                // ③ 日期（期间）：开始日期BEGDA，结束日期，ENDDA(必填)
                input.setValue(endda, "ENDDA");

                // ④ 物料号：多选 模糊 T_MATNR(不输入则不传入任何参数；默认*号则传入*号；输入“空”、“无”则传入空格)
                JCO.Table t_matnr = function.getTableParameterList().getTable("T_MATNR");
                if ("*".equals(matnr)) {
                    t_matnr.appendRow();
                    t_matnr.setValue("*", "STR");
                } else if ("空".equals(matnr) || "无".equals(matnr)) {
//                    t_matnr.appendRow();
//                    t_matnr.setValue(" ", "STR");
                } else if (!StringUtils.isNullBlank(matnr)) {
                    String tx[] = matnr.split(",");
                    for (String str : tx) {
                        t_matnr.appendRow();
                        t_matnr.setValue(str, "STR");
                    }
                }

                // ⑤ 物料组：多选 模糊
                // T_MATGROUP(不输入则不传入任何参数；默认*号则传入所有+空格；输入“空”、“无”则传入空格)
                JCO.Table t_matgroup = function.getTableParameterList().getTable("T_MATGROUP");
                if ("*".equals(matgroup)) {
//                    List<Map> allList = vatDetailJdbcDao.getAllBm_material_group();
//                    for (Map map : allList) {
//                        t_matgroup.appendRow();
//                        t_matgroup.setValue(map.get("ID"), "STR");
//                    }
//                    // 补一个空格
//                    t_matgroup.appendRow();
//                    t_matgroup.setValue(" ", "STR");
                	 t_matgroup.appendRow();
                	 t_matgroup.setValue("*", "STR");
                } else if ("空".equals(matgroup) || "无".equals(matgroup)) {
                    // 补一个空格
//                    t_matgroup.appendRow();
//                    t_matgroup.setValue(" ", "STR");
                } else if (!StringUtils.isNullBlank(matgroup)) {
                    String tx[] = matgroup.split(",");
                    for (String str : tx) {
                        t_matgroup.appendRow();
                        t_matgroup.setValue(str, "STR");
                    }
                }

                // ⑥ 业务类型：多选 T_TYPE(不输入则不传入任何参数；默认*号则传入所有+空格；输入“空”、“无”则传入空格)
                JCO.Table t_type = function.getTableParameterList().getTable("T_TYPE");
                if ("*".equals(type)) {
//                    List<String> allList = SapBusType.getbusTypeList();
//                    for (String str : allList) {
//                        t_type.appendRow();
//                        t_type.setValue(str, "STR");
//                    }
//                    // 补一个空格
//                    t_type.appendRow();
//                    t_type.setValue(" ", "STR");
                	t_type.appendRow();
                	t_type.setValue("*", "STR");
                } else if ("空".equals(type) || "无".equals(type)) {
                    // 补一个空格
//                    t_type.appendRow();
//                    t_type.setValue(" ", "STR");
                } else if (!StringUtils.isNullBlank(type)) {
                    String tx[] = type.split(",");
                    for (String str : tx) {
                        t_type.appendRow();
                        t_type.setValue(str, "STR");
                    }
                }

                // ⑦ 税率：多选 T_TAXP(不输入则不传入任何参数；默认*号传入*号;)
                JCO.Table t_taxp = function.getTableParameterList().getTable("T_TAXP");
                if ("*".equals(taxp)) {
                    t_taxp.appendRow();
                    t_taxp.setValue("*", "STR");
                } else if (!StringUtils.isNullBlank(taxp)) {
                    String tx[] = taxp.split(",");
                    for (String str : tx) {
                        t_taxp.appendRow();
                        t_taxp.setValue(str, "STR");
                    }
                }

                client.execute(function);

                // 新增的数据集合
                vatDatas = function.getTableParameterList().getTable("T_VAT");
                taxamountDatas = function.getTableParameterList().getTable("T_TAXAMOUNT");
                System.out.print(vatDatas);
                vatList = ExtractSapData.getList(vatDatas);
                taxamountList = ExtractSapData.getList(taxamountDatas);

            } else {
                log.error("----------------取得需要同步是主数据发生错误：目标系统上不存在RFC" + rfcName);
            }
        } catch (Exception ex) {
            log.error("----------------取得往来账龄分析数据发生错误" + ex);
        } finally {
            manager.cleanUp();
        }

        List<VatGoodDetail> ageAnalysisList = new ArrayList<VatGoodDetail>();
        for (Map<String, String> map : vatList) {
            VatGoodDetail vatGoodDetail = new VatGoodDetail();
            vatGoodDetail.setBukrs(map.get("BUKRS").toString());
            vatGoodDetail.setGsber(map.get("GSBER").toString());
            vatGoodDetail.setMatnr(map.get("MATNR").toString());
            vatGoodDetail.setMatnr_name(map.get("MATNR_NAME").toString());
            vatGoodDetail.setMatgroup(map.get("MATGROUP").toString());
            vatGoodDetail.setMatgroup_name(map.get("MATGROUP_NAME").toString());
            vatGoodDetail.setTaxp(map.get("TAXP").toString());
            vatGoodDetail.setSaptype(map.get("SAPTYPE").toString());
            vatGoodDetail.setQcyjcwdp(map.get("QCYJCWDP").toString());
            vatGoodDetail.setQcydpwjc(map.get("QCYDPWJC").toString());
            vatGoodDetail.setQcydswjc(map.get("QCYDSWJC").toString());
            vatGoodDetail.setQcysckts(map.get("QCYSCKTS").toString());
            vatGoodDetail.setBqrkje(map.get("BQRKJE").toString());
            vatGoodDetail.setBqjyje(map.get("BQJYJE").toString());
            vatGoodDetail.setInputtax(map.get("INPUTTAX").toString());
            vatGoodDetail.setInputurn(map.get("INPUTURN").toString());
            vatGoodDetail.setTaxturn(map.get("TAXTURN").toString());
            vatGoodDetail.setOutboundm(map.get("OUTBOUNDM").toString());
            vatGoodDetail.setBusin(map.get("BUSIN").toString());
            vatGoodDetail.setSalesin(map.get("SALESIN").toString());
            vatGoodDetail.setOutputtax(map.get("OUTPUTTAX").toString());
            vatGoodDetail.setBuscost(map.get("BUSCOST").toString());
            vatGoodDetail.setSalescost(map.get("SALESCOST").toString());
            vatGoodDetail.setExporttax(map.get("EXPORTTAX").toString());
            vatGoodDetail.setAccexport(map.get("ACCEXPORT").toString());
            vatGoodDetail.setQmyjcwdp(map.get("QMYJCWDP").toString());
            vatGoodDetail.setQmydpwjc(map.get("QMYDPWJC").toString());
            vatGoodDetail.setQmydswjc(map.get("QMYDSWJC").toString());
            vatGoodDetail.setQmysckts(map.get("QMYSCKTS").toString());

            ageAnalysisList.add(vatGoodDetail);
        }

        // 处理明细数据
        com.infolion.platform.console.sys.context.UserContext userContext = com.infolion.platform.console.sys.context.UserContextHolder
                .getLocalUserContext().getUserContext();
        String userid = userContext.getSysUser().getUserId();
        this.vatDetailJdbcDao.delVatDetail(userid);
        List<VatDetail> list = new ArrayList<VatDetail>();
        for (Map<String, String> map : taxamountList) {
            VatDetail vatDetail = new VatDetail();
            if (StringUtils.isNullBlank(map.get("EBELN"))) {
            	vatDetail.setAufnr(" "); // 采购订单号
            }else{
            	vatDetail.setAufnr(map.get("EBELN")); // 采购订单号
            }
            
            if (StringUtils.isNullBlank(map.get("BELNR"))) {
            	vatDetail.setBelnr(" "); // 会计凭证号码
            }else{
            	vatDetail.setBelnr(map.get("BELNR")); // 会计凭证号码
            }
            
            if (StringUtils.isNullBlank(map.get("/BIC/BUDAT"))) {
            	  vatDetail.setBudat(" "); // 过账日期
            }else{
            	  vatDetail.setBudat(map.get("/BIC/BUDAT")); // 过账日期
            }          
            
            vatDetail.setClient("800");
            
            if (StringUtils.isNullBlank(map.get("/BIC/DEPT_CODE"))) {
            	vatDetail.setDepartment(" "); // 部门
            }else{
            	vatDetail.setDepartment(map.get("/BIC/DEPT_CODE")); // 部门
            }
            
            if (StringUtils.isNullBlank(map.get("/BIC/MAT_TEXT"))) {
            	vatDetail.setMaterailname(" "); // 物料名称
            }else{
            	vatDetail.setMaterailname(map.get("/BIC/MAT_TEXT")); // 物料名称
            }
           
            if (StringUtils.isNullBlank(map.get("/BIC/MATEUNIT"))) {
            	 vatDetail.setMatgroupname(" "); // 物料组名称
            }else{
            	 vatDetail.setMatgroupname(map.get("/BIC/MATEUNIT")); // 物料组名称
            }
           
            if (StringUtils.isNullBlank(map.get("/BIC/MATNR"))) {
            	 vatDetail.setMatnr(" "); // 物料号
            }else{
            	 vatDetail.setMatnr(map.get("/BIC/MATNR")); // 物料号
            }
           
            if (StringUtils.isNullBlank(map.get("/BIC/MATGROUP"))) {
            	 vatDetail.setMatnrgroup(" "); // 物料组名称
            }else{
            	 vatDetail.setMatnrgroup(map.get("/BIC/MATGROUP")); // 物料组名称
            }
           
            if (StringUtils.isNullBlank(map.get("GOODSVALUE"))) {
            	 vatDetail.setReceamount("0"); // 收货金额
            }else{
            	 vatDetail.setReceamount(map.get("GOODSVALUE")); // 收货金额
            }
            if (StringUtils.isNullBlank(map.get("POSTXT"))) {
            	vatDetail.setSummary(" "); // 摘要
            }else{
            	vatDetail.setSummary(map.get("POSTXT")); // 摘要
            }
            String _tax = map.get("TAXP"); // 税金
            if (StringUtils.isNullBlank(_tax)) {
                vatDetail.setTax(new BigDecimal(0));
            } else {
                vatDetail.setTax( BigDecimal.valueOf(Double.valueOf(_tax)));                
            }
            if (StringUtils.isNullBlank(map.get("/BIC/MWSKZ"))) {
            	vatDetail.setTaxcode(" "); // 税码
            }else{
            	vatDetail.setTaxcode(map.get("/BIC/MWSKZ")); // 税码
            }
            if (StringUtils.isNullBlank(map.get("/BIC/TAXP"))) {
            	vatDetail.setTaxrate(" "); // 税率
            }else{
            	vatDetail.setTaxrate(map.get("/BIC/TAXP")); // 税率
            }
            vatDetail.setTid(UUID.randomUUID().toString());
            
            if (StringUtils.isNullBlank(map.get("/BIC/BUDAT"))) {
            	vatDetail.setVoucherdate(" ");
            }else{
            	vatDetail.setVoucherdate(map.get("/BIC/BUDAT"));
            }
            vatDetail.setFlag(map.get("FLAG")); // 标识
            
            
            if (StringUtils.isNullBlank(map.get("/BIC/POSNR"))) {
            	vatDetail.setBuzei(" "); // 行项目号
            }else{
            	vatDetail.setBuzei(map.get("/BIC/POSNR")); // 行项目号
            }
            
            if (StringUtils.isNullBlank(map.get("/BIC/SAPTYPE"))) {
            	vatDetail.setSaptype(" "); // SAP业务类型
            }else{
            	vatDetail.setSaptype(map.get("/BIC/SAPTYPE")); // SAP业务类型
            }
            vatDetail.setUserid(userid);
            list.add(vatDetail);
        }
        this.vatDetailJdbcDao.saveOrUpdateAll(list);
        return ageAnalysisList;
    }
}