package givemeviews.model;

import java.util.ArrayList;

public class Arquivo 
{    
    private int NumRDM;
    private int Desdobramento;
    private String CodEmpresa;
    private String DataAbertura;
    private String HoraAbertura;
    private String Tipo;
    private String Origem;
    private String ModuloRDM;
    private String Modulo;
    private String Componente;
    private String Equipe;
    private String SituacaoRDM;
    private String DataConclusao;
    private String HoraConclusao;
    private ArrayList<String> ListaTipos;
    private ArrayList<String> ListaComponentes;
    private ArrayList<String> ListaModulos;
    private ArrayList<String> ListaModuloRDM;
	
	public Arquivo()
	{
		
	}
    
    public int getNumRDM() {
		return NumRDM;
	}
	public void setNumRDM(int numRDM) {
		NumRDM = numRDM;
	}
	public int getDesdobramento() {
		return Desdobramento;
	}
	public void setDesdobramento(int desdobramento) {
		Desdobramento = desdobramento;
	}
	public String getCodEmpresa() {
		return CodEmpresa;
	}
	public void setCodEmpresa(String codEmpresa) {
		CodEmpresa = codEmpresa;
	}
	public String getDataAbertura() {
		return DataAbertura;
	}
	public void setDataAbertura(String dataAbertura) {
		DataAbertura = dataAbertura;
	}
	public String getHoraAbertura() {
		return HoraAbertura;
	}
	public void setHoraAbertura(String horaAbertura) {
		HoraAbertura = horaAbertura;
	}
	public String getTipo() {
		return Tipo;
	}
	public void setTipo(String tipo) {
		Tipo = tipo;
	}
	public String getOrigem() {
		return Origem;
	}
	public void setOrigem(String origem) {
		Origem = origem;
	}
	public String getModuloRDM() {
		return ModuloRDM;
	}
	public void setModuloRDM(String moduloRDM) {
		ModuloRDM = moduloRDM;
	}
	public String getModulo() {
		return Modulo;
	}
	public void setModulo(String modulo) {
		Modulo = modulo;
	}
	public String getComponente() {
		return Componente;
	}
	public void setComponente(String componente) {
		Componente = componente;
	}
	public String getEquipe() {
		return Equipe;
	}
	public void setEquipe(String equipe) {
		Equipe = equipe;
	}
	public String getSituacaoRDM() {
		return SituacaoRDM;
	}
	public void setSituacaoRDM(String situacaoRDM) {
		SituacaoRDM = situacaoRDM;
	}
	public String getDataConclusao() {
		return DataConclusao;
	}
	public void setDataConclusao(String dataConclusao) {
		DataConclusao = dataConclusao;
	}
	public String getHoraConclusao() {
		return HoraConclusao;
	}
	public void setHoraConclusao(String horaConclusao) {
		HoraConclusao = horaConclusao;
	}
	public ArrayList<String> getListaComponentes() {
		return ListaComponentes;
	}
	public void setListaComponentes(ArrayList<String> listaComponentes) {
		ListaComponentes = listaComponentes;
	}
	public ArrayList<String> getListaModulos() {
		return ListaModulos;
	}
	public void setListaModulos(ArrayList<String> listaModulos) {
		ListaModulos = listaModulos;
	}
	public ArrayList<String> getListaModuloRDM() {
		return ListaModuloRDM;
	}
	public void setListaModuloRDM(ArrayList<String> listaModuloRDM) {
		ListaModuloRDM = listaModuloRDM;
	}

	public ArrayList<String> getListaTipos() {
		return ListaTipos;
	}

	public void setListaTipos(ArrayList<String> listaTipos) {
		ListaTipos = listaTipos;
	}
	
	public ArrayList<MaintenanceType> getMaintenanceTypeList(ArrayList<Arquivo> listaLinhasLidas)
    {
		ArrayList<MaintenanceType> listaFinal = new ArrayList<MaintenanceType>();
        for (int i = 0; i < listaLinhasLidas.size(); i++)
        {
            Arquivo objAux = (Arquivo)listaLinhasLidas.get(i);
            if (listaFinal.size()== 0 && !objAux.getTipo().equals(""))
            {
                listaFinal.add(new MaintenanceType(objAux.getTipo(), 0));
            }
            else
            {
                Boolean encontrou = false;
                for (int j = 0; j < listaFinal.size(); j++)
                {
                    if (listaFinal.get(j).getName().equals(objAux.getTipo()))
                    {
                        encontrou = true;
                        break;
                    }
                }
                if (encontrou == false && !objAux.getTipo().equals(""))
                {
                    listaFinal.add(new MaintenanceType(objAux.getTipo(), 0));
                }
            }
        }
        return listaFinal;
    }
	
	public ArrayList<Arquivo> eliminateCanceledAndNotPerformed(ArrayList<Arquivo> listRequests)
    {
        ArrayList<Arquivo> newList = new ArrayList<Arquivo>();
        for (int i = 0; i < listRequests.size(); i++)
        {
            Arquivo objAux = new Arquivo();
            objAux = (Arquivo)listRequests.get(i);
            if (!objAux.getSituacaoRDM().equals("Cancelada") || (!objAux.getComponente().equals("") && !objAux.getModulo().equals(""))) // recusa RDM's e Desdobramentos com situação = "cancelada"
            {
            	newList.add(listRequests.get(i));
            }
        }
        return newList;
    }
	
	 public ArrayList<Arquivo> joinLists(ArrayList<Arquivo> listRDMs, ArrayList<Arquivo> listDesdobramentos)
     {
         ArrayList<Arquivo> listFull = new ArrayList<Arquivo>();
         for (int i = 0; i < listRDMs.size(); i++)
         {
        	 listFull.add(listRDMs.get(i));
         }
         for (int i = 0; i < listDesdobramentos.size(); i++)
         {
        	 listFull.add(listDesdobramentos.get(i));
         }
         return listFull;
     }
	 
	 private Boolean verifyByType(ArrayList<String> listTypes, String type)
     {
         for (int i = 0; i < listTypes.size(); i++)
         {
             if (listTypes.get(i).equals(type))
             {
                 return true;
             }
         }
         return false;
     }

     public int countRequestsByType(ArrayList<String> validTypes, ArrayList<Arquivo> requestList)
     {
         int qtd = 0;
         for (int i = 0; i < requestList.size(); i++)
         {
             Arquivo objAux = (Arquivo)requestList.get(i);
             Boolean test = false;
             for (int j = 0; j < objAux.getListaTipos().size(); j++)
             {
                 if (verifyByType(validTypes, (String)objAux.getListaTipos().get(j)) == true)
                 {
                     test = true;
                 }
             }
             if(test == true)
             {
                 qtd++;
             }
         }
         return qtd;
     }   
     
     public ArrayList<SetModules> getListComponentsByModule(ArrayList<Arquivo> changeRequestList, String selectedModule)
     {
         Arquivo objDefeito;
         ArrayList<String> listModulosObjeto;
         ArrayList<String> listComponentesObjeto;
         Boolean encontrou = false;
         SetModules objModuloAtual;

         ArrayList<SetModules> listaFinal = new ArrayList<SetModules>();

         for (int i = 0; i < changeRequestList.size(); i++)
         {
             objDefeito = (Arquivo)changeRequestList.get(i); // assume-se que toda linha é uma RDM de Origem                
             listModulosObjeto = objDefeito.getListaModulos();
             listComponentesObjeto = objDefeito.getListaComponentes();
             for (int j = 0; j < listModulosObjeto.size(); j++)
             {
            	 SetModules objManutenido = new SetModules((String)listModulosObjeto.get(j), (String)listComponentesObjeto.get(j));
                 encontrou = false;
                 if (objManutenido.getNome().equals(selectedModule))
                 {
                     if (listaFinal.size() == 0)
                     {
                    	 objManutenido.setOcorrencia(objManutenido.getOcorrencia() + 1);
                         listaFinal.add(objManutenido);
                     }
                     else
                     {
                         for (int l = 0; l < listaFinal.size(); l++)
                         {
                             objModuloAtual = (SetModules)listaFinal.get(l);
                             if (objModuloAtual.getNome().equals(objManutenido.getNome()) && objModuloAtual.getComponenteAssociado().equals(objManutenido.getComponenteAssociado()))
                             {
                            	 objModuloAtual.setOcorrencia(objModuloAtual.getOcorrencia() + 1);
                                 listaFinal.remove(l);
                                 listaFinal.add(l, objModuloAtual);
                                 encontrou = true;
                                 break;
                             }
                         }
                         if (encontrou == false)
                         {
                        	 objManutenido.setOcorrencia(objManutenido.getOcorrencia() + 1);
                             listaFinal.add(objManutenido);
                         }
                     }
                 }
             }
         }
         return listaFinal;
     } 
	
}
