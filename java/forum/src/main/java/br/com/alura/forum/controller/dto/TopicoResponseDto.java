package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;

import br.com.alura.forum.modelo.Topico;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

public class TopicoResponseDto {

	@Schema(name = "id", description = "Identificador do tópico")
	private final Long id;

	@Schema(name = "titulo", description = "Título do tópico")
	private final String titulo;

	@Schema(name = "mensagem", description = "Mensagem do tópico")
	private final String mensagem;

	@Schema(name = "dataCriacao", description = "Data de criação do tópico")
	private final LocalDateTime dataCriacao;
	
	public TopicoResponseDto(Topico topico) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public static Page<TopicoResponseDto> converter(Page<Topico> topicos) {
		return topicos.map(TopicoResponseDto::new);
	}

}
