package br.com.cidade.service.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.cidade.model.EntryRest;
import br.com.cidade.model.dto.DistanciaCidadeDTO;
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
	    //TODO: arrumar URLs
		//TODO: arrumar repostas de erro
		//TODO: arrumar calculo de maior ditancia (ineficiente)
		
		@Inject
		private CidadeDAO cidadeDAO;
		
	@GET
	@Path("readcsv")
	public Response readCSV() {
		try {
			cidadeDAO.deleteAll(Cidade.class.getSimpleName());
			List<Cidade> listCidade = CidadeEntityLoader.loadCidadesFromCsvFile();
			listCidade.forEach(cidade -> cidadeDAO.save(cidade));

			return Response.ok().build();
		} catch (Exception e) {
			return RestUtil.buildResponseError(e.getMessage());
		}
	}

	@GET
	@Path("/get/capitais")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Cidade> getCapitaisOrderedByNome() {
		return cidadeDAO.getCapitaisOrderedByNome();
	}

	@GET
	@Path("/get/estados/quantidade/cidades/extremo")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EntryRest> getEstadosComMaiorEMenorQuantidadeDeCidades() {
		List<EntryRest> entry = cidadeDAO.getQuantidadeDeCidadesPorEstado();
		return sortAndReturnByQuantidadeDeCidadesMaiorEMenor(entry);
	}

	@GET
	@Path("/get/estados/quantidade/cidades")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EntryRest> getQuantidadeDeCidadesPorEstado() {
		return cidadeDAO.getQuantidadeDeCidadesPorEstado();
	}

	@GET
	@Path("/get/cidade/{ibgeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void getCidadeByIbgeId(@PathParam("ibgeId")Long ibgeId) {
		cidadeDAO.getCidadeByIbgeId(ibgeId);
	}

	@GET
	@Path("/get/nome/cidade/{estado}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getNomeDeCidadesPorEstado(@PathParam("estado") String estado) {
		return cidadeDAO.getNomeCidadesPorEstado(estado);
	}

	@POST
	@Path("/add/cidade")
	public Response addCidade(Cidade cidade) {
		try {
			cidadeDAO.save(cidade);
			return Response.ok().build();
		} catch (Exception ex) {
			return RestUtil.buildResponseError(ex.getMessage());
		}
	}

	@DELETE
	@Path("/cidade/{ibgeId}")
	public Response deleteCidade(@PathParam("ibgeId") Long ibgeId) {
		try {
			cidadeDAO.delete(cidadeDAO.findByIbgeId(ibgeId));
			return Response.ok().build();
		} catch (Exception e) {
			return RestUtil.buildResponseError(e.getMessage());
		}
	}

	@GET
	@Path("/get/coluna/{coluna}/{texto}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getFilterByString(@PathParam("coluna") String coluna, 
										  @PathParam("texto") String texto) {
		return cidadeDAO.getTextFilterByString(coluna, texto);
	}

	@GET
	@Path("/total/{coluna}/semrepetidos")
	public Long getQuantidadeRegistroBaseadoEmColuna(@PathParam("coluna") String coluna) {
		return cidadeDAO.getQuantidadeRegistrosNotRepetidos(coluna);
	}

	@GET
	@Path("/get/quantidade/registros/total")
	public Long getQuantidadesRegistroTotal() {
		return cidadeDAO.getQuantidadesRegistroTotal();
	}

	@GET
	@Path("/get/maiordistanciaentrecidades")
	@Produces(MediaType.APPLICATION_JSON)
	public DistanciaCidadeDTO getCidadesComMaiorDistanciaEntreElas() {

		List<Cidade> listCidades = cidadeDAO.getTodasCidades();
		System.out.println(listCidades.size());
		List<DistanciaCidadeDTO> listDitancia = new ArrayList();
		for (Cidade cidade1 : listCidades) {
			for (Cidade cidade2 : listCidades) {
				if (cidade1.getIbgeId() != cidade2.getIbgeId()) {
					listDitancia.add(new DistanciaCidadeDTO(cidade1.getNome(), cidade2.getNome(),
							getDistancia(cidade1, cidade2)));
				}
			}
		}
		Collections.sort(listDitancia);
		List<DistanciaCidadeDTO> listClean = new ArrayList();

		return listClean.get(0);
	}

	private Double getDistancia(Cidade cidade1, Cidade cidade2) {
		return distance(cidade1.getLatitude(), cidade1.getLongitude(), cidade2.getLatitude(), cidade2.getLongitude());
	}

	private List<EntryRest> sortAndReturnByQuantidadeDeCidadesMaiorEMenor(List<EntryRest> entry) {
		Collections.sort(entry, new Comparator<EntryRest>() {

			@Override
			public int compare(EntryRest o1, EntryRest o2) {
				return o1.valueAsInt() - o2.valueAsInt();
			}
		});
		List<EntryRest> result = new ArrayList(2);
		result.add(entry.get(0));
		result.add(entry.get(entry.size() - 1));
		return result;
	}

	private double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;

		return (dist);
	}

	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

}
