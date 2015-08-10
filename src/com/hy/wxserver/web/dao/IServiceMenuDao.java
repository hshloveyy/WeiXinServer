package com.hy.wxserver.web.dao;

import java.util.List;

import com.hy.wxserver.web.pojo.ServiceMenu;

/**
 * @author heshaohua E-mail:386184368@qq.com
 * @version 创建时间：2014-4-8 下午05:02:20
 * 类说明
 */
public interface IServiceMenuDao {

	public abstract void save(ServiceMenu transientInstance);

	public abstract void delete(ServiceMenu persistentInstance);

	public abstract ServiceMenu findById(Integer id);

	public abstract List<ServiceMenu> findAll();

}