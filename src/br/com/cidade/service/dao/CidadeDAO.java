package br.com.cidade.service.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.com.cidade.model.EntryRest;
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
				+ " where c.ibgeId = ?1", Cidade.class)
				.setParameter(1, ibgeId)
				.getSingleResult();
	}
	
	public List<Cidade> getCapitaisOrderedByNome(){
		return getEntityManager().createQuery("select c from Cidade c where c.capital = true order by c.nome", Cidade.class)
				.getResultList();
	}

	public Cidade findByIbgeId(Long ibgeId) {
		return getEntityManager().createQuery("select c from Cidade c where c.ibgeId = ?1",Cidade.class)
				.setParameter(1, ibgeId)
				.getSingleResult();
	}
	
	public List<EntryRest> getQuantidadeDeCidadesPorEstado(){
		return getEntityManager().createQuery("select new br.com.desafio.entity.EntryRest(c.Uf, count(c.nome)) from Cidade c group by c.Uf")
				.getResultList();
	}
	
	public Long getQuantidadesRegistroTotal() {
		return getEntityManager().createQuery("select count(c) from Cidade c", Long.class).getSingleResult();
	}

	public List<String> getTextFilterByString(String coluna, String texto) {
		return getEntityManager().createQuery("select c." + coluna + " from Cidade c where c." + coluna + " like ?1", String.class)
				.setParameter(1, "%" + texto + "%").getResultList();
	}

	public Long getQuantidadeRegistrosNotRepetidos(String coluna) {
		return getEntityManager().createQuery("select distinct count(c." + coluna + ") from Cidade c", Long.class)
				.getSingleResult();
	}

	public List<String> getNomeCidadesPorEstado(String estado) {
		return getEntityManager().createQuery("select c.nome from Cidade c where c.Uf = ?1", String.class)
				.setParameter(1, estado)
				.getResultList();
	}

	public List<Cidade> getTodasCidades() {
		return getEntityManager().createQuery("select c from Cidade c", Cidade.class)
				.getResultList();
	}

}
