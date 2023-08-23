package sourceminer.filters;

import aimv.controllers.Nodes;
import aimv.filters.Filter;
import aimv.modeling.Node;

public class FilterID extends Filter {

	@Override
	protected Object[] applyFilter(Object[] args) {
		
		try {
			
			String text = (String) args[0];
			String group = (String) args[1];
			
			removeFilter(args);
			for (Node node : Nodes.getGroup(group).getNodes()) {
				if (!node.getID().contains(text)) 
					node.addFilter(this);
			}
			
		} catch (Exception e) {}
		return null;
	}//applyFilter

	@Override
	protected Object[] removeFilter(Object[] args) {
		
		try {
			
			String group = (String) args[1];
			for (Node node : Nodes.getGroup(group).getNodes())
				node.removeFilter(this);
			
		} catch (Exception e) {}
		return null;
	}//removeFilter
	

}//class
