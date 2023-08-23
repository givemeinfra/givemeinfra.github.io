package givemetrace.implementations;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class GiveMeTraceXML {

	public static InfoRepository tryFoundInfoRepository(String pathToRead, String projectName) throws JDOMException, IOException{
		File f = new File(pathToRead);   
		SAXBuilder saxbuilder = new SAXBuilder();
		Document doc = (Document)saxbuilder.build(f);		             
		Element typeRead = (Element) doc.getRootElement(); // get Root Element		         
		List<?> listDate = typeRead.getChildren();// get all children of root element 		             
		if(listDate.isEmpty()){return null;}
		Iterator<?> i = listDate.iterator();//iterator
		             
		while( i.hasNext() )
		{
			Element type = (Element) i.next();
	        if(type.getName().equals(projectName))
	        {
	        	
	        	String id = type.getAttributeValue("id");
	        	InfoRepository ir = new InfoRepository();
	        	ir.setProjectName(type.getName());
	        	ir.setRepositoryPath(type.getChildText("URL"+id));
	        	ir.setRepositoryType(type.getChildText("repositoryType"+id));
	        	ir.setLogin(type.getChildText("login"));
	        	ir.setPassword(type.getChildText("password"));

	        	return ir;
	        }
	    }		
		return null;
				
	}

}
