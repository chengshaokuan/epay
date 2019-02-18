package com.csk.epay.utils;

import javax.servlet.*;
import java.io.*;
import java.util.*;
public class DateServlet implements Servlet
{
	private ServletConfig config;
	/**
		1���쳣ServletException:�����е��쳣��װ��ServletException�ܳ�
		2������config:����servlet�ڷ������е�������Ϣ��

		init:��ʼ����������servletʵ����֮���ɷ������Զ�����һ�Ρ�
	*/
	@Override
	public void init(ServletConfig config) throws ServletException{
		this.config=config;
		System.out.println("======This is init method=======");
	}
	/**
		destroy:��servlet���ٵ�ʱ����á�
	*/
	@Override
	public void destroy(){
		System.out.println("======This is destroy method=======");
	}
	/**
		��ȡconfig����
	*/
	@Override
	public ServletConfig getServletConfig(){
		return config;
	}
	/**
		��ȡservlet��������Ϣ
	*/
	@Override
	public String getServletInfo(){
		return "This is my first Servlet";
	}
	/**
		service:����ҵ��

		1���쳣��
			--IOException:��ΪҪͨ�����緵����Ӧ��Ϣ��
			--ServletException:����IOException֮�������쳣��װ��ServletException�ܳ�
		2��������
			--request:�����������Կͻ��˵�����
			--response:������ͻ��˵���Ӧ��Ϣ��
		
	*/
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException,IOException{
		System.out.println("This is service method");
		//������Ӧ����
		response.setContentType("text/html;charset=UTF-8");
		//��ȡ�����
		PrintWriter out = response.getWriter();
		/*
		out.print("<html>");
		out.print("<body>");
		out.print("<h2>"+new Date()+"</h2>");
		out.print("</body>");
		out.print("</html>");
		out.flush();
		*/

		//out.println��html�ַ���ÿһ�к��һ���س�����
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>"+new Date()+"</h1>");
		out.println("<h1>������ӭ�㣡</h1>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
	}
}