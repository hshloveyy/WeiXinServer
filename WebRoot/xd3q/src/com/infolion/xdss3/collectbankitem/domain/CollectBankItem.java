/*
 * @(#)CollectBankItem.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年08月20日 01点22分58秒
 *  描　述：创建
 */
package com.infolion.xdss3.collectbankitem.domain;

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
 * 收款银行行项(CollectBankItem)实体类
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
@Table(name = "YCOLLECTBANKITEM")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class CollectBankItem extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 收款银行ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="COLBANKITEMID")
      
    private String colbankitemid;   
    
	/*
	 * 收款银行
	 */
    @Column(name="COLLECTBANKID")
    @ValidateRule(dataType=9,label="收款银行",maxLength= 36,required=false)  
    private String collectbankid;   
    
	/*
	 * 收款银行账号
	 */
    @Column(name="COLLECTBANKACC")
    @ValidateRule(dataType=9,label="收款银行账号",maxLength= 50,required=false)  
    private String collectbankacc;   
    
	/*
	 * 收款金额
	 */
    @Column(name="COLLECTAMOUNT")
    @ValidateRule(dataType=0,label="收款金额",required=false)  
    private BigDecimal collectamount;   
    
	/*
	 * 收款金额（本位币）
	 */
    @Column(name="COLLECTAMOUNT2")
    @ValidateRule(dataType=0,label="收款金额（本位币）",required=false)  
    private BigDecimal collectamount2;   
    
	/*
	 * 现金流项目
	 */
    @Column(name="CASHFLOWITEM")
    @ValidateRule(dataType=9,label="现金流项目",maxLength= 10,required=false)  
    private String cashflowitem;   
    
	/*
	 * 收款银行科目
	 */
    @Column(name="COLBANKSUBJECT")
    @ValidateRule(dataType=9,label="收款银行科目",maxLength= 20,required=false)  
    private String colbanksubject;   
    
    /*
     * 数据标识
     */
    @Column(name = "FLAG")
    @ValidateRule(dataType = 9, label = "数据标识", required = false)
    private String flag;
    
	/*
	 * 收款
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
     *    获得收款银行ID
     * @return 收款银行ID : String
     */
    public String getColbankitemid()
    {
		return this.colbankitemid;
    }

    /**
     * 功能描述:
     *    设置收款银行ID
     * @param colbankitemid : String
     */
    public void setColbankitemid(String colbankitemid)
    {
	    this.colbankitemid = colbankitemid;
    }
    

    /**
     * 功能描述:
     *    获得收款银行
     * @return 收款银行 : String
     */
    public String getCollectbankid()
    {
		return this.collectbankid;
    }

    /**
     * 功能描述:
     *    设置收款银行
     * @param collectbankid : String
     */
    public void setCollectbankid(String collectbankid)
    {
	    this.collectbankid = collectbankid;
    }
    

    /**
     * 功能描述:
     *    获得收款银行账号
     * @return 收款银行账号 : String
     */
    public String getCollectbankacc()
    {
		return this.collectbankacc;
    }

    /**
     * 功能描述:
     *    设置收款银行账号
     * @param collectbankacc : String
     */
    public void setCollectbankacc(String collectbankacc)
    {
	    this.collectbankacc = collectbankacc;
    }
    

    /**
     * 功能描述:
     *    获得收款金额
     * @return 收款金额 : BigDecimal
     */
    public BigDecimal getCollectamount()
    {
		return this.collectamount;
    }

    /**
     * 功能描述:
     *    设置收款金额
     * @param collectamount : BigDecimal
     */
    public void setCollectamount(BigDecimal collectamount)
    {
	    this.collectamount = collectamount;
    }
    

    /**
     * 功能描述:
     *    获得收款金额（本位币）
     * @return 收款金额（本位币） : BigDecimal
     */
    public BigDecimal getCollectamount2()
    {
		return this.collectamount2;
    }

    /**
     * 功能描述:
     *    设置收款金额（本位币）
     * @param collectamount2 : BigDecimal
     */
    public void setCollectamount2(BigDecimal collectamount2)
    {
	    this.collectamount2 = collectamount2;
    }
    

    /**
     * 功能描述:
     *    获得现金流项目
     * @return 现金流项目 : String
     */
    public String getCashflowitem()
    {
		return this.cashflowitem;
    }

    /**
     * 功能描述:
     *    设置现金流项目
     * @param cashflowitem : String
     */
    public void setCashflowitem(String cashflowitem)
    {
	    this.cashflowitem = cashflowitem;
    }
    

    /**
     * 功能描述:
     *    获得收款银行科目
     * @return 收款银行科目 : String
     */
    public String getColbanksubject()
    {
		return this.colbanksubject;
    }

    /**
     * 功能描述:
     *    设置收款银行科目
     * @param colbanksubject : String
     */
    public void setColbanksubject(String colbanksubject)
    {
	    this.colbanksubject = colbanksubject;
    }
    

    /**
     * 功能描述:
     *    获得收款
     * @return 收款 : Collect
     */
    public Collect getCollect()
    {
		return this.collect;
    }

    /**
     * 功能描述:
     *    设置收款
     * @param collect : Collect
     */
    public void setCollect(Collect collect)
    {
	    this.collect = collect;
    }
    
    
	/**
	 *  默认构造器
	 */
    public CollectBankItem()
    {
    	super();
    }

    public String getFlag(){
        return flag;
    }

    public void setFlag(String flag){
        this.flag = flag;
    }
    
}
