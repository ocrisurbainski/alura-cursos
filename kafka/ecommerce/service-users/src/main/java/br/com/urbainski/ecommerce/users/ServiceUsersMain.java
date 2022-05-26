package br.com.urbainski.ecommerce.users;

import br.com.urbainski.ecommerce.commons.kafka.consumer.ConsumerRunner;
import br.com.urbainski.ecommerce.users.repository.UsersRepositoryFactory;

public class ServiceUsersMain {

    public static void main(String[] args) {
        new ConsumerRunner<>(() -> {
            var usersRepository = UsersRepositoryFactory.getInstance().getUsersRepository();
            return new CreateNewUserConsumerService(usersRepository);
        }).start();
    }

}
