package sourceminer.modules;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import sourceminer.utilities.Groups;
import sourceminer.utilities.Properties;
import sourceminer.utilities.RelationType;
import aimv.controllers.Nodes;
import aimv.modeling.Node;
import aimv.modeling.Relation;
import aimv.modules.Module;

public class RelationsModule extends Module {

	
	protected void start(Object fonte, IProgressMonitor monitor) {
		
		if (!(fonte instanceof IJavaProject)) 
			return;
			
		for (String group : Nodes.getGroups()) {
			for (Node node : Nodes.getGroup(group).getNodes()) {
				node.setProperty(Properties.COUPLING, 0);
				node.setProperty(Properties.COUPLING_AFFERENT, 0);
				node.setProperty(Properties.COUPLING_EFFERENT, 0);
			}
		}
		
		try {
			
			monitor.subTask("Obtaining relationships from the project");
			RelationsVisitor visitor = new RelationsVisitor();
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

class RelationsVisitor extends ASTVisitor {
	
	private String currentClass = null;
	private String currentMethod = null;
	
	private void buildDependency(String src, String trg, String dependencyType){

		Node source = Nodes.getGroup(Groups.ALL).getNode(src);
		Node target = Nodes.getGroup(Groups.ALL).getNode(trg);
		
		while (source != null && target != null) {
			
			if (source == target)
				return;
			
			createDependency(source, target, dependencyType);
			source = (Node) source.getProperty(Properties.PARENT);
			target = (Node) target.getProperty(Properties.PARENT);
		}
		
	}//buildDependency

	private void createDependency(Node source, Node target, String dependencyType) {
		
		Relation relation = null;
		for (Relation rel : source.getRelations()) {
			if (rel.getTarget().equals(target)) {
				relation = rel;
				break;
			}
		}
		
		if (relation == null) {
			relation = new Relation(source, target);
			source.addRelation(relation);
			target.addRelation(relation);
			relation.setProperty(Properties.DEGREE, 0);
		}
		
		int grau = 1;
		if (relation.getProperty(dependencyType) != null)
			grau = (Integer) relation.getProperty(dependencyType) + 1;
		relation.setProperty(dependencyType, grau);
		
		grau = (Integer) relation.getProperty(Properties.DEGREE) + 1;
		relation.setProperty(Properties.DEGREE, grau);
		
		source.setProperty(Properties.COUPLING, (Integer) source.getProperty(Properties.COUPLING) + 1);
		source.setProperty(Properties.COUPLING_AFFERENT, (Integer) source.getProperty(Properties.COUPLING_AFFERENT) + 1);
		
		target.setProperty(Properties.COUPLING, (Integer) target.getProperty(Properties.COUPLING) + 1);
		target.setProperty(Properties.COUPLING_EFFERENT, (Integer) target.getProperty(Properties.COUPLING_EFFERENT) + 1);
		
	}//createDependency
	
	private void analyzeHierarchicalDependency(AbstractTypeDeclaration type){
		
		ITypeBinding binding = type.resolveBinding(); 
		if(binding.getSuperclass() != null && binding.getSuperclass().isFromSource()) {
			buildDependency(currentClass,binding.getSuperclass().getQualifiedName(),  RelationType.HIERARCHICAL);
		}
		ITypeBinding[] interfaces = binding.getInterfaces();
		String d = (binding.isInterface() ? RelationType.INTERFACE_INHERITANCE : RelationType.INTERFACE_IMPL );
		for(ITypeBinding inter : interfaces)
			if(inter.isFromSource())
				buildDependency(currentClass, inter.getQualifiedName(), d);
		
	}//analyzeHierarchicalDependency
	
	@Override
	public boolean visit(TypeDeclaration node) {
		
		try
		{
			if(node.resolveBinding().isFromSource()){
				currentClass = node.resolveBinding().getQualifiedName();	
				analyzeHierarchicalDependency(node);
				IVariableBinding[] fields = node.resolveBinding().getDeclaredFields();
				for(IVariableBinding field: fields)
					if(field.getType().isFromSource())
						buildDependency(currentClass, field.getType().getQualifiedName(), RelationType.OBJECT);
			}
			return true;
		}
		catch(Exception e)
		{
			return true;
		}
		
	}//visit(TypeDeclaration node)
	
	@Override
	public void endVisit(TypeDeclaration node) {		
		try
		{
			if(node.resolveBinding().isNested())
				currentClass = node.resolveBinding().getDeclaringClass().getQualifiedName();	
		}
		catch(Exception e){}
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
	
		try
		{
			buildDependencys(node.resolveBinding().getParameterTypes());
			currentMethod = currentClass + '.' + node.getName().getFullyQualifiedName();
			if(node.resolveBinding().getReturnType().isFromSource())
				buildDependency(currentClass, node.resolveBinding().getReturnType().getQualifiedName(), RelationType.OBJECT);

			buildDependencys(node.resolveBinding().getExceptionTypes());		
			return true;	
		}
		catch(Exception e)
		{
			return true;
		}
		
	}//visit(MethodDeclaration node)
	
	
	public void buildDependencys(ITypeBinding[] bindings){
		for(ITypeBinding parameter:bindings)
			if(parameter.isFromSource())
				buildDependency(currentClass, parameter.getQualifiedName(), RelationType.OBJECT);		
	}//buildDependencys
	
	@Override
	public boolean visit(ParameterizedType node) {
		try
		{
			buildDependencys(node.resolveBinding().getTypeArguments());
			return true;
		}
		catch(Exception e)
		{
			return true;
		}
	}//visit(ParameterizedType node)
	
	@Override
	public boolean visit(ArrayType node) {
		try
		{
			if(node.resolveBinding().getElementType().isFromSource())
				buildDependency(currentClass, node.resolveBinding().getElementType().getQualifiedName(), RelationType.OBJECT);		
			return true;
		}
		catch(Exception e)
		{
			return true;
		}
	}//visit(ArrayType node)	 

	@Override
	public boolean visit(MethodInvocation node) {
		
		try
		{
			IMethodBinding binding = node.resolveMethodBinding();
			if(binding.getDeclaringClass().isFromSource() && !binding.isConstructor())
				buildDependency(currentMethod, binding.getDeclaringClass().getQualifiedName() + '.' + node.getName().getFullyQualifiedName(), RelationType.METHOD);
			return true;
		}
		catch(Exception e)
		{
			return true;
		}
		
	}//visit(MethodInvocation node)

	@Override
	public boolean visit(VariableDeclarationStatement node){
		try
		{
			if(node.getType().resolveBinding().isFromSource())
				buildDependency(currentClass, node.getType().resolveBinding().getQualifiedName(), RelationType.OBJECT);
			return true;
		}
		catch(Exception e)
		{
			return true;
		}
		
	}//visit(VariableDeclarationStatement node)
	
	@Override
	public boolean visit(ClassInstanceCreation node) {
		
		try
		{
			if(node.resolveConstructorBinding().getDeclaringClass().isFromSource())
				buildDependency(currentClass, node.resolveConstructorBinding().getDeclaringClass().getQualifiedName(), RelationType.OBJECT);
			return true;
		}
		catch(Exception e)
		{
			return true;
		}
		
	}//visit(ClassInstanceCreation node)
	
	@Override
	public boolean visit(QualifiedName node) {		
		
		try
		{
			ITypeBinding binding = node.getQualifier().resolveTypeBinding();
			if(binding != null && binding.isFromSource())
				buildDependency(currentClass, binding.getQualifiedName(), RelationType.FIELD);
			return super.visit(node);
		}
		catch(Exception e)
		{
			return true;
		}
		
	}//visit(QualifiedName node)
	
	@Override
	public boolean visit(FieldAccess node) {
		
		try {
			if(node.resolveFieldBinding().getDeclaringClass().isFromSource())
				buildDependency( currentClass, node.resolveFieldBinding().getDeclaringClass().getQualifiedName(), RelationType.FIELD);
		}
		catch (NullPointerException e) {}
		
		return true;
	}//visit(FieldAccess node)
	
	@Override
	public boolean visit(EnumDeclaration node) {
		
		try
		{
			currentClass = node.resolveBinding().getQualifiedName();
			analyzeHierarchicalDependency(node);
			return true;
		}
		catch(Exception e)
		{
			return true;
		}
		
	}//visit(EnumDeclaration node)
	
}//class
