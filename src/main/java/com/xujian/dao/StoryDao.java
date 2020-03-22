package com.xujian.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.xujian.beans.Story;
import com.xujian.dao.impl.StoryDaoImpl;
import com.xujian.util.DruidUtil;

public class StoryDao implements StoryDaoImpl{

	public List<Story> select(String minDateTime) {
        List<Story> list=new ArrayList<Story>();
        SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "";
        if(minDateTime.equals("")) {
        	sql = "select * from story where \"\"=? order by s_time desc limit 10";
        }else {
        	sql = "select * from story where s_time < ? order by s_time desc limit 10";
        }
        try {
            Connection conn = DruidUtil.ds.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setString(1, minDateTime);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                Story c=new Story(rs.getInt("s_id"), rs.getString("s_title"), rs.getString("s_content"),myFmt.format(rs.getTimestamp("s_time")));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
	}

	public int insert(Story story) {
		int row=0;
        try {
            Connection conn=DruidUtil.ds.getConnection();
            
            PreparedStatement ps=conn.prepareStatement("insert into Story(`s_title`, `s_content`, `s_time`) values (?,?,?);");
            ps.setString(1,story.getTitle());
            ps.setString(2,story.getContent());
            ps.setString(3,story.getDateTime());
            row=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return row;
 
	}
	

}
