package views.polymetric;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;

import views.polymetric.actions.ZoomAction;
import views.polymetric.utilities.Properties;
import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.events.ControllerEvent;
import aimv.events.GroupEvent;
import aimv.listeners.IControllerListener;
import aimv.listeners.IGroupListener;
import aimv.modeling.Group;
import aimv.modeling.Node;
import aimv.views.Paradigm;

public class Polymetric extends Paradigm implements IGroupListener, IControllerListener {
	
	
	private final int loff = 15;
	private final int aoff = 30;
	private final int MIN = 10;
	private final int MAX = 110;
	
	private int minA; //menor altura
	private int maxA; //maior altura
	private int minL; //menor largura
	private int maxL; //maior largura 
	
	public static String propertyAltura;
	public static String propertyLargura;
	public static String propertyRelation = "childrens";
	
	private Figure polymetric;
	private Figure polymetricConections;
	
	private Group group;
	private String group_selected;
	private double scale;
	public String[] properties;
	public static List<Node> nodesSelected;
	
	@Override
	protected void createLayout() {
		
		setDafaultLayout();
		if (group == null)
			return;
			
		getFigure().removeAll();
		polymetricConections.removeAll();
		
		int xprox = 0;
		int alturaMaiorArvore = 0;
		PolymetricItem item;
		
	
		configurarArvore();
		
		for (Node node : group.getNodes()) {
			item = (PolymetricItem) node.getProperty(Properties.POLIMETRIC_ITEM);
			if (!item.desenhou) {
				desenharArvore(item, xprox, aoff);
				xprox = xprox + item.lsub + loff;
				alturaMaiorArvore = alturaMaiorArvore < item.asub ? item.asub : alturaMaiorArvore;
			}
		}
					
		int width = xprox + loff;
		int height = alturaMaiorArvore + aoff;
		
		polymetric.setSize(width, height);
		polymetricConections.setSize(width, height);
		getFigure().setSize(width, height);
		getFigure().add(polymetricConections);
		getFigure().add(polymetric);
		
	}//createLayout
	
	@Override
	protected void open() {
		
		polymetric = new Figure();
		polymetricConections = new Figure();
		group_selected = (String) AIMV.getProperty(Properties.GROUP_SELECTED);
		group = Nodes.getGroup(group_selected);	
		nodesSelected = new ArrayList<Node>();
		
		getViewSite().getActionBars().getToolBarManager().add(new ZoomAction(this));
		scale = (double) AIMV.getProperty(Properties.POLIMETRIC_SCALE);
		
		findProperties();
		AIMV.setProperty(Properties.POLIMETRIC_HEIGHT, propertyAltura);
		AIMV.setProperty(Properties.POLIMETRIC_WIDTH, propertyLargura);
		AIMV.setProperty(Properties.POLIMETRIC_RELATION, propertyRelation);
		
		iniciarValores();
		addItens();
		AIMV.addListener(this);
		Nodes.addListener(this);
		
		getCanvas().setVerticalScrollBarVisibility(FigureCanvas.AUTOMATIC);
		getCanvas().setHorizontalScrollBarVisibility(FigureCanvas.AUTOMATIC);
		
	}//open

	@Override
	protected void closed() {
		
		AIMV.removeListener(this);
		Nodes.removeListener(this);
		removeItens();
		polymetric = null;
		polymetricConections = null;
		
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
		iniciarValores();
		addItens();
		layout();
		
	}//changeGroup
	
	private void removeItens() {
		if (group != null) {
			group.removeListener(this);
			for (Node node : group.getNodes()) {
				PolymetricItem item = (PolymetricItem) node.getProperty(Properties.POLIMETRIC_ITEM);
				if (item != null) {
					item.dispose();
					removerFilhos(node);
				}
			}
		}	
	}//removeItens
	
	private void addItens() {
		if (group != null) {
			group.addListener(this);
			for (Node node : group.getNodes()) {
				PolymetricItem item = (PolymetricItem) node.getProperty(Properties.POLIMETRIC_ITEM);
				if (item == null) {
					polymetric.add(new PolymetricItem(node));
					verificarValores(node);
					adicionarFilhos(node);
				}
			}
		}
	}//addItens

	@Override
	public void addNode(GroupEvent event) {
		PolymetricItem item = (PolymetricItem) event.node.getProperty(Properties.POLIMETRIC_ITEM);
		if (item == null) {
			polymetric.add(new PolymetricItem(event.node));
			verificarValores(event.node);
			adicionarFilhos(event.node);
		}
		layout();
	}//addNode

	@Override
	public void removeNode(GroupEvent event) {
		PolymetricItem item = (PolymetricItem) event.node.getProperty(Properties.POLIMETRIC_ITEM);
		if (item != null) {
			item.dispose();
			removerFilhos(event.node);
		}
		layout();
	}//removeNode
	
	private void adicionarFilhos(Node node){
		
		Node[] list = validarFilhos(node);
		if (list != null) {		
			for(Node filho: list){
				PolymetricItem item = (PolymetricItem) filho.getProperty(Properties.POLIMETRIC_ITEM);
				if (item == null) {
					polymetric.add(new PolymetricItem(filho));
					verificarValores(filho);
					adicionarFilhos(filho);
				}
			}
		}

	}//adicionarFilhos
	
	private Node[] validarFilhos(Node node) {
		
		try {
			Node[] list = (Node[]) node.getProperty(propertyRelation);
			if (list != null) {
				if (list.length == 0) list = null;
			}
			return list;
		} catch (ClassCastException e) {
			return null;
		}
		
	}//validarFilhos
	
	private void removerFilhos(Node node){
		
		Node[] list = validarFilhos(node);
		if (list != null) {
			for(Node filho: list) {
				PolymetricItem item = (PolymetricItem) filho.getProperty(Properties.POLIMETRIC_ITEM);
				if (item != null) {
					item.dispose();
					removerFilhos(filho);
				}
			}
		}
		
	}//removerFilhos
	
	private void iniciarValores() {
		minL = minA = Integer.MAX_VALUE;
		maxL = maxA = 0;
	}//iniciarValores
	
	private void verificarValores(Node node) {
		
		PolymetricItem item = (PolymetricItem) node.getProperty(Properties.POLIMETRIC_ITEM);
		
		Object property = node.getProperty(Polymetric.propertyAltura);
		if (property instanceof Integer)
			item.pA = (Integer) node.getProperty(Polymetric.propertyAltura);
		else
			item.pA = 0;
		
		property = node.getProperty(Polymetric.propertyLargura);
		if (property instanceof Integer)
			item.pL = (Integer) node.getProperty(Polymetric.propertyLargura);
		else
			item.pL = 0;
		
		if(item.pL < minL)
			minL = item.pL;
		
		if(item.pA < minA)
			minA = item.pA;
				
		if(item.pL > maxL)
			maxL = item.pL;
				
		if(item.pA > maxA)
			maxA = item.pA;
				
	}//verificarValores
	
	private void configurarValores() {
		iniciarValores();
		if (group != null) {
			for (Node node : group.getNodes())
				calcularValores(node);
		}
	}//configurarValores
	
	private void calcularValores(Node node) {
		verificarValores(node);
		Node[] list = validarFilhos(node);
		if (list != null) {
			for (Node n : list)
				calcularValores(n);
		}
	}//calcularTamanhoArvore
	
	private void configurarArvore() {
		if (group != null) {
			for (Node node : group.getNodes())
				calcularTamanhoArvore((PolymetricItem) node.getProperty(Properties.POLIMETRIC_ITEM));
		}
	}//configuarArvore
	
	private void calcularTamanhoArvore(PolymetricItem raiz) {
		
		int lsub = 0;
		int asub = 0;
		
		//determina a dimensão do item através da conversão de escala x para escala y
		int difL = maxL - minL;
		difL = difL == 0 ? 1 : difL;
		int difA = maxA - minA;
		difA = difA == 0 ? 1 : difA;
		
		raiz.getBounds().width =  (int) (((((raiz.pL - minL) * (MAX - MIN)) / difL) + MIN) * scale);
		raiz.getBounds().height = (int) (((((raiz.pA - minA) * (MAX - MIN)) / difA) + MIN) * scale);	
		raiz.desenhou = false;
		
		Node[] list = validarFilhos(raiz.node);
		if (list != null) {
			for (Node n : list) {
				PolymetricItem itemFilho = (PolymetricItem) n.getProperty(Properties.POLIMETRIC_ITEM);
				calcularTamanhoArvore(itemFilho);
				lsub += itemFilho.lsub;
				if(asub < itemFilho.asub) asub = itemFilho.asub;
			}
			raiz.asub = raiz.getBounds().height + asub + aoff;
			if(raiz.getBounds().width > lsub) raiz.lsub = raiz.getBounds().width; else raiz.lsub = lsub;
			raiz.lsub = raiz.lsub + loff*(list.length-1);
		} else{	
			raiz.asub = raiz.getBounds().height + aoff;
			raiz.lsub = raiz.getBounds().width;
		}			
			
	}//calcularTamanhoArvore

	private void desenharArvore(PolymetricItem raiz, int x, int y){
		
		raiz.getBounds().y = y;
		raiz.getBounds().x = x + raiz.lsub/2 - raiz.getBounds().width/2 + loff;
		raiz.desenhou = true;
		
		Node[] list = validarFilhos(raiz.node);
		if(list == null)
			return;
		
		int xprox = x;
		int yprox = y + raiz.getBounds().height + aoff;
		
		for (Node n : list){
			PolymetricItem item = (PolymetricItem) n.getProperty(Properties.POLIMETRIC_ITEM);
			
			if(item.lsub <= raiz.getBounds().width && list.length == 1)
				xprox = xprox + (raiz.getBounds().width - item.lsub)/2;
			
			desenharArvore(item, xprox, yprox);
			criarConexao(raiz, item);
			xprox = xprox + item.lsub + loff;
		}
	
	}//desenharArvore
	
	public void criarConexao(PolymetricItem item1, PolymetricItem item2){
		
		PolylineConnection conect = new PolylineConnection();		
		Point p1 = new Point(item1.getBounds().x + item1.getBounds().width/2, item1.getBounds().y + item1.getBounds().height);
		Point p2 = new Point(item2.getBounds().x + item2.getBounds().width/2, item2.getBounds().y);
		
		conect.setAntialias(SWT.ON);
		conect.setStart(p1);
		conect.setEnd(p2);
		polymetricConections.add(conect);
		
	}//criarConexao

	@Override
	public void changeProperty(ControllerEvent event) {
		
		if (event.property.equals(Properties.GROUP_SELECTED))
			changeGroup();
		else if (event.property.equals(Properties.COLOR_SELECTED))
			changeColor();
		else if (event.property.equals(Properties.POLIMETRIC_SCALE)) {
			scale = (double) event.afterValue;
			layout();
		}
		else if (event.property.equals(Properties.POLIMETRIC_WIDTH)) {
			propertyLargura = (String) event.afterValue;
			configurarValores();
			layout();
		}
		else if (event.property.equals(Properties.POLIMETRIC_HEIGHT)) {
			propertyAltura = (String) event.afterValue;
			configurarValores();
			layout();
		}
		else if (event.property.equals(Properties.POLIMETRIC_RELATION)) {
			removeItens();
			propertyRelation = (String) event.afterValue;
			iniciarValores();
			addItens();
			layout();
		}
		
	}//changeProperty
	
	private void changeColor() {
		
		PolymetricItem item;
		for (Object ob : polymetric.getChildren()) {
			item = (PolymetricItem) ob;
			item.configureColor();
		}
		
	}//changeColor
	
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
		
		if (list.contains(propertyAltura))
			return;
		else {
			if (properties != null)
				propertyAltura = properties[0];
			else
				propertyAltura = null;
		}
		
		if (list.contains(propertyLargura))
			return;
		else {
			if (properties != null)
				propertyLargura = properties[0];
			else
				propertyLargura = null;
		}
		
	}//findProperties

	@Override
	public void startImport(ControllerEvent event) {}

	@Override
	public void stopImport(ControllerEvent event) {
		findProperties();
		AIMV.setProperty(Properties.POLIMETRIC_WIDTH, propertyLargura);
		AIMV.setProperty(Properties.POLIMETRIC_HEIGHT, propertyAltura);
	}//stopImport
	
		
}//class

