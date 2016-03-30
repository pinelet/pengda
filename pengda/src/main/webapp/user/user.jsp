<!DOCTYPE html>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>

<body>
	    <!-- 弹出的编辑对话框  -->
	    <div id="dlgself" class="easyui-panel" data-options="iconCls:'icon-save', border:true" style="width:800px;height:350px;padding:50px 80px">  
	        <form id="fm" method="post">
	        	<input id="pid" name="pid" type="hidden" value="${model.pid}"/>
	        	<table class="table" style="width:100%"  cellspacing = '0' cellpadding = '4'> 
	        		<tr height="35" class="table_head" style="height: 35px;"><td  colspan="4">修改个人信息</td></tr>
		            <tr class="table_odd">
		                <td width=20% align="right">用户登录账号:</td>  
		               <td align="left" width=30%>${model.pid}</td>  
		                <td width=20% align="right">用户姓名:</td>  
		               <td align="left"><input id="pname" name="pname" class="easyui-validatebox" required="true" value="${model.pname}"/></td>
		            </tr>
		            <tr class="table_even">  
		                <td align="right">客户状态:</td>  
		               <td align="left">
		               		<s:if test=" model.status == true ">正常</s:if><s:else>冻结</s:else>		               		              
					   </td>  
		                <td align="right">联系电话:</td>  
		                <td align="left"><input name="telephone" value="${model.telephone}"></td>  
		            </tr>
		            <tr class="table_odd">  
		            	<td align="right">备注:</td>  
		                <td align="left"  colspan="3"><input name="ramark" size="80" value="${model.ramark}"></td>  
		            </tr>
	            </table>
		     <div id="dlg-buttons" align="right"> 
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveclient()">保存</a>  
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgself').dialog('close')">取消</a>  
		    </div>
	        </form>  
	    </div>    
	    <script type="text/javascript">
			//-------------------------------------------------------------------------------     
			//TODO datagrid自适应宽度
			var tablewidth = null;
			function resize() {
//				try {
//					if(tablewidth == null) tablewidth = $("#queryform").width();
//					else  {
//						tablewidth = $("#queryform").width();
//						var options = $('#datalist').datagrid('options');
//						$('#datalist').datagrid("resize",{width: tablewidth});
//					}
//				}catch(e){};
			};
			//--------------------------------------------------------------------------------
	        function saveclient(){  
	            $('#fm').form('submit',{  
	                url: '<s:url value="/user/updateuser"/>',  
	                onSubmit: function(){  
	                    return $(this).form('validate');  
	                },  
	                success: function(result){ 
	                    var result = $.parseJSON(result);  
	                    alert(result.success);
	                    if (result.success){  
	                        $('#dlgself').dialog('close');      // close the dialog  
	                    } else {  
	                        $.messager.show({  
	                            title: '提示',  
	                            msg: result.msg  
	                        }); 
	                    }  
	                }  
	            });  
	        }  
	    </script> 
</body>
</html>