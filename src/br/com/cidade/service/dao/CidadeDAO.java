package br.com.cidade.service.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.com.cidade.model.EntryRest;
import br.com.cidade.model.dto.CidadeNomeLatLongDTO;
import br.com.cidade.model.entity.Cidade;

/**
 * 
 * @author Jose Wollinger
 * Feb 20, 2018
 */
@Stateless
public class CidadeDAO extends AbstractDAO<Cidade>{

	public Cidade getCidadeByIbgeId(Long ibgeId) {
		
		return getEntityManager().createQuery("select c from Cidade c " 
				+ "where c.ibgeId = ?1", Cidade.class)
				.setParameter(1, ibgeId)
				.getSingleResult();
	}
	
	public List<Cidade> getCapitaisOrderedByNome(){
		return getEntityManager().createQuery("select c from Cidade c "
				+ "where c.capital = true order by c.nome", Cidade.class)
				.getResultList();
	}

	public Cidade findByIbgeId(Long ibgeId) {
		return getEntityManager().createQuery("select c from Cidade c "
				+ "where c.ibgeId = ?1",Cidade.class)
				.setParameter(1, ibgeId)
				.getSingleResult();
	}
	
	public List<EntryRest> getQuantidadeDeCidadesPorEstado(){
		return getEntityManager().createQuery("select new br.com.cidade.model.EntryRest(c.uf, count(c.nome)) "
				+ "from Cidade c "
				+ "group by c.uf", EntryRest.class)
				.getResultList();
	}
	
	public Long getQuantidadesRegistroTotal() {
		return getEntityManager().createQuery("select count(c) from Cidade c", Long.class)
				.getSingleResult();
	}

	public List<String> getTextFilterByString(String coluna, String texto) throws Exception {
		String field = Cidade.getFieldByColumnName(coluna);
		return getEntityManager().createQuery("select c." + field + " from Cidade c "
				+ "where c." + field 
				+ " like ?1", String.class)
				.setParameter(1, "%" + texto + "%")
				.getResultList();
	}

	public Long getQuantidadeRegistrosNotRepetidos(String coluna) throws Exception {
		String field = Cidade.getFieldByColumnName(coluna);
		return getEntityManager().createQuery("select count(distinct c." + field + ") "
				+ "from Cidade c ",Long.class)
				.getSingleResult();
	}

	public List<String> getNomeCidadesPorEstado(String estado) {
		return getEntityManager().createQuery("select c.nome from Cidade c "
				+ "where c.uf = ?1", String.class)
				.setParameter(1, estado.toUpperCase())
				.getResultList();
	}

	public List<CidadeNomeLatLongDTO> findAllCidadeNomeLatLongDTO() {
		return getEntityManager().createQuery("select new br.com.cidade.model.dto.CidadeNomeLatLongDTO"
				+ "(c.nome, c.longitude, c.latitude) from Cidade c", CidadeNomeLatLongDTO.class)
				.getResultList();
	}

}
