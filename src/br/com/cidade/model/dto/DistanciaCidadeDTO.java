package br.com.cidade.model.dto;
/***
 * 
 * @author Jose Wollinger
 * 21 Feb, 2018
 */
public class DistanciaCidadeDTO implements Comparable<DistanciaCidadeDTO> {
	
	private String nomeCidade1;
	
	private String nomeCidade2;
	
	private Double distancia;

	public DistanciaCidadeDTO(String nomeCidade1, String nomeCidade2, Double distancia) {
		super();
		this.nomeCidade1 = nomeCidade1;
		this.nomeCidade2 = nomeCidade2;
		this.distancia = distancia;
	}

	public String getCidade1() {
		return nomeCidade1;
	}

	public void setCidade1(String cidade1) {
		this.nomeCidade1 = cidade1;
	}

	public String getCidade2() {
		return nomeCidade2;
	}

	public void setCidade2(String cidade2) {
		this.nomeCidade2 = cidade2;
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
