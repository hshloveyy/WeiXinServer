package com.hy.wxserver.web.dao;

import java.util.List;

import com.hy.wxserver.web.pojo.Message;
import com.hy.wxserver.web.pojo.ReqMessage;

/**
 * @author heshaohua E-mail:386184368@qq.com
 * @version ����ʱ�䣺2014-4-8 ����05:01:52
 * ��˵��
 */
public interface IReqMessageDao {

	public abstract void save(ReqMessage transientInstance);

	public abstract void delete(ReqMessage persistentInstance);

	public abstract ReqMessage findById(String msgId);

	public abstract List<Message> findAll();

}