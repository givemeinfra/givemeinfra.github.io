package givemeviews.statisticalanalysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import givemeviews.filtering.FilterCEORequests;
import givemeviews.metrics.Percent;
import givemeviews.model.Arquivo;
import givemeviews.model.ObjectLog;
import givemeviews.model.Projeto;
import givemeviews.model.SetModules;
import givemeviews.model.SortPercent;
import givemeviews.persistence.MemoryApplication;
import givemeviews.persistence.MemoryDriverCEO;
import givemeviews.persistence.MemoryMantis;
import givemeviews.views.provider.MasterProvider;
import implementacoes.tree.GeraTreeXML;
import implementacoes.radial.GeraRadialXML;

public class FrequencyDistribution 
{
	private DecimalFormat formatter = new DecimalFormat("0.00"); 

	public Boolean startStatisticalProcess()
    {
    	/* OBS:
		   	Deverá fazer todos os calculos, gerar todas as matrizes de dados e deixá-la em memoria.
    		Quando for abrir uma visualização, a classe provider respectiva deverá montar as informações como desejar		
    	*/
		
		switch (MemoryApplication.getProject().getFerramentaGMM()) {
		case "Driver Empresa 1":
			try
	    	{
	    		SetModules module = new SetModules();
	    		FilterCEORequests filter = new FilterCEORequests();
	    		
	        	ArrayList<SetModules> listFullModulesAndComponents = module.returnListModulesAndComponents(MemoryDriverCEO.getListChangeRequests(), ""); 
	        	ArrayList<SetModules> listMatrixViewModulesAndComponents = module.returnListModulesAndComponents(MemoryDriverCEO.getListChangeRequests(), MemoryApplication.getProject().getNomeProjeto());
	        	
	        	MemoryApplication.setListModulesAndComponents(listFullModulesAndComponents);
	        	MemoryApplication.setMatrixListModulesAndComponents(listMatrixViewModulesAndComponents);        	
	        	
	        	ArrayList<Arquivo> copyListChangeRequestesFull = filter.copy(MemoryDriverCEO.getListChangeRequests());        	        	
	        	ArrayList<Arquivo> listChangeRequestesByModule = filter.filterChangeRequestsByModule(copyListChangeRequestesFull, MemoryApplication.getProject().getNomeProjeto());
	        	        	
	        	// generate MasterProbabilityTable and save
	        	MemoryApplication.setMasterProbabilityTable(generateFrequencies(MemoryDriverCEO.getListChangeRequests(), listFullModulesAndComponents));
	        	// generate MatrixProbabilityTable and save
	        	MemoryApplication.setMatrixViewProbabilityTable(generateFrequencies(listChangeRequestesByModule, listMatrixViewModulesAndComponents));	
	        	
	        	String aux = MemoryApplication.getGmmRepositoryPath();
				aux = aux.replace("\\", "/");
				
	        	// Create TreeView XML        	
				String pathXML1 = aux + "/Brain/treeview.xml";
	        	createTreeViewSetDate(pathXML1, MemoryApplication.getProject(), listFullModulesAndComponents, MemoryApplication.getMasterProbabilityTable());
	        	
	        	// Create Radial XML
	        	String pathXML2 = aux + "/Brain/radialview.xml";
	        	createRadialViewSetData(pathXML2, MemoryApplication.getProject(), listFullModulesAndComponents, MemoryApplication.getMasterProbabilityTable());
	        	
	        	String pathLog = aux + "/Brain/log.txt";
	        	String pathLogMin = aux + "/Brain/logMin.txt";
	        	
	        	generateLogs(pathLog, pathLogMin, listFullModulesAndComponents, MemoryApplication.getMasterProbabilityTable());
	        	
	        	// Process and save the objects node from GiveMe Views
	        	MasterProvider provider = new MasterProvider();
	        	provider.buildObjectsNode();       
	        	
	        	System.gc();
	        	
	        	return true;
	        	        	
	    	}
	    	catch(Exception e)
	    	{    		
	    		return false;
	    	}
		case "Mantis":
			ArrayList<SetModules> listFullModulesAndComponents = null;
			ArrayList<SetModules> listMatrixViewModulesAndComponents = null;
			
			try
	    	{
	    		// clear
				//MemoryApplication.getListModulesAndComponents().removeAll(MemoryApplication.getListModulesAndComponents());
				//MemoryApplication.getMatrixListModulesAndComponents().removeAll(MemoryApplication.getMatrixListModulesAndComponents());
				MemoryApplication.clearMasterProbabilityTable();
				MemoryApplication.clearMatrixProbabilityTable();
				
				
	        	listFullModulesAndComponents = MemoryMantis.getListMantisComponents();
	        	listMatrixViewModulesAndComponents = MemoryMantis.getListMantisComponents();
	        	
	        	MemoryApplication.setListModulesAndComponents(listFullModulesAndComponents);
	        	MemoryApplication.setMatrixListModulesAndComponents(listMatrixViewModulesAndComponents);        	
	        	
	        	//ArrayList<MantisCase> copyListChangeRequestesFull = filter.copy(MemoryDriverCEO.getListChangeRequests());        	        	
	        	//ArrayList<MantisCase> listChangeRequestesByModule = filter.filterChangeRequestsByModule(copyListChangeRequestesFull, MemoryApplication.getProject().getNomeProjeto());
	        	        	
	        	// generate MasterProbabilityTable and save
	        	MemoryApplication.setMasterProbabilityTable(generateMantisFrequencies(MemoryMantis.getListGITorSVNlog(), listFullModulesAndComponents));
	        	// generate MatrixProbabilityTable and save
	        	MemoryApplication.setMatrixViewProbabilityTable(generateMantisFrequencies(MemoryMantis.getListGITorSVNlog(), listMatrixViewModulesAndComponents));	
	        	
	        	String aux = MemoryApplication.getGmmRepositoryPath();
				aux = aux.replace("\\", "/");
				
	        	// Create TreeView XML        	
				String pathXML1 = aux + "/Brain/treeview.xml";
	        	createTreeViewSetDate(pathXML1, MemoryApplication.getProject(), listFullModulesAndComponents, MemoryApplication.getMasterProbabilityTable());
	        	
	        	// Create Radial XML
	        	String pathXML2 = aux + "/Brain/radialview.xml";
	        	createRadialViewSetData(pathXML2, MemoryApplication.getProject(), listFullModulesAndComponents, MemoryApplication.getMasterProbabilityTable());
	        	
	        	String pathLog = aux + "/Brain/log.txt";
	        	String pathLogMin = aux + "/Brain/logMin.txt";
	        	
	        	generateLogs(pathLog, pathLogMin, listFullModulesAndComponents, MemoryApplication.getMasterProbabilityTable());
	        	
	        	// Process and save the objects node from GiveMe Views
	        	MasterProvider provider = new MasterProvider();
	        	provider.buildObjectsNode(); 
	        	
	        	listFullModulesAndComponents = null;
	        	listMatrixViewModulesAndComponents = null;
	        	
	        	System.gc();
	        	
	        	return true;
	        	        	
	    	}
	    	catch(Exception e)
	    	{
	    		listFullModulesAndComponents = null;
	        	listMatrixViewModulesAndComponents = null;
	        	System.gc();
	        	
	    		return false;
	    	}
		default:
			return false;
		}		
    	
    }
	
	// Driver Empresa 1 Methods //////////////////////////////////////////////////////////////////////////////////////////
	
	private  float[][] generateFrequencies(ArrayList<Arquivo> listChangeRequests, ArrayList<SetModules> listModulesAndComponents)
    {
    	return createTraceabilityTable(listChangeRequests, listModulesAndComponents);
    }
	
	private void createTreeViewSetDate(String pathXML1, Projeto project, ArrayList<SetModules> list, float[][] masterProbabilityTable)
    {
    	ArrayList<String> finalList = new ArrayList<>();
    	ArrayList<SortPercent> auxFinalList = new ArrayList<>();
    	for(int i = 0; i < list.size(); i++)
    	{
    		if(project.getNomeProjeto().equals(list.get(i).getNome()) && project.getNomeComponente().equals(list.get(i).getComponenteAssociado()))
    		{
    			for(int j = 0; j < list.size(); j++)
        		{
        			if(i != j && masterProbabilityTable[i][j] > 0)
        			{
        				//finalList.add(list.get(j).getNome() + "/" + list.get(j).getComponenteAssociado() + " " + "(" + masterProbabilityTable[i][j] + "%)");
        				SortPercent aux = new SortPercent(list.get(j).getNome(), list.get(j).getComponenteAssociado(), masterProbabilityTable[i][j]);
        				auxFinalList.add(aux);
        			}
        		}
    		}
    	}
    	
    	// Sorting nodeList
    	Collections.sort(auxFinalList);    	
    		
    	for(int j = 0; j < auxFinalList.size(); j++)
    	{
    		finalList.add(auxFinalList.get(j).getNome() + "/" + auxFinalList.get(j).getComponenteAssociado() + " " + "(" + auxFinalList.get(j).getPorcentagemAssociada() + "%)");
    	}

    	GeraTreeXML objXMl = new GeraTreeXML();
    	String moduleANDcomponent = MemoryApplication.getProject().getNomeProjeto() + "/" + MemoryApplication.getProject().getNomeComponente();
    	objXMl.createTreeViewXML(pathXML1, moduleANDcomponent, finalList);
    	
    	finalList = null;
    	auxFinalList = null;    	
    }
	
	private void createRadialViewSetData(String pathXML2, Projeto project, ArrayList<SetModules> list, float[][] masterProbabilityTable) 
    {    	
    	ArrayList<String> nodesList = new ArrayList<>();
    	ArrayList<String> edgeList = new ArrayList<>();
    	ArrayList<SortPercent> nodesAuxList= new ArrayList<>();
    	for(int i = 0; i < list.size(); i++)
    	{
    		if(project.getNomeProjeto().equals(list.get(i).getNome()) && project.getNomeComponente().equals(list.get(i).getComponenteAssociado()))
    		{
    			for(int j = 0; j < list.size(); j++)
        		{
        			if(i != j && masterProbabilityTable[i][j] > 0)
        			{        				
        				SortPercent aux = new SortPercent(list.get(j).getNome(), list.get(j).getComponenteAssociado(), masterProbabilityTable[i][j]);
        				nodesAuxList.add(aux);        				
        			}
        		}
    			break;
    		}
    	}
    	
    	// Sorting nodeList
    	Collections.sort(nodesAuxList);
    	
    	// create minimal list
    	int paramLimit = 10;
    	int limite = 0;
    	if(nodesAuxList.size() > paramLimit)
    		limite = paramLimit;
    	else
    		limite = nodesAuxList.size();
    		
    	for(int j = 0; j < limite; j++)
    	{
    		String node = "";
			node += ("<node id=" + '"' + j + '"' + ">") + "\n";
			node += ("<data key=" + '"' + "name" + '"' + ">" + (nodesAuxList.get(j).getNome() + "/" + nodesAuxList.get(j).getComponenteAssociado() + " " + "( " + nodesAuxList.get(j).getPorcentagemAssociada() + " %)") + "</data>") + "\n";
			node += ("<data key=" + '"' + "gender" + '"' + ">A</data>") + "\n";
			node += ("</node>") + "\n";
		    nodesList.add(node);
		    
		    // create string edge
			String edge = "<edge source=" + '"' + -1 + '"' + " target=" + '"' + j + '"' + "></edge>"; // -1 is (project.getNome + project.getComponenteAssociado) id
			edgeList.add(edge);
    	}    	
    	
    	GeraRadialXML objXMl = new GeraRadialXML();
    	objXMl.createRadialViewXML(pathXML2, MemoryApplication.getProject().getNomeProjeto(), MemoryApplication.getProject().getNomeComponente(), nodesList, edgeList);
    	
    	nodesList = null;
    	edgeList = null;
    	
    }	
    
	private void generateLogs(String pathLog, String pathLogMin, ArrayList<SetModules> listFullModulesAndComponents, float[][] masterProbabilityTable) 
	{
		File file = new File(pathLog);  
        BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			
			for(int i = 0; i < listFullModulesAndComponents.size(); i++)
			{
				for(int j = 0; j < listFullModulesAndComponents.size(); j++)
				{
					writer.write("::" + masterProbabilityTable[i][j]);			        
				}
				writer.write("::");
				if(! (i == listFullModulesAndComponents.size() - 1) )
					writer.newLine();
			}			  
	       
	        writer.close(); 
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
		File file2 = new File(pathLogMin);  
        BufferedWriter writer2;
		try {
			writer2 = new BufferedWriter(new FileWriter(file2));
			
			for(int i = 0; i < listFullModulesAndComponents.size(); i++)
			{
				writer2.write(listFullModulesAndComponents.get(i).getNome() + ":::" + listFullModulesAndComponents.get(i).getComponenteAssociado());
				if(! (i == listFullModulesAndComponents.size() - 1) )
					writer2.newLine();
			}       
	        writer2.close(); 
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	// End Driver Empresa 1 Methods //////////////////////////////////////////////////////////////////////////////////////////
	
	private  float[][] generateMantisFrequencies(ArrayList<ObjectLog> listChangeRequests, ArrayList<SetModules> listModulesAndComponents)
    {
    	return createMantisTraceabilityTable(listChangeRequests, listModulesAndComponents);
    }
	
	private  float[][] createMantisTraceabilityTable(ArrayList<ObjectLog> listChangeRequests, ArrayList<SetModules> listModulesAndComponents)
    {
		ObjectLog objCase;

        int tracebilityTable[][] = new int[listModulesAndComponents.size()][listModulesAndComponents.size()]; // contain counted relation between modules/components
        
        for (int i = 0; i < listChangeRequests.size(); i++)
        {
        	objCase = (ObjectLog)listChangeRequests.get(i);
            ArrayList<String> listComponents = objCase.getListLogComponents();
            for (int j = 0; j < listComponents.size(); j++) // tanto faz, lista de componentes ou de modulos .size()
            {
                String componenteAtual = (String)listComponents.get(j);
                String moduloAtual = MemoryApplication.getProject().getNomeProjeto();
                for (int l = 0; l < listComponents.size(); l++)
                {
                    int linha = getIndex(listModulesAndComponents, componenteAtual, moduloAtual);
                    int coluna = getIndex(listModulesAndComponents, (String)listComponents.get(l), moduloAtual);
                    tracebilityTable[linha][coluna]++;
                }
            }
        }
        
        // create the matrix contains all calculated probabilities 
        SetModules objModule = new SetModules();
        float numManutencoesTotais = (float)objModule.getTotalMantisMaintenanceNumber(listChangeRequests);               
        return createMasterProbabilityTable(listModulesAndComponents, tracebilityTable, listModulesAndComponents.size(), (int)numManutencoesTotais);
        
        //return null;
    }
    
	private int getIndex(ArrayList<SetModules> listComps, String scannerComponent, String scannerModule)
    {
        for (int i = 0; i < listComps.size(); i++)
        {
            SetModules objModulo = (SetModules)listComps.get(i);
            if (objModulo.getComponenteAssociado().equals(scannerComponent) && objModulo.getNome().equals(scannerModule))
            {
                return i;
            }
        }
        return -1;
    }	
    
    private  float[][] createTraceabilityTable(ArrayList<Arquivo> listChangeRequests, ArrayList<SetModules> listModulesAndComponents)
    {
        Arquivo objRDMIntercalada;

        int tracebilityTable[][] = new int[listModulesAndComponents.size()][listModulesAndComponents.size()]; // contain counted relation between modules/components
        
        for (int i = 0; i < listChangeRequests.size(); i++)
        {
            objRDMIntercalada = (Arquivo)listChangeRequests.get(i);
            ArrayList<String> listaComponentesDaRDMintercalada = objRDMIntercalada.getListaComponentes();
            ArrayList<String> listaModulosDaRDMintercalada = objRDMIntercalada.getListaModulos();
            for (int j = 0; j < listaComponentesDaRDMintercalada.size(); j++) // tanto faz, lista de componentes ou de modulos .size()
            {
                String componenteAtual = (String)listaComponentesDaRDMintercalada.get(j);
                String moduloAtual = (String)listaModulosDaRDMintercalada.get(j);
                for (int l = 0; l < listaComponentesDaRDMintercalada.size(); l++)
                {
                    int linha = getIndex(listModulesAndComponents, componenteAtual, moduloAtual);
                    int coluna = getIndex(listModulesAndComponents, (String)listaComponentesDaRDMintercalada.get(l), (String)listaModulosDaRDMintercalada.get(l));
                    tracebilityTable[linha][coluna]++;
                }
            }
        }
        
        // create the matrix contains all calculated probabilities 
        SetModules objModule = new SetModules();
        float numManutencoesTotais = (float)objModule.getTotalMaintenanceNumber(listChangeRequests);               
        return createMasterProbabilityTable(listModulesAndComponents, tracebilityTable, listModulesAndComponents.size(), (int)numManutencoesTotais);
        
    } 
       
    private  float[][] createMasterProbabilityTable(ArrayList<SetModules> listModulesAndComponents, int[][] tracebilityTable, int widthSquareMatrix, int calculatedSimpleFrequency)
    {
        float[][] probalityTable = new float[widthSquareMatrix][widthSquareMatrix];
        Percent objPercent = new Percent();
        
        for (int i = 0; i < widthSquareMatrix; i++)
        {
            for (int j = 0; j < widthSquareMatrix; j++)
            {
                if (tracebilityTable[i][j] != 0 && i != j)
                {
                    float totalNumberMaintenances = tracebilityTable[i][i];
                    float totalSetMaintenances = tracebilityTable[i][j];
                    float cell = objPercent.getMaintenancePercentByComponent(totalNumberMaintenances, totalSetMaintenances);
                    String cellAux = (String)formatter.format(cell);
                    cellAux = cellAux.replace(",", ".");
                    cell = Float.parseFloat(cellAux);                    
                    probalityTable[i][j] = cell;
                }
                else if (tracebilityTable[i][j] != 0 && i == j)
                {
                    float numeroManutencoesTotais = calculatedSimpleFrequency; // Simple Frequency value
                    float numeroManutencoesSofridas = tracebilityTable[i][j];
                    float cell = objPercent.getMaintenancePercentByComponent(numeroManutencoesTotais, numeroManutencoesSofridas);
                    String cellAux = (String)formatter.format(cell);
                    cellAux = cellAux.replace(",", ".");
                    cell = Float.parseFloat(cellAux);                    
                    probalityTable[i][j] = cell;
                }
            }
        }       
        return probalityTable;        
    } 
    
    
}
