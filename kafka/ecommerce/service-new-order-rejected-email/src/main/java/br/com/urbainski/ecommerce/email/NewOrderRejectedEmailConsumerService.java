package br.com.urbainski.ecommerce.email;

import br.com.urbainski.ecommerce.commons.email.Email;
import br.com.urbainski.ecommerce.commons.kafka.AbstractDefaultConsumer;
import br.com.urbainski.ecommerce.commons.kafka.MyMessage;
import br.com.urbainski.ecommerce.commons.kafka.Topics;
import br.com.urbainski.ecommerce.commons.order.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NewOrderRejectedEmailConsumerService extends AbstractDefaultConsumer<String, Order> {

    private static final Logger log = LoggerFactory.getLogger(NewOrderRejectedEmailConsumerService.class);

    private EmailProducerService emailProducerService;

    @Override
    public void processarRecord(ConsumerRecord<String, MyMessage<Order>> record) {
        super.processarRecord(record);

        var message = record.value();
        var order = message.getPayload();

        var emailMessage = String.format("A venda da order: %s está sendo rejeitada, infelizmente não poderemos dar sequência a venda.", order.getOrderId());
        var title = "Venda Rejeitada";

        var email = new Email(order.getOrderId(), title, emailMessage, order.getEmail());

        getEmailProducerService().send(
                message.getCorrelationId().continueWith(getClass().getSimpleName()),
                order.getEmail(),
                email);
    }

    @Override
    public String getGroupId() {
        return NewOrderRejectedEmailConsumerService.class.getSimpleName();
    }

    @Override
    public List<Topics> getTopics() {
        return List.of(Topics.ECOMMERCE_NEW_ORDER_REJECTED);
    }

    @Override
    public Logger getLog() {
        return log;
    }

    private EmailProducerService getEmailProducerService() {
        if (emailProducerService == null) {
            emailProducerService = new EmailProducerService();
        }
        return emailProducerService;
    }

}
