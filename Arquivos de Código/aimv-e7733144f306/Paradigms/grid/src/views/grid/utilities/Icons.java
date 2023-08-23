package views.grid.utilities;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;


public class Icons {

	private static URL url = getPath();
	public static ImageDescriptor GRID_SORT = create("gridsort.gif");
	public static ImageDescriptor GRID_PROPERTY = create("gridproperty.gif");
	
	private static URL getPath(){
		
		try {
			return new URL(Platform.getBundle("org.sourceminer.views.grid").getEntry("/"), "icons/");
		} catch (MalformedURLException e){
			return null;
		}		
		
	}//getPath
	
	private static ImageDescriptor create(String name) {
		
		try {
			return ImageDescriptor.createFromURL(new URL(url, name));
		} catch (MalformedURLException e) {
			return ImageDescriptor.getMissingImageDescriptor();
		}
		
	}//create
	
	
}//class
