package views.treemap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.Figure;

import views.treemap.utilities.Properties;
import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.events.ControllerEvent;
import aimv.events.GroupEvent;
import aimv.listeners.IControllerListener;
import aimv.listeners.IGroupListener;
import aimv.modeling.Group;
import aimv.modeling.Node;
import aimv.views.Paradigm;

public class TreeMap extends Paradigm implements IGroupListener, IControllerListener {
	
	
	private Group group;
	private String group_selected;
	private Figure treemap;
	
	public static List<Node> nodesSelected;
	
	public static String propertyArea;
	public static String propertyRelation = "childrens";
	
	public String[] properties;
	
	
	@Override
	protected void createLayout() {
		
		setDafaultLayout();
		if (group == null)
			return;
			
		int width = getCanvas().getClientArea().width;
		int height = getCanvas().getClientArea().height;			
		
		getFigure().removeAll();
		treemap.setSize(width, height);
		getFigure().setSize(width, height);
		
		SquarifiedLayout layout = new SquarifiedLayout();
		
		ArrayList<TreeMapItem> itens = new ArrayList<TreeMapItem>();
		for (Node node : group.getNodes())
			itens.add((TreeMapItem) node.getProperty(Properties.TREEMAP_ITEM));
		layout.layout(itens.toArray(new TreeMapItem[0]), treemap.getBounds());
		
		getFigure().add(treemap);
		
	}//createLayout
	
	@Override
	protected void open() {
		
		treemap = new Figure();
		group_selected = (String) AIMV.getProperty(Properties.GROUP_SELECTED);
		group = Nodes.getGroup(group_selected);	
		nodesSelected = new ArrayList<Node>();
		
		findProperties();
		AIMV.setProperty(Properties.TREEMAP_AREA, propertyArea);
		AIMV.setProperty(Properties.TREEMAP_NIVEL, propertyRelation);
		
		addItens();
		AIMV.addListener(this);
		Nodes.addListener(this);
		
	}//open

	@Override
	protected void closed() {
		
		AIMV.removeListener(this);
		Nodes.removeListener(this);
		removeItens();
		treemap = null;
		
	}//closed

	@Override
	public void removeGroup(GroupEvent event) {
		if (event.id.equals(group_selected))
			changeGroup();
	}//removeGroup

	@Override
	public void setGroup(GroupEvent event) {
		if (event.id.equals(group_selected))
			changeGroup();
	}//setGroup
	
	private void changeGroup() {
	
		removeItens();
		group_selected = (String) AIMV.getProperty(Properties.GROUP_SELECTED);
		group = Nodes.getGroup(group_selected);	
		addItens();
		layout();

	}//changeGroup
	
	private void removeItens() {
		if (group != null) {
			group.removeListener(this);
			for (Node node : group.getNodes()) {
				((TreeMapItem) node.getProperty(Properties.TREEMAP_ITEM)).dispose();
				removerFilhos(node);
			}
		}	
	}//removeItens
	
	private void addItens() {
		if (group != null) {
			group.addListener(this);
			for (Node node : group.getNodes()) {
				TreeMapItem item = new TreeMapItem(node,0);
				treemap.add(item);
				adicionarFilhos(node);
				item.configureFiltered();
				item.configureSelection();
			}
		}
	}//addItens
	
	@Override
	public void addNode(GroupEvent event) {
		treemap.add(new TreeMapItem(event.node,0));
		adicionarFilhos(event.node);
		layout();
	}//addNode

	@Override
	public void removeNode(GroupEvent event) {
		((TreeMapItem) event.node.getProperty(Properties.TREEMAP_ITEM)).dispose();
		removerFilhos(event.node);
		layout();
	}//removeNode
	
	private void adicionarFilhos(Node node){
		
		try {
			TreeMapItem item = (TreeMapItem) node.getProperty(Properties.TREEMAP_ITEM);
			Node[] list = (Node[]) node.getProperty(propertyRelation);
			
			if (list != null) {		
				for(Node filho: list){
					TreeMapItem itemFilho = new TreeMapItem(filho, item.nivel+1);
					treemap.add(itemFilho);	
					adicionarFilhos(filho);
					itemFilho.configureFiltered();
				}
			}
			
		} catch (ClassCastException e) {}
		
	}//adicionarFilhos
	
	private void removerFilhos(Node node){
		
		try {
			
			Node[] list = (Node[]) node.getProperty(propertyRelation);
			if (list != null) {
				for(Node filho: list) {
					((TreeMapItem) filho.getProperty(Properties.TREEMAP_ITEM)).dispose();
					removerFilhos(filho);
				}
			}
			
		} catch (ClassCastException e) {}
		
	}//removerFilhos
	
	private void findProperties() {
		
		int cont;
		ArrayList<String> list = new ArrayList<String>();
		for (String group : Nodes.getGroups()) {
			cont = 0;
			for (Node node : Nodes.getGroup(group).getNodes()) {
				for (String prop : node.getAllProperties()) {
					if (node.getProperty(prop) instanceof Integer && !list.contains(prop))
						list.add(prop);
				}
				cont++;
				if (cont == 100)
					break;
			}
		}
	
		Collections.sort(list);
		properties = list.toArray(new String[0]);
		
		if (properties.length == 0)
			properties = null;
		
		if (list.contains(propertyArea))
			return;
		else {
			if (properties != null)
				propertyArea = properties[0];
			else
				propertyArea = null;
		}
		
	}//findProperties
	
	@Override
	public void changeProperty(ControllerEvent event) {
		
		if (event.property.equals(Properties.GROUP_SELECTED))
			changeGroup();
		else if (event.property.equals(Properties.COLOR_SELECTED))
			changeColor();
		else if (event.property.equals(Properties.TREEMAP_AREA)) {
			propertyArea = (String) event.afterValue;
			for (Object ob : treemap.getChildren()) {
				TreeMapItem item = (TreeMapItem) ob;
				item.setPeso();
			}
			layout();
		}
		
	}//changeProperty
	
	private void changeColor() {
		
		TreeMapItem item;
		for (Object ob : treemap.getChildren()) {
			item = (TreeMapItem) ob;
			item.configureColor();
		}
		
	}//changeColor
	
	@Override
	public void startImport(ControllerEvent event) {}//startImport

	@Override
	public void stopImport(ControllerEvent event) {
		findProperties();
		AIMV.setProperty(Properties.TREEMAP_AREA, propertyArea);
	}//stopImport
	
	
}//class

