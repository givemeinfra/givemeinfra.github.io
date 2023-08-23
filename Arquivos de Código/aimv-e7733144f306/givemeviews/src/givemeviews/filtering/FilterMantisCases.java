package givemeviews.filtering;

import givemeviews.model.Arquivo;
import givemeviews.model.MaintenanceType;
import givemeviews.persistence.MemoryApplication;

import java.util.ArrayList;

public class FilterMantisCases 
{
	/*public ArrayList filterChangeRequestsByType(ArrayList listChangesRequests)
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
    }*/
}
