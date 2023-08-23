package com.br.service.valueObject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.ui.packageview.PackageFragmentRootContainer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.internal.Workbench;

public class ActivePageVO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 133790289111152055L;

	private String activePageName;
	private String activePageProjectName;

	public static ActivePageVO getCurrentProjectFromActivePage() {

		ActivePageVO activePageVO = null;

		IEditorPart editorPart = Workbench.getInstance()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();

		if (editorPart != null) {
			
			activePageVO = new ActivePageVO();
			
			IFileEditorInput input = (IFileEditorInput) editorPart
					.getEditorInput();
			IFile file = input.getFile();
			String path = file.getFullPath().toString();
			String activePage = path.substring( (path.indexOf("/src/") + 5), path.length());
			
			activePageVO.activePageProjectName = file.getProject().getName();
			activePageVO.activePageName = activePage.replaceAll("/", ".");
		}

		
		if (activePageVO == null) {

			IProject project = null;
			
			ISelectionService selectionService = Workbench.getInstance()
					.getActiveWorkbenchWindow().getSelectionService();

			ISelection selection = selectionService.getSelection();

			if (selection instanceof IStructuredSelection) {
				Object element = ((IStructuredSelection) selection)
						.getFirstElement();

				if (element instanceof IResource) {
					project = ((IResource) element).getProject();
				} else if (element instanceof PackageFragmentRootContainer) {
					IJavaProject jProject = ((PackageFragmentRootContainer) element)
							.getJavaProject();
					project = jProject.getProject();
				} else if (element instanceof IJavaElement) {
					IJavaProject jProject = ((IJavaElement) element)
							.getJavaProject();
					project = jProject.getProject();
				}
			}
			
			if (project!=null){
				
				activePageVO = new ActivePageVO();
				activePageVO.activePageProjectName = project.getName();
			}
		}

		return activePageVO;
	}

	public String getActivePageName() {
		return activePageName;
	}

	public String getActivePageProjectName() {
		return activePageProjectName;
	}
}
