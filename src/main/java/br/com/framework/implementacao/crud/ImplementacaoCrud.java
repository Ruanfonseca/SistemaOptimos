package br.com.framework.implementacao.crud;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.framework.hibernate.session.HibernateUtil;
import br.com.framework.interfac.crud.InterfaceCrud;


@Component // para poder trabalhar com injeção de dependencias
@Transactional // porque faz trasação com banco
public class ImplementacaoCrud<T> implements InterfaceCrud<T> {

	private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@Autowired // injetando dependecias
	private JdbcTemplateImpl jdbcTemplate;

	@Autowired // injetando dependecias
	private SimpleJdbcTemplate simpleJdbcTemplate;

	@Autowired // injetando dependecias
	private SimpleJdbcInsertImpl simpleJdbcInsertImpl;

	@Autowired // injetando dependecias
	private SimpleJdbcTemplateImpl SimpleJdbcTemplateImpl;

	private static final long serialVersionUID = 1L;

	/**************************************************************************************/

	@Override
	public void save(T obj) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().save(obj);
		executeFlushSession();
	}

	@Override
	public void persist(T obj) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().persist(obj);
		executeFlushSession();
	}

	@Override
	public void saveOrUpdate(T obj) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().saveOrUpdate(obj);
		executeFlushSession();
	}

	@Override
	public void update(T obj) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().update(obj);
		executeFlushSession();
	}

	@Override
	public void delete(T obj) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().delete(obj);
		executeFlushSession(); // limpa o cache alem de executar instantaneamente
	}

	@Override
	public T merge(T obj) throws Exception {
		validaSessionFactory();
		obj = (T) sessionFactory.getCurrentSession().merge(obj);
		executeFlushSession();
		return null;
	}

	@Override
	public List<T> findList(Class<T> entidade) throws Exception {

		validaSessionFactory();

		// para trabalhar com textos grandes
		StringBuilder query = new StringBuilder();

		// esse simple name traz só o nome da entidade
		query.append("select distinct(entity)").append(entidade.getSimpleName()).append("entity");

		List<T> lista = sessionFactory.getCurrentSession().createQuery(query.toString()).list();

		return lista;
	}

	@Override
	public Object findById(Class<T> entidade, Long id) throws Exception {
		validaSessionFactory();
		Object obj = sessionFactory.getCurrentSession().load(getClass(), id);
		return obj;

	}

	@Override
	public T findporId(Class<T> entidade, Long id) throws Exception {
		validaSessionFactory();
		T obj = (T) sessionFactory.getCurrentSession().load(getClass(), id);
		return obj;
	}

	@Override
	public List<T> findListByQueryDinamica(String s) throws Exception {
		validaSessionFactory();

		List<T> lista = new ArrayList<T>();
		lista = sessionFactory.getCurrentSession().createQuery(s).list();

		return lista;
	}

	@Override
	public void executeUpdateQueryDinamica(String s) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().createQuery(s).executeUpdate();
		executeFlushSession();
	}

	@Override
	public void executeUpdateSQLDinamica(String s) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().createSQLQuery(s).executeUpdate();
		executeFlushSession();

	}

	@Override
	public void clearSession() throws Exception {
		sessionFactory.getCurrentSession().clear();
	}

	@Override
	public void evict(Object objs) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().evict(objs);
	}

	@Override
	public Session getSession() throws Exception {
		validaSessionFactory();
		return (Session) sessionFactory.getCurrentSession();
	}

	@Override
	public List<?> getListSQLDinamica(String sql) throws Exception {
		validaSessionFactory();
		List<?> lista = sessionFactory.getCurrentSession().createSQLQuery(sql).list();

		return lista;
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		// TODO Auto-generated method stub
		return jdbcTemplate;
	}

	@Override
	public SimpleJdbcTemplate getSimpleJdbcTemplate() {
		// TODO Auto-generated method stub
		return simpleJdbcTemplate;
	}

	@Override
	public SimpleJdbcInsert getSimpleJdbcInsert() {
		// TODO Auto-generated method stub
		return getSimpleJdbcInsert();
	}

	@Override
	public Long totalRegistro(String table) throws Exception {
		StringBuilder sql = new StringBuilder();

		sql.append("select count(1) from ").append(table);

		return jdbcTemplate.queryForLong(sql.toString());
	}

	@Override
	public Query obterQuery(String query) throws Exception {
		validaSessionFactory();
		// passa um hql e retonar um obj query preparado
		Query queryReturn = (Query) sessionFactory.getCurrentSession().createQuery(query.toString());
		return queryReturn;
	}

	/*
	 * Realiza consulta no banco de dados ,iniciando o carregamento a partir do
	 * registro passado no parametro ->iniciaNoRegistro e obtendo maximo de
	 * resultados passados em ->maximoResultado
	 */
	@Override
	public List<T> findListByQueryDinamica(String query, int iniciaNoRegistro, int maximoResultado) throws Exception {
		validaSessionFactory();
		List<T> lista = new ArrayList<T>();
		lista = sessionFactory.getCurrentSession().createQuery(query).setFirstResult(iniciaNoRegistro)
				.setMaxResults(maximoResultado).list();
		return lista;
	}

	private void validaSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = HibernateUtil.getSessionFactory();
		}
		validarTransaction();
	}

	private void validarTransaction() {
		// se a sessão na estiver ativa ,ele inicia uma
		if (!sessionFactory.getCurrentSession().getTransaction().isActive()) {
			sessionFactory.getCurrentSession().beginTransaction();
		}
	}

	private void commitProcessoAjax() {
		sessionFactory.getCurrentSession().beginTransaction().commit();
	}

	private void rollbackProcessoAjax() {
		sessionFactory.getCurrentSession().beginTransaction().rollback();
	}

	// roda instantaneamente o SQL no banco de dados
	private void executeFlushSession() {
		sessionFactory.getCurrentSession().flush();
	}

	// retorna uma lista de array de objetos genéricos
	List<Object[]> getListSQLDinamicaArray(String sql) throws Exception {
		validaSessionFactory();

		List<Object[]> lista = (List<Object[]>) sessionFactory.getCurrentSession().createSQLQuery(sql).list();

		return lista;
	}

}
