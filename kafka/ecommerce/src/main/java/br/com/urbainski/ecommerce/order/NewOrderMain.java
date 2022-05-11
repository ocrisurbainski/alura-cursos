package br.com.urbainski.ecommerce.order;

import br.com.urbainski.ecommerce.kafka.AbstractDefaultProducer;
import br.com.urbainski.ecommerce.kafka.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewOrderMain extends AbstractDefaultProducer<String> {

    private static final Logger log = LoggerFactory.getLogger(NewOrderMain.class);

    public static void main(String[] args) {

        var value = "1,1,1250.00";

        var newOrder = new NewOrderMain();
        newOrder.send(value);
    }

    @Override
    public Topics getTopic() {
        return Topics.ECOMMERCE_NEW_ORDER;
    }

    @Override
    public Logger getLog() {
        return log;
    }

}
