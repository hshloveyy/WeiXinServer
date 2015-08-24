/*
 * @(#)ExportCcollect.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月27日 08点04分08秒
 *  描　述：创建
 */
package com.infolion.xdss3.exportCcollect.domain;

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
 * 出单清款表(ExportCcollect)实体类
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
@Table(name = "YEXPORTCCOLLECT")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class ExportCcollect extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 出单清款ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="EXPORTCCOLLECTID")
      
    private String exportccollectid;   
    
	/*
	 * 出单审单记录ID
	 */
    @Column(name="EXPORTBILLEXAMID")
    @ValidateRule(dataType=9,label="出单审单记录ID",maxLength= 36,required=false)  
    private String exportbillexamid;   
    
	/*
	 * 收款ID
	 */
    @Column(name="COLLECTID")
    @ValidateRule(dataType=9,label="收款ID",maxLength= 36,required=false)  
    private String collectid;   
    
	/*
	 * 多收ID
	 */
    @Column(name="OVERCOLLECTID")
    @ValidateRule(dataType=9,label="多收ID",maxLength= 36,required=false)  
    private String overcollectid;   
    
	/*
	 * 财务凭证号
	 */
    @Column(name="VOUCHERNO")
    @ValidateRule(dataType=9,label="财务凭证号",maxLength= 30,required=false)  
    private String voucherno;   
    
	/*
	 * 项目ID
	 */
    @Column(name="PROJECT_ID")
    @ValidateRule(dataType=9,label="项目ID",maxLength= 36,required=false)  
    private String project_id;   
    
	/*
	 * 项目编号
	 */
    @Column(name="PROJECT_NO")
    @ValidateRule(dataType=9,label="项目编号",maxLength= 200,required=false)  
    private String project_no;   
    
	/*
	 * 合同号
	 */
    @Column(name="CONTRACT_NO")
    @ValidateRule(dataType=9,label="合同号",maxLength= 50,required=false)  
    private String contract_no;   
    
	/*
	 * 收款单号
	 */
    @Column(name="COLLECTNO")
    @ValidateRule(dataType=9,label="收款单号",maxLength= 50,required=false)  
    private String collectno;   
    
	/*
	 * 收款状态
	 */
    @Column(name="COLLECTSTATE")
    @ValidateRule(dataType=9,label="收款状态",maxLength= 1,required=false)  
    private String collectstate;   
    
	/*
	 * 预计发货日
	 */
    @Column(name="SENDGOODSDATE")
    @ValidateRule(dataType=9,label="预计发货日",maxLength= 14,required=false)  
    private String sendgoodsdate;   
    
	/*
	 * 预收款金额
	 */
    @Column(name="COLLECTAMOUNT")
    @ValidateRule(dataType=0,label="预收款金额",required=false)  
    private BigDecimal collectamount;   
    
	/*
	 * 货币
	 */
    @Column(name="CURRENCY")
    @ValidateRule(dataType=9,label="货币",maxLength= 5,required=false)  
    private String currency;   
    
	/*
	 * 押汇汇率
	 */
    @Column(name="EXCHANGERATE")
    @ValidateRule(dataType=0,label="押汇汇率",required=false)  
    private BigDecimal exchangerate;   
    
	/*
	 * 已清金额
	 */
    @Column(name="OFFSETAMOUNT")
    @ValidateRule(dataType=0,label="已清金额",required=false)  
    private BigDecimal offsetamount;   
    
	/*
	 * 未清金额
	 */
    @Column(name="UNOFFSETAMOUNT")
    @ValidateRule(dataType=0,label="未清金额",required=false)  
    private BigDecimal unoffsetamount;   
    
	/*
	 * 在批金额
	 */
    @Column(name="ONROADAMOUNT")
    @ValidateRule(dataType=0,label="在批金额",required=false)  
    private BigDecimal onroadamount;   
    
	/*
	 * 清帐金额
	 */
    @Column(name="CLEARAMOUNT")
    @ValidateRule(dataType=0,label="清帐金额",required=false)  
    private BigDecimal clearamount;   
    

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
     *    获得出单清款ID
     * @return 出单清款ID : String
     */
    public String getExportccollectid()
    {
		return this.exportccollectid;
    }

    /**
     * 功能描述:
     *    设置出单清款ID
     * @param exportccollectid : String
     */
    public void setExportccollectid(String exportccollectid)
    {
	    this.exportccollectid = exportccollectid;
    }
    

    /**
     * 功能描述:
     *    获得出单审单记录ID
     * @return 出单审单记录ID : String
     */
    public String getExportbillexamid()
    {
		return this.exportbillexamid;
    }

    /**
     * 功能描述:
     *    设置出单审单记录ID
     * @param exportbillexamid : String
     */
    public void setExportbillexamid(String exportbillexamid)
    {
	    this.exportbillexamid = exportbillexamid;
    }
    

    /**
     * 功能描述:
     *    获得收款ID
     * @return 收款ID : String
     */
    public String getCollectid()
    {
		return this.collectid;
    }

    /**
     * 功能描述:
     *    设置收款ID
     * @param collectid : String
     */
    public void setCollectid(String collectid)
    {
	    this.collectid = collectid;
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
     *    获得财务凭证号
     * @return 财务凭证号 : String
     */
    public String getVoucherno()
    {
		return this.voucherno;
    }

    /**
     * 功能描述:
     *    设置财务凭证号
     * @param voucherno : String
     */
    public void setVoucherno(String voucherno)
    {
	    this.voucherno = voucherno;
    }
    

    /**
     * 功能描述:
     *    获得项目ID
     * @return 项目ID : String
     */
    public String getProject_id()
    {
		return this.project_id;
    }

    /**
     * 功能描述:
     *    设置项目ID
     * @param project_id : String
     */
    public void setProject_id(String project_id)
    {
	    this.project_id = project_id;
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
     *    获得收款状态
     * @return 收款状态 : String
     */
    public String getCollectstate()
    {
		return this.collectstate;
    }

    /**
     * 功能描述:
     *    设置收款状态
     * @param collectstate : String
     */
    public void setCollectstate(String collectstate)
    {
	    this.collectstate = collectstate;
    }
    

    /**
     * 功能描述:
     *    获得预计发货日
     * @return 预计发货日 : String
     */
    public String getSendgoodsdate()
    {
		return this.sendgoodsdate;
    }

    /**
     * 功能描述:
     *    设置预计发货日
     * @param sendgoodsdate : String
     */
    public void setSendgoodsdate(String sendgoodsdate)
    {
	    this.sendgoodsdate = sendgoodsdate;
    }
    

    /**
     * 功能描述:
     *    获得预收款金额
     * @return 预收款金额 : BigDecimal
     */
    public BigDecimal getCollectamount()
    {
		return this.collectamount;
    }

    /**
     * 功能描述:
     *    设置预收款金额
     * @param collectamount : BigDecimal
     */
    public void setCollectamount(BigDecimal collectamount)
    {
	    this.collectamount = collectamount;
    }
    

    /**
     * 功能描述:
     *    获得货币
     * @return 货币 : String
     */
    public String getCurrency()
    {
		return this.currency;
    }

    /**
     * 功能描述:
     *    设置货币
     * @param currency : String
     */
    public void setCurrency(String currency)
    {
	    this.currency = currency;
    }
    

    /**
     * 功能描述:
     *    获得押汇汇率
     * @return 押汇汇率 : BigDecimal
     */
    public BigDecimal getExchangerate()
    {
		return this.exchangerate;
    }

    /**
     * 功能描述:
     *    设置押汇汇率
     * @param exchangerate : BigDecimal
     */
    public void setExchangerate(BigDecimal exchangerate)
    {
	    this.exchangerate = exchangerate;
    }
    

    /**
     * 功能描述:
     *    获得已清金额
     * @return 已清金额 : BigDecimal
     */
    public BigDecimal getOffsetamount()
    {
		return this.offsetamount;
    }

    /**
     * 功能描述:
     *    设置已清金额
     * @param offsetamount : BigDecimal
     */
    public void setOffsetamount(BigDecimal offsetamount)
    {
	    this.offsetamount = offsetamount;
    }
    

    /**
     * 功能描述:
     *    获得未清金额
     * @return 未清金额 : BigDecimal
     */
    public BigDecimal getUnoffsetamount()
    {
		return this.unoffsetamount;
    }

    /**
     * 功能描述:
     *    设置未清金额
     * @param unoffsetamount : BigDecimal
     */
    public void setUnoffsetamount(BigDecimal unoffsetamount)
    {
	    this.unoffsetamount = unoffsetamount;
    }
    

    /**
     * 功能描述:
     *    获得在批金额
     * @return 在批金额 : BigDecimal
     */
    public BigDecimal getOnroadamount()
    {
		return this.onroadamount;
    }

    /**
     * 功能描述:
     *    设置在批金额
     * @param onroadamount : BigDecimal
     */
    public void setOnroadamount(BigDecimal onroadamount)
    {
	    this.onroadamount = onroadamount;
    }
    

    /**
     * 功能描述:
     *    获得清帐金额
     * @return 清帐金额 : BigDecimal
     */
    public BigDecimal getClearamount()
    {
		return this.clearamount;
    }

    /**
     * 功能描述:
     *    设置清帐金额
     * @param clearamount : BigDecimal
     */
    public void setClearamount(BigDecimal clearamount)
    {
	    this.clearamount = clearamount;
    }
    
    
	/**
	 *  默认构造器
	 */
    public ExportCcollect()
    {
    	super();
    }
}
