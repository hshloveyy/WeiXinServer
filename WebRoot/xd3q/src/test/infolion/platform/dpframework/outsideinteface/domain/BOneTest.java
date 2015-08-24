/*
 * @(#)BOneTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年09月17日 11点43分57秒
 *  描　述：创建
 */
package test.infolion.platform.dpframework.outsideinteface.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;
import java.math.BigDecimal;
          

/**
 * <pre>
 * B1测试(BOneTest)实体类
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
@Table(name = "YTESTB1INTERFACE")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class BOneTest extends BaseObject
{
	//fields
	/*
	 * ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="ID")
     
    private String id;   
    
	/*
	 * 客房编码
	 */
    @Column(name="CARDCODE")
    @ValidateRule(dataType=9,label="客房编码",maxLength= 10,required=false) 
    private String cardcode;   
    
	/*
	 * 过帐日期
	 */
    @Column(name="DOCDATE")
    @ValidateRule(dataType=9,label="过帐日期",maxLength= 16,required=false) 
    private String docdate;   
    
	/*
	 * 到期日期
	 */
    @Column(name="DOCDUEDATE")
    @ValidateRule(dataType=9,label="到期日期",maxLength= 16,required=false) 
    private String docduedate;   
    
	/*
	 * 折扣
	 */
    @Column(name="DISCOUNTPERCENT")
    @ValidateRule(dataType=0,label="折扣",required=false) 
    private Double discountpercent;   
    
	/*
	 * B1测试2
	 */
    @OneToMany(mappedBy="BOneTest",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @NotFound(action=NotFoundAction.IGNORE)
     
    private Set<BOneTestItem> doclines;   
    

    /**
     * 功能描述:
     *    获得ID
     * @return ID : String
     */
    public String getId()
    {
		return this.id;
    }

    /**
     * 功能描述:
     *    设置ID
     * @param id : String
     */
    public void setId(String id)
    {
	    this.id = id;
    }
    

    /**
     * 功能描述:
     *    获得客房编码
     * @return 客房编码 : String
     */
    public String getCardcode()
    {
		return this.cardcode;
    }

    /**
     * 功能描述:
     *    设置客房编码
     * @param cardcode : String
     */
    public void setCardcode(String cardcode)
    {
	    this.cardcode = cardcode;
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
     *    获得到期日期
     * @return 到期日期 : String
     */
    public String getDocduedate()
    {
		return this.docduedate;
    }

    /**
     * 功能描述:
     *    设置到期日期
     * @param docduedate : String
     */
    public void setDocduedate(String docduedate)
    {
	    this.docduedate = docduedate;
    }
    

    /**
     * 功能描述:
     *    获得折扣
     * @return 折扣 : BigDecimal
     */
    public Double getDiscountpercent()
    {
		return this.discountpercent;
    }

    /**
     * 功能描述:
     *    设置折扣
     * @param discountpercent : Double
     */
    public void setDiscountpercent(Double discountpercent)
    {
	    this.discountpercent = discountpercent;
    }
    

    /**
     * 功能描述:
     *    获得B1测试2
     * @return B1测试2 : Set<BOneTestItem>
     */
    public Set<BOneTestItem> getDoclines()
    {
		return this.doclines;
    }

    /**
     * 功能描述:
     *    设置B1测试2
     * @param doclines : Set<BOneTestItem>
     */
    public void setDoclines(Set<BOneTestItem> doclines)
    {
	    this.doclines = doclines;
    }
    
    
	/**
	 *  默认构造器
	 */
    public BOneTest()
    {
    	super();
    }
}
