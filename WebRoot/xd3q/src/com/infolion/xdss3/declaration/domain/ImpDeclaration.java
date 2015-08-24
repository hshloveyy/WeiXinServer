/*
 * @(#)ImpDeclaration.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年06月23日 17点57分22秒
 *  描　述：创建
 */
package com.infolion.xdss3.declaration.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;


/**
 * <pre>
 * 进口报关单(ImpDeclaration)实体类
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
@Table(name = "YIMPDECLARATION")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class ImpDeclaration extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 报关单ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="DECLARATIONSID")
      
    private String declarationsid;   
    
	/*
	 * 报关单号
	 */
    @Column(name="DECLARATIONSNO")
    @ValidateRule(dataType=9,label="报关单号",maxLength= 50,required=false)  
    private String declarationsno;   
    
	/*
	 * 贸易方式
	 */
    @Column(name="TRADE_TYPE")
    @ValidateRule(dataType=9,label="贸易方式",maxLength= 20,required=false)  
    private String trade_type;   
    
	/*
	 * 成交方式
	 */
    @Column(name="TRANSACTIONWAY")
    @ValidateRule(dataType=9,label="成交方式",maxLength= 30,required=false)  
    private String transactionway;   
    
	/*
	 * 进口口岸
	 */
    @Column(name="IMPORTPORT")
    @ValidateRule(dataType=9,label="进口口岸",maxLength= 30,required=false)  
    private String importport;   
    
	/*
	 * 货币
	 */
    @Column(name="WAERS")
    @ValidateRule(dataType=9,label="货币",maxLength= 50,required=false)  
    private String waers;   
    
	/*
	 * 合同号
	 */
    @Column(name="CONTRACTNO")
    @ValidateRule(dataType=9,label="合同号",maxLength= 50,required=false)  
    private String contractno;   
    
	/*
	 * 报关金额
	 */
    @Column(name="BGJE")
    @ValidateRule(dataType=9,label="报关金额",maxLength= 50,required=false)  
    private String bgje;   
    
	/*
	 * 报关金额折美元
	 */
    @Column(name="BGJEZMY")
    @ValidateRule(dataType=9,label="报关金额折美元",maxLength= 30,required=false)  
    private String bgjezmy;   
    
	/*
	 * 进口日期
	 */
    @Column(name="IMPORTDATE")
    @ValidateRule(dataType=9,label="进口日期",maxLength= 12,required=false)  
    private String importdate;   
    
	/*
	 * 总量核查状态
	 */
    @Column(name="TOTALCHECKFLAG")
    @ValidateRule(dataType=9,label="总量核查状态",maxLength= 30,required=false)  
    private String totalcheckflag;   
    
	/*
	 * 逐笔核查状态
	 */
    @Column(name="EARCHCHECKFLAG")
    @ValidateRule(dataType=9,label="逐笔核查状态",maxLength= 30,required=false)  
    private String earchcheckflag;   
    

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
     *    获得报关单ID
     * @return 报关单ID : String
     */
    public String getDeclarationsid()
    {
		return this.declarationsid;
    }

    /**
     * 功能描述:
     *    设置报关单ID
     * @param declarationsid : String
     */
    public void setDeclarationsid(String declarationsid)
    {
	    this.declarationsid = declarationsid;
    }
    

    /**
     * 功能描述:
     *    获得报关单号
     * @return 报关单号 : String
     */
    public String getDeclarationsno()
    {
		return this.declarationsno;
    }

    /**
     * 功能描述:
     *    设置报关单号
     * @param declarationsno : String
     */
    public void setDeclarationsno(String declarationsno)
    {
	    this.declarationsno = declarationsno;
    }
    

    /**
     * 功能描述:
     *    获得贸易方式
     * @return 贸易方式 : String
     */
    public String getTrade_type()
    {
		return this.trade_type;
    }

    /**
     * 功能描述:
     *    设置贸易方式
     * @param trade_type : String
     */
    public void setTrade_type(String trade_type)
    {
	    this.trade_type = trade_type;
    }
    

    /**
     * 功能描述:
     *    获得成交方式
     * @return 成交方式 : String
     */
    public String getTransactionway()
    {
		return this.transactionway;
    }

    /**
     * 功能描述:
     *    设置成交方式
     * @param transactionway : String
     */
    public void setTransactionway(String transactionway)
    {
	    this.transactionway = transactionway;
    }
    

    /**
     * 功能描述:
     *    获得进口口岸
     * @return 进口口岸 : String
     */
    public String getImportport()
    {
		return this.importport;
    }

    /**
     * 功能描述:
     *    设置进口口岸
     * @param importport : String
     */
    public void setImportport(String importport)
    {
	    this.importport = importport;
    }
    

    /**
     * 功能描述:
     *    获得货币
     * @return 货币 : String
     */
    public String getWaers()
    {
		return this.waers;
    }

    /**
     * 功能描述:
     *    设置货币
     * @param waers : String
     */
    public void setWaers(String waers)
    {
	    this.waers = waers;
    }
    

    /**
     * 功能描述:
     *    获得合同号
     * @return 合同号 : String
     */
    public String getContractno()
    {
		return this.contractno;
    }

    /**
     * 功能描述:
     *    设置合同号
     * @param contractno : String
     */
    public void setContractno(String contractno)
    {
	    this.contractno = contractno;
    }
    

    /**
     * 功能描述:
     *    获得报关金额
     * @return 报关金额 : String
     */
    public String getBgje()
    {
		return this.bgje;
    }

    /**
     * 功能描述:
     *    设置报关金额
     * @param bgje : String
     */
    public void setBgje(String bgje)
    {
	    this.bgje = bgje;
    }
    

    /**
     * 功能描述:
     *    获得报关金额折美元
     * @return 报关金额折美元 : String
     */
    public String getBgjezmy()
    {
		return this.bgjezmy;
    }

    /**
     * 功能描述:
     *    设置报关金额折美元
     * @param bgjezmy : String
     */
    public void setBgjezmy(String bgjezmy)
    {
	    this.bgjezmy = bgjezmy;
    }
    

    /**
     * 功能描述:
     *    获得进口日期
     * @return 进口日期 : String
     */
    public String getImportdate()
    {
		return this.importdate;
    }

    /**
     * 功能描述:
     *    设置进口日期
     * @param importdate : String
     */
    public void setImportdate(String importdate)
    {
	    this.importdate = importdate;
    }
    

    /**
     * 功能描述:
     *    获得总量核查状态
     * @return 总量核查状态 : String
     */
    public String getTotalcheckflag()
    {
		return this.totalcheckflag;
    }

    /**
     * 功能描述:
     *    设置总量核查状态
     * @param totalcheckflag : String
     */
    public void setTotalcheckflag(String totalcheckflag)
    {
	    this.totalcheckflag = totalcheckflag;
    }
    

    /**
     * 功能描述:
     *    获得逐笔核查状态
     * @return 逐笔核查状态 : String
     */
    public String getEarchcheckflag()
    {
		return this.earchcheckflag;
    }

    /**
     * 功能描述:
     *    设置逐笔核查状态
     * @param earchcheckflag : String
     */
    public void setEarchcheckflag(String earchcheckflag)
    {
	    this.earchcheckflag = earchcheckflag;
    }
    
    
	/**
	 *  默认构造器
	 */
    public ImpDeclaration()
    {
    	super();
    }
}
