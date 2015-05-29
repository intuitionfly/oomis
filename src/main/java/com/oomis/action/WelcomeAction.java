package com.oomis.action;

import org.apache.struts2.convention.annotation.Action;

import com.opensymphony.xwork2.ActionSupport;

public class WelcomeAction extends ActionSupport {

	@Override
	public String execute() throws Exception {
		System.out.println("Execute Action");
		return SUCCESS;
	}
	
	@Action("save")
	public String save() {
		System.out.println("save");
		return "fail";
	}
	
}
