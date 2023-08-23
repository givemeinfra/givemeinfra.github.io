package sourceminer.filters;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Color;

import sourceminer.utilities.Properties;
import aimv.controllers.Nodes;
import aimv.filters.Filter;
import aimv.modeling.Node;
import aimv.utilities.Colors;

public class ConcernFilter extends Filter {

	@SuppressWarnings("unchecked")
	@Override
	protected Object[] applyFilter(Object[] args) {
		
		ArrayList<Color> cores = (ArrayList<Color>) args[1];
		ArrayList<String> concerns = (ArrayList<String>) args[0];
		
		for (String group : Nodes.getGroups()) {
			for (Node node : Nodes.getGroup(group).getNodes()) {
				
				String[] aux = (String[]) node.getProperty(Properties.CONCERNS); 
				for (int i = 0; i < aux.length; i++) {
					int index = concerns.indexOf(aux[i]);
					if (index != -1) {
						node.setProperty(Properties.COLOR_CONCERN, cores.get(index));
						//if (A)
					}
				}
				
			}
		}
		
		return null;
		
	}//applyFilter

	@Override
	protected Object[] removeFilter(Object[] args) {
		
		for (String group : Nodes.getGroups()) {
			for (Node node : Nodes.getGroup(group).getNodes())
				node.setProperty(Properties.COLOR_CONCERN, Colors.WHITE);
		}
		
		return null;
		
	}//removeFilter
	

}//class
