package br.com.alura.forum.repository;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.modelo.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TopicoRepositoryTest {

    @Autowired
    private TopicoRepository topicoRepository;

    @Test
    public void deveriaCarregarUmTopicoAoBuscarPeloSeuCurso() {
        Page<Topico> page = topicoRepository.findByCursoNome("Spring Boot", PageRequest.of(0, 10));
        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
        Assertions.assertEquals(1, page.getTotalPages());
        Assertions.assertEquals(2, page.getTotalElements());
    }

    @Test
    public void naoDeveriaCarregarUmTopicoQueNaoEstaCadastrado() {
        Page<Topico> page = topicoRepository.findByCursoNome("Spring Security", PageRequest.of(0, 10));
        Assertions.assertNotNull(page);
        Assertions.assertTrue(page.isEmpty());
    }

}