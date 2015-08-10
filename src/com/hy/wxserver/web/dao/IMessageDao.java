package com.hy.wxserver.web.dao;

import java.util.List;

import com.hy.wxserver.web.pojo.Message;

/**
 * @author heshaohua E-mail:386184368@qq.com
 * @version ����ʱ�䣺2014-4-8 ����05:01:34
 * ��˵��
 */
public interface IMessageDao {

	public abstract void save(Message transientInstance);

	public abstract void delete(Message persistentInstance);

	public abstract Message findById(String msgId);

	public abstract List<Message> findAll();

}