<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache"> 
	<meta http-equiv="cache-control" content="no-cache"> 
	<meta http-equiv="expires" content="0"> 
	<title>平台管理系统</title>
	<link rel="stylesheet" type="text/css" href="<s:url value='/js/themes/default/easyui.css'/>" />
	<link rel="stylesheet" type="text/css" href="<s:url value='/js/themes/icon.css'/>" />
	<link rel="stylesheet" type="text/css" href="<s:url value='/css/style.css'/>" />
	<script type="text/javascript" src="<s:url value='/js/jquery-1.8.0.min.js'/>"></script>
	<script type="text/javascript" src="<s:url value='/js/jquery.easyui.min.js'/>"></script>
	<script type="text/javascript" src="<s:url value='/js/public.js'/>"></script>
	<script type="text/javascript" src="<s:url value='/js/locale/easyui-lang-zh_CN.js'/>"></script> 
	<script type="text/javascript">
	$.ajaxSetup ({cache:false});
	var link = function(node){
		//如果是叶子节点
		if($(this).tree('isLeaf', node.target)){
			//打开页面
			var formurl = "<%=request.getContextPath()%>" + node.attributes.url;
			$('#mainpanel').panel('setTitle' ,node.text).panel('refresh',formurl);
		}
		//折叠/展开功能组菜单
		else  $(this).tree('toggle', node.target);
	};
	//$(function(){
	//	$('#signmenu').tree({
	//		url:'sign/sign_data.json',
	//		onClick: link
	//	});
	//	$('#appmenu').tree({
	//		url:'utp/pengda_data.json',
	//		onClick: link	
	//	});
	//});
	</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false" style="height:60px;padding:0px;background-image:url('<s:url value="/images/login_13.gif"/>')">
	 上头域增加图 片
	</div>
	<div data-options="region:'west',split:true" title= "菜单" style="width:200px;padding:0px;">
		<div class="easyui-accordion" data-options="fit:true,border:false">
		<sec:authorize access="hasRole('R_ADMIN')">
			<div title="管理员功能列表"  data-options="selected:true" style="padding:10px;overflow:auto;">
					<ul id="adminmenu" class="easyui-tree" data-options="url:'<s:url value="/manage/utp/pengda_data.json"/>', onClick:link"/>
			</div>
		</sec:authorize>
		<sec:authorize access="hasRole('R_CLIENT')">
			<div title="客户功能列表" data-options="selected:true" style="padding:10px;">
				<ul id="clientmenu" class="easyui-tree" data-options="url:'<s:url value="client/pengda_data.json"/>', onClick:link"/>
			</div>
		</sec:authorize>
		<sec:authorize access="hasRole('R_USER')">
			<div title="用户功能列表" data-options="selected:true" style="padding:10px">
				<ul id="usermenu" class="easyui-tree" data-options="url:'<s:url value="user/pengda_data.json"/>', onClick:link"/>
			</div>
		</sec:authorize>
		</div>
	</div>
	<div data-options="region:'east',split:true,collapsed:true,title:'East'" style="width:100px;padding:10px;">
		右侧隐藏域，暂不考虑
	</div>
	<div data-options="region:'south',border:false" style="height:25px;background:#A9FACD;padding:5px;">
	下边域
	</div>
	<div id="main" data-options="region:'center' " style="padding:0px;">
		<!-- 如果希望嵌入panel或tab -->
		<div id="mainpanel" style="padding:5px">	
		</div>
	</div>
	<script type="text/javascript">
	 $('#mainpanel').panel({ 
		 title:'欢迎使用应用系统',
		 fit:true,
		 border:false,
		 cache:false,
		 <sec:authorize access="hasRole('R_USER')">
		 href:'<%=request.getContextPath()%>/user/alert.jsp',
		 </sec:authorize>
		 collapsible:true
	});
	</script>
</body>
</html>