package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;

import br.com.alura.forum.modelo.Resposta;
import io.swagger.v3.oas.annotations.media.Schema;

public class RespostaDto {

	@Schema(name = "id", description = "Identificador da resposta")
	private final Long id;

	@Schema(name = "mensagem", description = "Mensagem da resposta")
	private final String mensagem;

	@Schema(name = "dataCriacao", description = "Data de criação da resposta")
	private final LocalDateTime dataCriacao;

	@Schema(name = "nomeAutor", description = "Nome do autor das resposta")
	private final String nomeAutor;
	
	public RespostaDto(Resposta resposta) {
		this.id = resposta.getId();
		this.mensagem = resposta.getMensagem();
		this.dataCriacao = resposta.getDataCriacao();
		this.nomeAutor = resposta.getAutor().getNome();
	}

	public Long getId() {
		return id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}
	
}
