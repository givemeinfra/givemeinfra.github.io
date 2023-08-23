package givemeviews.importation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import givemeviews.metrics.Metric;
import givemeviews.metrics.Percent;
import givemeviews.model.Arquivo; 
import givemeviews.model.MaintenanceType;
import givemeviews.model.SetModules;
import givemeviews.model.RDMDesdobramento;
import givemeviews.model.RDMOrigem;
import givemeviews.model.SortModules;
import givemeviews.persistence.MemoryApplication;
import givemeviews.persistence.MemoryDriverCEO;
import givemeviews.statisticalanalysis.FrequencyDistribution;
import givemeviews.filtering.FilterCEORequests;

public class DriverCEODate {
	
	   private FilterCEORequests request = new FilterCEORequests();
	   private ArrayList<Arquivo> listaObjetos = null;
	   
	   public DriverCEODate()
	   {
			
	   }
	   
	   public void startGiveMeViews()
	   {
		   	ArrayList<Arquivo> listaLinhasLidas = MemoryDriverCEO.getListChangeRequests();
		    if(listaLinhasLidas.size() > 0)
		    {
		    	MemoryApplication.clearListMetrics();
		    	
		    	Arquivo objArquivo = new Arquivo();	 	
		    	
		    	Metric objMetric1 = new Metric("Number of Valid Lines ", Integer.toString(listaLinhasLidas.size()));
			    MemoryApplication.setMetrics(objMetric1);
			    
			    RDMOrigem objOrigem = new RDMOrigem();
			    ArrayList<RDMOrigem> listaRDMs =  objOrigem.montarListaRDMs(listaLinhasLidas);				    
			    RDMDesdobramento objDesdobramento = new RDMDesdobramento();
			    ArrayList<RDMDesdobramento> listaDesdobramentos =  objDesdobramento.montarListaRDMsDesdobramento(listaLinhasLidas);	
			    Metric objMetric2 = new Metric("Number of Change Requests (Origem Type) ", Integer.toString(listaRDMs.size()));
			    MemoryApplication.setMetrics(objMetric2);				
				Metric objMetric3 = new Metric("Number of Change Requests (Desdobramento Type) ", Integer.toString(listaDesdobramentos.size()));
				MemoryApplication.setMetrics(objMetric3);
			    
			    if(MemoryApplication.getListTypesMaintenance().size() > 0)
			    {
			    	ArrayList<Arquivo> listRDMs = request.filterChangeRequestsByType(listaRDMs);
			    	ArrayList<Arquivo> listDesdobramentos = request.filterChangeRequestsByType(listaDesdobramentos);
			    	
					ArrayList<Arquivo> listRDMs1 = objArquivo.eliminateCanceledAndNotPerformed(listRDMs);
					ArrayList<Arquivo> listDesdobramento1 = objArquivo.eliminateCanceledAndNotPerformed(listDesdobramentos);					
					Metric objMetric4 = new Metric("Number of Change Requests (Origem Type) with different status of 'Cancel' and 'Executed'", Integer.toString(listRDMs1.size()));
					Metric objMetric5 = new Metric("Number of Change Requests (Desdobramento Type) with different status of 'Cancel' and 'Executed'", Integer.toString(listDesdobramento1.size()));
					MemoryApplication.setMetrics(objMetric4);
					MemoryApplication.setMetrics(objMetric5);
					
					ArrayList<Arquivo> listChangeRequestFull = objArquivo.joinLists(listRDMs1, listDesdobramento1);
					MemoryDriverCEO.setListChangeRequests(listChangeRequestFull);		
					
					Percent percent = new Percent();
					Metric objMetric6 = new Metric("Hit Percentage Estimation Module (Origem Type)", percent.formatInputPercentage(percent.calculateEstimatedPercentage(listRDMs1)));
					Metric objMetric7 = new Metric("Hit Percentage Estimation Module (Desdobramento Type)", percent.formatInputPercentage(percent.calculateEstimatedPercentage(listDesdobramento1)));
					Metric objMetric8 = new Metric("Hit Percentage Estimation Module (Total)", percent.formatInputPercentage(percent.calculateEstimatedPercentage(listChangeRequestFull)));
					MemoryApplication.setMetrics(objMetric6);
					MemoryApplication.setMetrics(objMetric7);
					MemoryApplication.setMetrics(objMetric8);					
					
					MaintenanceType objType = new MaintenanceType();					
					ArrayList<String> listFilterCorretive = objType.getMaintenanceTypesByID(1);
					ArrayList<String> listFilterAdaptative = objType.getMaintenanceTypesByID(2);
					ArrayList<String> listFilterEvolutionary = objType.getMaintenanceTypesByID(3);
					Metric objMetric9 = new Metric("Number of Corrective Maintenance", Integer.toString(objArquivo.countRequestsByType(listFilterCorretive, MemoryDriverCEO.getListChangeRequests())));
					Metric objMetric10 = new Metric("Number of Adaptative Maintenance", Integer.toString(objArquivo.countRequestsByType(listFilterAdaptative, MemoryDriverCEO.getListChangeRequests())));
					Metric objMetric11 = new Metric("Number of Evolutive Maintenance", Integer.toString(objArquivo.countRequestsByType(listFilterEvolutionary, MemoryDriverCEO.getListChangeRequests())));
					MemoryApplication.setMetrics(objMetric9);
					MemoryApplication.setMetrics(objMetric10);
					MemoryApplication.setMetrics(objMetric11);
					
					SetModules objModule = new SetModules();
					MemoryApplication.setListModules(objModule.returnListModulesDriverCEO(MemoryDriverCEO.getListChangeRequests()));
					SortModules objSort = new SortModules();
					Collections.sort(MemoryApplication.getListModules(), objSort);
					
					Metric objMetric12 = new Metric("Total Number of Change Requests", Integer.toString(MemoryDriverCEO.getListChangeRequests().size()));
					Metric objMetric13 = new Metric("Number of Modules that have set maintenance", Integer.toString(MemoryApplication.getListModules().size()));
					MemoryApplication.setMetrics(objMetric12);					
					MemoryApplication.setMetrics(objMetric13);
					
					FrequencyDistribution math = new FrequencyDistribution();
					Boolean status = math.startStatisticalProcess();
					if(status)
					{
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
		
	   public Boolean readReportMode1(String path)
	   {
		   try
		   {
			   ArrayList<Arquivo> listaLinhasLidas =  readLines(path);		    
			   Arquivo objArquivo = new Arquivo();
			   
			   MemoryDriverCEO.setListChangeRequests(listaLinhasLidas);
			   MemoryApplication.setMaintenanceTypes(objArquivo.getMaintenanceTypeList(listaLinhasLidas)); 
			   
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
			   ArrayList<Arquivo> listaLinhasLidas =  readLines(path);			   
			   MemoryDriverCEO.setListChangeRequests(listaLinhasLidas);	
			   
			   listaLinhasLidas = null;
			   
		       return true;
		   }
		   catch(Exception e)
		   {
			   return false;
		   }
	   }
	   
	   public Boolean verifyComponentOccur(String path, String componentName)
	   {
	       Workbook workbook = null;
	       listaObjetos = new ArrayList<>();
			try 
			{
				workbook = Workbook.getWorkbook(new File(path));
				Sheet sheet = workbook.getSheet(0);
				int linhas = sheet.getRows();
		        for(int i = 1; i < linhas; i++)
		        {
		        	Cell[] linha;
					linha = sheet.getRow(i);
					if(!linha[0].getContents().equals(""))
					{
					   Arquivo objLinhaLida = makeObjectLine(linha);
					   if (!objLinhaLida.getModulo().equals("") && !objLinhaLida.getComponente().equals("") && objLinhaLida.getComponente().equals(componentName))
				       {
				          return true;
				       }
					}       	
		        }
		        return false;
			             
			} 
			catch (BiffException e) {
				
				JOptionPane.showMessageDialog(null, "Impossible  Read Plan");
				return null;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Impossible  Read Plan");
				return null;
			}      
	       
	   }
	   
	   private ArrayList<Arquivo> readLines(String path)
	   {    	
	       
	       Workbook workbook = null;
	       listaObjetos = new ArrayList<>();
			try 
			{
				workbook = Workbook.getWorkbook(new File(path));
				Sheet sheet = workbook.getSheet(0);
				int linhas = sheet.getRows();
		        for(int i = 1; i < linhas; i++)
		        {
		        	Cell[] linha;
					linha = sheet.getRow(i);
					if(!linha[0].getContents().equals(""))
					{
					   Arquivo objLinhaLida = makeObjectLine(linha);
					   if (!objLinhaLida.getModulo().equals("") && !objLinhaLida.getComponente().equals(""))
				       {
				          listaObjetos.add(objLinhaLida);
				       }
					}       	
		        }
		        return listaObjetos;
			             
			} 
			catch (BiffException e) {
				//System.out.println("GiveMe Views say: Impossible  Read Plan");
				JOptionPane.showMessageDialog(null, "Impossible  Read Plan");
				return null;
			} catch (IOException e) {
				//System.out.println("GiveMe Views say: Impossible  Read Plan");
				JOptionPane.showMessageDialog(null, "Impossible  Read Plan");
				return null;
			}      
	       
	   }
   
	   private Arquivo makeObjectLine(Cell[] linha)
	   {
	   	   Arquivo objLinhaLida = new Arquivo();
	       objLinhaLida.setNumRDM(Integer.parseInt(linha[0].getContents()));
	       objLinhaLida.setDesdobramento(Integer.parseInt(linha[1].getContents())); 
	       objLinhaLida.setCodEmpresa(linha[2].getContents());
	       objLinhaLida.setDataAbertura(linha[3].getContents());
	       objLinhaLida.setHoraAbertura(linha[4].getContents());  
	       objLinhaLida.setTipo(linha[5].getContents());
	       objLinhaLida.setOrigem(linha[6].getContents());
	       objLinhaLida.setModuloRDM(linha[7].getContents());
	       objLinhaLida.setModulo(linha[8].getContents());
	       objLinhaLida.setComponente(linha[9].getContents());
	       objLinhaLida.setEquipe(linha[10].getContents());
	       objLinhaLida.setSituacaoRDM(linha[11].getContents());
	       objLinhaLida.setDataConclusao(linha[12].getContents());
	       objLinhaLida.setHoraConclusao(linha[13].getContents());      
	       return objLinhaLida;
	   }
}
