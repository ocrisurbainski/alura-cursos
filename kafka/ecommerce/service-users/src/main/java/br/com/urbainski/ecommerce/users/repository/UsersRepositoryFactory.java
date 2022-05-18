package br.com.urbainski.ecommerce.users.repository;

import br.com.urbainski.ecommerce.users.database.DatabaseConnection;

public class UsersRepositoryFactory {

    private static UsersRepositoryFactory INSTANCE;
    private UsersRepository usersRepository;

    private UsersRepositoryFactory() {

    }

    public static UsersRepositoryFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UsersRepositoryFactory();
        }
        return INSTANCE;
    }

    public UsersRepository getUsersRepository() {
        if (usersRepository == null) {
            usersRepository = new UsersRepository(DatabaseConnection.getInstance().getConnection());
        }
        return usersRepository;
    }

}
