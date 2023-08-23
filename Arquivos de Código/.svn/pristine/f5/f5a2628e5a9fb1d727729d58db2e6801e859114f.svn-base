package sourceminer.filters;

import sourceminer.utilities.Groups;
import sourceminer.utilities.Properties;
import aimv.controllers.Nodes;
import aimv.filters.Filter;
import aimv.modeling.Node;

public class ECFilter extends Filter {

	@Override
	protected Object[] applyFilter(Object[] args) {
		
		String group = Groups.ALL;
		String property = Properties.COUPLING_EFFERENT;
		Integer min = Integer.parseInt((String) args[0]);
		Integer max = Integer.parseInt((String) args[1]);
		
		if (Nodes.getGroup(group) == null)
			return null;
		
		removeFilter(args);
		for (Node node :  Nodes.getGroup(group).getNodes()) {
			if (node.getProperty(property) != null) {
				Integer valor = (Integer) node.getProperty(property);
				if (valor < min || valor > max)
					node.addFilter(this);
				
			}	
		}
		return null;
	}//applyFilter

	@Override
	protected Object[] removeFilter(Object[] args) {
		
		String group = Groups.ALL;
		if (Nodes.getGroup(group) == null)
			return null;
		
		for (Node node : Nodes.getGroup(group).getNodes())
			node.removeFilter(this);
		return null;
	}//removeFilter

}//class

