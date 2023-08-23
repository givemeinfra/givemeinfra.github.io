package givemeviews.filtering;

import givemeviews.model.Projeto;
import givemeviews.model.SetModules;
import givemeviews.persistence.MemoryApplication;
import givemetrace.provider.MasterProvider;

import java.util.ArrayList;
import java.util.Stack;

public class FilterStatistics 
{
	private static Stack<Integer> stack = new Stack<>();
	private static ArrayList<Integer> listFinal= new ArrayList<>();
	
	public ArrayList<String> filterStatisticByRange(String entity, float statisticRange) // return list or null
	{
		if(statisticRange > 0)
		{
			float[][] masterProbabilityTable = MemoryApplication.getMasterProbabilityTable();
			ArrayList<SetModules> listModules = MemoryApplication.getListModulesAndComponents();
			if(masterProbabilityTable != null && listModules != null)
			{
				try
				{
					listFinal.removeAll(listFinal);
					stack.removeAllElements();
					loadingStack(entity, masterProbabilityTable, listModules, statisticRange);
					ArrayList<String> listFinal = getRelatedComponents(entity, masterProbabilityTable, listModules, statisticRange);
					if(listFinal.size() > 0)
						return listFinal;
					else
						return null;
					
				}
				catch(Exception e){
					return null;
				}
			}
			else
				return null;
		}
		else
			return null;
	}
	
	private void loadingStack(String entity, float[][] masterProbabilityTable, ArrayList<SetModules> listModules, float statisticRange)
	{
		for(int i = 0; i < listModules.size(); i++)
		{
			if(listModules.get(i).getComponenteAssociado().equals(entity))
			{
				stack.push(i);
				for (int j = 0; j < listModules.size(); j++)
				{
					if(i != j && masterProbabilityTable[i][j] >= statisticRange)
						stack.push(j);
				}
				break;
			}
		}
	}
	
	private ArrayList<String> getRelatedComponents(String entity, float[][] masterProbabilityTable, ArrayList<SetModules> listModules, float statisticRange)
	{
		ArrayList<String> fullList = new ArrayList<>();
		while(!stack.isEmpty())
		{
			int atual = stack.pop();
			for(int i = 0; i < listModules.size(); i++)
			{
				if(i == atual)
				{
					listFinal.add(atual);
					for(int j = 0; j < listModules.size(); j++)
					{
						if(masterProbabilityTable[i][j] > 0 && masterProbabilityTable[i][j] >= statisticRange && !verifyIfIDExist(j))
							listFinal.add(j);
					}
					break;
				}				
			}
		}
		for(int i = 0; i < listFinal.size(); i++)
			fullList.add(listModules.get(listFinal.get(i)).getComponenteAssociado());
			
		return fullList;
	}
	
	private Boolean verifyIfIDExist(int id)
	{
		for(int i = 0; i < listFinal.size(); i++)
		{
			if(listFinal.get(i) == id)
				return true;
		}
		for(int i = 0; i < stack.size(); i++)
		{
			if(stack.elementAt(i) == id)
				return true;
		}
		return false;
	}
	
	

	public ArrayList<String[]> filterModificationsByCommit(int commitNumber) 
	{
		try
		{		
			ArrayList<String[]> listModifications = null;
			
			// get xml path
			String aux = MemoryApplication.getGmmRepositoryPath();
			aux = aux.replace("\\", "/");	    
			String pathToRead = aux + "/Brain/givemetrace.xml";
			
			// get repository informations
			Projeto project = MemoryApplication.getProject();		
			MasterProvider provider = new MasterProvider();
			ArrayList<String> listRepoInfos = provider.getObjectRepository(pathToRead, project.getNomeProjeto());
			
			// verify infos
			if(listRepoInfos != null && listRepoInfos.size() > 0)
			{
				ArrayList<String> listMethod = null;
				
				// verify repository type
				if(listRepoInfos.get(2).equals("SVN"))
				{
					// call gitToArrayMethod
					listMethod = provider.svnToArrayMethod(listRepoInfos.get(1), Integer.toString(commitNumber), listRepoInfos.get(4), listRepoInfos.get(5));
					listModifications = new ArrayList<>();
				}
				if(listRepoInfos.get(2).equals("GIT"))
				{
					// call gitToArrayMethod
					listMethod = provider.gitToArrayMethod(listRepoInfos.get(1), Integer.toString(commitNumber));
					listModifications = new ArrayList<>();
				}
				else
					return null;
				
				// loading methods in the listModifications with respective colors
				for(int i = 0; i < listMethod.size(); i++)
				{
					String modificationItem[] = new String[2];
					modificationItem[0] = listMethod.get(i);
					modificationItem[1] = "1"; // 1 equals color blue
					listModifications.add(modificationItem);
				}
				
			}
			else
				return null;
			
			return listModifications;
		
		}
		catch(Exception e)
		{
			return null;
		}
	}		
	
}
