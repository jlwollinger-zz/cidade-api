package br.com.cidade.service.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 
 * @author Jose Wollinger
 * Feb 20, 2018
 */
public class AbstractDAO<T> {

	public EntityManager getEntityManager() {
		return em;
	}

	@PersistenceContext(name="cidade-api-pu")
	private EntityManager em;

	public void persist(T entity) {
		getEntityManager().persist(entity);
	}

	public T save(T entity) {
		return getEntityManager().merge(entity);
	}

	public void delete(T entity) {
		getEntityManager().remove(getEntityManager().merge(entity));
	}

	public void deleteAll(String entityName) {
		getEntityManager().createQuery("delete from " + entityName)
		.executeUpdate();
	}
	
}
