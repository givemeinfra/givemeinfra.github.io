package views.graph;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Transform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import views.graph.utilities.Properties;

import aimv.controllers.AIMV;
import aimv.events.RelationEvent;
import aimv.listeners.IRelationListener;
import aimv.modeling.Relation;
import aimv.utilities.Colors;

public class GraphEdge extends Figure implements IRelationListener {
	
	private Point pt1;
	private Point pt2;
	private int radius;
	private static int LINE_WiDTH = 1;
	private Relation relation;

	public GraphEdge(Relation relation) {
		
		this.relation = relation;
		relation.addListener(this);
		relation.setProperty(Properties.GRAPH_ITEM, this);
			
		configureColor();
		configureFiltered();
		configureSelection();
	
	}//GraphEdge
	
	public Relation getRelation() {
		return relation;
	}

	public void setDimension(Point pt1, Point pt2, int radius) {
		this.pt1 = pt1;
		this.pt2 = pt2;
		this.radius = radius;
	}
	
	@Override
	protected void paintFigure(Graphics graphics) {

		graphics.setAntialias(SWT.ON);
		graphics.setLineWidth(LINE_WiDTH);
		//graphics.setForegroundColor(Colors.BLUE);
		//graphics.setBackgroundColor(Colors.BLUE);

		int MODE_VERTICAL = 0;
		int MODE_HORIZONTAL = 1;
		int MODE = MODE_HORIZONTAL;
		int	xSource = pt1.x;
		int ySource = pt1.y;
		int	xTarget = pt2.x;
		int yTarget = pt2.y;
		
		int xMax = Math.max(xSource, xTarget);
		int yMax = Math.max(ySource, yTarget);
		int xMin = Math.min(xSource, xTarget);
		int yMin = Math.min(ySource, yTarget);
		int xDiference = xMax - xMin;
		int yDiference = yMax - yMin;

		double angle = Math.atan((double)(yTarget - ySource) / (double) (xTarget - xSource));
		double angleGraus = (double)(angle * 180 / Math.PI);

		int distance = 0;

		Rectangle arcBounds = new Rectangle();
		Point translatePoint = new Point(xMin + xDiference / 2, yMin + yDiference / 2);
		
		int startAngle = 0;
		int CEx = 0;
		//int CEy = 0;

		Transform transformIntersectionPoint = new Transform();

		double a = 0;

		// Vertical
		if((angleGraus <= -45 && angleGraus >= -90) || (angleGraus >= 45 && angleGraus <= 90)){

			MODE = MODE_VERTICAL;

			startAngle = 90;

			distance = (int) (yDiference / Math.abs(Math.sin(angle)));

			arcBounds.x  = - radius/2;
			arcBounds.y  = -distance/2;
			arcBounds.width = -arcBounds.x*2;
			arcBounds.height = -arcBounds.y*2;

			if((angleGraus <= -45 && angleGraus >= -90)){
				angle = Math.PI/2 + angle;
			} else {
				angle = -(Math.PI/2 - angle);
			}

			if(ySource < yTarget)
				startAngle = 270;

			a = -Math.PI/2;

			if(startAngle == 0 || startAngle == 90)
				a = -a;
				
			a += angle;

			transformIntersectionPoint.setRotation(a);

			CEx = arcBounds.height/2;
			//CEy = 0;

		} else {

			MODE = MODE_HORIZONTAL;
			a = angle;

			distance = (int) (xDiference / Math.abs(Math.cos(angle)));

			arcBounds.x  = -distance/2;
			arcBounds.y  = - radius/2;
			arcBounds.width = -arcBounds.x*2;
			arcBounds.height = -arcBounds.y*2;

			if(xSource > xTarget){
				startAngle = 180;
			} else {
				a = a - Math.PI;
			}

			CEx = arcBounds.width/2;
			//CEy = 0;

			transformIntersectionPoint.setRotation(angle);

		}

		angleGraus = (double)(angle * 180 / Math.PI);
		graphics.pushState();
		graphics.translate(translatePoint);
		graphics.rotate((float)angleGraus);
		graphics.drawArc(arcBounds, startAngle, 180);
		graphics.popState();

		// Intersection Point

		int a2 = Math.max(arcBounds.width / 2, arcBounds.height / 2) * Math.max(arcBounds.width / 2, arcBounds.height / 2);

		int b2 = Math.min(arcBounds.width / 2, arcBounds.height / 2) * Math.min(arcBounds.width / 2, arcBounds.height / 2);

		int r2 = (radius / 2) * (radius / 2);

		int CCx = 0;
		int CCy = 0;

		double A = b2 - a2;
		double B = 2 * (a2 * CCx - b2 * CEx);
		double C = a2*r2 - a2*b2 - a2 * CCx*CCx + b2 * CEx*CEx;
		double delta = (double) Math.sqrt(B*B - (4 * A * C));

		int x = (int)((double) (-B - delta) / (double) (2*A));
		int y = (int)Math.sqrt(r2 - (x - CCx)*(x - CCx)) + CCy;

		double angleCorrection = Math.atan((double)y/(double)x);

		if(startAngle == 0 || startAngle == 90){
			if(MODE == MODE_HORIZONTAL){
				x -= x*2;
				y -= y*2;
			}
		}

		Point intersectionPoint = new Point(x, y);

		PointList arrowPoints = new PointList();

		int size = (int)((float) LINE_WiDTH / 2.5f);

		if(size == 0)
			size = 1;

		int arrowWidth = 6 * size;
		int arrowHeight = 6 * size;

		Transform transform = new Transform();
		Transform transform2 = new Transform();

		transform.setTranslation((radius / 2) + arrowWidth/2, 0);
		transform.setRotation(0);

		transformIntersectionPoint.setRotation(angleCorrection);
		intersectionPoint = transformIntersectionPoint.getTransformed(intersectionPoint);

		Point p1 = transformIntersectionPoint.getTransformed(transform.getTransformed(new Point(-arrowWidth/2, 0)));
		Point p2 = transformIntersectionPoint.getTransformed(transform.getTransformed(new Point(arrowWidth/4, -arrowHeight)));
		Point p3 = transformIntersectionPoint.getTransformed(transform.getTransformed(new Point(arrowWidth/2, arrowHeight/4)));

		transform2.setTranslation(xTarget, yTarget);
		transform2.setRotation(a);

		arrowPoints.addPoint(transform2.getTransformed(p1));
		arrowPoints.addPoint(transform2.getTransformed(p2));
		arrowPoints.addPoint(transform2.getTransformed(p3));

		graphics.fillPolygon(arrowPoints);	
		
	}//paintFigure
		
	private void configureColor() {
		
		Object property = relation.getProperty(Properties.COLOR_SELECTED);
		if (property instanceof Color)
			setBackgroundColor((Color) property);
		else
			setBackgroundColor(Colors.BLACK);
		
	}//configureColor
	
	public void configureFiltered() {
		
		if (relation.isFiltered()) 
			setVisible(!relation.isFiltered());
		else {
			setVisible(!relation.isFiltered());
			Object property = AIMV.getProperty(Properties.GRAPH_OPTIONS); 
			if (property instanceof Integer) {
				int option = (int) property;
				switch (option) {
				case 0:
					property = relation.getSource().getProperty(Properties.SELECTION);
					if (property instanceof Boolean && property.equals(false))
						setVisible(false);
					break;
				case 1:
					property = relation.getTarget().getProperty(Properties.SELECTION);
					if (property instanceof Boolean && property.equals(false))
						setVisible(false);
					break;
				case 3:
					property = relation.getSource().getProperty(Properties.SELECTION);
					if (property instanceof Boolean && property.equals(false)) {
						property = relation.getTarget().getProperty(Properties.SELECTION);
						if (property instanceof Boolean && property.equals(false))
							setVisible(false);
					}
					break;
				case 4:
					property = relation.getSource().getProperty(Properties.SELECTION);
					if (property instanceof Boolean && property.equals(false))
						setVisible(false);
					property = relation.getTarget().getProperty(Properties.SELECTION);
					if (property instanceof Boolean && property.equals(false))
						setVisible(false);
					break;
				}
			}
		}
		
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
		relation.setProperty(Properties.GRAPH_ITEM, null);
		
	}//dispose

	@Override
	public void changeProperty(RelationEvent event) {
		
		if (event.property.equals(Properties.SELECTION))
			configureSelection();
		else if (event.property.equals(Properties.FILTERED))
			configureFiltered();
		else if (event.property.equals(Properties.COLOR_SELECTED))
			configureColor();
		
	}//changeProperty
	
	@Override
	public void addFilter(RelationEvent event) {}

	@Override
	public void removeFilter(RelationEvent event) {}
	

}//class
