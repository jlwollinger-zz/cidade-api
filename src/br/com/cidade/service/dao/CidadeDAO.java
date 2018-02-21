package br.com.cidade.service.dao;

import java.util.List;

import javax.ejb.Stateless;

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
	
   
}
