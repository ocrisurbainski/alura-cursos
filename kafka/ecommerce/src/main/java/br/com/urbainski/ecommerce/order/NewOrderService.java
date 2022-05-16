package br.com.urbainski.ecommerce.order;

import br.com.urbainski.ecommerce.kafka.AbstractDefaultProducer;
import br.com.urbainski.ecommerce.kafka.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewOrderService extends AbstractDefaultProducer<String, Order> {

    private static final Logger log = LoggerFactory.getLogger(NewOrderService.class);

    @Override
    public Topics getTopic() {
        return Topics.ECOMMERCE_NEW_ORDER;
    }

    @Override
    public Logger getLog() {
        return log;
    }

}
