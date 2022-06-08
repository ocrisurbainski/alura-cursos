package br.com.urbainski.escola.gameficacao.aplicacao.selo;

import br.com.urbainski.escola.academico.dominio.aluno.AlunoRepository;
import br.com.urbainski.escola.academico.infra.aluno.AlunoRepositoryJDBCImpl;
import br.com.urbainski.escola.academico.infra.aluno.TelefoneRepositoryJDBCImpl;
import br.com.urbainski.escola.gameficacao.dominio.selo.SeloRepository;
import br.com.urbainski.escola.gameficacao.infra.selo.SeloRepositoryJDBCImpl;
import br.com.urbainski.escola.shared.dominio.CPF;
import br.com.urbainski.escola.shared.dominio.evento.Evento;
import br.com.urbainski.escola.shared.dominio.evento.PublicadorEvento;
import br.com.urbainski.escola.shared.dominio.evento.PublicadorEventoDefault;
import br.com.urbainski.escola.shared.dominio.evento.TipoEventoEnum;
import br.com.urbainski.escola.shared.infra.database.H2DatabaseConnection;
import br.com.urbainski.escola.shared.util.Constantes;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;

public class GeraSeloAlunoNovatoTest {

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
    public void test_executar() {
        PublicadorEvento publicadorEvento = PublicadorEventoDefault.getInstance();
        publicadorEvento.adicionar(new GeraSeloAlunoNovato(seloRepository));
        publicadorEvento.publicar(new Evento() {
            @Override
            public LocalDateTime getDataHora() {
                return LocalDateTime.now();
            }

            @Override
            public TipoEventoEnum getTipoEvento() {
                return TipoEventoEnum.ALUNO_MATRICULADO;
            }

            @Override
            public Map<String, Object> getInformacoes() {
                return Map.of("cpf", new CPF("12345678900"));
            }
        });

        var selos = seloRepository.findByCpf(new CPF("12345678900"));

        Assertions.assertNotNull(selos);
        Assertions.assertEquals(1, selos.size());
    }

}