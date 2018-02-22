package br.com.cidade.model.dto;

public class CidadeNomeLatLongDTO {
	
	private String nome;
	
	private Double lon;
	
	private Double lat;

	public CidadeNomeLatLongDTO(String nome, Double lon, Double lat) {
		super();
		this.nome = nome;
		this.lon = lon;
		this.lat = lat;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}
	
	

}
