package br.com.framework.implementacao.crud;

import java.io.Serializable;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
//parametros de configuração , se não exitir conexão cria e faz rollback se acontecer erros
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class SimpleJdbcInsertImpl extends SimpleJdbcInsert implements Serializable{

	public SimpleJdbcInsertImpl(DataSource dataSource) {
		super(dataSource);
	}
	private static final long serialVersionUID = 1L;

}
