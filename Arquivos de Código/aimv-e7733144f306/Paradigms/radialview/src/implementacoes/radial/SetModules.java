package implementacoes.radial;

import java.util.ArrayList;

public class SetModules 
{
	SetModules[][] tabelaComponentes;
    SetModules[][] tabelaComponentesFrequencias;
    
    private int id;
    private int randomID;
    private String nome;
    private int ocorrencia;
    private String componenteAssociado;
    private float porcentagemAssociada; 
   
   	public int getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(int ocorrencia) {
		this.ocorrencia = ocorrencia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public float getPorcentagemAssociada() {
		return porcentagemAssociada;
	}
	
	public void setPorcentagemAssociada(float porcentagemAssociada) {
		this.porcentagemAssociada = porcentagemAssociada;
	}

	public String getComponenteAssociado() {
		return componenteAssociado;
	}

	public void setComponenteAssociado(String componenteAssociado) {
		this.componenteAssociado = componenteAssociado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getRandomID() {
		return randomID;
	}

	public void setRandomID(int randomID) {
		this.randomID = randomID;
	}
	
	public SetModules(String pnome, int pocorrencia)
    {
        this.setNome(pnome);
        this.setOcorrencia(pocorrencia);
    }

    public SetModules()
    {

    }

    public SetModules(String pnome, String pcomponenteAssociado)
    {
        this.setNome(pnome);
        this.setComponenteAssociado(pcomponenteAssociado);
    }

    public SetModules(String pnome, String pcomponenteAssociado, String pPorcentagem)
    {
        this.setNome(pnome);
        this.setComponenteAssociado(pcomponenteAssociado);
        this.setPorcentagemAssociada(porcentagemAssociada);
    }
    
    public SetModules(int id, String pnome, String pcomponenteAssociado, String pPorcentagem )
    {
    	this.setId(id);
    	this.setNome(pnome);
        this.setComponenteAssociado(pcomponenteAssociado);
        this.setPorcentagemAssociada(porcentagemAssociada);
    }	
    
 
}
