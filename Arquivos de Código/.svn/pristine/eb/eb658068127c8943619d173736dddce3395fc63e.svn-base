package givemeviews.importation;

import givemeviews.metrics.Metric;
import givemeviews.model.Arquivo;
import givemeviews.model.GenericObject;
import givemeviews.model.ObjectLog;
import givemeviews.model.MantisCase;
import givemeviews.model.SetModules;
import givemeviews.model.SortModules;
import givemeviews.persistence.MemoryApplication;
import givemeviews.persistence.MemoryMantis;
import givemeviews.statisticalanalysis.FrequencyDistribution;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class MantisDriver 
{	   
	   public void startGiveMeViews()
	   {
		   	ArrayList<MantisCase> listaLinhasLidas = MemoryMantis.getListCases();
		    if(listaLinhasLidas.size() > 0)
		    {
		    	// clears
		    	MemoryApplication.clearListMetrics();	
		    	MemoryApplication.getListModules().removeAll(MemoryApplication.getListModules());
		    	MemoryMantis.getListGITorSVNlog().removeAll(MemoryMantis.getListGITorSVNlog());
		    	MemoryMantis.getListMantisComponents().removeAll(MemoryMantis.getListMantisComponents());
		    	
		    	
		    	Metric objMetric1 = new Metric("Number of Cases ", Integer.toString(listaLinhasLidas.size()));
			    MemoryApplication.setMetrics(objMetric1);			  
			    
			    if(MemoryApplication.getListTypesMaintenance().size() > 0)
			    {  	
					SetModules objModule = new SetModules();
					MemoryApplication.setListModules(objModule.returnListModulesMantisDate(MemoryMantis.getListCases()));
					SortModules objSort = new SortModules();
					Collections.sort(MemoryApplication.getListModules(), objSort);
					Metric objMetric13 = new Metric("Number of Modules that have set maintenance", Integer.toString(MemoryApplication.getListModules().size()));
					MemoryApplication.setMetrics(objMetric13);
					
					// get all gitLogs or svnLogs
					MemoryMantis.setLisGITorSVNlog(getListComponentsFromGITLog());
					
					// Number of Components that have set maintenance
					ArrayList<SetModules> listSetComponents = new ArrayList<>();
					if(MemoryMantis.getListGITorSVNlog() != null)
					{
						listSetComponents = countComponentsMaintenance(MemoryMantis.getListGITorSVNlog());
					}
					MemoryMantis.setListMantisComponents(listSetComponents);
					Metric objMetric14 = new Metric("Number of Components that have set maintenance", Integer.toString(listSetComponents.size()));
					MemoryApplication.setMetrics(objMetric14);
					
					MantisCase objCase = new MantisCase();
					try
					{
						ArrayList<GenericObject> listfinal = objCase.getPriorityCount(MemoryMantis.getListCases());
						for(int i = 0; i < listfinal.size(); i++)
						{
							Metric objPriorityMetric = new Metric("Number of cases with Priority " + listfinal.get(i).getNome(), Integer.toString(listfinal.get(i).getOcorrencia()));
							MemoryApplication.setMetrics(objPriorityMetric);
						}					
					}
					catch(Exception e){}
					
					/*MaintenanceType objType = new MaintenanceType();					
					ArrayList<String> listFilterCorretive = objType.getMaintenanceTypesByID(1);
					ArrayList<String> listFilterAdaptative = objType.getMaintenanceTypesByID(2);
					ArrayList<String> listFilterEvolutionary = objType.getMaintenanceTypesByID(3);
					Metric objMetric9 = new Metric("Number of Corrective Maintenance", Integer.toString(objArquivo.countRequestsByType(listFilterCorretive, MemoryDriverCEO.getListChangeRequests())));
					Metric objMetric10 = new Metric("Number of Adaptative Maintenance", Integer.toString(objArquivo.countRequestsByType(listFilterAdaptative, MemoryDriverCEO.getListChangeRequests())));
					Metric objMetric11 = new Metric("Number of Evolutive Maintenance", Integer.toString(objArquivo.countRequestsByType(listFilterEvolutionary, MemoryDriverCEO.getListChangeRequests())));
					MemoryApplication.setMetrics(objMetric9);
					MemoryApplication.setMetrics(objMetric10);
					MemoryApplication.setMetrics(objMetric11);
					*/
					
					FrequencyDistribution math = new FrequencyDistribution();
					Boolean status = math.startStatisticalProcess();
					if(status)
					{
						listSetComponents= null;
						listaLinhasLidas = null;
						
						MemoryApplication.setGeneratedSource(true);						
						//System.out.println("GiveMe Views say: Executed Successfully");
						JOptionPane.showMessageDialog(null, "Executed Successfully");
					}
					else{
						//System.out.println("GiveMe Views say: Execution Failed");
						JOptionPane.showMessageDialog(null, "Execution Failed");
					}
			    }
			    
		    }
	   }	   
	
	
	private ArrayList<SetModules> countComponentsMaintenance(ArrayList<ObjectLog> listGITorSVNlog) 
	{
		ArrayList<SetModules> listFinal = new ArrayList<>();
		Boolean status = false;
		
		for(int i = 0; i < listGITorSVNlog.size(); i++)
		{
			ArrayList<String> listComponents = listGITorSVNlog.get(i).getListLogComponents();
			for(int j = 0; j < listComponents.size(); j++)
			{
				String atualComponent = listComponents.get(j);
				status = false;
				
				if(listFinal.size() == 0)
				{
					SetModules atualModule = new SetModules(MemoryApplication.getProject().getNomeProjeto(), listComponents.get(j), 1);
					listFinal.add(atualModule);
				}
				else
				{
					for(int l = 0; l < listFinal.size(); l++)
					{
						if(atualComponent.equals(listFinal.get(l).getComponenteAssociado()))
						{
							listFinal.get(l).setOcorrencia(listFinal.get(l).getOcorrencia() + 1); // update 
							status = true;
						}
					}
					if(status == false)
					{
						SetModules atualModule = new SetModules(MemoryApplication.getProject().getNomeProjeto(), listComponents.get(j), 1);
						listFinal.add(atualModule);
					}
				}
			}
		}
		return listFinal;
	}


	private ArrayList<ObjectLog> getListComponentsFromGITLog() 
	   {		    
		    String path = MemoryApplication.getProject().getPathLogReport();
		    String pattern = Pattern.quote(System.getProperty("file.separator"));
			String[] splittedFileName = path.split(pattern);		
			String file = splittedFileName[splittedFileName.length - 1];
			file = file.replaceAll(".xls", ".txt");
			splittedFileName[splittedFileName.length - 1] = file;
			path = "";
			for(int i = 0; i < splittedFileName.length; i++)
			{
				if(i == 0)
					path += splittedFileName[i];
				else
				{
					path += "\\" +splittedFileName[i];
				}
			}
			
			ArrayList<String> listComponents = null;
			ArrayList<ObjectLog> listGITobjects = new ArrayList<>();
			int open = 0; // initial value
		    
		    try 
		    {
		        FileReader arq = new FileReader(path);
		        BufferedReader lerArq = new BufferedReader(arq);
	
		        String line = null; Boolean status = true;
		        while (line != null || status == true) 
		        {
		        	status = false;
			        line = lerArq.readLine();
			        if(line != null)
			        {
				        String tokensLine[] = line.split(" ");
				        
				        if(tokensLine.length > 0 && tokensLine != null)
				        {
					        if(tokensLine[0].contains("M") && line.contains("M ") && line.contains("/")) // então iniciou a leitura de uma linha válida
					        {				        	
					        	String tokensComponent[] = line.split("/");
					        	if(tokensComponent.length > 1)
					        	{
					        		open = 1; // open to sequence of components.
					        		String component = tokensComponent[tokensComponent.length - 1];
					        		if(listComponents == null)
					        			listComponents = new ArrayList<>();
					        		listComponents.add(component);
					        		// System.out.println(component); // Claudio, se descomentar, verá a impressão do nome de uma por uma das classes que estão no log.
					        	}
					        }
					        else
					        {
					        	if(open == 1)
						        	open = 2;
					        }
				        }
				        
			        }
			        if(open == 2)
			        {
			        	ObjectLog objGIT = new ObjectLog(listComponents);
			        	listGITobjects.add(objGIT);
			        	listComponents = null;
			        	open = 0;
			        }
			        	
		        }
		        if(listComponents != null)
		        {
		        	ObjectLog objGIT = new ObjectLog(listComponents);
		        	listGITobjects.add(objGIT);
		        	listComponents = null;
		        }
		        arq.close();
		        
		        return listGITobjects;
		    } 
		    catch (IOException e)
		    {
		        JOptionPane.showMessageDialog(null, "Impossible read Repository Log");
		        return null;
		    }		    
	   }


	   public Boolean readReportMode1(String path)
	   {
		   try
		   {
			   ArrayList<MantisCase> listaLinhasLidas =  readLines(path);		    
			   MantisCase objCase = new MantisCase();
			   
			   // clear
			   if(MemoryMantis.getListCases() != null)
				   MemoryMantis.getListCases().removeAll(MemoryMantis.getListCases());
			   if(MemoryApplication.getListTypesMaintenance() != null)
				   MemoryApplication.getListTypesMaintenance().removeAll(MemoryApplication.getListTypesMaintenance());
			   
			   // insert new
			   MemoryMantis.setListCases(listaLinhasLidas);
			   MemoryApplication.setMaintenanceTypes(objCase.getMaintenanceTypeList(listaLinhasLidas)); 
			   
			   listaLinhasLidas = null;
		       
		       return true;
		   }
		   catch(Exception e)
		   {
			   return false;
		   }
	   }
	   
	   public Boolean readReportMode2(String path)
	   {
		   try
		   {
			   ArrayList<MantisCase> listaLinhasLidas =  readLines(path);			   
			   
			   try{// clear
				   MemoryMantis.getListCases().removeAll(MemoryMantis.getListCases());
			   }catch(Exception e){}
			   
			   
			   // insert new
			   MemoryMantis.setListCases(listaLinhasLidas);	
			   
			   listaLinhasLidas = null;
			   
		       return true;
		   }
		   catch(Exception e)
		   {
			   return false;
		   }
	   }
	   
	   private ArrayList<MantisCase> readLines(String path)
	   {    	
	       
	       Workbook workbook = null;
	       ArrayList<MantisCase> listaObjetos = new ArrayList<>();
			try 
			{
				workbook = Workbook.getWorkbook(new File(path));
				Sheet sheet = workbook.getSheet(0);
				int linhas = sheet.getRows();
		        for(int i = 1; i < linhas; i++)
		        {
		        	Cell[] linha;
					linha = sheet.getRow(i);
					MantisCase objLinhaLida = makeObjectLine(linha);
					listaObjetos.add(objLinhaLida);      	
		        }
		        return listaObjetos;
			             
			} 
			catch (BiffException e) {
				JOptionPane.showMessageDialog(null, "Impossible  Read Plan");
				return null;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Impossible  Read Plan");
				return null;
			}      
	       
	   }
	   
	   private MantisCase makeObjectLine(Cell[] linha)
	   {
		    MantisCase objCaso = new MantisCase();
			objCaso.setNum(linha[0].getContents());
			objCaso.setProjeto(linha[1].getContents());
			objCaso.setRelator(linha[2].getContents());
			objCaso.setAtribuido(linha[3].getContents());
			objCaso.setPrioridade(linha[4].getContents());
			objCaso.setGravidade(linha[5].getContents());
			objCaso.setFrequencia(linha[6].getContents());
			objCaso.setEstado(linha[7].getContents());
			objCaso.setResolucao(linha[8].getContents());
			objCaso.setCategoria(linha[9].getContents());
			objCaso.setDataEnvio(linha[10].getContents());
			objCaso.setAtualizado(linha[11].getContents());
			objCaso.setSo(linha[12].getContents());
			objCaso.setVersaoSO(linha[13].getContents());
			objCaso.setPlataforma(linha[14].getContents());
			objCaso.setVersaoProduto(linha[15].getContents());
			objCaso.setCorrigidoNaVersao(linha[16].getContents());			
			objCaso.setPrevistoPara(linha[17].getContents());
			objCaso.setVisibilidade(linha[18].getContents());
			objCaso.setResumo(linha[19].getContents());
			objCaso.setDataLimite(linha[20].getContents());
			objCaso.setDescricao(linha[21].getContents());
			objCaso.setPassosReproduzir(linha[22].getContents());
			objCaso.setInformacoesAdicionais(linha[23].getContents());
			objCaso.setTipoCaso(linha[24].getContents());
			return objCaso;
	   }	
	   
	   public Boolean verifyPathFile(String path)
	   {
		    boolean exists = (new File(path)).exists();  
	        if (exists) {  
	            return true;
	        } else {  
	            return false;
	        }  
	   }
	   
	   public Boolean verifyComponentOccur(String componentName)
	   {
	       	 ArrayList<ObjectLog> list = getListComponentsFromGITLog();
	       	 if(list != null)
	       	 {
	       		 for(int i = 0; i < list.size(); i++)
	       		 {
	       			 ArrayList<String> setComponents = list.get(i).getListLogComponents();
	       			 for(int j = 0; j < setComponents.size(); j++)
	       			 {
	       				 if(setComponents.get(j).equals(componentName))
	       				 {
	       					 list = null;
	       					 return true;
	       				 }
	       			 }
	       		 }
	       	 }
	       	 list = null;
	       	 return false;
	   }
	   
}
