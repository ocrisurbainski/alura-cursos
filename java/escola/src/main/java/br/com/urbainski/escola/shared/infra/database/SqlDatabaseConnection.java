package br.com.urbainski.escola.shared.infra.database;

import java.sql.Connection;

public interface SqlDatabaseConnection {

    Connection getConnection();

}
