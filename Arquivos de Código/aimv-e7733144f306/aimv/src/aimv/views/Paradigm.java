package aimv.views;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Composite;


public abstract class Paradigm extends ViewAIMV {

	private Figure figure;
	private FigureCanvas canvas;

	@Override
	public void createPartControl(Composite parent) {
		
		canvas = new FigureCanvas(parent, SWT.NONE);
		figure = new Figure();
		canvas.setContents(figure);
		super.createPartControl(parent);
		
		start();
		
	}//createPartControl
	
	private void start() {
		
		getCanvas().addControlListener(new ControlListener() {
			@Override
			public void controlMoved(ControlEvent e) {
				//layout();
			}

			@Override
			public void controlResized(ControlEvent e) {
				int width = getCanvas().getClientArea().width;
				int height = getCanvas().getClientArea().height;
				getFigure().setSize(width, height);
				controlResize();
				layout();
			}
		});
		
	}//start
	
	public void setDafaultLayout() {
		
		getFigure().removeAll();
		Label label = new Label("There are no data to be viewed!");
		label.setSize(getFigure().getSize());
		getFigure().add(label);
		
	}//setDafaultLayout
    
	public FigureCanvas getCanvas() {
		return canvas;
	}

	public Figure getFigure() {
		return figure;
	}

    
}//abstract class
