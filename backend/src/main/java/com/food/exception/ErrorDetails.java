package com.food.exception;

import java.time.LocalDate;

public class ErrorDetails {
	String msg;
	LocalDate date;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	

}


