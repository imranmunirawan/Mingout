package com.mingout.models;

public class ChatModel {

	String messageFrom, messageTo, messageFromTime, messageToTime,
			ChatUserName;

	public String getMessageFrom() {
		return messageFrom;
	}

	public void setMessageFrom(String messageFrom) {
		this.messageFrom = messageFrom;
	}

	public String getMessageTo() {
		return messageTo;
	}

	public void setMessageTo(String messageTo) {
		this.messageTo = messageTo;
	}

	public String getMessageFromTime() {
		return messageFromTime;
	}

	public void setMessageFromTime(String messageFromTime) {
		this.messageFromTime = messageFromTime;
	}

	public String getMessageToTime() {
		return messageToTime;
	}

	public void setMessageToTime(String messageToTime) {
		this.messageToTime = messageToTime;
	}

	public String getChatUserName() {
		return ChatUserName;
	}

	public void setChatUserName(String chatUserName) {
		ChatUserName = chatUserName;
	}

}
