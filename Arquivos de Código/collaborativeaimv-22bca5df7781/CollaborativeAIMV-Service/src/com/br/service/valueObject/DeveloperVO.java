package com.br.service.valueObject;

public class DeveloperVO {

	private String name;
	private String userName;
	private String passWord;
	private Boolean coordination;
	private ProjectVO projectVO;
	private boolean active = true;
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public void setCoordination(Boolean coordination){
		this.coordination = coordination;
	}
		
	public Boolean getCoordination(){
		return coordination;
	}
	
	public ProjectVO getProjectVO() {
		return this.projectVO;
	}
	public void setProjectVO(ProjectVO projectVO) {
		this.projectVO = projectVO;
	}
}
