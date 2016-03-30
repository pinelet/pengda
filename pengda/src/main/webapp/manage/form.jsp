<!DOCTYPE html>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>

<body>
		<!-- 查询条件栏   -->
	    <form name="form1" method="post" novalidate>
	        <table id="queryform" border="0" width="100%" cellspacing="0" cellpadding="5" align="center" onresize="resize();">
	        <tr>
	        	<td>用户号:</td>
	            <td><input id="pid" class="easyui-validatebox" type="text" name="pid" required="true"></input></td>
	        	<td>Email:</td>
	            <td><input class="easyui-validatebox" type="text" name="email" validType="email"></input></td>
	            <td></td>
	        </tr>
	        <tr>
	        	<td><label for="subject">Subject:</label></td>
	            <td><input class="easyui-validatebox" type="text" name="subject"></input></td>
	        	<td><label for="language">Language:</label></td>
				<td><input class="easyui-combobox" name="language"
						data-options="
								url:'combobox_data.json',
								valueField:'id',
								textField:'text',
								panelHeight:'auto'
						"></td>
				<td rowspan='4'><a href="#" onclick="querysubmit();" class="easyui-linkbutton" id="dd1">查询</a></td>
	        </tr>
	        </table>
	    </form>
	    <!-- 查询列表 -->
		<table id="datalist" class="easyui-datagrid" data-options="title:'查询结果',
																	url:'<s:url value="/manage/queryDevicelist"/>',
																	idField:'devicecode',
														    		striped: true,
														    		toolbar:'#toolbar',
														    		pagination: true,  
														            rownumbers:false,
														            fitColumns:false,
														            singleSelect:true,
														            queryParams: {pid: '1001'},
														            pageList:[10,20,50],
														            pageSize:15,
														            frozenColumns:[[
														                {field:'ck',checkbox:true}
																	]]"														            
		 >  
	        <thead>  
	            <tr>  
	                <th field="devicecode" width="100">设备编号</th>  
	                <th field="pid" width="120">用户号</th>  
	                <th field="gprscode" width="100">GPRS号</th>  
	                <th field="model" width="100">设备型号</th>
	                <th field="contactcall" width="100">联系电话</th>
	                <th field="address" width="150">设备地址</th>  
	            </tr>  
	        </thead>  
	    </table>  
	    <div id="toolbar">  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新增</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>  
	    </div>    
	    <!-- 弹出的编辑对话框  -->
	    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"  
	            closed="true" buttons="#dlg-buttons" modal="true">  
	        <div class="ftitle">User Information</div>  
	        <form id="fm" method="post" novalidate>
	        	<table> 
		            <td class="fitem">  
		                <tr><label>设备编号:</label></tr>  
		                <tr><input name="devicecode" class="easyui-validatebox" required="true"></tr>  
		                <tr><label>用户号:</label></tr>  
		                <tr><input name="pid" class="easyui-validatebox" required="true"></tr>  
		            </td>  
		            <td class="fitem">  
		                <tr><label>GPRS号:</label></tr>  
		                <tr><input name="gprs"></tr>   
		                <tr><label>设备型号:</label></tr>  
		                <tr><input name="type"></tr>  
		            </td> 
		            <td class="fitem">  
		                <tr><label>联系电话:</label></tr>  
		                <tr><input name="phone"></tr>   
		                <tr><label>设备地址:</label></tr>  
		                <tr><input name="add"></tr>  
		            </td> 
	            </table>  
	        </form>  
	    </div>  
	    <div id="dlg-buttons">  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">Save</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>  
	    </div>  
	    <script type="text/javascript">
			//-------------------------------------------------------------------------------     
			//TODO datagrid自适应宽度
			var tablewidth = null;
			function resize() {
				if(tablewidth == null) tablewidth = $("#queryform").width();
				else  {
					tablewidth = $("#queryform").width();
					var options = $('#datalist').datagrid('options');
					$('#datalist').datagrid("resize",{width: tablewidth});
				}
			};
			//--------------------------------------------------------------------------------
			function querysubmit() {
				$('#datalist').datagrid('load',{pid:document.form1.pid.value});
			}
	        var url;  
	        function newUser(){ 
	            $('#dlg').dialog('open').dialog('setTitle','新建');  
	            $('#fm').form('clear');  
	            url = "<s:url value='/manage/addevice'/>"; 
	        }  
	        function editUser(){  
	            var row = $('#datalist').datagrid('getSelected');  
	            if (row){  
	                $('#dlg').dialog('open').dialog('setTitle','修改');  
	                $('#fm').form('load',row);  
	                url = 'update_user.php?id='+row.id;  
	            }  
	        }  
	        function saveUser(){  
	            $('#fm').form('submit',{  
	                url: url,  
	                onSubmit: function(){  
	                    return $(this).form('validate');  
	                },  
	                success: function(result){ 
		              
	                    var result = eval('('+result+')');  
	                    if (result.errorMsg){  
	                        $.messager.show({  
	                            title: 'Error',  
	                            msg: result.errorMsg  
	                        });  
	                    } else {  
	                        $('#dlg').dialog('close');      // close the dialog  
	                        $('#datalist').datagrid('reload');    // reload the user data  
	                    }  
	                }  
	            });  
	        }  
	        function destroyUser(){  
	            var row = $('#datalist').datagrid('getSelected');  
	            if (row){  
	                $.messager.confirm('Confirm','Are you sure you want to destroy this user?',function(r){  
	                    if (r){  
	                        $.post('destroy_user.php',{id:row.id},function(result){  
	                            if (result.success){  
	                                $('#datalist').datagrid('reload');    // reload the user data  
	                            } else {  
	                                $.messager.show({   // show error message  
	                                    title: 'Error',  
	                                    msg: result.errorMsg  
	                                });  
	                            }  
	                        },'json');  
	                    }  
	                });  
	            }  
	        }  
	    </script> 
</body>
</html>