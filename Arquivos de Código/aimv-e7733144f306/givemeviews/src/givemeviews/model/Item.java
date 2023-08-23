package givemeviews.model;

import java.util.ArrayList;

import aimv.modeling.Node;

public class Item 
{
	private String viewName;
	private ArrayList<Node> listNodes;
	
	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public ArrayList<Node> getListNodes() {
		return listNodes;
	}

	public void setListNodes(ArrayList<Node> listNodes) {
		this.listNodes = listNodes;
	}

	public Item()
	{
		
	}
	
    public Item(String viewName, ArrayList<Node> listNodes)
    {
    	this.viewName = viewName;
    	this.listNodes = listNodes;
    }
}
