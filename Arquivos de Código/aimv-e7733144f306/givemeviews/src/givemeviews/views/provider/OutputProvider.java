package givemeviews.views.provider;

import givemeviews.metrics.Metric;
import givemeviews.model.Projeto;
import givemeviews.model.SetModules;
import givemeviews.persistence.MemoryApplication;

import java.util.ArrayList;

import org.eclipse.jface.viewers.deferred.SetModel;

public class OutputProvider 
{
	
	public OutputProvider()
	{
		
	}
	
	public ArrayList<Metric> providerListMetrics()
	{
		return MemoryApplication.getListMetrics();
	}
	
	public ArrayList<SetModules> returnFrequenciesByModuleAndComponent()
	{
		Projeto objSelectedModule = MemoryApplication.getProject();
		ArrayList<SetModules> listModulesAndComponents = MemoryApplication.getMatrixListModulesAndComponents();	
		float [][] matrix = MemoryApplication.getMatrixViewProbabilityTable();
		Boolean status = false;
		
		ArrayList<SetModules> listFinal = new ArrayList<>();		
		for(int i = 0; i < listModulesAndComponents.size(); i++)
		{
			if(listModulesAndComponents.get(i).getNome().equals(objSelectedModule.getNomeProjeto()) && listModulesAndComponents.get(i).getComponenteAssociado().equals(objSelectedModule.getNomeComponente()))
			{
				for(int j = 0; j < listModulesAndComponents.size(); j++)
				{
					SetModules objAux = new SetModules(listModulesAndComponents.get(j).getNome(), listModulesAndComponents.get(j).getComponenteAssociado(), matrix[i][j]);
					listFinal.add(objAux);	
				}
				status = true;			
			}
			if(status)
				break;
		}
		return listFinal;
	}
	
	public Boolean providerProgramDataSource()
	{
		MemoryApplication.getMemory();
		return MemoryApplication.getGeneratedSource();
	}
	
	public Projeto providerSelectedModule()
	{
		MemoryApplication.getMemory();
		return MemoryApplication.getProject();
	}

}
