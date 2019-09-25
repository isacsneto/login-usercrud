package br.com.pitang.test;

import java.util.List;

import org.junit.Test;

import br.com.pitang.dao.DaoGeneric;
import br.com.pitang.model.Telefone;
import br.com.pitang.model.Usuario;

public class JUnitTests {

	@Test
	public void testeHibernateUtil() {
		
		DaoGeneric<Usuario> daoGeneric =
				new DaoGeneric<Usuario>();// Instância o DAO genérico
		Usuario pessoa = new Usuario(); // Cria o obejto para ser salvo

		// Seta todas as propriedades do objeto
		pessoa.setId(null);
		pessoa.setNome("Isac");
		pessoa.setEmail("sefaz@pitang.com");
		pessoa.setSenha("admin10");

		daoGeneric.salvar(pessoa);// Chama o salvar para gravar no banco de dados.

	}

	@Test
	public void testeBuscar() {
		DaoGeneric<Usuario> daoGeneric = 
				new DaoGeneric<Usuario>();// Inicia o DAO
		Usuario pessoa = new Usuario();// Inicia o Objeto 
		pessoa.setId(2L);// Precisamos apenas do ID

		pessoa = daoGeneric.pesquisar(pessoa); // Envia para pesquisa

		System.out.println(pessoa); // Imprime objeto para conferir

	}

	@Test
	public void testeBuscar2() {
		DaoGeneric<Usuario> daoGeneric = new DaoGeneric<Usuario>();

		Usuario pessoa = daoGeneric.pesquisar(1L, Usuario.class);

		System.out.println(pessoa);

	}

	@Test
	public void testeUpdate() {
		DaoGeneric<Usuario> daoGeneric = new DaoGeneric<Usuario>();

		Usuario pessoa = daoGeneric.
				pesquisar(1L, Usuario.class);// Carrega Objeto para editar

		// Atualiza os atributos
		pessoa.setNome("Nome atualizado Hibernate");
		pessoa.setSenha("sd4s5d4s4d");

		pessoa = daoGeneric.updateMerge(pessoa);// Atualiza no banco de dados.

		System.out.println(pessoa);

	}

	@Test
	public void testeDelete() {
		DaoGeneric<Usuario> daoGeneric = new 
				DaoGeneric<Usuario>();// Instancia o DAO

		Usuario pessoa = daoGeneric.
				pesquisar(3L, Usuario.class);// Consulta o objeto antes de remover

		try {
			daoGeneric.deletarPorId(pessoa);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// Invoca o método de remoção do banco de dados

	}

	@Test
	public void testeConsultar() {
		DaoGeneric<Usuario> daoGeneric = 
				new DaoGeneric<Usuario>(); // Instanciar DAO

		List<Usuario> list = daoGeneric.
				listar(Usuario.class);// Invocar o método de lista passando a classe

		for (Usuario Usuario : list) {// Percorrer a lista verificando se está correto
			System.out.println(Usuario);
			System.out.println("--------------------------------------------------");
		}

	}

	@Test
	public void testeQueryList() {
		DaoGeneric<Usuario> daoGeneric = new DaoGeneric<Usuario>();
		List<Usuario> list = daoGeneric.getEntityManager()
				.createQuery(" from Usuario where nome = 'Isac'").getResultList();

		for (Usuario Usuario : list) {
			System.out.println(Usuario);
		}

	}

	@SuppressWarnings("unchecked")
	
	@Test
	public void testeQueryListMaxResult() {
		DaoGeneric<Usuario> daoGeneric = new DaoGeneric<Usuario>();
		List<Usuario> list = daoGeneric.getEntityManager().
				createQuery(" from Usuario order by id")
				.setMaxResults(5).getResultList();

		for (Usuario Usuario : list) {
			System.out.println(Usuario);
		}

	}

	@SuppressWarnings("unchecked")
	
	@Test
	public void testeQueryListParameter() {
		DaoGeneric<Usuario> daoGeneric = new DaoGeneric<Usuario>();

		List<Usuario> list = (List<Usuario>) daoGeneric.getEntityManager()
				.createQuery("from Usuario where nome = :nome")
				.setParameter("nome", "isac");

		for (Usuario Usuario : list) {
			System.out.println(Usuario);
		}
	}

	@SuppressWarnings("unchecked")
	
	
	@Test
	public void testeNameQUery1() {
		DaoGeneric<Usuario> daoGeneric = new DaoGeneric<Usuario>();

		List<Usuario> list = daoGeneric.getEntityManager().
				createNamedQuery("Usuario.todos")// Query escrita na entidade
				.getResultList();

		for (Usuario Usuario : list) {
			System.out.println(Usuario);
		}

	}

	@SuppressWarnings("unchecked")
	
	@Test
	public void testeNameQUery2() {
		DaoGeneric<Usuario> daoGeneric = new DaoGeneric<Usuario>();

		List<Usuario> list = daoGeneric.getEntityManager().
				createNamedQuery("Usuario.buscarEmail")
				.setParameter("email", "isacsneto@hotmail.com")
				.getResultList();

		for (Usuario Usuario : list) {
			System.out.println(Usuario);
		}

	}
	
	@Test
	public void testeGravaTelefone(){
		DaoGeneric daoGeneric = new DaoGeneric();
		
		Usuario pessoa =  (Usuario) daoGeneric.pesquisar(28L, Usuario.class);
		
		Telefone Telefone = new Telefone();
		
		Telefone.setTipo("Pessoal");
		Telefone.setDdd("(81)");
		Telefone.setNumero("9958-44258");
		Telefone.setUsuario(pessoa);
		
		daoGeneric.salvar(Telefone);
		
	}
	
	
	
	@Test
	public void testeConsultaTelefones(){
		DaoGeneric daoGeneric = new DaoGeneric();
		
		Usuario pessoa =  (Usuario) daoGeneric.pesquisar(24L, Usuario.class);
		
		for (Telefone fone : pessoa.getTelefoneUsers()) {
			System.out.println(fone.getNumero());
			System.out.println(fone.getDdd());
			System.out.println(fone.getTipo());
			System.out.println(fone.getUsuario().getNome());
			System.out.println("----------------------------------");
		}
		
	}

}