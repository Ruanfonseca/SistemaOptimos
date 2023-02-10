package br.com.project.util.all;

import org.springframework.stereotype.Component;

@Component
public abstract class BeanViewAbstract implements ActionViewPadrao {

	@Override
	public void limpaLista() throws Exception {
		

	}

	@Override
	public String save() throws Exception {
		
		return null;
	}

	@Override
	public void saveNotReturn() throws Exception {
		

	}

	@Override
	public void saveEdit() throws Exception {
		

	}

	@Override
	public void excluir() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String ativar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String novo() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String editar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setarVariaveisNulas() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void ConsultarEntidade() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void statusOperation(StatusPersistencia a) throws Exception {
		Mensagens.responseOperation(a);
	}

	
    protected void sucesso() throws Exception {
    	statusOperation(StatusPersistencia.SUCESSO);
    }
	
    protected void error() throws Exception {
    	statusOperation(StatusPersistencia.ERRO);
    }
	@Override
	public String redirecionarNewEntidade() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String redirecionarFindEntidade() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addMsg(String msg) throws Exception {
	     Mensagens.msg(msg);
	}

}
