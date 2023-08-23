package givemeviews.model;

public class SortPercent implements Comparable<SortPercent> {
	 
	    private int id;
        private int randomID;
        private String nome;
        private int ocorrencia;
        private String componenteAssociado;
        private float porcentagemAssociada;
      
	    public SortPercent()
	    {
	    	
	    }
        
	    public SortPercent(int pid, int prandomID, String pnome, int pocorrencia, String pcomponenteAssociado, float pporcentagemAssociada) 
	    {
	        super();
	        this.setId(pid);
	        this.setRandomID(prandomID);
	        this.setNome(pnome);
	        this.setOcorrencia(pocorrencia);
	        this.setComponenteAssociado(pcomponenteAssociado);
	        this.setPorcentagemAssociada(pporcentagemAssociada);
	    }
	    
	    public SortPercent(String pnome, String pcomponenteAssociado, float pporcentagemAssociada) 
	    {
	        super();
	        this.setNome(pnome);
	        this.setComponenteAssociado(pcomponenteAssociado);
	        this.setPorcentagemAssociada(pporcentagemAssociada);
	    }
	 
	    
	 
	    public int compareTo(SortPercent modulo) {
	        if(this.porcentagemAssociada > modulo.porcentagemAssociada){
	            return -1;
	        }
	        else if(this.porcentagemAssociada < modulo.porcentagemAssociada){
	            return 1;
	        }
	        return this.getNome() .compareToIgnoreCase(modulo.getNome());
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
	}     

