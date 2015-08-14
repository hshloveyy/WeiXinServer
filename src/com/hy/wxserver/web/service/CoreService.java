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
 * ���ķ����� 
 *  
 * @author heshaohua E-mail:386184368@qq.com
 * @version ����ʱ�䣺2014-3-28 ����05:37:21
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
				
				//�����û�������Ϣ
				//reqMessageDao.save(reqMessage);
				
				//��Ӧ�û�
				message = MessageUtils.responseMessage(content);
				
				if(message == null){
					textMessage.setContent("����������ı���Ϣ��");
					message = textMessage;
				}
				
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_IMAGE)) { // ͼƬ��Ϣ
				textMessage.setContent("�����͵���ͼƬ��Ϣ��");
				message = textMessage;
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
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return message;
    }
	
    
    private  RespBaseMessage responseMessage(String content){
    	RespBaseMessage message = null;
    	// �ظ��ı���Ϣ  
      TextMessage textMessage = new TextMessage();  
      textMessage.setCreateTime(new Date().getTime());  
      textMessage.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_TEXT);  
//    	if(content.equals("1")){	//		1��ʮ���ں�ͼ
//			
//		}else if (content.equals("2")) {//		2������"
//			
//		}else if (content.equals("3")) {//		3����Ӱ"
//			
//		}else if (content.equals("4")) {//		4����Ϸ"
//			
//		}else if (content.equals("5")) {//		5���Ż�"
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
     * ����΢�ŷ��������� 
     *  
     * @param request 
     * @return 
     */  
    public static String processRequest2(HttpServletRequest request) {  
        String respMessage = null;  
        try {  
            // Ĭ�Ϸ��ص��ı���Ϣ����  
            String respContent = "�������쳣�����Ժ��ԣ�";  
            IReqMessageDao reqMessageDAO = new ReqMessageDAO();
  
            // xml�������  
            Map<String, String> requestMap = MessageUtils.parseXml(request);  
  
            System.out.println(requestMap.get("Content"));
            
            // ���ͷ��ʺţ�open_id��  
            String fromUserName = requestMap.get("FromUserName");  
            // �����ʺ�  
            String toUserName = requestMap.get("ToUserName");  
            // ��Ϣ����  
            String msgType = requestMap.get("MsgType");  
  
            // �ظ��ı���Ϣ  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_TEXT);  
//            textMessage.setFuncFlag(0);  
            
			if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_TEXT)) { // �ı���Ϣ  
				respContent = "�����͵����ı���Ϣ��";
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_IMAGE)) { // ͼƬ��Ϣ
				respContent = "�����͵���ͼƬ��Ϣ��";
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_LOCATION)) { // ����λ����Ϣ
				respContent = "�����͵��ǵ���λ����Ϣ��";
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_LINK)) { // ������Ϣ
				respContent = "�����͵���������Ϣ��";
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_VOICE)) { // ��Ƶ��Ϣ
				respContent = "�����͵�����Ƶ��Ϣ��";
			}else if(msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_VIDEO)){	//��Ƶ��Ϣ
				respContent = "�����͵�����Ƶ��Ϣ��";
			}else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_EVENT)) { // �¼�����
				// �¼�����
				String eventType = requestMap.get("Event");
				// ����
				if (eventType.equals(MessageUtils.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "лл���Ĺ�ע��";
				}else if (eventType.equals(MessageUtils.EVENT_TYPE_UNSUBSCRIBE)) { // ȡ������
					// TODO ȡ�����ĺ��û����ղ������ںŷ��͵���Ϣ����˲���Ҫ�ظ���Ϣ
					respContent = "ȡ������";
				}else if (eventType.equals(MessageUtils.EVENT_TYPE_CLICK)) { // �Զ���˵�����¼�
					// TODO �Զ���˵�Ȩû�п��ţ��ݲ����������Ϣ
					respContent = "�Զ���˵�Ȩ";
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
