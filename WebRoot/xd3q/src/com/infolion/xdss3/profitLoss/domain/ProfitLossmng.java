/*
 * @(#)ProfitLossmng.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月21日 11点46分23秒
 *  描　述：创建
 */
package com.infolion.xdss3.profitLoss.domain;

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
 * 存货浮动盈亏调查表(ProfitLossmng)实体类
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
@Table(name = "YPROFITLOSS")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class ProfitLossmng extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 浮动盈亏ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="PROFITLOSSID")
      
    private String profitlossid;   
    
	/*
	 * 公司id
	 */
    @Column(name="COMPANYID")
    @ValidateRule(dataType=9,label="公司id",maxLength= 36,required=false)  
    private String companyid;   
    
	/*
	 * 日期
	 */
    @Column(name="DATEVALUE")
    @ValidateRule(dataType=9,label="日期",maxLength= 20,required=false)  
    private String datevalue;   
    
	/*
	 * 部门ID
	 */
    @Column(name="DEPTID")
    @ValidateRule(dataType=9,label="部门ID",maxLength= 36,required=false)  
    private String deptid;   
    
	/*
	 * 外部纸质合同号
	 */
    @Column(name="EKKO_UNSEZ")
    @ValidateRule(dataType=9,label="外部纸质合同号",maxLength= 50,required=false)  
    private String ekko_unsez;   
    
	/*
	 * 物料组
	 */
    @Column(name="MATERIAL_GROUP")
    @ValidateRule(dataType=9,label="物料组",maxLength= 10,required=false)  
    private String material_group;   
    
	/*
	 * 物料号
	 */
    @Column(name="MATERIAL_CODE")
    @ValidateRule(dataType=9,label="物料号",maxLength= 36,required=false)  
    private String material_code;   
    
	/*
	 * 物料名称
	 */
    @Column(name="METERIAL_NAME")
    @ValidateRule(dataType=9,label="物料名称",maxLength= 255,required=false)  
    private String meterial_name;   
    
	/*
	 * 采购订单
	 */
    @Column(name="ORDERID")
    @ValidateRule(dataType=9,label="采购订单",maxLength= 10,required=false)  
    private String orderid;   
    
	/*
	 * 批次
	 */
    @Column(name="BATCH_NO")
    @ValidateRule(dataType=9,label="批次",maxLength= 10,required=false)  
    private String batch_no;   
    
	/*
	 * 供应商/委托方ID
	 */
    @Column(name="PROVIDERID")
    @ValidateRule(dataType=9,label="供应商/委托方ID",maxLength= 36,required=false)  
    private String providerid;   
    
	/*
	 * 供应商
	 */
    @Column(name="PROVIDERNAME")
    @ValidateRule(dataType=9,label="供应商",maxLength= 150,required=false)  
    private String providername;   
    
	/*
	 * 税率
	 */
    @Column(name="TAXRATE")
    @ValidateRule(dataType=9,label="税率",maxLength= 10,required=false)  
    private String taxrate;   
    
	/*
	 * 单位
	 */
    @Column(name="UNIT")
    @ValidateRule(dataType=9,label="单位",maxLength= 10,required=false)  
    private String unit;   
    
	/*
	 * 采购订单数量
	 */
    @Column(name="QUANTITY")
    @ValidateRule(dataType=0,label="采购订单数量",required=false)  
    private BigDecimal quantity;   
    
	/*
	 * 单价
	 */
    @Column(name="UNITPRICE")
    @ValidateRule(dataType=0,label="单价",required=false)  
    private BigDecimal unitprice;   
    
	/*
	 * 总金额（成本）
	 */
    @Column(name="TOTALVALUE")
    @ValidateRule(dataType=0,label="总金额（成本）",required=false)  
    private BigDecimal totalvalue;   
    
	/*
	 * 科目
	 */
    @Column(name="SUBJECT")
    @ValidateRule(dataType=9,label="科目",maxLength= 20,required=false)  
    private String subject;   
    
	/*
	 * 类型
	 */
    @Column(name="TYPE")
    @ValidateRule(dataType=9,label="类型",maxLength= 30,required=false)  
    private String type;   
    
	/*
	 * 市场单价
	 */
    @Column(name="MARKETUNITPRICE")
    @ValidateRule(dataType=0,label="市场单价",required=false)  
    private BigDecimal marketunitprice;   
    
	/*
	 * 含税总金额
	 */
    @Column(name="TAXINCLUDEVLAUE")
    @ValidateRule(dataType=0,label="含税总金额",required=false)  
    private BigDecimal taxincludevlaue;   
    
	/*
	 * 估计售价总额
	 */
    @Column(name="ESTIMATEVALUE")
    @ValidateRule(dataType=0,label="估计售价总额",required=false)  
    private BigDecimal estimatevalue;   
    
	/*
	 * 单位浮亏/盈
	 */
    @Column(name="UNITPROFITLOSS")
    @ValidateRule(dataType=0,label="单位浮亏/盈",required=false)  
    private BigDecimal unitprofitloss;   
    
	/*
	 * 总浮亏/盈
	 */
    @Column(name="TOTALPROFITLOSS")
    @ValidateRule(dataType=0,label="总浮亏/盈",required=false)  
    private BigDecimal totalprofitloss;   
    
	/*
	 * 最大损失备注
	 */
    @Column(name="MAXLOSSCOMMENT")
    @ValidateRule(dataType=9,label="最大损失备注",maxLength= 255,required=false)  
    private String maxlosscomment;   
    
	/*
	 * 扣除预计返补后的总浮盈亏
	 */
    @Column(name="REMAINTOTALVALUE")
    @ValidateRule(dataType=0,label="扣除预计返补后的总浮盈亏",required=false)  
    private BigDecimal remaintotalvalue;   
    
	/*
	 * 返补备注
	 */
    @Column(name="BACKCOMMENT")
    @ValidateRule(dataType=9,label="返补备注",maxLength= 255,required=false)  
    private String backcomment;   
    
	/*
	 * 创建人
	 */
    @Column(name="CREATOR")
    @ValidateRule(dataType=9,label="创建人",maxLength= 36,required=false)  
    private String creator;   
    
	/*
	 * 创建日期
	 */
    @Column(name="CREATETIME")
    @ValidateRule(dataType=9,label="创建日期",maxLength= 14,required=false)  
    private String createtime;   
    
	/*
	 * 最后修改者
	 */
    @Column(name="LASTMODIFYER")
    @ValidateRule(dataType=9,label="最后修改者",maxLength= 36,required=false)  
    private String lastmodifyer;   
    
	/*
	 * 最后修改日期
	 */
    @Column(name="LASTMODIFYTIME")
    @ValidateRule(dataType=9,label="最后修改日期",maxLength= 14,required=false)  
    private String lastmodifytime;   
    
    /*
     * 立项号
     */
    @Column(name="PROJECT_NO")
    @ValidateRule(dataType=9,label="立项号",maxLength= 300,required=false)  
    private String project_no;
    
    /*
     * 合同号
     */
    @Column(name="CONTRACTNO")
    @ValidateRule(dataType=9,label="合同号",required=false)  
    private String contractno;
    
    /*
     * 采购订单数量
     */
    @Column(name="POSITIONVALUE")
    @ValidateRule(dataType=0,label="持仓费用",required=false)  
    private BigDecimal positionvalue;   
    
    /*
     * SAP传送过来标识
     */
    @Column(name="FLAG")
    @ValidateRule(dataType=9,label="标识",required=false)  
    private String flag;   
    
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
     *    获得浮动盈亏ID
     * @return 浮动盈亏ID : String
     */
    public String getProfitlossid()
    {
		return this.profitlossid;
    }

    /**
     * 功能描述:
     *    设置浮动盈亏ID
     * @param profitlossid : String
     */
    public void setProfitlossid(String profitlossid)
    {
	    this.profitlossid = profitlossid;
    }
    

    /**
     * 功能描述:
     *    获得公司id
     * @return 公司id : String
     */
    public String getCompanyid()
    {
		return this.companyid;
    }

    /**
     * 功能描述:
     *    设置公司id
     * @param companyid : String
     */
    public void setCompanyid(String companyid)
    {
	    this.companyid = companyid;
    }
    

    /**
     * 功能描述:
     *    获得日期
     * @return 日期 : String
     */
    public String getDatevalue()
    {
		return this.datevalue;
    }

    /**
     * 功能描述:
     *    设置日期
     * @param datevalue : String
     */
    public void setDatevalue(String datevalue)
    {
	    this.datevalue = datevalue;
    }
    

    /**
     * 功能描述:
     *    获得部门ID
     * @return 部门ID : String
     */
    public String getDeptid()
    {
		return this.deptid;
    }

    /**
     * 功能描述:
     *    设置部门ID
     * @param deptid : String
     */
    public void setDeptid(String deptid)
    {
	    this.deptid = deptid;
    }
    

    /**
     * 功能描述:
     *    获得外部纸质合同号
     * @return 外部纸质合同号 : String
     */
    public String getEkko_unsez()
    {
		return this.ekko_unsez;
    }

    /**
     * 功能描述:
     *    设置外部纸质合同号
     * @param ekko_unsez : String
     */
    public void setEkko_unsez(String ekko_unsez)
    {
	    this.ekko_unsez = ekko_unsez;
    }
    

    /**
     * 功能描述:
     *    获得物料组
     * @return 物料组 : String
     */
    public String getMaterial_group()
    {
		return this.material_group;
    }

    /**
     * 功能描述:
     *    设置物料组
     * @param material_group : String
     */
    public void setMaterial_group(String material_group)
    {
	    this.material_group = material_group;
    }
    

    /**
     * 功能描述:
     *    获得物料号
     * @return 物料号 : String
     */
    public String getMaterial_code()
    {
		return this.material_code;
    }

    /**
     * 功能描述:
     *    设置物料号
     * @param material_code : String
     */
    public void setMaterial_code(String material_code)
    {
	    this.material_code = material_code;
    }
    

    /**
     * 功能描述:
     *    获得物料名称
     * @return 物料名称 : String
     */
    public String getMeterial_name()
    {
		return this.meterial_name;
    }

    /**
     * 功能描述:
     *    设置物料名称
     * @param meterial_name : String
     */
    public void setMeterial_name(String meterial_name)
    {
	    this.meterial_name = meterial_name;
    }
    

    /**
     * 功能描述:
     *    获得采购订单
     * @return 采购订单 : String
     */
    public String getOrderid()
    {
		return this.orderid;
    }

    /**
     * 功能描述:
     *    设置采购订单
     * @param orderid : String
     */
    public void setOrderid(String orderid)
    {
	    this.orderid = orderid;
    }
    

    /**
     * 功能描述:
     *    获得批次
     * @return 批次 : String
     */
    public String getBatch_no()
    {
		return this.batch_no;
    }

    /**
     * 功能描述:
     *    设置批次
     * @param batch_no : String
     */
    public void setBatch_no(String batch_no)
    {
	    this.batch_no = batch_no;
    }
    

    /**
     * 功能描述:
     *    获得供应商/委托方ID
     * @return 供应商/委托方ID : String
     */
    public String getProviderid()
    {
		return this.providerid;
    }

    /**
     * 功能描述:
     *    设置供应商/委托方ID
     * @param providerid : String
     */
    public void setProviderid(String providerid)
    {
	    this.providerid = providerid;
    }
    

    /**
     * 功能描述:
     *    获得供应商
     * @return 供应商 : String
     */
    public String getProvidername()
    {
		return this.providername;
    }

    /**
     * 功能描述:
     *    设置供应商
     * @param providername : String
     */
    public void setProvidername(String providername)
    {
	    this.providername = providername;
    }
    

    /**
     * 功能描述:
     *    获得税率
     * @return 税率 : String
     */
    public String getTaxrate()
    {
		return this.taxrate;
    }

    /**
     * 功能描述:
     *    设置税率
     * @param taxrate : String
     */
    public void setTaxrate(String taxrate)
    {
	    this.taxrate = taxrate;
    }
    

    /**
     * 功能描述:
     *    获得单位
     * @return 单位 : String
     */
    public String getUnit()
    {
		return this.unit;
    }

    /**
     * 功能描述:
     *    设置单位
     * @param unit : String
     */
    public void setUnit(String unit)
    {
	    this.unit = unit;
    }
    

    /**
     * 功能描述:
     *    获得采购订单数量
     * @return 采购订单数量 : BigDecimal
     */
    public BigDecimal getQuantity()
    {
		return this.quantity;
    }

    /**
     * 功能描述:
     *    设置采购订单数量
     * @param quantity : BigDecimal
     */
    public void setQuantity(BigDecimal quantity)
    {
	    this.quantity = quantity;
    }
    

    /**
     * 功能描述:
     *    获得单价
     * @return 单价 : BigDecimal
     */
    public BigDecimal getUnitprice()
    {
		return this.unitprice;
    }

    /**
     * 功能描述:
     *    设置单价
     * @param unitprice : BigDecimal
     */
    public void setUnitprice(BigDecimal unitprice)
    {
	    this.unitprice = unitprice;
    }
    

    /**
     * 功能描述:
     *    获得总金额（成本）
     * @return 总金额（成本） : BigDecimal
     */
    public BigDecimal getTotalvalue()
    {
		return this.totalvalue;
    }

    /**
     * 功能描述:
     *    设置总金额（成本）
     * @param totalvalue : BigDecimal
     */
    public void setTotalvalue(BigDecimal totalvalue)
    {
	    this.totalvalue = totalvalue;
    }
    

    /**
     * 功能描述:
     *    获得科目
     * @return 科目 : String
     */
    public String getSubject()
    {
		return this.subject;
    }

    /**
     * 功能描述:
     *    设置科目
     * @param subject : String
     */
    public void setSubject(String subject)
    {
	    this.subject = subject;
    }
    

    /**
     * 功能描述:
     *    获得类型
     * @return 类型 : String
     */
    public String getType()
    {
		return this.type;
    }

    /**
     * 功能描述:
     *    设置类型
     * @param type : String
     */
    public void setType(String type)
    {
	    this.type = type;
    }
    

    /**
     * 功能描述:
     *    获得市场单价
     * @return 市场单价 : BigDecimal
     */
    public BigDecimal getMarketunitprice()
    {
		return this.marketunitprice;
    }

    /**
     * 功能描述:
     *    设置市场单价
     * @param marketunitprice : BigDecimal
     */
    public void setMarketunitprice(BigDecimal marketunitprice)
    {
	    this.marketunitprice = marketunitprice;
    }
    

    /**
     * 功能描述:
     *    获得含税总金额
     * @return 含税总金额 : BigDecimal
     */
    public BigDecimal getTaxincludevlaue()
    {
		return this.taxincludevlaue;
    }

    /**
     * 功能描述:
     *    设置含税总金额
     * @param taxincludevlaue : BigDecimal
     */
    public void setTaxincludevlaue(BigDecimal taxincludevlaue)
    {
	    this.taxincludevlaue = taxincludevlaue;
    }
    

    /**
     * 功能描述:
     *    获得估计售价总额
     * @return 估计售价总额 : BigDecimal
     */
    public BigDecimal getEstimatevalue()
    {
		return this.estimatevalue;
    }

    /**
     * 功能描述:
     *    设置估计售价总额
     * @param estimatevalue : BigDecimal
     */
    public void setEstimatevalue(BigDecimal estimatevalue)
    {
	    this.estimatevalue = estimatevalue;
    }
    

    /**
     * 功能描述:
     *    获得单位浮亏/盈
     * @return 单位浮亏/盈 : BigDecimal
     */
    public BigDecimal getUnitprofitloss()
    {
		return this.unitprofitloss;
    }

    /**
     * 功能描述:
     *    设置单位浮亏/盈
     * @param unitprofitloss : BigDecimal
     */
    public void setUnitprofitloss(BigDecimal unitprofitloss)
    {
	    this.unitprofitloss = unitprofitloss;
    }
    

    /**
     * 功能描述:
     *    获得总浮亏/盈
     * @return 总浮亏/盈 : BigDecimal
     */
    public BigDecimal getTotalprofitloss()
    {
		return this.totalprofitloss;
    }

    /**
     * 功能描述:
     *    设置总浮亏/盈
     * @param totalprofitloss : BigDecimal
     */
    public void setTotalprofitloss(BigDecimal totalprofitloss)
    {
	    this.totalprofitloss = totalprofitloss;
    }
    

    /**
     * 功能描述:
     *    获得最大损失备注
     * @return 最大损失备注 : String
     */
    public String getMaxlosscomment()
    {
		return this.maxlosscomment;
    }

    /**
     * 功能描述:
     *    设置最大损失备注
     * @param maxlosscomment : String
     */
    public void setMaxlosscomment(String maxlosscomment)
    {
	    this.maxlosscomment = maxlosscomment;
    }
    

    /**
     * 功能描述:
     *    获得扣除预计返补后的总浮盈亏
     * @return 扣除预计返补后的总浮盈亏 : BigDecimal
     */
    public BigDecimal getRemaintotalvalue()
    {
		return this.remaintotalvalue;
    }

    /**
     * 功能描述:
     *    设置扣除预计返补后的总浮盈亏
     * @param remaintotalvalue : BigDecimal
     */
    public void setRemaintotalvalue(BigDecimal remaintotalvalue)
    {
	    this.remaintotalvalue = remaintotalvalue;
    }
    

    /**
     * 功能描述:
     *    获得返补备注
     * @return 返补备注 : String
     */
    public String getBackcomment()
    {
		return this.backcomment;
    }

    /**
     * 功能描述:
     *    设置返补备注
     * @param backcomment : String
     */
    public void setBackcomment(String backcomment)
    {
	    this.backcomment = backcomment;
    }
    

    /**
     * 功能描述:
     *    获得创建人
     * @return 创建人 : String
     */
    public String getCreator()
    {
		return this.creator;
    }

    /**
     * 功能描述:
     *    设置创建人
     * @param creator : String
     */
    public void setCreator(String creator)
    {
	    this.creator = creator;
    }
    

    /**
     * 功能描述:
     *    获得创建日期
     * @return 创建日期 : String
     */
    public String getCreatetime()
    {
		return this.createtime;
    }

    /**
     * 功能描述:
     *    设置创建日期
     * @param createtime : String
     */
    public void setCreatetime(String createtime)
    {
	    this.createtime = createtime;
    }
    

    /**
     * 功能描述:
     *    获得最后修改者
     * @return 最后修改者 : String
     */
    public String getLastmodifyer()
    {
		return this.lastmodifyer;
    }

    /**
     * 功能描述:
     *    设置最后修改者
     * @param lastmodifyer : String
     */
    public void setLastmodifyer(String lastmodifyer)
    {
	    this.lastmodifyer = lastmodifyer;
    }
    

    /**
     * 功能描述:
     *    获得最后修改日期
     * @return 最后修改日期 : String
     */
    public String getLastmodifytime()
    {
		return this.lastmodifytime;
    }

    /**
     * 功能描述:
     *    设置最后修改日期
     * @param lastmodifytime : String
     */
    public void setLastmodifytime(String lastmodifytime)
    {
	    this.lastmodifytime = lastmodifytime;
    }
    
    
	/**
     * @return the project_no
     */
    public String getProject_no() {
        return project_no;
    }

    /**
     * @param projectNo the project_no to set
     */
    public void setProject_no(String projectNo) {
        project_no = projectNo;
    }

    /**
     * @return the contractno
     */
    public String getContractno() {
        return contractno;
    }

    /**
     * @param contractno the contractno to set
     */
    public void setContractno(String contractno) {
        this.contractno = contractno;
    }

    /**
     * @return the positionvalue
     */
    public BigDecimal getPositionvalue() {
        return positionvalue;
    }

    /**
     * @param positionvalue the positionvalue to set
     */
    public void setPositionvalue(BigDecimal positionvalue) {
        this.positionvalue = positionvalue;
    }

    /**
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
	 *  默认构造器
	 */
    public ProfitLossmng()
    {
    	super();
    }
}
