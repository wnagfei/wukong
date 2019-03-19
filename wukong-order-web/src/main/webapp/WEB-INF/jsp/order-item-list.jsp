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
   <title>订单详情 - 物空商城</title>
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
		<h2>订单详情</h2>
	</div>
	<div id="show">
	
<div class="cart-frame">
    <div class="tl"></div>
    <div class="tr"></div>
</div>
<div class="cart-inner">
    <div class="cart-thead clearfix">
        <!-- <div class="column t-checkbox form"><input data-cart="toggle-cb" name="toggle-checkboxes" id="toggle-checkboxes_up" type="checkbox" checked="" value=""><label for="toggle-checkboxes_up">全选</label></div> -->
        <div class="column t-goods">物品名</div>
        <div class="column t-price">物品价格</div>
        <div class="column t-promotion">数量</div>
      	<div class="column t-quantity">总价</div>
        
    </div>
    <div id="product-list" class="cart-tbody">
        <!-- ************************商品开始********************* -->
        <c:forEach items="${orderItem}" var="item">
	        <div id="product_11345721" data-bind="rowid:1" class="item item_selected ">
		        <div class="item_form clearfix">
		            <div class="cell p-goods">
		                   
		                <div class="p-name">
		                	${item.title}
		                	<span class="promise411 promise411_11345721" id="promise411_11345721"></span>
		                </div>    
		            </div>
		            <div class="cell p-price"><span class="price">¥<fmt:formatNumber groupingUsed="false" value="${item.price / 100}" maxFractionDigits="2" minFractionDigits="2"/> </span></div>
		           <div class="cell p-inventory stock-11345721">${item.num }</div>
		            <div class="cell p-remove">¥<fmt:formatNumber groupingUsed="false" value="${item.totalFee / 100 }" maxFractionDigits="2" minFractionDigits="2"/> 
		            </div>
		        </div>
	        </div> 
        </c:forEach>
        
    </div><!-- product-list结束 -->
          <div class="cart-toolbar clearfix">
            <div class="total fr">
                <p><span class="totalSkuPrice">¥<fmt:formatNumber value="${totalPrice / 100}" maxFractionDigits="2" minFractionDigits="2" groupingUsed="true"/></span>总计：</p>
            </div>
            <div class="amout fr"><span id="selectedCount">${count }</span> 个商品</div>
        </div>
        <div class="ui-ceilinglamp-1" style="width: 988px; height: 49px;"><div class="cart-dibu ui-ceilinglamp-current" style="width: 988px; height: 49px;">
          <div class="control fdibu fdibucurrent">
              <span class="column t-checkbox form">
                  
              </span> 
              <span class="shopping">
                  <b>
                  </b>
                  <a href="http://localhost:8069/order/order-list.html">返回</a>
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

</html>