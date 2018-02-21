package br.com.cidade.model.dto;
/***
 * 
 * @author Jose Wollinger
 * 21 Feb, 2018
 */
public class DistanciaCidadeDTO implements Comparable<DistanciaCidadeDTO> {
	
	private String cidade1;
	
	private String cidade2;
	
	private Double distancia;

	public DistanciaCidadeDTO(String cidade1, String cidade2, Double distancia) {
		super();
		this.cidade1 = cidade1;
		this.cidade2 = cidade2;
		this.distancia = distancia;
	}

	public String getCidade1() {
		return cidade1;
	}

	public void setCidade1(String cidade1) {
		this.cidade1 = cidade1;
	}

	public String getCidade2() {
		return cidade2;
	}

	public void setCidade2(String cidade2) {
		this.cidade2 = cidade2;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

	@Override
	public int compareTo(DistanciaCidadeDTO o) {
		return Double.compare(this.getDistancia(), o.getDistancia());
	}
	
	

}
