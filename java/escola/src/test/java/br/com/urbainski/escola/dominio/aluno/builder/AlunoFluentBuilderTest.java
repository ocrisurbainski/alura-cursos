package br.com.urbainski.escola.dominio.aluno.builder;

import br.com.urbainski.escola.dominio.aluno.Telefone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AlunoFluentBuilderTest {

    @Test
    public void test_criarAlunoAndTelefonesComIds() {
        var aluno = AlunoBuilder.withId(10)
                .withNome("Fulano")
                .withCpf("40566030063")
                .withEmail("meuemail@example.com")
                .withTelefone(10, "49", "999999999")
                .build();

        Assertions.assertNotNull(aluno);
        Assertions.assertEquals(10, aluno.getId());
        Assertions.assertEquals("Fulano", aluno.getNome());
        Assertions.assertEquals("40566030063", aluno.getCpf().getNumero());
        Assertions.assertEquals("meuemail@example.com", aluno.getEmail().getEndereco());
        Assertions.assertNotNull(aluno.getTelelefones());
        Assertions.assertEquals(1, aluno.getTelelefones().size());
        Assertions.assertEquals(10, aluno.getTelelefones().get(0).getId());
        Assertions.assertEquals(10, aluno.getTelelefones().get(0).getIdAluno());
        Assertions.assertEquals("49", aluno.getTelelefones().get(0).getDdd());
        Assertions.assertEquals("999999999", aluno.getTelelefones().get(0).getNumero());
    }

    @Test
    public void test_criarAlunoWithListTelefones() {
        var telefone = new Telefone("99", "999999999");

        var aluno = AlunoBuilder.withId(10)
                .withNome("Fulano")
                .withCpf("40566030063")
                .withEmail("meuemail@example.com")
                .withTelefones(List.of(telefone))
                .build();

        Assertions.assertNotNull(aluno);
        Assertions.assertEquals(10, aluno.getId());
        Assertions.assertEquals("Fulano", aluno.getNome());
        Assertions.assertEquals("40566030063", aluno.getCpf().getNumero());
        Assertions.assertEquals("meuemail@example.com", aluno.getEmail().getEndereco());
        Assertions.assertNotNull(aluno.getTelelefones());
        Assertions.assertEquals(1, aluno.getTelelefones().size());
    }

    @Test
    public void test_criarAlunoComTelefones() {
        var aluno = AlunoBuilder.withoutId()
                .withNome("Fulano")
                .withCpf("40566030063")
                .withEmail("meuemail@example.com")
                .withTelefone("49", "999999999")
                .build();

        Assertions.assertNotNull(aluno);
        Assertions.assertEquals("Fulano", aluno.getNome());
        Assertions.assertEquals("40566030063", aluno.getCpf().getNumero());
        Assertions.assertEquals("meuemail@example.com", aluno.getEmail().getEndereco());
        Assertions.assertNotNull(aluno.getTelelefones());
        Assertions.assertEquals(1, aluno.getTelelefones().size());
        Assertions.assertEquals("49", aluno.getTelelefones().get(0).getDdd());
        Assertions.assertEquals("999999999", aluno.getTelelefones().get(0).getNumero());
    }

    @Test
    public void test_criarAlunoSemTelefones() {
        var aluno = AlunoBuilder.withoutId()
                .withNome("Fulano")
                .withCpf("40566030063")
                .withEmail("meuemail@example.com")
                .build();

        Assertions.assertNotNull(aluno);
        Assertions.assertEquals("Fulano", aluno.getNome());
        Assertions.assertEquals("40566030063", aluno.getCpf().getNumero());
        Assertions.assertEquals("meuemail@example.com", aluno.getEmail().getEndereco());
        Assertions.assertNotNull(aluno.getTelelefones());
        Assertions.assertEquals(0, aluno.getTelelefones().size());
    }

    @Test
    public void test_criarAlunoCpfInvalido() {
        var ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            AlunoBuilder.withoutId()
                    .withNome("Fulano")
                    .withCpf("405660300632")
                    .withEmail("meuemail@example.com")
                    .build();
        });

        Assertions.assertNotNull(ex);
        Assertions.assertTrue(ex.getMessage().toLowerCase().contains("cpf inválido"));
    }

    @Test
    public void test_criarAlunoEmailInvalido() {
        var ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            AlunoBuilder.withoutId().withNome("Fulano")
                    .withCpf("40566030063")
                    .withEmail("meuemail@example")
                    .build();
        });

        Assertions.assertNotNull(ex);
        Assertions.assertTrue(ex.getMessage().toLowerCase().contains("endereço de email inválido"));
    }

    @Test
    public void test_criarAlunoDDDInvalido() {
        var ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            AlunoBuilder.withoutId()
                    .withNome("Fulano")
                    .withCpf("40566030063")
                    .withEmail("meuemail@example.com")
                    .withTelefone("999", "999999999")
                    .build();
        });

        Assertions.assertNotNull(ex);
        Assertions.assertTrue(ex.getMessage().toLowerCase().contains("ddd inválido"));
    }

    @Test
    public void test_criarAlunoNumeroTelefoneInvalido() {
        var ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            AlunoBuilder.withoutId()
                    .withNome("Fulano")
                    .withCpf("40566030063")
                    .withEmail("meuemail@example.com")
                    .withTelefone("99", "9999999999")
                    .build();
        });

        Assertions.assertNotNull(ex);
        Assertions.assertTrue(ex.getMessage().toLowerCase().contains("número telefone inválido"));
    }

}