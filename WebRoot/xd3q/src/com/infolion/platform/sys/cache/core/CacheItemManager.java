/*
 * @(#)CacheItemManager.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-11-30
 *  描　述：创建
 */
package com.infolion.platform.sys.cache.core;

import java.util.Set;
/**
 * 
 * <pre>缓存项目管理器</pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public interface CacheItemManager
{    
    /**
     * 初始化本类型的缓存
     */
    void init();
    
    /**
     * 初始化特定缓存条目的缓存
     *
     * @param items
     */
    void init(Set<String> items);
    
    /**
     * 刷新本类型的所有缓存
     */
    void refreshAll();
    
    /**
     * 刷新本类型特定条目的缓存
     * @param items
     */
    void refresh(Set<String> items);
}
