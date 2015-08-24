/*
 * @(#)OrdrRep.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月23日 14点32分44秒
 *  描　述：创建
 */
package com.infolion.sample.b1.ordr.domain;

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


/**
 * <pre>
 * OrdrRep(OrdrRep)实体类
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
@Table(name = "YVORDRVIEW")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class OrdrRep extends BaseObject
{
	//fields
	/*
	 * ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="DOCENTRY")
      
    private String docentry;   
    
	/*
	 * 单据日期
	 */
    @Column(name="TAXDATE")
    @ValidateRule(dataType=9,label="单据日期",maxLength= 14,required=false)  
    private String taxdate;   
    
	/*
	 * 注释
	 */
    @Column(name="COMMENTS")
    @ValidateRule(dataType=9,label="注释",maxLength= 200,required=false)  
    private String comments;   
    
	/*
	 * 业务伙伴编码
	 */
    @Column(name="CARDCODE")
    @ValidateRule(dataType=9,label="业务伙伴编码",maxLength= 15,required=false)  
    private String cardcode;   
    
	/*
	 * 业务伙伴名称
	 */
    @Column(name="CARDNAME")
    @ValidateRule(dataType=9,label="业务伙伴名称",maxLength= 100,required=false)  
    private String cardname;   
    
	/*
	 * 过帐日期
	 */
    @Column(name="DOCDATE")
    @ValidateRule(dataType=9,label="过帐日期",maxLength= 14,required=false)  
    private String docdate;   
    
	/*
	 * 参考编号
	 */
    @Column(name="NUMATCARD")
    @ValidateRule(dataType=9,label="参考编号",maxLength= 100,required=false)  
    private String numatcard;   
    
	/*
	 * 销售员代码
	 */
    @Column(name="SLPCODE")
    @ValidateRule(dataType=9,label="销售员代码",maxLength= 6,required=false)  
    private String slpcode;   
    
	/*
	 * 销售代表姓名
	 */
    @Column(name="SLPNAME")
    @ValidateRule(dataType=9,label="销售代表姓名",maxLength= 50,required=false)  
    private String slpname;   
    

    /**
     * 功能描述:
     *    获得ID
     * @return ID : String
     */
    public String getDocentry()
    {
		return this.docentry;
    }

    /**
     * 功能描述:
     *    设置ID
     * @param docentry : String
     */
    public void setDocentry(String docentry)
    {
	    this.docentry = docentry;
    }
    

    /**
     * 功能描述:
     *    获得单据日期
     * @return 单据日期 : String
     */
    public String getTaxdate()
    {
		return this.taxdate;
    }

    /**
     * 功能描述:
     *    设置单据日期
     * @param taxdate : String
     */
    public void setTaxdate(String taxdate)
    {
	    this.taxdate = taxdate;
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
     *    获得业务伙伴编码
     * @return 业务伙伴编码 : String
     */
    public String getCardcode()
    {
		return this.cardcode;
    }

    /**
     * 功能描述:
     *    设置业务伙伴编码
     * @param cardcode : String
     */
    public void setCardcode(String cardcode)
    {
	    this.cardcode = cardcode;
    }
    

    /**
     * 功能描述:
     *    获得业务伙伴名称
     * @return 业务伙伴名称 : String
     */
    public String getCardname()
    {
		return this.cardname;
    }

    /**
     * 功能描述:
     *    设置业务伙伴名称
     * @param cardname : String
     */
    public void setCardname(String cardname)
    {
	    this.cardname = cardname;
    }
    

    /**
     * 功能描述:
     *    获得过帐日期
     * @return 过帐日期 : String
     */
    public String getDocdate()
    {
		return this.docdate;
    }

    /**
     * 功能描述:
     *    设置过帐日期
     * @param docdate : String
     */
    public void setDocdate(String docdate)
    {
	    this.docdate = docdate;
    }
    

    /**
     * 功能描述:
     *    获得参考编号
     * @return 参考编号 : String
     */
    public String getNumatcard()
    {
		return this.numatcard;
    }

    /**
     * 功能描述:
     *    设置参考编号
     * @param numatcard : String
     */
    public void setNumatcard(String numatcard)
    {
	    this.numatcard = numatcard;
    }
    

    /**
     * 功能描述:
     *    获得销售员代码
     * @return 销售员代码 : String
     */
    public String getSlpcode()
    {
		return this.slpcode;
    }

    /**
     * 功能描述:
     *    设置销售员代码
     * @param slpcode : String
     */
    public void setSlpcode(String slpcode)
    {
	    this.slpcode = slpcode;
    }
    

    /**
     * 功能描述:
     *    获得销售代表姓名
     * @return 销售代表姓名 : String
     */
    public String getSlpname()
    {
		return this.slpname;
    }

    /**
     * 功能描述:
     *    设置销售代表姓名
     * @param slpname : String
     */
    public void setSlpname(String slpname)
    {
	    this.slpname = slpname;
    }
    
    
	/**
	 *  默认构造器
	 */
    public OrdrRep()
    {
    	super();
    }
}
