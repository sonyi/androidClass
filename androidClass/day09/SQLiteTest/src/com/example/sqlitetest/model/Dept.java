package com.example.sqlitetest.model;


public class Dept {
	private long id;
	private String daptName;
	//private List<Employee> empList;
	
	public Dept(long id, String daptName) {
		super();
		this.id = id;
		this.daptName = daptName;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDaptName() {
		return daptName;
	}
	public void setDaptName(String daptName) {
		this.daptName = daptName;
	}
	
	
}
