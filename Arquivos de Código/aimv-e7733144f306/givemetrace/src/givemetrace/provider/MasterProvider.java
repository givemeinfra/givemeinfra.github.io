package givemetrace.provider;


import givemetrace.implementations.GiveMeTraceXML;
import givemetrace.implementations.InfoRepository;
import givemetrace.implementations.TraceGit;
import givemetrace.implementations.TraceSvn;
import givemetrace.implementations.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.JDOMException;

public class MasterProvider {
	private final String filePath = "C:"+File.separator+"GiveMeRepository"+File.separator+"Brain"+File.separator+"givemetrace.xml";
	private final String dirPath = "C:"+File.separator+"GiveMeRepository"+File.separator+"Brain";
	
	/**
	 * @param projectName - Project Name to get assigned Repository.
	 * @param commitNumber - Commit Number and/or Range of Commits, separated by token ";".Ex.1-3;6;10-0
	 **/
	public ArrayList<String> toMantisCall(String projectName, String commitNumber){
		ArrayList<String> result = new ArrayList<>();
		if(commitNumber==null||commitNumber.isEmpty()){
			//JOptionPane.showMessageDialog(null, "Inform a Commit Number or Range (ex. X;Y) to proceed.");
			return result;
		}
		File f = new File(filePath);
		if(!f.exists() || !f.isFile()){//resolvo o problema do File não existir;
			if(!Utils.sucessCreateFile(filePath, dirPath)){
				//JOptionPane.showMessageDialog(null, "Can not create the File. Maybe permission denied.");
				return result;
			}
		}
		InfoRepository irToTrace = null;
		try {
			irToTrace = GiveMeTraceXML.tryFoundInfoRepository(filePath, projectName);
		} catch (JDOMException e2) {
			//JOptionPane.showMessageDialog(null, "Found an error in givemetrace.xml while trying to found Repository Information.");
			//e2.printStackTrace();
			return result;
		} catch (IOException e2) {
			//JOptionPane.showMessageDialog(null, "Error!\nThe registered URL is not a valid Repository.");
			//e2.printStackTrace();
			return result;
		}
		if(irToTrace==null){
			//JOptionPane.showMessageDialog(null, "Project not assigned yet!\nOpen givemetrace.xml and follow the model to assign it.");
			return result;
		}
		if(irToTrace.getRepositoryPath().isEmpty()){
			//JOptionPane.showMessageDialog(null, "Error!\nAssign a Repository URL to Project");
			return result;}
		if(irToTrace.getRepositoryType().isEmpty()){
			//JOptionPane.showMessageDialog(null, "Error!\nAssign a Repository Type to Project");
			return result;}
		String type = irToTrace.getRepositoryType();
		String url = irToTrace.getRepositoryPath().replaceAll("\\\\", "/");
		
		switch (type) {
		case "GIT"://GIT
			if(!url.endsWith("/.git")){
				if(url.endsWith("/")){
					url+=".git";
					}
				else{url+="/.git";}
			}
			MasterProvider mpGit = new MasterProvider();
			return mpGit.gitToArrayMethod(url, commitNumber);
		case "SVN"://SVN
			String name= "anonymous";
	        String password = "anonymous";
	        if(!url.startsWith("file:///")){
				url="file:///"+url;
			}
			MasterProvider mpSvn = new MasterProvider();
			return mpSvn.svnToArrayMethod(url, commitNumber, name, password);
		default:
			//JOptionPane.showMessageDialog(null, "Unknow repository Type informed. Change type and try again.");
			return result;
		}
	}
	public MasterProvider(){}
	
	/**
	 * @param urlRepositorio - URL Repository.
	 * @param commitNumber - Commit Number and/or Range of Commits, separated by token ";".Ex.1-3;6;10-0
	 * @param name - name to access Repository. Default is "anonymous". 
	 * @param password - password to access Repository. Default is "anonymous".
	 * */
	public ArrayList<String> svnToArrayClass(String urlRepositorio,String commitNumber, String name,String password){
		urlRepositorio = urlRepositorio.replaceAll("\\\\", "/");
		if(!urlRepositorio.startsWith("file:///")){
			urlRepositorio="file:///"+urlRepositorio;
		}
		if(name==null ||password==null){
			name= "anonymous";
            password = "anonymous";
		}
		String[] Revisions;
		if(commitNumber.contains(";")){
			Revisions = commitNumber.split(";");
		}else{
			Revisions = new String[]{commitNumber};
		}
		ArrayList<String> toc = new TraceSvn().preCoreToArrayClass(urlRepositorio,Revisions, name, password);
		return toc;
	}
	/**
	 * @param urlRepositorio - URL Repository.
	 * @param filename - URL to create and save the Log.
	 * @param commitNumber - Commit Number and/or Range of Commits, separated by token ";".Ex.1-3;6;10-0
	 * @param name - name to access Repository. Default is "anonymous". 
	 * @param password - password to access Repository. Default is "anonymous".
	 * */
	public boolean svnToLogClass(String urlRepositorio, String filename, String commitNumber, String name, String password){
		urlRepositorio = urlRepositorio.replaceAll("\\\\", "/");
		if(!urlRepositorio.startsWith("file:///")){
			urlRepositorio="file:///"+urlRepositorio;
		}
		if(name==null ||password==null){
			name= "anonymous";
            password = "anonymous";
		}
		String[] Revisions;
		if(commitNumber.contains(";")){
			Revisions = commitNumber.split(";");
		}else{
			Revisions = new String[]{commitNumber};
		}
		return new TraceSvn().preCoreToLogClass(urlRepositorio,filename,Revisions, name, password);
		
	}
	/**
	 * @param urlRepositorio - URL Repository.
	 * @param filename - URL to create and save the Log.
	 * @param commitNumber - Commit Number and/or Range of Commits, separated by token ";".Ex.1-3;6;10-0
	 * @param name - name to access Repository. Default is "anonymous". 
	 * @param password - password to access Repository. Default is "anonymous".
	 * */
	public boolean svnToLogMethod(String urlRepositorio, String filename, String commitNumber, String name, String password){
		urlRepositorio = urlRepositorio.replaceAll("\\\\", "/");
		if(!urlRepositorio.startsWith("file:///")){
			urlRepositorio="file:///"+urlRepositorio;
		}
		if(name==null ||password==null){
			name= "anonymous";
            password = "anonymous";
		}
		String[] Revisions;
		if(commitNumber.contains(";")){
			Revisions = commitNumber.split(";");
		}else{
			Revisions = new String[]{commitNumber};
		}
		return new TraceSvn().preCoreToLogMethod(urlRepositorio,filename,Revisions, name, password);
		
	}
	/**
	 * @param urlRepositorio - URL Repository.
	 * @param commitNumber - Commit Number and/or Range of Commits, separated by token ";".Ex.1-3;6;10-0
	 * @param name - name to access Repository. Default is "anonymous". 
	 * @param password - password to access Repository. Default is "anonymous".
	 * */
	public ArrayList<String> svnToArrayMethod(String urlRepositorio,String commitNumber, String name,String password){
		urlRepositorio = urlRepositorio.replaceAll("\\\\", "/");
		if(!urlRepositorio.startsWith("file:///")){
			urlRepositorio="file:///"+urlRepositorio;
		}
		if(name==null ||password==null){
			name= "anonymous";
            password = "anonymous";
		}
		String[] Revisions;
		if(commitNumber.contains(";")){
			Revisions = commitNumber.split(";");
		}else{
			Revisions = new String[]{commitNumber};
		}
		ArrayList<String> toc = new TraceSvn().preCoreToArrayMethod(urlRepositorio,Revisions, name, password);
		return toc;
	}

	//GIT
	
	/**
	 * @param urlRepositorio - URL Repository.
	 * @param filename - URL to create and save the Log.
	 * @param commitNumber - Commit Number and/or Range of Commits, separated by token ";".Ex.1-3;6;10-0
	 * */
	public boolean gitToLogMethod(String urlRepositorio, String filename, String commitNumber){
		urlRepositorio = urlRepositorio.replaceAll("\\\\", "/");
		if(!urlRepositorio.endsWith("/.git")){
			if(urlRepositorio.endsWith("/")){
				urlRepositorio+=".git";
				}
			else{urlRepositorio+="/.git";}
		}
		String[] Revisions;
		if(commitNumber.contains(";")){
			Revisions = commitNumber.split(";");
		}else{
			Revisions = new String[]{commitNumber};
		}
		return new TraceGit().preCoreToLogMethod(urlRepositorio,filename,Revisions);
		
	}
	/**
	 * @param urlRepositorio - URL Repository.
	 * @param filename - URL to create and save the Log.
	 * @param commitNumber - Commit Number and/or Range of Commits, separated by token ";".Ex.1-3;6;10-0
	 * */
	public boolean gitToLogClass(String urlRepositorio, String filename, String commitNumber){
		urlRepositorio = urlRepositorio.replaceAll("\\\\", "/");
		if(!urlRepositorio.endsWith("/.git")){
			if(urlRepositorio.endsWith("/")){
				urlRepositorio+=".git";
				}
			else{urlRepositorio+="/.git";}
		}
		String[] Revisions;
		if(commitNumber.contains(";")){
			Revisions = commitNumber.split(";");
		}else{
			Revisions = new String[]{commitNumber};
		}
		return new TraceGit().preCoreToLogClass(urlRepositorio,filename,Revisions);
		
	}
	 /**
	 *  @param urlRepositorio - URL Repository.
	 * @param commitNumber - Commit Number and/or Range of Commits, separated by token ";".Ex.1-3;6;10-0
	 * */
	public ArrayList<String> gitToArrayMethod(String urlRepositorio, String commitNumber){
		urlRepositorio = urlRepositorio.replaceAll("\\\\", "/");
		if(!urlRepositorio.endsWith("/.git")){
			if(urlRepositorio.endsWith("/")){
				urlRepositorio+=".git";
				}
			else{urlRepositorio+="/.git";}
		}
		String[] Revisions;
		if(commitNumber.contains(";")){
			Revisions = commitNumber.split(";");
		}else{
			Revisions = new String[]{commitNumber};
		}
		return new TraceGit().preCoreToArrayMethod(urlRepositorio,Revisions);
		
	}
	/**
	 *  @param urlRepositorio - URL Repository.
	 * @param commitNumber - Commit Number and/or Range of Commits, separated by token ";".Ex.1-3;6;10-0
	 * */
	public ArrayList<String> gitToArrayClass(String urlRepositorio, String commitNumber){
		urlRepositorio = urlRepositorio.replaceAll("\\\\", "/");
		if(!urlRepositorio.endsWith("/.git")){
			if(urlRepositorio.endsWith("/")){
				urlRepositorio+=".git";
				}
			else{urlRepositorio+="/.git";}
		}
		String[] Revisions;
		if(commitNumber.contains(";")){
			Revisions = commitNumber.split(";");
		}else{
			Revisions = new String[]{commitNumber};
		}
		return new TraceGit().preCoreToArrayClass(urlRepositorio,Revisions);
		
	}
	
	public ArrayList<String> getObjectRepository(String pathToRead, String projectName)
	{
		try 
		{
			InfoRepository repo = GiveMeTraceXML.tryFoundInfoRepository(pathToRead, projectName);
			ArrayList<String> listRepoInfos = new ArrayList<>();
			listRepoInfos.add(repo.getProjectName());
			listRepoInfos.add(repo.getRepositoryPath());
			listRepoInfos.add(repo.getRepositoryType());
			listRepoInfos.add(repo.getWorkCopyPath());
			listRepoInfos.add(repo.getLogin());
			listRepoInfos.add(repo.getPassword());
			
			return listRepoInfos;
		} 
		catch (JDOMException | IOException e) 
		{
			return null;
		}
	}
	
	public ArrayList<String> svnGetArrayMethodFromWorkCopy(String pathToRead, String projectName)
	{
		try 
		{			
			InfoRepository repo = GiveMeTraceXML.tryFoundInfoRepository(pathToRead, projectName);
			repo.setRepositoryPath(repo.getRepositoryPath().replaceAll("\\\\", "/"));
			if(!repo.getRepositoryPath().startsWith("file:///")){
				repo.setRepositoryPath("file:///"+ repo.getRepositoryPath());
			}
			if(repo.getLogin().equals("") ||repo.getPassword().equals("")){
				repo.setLogin("anonymous");
	            repo.setPassword("anonymous");
			}
			if(repo.getRepositoryType().equals("SVN"))
			{	
				TraceSvn trace = new TraceSvn();
				return trace.coreToWcHeadArrayMethod(repo.getWorkCopyPath(), repo.getRepositoryPath(), repo.getLogin(), repo.getPassword());
			}
			else
				return null;			
		} 
		catch (JDOMException | IOException e) 
		{
			return null;
		}
		
	}
	
}

