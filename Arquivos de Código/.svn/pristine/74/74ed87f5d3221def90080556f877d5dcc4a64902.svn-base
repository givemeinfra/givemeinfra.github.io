package givemeviews.givemerepository;

import givemeviews.exportation.Report;
import givemeviews.persistence.MemoryApplication;
import givemeviews.views.provider.MasterProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class GiveMeRepository 
{
	public static ArrayList<File> getListPaths (String project, File directory) 
	{  
    	ArrayList<File> listPaths = new ArrayList<>();
    	ArrayList<File> listFinal = new ArrayList<>();
    	listPaths.add(directory);
    	Boolean key = true;
    	int index = 0;
    	while (key == true || listPaths.size() > 0)
    	{
    		key = false;
	    	for (File file : listPaths.get(index).listFiles()) 
	    	{
	    		String aux = file.getAbsolutePath();
	            if (file.isDirectory() && !(aux.contains("Brain"))) 
	            {
	            	listPaths.add(file);  
	            	String path = file.getAbsolutePath();	            	
	        		if(path.contains(project))
	        		{
	        			listFinal.add(new File(file.getAbsolutePath()));
	        		}
	            }
	        }
	    	listPaths.remove(index);
    	}
    	listPaths = null;
        return listFinal;
    }
	
	
	public static File getLastFileModified(ArrayList<File> listPaths) 
	{		
		File fl = null;
		ArrayList<File> listFilesPath = new ArrayList<>();
		File[] filesInThePath = null;
		File choise = null;
		
		for(int i = 0; i < listPaths.size(); i++)
		{
			fl = listPaths.get(i);
			filesInThePath = fl.listFiles();
			for(int j = 0; j < filesInThePath.length; j++)
				listFilesPath.add(filesInThePath[j]);
		}
		
		long lastMod = Long.MIN_VALUE;		
		for (File file : listFilesPath) {
			if (file.getAbsolutePath().contains(".xls") && file.lastModified() > lastMod) 
			{
				choise = file;
				lastMod = file.lastModified();
			}
		}
		return choise;
	}
	
	public Boolean verifyifComparisonViewXMLExist()
	{
		try
		{
			MasterProvider provider = new MasterProvider();
			String url = provider.getGMRepositoryURL();
			url = url + "\\Brain\\comparisonview.xml";
			File file = new File(url);
			if(file.exists())
			{
				return true;
			}
			else
				return false;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public ArrayList<String[]> getComparisonViewXMLlist(String pathToRead)
	{	
		ArrayList<String[]> listGeneral = new ArrayList<>();
		try
		{
			File f = new File(pathToRead);   
			SAXBuilder builder = new SAXBuilder();	     
			Document doc = builder.build(f);		             
			Element typeRead = (Element) doc.getRootElement(); // get Root Element		         
			List listDate = typeRead.getChildren();		             
			Iterator i = listDate.iterator();
			             
			while( i.hasNext() )
			{
				Element type = (Element) i.next();
		        if(type.getName().equals("period"))
		        {
		        	List<Element> listChildren = type.getChildren();
		        	String nodes[] = new String[3];
		        	for(int j = 0; j < listChildren.size(); j++)
		        	{
		        		//listGeneral.add(listChildren.get(j).getValue());
		        		nodes[j] = listChildren.get(j).getValue();
		        	}
		        	listGeneral.add(nodes);
		        }		        
		    }			
			return listGeneral;
		}
		catch(Exception e)
		{
			return null;
		}		
	}
	
	public Boolean createCompareViewXML(String[] item)
	{
		String pathToSave = MemoryApplication.getGmmRepositoryPath();
		pathToSave = pathToSave + "\\Brain\\compareview.xml";
		Report report = new Report();
		return  report.createCompareViewXML(pathToSave, item);
	}
	
	public Boolean updateCompareViewXML(String[] item)
	{
		String pathToSave = MemoryApplication.getGmmRepositoryPath();
		pathToSave = pathToSave + "\\Brain\\comparisonview.xml";
		Report report = new Report();
		return report.updateCompareViewXML(pathToSave, item);	
		
	}
	
}
