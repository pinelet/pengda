<!DOCTYPE html>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>

<body>
		<!-- 查询条件栏   -->
	    <form name="form1" method="post" novalidate>
	        <table id="queryform" border="0" width="100%" cellspacing="0" cellpadding="5" align="center" onresize="resize();">
	        <tr>
	        	<td align="right">设备编号:</td>
	            <td align="left"><input class="easyui-validatebox" type="text" name="devicecode"></input></td>
	            <td align="right">设备地址:</td>
	            <td align="left"><input class="easyui-validatebox" type="text" name="address"/></td>
	            <td><a href="#" onclick="querysubmit();" class="easyui-linkbutton" id="b1">查询</a></td>
	        </tr>
	        </table>
	    </form>
	    <!-- 查询列表 -->
		<table id="devicedatalist" class="easyui-datagrid" data-options="title:'查询结果',
																	url:'<s:url value="/user/queryDevicelist"/>',
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
																	]]"														            
		 >  
	        <thead>  
	            <tr>  
	                <th field="devicecode" width="100">设备编号</th>  
	                <th field="gprscode" width="100">GPRS号</th>
	                <th field="phonecode" width="100">接入手机号</th>  
	                <th field="model" width="100">设备型号</th>
	                <th field="contactcall" width="100">联系电话</th>
	                <th field="devicestatus" width="80" data-options="formatter:p_devicestatus">设备状态</th> 
	                <th field="status" width="80" data-options="formatter:p_status">服务状态</th> 
	                <th field="consumeStatus" width="80"  data-options="formatter:formmterstatus">滤芯状态</th> 
	                <th field="remark" width="250">备注</th>  
	            </tr>  
	        </thead>  
	    </table>  
	    <div id="toolbar">  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新增</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="devicedec()">详细信息</a>   
	    </div>    
	    <!-- 弹出的编辑对话框  -->
	    <div id="dlgdevice" class="easyui-dialog" data-options="iconCls:'icon-save', border:true, top:100" style="width:600px;height:370px;padding:10px 20px"  
	            closed="true" buttons="#dlg-buttons" modal="true">  
	        <form id="devicefm" method="post" novalidate>
				<input id="pid"  name="pid" type="hidden" value="<sec:authentication property='principal.username' />"/>
	        	<table class="table" style="width:100%"  cellspacing = '0' cellpadding = '2'> 
	        		<tr class="table_head" height="40px"><td  colspan="4">设备信息</td></tr>
		            <tr class="table_odd">  
		                <td align="right">设备编号:</td>  
		               <td align="left"><input name="devicecode" class="easyui-validatebox" required="true"></td>  
		               <td align="right">GPRS号:</td>  
		                <td align="left"><input name="gprscode" class="easyui-validatebox" required="true"><div id="isrepeat" style="display: none"><font color=red>此号码重复</font></div></td> 
		            </tr>  
		            <tr class="table_even">  
		                <td align="right">接入手机号:</td>  
		                <td align="left"><input name="phonecode" class="easyui-validatebox" required="true"></td>   
		                <td align="right">设备型号:</td>  
		                <td align="left"><input name="model"></td>  
		            </tr>
		            <tr class="table_odd">  
		                <td align="right">联系电话:</td>  
		                <td align="left"><input name="contactcall"></td>   
		                <td align="right">设备地址:</td>  
		                <td align="left"><input name="address"></td>  
		            </tr>
		            <tr class="table_even">  
		                <td align="right">备注:</td>  
		                <td align="left" colspan="3"><input name="remark" size="80"></td>    
		            </tr>
		            <tr class="table_head" height="40px"><td  colspan="4">耗材信息</td></td>   
		            </tr>
		            <tr class="table_odd">  
		                <td align="right">耗材PP棉5u:</td>  
		                <td align="left"><input name="ppdate" class="easyui-datebox"></td>   
		                <td align="right">耗材活性碳:</td>  
		                <td align="left"><input name="carbondate" class="easyui-datebox"></td>  
		            </tr>
		            <tr class="table_even">  
		                <td align="right">耗材RO膜:</td>  
		                <td align="left"><input name="rodate" class="easyui-datebox"></td>   
		                <td align="right">耗材矿化:</td>  
		                <td align="left"><input name="minedate" class="easyui-datebox"></td>  
		            </tr>
	            </table>
	        </form>  
	    </div>  
	    <div id="dlg-buttons">  
	        <a id="bsave" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgdevice').dialog('close')">取消</a>  
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
	            $('#dlgdevice').dialog('open').dialog('setTitle','新建');  
	            $('#devicefm').form('clear');
	            $("#isrepeat").hide();
	            //$('#devicefm #pid').attr('value','<sec:authentication property="principal.username" />');  
	            $('#devicefm input').removeAttr('readonly');
	            $("#devicefm input[name='gprscode']").blur({url:"<s:url value='/user/unique'/>", key:""}, isGprsUnique);
	            $('#bsave').show();
	            url = "<s:url value='/user/addevice'/>"; 
	        }  
	        function editUser(){
		        //取得选中记录  
	            var row = $('#devicedatalist').datagrid('getSelected');  
	            if (row){ 
		            //显示对话框 
	                $('#dlgdevice').dialog('open').dialog('setTitle','修改'); 
	                //加载记录数据 
	                $('#devicefm').form('load',row);  
	                //主键不可修改
	                $('#devicefm input').removeAttr('readonly');
	                $("#isrepeat").hide();
	                $("#devicefm input[name='gprscode']").attr('readonly',true);
	                //$("#devicefm input[name='phonecode']").attr('readonly',true);
	                $('#bsave').show();
	                url = '<s:url value='/user/updatedevice'/>';  
	            }
	            else alert("请选择一条记录.");  
	        }  
	        function saveUser(){  
	            $('#devicefm').form('submit',{  
	                url: url,  
	                onSubmit: function(){  
	                    return $(this).form('validate');  
	                },  
	                success: function(data){             
	                	var result = $.parseJSON(data);    
	                        $.messager.alert('结果',result.msg);  		                     
	                        $('#dlgdevice').dialog('close');      // close the dialog  
	                        $('#devicedatalist').datagrid('reload');    // reload the user data  
	                }  
	            });  
	        }  
	        function destroyUser(){  
	            var row = $('#devicedatalist').datagrid('getSelected');  
	            if (row){  
	                $.messager.confirm('Confirm','你确定要删除此设备吗?',function(r){  
	                    if (r){  
	                        $.post('<s:url value='/user/deldevice'/>',{gprscode:row.gprscode},function(result){  
	                            if (result.msg){  
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

	  		var formmterstatus = function(value,row,index){
            	var defaultconsume = <s:property value="#session.defaultconsume"/>;
            	var pp = compareDate(row.ppdate, defaultconsume);
            	var carbon = compareDate(row.carbondate, defaultconsume);
            	var ro = compareDate(row.rodate, defaultconsume);
            	var mine = compareDate(row.minedate, defaultconsume);
            	if (pp == 2 || ro == 2 || carbon == 2 || mine == 2) return '<a  href="#" onclick="">已失效</a>';
            	else if( pp == 1 || ro == 1 || carbon == 1 || mine == 1) return '<a href="#" onclick="">报警</a>';
            	else return '<a href="#" onclick="">正常</a>';
            }; 

            function devicedec() {
            	var row = $('#devicedatalist').datagrid('getSelected');
	            if (row){ 
		            //显示对话框 
	                $('#dlgdevice').dialog('open').dialog('setTitle','详细信息'); 
	                //加载记录数据 
	                $('#devicefm').form('load',row);  
	                //不可修改
	                $('#devicefm input').attr('readonly',true);
	                $("#isrepeat").hide();
	                $('#bsave').hide();
	                url = '<s:url value='/user/updatedevice'/>';  
	            }
	            else alert("请选择一条记录."); 
            }

            function isGprsUnique(e) {
                e.data.key = $("#devicefm input[name='gprscode']").val();
                haveKey(e, function (result) {
                    if(result.msg)$("#isrepeat").show();
                    else $("#isrepeat").hide();
                });
            }
	    </script> 
</body>
</html>