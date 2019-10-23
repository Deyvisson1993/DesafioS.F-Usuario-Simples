package br.com.deyvisson.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import br.com.deyvisson.domain.Usuario;
import br.com.deyvisson.util.JpaUtil;

public class UsuarioDAO {

	public void salvar(Usuario entidade) {

		EntityManager em = JpaUtil.getInstance().getEm();

		try {

			em.getTransaction().begin();
			em.merge(entidade);
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	public void excluir(Usuario entidade) {

		EntityManager em = JpaUtil.getInstance().getEm();
		try {

			em.getTransaction().begin();
			em.remove(entidade);
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}

	}

	public List<Usuario> listar() {

		EntityManager em = JpaUtil.getInstance().getEm();

		em.getTransaction().begin();
		Query consulta = em.createQuery("select usuario from Usuario usuario");
		@SuppressWarnings("unchecked")
		List<Usuario> usuario = consulta.getResultList();
		em.getTransaction().commit();
		return usuario;
	}

	public Usuario consultarPorId(Long id) {
		EntityManager em = JpaUtil.getInstance().getEm();

		em.getTransaction().begin();
		Usuario usuario = em.find(Usuario.class, id);
		em.getTransaction().commit();
		return usuario;
	}

	public Usuario autenticar(String nome, String senha) {

		EntityManager em = JpaUtil.getInstance().getEm();
		Usuario usuario = null;

		try {
			usuario = (Usuario) em.createQuery("SELECT u FROM Usuario u WHERE u.nome = :nome AND u.senha = :senha")
					.setParameter("nome", nome).setParameter("senha", senha).getSingleResult();

		} catch (RuntimeException ex) {
			ex.printStackTrace();
		}
		return usuario;
	}
}