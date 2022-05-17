package br.com.urbainski.ecommerce.order;

import br.com.urbainski.ecommerce.commons.kafka.AbstractDefaultProducer;
import br.com.urbainski.ecommerce.commons.kafka.Topics;
import br.com.urbainski.ecommerce.commons.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewOrderRejectedProducerService extends AbstractDefaultProducer<String, Order> {

    private static final Logger log = LoggerFactory.getLogger(NewOrderApprovedProducerService.class);

    @Override
    public Topics getTopic() {
        return Topics.ECOMMERCE_NEW_ORDER_REJECTED;
    }

    @Override
    public Logger getLog() {
        return log;
    }

}
