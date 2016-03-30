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
		<table id="clientdatalist" class="easyui-datagrid" data-options="title:'查询结果',
																	url:'<s:url value="/manage/queryPersonlist"/>',
																	idField:'pid',
														    		striped: true,
														    		toolbar:'#toolbar',
														    		pagination: true,  
														            rownumbers:false,
														            fitColumns:false,
														            singleSelect:true,
														            queryParams: {roleid: 'R_CLIENT'},
														            pageList:[10,20,50],
														            pageSize:15,
														            frozenColumns:[[
														                {field:'ck',checkbox:true}
																	]]"														            
		 >  
	        <thead>  
	            <tr>  
	                <th field="pid" width="100">客户登录账号</th>  
	                <th field="pname" width="120">客户姓名</th>
	                <th field="orgno" hidden="true"></th> 
	                <th field="status" width="50" data-options="formatter:p_personstatus">状态</th>  
	                <th field="telephone" width="100">电话</th>
	                <th field="ramark" width="300">备注</th>  
	            </tr>  
	        </thead>  
	    </table>  
	    <div id="toolbar">  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newclient()">新增客户</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editclient()">修改客户信息</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyclient()">删除客户</a>  
	    </div>    
	    <!-- 弹出的编辑对话框  -->
	    <div id="dlgcli" class="easyui-dialog" data-options="iconCls:'icon-save', border:true, cache:false" style="width:600px;height:370px;padding:10px 20px"  
	            closed="true" buttons="#dlg-buttons" modal="true">  
	        <form id="clientfm" method="post">
	        	<input id="roleid" name="roleid" type="hidden" value="R_CLIENT"/>
	        	<table class="table" style="width:100%"  cellspacing = '0' cellpadding = '2'> 
	        		<tr class="table_head" height="40px"><td  colspan="4">客户信息</td></tr>
		            <tr class="table_odd">  
		                <td>客户登录账号:</td>  
		               <td align="left"><input id="pid" name="pid" class="easyui-validatebox" required="true"></td>  
		                <td>客户人员姓名:</td>  
		               <td align="left"><input id="pname" name="pname" class="easyui-validatebox" required="true"/></td>
		            </tr>
		            <tr class="table_even">  
		                <td>客户状态:</td>  
		               <td align="left">
		               <select id="status" name="status">
		               		<option value="true" >正常</option>
		               		<option value="false" >冻结</option>
		               </select>
		               </td>  
		                <td>客户登录密码:</td>  
		                <td align="left"><input id="pwd" name="pwd" type="password" class="easyui-validatebox" required="true" value="888888">
		                </td>  
		            </tr>
		            <tr class="table_odd">  
		                <td>联系电话:</td>  
		                <td align="left"><input name="telephone"></td>   
		                <td>用户上限:</td>  
		                <td align="left"><input name="usermax" class="easyui-validatebox" required="true">
		                </td>  
		            </tr>
		            <tr class="table_even">  
		                <td>客户厂家名称:</td>  
		                <td align="left"  colspan="3"><input name="orgname" size="50"></td>   
		            </tr>
		            <tr class="table_odd">
		            	<td>备注:</td>  
		                <td align="left"  colspan="3"><input name="ramark" size="50"></td>  
		            </tr>
	            </table>
	        </form>  
	    </div>  
	    <div id="dlg-buttons">  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveclient()">保存</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgcli').dialog('close')">取消</a>  
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
//						var options = $('#clientdatalist').datagrid('options');
//						$('#clientdatalist').datagrid("resize",{width: tablewidth});
//					}
//				}catch(e){};
			};
			//--------------------------------------------------------------------------------
			function querysubmit() {
				$('#clientdatalist').datagrid('clearSelections');
				$('#clientdatalist').datagrid('load',{orgno:$('#qorgno').combobox('getValue'), roleid:'R_CLIENT'});
			}
	        var url;  
	        function newclient(){ 
	            $('#dlgcli').dialog('open').dialog('setTitle','新建');
                //主键可修改
                $('#pid').removeAttr('readonly');  
	            $('#clientfm').form('clear');
	            $('#clientfm #roleid').val("R_CLIENT");  
	            url = "<s:url value='/manage/addclient'/>"; 
	        }  
	        function editclient(){
		        //取得选中记录  
	            var row = $('#clientdatalist').datagrid('getSelected');  
	            if (row){ 
		            //显示对话框 
	                $('#dlgcli').dialog('open').dialog('setTitle','修改'); 
	                //加载记录数据 
	                $('#clientfm').form('load',row);  
	                //主键不可修改
	                $('#pid').attr('readonly',true);
	                //状态中文
	                $('#status').attr("value",row.status); 
	                url = "<s:url value='/manage/updateclient'/>";  
	            }
	            else alert("请选择一条记录.");  
	        }  
	        function saveclient(){  
	            $('#clientfm').form('submit',{  
	                url: url,  
	                onSubmit: function(){  
	                    return $(this).form('validate');  
	                },  
	                success: function(result){ 
	                    var result = $.parseJSON(result);  
	                    if (result.success){
	                    	$.messager.alert('结果',result.msg);    
	                        $('#dlgcli').dialog('close');      // close the dialog  
	                        $('#clientdatalist').datagrid('reload');    // reload the user data 
	                    } else {  
	                        $.messager.show({  
	                            title: '提示',  
	                            msg: result.msg  
	                        }); 
	                    }  
	                }  
	            });  
	        }  
	        function destroyclient(){  
	            var row = $('#clientdatalist').datagrid('getSelected');  
	            if (row){  
	                $.messager.confirm('确认','您确认要删除此客户吗?',function(r){  
	                    if (r){  
	                        $.post('<s:url value='/manage/delclient'/>',{pid:row.pid},function(result){  
	                            if (result.success){  
	                                $('#clientdatalist').datagrid('reload');    // reload the user data  
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