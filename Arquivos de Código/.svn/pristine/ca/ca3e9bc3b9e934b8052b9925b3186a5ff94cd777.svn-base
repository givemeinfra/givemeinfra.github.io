package com.br.collaborativeAIMV.eventsManager;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.SourceField;
import org.eclipse.jdt.internal.core.SourceMethod;
import org.eclipse.jdt.internal.core.SourceType;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.jdt.internal.ui.packageview.PackageExplorerPart;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.contentoutline.ContentOutline;

import com.br.collaborativeAIMV.control.ConcernsControl;
import com.br.collaborativeAIMV.control.LoginControl;
import com.br.collaborativeAIMV.control.NodesControl;
import com.br.collaborativeAIMV.control.SystemMessagesControl;
import com.br.collaborativeAIMV.delegate.MessageDelegate;
import com.br.collaborativeAIMV.log.Log;
import com.br.service.valueObject.ActivePageVO;
import com.br.service.valueObject.MessageVO;

@SuppressWarnings("restriction")
public class EventsManager implements IStartup {
	
	@Override
	public void earlyStartup() {
		this.startListeners();
	}

	private void startListeners() {

		PlatformUI.getWorkbench().addWindowListener(new IWindowListener() {

			@Override
			public void windowActivated(IWorkbenchWindow window) {
				Log.print("WORKBENCH ACTIVATED");
			}

			@Override
			public void windowClosed(IWorkbenchWindow window) {
				Log.print("WORKBENCH CLOSED");
				if (LoginControl.GET_INSTANCE().currentProject != null){
					SystemMessagesControl.sendSystemMessage(LoginControl.GET_INSTANCE().currentProject.getName(),
							"", "", "Eclipse closed.");
				}	
			}

			@Override
			public void windowDeactivated(IWorkbenchWindow window) {
				Log.print("WORKBENCH DEACTIVATED");
			}

			@Override
			public void windowOpened(IWorkbenchWindow window) {
				Log.print("WORKBENCH OPENED");
			}
		});

		// Listener for updates resources in workspace
		IResourceChangeListener listener = new ResourceChangeReporter();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				listener,
				IResourceChangeEvent.PRE_CLOSE
						| IResourceChangeEvent.PRE_DELETE
						| IResourceChangeEvent.PRE_BUILD
						| IResourceChangeEvent.POST_BUILD
						| IResourceChangeEvent.POST_CHANGE);

		if ( PlatformUI.getWorkbench().getActiveWorkbenchWindow()!=null 
				&& PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
			
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().addSelectionListener(

				new ISelectionListener() {

					@Override
					public void selectionChanged(IWorkbenchPart part,
							ISelection selection) {
						if (part instanceof CompilationUnitEditor) {
							if (selection instanceof TreeSelection) {
								Log.print("EDITOR SELECTION "
										+ part.getTitle() + " " + ((TreeSelection) selection).getPaths());
								
								
							} else if (selection instanceof TextSelection) {
								Log.print("EDITOR SELECTION "
										+ part.getTitle() + " " + ((TextSelection) selection).getText());
							}
						} else if (part instanceof ContentOutline) {
							Log.print(part.getTitle()
									+ " SELECTION " + ((IJavaElement) ((TreeSelection) selection)
											.getFirstElement()).getElementName());
							
						} else if (part instanceof PackageExplorerPart) {
							String log = part.getTitle() + " SELECTION ";

							List selections = ((TreeSelection) selection).toList();

							try {
								
								for(int i = 0; i < selections.size(); i++){
									if(selections.get(i).getClass().getName().equals(SourceType.class.getName())){
										Log.print(log + ((IType) selections.get(i)).getFullyQualifiedName());
									}else if(selections.get(i).getClass().getName().equals(SourceField.class.getName())){
										Log.print(log + ((IType)((IField) selections.get(i)).getParent()).getFullyQualifiedName() + "." 
												+ ((IField) selections.get(i)).getElementName());
									}else if(selections.get(i).getClass().getName().equals(SourceMethod.class.getName())){
										Log.print(log + ((IType)((IMethod) selections.get(i)).getParent()).getFullyQualifiedName() + "." 
												+ ((IMethod) selections.get(i)).getElementName() + "(" + ((IMethod) selections.get(i)).getSignature() + ")");
									}else{
										Log.print(log + ((IJavaElement) selections.get(i)).getElementName());
									}
								}
							} catch (JavaModelException e) {
								e.printStackTrace();
							}
						}
					}
				});

		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.addPartListener(new IPartListener() {

					@Override
					public void partActivated(IWorkbenchPart part) {
						
						//Adiciona os eventos nas visões
						if( (part.getTitle().equals("TreeMap") || part.getTitle().equals("Graph") || part.getTitle().equals("Grid") ||
								part.getTitle().equals("Matrix") || part.getTitle().equals("Polymetric")) && 
								LoginControl.GET_INSTANCE().isRightProjectAnalysed()){
							//TODO melhorar
//							ViewControl.GET_INSTANCE().addListenerOnViewsThread();
							NodesControl.GET_INSTANCE().addListenerOnViewPropertyOfNodes();
						}

						String name = part.getTitle();

						String second = "";
						if (part instanceof CompilationUnitEditor) {
							second = name;
							name = "Editor";
						}

						Log.print(name + " ACTIVATED " + second);
					}

					@Override
					public void partBroughtToTop(IWorkbenchPart part) {
						
						String name = part.getTitle();
						String second = "";
						
						//Faz a pesquisa de mensagens a cada seleção de visão.
						ConcernsControl.GET_INSTANCE().removeMessageConcernFromNodes();
						ConcernsControl.GET_INSTANCE().addMessageConcernFromXml();
						
						if(part instanceof CompilationUnitEditor){
							second = name;
							name = "Editor";
							
							//INICIA O ENVIO DE MENSAGEM DE SISTEMA.
							
							ActivePageVO activePageVO = ActivePageVO.getCurrentProjectFromActivePage();
							
							if(activePageVO!=null && !"".equals(activePageVO.getActivePageProjectName())){
								
								if (LoginControl.GET_INSTANCE().logado &&
										LoginControl.GET_INSTANCE().currentProject.getName().equals(
												activePageVO.getActivePageProjectName())) {
									//COMECA
									MessageDelegate messageDelegate = new MessageDelegate();
									
									MessageVO message = new MessageVO();
									message.setActivity(SystemMessagesControl.activitySelected);
									SimpleDateFormat sdf = new SimpleDateFormat(SystemMessagesControl.DATE_FORMAT);
									message.setDate(sdf.format(new Date()));
									message.setDeveloperVO(LoginControl.GET_INSTANCE().currentDeveloper);
									message.setEntity(activePageVO.getActivePageName());
									message.setParadigm("EDITOR");
									message.setText("Was brought to top.");
									message.setFinalContent("");
									message.setInitialContent("");

									try {
										messageDelegate.sendSystemMessageOnProject(message);
									} catch (RemoteException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									//TERMINA
								}
							}
							
							//FINALIZA O ENVIO DE MENSAGEM DE SISTEMA.
						}
						Log.print(name + " BroughtToTop " + second);
					}

					@Override
					public void partClosed(IWorkbenchPart part) {
						
						//Retira os listeners das visões de metáforas.
//						if(part.getTitle().equals("TreeMap") || part.getTitle().equals("Graph") || part.getTitle().equals("Grid") ||
//								part.getTitle().equals("Matrix") || part.getTitle().equals("Polymetric")){
//							
//							if(LoginControl.GET_INSTANCE().isRightProjectAnalysed()){
//								ViewControl.GET_INSTANCE().removeAllListenersOnViews();
//								NodesControl.GET_INSTANCE().removeAllListenersOnViewPropertyOfNodes();
//							}
//						}
						
//						String name = part.getTitle();
//						String second = "";
//						if (part instanceof CompilationUnitEditor) {
//							second = name;
//							name = "Editor";
//
//							//INICIA O ENVIO DE MENSAGEM DE SISTEMA.
//							ActivePageVO activePageVO = ActivePageVO.getCurrentProjectFromActivePage();
//							
//							if(activePageVO!=null && !"".equals(activePageVO.getActivePageProjectName())){
//								
//								if (LoginControl.GET_INSTANCE().logado &&
//										LoginControl.GET_INSTANCE().currentProject.getName().equals(
//												activePageVO.getActivePageProjectName())) {
//									//COMECA
//									MessageDelegate messageDelegate = new MessageDelegate();
//									
//									MessageVO message = new MessageVO();
//									message.setActivity(SystemMessagesControl.activitySelected);
//									SimpleDateFormat sdf = new SimpleDateFormat(SystemMessagesControl.dateFormat);
//									message.setDate(sdf.format(new Date()));
//									message.setDeveloperVO(LoginControl.GET_INSTANCE().currentDeveloper);
//									message.setEntity(activePageVO.getActivePageName());
//									message.setParadigm("EDITOR");
//									message.setText("Was closed.");
//									message.setFinalContent("");
//									message.setInitialContent("");
//
//									try {
//										messageDelegate.sendSystemMessageOnProject(message);
//									} catch (RemoteException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									}
//									//TERMINA
//								}
//							}
//							//FINALIZA O ENVIO DE MENSAGEM DE SISTEMA.
//						}

//						Log.print(name + " CLOSED " + second);
					}

					@Override
					public void partDeactivated(IWorkbenchPart part) {
						String name = part.getTitle();
						String second = "";
						if (part instanceof CompilationUnitEditor) {
							second = name;
							name = "Editor";
						}

						Log.print(name + " DEACTIVATED " + second);
					}

					@Override
					public void partOpened(IWorkbenchPart part) {
						String name = part.getTitle();
						String second = "";

						if (part instanceof CompilationUnitEditor) {
							second = name;
							name = "Editor";
							// Log.sendSystemMessage(part, "Editor", "Aberto");
						}
						Log.print(name + " OPENED " + second);
					}
				});
		}
	}
}
