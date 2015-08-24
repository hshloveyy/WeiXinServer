package com.infolion.platform.component.processor;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.infolion.platform.console.menu.domain.SysResource;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.sys.Constants;
/**
 * ExtJS组件ToolBar权限处理类
 * @author cxp
 *
 */
public class ToolBar {
	/**
	 * 判断是否显示toolBar上的按键
	 * @param request
	 * @param modelName
	 * @param pageResourcesName
	 * @param isNeedAuth
	 * @return	布尔值数组(true:变灰;false:显亮)
	 * @throws Exception
	 */
	public boolean[] justify(HttpServletRequest request,String modelName,String pageResourcesName,String isNeedAuth)throws Exception{
		try {
			UserContext ctx = (UserContext)request.getSession().getAttribute(Constants.USER_CONTEXT_NAME);
			List sysResources = ctx.getGrantedResources();
			String[] auth = isNeedAuth.split(";");
			boolean[] show = new boolean[auth.length];
			boolean[] authbl = new boolean[auth.length];
			for(int i=0;i<auth.length;i++){
				authbl[i]="true".equalsIgnoreCase(auth[i])?true:false;
			}
			String[] pages = pageResourcesName.split(";");
			for(int j=0;j<pages.length;j++){
				show[j]=false;
				if(!authbl[j])break;//如果是false,表示没有权限控制
				for(int i=0;i<sysResources.size();i++){
					SysResource item = (SysResource)sysResources.get(i);
					if(modelName!=null && modelName.equals(item.getParentid()) && pages[j].equals(item.getResourcename())){
						//compositeAuthority(parentId,resourceName);
						show[j] = true;
					}
				}
			}//没有权限控制:显亮;有权限控制+资源在上下文中:显亮;
			for(int i=0;i<authbl.length;i++){
				authbl[i]=!authbl[i]||show[i]&&authbl[i]?false:true;
			}

			return show;
		} catch (RuntimeException e) {
			e.printStackTrace(new PrintWriter("输入不匹配或者配对有问题"));
		}
		return new boolean[1];
	}
}
