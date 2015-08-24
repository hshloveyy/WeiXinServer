/*
 * @(#)CollectItem.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年08月20日 01点22分48秒
 *  描　述：创建
 */
package com.infolion.xdss3.collectitem.domain;

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
 * 收款金额分配(CollectItem)实体类
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
@Table(name = "YCOLLECTITEM")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class CollectItem extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 收款金额分配ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="COLLECTITEMID")
      
    private String collectitemid;   
    
	/*
	 * 收款单号
	 */
    @Column(name="COLLECTNO")
    @ValidateRule(dataType=9,label="收款单号",maxLength= 50,required=false)  
    private String collectno;   
    
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
	 * 受信额度类型
	 */
    @Column(name="CREDITLINETYPE")
    @ValidateRule(dataType=9,label="授信额度类型",maxLength= 20,required=false)  
    private String creditlinetype;   
    
	/*
	 * 合同金额
	 */
    @Column(name="CONTRACTAMOUNT")
    @ValidateRule(dataType=0,label="合同金额",required=false)  
    private BigDecimal contractamount;   
    
	/*
	 * 分配金额
	 */
    @Column(name="ASSIGNAMOUNT")
    @ValidateRule(dataType=0,label="分配金额",required=false)  
    private BigDecimal assignamount;   
    
	/*
	 * 分配金额（本位币）
	 */
    @Column(name="ASSIGNAMOUNT2")
    @ValidateRule(dataType=0,label="分配金额（本位币）",required=false)  
    private BigDecimal assignamount2;   
    
	/*
	 * 保证金
	 */
    @Column(name="SURETYBOND")
    @ValidateRule(dataType=0,label="保证金",required=false)  
    private BigDecimal suretybond;   
    
	/*
	 * 实际剩余保证金
	 */
    @Column(name="ACTSURETYBOND")
    @ValidateRule(dataType=0,label="实际剩余保证金",required=false)  
    private BigDecimal actsuretybond;   
    
	/*
	 * 货款金额
	 */
    @Column(name="GOODSAMOUNT")
    @ValidateRule(dataType=0,label="货款金额",required=false)  
    private BigDecimal goodsamount;   
    
	/*
	 * 本次已清金额
	 */
    @Column(name="CLEAREDAMOUNT")
    @ValidateRule(dataType=0,label="本次已清金额",required=false)  
    private BigDecimal clearedamount;   
    
	/*
	 * 预收款
	 */
    @Column(name="PREBILLAMOUNT")
    @ValidateRule(dataType=0,label="预收款",required=false)  
    private BigDecimal prebillamount;   
    
	/*
	 * 是否已清
	 */
    @Column(name="ISCLEAR")
    @ValidateRule(dataType=9,label="是否已清",maxLength= 1,required=false)  
    private String isclear;   
    
	/*
	 * 保证金是否已全转
	 */
    @Column(name="SURETYBONDCLEAR")
    @ValidateRule(dataType=9,label="保证金是否已全转",maxLength= 1,required=false)  
    private String suretybondclear;   
    
	/*
	 * 收款
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="COLLECTID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Collect collect;   
    
	/*
	 * 物料组名称
	 */
    @Column(name="YMAT_GROUP")
    @ValidateRule(dataType=9,label="物料组名称",maxLength= 20,required=false)  
    private String ymat_group;   
    

	public String getYmat_group() {
		return ymat_group;
	}

	public void setYmat_group(String ymat_group) {
		this.ymat_group = ymat_group;
	}

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
     *    获得收款金额分配ID
     * @return 收款金额分配ID : String
     */
    public String getCollectitemid()
    {
		return this.collectitemid;
    }

    /**
     * 功能描述:
     *    设置收款金额分配ID
     * @param collectitemid : String
     */
    public void setCollectitemid(String collectitemid)
    {
	    this.collectitemid = collectitemid;
    }
    

    /**
     * 功能描述:
     *    获得收款单号
     * @return 收款单号 : String
     */
    public String getCollectno()
    {
		return this.collectno;
    }

    /**
     * 功能描述:
     *    设置收款单号
     * @param collectno : String
     */
    public void setCollectno(String collectno)
    {
	    this.collectno = collectno;
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
     *    获得受信额度类型
     * @return 受信额度类型 : String
     */
    public String getCreditlinetype()
    {
		return this.creditlinetype;
    }

    /**
     * 功能描述:
     *    设置受信额度类型
     * @param creditlinetype : String
     */
    public void setCreditlinetype(String creditlinetype)
    {
	    this.creditlinetype = creditlinetype;
    }
    

    /**
     * 功能描述:
     *    获得合同金额
     * @return 合同金额 : BigDecimal
     */
    public BigDecimal getContractamount()
    {
		return this.contractamount;
    }

    /**
     * 功能描述:
     *    设置合同金额
     * @param contractamount : BigDecimal
     */
    public void setContractamount(BigDecimal contractamount)
    {
	    this.contractamount = contractamount;
    }
    

    /**
     * 功能描述:
     *    获得分配金额
     * @return 分配金额 : BigDecimal
     */
    public BigDecimal getAssignamount()
    {
		return this.assignamount;
    }

    /**
     * 功能描述:
     *    设置分配金额
     * @param assignamount : BigDecimal
     */
    public void setAssignamount(BigDecimal assignamount)
    {
	    this.assignamount = assignamount;
    }
    

    /**
     * 功能描述:
     *    获得分配金额（本位币）
     * @return 分配金额（本位币） : BigDecimal
     */
    public BigDecimal getAssignamount2()
    {
		return this.assignamount2;
    }

    /**
     * 功能描述:
     *    设置分配金额（本位币）
     * @param assignamount2 : BigDecimal
     */
    public void setAssignamount2(BigDecimal assignamount2)
    {
	    this.assignamount2 = assignamount2;
    }
    

    /**
     * 功能描述:
     *    获得保证金
     * @return 保证金 : BigDecimal
     */
    public BigDecimal getSuretybond()
    {
		return this.suretybond;
    }

    /**
     * 功能描述:
     *    设置保证金
     * @param suretybond : BigDecimal
     */
    public void setSuretybond(BigDecimal suretybond)
    {
	    this.suretybond = suretybond;
    }
    

    /**
     * 功能描述:
     *    获得实际剩余保证金
     * @return 实际剩余保证金 : BigDecimal
     */
    public BigDecimal getActsuretybond()
    {
		return this.actsuretybond;
    }

    /**
     * 功能描述:
     *    设置实际剩余保证金
     * @param actsuretybond : BigDecimal
     */
    public void setActsuretybond(BigDecimal actsuretybond)
    {
	    this.actsuretybond = actsuretybond;
    }
    

    /**
     * 功能描述:
     *    获得货款金额
     * @return 货款金额 : BigDecimal
     */
    public BigDecimal getGoodsamount()
    {
		return this.goodsamount;
    }

    /**
     * 功能描述:
     *    设置货款金额
     * @param goodsamount : BigDecimal
     */
    public void setGoodsamount(BigDecimal goodsamount)
    {
	    this.goodsamount = goodsamount;
    }
    

    /**
     * 功能描述:
     *    获得本次已清金额
     * @return 本次已清金额 : BigDecimal
     */
    public BigDecimal getClearedamount()
    {
		return this.clearedamount;
    }

    /**
     * 功能描述:
     *    设置本次已清金额
     * @param clearedamount : BigDecimal
     */
    public void setClearedamount(BigDecimal clearedamount)
    {
	    this.clearedamount = clearedamount;
    }
    

    /**
     * 功能描述:
     *    获得预收票款
     * @return 预收票款 : BigDecimal
     */
    public BigDecimal getPrebillamount()
    {
		return this.prebillamount;
    }

    /**
     * 功能描述:
     *    设置预收票款
     * @param prebillamount : BigDecimal
     */
    public void setPrebillamount(BigDecimal prebillamount)
    {
	    this.prebillamount = prebillamount;
    }
    

    /**
     * 功能描述:
     *    获得是否已清
     * @return 是否已清 : String
     */
    public String getIsclear()
    {
		return this.isclear;
    }

    /**
     * 功能描述:
     *    设置是否已清
     * @param isclear : String
     */
    public void setIsclear(String isclear)
    {
	    this.isclear = isclear;
    }
    

    /**
     * 功能描述:
     *    获得保证金是否已全转
     * @return 保证金是否已全转 : String
     */
    public String getSuretybondclear()
    {
		return this.suretybondclear;
    }

    /**
     * 功能描述:
     *    设置保证金是否已全转
     * @param suretybondclear : String
     */
    public void setSuretybondclear(String suretybondclear)
    {
	    this.suretybondclear = suretybondclear;
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
    public CollectItem()
    {
    	super();
    }
}
