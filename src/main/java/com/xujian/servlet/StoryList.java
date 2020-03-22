package com.xujian.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 

import com.xujian.beans.Story;
import com.xujian.service.StoryService;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class StoryList
 */
public class StoryList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final StoryService storyService = new StoryService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StoryList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("StoryList接口访问，来自：" + request.getContextPath());
		
		//记录最前的时间
		String minDateTime = request.getParameter("minDateTime");
		//System.out.println("minDateTime" + minDateTime);
		 
		List<Story> storyList = storyService.storyList(minDateTime);
		/*将list集合装换成json对象*/
		JSONArray data = JSONArray.fromObject(storyList);
		//接下来发送数据
		/*设置编码，防止出现乱码问题*/
		response.setCharacterEncoding("utf-8");
		/*得到输出流*/
		PrintWriter respWritter = response.getWriter();
		/*将JSON格式的对象toString()后发送*/
		respWritter.append(data.toString());
		/*
		 * for(Story s: storyList) { System.out.println(s.getTitle());
		 * System.out.println(s.getContent()); System.out.println(s.getDateTime()); }
		 */
	 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
