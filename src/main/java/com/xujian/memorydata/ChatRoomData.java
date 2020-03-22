package com.xujian.memorydata;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import javax.websocket.Session;

import com.xujian.beans.Message;

public class ChatRoomData {
	
	
	private static ArrayList<String> roomsId = new ArrayList<String>(Arrays.asList("寂寞聊聊","单身男女","技术交流","学习交流","游戏专区"));
	private static volatile ConcurrentHashMap<String, Integer> roomNumMap = new ConcurrentHashMap<String, Integer>(); 
    private static volatile ConcurrentHashMap<String, ConcurrentLinkedDeque<Message>> roomMsgs
    				= new ConcurrentHashMap<String, ConcurrentLinkedDeque<Message>>();
    private static volatile ConcurrentHashMap<String, ConcurrentLinkedDeque<Session>> roomMems
    				= new ConcurrentHashMap<String, ConcurrentLinkedDeque<Session>>();
	static {
    	for(String roomId: roomsId) {
    		roomNumMap.put(roomId, 0);
    		roomMsgs.put(roomId, new ConcurrentLinkedDeque<Message>());
    		roomMems.put(roomId, new ConcurrentLinkedDeque<Session>());
    	}
    }
    
    public static ConcurrentHashMap<String, Integer> getRoomNumMap() {
    	return roomNumMap;
    }
    
    public static ArrayList<String> getRoomsId() {
		return roomsId;
	}

	public static ConcurrentHashMap<String, ConcurrentLinkedDeque<Message>> getRoomMsgs() {
		return roomMsgs;
	}

	public static ConcurrentHashMap<String, ConcurrentLinkedDeque<Session>> getRoomMems() {
		return roomMems;
	}

	public static void addChatRoomPersonCount(String roomId) {
    	synchronized (roomId.intern()) {
    		roomNumMap.put(roomId, (Integer)roomNumMap.get(roomId) + 1);
		}
    	
    }
    
    public static void subChatRoomPersonCount(String roomId) {
    	synchronized (roomId.intern()) {
    		roomNumMap.put(roomId, (Integer)roomNumMap.get(roomId) - 1);
		}
    }
    
    
    
}
