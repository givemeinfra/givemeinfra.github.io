package sourceminer.modules;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import sourceminer.utilities.Groups;
import sourceminer.utilities.Properties;
import aimv.controllers.Nodes;
import aimv.modeling.Group;
import aimv.modeling.Node;
import aimv.modules.Module;

public class NodesModule extends Module {


	protected void start(Object fonte, IProgressMonitor monitor) {
		
		if (!(fonte instanceof IJavaProject)) 
			return;
		
		Nodes.removeAllGroups();
		Nodes.setGroup(Groups.CLASS, new Group());
		Nodes.setGroup(Groups.METHOD, new Group());
		Nodes.setGroup(Groups.PACKAGE, new Group());
		Nodes.setGroup(Groups.PROJECT, new Group());
		Nodes.setGroup(Groups.ALL, new Group());
		
		try {
			analyzeProject((IJavaProject) fonte, monitor);
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		
	}//start
	
	private void analyzeProject(IJavaProject project, IProgressMonitor monitor) throws JavaModelException {
		
		Node node = new Node("Project " + project.getElementName());
		Nodes.getGroup(Groups.PROJECT).addNode(node);
		Nodes.getGroup(Groups.ALL).addNode(node);
		node.setProperty(Properties.TYPE, Groups.PROJECT);
		node.setProperty(Properties.JAVA_ELEMENT, project);
		monitor.subTask("Analyzing the project entity " + node.getID());
		
		ArrayList<Node> list = new ArrayList<Node>();
		for (IPackageFragmentRoot element : project.getPackageFragmentRoots()) {
			if (!element.isArchive()) {
				for(IJavaElement children : element.getChildren()) {
					if (children instanceof IPackageFragment) { 
						IPackageFragment fragment = (IPackageFragment) children;
						if (fragment.containsJavaResources()) {
							Node filho = analyzePackage(fragment, monitor);
							filho.setProperty(Properties.PARENT, node);
							list.add(filho);
						}
					}
				}
			}
		}
		
		node.setProperty(Properties.PARENT, null);
		node.setProperty(Properties.SELECTION, false);
		Node[] filhos = list.toArray(new Node[0]);
		node.setProperty(Properties.CHILDRENS, filhos);
			
	}//analyzeProject
	
	private Node analyzePackage(IPackageFragment fragment, IProgressMonitor monitor) throws JavaModelException {
		
		Node node = new Node(fragment.getElementName());
		node.setProperty(Properties.JAVA_ELEMENT, fragment);
		node.setProperty(Properties.TYPE, Groups.PACKAGE);
		Nodes.getGroup(Groups.PACKAGE).addNode(node);
		Nodes.getGroup(Groups.ALL).addNode(node);
		monitor.subTask("Analyzing the package entity " + node.getID());
		
		ArrayList<Node> list = new ArrayList<Node>();
		for (ICompilationUnit unit : fragment.getCompilationUnits()) {
			for(IType type : unit.getAllTypes()) {
				Node filho = analyzeType(type, monitor);
				filho.setProperty(Properties.PARENT, node);
				list.add(filho);
			}
		}
		node.setProperty(Properties.SELECTION, false);
		node.setProperty(Properties.CHILDRENS, list.toArray(new Node[0]));
		
		return node;
		
	}//analyzePackage
	
	private Node analyzeType(IType type, IProgressMonitor monitor) throws JavaModelException {
		
		Node node = new Node(type.getFullyQualifiedName('.'));
		node.setProperty(Properties.JAVA_ELEMENT, type);
		node.setProperty(Properties.TYPE, Groups.CLASS);
		Nodes.getGroup(Groups.CLASS).addNode(node);
		Nodes.getGroup(Groups.ALL).addNode(node);
		monitor.subTask("Analyzing the type entity " + node.getID());
		
		ArrayList<Node> list = new ArrayList<Node>();
		for(IMethod method : type.getMethods()) {
			Node filho = analyzeMethod(method, node, monitor);
			if (filho != null) {
				filho.setProperty(Properties.PARENT, node);
				list.add(filho);
			}
		}
		
		Node[] filhos = list.toArray(new Node[0]);
		node.setProperty(Properties.CHILDRENS, filhos);
		node.setProperty(Properties.SELECTION, false);
		return node;
		
	}//analyzeType
	
	private Node analyzeMethod(IMethod method, Node type, IProgressMonitor monitor) throws JavaModelException {
		
		String id = type.getID() + '.' + method.getElementName();
		if (Nodes.getGroup(Groups.METHOD).getNode(id) != null)
			return null;
		
		Node node = new Node(id);
		Nodes.getGroup(Groups.METHOD).addNode(node);
		Nodes.getGroup(Groups.ALL).addNode(node);
		node.setProperty(Properties.JAVA_ELEMENT, method);
		node.setProperty(Properties.TYPE, Groups.METHOD);
		node.setProperty(Properties.CHILDRENS, null);
		node.setProperty(Properties.SELECTION, false);
		monitor.subTask("Analyzing the method entity " + node.getID());
		
		return node;
		
	}//analyzeMethod
	

}//class
