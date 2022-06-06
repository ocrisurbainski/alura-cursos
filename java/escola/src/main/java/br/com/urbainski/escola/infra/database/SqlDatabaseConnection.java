package br.com.urbainski.escola.infra.database;

import java.sql.Connection;

public interface SqlDatabaseConnection {

    Connection getConnection();

}
