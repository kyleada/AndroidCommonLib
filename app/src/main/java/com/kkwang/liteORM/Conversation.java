package com.kkwang.liteORM;

import com.litesuits.orm.db.annotation.Table;

import java.util.List;

/**
 * Created by kw on 2016/3/8.
 */
@Table("Conversation") //指定表名，可任意写
public class Conversation extends BaseModel {

    public static final String MESSAGEID = "messageId";
    public static final String ISVISIBILITY = "isVisibility";
    //这里又是一个实体类的LIST，可以理解为另外一张表。这么写，就代表 Conversation表和User表是 一对多关系
    private List<User> user;

    private String nickName;
    private String headImgUrl;
    private String content;
    private String sendDate;
    private int msgType;
    private int subType;
    private int messageId; // 用于话题聊天的id，和私聊的 Userid
    private int senderUserId;
    private Boolean isVisibility = true;
    private int messageType;
    private int messageNum;


    public static String getMESSAGEID() {
        return MESSAGEID;
    }


    public static String getISVISIBILITY() {
        return ISVISIBILITY;
    }


    public List<User> getUser() {
        return user;
    }


    public void setUser(List<User> user) {
        this.user = user;
    }


    public String getNickName() {
        return nickName;
    }


    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public String getHeadImgUrl() {
        return headImgUrl;
    }


    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public String getSendDate() {
        return sendDate;
    }


    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }


    public int getMsgType() {
        return msgType;
    }


    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }


    public int getSubType() {
        return subType;
    }


    public void setSubType(int subType) {
        this.subType = subType;
    }


    public int getMessageId() {
        return messageId;
    }


    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }


    public int getSenderUserId() {
        return senderUserId;
    }


    public void setSenderUserId(int senderUserId) {
        this.senderUserId = senderUserId;
    }


    public Boolean getVisibility() {
        return isVisibility;
    }


    public void setVisibility(Boolean visibility) {
        isVisibility = visibility;
    }


    public int getMessageType() {
        return messageType;
    }


    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }


    public int getMessageNum() {
        return messageNum;
    }


    public void setMessageNum(int messageNum) {
        this.messageNum = messageNum;
    }
}
