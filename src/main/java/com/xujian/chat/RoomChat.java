package com.xujian.chat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import com.xujian.beans.Message;
import com.xujian.memorydata.ChatRoomData;

@ServerEndpoint("/chatroom")
public class RoomChat {
	
	 private Session session;
	 private String roomId;
	 @OnOpen
	 public void onOpen(Session session) throws IOException {
		 onClose();
		 this.session = session;
	 }

	 
	//服务器接收客户端信息
	@OnMessage
	public void onMessage(String message, Session session) throws IOException  {
		System.out.println("来自客户端的消息:" + message);
		JSONObject jsonObj = new JSONObject(message);
		//System.out.println(jsonObj.get("msgType"));
		
		if(jsonObj.get("msgType").equals("enterRoom")) {
			String roomId = (String) jsonObj.get("roomId");
			this.roomId = roomId;
			//System.out.println(System.identityHashCode(roomId));
			//System.out.println(roomId);
			if(this.session != null) {
				ChatRoomData.addChatRoomPersonCount(roomId);
				ChatRoomData.getRoomMems().get(roomId).add(this.session);
				
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				hashMap.put("msgType","successEnter");
				hashMap.put("onlineNum",ChatRoomData.getRoomNumMap().get(roomId));
				hashMap.put("msgsRecord", ChatRoomData.getRoomMsgs().get(roomId));
				net.sf.json.JSONObject  data = net.sf.json.JSONObject.fromObject(hashMap);
				
				this.session.getBasicRemote().sendText(data.toString());
				for(Session s: ChatRoomData.getRoomMems().get(roomId)) {
					if(s != null && s!=this.session) {
						s.getBasicRemote().sendText(data.toString());
					} 
				}

				System.out.println(ChatRoomData.getRoomMsgs());
				System.out.println(ChatRoomData.getRoomMems());
				
			} 
			
		}else if(jsonObj.get("msgType").equals("sendMsg")) {
			String roomId = (String) jsonObj.get("roomId");
			String content = (String) jsonObj.get("content");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateTime=df.format(new Date());
			Message msg = new Message(content, dateTime);
			ChatRoomData.getRoomMsgs().get(roomId).add(msg);
			for(Session s: ChatRoomData.getRoomMems().get(roomId)) {
				if(s != null && s!=this.session) {
					s.getBasicRemote().sendText("{\"msgType\":\"sendMsg\", \"content\":"+"\""+content+"\","+"\"dateTime\":"+"\""+dateTime +"\""+"}");
				}else {
					s.getBasicRemote().sendText("{\"msgType\":\"myMsg\", \"content\":"+"\""+content+"\","+"\"dateTime\":"+"\""+dateTime +"\""+"}");
				}
			}
		}
 
	}
	
	//服务器发送信息给客户端
	public void sendMessage(String message){
        try {
        	if(this.session != null) {
    			this.session.getBasicRemote().sendText(message);
        	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
		 
		}
      
    }
	
	@OnClose
	public void onClose() throws IOException{
		if(this.session != null) {
			ChatRoomData.subChatRoomPersonCount(this.roomId);
			 
			ChatRoomData.getRoomMems().get(this.roomId).remove(this.session);
			System.out.println("close"+ChatRoomData.getRoomMsgs());
			System.out.println("close"+ChatRoomData.getRoomMems());
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("msgType","quit");
			hashMap.put("onlineNum",ChatRoomData.getRoomNumMap().get(this.roomId));
			
			net.sf.json.JSONObject  data = net.sf.json.JSONObject.fromObject(hashMap);
			
			for(Session s: ChatRoomData.getRoomMems().get(roomId)) {
				if(s != null && s!=this.session) {
					s.getBasicRemote().sendText(data.toString());
				} 
			}
			
		 
		}
		
		System.out.println("onClose");
		 
	}
	
	
}
 