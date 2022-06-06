package br.com.urbainski.escola;

import br.com.urbainski.escola.infra.database.impl.H2DatabaseConnection;
import br.com.urbainski.escola.util.Constantes;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        System.setProperty(Constantes.DB_NAME_PARAM, "./escola.db");

        try (var connection = H2DatabaseConnection.getInstance().getConnection()) {

        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

}
