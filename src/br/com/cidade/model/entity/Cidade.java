package br.com.cidade.model.entity;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 
 * @author Jose Wollinger
 * Feb 20, 2018
 */
@Entity
public class Cidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="ibge_id")
	private Long ibgeId;
	
	@Column(name="UF")
	private String uf;
	
	@Column(name="name")
	private String nome;
	
	private Boolean capital;
	
	@Column(name="lon")
	private Double longitude;
	
	@Column(name="lat")
	private Double latitude;
	
	@Column(name="no_accents")
	private String noAccents;
	
	@Column(name="alternative_names")
	private String alternativeNames;
	
	@Column(name="microRegion")
	private String microRegiao;
	
	@Column(name="mesoRegion")
	private String mesoRegiao;

	public static String getFieldByColumnName(String field) throws Exception{
		switch (field) {
		case "ibge_id":
			return "ibgeId";
		case "UF":
			return "uf";
		case "name":
			return "nome";
		case "lon":
			return "longitude";
		case "lat":
			return "latitude";
		case "no_accents":
			return "noAccents";
		case "alternative_names":
			return "alternativeNames";
		case "microRegion":
			return "microRegiao";
		case "mesoRegion":
			return "mesoRegiao";
		default:
			throw new Exception(String.format("Coluna %s não encontrada", field));
		}
	}
	
	public Cidade(){}
	
	public Cidade(Long ibgeId, String uf, String nome, Boolean capital, Double longitude, Double latitude,
			String noAccents, String alternativeNames, String microRegiao, String mesoRegiao) {
		this.ibgeId = ibgeId;
		this.uf = uf;
		this.nome = nome;
		this.capital = capital;
		this.longitude = longitude;
		this.latitude = latitude;
		this.noAccents = noAccents;
		this.alternativeNames = alternativeNames;
		this.microRegiao = microRegiao;
		this.mesoRegiao = mesoRegiao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIbgeId() {
		return ibgeId;
	}

	public void setIbgeId(Long ibgeId) {
		this.ibgeId = ibgeId;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean isCapital() {
		return capital;
	}

	public void setCapital(Boolean capital) {
		this.capital = capital;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getNoAccents() {
		return noAccents;
	}

	public void setNoAccents(String noAccents) {
		this.noAccents = noAccents;
	}

	public String getAlternativeNames() {
		return alternativeNames;
	}

	public void setAlternativeNames(String alternativeNames) {
		this.alternativeNames = alternativeNames;
	}

	public String getMicroRegiao() {
		return microRegiao;
	}

	public void setMicroRegiao(String microRegiao) {
		this.microRegiao = microRegiao;
	}

	public String getMesoRegiao() {
		return mesoRegiao;
	}

	public void setMesoRegiao(String mesoRegiao) {
		this.mesoRegiao = mesoRegiao;
	}

}
