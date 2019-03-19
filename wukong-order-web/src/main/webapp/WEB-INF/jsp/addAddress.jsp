<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache,must-revalidate">
		<title>添加地址</title>
		<link type="text/css" rel="stylesheet" href="/css/regist.personal.css" />
		<link type="text/css" rel="stylesheet" href="/css/passport.base.css" />
		<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
</head>
<body>
	<div class="w" id="logo">
		<div>
			<a href="http://localhost:8078"> <img
				src="/images/wukong-logo.gif" alt="物空商城" width="170" height="60" />
			</a> <b></b>
		</div>
	</div>

	<div class="w" id="regist">
		<div class="mt">
			<ul class="tab">
				<li class="curr">个人用户</li>
			</ul>
			<div class="extra">
				
			</div>
		</div>
		<div class="mc">
			<form id="personRegForm" method="post" onsubmit="return false;">
				<div class="form" onselectstart="return false;">
					<div class="item" id="select-regName">
						<!-- *******************姓名************************* -->
						<span class="label"><b class="ftx04">*</b>收件人姓名：</span>

						<div class="fl item-ifo">
							<div class="o-intelligent-regName">
								<input type="text" id="receiverName" name="receiverName"
									class="text" tabindex="1" autoComplete="off"
									onpaste="return false;" value=""
									onfocus="if(this.value=='') this.value='';this.style.color='#333'"
									onblur="if(this.value=='') {this.value='';this.style.color='#999999'}" />
								<i class="i-name"></i>
								<ul id="intelligent-regName" class="hide"></ul>
								<label id="regName_succeed" class="blank"></label> <label
									id="regName_error" class="hide"></label>
							</div>
						</div>
					</div>
					<div class="item" id="select-regName">
						<!-- *******************电话************************* -->
						<span class="label"><b class="ftx04">*</b>收件人电话：</span>

						<div class="fl item-ifo">
							<div class="o-intelligent-regName">
								<input type="text" id="receiverMobile" name="receiverMobile"
									class="text" tabindex="2" autoComplete="off"
									onpaste="return false;" value=""
									onfocus="if(this.value=='') this.value='';this.style.color='#333'"
									onblur="if(this.value=='') {this.value='';this.style.color='#999999'}" />
								<i class="i-name"></i>
								<ul id="intelligent-regName" class="hide"></ul>
								<label id="regName_succeed" class="blank"></label> <label
									id="regName_error" class="hide"></label>
							</div>
						</div>
					</div>
					<!-- *******************省份************************* -->
					<div class="item" id="select-regName">
						<span class="label"><b class="ftx04">*</b>省份：</span>

						<div class="fl item-ifo">
							<div class="o-intelligent-regName">
								<input type="text" id="receiverState" name="receiverState"
									class="text" tabindex="3" autoComplete="off"
									onpaste="return false;" value=""
									onfocus="if(this.value=='') this.value='';this.style.color='#333'"
									onblur="if(this.value=='') {this.value='';this.style.color='#999999'}" />
								<i class="i-name"></i>
								<ul id="intelligent-regName" class="hide"></ul>
								<label id="regName_succeed" class="blank"></label> <label
									id="regName_error" class="hide"></label>
							</div>
						</div>
					</div>
					<!-- *******************城市************************* -->
					<div class="item" id="select-regName">
						<span class="label"><b class="ftx04">*</b>城市：</span>

						<div class="fl item-ifo">
							<div class="o-intelligent-regName">
								<input type="text" id="receiverCity" name="receiverCity"
									class="text" tabindex="4" autoComplete="off"
									onpaste="return false;" value=""
									onfocus="if(this.value=='') this.value='';this.style.color='#333'"
									onblur="if(this.value=='') {this.value='';this.style.color='#999999'}" />
								<i class="i-name"></i>
								<ul id="intelligent-regName" class="hide"></ul>
								<label id="regName_succeed" class="blank"></label> <label
									id="regName_error" class="hide"></label>
							</div>
						</div>
					</div>
					<!-- *******************区/县************************* -->
					<div class="item" id="select-regName">
						<span class="label"><b class="ftx04">*</b>区/县：</span>

						<div class="fl item-ifo">
							<div class="o-intelligent-regName">
								<input type="text" id="receiverDistrict" name="receiverDistrict"
									class="text" tabindex="5" autoComplete="off"
									onpaste="return false;" value=""
									onfocus="if(this.value=='') this.value='';this.style.color='#333'"
									onblur="if(this.value=='') {this.value='';this.style.color='#999999'}" />
								<i class="i-name"></i>
								<ul id="intelligent-regName" class="hide"></ul>
								<label id="regName_succeed" class="blank"></label> <label
									id="regName_error" class="hide"></label>
							</div>
						</div>
					</div>
					<!-- *******************详细地址*************************-->
					<div class="item" id="select-regName">
						<span class="label"><b class="ftx04">*</b>详细地址：</span>

						<div class="fl item-ifo">
							<div class="o-intelligent-regName">
								<input type="text" id="receiverAddress" name="receiverAddress"
									class="text" tabindex="6" autoComplete="off"
									onpaste="return false;" value=""
									onfocus="if(this.value=='') this.value='';this.style.color='#333'"
									onblur="if(this.value=='') {this.value='';this.style.color='#999999'}" />
								<i class="i-name"></i>
								<ul id="intelligent-regName" class="hide"></ul>
								<label id="regName_succeed" class="blank"></label> <label
									id="regName_error" class="hide"></label>
							</div>
						</div>
					</div>
					<!-- ********************************************************************************************** -->


					<div class="item">
						<span class="label">&nbsp;</span> <input type="button"
							class="btn-img btn-regist" id="registsubmit" value="确认"
							tabindex="7" clstag="regist|keycount|personalreg|07"
							onclick="REGISTER.reg();" />
					</div>
				</div>
				<div class="phone">
					<img width="180" height="180" src="/images/phone-bg.jpg">
				</div>
				<span class="clr"></span>
			</form>
		</div>
		<script type="text/javascript">
			var REGISTER = {
				param : {
					//单点登录系统的url
					surl : ""
				},
				inputcheck : function() {
					//不能为空检查
					if ($("#receiverName").val() == "") {
						alert("用户名不能为空");
						$("#receiverName").focus();
						return false;
					}
					if ($("#receiverMobile").val() == "") {
						alert("电话不能为空");
						$("#receiverMobile").focus();
						return false;
					}
					if ($("#receiverState").val() == "") {
						alert("省份不能为空");
						$("#receiverState").focus();
						return false;
					}
					if ($("#receiverCity").val() == "") {
						alert("城市不能为空");
						$("#receiverCity").focus();
						return false;
					}
					if ($("#receiverDistrict").val() == "") {
						alert("区/县不能为空");
						$("#receiverDistrict").focus();
						return false;
					}
					if ($("#receiverAddress").val() == "") {
						alert("详细地址不能为空");
						$("#receiverAddress").focus();
						return false;
					}

					return true;
				},
				/* beforeSubmit:function() {
						//检查用户是否已经被占用
						$.ajax({
				        	url : REGISTER.param.surl + "/user/check/"+escape($("#regName").val())+"/1?r=" + Math.random(),
				        	success : function(data) {
				        		if (data.data) {
				        			//检查手机号是否存在
				        			$.ajax({
				        				url : REGISTER.param.surl + "/user/check/"+$("#phone").val()+"/2?r=" + Math.random(),
						            	success : function(data) {
						            		if (data.data) {
							            		REGISTER.doSubmit();
						            		} else {
						            			alert("此手机号已经被注册！");
						            			$("#phone").select();
						            		}
						            	}
				        			});
				        		} else {
				        			alert("此用户名已经被占用，请选择其他用户名");
				        			$("#regName").select();
				        		}	
				        	}
						});
				        	
				}, */
				doSubmit : function() {
					$.post("/order/addAddress/add.action", $("#personRegForm")
							.serialize(), function(data) {
						if (data.status == 200) {
							alert('添加成功！');
							REGISTER.returnOrder();
						} else {
							alert("添加失败！");
						}
					});
				},
				returnOrder : function() {
					location.href = "/order/order-cart.html";
					return false;
				},
				reg : function() {
					if (this.inputcheck()) {
						this.doSubmit();
					}
				}
			};
		</script>
</body>
</html>
