<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/">

<link rel="stylesheet" href="jquery/ztree/css/zTreeStyle/zTreeStyle.css" />
<script src="jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="jquery/ztree/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>

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
				url: "${pageContext.request.contextPath}/permission/makePermissionTree.do"
			},
			callback:{	
				//树节点被单击的时候执行的回调函数
				onClick:function(){			
					//获取树对象
					var treeObj = $.fn.zTree.getZTreeObj("permission_tree");
					//获取选中的节点集合  注意:该方法的返回值是一个json数组,但是我们这棵树,禁止树节点多选,所以返回的数组中,只有一个元素
					var nodes = treeObj.getSelectedNodes();
					//alert(nodes[0].id);
					parent.workareaFrame.location = "${pageContext.request.contextPath}/permission/detail.do?id="+nodes[0].id;
				},
				//树节点被删除的之后执行的回调函数		treeNode代表刚刚删除的节点
				onRemove:function(event, treeId, treeNode){
					//获取树对象
					var treeObj = $.fn.zTree.getZTreeObj("permission_tree");
					//选中要删除的节点的父节点
					treeObj.selectNode(treeNode.getParentNode());
					//显示父节点的详情
					parent.workareaFrame.location = "${pageContext.request.contextPath}/permission/detail.do?id="+treeNode.getParentNode().id;
				}
			}
		};

		$(function(){
			//页面加载完成之后初始化树
			$.fn.zTree.init($("#permission_tree"), setting);			
		});
</script>
<ul id="permission_tree" class="ztree">
</ul>
