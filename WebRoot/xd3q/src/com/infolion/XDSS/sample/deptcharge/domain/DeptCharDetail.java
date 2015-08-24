/*
 * @(#)DeptCharDetail.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月11日 09点45分50秒
 *  描　述：创建
 */
package com.infolion.XDSS.sample.deptcharge.domain;

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
import com.infolion.XDSS.sample.deptcharge.domain.DeptCharge;

/**
 * <pre>
 * 费用明细(DeptCharDetail)实体类
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
@Table(name = "YDEPTCHARDETAIL")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class DeptCharDetail extends BaseObject
{
	//fields
	/*
	 * 客户端
	 */
    @Column(name="MANDT")
      
    private String client;   
    
	/*
	 * 费用明细ID
	 */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="DEPTCHARDETAILID")
      
    private String deptchardetailid;   
    
	/*
	 * 预算模版ID
	 */
    @Column(name="BUDTEMID")
    @ValidateRule(dataType=9,label="预算模版ID",maxLength= 36,required=false)  
    private String budtemid;   
    
	/*
	 * 模版预算项ID
	 */
    @Column(name="BUDTEMITEMID")
    @ValidateRule(dataType=9,label="模版预算项ID",maxLength= 36,required=false)  
    private String budtemitemid;   
    
	/*
	 * 预算项名称
	 */
    @Column(name="BUDITEMNAME")
    @ValidateRule(dataType=9,label="预算项名称",maxLength= 100,required=false)  
    private String buditemname;   
    
	/*
	 * 上年1-9月份实际
	 */
    @Column(name="LAST9MONTH")
    @ValidateRule(dataType=9,label="上年1-9月份实际",maxLength= 32,required=false)  
    private String last9month;   
    
	/*
	 * 上年10-12月实际
	 */
    @Column(name="LAST3MONTH")
    @ValidateRule(dataType=9,label="上年10-12月实际",maxLength= 32,required=false)  
    private String last3month;   
    
	/*
	 * 上年预计数
	 */
    @Column(name="LASTPREDICT")
    @ValidateRule(dataType=9,label="上年预计数",maxLength= 32,required=false)  
    private String lastpredict;   
    
	/*
	 * 本年预算数
	 */
    @Column(name="CRNTBUDGET")
    @ValidateRule(dataType=9,label="本年预算数",maxLength= 32,required=false)  
    private String crntbudget;   
    
	/*
	 * 1月份预算
	 */
    @Column(name="BUDMONTH1")
    @ValidateRule(dataType=9,label="1月份预算",maxLength= 32,required=false)  
    private String budmonth1;   
    
	/*
	 * 2月份预算
	 */
    @Column(name="BUDMONTH2")
    @ValidateRule(dataType=9,label="2月份预算",maxLength= 32,required=false)  
    private String budmonth2;   
    
	/*
	 * 3月份预算
	 */
    @Column(name="BUDMONTH3")
    @ValidateRule(dataType=9,label="3月份预算",maxLength= 32,required=false)  
    private String budmonth3;   
    
	/*
	 * 4月份预算
	 */
    @Column(name="BUDMONTH4")
    @ValidateRule(dataType=9,label="4月份预算",maxLength= 32,required=false)  
    private String budmonth4;   
    
	/*
	 * 5月份预算
	 */
    @Column(name="BUDMONTH5")
    @ValidateRule(dataType=9,label="5月份预算",maxLength= 32,required=false)  
    private String budmonth5;   
    
	/*
	 * 6月份预算
	 */
    @Column(name="BUDMONTH6")
    @ValidateRule(dataType=9,label="6月份预算",maxLength= 32,required=false)  
    private String budmonth6;   
    
	/*
	 * 7月份预算
	 */
    @Column(name="BUDMONTH7")
    @ValidateRule(dataType=9,label="7月份预算",maxLength= 32,required=false)  
    private String budmonth7;   
    
	/*
	 * 8月份预算
	 */
    @Column(name="BUDMONTH8")
    @ValidateRule(dataType=9,label="8月份预算",maxLength= 32,required=false)  
    private String budmonth8;   
    
	/*
	 * 9月份预算
	 */
    @Column(name="BUDMONTH9")
    @ValidateRule(dataType=9,label="9月份预算",maxLength= 32,required=false)  
    private String budmonth9;   
    
	/*
	 * 10月份预算
	 */
    @Column(name="BUDMONTH10")
    @ValidateRule(dataType=9,label="10月份预算",maxLength= 32,required=false)  
    private String budmonth10;   
    
	/*
	 * 11月份预算
	 */
    @Column(name="BUDMONTH11")
    @ValidateRule(dataType=9,label="11月份预算",maxLength= 32,required=false)  
    private String budmonth11;   
    
	/*
	 * 12月份预算
	 */
    @Column(name="BUDMONTH12")
    @ValidateRule(dataType=9,label="12月份预算",maxLength= 32,required=false)  
    private String budmonth12;   
    
	/*
	 * 预算编制依据
	 */
    @Column(name="BUDORGACC")
    @ValidateRule(dataType=9,label="预算编制依据",maxLength= 100,required=false)  
    private String budorgacc;   
    
	/*
	 * 预占额度
	 */
    @Column(name="PRELIMIT")
    @ValidateRule(dataType=9,label="预占额度",maxLength= 32,required=false)  
    private String prelimit;   
    
	/*
	 * 实际占用额度
	 */
    @Column(name="ACTAMOUNT")
    @ValidateRule(dataType=9,label="实际占用额度",maxLength= 32,required=false)  
    private String actamount;   
    
	/*
	 * 管理费用预算
	 */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="DEPTCHARGEID")
    @NotFound(action=NotFoundAction.IGNORE)
      
    private DeptCharge deptCharge;   
    

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
     *    获得费用明细ID
     * @return 费用明细ID : String
     */
    public String getDeptchardetailid()
    {
		return this.deptchardetailid;
    }

    /**
     * 功能描述:
     *    设置费用明细ID
     * @param deptchardetailid : String
     */
    public void setDeptchardetailid(String deptchardetailid)
    {
	    this.deptchardetailid = deptchardetailid;
    }
    

    /**
     * 功能描述:
     *    获得预算模版ID
     * @return 预算模版ID : String
     */
    public String getBudtemid()
    {
		return this.budtemid;
    }

    /**
     * 功能描述:
     *    设置预算模版ID
     * @param budtemid : String
     */
    public void setBudtemid(String budtemid)
    {
	    this.budtemid = budtemid;
    }
    

    /**
     * 功能描述:
     *    获得模版预算项ID
     * @return 模版预算项ID : String
     */
    public String getBudtemitemid()
    {
		return this.budtemitemid;
    }

    /**
     * 功能描述:
     *    设置模版预算项ID
     * @param budtemitemid : String
     */
    public void setBudtemitemid(String budtemitemid)
    {
	    this.budtemitemid = budtemitemid;
    }
    

    /**
     * 功能描述:
     *    获得预算项名称
     * @return 预算项名称 : String
     */
    public String getBuditemname()
    {
		return this.buditemname;
    }

    /**
     * 功能描述:
     *    设置预算项名称
     * @param buditemname : String
     */
    public void setBuditemname(String buditemname)
    {
	    this.buditemname = buditemname;
    }
    

    /**
     * 功能描述:
     *    获得上年1-9月份实际
     * @return 上年1-9月份实际 : String
     */
    public String getLast9month()
    {
		return this.last9month;
    }

    /**
     * 功能描述:
     *    设置上年1-9月份实际
     * @param last9month : String
     */
    public void setLast9month(String last9month)
    {
	    this.last9month = last9month;
    }
    

    /**
     * 功能描述:
     *    获得上年10-12月实际
     * @return 上年10-12月实际 : String
     */
    public String getLast3month()
    {
		return this.last3month;
    }

    /**
     * 功能描述:
     *    设置上年10-12月实际
     * @param last3month : String
     */
    public void setLast3month(String last3month)
    {
	    this.last3month = last3month;
    }
    

    /**
     * 功能描述:
     *    获得上年预计数
     * @return 上年预计数 : String
     */
    public String getLastpredict()
    {
		return this.lastpredict;
    }

    /**
     * 功能描述:
     *    设置上年预计数
     * @param lastpredict : String
     */
    public void setLastpredict(String lastpredict)
    {
	    this.lastpredict = lastpredict;
    }
    

    /**
     * 功能描述:
     *    获得本年预算数
     * @return 本年预算数 : String
     */
    public String getCrntbudget()
    {
		return this.crntbudget;
    }

    /**
     * 功能描述:
     *    设置本年预算数
     * @param crntbudget : String
     */
    public void setCrntbudget(String crntbudget)
    {
	    this.crntbudget = crntbudget;
    }
    

    /**
     * 功能描述:
     *    获得1月份预算
     * @return 1月份预算 : String
     */
    public String getBudmonth1()
    {
		return this.budmonth1;
    }

    /**
     * 功能描述:
     *    设置1月份预算
     * @param budmonth1 : String
     */
    public void setBudmonth1(String budmonth1)
    {
	    this.budmonth1 = budmonth1;
    }
    

    /**
     * 功能描述:
     *    获得2月份预算
     * @return 2月份预算 : String
     */
    public String getBudmonth2()
    {
		return this.budmonth2;
    }

    /**
     * 功能描述:
     *    设置2月份预算
     * @param budmonth2 : String
     */
    public void setBudmonth2(String budmonth2)
    {
	    this.budmonth2 = budmonth2;
    }
    

    /**
     * 功能描述:
     *    获得3月份预算
     * @return 3月份预算 : String
     */
    public String getBudmonth3()
    {
		return this.budmonth3;
    }

    /**
     * 功能描述:
     *    设置3月份预算
     * @param budmonth3 : String
     */
    public void setBudmonth3(String budmonth3)
    {
	    this.budmonth3 = budmonth3;
    }
    

    /**
     * 功能描述:
     *    获得4月份预算
     * @return 4月份预算 : String
     */
    public String getBudmonth4()
    {
		return this.budmonth4;
    }

    /**
     * 功能描述:
     *    设置4月份预算
     * @param budmonth4 : String
     */
    public void setBudmonth4(String budmonth4)
    {
	    this.budmonth4 = budmonth4;
    }
    

    /**
     * 功能描述:
     *    获得5月份预算
     * @return 5月份预算 : String
     */
    public String getBudmonth5()
    {
		return this.budmonth5;
    }

    /**
     * 功能描述:
     *    设置5月份预算
     * @param budmonth5 : String
     */
    public void setBudmonth5(String budmonth5)
    {
	    this.budmonth5 = budmonth5;
    }
    

    /**
     * 功能描述:
     *    获得6月份预算
     * @return 6月份预算 : String
     */
    public String getBudmonth6()
    {
		return this.budmonth6;
    }

    /**
     * 功能描述:
     *    设置6月份预算
     * @param budmonth6 : String
     */
    public void setBudmonth6(String budmonth6)
    {
	    this.budmonth6 = budmonth6;
    }
    

    /**
     * 功能描述:
     *    获得7月份预算
     * @return 7月份预算 : String
     */
    public String getBudmonth7()
    {
		return this.budmonth7;
    }

    /**
     * 功能描述:
     *    设置7月份预算
     * @param budmonth7 : String
     */
    public void setBudmonth7(String budmonth7)
    {
	    this.budmonth7 = budmonth7;
    }
    

    /**
     * 功能描述:
     *    获得8月份预算
     * @return 8月份预算 : String
     */
    public String getBudmonth8()
    {
		return this.budmonth8;
    }

    /**
     * 功能描述:
     *    设置8月份预算
     * @param budmonth8 : String
     */
    public void setBudmonth8(String budmonth8)
    {
	    this.budmonth8 = budmonth8;
    }
    

    /**
     * 功能描述:
     *    获得9月份预算
     * @return 9月份预算 : String
     */
    public String getBudmonth9()
    {
		return this.budmonth9;
    }

    /**
     * 功能描述:
     *    设置9月份预算
     * @param budmonth9 : String
     */
    public void setBudmonth9(String budmonth9)
    {
	    this.budmonth9 = budmonth9;
    }
    

    /**
     * 功能描述:
     *    获得10月份预算
     * @return 10月份预算 : String
     */
    public String getBudmonth10()
    {
		return this.budmonth10;
    }

    /**
     * 功能描述:
     *    设置10月份预算
     * @param budmonth10 : String
     */
    public void setBudmonth10(String budmonth10)
    {
	    this.budmonth10 = budmonth10;
    }
    

    /**
     * 功能描述:
     *    获得11月份预算
     * @return 11月份预算 : String
     */
    public String getBudmonth11()
    {
		return this.budmonth11;
    }

    /**
     * 功能描述:
     *    设置11月份预算
     * @param budmonth11 : String
     */
    public void setBudmonth11(String budmonth11)
    {
	    this.budmonth11 = budmonth11;
    }
    

    /**
     * 功能描述:
     *    获得12月份预算
     * @return 12月份预算 : String
     */
    public String getBudmonth12()
    {
		return this.budmonth12;
    }

    /**
     * 功能描述:
     *    设置12月份预算
     * @param budmonth12 : String
     */
    public void setBudmonth12(String budmonth12)
    {
	    this.budmonth12 = budmonth12;
    }
    

    /**
     * 功能描述:
     *    获得预算编制依据
     * @return 预算编制依据 : String
     */
    public String getBudorgacc()
    {
		return this.budorgacc;
    }

    /**
     * 功能描述:
     *    设置预算编制依据
     * @param budorgacc : String
     */
    public void setBudorgacc(String budorgacc)
    {
	    this.budorgacc = budorgacc;
    }
    

    /**
     * 功能描述:
     *    获得预占额度
     * @return 预占额度 : String
     */
    public String getPrelimit()
    {
		return this.prelimit;
    }

    /**
     * 功能描述:
     *    设置预占额度
     * @param prelimit : String
     */
    public void setPrelimit(String prelimit)
    {
	    this.prelimit = prelimit;
    }
    

    /**
     * 功能描述:
     *    获得实际占用额度
     * @return 实际占用额度 : String
     */
    public String getActamount()
    {
		return this.actamount;
    }

    /**
     * 功能描述:
     *    设置实际占用额度
     * @param actamount : String
     */
    public void setActamount(String actamount)
    {
	    this.actamount = actamount;
    }
    

    /**
     * 功能描述:
     *    获得管理费用预算
     * @return 管理费用预算 : DeptCharge
     */
    public DeptCharge getDeptCharge()
    {
		return this.deptCharge;
    }

    /**
     * 功能描述:
     *    设置管理费用预算
     * @param deptCharge : DeptCharge
     */
    public void setDeptCharge(DeptCharge deptCharge)
    {
	    this.deptCharge = deptCharge;
    }
    
    
	/**
	 *  默认构造器
	 */
    public DeptCharDetail()
    {
    	super();
    }
}
