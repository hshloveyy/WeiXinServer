/*
 * @(#)AdvanceWarnObjectService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月13日 16点08分37秒
 *  描　述：创建
 */
package com.infolion.sapss.waitForTransactWork.service;


import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.infolion.platform.console.org.domain.SysRole;
import com.infolion.platform.console.org.domain.SysUser;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.MASUtils;
import com.infolion.sapss.waitForTransactWork.dao.WaitForTransactWorkJdbcDao;


/**
 * <pre>
 * 待办事项(WaitForTransactWork)服务类
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
@Service
public class WaitForTransactWorkService
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	@Autowired
	private WaitForTransactWorkJdbcDao waitForTransactWorkJdbcDao;
	
	public void setWaitForTransactWorkJdbcDao(WaitForTransactWorkJdbcDao waitForTransactWorkJdbcDao)
	{
		this.waitForTransactWorkJdbcDao = waitForTransactWorkJdbcDao;
	}

	/**
	 * 定时查找待办事项
	 */
	private void querywaitForTransactWork()
	{
		//遍历手机号非空用户
		List<SysUser> sysUserList = this.waitForTransactWorkJdbcDao.getSysUserListByMobile();
		MASUtils.init();
		int result=0;
		for (int i=0; i<sysUserList.size(); i++){
			SysUser sysUser=sysUserList.get(i);
			String userName=sysUser.getUserName();
			String realName=sysUser.getRealName();
			String deptId = sysUser.getDeptId();
			String isFuncDept=sysUser.getIsFuncDept();
			String userId = sysUser.getUserId();
			String positionDes = sysUser.getPositionDes();
			String mobile=sysUser.getMobile();
			String dateStr = DateUtils.getCurrMin(DateUtils.CN_DISPLAY_DATE);
			//判断用户是否属于多个部门
		    int a=waitForTransactWorkJdbcDao.checkUserDept(userId);
		    if (a>1){
		    	//多个部门处理
		    	result=this.dealWithMutiDept(userName,userId);		    	
		    }
		    else{
			    //判断是否有待办事项
		        String deptUserId=userId;
			    result=waitForTransactWorkJdbcDao.checkUserWaitWork(userName,deptId,deptUserId,userId,isFuncDept);
		    }
			//如果有待办事项
			if (result>0)
			{
				//向该用户发送短信				
				String content="尊敬的"+realName+positionDes+"您好，截止到"+dateStr+"您在外围辅助系统有"+result+"条待办事项需要处理！";				
				MASUtils.sendSM(mobile, content);				
			}			
		}
		MASUtils.release();
		
	}
	/**
	 * 多部门处理
	 */
	public int dealWithMutiDept(String userName,String userId){
		List<SysUser> sysUserList=this.waitForTransactWorkJdbcDao.getDeptUserId(userId);
		int a=0;int c=0;
		for(int i=0 ;i<sysUserList.size(); i++){
		   SysUser sysUser=sysUserList.get(i);
		   String deptId = sysUser.getDeptId();
		   String deptUserId=sysUser.getDeptUserId();
		   String isFuncDept=sysUser.getIsFuncDept();
		 //判断是否有待办事项
		   int b=waitForTransactWorkJdbcDao.checkUserWaitWork(userName,deptId,deptUserId,userId,isFuncDept);
		   c=a+b;
		   a=c;
		}
		return c;
	}
	
	public String isOYZAutoSubmit(String userName,String businessId,String type){
		return waitForTransactWorkJdbcDao.isOYZAutoSubmit(userName,businessId,type);
	}
	

}