package givemeviews.filtering;

import givemeviews.model.Arquivo;
import givemeviews.model.MaintenanceType;
import givemeviews.persistence.MemoryApplication;

import java.util.ArrayList;

public class FilterCEORequests {
	
	public FilterCEORequests()
	{
		
	}
	
	public ArrayList filterChangeRequestsByType(ArrayList listChangesRequests)
    {
		ArrayList<MaintenanceType> tiposValidos = MemoryApplication.getListTypesMaintenance();
        int tamLista = listChangesRequests.size();
        ArrayList listaMontada = new ArrayList();
        for (int i = 0; i < tamLista; i++)
        {
            Arquivo objAux = (Arquivo)listChangesRequests.get(i);
            for (int j = 0; j < objAux.getListaTipos().size(); j++)
            {
                if (verifyTypes(tiposValidos, (String)objAux.getListaTipos().get(j)) == false)
                {
                    objAux.getListaTipos().remove(j);
                    objAux.getListaModulos().remove(j);
                    objAux.getListaModuloRDM().remove(j);
                    objAux.getListaComponentes().remove(j);
                    j--;                                             
                }
            }
            if (objAux.getListaTipos().size() > 0)
            {
                listaMontada.add(objAux);
            }                
        }
        return listaMontada;                
    }
	
	public ArrayList<Arquivo> filterChangeRequestsByModule(ArrayList<Arquivo> listChangesRequests, String selectedModule)
    {
		int tamLista = listChangesRequests.size();
        ArrayList<Arquivo> listaMontada = new ArrayList<Arquivo>();
        for (int i = 0; i < tamLista; i++)
        {
            Arquivo objAux = (Arquivo)listChangesRequests.get(i);
            for (int j = 0; j < objAux.getListaTipos().size(); j++)
            {
                if (verifyModule(selectedModule, (String)objAux.getListaModulos().get(j)) == false)
                {
                    objAux.getListaTipos().remove(j);
                    objAux.getListaModulos().remove(j);
                    objAux.getListaModuloRDM().remove(j);
                    objAux.getListaComponentes().remove(j);
                    j--;                                             
                }
            }
            if (objAux.getListaModulos().size() > 0)
            {
                listaMontada.add(objAux);
            }                
        }
        return listaMontada;                
    }
	
	public ArrayList<Arquivo> copy(ArrayList<Arquivo> listChangeRequestFull)
	{
		ArrayList<Arquivo> newChangeRequestFullList = new ArrayList<Arquivo>();  
	      
        for(Arquivo update : listChangeRequestFull)
        {  
            Arquivo objMontado = new Arquivo();     
                          
        	objMontado.setNumRDM(update.getNumRDM());
        	objMontado.setDesdobramento(update.getDesdobramento());
        	objMontado.setCodEmpresa(update.getCodEmpresa());
        	objMontado.setDataAbertura(update.getDataAbertura());
        	objMontado.setHoraAbertura(update.getHoraAbertura());
        	objMontado.setTipo(update.getTipo());
        	objMontado.setOrigem(update.getOrigem());
        	objMontado.setModuloRDM(update.getModuloRDM());
        	objMontado.setModulo(update.getModulo());
        	objMontado.setComponente(update.getComponente());
        	objMontado.setEquipe(update.getEquipe());
        	objMontado.setSituacaoRDM(update.getSituacaoRDM());
        	objMontado.setDataConclusao(update.getDataConclusao());
        	objMontado.setHoraConclusao(update.getHoraConclusao());
       
        	ArrayList<String> listComponentes = new ArrayList<>(update.getListaComponentes());
        	ArrayList<String> listModuloRDM = new ArrayList<>(update.getListaModuloRDM());
            ArrayList<String> listModulos = new ArrayList<>(update.getListaModulos()); 
            ArrayList<String> listTipos = new ArrayList<>(update.getListaTipos());
            
            objMontado.setListaComponentes(listComponentes);
            objMontado.setListaModuloRDM(listModuloRDM);
            objMontado.setListaModulos(listModulos);
            objMontado.setListaTipos(listTipos);
            
            newChangeRequestFullList.add(objMontado);
        }
        return newChangeRequestFullList;
	}
	
	private Boolean verifyTypes(ArrayList<MaintenanceType> listaTipos, String tipo)
    {
        for (int i = 0; i < listaTipos.size(); i++)
        {
        	MaintenanceType type = (MaintenanceType) listaTipos.get(i);
            if (type.getName().equals(tipo))
            {
                return true;
            }
        }
        return false;
    }	
	
	private Boolean verifyModule(String selectedModule, String atualModule)
    {
        if(selectedModule.equals(atualModule))
        	return true;
        else
        	return false;
    }
	
}
