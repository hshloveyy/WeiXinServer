/*
 * @(#)VatDetail.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年12月13日 17点52分51秒
 *  描　述：创建
 */
package com.infolion.xdss3.vatdetail.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;
import com.infolion.platform.dpframework.util.DateUtils;
import java.math.BigDecimal;

/**
 * <pre>
 * 期初已到税票未进仓(税额)(VatDetail)实体类
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
@Entity
@Table(name = "YVATDETAIL")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class VatDetail extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="TID")
      
    private String tid;   
    
	/*
	 * 部门
	 */
    @Column(name="DEPARTMENT")
    @ValidateRule(dataType=9,label="部门",maxLength= 50,required=false)  
    private String department;   
    
	/*
	 * 订单号
	 */
    @Column(name="AUFNR")
    @ValidateRule(dataType=9,label="订单号",maxLength= 12,required=false)  
    private String aufnr;   
    
	/*
	 * 会计凭证号码
	 */
    @Column(name="BELNR")
    @ValidateRule(dataType=9,label="会计凭证号码",maxLength= 10,required=false)  
    private String belnr;   
    
	/*
	 * 税金
	 */
    @Column(name="TAX")
    @ValidateRule(dataType=0,label="税金",required=false)  
    private BigDecimal tax;   
    
	/*
	 * 过账日期
	 */
    @Column(name="BUDAT")
    @ValidateRule(dataType=9,label="过账日期",maxLength= 14,required=false)  
    private String budat;   
    
	/*
	 * 物料号
	 */
    @Column(name="MATNR")
    @ValidateRule(dataType=9,label="物料号",maxLength= 18,required=false)  
    private String matnr;   
    
	/*
	 * 物料名称
	 */
    @Column(name="MATERAILNAME")
    @ValidateRule(dataType=9,label="物料名称",maxLength= 255,required=false)  
    private String materailname;   
    
	/*
	 * 物料组
	 */
    @Column(name="MATNRGROUP")
    @ValidateRule(dataType=9,label="物料组",maxLength= 36,required=false)  
    private String matnrgroup;   
    
	/*
	 * 物料组名称
	 */
    @Column(name="MATGROUPNAME")
    @ValidateRule(dataType=9,label="物料组名称",maxLength= 200,required=false)  
    private String matgroupname;   
    
	/*
	 * 税率
	 */
    @Column(name="TAXRATE")
    @ValidateRule(dataType=9,label="税率",maxLength= 10,required=false)  
    private String taxrate;   
    
	/*
	 * 税码
	 */
    @Column(name="TAXCODE")
    @ValidateRule(dataType=9,label="税码",maxLength= 50,required=false)  
    private String taxcode;   
    
	/*
	 * 凭证日期
	 */
    @Column(name="VOUCHERDATE")
    @ValidateRule(dataType=9,label="凭证日期",maxLength= 20,required=false)  
    private String voucherdate;   
    
	/*
	 * 收货金额
	 */
    @Column(name="RECEAMOUNT")
    @ValidateRule(dataType=9,label="收货金额",maxLength= 50,required=false)  
    private String receamount;   
    
	/*
	 * 摘要
	 */
    @Column(name="SUMMARY")
    @ValidateRule(dataType=9,label="摘要",maxLength= 200,required=false)  
    private String summary;   
    
	/*
	 * 用户ID
	 */
    @Column(name="USERID")
    @ValidateRule(dataType=9,label="用户ID",maxLength= 36,required=false)  
    private String userid;   
    
    /*
     * 标识
     */
    @Column(name="FLAG")
    @ValidateRule(dataType=9,label="标识",maxLength= 108,required=false)  
    private String flag;   
    
    /*
     * 行项目号
     */
    @Column(name="BUZEI")
    @ValidateRule(dataType=9,label="行项目号",maxLength= 108,required=false)  
    private String buzei;   
    
    /*
     * 业务类型
     */
    @Column(name="SAPTYPE")
    @ValidateRule(dataType=9,label="类型",maxLength= 108,required=false)  
    private String saptype;   
    

    /**
     * 功能描述:
     *    获得客户端
     * @return 客户端 : String
     */
    public String getClient()
    {
		return this.client;
    }

    /**
     * 功能描述:
     *    设置客户端
     * @param client : String
     */
    public void setClient(String client)
    {
	    this.client = client;
    }
    

    /**
     * 功能描述:
     *    获得ID
     * @return ID : String
     */
    public String getTid()
    {
		return this.tid;
    }

    /**
     * 功能描述:
     *    设置ID
     * @param tid : String
     */
    public void setTid(String tid)
    {
	    this.tid = tid;
    }
    

    /**
     * 功能描述:
     *    获得部门
     * @return 部门 : String
     */
    public String getDepartment()
    {
		return this.department;
    }

    /**
     * 功能描述:
     *    设置部门
     * @param department : String
     */
    public void setDepartment(String department)
    {
	    this.department = department;
    }
    

    /**
     * 功能描述:
     *    获得订单号
     * @return 订单号 : String
     */
    public String getAufnr()
    {
		return this.aufnr;
    }

    /**
     * 功能描述:
     *    设置订单号
     * @param aufnr : String
     */
    public void setAufnr(String aufnr)
    {
	    this.aufnr = aufnr;
    }
    

    /**
     * 功能描述:
     *    获得会计凭证号码
     * @return 会计凭证号码 : String
     */
    public String getBelnr()
    {
		return this.belnr;
    }

    /**
     * 功能描述:
     *    设置会计凭证号码
     * @param belnr : String
     */
    public void setBelnr(String belnr)
    {
	    this.belnr = belnr;
    }
    

    /**
     * 功能描述:
     *    获得税金
     * @return 税金 : BigDecimal
     */
    public BigDecimal getTax()
    {
		return this.tax;
    }

    /**
     * 功能描述:
     *    设置税金
     * @param tax : BigDecimal
     */
    public void setTax(BigDecimal tax)
    {
	    this.tax = tax;
    }
    

    /**
     * 功能描述:
     *    获得过账日期
     * @return 过账日期 : String
     */
    public String getBudat()
    {
		return this.budat;
    }

    /**
     * 功能描述:
     *    设置过账日期
     * @param budat : String
     */
    public void setBudat(String budat)
    {
	    this.budat = budat;
    }
    

    /**
     * 功能描述:
     *    获得物料号
     * @return 物料号 : String
     */
    public String getMatnr()
    {
		return this.matnr;
    }

    /**
     * 功能描述:
     *    设置物料号
     * @param matnr : String
     */
    public void setMatnr(String matnr)
    {
	    this.matnr = matnr;
    }
    

    /**
     * 功能描述:
     *    获得物料名称
     * @return 物料名称 : String
     */
    public String getMaterailname()
    {
		return this.materailname;
    }

    /**
     * 功能描述:
     *    设置物料名称
     * @param materailname : String
     */
    public void setMaterailname(String materailname)
    {
	    this.materailname = materailname;
    }
    

    /**
     * 功能描述:
     *    获得物料组
     * @return 物料组 : String
     */
    public String getMatnrgroup()
    {
		return this.matnrgroup;
    }

    /**
     * 功能描述:
     *    设置物料组
     * @param matnrgroup : String
     */
    public void setMatnrgroup(String matnrgroup)
    {
	    this.matnrgroup = matnrgroup;
    }
    

    /**
     * 功能描述:
     *    获得物料组名称
     * @return 物料组名称 : String
     */
    public String getMatgroupname()
    {
		return this.matgroupname;
    }

    /**
     * 功能描述:
     *    设置物料组名称
     * @param matgroupname : String
     */
    public void setMatgroupname(String matgroupname)
    {
	    this.matgroupname = matgroupname;
    }
    

    /**
     * 功能描述:
     *    获得税率
     * @return 税率 : String
     */
    public String getTaxrate()
    {
		return this.taxrate;
    }

    /**
     * 功能描述:
     *    设置税率
     * @param taxrate : String
     */
    public void setTaxrate(String taxrate)
    {
	    this.taxrate = taxrate;
    }
    

    /**
     * 功能描述:
     *    获得税码
     * @return 税码 : String
     */
    public String getTaxcode()
    {
		return this.taxcode;
    }

    /**
     * 功能描述:
     *    设置税码
     * @param taxcode : String
     */
    public void setTaxcode(String taxcode)
    {
	    this.taxcode = taxcode;
    }
    

    /**
     * 功能描述:
     *    获得凭证日期
     * @return 凭证日期 : String
     */
    public String getVoucherdate()
    {
		return this.voucherdate;
    }

    /**
     * 功能描述:
     *    设置凭证日期
     * @param voucherdate : String
     */
    public void setVoucherdate(String voucherdate)
    {
	    this.voucherdate = voucherdate;
    }
    

    /**
     * 功能描述:
     *    获得收货金额
     * @return 收货金额 : String
     */
    public String getReceamount()
    {
		return this.receamount;
    }

    /**
     * 功能描述:
     *    设置收货金额
     * @param receamount : String
     */
    public void setReceamount(String receamount)
    {
	    this.receamount = receamount;
    }
    

    /**
     * 功能描述:
     *    获得摘要
     * @return 摘要 : String
     */
    public String getSummary()
    {
		return this.summary;
    }

    /**
     * 功能描述:
     *    设置摘要
     * @param summary : String
     */
    public void setSummary(String summary)
    {
	    this.summary = summary;
    }
    

    /**
     * 功能描述:
     *    获得用户ID
     * @return 用户ID : String
     */
    public String getUserid()
    {
		return this.userid;
    }

    /**
     * 功能描述:
     *    设置用户ID
     * @param userid : String
     */
    public void setUserid(String userid)
    {
	    this.userid = userid;
    }
    
    
	public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getBuzei() {
        return buzei;
    }

    public void setBuzei(String buzei) {
        this.buzei = buzei;
    }

    public String getSaptype() {
        return saptype;
    }

    public void setSaptype(String saptype) {
        this.saptype = saptype;
    }

    /**
	 *  默认构造器
	 */
    public VatDetail()
    {
    	super();
    }
}
