<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="easyui-datagrid" id="orderList" title="订单列表" 
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/item/order-list',method:'get',pageSize:30,toolbar:toolbar">
    <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'orderId',width:150,align:'center',">订单号</th>
            <th data-options="field:'buyerNick',width:150,align:'center',">购买人</th>
            <th data-options="field:'paymentType',width:100,align:'center',formatter:WUKONG.formatPaymentType">支付类型</th>
            <th data-options="field:'payment',width:150,align:'center',formatter:WUKONG.formatPrice">订单总价</th>
            <th data-options="field:'status',width:100,align:'center',formatter:WUKONG.formatOrderStatus">处理状态</th>
            <th data-options="field:'createTime',width:130,align:'center',formatter:WUKONG.formatDateTime">创建日期</th>
            <th data-options="field:'updateTime',width:130,align:'center',formatter:WUKONG.formatDateTime">更新日期</th>
        </tr>
    </thead>
</table>
<div id="itemEditWindow" class="easyui-window" title="订单详情" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/order-item'" style="width:80%;height:80%;padding:10px;">
</div>
<script>

    function getSelectionsIds(){
    	var orderList = $("#orderList");
    	var sels = orderList.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].orderId);
    	}
    	ids = ids.join(",");
    	return ids;
    }
    
    /* var toolbar = [{
        text:'新增',
        iconCls:'icon-add',
        handler:function(){
        	$(".tree-title:contains('新增商品')").parent().click();
        }
    }, */
    var toolbar = [{
        text:'查看',
        iconCls:'icon-edit',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','必须选择一个订单才能查看!');
        		return ;
        	}
        	if(ids.indexOf(',') > 0){
        		$.messager.alert('提示','只能选择一个订单!');
        		return ;
        	}
        	
        	$("#itemEditWindow").window({
        		onLoad :function(){
        			var data = $("#orderList").datagrid("getSelections")[0];
        			//加载订单详情
        			$.getJSON('/item/orderItemList/'+data.orderId,function(_data){
        				if( _data.status == 200 ){
        					var itemData = new Array();
        					itemData = _data.data;
        					 var html = "<table cellpadding=\"5\">";
        					 
        					 for(var i in itemData){
        						 html+="<tr id=\"orderItemReload\"><td width=\"150px\"  align=\"center\">"+itemData[i].itemId+"</td>";
        						 html+="<td width=\"140px\"  align=\"center\">"+itemData[i].title+"</td>";
        						 html+="<td width=\"140px\"  align=\"center\">"+itemData[i].price+"</td>";
        						 html+="<td width=\"140px\"  align=\"center\">"+itemData[i].num+"</td>";
        						 html+="<td width=\"140px\"  align=\"center\">"+itemData[i].totalFee+"</td></tr>";
        						 
        					 }
        					 html+= "</table>";
        					 html+= "<input type=\"hidden\" id=\"hidOrderId\"  value=\""+data.orderId +"\"/>";
        					 html+= "<input type=\"hidden\" id=\"hidStatus\"  value=\""+data.status +"\"/>";
        					 if(data.status == 1){
        						var s = "处理此订单";
        						 $("#orderStatus").html(s);
        					 }else if(data.status == 5){
        						 var s = "关闭交易";
        						 $("#orderStatus").html(s);
        					 }else{
        						 var s = "关闭";
        						 $("#orderStatus").html(s);
        					 }
        					 $("#orderEditForm").html(html);
        					
        				}
        			});
        			
        			WUKONG.init({
        				fun:function(node){
        					WUKONG.changeItemParam(node, "orderEditForm");
        				}
        			});
        		}
        	}).window("open");
        	
        }
    
    }];
    


   
    
</script>