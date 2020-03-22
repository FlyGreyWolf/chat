package com.xujian.beans;

public class Story {
	
	private Integer id;
	private String title;
	private String content;
	private String dateTime;
	
	
	
	public Story(String title, String content, String dateTime) {
		super();
		this.title = title;
		this.content = content;
		this.dateTime = dateTime;
	}


	public Story(Integer id, String title, String content, String dateTime) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.dateTime = dateTime;
	}
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
