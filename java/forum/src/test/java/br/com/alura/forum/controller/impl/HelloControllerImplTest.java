package br.com.alura.forum.controller.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class HelloControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ALUNO")
    public void deveriaRetornarTextoHelloWorldQuandoChamadoOPathInicial() throws Exception {
        URI uri = new URI("/");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN + ";charset=UTF-8"))
                .andExpect(content().string("Hello World!"));
    }

    @Test
    public void deveriaRetornarStatus403QuandoTentarAcessarSemEstarAutenticadoNoSistema() throws Exception {
        URI uri = new URI("/");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri);

        mockMvc.perform(requestBuilder).andExpect(status().isForbidden());
    }

}