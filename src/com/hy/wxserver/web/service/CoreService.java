package com.hy.wxserver.web.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.hy.wxserver.message.response.RespBaseMessage;
import com.hy.wxserver.message.response.TextMessage;
import com.hy.wxserver.utils.MessageUtils;
import com.hy.wxserver.web.dao.IMessageDao;
import com.hy.wxserver.web.dao.IReqMessageDao;
import com.hy.wxserver.web.dao.IServiceMenuDao;
import com.hy.wxserver.web.dao.ReqMessageDAO;
import com.hy.wxserver.web.pojo.ReqMessage;
import com.hy.wxserver.web.pojo.ServiceMenu;

/** 
 * 核心服务类 
 *  
 * @author heshaohua E-mail:386184368@qq.com
 * @version 创建时间：2014-3-28 下午05:37:21
 */  
@Service("coreService")
public class CoreService{  
	
	private IMessageDao messageDao;
	
	private IReqMessageDao reqMessageDao;
	
	private IServiceMenuDao serviceMenuDao;
	
	/* (non-Javadoc)
	 * @see com.hy.wxserver.web.service.ICoreService#processRequest(javax.servlet.http.HttpServletRequest)
	 */  
    public static RespBaseMessage processRequest(HttpServletRequest request) { 
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
				
				//保存用户请求信息
				//reqMessageDao.save(reqMessage);
				
				//响应用户
				message = MessageUtils.responseMessage(content);
				
				if(message == null){
					textMessage.setContent("你输入的是文本消息！");
					message = textMessage;
				}
				
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_IMAGE)) { // 图片消息
				textMessage.setContent("您发送的是图片消息！");
				message = textMessage;
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
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return message;
    }
	
    
    private  RespBaseMessage responseMessage(String content){
    	RespBaseMessage message = null;
    	// 回复文本消息  
      TextMessage textMessage = new TextMessage();  
      textMessage.setCreateTime(new Date().getTime());  
      textMessage.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_TEXT);  
//    	if(content.equals("1")){	//		1：十张内涵图
//			
//		}else if (content.equals("2")) {//		2：歌曲"
//			
//		}else if (content.equals("3")) {//		3：电影"
//			
//		}else if (content.equals("4")) {//		4：游戏"
//			
//		}else if (content.equals("5")) {//		5：优惠"
//			
//		}else{
//			
//		}
    	List<ServiceMenu> serviceList = serviceMenuDao.findAll();
    	for (ServiceMenu serviceMenu : serviceList) {
			System.out.println(serviceMenu.getCode() + " ------ " + serviceMenu.getCodeDesc());
		}
    	
    	return message;
    }
	
	/** 
     * 处理微信发来的请求 
     *  
     * @param request 
     * @return 
     */  
    public static String processRequest2(HttpServletRequest request) {  
        String respMessage = null;  
        try {  
            // 默认返回的文本消息内容  
            String respContent = "请求处理异常，请稍候尝试！";  
            IReqMessageDao reqMessageDAO = new ReqMessageDAO();
  
            // xml请求解析  
            Map<String, String> requestMap = MessageUtils.parseXml(request);  
  
            System.out.println(requestMap.get("Content"));
            
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType");  
  
            // 回复文本消息  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_TEXT);  
//            textMessage.setFuncFlag(0);  
            
			if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_TEXT)) { // 文本消息  
				respContent = "您发送的是文本消息！";
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_IMAGE)) { // 图片消息
				respContent = "您发送的是图片消息！";
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_LOCATION)) { // 地理位置消息
				respContent = "您发送的是地理位置消息！";
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_LINK)) { // 链接消息
				respContent = "您发送的是链接消息！";
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_VOICE)) { // 音频消息
				respContent = "您发送的是音频消息！";
			}else if(msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_VIDEO)){	//视频消息
				respContent = "您发送的是视频消息！";
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_EVENT)) { // 事件推送
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtils.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "谢谢您的关注！";
				}else if (eventType.equals(MessageUtils.EVENT_TYPE_UNSUBSCRIBE)) { // 取消订阅
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
					respContent = "取消订阅";
				}else if (eventType.equals(MessageUtils.EVENT_TYPE_CLICK)) { // 自定义菜单点击事件
					// TODO 自定义菜单权没有开放，暂不处理该类消息
					respContent = "自定义菜单权";
				}
			}
  
            textMessage.setContent(respContent);  
            respMessage = MessageUtils.textMessageToXml(textMessage);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        return respMessage;  
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
