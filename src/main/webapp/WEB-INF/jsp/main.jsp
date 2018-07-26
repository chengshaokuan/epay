<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/">

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>互联网支付-后台管理</title>

<script src="jquery/jquery-1.7.2.min.js" type="text/javascript"></script>

<link href="dwz/themes/azure/style.css" rel="stylesheet" type="text/css" media="screen" />
<link href="dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen" />
<link href="dwz/themes/css/print.css" rel="stylesheet" type="text/css" media="print" />
<link href="dwz/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen" />
<script src="dwz/bin/dwz.min.js" type="text/javascript"></script>
<script src="dwz/js/dwz.regional.zh.js" type="text/javascript"></script>

<link rel="stylesheet" href="jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="jquery/ztree/js/jquery.ztree.all-3.5.min.js"></script>

<script type="text/javascript">
	$(function() {
		DWZ.init("dwz/dwz.frag.xml", {
			loginUrl : "login_dialog.html",
			loginTitle : "登录", // 弹出登录对话框
			statusCode : {
				ok : 200,
				error : 300,
				timeout : 301
			}, //【可选】
			pageInfo : {
				pageNum : "pageNum",
				numPerPage : "numPerPage",
				orderField : "orderField",
				orderDirection : "orderDirection"
			}, //【可选】
			keys : {
				statusCode : "statusCode",
				message : "message"
			}, //【可选】
			ui : {
				hideMode : 'offsets'
			}, //【可选】hideMode:navTab组件切换的隐藏方式，支持的值有’display’，’offsets’负数偏移位置的值，默认值为’display’
			debug : false, // 调试模式 【true|false】
			callback : function() {
				initEnv();
				$("#themeList").theme({
					themeBase : "themes"
				}); // themeBase 相对于index页面的主题base路径
			}
		});
	});
</script>
</head>

<body>
	<jsp:useBean id="time" type="java.io.Serializable" beanName="java.util.Date"/>
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" src="images/csk.jpg">标志</a>
				<ul class="nav">
					<li><a href="javascript:void(0)">用户信息：${session_user.name}</a></li>
					<li><a href="javascript:void(0)">当前时间：${time}</a></li>
					<li><a href="javascript:void(0)">登录次数：888</a></li>
					<li><a href="main/changepwd.do" target="dialog" width="600">修改密码</a></li>
					<li><a href="login.html">退出登录</a></li>
				</ul>
			</div>
		</div>
		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse">
						<div></div>
					</div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse">
					<h2>互联网支付管理</h2>
					<div>收缩</div>
				</div>
				<div class="accordion" fillSpace="sidebar">
					<!-- 操作员管理 -->
					<div class="accordionHeader"><h2>&nbsp;&nbsp;操作员管理</h2></div>
					<div class="accordionContent">
						<script type="text/javascript">
							var setting = {
								data: {
						   			//使用简单的json格式拼接树节点数据
						   			simpleData: {
						   				enable: true
						   			}
						   		},
						   		view: {
						   			//禁止树节点多选
						   			selectedMulti: false
						   		},
						   		async: {
						   			//开启异步加载模式
						   			enable: true,
						   			url: "${pageContext.request.contextPath}/permission/makeMenuTree.do?userId=${session_user.id}"
						   		}
							};
                            var zNodes1 =[
                                {id : "11",name:"权限管理", open:true},
                                {id : "111",name : "许可维护",pId : "11",url : "${pageContext.request.contextPath}/permission/index.do",target : "navTab"},
                                {id : "112",name : "角色维护",pId : "11",url : "${pageContext.request.contextPath}/role/index.do",target : "navTab"},
                                {id : "113",name : "用户维护",pId : "11",url : "${pageContext.request.contextPath}/user/index.do",target : "navTab"},
                                {id : "13",name : "操作记录管理",open:true},
                                {id : "131",name : "操作记录查询",pId : "13",url : "${pageContext.request.contextPath}/log/index.do",target : "navTab"},
                                {id : "132",name : "登录日志管理",pId : "13",url : "${pageContext.request.contextPath}/log/delete.do",target : "navTab"},
                                {id : "133",name : "操作审计配置",pId : "13",open:true},
                                {id : "1331",name : "详情",pId : "133"},
                                {id : "1332",name : "修改",pId : "133"},
                                {id : "1333",name : "重置",pId : "133"}
                            ];
							$(document).ready(function(){
								$.fn.zTree.init($("#treeDemo1"), setting, zNodes1);
							});
						</script>
						<div id="treeDemo1" class="ztree"></div>
					</div>

					<!-- 运营管理 -->
					<div class="accordionHeader"><h2>&nbsp;&nbsp;运营管理</h2></div>
					<div class="accordionContent">
						<script type="text/javascript">
                            var zNodes2 =[
                                {id : "21",name:"客户管理",open:true},
                                {id : "211",name:"用户管理",pId:"21",open:true},
                                {id : "2111",name:"用户信息维护",pId:"211"},
                                {id : "2112",name:"用户认证审核",pId:"211"},
                                {id : "2113",name:"用户账户维护",pId:"211"},
                                {id : "212",name:"商户管理",pId:"21",open:true},
                                {id : "2121",name:"商户信息维护",pId:"212"},
                                {id : "2122",name:"商户开户",pId:"212"},
                                {id : "2123",name:"商户认证审核",pId:"212"},
                                {id : "2124",name:"商户账户维护",pId:"212"},
                                {id : "2125",name:"商户充值记录查询",pId:"212"},
                                {id : "2126",name:"商户提现记录查询",pId:"212"},
                                {id : "2127",name:"商户签约维护",pId:"212"},
                                {id : "22",name:"订单管理",open:true},
                                {id : "221",name:"商品订单查询",pId:"22"},
                                {id : "222",name:"充值订单查询",pId:"22"},
                                {id : "223",name:"提现订单查询",pId:"22"},
                                {id : "224",name:"退款订单查询",pId:"22"},
                                {id : "225",name:"转账订单查询",pId:"22"},
                                {id : "23",name:"银行管理",open:true},
                                {id : "231",name:"合作银行维护",pId:"23"},
                                {id : "232",name:"银行费率维护",pId:"23"},
                                {id : "233",name:"银行费用查询",pId:"23"},
                                {id : "24",name:"参数管理",open:true},
                                {id : "241",name:"费率维护",pId:"24"},
                                {id : "25",name:"产品管理",open:true},
                                {id : "251",name:"套餐维护",pId:"25"},
                                {id : "252",name:"产品维护",pId:"25"},
                                {id : "26",name:"结算管理",open:true},
                                {id : "261",name:"商户清算查询",pId:"26"},
                                {id : "262",name:"商户清算发起",pId:"26"},
                                {id : "27",name:"对账管理",open:true},
                                {id : "271",name:"银行对账发送",pId:"27"},
                                {id : "272",name:"银行对账查询",pId:"27"},
                                {id : "273",name:"对账存疑查询",pId:"27"},
                                {id : "28",name:"复核管理",open:true},
                                {id : "281",name:"账户调账复核维护",pId:"28"},
                                {id : "29",name:"账务管理",open:true},
                                {id : "291",name:"内部账户查询",pId:"29"},
                                {id : "292",name:"账务调账",pId:"29"},
                                {id : "293",name:"记账明细查询",pId:"29"},
                                {id : "294",name:"备付金汇总查询",pId:"29"},
                                {id : "30",name:"短信管理",open:true},
                                {id : "301",name:"发送日志",pId:"30"},
                                {id : "31",name:"批量管理",open:true},
                                {id : "311",name:"批次查询",pId:"31"},
                                {id : "32",name:"报表中心",open:true},
                                {id : "321",name:"商户每日交易表",pId:"32"},
                                {id : "322",name:"商户每月交易表",pId:"32"},
                                {id : "323",name:"用户每日交易表",pId:"32"},
                                {id : "324",name:"用户每月交易表",pId:"32"},
                                {id : "325",name:"支付渠道每日交易表",pId:"32"},
                                {id : "326",name:"支付渠道每月交易表",pId:"32"},
                                {id : "327",name:"商户交易结算表",pId:"32"},
                                {id : "328",name:"业务指标统计表",pId:"32"},
                                {id : "33",name:"公告管理",open:true},
                                {id : "331",name:"公告管理",pId:"33"},
                                {id : "34",name:"下载中心",open:true},
                                {id : "341",name:"下载中心",pId:"34"},
                                {id : "35",name:"节假日管理",open:true},
                                {id : "351",name:"节假日管理",pId:"35"}
                            ];
                            $(document).ready(function(){
                                $.fn.zTree.init($("#treeDemo2"), setting, zNodes2);
                            });
						</script>
						<div id="treeDemo2" class="ztree"></div>
					</div>
					<!-- 风控管理 -->
					<div class="accordionHeader"><h2>&nbsp;&nbsp;风控管理</h2></div>
					<div class="accordionContent">
						<script type="text/javascript">
                            var zNodes3 =[
                                {id : "31",name : "限额管理",open:true},
                                {id : "311",name : "用户限额管理",pId : "31"},
                                {id : "312",name : "商户限额管理",pId : "31"},
                                {id : "32",name : "黑名单管理",open:true},
                                {id : "321",name : "用户黑名单管理",pId:"32",url : "fk/blackListMgt/user/index.html",target:"navTab"},
                                {id : "322",name : "商户黑名单管理",pId:"32",url : "fk/blackListMgt/biz/index.html",target:"navTab"},
                                {id : "33",name : "用户银行卡管理",open:true},
                                {id : "331",name : "银行卡解绑",pId:"33"},
                                {id : "332",name : "银行卡绑定历史浏览",pId:"33"},
                                {id : "34",name : "异常交易管理",open:true},
                                {id : "341",name : "异常规则管理",pId:"34"},
                                {id : "342",name : "异常交易汇总管理",pId:"34"},
                                {id : "343",name : "可疑交易汇总管理",pId:"34"},
                                {id : "35",name : "报告文件管理",open:true},
                                {id : "351",name : "报文生成",pId:"35"},
                                {id : "352",name : "报文查询",pId:"35"}
                            ];
                            $(document).ready(function(){
                                $.fn.zTree.init($("#treeDemo3"), setting, zNodes3);
                            });
						</script>
						<div id="treeDemo3" class="ztree"></div>
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main">
								<a href="javascript:;">
									<span>
										<span class="home_icon">我的主页</span>
									</span>
								</a>
							</li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div>
					<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div style="display: table; width: 100%; height: 500px;">
						<div id="box" style="display: table-cell; vertical-align: middle; text-align: center;">
							<img src="dwz/themes/default/images/bj.jpg" width="100%" height="100%"/>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">
		<b>互联网支付-后台管理</b>&nbsp;Copyright &copy; <a href="demo_page2.html" target="dialog">程少宽个人</a>
	</div>
</body>
</html>