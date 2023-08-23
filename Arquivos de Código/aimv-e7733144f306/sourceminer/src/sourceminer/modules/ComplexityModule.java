package sourceminer.modules;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import sourceminer.utilities.Groups;
import sourceminer.utilities.Properties;
import aimv.controllers.Nodes;
import aimv.modeling.Node;
import aimv.modules.Module;

public class ComplexityModule extends Module {
	
	@Override
	protected void start(Object fonte, IProgressMonitor monitor) {
		
		if (!(fonte instanceof IJavaProject) || Nodes.getGroup(Groups.ALL) == null) 
			return;
		
		monitor.subTask("Calculating the complexity...");
		for (Node node : Nodes.getGroup(Groups.ALL).getNodes())
			node.setProperty(Properties.COMPLEXITY, 1);
		
		try {
			ComplexityVisitor visitor = new ComplexityVisitor();
			for (Node node : Nodes.getGroup(Groups.PACKAGE).getNodes()) {
				IPackageFragment fragment = (IPackageFragment) node.getProperty(Properties.JAVA_ELEMENT); 
				for (ICompilationUnit unit : fragment.getCompilationUnits()) {
					ASTParser ast = ASTParser.newParser(AST.JLS3);
					ast.setSource(unit);
					ast.setResolveBindings(true);
					ast.setKind(ASTParser.K_COMPILATION_UNIT);
					((CompilationUnit)ast.createAST(null)).accept(visitor);
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		
	}//start
	
	
}//class

class ComplexityVisitor extends ASTVisitor {
	
	private Node currentClass;
	
	@Override
	public boolean visit(TypeDeclaration node) {
		
		try{
		if(node.resolveBinding().isFromSource()) {
			String id = node.resolveBinding().getQualifiedName();
			currentClass = Nodes.getGroup(Groups.CLASS).getNode(id);
		}
		
		return true;}
		catch (Exception e) {
			return false;
		}
		
	}//visit(TypeDeclaration node)
	
	@Override
	public boolean visit(MethodDeclaration node) {
	
		if (currentClass != null) {
			String id = currentClass.getID() + '.' + node.getName().getFullyQualifiedName();
			Node method = Nodes.getGroup(Groups.METHOD).getNode(id);
			if (method != null) {
				int complexity = processBlock(node.getBody());
				method.setProperty(Properties.COMPLEXITY, complexity);
			}
		}
		return true;
		
	}//visit(MethodDeclaration node)
	
	@SuppressWarnings("unchecked")
	private int processBlock(Block codeBlock){
		
		int calculatecc = 1;
		if(codeBlock != null){
			// if there is code..
			try{
				List<Statement> statements = codeBlock.statements();
				
				if(statements != null){
					// if there are statements..
					for(int i = 0; i < statements.size(); i++){
						Statement currentStatement = (Statement) statements.get(i);
						calculatecc += processStatement(currentStatement);
					}
				}

			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return calculatecc;
		
	}//processBlock

	private int processStatement(Statement currentStatement) {
		
		if(currentStatement == null)
			return 0;
		
		switch(currentStatement.getNodeType()){
			
			case ASTNode.IF_STATEMENT: return 1;
			case ASTNode.FOR_STATEMENT: return 1;
			case ASTNode.WHILE_STATEMENT: return 1;
			case ASTNode.SWITCH_STATEMENT: return 1;
			case ASTNode.BLOCK:
				processBlock((Block) currentStatement);
				break;
		}
		
		return 0;
		
	}//processStatement
	
}//class
