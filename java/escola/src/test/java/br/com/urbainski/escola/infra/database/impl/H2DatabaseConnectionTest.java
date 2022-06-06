package br.com.urbainski.escola.infra.database.impl;

import br.com.urbainski.escola.util.Constantes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class H2DatabaseConnectionTest {

    @Test
    public void test_getConnection() throws SQLException {
        System.setProperty(Constantes.DB_NAME_PARAM, "mem:test_escola.db");

        var connection = H2DatabaseConnection.getInstance().getConnection();

        Assertions.assertNotNull(connection);

        H2DatabaseConnection.clear();
    }

}