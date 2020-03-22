package com.xujian.chat;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import net.sf.json.JSONObject;
public class FindOther {

	private static volatile Hashtable<String, ArrayList<BlockingDeque<Match>>> ht = new Hashtable<String, ArrayList<BlockingDeque<Match>>>();
	private static String key = "666";
	private static boolean flag = true;
	
	private static Object lock = new Object(); 
	private static Integer count = 0;  //次数到达10次的时候就匹配机器人吧
	
	static { 
		new Thread(new Runnable() {	
			public void run() {
				while(true) {
					Enumeration<String> e2 = ht.keys();
					try {
						Thread.sleep(1000);
						count ++;
						//System.out.println(count);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					while(e2.hasMoreElements()){
						String key = e2.nextElement();   //遍历每一个key
						
						
						//synchronized (lock) {
						if(ht.get(key).get(0).size() + ht.get(key).get(1).size() <= 1) { 
							
							if(ht.get(key).get(0).size() != 0  && count == 30) {
								count = 0;
								Match boy = ht.get(key).get(0).removeFirst();
								Match robot = new Match();
								robot.setRobot(true);
								robot.setMatchTo(boy);
								robot.setArea("泥石流星球");
								robot.setGender("女孩");
								robot.setIconSrc("./img/nishiliu.jpg");
								 
								HashMap<String, Object> hashMap = new HashMap<String, Object>();
								hashMap.put("msgType", "tip");
								hashMap.put("content", "matchedRobot");
								hashMap.put("another_sex", robot.getGender());
								hashMap.put("another_area", robot.getArea());
								hashMap.put("another_iconSrc", robot.getIconSrc());
								hashMap.put("my_iconSrc", boy.getIconSrc()); 
								JSONObject  data = JSONObject.fromObject(hashMap);
								boy.setMatchTo(robot);
								boy.sendMessage(data.toString());
							} 
							
							if(ht.get(key).get(1).size() != 0  && count == 30) {
								count = 0;
								Match girl = ht.get(key).get(10).removeFirst();
								Match robot = new Match();
								robot.setRobot(true);
								robot.setMatchTo(girl);
								robot.setArea("泥石流星球");
								robot.setGender("男孩");
								robot.setIconSrc("./img/nishiliu.jpg");
								 
								HashMap<String, Object> hashMap = new HashMap<String, Object>();
								hashMap.put("msgType", "tip");
								hashMap.put("content", "matchedRobot");
								hashMap.put("another_sex", robot.getGender());
								hashMap.put("another_area", robot.getArea());
								hashMap.put("another_iconSrc", robot.getIconSrc());
								hashMap.put("my_iconSrc", girl.getIconSrc()); 
								JSONObject  data = JSONObject.fromObject(hashMap);
								girl.setMatchTo(robot);
								girl.sendMessage(data.toString());
							} 
							continue;
						}
						//}
						
						//synchronized (lock) {
						else if(ht.get(key).get(0).size() != 0 && 
								ht.get(key).get(1).size() != 0) {
							count = 0;
							Match boy = ht.get(key).get(0).removeFirst();
							Match girl = ht.get(key).get(1).removeFirst();
							System.out.println("匹配到了" + boy + "-->" + girl);
							boy.setMatchTo(girl);
							girl.setMatchTo(boy);
							boy.sendMessage("{\"msgType\":\"tip\", \"content\":\"matchedReal\", \"another_sex\":" +"\""+girl.getGender()+"\""+",\"another_area\":"+"\""+girl.getArea()+"\""+ 
							",\"another_iconSrc\":"+ "\""+girl.getIconSrc()+"\""+ ",\"my_iconSrc\":"+ "\""+boy.getIconSrc()+"\""+"}");  
							girl.sendMessage("{\"msgType\":\"tip\", \"content\":\"matchedReal\", \"another_sex\":" +"\""+boy.getGender()+"\""+",\"another_area\":"+"\""+boy.getArea()+"\""+ 
							",\"another_iconSrc\":"+ "\""+boy.getIconSrc()+"\""+ ",\"my_iconSrc\":"+ "\""+girl.getIconSrc()+"\""+"}"); 
						}else {
							if(flag) {
								if(ht.get(key).get(0).size() >=2) {
									count = 0;
									Match boy_1 = ht.get(key).get(0).removeFirst();
									Match boy_2 = ht.get(key).get(0).removeFirst();
									System.out.println("匹配到了" + boy_1 + "-->" + boy_2);
									
									boy_1.setMatchTo(boy_2);
									boy_2.setMatchTo(boy_1);
									
									boy_1.sendMessage("{\"msgType\":\"tip\", \"content\":\"matchedReal\", \"another_sex\":" +"\""+boy_2.getGender()+"\""+",\"another_area\":"+"\""+boy_2.getArea()+"\""+ 
									",\"another_iconSrc\":"+ "\""+boy_2.getIconSrc()+"\""+ ",\"my_iconSrc\":"+ "\""+boy_1.getIconSrc()+"\""+"}");                           
									boy_2.sendMessage("{\"msgType\":\"tip\", \"content\":\"matchedReal\", \"another_sex\":" +"\""+boy_1.getGender()+"\""+",\"another_area\":"+"\""+boy_1.getArea()+"\""+ 
									",\"another_iconSrc\":"+ "\""+boy_1.getIconSrc()+"\""+ ",\"my_iconSrc\":"+ "\""+boy_2.getIconSrc()+"\""+"}");  
								}
								flag = !flag;
							}else {
								if(ht.get(key).get(1).size() >=2) {
									count = 0;
									Match girl_1 = ht.get(key).get(1).removeFirst();
									Match girl_2 = ht.get(key).get(1).removeFirst();
									System.out.println("匹配到了" + girl_1 + "-->" + girl_2);
									girl_1.setMatchTo(girl_2);
									girl_2.setMatchTo(girl_1);
									girl_1.sendMessage("{\"msgType\":\"tip\", \"content\":\"matchedReal\", \"another_sex\":" +"\""+girl_2.getGender()+"\""+",\"another_area\":"+"\""+girl_2.getArea()+"\""+ 
									",\"another_iconSrc\":"+ "\""+girl_2.getIconSrc()+"\""+ ",\"my_iconSrc\":"+ "\""+girl_1.getIconSrc()+"\""+"}"); 
									girl_2.sendMessage("{\"msgType\":\"tip\", \"content\":\"matchedReal\", \"another_sex\":" +"\""+girl_1.getGender()+"\""+",\"another_area\":"+"\""+girl_1.getArea()+"\""+ 
									",\"another_iconSrc\":"+ "\""+girl_1.getIconSrc()+"\""+ ",\"my_iconSrc\":"+ "\""+girl_2.getIconSrc()+"\""+"}");
								}
								flag = !flag;
							}
							System.out.println(ht);
						}
						//}
						 
					}	 
				}
				
			}
		}).start();
	}

	public static void addMatch(Match match) {
		if (!ht.containsKey(key)) { //double check
			synchronized (FindOther.class) {
				if (!ht.containsKey(key)) {
					ArrayList<BlockingDeque<Match>> twoGender = new ArrayList<BlockingDeque<Match>>();
					BlockingDeque<Match> boysQueue = new LinkedBlockingDeque<Match>();
					BlockingDeque<Match> girlsQueue = new LinkedBlockingDeque<Match>();
					twoGender.add(boysQueue);
					twoGender.add(girlsQueue);
					ht.put(key, twoGender);
					
				}
			}
		}

		if (match.getGender().equals("男孩")) { 
			ht.get(key).get(0).add(match);   //男孩队列添加一个match
			System.out.println(ht);
		} else {
			ht.get(key).get(1).add(match);   //女孩队列添加一个match
			System.out.println(ht);
		}

	}

	public static void subMatch(Match match) {
		//synchronized (lock) {
		Match matchTo = match.getMatchTo();
		if(matchTo != null) {				//1. 两者匹配上了之后，一方退出，要通知另一方     
			matchTo.setMatchTo(null);
			matchTo.sendMessage("{\"msgType\":\"tip\", \"content\":\"otherDisconnet\"}");
			matchTo = null;
		}else {
			if (match.getGender().equals("男孩")) {		//2.未匹配上，就断开链接了
				ht.get(key).get(0).remove(match);
			} else { 
				ht.get(key).get(1).remove(match);
			}
		}	
		//}	
	}
}
