package br.com.urbainski.escola.academico.infra.aluno;

import br.com.urbainski.escola.academico.dominio.aluno.*;
import br.com.urbainski.escola.academico.dominio.aluno.builder.AlunoBuilder;
import br.com.urbainski.escola.shared.dominio.CPF;
import br.com.urbainski.escola.shared.infra.database.DatabaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlunoRepositoryJDBCImpl implements AlunoRepository {

    private static final Logger logger = Logger.getLogger(AlunoRepositoryJDBCImpl.class.getName());
    private final Connection connection;
    private final TelefoneRepository telefoneRepository;

    public AlunoRepositoryJDBCImpl(Connection connection, TelefoneRepository telefoneRepository) {
        this.connection = connection;
        this.telefoneRepository = telefoneRepository;
    }

    @Override
    public void matriculate(Aluno aluno) {

        var sql = "insert into aluno (nome, cpf, email) values (?,?,?)";

        DatabaseUtil.setAutoCommit(connection, false);

        var savepoint = DatabaseUtil.setSavepoint(connection);

        try (var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, aluno.getNome());
            ps.setString(2, aluno.getCpf().getNumero());
            ps.setString(3, aluno.getEmail().getEndereco());

            var result = ps.executeUpdate();

            if (result == 0) {
                throw new SQLException("Create aluno failed, no rows affected");
            }

            var idAluno = DatabaseUtil.extractGeneratedId(ps);
            if (idAluno == null) {
                throw new SQLException("Create aluno failed, no ID obtained");
            }

            aluno.setId(idAluno);
            aluno.getTelelefones().forEach(telefone -> telefoneRepository.salvar(aluno.getId(), telefone));
        } catch (Exception ex) {
            DatabaseUtil.rollback(connection, savepoint);
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @Override
    public Aluno findByCpf(CPF cpf) {
        var sql = "select id, nome, cpf, email from aluno where cpf = ?";

        try (var ps = this.connection.prepareStatement(sql)) {
            ps.setString(1, cpf.getNumero());
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractAlunoFromResultSet(rs);
                }
                throw new AlunoNotFound(cpf);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Aluno> findAll() {
        var sql = "select id, nome, cpf, email from aluno";

        var alunos = new ArrayList<Aluno>();

        try (var ps = this.connection.prepareStatement(sql)) {
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    Aluno aluno = extractAlunoFromResultSet(rs);
                    alunos.add(aluno);
                }
            }
            return alunos;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    private Aluno extractAlunoFromResultSet(ResultSet rs) throws SQLException {
        var idAlunoDb = rs.getInt(1);
        var nomeDb = rs.getString(2);
        var cpfDb = rs.getString(3);
        var emailDb = rs.getString(4);
        var listTelefones = telefoneRepository.findByIdAluno(idAlunoDb);

        return AlunoBuilder.withId(idAlunoDb)
                .withNome(nomeDb)
                .withCpf(cpfDb)
                .withEmail(emailDb)
                .withTelefones(listTelefones)
                .build();
    }

}
