<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
   <meta http-equiv="pragma" content="no-cache">
   <meta http-equiv="cache-control" content="no-cache">
   <meta http-equiv="expires" content="0"> 
   <meta name="format-detection" content="telephone=no">  
   <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"> 
   <meta name="format-detection" content="telephone=no">
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <link rel="stylesheet" href="/css/base.css">
   <link href="/css/purchase.2012.css?v=201410141639" rel="stylesheet" type="text/css">
   <title>我的订单 - 物空商城</title>
   <script>
   	var pageConfig  = {};
   </script>
<body> 
<!--shortcut start-->
<jsp:include page="commons/shortcut.jsp" />
<!--shortcut end-->
<div class="w w1 header clearfix">
	<div id="logo"><a href="http://localhost:8078"><img width="270" height="60" clstag="clickcart|keycount|xincart|logo" src="/images/wukong-logo.gif" title="返回物空商城首页" alt="返回物空商城首页"></a></div>
    <div class="language"><a href="javascript:void(0);" onclick="toEnCart()"></a></div>
	<div class="progress clearfix">
		
	</div>
</div>
<div class="w cart">
	<div class="cart-hd group">
		<h2>我的订单</h2>
	</div>
	<div id="show">
	
<div class="cart-frame">
    <div class="tl"></div>
    <div class="tr"></div>
</div>
<div class="cart-inner">
    <div class="cart-thead clearfix">
        <!-- <div class="column t-checkbox form"><input data-cart="toggle-cb" name="toggle-checkboxes" id="toggle-checkboxes_up" type="checkbox" checked="" value=""><label for="toggle-checkboxes_up">全选</label></div> -->
        <div class="column t-goods">订单号</div>
        <div class="column t-price">订单价格</div>
        <div class="column t-promotion">购买人</div>
       <div class="column t-quantity">下单时间</div>
        <div class="column t-action">操作</div> 
    </div>
    <div id="product-list" class="cart-tbody">
        <!-- ************************商品开始********************* -->
        <c:forEach items="${orderList}" var="order">
	        <div id="product_11345721" data-bind="rowid:1" class="item item_selected ">
		        <div class="item_form clearfix">
		            <div class="cell p-goods">
		                   
		                <div class="p-name">
		                	<a href="http://localhost:8069/order/order-item-list/${order.orderId }.html" clstag="clickcart|keycount|xincart|productnamelink" target="_blank"><h3>${order.orderId}(详情)</h3></a>
		                	<span class="promise411 promise411_11345721" id="promise411_11345721"></span>
		                </div>    
		            </div>
		            <div class="cell p-price"><span class="price">¥<fmt:formatNumber groupingUsed="false" value="${order.payment / 100}" maxFractionDigits="2" minFractionDigits="2"/> </span></div>
		           <div class="cell p-inventory stock-11345721">${order.buyerNick }</div>
		           <div class="cell p-inventory stock-11345721"><fmt:formatDate value='${order.createTime }' type='date' pattern='yyyy-MM-dd HH:mm:ss'/></div>
		            <div class="cell p-remove">
		            
		            <c:if test="${order.status  == 1}">
		            <h3>
		            	未发货
		            	</h3>
		            </c:if>
		            <c:if test="${order.status  == 4}">
		            	<a href="javascript:void(0)" clstag="clickcart|keycount|xincart|productnamelink" target="_blank" onclick="confirmReceice(${order.orderId})">
		            		<h3>
		            			确认收货
		            		</h3>
		            	</a>
		            </c:if>
		            <c:if test="${order.status  == 5}">
		            	<h3>
		            	已收货
		            	</h3>
		            </c:if>
		            </div>
		        </div>
	        </div> 
        </c:forEach>
        
    </div><!-- product-list结束 -->
          <div class="cart-toolbar clearfix">
            <div class="total fr">
                <p><span class="totalSkuPrice">¥<fmt:formatNumber value="${totalPrice / 100}" maxFractionDigits="2" minFractionDigits="2" groupingUsed="true"/></span>总计：</p>
            </div>
            <div class="amout fr"><span >${count }</span> 个订单</div>
        </div>
        <div class="ui-ceilinglamp-1" style="width: 988px; height: 49px;"><div class="cart-dibu ui-ceilinglamp-current" style="width: 988px; height: 49px;">
          <div class="control fdibu fdibucurrent">
              <span class="column t-checkbox form">
                  
              </span> 
              <span class="shopping">
                  <b>
                  </b>
                  <a href="http://localhost:8078" target="_blank" clstag="clickcart|keycount|xincart|coudanlink" id="continue">继续购物</a>
              </span>
          </div>
          <div class="cart-total-2014">
              
          </div>
      </div></div>
</div><!-- cart-inner结束 -->
</div>
</div>
<!--推荐位html修改处-->


<script type="text/javascript" src="/js/base-v1.js"></script>
<!-- footer start -->
<jsp:include page="commons/footer.jsp" />
<!-- footer end -->

<!-- 购物车相关业务 -->
<script type="text/javascript" src="/js/cart.js"></script>
<script type="text/javascript" src="/js/jquery.price_format.2.0.min.js"></script>
<script type="text/javascript">
function confirmReceice(orderId){
	
	var msg = "确定收货？";  
    if (confirm(msg)!=true){  
        return ;  
    }
	$.ajax({
		url : "/order/orderUpdate.action",
		type : "post",
		data :{
			orderId : orderId
		},
		success : function(data){
			if(data.status == 200){
				alert("已收货");
			}
			window.location.reload();
		},
		error :function(){
			alert("请稍后重试！！ ");
		}
	})
}
</script>
</html>