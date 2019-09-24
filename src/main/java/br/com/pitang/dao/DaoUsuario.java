/* Essa classe é uma herança da DaoGeneric
 * possui alguns metodos de busca mais especificos para entidade
 */
package br.com.pitang.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pitang.model.Usuario;
import br.com.pitang.util.HibernateUtil;

public class DaoUsuario<E> extends DaoGeneric<Usuario> {
	
	private static EntityManager entityManager = HibernateUtil.getEntityManager();
	
	    //lista todos os usuarios
		public List<Usuario> obterUsuarios() {
			List<Usuario> listaUsuarios = new ArrayList<>();
			Query q = entityManager.createQuery("from Usuario c");
			listaUsuarios = q.getResultList();
			return listaUsuarios;
		}
		
		//metodo para buscar um usuario por email
		public static Usuario findByEmail(String email) {
			String query = "from Usuario u WHERE u.email = :emailParam";
			Query q = entityManager.createQuery(query);
			q.setParameter("emailParam", email);
			Usuario user = null;
			try{
			user = (Usuario) q.getSingleResult();
			}
			catch (NoResultException nre) {
			//returna null caso nenhum usuario seja achado
			}
			return user;
		}
		
		//metodo para buscar um usuario por id
		public static Usuario findById(Long id) {
			String query = "from Usuario u WHERE u.id = :idParam";
			Query q = entityManager.createQuery(query);
			q.setParameter("idParam", id);
			Usuario user = null;
			try{
			user = (Usuario) q.getSingleResult();
			}
			catch (NoResultException nre) {
			//returna null caso nenhum usuario seja achado
			}
			return user;
		}

}
