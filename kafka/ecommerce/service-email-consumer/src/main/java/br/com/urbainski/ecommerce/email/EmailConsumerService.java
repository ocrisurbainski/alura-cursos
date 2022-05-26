package br.com.urbainski.ecommerce.email;

import br.com.urbainski.ecommerce.commons.email.Email;
import br.com.urbainski.ecommerce.commons.kafka.AbstractDefaultConsumer;
import br.com.urbainski.ecommerce.commons.kafka.MyMessage;
import br.com.urbainski.ecommerce.commons.kafka.Topics;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EmailConsumerService extends AbstractDefaultConsumer<String, Email> {

    private static final Logger log = LoggerFactory.getLogger(EmailConsumerService.class);

    public EmailConsumerService() {}

    @Override
    public void processarRecord(ConsumerRecord<String, MyMessage<Email>> record) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (Character.isDigit(record.value().getPayload().getOrderId().charAt(0))) {
            throw new RuntimeException("Email Problem.");
        }
    }

    @Override
    public String getGroupId() {
        return EmailConsumerService.class.getSimpleName();
    }

    @Override
    public List<Topics> getTopics() {
        return List.of(Topics.ECOMMERCE_SEND_EMAIL);
    }

    @Override
    public Logger getLog() {
        return log;
    }

}
