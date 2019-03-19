var TT = WUKONG = {
	checkLogin : function(){
		var _ticket = $.cookie("WU_KONG");
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://localhost:8072/user/token/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				if(data.status == 200){
					var username = data.data.username;
					var html = username + "，欢迎来到物空！<a href=\"http://www.wukong.com/user/logout.html\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}
			},
		
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});