/* Classe responsavel por guardar os metodos de chamada a unidade de persistenciae também o gerenciador de entidades
 * foi adicionada uma função extra para obter a chave primaria da entidade que é representada por uma objeto
 */
package br.com.pitang.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class HibernateUtil {
	
	public static EntityManagerFactory factory = null;

	static {
		init();
	}
	
	private static void init() {
		try {
			if (factory == null) {
				factory = Persistence.
						createEntityManagerFactory("PERSISTENCE"); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static EntityManagerFactory getFactory() {
		return factory;
	}

	@Produces
    @RequestScoped
    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

	public static Object getPrimaryKey(Object entity) { // Retorna a primary key da entidade
		return factory.getPersistenceUnitUtil().getIdentifier(entity);
	}

}
