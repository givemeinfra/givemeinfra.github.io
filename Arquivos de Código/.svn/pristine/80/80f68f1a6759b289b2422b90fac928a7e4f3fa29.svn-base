package givemeviews.model;

import java.awt.List;
import java.util.ArrayList;

public class SetModules
{
	private String nome;
    private int ocorrencia;
    private String componenteAssociado;
    private float porcentagemAssociada;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getOcorrencia() {
		return ocorrencia;
	}
	public void setOcorrencia(int ocorrencia) {
		this.ocorrencia = ocorrencia;
	}
	public String getComponenteAssociado() {
		return componenteAssociado;
	}
	public void setComponenteAssociado(String componenteAssociado) {
		this.componenteAssociado = componenteAssociado;
	}
	public float getPorcentagemAssociada() {
		return porcentagemAssociada;
	}
	public void setPorcentagemAssociada(float porcentagemAssociada) {
		this.porcentagemAssociada = porcentagemAssociada;
	}  
	
	public SetModules()
	{

	}
	
	public SetModules(String nome, int ocorrencia)
    {
        this.nome = nome;
        this.ocorrencia = ocorrencia;
    }
   

    public SetModules(String nome, String componenteAssociado)
    {
        this.nome = nome;
        this.componenteAssociado = componenteAssociado;
    }

    public SetModules(String nome, String componenteAssociado, int ocorrencia)
    {
        this.nome = nome;
        this.componenteAssociado = componenteAssociado;
        this.ocorrencia = ocorrencia;
    }

    public SetModules(String nome, String componenteAssociado, float porcentagemAssociada)
    {
        this.nome = nome;
        this.componenteAssociado = componenteAssociado;
        this.porcentagemAssociada = porcentagemAssociada;
    }
    
    public ArrayList<SetModules> returnListModulesDriverCEO(ArrayList<Arquivo> listaSolicitacoesMudancas)
    {
        ArrayList<SetModules> listaFinal = new ArrayList<SetModules>();
        Arquivo objDefeitoRecuperado;
        SetModules objModulo;
        SetModules objModuloExistente;
        SetModules objModuloNovo;
        Boolean encontrou = false;

        for (int i = 0; i < listaSolicitacoesMudancas.size(); i++)
        {
            objDefeitoRecuperado = (Arquivo)listaSolicitacoesMudancas.get(i);

            ArrayList<String> listaItems = objDefeitoRecuperado.getListaModulos();
            for (int x = 0; x < listaItems.size(); x++)
            {
                encontrou = false;
                if (listaFinal.size() == 0) // cria novo modulo e add a lista final
                {
                    objModulo = new SetModules((String)listaItems.get(x), 1);
                    listaFinal.add(objModulo);
                }
                else // atualiza ocorrencia do modulo e atualiza na lista.
                {
                    for (int j = 0; j < listaFinal.size(); j++)
                    {
                        objModuloExistente = (SetModules)listaFinal.get(j);
                        if (objModuloExistente.getNome().equals(listaItems.get(x)))
                        {
                            encontrou = true;
                            objModuloExistente.setOcorrencia(objModuloExistente.getOcorrencia() + 1); 
                            listaFinal.remove(j);
                            listaFinal.add(j, objModuloExistente);
                            break;
                        }
                    }
                    if (encontrou == false)
                    {
                        objModuloNovo = new SetModules((String)listaItems.get(x), 1);
                        listaFinal.add(objModuloNovo);
                    }
                }
            }
        }
        return listaFinal;
    }
    
    public ArrayList<SetModules> returnListModulesMantisDate(ArrayList<MantisCase> listCases)
    {
        ArrayList<SetModules> listaFinal = new ArrayList<SetModules>();
        for(int i = 0; i < listCases.size(); i++)
        {
        	if(!listCases.get(i).getCategoria().equals(""))
        	{
        		Boolean found = false;
        		for(int j = 0; j < listaFinal.size(); j++)
        		{
        			if(listaFinal.get(j).getNome().equals(listCases.get(i).getCategoria()))
        			{
        				found = true;
        				listaFinal.get(j).setOcorrencia(listaFinal.get(j).getOcorrencia() + 1); // Incrementa ocorrencia
        			}
        		}
        		if(found == false)
        		{
        			listaFinal.add(new SetModules(listCases.get(i).getCategoria(), 1));
        		}
        		else
        			found = false;
        	}
        }        
        return listaFinal;
    }
    
    public ArrayList<SetModules> returnListModulesAndComponents(ArrayList<Arquivo> listChangeRequests, String selectedModule)
    {
        Arquivo objDefeito;
        ArrayList<String> listModulosObjeto;
        ArrayList<String> listComponentesObjeto;
        Boolean encontrou = false;
        SetModules objModuloAtual;
        
        ArrayList<SetModules> listFinal = new ArrayList<SetModules>();

        for (int i = 0; i < listChangeRequests.size(); i++)
        {
            objDefeito = (Arquivo)listChangeRequests.get(i);           
            listModulosObjeto = objDefeito.getListaModulos();
            listComponentesObjeto = objDefeito.getListaComponentes();
            for (int j = 0; j < listModulosObjeto.size(); j++)
            {
            	if(selectedModule.length() == 0) 
            	{
	                SetModules objManutenido = new SetModules((String)listModulosObjeto.get(j), (String)listComponentesObjeto.get(j));
	                encontrou = false;
	                if (listFinal.size() == 0)
	                {
	                	listFinal.add(objManutenido);
	                }
	                else
	                {
	                    for (int l = 0; l < listFinal.size(); l++)
	                    {
	                        objModuloAtual = (SetModules)listFinal.get(l);
	                        if (objModuloAtual.getNome().equals(objManutenido.getNome()) && objModuloAtual.getComponenteAssociado().equals(objManutenido.getComponenteAssociado()))
	                        {
	                            encontrou = true;
	                            break;
	                        }
	                    }
	                    if (encontrou == false)
	                    {
	                    	listFinal.add(objManutenido);
	                    }
	                }
            	}// fim if
            	else // select Change requests by Selected Module
            	{
            		SetModules objManutenido = new SetModules((String)listModulosObjeto.get(j), (String)listComponentesObjeto.get(j));
            		if(objManutenido.getNome().equals(selectedModule))
            		{
		                encontrou = false;
		                if (listFinal.size() == 0)
		                {
		                	listFinal.add(objManutenido);
		                }
		                else
		                {
		                    for (int l = 0; l < listFinal.size(); l++)
		                    {
		                        objModuloAtual = (SetModules)listFinal.get(l);
		                        if (objModuloAtual.getNome().equals(objManutenido.getNome()) && objModuloAtual.getComponenteAssociado().equals(objManutenido.getComponenteAssociado()))
		                        {
		                            encontrou = true;
		                            break;
		                        }
		                    }
		                    if (encontrou == false)
		                    {
		                    	listFinal.add(objManutenido);
		                    }
		                }
            		}// fim if
            	}// fim else
            }
        }
        return listFinal;
    }
    
    public int getTotalMaintenanceNumber(ArrayList<Arquivo> listChangeRequests)
    {
        int contManutencoes = 0;
        for (int i = 0; i < listChangeRequests.size(); i++)
        {
            Arquivo objArq = (Arquivo)listChangeRequests.get(i);
            contManutencoes = contManutencoes + objArq.getListaComponentes().size();
        }
        return contManutencoes;
    }
    
    public int getTotalMantisMaintenanceNumber(ArrayList<ObjectLog> listChangeRequests)
    {
        int contManutencoes = 0;
        for (int i = 0; i < listChangeRequests.size(); i++)
        {
        	ObjectLog objArq = (ObjectLog)listChangeRequests.get(i);
            contManutencoes = contManutencoes + objArq.getListLogComponents().size();
        }
        return contManutencoes;
    }
    
}
