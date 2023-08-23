package com.br.collaborativeAIMV.eventsManager;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.TableViewer;

import com.br.collaborativeAIMV.log.Log;

public class ResourceChangeReporter implements IResourceChangeListener{

	private TableViewer table;
	
	public TableViewer getTable() {
		return table;
	}

	public void setTable(TableViewer table) {
		this.table = table;
	}

	public void resourceChanged(IResourceChangeEvent event) 
	{
//		if (table==null)
//		{
//			table = new TableViewer(parent)
//		}
		
		int eventType = event.getType();
		IResource res = event.getResource();
        
        if (eventType == IResourceChangeEvent.PRE_CLOSE)
        {
        	Log.print("PRE_CLOSE");
        	Log.print("Project");
        	Log.print(res.getFullPath().toOSString());
        	Log.print(" is about to close. ");
        }
        else if (eventType == IResourceChangeEvent.PRE_DELETE)
        {
        	Log.print("PRE_DELETE");
        	Log.print("Project ");
        	Log.print(res.getFullPath().toOSString());
        	Log.print(" is about to be deleted.");
        }
        else if (eventType == IResourceChangeEvent.POST_CHANGE)
        {
        	Log.print("POST_CHANGE");
        	Log.print("Resources have changed.");
        	try {
        		event.getDelta().accept(new DeltaPrinterVisitor());
        	} catch (CoreException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        }
        else if (eventType == IResourceChangeEvent.PRE_BUILD)
        {
        	Log.print("PRE_BUILD");
        	Log.print("Build about to run.");
//        	try {
//        		event.getDelta().accept(new DeltaPrinterVisitor());
//        	} catch (CoreException e) {
//        		// TODO Auto-generated catch block
//        		e.printStackTrace();
//        	}
        }
        else if (res!=null && eventType == IResourceChangeEvent.POST_BUILD)
        {
        	Log.print("POST_BUILD");
        	Log.print("Build complete.");
//        	try {
//        		event.getDelta().accept(new DeltaPrinterVisitor());
//        	} catch (CoreException e) {
//        		// TODO Auto-generated catch block
//        		e.printStackTrace();
//        	}
        }
	}
}
