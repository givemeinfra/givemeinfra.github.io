package views.treemap;

import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import views.treemap.utilities.Properties;
import aimv.controllers.AIMV;
import aimv.events.NodeEvent;
import aimv.listeners.INodeListener;
import aimv.modeling.Node;
import aimv.utilities.Colors;


public class TreeMapItem extends Figure implements INodeListener, MouseListener {
	
	public int peso;
	public int nivel;
	public Node node;
	
	public TreeMapItem(Node node, int n) {
		
		this.node = node;
		node.addListener(this);
		node.setProperty(Properties.TREEMAP_ITEM, this);
		setToolTip(new Label("ID: " + node.getID()));
		
		nivel = n;
		setNivel(nivel);
		setPeso();	
		
		configureColor();
		//configureFiltered();
		//configureSelection();
		
	}//GridItem
	
	@Override
	protected void paintFigure(Graphics graphics) {
		graphics.fillRectangle(getBounds());
	}//paintFigure

	@Override
	public void changeProperty(NodeEvent event) {
		
		if (event.property.equals(Properties.FILTERED))
			configureFiltered();
		else if (event.property.equals(Properties.SELECTION))
			configureSelection();
		else if (event.property.equals(AIMV.getProperty(Properties.COLOR_SELECTED)))
			configureColor();
				
	}//changeProperty
		
	public void configureColor() {
		
		String color = (String) AIMV.getProperty(Properties.COLOR_SELECTED);
		Object property = node.getProperty(color);
		if (property instanceof Color)
			setBackgroundColor((Color) property);
		else
			setBackgroundColor(Colors.WHITE);
		
	}//configureColor
	
	public void configureFiltered() {
		
		removeMouseListener(this);
		setCursor(Cursors.NO);
		setBackgroundColor(Colors.WHITE);
				
		if (!node.isFiltered()) {
			addMouseListener(this);
			setCursor(Cursors.HAND);
			configureSelection();
		}
		repaint();
		
	}//configureFiltered
	
	public void configureSelection() {
		
		Object property = node.getProperty(Properties.SELECTION);
		if (property instanceof Boolean && property.equals(true)) {
			setBackgroundColor(Colors.GRAY);
			atribuirBordaFilhos(Colors.GRAY);
			if (!TreeMap.nodesSelected.contains(node))
				TreeMap.nodesSelected.add(node);
		}
		else {
			configureColor();
			restaurarBordaFilhos();
			TreeMap.nodesSelected.remove(node);
		}
		
	}//configureSelection
	
	public void dispose() {
		
		if (getParent() != null)
			getParent().remove(this);
		
		node.removeListener(this);
		node.setProperty(Properties.TREEMAP_ITEM, null);
		
	}//dispose
	
	@Override
	public void mousePressed(MouseEvent event) {
		
		boolean selec = true;
		if (node.getProperty(Properties.SELECTION) instanceof Boolean)
			selec = !(Boolean) node.getProperty(Properties.SELECTION);
		node.setProperty(Properties.SELECTION, selec);
		
		if (event.getState() != SWT.CONTROL) {
			Node[] aux = TreeMap.nodesSelected.toArray(new Node[0]);
			for (Node n : aux) {
				if (n != node) {
					TreeMap.nodesSelected.remove(n);
					n.setProperty(Properties.SELECTION, false);
				}
			}
		}
		
	}//mousePressed
	
	@Override
	public void addRelation(NodeEvent event) {}

	@Override
	public void removeRelation(NodeEvent event) {}

	@Override
	public void addFilter(NodeEvent event) {}

	@Override
	public void removeFilter(NodeEvent event) {}

	@Override
	public void mouseDoubleClicked(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	private void setNivel(int nivel) {
		this.setBorder(new LineBorder(definirCor(), definirLargura()));
	}
	
	public void setPeso() {
		
		Object property = node.getProperty(TreeMap.propertyArea);
		if (property instanceof Integer)
			peso = (Integer) property;
		else
			peso = 1;
		
	}//setPeso
	
	public void atribuirBordaFilhos(Color color) {
		
		try {
			Node[] list = (Node[]) node.getProperty(TreeMap.propertyRelation);
			if (list != null) {		
				for(Node filho: list){
					TreeMapItem item = (TreeMapItem) filho.getProperty(Properties.TREEMAP_ITEM);	
					item.atribuirBorda(color);
					item.atribuirBordaFilhos(color);
				}
			}
			
		} catch (ClassCastException e) {}
		
	}//atribuirBordaFilhos
	
	public void restaurarBordaFilhos() {
		
		try {
			Node[] list = (Node[]) node.getProperty(TreeMap.propertyRelation);
			if (list != null) {		
				for(Node filho: list){
					TreeMapItem item = (TreeMapItem) filho.getProperty(Properties.TREEMAP_ITEM);	
					item.restaurarBorda();
					item.restaurarBordaFilhos();
				}
			}
			
		} catch (ClassCastException e) {}
		
	}//atribuirBordaFilhos
	
	public void atribuirBorda(Color color){
		this.setBorder(new LineBorder(color, definirLargura()));
	}//atribuirBordaSelecionada
	
	public void restaurarBorda(){
		this.setBorder(new LineBorder(definirCor(), definirLargura()));
	}//atribuirBordaSelecionada
	
	private int definirLargura() {
		int larguraBorda = 1;
		
		/*int maxBorda = 4;
		
		
		if(maxBorda-nivel > 1) larguraBorda = maxBorda-nivel;
		*/
		
		switch(nivel){
		case 0: larguraBorda = 4;
			break;
		case 1: larguraBorda = 3;
			break;
		case 2: larguraBorda = 3;
			break;
		default: larguraBorda = 1;
			break;
		}
		
		
		return larguraBorda;
		
	}//definirLargura
	
	private Color definirCor() {
		
		switch(nivel){
			case 0:
				return Colors.PROJECT;
			case 1:
				return Colors.PACKAGE;
			case 2:
				return Colors.CLASS;
			case 3:
				return Colors.METHODBORDER;
			default:
				return Colors.UNKNOWN;	
		}
		
	}//definirCor


}//class
