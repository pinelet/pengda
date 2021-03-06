<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0"> 
<title>Insert title here</title>
</head>
<body>
		<!-- 查询条件栏   -->
	    <form id="querydevtransform" method="post" novalidate>
	        <table id="queryform" border="0" width="100%" cellspacing="0" cellpadding="5" align="center" onresize="resize();">
	        <tr>
	           	<td align="right">设备编号:</td>
	            <td align="left"><input id="devicecode" class="easyui-validatebox" type="text"></input></td>    
	            <td align="right">设备地址:</td>
	            <td align="left"  colspan="2"><input id="address" class="easyui-validatebox" type="text"></input></td> 
	        </tr>
	        <tr>
	        	<td align="right">开始时间:</td>
    			<td align="left"><input id="startdate" class="easyui-datebox" type="text"/></td>
    			<td align="right">结束时间:</td>
    			<td align="left"><input id="enddate" class="easyui-datebox" type="text"/></td>    
	        	<td align="center"><a href="#" onclick="querysubmit();" class="easyui-linkbutton" id="b1">查询</a></td>
	        </tr>
	        </table>
	    </form>
	    <!-- 查询列表 -->
		<table id="devicetranslist" class="easyui-datagrid" data-options="title:'查询结果',
																	url:'<s:url value="/user/querydevtrans"/>',
																	idField:'transId',
														    		striped: true,
														    		pagination: true,  
														            rownumbers:false,
														            fitColumns:false,
														            pageList:[10,20,50],
														            pageSize:15,
														            queryParams: {transtype: 'B'}"														            
		 >  
	        <thead>  
	            <tr>  
	            	<th field="transId" width="120">流水号</th>
	                <th field="devicecode" width="100">设备编号</th>    
	                <th field="address" width="200">设备地址</th>  
	                <th field="cardcode" width="100">卡号</th>        
	                <th field="expenditure" width="80" align="right">消费金额(元)</th> 
	                <th field="transdate" width="120">交易时间</th> 
	                <th field="remark" width="250">备注</th>  
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
				if($("#querydevtransform").form('validate'))
				$('#devicetranslist').datagrid('load',{transtype: 'B', 
													address: $('#address').val(), 
													devicecode: $("#devicecode").val(),
													startdate: $('#startdate').datebox("getValue"),
													enddate: $('#enddate').datebox("getValue")});
			}
	    </script> 
</body>
</html>