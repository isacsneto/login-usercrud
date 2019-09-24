/* Essa classe é usada para persistir os dados de contato do usuario
 * o usuário já é inicializado quando a pagina é acessada para facilitar
 */
package br.com.pitang.managedbean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.pitang.dao.DaoTelefones;
import br.com.pitang.dao.DaoUsuario;
import br.com.pitang.model.Telefone;
import br.com.pitang.model.Usuario;
import br.com.pitang.util.JSFMessageUtil;

@ManagedBean(name = "telefoneManagedBean")
@ViewScoped
public class TelefoneManagedBean {

	private Usuario user = new Usuario();
	private Telefone telefone = new Telefone();
	private DaoUsuario<Usuario> daoUser = new DaoUsuario<Usuario>();
	private DaoTelefones<Telefone> daoTelefone = new DaoTelefones<Telefone>();
	private JSFMessageUtil message = new JSFMessageUtil();

	@PostConstruct
	public void init() {

		String coduser = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("codigouser");
		user = daoUser.pesquisar(Long.parseLong(coduser), Usuario.class);

	}
	
	public String salvar(){
		telefone.setUsuario(user);
		daoTelefone.salvar(telefone);
		telefone = new Telefone();
		user = daoUser.pesquisar(user.getId(), Usuario.class);
		message.sendInfoMessage("Telefone salvo com sucesso!");		
		return "";
	}
	
	public String removeTelefone() throws Exception{
		
		daoTelefone.deletarPorId(telefone);
		user = daoUser.pesquisar(user.getId(), Usuario.class);
		telefone = new Telefone();
		message.sendInfoMessage("Telefone removido com sucesso!");
		return "";
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public Usuario getUser() {
		return user;
	}

	public void setDaoTelefone(DaoTelefones<Telefone> daoTelefone) {
		this.daoTelefone = daoTelefone;
	}

	public DaoTelefones<Telefone> getDaoTelefone() {
		return daoTelefone;
	}

}
