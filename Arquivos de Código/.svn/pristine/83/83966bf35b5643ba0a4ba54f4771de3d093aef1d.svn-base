package views.grid;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Rectangle;

import views.grid.actions.GridPropertyAction;
import views.grid.actions.GridSortAction;
import views.grid.actions.GridZoomAction;
import views.grid.utilities.Properties;
import views.grid.utilities.QuickSort;
import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.events.ControllerEvent;
import aimv.events.GroupEvent;
import aimv.listeners.IControllerListener;
import aimv.listeners.IGroupListener;
import aimv.modeling.Group;
import aimv.modeling.Node;
import aimv.views.Paradigm;
import aimv.controllers.SessionProgram;
import givemeviews.views.provider.MasterProvider;

public class GridView extends Paradigm implements IGroupListener, IControllerListener {

	private Figure grid;
	private Group group;
	private String group_selected;
	private int min;
	private double scale = 1;
	private String sort = "Decrescent";
	private static String property;
	
	public static List<Node> nodesSelected;
	
	/*@Override
	protected void createLayout() {
		
		setDafaultLayout(); 
		if (group == null)
			return;
		
		SessionProgram session = SessionProgram.getInstance();
		MasterProvider provider = new MasterProvider();
		if(session.getUsuario() == 1 && provider.providerProgramDataSource() == false)
			return;
			
		int width = getCanvas().getClientArea().width;
		int height = getCanvas().getClientArea().height;
		int raiz = (int) Math.ceil(Math.sqrt(group.getNodes().size()));
		int itemW = width / raiz;
		int itemH = height / raiz;
			
		itemW = itemW < min ? min : itemW;
		itemH = itemH < min ? min : itemH;
		itemW = (int) (itemW*scale);
		itemH = (int) (itemH*scale);
		
		width = itemW*raiz;
		height = itemH*raiz;
		
		getFigure().removeAll();
		grid.setSize(width, height);
		getFigure().setSize(width, height);
		
		int x = 0;
		int y = 0;
		GridItem item;
		
		for (Object ob : grid.getChildren()) {
			
			item = (GridItem) ob;
			item.setBounds(new Rectangle(x*itemW,y*itemH,itemW,itemH));
			x++; 
			if (x == raiz) {
				x = 0;
				y++;
			}
		}
		
		getFigure().add(grid);
		
	}//createLayout */
	
	@Override
	protected void open() {
		
		grid = new Figure();
		group_selected = (String) AIMV.getProperty(Properties.GROUP_SELECTED);
		group = Nodes.getGroup(group_selected);	
		nodesSelected = new ArrayList<Node>();
		
		getViewSite().getActionBars().getToolBarManager().add(new GridZoomAction(this));
		getViewSite().getActionBars().getToolBarManager().add(new GridSortAction(this));
		getViewSite().getActionBars().getToolBarManager().add(new GridPropertyAction(this));
		
		sort = (String) AIMV.getProperty("Grid Sort");
		scale = (double) AIMV.getProperty("Geometric Zoom");
		property = (String) AIMV.getProperty("Grid Property");
		
		
		MasterProvider provider = new MasterProvider();
		if(SessionProgram.getInstance().getUsuario() == 0)
	    {
			addItens();
			sortGroup();
			AIMV.addListener(this);
			Nodes.addListener(this);
			
			getCanvas().setVerticalScrollBarVisibility(FigureCanvas.AUTOMATIC);
			getCanvas().setHorizontalScrollBarVisibility(FigureCanvas.AUTOMATIC);
	    }
		else if (SessionProgram.getInstance().getUsuario() == 1 && provider.providerProgramDataSource() == true) 
		{
			addItensGiveMeViews();
			sortGroup();
			AIMV.addListener(this); 
			Nodes.addListener(this);
			
			getCanvas().setVerticalScrollBarVisibility(FigureCanvas.AUTOMATIC);
			getCanvas().setHorizontalScrollBarVisibility(FigureCanvas.AUTOMATIC);
		}		
		
	}//open

	@Override
	protected void closed() {
		
		AIMV.removeListener(this);
		Nodes.removeListener(this);
		removeItens();
		grid = null;
		
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
	
	private void removeItens() {
		if (group != null) {
			group.removeListener(this);
			for (Node node : group.getNodes())
				((GridItem) node.getProperty(Properties.GRID_ITEM)).dispose();
		}	
	}//removeItens
	
	private void addItens() {
		if (group != null) {
			group.addListener(this);
			for (Node node : group.getNodes())
				grid.add(new GridItem(node));
		}
	}//addItens
	
	private void addItensGiveMeViews() 
	{	
		MasterProvider provider = new MasterProvider();
		Group groupDate = provider.getNodes(group, "Grid");		
				
		// Insert on GridView
		if (groupDate != null) {
			groupDate.addListener(this);
			for (Node node : groupDate.getNodes())
			{
				grid.add(new GridItem(node));
			}
		}				
	}//addItens

	@Override
	public void addNode(GroupEvent event) {
		grid.add(new GridItem(event.node));
		sortGroup();
		layout();
	}//addNode

	@Override
	public void removeNode(GroupEvent event) {
		((GridItem) event.node.getProperty(Properties.GRID_ITEM)).dispose();
		layout();
	}//removeNode
	
	private void sortGroup() {
				
		min = 30;
		if (sort == null || group == null || property == null)
			return;
		
		grid.removeAll();
		if (sort.equals("Empty")) {
			for (Node node : group.getNodes()) {
				grid.add((GridItem) node.getProperty(Properties.GRID_ITEM));
				findSizeMin(node);
			}
			return;
		}
			
		//pega o grupo de nós do Grid e ordena pela propriedade a ser visualizada
		Node[] nodes = group.getNodes().toArray(new Node[0]);
		QuickSort.sort(nodes, property, true);
		
		//ordena os itens do grid de acordo com os nós ordenados
		if (sort.equals("Decrescent")) {
			for (int i = nodes.length - 1; i >= 0; i--) {
				grid.add((GridItem) nodes[i].getProperty(Properties.GRID_ITEM));
				findSizeMin(nodes[i]);
			}
		}
		else {
			for (int i = 0; i < nodes.length; i++) {
				grid.add((GridItem) nodes[i].getProperty(Properties.GRID_ITEM));
				findSizeMin(nodes[i]);
			}
		}

	}//sortGroup
	
	private void findSizeMin(Node node) {
		
		if (node.getProperty(property) != null) {
			String valor = ""+ node.getProperty(property);
			int w = valor.length() * 6 + 10;
			min = min < w ? w : min;  
		}
		
	}//findSizeMin

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		if (this.scale != scale) {
			this.scale = scale;
			layout();
		}
	}//setScale

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		if (this.sort != sort) {
			this.sort = sort;
			sortGroup();
			layout();
		}
	}//setSort

	public static String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		if (GridView.property != property) {
			GridView.property = property;
			sortGroup();
			layout();
		}
	}//setProperty

	@Override
	public void changeProperty(ControllerEvent event) {
		
		if (event.property.equals(Properties.GROUP_SELECTED))
			changeGroup();
		else if (event.property.equals(Properties.COLOR_SELECTED))
			changeColor();
		
	}//changeProperty
	
	private void changeColor() {
		
		GridItem item;
		for (Object ob : grid.getChildren()) {
			item = (GridItem) ob;
			item.configureColor();
		}
		
	}//changeColor
	
	private void changeGroup() {
		
		removeItens();
		group_selected = (String) AIMV.getProperty(Properties.GROUP_SELECTED);
		group = Nodes.getGroup(group_selected);	
		addItens();
		sortGroup();
		layout();
		
	}//changeGroup

	@Override
	public void startImport(ControllerEvent event) {}

	@Override
	public void stopImport(ControllerEvent event) {}

	@Override
	protected void createLayout() {
		setDafaultLayout(); 
		if (group == null)
			return;
		
		SessionProgram session = SessionProgram.getInstance();
		MasterProvider provider = new MasterProvider();
		if(session.getUsuario() == 1 && provider.providerProgramDataSource() == false)
			return;
			
		int width = getCanvas().getClientArea().width;
		int height = getCanvas().getClientArea().height;
		int raiz = (int) Math.ceil(Math.sqrt(group.getNodes().size()));
		int itemW = width / raiz;
		int itemH = height / raiz;
			
		itemW = itemW < min ? min : itemW;
		itemH = itemH < min ? min : itemH;
		itemW = (int) (itemW*scale);
		itemH = (int) (itemH*scale);
		
		width = itemW*raiz;
		height = itemH*raiz;
		
		getFigure().removeAll();
		grid.setSize(width, height);
		getFigure().setSize(width, height);
		
		int x = 0;
		int y = 0;
		GridItem item;
		
		for (Object ob : grid.getChildren()) {
			
			item = (GridItem) ob;
			item.setBounds(new Rectangle(x*itemW,y*itemH,itemW,itemH));
			x++; 
			if (x == raiz) {
				x = 0;
				y++;
			}
		}
		
		getFigure().add(grid);
		
	}

	
}//class
