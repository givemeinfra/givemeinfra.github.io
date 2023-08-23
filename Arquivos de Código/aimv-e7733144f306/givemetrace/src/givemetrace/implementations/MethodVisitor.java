package givemetrace.implementations;

import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

/**
 * Simple visitor implementation for visiting MethodDeclaration nodes. 
 */
public class MethodVisitor extends VoidVisitorAdapter<Object> {
	private List<String> methodsVisitor;
	private List<Range> rangesVisitor;
   
	public MethodVisitor(List<String> methodsVisitor, List<Range> rangesVisitor) {
		super();
		this.methodsVisitor = methodsVisitor;
		this.rangesVisitor = rangesVisitor;
	}

	public List<String> getMethodsVisitor() {
		return methodsVisitor;
	}

	public List<Range> getRangesVisitor() {
		return rangesVisitor;
	}

	@Override
	public void visit(MethodDeclaration n, Object arg) {
        // here you can access the attributes of the method.
        // this method will be called for all methods in this 
        // CompilationUnit, including inner class methods
        //System.out.println("nome do metodo: "+n.getName()+"\nlinha de inicio: "+n.getBeginLine()+"\nlinha final: "+n.getEndLine());
        //verifica se o método estava na classe desde o commit passado 
     //   if(  methods_prev1.indexOf(n.getName())!=-1  ){
        methodsVisitor.add(n.getName());
        rangesVisitor.add(new Range(n.getBeginLine(), n.getEndLine()));
       // }
    
    }
}
