
package givemeviews.views.provider;

import givemeviews.model.Arquivo;
import givemeviews.model.MantisCase;
import givemeviews.model.SetModules;
import givemeviews.persistence.MemoryApplication;
import givemeviews.persistence.MemoryDriverCEO;
import givemeviews.persistence.MemoryMantis;
import givemeviews.util.MergeSort;

import java.util.ArrayList;

import aimv.modeling.Node;

public class GridViewProvider {
	
	public GridViewProvider()
	{
		
	}
	
	public ArrayList<Node> buildingNodes()
	{
		ArrayList<Node> listNodes = new ArrayList<>();
	
		// Modules and Components list
		ArrayList<SetModules> listFullModulosComponentes = MemoryApplication.getListModulesAndComponents();
        
		int manutencoesSofridasModulo = 0;		
		int manutencoesSofridasComponente = 0 ;
		int manutencoesTotais = 0;
		
		ArrayList<SetModules> listModules = null;
		SetModules objModule = new SetModules();
		
		switch (MemoryApplication.getProject().getFerramentaGMM()) 
		{
			case "Driver Empresa 1":
				manutencoesTotais = objModule.getTotalMaintenanceNumber(MemoryDriverCEO.getListChangeRequests());				
				listModules = MemoryApplication.getListModules();
				
				// Insert new Nodes in the Group.
				for(int j = 0; j < listFullModulosComponentes.size(); j++)
				{
					Node nodeAux = new Node(listFullModulosComponentes.get(j).getNome() + "/" + listFullModulosComponentes.get(j).getComponenteAssociado());
								
					manutencoesSofridasModulo = returnOccurrenceByModule(listModules, listFullModulosComponentes.get(j).getNome());
					manutencoesSofridasComponente = returnOccurrenceByComponent(listFullModulosComponentes.get(j).getNome(), listFullModulosComponentes.get(j).getComponenteAssociado());
					
					nodeAux.setProperty("Complexity", listFullModulosComponentes.get(j).getNome() + "("+ manutencoesSofridasModulo + "/" + manutencoesTotais + ")|" + listFullModulosComponentes.get(j).getComponenteAssociado() + "(" + manutencoesSofridasComponente + "/" + manutencoesTotais + ")");
					
					// Definindo Propriedades
					nodeAux.setProperty("selection", false); // essa propriedade permite visualizar o node no Propriety view.
					//nodeAux.setProperty("coupling", 10); //diz que o node se relaciona com outros 10 componentes.
					nodeAux.setProperty("about", "ceo software"); //posso definir a propriedade que eu quiser pra um node
					nodeAux.setProperty("Concerns", null); // compatibilidade com o collaborative sourceminer
					// Fim código propriedades

					listNodes.add(nodeAux);
				}	
				
			case "Mantis":
				//manutencoesTotais = objModule.getTotalMantisMaintenanceNumber(MemoryMantis.getListGITorSVNlog());	
				manutencoesTotais = MemoryMantis.getListCases().size();
				listModules = MemoryApplication.getListModules();
				
				// Insert new Nodes in the Group.
				for(int j = 0; j < listFullModulosComponentes.size(); j++)
				{
					Node nodeAux = new Node(listFullModulosComponentes.get(j).getNome() + "/" + listFullModulosComponentes.get(j).getComponenteAssociado());
								
					manutencoesSofridasModulo = countMaintenanceByMantisProject(listFullModulosComponentes.get(j).getNome(), MemoryMantis.getListCases());
					manutencoesSofridasComponente = listFullModulosComponentes.get(j).getOcorrencia();
					
					nodeAux.setProperty("Complexity", listFullModulosComponentes.get(j).getNome() + "("+ manutencoesSofridasModulo + "/" + manutencoesTotais + ")|" + listFullModulosComponentes.get(j).getComponenteAssociado() + "(" + manutencoesSofridasComponente + "/" + manutencoesTotais + ")");
					
					// Definindo Propriedades
					nodeAux.setProperty("selection", false); // essa propriedade permite visualizar o node no Propriety view.
					//nodeAux.setProperty("coupling", 10); //diz que o node se relaciona com outros 10 componentes.
					nodeAux.setProperty("about", "ceo software"); //posso definir a propriedade que eu quiser pra um node
					nodeAux.setProperty("Concerns", null); // compatibilidade com o collaborative sourceminer
					// Fim código propriedades

					listNodes.add(nodeAux);
				}	
				
			default:
				break;
		}
		
		// Sort with Merge Sort
		/*String teste[] = new String[2];
		MergeSort.main(teste);*/
		
		return listNodes;
	}	
	
	private int returnOccurrenceByModule(ArrayList<SetModules> listModulos, String module)
	{
		for(int i = 0; i < listModulos.size(); i++)
		{
			if(listModulos.get(i).getNome().equals(module))
				return listModulos.get(i).getOcorrencia();			
		}
		return -1;		
	}
	
	private int returnOccurrenceByComponent(String module, String component)
	{
		Arquivo arquivo = new Arquivo();
		ArrayList<SetModules> listComponents = arquivo.getListComponentsByModule(MemoryDriverCEO.getListChangeRequests(), module);
		for(int i = 0; i < listComponents.size(); i++)
		{
			if(listComponents.get(i).getNome().equals(module) && listComponents.get(i).getComponenteAssociado().equals(component))
				return listComponents.get(i).getOcorrencia();
		}
		return -1;
	}
	
	private int countMaintenanceByMantisProject(String project, ArrayList<MantisCase> list)
	{
		int count = 0;
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).getProjeto().equals(project))
			{
				count++;
			}
		}			
		return count;
	}

}
