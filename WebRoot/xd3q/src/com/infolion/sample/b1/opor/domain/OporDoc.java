/*
 * @(#)OporDoc.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月26日 10点30分25秒
 *  描　述：创建
 */
package com.infolion.sample.b1.opor.domain;

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
import com.infolion.sample.b1.opor.domain.OporDocItem;

/**
 * <pre>
 * 采购订单(OporDoc)实体类
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
@Table(name = "YOPORDOC")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class OporDoc extends BaseObject
{
	//fields
	/*
	 * 采购订单明细
	 */
    @OneToMany(mappedBy="oporDoc",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
      
    private Set<OporDocItem> oporDocItem;   
    
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 采购订单ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="OPORDOCID")
      
    private String oporDocId;   
    
	/*
	 * 业务伙伴编码
	 */
    @Column(name="CARDCODE")
    @ValidateRule(dataType=9,label="业务伙伴编码",maxLength= 15,required=false)  
    private String cardCode;   
    
	/*
	 * 业务伙伴名称
	 */
    @Column(name="CARDNAME")
    @ValidateRule(dataType=9,label="业务伙伴名称",maxLength= 100,required=false)  
    private String cardName;   
    
	/*
	 * 单据日期
	 */
    @Column(name="TAXDATE")
    @ValidateRule(dataType=8,label="单据日期",required=false)  
    private String taxDate;   
    
	/*
	 * 过帐日期
	 */
    @Column(name="DOCDATE")
    @ValidateRule(dataType=8,label="过帐日期",required=false)  
    private String docDate;   
    
	/*
	 * 过期日期
	 */
    @Column(name="DOCDUEDATE")
    @ValidateRule(dataType=8,label="过期日期",required=false)  
    private String docDueDate;   
    
	/*
	 * 总计
	 */
    @Column(name="DOCTOTAL")
    @ValidateRule(dataType=0,label="总计",required=false)  
    private BigDecimal docTotal;   
    
	/*
	 * 参考编号
	 */
    @Column(name="NUMATCARD")
    @ValidateRule(dataType=9,label="参考编号",maxLength= 100,required=false)  
    private String numatCard;   
    
	/*
	 * 注释
	 */
    @Column(name="COMMENTS")
    @ValidateRule(dataType=9,label="注释",maxLength= 200,required=false)  
    private String comments;   
    
	/*
	 * 流程状态
	 */
    @Column(name="PROCESSSTATE")
    @ValidateRule(dataType=9,label="流程状态",maxLength= 30,required=false)  
    private String processstate;   
    
	/*
	 * 麦头
	 */
    @Column(name="U_LC")
    @ValidateRule(dataType=9,label="麦头",maxLength= 10,required=false)  
    private String u_lc;   
    

    /**
     * 功能描述:
     *    获得采购订单明细
     * @return 采购订单明细 : Set<OporDocItem>
     */
    public Set<OporDocItem> getOporDocItem()
    {
		return this.oporDocItem;
    }

    /**
     * 功能描述:
     *    设置采购订单明细
     * @param oporDocItem : Set<OporDocItem>
     */
    public void setOporDocItem(Set<OporDocItem> oporDocItem)
    {
	    this.oporDocItem = oporDocItem;
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
     *    获得采购订单ID
     * @return 采购订单ID : String
     */
    public String getOporDocId()
    {
		return this.oporDocId;
    }

    /**
     * 功能描述:
     *    设置采购订单ID
     * @param oporDocId : String
     */
    public void setOporDocId(String oporDocId)
    {
	    this.oporDocId = oporDocId;
    }
    

    /**
     * 功能描述:
     *    获得业务伙伴编码
     * @return 业务伙伴编码 : String
     */
    public String getCardCode()
    {
		return this.cardCode;
    }

    /**
     * 功能描述:
     *    设置业务伙伴编码
     * @param cardCode : String
     */
    public void setCardCode(String cardCode)
    {
	    this.cardCode = cardCode;
    }
    

    /**
     * 功能描述:
     *    获得业务伙伴名称
     * @return 业务伙伴名称 : String
     */
    public String getCardName()
    {
		return this.cardName;
    }

    /**
     * 功能描述:
     *    设置业务伙伴名称
     * @param cardName : String
     */
    public void setCardName(String cardName)
    {
	    this.cardName = cardName;
    }
    

    /**
     * 功能描述:
     *    获得单据日期
     * @return 单据日期 : String
     */
    public String getTaxDate()
    {
    	return DateUtils.toDisplayStr(this.taxDate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置单据日期
     * @param taxDate : String
     */
    public void setTaxDate(String taxDate)
    {
    	taxDate = DateUtils.toStoreStr(taxDate);
	    this.taxDate = taxDate;
    }
    

    /**
     * 功能描述:
     *    获得过帐日期
     * @return 过帐日期 : String
     */
    public String getDocDate()
    {
    	return DateUtils.toDisplayStr(this.docDate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置过帐日期
     * @param docDate : String
     */
    public void setDocDate(String docDate)
    {
    	docDate = DateUtils.toStoreStr(docDate);
	    this.docDate = docDate;
    }
    

    /**
     * 功能描述:
     *    获得过期日期
     * @return 过期日期 : String
     */
    public String getDocDueDate()
    {
    	return DateUtils.toDisplayStr(this.docDueDate, DateUtils.HYPHEN_DISPLAY_DATE);
    }

    /**
     * 功能描述:
     *    设置过期日期
     * @param docDueDate : String
     */
    public void setDocDueDate(String docDueDate)
    {
    	docDueDate = DateUtils.toStoreStr(docDueDate);
	    this.docDueDate = docDueDate;
    }
    

    /**
     * 功能描述:
     *    获得总计
     * @return 总计 : BigDecimal
     */
    public BigDecimal getDocTotal()
    {
		return this.docTotal;
    }

    /**
     * 功能描述:
     *    设置总计
     * @param docTotal : BigDecimal
     */
    public void setDocTotal(BigDecimal docTotal)
    {
	    this.docTotal = docTotal;
    }
    

    /**
     * 功能描述:
     *    获得参考编号
     * @return 参考编号 : String
     */
    public String getNumatCard()
    {
		return this.numatCard;
    }

    /**
     * 功能描述:
     *    设置参考编号
     * @param numatCard : String
     */
    public void setNumatCard(String numatCard)
    {
	    this.numatCard = numatCard;
    }
    

    /**
     * 功能描述:
     *    获得注释
     * @return 注释 : String
     */
    public String getComments()
    {
		return this.comments;
    }

    /**
     * 功能描述:
     *    设置注释
     * @param comments : String
     */
    public void setComments(String comments)
    {
	    this.comments = comments;
    }
    

    /**
     * 功能描述:
     *    获得流程状态
     * @return 流程状态 : String
     */
    public String getProcessstate()
    {
		return this.processstate;
    }

    /**
     * 功能描述:
     *    设置流程状态
     * @param processstate : String
     */
    public void setProcessstate(String processstate)
    {
	    this.processstate = processstate;
    }
    

    /**
     * 功能描述:
     *    获得麦头
     * @return 麦头 : String
     */
    public String getU_lc()
    {
		return this.u_lc;
    }

    /**
     * 功能描述:
     *    设置麦头
     * @param u_lc : String
     */
    public void setU_lc(String u_lc)
    {
	    this.u_lc = u_lc;
    }
    
    
	/**
	 *  默认构造器
	 */
    public OporDoc()
    {
    	super();
    }
}
