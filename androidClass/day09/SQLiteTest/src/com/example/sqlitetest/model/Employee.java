package com.example.sqlitetest.model;

public class Employee {
	private long id;
	private String empName;
	private String tel;
	private long dept_id;
	
	
	
	public Employee() {
		super();
	}

	public Employee(long id, String empName, String tel,long dept_id) {
		super();
		this.id = id;
		this.empName = empName;
		this.tel = tel;
		this.dept_id = dept_id;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

	public long getDept_id() {
		return dept_id;
	}

	public void setDept_id(long dept_id) {
		this.dept_id = dept_id;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", empName=" + empName + ", tel=" + tel
				+ ", dept_id=" + dept_id + "]";
	}
	
	
}
