package implementacoes.tree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GeraTreeXML 
{
	public GeraTreeXML()
	{
		
	}
	
	public void createTreeViewXML(String pathToSave, String moduleANDcomponent, ArrayList<String> list)
	{  
	    PrintWriter writer = null;
		try 
		{
			 
			 writer = new PrintWriter(new BufferedWriter(new FileWriter(pathToSave)));
			
			 writer.println("<?xml version=" + '"' + "1.0" + '"' + " encoding=" + '"' + "ISO8859-1" + '"' + "?>");
			 writer.println("<tree>");
             	writer.println("<declarations>");
             		writer.println("<attributeDecl name=" + '"' + "name" + '"' + " type=" + '"' + "String" + '"' + "/>");
             	writer.println("</declarations>");
			    writer.println("<branch>");
			    	writer.println("<attribute name=" + '"' + "name" + '"' +  " value=" + '"' + moduleANDcomponent + '"' + "/>");
			    	for(int i = 0; i < list.size(); i++)
			    	{
			    		writer.println("<branch>");
			    			writer.println("<attribute name=" + '"' + "name" + '"' +  " value=" + '"' + list.get(i) + '"' + "/>");
			    		writer.println("</branch>");
			    	}
			    writer.println("</branch>");
			 writer.println("</tree>");

             writer.close();
        }
        catch (Exception ex)
        {
        	System.out.println("_________________________");
       	 	ex.printStackTrace();       	 	
        } 
	   	
	}
}
