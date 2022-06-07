package br.com.urbainski.escola.shared.infra.database;

import br.com.urbainski.escola.util.Constantes;
import br.com.urbainski.escola.util.ValidationUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class H2DatabaseConnection implements SqlDatabaseConnection {

    private static final Logger logger = Logger.getLogger(H2DatabaseConnection.class.getName());

    private static H2DatabaseConnection INSTANCE;
    private final Connection connection;

    public H2DatabaseConnection() {
        this.connection = createConnection();
        createTables();
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    private Connection createConnection() {
        try {
            logger.info("Load h2 JDBC driver...");
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }

        var dbName = System.getProperty(Constantes.DB_NAME_PARAM);
        if (ValidationUtils.isEmpty(dbName)) {
            throw new IllegalStateException(String.format("'%s' variable has not been set.", Constantes.DB_NAME_PARAM));
        }

        try {
            logger.info("Create h2 JDBC connection...");
            return DriverManager.getConnection(String.format("jdbc:h2:%s", dbName));
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    private void createTables() {
        createTableAluno();
        createTableTelefone();
    }

    private void createTableAluno() {
        var columnId = "id bigint auto_increment primary key";
        var columnNome = "nome varchar(255) not null";
        var columnCpf = "cpf varchar(11) not null";
        var columnEmail = "email varchar(255) not null";

        var tblAluno = String.format(
                "create table if not exists aluno (%s, %s, %s, %s)",
                columnId,
                columnNome,
                columnCpf,
                columnEmail);

        logger.info("h2 create table of alunos...");

        executeSql(tblAluno);
    }

    private void createTableTelefone() {
        var columnId = "id bigint auto_increment primary key";
        var columnIdAluno = "idaluno bigint not null";
        var columnDdd = "ddd varchar(2) not null";
        var columnNumero = "numero varchar(9) not null";
        var foreignKeyAluno = "foreign key (idaluno) references aluno(id)";

        var tblTelefone = String.format(
                "create table if not exists telefone (%s, %s, %s, %s, %s)",
                columnId,
                columnIdAluno,
                columnDdd,
                columnNumero,
                foreignKeyAluno);

        logger.info("h2 create table of telefones...");

        executeSql(tblTelefone);
    }

    private void executeSql(String sql) {
        try (var ps = this.connection.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    public static H2DatabaseConnection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new H2DatabaseConnection();
        }
        return INSTANCE;
    }

    public static void clear() throws SQLException {
        if (INSTANCE == null) {
            return;
        }

        INSTANCE.getConnection().close();
        INSTANCE = null;
    }

}
