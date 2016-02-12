<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0"> 
</head>
<body>
		<!-- 查询条件栏   -->
	    <form id="querycardinfoform" method="post">
	        <table id="queryform" border="0" width="100%" cellspacing="0" cellpadding="5" align="center" onresize="resize();">
	        <tr>
	        	<td align="right">设备编号:</td>
	            <td align="left"><input id="devicecode" type="text"></input></td>
	           	<td align="right">GPRS号:</td>
	            <td align="left"><input id="cardname" type="text"></input></td>     
	            <td align="right">设备地址:</td>
    			<td align="left"><input id="phone" type="text"/></td>
	        </tr>
	        <tr>
	        	<td align="right">开始时间:</td>
    			<td align="left"><input id="startdate" class="easyui-datebox" type="text"/></td>
    			<td align="right">结束时间:</td>
    			<td align="left"><input id="enddate" class="easyui-datebox" type="text"/></td>    
	        	<td align="center" colspan="2"><a href="#" onclick="querysubmit();" class="easyui-linkbutton" id="b1">查询</a></td>
	        </tr>
	        </table>
	    </form>
	    <!-- 查询列表 -->
		<table id="cardinfolist" class="easyui-datagrid" data-options="title:'查询结果',
																	url:'<s:url value="/user/querycardinfo"/>',
														    		striped: true,
														    		pagination: true,  
														            rownumbers:false,
														            fitColumns:false,
														            pageList:[10,20,50],
														            pageSize:15"														            
		 >  
	        <thead>  
	            <tr>  
	                <th field="cardcode" width="150">卡号</th>
	                <th field="cardname" width="80">持卡人姓名</th>             
	                <th field="phone" width="100">持卡人电话</th> 
	                <th field="address" width="160">办卡地址</th> 
	                <th field="cardstatus" width="100" data-options="formatter:p_cardstatus">卡状态</th> 
	                <th field="balance" width="100" align="right">卡剩余金额(元)</th> 
	                <th field="remark" width="150">备注</th>  
	            </tr>  
	        </thead>  
	    </table>     

	    <script type="text/javascript">
			//-------------------------------------------------------------------------------     
			//TODO datagrid自适应宽度
			var tablewidth = null;
			function resize() {
				try {
					if(tablewidth == null) tablewidth = $("#queryform").width();
					else  {
						tablewidth = $("#queryform").width();
						$('#cardinfolist').datagrid("resize",{width: tablewidth});
					}
				}catch(e){};
			};
			//--------------------------------------------------------------------------------
			function querysubmit() {
				$('#cardinfolist').datagrid('load',{cardcode: $('#cardcode').val(), 
													cardname: $("#cardname").val(),
													phone: $('#phone').val(),
													address: $('#address').val()});
			}
	    </script> 
</body>
</html>