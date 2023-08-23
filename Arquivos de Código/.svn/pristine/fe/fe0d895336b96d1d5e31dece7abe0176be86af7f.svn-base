package com.br.collaborativeAIMV.control;

import givemeviews.model.BucketItem;
import givemeviews.model.Item;
import givemeviews.views.provider.MasterProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.draw2d.Figure;

import aimv.controllers.Nodes;
import aimv.controllers.SessionProgram;
import aimv.modeling.Node;

import com.br.collaborativeAIMV.listeners.NodesListener;

public class NodesControl {
	
	private static NodesControl instance;
	public Collection<AuxiliarNode> auxiliar;
	private HashMap<String, NodesListener> nodesListenerControl;
	
	private NodesControl(){
		nodesListenerControl = new HashMap<String, NodesListener>();
		nodesListenerControl.put("treemap_item", new NodesListener());
		nodesListenerControl.put("grah_item", new NodesListener());
		nodesListenerControl.put("grid_item", new NodesListener());
		nodesListenerControl.put("matrix_sideitem", new NodesListener());
		nodesListenerControl.put("matrix_topitem", new NodesListener());
		nodesListenerControl.put("polimetric_item", new NodesListener());
	}
	
	public static NodesControl GET_INSTANCE(){
		if(NodesControl.instance==null){
			NodesControl.instance = new NodesControl();
			NodesControl.instance.auxiliar = new ArrayList<AuxiliarNode>();
		}
		return NodesControl.instance;
	}
	
	public void addListenerOnViewPropertyOfNodes()
	{
		SessionProgram session = SessionProgram.getInstance();
		MasterProvider provider = new MasterProvider();
		
		if(session.getUsuario() == 1 && provider.providerProgramDataSource() == true) // Condição para obter nodes gerados pelo GiveMe Views
		{
			BucketItem bucket = provider.providerNodes();
			ArrayList<Item> listItems = bucket.getListBucketItems();
			
			for(int i = 0; i < listItems.size(); i++)
			{
				Item itemAux = listItems.get(i);
				Collection<Node> nodes = itemAux.getListNodes();
				for (Node nodeTemp : nodes) {
					if (!isOnList(nodeTemp.getID())) {
						auxiliar.add(new AuxiliarNode(nodeTemp.getID()));
					}
					addingListener(nodeTemp);
				}
			}
			
		}
		else // Obtem nodes gerados pelo Sourceminer, que estão no AIMV
		{
			Collection<Node> nodes = Nodes.getGroup("all") != null ? Nodes.getGroup("all").getNodes() : null;
			if (nodes != null) {
				for (Node nodeTemp : nodes) {
					if (!isOnList(nodeTemp.getID())) {
						auxiliar.add(new AuxiliarNode(nodeTemp.getID()));
					}
					addingListener(nodeTemp);
				}
			}
		}		
		
	}
	
	public class AuxiliarNode{
		public String ID;
		public int treemapCont=0;
		public int grahCont=0;
		public int gridCont=0;
		public int polimetricCont=0;
		public int matrixTopCont=0;
		public int matrixsideCont=0;
		
		public AuxiliarNode(String id) {
			this.ID = id;
		}
		
		public String getID(){
			return this.ID;
		}
		
	}
	
	public boolean isOnList(String id){
		for(AuxiliarNode auxiliarTemp : auxiliar){
			if(auxiliarTemp.getID().equals(id)){
				return true;
			}
		}
		return false;
	}
	
	public AuxiliarNode getAuxiliarNodeOnList(String id){
		for(AuxiliarNode auxiliarTemp : auxiliar){
			if(auxiliarTemp.getID().equals(id)){
				return auxiliarTemp;
			}
		}
		return null;
	}
	
	public void addingListener(Node node){
		AuxiliarNode auxTemp = getAuxiliarNodeOnList(node.getID());
		if (auxTemp.treemapCont == 0) {

			Figure item = (Figure) node.getProperty("treemap_item");
			if (item != null){
				item.addMouseListener(nodesListenerControl.get("treemap_item"));
				auxTemp.treemapCont++;
			}
		}
		if (auxTemp.grahCont == 0) {
			Figure item = (Figure) node.getProperty("grah_item");
			if (item != null){
				item.addMouseListener(nodesListenerControl.get("grah_item"));
				auxTemp.grahCont++;
			}
		}
		if (auxTemp.gridCont == 0) {
			Figure item = (Figure) node.getProperty("grid_item");
			if (item != null){
				item.addMouseListener(nodesListenerControl.get("grid_item"));
				auxTemp.gridCont++;
			}
		}
		if (auxTemp.matrixsideCont == 0) {
			Figure item = (Figure) node.getProperty("matrix_sideitem");
			if (item != null){
				item.addMouseListener(nodesListenerControl.get("matrix_sideitem"));
				auxTemp.matrixsideCont++;
			}
		}
		if (auxTemp.matrixTopCont == 0) {
			Figure item = (Figure) node.getProperty("matrix_topitem");
			if (item != null){
				item.addMouseListener(nodesListenerControl.get("matrix_topitem"));
				auxTemp.matrixTopCont++;
			}
		}
		if (auxTemp.polimetricCont == 0) {
			Figure item = (Figure) node.getProperty("polimetric_item");
			if (item != null){
				item.addMouseListener(nodesListenerControl.get("polimetric_item"));
				auxTemp.polimetricCont++;
			}
		}
	}
	
	public void removingListener(Node node){
		{
		Figure item = (Figure) node.getProperty("treemap_item");
		if (item != null)
			item.removeMouseListener(nodesListenerControl.get("treemap_item"));
		}
		{
		Figure item = (Figure) node.getProperty("grah_item");
		if (item != null)
			item.removeMouseListener(nodesListenerControl.get("grah_item"));
		}
		{
		Figure item = (Figure) node.getProperty("grid_item");
		if (item != null)
			item.removeMouseListener(nodesListenerControl.get("grid_item"));
		}
		{
		Figure item = (Figure) node.getProperty("matrix_sideitem");
		if (item != null)
			item.removeMouseListener(nodesListenerControl.get("matrix_sideitem"));
		}
		{
		Figure item = (Figure) node.getProperty("matrix_topitem");
		if (item != null)
			item.removeMouseListener(nodesListenerControl.get("matrix_topitem"));
		}
		{
		Figure item = (Figure) node.getProperty("polimetric_item");
		if (item != null)
			item.removeMouseListener(nodesListenerControl.get("polimetric_item"));
		}
	}
	
	public void removeAllListenersOnViewPropertyOfNodes(){
		Collection<Node> nodes = Nodes.getGroup("all") != null ? Nodes.getGroup("all").getNodes() : null;
		if (nodes != null) {
			for (Node nodeTemp : nodes) {
				removingListener(nodeTemp);
			}
			auxiliar.clear();
		}
	}
	
	
}