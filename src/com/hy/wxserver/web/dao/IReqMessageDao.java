package com.hy.wxserver.web.dao;

import java.util.List;

import com.hy.wxserver.web.pojo.Message;
import com.hy.wxserver.web.pojo.ReqMessage;

/**
 * @author heshaohua E-mail:386184368@qq.com
 * @version 创建时间：2014-4-8 下午05:01:52
 * 类说明
 */
public interface IReqMessageDao {

	public abstract void save(ReqMessage transientInstance);

	public abstract void delete(ReqMessage persistentInstance);

	public abstract ReqMessage findById(String msgId);

	public abstract List<Message> findAll();

}