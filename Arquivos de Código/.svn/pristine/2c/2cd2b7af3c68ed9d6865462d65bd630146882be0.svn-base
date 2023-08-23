package givemeviews.model;

import java.util.ArrayList;

public class RDMOrigem extends Arquivo
{
	public RDMOrigem()
    {

    }

    public ArrayList<RDMOrigem> montarListaRDMs(ArrayList<Arquivo> listaLinhas)
    {
        Arquivo objRDMOrigem = new Arquivo();
        Arquivo objRDMAtual = new Arquivo();
        ArrayList<String> listaComponentesAux;
        ArrayList<String> listaModulosAux;
        ArrayList<String> listaModulosRDMAux;
        ArrayList<String> listaTiposAux;
        Boolean encontrou = false;
        ArrayList listaRDMS = new ArrayList();

        for (int i = 0; i < listaLinhas.size(); i++)
        {
            objRDMOrigem = (Arquivo)listaLinhas.get(i); // assume-se que toda linha é uma RDM de Origem                
            if (objRDMOrigem.getDesdobramento() == 0)// realmente é um registro de RDM de Origem
            {
                encontrou = false;
                if (listaRDMS.size() == 0)
                {
                    listaComponentesAux = new ArrayList<String>();
                    listaModulosAux = new ArrayList<String>();
                    listaModulosRDMAux = new ArrayList<String>();
                    listaTiposAux = new ArrayList<String>();
                    listaComponentesAux.add(objRDMOrigem.getComponente());
                    listaModulosAux.add(objRDMOrigem.getModulo());
                    listaModulosRDMAux.add(objRDMOrigem.getModuloRDM());
                    listaTiposAux.add(objRDMOrigem.getTipo());
                    objRDMOrigem.setListaComponentes(listaComponentesAux);
                    objRDMOrigem.setListaModulos(listaModulosAux);
                    objRDMOrigem.setListaModuloRDM(listaModulosRDMAux);
                    objRDMOrigem.setListaTipos(listaTiposAux);
                    listaRDMS.add(objRDMOrigem);
                }
                else
                {
                    for (int j = 0; j < listaRDMS.size(); j++)
                    {
                        objRDMAtual = (Arquivo)listaRDMS.get(j);
                        if (objRDMOrigem.getNumRDM() == objRDMAtual.getNumRDM() && objRDMOrigem.getDesdobramento() == objRDMAtual.getDesdobramento())
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
                            listaComponentesAux.add(objRDMOrigem.getComponente());
                            listaModulosAux.add(objRDMOrigem.getModulo());
                            listaModulosRDMAux.add(objRDMOrigem.getModuloRDM());
                            listaTiposAux.add(objRDMOrigem.getTipo());
                            objRDMAtual.setListaComponentes(listaComponentesAux); 
                            objRDMAtual.setListaModulos(listaModulosAux);
                            objRDMAtual.setListaModuloRDM(listaModulosRDMAux);
                            objRDMAtual.setListaTipos(listaTiposAux);
                            listaRDMS.remove(j);
                            listaRDMS.add(j, objRDMAtual);
                        }
                    }
                    if (encontrou == false)
                    {
                        listaComponentesAux = new ArrayList<String>();
                        listaModulosAux = new ArrayList<String>();
                        listaModulosRDMAux = new ArrayList<String>();
                        listaTiposAux = new ArrayList<String>();
                        listaComponentesAux.add(objRDMOrigem.getComponente());
                        listaModulosAux.add(objRDMOrigem.getModulo());
                        listaModulosRDMAux.add(objRDMOrigem.getModuloRDM());
                        listaTiposAux.add(objRDMOrigem.getTipo());
                        objRDMOrigem.setListaComponentes(listaComponentesAux);
                        objRDMOrigem.setListaModulos(listaModulosAux);
                        objRDMOrigem.setListaModuloRDM(listaModulosRDMAux);
                        objRDMOrigem.setListaTipos(listaTiposAux);
                        listaRDMS.add(objRDMOrigem);
                    }
                }
            }
        }
        return listaRDMS;
    }
    
    /*public RDMOrigem montarObjeto(ArrayList<String> lista)
    {    	
    	RDMOrigem objMontado = new RDMOrigem();
    	objMontado.setNumRDM(Integer.parseInt(lista.get(0)));
    	objMontado.setDesdobramento(Integer.parseInt(lista.get(1)));
    	objMontado.setCodEmpresa(lista.get(2));
    	objMontado.setDataAbertura(lista.get(3));
    	objMontado.setHoraAbertura(lista.get(4));
    	objMontado.setTipo(lista.get(5));
    	objMontado.setOrigem(lista.get(6));
    	objMontado.setModuloRDM(lista.get(7));
    	objMontado.setModulo(lista.get(8));
    	objMontado.setComponente(lista.get(9));
    	objMontado.setEquipe(lista.get(10));
    	objMontado.setSituacaoRDM(lista.get(11));
    	objMontado.setDataConclusao(lista.get(12));
    	objMontado.setHoraConclusao(lista.get(13));    	
    	
    	return objMontado;
    }*/
  
}
