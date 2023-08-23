package views.matrix;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;

import aimv.utilities.Colors;


public class Matrix extends Figure {

	private int num;
	private int width;
	private int height;
	
	public void setDimension(int n, int width, int height) {
		this.num = n;
		this.width = width;
		this.height = height;
		setBackgroundColor(Colors.WHITE);
	}
	
	@Override
	protected void paintFigure(Graphics graphics) {
		
		graphics.fillRectangle(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
		
		for (int i = 0; i <= num; i++)
			drawLines(i, graphics);
		
	}//paintFigure
	
	private void drawLines(int i, Graphics graphics) {
		
		//desenha linha horizontal
		Point p1 = new Point(getLocation().x, getLocation().y + i*height);
		Point p2 = new Point(getLocation().x + num*width, getLocation().y + i*height);
		graphics.drawLine(p1, p2);
		
		//desenha linha vertical
		p1.setLocation(getLocation().x + i*width, getLocation().y);
		p2.setLocation(getLocation().x + i*width, getLocation().y + num*height);
		graphics.drawLine(p1, p2);
		
	}//drawLines
	
	
}//class