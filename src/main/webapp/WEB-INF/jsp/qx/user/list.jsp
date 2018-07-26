<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/">

<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/table.css" rel="stylesheet" type="text/css" />
<link href="dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen" />
<link href="dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen" />

<link href="jquery/ui/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css" />
<link href="jquery/pagination/pagination.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="jquery/ui/js/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript" src="jquery/ui/js/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="jquery/ui/js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="jquery/pagination/jquery.pagination.js"></script> 
<script type="text/javascript" src="js/jsUtil.js"></script> 
<script type="text/javascript">
	//页面加载完成之后
	$(function(){
		//集成日历插件
		$(".time").datetimepicker({
			changeMonth: true,
			changeYear: true,
			showButtonPanel: true,
			showSecond: true,
			timeFormat: 'hh:mm:ss'
		});
		
		displayData(0);
		$("#pageNo").keydown(function(event){
			if(event.keyCode==13){
				displayData(this.value-1);
			}
		});
		$("#checkOrCancelAll").click(function(){
			$(":checkbox[name='id']").prop("checked",this.checked);
		});
	});
	
	function displayData(pageNo){
		$("#checkOrCancelAll").prop("checked",false);
		var pageSize = $("#pageSize").val();
		var lockStatus = 0;
		var checkedElts = $(":checkbox[name='lockStatus']:checked");
		if(checkedElts.length==1){
			lockStatus = checkedElts.val();
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/user/getByPage.do",
			type:"get",
			cache:false,
			data:{
				"pageNo":pageNo+1,
				"pageSize":pageSize,
				"userName":$("#userName").val(),
				"startTime":$("#startTime").val(),
				"endTime":$("#endTime").val(),
				"lockStatus":lockStatus
			},
			beforeSend:function(){
				$("#message").text("正在进行分页查询请稍后...");
				return true;
			},
			success:function(jsonObject){
				$("#userInfoTBody").empty();
				//{"total":100,"dataList":[{"id":"","accountNo":"","name":"","createTime":"","expireTime":"","allowIps":"","lockStatusText":""},{},{}]}
				if(jsonObject.total==0){
					$("#message").text("没有符合条件的记录");
				}else{
					/*$("#message").text("查询结果如下:");*/
					var htmlString = "";
					$.each(jsonObject.dataList,function(i,n){
						htmlString +='<tr class="odd">';
						htmlString +='<td><input type="checkbox" name="id" value="'+n.id+'" onclick="controlTheadCheckbox()"/></td>';
						htmlString +='<td>'+(i+1)+'</td>';
						htmlString +='<td>'+n.accountNo+'</td>';
						htmlString +='<td>'+n.name+'</td>';
						htmlString +='<td>'+n.createTime+'</td>';
						htmlString +='<td>'+n.expireTime+'</td>';
						htmlString +='<td>'+n.allowIps+'</td>';
						htmlString +='<td>'+n.lockStatusText+'</td>';
						htmlString +='</tr>';
					});
					//将上面拼接好的html字符串追加到tbody中
					$("#userInfoTBody").append(htmlString);
				}
				//集成jquery.pagination插件
				$("#pagination").pagination(jsonObject.total, {//总记录条数
		            callback: displayData,//每次翻页的时候 执行的回调函数	会传递一个参数 	参数代表页码的索引 ,比正常显示的页码小1
		            items_per_page:pageSize,//每页显示多少条数据
		            current_page:pageNo,//当前页码的索引 
		            link_to:"javascript:void(0);",//保留超链接的样式,执行js代码,但是不跳转到任何资源
		            num_display_entries:8,//默认显示的页码入口个数
		            next_text:"下一页",
		            prev_text:"上一页",
		            next_show_always:true,//如果没有下一页  是否显示链接
		            prev_show_always:true,//如果没有上一页  是否显示链接
		            num_edge_entries:2,//如果页码过多,用...省略
		            ellipse_text:"..."
		        });
				//总记录条数
				$("#total").text(jsonObject.total);
				//总页数
				var pageCount = jsonObject.total%pageSize==0?jsonObject.total/pageSize:parseInt(jsonObject.total/pageSize)+1;
				$("#pageCount").text(pageCount);
			}
		});
	}
	
	//控制thead中的checkbox的选中状态
	function controlTheadCheckbox(){
		$("#checkOrCancelAll").prop("checked",$(":checkbox[name='id']").length==$(":checkbox[name='id']:checked").length);
	}

    //删除用户
    function del(){
        //获取要删除的角色
        var checkedElts = $(":checkbox[name='id']:checked");
        if(checkedElts.length==0){
            $("#message").text("请选择要删除的用户");
        }else{
            if(confirm("您确定要删除选中的用户吗?")){
                //发送ajax请求,完成角色的删除    ids=			111&ids=222&ids=333

                /* //拼接要删除的角色的ids的第一种方式
                var sendData = "";
                //对所有选中的checkbox进行遍历
                $.each(checkedElts,function(i,n){
                    sendData +="&ids="+n.value;
                });
                //sendData = sendData.substr(1);
                sendData = sendData.substring(1); */

                //拼接要删除的角色的ids的第二种方式
                var sendData = "ids=";
                var idArrays = [];
                $.each(checkedElts,function(i,n){
                    idArrays.push(n.value);
                });
                sendData += idArrays.join("&ids=");//join("&ids") 用"&ids"连接数组中的元素,形成一个新的字符串
                alert(sendData);

                $.ajax({
                    url:"${pageContext.request.contextPath}/user/delete.do",
                    type:"post",
                    data:sendData,
                    beforeSend:function(){
                        $("#message").text("正在删除用户请稍后...");
                        return true;
                    },
                    success:function(jsonObject){
                        //{"success":true} 成功 {"success":false} 失败
                        if(jsonObject.success){
                            $("#message").text("删除成功");
                            //重新加载页面 	displayData(0);
                            window.location.reload();
                        }else{
                            $("#message").text("删除失败");
                        }
                    }
                });
            }
        }
    }
	
	//跳转到分配角色页面前的校验
	function assign(){
		$_.selectSingleCheckbox("${pageContext.request.contextPath}/user/assign.do");
	}
	
	function detail(){
		$_.selectSingleCheckbox("${pageContext.request.contextPath}/user/detail.do");
	}
</script>


<table width="100%" border="0" cellspacing="0" class="table_padding">
	<tr>
		<td align="center">
			<table border="0" cellpadding="0" cellspacing="0" class="table_border">
				<tr>
					<td align="center">
						<table border="0" cellpadding="0" cellspacing="0" class="box_table" id="box_table">
							<tr>
								<td width="22%">&nbsp;用户姓名：<input type="text" id="userName"/></td>
								<td width="38%">
									失效时间：
										<input type="text" size="20" class="time" id="startTime"/>
										至 
										<input type="text" size="20" class="time" id="endTime"/> 
								</td>
								<td width="25%">锁定状态：
									<input type="checkbox" style="vertical-align: middle;" name="lockStatus" value="2"/> 锁定 
									<input type="checkbox" style="vertical-align: middle;" name="lockStatus" value="1"/> 启用
								</td>
								<td>
									<a class="button" href="javascript:void(0);"><span onclick="displayData(0);">查询</span></a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<table border="0" cellpadding="0" cellspacing="0" class="table_border">
				<tr>
					<td>
						<table style="position : relative;left : 5px; top : 5px;">
							<tr>
								<td width="75px" align="right">
									<a class="button" href="javascript:void(0);"><span onclick="detail();">查看明细</span></a>
								</td>
								<td width="50px">
									<a class="button" href="user/add.do"><span>新增</span></a>
								</td>
								<td width="50px">
									<a class="button" href="javascript:void(0);"><span onclick="del();">删除</span></a>
								</td>
								<td width="50px">
									<a class="button" href="user/edit.do"><span>修改</span></a>
								</td>
								<td width="50px">
									<a class="button"><span>启用</span></a>
								</td>
								<td width="50px">
									<a class="button"><span>锁定</span></a>
								</td>
								<td width="70px">
									<a class="button" href="javascript:void(0);"><span onclick="assign();">分配角色</span></a>
								</td>
								<td>
									<span id="message" style="color: fuchsia;font-size: 14px"></span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td height=110>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td align="center">
									<table border="0" cellpadding="0" cellspacing="0"
										id="box_num_table2" class="box_num_table">
										<thead>
											<tr>
												<td width="8%"><input type="checkbox" id="checkOrCancelAll"/></td>
												<td width="7%">序号</td>
												<td width="12%">登录帐号</td>
												<td width="11%">用户姓名</td>
												<td width="12%">创建时间</td>
												<td width="13%">失效时间</td>
												<td width="11%">允许访问IP</td>
												<td width="11%">状态</td>
											</tr>
										</thead>
										<tbody id="userInfoTBody">
										</tbody>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<table border="0" cellpadding="0" cellspacing="0" class="table_border">
                <tr>
                    <td>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0"
                               class="table_padding">
                            <tr>
                                <td width="8%" class="font_left">数据:<span id="total"></span>条</td>
                                <td width="12" class="font_left">第</td>
                                <td class="font_left">
                                    <input id="pageNo" type="text" size="2" maxlength="4" /> /<span id="pageCount"></span> 页
                                </td>
                                <td class="font_left">
                                	<select onchange="displayData(0);" id="pageSize">
                                		<option value="2">每页2条</option>
                                		<option value="3">每页3条</option>
                                		<option value="4">每页4条</option>
                                		<option value="5">每页5条</option>
                                	</select>
                                </td>
                                <td class="font_left">
                                    <div id="pagination"></div>
                                </td>
                                <td class="font_left">
                                    <a href="javascript:void(0);" onclick="displayData(0);">刷新</a>                                
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
		</td>
	</tr>
</table>
