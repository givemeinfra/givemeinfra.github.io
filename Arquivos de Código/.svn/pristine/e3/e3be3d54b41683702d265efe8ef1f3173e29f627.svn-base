package givemeviews.model;

import givemeviews.persistence.MemoryApplication;

import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class MaintenanceType {
	
	private String name;
    private int type; // equals(1), corretive. equals(2), adaptative. equals(3), evolutionary.
	
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public MaintenanceType(String name, int type)
	{
		this.name = name;
		this.type = type;
	}
	
	public MaintenanceType()
	{
		
	}
	
	public ArrayList<String> getMaintenanceTypesByID(int id)
	{
		ArrayList<String> listResult = new ArrayList<>();
		ArrayList<MaintenanceType> list = MemoryApplication.getListTypesMaintenance();
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).getType() == id)
				listResult.add(list.get(i).getName());
		}
		return listResult;
	}
	
	public Boolean tryFoundMaintenanceTypes(String pathToRead)
	{	
		ArrayList<String> listGeneral = new ArrayList<>();
		ArrayList<String> listAdaptative = new ArrayList<>();
		ArrayList<String> listCorretive = new ArrayList<>();
		ArrayList<String> listEvolutionary = new ArrayList<>();
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
		        if(type.getName().equals("General"))
		        {
		        	List<Element> listChildren = type.getChildren();
		        	for(int j = 0; j < listChildren.size(); j++)
		        	{
		        		listGeneral.add(listChildren.get(j).getValue());
		        	}       	
		        }
		        if(type.getName().equals("Corrective"))
		        {
		        	List<Element> listChildren = type.getChildren();
		        	for(int j = 0; j < listChildren.size(); j++)
		        	{
		        		listCorretive.add(listChildren.get(j).getValue());
		        	}      	
		        }
		        if(type.getName().equals("Adaptative"))
		        {
		        	List<Element> listChildren = type.getChildren();
		        	for(int j = 0; j < listChildren.size(); j++)
		        	{
		        		listAdaptative.add(listChildren.get(j).getValue());
		        	}     	
		        }
		        if(type.getName().equals("Evolutive"))
		        {
		        	List<Element> listChildren = type.getChildren();
		        	for(int j = 0; j < listChildren.size(); j++)
		        	{
		        		listEvolutionary.add(listChildren.get(j).getValue());
		        	}      	
		        }
		    }
			ArrayList<MaintenanceType> listGetTypesArrayList = new ArrayList<>();
			
			for(int a = 0; a < listGeneral.size(); a++)
			{
				listGetTypesArrayList.add(new MaintenanceType(listGeneral.get(a), 0));
			}
			for(int a = 0; a < listCorretive.size(); a++)
			{
				listGetTypesArrayList.add(new MaintenanceType(listCorretive.get(a), 1));
			}
			for(int a = 0; a < listAdaptative.size(); a++)
			{
				listGetTypesArrayList.add(new MaintenanceType(listAdaptative.get(a), 2));
			}
			for(int a = 0; a < listEvolutionary.size(); a++)
			{
				listGetTypesArrayList.add(new MaintenanceType(listEvolutionary.get(a), 3));
			}
			MemoryApplication.setMaintenanceTypes(listGetTypesArrayList);
			
			listGeneral = null;
			listAdaptative = null;
			listCorretive = null;
			listEvolutionary = null;
			
			return true;
		}
		catch(Exception e)
		{
			return false;
		}		
	}

}
