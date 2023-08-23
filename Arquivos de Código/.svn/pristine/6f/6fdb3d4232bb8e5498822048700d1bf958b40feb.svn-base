package givemeviews.model;

import java.util.ArrayList;

public class RDMDesdobramento extends Arquivo
{
	 
	public RDMDesdobramento()
	{
		
	}
	
	public ArrayList<RDMDesdobramento> montarListaRDMsDesdobramento(ArrayList<Arquivo> listaLinhas)
    {
        Arquivo objRDMDesdobramento = new Arquivo();
        Arquivo objRDMAtual = new Arquivo();
        ArrayList<String> listaComponentesAux;
        ArrayList<String> listaModulosAux;
        ArrayList<String> listaModulosRDMAux;
        ArrayList<String> listaTiposAux;
        Boolean encontrou = false;
        ArrayList listaRDMSDesdobramento = new ArrayList();

        for (int i = 0; i < listaLinhas.size(); i++)
        {
        	objRDMDesdobramento = (Arquivo)listaLinhas.get(i); // assume-se que toda linha é uma RDM de Origem                
            if (!(objRDMDesdobramento.getDesdobramento() == 0))// realmente é um registro de RDM de Origem
            {
                encontrou = false;
                if (listaRDMSDesdobramento.size() == 0)
                {
                    listaComponentesAux = new ArrayList<String>();
                    listaModulosAux = new ArrayList<String>();
                    listaModulosRDMAux = new ArrayList<String>();
                    listaTiposAux = new ArrayList<String>();
                    listaComponentesAux.add(objRDMDesdobramento.getComponente());
                    listaModulosAux.add(objRDMDesdobramento.getModulo());
                    listaModulosRDMAux.add(objRDMDesdobramento.getModuloRDM());
                    listaTiposAux.add(objRDMDesdobramento.getTipo());
                    objRDMDesdobramento.setListaComponentes(listaComponentesAux);
                    objRDMDesdobramento.setListaModulos(listaModulosAux);
                    objRDMDesdobramento.setListaModuloRDM(listaModulosRDMAux);
                    objRDMDesdobramento.setListaTipos(listaTiposAux);
                    listaRDMSDesdobramento.add(objRDMDesdobramento);
                }
                else
                {
                    for (int j = 0; j < listaRDMSDesdobramento.size(); j++)
                    {
                        objRDMAtual = (Arquivo)listaRDMSDesdobramento.get(j);
                        if (objRDMDesdobramento.getNumRDM() == objRDMAtual.getNumRDM() && objRDMDesdobramento.getDesdobramento() == objRDMAtual.getDesdobramento())
                        {
                            encontrou = true;
                            listaComponentesAux = new ArrayList<String>();
                            listaModulosAux = new ArrayList<String>();
                            listaModulosRDMAux = new ArrayList<String>();
                            listaTiposAux = new ArrayList<String>();
                            listaComponentesAux = objRDMAtual.getListaComponentes();
                            listaModulosAux = objRDMAtual.getListaModulos();
                            listaModulosRDMAux = objRDMAtual.getListaModuloRDM();
                            listaTiposAux = objRDMAtual.getListaTipos();
                            listaComponentesAux.add(objRDMDesdobramento.getComponente());
                            listaModulosAux.add(objRDMDesdobramento.getModulo());
                            listaModulosRDMAux.add(objRDMDesdobramento.getModuloRDM());
                            listaTiposAux.add(objRDMDesdobramento.getTipo());
                            objRDMAtual.setListaComponentes(listaComponentesAux); 
                            objRDMAtual.setListaModulos(listaModulosAux);
                            objRDMAtual.setListaModuloRDM(listaModulosRDMAux);
                            objRDMAtual.setListaTipos(listaTiposAux);
                            listaRDMSDesdobramento.remove(j);
                            listaRDMSDesdobramento.add(j, objRDMAtual);
                        }
                    }
                    if (encontrou == false)
                    {
                        listaComponentesAux = new ArrayList<String>();
                        listaModulosAux = new ArrayList<String>();
                        listaModulosRDMAux = new ArrayList<String>();
                        listaTiposAux = new ArrayList<String>();
                        listaComponentesAux.add(objRDMDesdobramento.getComponente());
                        listaModulosAux.add(objRDMDesdobramento.getModulo());
                        listaModulosRDMAux.add(objRDMDesdobramento.getModuloRDM());
                        listaTiposAux.add(objRDMDesdobramento.getTipo());
                        objRDMDesdobramento.setListaComponentes(listaComponentesAux);
                        objRDMDesdobramento.setListaModulos(listaModulosAux);
                        objRDMDesdobramento.setListaModuloRDM(listaModulosRDMAux);
                        objRDMDesdobramento.setListaTipos(listaTiposAux);
                        listaRDMSDesdobramento.add(objRDMDesdobramento);
                    }
                }
            }
        }
        return listaRDMSDesdobramento;
    }

}
