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
	 * Obs: Esta classe será responsável por instanciar os objetos métricas que forem calculados
	 * A cada métrica calculada, eu irei instanciar um objeto, que fará parte de um arraylist de
	 * métricas. No final o arrylist será armazenado na classe singleton
	 */

}
