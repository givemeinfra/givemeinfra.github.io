package implementacoes.radial;

import java.util.ArrayList;

public class MemoryCompare 
{
	private static ArrayList<String> listIndications;
	
	private static MemoryCompare instancia = null;

	public static ArrayList<String> getListIndications() {
		return listIndications;
	}

	public static void setListIndications(ArrayList<String> listIndications) {
		getMemory();
		MemoryCompare.listIndications = listIndications;
	}
	
	public static void addIndications(String indication) {
		getMemory();
		MemoryCompare.listIndications.add(indication);
	}
	
	private MemoryCompare()
	{
		listIndications = new ArrayList<>();
	}
	
	public static MemoryCompare getMemory()
	{
		if(instancia == null)
			instancia = new MemoryCompare();
		return instancia;		
	}
	
	public void clearListIndications()
	{
		listIndications.removeAll(getListIndications());
		listIndications = null;
		listIndications = new ArrayList<>();
	}
}
