package givemeviews.persistence;

import givemeviews.model.Arquivo;

import java.util.ArrayList;


public class MemoryDriverCEO 
{
    
    private static ArrayList<Arquivo> listChangeRequests;
    private static MemoryDriverCEO instancia = null;
	
	private MemoryDriverCEO()
	{			
		listChangeRequests = new ArrayList<>();
	}
	public static MemoryDriverCEO getMemory()
	{
		if(instancia == null)
			instancia = new MemoryDriverCEO();
		return instancia;		
	}
	
	public static ArrayList<Arquivo> getListChangeRequests() {
		return listChangeRequests;
	}		
	
	public static void setListChangeRequests(ArrayList<Arquivo> list)
	{
		getMemory();
		instancia.listChangeRequests.removeAll(instancia.listChangeRequests);
		for(int i = 0; i < list.size(); i++)
			instancia.listChangeRequests.add(list.get(i));
	}	
	
}
