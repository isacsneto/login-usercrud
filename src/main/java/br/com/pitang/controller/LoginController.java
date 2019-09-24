/* Controller responsavel por receber as chamadas de login e logoff
 * e pôr o usuario na sessão, assim como invalidar a sessão ao logout
 */
package br.com.pitang.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.pitang.dao.DaoUsuario;
import br.com.pitang.managedbean.UsuarioManagedBean;
import br.com.pitang.model.Usuario;

@RequestScoped
@ManagedBean(name = "loginController")
public class LoginController {

    @ManagedProperty(value = UsuarioController.INJECTION_NAME)
    private UsuarioController usuarioController;

    private String email;
    private String password;

    public void setUsuarioController(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    public String getLogin() {
        return email;
    }

    public void setLogin(String login) {
        this.email = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	private Usuario isValidLogin(String email, String password) {
         Usuario user = null;
         
        if(isEmail(email)){
            user = new DaoUsuario().findByEmail(email);
        }
        
        if (user == null || !password.equals(user.getSenha())) {
            return null;
        }
        return user;
    }
    
    private boolean isEmail(String value){
        return value.contains("@");
    }

    public String entrar() {
    	
    	if (DaoUsuario.findByEmail("admin@admin.com") == null) {
    		UsuarioManagedBean criar = new UsuarioManagedBean();
        	criar.userpadrao();
    	}
    	
        Usuario user = isValidLogin(email, password);

        if (user != null) {
            usuarioController.setUser(user);
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            request.getSession().setAttribute("user", user);
            return "primefaces.xhtml";
        }
        FacesContext.getCurrentInstance().addMessage(null,
    	new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email e/ou senha inválido!", ""));
        return null;
    }
    
    public String logOff() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.invalidate();
        return "/login.xhtml";
    }

}
