package chat;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import javax.websocket.Session;

import com.xujian.beans.Message;
import com.xujian.memorydata.ChatRoomData;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		 * ConcurrentLinkedDeque<Message> roomMems = new
		 * ConcurrentLinkedDeque<Message>(); Message a = new Message("1244", "321321");
		 * Message b = new Message("1244", "3213211"); roomMems.add(a); roomMems.add(b);
		 * System.out.println(roomMems); for(Message s: roomMems) {
		 * System.out.println(s.getDateTime()); } roomMems.remove(new Message("1244",
		 * "321321")); System.out.println(roomMems); for(Message s: roomMems) {
		 * System.out.println(s.getDateTime()); }
		 */
		ConcurrentLinkedDeque<Message> msgs = ChatRoomData.getRoomMsgs().get("╪ед╞адад");
		System.out.println(msgs.size());
		for(Message msg: msgs) {
			System.out.println(msg.getContent());
		}
	 
		
	}

}
