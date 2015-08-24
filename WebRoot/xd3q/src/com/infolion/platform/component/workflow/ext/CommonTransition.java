/*
 * @(#)CommonTransition.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-2-12
 *  描　述：创建
 */

package com.infolion.platform.component.workflow.ext;

import java.util.ArrayList;
import java.util.List;

import com.infolion.platform.console.org.domain.SysUser;

/**
 * 
 * <pre>
 * 带有节点任务参与者的迁移
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class CommonTransition {
	private String transitionId;
	private String transitionName;
	private String fromTaskId;
	private String fromTaskName;
	private String toTaskId;
	private String toTaskName;
	private List<SysUser> actors = new ArrayList();

	public String getTransitionId() {
		return transitionId;
	}

	public void setTransitionId(String transitionId) {
		this.transitionId = transitionId;
	}

	public String getTransitionName() {
		return transitionName;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

	public String getFromTaskId() {
		return fromTaskId;
	}

	public void setFromTaskId(String fromTaskId) {
		this.fromTaskId = fromTaskId;
	}

	public String getFromTaskName() {
		return fromTaskName;
	}

	public void setFromTaskName(String fromTaskName) {
		this.fromTaskName = fromTaskName;
	}

	public String getToTaskId() {
		return toTaskId;
	}

	public void setToTaskId(String toTaskId) {
		this.toTaskId = toTaskId;
	}

	public String getToTaskName() {
		return toTaskName;
	}

	public void setToTaskName(String toTaskName) {
		this.toTaskName = toTaskName;
	}

	public List<SysUser> getActors() {
		return actors;
	}

	public void setActors(List<SysUser> actors) {
		this.actors = actors;
	}

}
