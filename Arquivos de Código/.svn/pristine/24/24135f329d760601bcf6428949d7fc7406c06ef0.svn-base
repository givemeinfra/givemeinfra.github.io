package views.matrix.itens;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.TextUtilities;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

import views.matrix.utilities.Properties;
import aimv.events.RelationEvent;
import aimv.listeners.IRelationListener;
import aimv.modeling.Relation;
import aimv.utilities.Colors;

public class MatrixRelation extends Figure implements IRelationListener {
	
	private Relation relation;
	
	public MatrixRelation(Relation relation) {
		
		this.relation = relation;
		relation.addListener(this);
		relation.setProperty(Properties.MATRIX_RELATION, this);
			
		configureColor();
		configureFiltered();
		configureProperty();
		configureSelection();
		
	}//MatrixRelation
	
	public Relation getRelation() {
		return relation;
	}
		
	public void setDimension(int x, int y, int w, int h) {
		setBounds(new Rectangle(x, y, w, h));
	}
	
	@Override
	protected void paintFigure(Graphics graphics) {
		
		if (relation.isFiltered())
			graphics.setAlpha(50);
		
		graphics.translate(getLocation().x, getLocation().y);
		graphics.fillRectangle(1, 1, getBounds().width - 2, getBounds().height - 2);
		graphics.drawRectangle(0, 0, getBounds().width - 1, getBounds().height - 1);
		
		graphics.setForegroundColor(Colors.WHITE);
		String property = configureProperty();
		Dimension d = new TextUtilities().getStringExtents(property, getFont());
		graphics.drawString(property, (getBounds().width - d.width)/2, (getBounds().height - d.height)/2);
		
	}//paintFigure
	
	private String configureProperty() {
		
		if (relation.getProperty(Properties.DEGREE) != null)
			return ""+ relation.getProperty(Properties.DEGREE);
		else
			return "";
		
	}//configureProperty
	
	private void configureColor() {
		
		Object property = relation.getProperty(Properties.COLOR);
		if (property instanceof Color)
			setBackgroundColor((Color) property);
		else
			setBackgroundColor(Colors.GRAY_DARK);
		
	}//configureColor
	
	private void configureFiltered() {
		setVisible(!relation.isFiltered());		
	}//configureFiltered
	
	private void configureSelection() {
		
		Object property = relation.getProperty(Properties.SELECTION);
		if (property instanceof Boolean && property.equals(true))
			setBackgroundColor(Colors.GRAY);
		else
			configureColor();
		
	}//configureSelection
	
	public void dispose() {
		
		if (getParent() != null)
			getParent().remove(this);
		
		relation.removeListener(this);
		relation.setProperty(Properties.MATRIX_RELATION, null);
		
	}//dispose

	@Override
	public void changeProperty(RelationEvent event) {
		
		if (event.property.equals(Properties.SELECTION))
			configureSelection();
		else if (event.property.equals(Properties.FILTERED))
			configureFiltered();
		else if (event.property.equals(Properties.COLOR))
			configureColor();
		
	}//changeProperty
	
	@Override
	public void addFilter(RelationEvent event) {}

	@Override
	public void removeFilter(RelationEvent event) {}


}//class
