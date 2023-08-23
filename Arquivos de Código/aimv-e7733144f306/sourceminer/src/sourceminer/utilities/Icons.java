package sourceminer.utilities;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;

import sourceminer.SourceMiner;

/**
 * This class contains all icon data.
 * @author Rodrigo Magnavita
 *
 */

public final class Icons {

	/*
	 * Attributes
	 */
	
	private static URL iconsPath = Icons.getPath();
	
	public static final ImageDescriptor REFRESH_VIEW 				= Icons.create("refreshview.gif");
	public static final ImageDescriptor ZOOM_IN 					= Icons.create("zoomin.gif");
	public static final ImageDescriptor ZOOM_OUT 					= Icons.create("zoomout.gif");
	public static final ImageDescriptor SAVE	 					= Icons.create("save.gif");
	public static final ImageDescriptor SAVEAS 						= Icons.create("saveas.gif");
	public static final ImageDescriptor SNAPSHOT 					= Icons.create("snapshot.gif");
	public static final ImageDescriptor FITVIEW 					= Icons.create("fittoview.gif");
	/*public static final ImageDescriptor TREEMAP_SIZE 				= Icons.create("treemapSize.gif");
	public static final ImageDescriptor TREEMAP_COMPLEXITY 			= Icons.create("treemapComplexity.gif");*/
	public static final ImageDescriptor FILTER							= Icons.create("filter.gif");	
	public static final ImageDescriptor COLOR_MODE						= Icons.create("colorMode.png");
	public static final ImageDescriptor GRAPH_CLASS_DEPENDENCY_MODE 	= Icons.create("classdependency.gif");
	public static final ImageDescriptor GRAPH_PACKAGE_DEPENDENCY_MODE	= Icons.create("packagedependency.gif");
	public static final ImageDescriptor GRAPH_HIDDEN_MENU 	            = Icons.create("hiddenmenu.gif");
	public static final ImageDescriptor GRAPH_OPAQUE_MENU 	            = Icons.create("opaque.gif");
	
	public static final ImageDescriptor CHECKBOX_SELECTED 		= Icons.create("checked.gif");
	public static final ImageDescriptor CHECKBOX_UNSELECTED 	= Icons.create("unchecked.gif");
	public static final ImageDescriptor RADIO_SELECTED 			= Icons.create("radio_selected.gif");
	public static final ImageDescriptor RADIO_UNSELECTED 	    = Icons.create("radio_unselected.gif");
	public static final ImageDescriptor AREA_MODE				= Icons.create("areaMode.png");
	public static final ImageDescriptor COUPLING_ARROW			= Icons.create("couplingArrow.gif");
	public static final ImageDescriptor FLIP					= Icons.create("flip.png");
	
	public static final ImageDescriptor GRID_CLASS_DEPENDENCY_MODE 		= Icons.create("classdependency.gif");
	public static final ImageDescriptor GRID_PACKAGE_DEPENDENCY_MODE 	= Icons.create("packagedependency.gif");
	public static final ImageDescriptor GRID_HIDDEN_MENU 	            = Icons.create("hiddenmenu.gif");
	public static final ImageDescriptor GRID_OPAQUE_MENU 	            = Icons.create("opaque.gif");
	public static final ImageDescriptor GRID 	                		= Icons.create("grid.gif");
	
	public static final ImageDescriptor MATRIX_CLASS_DEPENDENCY_MODE 	= Icons.create("classmode.gif");
	public static final ImageDescriptor MATRIX_PACKAGE_DEPENDENCY_MODE 	= Icons.create("packmode.gif");
	public static final ImageDescriptor MATRIX_METHOD_DEPENDENCY_MODE 	= Icons.create("methmode.gif");
	
	public static URL getPath(){
		try {
			return new URL(SourceMiner.getDefault().getBundle().getEntry("/"), "resources/icons/");
		} catch (MalformedURLException e){
			return null;
		}		
	}
	
	public static ImageDescriptor create(String name) {
		try {
			return ImageDescriptor.createFromURL(new URL(Icons.iconsPath, name));
		} catch (MalformedURLException e) {
			return ImageDescriptor.getMissingImageDescriptor();
		}
	}
	
}
