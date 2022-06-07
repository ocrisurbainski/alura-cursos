package br.com.urbainski.escola.gameficacao.infra.selo;

import br.com.urbainski.escola.gameficacao.dominio.selo.Selo;
import br.com.urbainski.escola.gameficacao.dominio.selo.SeloRepository;
import br.com.urbainski.escola.shared.dominio.CPF;
import br.com.urbainski.escola.shared.infra.database.H2DatabaseConnection;
import br.com.urbainski.escola.shared.util.Constantes;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeloRepositoryJDBCImplTest {

    private static SeloRepository seloRepository;

    @BeforeAll
    public static void setUp() {
        System.setProperty(Constantes.DB_NAME_PARAM, "mem:test_escola.db");

        var connection = H2DatabaseConnection.getInstance().getConnection();

        seloRepository = new SeloRepositoryJDBCImpl(connection);
    }

    @AfterAll
    public static void destroy() throws SQLException {
        H2DatabaseConnection.clear();
    }

    @Test
    @Order(1)
    public void test_salvar() {
        var selo = new Selo("Selo 1", new CPF("12345678900"));

        seloRepository.salvar(selo);

        Assertions.assertNotNull(selo.getId());
        Assertions.assertEquals("Selo 1", selo.getNome());
        Assertions.assertEquals("12345678900", selo.getCpf().getNumero());
    }

    @Test
    @Order(1)
    public void test_findByCpf() {
        var selo = new Selo("Selo 2", new CPF("12345678900"));

        seloRepository.salvar(selo);

        var selos = seloRepository.findByCpf(new CPF("12345678900"));

        Assertions.assertNotNull(selos);
        Assertions.assertEquals(2, selos.size());
    }

}