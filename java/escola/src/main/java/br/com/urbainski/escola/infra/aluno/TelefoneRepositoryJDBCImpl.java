package br.com.urbainski.escola.infra.aluno;

import br.com.urbainski.escola.dominio.aluno.CPF;
import br.com.urbainski.escola.dominio.aluno.Telefone;
import br.com.urbainski.escola.dominio.aluno.TelefoneRepository;
import br.com.urbainski.escola.infra.database.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TelefoneRepositoryJDBCImpl implements TelefoneRepository {

    private static final Logger logger = Logger.getLogger(TelefoneRepositoryJDBCImpl.class.getName());
    private final Connection connection;

    public TelefoneRepositoryJDBCImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void salvar(Integer idAluno, Telefone telefone) {
        var sql = "insert into telefone (idaluno, ddd, numero) values (?,?,?)";

        try (var ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idAluno);
            ps.setString(2, telefone.getDdd());
            ps.setString(3, telefone.getNumero());

            var result = ps.executeUpdate();

            if (result == 0) {
                throw new SQLException("Create telefone failed, no rows affected");
            }

            var idTelefone = DatabaseUtil.extractGeneratedId(ps);
            if (idTelefone == null) {
                throw new SQLException("Create telefone failed, no ID obtained");
            }

            telefone.setId(idTelefone);
            telefone.setIdAluno(idAluno);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Telefone> findByIdAluno(Integer idAluno) {
        var sql = "select t.id, t.idaluno, t.ddd, t.numero from telefone t where t.idaluno = ?";

        try (var ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, idAluno);

            List<Telefone> list = new ArrayList<>();

            try (var rs = ps.executeQuery()) {

                while (rs.next()) {
                    var idDb = rs.getInt(1);
                    var idAlunoDb = rs.getInt(2);
                    var dddDb = rs.getString(3);
                    var numeroDb = rs.getString(4);

                    list.add(new Telefone(idDb, idAlunoDb, dddDb, numeroDb));
                }
            }

            return list;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

}
