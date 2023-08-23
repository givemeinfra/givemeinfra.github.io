package views.matrix;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Listener;

import views.matrix.actions.MatrixZoomAction;
import views.matrix.itens.MatrixRelation;
import views.matrix.itens.MatrixSideItem;
import views.matrix.itens.MatrixTopItem;
import views.matrix.utilities.Properties;
import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.events.ControllerEvent;
import aimv.events.GroupEvent;
import aimv.listeners.IControllerListener;
import aimv.listeners.IGroupListener;
import aimv.modeling.Group;
import aimv.modeling.Node;
import aimv.modeling.Relation;
import aimv.views.Paradigm;

import java.util.ArrayList;

import aimv.controllers.SessionProgram;
import givemeviews.views.provider.MasterProvider;

public class MatrixView extends Paradigm implements SelectionListener, IGroupListener, IControllerListener {
	
	private int width;
	private int height;
	private int min;
	private int sizeTopMenu;
	private int sizeSideMenu;
	private double scale = 1;
	
	private Matrix matrix;
	private MatrixContainer topMenu;
	private MatrixContainer sideMenu;
	private MatrixContainer tampa;
	
	public static ArrayList<Node> nodesSelected;
	
	private Group group;
	private String group_selected;
	
	@Override
	protected void createLayout() {
		
		setDafaultLayout();
		if (group == null)
			return;
		
		SessionProgram session = SessionProgram.getInstance();
		MasterProvider provider = new MasterProvider();
		if(session.getUsuario() == 1 && provider.providerProgramDataSource() == false)
			return;
		
		getFigure().removeAll();
		
		sizeTopMenu = (Integer.toString(group.getNodes().size()).length() * 6) + 10;
		if(SessionProgram.getInstance().getUsuario() == 0)
		   sizeSideMenu = 120 + sizeTopMenu; 
		else 
			sizeSideMenu = 300 + sizeTopMenu; // GiveMe Views better size
		
		
		if(SessionProgram.getInstance().getUsuario() == 0){
			width = (getCanvas().getClientArea().width - sizeSideMenu) / (group.getNodes().size());
			height = (getCanvas().getClientArea().height - sizeTopMenu) / (group.getNodes().size());
		}
		else{
			width = 170 / (group.getNodes().size()); 
			height = 100 / (group.getNodes().size()); 
		}
		width = width < min ? min : width;
		height = height < min ? min : height;
		
		width *= scale;
		height *= scale;
		
		setPosition(0, 0);
		setDimension();
			
		int x = topMenu.getLocation().x;
		int y = topMenu.getLocation().y;
		int x2 = sideMenu.getLocation().x;
		int y2 = sideMenu.getLocation().y;
		
		MatrixTopItem topItem;
		MatrixSideItem sideItem;
		
		for (Node node : group.getNodes()) {
			//cria o item do menu superior
			topItem = (MatrixTopItem) node.getProperty(Properties.MATRIX_TOPITEM);
			topItem.setDimension(x + topItem.getIndex()*width, y, width + 1, sizeTopMenu + 1);
				
			//cria o item do menu lateral
			sideItem = (MatrixSideItem) node.getProperty(Properties.MATRIX_SIDEITEM);
			sideItem.setDimension(x2, y2 + sideItem.getIndex()*height, sizeSideMenu + 1, height + 1);
		}
		
		MatrixRelation relat;
		//preenche a matriz com as relações
		for (Object rl : matrix.getChildren()) {
			relat = (MatrixRelation) rl;
			int ind1 = ((MatrixTopItem) relat.getRelation().getSource().getProperty(Properties.MATRIX_TOPITEM)).getIndex();
			int ind2 = ((MatrixTopItem) relat.getRelation().getTarget().getProperty(Properties.MATRIX_TOPITEM)).getIndex();
			int x3 = matrix.getLocation().x + ind2*width;
			int y3 = matrix.getLocation().y + ind1*height;
			relat.setDimension(x3, y3, width + 1, height + 1);
		}
		
		getFigure().add(matrix);
		getFigure().add(topMenu);
		getFigure().add(sideMenu);
		getFigure().add(tampa);
		
	}//createLayout
	
	private void configureScrollBar() {
		
		getCanvas().setHorizontalScrollBarVisibility(FigureCanvas.AUTOMATIC);
		getCanvas().setVerticalScrollBarVisibility(FigureCanvas.AUTOMATIC);
		
		Listener[] list = getCanvas().getHorizontalBar().getListeners(SWT.Selection);
		for (Listener l : list)
			getCanvas().getHorizontalBar().removeListener(SWT.Selection, l);
		
		list = getCanvas().getVerticalBar().getListeners(SWT.Selection);
		for (Listener l : list)
			getCanvas().getVerticalBar().removeListener(SWT.Selection, l);
		
		getCanvas().getHorizontalBar().addSelectionListener(this);
		getCanvas().getVerticalBar().addSelectionListener(this);
		
	}//configureScrollBar
	
	private void setDimension() { // função a ser modificada
		
		tampa.setSize(sizeSideMenu + 1, sizeTopMenu + 1);
		topMenu.setSize(width * group.getNodes().size() + 1, sizeTopMenu + 1);
		sideMenu.setSize(sizeSideMenu + 1, group.getNodes().size() * height + 1);
		matrix.setDimension(group.getNodes().size(),width,height); // aqui é passado group.getNodes().size() como sendo a quantidade de itens que estará na matrix
		matrix.setSize(topMenu.getSize().width, sideMenu.getSize().height);
		getFigure().setSize(topMenu.getSize().width + sizeSideMenu, sideMenu.getSize().height + sizeTopMenu);
		
	}//setDimension
	
	public void setPosition(int x, int y) {
		
		matrix.setLocation(new Point(sizeSideMenu + x, sizeTopMenu + y));
		topMenu.setLocation(new Point(sizeSideMenu + x, 0));
		sideMenu.setLocation(new Point(0, sizeTopMenu + y));
		
	}//setPosition

	@Override
	public void widgetSelected(SelectionEvent e) {
		int posX = - getCanvas().getHorizontalBar().getSelection();
		int posY = - getCanvas().getVerticalBar().getSelection();
		setPosition(posX, posY);
	}//widgetSelected

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}

	@Override
	protected void open() {
		
		matrix = new Matrix();
		topMenu = new MatrixContainer();
		sideMenu = new MatrixContainer();
		tampa = new MatrixContainer();
		group_selected = (String) AIMV.getProperty(Properties.GROUP_SELECTED);
		group = Nodes.getGroup(group_selected);	
		nodesSelected = new ArrayList<Node>();
		
		getViewSite().getActionBars().getToolBarManager().add(new MatrixZoomAction());
		
		scale = (double) AIMV.getProperty(Properties.MATRIX_SCALE);
		min = 20;
		

		MasterProvider provider = new MasterProvider();
		if(SessionProgram.getInstance().getUsuario() == 0)
	    {
	    	configureScrollBar();
			
			addItens();
			Nodes.addListener(this);
			AIMV.addListener(this);
	    }
	    else if (SessionProgram.getInstance().getUsuario() == 1 && provider.providerProgramDataSource() == true) {
            configureScrollBar();
			
			addItensGiveMeViews();
			Nodes.addListener(this);
			AIMV.addListener(this);
		}
		
		
	}//open

	@Override
	protected void closed() {

		Nodes.removeListener(this);
		AIMV.removeListener(this);
		removeItens();
		matrix = null;
		topMenu = null;
		sideMenu = null;
		tampa = null;
		
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
			for (Node node : group.getNodes()) {
				((MatrixTopItem) node.getProperty(Properties.MATRIX_TOPITEM)).dispose();
				((MatrixSideItem) node.getProperty(Properties.MATRIX_SIDEITEM)).dispose();
				removeRelations(node);
			}
		}	
	}//removeItens
	
	private void addItens() {
		if (group != null) {
			group.addListener(this);
			for (Node node : group.getNodes()) {
				topMenu.add(new MatrixTopItem(node, topMenu.getChildren().size()));
				sideMenu.add(new MatrixSideItem(node, sideMenu.getChildren().size()));
				addRelations(node);
			}
		}
	}//addItens 
	
	private void addItensGiveMeViews() {		

		MasterProvider provider = new MasterProvider();
		Group groupDate = provider.getNodes(group, "Matrix");
		
		if (groupDate != null) {
			groupDate.addListener(this);
			for (Node node : groupDate.getNodes()) 
			{
				// Create Matrix TopMenu and SideMenu
				topMenu.add(new MatrixTopItem(node, topMenu.getChildren().size()));
				sideMenu.add(new MatrixSideItem(node, sideMenu.getChildren().size()));
							
				addRelations(node);
			}
		}		
				
	}//addItens
	
	private void removeRelations(Node node) {
		for (Relation relation : node.getRelations()) {
			if (relation.getProperty(Properties.MATRIX_RELATION) != null)
				((MatrixRelation) relation.getProperty(Properties.MATRIX_RELATION)).dispose();
		}
	}//removeEdges
		
	private void addRelations(Node node) {
		for (Relation relation : node.getRelations()) {
			if (relation.getProperty(Properties.MATRIX_RELATION) == null) {
				if (group.contains(relation.getSource()) && group.contains(relation.getTarget())) {
					matrix.add(new MatrixRelation(relation));
					findSizeMin(relation);
				}
			}
		}
	}//addEdges

	@Override
	public void addNode(GroupEvent event) {
		topMenu.add(new MatrixTopItem(event.node, topMenu.getChildren().size()));
		sideMenu.add(new MatrixSideItem(event.node, sideMenu.getChildren().size()));
		addRelations(event.node);
		layout();
	}//addNode

	@Override
	public void removeNode(GroupEvent event) {
		((MatrixTopItem) event.node.getProperty(Properties.MATRIX_TOPITEM)).dispose();
		((MatrixSideItem) event.node.getProperty(Properties.MATRIX_SIDEITEM)).dispose();
		removeRelations(event.node);
		layout();
	}//removeNode

	@Override
	public void changeProperty(ControllerEvent event) {
		
		if (event.property.equals(Properties.MATRIX_SCALE)) {
			scale = (double) event.afterValue;
			layout();
		}
		else if (event.property.equals(Properties.GROUP_SELECTED))
			changeGroup();
		else if (event.property.equals(Properties.COLOR_SELECTED))
			changeColor();
		
	}//changeProperty
	
	private void changeColor() {
		
		MatrixTopItem topItem;
		MatrixSideItem sideItem;
		
		if (group == null)
			return;
		
		for (Node node : group.getNodes()) {
			topItem = (MatrixTopItem) node.getProperty(Properties.MATRIX_TOPITEM);
			topItem.configureColor();
				
			sideItem = (MatrixSideItem) node.getProperty(Properties.MATRIX_SIDEITEM);
			sideItem.configureColor();
		}
		
	}//changeColor
	
	private void changeGroup() {
		
		min = 20;
		removeItens();
		group_selected = (String) AIMV.getProperty(Properties.GROUP_SELECTED);
		group = Nodes.getGroup(group_selected);
		addItens();
		layout();
		
	}//changeGroup
	
	private void findSizeMin(Relation relation) {
		if (relation.getProperty(Properties.DEGREE) != null) {
			String degree = ""+ relation.getProperty(Properties.DEGREE);
			int w = degree.length() * 6 + 10;
			min = min < w ? w : min;  
		}
	}//findSizeMin

	@Override
	public void startImport(ControllerEvent event) {}

	@Override
	public void stopImport(ControllerEvent event) {}
	
	
}//class
