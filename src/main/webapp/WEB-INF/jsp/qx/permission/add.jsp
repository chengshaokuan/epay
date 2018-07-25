<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/table.css" rel="stylesheet" type="text/css" />
<link href="dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen" />
<link href="dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen" />

<script type="text/javascript" src="jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="jquery/form/jquery.form.js"></script>

<script type="text/javascript">
	//页面加载完成之后
	$(function(){
		//给保存按钮注册一个单击事件
		$("#saveBtn").click(function(){
			//提交表单
			$("#permissionForm").submit();
		});
		//给表单绑定相关的操作
		$("#permissionForm").ajaxForm({
			//发送请求之前执行的回调函数,该函数有返回值,返回true,请求继续提交;返回false,请求不在提交
			beforeSubmit:function(){
				$("#message").text("正在保存许可请稍后...");
				
				//设置全局ajax同步
				$.ajaxSetup({
					async:false
				});
				
				//获取所有需要校验的文本框,触发失去焦点事件
				$(".requireText").blur();
				
				/*
					jQuery对数组遍历的方式
					返回 'false' 将停止循环 (就像在普通的循环中使用 'break')。返回 'true' 跳至下一个循环(就像在普通的循环中使用'continue')
						方式1:
							数组.each(function(i){
								i代表遍历的索引    this代表遍历的当前对象
							});
						例1:
							$(".requiredSpan").each(function(i){
								alert(i+"::::"+this);
							});
						方式2 :
							$.each(数组,function(i,n){
								i代表遍历的索引   n和this代表遍历的当前对象								
							});
						例2
							$.each($(".requiredSpan"),function(i,n){
								alert(i+"::::"+n+":::"+this);
							});
				
				*/
				
				//定义一个标记位 ,控制发送请求前的这个方法的返回值
				var flag = true;
				//获取所有提示信息的span,并对其进行遍历
				$.each($(".requiredSpan"),function(i,n){
					if(n.innerHTML!=""){
						flag = false;
						return false;//相当于break
					}
				});
				
				if(!flag){
					$("#message").text("数据非法请检查!");
				}
				return flag;
			},
			//服务器处理成功之后,执行的回调函数;该函数会接收服务器响应的数据,具体的数据格式可以取决于response.setContentType(text|html|json|xml...)
			success:function(jsonObject){
				//{"success":true,"data":{"id":"","name":"","pid":""}} 成功		{"success":false} 失败
				if(jsonObject.success){
					$("#message").text("保存成功");
					//获取树对象
					var treeObj = window.parent.treeFrame.$.fn.zTree.getZTreeObj("permission_tree");
					
					var id = jsonObject.data.id;
					var name = jsonObject.data.name;
					var pid = jsonObject.data.pid;
					
					var newNode = {"id":id,"name":name};
					var parentNode = treeObj.getNodeByParam("id",pid) ;
					//在树对象上添加节点
					treeObj.addNodes(parentNode, newNode);
				}else{
					$("#message").text("保存失败");						
				}
				
				//设置全局ajax异步
				$.ajaxSetup({
					async:true
				});
			}
		});
		
		//给许可代码文本框注册失去焦点的事件
		$("#code").blur(function(){
			//获取许可代码
			//var code = document.getElementById("code").value;//基于dom的编程方式
			//var code = $("#code").val();//基于jQuery的编程方式
			var code = this.value;//基于dom的变成方式,this代表当前文本框对象
			//var code = $(this).val();//基于jQuery的编程方式   this是dom元素,但是$(this)是jQuery对象
			//去除前后空白   javascript中的String对象提供了trim函数,但是在早期的IE浏览器中,不支持,存在兼容性问题;一般在去除前后空白的时候,
			//我们使用jquery提供的一个trim函数
			//code = code.trim();
			code = $.trim(code);
			//alert("---"+code+"----");
			//判断是否为空
			//if(code==null){} 这是错误的写法   js中字符串没有null
			//if(code.length==0){}这种方式可以  但是繁琐了
			if(code==""){
				//为空提示用户
				$("#codeRequiredSpan").text("许可代码不能为空");
			}else{
				//不为空 判断是否含有特殊字符 需要使用正则表达式
				/*
					创建正则表达式的两种方式:
						方式1:
							var regExp = new RegExp("pattern");
						方式2:
							var regExp = /pattern/;
					正则表示中有一个test()方法,判断字符串的内容是否符合正则表达式的要求 ,符合返回true,否则返回false
				*/
				var regExp = /^[A-Za-z0-9]+$/;		
				var ok = regExp.test(code);
				if(!ok){
					//含有特殊字符  提示用户
					$("#codeRequiredSpan").text("许可代码只能是字母或者数字");
				}else{
					//不含有特殊字符	发送ajax请求判断是否重复
					$.get("${pageContext.request.contextPath}/permission/getByCode.do",
							{"code":code,"_":new Date().getTime()},function(jsonObject){
								//{"success":true} 不重复  校验通过了		{"success":false} 重复 	校验没通过
								if(jsonObject.success){
									//不重复   校验通过   清空提示信息
									$("#codeRequiredSpan").text("");
								}else{
									//重复   校验没通过 提示用户
									$("#codeRequiredSpan").text("许可代码已经存在");
								}
							});
					
				}
			}
			
		});
		
		//给许可名称的文本框注册失去焦点事件
		$("#name").blur(function(){
			//获取许可名称
			var name = this.value;
			//去除前后空白
			name = $.trim(name);
			//判断是否为空
			if(name==""){
				//为空提示用户
				$("#nameRequiredSpan").text("许可名称不能为空");
			}else{
				//不为空  发送ajax请求 判断是否重复
				$.get("${pageContext.request.contextPath}/permission/getByNameAndPid.do",
						{"name":name,"pid":"${param.id}","_":new Date().getTime()},function(jsonObject){
							//{"success":true} 不重复 校验通过  {"success":false} 重复 校验没通过    
							if(jsonObject.success){
								//不重复  校验通过  清空提示信息
								$("#nameRequiredSpan").text("");
							}else{
								//重复  提示用户
								$("#nameRequiredSpan").text("在该节点下许可名称已经存在");
							}
						});
			}
		});
	});
</script>

<form action="permission/save.do" method="post" id="permissionForm">
<input type="hidden" name="pid" value="${param.id}">
<table border="0" cellpadding="0" cellspacing="0" class="table_border">
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="table_right">
				<tr>
					<td><font style="font-size: 12px;"><strong>新增许可</strong></font>
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



						<table border="0" cellpadding="0" cellspacing="0"
							class="box_table" id="box_table2">
							<thead>
								<tr>
									<td height="36" class="box_table_even">代码：</td>
									<td class="box_table_odd"><input type="text" id="code" name="code" class="requireText"/> 
									<span class="star">*</span>
									<span id="codeRequiredSpan" style="color: red;font-size: 12px" class="requiredSpan"></span>
									</td>
								</tr>
								<tr>
									<td height="36" class="box_table_even">名称：</td>
									<td class="box_table_odd"><input type="text" id="name" name="name" class="requireText"/> 
									<span class="star">*</span>
									<span id="nameRequiredSpan" style="color: red;font-size: 12px" class="requiredSpan"></span>
									</td>
								</tr>
								<tr>
									<td class="box_table_even">模块URL：</td>
									<td class="box_table_odd"><input type="text" id="moduleUrl" name="moduleUrl"/></td>
								</tr>
								<tr>
									<td class="box_table_even">操作URL：</td>
									<td class="box_table_odd"><input type="text" size="90" id="operationUrl" name="operationUrl"/>
										多个逗号隔开</td>
								</tr>
								<tr>
									<td class="box_table_even">排序号：</td>
									<td class="box_table_odd"><input type="text" id="orderNo" name="orderNo"/></td>
								</tr>
							</thead>
							<tbody>
							</tbody>
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
								<!-- javascript:void(0) 保留超链接的样式,执行js代码,但是不跳转到任何资源 -->
								<td width="50px"><a class="button" href="javascript:void(0)"><span id="saveBtn">保存</span></a></td>
								<td width="50px"><a class="button"
									href="javascript:void(window.history.back());"><span>返回</span></a>
								</td>
								<td>
									<span id="message" style="color: red;font-size: 12px"></span>
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