package br.com.cidade.service.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
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
import javax.ws.rs.core.Response.Status;

import br.com.cidade.model.EntryRest;
import br.com.cidade.model.dto.CidadeNomeLatLongDTO;
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
	
	private static final long SAMPLE_IBGEID = 1100015l;
	
	@Inject
	private CidadeDAO cidadeDAO;

	@GET
	@Path("readcsv")
	public Response readCSV() {
		try {
			if (cidadeDAO.findByIbgeId(SAMPLE_IBGEID) != null) {
				cidadeDAO.deleteAll(Cidade.class.getSimpleName());
			}
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
	public Response getCapitaisOrderedByNome() {
		try {
			return Response.ok(cidadeDAO.getCapitaisOrderedByNome()).build();
		} catch (Exception e) {
			return RestUtil.buildResponseBadRequest(e.getMessage());
		}
	}

	@GET
	@Path("/get/estados/quantidade/cidades/extremo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEstadosComMaiorEMenorQuantidadeDeCidades() {
		try {
			List<EntryRest> entry = cidadeDAO.getQuantidadeDeCidadesPorEstado();
			return Response.ok(sortAndReturnByValueWithBiggestAndSmallest(entry)).build();
		} catch (Exception e) {
			return RestUtil.buildResponseBadRequest(e.getMessage());
		}
	}

	@GET
	@Path("/get/estados/quantidade/cidades")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQuantidadeDeCidadesPorEstado() {
		try {
			return Response.ok(cidadeDAO.getQuantidadeDeCidadesPorEstado()).build();
		} catch (Exception e) {
			return RestUtil.buildResponseBadRequest(e.getMessage());
		}
	}

	@GET
	@Path("/get/cidade/{ibgeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCidadeByIbgeId(@PathParam("ibgeId")Long ibgeId) {
		try {
			return Response.ok(cidadeDAO.getCidadeByIbgeId(ibgeId)).build();
		} catch(Exception e){
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/get/nome/cidade/{estado}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNomeDeCidadesPorEstado(@PathParam("estado") String estado) {
		try {
			return Response.ok(cidadeDAO.getNomeCidadesPorEstado(estado)).build();
		} catch (Exception e) {
			return RestUtil.buildResponseBadRequest(e.getMessage());
		}
	}

	@POST
	@Path("/add/cidade")
	public Response addCidade(Cidade cidade) {
		try {
			cidadeDAO.save(cidade);
			return Response.ok().build();
		} catch (Exception ex) {
			return RestUtil.buildResponseBadRequest(ex.getMessage());
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
	public Response getFilterByString(@PathParam("coluna") String coluna, 
									  @PathParam("texto") String texto) {
		try{
			return Response.ok(cidadeDAO.getTextFilterByString(coluna, texto)).build();
		} catch (Exception e) {
			return RestUtil.buildResponseBadRequest(e.getMessage());
		}
	}

	@GET
	@Path("/total/{coluna}/semrepetidos")
	public Response getQuantidadeRegistroByEmColuna(@PathParam("coluna") String coluna) {
		try {
			return Response.ok(cidadeDAO.getQuantidadeRegistrosNotRepetidos(coluna)).build();
		} catch (Exception e) {
			return RestUtil.buildResponseBadRequest(e.getMessage());
		}
	}

	@GET
	@Path("/get/quantidade/registros/total")
	public Response getQuantidadesRegistroTotal() {
		try {
			return Response.ok(cidadeDAO.getQuantidadesRegistroTotal()).build();
		} catch (Exception e) {
			return RestUtil.buildResponseBadRequest(e.getMessage());
		}
	}

	@GET
	@Path("/get/maiordistancia/cidades")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCidadesComMaiorDistanciaEntreElas() {
		try {
			List<CidadeNomeLatLongDTO> listCidadesNomeLatLong = cidadeDAO.findAllCidadeNomeLatLongDTO();
			List<DistanciaCidadeDTO> listDitanciaCidadeDTO = new ArrayList();

			for (CidadeNomeLatLongDTO cidade : listCidadesNomeLatLong) {
				getDistancia(cidade, listCidadesNomeLatLong, listDitanciaCidadeDTO);
			}
			Collections.sort(listDitanciaCidadeDTO);

		return Response.ok(listDitanciaCidadeDTO.get(0)).build();
		} catch (Exception e) {
			return RestUtil.buildResponseBadRequest(e.getMessage());
		}
	}

	private void getDistancia(CidadeNomeLatLongDTO cidade, List<CidadeNomeLatLongDTO> listCidade, List<DistanciaCidadeDTO> listDitancia) {
		for (CidadeNomeLatLongDTO aCidade : listCidade) {
			listDitancia.add(new DistanciaCidadeDTO(cidade.getNome(), aCidade.getNome(),
					distance(cidade.getLat(), cidade.getLon(), aCidade.getLat(), aCidade.getLon())));
		}
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
	
	private List<EntryRest> sortAndReturnByValueWithBiggestAndSmallest(List<EntryRest> entry) {
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

}
