/*
 * @(#)ImportPaymentItem.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月25日 09点52分21秒
 *  描　述：创建
 */
package com.infolion.xdss3.payment.domain;

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
import com.infolion.xdss3.payment.domain.ImportPayment;

/**
 * <pre>
 * 付款金额分配(ImportPaymentItem)实体类
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
@Table(name = "YPAYMENTITEM")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class ImportPaymentItem extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 付款金额分配ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="PAYMENTITEMID")
      
    private String paymentitemid;   
    
	/*
	 * 付款单号
	 */
    @Column(name="PAYMENTNO")
    @ValidateRule(dataType=9,label="付款单号",maxLength= 30,required=false)  
    private String paymentno;   
    
	/*
	 * 项目编号
	 */
    @Column(name="PROJECT_NO")
    @ValidateRule(dataType=9,label="项目编号",maxLength= 200,required=false)  
    private String project_no;   
    
	/*
	 * 到单号
	 */
    @Column(name="PICK_LIST_NO")
    @ValidateRule(dataType=9,label="到单号",maxLength= 100,required=false)  
    private String pick_list_no;   
    
	/*
	 * 采购合同号
	 */
    @Column(name="CONTRACT_NO")
    @ValidateRule(dataType=9,label="采购合同号",maxLength= 50,required=false)  
    private String contract_no;   
    
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
	 * 预付金额
	 */
    @Column(name="PREPAYAMOUNT")
    @ValidateRule(dataType=0,label="预付金额",required=false)  
    private BigDecimal prepayamount;   
    
	/*
	 * 票清款结清标识
	 */
    @Column(name="BILLISCLEAR")
    @ValidateRule(dataType=9,label="票清款结清标识",maxLength= 1,required=false)  
    private String billisclear;   
    
	/*
	 * 分配金额（本位币）
	 */
    @Column(name="ASSIGNAMOUNT2")
    @ValidateRule(dataType=0,label="分配金额（本位币）",required=false)  
    private BigDecimal assignamount2;   
    
	/*
	 * 外部纸质合同号
	 */
    @Column(name="OLD_CONTRACT_NO")
    @ValidateRule(dataType=9,label="外部纸质合同号",maxLength= 200,required=false)  
    private String old_contract_no;   
    
	/*
	 * 货款金额
	 */
    @Column(name="GOODSAMOUNT")
    @ValidateRule(dataType=0,label="货款金额",required=false)  
    private BigDecimal goodsamount;   
    
	/*
	 * 进口付款
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="PAYMENTID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private ImportPayment importPayment;
    
	/*
	 * 物料组名称
	 */
    @Column(name="YMAT_GROUP")
    @ValidateRule(dataType=9,label="物料组名称",maxLength= 50,required=false)  
    private String ymat_group;  
    
    
    
    public String getYmat_group() {
		return ymat_group;
	}

	public void setYmat_group(String ymat_group) {
		this.ymat_group = ymat_group;
	}


	@Transient
	private BigDecimal clearedamount;

	public BigDecimal getClearedamount()
	{
		return clearedamount;
	}

	public void setClearedamount(BigDecimal clearedamount)
	{
		this.clearedamount = clearedamount;
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
     *    获得付款金额分配ID
     * @return 付款金额分配ID : String
     */
    public String getPaymentitemid()
    {
		return this.paymentitemid;
    }

    /**
     * 功能描述:
     *    设置付款金额分配ID
     * @param paymentitemid : String
     */
    public void setPaymentitemid(String paymentitemid)
    {
	    this.paymentitemid = paymentitemid;
    }
    

    /**
     * 功能描述:
     *    获得付款单号
     * @return 付款单号 : String
     */
    public String getPaymentno()
    {
		return this.paymentno;
    }

    /**
     * 功能描述:
     *    设置付款单号
     * @param paymentno : String
     */
    public void setPaymentno(String paymentno)
    {
	    this.paymentno = paymentno;
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
     *    获得到单号
     * @return 到单号 : String
     */
    public String getPick_list_no()
    {
		return this.pick_list_no;
    }

    /**
     * 功能描述:
     *    设置到单号
     * @param pick_list_no : String
     */
    public void setPick_list_no(String pick_list_no)
    {
	    this.pick_list_no = pick_list_no;
    }
    

    /**
     * 功能描述:
     *    获得采购合同号
     * @return 采购合同号 : String
     */
    public String getContract_no()
    {
		return this.contract_no;
    }

    /**
     * 功能描述:
     *    设置采购合同号
     * @param contract_no : String
     */
    public void setContract_no(String contract_no)
    {
	    this.contract_no = contract_no;
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
     *    获得预付金额
     * @return 预付金额 : BigDecimal
     */
    public BigDecimal getPrepayamount()
    {
		return this.prepayamount;
    }

    /**
     * 功能描述:
     *    设置预付金额
     * @param prepayamount : BigDecimal
     */
    public void setPrepayamount(BigDecimal prepayamount)
    {
	    this.prepayamount = prepayamount;
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
     *    获得外部纸质合同号
     * @return 外部纸质合同号 : String
     */
    public String getOld_contract_no()
    {
		return this.old_contract_no;
    }

    /**
     * 功能描述:
     *    设置外部纸质合同号
     * @param old_contract_no : String
     */
    public void setOld_contract_no(String old_contract_no)
    {
	    this.old_contract_no = old_contract_no;
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
     *    获得进口付款
     * @return 进口付款 : ImportPayment
     */
    public ImportPayment getImportPayment()
    {
		return this.importPayment;
    }

    /**
     * 功能描述:
     *    设置进口付款
     * @param importPayment : ImportPayment
     */
    public void setImportPayment(ImportPayment importPayment)
    {
	    this.importPayment = importPayment;
    }
    
    
	/**
	 *  默认构造器
	 */
    public ImportPaymentItem()
    {
    	super();
    }
}
