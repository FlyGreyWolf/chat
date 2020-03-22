package com.xujian.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xujian.beans.Story;
import com.xujian.service.StoryService;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class SendStory
 */
public class SendStory extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final StoryService storyService = new StoryService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendStory() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//		System.out.println("wocaonioma ");
		System.out.println("SendStory接口访问，来自：" + request.getContextPath());
		/*设置编码，防止出现乱码问题*/
		response.setCharacterEncoding("utf-8");
		String titlename = request.getParameter("titlename");
		String content = request.getParameter("content");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime=df.format(new Date());
		Story story = new Story(titlename, content, dateTime);
		int row = storyService.insertStory(story);
	 
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("row",Integer.toString(row));
		hashMap.put("story",story);
		JSONObject  data = JSONObject.fromObject(hashMap);
		
		response.getWriter().append(data.toString());
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
