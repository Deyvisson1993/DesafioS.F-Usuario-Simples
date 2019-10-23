package br.com.deyvisson.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.deyvisson.dao.UsuarioDAO;
import br.com.deyvisson.domain.Telefone;
import br.com.deyvisson.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable {

	private Usuario usuario = new Usuario();
	private Telefone telefone = new Telefone();
	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	List<Usuario> usuarios;
	List<Telefone> telefones;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public UsuarioBean() {

		this.usuario = new Usuario();
		this.usuario.setTelefone(new Telefone());
		this.usuarioDAO = new UsuarioDAO();
		this.usuarios = this.usuarioDAO.listar();

	}

	public void lista() {

		try {

			this.usuarios = this.usuarioDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Não foi possivil listar os dados do Usuario");
			erro.printStackTrace();
		}
	}

	public void novo() {

		try {

			this.usuario = new Usuario();
			this.usuario.setTelefone(new Telefone());

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Não foi possivil inicializar o usuario");
			erro.printStackTrace();
		}
	}

	public void salvar() {

		try {

			this.usuarioDAO.salvar(usuario);
			novo();
			this.usuarios = this.usuarioDAO.listar();

			Messages.addGlobalInfo("Usuario Cadastrado com Sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Nao foi Possivel Salvar");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {

		try {

			this.usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioEditar");
			this.usuarios = this.usuarioDAO.listar();


		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar Editar um Usuario");
			erro.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {

		try {
			this.usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioExcluir");

			this.usuarioDAO.excluir(usuario);
			this.usuarios = this.usuarioDAO.listar();

			Messages.addGlobalInfo("Usuario excluido com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar Excluir um Usuario");
			erro.printStackTrace();
		}

	}

	public String logar() {

		this.usuario = this.usuarioDAO.autenticar(this.usuario.getNome(), this.usuario.getSenha());

		if (this.usuario == null) {
			this.usuario = new Usuario();
		     FacesContext.getCurrentInstance().addMessage(
		             null,
		             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário e/ou Senha Incorretos!","Tente Novamente"));
		          return null;
		} else {

			return "/pages/usuario?faces-redirect=true";
		}
	}

	public String novoCadastro() {
		
		novo();
		return "/pages/cadastrar?faces-redirect=true";
		
	}
	
	public String salvarPrimeiroCadastro() {

		this.usuarioDAO.salvar(usuario);
		novo();

		return "/pages/login?faces-redirect=true";
	}
}