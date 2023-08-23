package sourceminer.modules;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import aimv.utilities.Colors;
import sourceminer.utilities.Groups;
import sourceminer.utilities.Properties;
import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.modeling.Node;
import aimv.modules.Module;

public class ColorationModule extends Module {

	private int colorCount;
	private Color lastColor;
	
	private void addColor(Node node, Color c1, Color c2, Color c3, Color c4, Color c5) {
		
		node.setProperty(Properties.COLOR_PACKAGE, c1);
		node.setProperty(Properties.COLOR_HIERARCHY, c2);
		node.setProperty(Properties.COLOR_SIZE, c3);
		node.setProperty(Properties.COLOR_COMPLEXITY, c4);
		node.setProperty(Properties.COLOR_ENTITY_TYPE, c5);
		node.setProperty(Properties.COLOR_CONCERN, Colors.WHITE);
		
	}//addColor
	
	@Override
	protected void start(Object fonte, IProgressMonitor monitor) {
		
		//verifica se é um IJavaProject
		if (!(fonte instanceof IJavaProject)) 
			return;
		
		ArrayList<String> list = new ArrayList<String>();
		list.add(Properties.COLOR_PACKAGE);
		list.add(Properties.COLOR_HIERARCHY);
		list.add(Properties.COLOR_SIZE);
		list.add(Properties.COLOR_COMPLEXITY);
		list.add(Properties.COLOR_ENTITY_TYPE);
		list.add(Properties.COLOR_CONCERN);
		AIMV.setProperty(Properties.COLORS, list.toArray(new String[0]));
		
		int maxSize = 0;
		for (Node node : Nodes.getGroup(Groups.PACKAGE).getNodes()) {
			int size = (Integer) node.getProperty(Properties.SIZE);
			maxSize = size > maxSize ? size : maxSize;
		}
		colorCount = 0;
		for (Node node : Nodes.getGroup(Groups.PACKAGE).getNodes()) {
			monitor.subTask("Applying coloring in " + node.getID());
			Color color = Colors.getColorFromHex("#84EF50");
			Color c3 = tonalidade(Colors.YELLOW, (Integer) node.getProperty(Properties.SIZE), maxSize);
			addColor(node, getColor(), color, c3, Colors.YELLOW, Colors.WHITE);
		}	
		colorCount = 0;
		for (Node node : Nodes.getGroup(Groups.PACKAGE).getNodes()) {
			monitor.subTask("Applying coloring in " + node.getID());
			addColor(node, getColor(), Colors.PACKAGE, Colors.PACKAGE, Colors.PACKAGE, Colors.PACKAGE);
		}
		
		maxSize = 0;
		for (Node node : Nodes.getGroup(Groups.CLASS).getNodes()) {
			int size = (Integer) node.getProperty(Properties.SIZE);
			maxSize = size > maxSize ? size : maxSize;
		}
		
		for (Node node : Nodes.getGroup(Groups.CLASS).getNodes()) {
			monitor.subTask("Applying coloring in " + node.getID());
			Color color = (Color) ((Node) node.getProperty(Properties.PARENT)).getProperty(Properties.COLOR_PACKAGE);

			Color color1 = Colors.getColorFromHex("#AF621B");
			Color color2 = tonalidade(Colors.getColorFromHex("#7F0000"), (Integer) node.getProperty(Properties.SIZE), maxSize);
			Color color3 = setColorSubclasses(node);
			addColor(node, color, color1, color2, color2, color3);

			Color colorSubclasse = setColorSubclasses(node);
			addColor(node, color, Colors.CLASS, Colors.CLASS, Colors.CLASS, colorSubclasse);

		}
				
		for (Node node : Nodes.getGroup(Groups.PROJECT).getNodes()) {
			monitor.subTask("Applying coloring in " + node.getID());
			addColor(node, Colors.PROJECT, Colors.PROJECT, Colors.PROJECT, Colors.PROJECT, Colors.PROJECT);
		}

		maxSize = 0;
		int maxComplex = 0;
		for (Node node : Nodes.getGroup(Groups.METHOD).getNodes()) {
			int size = (Integer) node.getProperty(Properties.SIZE);
			maxSize = size > maxSize ? size : maxSize;
			int complex = (Integer) node.getProperty(Properties.COMPLEXITY);
			maxComplex = complex > maxComplex ? complex : maxComplex;
		}
				
		for (Node node : Nodes.getGroup(Groups.METHOD).getNodes()) {
			monitor.subTask("Applying coloring in " + node.getID());
			Color c1 = (Color) ((Node) node.getProperty(Properties.PARENT)).getProperty(Properties.COLOR_PACKAGE);
			Color c2 = tonalidade(Colors.SIZE, (Integer) node.getProperty(Properties.SIZE), maxSize);
			Color c3 = tonalidade(Colors.COMPLEXITY, (Integer) node.getProperty(Properties.COMPLEXITY), maxComplex);
			Color c4 = (Color) ((Node) node.getProperty(Properties.PARENT)).getProperty(Properties.COLOR_ENTITY_TYPE);
			addColor(node, c1, Colors.METHOD, c2, c3, c4);
		}
		
	}//start
	
	private Color getColor(){
		
		Color color = null;
		
		if(colorCount == 0){
			color = new Color(Display.getCurrent(), 255, 0, 0);
		}else if(colorCount == 1){
			color = new Color(Display.getCurrent(), 0, 255, 0);
		}else if(colorCount == 2){
			color = new Color(Display.getCurrent(), 0, 0, 255);
		}

		if(color != null){
			colorCount++;
			lastColor = color;
			return color;
		}

		int maxValue = 255;
		int R = lastColor.getRed();
		int G = lastColor.getGreen();
		int B = lastColor.getBlue();

		//os Delta's devem ser numeros primos para evitar (ao maximo) repeticao de cores 
		int deltaR = 119; 
		int deltaG = 149;
		int deltaB = 61;

		if(colorCount % 3 == 0){
			R = (R + deltaR) % maxValue;
		}else if(colorCount % 3 == 1){
			G = (G + deltaG) % maxValue;
		}else{
			B = (B + deltaB) % maxValue;
		}
		colorCount ++;

		color = new Color(Display.getCurrent(), R, G, B);
		lastColor = color;

		return color;
		
	}//getColor
	
	private Color tonalidade(Color cor, int valor, int maiorValor){
		
		RGB rgb = new RGB(cor.getRGB().red, cor.getRGB().green, cor.getRGB().blue);

		int proporcao = ((valor * 150) / maiorValor);

		rgb.red -= proporcao;

		if(rgb.red < 0)
			rgb.red = 0;

		rgb.green -= proporcao;

		if(rgb.green < 0)
			rgb.green = 0;

		rgb.blue -= proporcao;

		if(rgb.blue < 0)
			rgb.blue = 0;

		return new Color(null, rgb);
		
	}//definirTonalidade
	
	private Color setColorSubclasses(Node node){
		
	
		IType type = (IType) node.getProperty(Properties.JAVA_ELEMENT);
		Color cor = Colors.UNKNOWN;
		String text = "Class";
		try {
			if(type.isInterface()){ 
				text = "Interface"; //INTERFACE
				cor = Colors.INTERFACE; 
			} else if(type.isClass()){ 
				if(Flags.isAbstract(type.getFlags())) {
					text = "Class Abstract"; //CLASS ABSTRACT
					cor = Colors.ABSTRACT;
				}
				else {
					text = "Class Concrete"; //CLASS CONCRETE
					cor = Colors.CONCRETE;
				}
			} else if(type.isEnum()){
				text = "Enum"; //ENUM
				cor = Colors.ENUM; 
			}
		} catch(JavaModelException e){
			e.printStackTrace();
		}
		node.setProperty(Properties.TYPE, text);
		return cor;

	}//setColorSubclasses

	
}//class
