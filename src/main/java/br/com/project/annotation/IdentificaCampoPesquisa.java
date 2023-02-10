package br.com.project.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*@Target: Essa anotação especifica o tipo de elemento do programa que a anotação pode ser aplicada. 
 * No caso, o valor "ElementType.FIELD" significa que a anotação só pode ser aplicada a campos de uma classe.

@Retention: Essa anotação especifica a política de retenção da anotação. 
O valor "RetentionPolicy.RUNTIME" significa que a anotação será retida durante a execução da aplicação e 
pode ser recuperada usando reflexão.

@Documented: Essa anotação especifica que a anotação deve ser incluída na documentação da API gerada.
 
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface IdentificaCampoPesquisa {
	String descricaoCampo(); //descrição do campo para a tela 
	String campoConsulta(); // campo do banco
	int principal() default 10000; //posição que irá aparacer no comboBox 
}
