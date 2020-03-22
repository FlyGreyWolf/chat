package com.xujian.dao.impl;

import java.sql.ResultSet;
import java.util.List;

import com.xujian.beans.Story;

public interface StoryDaoImpl {
	
	List<Story> select(String minDateTime);
	int insert(Story story);

}
