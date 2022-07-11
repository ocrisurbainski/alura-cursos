package br.com.alura.forum.controller.impl;

import br.com.alura.forum.controller.form.LoginFormRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AutenticacaoControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void deveriaDevolverStatus400CasoDadosDeAutenticacaoEstejamErrados() throws Exception {
        URI uri = new URI("/public/auth");

        LoginFormRequestDto dto = new LoginFormRequestDto();
        dto.setEmail("invalido@email.com");
        dto.setSenha("123456");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(uri)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
    }

    @Test
    public void deveriaDevolverTokenDeAutenticacaoParaAluno() throws Exception {
        URI uri = new URI("/public/auth");

        LoginFormRequestDto dto = new LoginFormRequestDto();
        dto.setEmail("aluno@email.com");
        dto.setSenha("123456");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(uri)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token", not(empty())))
                .andExpect(jsonPath("$.type", is("Bearer")));
    }

    @Test
    public void deveriaDevolverTokenDeAutenticacaoParaModerador() throws Exception {
        URI uri = new URI("/public/auth");

        LoginFormRequestDto dto = new LoginFormRequestDto();
        dto.setEmail("moderador@email.com");
        dto.setSenha("123456");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(uri)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token", not(empty())))
                .andExpect(jsonPath("$.type", is("Bearer")));
    }

}