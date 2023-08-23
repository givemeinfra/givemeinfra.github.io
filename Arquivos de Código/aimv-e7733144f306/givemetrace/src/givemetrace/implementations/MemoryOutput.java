package givemetrace.implementations;

import java.util.ArrayList;

public class MemoryOutput 
{
    private static ArrayList<String> listCases;
    
    private static MemoryOutput instancia = null;
    
    private MemoryOutput()
    {            
        listCases = new ArrayList<>();
        
    }
    public static MemoryOutput getMemory()
    {
        if(instancia == null)
            instancia = new MemoryOutput();
        return instancia;        
    }
	public static ArrayList<String> getListCases() {
		return listCases;
	}
	public static void setListCases(ArrayList<String> listCases) {
		getMemory();
		MemoryOutput.listCases = listCases;
	}
	public static void setStringCase(String stringCase) {
		getMemory();
		MemoryOutput.listCases.add(stringCase);
	}
	public static void clear(){
		MemoryOutput.listCases.removeAll(listCases);
		MemoryOutput.listCases=null;
		MemoryOutput.listCases = new ArrayList<>();
	}
    
}
