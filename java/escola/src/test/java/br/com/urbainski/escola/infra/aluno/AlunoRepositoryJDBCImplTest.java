package br.com.urbainski.escola.infra.aluno;

import br.com.urbainski.escola.dominio.aluno.AlunoNotFound;
import br.com.urbainski.escola.dominio.aluno.AlunoRepository;
import br.com.urbainski.escola.dominio.aluno.CPF;
import br.com.urbainski.escola.dominio.aluno.Telefone;
import br.com.urbainski.escola.dominio.aluno.builder.AlunoBuilder;
import br.com.urbainski.escola.infra.database.impl.H2DatabaseConnection;
import br.com.urbainski.escola.util.Constantes;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlunoRepositoryJDBCImplTest {

    private static AlunoRepository alunoRepository;

    @BeforeAll
    public static void setUp() {
        System.setProperty(Constantes.DB_NAME_PARAM, "mem:test_escola.db");

        var connection = H2DatabaseConnection.getInstance().getConnection();

        var telefoneRepository = new TelefoneRepositoryJDBCImpl(connection);

        alunoRepository = new AlunoRepositoryJDBCImpl(connection, telefoneRepository);
    }

    @AfterAll
    public static void destroy() throws SQLException {
        H2DatabaseConnection.clear();
    }

    @Test
    @Order(1)
    public void test_matricular() {
        var aluno = AlunoBuilder.withoutId()
                .withNome("Fulano")
                .withCpf("40566030063")
                .withEmail("meuemail@example.com")
                .withTelefone("49", "999999999")
                .build();

        alunoRepository.matriculate(aluno);

        Assertions.assertNotNull(aluno.getId());
        Assertions.assertNotNull(aluno.getTelelefones().get(0).getIdAluno());
        Assertions.assertNotNull(aluno.getTelelefones().get(0).getId());
        Assertions.assertEquals(aluno.getId(), aluno.getTelelefones().get(0).getIdAluno());
    }

    @Test
    @Order(2)
    public void test_matricular_roolback() throws NoSuchFieldException, IllegalAccessException {
        var aluno = AlunoBuilder.withoutId()
                .withNome("Fulano")
                .withCpf("40566030063")
                .withEmail("meuemail@example.com")
                .withTelefone("49", "999999999")
                .build();

        var telefone = aluno.getTelelefones().get(0);

        var field = Telefone.class.getDeclaredField("ddd");
        field.setAccessible(true);
        field.set(telefone, "999");

        Assertions.assertThrows(RuntimeException.class, () -> alunoRepository.matriculate(aluno));
    }

    @Test
    @Order(3)
    public void test_findByCpf() {
        var alunoDb = alunoRepository.findByCpf(new CPF("40566030063"));

        Assertions.assertNotNull(alunoDb);
        Assertions.assertEquals(1, alunoDb.getTelelefones().size());
    }

    @Test
    @Order(4)
    public void test_findByCpf_notFound() {
        Assertions.assertThrows(AlunoNotFound.class, () -> alunoRepository.findByCpf(new CPF("40542265079")));
    }

    @Test
    @Order(5)
    public void test_findAll() {
        var alunos = alunoRepository.findAll();

        Assertions.assertNotNull(alunos);
        Assertions.assertFalse(alunos.isEmpty());
        Assertions.assertEquals(1, alunos.size());
    }

}