package givemeviews.importation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

public class FileDialogOpen 
{
	public String getPathToSaveFile(String fileName)
    {
	  FileDialog dialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE
	          | SWT.SINGLE);
	  dialog.setText("Select the local to save file");
	  dialog.setFileName(fileName);
	  dialog.open();
	  
	  if(dialog.getFilterPath().length() > 0)	  
		  return dialog.getFilterPath() + "\\" + dialog.getFileName();
	  else
		  return "";
		  
	}	
}
