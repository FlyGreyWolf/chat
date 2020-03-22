package com.xujian.service.impl;

import java.util.List;

import com.xujian.beans.Story;

public interface StoryServiceImpl {

	public List<Story> storyList(String minDateTime);
	public int insertStory(Story story);
}
