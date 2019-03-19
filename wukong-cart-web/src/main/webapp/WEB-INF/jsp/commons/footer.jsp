<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="w" clstag="homepage|keycount|home2013|37a">
	<div id="service-2013">
		<dl class="fore1">
			<dt><b></b><strong>购物指南</strong></dt>
			<dd>
				<div><a href="#" target="_blank" rel="nofollow">购物流程</a></div>
				<div><a href="#" target="_blank" rel="nofollow">会员介绍</a></div>
				<div><a href="#" target="_blank" rel="nofollow">团购/机票</a></div>
				<div><a href="#" target="_blank" rel="nofollow">常见问题</a></div>
				<div><a href="#" target="_blank" rel="nofollow">大家电</a></div>
				<div><a href="#" target="_blank" rel="nofollow">联系客服</a></div>
			</dd>
		</dl>
		<dl class="fore2">		
			<dt><b></b><strong>配送方式</strong></dt>
			<dd>
				<div><a href="#" target="_blank" rel="nofollow">上门自提</a></div>
				<div><a href="#" target="_blank" rel="nofollow">211限时达</a></div>
				<div><a href="#" target="_blank" rel="nofollow">配送服务查询</a></div>
				<div><a href="#" target="_blank" rel="nofollow">配送费收取标准</a></div>
				<div><a href="#" target="_blank" rel="nofollow">如何送礼</a></div>
				<div><a href="#" target="_blank">海外配送</a></div>
			</dd>
		</dl>
		<dl class="fore3">
			<dt><b></b><strong>支付方式</strong></dt>
			<dd>
				<div><a href="#" target="_blank" rel="nofollow">货到付款</a></div>
				<div><a href="#" target="_blank" rel="nofollow">在线支付</a></div>
				<div><a href="#" target="_blank" rel="nofollow">分期付款</a></div>
				<div><a href="#" target="_blank" rel="nofollow">邮局汇款</a></div>
				<div><a href="#" target="_blank" rel="nofollow">公司转账</a></div>
			</dd>
		</dl>
		<dl class="fore4">		
			<dt><b></b><strong>售后服务</strong></dt>
			<dd>
				<div><a href="#" target="_blank" rel="nofollow">售后政策</a></div>
				<div><a href="#" target="_blank" rel="nofollow">价格保护</a></div>
				<div><a href="#" target="_blank" rel="nofollow">退款说明</a></div>
				<div><a href="#" target="_blank" rel="nofollow">返修/退换货</a></div>
				<div><a href="#" target="_blank" rel="nofollow">取消订单</a></div>
			</dd>
		</dl>
		<dl class="fore5">
			<dt><b></b><strong>特色服务</strong></dt>
			<dd>		
				<div><a href="#" target="_blank">夺宝岛</a></div>
				<div><a href="#" target="_blank">DIY装机</a></div>
				<div><a href="#" target="_blank" rel="nofollow">延保服务</a></div>
				<div><a href="#" target="_blank" rel="nofollow">物空E卡</a></div>
				<div><a href="#" target="_blank" rel="nofollow">节能补贴</a></div>
				<div><a href="#" target="_blank" rel="nofollow">物空通信</a></div>
			</dd>
		</dl>
		<div class="fr">
			<div class="sm" id="branch-office">
				<div class="smt">
					<h3>物空自营覆盖区县</h3>
				</div>
				<div class="smc">
					<p>物空已向全国提供售后上门服务。</p>
					<p class="ar"><a href="#" target="_blank">查看详情&nbsp;></a></p>
				</div>
			</div>
		</div>
		<span class="clr"></span>
	</div>
</div>
<div class="w" clstag="homepage|keycount|home2013|38a">
	<jsp:include page="footer-links.jsp"></jsp:include>
</div>
<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
<script type="text/javascript" src="/js/jquery-extend.js"></script>
<script type="text/javascript" src="/js/lib-v1.js" charset="utf-8"></script>
<script type="text/javascript" src="/js/wukong.js" charset="utf-8"></script>
<script type="text/javascript"> (function(){
var A="<strong>热门搜索：</strong><a href='http://localhost:8075/search.html?q=%E7%94%B5%E8%84%91' target='_blank' style='color:#ff0000' clstag='homepage|keycount|home2013|03b1'>电脑</a><a href='http://localhost:8075/search.html?q=adidas' target='_blank'>adidas</a><a href='http://localhost:8075/search.html?q=%E7%AC%94%E8%AE%B0%E6%9C%AC' target='_blank'>笔记本</a><a href='http://localhost:8075/search.html?q=iPhone' target='_blank'>iPhone</a><a href='http://localhost:8075/search.html?q=%E5%B0%8F%E7%B1%B3' target='_blank'>小米</a><a href='http://localhost:8075/search.html?q=%E6%AD%A3%E5%93%81' target='_blank'>正品</a><a href='http://localhost:8075/search.html?q=%E7%94%B5%E8%A7%86' target='_blank'>电视</a><a href='http://localhost:8075/search.html?q=%E5%A5%BD%E4%B9%A6' target='_blank'>好书</a>";
var B=["电脑","java","NIKE","华为","adidas","空调"];
B=pageConfig.FN_GetRandomData(B);
$("#hotwords").html(A);
var _searchValue = "${query}";
if(_searchValue.length == 0){
	_searchValue = B;
}
$("#key").val(_searchValue).bind("focus",function(){if (this.value==B){this.value="";this.style.color="#333"}}).bind("blur",function(){if (!this.value){this.value=B;this.style.color="#999"}});
})();
</script>