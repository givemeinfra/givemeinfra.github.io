package views.matrix.itens;

import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import views.matrix.MatrixView;
import views.matrix.utilities.Properties;

import aimv.controllers.AIMV;
import aimv.events.NodeEvent;
import aimv.listeners.INodeListener;
import aimv.modeling.Node;
import aimv.utilities.Colors;

public class MatrixTopItem extends Figure implements INodeListener, MouseListener {
	
	private Node node;
	private int index;
	
	public MatrixTopItem() {}
	
	public MatrixTopItem(Node node, int index) {
		
		this.node = node;
		this.index = index;
		node.addListener(this);
		node.setProperty(Properties.MATRIX_TOPITEM, this);
			
		configureColor();
		configureFiltered();
		configureSelection();
		//setToolTip(new Label(" ID: " + node.getID() + " "));
	}//MatrixTopItem
	
	public int getIndex() {
		return index;
	}
		
	public void setDimension(int x, int y, int w, int h) {
		setBounds(new Rectangle(x, y, w, h));
	}
	
	@Override
	protected void paintFigure(Graphics graphics) {
		
		if (node.isFiltered())
			graphics.setAlpha(50);
		
		graphics.translate(getLocation().x, getLocation().y);
		graphics.fillRectangle(1, 1, getBounds().width - 2, getBounds().height - 2);
		graphics.drawRectangle(0, 0, getBounds().width - 1, getBounds().height - 1);
		graphics.rotate(90);
		graphics.drawString("" + index, 5, -(getSize().width/2 + 7));
		
	}//paintFigure

	@Override
	public void changeProperty(NodeEvent event) {
		
		if (event.property.equals(Properties.SELECTION))
			configureSelection();
		else if (event.property.equals(Properties.FILTERED))
			configureFiltered();
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
		
		if (node.getProperty(Properties.SELECTION) instanceof Boolean && node.getProperty(Properties.SELECTION).equals(true)) {
			setBackgroundColor(Colors.GRAY);
			if (!MatrixView.nodesSelected.contains(node))
				MatrixView.nodesSelected.add(node);
		}
		else {
			configureColor();
			MatrixView.nodesSelected.remove(node);
		}
		
	}//configureSelection
	
	public void dispose() {
		
		if (getParent() != null)
			getParent().remove(this);
		
		node.removeListener(this);
		node.setProperty(Properties.MATRIX_TOPITEM, null);
		
	}//dispose
	
	@Override
	public void mousePressed(MouseEvent event) {
		
		boolean selec = true;
		if (node.getProperty(Properties.SELECTION) instanceof Boolean)
			selec = !(Boolean) node.getProperty(Properties.SELECTION);
		node.setProperty(Properties.SELECTION, selec);
		
		if (event.getState() != SWT.CONTROL) {
			Node[] aux = MatrixView.nodesSelected.toArray(new Node[0]);
			for (Node n : aux) {
				if (n != node) {
					MatrixView.nodesSelected.remove(n);
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
