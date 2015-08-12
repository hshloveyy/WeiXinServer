package com.hy.wxserver.web.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.hy.wxserver.message.response.ImageMessage;
import com.hy.wxserver.message.response.RespBaseMessage;
import com.hy.wxserver.message.response.TextMessage;
import com.hy.wxserver.message.response.model.Image;
import com.hy.wxserver.utils.MessageUtils;
import com.hy.wxserver.web.dao.IMessageDao;
import com.hy.wxserver.web.dao.IReqMessageDao;
import com.hy.wxserver.web.dao.IServiceMenuDao;
import com.hy.wxserver.web.pojo.ReqMessage;
import com.hy.wxserver.web.pojo.ServiceMenu;

/**
 * @author heshaohua E-mail:386184368@qq.com
 * @version 创建时间：2014-4-8 下午06:13:00
 * 类说明
 */
@Service("customerService")
public class CustomerService implements ICustomerService {
	
	private IMessageDao messageDao;
	
	private IReqMessageDao reqMessageDao;
	
	private IServiceMenuDao serviceMenuDao;


	public RespBaseMessage processRequest(HttpServletRequest request) {
		RespBaseMessage message = null;
    	// 默认返回的文本消息内容  
    	try {
    		// xml请求解析  
            Map<String, String> requestMap = MessageUtils.parseXml(request); 
            
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType");  
            //消息ID

            String msgId = requestMap.get("MsgId");
            //创建时间
            String createTime = requestMap.get("CreateTime");
            
          //保存用户请求消息
			ReqMessage reqMessage = new ReqMessage(msgId, toUserName, fromUserName, new Date(Long.valueOf(createTime)*1000), msgType);
            
			// 回复文本消息  
		      TextMessage textMessage = new TextMessage();  
		      textMessage.setToUserName(fromUserName);  
		      textMessage.setFromUserName(toUserName);  
		      textMessage.setCreateTime(new Date().getTime());  
		      textMessage.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_TEXT);  
		      
			if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_TEXT)) { // 文本消息  
				String content = requestMap.get("Content");
				
				reqMessage.setContent(content);
				
				//响应用户
				List<ServiceMenu> serviceList = serviceMenuDao.findAll();
		    	for (ServiceMenu serviceMenu : serviceList) {
					if(serviceMenu.getCode().equalsIgnoreCase(content)){
						textMessage.setContent(serviceMenu.getCodeDesc());
						
						message = textMessage;
					}
				}
				if(message == null){
					textMessage.setContent("你输入的是文本消息！");
					message = textMessage;
				}
				
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_IMAGE)) { // 图片消息
				String picUrl = requestMap.get("PicUrl");
				String mediaId = requestMap.get("MediaId");
				
				reqMessage.setPicUrl(picUrl);
				reqMessage.setMediaId(mediaId);
				
				ImageMessage imageMessage = new ImageMessage();
				imageMessage.setToUserName(fromUserName);  
				imageMessage.setFromUserName(toUserName);  
				imageMessage.setCreateTime(new Date().getTime());  
				imageMessage.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_IMAGE); 
				imageMessage.setImage(new Image(mediaId));
				
				message = imageMessage;
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_LOCATION)) { // 地理位置消息
				textMessage.setContent("您发送的是地理位置消息！");
				message = textMessage;
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_LINK)) { // 链接消息
				textMessage.setContent("您发送的是链接消息！");
				message = textMessage;
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_VOICE)) { // 音频消息
				textMessage.setContent("您发送的是音频消息！");
				message = textMessage;
			}else if(msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_VIDEO)){	//视频消息
				textMessage.setContent("您发送的是视频消息！");
				message = textMessage;
			}else if(msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_SHORTVIDEO)){	//视频消息
				textMessage.setContent("您发送的是小视频消息！");
				message = textMessage;
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_EVENT)) { // 事件推送
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtils.EVENT_TYPE_SUBSCRIBE)) {
					textMessage.setContent("谢谢您的关注！");
					message = textMessage;
				}else if (eventType.equals(MessageUtils.EVENT_TYPE_UNSUBSCRIBE)) { // 取消订阅
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
					textMessage.setContent("取消订阅");
					message = textMessage;
				}else if (eventType.equals(MessageUtils.EVENT_TYPE_CLICK)) { // 自定义菜单点击事件
					// TODO 自定义菜单权没有开放，暂不处理该类消息
					textMessage.setContent("自定义菜单权");
					message = textMessage;
				}
			}
			//保存用户请求信息
			ReqMessage testReqMessage = reqMessageDao.findById(msgId);
			if(testReqMessage == null){
				reqMessageDao.save(reqMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return message;
	}
	
	public IMessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(IMessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public IReqMessageDao getReqMessageDao() {
		return reqMessageDao;
	}

	public void setReqMessageDao(IReqMessageDao reqMessageDao) {
		this.reqMessageDao = reqMessageDao;
	}

	public IServiceMenuDao getServiceMenuDao() {
		return serviceMenuDao;
	}

	public void setServiceMenuDao(IServiceMenuDao serviceMenuDao) {
		this.serviceMenuDao = serviceMenuDao;
	}

}
