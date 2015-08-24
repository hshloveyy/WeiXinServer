package com.infolion.platform.component.processor.impl;

import java.util.ArrayList;
import java.util.List;

import com.infolion.platform.console.menu.domain.SysResource;
import com.infolion.platform.console.sys.context.UserContextHolder;

/**
 * 处理器button实现类
 * @author cxp
 *
 */
public class Button{
	private static final long serialVersionUID = 3869599888089108635L;
	public Object processor(String model,ArrayList<SysResource> list,String pageResource){
		try {
			for (int i = 0; i < list.size(); i++) {
				SysResource item = (SysResource) list.get(i);
				if (model != null && model.equals(item.getParentid())&& pageResource.equals(item.getResourcename())) {
					// compositeAuthority(parentId,resourceName);
					return Boolean.FALSE;
				}
			}// 没有权限控制:显亮;有权限控制+资源在上下文中:显亮;
			return Boolean.TRUE;
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return Boolean.TRUE;
	}
	public static Boolean processora(String model,String pageResource){
		List list = UserContextHolder.getLocalUserContext().getUserContext().getGrantedResources();
		try {
			for (int i = 0; i < list.size(); i++) {
				SysResource item = (SysResource) list.get(i);
				if (model != null && model.equals(item.getParentid())&& pageResource.equals(item.getResourcename())) {
					// compositeAuthority(parentId,resourceName);
					return Boolean.FALSE;
				}
			}// 没有权限控制:显亮;有权限控制+资源在上下文中:显亮;
			return Boolean.TRUE;
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return Boolean.TRUE;
	}

}
