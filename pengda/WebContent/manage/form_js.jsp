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
		<table id="datalist"/>   
	    <!-- div id="toolbar">  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新增</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>  
	    </div -->    
	    <!-- 弹出的编辑对话框  -->
	    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"  
	            closed="true" buttons="#dlg-buttons" modal="true">  
	        <div class="ftitle">User Information</div>  
	        <form id="fm" method="post" novalidate>  
	            <div class="fitem">  
	                <label>First Name:</label>  
	                <input name="firstname" class="easyui-validatebox" required="true">  
	            </div>  
	            <div class="fitem">  
	                <label>Last Name:</label>  
	                <input name="lastname" class="easyui-validatebox" required="true">  
	            </div>  
	            <div class="fitem">  
	                <label>Phone:</label>  
	                <input name="phone">  
	            </div>  
	            <div class="fitem">  
	                <label>Email:</label>  
	                <input name="email" class="easyui-validatebox" validType="email">  
	            </div>  
	        </form>  
	    </div>  
	    <div id="dlg-buttons">  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">Save</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>  
	    </div>  
	    <script type="text/javascript">
	    //组件初始化
//------------------------------------------------------------------------------- 
			//列表初始化
			$('#datalist').datagrid({
				title:'查询结果',
				url:'<s:url value="/manage/queryDevicelist"/>',
				idField:'devicecode',
				doSize:false,
	    		striped: true,

	    		pagination: true,  
	            rownumbers:false,
	            fitColumns:false,
	            singleSelect:true,
	            queryParams: {pid: '1001'},
	            columns:[[
					{field:"devicecode" ,width:"100",title:'设备编号'},
					{field:"pid" 		,width:"120",title:'用户号'},
					{field:"gprscode"	,width:"100",title:'GPRS号' },
					{field:"model" 		,width:"100",title:'设备型号'},
					{field:"contactcall",width:"100",title:'联系电话'},
					{field:"address" 	,width:"150",title:'设备地址'}      	            
	      	      		]],
      	      	toolbar:[{
					id:'btnadd',
					text:'Add',
					iconCls:'icon-add',
					handler:function(){
						$('#btnsave').linkbutton('enable');
						alert('add');
					}
				},{
					id:'btncut',
					text:'Cut',
					iconCls:'icon-cut',
					handler:function(){
						$('#btnsave').linkbutton('enable');
						alert('cut');
					}
				},'-',{
					id:'btnsave',
					text:'Save',
					disabled:true,
					iconCls:'icon-save',
					handler:function(){
						$('#btnsave').linkbutton('disable');
						alert('save');
					}
				}]
				});
		    //分页初始化
		   	var pager = $('#datalist').datagrid('getPager');
		   	$(pager).pagination({
				total:20,
				pageSize:15,
				pageList:[10,20,50]
			});
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
				//$('#datalist').datagrid('options').url = "/manage/queryDevicelist";
				$('#datalist').datagrid('load',{pid:document.form1.pid.value});
			}
	        var url;  
	        function newUser(){ 
	            $('#dlg').dialog('open').dialog('setTitle','新建');  
	            $('#fm').form('clear');  
	            url = 'save_user.php'; 
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