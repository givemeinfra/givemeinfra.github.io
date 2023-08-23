package views.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.controllers.SessionProgram;
import aimv.events.ControllerEvent;
import aimv.events.NodeEvent;
import aimv.listeners.IControllerListener;
import aimv.listeners.INodeListener;
import aimv.modeling.Node;
import aimv.utilities.Colors;
import aimv.utilities.Imagens;
import aimv.views.UtilityView;

public class PropertiesView extends UtilityView implements INodeListener, IControllerListener {

	private Tree tree;
	private List<Node> nodes;
	private ArrayList<String> properties;
	private ArrayList<String> propertiesFocus;
	private ArrayList<String> propertiesVisible;
	
	public static String PROPERTIES = "aimv.views.propertiesview_properties";
	public static String PROPERTIESFOCUS = "aimv.views.propertiesview_propertiesFocus";
	public static String PROPERTIESVISIBLE = "aimv.views.propertiesview_propertiesVisible";
	
	@Override
	protected void createLayout() {
		
		tree = new Tree(getComposite(), SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		
	    TreeColumn column = new TreeColumn(tree, SWT.LEFT);
	    column.setText("Property");
	    column.setWidth(150);
	    
	    column = new TreeColumn(tree, SWT.LEFT);
	    column.setText("Value");
	    column.setWidth(150);
	    
	    if (nodes != null) {
	    	for (Node node : nodes) {
	    		TreeItem itemNode = new TreeItem(tree, SWT.NONE);
	    		configureItem(node, itemNode);
	    		itemNode.setExpanded(true);
	    	}
	    }
	   	    
	}//createLayout	
	
	private void configureItem(Node node, TreeItem itemNode) {
		
		if (propertiesVisible == null)
			return;
		if (propertiesVisible.size() == 0)
			return;
		
		itemNode.setText(0, node.toString());
		itemNode.setImage(0, PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT));
		
		for (String property : node.getAllProperties()) {
			if (propertiesVisible.contains(property)) {
				TreeItem itemProperty = new TreeItem(itemNode, SWT.NONE);
				itemProperty.setText(0, property);
				itemProperty.setImage(0, Imagens.getPluginImage("org.sourceminer.views.properties", "icons/property.png"));
				itemProperty.setText(1, ""+node.getProperty(property));
				checkProperty(node, itemProperty, property);
				if (propertiesFocus != null) {
					if (propertiesFocus.contains(property))
						itemProperty.setForeground(Colors.RED);
				}
			}
		}
		
		//itemNode.setExpanded(true);
		
	}//createItem
	
	private void checkProperty(Node node, TreeItem item, String property) {
		
		Object valor = node.getProperty(property);
		if (valor instanceof Color) {
			item.setText(1, ""+node.getProperty(property));
			item.setImage(1, createImage((Color) node.getProperty(property)));
		}
		else if (valor instanceof Object[]) {
			Object[] ob = (Object[]) node.getProperty(property);
			String st = "";
			if (ob.length != 0) {
				for (int i = 0; i < ob.length - 1; i++)
					st += ob[i] + ", ";
				st += ob[ob.length - 1];
			}	
			item.setText(1, st);
		}
		
	}//checkProperty
	
	private Image createImage(Color color) {
		
		Image image = new Image(null,16,16);
		GC gc = new GC(image);
		gc.setBackground(color);
		gc.fillRectangle(2, 2, 12, 12);
		gc.drawRectangle(2, 2, 12, 12);
		gc.dispose();  
		return image;
		
	}//createImage

	@Override
	public void changeProperty(NodeEvent event) {
		
		if (!event.property.equals("selection") && !nodes.contains(event.getNode()))
			return;
		
		Node node = event.getNode();
		boolean selection = (Boolean) event.getNode().getProperty("selection");
		if (selection) {
			if (!nodes.contains(node)) {
				nodes.add(node);
				TreeItem itemNode = new TreeItem(tree, SWT.NONE);
	    		configureItem(node, itemNode);
	    		itemNode.setExpanded(true);
			}
			else {
				TreeItem item = tree.getItem(nodes.indexOf(node));
				item.removeAll();
				configureItem(node, item);
			}
		}
		else if (nodes.contains(node)) {
			TreeItem item = tree.getItem(nodes.indexOf(node));
			item.dispose();
			nodes.remove(node);
		}
		
	}//changeProperty
	
	@SuppressWarnings("unchecked")
	@Override
	protected void open() {
		
		nodes = (List<Node>) AIMV.getProperty(getID() + "_nodes_selected");
		if (nodes == null) {
			nodes = new ArrayList<Node>();
			AIMV.setProperty(getID() + "_nodes_selected", nodes);
		}
		addListeners();
		AIMV.addListener(this);
		
	}//open
	
	private void addListeners() {
		
		properties = new ArrayList<String>();
		propertiesFocus = new ArrayList<String>();
		propertiesVisible = new ArrayList<String>();
		
		for (String group : Nodes.getGroups()) {
			if (Nodes.getGroup(group) != null) {
				for (Node node : Nodes.getGroup(group).getNodes()) {
					node.addListener(this);
					if (node.getProperty("selection").equals(true) && !nodes.contains(node)){
					    //if(SessionProgram.getInstance().getUsuario() == 1)					    	
					    	//node.setProperty("Complexity", null);
						nodes.add(node);
					}
					for (String property : node.getAllProperties()) {
						if (!properties.contains(property)) {
							if(changePropertyComplexity(property) == false)
							    properties.add(property);
							propertiesVisible.add(property);
						}
					}
				}
			}
		}
		
		AIMV.setProperty(PROPERTIES, properties);
		AIMV.setProperty(PROPERTIESFOCUS, propertiesFocus);
		AIMV.setProperty(PROPERTIESVISIBLE, propertiesVisible);
		
	}//addListeners
	
	private Boolean changePropertyComplexity(String property)
	{
		if(SessionProgram.getInstance().getUsuario() == 1 && property.equals("Complexity"))
		{
			property = null;
			return true;
		}
		return false;
	}	
	
	@Override
	protected void closed() {
		AIMV.removeListener(this);
		removeListeners();
	}//closed
	
	private void removeListeners() {
		
		nodes.clear();
		properties = null;
		propertiesFocus = null;
		propertiesVisible = null;
		
		for (String group : Nodes.getGroups()) {	
			if (Nodes.getGroup(group) != null) {
				for (Node node : Nodes.getGroup(group).getNodes())
					node.removeListener(this);
			}
		}
		
		AIMV.setProperty(PROPERTIES, properties);
		AIMV.setProperty(PROPERTIESFOCUS, propertiesFocus);
		AIMV.setProperty(PROPERTIESVISIBLE, propertiesVisible);
		
	}//removeListeners
	
	@SuppressWarnings("unchecked")
	@Override
	public void changeProperty(ControllerEvent event) {
		
		if (event.property.equals(PROPERTIESFOCUS))
			propertiesFocus = (ArrayList<String>) event.afterValue;
		else if (event.property.equals(PROPERTIESVISIBLE))
			propertiesVisible = (ArrayList<String>) event.afterValue;
		else
			return;
		
		if (nodes != null) {
	    	for (Node node : nodes) {
	    		TreeItem item = tree.getItem(nodes.indexOf(node));
				item.removeAll();
				configureItem(node, item);
	    	}
		}
		
	}//changeProperty
	
	@Override
	public void startImport(ControllerEvent event) {
		
		AIMV.removeListener(this);
		removeListeners();
		AIMV.addListener(this);
		layout();
		
	}//startImport
	
	@Override
	public void stopImport(ControllerEvent event) {
		
		AIMV.removeListener(this);
		addListeners();
		AIMV.addListener(this);
		
	}//stopImport

	@Override
	public void addRelation(NodeEvent event) {}
	@Override
	public void removeRelation(NodeEvent event) {}
	@Override
	public void addFilter(NodeEvent event) {}
	@Override
	public void removeFilter(NodeEvent event) {}

	
}//class
