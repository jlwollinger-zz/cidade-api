package br.com.cidade.service.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.cidade.model.entity.Cidade;
import br.com.cidade.service.CidadeEntityLoader;
import br.com.cidade.service.dao.CidadeDAO;
import br.com.cidade.uti.RestUtil;

/**
 * 
 * @author Jose Wollinger
 * Feb 20, 2018
 */
@Stateless
@Path("/cidade")
public class CidadeRest {
	
	@Inject
	private CidadeDAO cidadeDAO;
	
	@GET
	@Path("readcsv")
	public Response readCSV() {
		cidadeDAO.deleteAll(Cidade.class.getSimpleName());
		List<Cidade> listCidade = CidadeEntityLoader.loadCidadesFromCsvFile();
		listCidade.forEach(cidade -> cidadeDAO.save(cidade));
		
		return Response.ok().build();
	}

	@GET
	@Path("/get/capitais")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Cidade> getCapitaisOrderedByNome() {
		return cidadeDAO.getCapitaisOrderedByNome();
	}
	
	@GET
	@Path("/get/estados/qtmaior/qtmenor")
	@Produces(MediaType.APPLICATION_JSON)
	public void getEstadosComMaiorEMenorQuantidadeDeCidades() {
		
	}
	
	@GET
	@Path("/get/qtcidades/estado")
	@Produces(MediaType.APPLICATION_JSON)
	public void getQuantidadeDeCidadesPorEstado() {
		
	}
	
	@GET
	@Path("/get/ibgeId")
	@Produces(MediaType.APPLICATION_JSON)
	public void getCidadeByIbgeId(Long ibgeId) {
		cidadeDAO.getCidadeByIbgeId(ibgeId);
	}
	
	@GET
	@Path("/get/nomecidades/estado")
	@Produces(MediaType.APPLICATION_JSON)
	public void getNomeDeCidadesPorEstado(@QueryParam("estado") String estado) {
		
	}
	
	@POST
	@Path("/add/cidade")
	public Response addCidade(@QueryParam("ibgeId") Long ibgeId) {
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/cidade/{ibgeId}")
	public Response deleteCidade(@PathParam("ibgeId") Long ibgeId) {
		try{
			cidadeDAO.delete(cidadeDAO.findByIbgeId(ibgeId));
			return Response.ok().build();
		}catch (Exception e){
			return RestUtil.buildResponseError(e.getMessage());
		}
	}
	
	@GET
	@Path("/get/coluna/string")
	public void getFilterByString() {
		List<String> listString = new ArrayList();
	}
	
	@GET
	@Path("/get/registrosemcoluna/semrepetidos")
	public Integer getQuantidadeRegistroBaseadoEmColuna() {
		return 0;
	}
	
	@GET
	@Path("/get/quantidade/registros/total")
	public Integer getQuantidadesRegistroTotal() {
		return 0;
	}
	
	
    @GET
    @Path("/get/maiordistanciaentrecidades")
    public void getCidadesComMaiorDistanciaEntreElas() {
        List<Cidade> listCidades = new ArrayList();
         JsonArrayBuilder builder = Json.createArrayBuilder();
        listCidades.forEach(cidade -> builder.add(cidade.toJson()));
        //builder.build().toString();
    }
    /**
     * 	double cRadiansLat = Math.toRadians(c.getLat());
		double ciRadiansLat = Math.toRadians(ci.getLat());
		double deltaRadiansLongitude = Math.toRadians(c.getLon() - c.getLon());
		return Math.acos(Math.cos(cRadiansLat) * Math.cos(ciRadiansLat)
			* Math.cos(deltaRadiansLongitude) + Math.sin(cRadiansLat)
* Math.sin(ciRadiansLat)) * 6371;
     */

}
