package br.com.project.filter;

import java.io.IOException;
import java.io.Serializable;

import javax.activation.DataSource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.filter.DelegatingFilterProxy;

import br.com.framework.hibernate.session.HibernateUtil;
import br.com.framework.utils.UtilFramework;
import br.com.project.listener.ContextLoaderListenerOptimaUtils;
import br.com.project.model.classes.Entidade;

/*
 * Esta classe é um filtro para o Hibernate (biblioteca Java para persistência de dados) e 
 * usa o Spring Framework (framework para aplicações Java) para gerenciar transações JDBC. O filtro 
 * é responsável por abrir e fechar a sessão do Hibernate, gerenciar o commit ou o rollback das transações,
 *  definir a codificação UTF-8 para a requisição e resposta e armazenar informações sobre o usuário logado na 
 * sessão. O filtro é inicializado apenas uma vez, no momento de inicialização da aplicação.
 * 
 * 
 * */

@WebFilter(filterName = "conexaoFilter")
public class FilterOpenSessionView extends DelegatingFilterProxy implements Serializable {

	private static final long serialVersionUID = 1L;

	private static SessionFactory sf;

	// executa apenas uma vez
	// executado quando a aplicação está sendo iniciada
	@Override
	protected void initFilterBean() throws ServletException {
		sf = HibernateUtil.getSessionFactory();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain Chain)
			throws ServletException, IOException {
		// JDBC spring
		DataSource springDataSource = (DataSource) ContextLoaderListenerOptimaUtils.getBean("springDataSource");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		PlatformTransactionManager transactionManager = new DataSourceTransactionManager(
				(javax.sql.DataSource) springDataSource);
		TransactionStatus status = transactionManager.getTransaction(def);

		try {
			// DEFINE CODIFICAÇÃO
			request.setCharacterEncoding("UTF-8");

			// captura usuário que faz a operação
			HttpServletRequest request2 = (HttpServletRequest) request;
			HttpSession sessao = request2.getSession();
			Entidade userLogadoSessao = (Entidade) sessao.getAttribute("userLogadoSessao");
			if (userLogadoSessao != null) {
				UtilFramework.getThreadLocal().set(userLogadoSessao.getEnt_codigo());
			}

			sf.getCurrentSession().beginTransaction();
			// antes de executar a ação (request)
			Chain.doFilter(request, response);
			// apos executação da ação (response)
			transactionManager.commit(status);

			if (sf.getCurrentSession().getTransaction().isActive()) {
				sf.getCurrentSession().flush();
				sf.getCurrentSession().getTransaction().commit();
			}
			if (sf.getCurrentSession().isOpen()) {
				sf.getCurrentSession().close();
			}
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
		} catch (Exception e) {

			transactionManager.rollback(status);
			e.printStackTrace();
			if (sf.getCurrentSession().getTransaction().isActive()) {
				sf.getCurrentSession().getTransaction().rollback();
			}
			if (sf.getCurrentSession().isOpen())
				sf.getCurrentSession().close();
		} finally {
			if (sf.getCurrentSession().isOpen()) {
				if (sf.getCurrentSession().beginTransaction().isActive()) {
					sf.getCurrentSession().flush();
					sf.getCurrentSession().clear();
				}
				if (sf.getCurrentSession().isOpen()) {
					sf.getCurrentSession().close();
				}

			}
		}

	}

}
