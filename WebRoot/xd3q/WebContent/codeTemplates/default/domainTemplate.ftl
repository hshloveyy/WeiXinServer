/*
 * @(#)${entityAttribute.beanName}.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：${entityAttribute.createTime?string("yyyy年MM月dd日 HH点mm分ss秒")}
 *  描　述：创建
 */
package ${entityAttribute.packageName}.domain;

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
<#compress>
<#list entityTemp as imp>
<#if imp.dataTypeImport!="">
import ${imp.dataTypeImport};
</#if>
</#list>
<#-- 导入引用到的业务对象子对象domain包路径 -->
<#if subBoList?exists && (subBoList?size>0)>
<#list subBoList as subBo>          <#-- 遍历业务对象下所有子对象 && subBo.beanAttribute.packageName!="${entityAttribute.packageName} //${subBo.beanAttribute.domainPackage} ${subBo.parentBusinessObjectName}  ${subBo.beanAttribute.packageName}   ${entityAttribute.packageName}"-->
<#if subBo.beanAttribute.domainPackage!="" && subBo.parentBusinessObjectName!="" && subBo.boName!="Attachement" >
import ${subBo.beanAttribute.domainPackage};
</#if>
</#list>
</#if>
</#compress>


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
@Table(name = "${entityAttribute.tableName?upper_case}")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
public class ${entityAttribute.beanName} extends BaseObject
{
	//fields
	<#list entityTemp as modelPro>
	/*
	 * ${modelPro.fieldText?default("")}
	 */
    ${modelPro.jpaAnnotation}
    <#if modelPro.validateRule?exists>${modelPro.validateRule}</#if>  
    private ${modelPro.javaDataType} ${modelPro.propertyName};   
    
	</#list>
	<#list entityTemp as model>

    /**
     * 功能描述:
     *    获得${model.fieldText?default("")}
     * @return ${model.fieldText?default("")} : ${model.javaDataType}
     */
    public ${model.javaDataType} get${model.columnJavaName}()
    {
<#if model.isCalendar?exists && model.isCalendar=="Y">
    	return DateUtils.toDisplayStr(this.${model.propertyName}, DateUtils.HYPHEN_DISPLAY_DATE);
<#else>
		return this.${model.propertyName};
</#if>
    }

    /**
     * 功能描述:
     *    设置${model.fieldText?default("")}
     * @param ${model.propertyName} : ${model.javaDataType}
     */
    public void set${model.columnJavaName}(${model.javaDataType} ${model.propertyName})
    {
<#if model.isCalendar?exists && model.isCalendar=="Y">
    	${model.propertyName} = DateUtils.toStoreStr(${model.propertyName});
</#if>  
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
