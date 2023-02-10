package br.com.project.acessos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/*
 * A classe "Permissao" é um enum que representa diferentes tipos de permissões
 *  que um usuário pode ter dentro de uma aplicação. Cada permissão é representada por uma constante 
 *  (ex: ADMIN, USER, CADASTRO_ACESSAR, etc.) e possui um valor e uma descrição. 
 *  Além disso, a classe tem métodos para obter e configurar esses valores e descrições,
 *   e também tem uma implementação do método "toString" que retorna o valor da permissão. 
 *   A classe também tem um método estático chamado "getListPermissao" que cria uma lista com todas as 
 * permissões e as ordena de acordo com o seu índice de ordem.
 * */

public enum Permissao {
	ADMIN("ADMIN", "Administrador"), USER("USER", "Usuario Padrao"),
	CADASTRO_ACESSAR("CADASTRO_ACESSAR", "Cadastro - acessar"),
	FINANCEIRO_ACESSAR("FINANCEIRO_ACESSAR", "Financeiro - Acessar"),
	MENSAGEM_ACESSAR("MENSAGEM_ACESSAR", "Mensagem recebida - Acessar"),

	BAIRRO_ACESSAR("BAIRRO_ACESSAR", "Bairro - Acessar"), BAIRRO_NOVO("BAIRRO_NOVO", "Bairro - Novo"),
	BAIRRO_EDITAR("BAIRRO_EDITAR", "Bairro - Editar"), BAIRRO_EXCLUIR("BAIRRO_EXCLUIR", "Bairro - Excluir");

	private String valor = "";
	private String descricao = "";

	private Permissao(String name, String descricao) {
		this.valor = name;
		this.descricao = descricao;
	}

	private Permissao() {

	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return getValor();
	}

	// cria uma lista e faz comparações
	public static List<Permissao> getListPermissao() {
		List<Permissao> permissoes = new ArrayList<Permissao>();
		for (Permissao permissao : Permissao.values()) {
			permissoes.add(permissao);
		}
		Collections.sort(permissoes, new Comparator<Permissao>() {

			@Override
			public int compare(Permissao o1, Permissao o2) {

				return new Integer(o1.ordinal()).compareTo(new Integer(o2.ordinal()));
			}

		});
		return permissoes;
	}

}
