package com.example.taobaoapp_1;

public class Person {

	private int id;
	private String username;
	private String password;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", username=" + username + ", password="
				+ password + "]";
	}
	public Person(int id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
