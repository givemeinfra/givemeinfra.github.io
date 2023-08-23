package givemeviews.views.provider;

import givemeviews.model.Arquivo;
import givemeviews.model.Projeto;
import givemeviews.model.SetModules;
import givemeviews.persistence.MemoryApplication;
import givemeviews.persistence.MemoryDriverCEO;
import givemeviews.persistence.MemoryMantis;

import java.util.ArrayList;

public class ModulesComponentsProvider 
{
	public ModulesComponentsProvider()
	{
		
	}
	
	public ArrayList<SetModules> providerSetModules()
	{
		MemoryApplication.getMemory();
		return MemoryApplication.getListModules();
	}
	
	public Projeto providerSetModule()
	{
		MemoryApplication.getMemory();
		return MemoryApplication.getProject();
	}
	
	public void providerSetModuleAndComponent(String module, String component)
	{
		Projeto objModule = new Projeto(module, "", component, "");
		MemoryApplication.setProject(objModule);
	}
	
	public ArrayList<Arquivo> providerChangeRequestList()
	{
		MemoryApplication.getMemory();
		return MemoryDriverCEO.getListChangeRequests();
	}
	
	public ArrayList<SetModules> providerProjectList()
	{
		ArrayList<SetModules> list = MemoryMantis.getListMantisComponents();
		ArrayList<String> listAux = new ArrayList<>();
		ArrayList<SetModules> listFinal = new ArrayList<>();
		Boolean found = false;
		for(int i = 0; i < list.size(); i++)
		{
			for(int l = 0; l < listAux.size(); l++)
			{
				if(listAux.get(l).equals(list.get(i).getNome()))
				{
					found = true;
					break;
				}
			}
			if(found == false)
			{
				listAux.add(list.get(i).getNome()); // add project names
			}
			else
				found = false;
		}
		for(int i = 0; i < listAux.size(); i++)
		{
			SetModules atualProject = new SetModules(listAux.get(i), 0);
			for(int j = 0; j < MemoryMantis.getListCases().size(); j++)
			{
				if(MemoryMantis.getListCases().get(j).getProjeto().equals(listAux.get(i)))
				{
					atualProject.setOcorrencia(atualProject.getOcorrencia() + 1);
				}
			}
			listFinal.add(atualProject);
		}
		
		return listFinal;
	}
	
	public ArrayList<SetModules> providerProjectComponentsList(String project)
	{
		ArrayList<SetModules> list = MemoryMantis.getListMantisComponents();
		ArrayList<SetModules> listFinal = new ArrayList<>();
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).getNome().equals(project))
			{
				listFinal.add(list.get(i));
			}				
		}
		return listFinal;
	}

}
