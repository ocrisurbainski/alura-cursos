package br.com.urbainski.ecommerce.users;

import br.com.urbainski.ecommerce.commons.kafka.AbstractDefaultConsumer;
import br.com.urbainski.ecommerce.commons.kafka.Topics;
import br.com.urbainski.ecommerce.commons.order.Order;
import br.com.urbainski.ecommerce.users.model.Users;
import br.com.urbainski.ecommerce.users.repository.UsersRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class CreateNewUserConsumerService extends AbstractDefaultConsumer<String, Order> {

    private static final Logger log = LoggerFactory.getLogger(CreateNewUserConsumerService.class);

    private final UsersRepository usersRepository;

    public CreateNewUserConsumerService(UsersRepository usersRepository) {

        this.usersRepository = usersRepository;
    }

    @Override
    public String getGroupId() {
        return CreateNewUserConsumerService.class.getSimpleName();
    }

    @Override
    public List<Topics> getTopics() {
        return List.of(Topics.ECOMMERCE_NEW_ORDER);
    }

    @Override
    public Logger getLog() {
        return log;
    }

    @Override
    public void processarRecord(ConsumerRecord<String, Order> record) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        var order = record.value();

        if (usersRepository.existsUsersByEmail(order.getEmail())) {

            getLog().info("Usuário já existe.");
        } else {

            var users = new Users(UUID.randomUUID().toString(), order.getEmail());

            boolean result = usersRepository.save(users);

            getLog().info("Usuário foi salvo? " + result);
            getLog().info(users.toString());
        }
    }

}
