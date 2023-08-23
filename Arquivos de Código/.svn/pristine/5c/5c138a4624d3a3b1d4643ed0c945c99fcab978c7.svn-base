package aimv.utilities;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

public final class Colors {

	public static final Color BLACK	= new Color(null, 0, 0, 0);
	
	public static final Color WHITE	= new Color(null, 255, 255, 255);
	
	public static final Color YELLOW	= new Color(null, 255, 255, 255);
	
	public static final Color RED	= new Color(null, 255, 0, 0);
	
	public static final Color GREEN	= new Color(null, 0, 255, 0);
	
	public static final Color BLUE	= new Color(null, 0, 0, 255);
	
	public static final Color GRAY  = new Color(null, 192, 192, 192);
	
	public static final Color GRAY_DARK  = new Color(null, 102, 102, 102);

	public static final Color PROJECT = new Color(null, 47, 79, 79);
	public static final Color PACKAGE = new Color(null, 132, 239, 80);	
	public static final Color CLASS = new Color(null, 139, 69, 19);
	public static final Color METHOD = new Color(null, 65, 105, 225);
	public static final Color METHODBORDER = new Color(null,99,184,255);
	
	public static final Color SELECTED = new Color(null, 170,170,170);

	public static final Color FILTERED = new Color(null, 255, 255, 255);
	public static final Color UNKNOWN = new Color(null,51,51,51);
	
	public static final Color ABSTRACT = new Color(null, 250,247,112);
	public static final Color CONCRETE = new Color(null, 237,124,124);
	public static final Color INTERFACE = new Color(null, 160, 115, 247);
	public static final Color ENUM = new Color(null, 140,148,10);
 	
	public static final Color COMPLEXITY = new Color(null,255,255,210);
	public static final Color SIZE = new Color(null,236,202,236);

	public static Color getColorFromHex(String hex){
		return new Color(null, Colors.getRGBFromHex(hex));
	}
	
	public static RGB getRGBFromHex(String hex){
		RGB rgb = new RGB(0, 0, 0);

		rgb.red = Integer.decode("#" + hex.substring(1, 3));
		rgb.green = Integer.decode("#" + hex.substring(3, 5));
		rgb.blue = Integer.decode("#" + hex.substring(5, 7));
		
		return rgb;
	}
	
	public static Color getColorFromRGB(RGB rgb) {
		return new Color(null, rgb.red, rgb.green, rgb.blue);
	}
	
}
