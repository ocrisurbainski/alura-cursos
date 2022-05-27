package br.com.urbainski.ecommerce.users;

import br.com.urbainski.ecommerce.commons.kafka.consumer.ConsumerRunner;
import br.com.urbainski.ecommerce.users.consumer.CreateNewUserConsumerService;
import br.com.urbainski.ecommerce.users.consumer.ReportForAllUsersConsumerService;
import br.com.urbainski.ecommerce.users.repository.UsersRepositoryFactory;

public class ServiceUsersMain {

    public static void main(String[] args) {
        var usersRepository = UsersRepositoryFactory.getInstance().getUsersRepository();

        new ConsumerRunner<>(() -> new CreateNewUserConsumerService(usersRepository)).start();

        new ConsumerRunner<>(() -> new ReportForAllUsersConsumerService(usersRepository)).start(3);
    }

}
