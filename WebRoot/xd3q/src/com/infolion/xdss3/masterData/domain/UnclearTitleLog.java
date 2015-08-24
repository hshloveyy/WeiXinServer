package com.infolion.xdss3.masterData.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.infolion.platform.dpframework.core.domain.BaseObject;

/**
 * @创建作者：邱杰烜
 * @创建时间：2010-12-06
 * 未清（客户/供应商）抬头更新记录表
 */
@Entity
@Table(name = "T_UNCLEAR_TITLE_LOG")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class UnclearTitleLog extends BaseObject{
    
    /*
     * 未清抬头更新记录ID
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="UNCLEARTITLELOGID")
    private String unclearTitleLogId;
    
    /*
     * 未清抬头ID
     */
    @Column(name="UNCLEARTITLEID")
    private String unclearTitleId;
    
    /*
     * 更新前未清抬头未清标识
     */
    @Column(name="OLD_ISCLEARED")
    private String oldIscleared;
    
    /*
     * 更新后未清抬头未清标识
     */
    @Column(name="NEW_ISCLEARED")
    private String newIscleared;
    
    /*
     * 客户编号
     */
    @Column(name="CUSTOMER")
    private String customer;
    
    /*
     * 供应商编号
     */
    @Column(name="SUPPLIER")
    private String supplier;
    
    /*
     * 记录时间
     */
    @Column(name="LOGTIME")
    private String logTime;
    
    /*
     * 备注
     */
    @Column(name="REMARK")
    private String remark;

    public String getUnclearTitleLogId(){
        return unclearTitleLogId;
    }

    public void setUnclearTitleLogId(String unclearTitleLogId){
        this.unclearTitleLogId = unclearTitleLogId;
    }

    public String getUnclearTitleId(){
        return unclearTitleId;
    }

    public void setUnclearTitleId(String unclearTitleId){
        this.unclearTitleId = unclearTitleId;
    }

    public String getOldIscleared(){
        return oldIscleared;
    }

    public void setOldIscleared(String oldIscleared){
        this.oldIscleared = oldIscleared;
    }

    public String getNewIscleared(){
        return newIscleared;
    }

    public void setNewIscleared(String newIscleared){
        this.newIscleared = newIscleared;
    }

    public String getCustomer(){
        return customer;
    }

    public void setCustomer(String customer){
        this.customer = customer;
    }

    public String getSupplier(){
        return supplier;
    }

    public void setSupplier(String supplier){
        this.supplier = supplier;
    }

    public String getLogTime(){
        return logTime;
    }

    public void setLogTime(String logTime){
        this.logTime = logTime;
    }

    public String getRemark(){
        return remark;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }
    
}
