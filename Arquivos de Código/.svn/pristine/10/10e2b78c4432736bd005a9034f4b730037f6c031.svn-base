package com.br.collaborativeAIMV.view.templates;

import org.eclipse.jface.wizard.Wizard;


import com.br.collaborativeAIMV.control.LoginControl;
import com.br.collaborativeAIMV.view.pages.AccessDeniedPage;
import com.br.collaborativeAIMV.view.pages.ActivityPage;
import com.br.collaborativeAIMV.view.pages.DeveloperPage;
import com.br.collaborativeAIMV.view.pages.LoginPage;
import com.br.collaborativeAIMV.view.pages.ProjectPage;


public class TemplateWizard extends Wizard{
	protected TemplatePage page;
	protected static final String LOGIN = "login";
	protected static final String ATIVIDADE = "atividade";
	LoginControl loginControl;
	
	public TemplateWizard(String screen){
		
		if (screen=="login"){
			setWindowTitle("Perform Login");
			page = new LoginPage();
		}else
			if(screen=="atividade"){
				setWindowTitle("Manage Activities");
				page = new ActivityPage();
			}else
				if(screen=="projeto"){
					setWindowTitle("Manage Projects");
					page = new ProjectPage();
				}else
					if(screen=="desenvolvedor"){
						setWindowTitle("Manage Developers");
						page = new DeveloperPage();
					}else
						if(screen=="accessDenied"){
							setWindowTitle("Access Denied");
							page = new AccessDeniedPage();
						}
		
		
	}
	
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return page.performFinish();
	}
	
	@Override
	public void addPages(){
		addPage(page);
	}
	
	@Override
	public boolean canFinish(){
		return true;
		
	}
		
}
