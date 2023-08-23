package givemeviews.persistence;
import givemeviews.model.ObjectLog;
import givemeviews.model.MantisCase;
import givemeviews.model.SetModules;

import java.util.ArrayList;

public class MemoryMantis 
{
	private static ArrayList<MantisCase> listCases;
	private static ArrayList<ObjectLog> listGITorSVNlog;
	private static ArrayList<SetModules> listMantisComponents;    

	private static MemoryMantis instancia = null;
	
	private MemoryMantis()
	{			
		listCases = new ArrayList<>();
		listGITorSVNlog = new ArrayList<>();
		listMantisComponents = new ArrayList<>();
	}
	public static MemoryMantis getMemory()
	{
		if(instancia == null)
			instancia = new MemoryMantis();
		return instancia;		
	}
	
	public static ArrayList<MantisCase> getListCases() {
		return listCases;
	}		
	
	public static void setListCases(ArrayList<MantisCase> list)
	{
		getMemory();
		instancia.listCases.removeAll(instancia.listCases);
		for(int i = 0; i < list.size(); i++)
			instancia.listCases.add(list.get(i));
	}
	
	public static ArrayList<ObjectLog> getListGITorSVNlog() {
		return listGITorSVNlog;
	}		
	
	public static void setLisGITorSVNlog(ArrayList<ObjectLog> list)
	{
		getMemory();
		instancia.listGITorSVNlog.removeAll(instancia.listGITorSVNlog);
		for(int i = 0; i < list.size(); i++)
			instancia.listGITorSVNlog.add(list.get(i));
	}
	
	public static ArrayList<SetModules> getListMantisComponents() {
		return listMantisComponents;
	}
	
	public static void setListMantisComponents(ArrayList<SetModules> listMantisComponents) 
	{
		getMemory();
		instancia.listMantisComponents.removeAll(instancia.listMantisComponents);
		for(int i = 0; i < listMantisComponents.size(); i++)
			instancia.listMantisComponents.add(listMantisComponents.get(i));
	}
}
