package br.com.alura.forum.controller.impl;

import br.com.alura.forum.controller.form.AtualizacaoTopicoFormRequestDto;
import br.com.alura.forum.controller.form.CadastroTopicoFormRequestDto;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.StatusTopico;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import br.com.alura.forum.repository.UsuarioRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TopicosControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void deveriaDevolverListaDeTopicosComItens() throws Exception {
        URI uri = new URI("/public/topicos");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.totalElements", is(3)))
                .andExpect(jsonPath("$.content", hasSize(3)));
    }

    @Test
    public void deveriaDevolverListaDeTopicosComItensQuandoPesquisadoPorNomeDoCurso() throws Exception {
        URI uri = new URI("/public/topicos");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).queryParam("nomeCurso", "Spring Boot");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    public void deveriaDevolverListaDeTopicoVaziaQuandoPesquisadoPorNomeDoCursoQueNaoExista() throws Exception {
        URI uri = new URI("/public/topicos");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).queryParam("nomeCurso", "Qualquer Curso");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.totalElements", is(0)))
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    public void deveriaDevolverUmTopicoQuandoPesquisadoPorIdentificador() throws Exception {
        URI uri = new URI("/public/topicos/1");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.titulo", is("Dúvida")))
                .andExpect(jsonPath("$.mensagem", is("Erro ao criar projeto")))
                .andExpect(jsonPath("$.dataCriacao", is("2019-05-05T18:00:00")));
    }

    @Test
    public void deveriaDevolverUmStatus404QuandoPesquisadoPorIdentificadorQueNaoExisteUmTopicoNoBancoDeDados() throws Exception {
        URI uri = new URI("/public/topicos/99999");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "MODERADOR")
    public void deveriaDevolverUmStatus404AoRemoverUmTopicoQuandoTentarRemoverUmTopicoPorIdentificadorQueNaoExisteUmTopicoNoBancoDeDados() throws Exception {
        URI uri = new URI("/topicos/99999");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(uri);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ALUNO")
    public void deveriaDevolverUmStatus403AoRemoverUmTopicoQuandoUmUsuarioComRoleDeAlunoTentarRemoverUmTopicoPorIdentificador() throws Exception {
        URI uri = new URI("/topicos/99999");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(uri);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "MODERADOR")
    public void deveriaDevolverStatus200AoRemoverUmTopicoPeloSeuIdentificador() throws Exception {
        Curso curso = cursoRepository.findByNome("Spring Boot");

        Topico topico = new Topico();
        topico.setCurso(curso);
        topico.setDataCriacao(LocalDateTime.now());
        topico.setTitulo("Topico Teste");
        topico.setMensagem("Topico Teste");
        topico.setAutor(usuarioRepository.findAll().get(0));
        topico.setStatus(StatusTopico.NAO_RESPONDIDO);

        topico = topicoRepository.save(topico);

        URI uri = new URI("/topicos/" + topico.getId());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(uri);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ALUNO")
    public void deveriaDevolverStatus201AoTentarCadastrarUmTopico() throws Exception {
        CadastroTopicoFormRequestDto dto = new CadastroTopicoFormRequestDto();
        dto.setMensagem("Mensagem de Teste");
        dto.setNomeCurso("Spring Boot");
        dto.setTitulo("Titulo");

        URI uri = new URI("/topicos");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(uri)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, not(emptyString())))
                .andExpect(jsonPath("$.id", not(empty())))
                .andExpect(jsonPath("$.titulo", is("Titulo")))
                .andExpect(jsonPath("$.mensagem", is("Mensagem de Teste")))
                .andExpect(jsonPath("$.dataCriacao", not(empty())))
                .andReturn();

        if (HttpStatus.CREATED.value() == result.getResponse().getStatus()) {
            TypeReference<HashMap<String, Object>> typeReference = new TypeReference<HashMap<String, Object>>() {
            };
            Map<String, Object> dados = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
            topicoRepository.deleteById(((Integer) dados.get("id")).longValue());
        }
    }

    @Test
    @WithMockUser(roles = "ALUNO")
    public void deveriaDevolverStatus400AoTentarCadastrarUmTopicoQuandoTituloDoTopicoEstaNull() throws Exception {
        CadastroTopicoFormRequestDto dto = new CadastroTopicoFormRequestDto();
        dto.setMensagem("Mensagem de Teste");
        dto.setNomeCurso("Spring Boot");
        dto.setTitulo(null);

        URI uri = new URI("/topicos");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(uri)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].campo", is("titulo")))
                .andExpect(jsonPath("$[0].erro", is("Valor não pode ser vazio.")))
                .andExpect(jsonPath("$[1].campo", is("titulo")))
                .andExpect(jsonPath("$[1].erro", is("Valor não pode ser vazio.")));
    }

    @Test
    @WithMockUser(roles = "ALUNO")
    public void deveriaDevolverStatus400AoTentarCadastrarUmTopicoQuandoTituloDoTopicoEstaVazio() throws Exception {
        CadastroTopicoFormRequestDto dto = new CadastroTopicoFormRequestDto();
        dto.setMensagem("Mensagem de Teste");
        dto.setNomeCurso("Spring Boot");
        dto.setTitulo("");

        URI uri = new URI("/topicos");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(uri)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);

        LinkedHashMap<String, String> retornoEsperado1 = new LinkedHashMap<String, String>() {{
            put("campo", "titulo");
            put("erro", "Valor deve ter tamanho entre 5 e 2147483647.");
        }};

        LinkedHashMap<String, String> retornoEsperado2 = new LinkedHashMap<String, String>() {{
            put("campo", "titulo");
            put("erro", "Valor não pode ser vazio.");
        }};

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$", hasItems(retornoEsperado1, retornoEsperado2)));
    }

    @Test
    @WithMockUser(roles = "ALUNO")
    public void deveriaDevolverStatus400AoTentarCadastrarUmTopicoQuandoTituloDoTopicoNaoEstaVazioMasTemMenosDeCincoCaracteres() throws Exception {
        CadastroTopicoFormRequestDto dto = new CadastroTopicoFormRequestDto();
        dto.setMensagem("Mensagem de Teste");
        dto.setNomeCurso("Spring Boot");
        dto.setTitulo("ABC");

        URI uri = new URI("/topicos");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(uri)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].campo", is("titulo")))
                .andExpect(jsonPath("$[0].erro", is("Valor deve ter tamanho entre 5 e 2147483647.")));
    }

    @Test
    @WithMockUser(roles = "ALUNO")
    public void deveriaDevolverStatus400AoTentarCadastrarUmTopicoQuandoMensagemDoTopicoEstaNull() throws Exception {
        CadastroTopicoFormRequestDto dto = new CadastroTopicoFormRequestDto();
        dto.setMensagem(null);
        dto.setNomeCurso("Spring Boot");
        dto.setTitulo("Titulo");

        URI uri = new URI("/topicos");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(uri)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].campo", is("mensagem")))
                .andExpect(jsonPath("$[0].erro", is("Valor não pode ser vazio.")))
                .andExpect(jsonPath("$[1].campo", is("mensagem")))
                .andExpect(jsonPath("$[1].erro", is("Valor não pode ser vazio.")));
    }

    @Test
    @WithMockUser(roles = "ALUNO")
    public void deveriaDevolverStatus400AoTentarCadastrarUmTopicoQuandoMensagemDoTopicoEstaVazio() throws Exception {
        CadastroTopicoFormRequestDto dto = new CadastroTopicoFormRequestDto();
        dto.setMensagem("");
        dto.setNomeCurso("Spring Boot");
        dto.setTitulo("Titulo");

        URI uri = new URI("/topicos");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(uri)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);

        LinkedHashMap<String, String> retornoEsperado1 = new LinkedHashMap<String, String>() {{
            put("campo", "mensagem");
            put("erro", "Valor não pode ser vazio.");
        }};

        LinkedHashMap<String, String> retornoEsperado2 = new LinkedHashMap<String, String>() {{
            put("campo", "mensagem");
            put("erro", "Valor deve ter tamanho entre 10 e 2147483647.");
        }};

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$", hasItems(retornoEsperado1, retornoEsperado2)));
    }

    @Test
    @WithMockUser(roles = "ALUNO")
    public void deveriaDevolverStatus400AoTentarCadastrarUmTopicoQuandoMensagemDoTopicoNaoEstaVazioMasTemMenosDeDezCaracteres() throws Exception {
        CadastroTopicoFormRequestDto dto = new CadastroTopicoFormRequestDto();
        dto.setMensagem("Mensagem");
        dto.setNomeCurso("Spring Boot");
        dto.setTitulo("Titulo");

        URI uri = new URI("/topicos");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(uri)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);

        LinkedHashMap<String, String> retornoEsperado1 = new LinkedHashMap<String, String>() {{
            put("campo", "mensagem");
            put("erro", "Valor deve ter tamanho entre 10 e 2147483647.");
        }};

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$", hasItems(retornoEsperado1)));
    }

    @Test
    @WithMockUser(roles = "ALUNO")
    public void deveriaDevolverStatus400AoTentarCadastrarUmTopicoQuandoNomeDoCursoDoTopicoEstaNull() throws Exception {
        CadastroTopicoFormRequestDto dto = new CadastroTopicoFormRequestDto();
        dto.setMensagem("Mensagem de Teste");
        dto.setNomeCurso(null);
        dto.setTitulo("Titulo");

        URI uri = new URI("/topicos");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(uri)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].campo", is("nomeCurso")))
                .andExpect(jsonPath("$[0].erro", is("Valor não pode ser vazio.")))
                .andExpect(jsonPath("$[1].campo", is("nomeCurso")))
                .andExpect(jsonPath("$[1].erro", is("Valor não pode ser vazio.")));
    }

    @Test
    @WithMockUser(roles = "ALUNO")
    public void deveriaDevolverStatus400AoTentarCadastrarUmTopicoQuandoNomeDoCursoDoTopicoEstaVazio() throws Exception {
        CadastroTopicoFormRequestDto dto = new CadastroTopicoFormRequestDto();
        dto.setMensagem("Mensagem de Teste");
        dto.setNomeCurso("");
        dto.setTitulo("Titulo");

        URI uri = new URI("/topicos");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(uri)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].campo", is("nomeCurso")))
                .andExpect(jsonPath("$[0].erro", is("Valor não pode ser vazio.")));
    }

    @Test
    @WithMockUser(roles = "ALUNO")
    public void deveriaDevolverStatus400AoTentarAtualizarUmTopicoQuandoTituloDoTopicoEstaNull() throws Exception {
        AtualizacaoTopicoFormRequestDto dto = new AtualizacaoTopicoFormRequestDto();
        dto.setMensagem("Mensagem de Teste");
        dto.setTitulo(null);

        URI uri = new URI("/topicos/1");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(uri)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].campo", is("titulo")))
                .andExpect(jsonPath("$[0].erro", is("Valor não pode ser vazio.")))
                .andExpect(jsonPath("$[1].campo", is("titulo")))
                .andExpect(jsonPath("$[1].erro", is("Valor não pode ser vazio.")));
    }

    @Test
    @WithMockUser(roles = "ALUNO")
    public void deveriaDevolverStatus400AoTentarAtualizarUmTopicoQuandoTituloDoTopicoEstaVazio() throws Exception {
        AtualizacaoTopicoFormRequestDto dto = new AtualizacaoTopicoFormRequestDto();
        dto.setMensagem("Mensagem de Teste");
        dto.setTitulo("");

        URI uri = new URI("/topicos/1");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(uri)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);

        LinkedHashMap<String, String> retornoEsperado1 = new LinkedHashMap<String, String>() {{
            put("campo", "titulo");
            put("erro", "Valor deve ter tamanho entre 5 e 2147483647.");
        }};

        LinkedHashMap<String, String> retornoEsperado2 = new LinkedHashMap<String, String>() {{
            put("campo", "titulo");
            put("erro", "Valor não pode ser vazio.");
        }};

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$", hasItems(retornoEsperado1, retornoEsperado2)));
    }

    @Test
    @WithMockUser(roles = "ALUNO")
    public void deveriaDevolverStatus400AoTentarAtualizarUmTopicoQuandoTituloDoTopicoNaoEstaVazioMasTemMenosDeCincoCaracteres() throws Exception {
        AtualizacaoTopicoFormRequestDto dto = new AtualizacaoTopicoFormRequestDto();
        dto.setMensagem("Mensagem de Teste");
        dto.setTitulo("ABC");

        URI uri = new URI("/topicos/1");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(uri)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].campo", is("titulo")))
                .andExpect(jsonPath("$[0].erro", is("Valor deve ter tamanho entre 5 e 2147483647.")));
    }

    @Test
    @WithMockUser(roles = "ALUNO")
    public void deveriaDevolverStatus400AoTentarAtualizarUmTopicoQuandoMensagemDoTopicoEstaNull() throws Exception {
        AtualizacaoTopicoFormRequestDto dto = new AtualizacaoTopicoFormRequestDto();
        dto.setMensagem(null);
        dto.setTitulo("Titulo");

        URI uri = new URI("/topicos/1");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(uri)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].campo", is("mensagem")))
                .andExpect(jsonPath("$[0].erro", is("Valor não pode ser vazio.")))
                .andExpect(jsonPath("$[1].campo", is("mensagem")))
                .andExpect(jsonPath("$[1].erro", is("Valor não pode ser vazio.")));
    }

    @Test
    @WithMockUser(roles = "ALUNO")
    public void deveriaDevolverStatus400AoTentarAtualizarUmTopicoQuandoMensagemDoTopicoEstaVazio() throws Exception {
        AtualizacaoTopicoFormRequestDto dto = new AtualizacaoTopicoFormRequestDto();
        dto.setMensagem("");
        dto.setTitulo("Titulo");

        URI uri = new URI("/topicos/1");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(uri)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);

        LinkedHashMap<String, String> retornoEsperado1 = new LinkedHashMap<String, String>() {{
            put("campo", "mensagem");
            put("erro", "Valor não pode ser vazio.");
        }};

        LinkedHashMap<String, String> retornoEsperado2 = new LinkedHashMap<String, String>() {{
            put("campo", "mensagem");
            put("erro", "Valor deve ter tamanho entre 10 e 2147483647.");
        }};

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$", hasItems(retornoEsperado1, retornoEsperado2)));
    }

    @Test
    @WithMockUser(roles = "ALUNO")
    public void deveriaDevolverStatus400AoTentarAtualizarUmTopicoQuandoMensagemDoTopicoNaoEstaVazioMasTemMenosDeDezCaracteres() throws Exception {
        AtualizacaoTopicoFormRequestDto dto = new AtualizacaoTopicoFormRequestDto();
        dto.setMensagem("Mensagem");
        dto.setTitulo("Titulo");

        URI uri = new URI("/topicos/1");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(uri)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);

        LinkedHashMap<String, String> retornoEsperado1 = new LinkedHashMap<String, String>() {{
            put("campo", "mensagem");
            put("erro", "Valor deve ter tamanho entre 10 e 2147483647.");
        }};

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$", hasItems(retornoEsperado1)));
    }

    @Test
    @WithMockUser(roles = "ALUNO")
    public void deveriaDevolverStatus404AoTentarAtualizarUmTopicoQuandoIdentificadorNaoExistirNoBancoDeDados() throws Exception {
        AtualizacaoTopicoFormRequestDto dto = new AtualizacaoTopicoFormRequestDto();
        dto.setMensagem("Mensagem de Teste");
        dto.setTitulo("Titulo");

        URI uri = new URI("/topicos/9999");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(uri)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}