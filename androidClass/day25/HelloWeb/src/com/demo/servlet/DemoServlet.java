package com.demo.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.model.Employee;
import com.demo.model.ResponseEntity;
import com.google.gson.Gson;

/**
 * Servlet implementation class DemoServlet
 */
public class DemoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private List<Employee> mEmpList;

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
		mEmpList = new ArrayList<Employee>();

		Employee emp1 = new Employee();
		emp1.setId(1);
		emp1.setEmpName("Jack");
		emp1.setAge(25);

		Employee emp2 = new Employee();
		emp2.setId(2);
		emp2.setEmpName("Marilyn");
		emp2.setAge(26);

		Employee emp3 = new Employee();
		emp3.setId(3);
		emp3.setEmpName("Peter");
		emp3.setAge(24);

		mEmpList.add(emp1);
		mEmpList.add(emp2);
		mEmpList.add(emp3);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/json;charset=utf-8");

		String paramCid = request.getParameter("cid");

		ResponseEntity entity = new ResponseEntity();
		if (paramCid != null && !"".equals(paramCid)) {
			entity.setStatus(200);
			if (paramCid.equals("1")) {
				entity.setData(mEmpList);
			}
		} else {
			entity.setStatus(10001);
			entity.setMessage("参数错");
		}
		
		String resultJson = new Gson().toJson(entity);
		System.out.println(resultJson);
		
		PrintWriter writer = response.getWriter();
		writer.write(resultJson);
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
	}
}
