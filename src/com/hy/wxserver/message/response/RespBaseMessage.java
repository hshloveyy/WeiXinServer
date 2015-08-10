package com.hy.wxserver.message.response;

import com.hy.wxserver.message.Message;
import com.hy.wxserver.message.response.interfaces.IRespMessage;

/** 
 * ��Ϣ���ࣨ�����ʺ� -> ��ͨ�û��� 
 *  
 * @author heshaohua 
 * @date 2014-3-28 
 */  
public abstract class RespBaseMessage extends Message implements IRespMessage{  
    // ���շ��ʺţ��յ���OpenID��  
    protected String ToUserName;  
    // ������΢�ź�  
    protected String FromUserName;  
    // ��Ϣ����ʱ�� �����ͣ�  
    protected long CreateTime;  
    // ��Ϣ���ͣ�text/music/news�� 
    protected String MsgType;  
    // λ0x0001����־ʱ���Ǳ���յ�����Ϣ  
//    protected int FuncFlag;  
  
    public String getToUserName() {  
        return ToUserName;  
    }  
  
    public void setToUserName(String toUserName) {  
        ToUserName = toUserName;  
    }  
  
    public String getFromUserName() {  
        return FromUserName;  
    }  
  
    public void setFromUserName(String fromUserName) {  
        FromUserName = fromUserName;  
    }  
  
    public long getCreateTime() {  
        return CreateTime;  
    }  
  
    public void setCreateTime(long createTime) {  
        CreateTime = createTime;  
    }  
  
    public String getMsgType() {  
        return MsgType;  
    }  
  
    public void setMsgType(String msgType) {  
        MsgType = msgType;  
    }
  
//    public int getFuncFlag() {  
//        return FuncFlag;  
//    }  
//  
//    public void setFuncFlag(int funcFlag) {  
//        FuncFlag = funcFlag;  
//    }  
} 
