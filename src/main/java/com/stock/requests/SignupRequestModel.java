package com.stock.requests;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_database")
public class SignupRequestModel {
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	@Id
	private String username;
	private String password;
	private String emailid;
	private String phonenumber;
	private String ssn;
	public SignupRequestModel(String username, String password, String emailid, String phonenumber, String ssn) {
		super();
		this.username = username;
		this.password = password;
		this.emailid = emailid;
		this.phonenumber = phonenumber;
		this.ssn = ssn;
	}
	public SignupRequestModel() {
		super();
	}

}
