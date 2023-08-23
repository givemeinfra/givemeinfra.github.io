package com.br.collaborativeAIMV.log;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

//	1. New content proposal of one line of log file:
//	1.1. Date and time 
//		1.1.1. format dd/mm/YYYY hh:mm:ss
//	1.2. Action:
//		1.2.1. not null
//		1.2.2. all actions are mapped
//	1.3. View name or Workbench 
//		1.3.1. null
//	1.4. Project name
//		1.4.1. null
//	1.5. Artifact
//		1.5.1. null
//	1.6. Data separator: |
//2. Examples:
//	2.1. 03/24/2014 04:36:24|Changed|Editor|MobileMedia|ImageNotFoundException.java
//	2.2. 03/24/2014 04:36:24|BroughtToTop|Collaboration View||
//	2.3. 03/24/2014 04:36:24|Deactivated|Workbench||
//	2.4. 03/24/2014 04:36:24|Activated|Workbench||
	
	private static PrintWriter pr = null;
	private static FileWriter f = null;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"MM/dd/yyyy hh:mm:ss");

	private Log() {
	}
	
	private static void startLog(){
		try {
			if (pr==null){
				f = new FileWriter("collaborative_log.log", true);
				pr = new PrintWriter(f, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	public static void print(String s) {
		Log.startLog();
		pr.println("(" + sdf.format(new Date()) + ")" + " " + s);
	}

	public static void registerLogin(String name, String username,
			String project) {
		Log.startLog();
		print("NAME: " + name + " USERNAME: " + username
				+ " LOGGED ON PROJECT: " + project);
	}

	public static void collaborativeViewOpen(String view) {
		Log.startLog();
		print(view + " OPENED");
	}
	
//	public static void main(String [] ars){
//		String a = "03/24/2014 04:36:24|Changed|Editor|MobileMedia|ImageNotFoundException.java";
//		String[] as = a.split("\\|");
//		for (String y : as){
//			System.out.println(y);
//		}
//	}
//	public static void print(String action, String view, String project, String element) {
//		Log.startLog();
//		pr.println(sdf.format(new Date()) + "|" + action + "|" + view + "|" + project + "|" + element);
//	}
	
}