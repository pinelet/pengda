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
		<table id="devcondatalist" class="easyui-datagrid" data-options="title:'查询结果',
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
	                <th field="remark" width="250">备注</th>  
	            </tr>  
	        </thead>  
	    </table>  
	    <div id="toolbar">  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="devicecommand()">命令控制</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="devicerundec()">运行信息</a>   
	    </div> 
	    	    <!-- 弹出的编辑对话框  -->
	    <div id="dlgcontrol" class="easyui-dialog" data-options="iconCls:'icon-save', border:true, top:40" style="width:650px;height:500px;padding:10px 20px"  
	            closed="true" buttons="#dlg-buttons" modal="true">  
	        <form id="fmcontrol" method="post">
				<input id="pid"  name="pid" type="hidden" value="<sec:authentication property='principal.username' />"/>
	        	<table class="table" style="width:100%"  cellspacing = '0' cellpadding = '2'> 
	        		<tr class="table_head" height="40px"><td  colspan="6">设备运行控制命令集</td></tr>
		            <tr class="table_odd">  
		                <td align="right" colspan="1">设备编号:</td>  
		               <td align="left" colspan="2"><input name="devicecode" class="easyui-validatebox" required="true" readonly></td>  
		               <td align="right" colspan="1">GPRS号:</td>  
		                <td align="left" colspan="2"><input name="gprscode" class="easyui-validatebox" required="true" readonly size=40></td> 
		            </tr>  
		            <tr class="table_even">  
		            	<td align="center"><input type="checkbox" contr="mn"></td>  
		                <td align="right" nowrap>机器ID:</td>  
		                <td align="left"><input id="mn" name="mn" class="easyui-numberbox" data-options="min:1,max:9999,disabled:true"/></td>  
		                <td align="center"><input type="checkbox" contr="mm"></td>   
		                <td align="right" nowrap>刷卡模式:</td>  
		                <td align="left">
		                	<select name="mm" disabled="disabled">
		                		<option value="1">刷一次卡</option>
		                		<option value="2">刷两次卡</option>
		                	</select></td>  
		            </tr>  
		            <tr class="table_even">
		                <td align="center"><input type="checkbox" contr="ll"></td>  
		                <td align="right" nowrap>流量计使能:</td>  
		                <td align="left">
		                	<select name="ll" disabled="disabled">
		                		<option value="1">不使能</option>
		                		<option value="2">使能</option>
		                	</select></td>   
		                <td align="center"><input type="checkbox" contr="gu"></td>  
		                <td align="right" nowrap>gprs模块使能:</td>  
		                <td align="left">
		                	<select name="gu" disabled="disabled">
		                		<option value="1" >不使能</option>
		                		<option value="2">使能</option>
		                	</select></td>  
		            </tr>
		            <tr class="table_odd">
		            	<td align="center"><input type="checkbox" contr="c1"></td>    
		                <td align="right" nowrap>密钥1:</td>  
		                <td align="left"><input id="c1" name="c1" class="easyui-numberbox" disabled="disabled" min="1" max="9999"></td>
		                <td align="center"><input type="checkbox" contr="c2"></td>     
		                <td align="right">密钥2:</td>  
		                <td align="left"><input id="c2" name="c2" class="easyui-numberbox" disabled="disabled" min="1" max="9999"></td>
		            </tr>  
		            <tr class="table_even">
		                <td align="center"><input type="checkbox" contr="b"></td>     
		                <td align="right" nowrap>现金消费总计:</td>  
		                <td align="left"><input id="b" name="b" class="easyui-numberbox" disabled="disabled" min="0" max="999999"></td>
		                <td align="center"><input type="checkbox" contr="c"></td>     
		                <td align="right" nowrap>用卡消费总计:</td>  
		                <td align="left"><input id="c" name="c" class="easyui-numberbox" disabled="disabled" min="0" max="999999"></td>  
		            </tr>
		            <tr class="table_odd">
		            	<td align="center"><input type="checkbox" contr="y"></td>    
		                <td align="right" nowrap>一卡通消费总计:</td>  
		                <td align="left"><input id="y" name="y" class="easyui-numberbox" disabled="disabled" min="0" max="999999"></td>
		                <td align="center"><input type="checkbox" contr="wh"></td>     
		                <td align="right">冲洗时间:</td>  
		                <td align="left"><input id="wh" name="wh" class="easyui-numberbox" disabled="disabled" min="0" max="120"></td>
		            </tr>  
		            <tr class="table_even">
		                <td align="center"><input type="checkbox" contr="op"></td>    
		                <td align="right">臭氧周期:</td>  
		                <td align="left"><input id="op" name="op" class="easyui-numberbox" disabled="disabled" min="0" max="23"></td>
		                <td align="center"><input type="checkbox" contr="ot"></td>     
		                <td align="right">臭氧时间:</td>  
		                <td align="left"><input id="ot" name="ot" class="easyui-numberbox" disabled="disabled" min="0" max="600"></td>  
		            </tr>
		            <tr class="table_odd">
		            	<td align="center"><input type="checkbox" contr="ov"></td>    
		                <td align="right">出水速度:</td>  
		                <td align="left"><input id="ov" name="ov" class="easyui-numberbox" disabled="disabled" min="1" max="100"></td>
		                <td align="center"><input type="checkbox" contr="cp"></td>     
		                <td align="right">冷水价格:</td>  
		                <td align="left"><input id="cp" name="cp" class="easyui-numberbox" disabled="true" min="500" max="9999"></td>
		            </tr>  
		            <tr class="table_even">
		                <td align="center"><input type="checkbox" contr="hp"></td>     
		                <td align="right">热水价格:</td>  
		                <td align="left"><input id="hp" name="hp" class="easyui-numberbox" disabled="true" min="500" max="9999"></td>
		                <td align="center"><input type="checkbox" contr="yp"></td>     
		                <td align="right" nowrap>一卡通单价:</td>  
		                <td align="left"><input id="yp" name="yp" class="easyui-numberbox" disabled="true" min="10" max="500"></td>  
		            </tr>
		            <tr class="table_odd">
		            	<td align="center"><input type="checkbox" contr="cm"></td>    
		                <td align="right" nowrap>用卡最大消费:</td>  
		                <td align="left"><input id="cm" name="cm" class="easyui-numberbox" disabled="true" min="100" max="500"></td>
		                <td align="center"><input type="checkbox" contr="pt"></td>     
		                <td align="right">开灯时间:</td>  
		                <td align="left"><input id="pt" name="pt" class="easyui-numberbox" disabled="true" min="0" max="9999"></td>
		            </tr>  
		            <tr class="table_even">
		                <td align="center"><input type="checkbox" contr="ct"></td>    
		                <td align="right">关灯时间:</td>  
		                <td align="left"><input id="ct" name="ct" class="easyui-numberbox" disabled="true" min="0" max="9999"></td>
		                <td align="center"><input type="checkbox" contr="lo"></td>     
		                <td align="right">循环水开时间:</td>  
		                <td align="left"><input id="lo" name="lo" class="easyui-numberbox" disabled="true" min="1" max="9999"></td>  
		            </tr>
		            <tr class="table_odd">
		            	<td align="center"><input type="checkbox" contr="lc"></td>    
		                <td align="right" nowrap>循环水关时间:</td>  
		                <td align="left"><input id="lc" name="lc" class="easyui-numberbox" disabled="true" min="1" max="9999"></td>
		                <td align="center"><input type="checkbox" contr="fu"></td>     
		                <td align="right" nowrap>存储功能使能:</td>  
		                <td align="left">
		                	<select name="fu" disabled="disabled">
		                		<option value="1">不使能</option>
		                		<option value="2">使能</option>
		                	</select></td>   
		            </tr>  
		            <tr class="table_even">
		                <td align="center"><input type="checkbox" contr="yu"></td>  
		                <td align="right" nowrap>一卡通使能:</td>  
		                <td align="left">
		                	<select name="yu" disabled="disabled">
		                		<option value="1">不使能</option>
		                		<option value="2">使能</option>
		                	</select></td>
		                <td align="center"><input type="checkbox" contr="tl"></td>     
		                <td align="right">电话号码:</td>  
		                <td align="left"><input id="tl" name="tl" class="easyui-numberbox" disabled="true" min="0" max="99999999999" ></td>  
		            </tr>
	            </table>
	        </form>  
	    </div>  
	    <div id="dlg-buttons">  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveCommand()">保存&发送</a>  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgcontrol').dialog('close')">取消</a>  
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
						var options = $('#devcondatalist').datagrid('options');
						$('#devcondatalist').datagrid("resize",{width: tablewidth});
					}
				}catch(e){};
			};
			//--------------------------------------------------------------------------------
			function querysubmit() {
				alert($('#pid').combobox('getValue'));
				$('#devcondatalist').datagrid('load',{pid:$('#pid').combobox('getValue')});
			}
	        var url;
	        
	        //发起设备命令，显示命令页面  
	        function devicecommand(){  
		        //取得选中记录  
	            var row = $('#devcondatalist').datagrid('getSelected');  
	            if (row){ 
		            //显示对话框 
	                $('#dlgcontrol').dialog('open').dialog('setTitle','发起控制命令'); 
	                //加载记录数据(GPRS号) 
	                $('#fmcontrol').form('clear').form('load',row);  
	                url = '<s:url value='/user/savecommand'/>';  
	            }
	            else alert("请选择一条记录.");    
	        } 
	        function saveCommand(){  
	            $('#fmcontrol').form('submit',{  
	                url: url,  
	                onSubmit: function(){  
	                    return $(this).form('validate');  
	                },  
	                success: function(data){             
	                	var result = $.parseJSON(data);    
	                        $.messager.alert('结果',result.msg);  		                     
	                        $('#dlgcontrol').dialog('close');      // close the dialog  
	                        $('#devcondatalist').datagrid('reload');    // reload the user data  
	                }  
	            });  
	        }
	        //显示设备运行信息&监测信息
            function devicerundec() {
            	var row = $('#devcondatalist').datagrid('getSelected');
	            if (row){ 
		            //显示对话框 
	                $('#dlgcontrol').dialog('open').dialog('setTitle','详细信息'); 
	                //加载记录数据 
	                $('#fmcontrol').form('load',row);  
	                //不可修改
	                $('#fmcontrol input').attr('readonly',true);
	                $("#isrepeat").hide();
	                $('#bsave').hide();
	                url = '<s:url value='/user/updatedevice'/>';  
	            }
	            else alert("请选择一条记录."); 
            }

            $("input[type='checkbox']").click(function () {
                id = $(this).attr("contr");
                if($(this).is(':checked')) {
                //if(obj.checked) {
                    //$("input[name='" + id + "']").numberbox('enable');
                	//$("input[name=" + id + "]").prop("disabled", false);
                    $("#" + id).numberbox('enable');
                    $("select[name=" + id + "]").prop("disabled", false);
                }
                else {
                	//$("input[name='" + id + "']").numberbox('disable');
                	//$("input[name=" + id + "]").prop("disabled", true);
                	$("#" + id).numberbox('disable');
                	$("select[name=" + id + "]").prop("disabled", true);
                }
            } );
	    </script> 
</body>
</html>