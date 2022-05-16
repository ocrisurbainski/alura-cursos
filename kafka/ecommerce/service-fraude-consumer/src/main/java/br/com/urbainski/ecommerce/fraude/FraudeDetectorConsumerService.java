package br.com.urbainski.ecommerce.fraude;

import br.com.urbainski.ecommerce.commons.kafka.AbstractDefaultConsumer;
import br.com.urbainski.ecommerce.commons.kafka.Topics;
import br.com.urbainski.ecommerce.commons.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FraudeDetectorConsumerService extends AbstractDefaultConsumer<String, Order> {

    private static final Logger log = LoggerFactory.getLogger(FraudeDetectorConsumerService.class);

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

}
