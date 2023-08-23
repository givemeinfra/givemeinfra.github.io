package sourceminer.utilities;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

public final class Colors {

	public static final Color BLACK	= new Color(null, 0, 0, 0);
	
	public static final Color WHITE	= new Color(null, 255, 255, 255);
	
	public static final Color RED	= new Color(null, 255, 0, 0);
	
	public static final Color GREEN	= new Color(null, 0, 255, 0);
	
	public static final Color BLUE	= new Color(null, 0, 0, 255);
	
	public static final Color YELLOW = new Color(null, 255, 255, 0);
	


	
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
