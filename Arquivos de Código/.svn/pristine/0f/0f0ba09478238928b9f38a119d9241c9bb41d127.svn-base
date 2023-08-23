package com.br.collaborativeAIMV.eventsManager;

import java.io.FileInputStream;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import com.br.collaborativeAIMV.control.LoginControl;
import com.br.collaborativeAIMV.control.SystemMessagesControl;
import com.br.collaborativeAIMV.log.Log;

class DeltaPrinterVisitor implements IResourceDeltaVisitor {
	
	public boolean visit(IResourceDelta delta) {
		
		
		IResource res = delta.getResource();
		int type = res.getType();

		String projectName = null;
		String packageName = null;
		String resourceName = res.getName();
		String initialContent = null;
		String finalContent = null;
		String action = null;
		
		if (type == IResource.FILE 
				&& (isAcceptedFile(resourceName))){
			
			switch (delta.getKind()) {
			
			case IResourceDelta.ADDED:

				if ((delta.getFlags() & IResourceDelta.MOVED_FROM) != 0) {
					//The REMOVED covers this
				} else {
					if (res.getProject() != null) {
						projectName = res.getProject().getName();
					}
					packageName = this.getPackageName(res);
					action = "Was created.";
				}
				break;
			case IResourceDelta.REMOVED:

				if ((delta.getFlags() & IResourceDelta.MOVED_TO) != 0) {
					if (res.getProject() != null) {
						projectName = res.getProject().getName();
					}
					packageName = this.getPackageName(res);

					// Remove the project name, thus making path relative.
					IPath movedTo = delta.getMovedToPath().removeFirstSegments(
							1);

					action = "Was moved from " + packageName + " to "
							+ movedTo + ".";
				} else {
					if (res.getProject() != null) {
						projectName = res.getProject().getName();
					}
					packageName = this.getPackageName(res);
					action = "Was removed.";
				}
				break;
			case IResourceDelta.CHANGED:

				if ((delta.getFlags() & IResourceDelta.CONTENT) != 0) {

					if (res.getProject() != null) {
						projectName = res.getProject().getName();
					}
					packageName = this.getPackageName(res);
					action = "Was changed.";

					IFile file = (IFile) res;
					try {
						// Getting the final state
						FileInputStream inFinal = (FileInputStream) file
								.getContents();
						StringBuffer strContentFinal = new StringBuffer("");
						int chFinal = 0;
						try {
							while ((chFinal = inFinal.read()) != -1) {
								strContentFinal.append((char) chFinal);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						finalContent = strContentFinal.toString();
						
						// Getting the initial state
						IFileState[] states = file.getHistory(null);
						if (states.length > 0) {

							IFileState initialState = states[0];

							FileInputStream inInitial = (FileInputStream) initialState
									.getContents();
							StringBuffer strContentInitial = new StringBuffer(
									"");
							int chInitial = 0;
							try {
								while ((chInitial = inInitial.read()) != -1) {
									strContentInitial.append((char) chInitial);
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							initialContent = strContentInitial.toString();
						}
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if ((delta.getFlags() & IResourceDelta.REPLACED) != 0) {

					if (res.getProject() != null) {
						projectName = res.getProject().getName();
					}
					packageName = this.getPackageName(res);
					action = "Was replaced.";
				}
				// if ((delta.getFlags() & IResourceDelta.MARKERS) != 0)
				// {
				// Log.print("--> Marker Change");
				// Log.print("Resource ");
				// if (res.getProject()!=null)
				// {
				// String projetctName = res.getProject().getName();
				// Log.print("Projeto: " + projetctName);
				// }
				// Log.print("Pacote: " + this.getPackageName(res));
				// Log.print("Classe: " + res.getName());
				// Log.print("has marked.");
				//
				// IMarkerDelta[] markers = delta.getMarkerDeltas();
				// for (IMarkerDelta marker : markers)
				// {
				// Log.print("Marker: " + marker.getAttributes());
				// }
				// }
				break;
			}
			
			if ( (resourceName!=null && !"".equals(resourceName))
					&& LoginControl.GET_INSTANCE().logado
					&& LoginControl.GET_INSTANCE().currentProject.getName().equals(projectName))
			{
				String message = "PROJECTNAME: " + projectName + "\nPACKAGENAME: " + packageName + "\nRESOURCENAME: " + resourceName +
						"\nACTION: " + action + "\nINITIALCONTENT: " + initialContent + "\nFINALCONTENT: " + finalContent;
				
				Log.print(message);
				
				if(initialContent!=null && finalContent!=null){
					SystemMessagesControl.sendSystemMessage(projectName,
							packageName, resourceName, action, initialContent, finalContent);
				}else{
					SystemMessagesControl.sendSystemMessage(projectName,
							packageName, resourceName, action);
				}
			}
		}

		else if (type == IResource.FOLDER) {

//			IFolder folder = (IFolder) res;
		}

		else if (type == IResource.PROJECT) {
			IProject project = (IProject) res;
		}
		
		return true;
	}

	private String getPackageName(IResource res) {
		String resourcePath = res.getProjectRelativePath().toString();
		String packageName = resourcePath.substring(4);
		packageName = packageName.substring(0,
				packageName.indexOf(res.getName()));
		packageName = packageName.substring(0, packageName.length() - 1);
		return packageName.replaceAll("/", ".");
	}

	private String getPackageName(IPath path, String resourceName) {
		String resourcePath = path.toString();
		String packageName = resourcePath.substring(4);
		packageName = packageName.substring(0,
				packageName.indexOf(resourceName));
		packageName = packageName.substring(0, packageName.length() - 1);
		return packageName.replaceAll("/", ".");
	}
	
	private Boolean isAcceptedFile(String fileName){
		
		return fileName.indexOf(".java") != -1
		|| fileName.indexOf(".xml") != -1;
	}
	
	
}