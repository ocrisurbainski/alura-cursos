package br.com.urbainski.ecommerce.fraude;

import br.com.urbainski.ecommerce.commons.kafka.AbstractDefaultConsumer;
import br.com.urbainski.ecommerce.commons.kafka.Topics;
import br.com.urbainski.ecommerce.commons.order.Order;
import br.com.urbainski.ecommerce.order.NewOrderApprovedProducerService;
import br.com.urbainski.ecommerce.order.NewOrderRejectedProducerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class FraudeDetectorConsumerService extends AbstractDefaultConsumer<String, Order> {

    private static final Logger log = LoggerFactory.getLogger(FraudeDetectorConsumerService.class);

    private static final BigDecimal VALUE_ORDER_TO_REFLECT_ON_FRAUD = new BigDecimal("4500");

    private NewOrderApprovedProducerService newOrderApprovedProducerService;

    private NewOrderRejectedProducerService newOrderRejectedProducerService;

    @Override
    public String getGroupId() {
        return FraudeDetectorConsumerService.class.getSimpleName();
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

        var order = record.value();
        var isFraud = isFraud(order);

        if (isFraud) {
            getNewOrderRejectedProducerService().send(order.getUserId(), order);
            getLog().info("Está ordem É suspeita de ser uma fraude.");
        } else {
            getNewOrderApprovedProducerService().send(order.getUserId(), order);
            getLog().info("Está ordem NÃO TEM suspeita de ser uma fraude.");
        }
    }

    private boolean isFraud(Order order) {

        return VALUE_ORDER_TO_REFLECT_ON_FRAUD.compareTo(order.getValue()) < 0;
    }

    private NewOrderApprovedProducerService getNewOrderApprovedProducerService() {
        if (newOrderApprovedProducerService == null) {
            newOrderApprovedProducerService = new NewOrderApprovedProducerService();
        }
        return newOrderApprovedProducerService;
    }

    private NewOrderRejectedProducerService getNewOrderRejectedProducerService() {
        if (newOrderRejectedProducerService == null) {
            newOrderRejectedProducerService = new NewOrderRejectedProducerService();
        }
        return newOrderRejectedProducerService;
    }
}
