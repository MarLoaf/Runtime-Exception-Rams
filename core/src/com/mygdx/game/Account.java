package com.mygdx.game;

public class Account {
	
	private String username;
	private String password;
	private String secretQuestion;
	private String secretAnswer;
	private String fullName;
	
	public Account(String u, String p, String sq, String sa, String fn) {
		username = u;
		password = p;
		secretQuestion = sq;
		secretAnswer = sa;
		fullName = fn;
	}
	
	public Account() {
		username = "";
		password = "";
		secretQuestion = "";
		secretAnswer = "";
		fullName = "";
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getSecretQuestion() {
		return secretQuestion;
	}
	
	public String getSecretAnswer() {
		return secretAnswer;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setUsername(String u) {
		username = u;
	}
	
	public void setPassword(String p) {
		password = p;
	}
	
	public void setSecretQuestion(String sq) {
		secretQuestion = sq;
	}
	
	public void setSecretAnswer(String sa) {
		secretAnswer = sa;
	}
	
	public void setFullName(String fn) {
		fullName = fn;
	}
}
