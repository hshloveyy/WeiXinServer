/*
 * @(#)OverCollect.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月01日 06点24分19秒
 *  描　述：创建
 */
package com.infolion.xdss3.overCollect.domain;

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
import com.infolion.xdss3.collect.domain.Collect;

/**
 * <pre>
 * 多收款表(OverCollect)实体类
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
@Table(name = "YOVERCOLLECT")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class OverCollect extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 多收ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="OVERCOLLECTID")
      
    private String overcollectid;   
    
	/*
	 * 合同号
	 */
    @Column(name="CONTRACT_NO")
    @ValidateRule(dataType=9,label="合同号",maxLength= 50,required=false)  
    private String contract_no;   
    
	/*
	 * 项目编号
	 */
    @Column(name="PROJECT_NO")
    @ValidateRule(dataType=9,label="项目编号",maxLength= 200,required=false)  
    private String project_no;   
    
	/*
	 * 分配金额
	 */
    @Column(name="OVERAMOUNT")
    @ValidateRule(dataType=0,label="分配金额",required=false)  
    private BigDecimal overamount;   
    
	/*
	 * 预付款
	 */
    @Column(name="PREPAIDAMOUNT")
    @ValidateRule(dataType=0,label="预付款",required=false)  
    private BigDecimal prepaidamount;   
    
	/*
	 * 差异金额(与此次清票的差异金额)
	 */
    @Column(name="DIFFAMOUNT")
    @ValidateRule(dataType=0,label="差异金额(与此次清票的差异金额)",required=false)  
    private BigDecimal diffamount;   
    
	/*
	 * 纯资金往来
	 */
    @Column(name="CASHFLOW")
    @ValidateRule(dataType=0,label="纯资金往来",required=false)  
    private BigDecimal cashflow;   
    
	/*
	 * 多收分配类型
	 */
    @Column(name="OVERTYPE")
    @ValidateRule(dataType=9,label="多收分配类型",maxLength= 36,required=false)  
    private String overtype;   
    
	/*
	 * 行项目文本
	 */
    @Column(name="ITEMTEXT")
    @ValidateRule(dataType=9,label="行项目文本",maxLength= 255,required=false)  
    private String itemtext;   
    
	/*
	 * 出单结清标识
	 */
    @Column(name="EXPORTISCLEAR")
    @ValidateRule(dataType=9,label="出单结清标识",maxLength= 1,required=false)  
    private String exportisclear;   
    
	/*
	 * 出仓结清标识
	 */
    @Column(name="SHIPISCLEAR")
    @ValidateRule(dataType=9,label="出仓结清标识",maxLength= 1,required=false)  
    private String shipisclear;   
    
	/*
	 * 票清款结清标识
	 */
    @Column(name="BILLISCLEAR")
    @ValidateRule(dataType=9,label="票清款结清标识",maxLength= 1,required=false)  
    private String billisclear;   
    
	/*
	 * 款清款结清标识
	 */
    @Column(name="MONEYISCLEAR")
    @ValidateRule(dataType=9,label="款清款结清标识",maxLength= 1,required=false)  
    private String moneyisclear;   
    
	/*
	 * 收款信息表
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="COLLECTID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Collect collect;   
    

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
     *    获得多收ID
     * @return 多收ID : String
     */
    public String getOvercollectid()
    {
		return this.overcollectid;
    }

    /**
     * 功能描述:
     *    设置多收ID
     * @param overcollectid : String
     */
    public void setOvercollectid(String overcollectid)
    {
	    this.overcollectid = overcollectid;
    }
    

    /**
     * 功能描述:
     *    获得合同号
     * @return 合同号 : String
     */
    public String getContract_no()
    {
		return this.contract_no;
    }

    /**
     * 功能描述:
     *    设置合同号
     * @param contract_no : String
     */
    public void setContract_no(String contract_no)
    {
	    this.contract_no = contract_no;
    }
    

    /**
     * 功能描述:
     *    获得项目编号
     * @return 项目编号 : String
     */
    public String getProject_no()
    {
		return this.project_no;
    }

    /**
     * 功能描述:
     *    设置项目编号
     * @param project_no : String
     */
    public void setProject_no(String project_no)
    {
	    this.project_no = project_no;
    }
    

    /**
     * 功能描述:
     *    获得分配金额
     * @return 分配金额 : BigDecimal
     */
    public BigDecimal getOveramount()
    {
		return this.overamount;
    }

    /**
     * 功能描述:
     *    设置分配金额
     * @param overamount : BigDecimal
     */
    public void setOveramount(BigDecimal overamount)
    {
	    this.overamount = overamount;
    }
    

    /**
     * 功能描述:
     *    获得预付款
     * @return 预付款 : BigDecimal
     */
    public BigDecimal getPrepaidamount()
    {
		return this.prepaidamount;
    }

    /**
     * 功能描述:
     *    设置预付款
     * @param prepaidamount : BigDecimal
     */
    public void setPrepaidamount(BigDecimal prepaidamount)
    {
	    this.prepaidamount = prepaidamount;
    }
    

    /**
     * 功能描述:
     *    获得差异金额(与此次清票的差异金额)
     * @return 差异金额(与此次清票的差异金额) : BigDecimal
     */
    public BigDecimal getDiffamount()
    {
		return this.diffamount;
    }

    /**
     * 功能描述:
     *    设置差异金额(与此次清票的差异金额)
     * @param diffamount : BigDecimal
     */
    public void setDiffamount(BigDecimal diffamount)
    {
	    this.diffamount = diffamount;
    }
    

    /**
     * 功能描述:
     *    获得纯资金往来
     * @return 纯资金往来 : BigDecimal
     */
    public BigDecimal getCashflow()
    {
		return this.cashflow;
    }

    /**
     * 功能描述:
     *    设置纯资金往来
     * @param cashflow : BigDecimal
     */
    public void setCashflow(BigDecimal cashflow)
    {
	    this.cashflow = cashflow;
    }
    

    /**
     * 功能描述:
     *    获得多收分配类型
     * @return 多收分配类型 : String
     */
    public String getOvertype()
    {
		return this.overtype;
    }

    /**
     * 功能描述:
     *    设置多收分配类型
     * @param overtype : String
     */
    public void setOvertype(String overtype)
    {
	    this.overtype = overtype;
    }
    

    /**
     * 功能描述:
     *    获得行项目文本
     * @return 行项目文本 : String
     */
    public String getItemtext()
    {
		return this.itemtext;
    }

    /**
     * 功能描述:
     *    设置行项目文本
     * @param itemtext : String
     */
    public void setItemtext(String itemtext)
    {
	    this.itemtext = itemtext;
    }
    

    /**
     * 功能描述:
     *    获得出单结清标识
     * @return 出单结清标识 : String
     */
    public String getExportisclear()
    {
		return this.exportisclear;
    }

    /**
     * 功能描述:
     *    设置出单结清标识
     * @param exportisclear : String
     */
    public void setExportisclear(String exportisclear)
    {
	    this.exportisclear = exportisclear;
    }
    

    /**
     * 功能描述:
     *    获得出仓结清标识
     * @return 出仓结清标识 : String
     */
    public String getShipisclear()
    {
		return this.shipisclear;
    }

    /**
     * 功能描述:
     *    设置出仓结清标识
     * @param shipisclear : String
     */
    public void setShipisclear(String shipisclear)
    {
	    this.shipisclear = shipisclear;
    }
    

    /**
     * 功能描述:
     *    获得票清款结清标识
     * @return 票清款结清标识 : String
     */
    public String getBillisclear()
    {
		return this.billisclear;
    }

    /**
     * 功能描述:
     *    设置票清款结清标识
     * @param billisclear : String
     */
    public void setBillisclear(String billisclear)
    {
	    this.billisclear = billisclear;
    }
    

    /**
     * 功能描述:
     *    获得款清款结清标识
     * @return 款清款结清标识 : String
     */
    public String getMoneyisclear()
    {
		return this.moneyisclear;
    }

    /**
     * 功能描述:
     *    设置款清款结清标识
     * @param moneyisclear : String
     */
    public void setMoneyisclear(String moneyisclear)
    {
	    this.moneyisclear = moneyisclear;
    }
    

    /**
     * 功能描述:
     *    获得收款信息表
     * @return 收款信息表 : Collect
     */
    public Collect getCollect()
    {
		return this.collect;
    }

    /**
     * 功能描述:
     *    设置收款信息表
     * @param collect : Collect
     */
    public void setCollect(Collect collect)
    {
	    this.collect = collect;
    }
    
    
	/**
	 *  默认构造器
	 */
    public OverCollect()
    {
    	super();
    }
}
