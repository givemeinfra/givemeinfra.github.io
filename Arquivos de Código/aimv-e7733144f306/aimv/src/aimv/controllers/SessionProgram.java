package aimv.controllers;

public class SessionProgram {
	
	   private static SessionProgram instance = null;  
	   private int usuario;
	   
	   private SessionProgram()
	   {
		   usuario = 2;
	   }
	    
	   public void setUsuario(int usuario){  
	      this.usuario = usuario;  
	   }  
	  
	   public int getUsuario(){  
	       return usuario;  
	   } 
	   
	   public static SessionProgram getInstance(){  
	         if(instance == null){  
	               instance = new SessionProgram();  
	         }  
	        return instance;  
	   } 
		   
	}	   
	
	  

