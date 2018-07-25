
/*面向过程的封装方式
function selectSingleCheckbox(path){
	var checkedElts = $(":checkbox[name='id']:checked");
	if(checkedElts.length==0){
		$("#message").text("请选择记录");
	}else if(checkedElts.length>1){
		$("#message").text("只能选择一条记录");			
	}else{
		window.location.href = path+"?id="+checkedElts.val();
	}
}
在js中创建类的方式
JsUtil = function(){}
function JsUtil(){}
*/
function JsUtil(){}

//给类动态的扩展属性或者行为
JsUtil.prototype.selectSingleCheckbox=function(path){
	var checkedElts = $(":checkbox[name='id']:checked");
	if(checkedElts.length==0){
		$("#message").text("请选择记录");
	}else if(checkedElts.length>1){
		$("#message").text("只能选择一条记录");			
	}else{
		window.location.href = path+"?id="+checkedElts.val();
	}
};

//在该js文件被加载的时候,创建该类的实例
$_=jsUtil = new JsUtil();



