/* Classe que contem os metodos de crud basicos
 * foi separada para deixar as daoEntity mais limpas
 */
package br.com.pitang.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.pitang.util.HibernateUtil;

@SuppressWarnings("unchecked")
public class DaoGeneric<E> {

	private EntityManager entityManager = HibernateUtil.getEntityManager();


	//Grava no banco de dados
	public void salvar(E entidade) {
		EntityTransaction transaction = entityManager.getTransaction(); 
		transaction.begin(); 
		entityManager.persist(entidade); 
		transaction.commit(); 
	}
	
	
	//Salva, atualiza e retorna o objeto
	public E updateMerge(E entidade) { 
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		E entidadeSalva = entityManager.merge(entidade); 
		transaction.commit();

		return entidadeSalva;
	}

	public E pesquisar(Long id, Class<E> entidade) {
		entityManager.clear();
		E e = (E) entityManager.createQuery("from " + entidade.getSimpleName() + " where id = " + id).getSingleResult();
		return e;
	}
	
	public E pesquisar(E entidade) {
		Object id = HibernateUtil.getPrimaryKey(entidade);
		E e = (E) entityManager.find(entidade.getClass(), id);
		return e;
	}
   
	//Obtem o ID do objeto e deleta do banco de dados
	public void deletarPorId(E entidade) throws Exception{
		Object id = HibernateUtil.getPrimaryKey(entidade);  
		EntityTransaction transaction = entityManager.getTransaction(); 
		transaction.begin(); 

		entityManager
				.createNativeQuery(
						"delete from " + entidade.getClass().
						getSimpleName().toLowerCase() + " where id =" + id)
				.executeUpdate(); 
		transaction.commit(); 
	}

	//Retorna a lista de objetos consultados
	public List<E> listar(Class<E> entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		List<E> lista = entityManager.
				createQuery("from " + entidade.getName())
				.getResultList(); 
		transaction.commit();

		return lista;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
