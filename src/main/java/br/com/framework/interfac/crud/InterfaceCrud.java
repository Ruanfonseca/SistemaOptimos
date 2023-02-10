package br.com.framework.interfac.crud;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


//import jakarta.websocket.Session;

@Component
@Transactional
/* Classe generica */
public interface InterfaceCrud<T> extends Serializable {

	// salva dados
	void save(T obj) throws Exception;

	void persist(T obj) throws Exception;

	// salva ou atualiza
	void saveOrUpdate(T obj) throws Exception;

	// realiza a atualização dos dados
	void update(T obj) throws Exception;

	void delete(T obj) throws Exception;

	// recebe um objeto e retorna um obj em estado persistente
	T merge(T obj) throws Exception;

	// carrega a lista de dados de determinada classe
	List<T> findList(Class<T> objs) throws Exception;

	//
	Object findById(Class<T> entidade, Long id) throws Exception;

	T findporId(Class<T> entidade, Long id) throws Exception;

	List<T> findListByQueryDinamica(String s) throws Exception;

	// executa com HQL
	void executeUpdateQueryDinamica(String s) throws Exception;

	// executa com sql puro
	void executeUpdateSQLDinamica(String s) throws Exception;

	// limpa a sessão do hibernate
	void clearSession() throws Exception;

	// Retira o objeto da sessao do hibernate
	void evict(Object objs) throws Exception;

	Session getSession() throws Exception;

	List<?> getListSQLDinamica(String sql) throws Exception;

	// JDBC do spring
	JdbcTemplate getJdbcTemplate();

	SimpleJdbcTemplate getSimpleJdbcTemplate();

	SimpleJdbcInsert getSimpleJdbcInsert();

	Long totalRegistro(String table) throws Exception;

	// montagem dinamica para montagem de querys
	Query obterQuery(String query) throws Exception;
   
	
	
	//carregamento sob demanda do front
	List<T> findListByQueryDinamica(String query, int iniciaNoRegistro, int maximoResultado) throws Exception;
}
