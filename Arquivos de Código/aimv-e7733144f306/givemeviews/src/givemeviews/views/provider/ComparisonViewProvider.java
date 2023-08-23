package givemeviews.views.provider;

import java.util.ArrayList;
import givemeviews.givemerepository.GiveMeRepository;
import implementacoes.radial.MemoryCompare;

public class ComparisonViewProvider 
{
	public Boolean verifyComparisonViewXML()
	{
		GiveMeRepository repo = new GiveMeRepository();
		return repo.verifyifComparisonViewXMLExist();
	}
	
	public ArrayList<String[]>  getXMLContent()
	{
		MasterProvider provider = new MasterProvider();
		String url = provider.getGMRepositoryURL();
		url = url + "\\Brain\\comparisonview.xml";
		GiveMeRepository objDate = new GiveMeRepository();
		
		// lê xml
		return objDate.getComparisonViewXMLlist(url);
	}
	
}
