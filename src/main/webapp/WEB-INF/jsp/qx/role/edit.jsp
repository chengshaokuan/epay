<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/">

<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/table.css" rel="stylesheet" type="text/css" />
<link href="dwz/themes/default/style.css" rel="stylesheet"
	type="text/css" media="screen" />
<link href="dwz/themes/css/core.css" rel="stylesheet" type="text/css"
	media="screen" />
<script type="text/javascript" src="jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="jquery/form/jquery.form.js"></script>
<script type="text/javascript">
	$(function(){
		//给更新按钮注册一个单击事件
		$("#saveBtn").click(function(){			
			$("#roleForm").submit();
		});
		//给表单绑定相关的回调操作
		$("#roleForm").ajaxForm({
			//发送请求之前执行的函数
			beforeSubmit:function(){
				$("#message").text("正在更新请稍后...");
				return true;
			},
			//执行成功的函数
			success:function(jsonObject){
				//{"success":true} 成功  {"success":false} 失败
				if(jsonObject.success){
					$("#message").text("更新成功");
				}else{
					$("#message").text("更新失败");
				}
			}
		});
	});
</script>
<form action="role/update.do" id="roleForm" method="post">
<input type="hidden" name="id" value="${role.id}">
<table border="0" cellpadding="0" cellspacing="0" class="table_border">
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="table_right">
				<tr>
					<td><font style="font-size: 12px;"><strong>修改角色</strong></font>
					</td>
					<td width="25" height="26" align="left"></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table width="100%" border="0" cellspacing="0" class="table_right">
				<tr>
					<td align="center">



						<table width="100%" border="0" cellspacing="0"
							class="table_padding">
							<tr>
								<td align="center">
									<table border="0" cellpadding="0" cellspacing="0"
										class="box_table" id="box_table2">
										<thead>
											<tr>
												<td class="box_table_even"><span class="in">代码：</span></td>
												<td class="box_table_odd"><span class="in"> <input
														type="text" value="${role.code}" name="code" /> <span class="star">*</span>
												</span></td>
											</tr>
											<tr>
												<td class="box_table_even"><span class="in">名称：</span></td>
												<td class="box_table_odd"><span class="in"> <input
														type="text" value="${role.name }" name="name"/> <span class="star">*</span>
												</span></td>
											</tr>
											<tr>
												<td class="box_table_even">描述：</td>
												<td class="box_table_odd"><textarea style="width: 95%"
														cols="100" rows="5" name="remark">${role.remark}</textarea></td>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</td>
							</tr>
						</table>

						<table border="0" cellpadding="0" cellspacing="0"
							class="operation">
							<thead>
								<tr>
									<td height="24">&nbsp;</td>
								</tr>
							</thead>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="table_padding">
				<tr>
					<td height="21" align="right">
						<table align="left">
							<tr>
								<td width="50px"><a class="button" href="javascript:void(0)" id="saveBtn"><span>更新</span></a></td>
								<td width="50px"><a class="button"
									href="javascript:void(window.history.back());"><span>返回</span></a></td>
								<td>
									<span id="message"></span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
