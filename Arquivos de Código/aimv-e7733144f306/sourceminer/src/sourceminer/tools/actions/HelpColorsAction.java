package sourceminer.tools.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import aimv.utilities.Colors;


public class HelpColorsAction implements IViewActionDelegate {

	
	@Override
	public void run(IAction action) {
		
		action.setMenuCreator(new IMenuCreator() {
			
			private String[] textoMenuCores = {"Complexity", "Package","Hierarchy","Size","Entity Type","Others"};
			
			private String[] textoOutros = {"Selected","Filtered","Unknown"};
			
			private String[][] textosSubmenuCores = {
					{"Project","Package","Class","(Color Tone) Method"},
					{"Project","(Random Colors) Package","(Package Color) Class","(Package Color) Method"},
					{"Project","Package","Class","Method"},
					{"Project","Package","Class","(Color Tone) Method"},
					{"Project","Package","Interface","Abstract Class","Concrete Class","Enum","Method"},
					{"Selected","Filtered","Method Border","Unknown"}
			};
			private Color[][] coresSubmenuCores = {
					{Colors.PROJECT,Colors.PACKAGE,Colors.CLASS,Colors.COMPLEXITY},
					{Colors.PROJECT,Colors.PACKAGE,Colors.CLASS,Colors.METHOD},
					{Colors.PROJECT,Colors.PACKAGE,Colors.CLASS,Colors.METHOD},
					{Colors.PROJECT,Colors.PACKAGE,Colors.CLASS,Colors.SIZE},
					{Colors.PROJECT,Colors.PACKAGE,Colors.INTERFACE,Colors.ABSTRACT,Colors.CONCRETE,Colors.ENUM,Colors.METHOD},
					{Colors.SELECTED,Colors.FILTERED,Colors.METHODBORDER,Colors.UNKNOWN},
			};
				
			@Override
			public Menu getMenu(Control parent) {
				Menu menu = new Menu(parent);
				
				for (int i = 0; i < textoMenuCores.length; i++)
					createItem(parent, menu, textoMenuCores[i],i);	
				
				return menu;
			}	
			
			private void createItem(Control control, Menu parent, String text,int index){
				
				MenuItem item = new MenuItem(parent, SWT.CASCADE );		
				item.setText(text);
				
				Menu submenu = new Menu(control.getShell(), SWT.DROP_DOWN );	
				item.setMenu(submenu);
				
				for (int i = 0; i < textosSubmenuCores[index].length; i++)
					createSubItem(submenu, textosSubmenuCores[index][i],coresSubmenuCores[index][i], i);	
				
					
			}//createItem
			private void createSubItem(Menu parent, String text,Color cor, int index){
				
				MenuItem item = new MenuItem(parent, SWT.PUSH  );		
				item.setText(text);
				
				Image image = new Image(parent.getDisplay(),16,16);
				
				
				if(text == "(Random Colors) Package" || text == "(Package Color) Class" || text == "(Package Color) Method"){
					PaletteData palette = new PaletteData(0xff, 0xff00, 0xff0000);

				    // ImageData showing variations of hue
				    ImageData hueData = new ImageData(16, 16, 24, palette);
				    float hue = 0;
				    for (int x = 0; x < hueData.width; x++) {
				      for (int y = 0; y < hueData.height; y++) {
				        int pixel = palette.getPixel(new RGB(hue, 1f, 1f));
				        hueData.setPixel(x, y, pixel);
				      }
				      hue += 270f / hueData.width;
				    }
				    image = new Image(parent.getDisplay(), hueData);
				} else{
					
					GC gc = new GC(image);
					gc.setBackground(cor);
					if(text == "(Color Tone) Method"){
						gc.fillGradientRectangle(0, 0, 16, 16,false);
					} else{
						gc.fillRectangle(0, 0, 16, 16);
					}
					gc.drawRectangle(0, 0, 16, 16);
					gc.dispose();  
				}
				
				item.setImage(image);
				
					
			}//createItem
			
			@Override
			public Menu getMenu(Menu parent) {
				return null;
			}
			@Override
			public void dispose() {}
			
		});
		
	}//run

	@Override
	public void selectionChanged(IAction action, ISelection selection) {}

	@Override
	public void init(IViewPart view) {}

	
}//class
