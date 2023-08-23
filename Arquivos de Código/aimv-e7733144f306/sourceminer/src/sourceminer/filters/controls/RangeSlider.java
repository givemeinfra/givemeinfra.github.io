package sourceminer.filters.controls;

import java.math.BigDecimal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Composite;

public class RangeSlider extends AbstractSliderWidget {

	int leftSliderValue;
	int rightSliderValue;
	double leftValue;
	double rightValue;

	int[] leftValueMark = {0, 10, 0, 0, -5, 0, -5, 5};
	int[] rightValueMark = {0, 10, 0, 0, 5, 0, 5, 5};

	int mode;
	static final int NO_MODE = 0;
	static final int LEFT_MODE = 1;
	static final int RIGHT_MODE = 2;
	static final int RANGE_MODE = 3;

	int xMouse;
	int oldLeftSliderValue;
	int oldRightSliderValue;

	public RangeSlider(Composite parent, int style, double minValue, double maxValue) {
		super(parent, style, minValue, maxValue);
		this.leftValue = minValue;
		this.rightValue = maxValue;
	}

	public RangeSlider(Composite parent, int style, double minValue, double maxValue, String minString,  String maxString) {
		super(parent, style, minValue, maxValue, minString, maxString);
		this.leftValue = minValue;
		this.rightValue = maxValue;
	}

	@Override
	protected void controlResized() {
		super.controlResized();
		leftSliderValue = (int)((leftValue - super.minValue) * sliderLength / valueRange);
		rightSliderValue = (int)((rightValue - super.minValue) * sliderLength / valueRange);
	}

	@Override
	protected void update(MouseEvent e) {
		
		switch (mode) {
		case LEFT_MODE:
			leftValue = computeValue(oldLeftSliderValue + e.x - xMouse);
			rightValue = Math.max(leftValue, rightValue);
			this.controlResized();
			break;
		case RIGHT_MODE:
			rightValue = computeValue(oldRightSliderValue + e.x - xMouse);
			leftValue = Math.min(leftValue, rightValue);
			this.controlResized();
			break;
		case RANGE_MODE:
			leftValue = computeValue(oldLeftSliderValue + e.x - xMouse);
			rightValue = computeValue(oldRightSliderValue + e.x - xMouse);
			this.controlResized();
			break;
		}
		redraw();
		notifySelectionListeners();
	}

	double computeValue(double sliderValue) {
		double value = minValue + (sliderValue * valueRange / sliderLength);
		int logValue = (int)Math.ceil(Math.log10(valueRange))-2;
		BigDecimal bigFactor = bigPow10(logValue);
		double smallTicksLength = smallTicksDelta * sliderLength / valueRange;

		if (smallTicksLength >= 8) bigFactor = bigFactor.divide(new BigDecimal(8));
		else if (smallTicksLength >= 5) bigFactor = bigFactor.divide(new BigDecimal(5));
		else if (smallTicksLength >= 4) bigFactor = bigFactor.divide(new BigDecimal(4));
		else if (smallTicksLength >= 2) bigFactor = bigFactor.divide(new BigDecimal(2));

		BigDecimal bigValue = new BigDecimal(value);
		bigValue = bigValue.divideToIntegralValue(bigFactor).multiply(bigFactor);
		value = bigValue.doubleValue();
		value = Math.min(value, maxValue);
		value = Math.max(value, minValue);
		return value;
	}

	private BigDecimal bigPow10(double logValue) {
		BigDecimal bigTen = new BigDecimal(10);
		BigDecimal result = new BigDecimal(1);
		int count = (int) logValue;
		while (count>0) {
			count --;
			result = result.multiply(bigTen);
		}
		while (count<0) {
			count ++;
			result = result.divide(bigTen);
		}
		return result;
	}


	@Override
	protected void paintControl(PaintEvent e) {
		super.paintControl(e);

		GC gc = e.gc;
		gc.setAntialias(SWT.ON);
		gc.setTransform(null);
		gc.drawLine(leftSliderValue + offset, 0, rightSliderValue + offset, 0);
		gc.drawLine(leftSliderValue + offset, 5, rightSliderValue + offset, 5);


		Transform transform = new Transform(e.display);
		transform.translate(offset + leftSliderValue, 0f);
		gc.setTransform(transform);

		gc.fillPolygon(leftValueMark);
		gc.drawPolygon(leftValueMark);
		transform.dispose();

		transform = new Transform(e.display);
		transform.translate(offset + rightSliderValue, 0f);
		gc.setTransform(transform);

		gc.fillPolygon(rightValueMark);
		gc.drawPolygon(rightValueMark);
		transform.dispose();

}

	@Override
	protected void mouseDown(MouseEvent e) {
		xMouse = e.x;
		oldLeftSliderValue = leftSliderValue;
		oldRightSliderValue = rightSliderValue;

		if ((e.x >= leftSliderValue + offset) && (e.x <= rightSliderValue + offset)
				&& (e.y >= 0) && (e.y <= 5)) {
			mode = RANGE_MODE;
		}
		if ((e.x >= leftSliderValue) && (e.x <= leftSliderValue + offset)
				&& (e.y >= 0) && (e.y <= 10)) {
			mode = LEFT_MODE;
		}
		if ((e.x >= rightSliderValue+ offset) && (e.x <= rightSliderValue + 2*offset)
				&& (e.y >= 0) && (e.y <= 10)) {
			mode = RIGHT_MODE;
		}
		super.mouseDown(e);
	}

	public double getLeftValue() {
		return leftValue;
	}

	public void setLeftValue(double leftValue) {
		if (this.leftValue == leftValue) return;
		this.leftValue = leftValue;
		this.leftValue = Math.min(this.leftValue, maxValue);
		this.leftValue = Math.max(this.leftValue, minValue);
		leftSliderValue = (int)((this.leftValue - minValue) * sliderLength / valueRange);
		redraw();
	}

	public double getRightValue() {
		return rightValue;
	}

	public void setRightValue(double rightValue) {
		if (this.rightValue == rightValue) return;
		this.rightValue = rightValue;
		this.rightValue = Math.min(this.rightValue, maxValue);
		this.rightValue = Math.max(this.rightValue, minValue);
		rightSliderValue = (int)((this.rightValue - minValue) * sliderLength / valueRange);
		redraw();
	}

}

