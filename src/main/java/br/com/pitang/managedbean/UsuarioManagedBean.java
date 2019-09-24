/*
 * Códigos maiores ficam comentados por partes, só ir descendo =D
 */
package br.com.pitang.managedbean;

import br.com.pitang.model.Usuario;
import br.com.pitang.util.JSFMessageUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.pitang.dao.DaoUsuario;

@ManagedBean(name = "usuarioManagedBean")
@ViewScoped
public class UsuarioManagedBean implements Serializable {

	/**
	 * @author isac_
	 */
	
	private static final long serialVersionUID = 1L;
	private Usuario usuario = new Usuario();
	private List<Usuario> list = new ArrayList<Usuario>();
	private DaoUsuario<Usuario> daoGeneric = new DaoUsuario<Usuario>();
	private JSFMessageUtil message = new JSFMessageUtil();
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String novo() {
		usuario = new Usuario();
		return "";
	}
	
	//criar usuario admin
	public Usuario userpadrao() {
    	Usuario useradmin = new Usuario();
    	useradmin.setId(null);
    	useradmin.setEmail("admin@admin.com");
    	useradmin.setNome("Admin");
    	useradmin.setSenha("admin123");
    	daoGeneric.salvar(useradmin);
        return useradmin;    	
	}

	public String salvar() {
		
		//patterns para o email, para a senha e para o nome(varia de lugar pra lugar)
		String patternemail = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		String patternsenha = "(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,15}";
		String patternname = "^[a-zA-Z\\s]+";
		
		String email1 = usuario.getEmail();
		Long id1 = usuario.getId();
		
		//verifica se o id já está cadastrado
		if (DaoUsuario.findById(id1) != null){ 
            message.sendErrorMessage("Usuário não pode ser salvo com ID existente, clique em novo.");
                return "id-invalido";
        //verificar se o nome é valido(se tem caracteres especiais, números, etc)
        } else if (!usuario.getNome().matches(patternname)) {
            message.sendErrorMessage("Verifique seu nome.");
				return "nome-invalido";
		//verifica se o email ja esta cadastrado ou é invalido
        } else if(!(DaoUsuario.findByEmail(email1) == null) || !email1.matches(patternemail)) {
            message.sendErrorMessage("Esse email é inválido ou já está cadastrado.");
				return "email-existe";
		//verifica senha invalida
        } else if (!usuario.getSenha().matches(patternsenha)) {
            message.sendErrorMessage("A senha deve ter ao menos 6 caracteres sendo números e letras!");
				return "senha-invalida";
		//chama o metodo para persistir o usuario no banco e adiciona o usuario a lista.
        } else {
            daoGeneric.salvar(usuario);
				list.remove(usuario);
				list.add(usuario);
				message.sendInfoMessage("Salvo com sucesso!");
				return "usuario-salvo";
        }
   
}
	
	public String editar() {
		
		//verifica se existe id no formulario
		if (usuario.getId() == null) {
			message.sendErrorMessage("Sem ID para alterar o usuário, clique no ícone de editar na tabela.");
			return "missing-id";
			
		//verifica se o usuario está trocando de email para um email que já existe.
		} else {
			
			String email1 = usuario.getEmail();
			String email2 = DaoUsuario.findById(usuario.getId()).getEmail();
			
			if (!email1.equals(email2) && DaoUsuario.findByEmail(email1) != null) {
			message.sendErrorMessage("Esse e-mail já está em uso!");
			return "email-existe";
			
		//chama o metodo de edição passando o usuario como parametro
		} else {	
			daoGeneric.updateMerge(usuario);
			message.sendInfoMessage("Editado com sucesso!");
			return "usuario-editado";
		}
			
	  }
		
	}
	
	public String remover() {

		//realiza uma busca pelo id e chama o metodo para remover o usuario
		try {
			daoGeneric.deletarPorId(usuario);
			list.remove(usuario);
			usuario = new Usuario();
			message.sendInfoMessage("Removido com sucesso!");

			//verifica se não existe violação de constraint
		} catch (Exception e) {
			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				message.sendErrorMessage("Existem telefones para o usuario!");
			}else {
				e.printStackTrace();
			}
		}

		return "";
	}
	
	//chama o metodo que lista todos os usuarios
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Usuario> obterUsuario() {
		DaoUsuario usuarioDAO = new DaoUsuario();
		return usuarioDAO.obterUsuarios();
	}

}
