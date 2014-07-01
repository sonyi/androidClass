package com.demo.servlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DemoServlet
 */
public class DemoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DemoServlet() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("DemoServlet���ڳ�ʼ��...");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = 0;
		String name = "";

		String paramId = request.getParameter("id");
		String paramName = request.getParameter("name");
		if (paramId != null && !"".equals(paramId)) {
			id = Integer.parseInt(paramId) + 1;
		}
		if (paramName != null && !"".equals(paramName)) {
			name = paramName;
		}

		response.getWriter()
				.write("<html>"
						+ "<body>"
						+ "<h1>Hello Web,this is our first Servlet,it's working...</h1>"
						+ "<h3>id:" + id + "</h3>" + "<h3>name:" + name
						+ "</h3>" + "</body>" + "</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("gb2312");

		String userName = request.getParameter("username");
		String password = request.getParameter("password");

		response.getWriter().write(
				"<html><body><h1>你好:" + userName + "</h1></body></html>");
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
	}
}
