package givemeviews.model;

import givemeviews.persistence.MemoryApplication;

public class Projeto 
{
     private String nomeProjeto;
     private String ferramentaGMM;
     private String nomeComponente;
     private String pathLogReport;
     
     public Projeto()
     {
    	 
     }
       
     public Projeto(String nomeProjeto, String ferramentaGMM, String nomeComponente, String path) 
     {
 		this.nomeProjeto = nomeProjeto;
 		this.ferramentaGMM = ferramentaGMM;
 		this.nomeComponente = nomeComponente;
 		this.pathLogReport = path;
 	 }
     
	 public String getNomeProjeto() {
		return nomeProjeto;
	 }
	 public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	 }
	 public String getFerramentaGMM() {
		return ferramentaGMM;
	 }
	 public void setFerramentaGMM(String ferramentaGMM) {
		this.ferramentaGMM = ferramentaGMM;
	 }
	 public String getNomeComponente() {
		return nomeComponente;
	 }
	 public void setNomeComponente(String nomeComponente) {
		this.nomeComponente = nomeComponente;
	 }
	 
	 public Boolean verifyTool(String path)
	 {
		 if(path.contains("Driver Empresa 1"))
		 {
			 Projeto project = MemoryApplication.getProject();
			 project.setFerramentaGMM("Driver Empresa 1");
			 MemoryApplication.setProject(project);
			 return true;
		 }
		 else if(path.contains("Mantis"))
		 {
			 Projeto project = MemoryApplication.getProject();
			 project.setFerramentaGMM("Mantis");
			 MemoryApplication.setProject(project);
			 return true;
		 }
		 return false;
	 }

	public String getPathLogReport() {
		return pathLogReport;
	}

	public void setPathLogReport(String pathLogReport) {
		this.pathLogReport = pathLogReport;
	}
}
