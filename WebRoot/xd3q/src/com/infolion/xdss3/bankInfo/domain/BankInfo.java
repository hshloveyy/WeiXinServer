/*
 * @(#)BankInfo.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月29日 11点38分26秒
 *  描　述：创建
 */
package com.infolion.xdss3.bankInfo.domain;

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
 * 银行信息(BankInfo)实体类
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
@Table(name = "YBANK_INFO")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class BankInfo extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 银行ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="BANK_ID")
      
    private String bank_id;   
    
	/*
	 * 银行代码
	 */
    @Column(name="BANK_CODE")
    @ValidateRule(dataType=9,label="银行代码",maxLength= 15,required=true)  
    private String bank_code;   
    
	/*
	 * 银行名称
	 */
    @Column(name="BANK_NAME")
    @ValidateRule(dataType=9,label="银行名称",maxLength= 255,required=true)  
    private String bank_name;   
    
	/*
	 * 银行账号
	 */
    @Column(name="BANK_ACCOUNT")
    @ValidateRule(dataType=9,label="银行账号",maxLength= 40,required=true)  
    private String bank_account;   
    
	/*
	 * 银行科目
	 */
    @Column(name="BANK_HKONT")
    @ValidateRule(dataType=9,label="银行科目",maxLength= 20,required=false)  
    private String bank_hkont;   
    

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
     *    获得银行ID
     * @return 银行ID : String
     */
    public String getBank_id()
    {
		return this.bank_id;
    }

    /**
     * 功能描述:
     *    设置银行ID
     * @param bank_id : String
     */
    public void setBank_id(String bank_id)
    {
	    this.bank_id = bank_id;
    }
    

    /**
     * 功能描述:
     *    获得银行代码
     * @return 银行代码 : String
     */
    public String getBank_code()
    {
		return this.bank_code;
    }

    /**
     * 功能描述:
     *    设置银行代码
     * @param bank_code : String
     */
    public void setBank_code(String bank_code)
    {
	    this.bank_code = bank_code;
    }
    

    /**
     * 功能描述:
     *    获得银行名称
     * @return 银行名称 : String
     */
    public String getBank_name()
    {
		return this.bank_name;
    }

    /**
     * 功能描述:
     *    设置银行名称
     * @param bank_name : String
     */
    public void setBank_name(String bank_name)
    {
	    this.bank_name = bank_name;
    }
    

    /**
     * 功能描述:
     *    获得银行账号
     * @return 银行账号 : String
     */
    public String getBank_account()
    {
		return this.bank_account;
    }

    /**
     * 功能描述:
     *    设置银行账号
     * @param bank_account : String
     */
    public void setBank_account(String bank_account)
    {
	    this.bank_account = bank_account;
    }
    

    /**
     * 功能描述:
     *    获得银行科目
     * @return 银行科目 : String
     */
    public String getBank_hkont()
    {
		return this.bank_hkont;
    }

    /**
     * 功能描述:
     *    设置银行科目
     * @param bank_hkont : String
     */
    public void setBank_hkont(String bank_hkont)
    {
	    this.bank_hkont = bank_hkont;
    }
    
    
	/**
	 *  默认构造器
	 */
    public BankInfo()
    {
    	super();
    }
}
