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
	            <td><input id="pid" class="easyui-combobox" type="text" name="pid" required="true" data-options="
								url:'<s:url value="/manage/queryPersonlist"/>',
								valueField:'pid',
								textField:'name',
								panelHeight:'auto',
								onLoadSuccess:function() {$('#pid').combobox('select','请选择');}
						"/></td>
	        	<td>设备编号:</td>
	            <td><input class="easyui-validatebox" type="text" name="devicecode"></input></td>
	            <td><a href="#" onclick="querysubmit();" class="easyui-linkbutton" id="b1">查询</a></td>
	        </tr>
	        </table>
	    </form>
	    <!-- 查询列表 -->
		<table id="devicedatalist" class="easyui-datagrid" data-options="title:'查询结果',
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
	    <div id="dlg" class="easyui-dialog" data-options="iconCls:'icon-save', border:true" style="width:600px;height:370px;padding:10px 20px"  
	            closed="true" buttons="#dlg-buttons" modal="true">  
	        <form id="devicefm" method="post" novalidate>

	        	<table class="table" style="width:100%"  cellspacing = '0' cellpadding = '2'> 
	        		<tr class="table_head" height="40px"><td  colspan="4">设备信息</td></tr>
		            <tr class="table_odd">  
		                <td>设备编号:</td>  
		               <td align="left"><input id="fmdevicecode" name="devicecode" class="easyui-validatebox" required="true"></td>  
		                <td>用户号:</td>  
		               <td align="left"><input id="fmpid" name="pid" class="easyui-validatebox" required="true"></td>  
		            </tr>  
		            <tr class="table_even">  
		                <td>GPRS号:</td>  
		                <td align="left"><input name="gprs"></td>   
		                <td>设备型号:</td>  
		                <td align="left"><input name="type"></td>  
		            </tr>
		            <tr class="table_odd">  
		                <td>联系电话:</td>  
		                <td align="left"><input name="phone"></td>   
		                <td>设备地址:</td>  
		                <td align="left"><input name="add"></td>  
		            </tr>
	            </table>
	        </form>  
	    </div>  
	    <div id="dlg-buttons">  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>  
	    </div>  
	    <script type="text/javascript">
			//-------------------------------------------------------------------------------     
			//TODO datagrid自适应宽度
			var tablewidth = null;
			function resize() {
				try {
					if(tablewidth == null) tablewidth = $("#queryform").width();
					else  {
						tablewidth = $("#queryform").width();
						var options = $('#devicedatalist').datagrid('options');
						$('#devicedatalist').datagrid("resize",{width: tablewidth});
					}
				}catch(e){};
			};
			//--------------------------------------------------------------------------------
			function querysubmit() {
				alert($('#pid').combobox('getValue'));
				$('#devicedatalist').datagrid('load',{pid:$('#pid').combobox('getValue')});
			}
	        var url;  
	        function newUser(){ 
	            $('#dlg').dialog('open').dialog('setTitle','新建');  
	            $('#devicefm').form('clear');
	              
	            url = "<s:url value='/manage/addevice'/>"; 
	        }  
	        function editUser(){
		        //取得选中记录  
	            var row = $('#devicedatalist').datagrid('getSelected');  
	            if (row){ 
		            //显示对话框 
	                $('#dlg').dialog('open').dialog('setTitle','修改'); 
	                //加载记录数据 
	                $('#devicefm').form('load',row);  
	                //主键不可修改
	                $('#fmdevicecode').attr('disabled','disabled');
	                $('#fmpid').attr('disabled','disabled');
	                url = '<s:url value='/manage/updatedevice'/>';  
	            }
	            else alert("请选择一条记录.");  
	        }  
	        function saveUser(){  
	            $('#devicefm').form('submit',{  
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
	                        $('#devicedatalist').datagrid('reload');    // reload the user data  
	                    }  
	                }  
	            });  
	        }  
	        function destroyUser(){  
	            var row = $('#devicedatalist').datagrid('getSelected');  
	            if (row){  
	                $.messager.confirm('Confirm','Are you sure you want to destroy this user?',function(r){  
	                    if (r){  
	                        $.post('<s:url value='/manage/deldevice'/>',{pid:row.pid},function(result){  
	                            if (result.success){  
	                                $('#devicedatalist').datagrid('reload');    // reload the user data  
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