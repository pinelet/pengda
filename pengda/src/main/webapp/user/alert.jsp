<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0"> 
<title></title>
</head>
<body>
	<table id="alertdatalist" class="easyui-datagrid" data-options="title:'设备报警一览',
																	url:'<s:url value="/user/queryAlertlist"/>',
																	idField:'gprscode',
														    		striped: true,
														    		toolbar:'#toolbar',
														    		pagination: true,  
														            rownumbers:false,
														            fitColumns:false,
														            singleSelect:true,
														            queryParams: {pid: '<sec:authentication property="principal.username"/>'},
														            pageList:[10,20,50],
														            pageSize:15,
														            frozenColumns:[[
														                {field:'ck',checkbox:true}
																	]]">  
	        <thead>  
	            <tr>  
	                <th field="devicecode" width="100">设备编号</th>  
	                <th field="gprscode" width="100">GPRS号</th> 
	                <th field="model" width="100">设备型号</th>
	                <th field="cardcom" data-options="formatter:formmterstatus">Card通讯错误</th>
	                <th field="lowpressure" data-options="formatter:formmterstatus">低压报警</th>
	                <th field="highposition" width="80" data-options="formatter:formmterstatus">高液位报警</th> 
	                <th field="lowposition" width="80" data-options="formatter:formmterstatus">低液位报警</th> 
	                <th field="makewater" width="80"  data-options="formatter:formmterstatus">制水</th> 
	                <th field="offsale" width="80"  data-options="formatter:formmterstatus">停售</th>
	                <th field="onsale" width="80"  data-options="formatter:formmterstatus">售水</th> 
	            </tr>  
	        </thead>  
	    </table>  
	    <div id="toolbar">  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refresh()">更新一下</a>   
	    </div>
	    <script type="text/javascript">
	    function refresh() {
	    	$('#alertdatalist').datagrid('reload');
	    }
	    var formmterstatus = function(value,row,index){
		    if (value == true)
        		return "<img src='<s:url value="/js/themes/icons/no.png"/>'/>";
        	else 
            	return "<img src='<s:url value="/js/themes/icons/ok.png"/>'/>";
        }; 
	    </script>
</body>
</html>