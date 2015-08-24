package com.infolion.xdss3.masterData.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.infolion.platform.dpframework.core.domain.BaseObject;
/**
 * @创建作者：邱杰烜
 * @创建时间：2010-12-16
 * 存放从SAP同步过来的凭证数据表
 */
@Entity
@Table(name = "YGETBSEG")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class Bseg extends BaseObject{
    
    @Column(name="MANDT")
    private String client;
    
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="BSEGID")
    private String bsegid;
    
    /**
     * 公司代码
     */
    @Column(name="BUKRS")
    private String bukrs;
    
    /**
     * 会计凭证编号 
     */
    @Column(name="BELNR")
    private String belnr;
    
    /**
     * 会计年度
     */
    @Column(name="GJAHR")
    private String gjahr;
    
    /**
     * 会计凭证中的行项目数
     */
    @Column(name="BUZEI")
    private String buzei;
    
    /**
     * 凭证中的过帐日期
     */
    @Column(name="BUDAT")
    private String budat;
    
    /**
     * 清帐日期
     */
    @Column(name="AUGDT")
    private String augdt;
    
    /**
     * 借方/贷方标识
     */
    @Column(name="SHKZG")
    private String shkzg;
    
    /**
     * 业务范围
     */
    @Column(name="GSBER")
    private String gsber;
    
    /**
     * 按本位币计的金额
     */
    @Column(name="DMBTR")
    private BigDecimal dmbtr;
     
    /**
     * 凭证贷币金额
     */
    @Column(name="WRBTR")
    private BigDecimal wrbtr;
    
    /**
     * 总分类帐帐目
     */
    @Column(name="HKONT")
    private String hkont;
    
    /**
     * 客户编号
     */
    @Column(name="KUNNR")
    private String kunnr;
    
    /**
     * 供应商或债权人的帐号
     */
    @Column(name="LIFNR")
    private String lifnr;
    
    /**
     * 用于到期日计算的基准日期
     */
    @Column(name="ZFBDT")
    private String zfbdt;
    
    /**
     * 业务所属的发票号码
     */
    @Column(name="REBZG")
    private String rebzg; 
    
    /**
     * 有关发票的财政年度（贷欺项凭单）
     */
    @Column(name="REBZJ")
    private String rebzj; 
    
    /**
     * 相关发票中的行项目
     */
    @Column(name="REBZZ")
    private String rebzz;

    /**
     * 项目文本
     */
    @Column(name="SGTXT")
    private String sgtxt; 
    
    /**
     * 销售凭证 
     */
    @Column(name="VBEL2")
    private String vbel2; 
    
    /**
     * 采购凭证号 
     */
    @Column(name="EBELN")
    private String ebeln;
    
    /**
     * 销售凭证类型
     */
    @Column(name="VBELTYPE")
    private String vbeltype; 
    
    /**
     * 订单类型 （采购）
     */
    @Column(name="BSART")
    private String bsart; 
    
    /**
     * 更新总分类帐交易数字货币
     */
    @Column(name="PSWSL")
    private String pswsl;
    
    /**
     * 字段参考关键
     */
    @Column(name="AWKEY")
    private String awkey;
    
    /**
     * 合同号
     */
    @Column(name="IHREZ")
    private String ihrez;
    
    /**
     * 立项号 
     */
    @Column(name="BNAME")
    private String bname;

    public String getClient(){
        return client;
    }

    public void setClient(String client){
        this.client = client;
    }

    public String getBsegid(){
        return bsegid;
    }

    public void setBsegid(String bsegid){
        this.bsegid = bsegid;
    }

    public String getBukrs(){
        return bukrs;
    }

    public void setBukrs(String bukrs){
        this.bukrs = bukrs;
    }

    public String getBelnr(){
        return belnr;
    }

    public void setBelnr(String belnr){
        this.belnr = belnr;
    }

    public String getGjahr(){
        return gjahr;
    }

    public void setGjahr(String gjahr){
        this.gjahr = gjahr;
    }

    public String getBuzei(){
        return buzei;
    }

    public void setBuzei(String buzei){
        this.buzei = buzei;
    }

    public String getBudat(){
        return budat;
    }

    public void setBudat(String budat){
        this.budat = budat;
    }

    public String getAugdt(){
        return augdt;
    }

    public void setAugdt(String augdt){
        this.augdt = augdt;
    }

    public String getShkzg(){
        return shkzg;
    }

    public void setShkzg(String shkzg){
        this.shkzg = shkzg;
    }

    public String getGsber(){
        return gsber;
    }

    public void setGsber(String gsber){
        this.gsber = gsber;
    }

    public BigDecimal getDmbtr(){
        return dmbtr;
    }

    public void setDmbtr(BigDecimal dmbtr){
        this.dmbtr = dmbtr;
    }

    public BigDecimal getWrbtr(){
        return wrbtr;
    }

    public void setWrbtr(BigDecimal wrbtr){
        this.wrbtr = wrbtr;
    }

    public String getHkont(){
        return hkont;
    }

    public void setHkont(String hkont){
        this.hkont = hkont;
    }

    public String getKunnr(){
        return kunnr;
    }

    public void setKunnr(String kunnr){
        this.kunnr = kunnr;
    }

    public String getLifnr(){
        return lifnr;
    }

    public void setLifnr(String lifnr){
        this.lifnr = lifnr;
    }

    public String getZfbdt(){
        return zfbdt;
    }

    public void setZfbdt(String zfbdt){
        this.zfbdt = zfbdt;
    }

    public String getRebzg(){
        return rebzg;
    }

    public void setRebzg(String rebzg){
        this.rebzg = rebzg;
    }

    public String getRebzj(){
        return rebzj;
    }

    public void setRebzj(String rebzj){
        this.rebzj = rebzj;
    }

    public String getRebzz(){
        return rebzz;
    }

    public void setRebzz(String rebzz){
        this.rebzz = rebzz;
    }

    public String getSgtxt(){
        return sgtxt;
    }

    public void setSgtxt(String sgtxt){
        this.sgtxt = sgtxt;
    }

    public String getVbel2(){
        return vbel2;
    }

    public void setVbel2(String vbel2){
        this.vbel2 = vbel2;
    }

    public String getEbeln(){
        return ebeln;
    }

    public void setEbeln(String ebeln){
        this.ebeln = ebeln;
    }

    public String getVbeltype(){
        return vbeltype;
    }

    public void setVbeltype(String vbeltype){
        this.vbeltype = vbeltype;
    }

    public String getBsart(){
        return bsart;
    }

    public void setBsart(String bsart){
        this.bsart = bsart;
    }

    public String getPswsl(){
        return pswsl;
    }

    public void setPswsl(String pswsl){
        this.pswsl = pswsl;
    }

    public String getAwkey(){
        return awkey;
    }

    public void setAwkey(String awkey){
        this.awkey = awkey;
    }

    public String getIhrez(){
        return ihrez;
    }

    public void setIhrez(String ihrez){
        this.ihrez = ihrez;
    }

    public String getBname(){
        return bname;
    }

    public void setBname(String bname){
        this.bname = bname;
    }
}
