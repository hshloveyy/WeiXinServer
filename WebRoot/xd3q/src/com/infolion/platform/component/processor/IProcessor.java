package com.infolion.platform.component.processor;

/**
 * 处理接口类
 * @author cxp
 *
 */
public interface IProcessor {
	/**
	 * 处理器1
	 * @param request 来自页面的request
	 * @param model 模块名
	 * @param pageResource 页面资源
	 * @param other 其它页面参数
	 * @return
	 */
	public Object processor(String model,String pageResource,String other);
	/**
	 * 处理器2
	 * @param request 来自页面的request
	 * @param model 模块名
	 * @param pageResource 页面资源
	 * @return
	 */
	public Object processor(String model,String pageResource);
}
