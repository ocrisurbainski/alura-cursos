package br.com.urbainski.ecommerce.users;

import br.com.urbainski.ecommerce.users.repository.UsersRepositoryFactory;

public class ServiceUsersMain {

    public static void main(String[] args) {
        var usersRepository = UsersRepositoryFactory.getInstance().getUsersRepository();
        var consumer = new CreateNewUserConsumerService(usersRepository);
        consumer.consume();
    }

}
