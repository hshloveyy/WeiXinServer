package com.hy.wxserver.web.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hy.turing.util.TuringUtils;
import com.hy.wxserver.message.response.ImageMessage;
import com.hy.wxserver.message.response.RespBaseMessage;
import com.hy.wxserver.message.response.TextMessage;
import com.hy.wxserver.message.response.model.Image;
import com.hy.wxserver.utils.CharacterUtils;
import com.hy.wxserver.utils.MessageUtils;
import com.hy.wxserver.web.dao.IMessageDao;
import com.hy.wxserver.web.dao.IPInfoDao;
import com.hy.wxserver.web.dao.IReqMessageDao;
import com.hy.wxserver.web.dao.IServiceMenuDao;
import com.hy.wxserver.web.pojo.IpInfo;
import com.hy.wxserver.web.pojo.ReqMessage;
import com.hy.wxserver.web.pojo.ServiceMenu;

/**
 * @author heshaohua E-mail:386184368@qq.com
 * @version 创建时间：2014-4-8 下午06:13:00
 * 类说明
 */
public class CustomerService implements ICustomerService {

	private IMessageDao messageDao;

	private IReqMessageDao reqMessageDao;

	private IServiceMenuDao serviceMenuDao;
	
	private IPInfoDao ipInfoDao;

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
//				List<ServiceMenu> serviceList = serviceMenuDao.findAll();
//		    	for (ServiceMenu serviceMenu : serviceList) {
//					if(serviceMenu.getCode().equalsIgnoreCase(content)){
//						textMessage.setContent(serviceMenu.getCodeDesc());
//						
//						message = textMessage;
//					}
//				}
				
				//优先判断是否为ip地址
				if(CharacterUtils.isIpAddress(content)){
					String resultString = "";
					List<IpInfo> list = ipInfoDao.findByIP(content);
					if(list != null && list.size() > 0){
						resultString = list.get(0).toString();
					}else {
						resultString = "你输入的IP地址为内网IP";
					}
					textMessage.setContent(resultString);
					message = textMessage;
				}
				
				//调用图灵机器人接口
				if(message == null){
					String resultString = TuringUtils.askToTuring(content, fromUserName);
					JSONObject jsonObject = JSON.parseObject(resultString);
					StringBuffer sb = new StringBuffer();
					if(jsonObject.containsKey("url")){
						sb.append(jsonObject.getString("text")).append("\n").append((jsonObject.getString("url") != null ? "<a href='" + jsonObject.getString("url") + "'>点我查看</a>" : ""));
					}else{
						sb.append(jsonObject.getString("text"));
					}
					if(jsonObject.containsKey("list")){
						sb.append("\n");
						JSONArray array = jsonObject.getJSONArray("list");
						for (int i = 0; i < 10; i++) {
							JSONObject detail = array.getJSONObject(i);
							sb.append("<a href='").append(detail.getString("detailurl")).append("'>").append(i+1).append(".").append(detail.getString("name")).append("</a>\n");
						}
					}
					textMessage.setContent(sb.toString());
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
				String content = requestMap.get("Recognition");
				reqMessage.setContent(content);
				
				String resultString = TuringUtils.askToTuring(content, fromUserName);
				JSONObject jsonObject = JSON.parseObject(resultString);
				StringBuffer sb = new StringBuffer();
				if(jsonObject.containsKey("url")){
					sb.append(jsonObject.getString("text")).append("\n").append((jsonObject.getString("url") != null ? "<a href='" + jsonObject.getString("url") + "'>点我查看</a>" : ""));
				}else{
					sb.append(jsonObject.getString("text"));
				}
				if(jsonObject.containsKey("list")){
					sb.append("\n");
					JSONArray array = jsonObject.getJSONArray("list");
					for (int i = 0; i < 10; i++) {
						JSONObject detail = array.getJSONObject(i);
						sb.append("<a href='").append(detail.getString("detailurl")).append("'>").append(i+1).append(".").append(detail.getString("name")).append("</a>\n");
					}
				}
				textMessage.setContent(sb.toString());
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
	
	public void setMessageDao(IMessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public void setReqMessageDao(IReqMessageDao reqMessageDao) {
		this.reqMessageDao = reqMessageDao;
	}

	public void setServiceMenuDao(IServiceMenuDao serviceMenuDao) {
		this.serviceMenuDao = serviceMenuDao;
	}

	public void setIpInfoDao(IPInfoDao ipInfoDao) {
		this.ipInfoDao = ipInfoDao;
	}

}
