package br.com.urbainski.ecommerce.order;

import br.com.urbainski.ecommerce.commons.kafka.producer.AbstractDefaultProducer;
import br.com.urbainski.ecommerce.commons.kafka.Topics;
import br.com.urbainski.ecommerce.commons.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewOrderApprovedProducerService extends AbstractDefaultProducer<String, Order> {

    private static final Logger log = LoggerFactory.getLogger(NewOrderApprovedProducerService.class);

    @Override
    public Topics getTopic() {
        return Topics.ECOMMERCE_NEW_ORDER_APPROVED;
    }

    @Override
    public Logger getLog() {
        return log;
    }

}
