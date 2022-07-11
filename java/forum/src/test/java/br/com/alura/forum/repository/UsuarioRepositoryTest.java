package br.com.alura.forum.repository;

import br.com.alura.forum.modelo.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void deveriaCarregarUmUsuarioAoBuscarPeloSeuEmail() {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail("aluno@email.com");
        Assertions.assertTrue(usuarioOptional.isPresent());
        Assertions.assertEquals("aluno@email.com", usuarioOptional.get().getEmail());
    }

    @Test
    public void naoDeveriaCarregarUmUsuarioQueNaoEstaCadastrado() {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail("qualquer@email.com");
        Assertions.assertFalse(usuarioOptional.isPresent());
    }

}