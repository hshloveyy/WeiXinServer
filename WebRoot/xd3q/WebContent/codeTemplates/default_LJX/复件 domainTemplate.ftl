/*
 * @(#)${entityAttribute.beanName}.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：${entityAttribute.createTime?string("yyyy年MM月dd日 HH点mm分ss秒")}
 *  描　述：创建
 */
package com.infolion.${entityAttribute.packageName}.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;
<#list entityTemp as imp>
<#if imp.dataTypeImport!="">
import ${imp.dataTypeImport};
</#if>
</#list>

/**
 * <pre>
 * ${entityAttribute.boDescription}(${entityAttribute.boName})实体类
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
@Table(name = "${entityAttribute.tableName}")
public class ${entityAttribute.beanName} extends BaseObject
{
	//fields
	<#list entityTemp as modelPro>
	
	/*
	 * ${modelPro.fieldText}
	 */
	<#-- 关键字段的处理 -->
    <#if modelPro.primaryKey>
    @Id
    </#if>
    @Column(name = "${modelPro.columnName}")
    ${modelPro.validateRule} 
    private ${modelPro.javaDataType} ${modelPro.propertyName};   
	</#list>
	
	<#list entityTemp as model>
	
    /**
     * 功能描述:
     *    获得${model.fieldText}
     * @return ${model.fieldText} ${model.javaDataType}
     */
    public ${model.javaDataType} get${model.columnJavaName}()
    {
        return this.${model.propertyName};
    }

    /**
     * 功能描述:
     *    设置${model.fieldText}
     * @param ${model.propertyName} ${model.javaDataType}
     */
    public void set${model.columnJavaName}(${model.javaDataType} ${model.propertyName})
    {
        this.${model.propertyName} = ${model.propertyName};
    }
    </#list>
    
	/**
	 *  默认构造器
	 */
    public ${entityAttribute.beanName}()
    {
    	super();
    }
}
