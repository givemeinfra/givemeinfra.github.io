package views.polymetric;

import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import views.polymetric.utilities.Properties;
import aimv.controllers.AIMV;
import aimv.events.NodeEvent;
import aimv.listeners.INodeListener;
import aimv.modeling.Node;
import aimv.utilities.Colors;

public class PolymetricItem extends Figure implements INodeListener, MouseListener {
	
	public Node node;
	public int pA = 0;
	public int pL = 0;
	public int lsub = 0;
	public int asub = 0;
	public boolean desenhou;
	
	public PolymetricItem(Node node) {
		
		this.node = node;
		node.addListener(this);
		node.setProperty(Properties.POLIMETRIC_ITEM, this);
			
		configureColor();
		configureFiltered();
		configureSelection();
	
	}//GridItem
	
	@Override
	protected void paintFigure(Graphics graphics) {
		
		if (node.isFiltered())
			graphics.setAlpha(50);
		
		graphics.setAntialias(SWT.ON);	
		graphics.translate(getLocation().x, getLocation().y);
		graphics.fillRoundRectangle(new Rectangle(1, 1, getBounds().width - 2, getBounds().height - 2), 5, 5);
		graphics.drawRoundRectangle(new Rectangle(0, 0, getBounds().width - 1, getBounds().height - 1), 5, 5);
		
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
	
	private void configureFiltered() {
		
		removeMouseListener(this);
		setCursor(Cursors.NO);
				
		if (!node.isFiltered()) {
			addMouseListener(this);
			setCursor(Cursors.HAND);
		}
		repaint();
		
	}//configureFiltered
	
	private void configureSelection() {
		
		Object property = node.getProperty(Properties.SELECTION);
		if (property instanceof Boolean && property.equals(true)) {
			setBackgroundColor(Colors.GRAY);
			if (!Polymetric.nodesSelected.contains(node))
				Polymetric.nodesSelected.add(node);
		}
		else {
			configureColor();
			Polymetric.nodesSelected.remove(node);
		}
		
	}//configureSelection
	
	public void dispose() {
		
		if (getParent() != null)
			getParent().remove(this);
		
		node.removeListener(this);
		node.setProperty(Properties.POLIMETRIC_ITEM, null);
		
	}//dispose
	
	@Override
	public void mousePressed(MouseEvent event) {
		
		boolean selec = true;
		if (node.getProperty(Properties.SELECTION) instanceof Boolean)
			selec = !(Boolean) node.getProperty(Properties.SELECTION);
		node.setProperty(Properties.SELECTION, selec);
		
		if (event.getState() != SWT.CONTROL) {
			Node[] aux = Polymetric.nodesSelected.toArray(new Node[0]);
			for (Node n : aux) {
				if (n != node) {
					Polymetric.nodesSelected.remove(n);
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
	
	
}//class
