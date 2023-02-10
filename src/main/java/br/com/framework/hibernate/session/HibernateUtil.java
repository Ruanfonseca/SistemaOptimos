package br.com.framework.hibernate.session;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.faces.bean.ApplicationScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;

import br.com.framework.implementacao.crud.VariavelConexaoUtil;

/**
 * Responsavel por estabelecer a conexão com hibernate
 * 
 * Esse código é uma classe Java que se configura como um utilitário para a
 * conexão com o Hibernate. Ele contém métodos que constroem o objeto
 * SessionFactory (que representa uma fábrica de sessões com o banco de dados),
 * retornam sessões abertas ou correntes, obtêm conexões JDBC e também retornam
 * um DataSource JNDI. Além disso, a classe contém informações sobre a fonte de
 * dados JDBC, que é especificada como java:/comp/env/jdbc/datasource.
 * 
 */

@ApplicationScoped
public class HibernateUtil implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String JAVA_COMP_ENV_JDBC_DATA_SOURCE = "java:/comp/env/jdbc/datasource";

	private static SessionFactory sessionFactory = buildSessionFactory();

	/* Responsavel por ler o arquivo de configuração hibernate.cfg.xml */
	private static SessionFactory buildSessionFactory() {
		try {

			if (sessionFactory == null) {
				sessionFactory = new Configuration().configure().buildSessionFactory();
			} else {

			}
			return sessionFactory;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError("Erro ao criar a conexão SessionFactory");
		}
	}

	// retorna o session factory corrente
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session getCurrentSession() {
		return (Session) getSessionFactory().getCurrentSession();
	}

	// abrindo uma nova sessão
	public static Session openSession() {
		if (sessionFactory == null) {
			buildSessionFactory();
		}
		return (Session) sessionFactory.openSession();
	}

	// conection do sql , Obtem a conexao do provedor de conexões configurados
	public static Connection getConnectionProvider() throws SQLException {
		return ((SessionFactoryImplementor) sessionFactory).getConnectionProvider().getConnection();
	}

	/*
	 * retorna conexao no InitialContext java:/comp/env/jdbc/datasource
	 */
	public static Connection getConnection() throws Exception {
		InitialContext context = new InitialContext();
		DataSource ds = (DataSource) context.lookup(JAVA_COMP_ENV_JDBC_DATA_SOURCE);
		return ds.getConnection();
	}

	/*
	 * datasource jndi
	 */
	public DataSource getDataSourceJndi() throws NamingException {
		InitialContext context = new InitialContext();
		return (DataSource) context.lookup(VariavelConexaoUtil.JAVA_COMP_ENV_JDBC_DATA_SOURCE);

	}

}
