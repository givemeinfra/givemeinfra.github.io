package sourceminer.filters;

import sourceminer.utilities.Groups;
import aimv.controllers.Nodes;
import aimv.filters.Filter;
import aimv.modeling.Node;
import aimv.modeling.Relation;

public class DependencyFilter extends Filter {

	@Override
	protected Object[] applyFilter(Object[] args) {
		
		String group = Groups.ALL;
		
		if (Nodes.getGroup(group) == null)
			return null;
		
		for (Node node :  Nodes.getGroup(group).getNodes()) {
			for (Relation relation : node.getRelations()) {
				boolean flag = false;
				for (Object ob : args) {
					if (relation.getProperty((String) ob) != null)
						flag = true;
				}
				if (flag)
					relation.removeFilter(this);
				else
					relation.addFilter(this);
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
			for (Relation relation : node.getRelations())
				relation.removeFilter(this);
		return null;
	}//removeFilter
	

}//class

