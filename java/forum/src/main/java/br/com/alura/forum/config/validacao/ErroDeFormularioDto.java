package br.com.alura.forum.config.validacao;

import io.swagger.v3.oas.annotations.media.Schema;

public class ErroDeFormularioDto {

	@Schema(name = "campo", description = "Nome do campo com o erro")
	private final String campo;

	@Schema(name = "erro", description = "Mensagem com a descrição do erro")
	private final String erro;
	
	public ErroDeFormularioDto(String campo, String erro) {
		this.campo = campo;
		this.erro = erro;
	}

	public String getCampo() {
		return campo;
	}

	public String getErro() {
		return erro;
	}
	
	

}
