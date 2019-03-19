<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link href="/js/kindeditor-4.1.10/themes/default/default.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div style="padding: 10px 10px 10px 10px">
	<table>
		<tr>
			<td width="150px" align="center">商品ID:</td>
			<td width="150px" align="center">商品标题:</td>
			<td width="150px" align="center">价格:</td>
			<td width="150px" align="center">购买数量:</td>
			<td width="150px" align="center">总价:</td>
		</tr>
	</table>
	
	<form id="orderEditForm" class="orderForm" method="post">
		<!--<input type="hidden" name="id" />
		 <table cellpadding="5">
			<tr>
				<td>
					<div id="itemId" class="easyui-textbox"></div>
				</td>


				<td><div id="title" class="easyui-textbox"></div></td>


				<td><div id="price" class="easyui-textbox"></div></td>


				<td><div id="num" class="easyui-textbox"></div></td>


				<td><div id="totalFee" class="easyui-textbox"></div></td>
			</tr>

		</table> 
		<input type="hidden" name="itemParams" /> <input type="hidden"
			name="itemParamId" />-->
	</form>
	<div  style="padding: 5px" align="right">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="submitForm()"><span id="orderStatus"></span></a>
	</div>
</div>
<script type="text/javascript"> 
	var orderEditEditor;
	$(function() {
		//实例化编辑器
		orderEditEditor = WUKONG.createEditor("#orderEditForm [name=desc]");
	});

	function submitForm() {
		var orderId = $("#hidOrderId").val();
		var status = $("#hidStatus").val();
		
		if(status == "1"){
			$.ajax({
				url :"/item/orderUpdate",
				type :"post",
				data :{
					orderId : orderId
				},
				success : function (data){
					if (data.status == 200) {
						$.messager.alert('提示', '成功处理订单!', 'info', function() {
							$("#orderList").datagrid("reload");
							$("#itemEditWindow").window('close'); 
							
						/* 	location.reload(); */
						});
					}
					else{
						$.messager.alert('提示', '处理订单失败，请稍后重试!', 'info', function() {
							$("#itemEditWindow").window('close');
						});
					}
				},
				error : function (){
					$.messager.alert('提示', '处理订单失败，请稍后重试!', 'info', function() {
						$("#itemEditWindow").window('close');
					});
				}
			})
		}else if(status == "5"){
			$.ajax({
				url :"/item/closeOrder",
				type :"post",
				data :{
					orderId : orderId
				},
				success : function (data){
					if (data.status == 200) {
						$.messager.alert('提示', '已关闭交易!', 'info', function() {
							$("#orderList").datagrid("reload");
							$("#itemEditWindow").window('close');
							
							/* location.reload(); */
						});
					}
					else{
						$.messager.alert('提示', '处理订单失败，请稍后重试!', 'info', function() {
							$("#itemEditWindow").window('close');
							
						});
					}
				},
				error : function (){
					$.messager.alert('提示', '处理订单失败，请稍后重试!', 'info', function() {
						$("#itemEditWindow").window('close');
				
					});
				}
			})
		}else {
			$("#itemEditWindow").window('close');
		}
		
		
		
		

		
	}
</script>
