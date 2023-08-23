package givemeviews.views.provider;

import givemeviews.model.SetModules;
import givemeviews.persistence.MemoryApplication;

import java.util.ArrayList;

import aimv.modeling.*;

public class MatrixViewProvider {
	
	public MatrixViewProvider()
	{
		
	}
	
	public ArrayList<Node> buildingNodes()
	{
		ArrayList<Node> listNodes = new ArrayList<>();
		
        ArrayList<SetModules> listModulesAndComponents = MemoryApplication.getMatrixListModulesAndComponents();		
		float matrixProbabilityTable[][] = MemoryApplication.getMatrixViewProbabilityTable();
		
		// Insert new Nodes in the Group.
		for(int j = 0; j < listModulesAndComponents.size(); j++)
		{
			Node nodeAux = new Node(listModulesAndComponents.get(j).getNome() + "/" + listModulesAndComponents.get(j).getComponenteAssociado());
			listNodes.add(nodeAux);
		}
		
		// Define Relations
		if (listNodes.size() > 0) 
		{
			int line = 0;
			int column = 0;			
			for (int i = 0; i < listNodes.size(); i++) 
			{ 
				Node source = listNodes.get(i);
				
				//source.setProperty("Coupling", 10); //diz que o node se relaciona com outros 10 componentes.
				source.setProperty("selection", false); // essa propriedade permite visualizar o node no Propriety view.				
				source.setProperty("About", "ceo software"); //posso definir a propriedade que eu quiser pra um node
				source.setProperty("Concerns", null); // compatibilidade com o collaborative sourceminer
				for (int j = 0; j < listNodes.size(); j++)
				{
					Node target = listNodes.get(j);
					
					if(line != column)
					{
						if(matrixProbabilityTable[line][column] == 0)
						{
							Relation relationAux = new Relation(source, target);
							relationAux.setProperty("Degree", "--");
							source.addRelation(relationAux);
						}
						else
						{
							Relation relationAux = new Relation(source, target);
							relationAux.setProperty("Degree", matrixProbabilityTable[line][column] + "%");
							source.addRelation(relationAux);
						}
					}
					else
					{
						Relation relationAux = new Relation(source, target);
						relationAux.setProperty("Degree", "--");
						source.addRelation(relationAux);
					}
					column++;
				}
				column = 0;
				line++;
			}
		}		
		
		return listNodes;
	}	

}
