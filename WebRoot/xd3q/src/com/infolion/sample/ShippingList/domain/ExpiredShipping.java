/*
 * @(#)ExpiredShipping.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年12月04日 11点00分12秒
 *  描　述：创建
 */
package com.infolion.sample.ShippingList.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.IdClass;
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
 * 过期出货清单(ExpiredShipping)实体类
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
@Table(name = "YVSOITEM")
@IdClass(ExpiredShippingKey.class)
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class ExpiredShipping extends BaseObject
{
	//fields
	/*
	 * 销售凭证
	 */
    @Id
    @Column(name="VBELN")
    private String vbeln;   
    
	/*
	 * 客户端
	 */
    @Id
    @Column(name="MANDT")
    private String client; 
    
	/*
	 * 销售凭证行项目
	 */
    @Id
    @Column(name="POSNR")
    private String posnr;  
    
	/*
	 * 物料号
	 */
    @Column(name="MATNR")
    @ValidateRule(dataType=9,label="物料号",maxLength= 18,required=false) 
    private String matnr;   
    
	/*
	 * 销售订单项目短文本
	 */
    @Column(name="ARKTX")
    @ValidateRule(dataType=9,label="销售订单项目短文本",maxLength= 40,required=false) 
    private String arktx;   
    
	/*
	 * 订单数量
	 */
    @Column(name="KWMENG")
    @ValidateRule(dataType=9,label="订单数量",maxLength= 19,required=false) 
    private String kwmeng;   
    
	/*
	 * 单位价格
	 */
    @Column(name="VERPR")
    @ValidateRule(dataType=9,label="单位价格",maxLength= 15,required=false) 
    private String verpr;   
    
	/*
	 * 单价
	 */
    @Column(name="NETWR")
    @ValidateRule(dataType=9,label="单价",maxLength= 21,required=false) 
    private String netwr;   
    
	/*
	 * 货币
	 */
    @Column(name="WAERK")
    @ValidateRule(dataType=9,label="货币",maxLength= 5,required=false) 
    private String waerk;   
    
	/*
	 * 工厂
	 */
    @Column(name="WERKS")
    @ValidateRule(dataType=9,label="工厂",maxLength= 4,required=false) 
    private String werks;   
    
	/*
	 * 客户要求日期
	 */
    @Column(name="VDATU")
    @ValidateRule(dataType=9,label="客户要求日期",maxLength= 10,required=false) 
    private String vdatu;   
    
	/*
	 * 客户
	 */
    @Column(name="KUNNR")
    @ValidateRule(dataType=9,label="客户",maxLength= 10,required=false) 
    private String kunnr;   
    
	/*
	 * 现有存货数量
	 */
    @Column(name="CLABS")
    @ValidateRule(dataType=9,label="现有存货数量",maxLength= 18,required=false) 
    private String clabs;   
    
	/*
	 * 销售库存
	 */
    @Column(name="SSLAB")
    @ValidateRule(dataType=9,label="销售库存",maxLength= 18,required=false) 
    private String sslab;   
    
	/*
	 * 已出货数量
	 */
    @Column(name="LFIMG")
    @ValidateRule(dataType=9,label="已出货数量",maxLength= 17,required=false) 
    private String lfimg;   
    
	/*
	 * 未清数量
	 */
    @Transient
    @ValidateRule(dataType=9,label="未清数量",maxLength= 17,required=false) 
    private String wqsl;   
    
	/*
	 * 未清总价
	 */
    @Transient
    @ValidateRule(dataType=9,label="未清总价",maxLength= 17,required=false) 
    private String wqzj;   

    /**
     * 功能描述:
     *    获得销售凭证
     * @return 销售凭证 : String
     */
    public String getVbeln()
    {
		return this.vbeln;
    }

    /**
     * 功能描述:
     *    设置销售凭证
     * @param vbeln : String
     */
    public void setVbeln(String vbeln)
    {
	    this.vbeln = vbeln;
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
     *    获得物料号
     * @return 物料号 : String
     */
    public String getMatnr()
    {
		return this.matnr;
    }

    /**
     * 功能描述:
     *    设置物料号
     * @param matnr : String
     */
    public void setMatnr(String matnr)
    {
	    this.matnr = matnr;
    }
    

    /**
     * 功能描述:
     *    获得销售订单项目短文本
     * @return 销售订单项目短文本 : String
     */
    public String getArktx()
    {
		return this.arktx;
    }

    /**
     * 功能描述:
     *    设置销售订单项目短文本
     * @param arktx : String
     */
    public void setArktx(String arktx)
    {
	    this.arktx = arktx;
    }
    

    /**
     * 功能描述:
     *    获得订单数量
     * @return 订单数量 : String
     */
    public String getKwmeng()
    {
		return this.kwmeng;
    }

    /**
     * 功能描述:
     *    设置订单数量
     * @param kwmeng : String
     */
    public void setKwmeng(String kwmeng)
    {
	    this.kwmeng = kwmeng;
    }
    

    /**
     * 功能描述:
     *    获得单位价格
     * @return 单位价格 : String
     */
    public String getVerpr()
    {
		return this.verpr;
    }

    /**
     * 功能描述:
     *    设置单位价格
     * @param verpr : String
     */
    public void setVerpr(String verpr)
    {
	    this.verpr = verpr;
    }
    

    /**
     * 功能描述:
     *    获得单价
     * @return 单价 : String
     */
    public String getNetwr()
    {
		return this.netwr;
    }

    /**
     * 功能描述:
     *    设置单价
     * @param netwr : String
     */
    public void setNetwr(String netwr)
    {
	    this.netwr = netwr;
    }
    

    /**
     * 功能描述:
     *    获得货币
     * @return 货币 : String
     */
    public String getWaerk()
    {
		return this.waerk;
    }

    /**
     * 功能描述:
     *    设置货币
     * @param waerk : String
     */
    public void setWaerk(String waerk)
    {
	    this.waerk = waerk;
    }
    

    /**
     * 功能描述:
     *    获得工厂
     * @return 工厂 : String
     */
    public String getWerks()
    {
		return this.werks;
    }

    /**
     * 功能描述:
     *    设置工厂
     * @param werks : String
     */
    public void setWerks(String werks)
    {
	    this.werks = werks;
    }
    

    /**
     * 功能描述:
     *    获得客户要求日期
     * @return 客户要求日期 : String
     */
    public String getVdatu()
    {
		return this.vdatu;
    }

    /**
     * 功能描述:
     *    设置客户要求日期
     * @param vdatu : String
     */
    public void setVdatu(String vdatu)
    {
	    this.vdatu = vdatu;
    }
    

    /**
     * 功能描述:
     *    获得客户
     * @return 客户 : String
     */
    public String getKunnr()
    {
		return this.kunnr;
    }

    /**
     * 功能描述:
     *    设置客户
     * @param kunnr : String
     */
    public void setKunnr(String kunnr)
    {
	    this.kunnr = kunnr;
    }
    

    /**
     * 功能描述:
     *    获得现有存货数量
     * @return 现有存货数量 : String
     */
    public String getClabs()
    {
		return this.clabs;
    }

    /**
     * 功能描述:
     *    设置现有存货数量
     * @param clabs : String
     */
    public void setClabs(String clabs)
    {
	    this.clabs = clabs;
    }
    

    /**
     * 功能描述:
     *    获得销售库存
     * @return 销售库存 : String
     */
    public String getSslab()
    {
		return this.sslab;
    }

    /**
     * 功能描述:
     *    设置销售库存
     * @param sslab : String
     */
    public void setSslab(String sslab)
    {
	    this.sslab = sslab;
    }
    

    /**
     * 功能描述:
     *    获得已出货数量
     * @return 已出货数量 : String
     */
    public String getLfimg()
    {
		return this.lfimg;
    }

    /**
     * 功能描述:
     *    设置已出货数量
     * @param lfimg : String
     */
    public void setLfimg(String lfimg)
    {
	    this.lfimg = lfimg;
    }
    

    /**
     * 功能描述:
     *    获得未清数量
     * @return 未清数量 : String
     */
    public String getWqsl()
    {
		return this.wqsl;
    }

    /**
     * 功能描述:
     *    设置未清数量
     * @param wqsl : String
     */
    public void setWqsl(String wqsl)
    {
	    this.wqsl = wqsl;
    }
    

    /**
     * 功能描述:
     *    获得未清总价
     * @return 未清总价 : String
     */
    public String getWqzj()
    {
		return this.wqzj;
    }

    /**
     * 功能描述:
     *    设置未清总价
     * @param wqzj : String
     */
    public void setWqzj(String wqzj)
    {
	    this.wqzj = wqzj;
    }
    

    /**
     * 功能描述:
     *    获得销售凭证行项目
     * @return 销售凭证行项目 : String
     */
    public String getPosnr()
    {
		return this.posnr;
    }

    /**
     * 功能描述:
     *    设置销售凭证行项目
     * @param posnr : String
     */
    public void setPosnr(String posnr)
    {
	    this.posnr = posnr;
    }
    
    
	/**
	 *  默认构造器
	 */
    public ExpiredShipping()
    {
    	super();
    }
}
