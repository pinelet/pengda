<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
	    <form name="form1" method="post" novalidate>
	        <table id="queryform" border="0" width="100%" cellspacing="0" cellpadding="5" align="center" onresize="resize();">
	        <tr>
	        	<td align="right">卡号:</td>
	            <td align="left"><input id="cardcode" class="easyui-validatebox" type="text"></input></td>
	            <td align="right">卡状态:</td>
	            <td align="left">
	            	<select id="qcardstatus" name="qcardstatus">
	            		<option value="1">正常</option>
	            		<option value="2">挂失</option>
	            	</select>
	            </td>
	            <td><a href="#" onclick="querysubmit();" class="easyui-linkbutton" id="b1">查询</a></td>
	        </tr>
	        </table>
	    </form>
	    <!-- 查询列表 -->
		<table id="carddatalist" class="easyui-datagrid" data-options="title:'查询结果',
																	url:'<s:url value="/user/querycardlist"/>',
																	idField:'gprscode',
														    		striped: true,
														    		toolbar:'#toolbar',
														    		pagination: true,  
														            rownumbers:false,
														            fitColumns:false,
														            singleSelect:true,
														            pageList:[10,20,50],
														            pageSize:15,
														            frozenColumns:[[
														                {field:'ck',checkbox:true}
																	]]"														            
		 >  
	        <thead>  
	            <tr>  
	                <th field="cardcode" width="100">卡号</th>  
	                <th field="cardname" width="100">持有人姓名</th>
	                <th field="phone" width="100">持有人电话</th>  
	                <!-- th field="balance" width="100">卡余额</th -->
	                <th field="cardstatus" width="100" data-options="formatter:p_cardstatus">卡状态</th> 
	                <th field="remark" width="250">备注</th>  
	            </tr>  
	        </thead>  
	    </table>  
	    <div id="toolbar">  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newCard()">新增</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editCard()">修改</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyCard()">删除</a>
	        <!-- a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="devicedec()">详细信息</a -->   
	    </div>    
	    <!-- 弹出的编辑对话框  -->
	    <div id="dlgcard" class="easyui-dialog" data-options="iconCls:'icon-save', border:true, top:100" style="width:600px;height:370px;padding:10px 20px"  
	            closed="true" buttons="#dlg-buttons" modal="true">  
	        <form id="cardfm" method="post" novalidate>
				<input id="pid"  name="pid" type="hidden"/>
	        	<table class="table" style="width:100%"  cellspacing = '0' cellpadding = '2'> 
	        		<tr class="table_head" height="40px"><td  colspan="4">卡信息</td></tr>
		            <tr class="table_odd">  
		                <td align="right" weight='25%'>卡号:</td>  
		               <td align="left"><input name="cardcode" class="easyui-validatebox" required="true"><div id="isrepeat" style="display: none"><font color=red>此卡号重复</font></div></td>  
		               <td align="right"></td>  
		                <td align="left"></td> 
		            </tr>  
		            <tr class="table_even">  
		                <td align="right" weight='25%' NOWRAP>卡持有人姓名:</td>  
		                <td align="left"><input name="cardname" type="text"></td>   
		                <td align="right" weight='25%' NOWRAP>卡持有人电话:</td>  
		                <td align="left"><input name="phone" type="text"></td>  
		            </tr>
		            <tr class="table_odd">  
		                <td align="right">办卡日期:</td>  
		                <td align="left"><input name="applydate" class="easyui-datebox"></td>   
		                <td align="right">卡状态:</td>  
		                <td align="left">
		                	<select name="cardstatus">
		                		<option value="1">正常</option>
		                		<option value="2">挂失</option>
		                		<option value="0">作废</option>
		                	</select>
		                </td>  
		            </tr>
		            <tr class="table_even">  
		                <td align="right" NOWRAP>卡持有人地址:</td>  
		                <td align="left" colspan="3"><input name="address" size="67"></td>    
		            </tr>
		            <tr class="table_odd">  
		                <td align="right">备注:</td>  
		                <td align="left" colspan="3"><input name="remark" size="67"></td>    
		            </tr>
	            </table>
	        </form>  
	    </div>  
	    <div id="dlg-buttons">  
	        <a id="bsave" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveCard()">保存</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgcard').dialog('close')">取消</a>  
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
						var options = $('#carddatalist').datagrid('options');
						$('#carddatalist').datagrid("resize",{width: tablewidth});
					}
				}catch(e){};
			};
			//--------------------------------------------------------------------------------
			function querysubmit() {
				$('#carddatalist').datagrid('clearSelections');
				$('#carddatalist').datagrid('load',{cardcode:$('#cardcode').val(), cardstatus: $("#qcardstatus").val()});
			}
	        var url;  
	        //新增触发
	        function newCard(){ 
	            $('#dlgcard').dialog('open').dialog('setTitle','新建');  
	            $('#cardfm').form('clear');
	            $("#isrepeat").hide();
	            //$('#cardfm #pid').attr('value','<sec:authentication property="principal.username" />');  
	            $('#cardfm input').removeAttr('readonly');
	            $("#cardfm input[name='cardcode']").blur({url:"<s:url value='/user/cardunique'/>", key:""}, isCardUnique);
	            $('#bsave').show();
	            url = "<s:url value='/user/addcard'/>"; 
	        } 
	        //修改触发 
	        function editCard(){
		        //取得选中记录  
	            var row = $('#carddatalist').datagrid('getSelected');  
	            if (row){ 
		            //显示对话框 
	                $('#dlgcard').dialog('open').dialog('setTitle','修改'); 
	                //加载记录数据 
	                $('#cardfm').form('load',row);  
	                //主键不可修改
	                $("#isrepeat").hide();
	                $("#cardfm input[name='cardcode']").attr('readonly',true);
	                $('#bsave').show();
	                url = '<s:url value='/user/updatecard'/>';  
	            }
	            else alert("请选择一条记录.");  
	        }  
	        //保存触发
	        function saveCard(){  
	            $('#cardfm').form('submit',{  
	                url: url,  
	                onSubmit: function(){  
	                    return $(this).form('validate');  
	                },  
	                success: function(data){             
	                	var result = $.parseJSON(data);    
	                        $.messager.alert('结果',result.msg);  		                     
	                        $('#dlgcard').dialog('close');      // close the dialog  
	                        $('#carddatalist').datagrid('reload');    // reload the user data  
	                }  
	            });  
	        }  
	        //删除触发
	        function destroyCard(){  
	            var row = $('#carddatalist').datagrid('getSelected');  
	            if (row){  
	                $.messager.confirm('Confirm','你确定要删除此卡的信息吗?',function(r){  
	                    if (r){  
	                        $.post('<s:url value='/user/delcard'/>',{cardcode:row.cardcode},function(result){  
	                            if (result.msg){  
	                                $('#carddatalist').datagrid('reload');    // reload the user data  
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

            //判断卡是否唯一
            function isCardUnique(e) {
                e.data.key = $("#cardfm input[name='cardcode']").val();
                haveKey(e, function (result) {
                    if(result.msg)$("#isrepeat").show();
                    else $("#isrepeat").hide();
                });
            }
	    </script> 
</body>
</html>