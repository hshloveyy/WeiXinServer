/*
 * @(#)${beanAttribute.beanName}.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：${beanAttribute.createTime?string("yyyy年MM月dd日 HH点mm分ss秒")}
 *  描　述：创建
 */
package ${beanAttribute.packageName}.dao;

import org.springframework.stereotype.Repository;
import ${beanAttribute.packageName}.domain.${beanAttribute.boName};
import ${beanAttribute.packageNameGen}.dao.${beanAttribute.beanNameGen};


/**
 * <pre>
 * ${beanAttribute.boDescription}(${beanAttribute.boName}),HibernateDao 操作类
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
@Repository
public class ${beanAttribute.beanName} extends ${beanAttribute.beanNameGen}<${beanAttribute.boName}>
{
}