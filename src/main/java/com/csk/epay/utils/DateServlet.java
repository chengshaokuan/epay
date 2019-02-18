package com.csk.epay.utils;

import javax.servlet.*;
import java.io.*;
import java.util.*;
public class DateServlet implements Servlet
{
	private ServletConfig config;
	/**
		1、异常ServletException:把所有的异常封装成ServletException跑出
		2、参数config:代表servlet在服务器中的配置信息。

		init:初始化操作，在servlet实例化之后由服务器自动调用一次。
	*/
	@Override
	public void init(ServletConfig config) throws ServletException{
		this.config=config;
		System.out.println("======This is init method=======");
	}
	/**
		destroy:在servlet销毁的时候调用。
	*/
	@Override
	public void destroy(){
		System.out.println("======This is destroy method=======");
	}
	/**
		获取config对象
	*/
	@Override
	public ServletConfig getServletConfig(){
		return config;
	}
	/**
		获取servlet的配置信息
	*/
	@Override
	public String getServletInfo(){
		return "This is my first Servlet";
	}
	/**
		service:处理业务

		1、异常：
			--IOException:因为要通过网络返回响应信息。
			--ServletException:除了IOException之外所有异常封装成ServletException跑出
		2、参数：
			--request:代表来表来自客户端的请求。
			--response:代表给客户端的响应信息。
		
	*/
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException,IOException{
		System.out.println("This is service method");
		//设置响应类型
		response.setContentType("text/html;charset=UTF-8");
		//获取输出流
		PrintWriter out = response.getWriter();
		/*
		out.print("<html>");
		out.print("<body>");
		out.print("<h2>"+new Date()+"</h2>");
		out.print("</body>");
		out.print("</html>");
		out.flush();
		*/

		//out.println在html字符串每一行后加一个回车换行
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>"+new Date()+"</h1>");
		out.println("<h1>北京欢迎你！</h1>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
	}
}