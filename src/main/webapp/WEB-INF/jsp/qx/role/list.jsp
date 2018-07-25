<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/">

<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/table.css" rel="stylesheet" type="text/css" /> 
<link href="dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen" />
<link href="dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen" />

<link href="jquery/pagination/pagination.css" rel="stylesheet" type="text/css" /> 

<script type="text/javascript" src="jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="jquery/pagination/jquery.pagination.js"></script> 
<script type="text/javascript" src="js/jsUtil.js"></script> 

<script type="text/javascript">
	//页面加载完成之后
	$(function(){
		displayData(0);
		//给跳转到第几页的文本框注册一个keydown事件   event代表刚刚发生的事件
		$("#pageNo").keydown(function(event){
			//alert(event.keyCode);
			if(event.keyCode==13){
				displayData(this.value-1);
			}
		});
		//给thead中的checkbox注册一个单击事件
		$("#checkOrCancelAll").click(function(){
			//alert(this.checked);
			//获取所有checkbox
			//var allCheckbox = $(":checkbox");
			//获取name='id'的checkbox
			//var allCheckbox = $(":checkbox[name='id']");
			//alert(allCheckbox.length);
			/* if(this.checked){
				$(":checkbox[name='id']").prop("checked",true);
			}else{				
				$(":checkbox[name='id']").prop("checked",false);
			} */
			$(":checkbox[name='id']").prop("checked",this.checked);
		});
	});
	//发送ajax请求获取分页数据这个操作会被调用多次,所以我们把它封装为一个单独的函数
	function displayData(pageNo){
		//将thead中的checkbox设置为未勾选状态
		$("#checkOrCancelAll").prop("checked",false);
		
		var pageSize = $("#pageSize").val();
		$.ajax({
			url:"${pageContext.request.contextPath}/role/getByPage.do",
			type:"get",
			cache:false,//表示get请求时,浏览器不缓存页面,另一种方式是加时间戳
			data:{
				"pageNo":pageNo+1,
				"pageSize":pageSize
			},
			beforeSend:function(){
				$("#message").text("正在进行分页查询请稍后...");
				return true;
			},
			success:function(jsonObject){
				//清空tbody
				$("#roleInfoTBody").empty();
				//{"total":100,"dataList":[{"id":"","code":"","name":"","remark":""},{},{}]} 
				if(jsonObject.total==0){
					$("#message").text("没有符合条件的记录");					
				}else{
					$("#message").text("查询结果如下:");
					//定义一个字符串类型变量
					var htmlString = "";
					//对返回的json数组进行遍历
					$.each(jsonObject.dataList,function(i,n){
						htmlString += '<tr bgcolor="white">';
					    htmlString += '<td><input type="checkbox" name="id" value="'+n.id+'" onclick="controlTheadCheckbox()"/></td>';
					    htmlString += '<td>'+(i+1)+'</td>';
					    htmlString += '<td>'+n.code+'</td>';
					    htmlString += '<td>'+n.name+'</td>';
					    htmlString += '<td>&nbsp;'+n.remark+'</td>';
						htmlString += '</tr>';
					});
					//将上面拼接好的html字符串追加到tbody中
					$("#roleInfoTBody").append(htmlString);
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
		/* //获取页面中tbody中显示记录条数
		var showSize = $(":checkbox[name='id']").length;
		//获取页面中tbody中选中的记录条数
		var checkedSize = $(":checkbox[name='id']:checked").length;
		if(showSize==checkedSize){
			$("#checkOrCancelAll").prop("checked",true);
		}else{
			$("#checkOrCancelAll").prop("checked",false);			
		} */
		$("#checkOrCancelAll").prop("checked",$(":checkbox[name='id']").length==$(":checkbox[name='id']:checked").length);
	}
	
	//删除角色
	function del(){
		//获取要删除的角色
		var checkedElts = $(":checkbox[name='id']:checked");
		if(checkedElts.length==0){
			$("#message").text("请选择要删除的角色");
		}else{
			if(confirm("您确定要删除选中的角色吗?")){
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
				//alert(sendData);
				
				$.ajax({
					url:"${pageContext.request.contextPath}/role/delete.do",
					type:"post",
					data:sendData,
					beforeSend:function(){
						$("#message").text("正在删除角色请稍后...");
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
	
	//跳转到修改页面
	function edit(){
		$_.selectSingleCheckbox("${pageContext.request.contextPath}/role/edit.do");
	}
	
	//给角色分配许可
	function assign(){
		$_.selectSingleCheckbox("${pageContext.request.contextPath}/role/assign.do");
	}
	
	//查看角色明细
	function detail(){
		$_.selectSingleCheckbox("${pageContext.request.contextPath}/role/detail.do");
	}
</script>
<table border="0" cellpadding="0" cellspacing="0" class="table_border">
    <tr>
        <td>
            <table width="100%" border="0" cellpadding="0" cellspacing="0"
                   class="table_padding">
                <tr>
                    <td>
                        <table align="left">
                            <tr>
                                <td width="75px"><a class="button" href="javascript:void(0);"><span onclick="detail();">查看明细</span></a>
                                </td>
                                <td width="50px"><a class="button" href="role/add.do"><span>新增</span></a>
                                </td>
                                <td width="50px"><a class="button" href="javascript:void(0);"><span onclick="del();">删除</span></a></td>
                                <td width="50px"><a class="button" href="javascript:void(0);"><span onclick="edit();">修改</span></a>
                                </td>
                                <td width="80px"><a class="button" href="javascript:void(0);"><span onclick="assign();">分配许可</span></a>
                                </td>
                                <td>
                                	<span id="message" style="color: red;font-size: 12px"></span>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td width="25" height="26" align="right"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="center">
            <table border="0" cellpadding="0" cellspacing="0" id="box_num_table2"
                   class="box_num_table">
                <thead>
	                <tr  bgcolor="white">
	                    <td><input type="checkbox" id="checkOrCancelAll"/></td>
	                    <td>序号</td>
	                    <td>代码</td>
	                    <td>名称</td>
	                    <td>描述</td>
	                </tr>
                </thead>
                <tbody id="roleInfoTBody">
                </tbody>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table border="0" cellpadding="0" cellspacing="0"
                   class="table_border">
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
