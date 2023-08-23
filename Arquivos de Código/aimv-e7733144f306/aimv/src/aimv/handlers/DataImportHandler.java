package aimv.handlers;

import java.io.File;

import javax.swing.JOptionPane;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import aimv.controllers.AIMV;
import aimv.controllers.SessionProgram;

public class DataImportHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();		
		
		FileDialog fd = new FileDialog(window.getShell(), SWT.OPEN);
		fd.setText("Import");
		fd.setFilterPath("C:/");
		String selected = fd.open();
		if (selected != null)
			AIMV.setFonte(new File(selected));

		return null;

	}
	
}
