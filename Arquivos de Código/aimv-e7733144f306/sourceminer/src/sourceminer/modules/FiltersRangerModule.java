package sourceminer.modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;

import sourceminer.utilities.Groups;
import sourceminer.utilities.Properties;

import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.modeling.Node;
import aimv.modules.Module;

public class FiltersRangerModule extends Module {


	@Override
	protected void start(Object fonte, IProgressMonitor monitor) {
		
		if (!(fonte instanceof IJavaProject)) 
			return;
		
		monitor.subTask("Analyzing range properties");
		LinkedHashMap<String, Object> list_filtros = new LinkedHashMap<String, Object>();
		AIMV.setProperty("filtros_range", list_filtros);
		
		String[] filtros = {"Number of Methods (Class and Interface)","Lines of code (Class and Inerface)","Complexity (Method)",
				"Lines of code (Method)","Afferent Coupling","Efferent Coupling"};
		
		String[] props = {Properties.NUMBER_METHODS, Properties.SIZE, Properties.COMPLEXITY,
				Properties.SIZE, Properties.COUPLING_AFFERENT, Properties.COUPLING_EFFERENT};
		
		String[] groups = {Groups.CLASS, Groups.CLASS, Groups.METHOD,
				Groups.METHOD, Groups.ALL, Groups.ALL};
		
		for (int i = 0; i < filtros.length; i+=2) {
			
			ArrayList<Integer> list1 = new ArrayList<Integer>();
			ArrayList<Integer> list2 = new ArrayList<Integer>();
			
			for (Node node : Nodes.getGroup(groups[i]).getNodes()) {
				int valor1 = (Integer) node.getProperty(props[i]);
				if (!list1.contains(valor1))
					list1.add(valor1);
				int valor2 = (Integer) node.getProperty(props[i+1]);
				if (!list2.contains(valor2))
					list2.add(valor2);
			}
			
			Collections.sort(list1);
			Collections.sort(list2);
			
			list_filtros.put(filtros[i], list1.toArray(new Integer[0]));
			list_filtros.put(filtros[i+1], list2.toArray(new Integer[0]));
		}
		

	}//start

}//class
