//package com.csk.epay.service.impl.helper;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import com.csk.epay.domain.Constant;
//import com.csk.epay.domain.OperationLog;
//import com.csk.epay.domain.User;
//import com.csk.epay.service.OperationLogService;
//import com.csk.epay.utils.DateUtil;
//
//@Aspect //抽取独立性服务(记录操作日志的独立性服务，可复用，和业务流程没有关系，不管是什么应用记录日志都是这样记录的)
//@Component //纳入IOC容器管理，该对象的创建交给了IOC容器
//public class OperationLogHandler {
//
//	public OperationLogHandler() {
//		System.out.println("121231231");
//	}
//	@Resource(name="operationLogService")
//	private OperationLogService operationLogService;
//
//	//指定切入点,以下这个注解必须出现在方法上，才能编译通过
//	@Pointcut("execution(* com.csk.epay.service.impl.*.save*(..)) " +
//			"|| execution(* com.csk.epay.service.impl.*.delete*(..)) " +
//			"|| execution(* com.csk.epay.service.impl.*.update*(..))")
//	private void serviceMethod(){} //这个方法是一个空实现，这个方法没必要公开，只是为了让上面注解编译通过。
//
//
//	//对Aspect的具体实现Advice
//	//Advice是一段实际存在的代码，完成记录日志
//	//指定该通知是什么类型的通知？service方法执行结束之后记录日志，可见该通知是一个后置通知
//	@After("com.csk.epay.service.impl.helper.OperationLogHandler.serviceMethod()")
//	public void writeLog(JoinPoint joinPoint){ //连接点是具体切入的方法，这个方法是目标对象上的目标方法
//		OperationLog ol = new OperationLog();
//
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
//			    .getRequestAttributes()).getRequest();
//		//ip地址
//		String ip = request.getRemoteAddr();
//		ol.setIp(ip);
//		//操作员
//		User operator = ((User)request.getSession().getAttribute(Constant.SESSION_USER));
//		ol.setOperator(operator.getName());
//		//操作时间
//		ol.setTime(DateUtil.getSystemTime());
//		//操作模块
//		String uri = request.getRequestURI();
//		if(uri.contains("permission")){
//			ol.setModule("许可维护");
//		}
//		if(uri.contains("user")){
//			ol.setModule("用户维护");
//		}
//		if(uri.contains("role")){
//			ol.setModule("角色维护");
//		}
//		//操作节点
//		String className = joinPoint.getTarget().getClass().getName();
//		String methodName = joinPoint.getSignature().getName();
//		ol.setNode(className + "." + methodName + "()");
//		//操作类型
//		if(methodName.startsWith("save")){
//			ol.setType("增加");
//		}
//		if(methodName.startsWith("delete")){
//			ol.setType("删除");
//		}
//		if(methodName.startsWith("update")){
//			ol.setType("修改");
//		}
//		//保存操作日志
//		operationLogService.writeLog(ol);
//	}
//}
