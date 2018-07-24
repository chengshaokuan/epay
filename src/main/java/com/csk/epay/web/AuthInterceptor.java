//package com.csk.epay.web;
//
//import java.util.Set;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.csk.epay.domain.Constant;
//import com.csk.epay.domain.User;
//
//public class AuthInterceptor implements HandlerInterceptor{
//
//	private final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
//
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//
//		//非法用户认证  从session中获取用户对象
//		User user = (User)request.getSession().getAttribute(Constant.SESSION_USER);
//		if(user==null){
//			//重定向
//			response.sendRedirect(request.getContextPath()+"/login.jsp");
//			return false;
//		}
//		//合法用户认证
//		Set<String> urls = (Set<String>)request.getSession().getAttribute(Constant.URLS);
//		//获取当前用户请求的uri
//		String uri = request.getRequestURI();//     /epay /role/add.do
//		String contextPath = request.getContextPath();
//		uri = uri.substring(contextPath.length()+1);
//		if(!urls.contains(uri)){
//			//请求转发
//			request.getRequestDispatcher("/WEB-INF/jsp/common/error.jsp").forward(request, response);
//			return false;
//		}
//
//		return true;
//	}
//
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//
//	}
//
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//	}
//
//}
