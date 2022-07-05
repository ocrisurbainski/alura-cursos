package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.modelo.StatusTopico;
import br.com.alura.forum.modelo.Topico;
import io.swagger.v3.oas.annotations.media.Schema;

public class DetalhesTopicoResponseDto {

	@Schema(name = "id", description = "Identificador do tópico")
	private final Long id;

	@Schema(name = "titulo", description = "Título do tópico")
	private final String titulo;

	@Schema(name = "mensagem", description = "Mensagem do tópico")
	private final String mensagem;

	@Schema(name = "dataCriacao", description = "Data de criação do tópico")
	private final LocalDateTime dataCriacao;

	@Schema(name = "dataCriacao", description = "Data de criação do tópico")
	private final String nomeAutor;

	@Schema(name = "status", description = "Status do tópico")
	private final StatusTopico status;

	@Schema(name = "respostas", description = "Respostas do tópico")
	private final List<RespostaDto> respostas;
	
	public DetalhesTopicoResponseDto(Topico topico) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
		this.nomeAutor = topico.getAutor().getNome();
		this.status = topico.getStatus();
		this.respostas = new ArrayList<>();
		this.respostas.addAll(topico.getRespostas().stream().map(RespostaDto::new).collect(Collectors.toList()));
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

	public String getNomeAutor() {
		return nomeAutor;
	}

	public StatusTopico getStatus() {
		return status;
	}

	public List<RespostaDto> getRespostas() {
		return respostas;
	}
	
}
