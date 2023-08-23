package views.graph;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Point;

import views.graph.utilities.Properties;
import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.controllers.SessionProgram;
import aimv.events.ControllerEvent;
import aimv.events.GroupEvent;
import aimv.listeners.IControllerListener;
import aimv.listeners.IGroupListener;
import aimv.modeling.Group;
import aimv.modeling.Node;
import aimv.modeling.Relation;
import aimv.views.Paradigm;
import givemeviews.views.provider.MasterProvider;

public class GraphView extends Paradigm implements IGroupListener, IControllerListener {

	private int radius = 20;
	private Figure graph;
	private Figure graphEdge;
	private List<Point> pontos;
	public static ArrayList<Node> nodesSelected;
	private Group group;
	private String group_selected;

	
	private List<Point> createEllipseLayout(int nodesQtd) {
		
		List<Point> result = new ArrayList<Point>();

		try{
			int minimumRadiusX = getFigure().getSize().width / 20;
			int minimumRadiusY = getFigure().getSize().height / 20;

			int maximumRadiusX = (int) Math.round(getFigure().getSize().width / 2.5);
			int maximumRadiusY = (int) Math.round(getFigure().getSize().height / 2.5);

			Point center = new Point(getFigure().getSize().width / 2 - 10, getFigure().getSize().height / 2 - 10);

			if (nodesQtd > result.size()) {
				List<Integer> layers = getLayers(nodesQtd - result.size());
				int deltaRadiusX = (maximumRadiusX - minimumRadiusX)
				/ layers.size();
				int deltaRadiusY = (maximumRadiusY - minimumRadiusY)
				/ layers.size();

				for (int index = 0; index < layers.size(); index++) {

					double delta = 360.0 / (layers.get(index));
					//double angulo = new Random().nextInt() % delta;
					double angulo = 0;
					Point point;
					for (int i = 0; i < layers.get(index); i++) {
						int x = (int) (center.x + (maximumRadiusX - (layers.size() - index - 1) * deltaRadiusX) * Math.cos(parse(angulo)));
						int y = (int) (center.y + (maximumRadiusY - (layers.size() - index - 1) * deltaRadiusY) * Math.sin(parse(angulo)));
						point = new Point(x,y);
						result.add(point);
						angulo += delta;
					}
				}

			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		return result;
		
	}//createEllipseLayout
	
	private List<Integer> getLayers(int nodesQtd) {
		
		List<Integer> result = new ArrayList<Integer>();
		int minimum = 16;
		int delta = 10;

		if (nodesQtd <= minimum) {
			result.add(nodesQtd);
			return result;
		}

		for (int i = 0; i < nodesQtd; i++) {
			int layer = minimum + (i * delta);
			result.add(layer);
			nodesQtd -= layer;
		}
		// correcao da ultima camada
		result.set(result.size() - 1, result.get(result.size() - 1) + nodesQtd);

		return result;
		
	}//getLayers
	
	private double parse(double value) {

		double result = 0;
		result = (value * 2 * Math.PI) / 360;
		return result;

	}//parse
	
	@Override
	protected void createLayout() {
		
		setDafaultLayout();
		if (group == null)
			return;
		
		SessionProgram session = SessionProgram.getInstance();
		MasterProvider provider = new MasterProvider();
		if(session.getUsuario() == 1 && provider.providerProgramDataSource() == false)
			return;
		
		pontos = createEllipseLayout(group.getNodes().size());
		int width = getCanvas().getClientArea().width;
		int height = getCanvas().getClientArea().height;
		graph.setSize(width, height);
		graphEdge.setSize(width, height);
		getFigure().removeAll();

		
		try{
			int i = 0;
			GraphItem item;
			for (Object ob : graph.getChildren()) {
				item = (GraphItem) ob;
				item.setDimension(pontos.get(i), radius);
				i++;
			}
		}
		catch(Exception e){
		}
		
		try{
			GraphEdge edge;
			for (Object rl : graphEdge.getChildren()) {
				edge = (GraphEdge) rl;
				int ind1 = graph.getChildren().indexOf(edge.getRelation().getSource().getProperty(Properties.GRAPH_ITEM));
				int ind2 = graph.getChildren().indexOf(edge.getRelation().getTarget().getProperty(Properties.GRAPH_ITEM));
				edge.setDimension(pontos.get(ind1), pontos.get(ind2), radius);
				edge.setBounds(graph.getBounds());
			}
		}
		catch(Exception e){
		}
		
		getFigure().add(graphEdge);
		getFigure().add(graph);
		
	}//createLayout 
	
	@Override
	protected void open() {		
		
		MasterProvider provider = new MasterProvider();
		if(SessionProgram.getInstance().getUsuario() == 0)
	    {
			graph = new Figure();
			graphEdge = new Figure();
			graphEdge.setVisible(false);
			group_selected = (String) AIMV.getProperty(Properties.GROUP_SELECTED);
			group = Nodes.getGroup(group_selected);		
			nodesSelected = new ArrayList<Node>();
	
			configureShowDependency();
			
			addItens();
			Nodes.addListener(this);
			AIMV.addListener(this);
	    }
		else if (SessionProgram.getInstance().getUsuario() == 1 && provider.providerProgramDataSource() == true) {
			graph = new Figure();
			graphEdge = new Figure();
			graphEdge.setVisible(false);
			group_selected = (String) AIMV.getProperty(Properties.GROUP_SELECTED);
			group = Nodes.getGroup(group_selected);		
			nodesSelected = new ArrayList<Node>();
	
			configureShowDependency();
			
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
		graph = null;
		graphEdge = null;
		
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
				((GraphItem) node.getProperty(Properties.GRAPH_ITEM)).dispose();
				removeEdges(node);
			}
		}	
	}//removeItens
	
	private void addItens() {
		if (group != null) {
			group.addListener(this);
			for (Node node : group.getNodes()) {
				graph.add(new GraphItem(node));
				addEdges(node);
			}
		}
	}//addItens
	
	private void addItensGiveMeViews() 
	{        
		MasterProvider provider = new MasterProvider();
		Group groupDate = provider.getNodes(group, "Graph");		
		
		if (groupDate != null) {
			groupDate.addListener(this);
			for (Node node : groupDate.getNodes()) 
			{
				graph.add(new GraphItem(node));				
				addEdges(node);
			}
		}
	}//addItens
	
	private void removeEdges(Node node) {
		for (Relation relation : node.getRelations()) {
			if (relation.getProperty(Properties.GRAPH_ITEM) != null)
				((GraphEdge) relation.getProperty(Properties.GRAPH_ITEM)).dispose();
		}
	}//removeEdges
	
	private void addEdges(Node node) {
		for (Relation relation : node.getRelations()) {
			if (relation.getProperty(Properties.GRAPH_ITEM) == null) {
				if (group.contains(relation.getSource()) && group.contains(relation.getTarget()))
					graphEdge.add(new GraphEdge(relation));
			}
		}
	}//addEdges

	@Override
	public void addNode(GroupEvent event) {
		graph.add(new GraphItem(event.node));
		addEdges(event.node);
		layout();
	}//addNode

	@Override
	public void removeNode(GroupEvent event) {
		((GraphItem) event.node.getProperty(Properties.GRAPH_ITEM)).dispose();
		removeEdges(event.node);
		layout();
	}//removeNode

	@Override
	public void changeProperty(ControllerEvent event) {
		
		if (event.property.equals(Properties.GRAPH_SHOW))
			configureShowDependency();
		else if (event.property.equals(Properties.GRAPH_OPTIONS))
			configureCouplingOptions();
		else if (event.property.equals(Properties.GROUP_SELECTED))
			changeGroup();
		else if (event.property.equals(Properties.COLOR_SELECTED))
			changeColor();
			
	}//changeProperty
	
	private void changeColor() {
		
		GraphItem item;
		for (Object ob : graph.getChildren()) {
			item = (GraphItem) ob;
			item.configureColor();
		}
		
	}//changeColor
	
	private void configureShowDependency() {
		
		Object property = AIMV.getProperty(Properties.GRAPH_SHOW); 
		if (property instanceof Boolean)
			graphEdge.setVisible((Boolean) property);
		else
			graphEdge.setVisible(false);
		
	}//configureShowDependency

	private void configureCouplingOptions() {
		
		GraphEdge edge;
		for (Object rl : graphEdge.getChildren()) {
			edge = (GraphEdge) rl;
			edge.configureFiltered();
		}
		
	}//configureCouplingOptions

	@Override
	public void startImport(ControllerEvent event) {}//startImport

	@Override
	public void stopImport(ControllerEvent event) {}//stopImport
	

}//class
