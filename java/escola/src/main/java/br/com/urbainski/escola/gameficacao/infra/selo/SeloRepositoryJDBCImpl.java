package br.com.urbainski.escola.gameficacao.infra.selo;

import br.com.urbainski.escola.gameficacao.dominio.selo.Selo;
import br.com.urbainski.escola.gameficacao.dominio.selo.SeloRepository;
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

public class SeloRepositoryJDBCImpl implements SeloRepository {

    private static final Logger logger = Logger.getLogger(SeloRepositoryJDBCImpl.class.getName());
    private final Connection connection;

    public SeloRepositoryJDBCImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void salvar(Selo selo) {
        var sql = "insert into selo (nome, cpf) values (?,?)";

        DatabaseUtil.setAutoCommit(connection, false);

        var savepoint = DatabaseUtil.setSavepoint(connection);

        try (var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, selo.getNome());
            ps.setString(2, selo.getCpf().getNumero());

            var result = ps.executeUpdate();

            if (result == 0) {
                throw new SQLException("Create selo failed, no rows affected");
            }

            var idAluno = DatabaseUtil.extractGeneratedId(ps);
            if (idAluno == null) {
                throw new SQLException("Create selo failed, no ID obtained");
            }

            selo.setId(idAluno);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            DatabaseUtil.rollback(connection, savepoint);
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Selo> findByCpf(CPF cpf) {
        var sql = "select id, nome, cpf from selo where cpf = ?";

        var selos = new ArrayList<Selo>();

        try (var ps = this.connection.prepareStatement(sql)) {
            ps.setString(1, cpf.getNumero());
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    Selo selo = extractSeloFromResultSet(rs);
                    selos.add(selo);
                }
            }

            return selos;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    private Selo extractSeloFromResultSet(ResultSet rs) throws SQLException {
        var idDb = rs.getInt(1);
        var nomeDb = rs.getString(2);
        var cpfDb = rs.getString(3);

        return new Selo(idDb, nomeDb, new CPF(cpfDb));
    }

}
