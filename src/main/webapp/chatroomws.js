/**
 * 
 */

var ws = null 
var roomId = null;
var chatRecordNum = 0
var flag = true

var h = $("#body").scrollHeight;
window.onresize = function(){
	var chatbox = $('#chatbox')
    if (document.body.scrollHeight < h) {
    	chatbox.scrollTop(chatbox[0].scrollHeight);
             }else{
            	 chatbox.scrollTop(chatbox[0].scrollHeight);
    }
};
function enterRoom(roomId){
 
	ws = new WebSocket("ws://"+getIp()+"/chat/chatroom");
	
	ws.onopen = function(evt) { 
		var data = {}
		data["msgType"] = "enterRoom";
		data["roomId"] = roomId;
		window.roomId = roomId;
		ws.send(JSON.stringify(data));
	}
	
	ws.onmessage = function(evt) {// 绑定收到消息事件
		
		console.log( "Received Message: " + evt.data);
		var jsonObj = JSON.parse(evt.data);
		if(jsonObj["msgType"] == "successEnter"){
			//$("#main").load("roomchat.html")
			if(flag == true){
				$("#name").html(roomId)
				$("#onlineNum").html(jsonObj["onlineNum"])
				flag = false;
				 
				for(var p in jsonObj["msgsRecord"]){
					var str = '<div class="cleft cmsg"><img class="headIcon radius" ondragstart="return false;"  oncontextmenu="return false;"  src="./img/admin.png" /> <span class="name"><span class="htitle owner">管理员</span>聊天记录('+jsonObj["msgsRecord"][p]["dateTime"]+')</span><span class="content">'+jsonObj["msgsRecord"][p]["content"]+'</span></div>'
					justify(str, "")
				}
				       
			}else{
				$("#onlineNum").html(jsonObj["onlineNum"])
				var str = '<div class="tips"><span class="tips-danger">一名神秘人加入了群聊</span></div>'
				justify(str, "")
			}

			
			
			
		}else if(jsonObj["msgType"] == "quit"){
			$("#onlineNum").html(jsonObj["onlineNum"])
			var str = '<div class="tips"><span class="tips-normal">一名神秘人悄悄地走了</span></div>'
			justify(str, "")
			
		}else if(jsonObj["msgType"] == "sendMsg"){
			var str = '<div class="cleft cmsg"><img class="headIcon radius" ondragstart="return false;"  oncontextmenu="return false;"/><span class="name">神秘人('+jsonObj["dateTime"]+')</span><span class="content">'+jsonObj["content"]+ '</div>'
			justify(str, jsonObj)

		}else if(jsonObj["msgType"] == "myMsg"){
			var chatbox = $('#chatbox')
			var str = $('<div class="cright cmsg"><img class="headIcon radius" ondragstart="return false;"  oncontextmenu="return false;" ><span class="name">('+jsonObj["dateTime"]+')我</span><span class="content">'+jsonObj["content"]+'</span></div>')
			chatbox.find("div").eq(parseInt(window.chatRecordNum)).after(str)
		// 
			chatbox.scrollTop(chatbox[0].scrollHeight);
			window.chatRecordNum = parseInt(window.chatRecordNum) + 1
			$('#content').val('')
//			   
		}
	};

	ws.onclose = function(evt) { 
	　　console.log("Connection closed.");
	};
}

function send() {
	var content = $("#content").val()
//	alert(content)
	var data = {}
	if(content.trim() == ""){
		alert("输入内容不能为空")
	}else{
	   data["msgType"] = "sendMsg"
	   data["content"] = content
	   data["roomId"] = window.roomId;
	 
	   ws.send(JSON.stringify(data)) 
	   
	   
	}
}

function justify(str, jsonObj){
 
	var chatbox = $('#chatbox')
	var nDivHight = chatbox.height();
	var nScrollHight = chatbox[0].scrollHeight;
	var nScrollTop = chatbox[0].scrollTop;

	var str = $(str)                     
   
	var obj=document.getElementById("chatbox");
	if(Math.round(nScrollTop) + Math.round(nDivHight) >= Math.round(nScrollHight)){  //滚动条到底了
	   chatbox.find("div").eq(parseInt(chatRecordNum)).after(str);
	   chatbox.scrollTop(chatbox[0].scrollHeight);
	 }else{								//第一次出现滚动条
		chatbox.find("div").eq(parseInt(chatRecordNum)).after(str);
	 } 
   window.chatRecordNum = parseInt(chatRecordNum) + 1
}