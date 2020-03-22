package com.xujian.service;

import java.util.List;

import com.xujian.beans.Story;
import com.xujian.dao.StoryDao;
import com.xujian.dao.impl.StoryDaoImpl;

public class StoryService {

	private static StoryDaoImpl storyDao = new StoryDao();
	
	public List<Story> storyList(String minDateTime){
		List<Story> list= storyDao.select(minDateTime);
		return list;
	}
	
	public int insertStory(Story story) {
		int row = storyDao.insert(story);
		return row;
	}
	

}
