package views.matrix;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;


public class MatrixContainer extends Figure {
	
	@Override
	protected void paintFigure(Graphics graphics) {
		graphics.translate(getLocation().x, getLocation().y);
		graphics.fillRectangle(1, 1, getBounds().width - 2, getBounds().height - 2);
		graphics.drawRectangle(0, 0, getBounds().width - 1, getBounds().height - 1);
	}//paintFigure
	
	
}//class