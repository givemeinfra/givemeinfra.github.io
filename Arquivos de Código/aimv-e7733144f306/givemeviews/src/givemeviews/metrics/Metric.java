package givemeviews.metrics;

public class Metric {
	
	private String descricao;
	private String metrica;
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getMetrica() {
		return metrica;
	}

	public void setMetrica(String metrica) {
		this.metrica = metrica;
	}
	
	public Metric(String descricao, String metrica)
	{
		this.descricao = descricao;
		this.metrica = metrica;
	}
	
	/*
	 * Obs: Esta classe ser� respons�vel por instanciar os objetos m�tricas que forem calculados
	 * A cada m�trica calculada, eu irei instanciar um objeto, que far� parte de um arraylist de
	 * m�tricas. No final o arrylist ser� armazenado na classe singleton
	 */

}
