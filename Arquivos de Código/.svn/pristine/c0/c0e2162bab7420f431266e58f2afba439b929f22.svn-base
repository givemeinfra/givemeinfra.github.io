package givemeviews.views.provider;

import givemeviews.model.BucketItem;
import givemeviews.model.Item;
import givemeviews.persistence.MemoryApplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;

import javax.swing.JOptionPane;

import aimv.controllers.Nodes;
import aimv.modeling.Group;
import aimv.modeling.Node;

public class MasterProvider 
{
	
	public MasterProvider()
	{
		
	}
	
	public void buildObjectsNode()
	{
		// Free Memory
		Group auxGroup = new Group();
		auxGroup.removeAllNodes(Nodes.getGroup("all"));
		
		MatrixViewProvider matrix = new MatrixViewProvider();
    	GraphViewProvider graph = new GraphViewProvider();
    	GridViewProvider grid = new GridViewProvider();
    	
    	ArrayList<Node> listNodesMatrix = matrix.buildingNodes();
    	ArrayList<Node> listNodesGraph = graph.buildingNodes();
    	ArrayList<Node> listNodesGrid  = grid.buildingNodes();
    	
    	Item itemMatrix = new Item("Matrix", listNodesMatrix);
    	Item itemGraph = new Item("Graph", listNodesGraph);
    	Item itemGrid = new Item("Grid", listNodesGrid);
    	
    	ArrayList<Item> listFullNodes = new ArrayList<>();
    	listFullNodes.add(itemMatrix);
    	listFullNodes.add(itemGraph);
    	listFullNodes.add(itemGrid);
    	
    	BucketItem bucket = new BucketItem(listFullNodes);  
    	
    	// clear
    	MemoryApplication.clearBucket();
    	
    	MemoryApplication.setBucket(bucket);    
    	
    	// Gabage Collector
    	System.gc();
	}
	
	public Group getNodes(Group group, String viewName)
	{
		Group groupDate = group;	
		
		// Clear all nodes from group copy.
		groupDate = groupDate.removeAllNodes(groupDate);
		
		MasterProvider provider = new MasterProvider();
		BucketItem bucket = provider.providerNodes();
		ArrayList<Item> listItems = bucket.getListBucketItems();
		
		for(int i = 0; i < listItems.size(); i++)
		{
			Item itemAux = listItems.get(i);
			if(itemAux.getViewName().equals(viewName))
			{
				for(int j = 0; j < itemAux.getListNodes().size(); j++)
					groupDate.addNode(itemAux.getListNodes().get(j));
			}
		}
		
		return groupDate;
	}
	
	public Node getNode(String name)
	{
		MasterProvider provider = new MasterProvider();
		BucketItem bucket = provider.providerNodes();
		ArrayList<Item> listItems = bucket.getListBucketItems();
		
		for(int i = 0; i < listItems.size(); i++)
		{
			Item itemAux = listItems.get(i);
			for(int j = 0; j < itemAux.getListNodes().size(); j++)
			{
				Node aux = itemAux.getListNodes().get(j);
				if(aux.getID().equals(name))
				{
					return aux;
				}
			}
		}
		return null;
	}
	
	public Collection<Node> getCollectionNodes()
	{
		LinkedHashMap<String, Node> nodes = new LinkedHashMap<>();
		
		BucketItem bucket = providerNodes();
		ArrayList<Item> listItems = bucket.getListBucketItems();
		if(listItems != null)
		{
			for(int i = 0; i < listItems.size(); i++)
			{
				Item itemAux = listItems.get(i);
				Collection<Node> nodesAux = itemAux.getListNodes();
				for (Node nodeTemp : nodesAux) {
					nodes.put(null, nodeTemp);
				}
			}
			return nodes.values();
		}
		else
		{
			return null;
		}
		
	}
	
	public Boolean providerProgramDataSource()
	{
		MemoryApplication.getMemory();
		return MemoryApplication.getGeneratedSource();
	}
	
	public BucketItem providerNodes()
	{
		return MemoryApplication.getBucket();
	}
	
	public String getGMRepositoryURL()
	{
		return MemoryApplication.getGmmRepositoryPath();
	}

}
