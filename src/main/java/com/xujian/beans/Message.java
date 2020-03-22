package com.xujian.beans;

public class Message {
	
	private String content;
	private String dateTime;
	
	public Message(String content, String dateTime) {
		super();
		this.content = content;
		this.dateTime = dateTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	
	

}
