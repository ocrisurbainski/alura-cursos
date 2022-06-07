package br.com.urbainski.escola.aplicacao.aluno.matricular;

import br.com.urbainski.escola.dominio.aluno.Aluno;
import br.com.urbainski.escola.dominio.aluno.AlunoMatriculadoEvento;
import br.com.urbainski.escola.dominio.aluno.AlunoRepository;
import br.com.urbainski.escola.dominio.aluno.CPF;
import br.com.urbainski.escola.dominio.evento.PublicadorEvento;
import br.com.urbainski.escola.infra.aluno.AlunoRepositoryJDBCImpl;
import br.com.urbainski.escola.infra.aluno.TelefoneRepositoryJDBCImpl;
import br.com.urbainski.escola.infra.database.impl.H2DatabaseConnection;
import br.com.urbainski.escola.util.Constantes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MatricularAlunoTest {

    @Mock
    private PublicadorEvento publicadorEvento;

    private static AlunoRepository alunoRepository;

    @BeforeAll
    public static void setUp() {
        System.setProperty(Constantes.DB_NAME_PARAM, "mem:test_escola.db");

        var connection = H2DatabaseConnection.getInstance().getConnection();

        var telefoneRepository = new TelefoneRepositoryJDBCImpl(connection);

        alunoRepository = new AlunoRepositoryJDBCImpl(connection, telefoneRepository);
    }

    @Test
    public void test_executar() {

        MatricularAlunoDTO dto = new MatricularAlunoDTO("Fulano", "12345678900", "fulano@example.com");

        MatricularAluno useCase = new MatricularAluno(alunoRepository, publicadorEvento);

        useCase.executar(dto);

        Aluno encontrado = alunoRepository.findByCpf(new CPF("12345678900"));

        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("Fulano", encontrado.getNome());
        Assertions.assertEquals("12345678900", encontrado.getCpf().getNumero());
        Assertions.assertEquals("fulano@example.com", encontrado.getEmail().getEndereco());

        Mockito.verify(publicadorEvento, Mockito.times(1)).publicar(Mockito.any(AlunoMatriculadoEvento.class));
    }

}