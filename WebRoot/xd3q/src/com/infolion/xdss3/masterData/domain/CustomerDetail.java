/*
 * @(#)CustomerDetail.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月07日 10点19分59秒
 *  描　述：创建
 */
package com.infolion.xdss3.masterData.domain;

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
 * 未清客户数据明细(CustomerDetail)实体类
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
@Table(name = "YCUSTOMERDETAIL")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class CustomerDetail extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 客户未清数据明细ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="CUSTOMERDETAILID")
      
    private String customerdetailid;   
    
	/*
	 * SAP开票号
	 */
    @Column(name="INVOICE")
    @ValidateRule(dataType=9,label="SAP开票号",maxLength= 10,required=false)  
    private String invoice;   
    
	/*
	 * 会计凭证中的行项目数
	 */
    @Column(name="BUZEI")
    @ValidateRule(dataType=0,label="会计凭证中的行项目数",required=false)  
    private String buzei;   
    
	/*
	 * 按本位币计的金额
	 */
    @Column(name="DMBTR")
    @ValidateRule(dataType=0,label="按本位币计的金额",required=false)  
    private BigDecimal dmbtr;   
    
	/*
	 * 销售凭证
	 */
    @Column(name="VBELN")
    @ValidateRule(dataType=9,label="销售凭证",maxLength= 10,required=false)  
    private String vbeln;   
    
	/*
	 * 货币码
	 */
    @Column(name="WAERS")
    @ValidateRule(dataType=9,label="货币码",maxLength= 5,required=false)  
    private String waers;   
    
	/*
	 * 物料号
	 */
    @Column(name="MATNR")
    @ValidateRule(dataType=9,label="物料号",maxLength= 18,required=false)  
    private String matnr;   
    
	/*
	 * 物料描述
	 */
    @Column(name="MAKTX")
    @ValidateRule(dataType=9,label="物料描述",maxLength= 40,required=false)  
    private String maktx;   
    
	/*
	 * 定货人名字
	 */
    @Column(name="BNAME")
    @ValidateRule(dataType=9,label="定货人名字",maxLength= 35,required=false)  
    private String bname;   
    
	/*
	 * 立项号
	 */
    @Column(name="LIXIANG")
    @ValidateRule(dataType=9,label="立项号",maxLength= 6,required=false)  
    private String lixiang;   
    
    /*
	 * 结清标志
	 */
    @Column(name="ISCLEARED")
    @ValidateRule(dataType=9,label="结清标志",maxLength= 1,required=false)  
    private String iscleared;  

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
     *    获得客户未清数据明细ID
     * @return 客户未清数据明细ID : String
     */
    public String getCustomerdetailid()
    {
		return this.customerdetailid;
    }

    /**
     * 功能描述:
     *    设置客户未清数据明细ID
     * @param customerdetailid : String
     */
    public void setCustomerdetailid(String customerdetailid)
    {
	    this.customerdetailid = customerdetailid;
    }
    

    /**
     * 功能描述:
     *    获得发票号
     * @return 发票号 : String
     */
    public String getInvoice()
    {
		return this.invoice;
    }

    /**
     * 功能描述:
     *    设置发票号
     * @param invoice : String
     */
    public void setInvoice(String invoice)
    {
	    this.invoice = invoice;
    }
    

    /**
     * 功能描述:
     *    获得会计凭证中的行项目数
     * @return 会计凭证中的行项目数 : String
     */
    public String getBuzei()
    {
		return this.buzei;
    }

    /**
     * 功能描述:
     *    设置会计凭证中的行项目数
     * @param buzei : String
     */
    public void setBuzei(String buzei)
    {
	    this.buzei = buzei;
    }
    

    /**
     * 功能描述:
     *    获得按本位币计的金额
     * @return 按本位币计的金额 : BigDecimal
     */
    public BigDecimal getDmbtr()
    {
		return this.dmbtr;
    }

    /**
     * 功能描述:
     *    设置按本位币计的金额
     * @param dmbtr : BigDecimal
     */
    public void setDmbtr(BigDecimal dmbtr)
    {
	    this.dmbtr = dmbtr;
    }
    

    /**
     * 功能描述:
     *    获得销售凭证
     * @return 销售凭证 : String
     */
    public String getVbeln()
    {
		return this.vbeln;
    }

    /**
     * 功能描述:
     *    设置销售凭证
     * @param vbeln : String
     */
    public void setVbeln(String vbeln)
    {
	    this.vbeln = vbeln;
    }
    

    /**
     * 功能描述:
     *    获得货币码
     * @return 货币码 : String
     */
    public String getWaers()
    {
		return this.waers;
    }

    /**
     * 功能描述:
     *    设置货币码
     * @param waers : String
     */
    public void setWaers(String waers)
    {
	    this.waers = waers;
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
     *    获得物料描述
     * @return 物料描述 : String
     */
    public String getMaktx()
    {
		return this.maktx;
    }

    /**
     * 功能描述:
     *    设置物料描述
     * @param maktx : String
     */
    public void setMaktx(String maktx)
    {
	    this.maktx = maktx;
    }
    

    /**
     * 功能描述:
     *    获得定货人名字
     * @return 定货人名字 : String
     */
    public String getBname()
    {
		return this.bname;
    }

    /**
     * 功能描述:
     *    设置定货人名字
     * @param bname : String
     */
    public void setBname(String bname)
    {
	    this.bname = bname;
    }
    

    /**
     * 功能描述:
     *    获得立项号
     * @return 立项号 : String
     */
    public String getLixiang()
    {
		return this.lixiang;
    }

    /**
     * 功能描述:
     *    设置立项号
     * @param lixiang : String
     */
    public void setLixiang(String lixiang)
    {
	    this.lixiang = lixiang;
    }
    
    
	/**
	 * @return the iscleared
	 */
	public String getIscleared() {
		return iscleared;
	}

	/**
	 * @param iscleared the iscleared to set
	 */
	public void setIscleared(String iscleared) {
		this.iscleared = iscleared;
	}

	/**
	 *  默认构造器
	 */
    public CustomerDetail()
    {
    	super();
    }
}
