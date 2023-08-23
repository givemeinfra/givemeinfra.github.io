package givemeviews.model;

import java.util.ArrayList;

public class MantisCase 
{
	private String num;
	private String projeto;
	private String relator;
	private String atribuido;
	private String prioridade;
	private String gravidade;
	private String frequencia;
	private String estado;
	private String resolucao;
	private String categoria;
	private String dataEnvio;
	private String atualizado;
	private String so;
	private String versaoSO;
	private String plataforma;
	private String versaoProduto;
	private String corrigidoNaVersao;
	private String visibilidade;
	private String previstoPara;
	private String resumo;
	private String dataLimite;
	private String descricao;
	private String passosReproduzir;
	private String informacoesAdicionais;
	private String tipoCaso;
	
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getProjeto() {
		return projeto;
	}
	public void setProjeto(String projeto) {
		this.projeto = projeto;
	}
	public String getRelator() {
		return relator;
	}
	public void setRelator(String relator) {
		this.relator = relator;
	}
	public String getAtribuido() {
		return atribuido;
	}
	public void setAtribuido(String atribuido) {
		this.atribuido = atribuido;
	}
	public String getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}
	public String getGravidade() {
		return gravidade;
	}
	public void setGravidade(String gravidade) {
		this.gravidade = gravidade;
	}
	public String getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(String frequencia) {
		this.frequencia = frequencia;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getResolucao() {
		return resolucao;
	}
	public void setResolucao(String resolucao) {
		this.resolucao = resolucao;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getDataEnvio() {
		return dataEnvio;
	}
	public void setDataEnvio(String dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
	public String getAtualizado() {
		return atualizado;
	}
	public void setAtualizado(String atualizado) {
		this.atualizado = atualizado;
	}
	public String getSo() {
		return so;
	}
	public void setSo(String so) {
		this.so = so;
	}
	public String getVersaoSO() {
		return versaoSO;
	}
	public void setVersaoSO(String versaoSO) {
		this.versaoSO = versaoSO;
	}
	public String getPlataforma() {
		return plataforma;
	}
	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}
	public String getVersaoProduto() {
		return versaoProduto;
	}
	public void setVersaoProduto(String versaoProduto) {
		this.versaoProduto = versaoProduto;
	}
	public String getCorrigidoNaVersao() {
		return corrigidoNaVersao;
	}
	public void setCorrigidoNaVersao(String corrigidoNaVersao) {
		this.corrigidoNaVersao = corrigidoNaVersao;
	}
	public String getVisibilidade() {
		return visibilidade;
	}
	public void setVisibilidade(String visibilidade) {
		this.visibilidade = visibilidade;
	}
	public String getPrevistoPara() {
		return previstoPara;
	}
	public void setPrevistoPara(String previstoPara) {
		this.previstoPara = previstoPara;
	}
	public String getResumo() {
		return resumo;
	}
	public void setResumo(String resumo) {
		this.resumo = resumo;
	}
	public String getDataLimite() {
		return dataLimite;
	}
	public void setDataLimite(String dataLimite) {
		this.dataLimite = dataLimite;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getPassosReproduzir() {
		return passosReproduzir;
	}
	public void setPassosReproduzir(String passosReproduzir) {
		this.passosReproduzir = passosReproduzir;
	}
	public String getInformacoesAdicionais() {
		return informacoesAdicionais;
	}
	public void setInformacoesAdicionais(String informacoesAdicionais) {
		this.informacoesAdicionais = informacoesAdicionais;
	}
	public String getTipoCaso() {
		return tipoCaso;
	}
	public void setTipoCaso(String tipoCaso) {
		this.tipoCaso = tipoCaso;
	}
	
	/*public ArrayList<String[]> create(ArrayList<String[]> listLines)
	{
		ArrayList<MantisCase> listFinal = new ArrayList<>();
		for(int i = 0; i < listLines.size(); i++)
		{
			MantisCase objCaso = new MantisCase();
			objCaso.setNum(listLines.get(i)[0]);
			objCaso.setProjeto(listLines.get(i)[1]);
			objCaso.setRelator(listLines.get(i)[2]);
			objCaso.setAtribuido(listLines.get(i)[0]);
			objCaso.setPrioridade(listLines.get(i)[0]);
			objCaso.setGravidade(listLines.get(i)[0]);
			objCaso.setFrequencia(listLines.get(i)[0]);
			objCaso.setEstado(listLines.get(i)[0]);
			objCaso.setResolucao(listLines.get(i)[0]);
			objCaso.setCategoria(listLines.get(i)[0]);
			objCaso.setDataEnvio(listLines.get(i)[0]);
			objCaso.setAtualizado(listLines.get(i)[0]);
			objCaso.setSo(listLines.get(i)[0]);
			objCaso.setVersaoSO(listLines.get(i)[0]);
			objCaso.setPlataforma(listLines.get(i)[0]);
			objCaso.setVersaoProduto(listLines.get(i)[0]);
			objCaso.setCorrigidoNaVersao(listLines.get(i)[0]);
			objCaso.setVisibilidade(listLines.get(i)[0]);
			objCaso.setResumo(listLines.get(i)[0]);
			objCaso.setDataLimite(listLines.get(i)[0]);
			objCaso.setDescricao(listLines.get(i)[0]);
			objCaso.setPassosReproduzir(listLines.get(i)[0]);
			objCaso.setInformacoesAdicionais(listLines.get(i)[0]);
			objCaso.setTipoCaso(listLines.get(i)[0]);
			listFinal.add(objCaso);
		}		
		
		return listLines;		
	}*/
	
	public ArrayList<MaintenanceType> getMaintenanceTypeList(ArrayList<MantisCase> listaLinhasLidas)
    {
		ArrayList<MaintenanceType> listaFinal = new ArrayList<MaintenanceType>();
        for (int i = 0; i < listaLinhasLidas.size(); i++)
        {
            MantisCase objAux = (MantisCase)listaLinhasLidas.get(i);
            if (listaFinal.size()== 0 && !objAux.getTipoCaso().equals(""))
            {
                listaFinal.add(new MaintenanceType(objAux.getTipoCaso(), 0));
            }
            else
            {
                Boolean encontrou = false;
                for (int j = 0; j < listaFinal.size(); j++)
                {
                    if (listaFinal.get(j).getName().equals(objAux.getTipoCaso()))
                    {
                        encontrou = true;
                        break;
                    }
                }
                if (encontrou == false && !objAux.getTipoCaso().equals(""))
                {
                    listaFinal.add(new MaintenanceType(objAux.getTipoCaso(), 0));
                }
            }
        }
        return listaFinal;
    }
	
	
	public ArrayList<GenericObject> getPriorityCount(ArrayList<MantisCase> listCases)
	{
		ArrayList<GenericObject> listFinal = new ArrayList<>();
		for(int i = 0; i < listCases.size(); i++)
		{
			Boolean status = false;
			for(int j = 0; j < listFinal.size(); j++)
			{
				if(listFinal.get(j).getNome().equals(listCases.get(i).getPrioridade()))
				{
					listFinal.get(j).setOcorrencia(listFinal.get(j).getOcorrencia() + 1);
					status = true;
				}
			}
			if(status == false)
			{
				listFinal.add(new GenericObject(listCases.get(i).getPrioridade(), 1));
			}
		}
		return listFinal;		
	}
	
	
}
