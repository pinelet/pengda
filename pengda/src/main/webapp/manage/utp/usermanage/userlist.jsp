<!DOCTYPE html>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>

<body>
		<!-- 查询条件栏   -->
	        <table id="queryform" border="0" width="100%" cellspacing="0" cellpadding="5" align="center" onresize="resize();">
	        <tr>
	        	<td>客户厂家:</td>
	            <td><input id="qorgno" class="easyui-combobox" type="text" name="orgno" required="true" data-options="
								url:'<s:url value="/manage/orglist"/>',
								valueField:'orgno',
								width:'600',
								textField:'orgname',
								panelHeight:'auto',
								onLoadSuccess:function() {$('#qorgno').combobox('select','请选择');}
						"/></td>
	            <td><a href="#" onclick="querysubmit();" class="easyui-linkbutton" id="b1">查询</a></td>
	        </tr>
	        </table>
	    <!-- 查询列表 -->
		<table id="userdatalist" class="easyui-datagrid" data-options="title:'查询结果',
																	url:'<s:url value="/manage/queryPersonlist"/>',
																	idField:'pid',
														    		striped: true,
														    		toolbar:'#toolbar',
														    		pagination: true,  
														            rownumbers:false,
														            fitColumns:false,
														            singleSelect:true,
														            queryParams: {roleid: 'R_USER'},
														            pageList:[10,20,50],
														            pageSize:15,
														            frozenColumns:[[
														                {field:'ck',checkbox:true}
																	]]"														            
		 >  
	        <thead>  
	            <tr>  
	                <th field="pid" width="100">用户登录账号</th>  
	                <th field="pname" width="120">用户姓名</th>
	                <th field="orgno" hidden="true"></th> 
	                <th field="orgname" width="250">厂家名称</th> 
	                <th field="status" width="50" data-options="formatter:p_personstatus">状态</th>  
	                <th field="telephone" width="100">联系电话</th>
	                <th field="ramark" width="300">备注</th>  
	            </tr>  
	        </thead>  
	    </table>  
	    <div id="toolbar">  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newuser()">新增用户</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edituser()">修改用户信息</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyuser()">删除用户</a>  
	    </div>    
	    <!-- 弹出的编辑对话框  -->
	    <div id="dlguser" class="easyui-dialog" data-options="iconCls:'icon-save', border:true, cache:false" style="width:600px;height:370px;padding:10px 20px"  
	            closed="true" buttons="#dlg-buttons" modal="true">  
	        <form id="userfm" method="post">
	        	<input id="roleid" name="roleid" type="hidden"/>
	        	<table class="table" style="width:100%"  cellspacing = '0' cellpadding = '2'> 
	        		<tr class="table_head" height="40px"><td  colspan="4">用户信息</td></tr>
	        		<tr>
	        			<td>客户厂家:</td>
	            		<td  colspan="3"><input id="orgno" class="easyui-combobox" type="text" name="orgno" required="true" data-options="
								url:'<s:url value="/manage/orglist"/>',
								valueField:'orgno',
								width:'500',
								textField:'orgname',
								panelHeight:'auto'
						"/></td>  
	        		</tr>
		            <tr class="table_odd">
		                <td>用户登录账号:</td>  
		               <td align="left"><input id="pid" name="pid" class="easyui-validatebox" required="true"></td>  
		                <td>用户姓名:</td>  
		               <td align="left"><input id="pname" name="pname" class="easyui-validatebox" required="true"/></td>
		            </tr>
		            <tr class="table_even">  
		                <td>用户状态:</td>  
		               <td align="left">
		               <select id="status" name="status">
		               		<option value="true" >正常</option>
		               		<option value="false" >冻结</option>
		               </select>
		               </td>  
		                <td>用户登录密码:</td>  
		                <td align="left"><input id="pwd" name="pwd" type="password" class="easyui-validatebox" required="true" value="888888">
		                </td>  
		            </tr>
		            <tr class="table_odd">  
		                <td>联系电话:</td>  
		                <td align="left"><input name="telephone"></td>   

  
		            </tr>
		            <tr class="table_even">
		            	<td>备注:</td>  
		                <td align="left"  colspan="3"><input name="ramark" size="80"></td>  
		            </tr>
	            </table>
	        </form>  
	    </div>  
	    <div id="dlg-buttons">  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveuser()">保存</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlguser').dialog('close')">取消</a>  
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
//						var options = $('#userdatalist').datagrid('options');
//						$('#userdatalist').datagrid("resize",{width: tablewidth});
//					}
//				}catch(e){};
			};
			//--------------------------------------------------------------------------------
			function querysubmit() {
				$('#userdatalist').datagrid('clearSelections');
				$('#userdatalist').datagrid('load',{orgno:$('#qorgno').combobox('getValue'), roleid:'R_USER'});
			}
	        var url;  
	        function newuser(){ 
	            $('#dlguser').dialog('open').dialog('setTitle','新建');
                //主键可修改
                $('#pid').removeAttr('readonly');  
	            $('#userfm').form('clear');
	            $('#userfm #roleid').val("R_USER");  
	            url = "<s:url value='/manage/addclient'/>"; 
	        }  
	        function edituser(){
		        //取得选中记录  
	            var row = $('#userdatalist').datagrid('getSelected');  
	            if (row){ 
		            //显示对话框 
	                $('#dlguser').dialog('open').dialog('setTitle','修改'); 
	                //加载记录数据 
	                $('#userfm').form('load',row);  
	                //主键不可修改
	                $('#pid').attr('readonly',true);
	                //状态中文
	                $('#status').attr("value",row.status); 
	                url = "<s:url value='/manage/updateclient'/>";  
	            }
	            else alert("请选择一条记录.");  
	        }  
	        function saveuser(){  
	            $('#userfm').form('submit',{  
	                url: url,  
	                onSubmit: function(){  
	                    return $(this).form('validate');  
	                },  
	                success: function(result){ 
	                    var result = $.parseJSON(result);  
	                    if (result.success){
	                    	$.messager.alert('结果',result.msg);    
	                        $('#dlguser').dialog('close');      // close the dialog  
	                        $('#userdatalist').datagrid('reload');    // reload the user data 
	                    } else {  
	                        $.messager.show({  
	                            title: '提示',  
	                            msg: result.msg  
	                        }); 
	                    }  
	                }  
	            });  
	        }  
	        function destroyuser(){  
	            var row = $('#userdatalist').datagrid('getSelected');  
	            if (row){  
	                $.messager.confirm('确认','您确认要删除此用户吗?',function(r){  
	                    if (r){  
	                        $.post('<s:url value='/manage/delclient'/>',{pid:row.pid},function(result){  
	                            if (result.success){  
	                                $('#userdatalist').datagrid('reload');    // reload the user data  
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
	            else   alert("请选择一条记录.");  
	        }  
	    </script> 
</body>
</html>