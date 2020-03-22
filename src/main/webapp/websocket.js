var ws = null 
var chatRecordNum = 1
var flag = true
 
var sex = ""
var area = ""
var my_img = ""
var another_img = ""

var h = $("#body").scrollHeight;
window.onresize = function(){
	var chatbox = $('#chatbox')
    if (document.body.scrollHeight < h) {
    	chatbox.scrollTop(chatbox[0].scrollHeight);
             }else{
            	 chatbox.scrollTop(chatbox[0].scrollHeight);
    }
};

function closeMatch(){
	ws.close()	
}

function startMatch(){
	   
		ws = new WebSocket("ws://"+getIp()+"/chat/match");
		 
		ws.onopen = function(evt) { 
		　　console.log("Connection open ..."); 
		   var data = {}
		 
 
		   var cname=returnCitySN.cname;
 
		   data["msgType"] = "uInfo"
		   data["gender"] = $('input:radio:checked').val()

		 
		   data["area"] = cname
		    
		   
		   ws.send(JSON.stringify(data));
		   $("#myModal").modal("show")
		   
		};
		
		ws.onmessage = function(evt) {// 绑定收到消息事件
			
			//收到信息，打印调试
			//console.log( "Received Message: " + evt.data);
			var jsonObj = JSON.parse(evt.data);
 			
			 
			if(jsonObj["msgType"] == "tip"){	// 服务器给客户端的提示信息
				if(jsonObj["content"] == "matchedReal" || jsonObj["content"] == "matchedRobot"){
					window.sex = jsonObj["another_sex"]
					window.area = jsonObj["another_area"]
					window.my_img = jsonObj["my_iconSrc"]
					window.another_img = jsonObj["another_iconSrc"]
					//console.log(window.my_img)
					//console.log(window.another_img)
					$("#myModal").modal("hide")
					$("#main").load("chat.html")
				
			 
				}else if(jsonObj["content"] == "otherDisconnet"){
					alert("对方已退出聊天,请重新匹配");
					location.reload();
					ws.close();
					
					
				}
			}else if(jsonObj["msgType"] == "sendMsg"){	// 用户之间发的信息
			   var chatbox = $('#chatbox')
			   var nDivHight = chatbox.height();
			   nScrollHight = chatbox[0].scrollHeight;
			   nScrollTop = chatbox[0].scrollTop;
			 
			   //console.log(nScrollTop)
			   //console.log(nDivHight)
			   //console.log(nScrollHight)
			   var str = $('<div class="cleft cmsg"><img class="headIcon radius" ondragstart="return false;"  oncontextmenu="return false;"  src='+window.another_img+' /><span class="name">xxxxx</span><span class="content">'+jsonObj["content"]+'</span></div>')
			   
			   var obj=document.getElementById("chatbox");
			  
			   if(Math.round(nScrollTop) + Math.round(nDivHight) >= Math.round(nScrollHight)){  // 滚动条到底了
				   chatbox.find("div").eq(parseInt(chatRecordNum)).after(str);
				   chatbox.scrollTop(chatbox[0].scrollHeight);
			   }else{								// 第一次出现滚动条
				   chatbox.find("div").eq(parseInt(chatRecordNum)).after(str);
			   } 
			   chatRecordNum = parseInt(chatRecordNum) + 1
			}
		};

		ws.onclose = function(evt) { 
		　　console.log("Connection closed.");
			
		};
}
 function reMatch() {
	 ws.close();
	 location.reload();
}
 
function send() {
	var content = $("#content").val()
// alert(content)
	var data = {}
	if(content.trim() == ""){
		alert("输入内容不能为空")
	}else{
	   data["msgType"] = "sendMsg"
	   data["content"] = content
	   ws.send(JSON.stringify(data)) 
	   var chatbox = $('#chatbox')
	   var str = $('<div class="cright cmsg"><img class="headIcon radius" ondragstart="return false;"  oncontextmenu="return false;"  src='+window.my_img+'><span class="name">xxxxx</span><span class="content">'+content+'</span></div>')
	   
 
	   chatbox.find("div").eq(parseInt(chatRecordNum)).after(str)
 
	   chatbox.scrollTop(chatbox[0].scrollHeight);
	   chatRecordNum = parseInt(chatRecordNum) + 1
	   $('#content').val('')
	   
	   
	}
}

 
 function another_msg(){
	 console.log(window.sex)
	 $('#a').html("对方是来自【"+window.area+"】的一位" + "【"+window.sex+"】")
 }
 

 
 