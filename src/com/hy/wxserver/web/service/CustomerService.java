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
 * @version ����ʱ�䣺2014-4-8 ����06:13:00
 * ��˵��
 */
@Service("customerService")
public class CustomerService implements ICustomerService {
	
	private IMessageDao messageDao;
	
	private IReqMessageDao reqMessageDao;
	
	private IServiceMenuDao serviceMenuDao;


	public RespBaseMessage processRequest(HttpServletRequest request) {
		RespBaseMessage message = null;
    	// Ĭ�Ϸ��ص��ı���Ϣ����  
    	try {
    		// xml�������  
            Map<String, String> requestMap = MessageUtils.parseXml(request); 
            
            // ���ͷ��ʺţ�open_id��  
            String fromUserName = requestMap.get("FromUserName");  
            // �����ʺ�  
            String toUserName = requestMap.get("ToUserName");  
            // ��Ϣ����  
            String msgType = requestMap.get("MsgType");  
            //��ϢID

            String msgId = requestMap.get("MsgId");
            //����ʱ��
            String createTime = requestMap.get("CreateTime");
            
          //�����û�������Ϣ
			ReqMessage reqMessage = new ReqMessage(msgId, toUserName, fromUserName, new Date(Long.valueOf(createTime)*1000), msgType);
            
			// �ظ��ı���Ϣ  
		      TextMessage textMessage = new TextMessage();  
		      textMessage.setToUserName(fromUserName);  
		      textMessage.setFromUserName(toUserName);  
		      textMessage.setCreateTime(new Date().getTime());  
		      textMessage.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_TEXT);  
		      
			if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_TEXT)) { // �ı���Ϣ  
				String content = requestMap.get("Content");
				
				reqMessage.setContent(content);
				
				//��Ӧ�û�
				List<ServiceMenu> serviceList = serviceMenuDao.findAll();
		    	for (ServiceMenu serviceMenu : serviceList) {
					if(serviceMenu.getCode().equalsIgnoreCase(content)){
						textMessage.setContent(serviceMenu.getCodeDesc());
						
						message = textMessage;
					}
				}
				if(message == null){
					textMessage.setContent("����������ı���Ϣ��");
					message = textMessage;
				}
				
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_IMAGE)) { // ͼƬ��Ϣ
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
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_LOCATION)) { // ����λ����Ϣ
				textMessage.setContent("�����͵��ǵ���λ����Ϣ��");
				message = textMessage;
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_LINK)) { // ������Ϣ
				textMessage.setContent("�����͵���������Ϣ��");
				message = textMessage;
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_VOICE)) { // ��Ƶ��Ϣ
				textMessage.setContent("�����͵�����Ƶ��Ϣ��");
				message = textMessage;
			}else if(msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_VIDEO)){	//��Ƶ��Ϣ
				textMessage.setContent("�����͵�����Ƶ��Ϣ��");
				message = textMessage;
			}else if(msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_SHORTVIDEO)){	//��Ƶ��Ϣ
				textMessage.setContent("�����͵���С��Ƶ��Ϣ��");
				message = textMessage;
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_EVENT)) { // �¼�����
				// �¼�����
				String eventType = requestMap.get("Event");
				// ����
				if (eventType.equals(MessageUtils.EVENT_TYPE_SUBSCRIBE)) {
					textMessage.setContent("лл���Ĺ�ע��");
					message = textMessage;
				}else if (eventType.equals(MessageUtils.EVENT_TYPE_UNSUBSCRIBE)) { // ȡ������
					// TODO ȡ�����ĺ��û����ղ������ںŷ��͵���Ϣ����˲���Ҫ�ظ���Ϣ
					textMessage.setContent("ȡ������");
					message = textMessage;
				}else if (eventType.equals(MessageUtils.EVENT_TYPE_CLICK)) { // �Զ���˵�����¼�
					// TODO �Զ���˵�Ȩû�п��ţ��ݲ����������Ϣ
					textMessage.setContent("�Զ���˵�Ȩ");
					message = textMessage;
				}
			}
			//�����û�������Ϣ
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
