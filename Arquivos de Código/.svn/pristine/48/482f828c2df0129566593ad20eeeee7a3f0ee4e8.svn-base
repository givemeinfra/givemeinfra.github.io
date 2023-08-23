package sourceminer.filters.controls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

public abstract class AbstractSliderWidget extends Canvas {

	int sliderHeight = 20;
	int sliderLength;
	int minLength = 200;
	int offset = 35;

	public String minStringValue;
	public String maxStringValue;

	double smallTicksDelta = 1;
	int smallTicksLength = 5;
	double largeTicksDelta = 10;
	int largeTicksLength = 10;

	Color tickColor;

	double minValue;
	double maxValue;
	double valueRange;


	static FocusListener focusListener = new FocusAdapter() {
		public void focusGained(FocusEvent e) {
			((Composite) e.widget).setFocus();
		}
	};

	static ControlListener controlListener = new ControlAdapter() {
		public void controlResized(ControlEvent e) {
			AbstractSliderWidget widget = (AbstractSliderWidget) e.widget;
			widget.controlResized();
		}
	};

	static MouseTrackListener mouseTrackListener = new MouseTrackAdapter() {
		public void mouseEnter(MouseEvent e) {
			AbstractSliderWidget widget = (AbstractSliderWidget) e.widget;
			widget.mouseEnter(e);
		}

		public void mouseExit(MouseEvent e) {
			AbstractSliderWidget widget = (AbstractSliderWidget) e.widget;
			widget.mouseExit(e);
		}
	};

	static MouseListener mouseListener = new MouseAdapter() {
		public void mouseDown(MouseEvent e) {
			AbstractSliderWidget widget = (AbstractSliderWidget) e.widget;
			widget.mouseDown(e);
		}

		public void mouseUp(MouseEvent e) {
			AbstractSliderWidget widget = (AbstractSliderWidget) e.widget;
			widget.mouseUp(e);
		}
	};

	static MouseMoveListener mouseMoveListener = new MouseMoveListener() {
		public void mouseMove(MouseEvent e) {
			AbstractSliderWidget widget = (AbstractSliderWidget) e.widget;
			widget.mouseMove(e);
		}
	};

	static PaintListener paintListener = new PaintListener() {
    	public void paintControl(PaintEvent e) {
			AbstractSliderWidget widget = (AbstractSliderWidget) e.widget;
    		widget.paintControl(e);
    	}
	};

	private List<SelectionListener> selectionListeners;

	public AbstractSliderWidget(Composite parent, int style, double minValue, double maxValue) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		this.minValue = minValue;
		this.maxValue = maxValue;
		valueRange = maxValue - minValue;
		if(valueRange == 100.0) {
			largeTicksDelta = 10.0;
			smallTicksDelta = 1.0;
		} else {
			largeTicksDelta = Math.pow(10.0, Math.floor(Math.log10(valueRange)));
			smallTicksDelta = largeTicksDelta / 10.0;
		}

		tickColor = getDisplay().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);

		addFocusListener(focusListener);
		addControlListener(controlListener);
		addMouseTrackListener(mouseTrackListener);
		addMouseListener(mouseListener);
	    addPaintListener(paintListener);
	}

	public AbstractSliderWidget(Composite parent, int style, double minValue, double maxValue, String minString, String maxString) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		this.minStringValue = minString;
		this.maxStringValue = maxString;
		this.minValue = minValue;
		this.maxValue = maxValue;
		valueRange = maxValue - minValue;
		if(valueRange == 100.0) {
			largeTicksDelta = 10.0;
			smallTicksDelta = 1.0;
		} else {
			largeTicksDelta = Math.pow(10.0, Math.floor(Math.log10(valueRange)));
			smallTicksDelta = largeTicksDelta / 10.0;
		}

		tickColor = getDisplay().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);

		addFocusListener(focusListener);
		addControlListener(controlListener);
		addMouseTrackListener(mouseTrackListener);
		addMouseListener(mouseListener);
	    addPaintListener(paintListener);
	}

	protected void controlResized() {
		sliderLength = getSize().x - 2 * offset;
	}

	protected void mouseEnter(MouseEvent e) {
		tickColor = e.display.getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW);
		redraw();
	}

	protected void mouseExit(MouseEvent e) {
		tickColor = e.display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
		redraw();
	}

	protected void mouseDown(MouseEvent e) {
		addMouseMoveListener(mouseMoveListener);
		update(e);
	}

	protected void mouseMove(MouseEvent e) {
		update(e);
	}

	protected void mouseUp(MouseEvent e) {
		removeMouseMoveListener(mouseMoveListener);
		update(e);
	}

	abstract protected void update(MouseEvent e);

	protected void paintControl(PaintEvent e) {
		 
		GC gc = e.gc;
		Transform transform = new Transform(e.display);
		transform.translate(offset, 0f);
		transform.scale((float)(1.0/valueRange), 1f);
		gc.setTransform(transform);

		int height = getSize().y;
		gc.setTransform(null);
		int top = height - gc.stringExtent(String.valueOf((int)minValue)).y + 2;
		gc.drawString(this.minStringValue, offset - 10 - gc.stringExtent(String.valueOf((int)minValue)).x , top);
		gc.drawString(this.maxStringValue, offset + sliderLength + 10, top);
		 
	}//paintControl

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		return new Point(Math.max(wHint, minLength + 2 * offset), sliderHeight);
	}

	public void addSelectionListener(SelectionListener listener) {
		if (selectionListeners == null) selectionListeners = new ArrayList<SelectionListener>();
		selectionListeners.add(listener);
	}

	public void removeSelectionListener(SelectionListener listener) {
		if (selectionListeners == null) return;
		selectionListeners.remove(listener);
	}

	@SuppressWarnings("rawtypes")
	protected void notifySelectionListeners() {
		
		if (selectionListeners==null) return;
		for (Iterator iter = selectionListeners.iterator(); iter.hasNext();) {
			SelectionListener listener = (SelectionListener) iter.next();
			Event event = new Event();
			event.widget = this;
			listener.widgetSelected(new SelectionEvent(event));
		}
		
	}//notifySelectionListeners

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}
	

}//class

